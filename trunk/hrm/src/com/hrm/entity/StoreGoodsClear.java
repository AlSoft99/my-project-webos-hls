package com.hrm.entity;

import java.util.Date;

/**
 * StoreGoodsClear entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class StoreGoodsClear implements java.io.Serializable {

	// Fields

	private StoreGoodsClearId id;
	private Integer sstorenumber;
	private Integer soutnumber;
	private Integer sreturnnumber;
	private Integer slastnumber;
	private Integer gstorenumber;
	private Integer goutnumber;
	private Integer greturnnumber;
	private Integer glastnumber;
	private String equals;
	private String updtuser;
	private Date updttime;

	// Constructors

	/** default constructor */
	public StoreGoodsClear() {
	}

	/** minimal constructor */
	public StoreGoodsClear(StoreGoodsClearId id) {
		this.id = id;
	}

	/** full constructor */
	public StoreGoodsClear(StoreGoodsClearId id, Integer sstorenumber,
			Integer soutnumber, Integer sreturnnumber, Integer slastnumber,
			Integer gstorenumber, Integer goutnumber, Integer greturnnumber,
			Integer glastnumber, String equals, String updtuser, Date updttime) {
		this.id = id;
		this.sstorenumber = sstorenumber;
		this.soutnumber = soutnumber;
		this.sreturnnumber = sreturnnumber;
		this.slastnumber = slastnumber;
		this.gstorenumber = gstorenumber;
		this.goutnumber = goutnumber;
		this.greturnnumber = greturnnumber;
		this.glastnumber = glastnumber;
		this.equals = equals;
		this.updtuser = updtuser;
		this.updttime = updttime;
	}

	// Property accessors

	public StoreGoodsClearId getId() {
		return this.id;
	}

	public void setId(StoreGoodsClearId id) {
		this.id = id;
	}

	public Integer getSstorenumber() {
		return this.sstorenumber;
	}

	public void setSstorenumber(Integer sstorenumber) {
		this.sstorenumber = sstorenumber;
	}

	public Integer getSoutnumber() {
		return this.soutnumber;
	}

	public void setSoutnumber(Integer soutnumber) {
		this.soutnumber = soutnumber;
	}

	public Integer getSreturnnumber() {
		return this.sreturnnumber;
	}

	public void setSreturnnumber(Integer sreturnnumber) {
		this.sreturnnumber = sreturnnumber;
	}

	public Integer getSlastnumber() {
		return this.slastnumber;
	}

	public void setSlastnumber(Integer slastnumber) {
		this.slastnumber = slastnumber;
	}

	public Integer getGstorenumber() {
		return this.gstorenumber;
	}

	public void setGstorenumber(Integer gstorenumber) {
		this.gstorenumber = gstorenumber;
	}

	public Integer getGoutnumber() {
		return this.goutnumber;
	}

	public void setGoutnumber(Integer goutnumber) {
		this.goutnumber = goutnumber;
	}

	public Integer getGreturnnumber() {
		return this.greturnnumber;
	}

	public void setGreturnnumber(Integer greturnnumber) {
		this.greturnnumber = greturnnumber;
	}

	public Integer getGlastnumber() {
		return this.glastnumber;
	}

	public void setGlastnumber(Integer glastnumber) {
		this.glastnumber = glastnumber;
	}

	public String getEquals() {
		return this.equals;
	}

	public void setEquals(String equals) {
		this.equals = equals;
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

}