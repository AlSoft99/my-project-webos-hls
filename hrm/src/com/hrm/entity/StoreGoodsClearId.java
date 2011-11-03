package com.hrm.entity;

import java.util.Date;

/**
 * StoreGoodsClearId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreGoodsClearId implements java.io.Serializable {

	// Fields

	private Date cleardate;
	private String goodsid;
	private String userlist;

	// Constructors

	/** default constructor */
	public StoreGoodsClearId() {
	}

	/** full constructor */
	public StoreGoodsClearId(Date cleardate, String goodsid, String userlist) {
		this.cleardate = cleardate;
		this.goodsid = goodsid;
		this.userlist = userlist;
	}

	// Property accessors

	public Date getCleardate() {
		return this.cleardate;
	}

	public void setCleardate(Date cleardate) {
		this.cleardate = cleardate;
	}

	public String getGoodsid() {
		return this.goodsid;
	}

	public void setGoodsid(String goodsid) {
		this.goodsid = goodsid;
	}

	public String getUserlist() {
		return this.userlist;
	}

	public void setUserlist(String userlist) {
		this.userlist = userlist;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof StoreGoodsClearId))
			return false;
		StoreGoodsClearId castOther = (StoreGoodsClearId) other;

		return ((this.getCleardate() == castOther.getCleardate()) || (this
				.getCleardate() != null
				&& castOther.getCleardate() != null && this.getCleardate()
				.equals(castOther.getCleardate())))
				&& ((this.getGoodsid() == castOther.getGoodsid()) || (this
						.getGoodsid() != null
						&& castOther.getGoodsid() != null && this.getGoodsid()
						.equals(castOther.getGoodsid())))
				&& ((this.getUserlist() == castOther.getUserlist()) || (this
						.getUserlist() != null
						&& castOther.getUserlist() != null && this
						.getUserlist().equals(castOther.getUserlist())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCleardate() == null ? 0 : this.getCleardate().hashCode());
		result = 37 * result
				+ (getGoodsid() == null ? 0 : this.getGoodsid().hashCode());
		result = 37 * result
				+ (getUserlist() == null ? 0 : this.getUserlist().hashCode());
		return result;
	}

}