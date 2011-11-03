package com.hrm.util;

public class Constant {
	public final static String USER_INFO = "userInfo";
	public final static String ROLE_INFO = "roleInfo";
	public final static String GOODS_TYPE = "goodsType";
	public final static String GOODS_LIST = "goodsList";
	public final static String STORE_TYPE = "storeType";
	public final static String STORE_LIST = "storeList";
	public final static String[] PARAMS_INFO = {"STORE_STATUS","CONFIRM_STATUS"};
	//OUT_USER_LIST标示. 1=出仓人2=仓库人
	public final static String[] TAG = {"1","2"};
	//socket接受报文头的长度
	public final static int MSG_LEN = 8;
	public final static String ATTRIBUTE_INPUT_SOCKET = "input_socket_body";
	public final static String ATTRIBUTE_OUTPUT_SOCKET = "output_socket_body";
	public final static String HEADER_OUTPUT_SOCKET = "output_socket_header";
	public final static String BODY_OUTPUT_SOCKET = "output_socket_body";
	public final static String CLIENT_OUTPUT_SOCKET = "client_output_socket";
	public final static String CLIENT_INPUT_SOCKET = "client_input_socket";
	public final static String HIA_SOCKET = "hia_socket";
	public final static String HOA_SOCKET = "hoa_socket";
	//socket发送标示
	public final static String ATTRIBUTE_SOCKET = "socket";
	//交易码
	public final static String TX_CODE = "tx_code";
	//生成类规则
	public final static String[] CLASSNAME_SOCKET = {"St_","_Vo"};
	public final static String[] CLASSNAME_SOCKET_INPUT = {"SInput_","_Vo"};
	public final static String[] CLASSNAME_SOCKET_OUTPUT = {"SOutput_","_Vo"};
	public final static String[] CLASSNAME_WEBSERVICE = {"Ws","Vo"};
}
