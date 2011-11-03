package com.hrm.entity;

/**
 * GoodsListId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsListId implements java.io.Serializable {

	// Fields

	private String id;
	private String userlist;

	// Constructors

	/** default constructor */
	public GoodsListId() {
	}

	/** full constructor */
	public GoodsListId(String id, String userlist) {
		this.id = id;
		this.userlist = userlist;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getUserlist() {
		return userlist;
	}

	public void setUserlist(String userlist) {
		this.userlist = userlist;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof GoodsListId))
			return false;
		GoodsListId castOther = (GoodsListId) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getUserlist() == castOther.getUserlist()) || (this
						.getUserlist() != null
						&& castOther.getUserlist() != null && this.getUserlist()
						.equals(castOther.getUserlist())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getUserlist() == null ? 0 : this.getUserlist().hashCode());
		return result;
	}

}