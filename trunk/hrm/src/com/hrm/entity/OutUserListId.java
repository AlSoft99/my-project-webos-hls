package com.hrm.entity;

/**
 * OutUserListId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OutUserListId implements java.io.Serializable {

	// Fields

	private String userlist;
	private String outuser;
	private String tag;

	// Constructors

	/** default constructor */
	public OutUserListId() {
	}

	/** full constructor */
	public OutUserListId(String userlist, String outuser, String tag) {
		this.userlist = userlist;
		this.outuser = outuser;
		this.tag = tag;
	}

	// Property accessors

	public String getUserlist() {
		return this.userlist;
	}

	public void setUserlist(String userlist) {
		this.userlist = userlist;
	}

	public String getOutuser() {
		return this.outuser;
	}

	public void setOutuser(String outuser) {
		this.outuser = outuser;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OutUserListId))
			return false;
		OutUserListId castOther = (OutUserListId) other;

		return ((this.getUserlist() == castOther.getUserlist()) || (this
				.getUserlist() != null
				&& castOther.getUserlist() != null && this.getUserlist()
				.equals(castOther.getUserlist())))
				&& ((this.getOutuser() == castOther.getOutuser()) || (this
						.getOutuser() != null
						&& castOther.getOutuser() != null && this.getOutuser()
						.equals(castOther.getOutuser())))
				&& ((this.getTag() == castOther.getTag()) || (this.getTag() != null
						&& castOther.getTag() != null && this.getTag().equals(
						castOther.getTag())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserlist() == null ? 0 : this.getUserlist().hashCode());
		result = 37 * result
				+ (getOutuser() == null ? 0 : this.getOutuser().hashCode());
		result = 37 * result
				+ (getTag() == null ? 0 : this.getTag().hashCode());
		return result;
	}

}