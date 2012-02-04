package com.ux.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "ux_params_type")
public class ParamsType {
	private static final long serialVersionUID = 1L;
	@Id  
    @Basic(optional = false)   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
	//唯一ID
    @Column(name = "id", nullable = false)
	private Integer id;
	//唯一ID
    @Column(name = "typeid", nullable = false,length=20)
	private String typeid;
	//参数名称
	@Column(name = "typename", nullable = false,length=20)   
	private String typename;
	//参数类型描述
	@Column(name = "typedesc", nullable = false,length=40)   
	private String typedesc;
	//更新人
	@Column(name = "updtuser", nullable = false,length=30)   
	private String updtuser;
	//更新时间
	@Column(name = "updttime", nullable = false)   
	private Date updttime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getTypedesc() {
		return typedesc;
	}
	public void setTypedesc(String typedesc) {
		this.typedesc = typedesc;
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
