package com.hrm.entity;

import java.util.Date;

/**
 * GoodsList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsList implements java.io.Serializable {

	// Fields

	private GoodsListId id;
	private String goodsname;
	private String goodsdesc;
	private Integer goodsnumber;
	private Integer outnumber;
	private Integer innumber;
	private Double price;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public GoodsList() {
	}

	/** minimal constructor */
	public GoodsList(GoodsListId id) {
		this.id = id;
	}

	/** full constructor */
	public GoodsList(GoodsListId id, String goodsname, String goodsdesc,
			Integer goodsnumber, Double price, String updtuser, Date updttime) {
		this.id = id;
		this.goodsname = goodsname;
		this.goodsdesc = goodsdesc;
		this.goodsnumber = goodsnumber;
		this.price = price;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public GoodsListId getId() {
		return this.id;
	}

	public void setId(GoodsListId id) {
		this.id = id;
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

	public Integer getGoodsnumber() {
		return this.goodsnumber;
	}

	public void setGoodsnumber(Integer goodsnumber) {
		this.goodsnumber = goodsnumber;
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

	public Integer getOutnumber() {
		return outnumber;
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

}