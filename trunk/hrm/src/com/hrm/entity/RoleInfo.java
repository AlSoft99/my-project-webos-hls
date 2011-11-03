package com.hrm.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * RoleInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleInfo implements java.io.Serializable {

	// Fields

	private String roleCode;
	private String roleName;
	private String roleDesc;
	private String userName;
	private Date updtTime;
	private Set userInfos = new HashSet(0);

	// Constructors

	/** default constructor */
	public RoleInfo() {
	}

	/** full constructor */
	public RoleInfo(String roleName, String roleDesc, String userName,
			Date updtTime, Set userInfos) {
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.userName = userName;
		this.updtTime = updtTime;
		this.userInfos = userInfos;
	}

	// Property accessors

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getUpdtTime() {
		return this.updtTime;
	}

	public void setUpdtTime(Date updtTime) {
		this.updtTime = updtTime;
	}

	public Set getUserInfos() {
		return this.userInfos;
	}

	public void setUserInfos(Set userInfos) {
		this.userInfos = userInfos;
	}

}