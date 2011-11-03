package com.hrm.entity;

import java.util.Date;

/**
 * MenuLevelStair entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MenuLevelStair implements java.io.Serializable {

	// Fields

	private String menuId;
	private String menuCode;
	private String roleCode;
	private String menuName;
	private String menuTips;
	private String userName;
	private Date userUpdt;

	// Constructors

	/** default constructor */
	public MenuLevelStair() {
	}

	/** full constructor */
	public MenuLevelStair(String menuCode, String roleCode, String menuName,
			String menuTips, String userName, Date userUpdt) {
		this.menuCode = menuCode;
		this.roleCode = roleCode;
		this.menuName = menuName;
		this.menuTips = menuTips;
		this.userName = userName;
		this.userUpdt = userUpdt;
	}

	// Property accessors

	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuTips() {
		return this.menuTips;
	}

	public void setMenuTips(String menuTips) {
		this.menuTips = menuTips;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getUserUpdt() {
		return this.userUpdt;
	}

	public void setUserUpdt(Date userUpdt) {
		this.userUpdt = userUpdt;
	}

}