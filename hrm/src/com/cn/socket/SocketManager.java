package com.cn.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.hrm.util.ClsFactory;


public class SocketManager extends Thread{
	private int serverPort;
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public void run() {
		Socket socket = null;
		try {
			ServerSocket ss = new ServerSocket(serverPort);
			while(true){
				ClsFactory.newInstance().info("socket已经启动,端口号为:"+serverPort);
				socket = ss.accept();
				ThreadPoolListener.getThreadPoolExecutor().execute(new SocketService(socket));
			}
		} catch (IOException e) {
			ClsFactory.newInstance().error("socket异常:", e);
		}
		finally{
			if (socket!=null) {
				try {
					socket.close();
				} catch (IOException e) {
					ClsFactory.newInstance().error("socket的close异常:", e);
				}
			}
		}
	}
	
}
