package com.tianee.oa.app.quartz.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

/**
 * 定时任务中心
 * @author kakalion
 *
 */
@Entity
@Table(name="QUARTZ_TASK")
public class TeeQuartzTask {
	@Id
	@GenericGenerator(name="systemUUID",strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String sid;
	
	@Column(name="FROM_")
	private String from;
	
	@Column(name="TO_")
	private String to;
	
	@Column(name="MODEL_NO")
	@Index(name="QUARTZ_TASK_MN")
	private String modelNo;//模块编码
	
	@Column(name="MODEL_ID")
	@Index(name="QUARTZ_TASK_MI")
	private String modelId;//模块id
	
	@Column(name="CONTENT")
	private String content;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="URL1")
	private String url1;
	
	@Column(name="EXP")
	private String exp;
	
	@Column(name="EXP_DESC")
	private String expDesc;
	
	@Column(name="TYPE")
	private int type;
	
	@Column(name="NODE_")
	private String node;
	
	@Column(name="DELETE_STATUS")
	private int deleteStatus;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}
	
	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getExpDesc() {
		return expDesc;
	}

	public void setExpDesc(String expDesc) {
		this.expDesc = expDesc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public int getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(int deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getUrl1() {
		return url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}
	
	
}
