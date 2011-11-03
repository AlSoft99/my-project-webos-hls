package com.cn.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.cn.voa.Hia;
import com.cn.voa.Hoa;
import com.hrm.control.Request;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;
import com.hrm.vo.BaseVo;

public class SocketService implements Runnable {
	
	private Socket socket = null;
	public SocketService(Socket socket){
		this.socket = socket;
	}
	
	public void run() {
		String allReponseString = "";
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			InputStream is = socket.getInputStream();
			dis = new DataInputStream(is);
			OutputStream os = socket.getOutputStream();
			dos = new DataOutputStream(os);
			//得到报文长度
			int msgLen = readHeaderLen(dis);
			//解析报文
			String line = readClientMsg(dis,msgLen);
			Hia hia = ClsFactory.newInstance().getFactory().getBean("hia", Hia.class);
			hia.setLen(msgLen);
			String bodyLine = parseInputHeaderData(hia,line);
			ClsFactory.newInstance().info("开始处理交易码为["+hia.getTxCode()+"]的交易");
			//组装业务类报文体到JavaBean
			Object obj = parseInputBodyData(hia,bodyLine);
			Request request = new Request();
			setHeaderBaseAttribute(request);
			request.setAttribute(Constant.ATTRIBUTE_INPUT_SOCKET, obj);
			request.setAttribute(Constant.HIA_SOCKET, hia);
			request.setAttribute(Constant.TX_CODE, hia.getTxCode());
			//发送数据到业务类, 并且得到响应Request对象
			Request output_request = ClsFactory.newInstance().getFactory().getBean(Constant.CLASSNAME_SOCKET[0]+hia.getTxCode()+Constant.CLASSNAME_SOCKET[1],BaseVo.class).execute(request);
			String responseHeader = parseOutputHeaderData(output_request.getAttribute(Constant.HEADER_OUTPUT_SOCKET));
			allReponseString += responseHeader;
			String responseBody = parseOutputBodyData(output_request.getAttribute(Constant.BODY_OUTPUT_SOCKET),hia);
			allReponseString += responseBody;
			allReponseString = StringUtil.newInstance().numberToZero(allReponseString.getBytes().length, 8)+allReponseString;
			ClsFactory.newInstance().info("返回通讯报文头:"+responseHeader);
			ClsFactory.newInstance().info("返回通讯报文体:"+responseBody);
			ClsFactory.newInstance().info("返回讯通整体报文:"+allReponseString);
			
			writeClientMsgByte(dos,allReponseString);
		} catch (IOException e) {
			ClsFactory.newInstance().error("socket异常:", e);
		} catch (Exception e) {
			allReponseString += allReponseString+e.getMessage();
			allReponseString = StringUtil.newInstance().numberToZero(allReponseString.getBytes().length, 8)+allReponseString;
			try {
				writeClientMsgByte(dos,allReponseString);
			} catch (IOException e1) {
				ClsFactory.newInstance().error("socket异常:", e);
			}
			ClsFactory.newInstance().error("socket异常:", e);
		}
		finally{
			if (dis!=null) {
				try {
					dis.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("socket的close异常:", e);
				}
			}
			if (dos!=null) {
				try {
					dos.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("socket的close异常:", e);
				}
			}
			if (socket!=null) {
				try {
					socket.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("socket的close异常:", e);
				}
			}
		}
	}

	/**
	 * 通过UTF格式得到客户端消息
	 * @param dis
	 * @return
	 */
	public String readClientMsgUtf(DataInputStream dis){
		String line = "";
		try {
			line =  dis.readUTF();
		} catch (IOException e) {
			ClsFactory.newInstance().error("socket的IO异常:", e);
		}
		return line;
	}
	/**
	 * 通过字节流往客户端写消息
	 * @param dos
	 * @param lineByte
	 */
	public void writeClientMsgByte(DataOutputStream dos,String line) throws IOException{
		dos.write(line.getBytes());
	}
	/**
	 * 通过字节流得到客户端消息
	 * @param dis
	 * @return
	 * @throws IOException 
	 */
	public String readClientMsg(DataInputStream dis,int msgLen) throws IOException{
		byte[] body = new byte[msgLen];
		dis.read(body);
		return new String(body);
	}
	public int readHeaderLen(DataInputStream dis) throws IOException{
		byte[] len = new byte[Constant.MSG_LEN];
		dis.read(len);
		return Integer.parseInt(new String(len));
	}
	public String parseInputHeaderData(Hia hia,String line) throws Exception{
		return StringUtil.newInstance().inputData(hia, line);
	}
	public Object parseInputBodyData(Hia hia,String line) throws Exception{
		String inputCode = Constant.CLASSNAME_SOCKET_OUTPUT[0]+hia.getTxCode()+Constant.CLASSNAME_SOCKET_OUTPUT[1];
		Object inputObj = ClsFactory.newInstance().getFactory().getBean(inputCode);
		StringUtil.newInstance().inputData(inputObj, line);
		return inputObj;
	}
	public String parseOutputHeaderData(Object hoa) throws Exception{
		Hoa hoaBean = ClsFactory.newInstance().getFactory().getBean("hoa", Hoa.class);
		String response = StringUtil.newInstance().outputData(hoaBean, hoa);
		return response;
	}
	public String parseOutputBodyData(Object tbody,Hia hia) throws Exception{
		String name = Constant.CLASSNAME_SOCKET_INPUT[0]+hia.getTxCode()+Constant.CLASSNAME_SOCKET_INPUT[1];
		Object sbody = ClsFactory.newInstance().getFactory().getBean(name);
		String response = StringUtil.newInstance().outputData(sbody, tbody);
		return response;
	}
	public void setHeaderBaseAttribute(Request request){
		Hoa hoa = new Hoa();
		hoa.setLen("len");
		hoa.setRespMsg("RespMsg");
		hoa.setTxCode("txCode");
		request.setAttribute(Constant.HEADER_OUTPUT_SOCKET, hoa);
	}
}
