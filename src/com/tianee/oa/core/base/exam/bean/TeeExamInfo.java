package com.tianee.oa.core.base.exam.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 考试信息
 * 提示：如果要查询出指定人员的考试信息，那么可以拼写这样的hql语句
 * from TeeExamInfo info where exists (select 1 from info.participant p where p.uuid=${当前人的uuid})
 * 用到了exists 子查询~~效率高
 * @author kakalion
 *
 */
@Entity
@Table(name="EXAM_INFO")
public class TeeExamInfo implements Serializable{
	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAM_INFO_seq_gen")
	@SequenceGenerator(name="EXAM_INFO_seq_gen", sequenceName="EXAM_INFO_seq")
	private int sid;
	
	@Column(name="EXAM_NAME")
	private String examName;//考试名称
	
	/**
	 * 考试参加人，集合
	 */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="EXAM_PARTICIPANT")
	private Set<TeePerson> participant = new HashSet<TeePerson>(0);
	
	/**
	 * 所用试卷
	 */
	@ManyToOne()
	@Index(name="IDX118b26451cc34f7e90dde27dbc1")
	@JoinColumn(name="PAPER_ID")
	private TeeExamPaper examPaper;
	
	/**
	 * 主观题阅卷人
	 */
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="EXAM_SUBEXAMINER")
	private Set<TeePerson> subExaminer = new HashSet<TeePerson>(0);
	
	@Column(name="START_TIME")
	private Calendar startTime;//开始时间
	
	@Column(name="END_TIME")
	private Calendar endTime;//结束时间
	
	@Column(name="CHECK_DAYS")
	private int checkDays;//查卷时间，为0则立刻查卷
	
	@Column(name="INFO_DESC")
	@Lob()
	private String infoDesc;//信息描述

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Set<TeePerson> getParticipant() {
		return participant;
	}

	public void setParticipant(Set<TeePerson> participant) {
		this.participant = participant;
	}

	public TeeExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(TeeExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public Set<TeePerson> getSubExaminer() {
		return subExaminer;
	}

	public void setSubExaminer(Set<TeePerson> subExaminer) {
		this.subExaminer = subExaminer;
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

	public int getCheckDays() {
		return checkDays;
	}

	public void setCheckDays(int checkDays) {
		this.checkDays = checkDays;
	}

	public String getInfoDesc() {
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}
	
	
}
