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
@Table(name = "ux_tag_info")
public class TagInfo implements Serializable {   
	private static final long serialVersionUID = 1L;
	@Id  
    @Basic(optional = false)   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
	//唯一ID
    @Column(name = "id", nullable = false)   
    private Integer id;
	//文章id
	@Column(name = "articleid", nullable = false,length=30)   
	private String articleid;
	//标签名
	@Column(name = "tagname", nullable = false,length=30)   
	private String tagname;
	//标签名
	@Column(name = "userid", nullable = false,length=30)   
	private String userid;
	//最后更新时间
	@Column(name = "currentDate", nullable = true)
	private Date currentDate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getArticleid() {
		return articleid;
	}
	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
