package com.hrm.entity;

import java.util.Date;

/**
 * DayGoodsClearId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DayGoodsClearId implements java.io.Serializable {

	// Fields

	private Date cleardate;
	private String goodsid;
	private String userlist;

	// Constructors

	/** default constructor */
	public DayGoodsClearId() {
	}

	/** full constructor */
	public DayGoodsClearId(Date cleardate, String goodsid) {
		this.cleardate = cleardate;
		this.goodsid = goodsid;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DayGoodsClearId))
			return false;
		DayGoodsClearId castOther = (DayGoodsClearId) other;

		return ((this.getCleardate() == castOther.getCleardate()) || (this
				.getCleardate() != null
				&& castOther.getCleardate() != null && this.getCleardate()
				.equals(castOther.getCleardate())))
				&& ((this.getGoodsid() == castOther.getGoodsid()) || (this
						.getGoodsid() != null
						&& castOther.getGoodsid() != null && this.getGoodsid()
						.equals(castOther.getGoodsid())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCleardate() == null ? 0 : this.getCleardate().hashCode());
		result = 37 * result
				+ (getGoodsid() == null ? 0 : this.getGoodsid().hashCode());
		return result;
	}

	public String getUserlist() {
		return userlist;
	}

	public void setUserlist(String userlist) {
		this.userlist = userlist;
	}


}