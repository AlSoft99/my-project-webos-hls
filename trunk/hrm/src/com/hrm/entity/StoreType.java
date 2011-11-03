package com.hrm.entity;

import java.util.Date;

/**
 * StoreType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreType implements java.io.Serializable {

	// Fields

	private String id;
	private String typename;
	private String typedesc;
	private String storelist;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public StoreType() {
	}

	/** minimal constructor */
	public StoreType(String id) {
		this.id = id;
	}

	/** full constructor */
	public StoreType(String id, String typename, String typedesc,
			String storelist, String updtuser, Date updttime) {
		this.id = id;
		this.typename = typename;
		this.typedesc = typedesc;
		this.storelist = storelist;
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

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTypedesc() {
		return this.typedesc;
	}

	public void setTypedesc(String typedesc) {
		this.typedesc = typedesc;
	}

	public String getStorelist() {
		return this.storelist;
	}

	public void setStorelist(String storelist) {
		this.storelist = storelist;
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