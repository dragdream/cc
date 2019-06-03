package com.tianee.oa.core.base.pm.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 学习经历
 * @author kakalion
 *
 */
@Entity
@Table(name="HUMAN_EDUCATION")
public class TeeHumanEducation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="HUMAN_EDUCATION_seq_gen")
	@SequenceGenerator(name="HUMAN_EDUCATION_seq_gen", sequenceName="HUMAN_EDUCATION_seq")
	private int sid;
	
	private String eduMajor;//所学专业
	
	private String eduProject;//所获学历
	
	private Calendar startTime;//开始时间
	
	private Calendar endTime;//结束时间
	
	private String eduDegree;//学位
	
	private String eduLeader;//曾任班干
	
	private String eduProver;//证明人
	
	private String eduSchool;//所在院校
	
	private String eduSchoolPos;//院校所在地
	
	private String eduSchoolContact;//所在院校联系方式
	@Lob()
	private String remark;//备注
	
	@ManyToOne
	@Index(name="IDX57db28a7f0e6482a9dbd7485601")
	private TeeHumanDoc humanDoc;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getEduMajor() {
		return eduMajor;
	}

	public void setEduMajor(String eduMajor) {
		this.eduMajor = eduMajor;
	}

	public String getEduProject() {
		return eduProject;
	}

	public void setEduProject(String eduProject) {
		this.eduProject = eduProject;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
	}

	public Calendar getEndTime() {
		return endTime;
	}

	public void setEndTime(Calendar endTime) {
		this.endTime = endTime;
	}

	public String getEduDegree() {
		return eduDegree;
	}

	public void setEduDegree(String eduDegree) {
		this.eduDegree = eduDegree;
	}

	public String getEduLeader() {
		return eduLeader;
	}

	public void setEduLeader(String eduLeader) {
		this.eduLeader = eduLeader;
	}

	public String getEduProver() {
		return eduProver;
	}

	public void setEduProver(String eduProver) {
		this.eduProver = eduProver;
	}

	public String getEduSchool() {
		return eduSchool;
	}

	public void setEduSchool(String eduSchool) {
		this.eduSchool = eduSchool;
	}

	public String getEduSchoolPos() {
		return eduSchoolPos;
	}

	public void setEduSchoolPos(String eduSchoolPos) {
		this.eduSchoolPos = eduSchoolPos;
	}

	public String getEduSchoolContact() {
		return eduSchoolContact;
	}

	public void setEduSchoolContact(String eduSchoolContact) {
		this.eduSchoolContact = eduSchoolContact;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public TeeHumanDoc getHumanDoc() {
		return humanDoc;
	}

	public void setHumanDoc(TeeHumanDoc humanDoc) {
		this.humanDoc = humanDoc;
	}
	
	
}
