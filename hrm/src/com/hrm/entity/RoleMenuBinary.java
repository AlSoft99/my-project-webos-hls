package com.hrm.entity;

import java.util.Date;

/**
 * RoleMenuBinary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuBinary implements java.io.Serializable {

	// Fields

	private RoleMenuBinaryId id;
	private Date userUpdt;

	// Constructors

	/** default constructor */
	public RoleMenuBinary() {
	}

	/** minimal constructor */
	public RoleMenuBinary(RoleMenuBinaryId id) {
		this.id = id;
	}

	/** full constructor */
	public RoleMenuBinary(RoleMenuBinaryId id, Date userUpdt) {
		this.id = id;
		this.userUpdt = userUpdt;
	}

	// Property accessors

	public RoleMenuBinaryId getId() {
		return this.id;
	}

	public void setId(RoleMenuBinaryId id) {
		this.id = id;
	}

	public Date getUserUpdt() {
		return this.userUpdt;
	}

	public void setUserUpdt(Date userUpdt) {
		this.userUpdt = userUpdt;
	}

}