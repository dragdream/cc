package com.tianee.oa.core.base.exam.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 考试数据
 * 当用户点击“参加考试”的时候，系统先判断是否有该人的考试数据，
 * 如果没有，则根据题目ID，生成N条答题记录，然后呈现给前台。
 * 
 * 如果存在，则根据考试信息id和该人的id，查询出来这些数据，然后呈现给前台。
 * @author kakalion
 *
 */
@Entity
@Table(name="EXAM_DATA")
public class TeeExamData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAM_DATA_seq_gen")
	@SequenceGenerator(name="EXAM_DATA_seq_gen", sequenceName="EXAM_DATA_seq")
	private int sid;
	
	/**
	 * 答题人
	 */
	@ManyToOne()
	@Index(name="IDX660c4e75468a4357847584010d3")
	private TeePerson participant;
	
	/**
	 * 所属考试信息
	 */
	@ManyToOne()
	@Index(name="IDX4a42380321094da6bcd504ebbc0")
	private TeeExamInfo examInfo;
	
	/**
	 * 问题ID
	 */
	@ManyToOne()
	@Index(name="IDX8858ec1de20f4ac7b85fefa4f15")
	@JoinColumn(name="EXAM_QUEST")
	private TeeExamQuestion examQuest;
	
	/**
	 * 答案
	 */
	@Column(name="ANSWER",length=500)
	@Index(name="EXAM_DATA_ANWSER")
	private String answer;
	
	/**
	 * 单题所获得分数
	 */
	@Column(name="EXAM_SCORE")
	private int score;

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

	public TeeExamInfo getExamInfo() {
		return examInfo;
	}

	public void setExamInfo(TeeExamInfo examInfo) {
		this.examInfo = examInfo;
	}

	public TeeExamQuestion getExamQuest() {
		return examQuest;
	}

	public void setExamQuest(TeeExamQuestion examQuest) {
		this.examQuest = examQuest;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}
