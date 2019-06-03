package com.tianee.oa.core.base.exam.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="EXAM_RECORD")
public class TeeExamRecord implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAM_RECORD_seq_gen")
	@SequenceGenerator(name="EXAM_RECORD_seq_gen", sequenceName="EXAM_RECORD_seq")
	private int sid;
	
	/**
	 * 答题人
	 */
	@ManyToOne()
	@Index(name="IDX32b13b4e0af64ab4af93f6cd4cd")
	private TeePerson participant;
	
	/**
	 * 考试信息
	 */
	@ManyToOne()
	@Index(name="IDX98160a75fda047c99df3074ee46")
    private TeeExamInfo teeExamInfo;
	
	/**
	 * 开始考试时间
	 */
	@Column(name="STARTEXAM_TIME")
	private Calendar startExamTime;
	
	
	
	public Calendar getStartExamTime() {
		return startExamTime;
	}


	public void setStartExamTime(Calendar startExamTime) {
		this.startExamTime = startExamTime;
	}


	/**
	 * 交卷时间
	 */
	@Column(name="SUBEXAM_TIME")
	private Calendar subExamTime;
	
	
	/**
	 * 是否已阅卷 判断当前考试信息对应的试卷是否存在主观题，
	 * 不存在默认为true,如果存在阅后为true ,否则为false
	 */
	@Column(name="IS_CHECKED",columnDefinition="char(1)")
	@org.hibernate.annotations.Type(type="yes_no")
	private boolean isChecked;


	
	/**
	 * 阅卷时间
	 */
	@Column(name="CHECKEXAM_TIME")
	private Calendar checkExamTime;
	
	@Column(name="SCORE_")
	private int score;//分数
	
	
	public int getSid() {
		return sid;
	}


	public void setSid(int sid) {
		this.sid = sid;
	}


	public TeePerson getParticipant() {
		return participant;
	}


	public void setParticipant(TeePerson participant) {
		this.participant = participant;
	}


	public TeeExamInfo getTeeExamInfo() {
		return teeExamInfo;
	}


	public void setTeeExamInfo(TeeExamInfo teeExamInfo) {
		this.teeExamInfo = teeExamInfo;
	}


	public Calendar getSubExamTime() {
		return subExamTime;
	}


	public void setSubExamTime(Calendar subExamTime) {
		this.subExamTime = subExamTime;
	}


	public boolean isChecked() {
		return isChecked;
	}


	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}


	public Calendar getCheckExamTime() {
		return checkExamTime;
	}


	public void setCheckExamTime(Calendar checkExamTime) {
		this.checkExamTime = checkExamTime;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}
	
}
