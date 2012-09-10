package com.frame.log;

import org.apache.log4j.Logger;

public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class.getName());
	public static void info(String log){
		logger.info(log);
	}
	public static void debug(String log){
		logger.debug(log);
	}
	public static void error(String log){
		logger.error(log);
	}
	public static void error(String log,Exception e){
		logger.error(log,e);
	}
	public static void fatal(String log){
		logger.fatal(log);
	}
	public static Logger getLogger(){
		return logger;
	}
}
