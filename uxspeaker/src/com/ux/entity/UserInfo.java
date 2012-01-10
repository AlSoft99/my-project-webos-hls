package com.ux.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "ux_user_info")
public class UserInfo implements Serializable {   
	private static final long serialVersionUID = 1L;
	@Id  
    @Basic(optional = false)   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
	//唯一ID
    @Column(name = "id", nullable = false)   
    private Integer id;
	//邮箱
	@Column(name = "userid", nullable = false,length=30,unique=true)
	private String userid;
	//用户名
	@Column(name = "username", nullable = false,length=30)
	private String username;
	//密码
	@Column(name = "password", nullable = false,length=30)
	private String password;
	//签名
	@Column(name = "sign", nullable = true,length=100)
	private String sign;
	//职位
	@Column(name = "job", nullable = true,length=30)
	private String job;
	//个人简介
	@Column(name = "intro", nullable = true,length=200)
	private String intro;
	//电话
	@Column(name = "phone", nullable = true,length=30)
	private String phone;
	//头像路径
	@Column(name = "picture", nullable = true,length=100)
	private String picture;
	//更新时间
	@Column(name = "currentdate", nullable = true)
	private Date currentdate;
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getId() {
		return id;
	}
	public Date getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}
