package com.hrm.entity;

import java.util.Date;

/**
 * UserInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserInfo implements java.io.Serializable {

	// Fields

	private String userId;
	private RoleInfo roleInfo;
	private String userCode;
	private String userName;
	private String userPwd;
	private String userPhone;
	private String userMail;
	private String userQq;
	private String syscd;
	private String usercd;
	private Date syscdtime;
	private Date usercdtime;
	private Date updtTime;

	// Constructors

	/** default constructor */
	public UserInfo() {
	}

	/** full constructor */
	public UserInfo(RoleInfo roleInfo, String userCode, String userName,
			String userPwd, String userPhone, String userMail, String userQq,
			Date updtTime) {
		this.roleInfo = roleInfo;
		this.userCode = userCode;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userPhone = userPhone;
		this.userMail = userMail;
		this.userQq = userQq;
		this.updtTime = updtTime;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public RoleInfo getRoleInfo() {
		return this.roleInfo;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMail() {
		return this.userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserQq() {
		return this.userQq;
	}

	public void setUserQq(String userQq) {
		this.userQq = userQq;
	}

	public Date getUpdtTime() {
		return this.updtTime;
	}

	public void setUpdtTime(Date updtTime) {
		this.updtTime = updtTime;
	}

	public String getSyscd() {
		return syscd;
	}

	public void setSyscd(String syscd) {
		this.syscd = syscd;
	}

	public String getUsercd() {
		return usercd;
	}

	public void setUsercd(String usercd) {
		this.usercd = usercd;
	}

	public Date getSyscdtime() {
		return syscdtime;
	}

	public void setSyscdtime(Date syscdtime) {
		this.syscdtime = syscdtime;
	}

	public Date getUsercdtime() {
		return usercdtime;
	}

	public void setUsercdtime(Date usercdtime) {
		this.usercdtime = usercdtime;
	}

}