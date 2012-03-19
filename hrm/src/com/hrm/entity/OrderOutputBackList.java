package com.hrm.entity;

import java.util.Date;

/**
 * GoodsOutputList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderOutputBackList implements java.io.Serializable {

	// Fields

	private String id;
	private String outid;
	private String goodsid;
	//操作类型(手动, 自动)
	private String optiontype;
	//消耗类型(正常消耗, 退菜消耗)
	private String consumetype;
	//识别是否成功(1 是, 0否)
	private String checkyn;
	private Float goodsnumber;
	private Float returnnumber;
	//应付金额
	private Float shouldpay;
	//实付金额
	private Float actuallypay;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public OrderOutputBackList() {
	}

	/** minimal constructor */
	public OrderOutputBackList(String id) {
		this.id = id;
	}

	/** full constructor */
	public OrderOutputBackList(String id, String outid, String goodsid,
			Float goodsnumber, Float returnnumber, String updtuser,
			Date updttime) {
		this.id = id;
		this.outid = outid;
		this.goodsid = goodsid;
		this.goodsnumber = goodsnumber;
		this.returnnumber = returnnumber;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutid() {
		return this.outid;
	}

	public void setOutid(String outid) {
		this.outid = outid;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public Float getGoodsnumber() {
		return this.goodsnumber;
	}

	public void setGoodsnumber(Float goodsnumber) {
		this.goodsnumber = goodsnumber;
	}

	public Float getReturnnumber() {
		return this.returnnumber;
	}

	public void setReturnnumber(Float returnnumber) {
		this.returnnumber = returnnumber;
	}

	public String getUpdtuser() {
		return this.updtuser;
	}

	public void setUpdtuser(String updtuser) {
		this.updtuser = updtuser;
	}

	public Date getUpdttime() {
		return this.updttime;
	}

	public void setUpdttime(Date updttime) {
		this.updttime = updttime;
	}


	public String getConsumetype() {
		return consumetype;
	}

	public void setConsumetype(String consumetype) {
		this.consumetype = consumetype;
	}

	public String getOptiontype() {
		return optiontype;
	}

	public void setOptiontype(String optiontype) {
		this.optiontype = optiontype;
	}

	public String getCheckyn() {
		return checkyn;
	}

	public void setCheckyn(String checkyn) {
		this.checkyn = checkyn;
	}

	public Float getShouldpay() {
		return shouldpay;
	}

	public void setShouldpay(Float shouldpay) {
		this.shouldpay = shouldpay;
	}

	public Float getActuallypay() {
		return actuallypay;
	}

	public void setActuallypay(Float actuallypay) {
		this.actuallypay = actuallypay;
	}

}