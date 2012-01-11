package com.ux.entity;

import java.io.Serializable;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name = "ux_article_info")
public class ArticleInfo implements Serializable {   
	private static final long serialVersionUID = 1L;
	@Id  
    @Basic(optional = false)   
    @GeneratedValue(strategy = GenerationType.IDENTITY)   
	//唯一ID
    @Column(name = "id", nullable = false)   
    private Integer id;
	//类别
	@Column(name = "type", nullable = false,length=10)
	private String type;
	//题目
	@Column(name = "title", nullable = false,length=50)
	private String title;
	//图片路径
	@Column(name = "picture", nullable = true,length=50)
	private String picture;
	//内容
	@Column(name = "content", nullable = false)
	private Clob content;
	//标签
	@Column(name = "tag", nullable = true,length=50)
	private String tag;
	//首次添加时间
	@Column(name = "firstDate", nullable = true)
	private Date firstDate;
	//最后更新时间
	@Column(name = "currentDate", nullable = true)
	private Date currentDate;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Clob getContent() {
		return content;
	}
	public void setContent(Clob content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Date getFirstDate() {
		return firstDate;
	}
	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
}