package com.cn.cmm;

import com.cn.socket.SocketClient;
import com.cn.voa.Hia;
import com.hrm.cmm.client.CInput_PME111_Vo;
import com.hrm.control.Request;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.util.StringUtil;

public class ClientAgent {
	//socket发送服务端的ip
	private String socketServerIp;
	//socket发送服务端的端口
	private int socketServerPort;
	public String getSocketServerIp() {
		return socketServerIp;
	}
	public void setSocketServerIp(String socketServerIp) {
		this.socketServerIp = socketServerIp;
	}
	public int getSocketServerPort() {
		return socketServerPort;
	}
	public void setSocketServerPort(int socketServerPort) {
		this.socketServerPort = socketServerPort;
	}
	
	public void sendMsgAgent(String sendFlag,Request request) throws Exception{ 
		if(sendFlag.equals(Constant.ATTRIBUTE_SOCKET)){
			SocketClient sc = new SocketClient(socketServerIp,socketServerPort);
			Object tObj = request.getAttribute(Constant.CLIENT_OUTPUT_SOCKET);
			Hia hia = (Hia)request.getAttribute(Constant.HIA_SOCKET);
			String oCode = "COutput_"+hia.getTxCode()+"_Vo";
			String iCode = "CInput_"+hia.getTxCode()+"_Vo";
			Object sObj = ClsFactory.newInstance().getFactory().getBean(oCode);
			String line = StringUtil.newInstance().outputData(sObj, tObj);
			String returnLine = sc.send(line);
			Object iObj = ClsFactory.newInstance().getFactory().getBean(iCode);
			StringUtil.newInstance().inputData(iObj, returnLine);
			request.setAttribute(Constant.CLIENT_INPUT_SOCKET, iObj);
		}
	}
}
