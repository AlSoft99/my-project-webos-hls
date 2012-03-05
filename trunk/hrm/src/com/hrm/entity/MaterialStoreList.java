package com.hrm.entity;

import java.util.Date;

/**
 * ParamsList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MaterialStoreList implements java.io.Serializable {

	// Fields

	private MaterialStoreListId id;
	private String typeid;
	//该月初始数量
	private Float initsum;
	//单价
	private Float cost;
	private String updtuser;
	private Date updttime;

	// Property accessors

	public String getTypeid() {
		return this.typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
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

	public MaterialStoreListId getId() {
		return id;
	}

	public void setId(MaterialStoreListId id) {
		this.id = id;
	}

	public Float getInitsum() {
		return initsum;
	}

	public void setInitsum(Float initsum) {
		this.initsum = initsum;
	}

	public Float getCost() {
		return cost;
	}

	public void setCost(Float cost) {
		this.cost = cost;
	}


}