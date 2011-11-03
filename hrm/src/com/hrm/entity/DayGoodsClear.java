package com.hrm.entity;

import java.util.Date;

/**
 * DayGoodsClear entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DayGoodsClear implements java.io.Serializable {

	// Fields

	private DayGoodsClearId id;
	private Integer storenumber;
	private Integer outnumber;
	private Integer returnnumber;
	private Integer lastnumber;
	private String equals;
	private String goodsdesc;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public DayGoodsClear() {
	}

	/** minimal constructor */
	public DayGoodsClear(DayGoodsClearId id, Date updttime) {
		this.id = id;
		this.updttime = updttime;
	}

	/** full constructor */
	public DayGoodsClear(DayGoodsClearId id, Integer storenumber,
			Integer outnumber, Integer returnnumber, Integer lastnumber, String equals,
			String updtuser, Date updttime) {
		this.id = id;
		this.storenumber = storenumber;
		this.outnumber = outnumber;
		this.returnnumber = returnnumber;
		this.lastnumber = lastnumber;
		this.equals = equals;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public DayGoodsClearId getId() {
		return this.id;
	}

	public void setId(DayGoodsClearId id) {
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

	public String getEquals() {
		return this.equals;
	}

	public void setEquals(String equals) {
		this.equals = equals;
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

	public Integer getReturnnumber() {
		return returnnumber;
	}

	public void setReturnnumber(Integer returnnumber) {
		this.returnnumber = returnnumber;
	}

	public Integer getLastnumber() {
		return lastnumber;
	}

	public void setLastnumber(Integer lastnumber) {
		this.lastnumber = lastnumber;
	}

	public String getGoodsdesc() {
		return goodsdesc;
	}

	public void setGoodsdesc(String goodsdesc) {
		this.goodsdesc = goodsdesc;
	}

}