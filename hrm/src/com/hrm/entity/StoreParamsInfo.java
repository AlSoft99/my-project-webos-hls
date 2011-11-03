package com.hrm.entity;

import java.util.Date;

/**
 * StoreParamsInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreParamsInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String outuser;
	private String stocklevel;
	private Double min;
	private Double max;
	private Date updttime;

	// Constructors

	/** default constructor */
	public StoreParamsInfo() {
	}

	/** full constructor */
	public StoreParamsInfo(String outuser, Double min, Double max, Date updttime) {
		this.outuser = outuser;
		this.min = min;
		this.max = max;
		this.updttime = updttime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOutuser() {
		return this.outuser;
	}

	public void setOutuser(String outuser) {
		this.outuser = outuser;
	}

	public Double getMin() {
		return this.min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return this.max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Date getUpdttime() {
		return this.updttime;
	}

	public void setUpdttime(Date updttime) {
		this.updttime = updttime;
	}

	public String getStocklevel() {
		return stocklevel;
	}

	public void setStocklevel(String stocklevel) {
		this.stocklevel = stocklevel;
	}

}