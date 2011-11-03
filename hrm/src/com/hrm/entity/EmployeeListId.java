package com.hrm.entity;

import java.util.Date;

/**
 * EmployeeListId entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class EmployeeListId implements java.io.Serializable {

	// Fields

	private String userId;
	private Date salarydate;

	// Constructors

	/** default constructor */
	public EmployeeListId() {
	}

	/** full constructor */
	public EmployeeListId(String userId, Date salarydate) {
		this.userId = userId;
		this.salarydate = salarydate;
	}

	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getSalarydate() {
		return this.salarydate;
	}

	public void setSalarydate(Date salarydate) {
		this.salarydate = salarydate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof EmployeeListId))
			return false;
		EmployeeListId castOther = (EmployeeListId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null
				&& castOther.getUserId() != null && this.getUserId().equals(
				castOther.getUserId())))
				&& ((this.getSalarydate() == castOther.getSalarydate()) || (this
						.getSalarydate() != null
						&& castOther.getSalarydate() != null && this
						.getSalarydate().equals(castOther.getSalarydate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37
				* result
				+ (getSalarydate() == null ? 0 : this.getSalarydate()
						.hashCode());
		return result;
	}

}