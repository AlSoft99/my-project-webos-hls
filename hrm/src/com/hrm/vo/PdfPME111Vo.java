package com.hrm.vo;

import com.cn.cmm.ClientAgent;
import com.hrm.cmm.server.SOutput_PME111_Vo;
import com.hrm.control.Request;
import com.hrm.util.Constant;

public class PdfPME111Vo implements BaseVo {

	private ClientAgent clientAgent;
	public ClientAgent getClientAgent() {
		return clientAgent;
	}
	public void setClientAgent(ClientAgent clientAgent) {
		this.clientAgent = clientAgent;
	}
	public Request execute(Request request) throws Exception {
		System.out.println("进入PdfPME111Vo业务类");
		SOutput_PME111_Vo vo = new SOutput_PME111_Vo();
		vo.setA1("我是");
		vo.setA2("是神");
		vo.setA3("神");
		vo.setA4("仙");
		vo.setA5("吗");
		request.setAttribute(Constant.BODY_OUTPUT_SOCKET, vo);
		request.setAttribute(Constant.CLIENT_OUTPUT_SOCKET, vo);
//		clientAgent.sendMsgAgent(Constant.ATTRIBUTE_SOCKET, request);
		System.out.println("11111:="+request.getAttribute(Constant.CLIENT_INPUT_SOCKET));;
		
		return request;
	}

}
