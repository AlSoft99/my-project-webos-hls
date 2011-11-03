package com.hrm.entity;

import java.util.Date;

/**
 * MenuLevelBinary entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MenuLevelBinary implements java.io.Serializable {

	// Fields

	private String menuTreeCode;
	private String menuCode;
	private String roleCode;
	private String menuTreeName;
	private String menuTreeTips;
	private String menuPage;
	private String userName;
	private Date userUpdt;

	// Constructors

	/** default constructor */
	public MenuLevelBinary() {
	}

	/** full constructor */
	public MenuLevelBinary(String menuCode, String roleCode,
			String menuTreeName, String menuTreeTips, String menuPage,
			String userName, Date userUpdt) {
		this.menuCode = menuCode;
		this.roleCode = roleCode;
		this.menuTreeName = menuTreeName;
		this.menuTreeTips = menuTreeTips;
		this.menuPage = menuPage;
		this.userName = userName;
		this.userUpdt = userUpdt;
	}

	// Property accessors

	public String getMenuTreeCode() {
		return this.menuTreeCode;
	}

	public void setMenuTreeCode(String menuTreeCode) {
		this.menuTreeCode = menuTreeCode;
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

	public String getMenuTreeName() {
		return this.menuTreeName;
	}

	public void setMenuTreeName(String menuTreeName) {
		this.menuTreeName = menuTreeName;
	}

	public String getMenuTreeTips() {
		return this.menuTreeTips;
	}

	public void setMenuTreeTips(String menuTreeTips) {
		this.menuTreeTips = menuTreeTips;
	}

	public String getMenuPage() {
		return this.menuPage;
	}

	public void setMenuPage(String menuPage) {
		this.menuPage = menuPage;
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