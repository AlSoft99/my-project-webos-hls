package com.test.vo;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WebServiceManangerImplService ws = new WebServiceManangerImplServiceLocator();
		try {
			Test test = new Test();
			test.setA1("guanrl");
			test.setA2("111");
			Person person1 = ws.getWebService().sendMsgAgent(test, "110");
			System.out.println("person1:"+person1.getName());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}
