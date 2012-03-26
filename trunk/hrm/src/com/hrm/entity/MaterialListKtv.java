package com.hrm.entity;

import java.util.Date;

/**
 * ParamsList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MaterialListKtv implements java.io.Serializable {

	// Fields

	private String id;
	private String typeid;
	private String paramscode;
	private String paramsname;
	private String paramsdesc;
	private Float cost;
	private String unit;
	private String updtuser;
	private Date updttime;

	// Property accessors

	public String getId() {
		return this.id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getParamscode() {
		return this.paramscode;
	}

	public void setParamscode(String paramscode) {
		this.paramscode = paramscode;
	}

	public String getParamsname() {
		return this.paramsname;
	}

	public void setParamsname(String paramsname) {
		this.paramsname = paramsname;
	}

	public String getParamsdesc() {
		return this.paramsdesc;
	}

	public void setParamsdesc(String paramsdesc) {
		this.paramsdesc = paramsdesc;
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

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}

}