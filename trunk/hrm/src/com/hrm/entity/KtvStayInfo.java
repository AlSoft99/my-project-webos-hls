package com.hrm.entity;

import java.util.Date;

public class KtvStayInfo {
	private String id;
	//卡ID
	private String cardid;
	//密码
	private String password;
	//卡用户名
	private String username;
	//卡手机号
	private String moblie;
	//附言
	private String desc;
	//身份证
	private String idcard;
	//酒水ID
	private String materialid;
	//状态  1=保存酒水, 2=取出酒水, 3=过期, 0=删除
	private String state;
	//更新人
	private String updtuser;
	//更新时间
	private Date updttime;
	public String getMaterialid() {
		return materialid;
	}
	public void setMaterialid(String materialid) {
		this.materialid = materialid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
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
