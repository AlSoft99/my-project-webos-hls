package com.cn.webservice.server;

import com.cn.webservice.server.schma.Person;
import com.cn.webservice.server.schma.Test;
import com.hrm.control.Request;
import com.hrm.util.ClsFactory;
import com.hrm.util.Constant;
import com.hrm.vo.BaseVo;
/**
 * WebService的接口类, 定义自己的方法, 方法带有两个参数, 第一个为javabean, 第二个为交易码
 * @author Guanrl
 *
 */
public class WebServiceManangerImpl {

	public Person sendMsgAgent(Test test, String txCode) throws Exception {
		System.out.println("WebServiceManangerImpl进入");
		return (Person) managerMsg(test,txCode);
	}
	/**
	 * 转发到业务类,类名为[Ws(交易码TxCode)Vo]
	 * @param obj
	 * @param txcode
	 * @return
	 * @throws Exception
	 */
	private Object managerMsg(Object obj,String txcode) throws Exception{
		BaseVo vo = ClsFactory.newInstance().getFactory().getBean(Constant.CLASSNAME_WEBSERVICE[0]+txcode+Constant.CLASSNAME_WEBSERVICE[1], BaseVo.class);
		Request request = new Request();
		request.setEntity(obj);
		request.setAttribute(Constant.TX_CODE, txcode);
		Request response = vo.execute(request);
		return response.getResponse();
	}

}
