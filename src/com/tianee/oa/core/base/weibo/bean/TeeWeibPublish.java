package com.tianee.oa.core.base.weibo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 发布信息实体类
 * */
@Entity
@Table(name="weib_publish")
public class TeeWeibPublish {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="weib_publish_seq_gen")
	@SequenceGenerator(name="weib_publish_seq_gen", sequenceName="weib_publish_seq")
	@Column(name = "SID")
	private int sid;//发布信息主键
	
	@Column(name="CONTENT")
	private String content;//发布内容
	
	@Column(name="IMG")
	private String img;//上传图片id字符串
	
	@Column(name="COUNT_")
	private int count;//点赞次数
	
	@Column(name="USER_ID")
	private int userId;//发布者id
	
	@Column(name="NUM")
	private int num;//评论次数
	
	@Column(name="NUMBER_")
	private int number;//转发次数
	
	@Column(name="CREATE_TIME")
	private Date createTime;//发布时间
	
	@Column(name="ZF_ID")
	private int zfId;//被转发的id
	
	@Column(name="IMGY")
	private String imgy;//原图字符串


	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getZfId() {
		return zfId;
	}

	public void setZfId(int zfId) {
		this.zfId = zfId;
	}

	public String getImgy() {
		return imgy;
	}

	public void setImgy(String imgy) {
		this.imgy = imgy;
	}

}
