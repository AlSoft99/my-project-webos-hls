package com.ux.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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
	//用户id
	@Column(name = "userid", nullable = false,length=30)   
    private String userid;
	//类别
	@Column(name = "type", nullable = false,length=10)
	private String type;
	//状态(好友可见, 私有, 垃圾箱...)
	@Column(name = "status", nullable = false,length=10)
	private String status;
	//题目
	@Column(name = "title", nullable = false,length=50)
	private String title;
	//图片路径
	@Column(name = "picture", nullable = true,length=100)
	private String picture;
	//内容
	@Lob()
	@Type(type = "org.hibernate.type.StringClobType") 
	@Column(name = "content",columnDefinition="longtext", nullable = true)
	private String content;
	//开头部分文章
	@Column(name = "text", nullable = true,length=500)
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	//标签
	@Column(name = "tag", nullable = true,length=50)
	private String tag;
	//喜爱
	@Column(name = "love", nullable = true)   
    private Integer love;
	//浏览数
	@Column(name = "brower", nullable = true)   
	private Integer brower;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
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
	public Integer getLove() {
		return love;
	}
	public void setLove(Integer love) {
		this.love = love;
	}
	public Integer getBrower() {
		return brower;
	}
	public void setBrower(Integer brower) {
		this.brower = brower;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}