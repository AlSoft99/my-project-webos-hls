package com.hrm.entity;

/**
 * RoleMenuBinaryId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleMenuBinaryId implements java.io.Serializable {

	// Fields

	private String roleCode;
	private String menuCode;
	private String menuTreeCode;

	// Constructors

	/** default constructor */
	public RoleMenuBinaryId() {
	}

	/** full constructor */
	public RoleMenuBinaryId(String roleCode, String menuCode,
			String menuTreeCode) {
		this.roleCode = roleCode;
		this.menuCode = menuCode;
		this.menuTreeCode = menuTreeCode;
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

	public String getMenuTreeCode() {
		return this.menuTreeCode;
	}

	public void setMenuTreeCode(String menuTreeCode) {
		this.menuTreeCode = menuTreeCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RoleMenuBinaryId))
			return false;
		RoleMenuBinaryId castOther = (RoleMenuBinaryId) other;

		return ((this.getRoleCode() == castOther.getRoleCode()) || (this
				.getRoleCode() != null
				&& castOther.getRoleCode() != null && this.getRoleCode()
				.equals(castOther.getRoleCode())))
				&& ((this.getMenuCode() == castOther.getMenuCode()) || (this
						.getMenuCode() != null
						&& castOther.getMenuCode() != null && this
						.getMenuCode().equals(castOther.getMenuCode())))
				&& ((this.getMenuTreeCode() == castOther.getMenuTreeCode()) || (this
						.getMenuTreeCode() != null
						&& castOther.getMenuTreeCode() != null && this
						.getMenuTreeCode().equals(castOther.getMenuTreeCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getRoleCode() == null ? 0 : this.getRoleCode().hashCode());
		result = 37 * result
				+ (getMenuCode() == null ? 0 : this.getMenuCode().hashCode());
		result = 37
				* result
				+ (getMenuTreeCode() == null ? 0 : this.getMenuTreeCode()
						.hashCode());
		return result;
	}

}