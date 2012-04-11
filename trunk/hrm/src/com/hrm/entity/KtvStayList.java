package com.hrm.entity;

import java.util.Date;

public class KtvStayList {
	private String id;
	private String ktvid;
	//酒水ID
	private String materialid;
	//酒数量
	private Integer count;
	//更新人
	private String updtuser;
	//更新时间
	private Date updttime;
	public String getId() {
		return id;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKtvid() {
		return ktvid;
	}
	public void setKtvid(String ktvid) {
		this.ktvid = ktvid;
	}
	public String getMaterialid() {
		return materialid;
	}
	public void setMaterialid(String materialid) {
		this.materialid = materialid;
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
