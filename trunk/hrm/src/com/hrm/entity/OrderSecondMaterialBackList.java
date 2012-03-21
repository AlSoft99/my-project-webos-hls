package com.hrm.entity;

import java.util.Date;

/**
 * GoodsOutputList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class OrderSecondMaterialBackList implements java.io.Serializable {

	// Fields

	private OrderSecondMaterialId id;
	private Float sum;
	private String updtuser;
	private Date updttime;
	
	public OrderSecondMaterialId getId() {
		return id;
	}
	public void setId(OrderSecondMaterialId id) {
		this.id = id;
	}
	public Float getSum() {
		return sum;
	}
	public void setSum(Float sum) {
		this.sum = sum;
	}
	public String getUpdtuser() {
		return updtuser;
	}
	public void setUpdtuser(String updtuser) {
		this.updtuser = updtuser;
	}
	public Date getUpdttime() {
		return updttime;
	}
	public void setUpdttime(Date updttime) {
		this.updttime = updttime;
	}

	
}