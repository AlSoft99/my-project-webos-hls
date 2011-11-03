package com.hrm.entity;

import java.util.Date;

/**
 * WelfareParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class WelfareParam implements java.io.Serializable {

	// Fields

	private String id;
	private Integer workday;
	private Double welfare;
	private Double pubfund;
	private Double safefund;
	private Double curefund;
	private Double integfund;
	private Double tax;
	private Date updtTime;

	// Constructors

	/** default constructor */
	public WelfareParam() {
	}

	/** full constructor */
	public WelfareParam(Double pubfund, Double safefund, Double curefund,
			Double integfund, Double tax, Date updtTime) {
		this.pubfund = pubfund;
		this.safefund = safefund;
		this.curefund = curefund;
		this.integfund = integfund;
		this.tax = tax;
		this.updtTime = updtTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPubfund() {
		return this.pubfund;
	}

	public void setPubfund(Double pubfund) {
		this.pubfund = pubfund;
	}

	public Double getSafefund() {
		return this.safefund;
	}

	public void setSafefund(Double safefund) {
		this.safefund = safefund;
	}

	public Double getCurefund() {
		return this.curefund;
	}

	public void setCurefund(Double curefund) {
		this.curefund = curefund;
	}

	public Double getIntegfund() {
		return this.integfund;
	}

	public void setIntegfund(Double integfund) {
		this.integfund = integfund;
	}

	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Date getUpdtTime() {
		return this.updtTime;
	}

	public void setUpdtTime(Date updtTime) {
		this.updtTime = updtTime;
	}

	public Double getWelfare() {
		return welfare;
	}

	public void setWelfare(Double welfare) {
		this.welfare = welfare;
	}

	public Integer getWorkday() {
		return workday;
	}

	public void setWorkday(Integer workday) {
		this.workday = workday;
	}

}