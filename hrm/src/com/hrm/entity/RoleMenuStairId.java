package com.hrm.entity;

/**
 * RoleMenuStairId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuStairId implements java.io.Serializable {

	// Fields

	private String roleCode;
	private String menuCode;

	// Constructors

	/** default constructor */
	public RoleMenuStairId() {
	}

	/** full constructor */
	public RoleMenuStairId(String roleCode, String menuCode) {
		this.roleCode = roleCode;
		this.menuCode = menuCode;
	}

	// Property accessors

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getMenuCode() {
		return this.menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoleMenuStairId))
			return false;
		RoleMenuStairId castOther = (RoleMenuStairId) other;

		return ((this.getRoleCode() == castOther.getRoleCode()) || (this
				.getRoleCode() != null
				&& castOther.getRoleCode() != null && this.getRoleCode()
				.equals(castOther.getRoleCode())))
				&& ((this.getMenuCode() == castOther.getMenuCode()) || (this
						.getMenuCode() != null
						&& castOther.getMenuCode() != null && this
						.getMenuCode().equals(castOther.getMenuCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRoleCode() == null ? 0 : this.getRoleCode().hashCode());
		result = 37 * result
				+ (getMenuCode() == null ? 0 : this.getMenuCode().hashCode());
		return result;
	}

}