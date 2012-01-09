package com.ux.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity  
@Table(name = "ux_user_info")
public class UserInfo implements Serializable {   
	private static final long serialVersionUID = 1L;
	@Id  
    @Basic(optional = false)   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
    @Column(name = "id", nullable = false)   
    private Integer id;
	@Column(name = "userid", nullable = false,length=30,unique=true)
	private String userid;
	@Column(name = "username", nullable = false,length=30)
	private String username;
	@Column(name = "password", nullable = false,length=30)
	private String password;
	@Column(name = "picture", nullable = true,length=100)
	private String picture;
	public Integer getId() {
		return id;
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
