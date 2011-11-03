package com.hrm.entity;

import java.util.Date;

/**
 * GoodsMonthStat entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsMonthStat implements java.io.Serializable {

	// Fields

	private GoodsMonthStatId id;
	private Integer startnumber;
	private Integer innumber;
	private Integer outnumber;
	private Integer returnnumber;
	private Integer lastnumber;
	private Date startdate;
	private Date enddate;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public GoodsMonthStat() {
	}

	/** minimal constructor */
	public GoodsMonthStat(GoodsMonthStatId id, Date startdate, Date enddate) {
		this.id = id;
		this.startdate = startdate;
		this.enddate = enddate;
	}

	/** full constructor */
	public GoodsMonthStat(GoodsMonthStatId id, Integer startnumber,
			Integer outnumber, Integer returnnumber, Integer lastnumber,
			Date startdate, Date enddate, String updtuser, Date updttime) {
		this.id = id;
		this.startnumber = startnumber;
		this.outnumber = outnumber;
		this.returnnumber = returnnumber;
		this.lastnumber = lastnumber;
		this.startdate = startdate;
		this.enddate = enddate;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public GoodsMonthStatId getId() {
		return this.id;
	}

	public void setId(GoodsMonthStatId id) {
		this.id = id;
	}

	public Integer getStartnumber() {
		return this.startnumber;
	}

	public void setStartnumber(Integer startnumber) {
		this.startnumber = startnumber;
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

	public Integer getLastnumber() {
		return this.lastnumber;
	}

	public void setLastnumber(Integer lastnumber) {
		this.lastnumber = lastnumber;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
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

	public Integer getInnumber() {
		return innumber;
	}

	public void setInnumber(Integer innumber) {
		this.innumber = innumber;
	}

}