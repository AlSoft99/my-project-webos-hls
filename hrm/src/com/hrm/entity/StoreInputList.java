package com.hrm.entity;

import java.util.Date;

/**
 * StoreInputList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreInputList implements java.io.Serializable {

	// Fields

	private String id;
	private String inid;
	private String goodsid;
	private Integer goodsnumber;
	private Double goodsprice;
	private String goodsdesc;
	private Date updttime;
	private String updtuser;

	// Constructors

	/** default constructor */
	public StoreInputList() {
	}

	/** full constructor */
	public StoreInputList(String inid, String goodsid, Integer goodsnumber,
			Double goodsprice, String goodsdesc, Date updttime, String updtuser) {
		this.inid = inid;
		this.goodsid = goodsid;
		this.goodsnumber = goodsnumber;
		this.goodsprice = goodsprice;
		this.goodsdesc = goodsdesc;
		this.updttime = updttime;
		this.updtuser = updtuser;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInid() {
		return this.inid;
	}

	public void setInid(String inid) {
		this.inid = inid;
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

	public Double getGoodsprice() {
		return this.goodsprice;
	}

	public void setGoodsprice(Double goodsprice) {
		this.goodsprice = goodsprice;
	}

	public String getGoodsdesc() {
		return this.goodsdesc;
	}

	public void setGoodsdesc(String goodsdesc) {
		this.goodsdesc = goodsdesc;
	}

	public Date getUpdttime() {
		return this.updttime;
	}

	public void setUpdttime(Date updttime) {
		this.updttime = updttime;
	}

	public String getUpdtuser() {
		return this.updtuser;
	}

	public void setUpdtuser(String updtuser) {
		this.updtuser = updtuser;
	}

}