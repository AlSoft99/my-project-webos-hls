package com.hrm.entity;

import java.util.Date;

/**
 * StoreOutputInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreOutputInfo implements java.io.Serializable {

	// Fields

	private String id;
	private Date outdate;
	private String status;
	private String outuser;
	private String recuser;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public StoreOutputInfo() {
	}

	/** minimal constructor */
	public StoreOutputInfo(Date updttime) {
		this.updttime = updttime;
	}

	/** full constructor */
	public StoreOutputInfo(Date outdate, String status, String outuser,
			String recuser, String updtuser, Date updttime) {
		this.outdate = outdate;
		this.status = status;
		this.outuser = outuser;
		this.recuser = recuser;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutuser() {
		return this.outuser;
	}

	public void setOutuser(String outuser) {
		this.outuser = outuser;
	}

	public String getRecuser() {
		return this.recuser;
	}

	public void setRecuser(String recuser) {
		this.recuser = recuser;
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