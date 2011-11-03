package com.hrm.entity;

import java.util.Date;

/**
 * EmployeeInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmployeeInfo implements java.io.Serializable {

	// Fields

	private String userId;
	private Integer workday;
	private Float salary;
	private Float worksalary;
	private Float welfare;
	private Float tax;
	private Float pubfund;
	private Float safefund;
	private Float curefund;
	private Float otherfund;
	private Float integfund;
	private Date updtTime;

	// Constructors

	/** default constructor */
	public EmployeeInfo() {
	}

	/** minimal constructor */
	public EmployeeInfo(String userId, Date updtTime) {
		this.userId = userId;
		this.updtTime = updtTime;
	}

	/** full constructor */
	public EmployeeInfo(String userId, Integer workday, Float salary,
			Float worksalary, Float welfare, Float tax, Float pubfund,
			Float safefund, Float curefund, Float otherfund, Float integfund,
			Date updtTime) {
		this.userId = userId;
		this.workday = workday;
		this.salary = salary;
		this.worksalary = worksalary;
		this.welfare = welfare;
		this.tax = tax;
		this.pubfund = pubfund;
		this.safefund = safefund;
		this.curefund = curefund;
		this.otherfund = otherfund;
		this.integfund = integfund;
		this.updtTime = updtTime;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getWorkday() {
		return this.workday;
	}

	public void setWorkday(Integer workday) {
		this.workday = workday;
	}

	public Float getSalary() {
		return this.salary;
	}

	public void setSalary(Float salary) {
		this.salary = salary;
	}

	public Float getWorksalary() {
		return this.worksalary;
	}

	public void setWorksalary(Float worksalary) {
		this.worksalary = worksalary;
	}

	public Float getWelfare() {
		return this.welfare;
	}

	public void setWelfare(Float welfare) {
		this.welfare = welfare;
	}

	public Float getTax() {
		return this.tax;
	}

	public void setTax(Float tax) {
		this.tax = tax;
	}

	public Float getPubfund() {
		return this.pubfund;
	}

	public void setPubfund(Float pubfund) {
		this.pubfund = pubfund;
	}

	public Float getSafefund() {
		return this.safefund;
	}

	public void setSafefund(Float safefund) {
		this.safefund = safefund;
	}

	public Float getCurefund() {
		return this.curefund;
	}

	public void setCurefund(Float curefund) {
		this.curefund = curefund;
	}

	public Float getOtherfund() {
		return this.otherfund;
	}

	public void setOtherfund(Float otherfund) {
		this.otherfund = otherfund;
	}

	public Float getIntegfund() {
		return this.integfund;
	}

	public void setIntegfund(Float integfund) {
		this.integfund = integfund;
	}

	public Date getUpdtTime() {
		return this.updtTime;
	}

	public void setUpdtTime(Date updtTime) {
		this.updtTime = updtTime;
	}

}