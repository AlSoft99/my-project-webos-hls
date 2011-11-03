package com.cn.voa;

public class Hia {
	//输入字符长度(L8)
	private int len;
	//交易码(L6)
	private String txCode;
	//报文头长度(L100)
	private String header;
	public String getTxCode() {
		return txCode;
	}
	public void setTxCode(String txCode) {
		this.txCode = txCode;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
}
