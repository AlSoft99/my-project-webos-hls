package com.hrm.entity;

import java.util.Date;

/**
 * GoodsStat entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsStat implements java.io.Serializable {

	// Fields

	private GoodsStatId id;
	private Integer storenumber;
	private Integer outnumber;
	private Integer returnnumber;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public GoodsStat() {
	}

	/** minimal constructor */
	public GoodsStat(GoodsStatId id) {
		this.id = id;
	}

	/** full constructor */
	public GoodsStat(GoodsStatId id, Integer storenumber, Integer outnumber,
			Integer returnnumber, String updtuser, Date updttime) {
		this.id = id;
		this.storenumber = storenumber;
		this.outnumber = outnumber;
		this.returnnumber = returnnumber;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public GoodsStatId getId() {
		return this.id;
	}

	public void setId(GoodsStatId id) {
		this.id = id;
	}

	public Integer getStorenumber() {
		return this.storenumber;
	}

	public void setStorenumber(Integer storenumber) {
		this.storenumber = storenumber;
	}

	public Integer getOutnumber() {
		return this.outnumber;
	}

	public void setOutnumber(Integer outnumber) {
		this.outnumber = outnumber;
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

}