package com.hrm.entity;

import java.util.Date;

/**
 * EmployeeList entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmployeeList implements java.io.Serializable {

	// Fields

	private EmployeeListId id;
	private Double othersmart;
	private String smartreason;
	private Double factsalary;
	private String issend;
	private Date updtTime;

	// Constructors

	/** default constructor */
	public EmployeeList() {
	}

	/** minimal constructor */
	public EmployeeList(EmployeeListId id, Date updtTime) {
		this.id = id;
		this.updtTime = updtTime;
	}

	/** full constructor */
	public EmployeeList(EmployeeListId id, Double othersmart,
			String smartreason, Double factsalary, String issend, Date updtTime) {
		this.id = id;
		this.othersmart = othersmart;
		this.smartreason = smartreason;
		this.factsalary = factsalary;
		this.issend = issend;
		this.updtTime = updtTime;
	}

	// Property accessors

	public EmployeeListId getId() {
		return this.id;
	}

	public void setId(EmployeeListId id) {
		this.id = id;
	}

	public Double getOthersmart() {
		return this.othersmart;
	}

	public void setOthersmart(Double othersmart) {
		this.othersmart = othersmart;
	}

	public String getSmartreason() {
		return this.smartreason;
	}

	public void setSmartreason(String smartreason) {
		this.smartreason = smartreason;
	}

	public Double getFactsalary() {
		return this.factsalary;
	}

	public void setFactsalary(Double factsalary) {
		this.factsalary = factsalary;
	}

	public String getIssend() {
		return this.issend;
	}

	public void setIssend(String issend) {
		this.issend = issend;
	}

	public Date getUpdtTime() {
		return this.updtTime;
	}

	public void setUpdtTime(Date updtTime) {
		this.updtTime = updtTime;
	}

}