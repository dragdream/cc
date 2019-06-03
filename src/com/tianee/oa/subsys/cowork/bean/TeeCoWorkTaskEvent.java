package com.tianee.oa.subsys.cowork.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="CO_WORK_EVENT")
public class TeeCoWorkTaskEvent {
	@Id
	@Column(name="SID")
	@GeneratedValue(strategy = GenerationType.AUTO, generator="CO_WORK_EVENT_seq_gen")
	@SequenceGenerator(name="CO_WORK_EVENT_seq_gen", sequenceName="CO_WORK_EVENT_seq")
	private int sid;
	
	@Column(name="CR_TIME")
	private String createTime;
	
	@Column(name="CREATE_USER_ID")
	private int createUserId;
	
	@Column(name="CREATE_USER_NAME")
	private String createUserName;
	
	/**
	 * 1、布置(green)
	 * 2、接受(green)
	 * 3、审批(orange)
	 * 4、审核(orange)
	 * 5、撤销(orange)
	 * 6、督办(blue)
	 * 7、延期(blue)
	 * 8、失败(red)
	 * 9、汇报(blue)
	 * 10、完成(green)
	 * 11、文档(red)
	 * 12、修改(red)
	 */
	@Column(name="TYPE")
	private int type;
	
	@Column(name="TYPE_DESC")
	private String typeDesc;
	
	@Column(name="TITLE")
	private String title;
	
	@Column(name="CONTENT")
	private String content;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX28c939291a634aff92f60401521")
	private TeeCoWorkTask task;//父任务

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TeeCoWorkTask getTask() {
		return task;
	}

	public void setTask(TeeCoWorkTask task) {
		this.task = task;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public int getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
