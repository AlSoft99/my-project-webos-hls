package com.hrm.entity;

import java.util.Date;

/**
 * StoreInputInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreInputInfo implements java.io.Serializable {

	// Fields

	private String id;
	private Double preprice;
	private Double autprice;
	private String confirmstatus;
	private String applyuser;
	private Date applydate;
	private String manguser;
	private String ismang;
	private String accouser;
	private String isacco;
	private String storeuser;
	private String isstore;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public StoreInputInfo() {
	}

	/** full constructor */
	public StoreInputInfo(Double preprice, Double autprice, String manguser,
			String ismang, String accouser, String isacco, String storeuser,
			String isstore, String updtuser, Date updttime) {
		this.preprice = preprice;
		this.autprice = autprice;
		this.manguser = manguser;
		this.ismang = ismang;
		this.accouser = accouser;
		this.isacco = isacco;
		this.storeuser = storeuser;
		this.isstore = isstore;
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

	public Double getPreprice() {
		return this.preprice;
	}

	public void setPreprice(Double preprice) {
		this.preprice = preprice;
	}

	public Double getAutprice() {
		return this.autprice;
	}

	public void setAutprice(Double autprice) {
		this.autprice = autprice;
	}

	public String getManguser() {
		return this.manguser;
	}

	public void setManguser(String manguser) {
		this.manguser = manguser;
	}

	public String getIsmang() {
		return this.ismang;
	}

	public void setIsmang(String ismang) {
		this.ismang = ismang;
	}

	public String getAccouser() {
		return this.accouser;
	}

	public void setAccouser(String accouser) {
		this.accouser = accouser;
	}

	public String getIsacco() {
		return this.isacco;
	}

	public void setIsacco(String isacco) {
		this.isacco = isacco;
	}

	public String getStoreuser() {
		return this.storeuser;
	}

	public void setStoreuser(String storeuser) {
		this.storeuser = storeuser;
	}

	public String getIsstore() {
		return this.isstore;
	}

	public void setIsstore(String isstore) {
		this.isstore = isstore;
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

	public String getConfirmstatus() {
		return confirmstatus;
	}

	public void setConfirmstatus(String confirmstatus) {
		this.confirmstatus = confirmstatus;
	}

	public String getApplyuser() {
		return applyuser;
	}

	public void setApplyuser(String applyuser) {
		this.applyuser = applyuser;
	}

	public Date getApplydate() {
		return applydate;
	}

	public void setApplydate(Date applydate) {
		this.applydate = applydate;
	}

}