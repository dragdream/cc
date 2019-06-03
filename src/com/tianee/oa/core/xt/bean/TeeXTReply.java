package com.tianee.oa.core.xt.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;
/**
 * 回复
 * @author xsy
 *
 */
@Entity
@Table(name="XT_REPLY")
public class TeeXTReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="XT_REPLY_seq_gen")
	@SequenceGenerator(name="XT_REPLY_seq_gen", sequenceName="XT_REPLY_seq")
	private int sid;//主键
	
	@Column(name="CREATE_TIME")
	private Calendar createTime;//创建时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//创建人
	
	@Column(name="CONTENT")
	private String content;//回复内容
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="XT_PRCS_ID")
	private TeeXTRunPrcs xtRunPrcs;//关联的办理过程  


	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public Calendar getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}


	public TeePerson getCreateUser() {
		return createUser;
	}


	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public TeeXTRunPrcs getXtRunPrcs() {
		return xtRunPrcs;
	}


	public void setXtRunPrcs(TeeXTRunPrcs xtRunPrcs) {
		this.xtRunPrcs = xtRunPrcs;
	}
	
	
	
	
}
