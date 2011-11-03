package com.cn.socket;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolListener {
	private int corePoolSize;
	private int maxConnection;
	private int keepAliveTime;
	private static ThreadPoolExecutor pool = null;
	public void init(){
		pool = new ThreadPoolExecutor(corePoolSize,maxConnection,keepAliveTime,TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.DiscardOldestPolicy());
	}
	
	public static ThreadPoolExecutor getThreadPoolExecutor(){
		return pool;
	}
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getMaxConnection() {
		return maxConnection;
	}
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}
	public int getKeepAliveTime() {
		return keepAliveTime;
	}
	public void setKeepAliveTime(int keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}
	
}
