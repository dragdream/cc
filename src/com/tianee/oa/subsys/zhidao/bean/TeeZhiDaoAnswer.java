package com.tianee.oa.subsys.zhidao.bean;

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
 * 回答
 * @author xsy
 *
 */
@Entity
@Table(name = "ZD_ANSWER")
public class TeeZhiDaoAnswer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="zd_answer_seq_gen")
	@SequenceGenerator(name="zd_answer_seq_gen", sequenceName="zd_answer_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	
	@Column(name="CONTENT")
	private String content ;//回答内容
	
	@Column(name="CREATE_TIME")
	private Calendar createTime ;//创建时间
	
	@Column(name="IS_BEST")
	private int isBest ;//是否是最佳答案  1=是  0=不是
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="QUESTION_ID")
	private TeeZhiDaoQuestion question;//所属问题	
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CREATE_USER_ID")
	private TeePerson createUser;//创建人


	public int getSid() {
		return sid;
	}


	public String getContent() {
		return content;
	}


	public Calendar getCreateTime() {
		return createTime;
	}


	public int getIsBest() {
		return isBest;
	}


	public TeeZhiDaoQuestion getQuestion() {
		return question;
	}


	public TeePerson getCreateUser() {
		return createUser;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}


	public void setIsBest(int isBest) {
		this.isBest = isBest;
	}


	public void setQuestion(TeeZhiDaoQuestion question) {
		this.question = question;
	}


	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}
	
    
	
	
	
}
