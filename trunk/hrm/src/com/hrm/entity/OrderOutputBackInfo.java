package com.hrm.entity;

import java.util.Date;

/**
 * GoodsOutputInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderOutputBackInfo implements java.io.Serializable {

	// Fields

	private String id;
	private Date outdate;
	private String outuser;
	private String orderdesc;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public OrderOutputBackInfo() {
	}

	/** minimal constructor */
	public OrderOutputBackInfo(String id) {
		this.id = id;
	}

	/** full constructor */
	public OrderOutputBackInfo(String id, Date outdate, String outuser,
			String goodsdesc, String updtuser, Date updttime) {
		this.id = id;
		this.outdate = outdate;
		this.outuser = outuser;
		this.orderdesc = goodsdesc;
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

	

	public String getOrderdesc() {
		return orderdesc;
	}

	public void setOrderdesc(String orderdesc) {
		this.orderdesc = orderdesc;
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