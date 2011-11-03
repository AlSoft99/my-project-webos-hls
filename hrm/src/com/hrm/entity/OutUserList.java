package com.hrm.entity;

import java.util.Date;

/**
 * OutUserList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OutUserList implements java.io.Serializable {

	// Fields

	private OutUserListId id;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public OutUserList() {
	}

	/** minimal constructor */
	public OutUserList(OutUserListId id) {
		this.id = id;
	}

	/** full constructor */
	public OutUserList(OutUserListId id, String updtuser, Date updttime) {
		this.id = id;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public OutUserListId getId() {
		return this.id;
	}

	public void setId(OutUserListId id) {
		this.id = id;
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