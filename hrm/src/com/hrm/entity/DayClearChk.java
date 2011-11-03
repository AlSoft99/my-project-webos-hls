package com.hrm.entity;

import java.util.Date;

/**
 * DayClearChk entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DayClearChk implements java.io.Serializable {

	// Fields

	private String id;
	private Date cleardate;
	private String clearequals;
	private String cleardesc;
	private String storeclear;
	private String storedesc;
	private Date updttime;

	// Constructors

	/** default constructor */
	public DayClearChk() {
	}

	/** full constructor */
	public DayClearChk(Date cleardate, String clearequals, String cleardesc,
			String storeclear, String storedesc, Date updttime) {
		this.cleardate = cleardate;
		this.clearequals = clearequals;
		this.cleardesc = cleardesc;
		this.storeclear = storeclear;
		this.storedesc = storedesc;
		this.updttime = updttime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCleardate() {
		return this.cleardate;
	}

	public void setCleardate(Date cleardate) {
		this.cleardate = cleardate;
	}

	public String getClearequals() {
		return this.clearequals;
	}

	public void setClearequals(String clearequals) {
		this.clearequals = clearequals;
	}

	public String getCleardesc() {
		return this.cleardesc;
	}

	public void setCleardesc(String cleardesc) {
		this.cleardesc = cleardesc;
	}

	public String getStoreclear() {
		return this.storeclear;
	}

	public void setStoreclear(String storeclear) {
		this.storeclear = storeclear;
	}

	public String getStoredesc() {
		return this.storedesc;
	}

	public void setStoredesc(String storedesc) {
		this.storedesc = storedesc;
	}

	public Date getUpdttime() {
		return this.updttime;
	}

	public void setUpdttime(Date updttime) {
		this.updttime = updttime;
	}

}