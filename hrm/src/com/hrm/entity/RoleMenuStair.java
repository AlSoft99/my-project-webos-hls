package com.hrm.entity;

import java.util.Date;

/**
 * RoleMenuStair entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuStair implements java.io.Serializable {

	// Fields

	private RoleMenuStairId id;
	private Date userUpdt;

	// Constructors

	/** default constructor */
	public RoleMenuStair() {
	}

	/** minimal constructor */
	public RoleMenuStair(RoleMenuStairId id) {
		this.id = id;
	}

	/** full constructor */
	public RoleMenuStair(RoleMenuStairId id, Date userUpdt) {
		this.id = id;
		this.userUpdt = userUpdt;
	}

	// Property accessors

	public RoleMenuStairId getId() {
		return this.id;
	}

	public void setId(RoleMenuStairId id) {
		this.id = id;
	}

	public Date getUserUpdt() {
		return this.userUpdt;
	}

	public void setUserUpdt(Date userUpdt) {
		this.userUpdt = userUpdt;
	}

}