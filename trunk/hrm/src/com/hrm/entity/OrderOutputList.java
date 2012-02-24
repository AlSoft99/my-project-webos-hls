package com.hrm.entity;

import java.util.Date;

/**
 * GoodsOutputList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderOutputList implements java.io.Serializable {

	// Fields

	private String id;
	private String outid;
	private String goodsid;
	//操作类型(手动, 自动)
	private String optiontype;
	//消耗类型(正常消耗, 退菜消耗)
	private String consumetype;
	private Integer goodsnumber;
	private Integer returnnumber;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public OrderOutputList() {
	}

	/** minimal constructor */
	public OrderOutputList(String id) {
		this.id = id;
	}

	/** full constructor */
	public OrderOutputList(String id, String outid, String goodsid,
			Integer goodsnumber, Integer returnnumber, String updtuser,
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

	public Integer getGoodsnumber() {
		return this.goodsnumber;
	}

	public void setGoodsnumber(Integer goodsnumber) {
		this.goodsnumber = goodsnumber;
	}

	public Integer getReturnnumber() {
		return this.returnnumber;
	}

	public void setReturnnumber(Integer returnnumber) {
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

}