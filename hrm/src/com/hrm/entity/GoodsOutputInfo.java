package com.hrm.entity;

import java.util.Date;

/**
 * GoodsOutputInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsOutputInfo implements java.io.Serializable {

	// Fields

	private String id;
	private Date outdate;
	private String outuser;
	private String goodsdesc;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public GoodsOutputInfo() {
	}

	/** minimal constructor */
	public GoodsOutputInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public GoodsOutputInfo(String id, Date outdate, String outuser,
			String goodsdesc, String updtuser, Date updttime) {
		this.id = id;
		this.outdate = outdate;
		this.outuser = outuser;
		this.goodsdesc = goodsdesc;
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

	public Date getOutdate() {
		return this.outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	public String getOutuser() {
		return this.outuser;
	}

	public void setOutuser(String outuser) {
		this.outuser = outuser;
	}

	public String getGoodsdesc() {
		return this.goodsdesc;
	}

	public void setGoodsdesc(String goodsdesc) {
		this.goodsdesc = goodsdesc;
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