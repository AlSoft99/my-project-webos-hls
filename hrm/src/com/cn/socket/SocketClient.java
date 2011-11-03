package com.cn.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;

public class SocketClient{
	private Socket socket;
	public SocketClient(String ip,int port){
		try {
			socket = new Socket(ip,port);
		} catch (UnknownHostException e) {
			ClsFactory.newInstance().error("SocketClient报错", e);
		} catch (IOException e) {
			ClsFactory.newInstance().error("SocketClient报错", e);
		}
	}
	public String send(String message) {
		String res = "";
		DataOutputStream out = null;
		DataInputStream in = null;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			out.write(message.getBytes());
			out.flush();
			//得到报文长度
			int msgLen = readHeaderLen(in);
			//解析报文
			res = readClientMsg(in,msgLen);
		} catch (UnknownHostException e) {
			ClsFactory.newInstance().error("SocketClient报错", e);
		} catch (IOException e) {
			ClsFactory.newInstance().error("SocketClient报错", e);
		}
		finally{
			if (out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("SocketClient报错", e);
				}
			}
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("SocketClient报错", e);
				}
			}
			if (socket!=null) {
				try {
					socket.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("SocketClient报错", e);
				}
			}
		}
		return res;
	}
	public int readHeaderLen(DataInputStream dis) throws IOException{
		byte[] len = new byte[Constant.MSG_LEN];
		dis.read(len);
		return Integer.parseInt(new String(len));
	}
	public String readClientMsg(DataInputStream dis,int msgLen) throws IOException{
		byte[] body = new byte[msgLen];
		dis.read(body);
		return new String(body);
	}

}
