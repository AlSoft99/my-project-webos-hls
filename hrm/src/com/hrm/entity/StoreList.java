package com.hrm.entity;

import java.util.Date;

/**
 * StoreList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreList implements java.io.Serializable {

	// Fields

	private String id;
	private String typeid;
	private String goodsname;
	private String goodsdesc;
	private Integer initnumber;
	private Integer outnumber;
	private Integer innumber;
	private Double price;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public StoreList() {
	}

	/** full constructor */
	public StoreList(String typeid, String goodsname, String goodsdesc,
			Integer initnumber, Integer outnumber, Integer innumber,
			Double price, String updtuser, Date updttime) {
		this.typeid = typeid;
		this.goodsname = goodsname;
		this.goodsdesc = goodsdesc;
		this.initnumber = initnumber;
		this.outnumber = outnumber;
		this.innumber = innumber;
		this.price = price;
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

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getGoodsname() {
		return this.goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsdesc() {
		return this.goodsdesc;
	}

	public void setGoodsdesc(String goodsdesc) {
		this.goodsdesc = goodsdesc;
	}

	public Integer getInitnumber() {
		return this.initnumber;
	}

	public void setInitnumber(Integer initnumber) {
		this.initnumber = initnumber;
	}

	public Integer getOutnumber() {
		return this.outnumber;
	}

	public void setOutnumber(Integer outnumber) {
		this.outnumber = outnumber;
	}


	public Integer getInnumber() {
		return innumber;
	}

	public void setInnumber(Integer innumber) {
		this.innumber = innumber;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

}