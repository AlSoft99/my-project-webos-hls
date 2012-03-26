package com.hrm.entity;

import java.io.Serializable;
import java.util.Date;

public class MaterialTypeKtv implements Serializable {   
	private static final long serialVersionUID = 1L;
	//唯一ID
    private String id;
	//种类名称
    private String typename;
	//种类描述
	private String typedesc;
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