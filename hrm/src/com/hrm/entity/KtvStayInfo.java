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
	private String otherdesc;
	//身份证
	private String idcard;
	//状态  1=保存酒水, 2=取出酒水, 3=过期, 0=删除, 4=过期取货, 5=过期收卡
	private String state;
	//保存天数
	private int day;
	//过期日期
	private Date overtime;
	//押金
	private Float cash;
	//更新人
	private String updtuser;
	//更新时间
	private Date updttime;
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public Date getOvertime() {
		return overtime;
	}
	public void setOvertime(Date overtime) {
		this.overtime = overtime;
	}
	public Float getCash() {
		return cash;
	}
	public void setCash(Float cash) {
		this.cash = cash;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOtherdesc() {
		return otherdesc;
	}
	public void setOtherdesc(String otherdesc) {
		this.otherdesc = otherdesc;
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
