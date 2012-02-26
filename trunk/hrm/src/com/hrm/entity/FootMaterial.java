package com.hrm.entity;

import java.io.Serializable;
import java.util.Date;

public class FootMaterial implements Serializable {   
	private static final long serialVersionUID = 1L;
	//唯一ID
    private String id;
    //菜名ID
    private String footid;
    //原料ID
    private String materialid;
	//数量
    private Float amount;
	//单位(克, 斤, 两, 个)
	private String unit;
	private String issecond;
	private String secondcode;
	//更新人
	private String updtuser;
	//更新时间
	private Date updttime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFootid() {
		return footid;
	}
	public void setFootid(String footid) {
		this.footid = footid;
	}
	public String getMaterialid() {
		return materialid;
	}
	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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
	public String getIssecond() {
		return issecond;
	}
	public void setIssecond(String issecond) {
		this.issecond = issecond;
	}
	public String getSecondcode() {
		return secondcode;
	}
	public void setSecondcode(String secondcode) {
		this.secondcode = secondcode;
	}

	
}