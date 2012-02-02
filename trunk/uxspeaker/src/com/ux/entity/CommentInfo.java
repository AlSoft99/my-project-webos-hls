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
@Table(name = "ux_comment_info")
public class CommentInfo implements Serializable {   
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
	//用户id
	@Column(name = "userid", nullable = true,length=30)   
	private String userid;
	//称呼(未登陆填写)
	@Column(name = "commentuser", nullable = true,length=30)
	private String commentuser;
	//评论
	@Column(name = "comment", nullable = false,length=1000)   
	private String comment;
	//回复
	@Column(name = "receiver", nullable = true,length=30)   
	private String receiver;
	//赞成
	@Column(name = "approve", nullable = true)   
	private Integer approve;
	//反对
	@Column(name = "oppose", nullable = true)   
	private Integer oppose;
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCommentuser() {
		return commentuser;
	}
	public void setCommentuser(String commentuser) {
		this.commentuser = commentuser;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Integer getApprove() {
		return approve;
	}
	public void setApprove(Integer approve) {
		this.approve = approve;
	}
	public Integer getOppose() {
		return oppose;
	}
	public void setOppose(Integer oppose) {
		this.oppose = oppose;
	}
	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
}

