package com.tianee.oa.core.base.hr.training.record.bean;
import org.hibernate.annotations.Index;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.base.hr.training.plan.bean.TeeTrainingPlan;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 招聘计划
 * 
 * @author wyw
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HR_TRAINING_RECORD")
public class TeeTrainingRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HR_TRAINING_RECORD_seq_gen")
	@SequenceGenerator(name="HR_TRAINING_RECORD_seq_gen", sequenceName="HR_TRAINING_RECORD_seq")
	@Column(name = "SID")
	private int sid;// 自增id

	@ManyToOne(fetch = FetchType.LAZY)
	@Index(name="IDXa76581e8f9a34767b2407e47426")
	@JoinColumn(name="PLAN_ID")
	private TeeTrainingPlan plan;//培训计划
	
	@ManyToOne()
	@Index(name="IDXf1e9fc7917d04c45b08f6e2dc0a")
	@JoinColumn(name="PERSON_ID")
	private TeePerson recordUser;//培训记录人
	
	// 培训机构
	@Column(name = "RECORD_INSTITUTION", nullable = true)
	private String recordInstitution;//INSTITUTION

	// 培训费用
	@Column(name = "RECORD_COST", nullable = true, length = 254)
	private double recordCost;

	
	// 培训考核成绩
	@Column(name = "EXAM_RESULTS" )
	private double examResults = 0.0;
	
	
	// 	培训考核等级
	@Column(name = "EXAM_LEVEL" , columnDefinition="int default 0")
	private int examLevel = 0;

	// 出勤情况：
	@Column(name = "DUTY_SITUATION" ,length=500)
	private String dutySituation;
	
	// 总结完成情况：
	@Column(name = "TRAINNING_SITUATION" ,length=500)
	private String trainningSituation;

	// 评论：
	@Lob
	@Column(name = "RECORD_COMMENT")
	private String recordComment;

	// 培训备注
	@Lob
	@Column(name = "REMARK")
	private String remark;
	// 创建时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public TeeTrainingPlan getPlan() {
		return plan;
	}
	public void setPlan(TeeTrainingPlan plan) {
		this.plan = plan;
	}
	public TeePerson getRecordUser() {
		return recordUser;
	}
	public void setRecordUser(TeePerson recordUser) {
		this.recordUser = recordUser;
	}
	public String getRecordInstitution() {
		return recordInstitution;
	}
	public void setRecordInstitution(String recordInstitution) {
		this.recordInstitution = recordInstitution;
	}
	public double getRecordCost() {
		return recordCost;
	}
	public void setRecordCost(double recordCost) {
		this.recordCost = recordCost;
	}

	public double getExamResults() {
		return examResults;
	}
	public void setExamResults(double examResults) {
		this.examResults = examResults;
	}
	public int getExamLevel() {
		return examLevel;
	}
	public void setExamLevel(int examLevel) {
		this.examLevel = examLevel;
	}
	public String getDutySituation() {
		return dutySituation;
	}
	public void setDutySituation(String dutySituation) {
		this.dutySituation = dutySituation;
	}
	public String getTrainningSituation() {
		return trainningSituation;
	}
	public void setTrainningSituation(String trainningSituation) {
		this.trainningSituation = trainningSituation;
	}

	public String getRecordComment() {
		return recordComment;
	}
	public void setRecordComment(String recordComment) {
		this.recordComment = recordComment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	
}
