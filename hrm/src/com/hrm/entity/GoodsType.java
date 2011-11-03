package com.hrm.entity;

import java.util.Date;

/**
 * GoodsType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class GoodsType implements java.io.Serializable {

	// Fields

	private String id;
	private String typename;
	private String typedesc;
	private String outuser;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public GoodsType() {
	}

	/** minimal constructor */
	public GoodsType(Date updttime) {
		this.updttime = updttime;
	}

	/** full constructor */
	public GoodsType(String typename, String typedesc, String updtuser,
			Date updttime) {
		this.typename = typename;
		this.typedesc = typedesc;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public String getTypedesc() {
		return this.typedesc;
	}

	public void setTypedesc(String typedesc) {
		this.typedesc = typedesc;
	}

	public String getUpdtuser() {
		return this.updtuser;
	}

	public void setUpdtuser(String updtuser) {
		this.updtuser = updtuser;
	}

	public Date getUpdttime() {
		return this.updttime;
	}

	public void setUpdttime(Date updttime) {
		this.updttime = updttime;
	}

	public String getOutuser() {
		return outuser;
	}

	public void setOutuser(String outuser) {
		this.outuser = outuser;
	}

}