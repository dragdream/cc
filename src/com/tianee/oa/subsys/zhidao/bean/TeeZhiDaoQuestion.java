package com.tianee.oa.subsys.zhidao.bean;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 知道问题
 * @author xsy
 *
 */
@Entity
@Table(name = "ZD_QUESTION")
public class TeeZhiDaoQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="zd_question_seq_gen")
	@SequenceGenerator(name="zd_question_seq_gen", sequenceName="zd_question_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@Column(name="TITLE")
	private String title ;//标题
	
	@Lob
	@Column(name="DESC_")
	private String description;//描述
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CAT_ID")
	private TeeZhiDaoCat cat;//所属分类
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//创建人
	
	@Column(name="CREATE_TIME")
	private Date createTime;//创建时间
	
	
	@Column(name="STATUS_")
	private int status;//状态  0=未解决  1=已解决
	
	
	@Column(name="LABEL_")
	private String label;//标签
	
	@Column(name="CLICK0")
	private int clickCount;//点击量

	
	@Column(name="HANDLE_TIME")
	private Date handleTime;//问题处理时间
	
	
	
	
	
	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getSid() {
		return sid;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public TeeZhiDaoCat getCat() {
		return cat;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public int getStatus() {
		return status;
	}

	public String getLabel() {
		return label;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCat(TeeZhiDaoCat cat) {
		this.cat = cat;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	
	
	
	
}
