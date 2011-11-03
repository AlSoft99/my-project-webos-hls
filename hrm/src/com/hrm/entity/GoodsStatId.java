package com.hrm.entity;

/**
 * GoodsStatId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsStatId implements java.io.Serializable {

	// Fields

	private String goodsid;
	private String userlist;

	// Constructors

	/** default constructor */
	public GoodsStatId() {
	}

	/** full constructor */
	public GoodsStatId(String goodsid, String userlist) {
		this.goodsid = goodsid;
		this.userlist = userlist;
	}

	// Property accessors

	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
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
		if (!(other instanceof GoodsStatId))
			return false;
		GoodsStatId castOther = (GoodsStatId) other;

		return ((this.getGoodsid() == castOther.getGoodsid()) || (this
				.getGoodsid() != null
				&& castOther.getGoodsid() != null && this.getGoodsid().equals(
				castOther.getGoodsid())))
				&& ((this.getUserlist() == castOther.getUserlist()) || (this
						.getUserlist() != null
						&& castOther.getUserlist() != null && this.getUserlist()
						.equals(castOther.getUserlist())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getGoodsid() == null ? 0 : this.getGoodsid().hashCode());
		result = 37 * result
				+ (getUserlist() == null ? 0 : this.getUserlist().hashCode());
		return result;
	}

}