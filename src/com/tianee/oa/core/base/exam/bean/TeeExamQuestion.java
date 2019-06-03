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

/**
 * 试题
 * @author kakalion
 *
 */
@Entity
@Table(name="EXAM_QUESTION")
public class TeeExamQuestion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAM_QUESTION_seq_gen")
	@SequenceGenerator(name="EXAM_QUESTION_seq_gen", sequenceName="EXAM_QUESTION_seq")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDX01f40e5a2b154e28a3c4d502b08")
	@JoinColumn(name="EXAM_STORE")
	private TeeExamStore examStore;//所属题库
	
	/**
	 * 题型种类
	 * 1、单选
	 * 2、多选
	 * 3、主观
	 */
	@Column(name="QTYPE")
	private int qType;
	
	/**
	 * 难度
	 * 1、低
	 * 2、中
	 * 3、高
	 */
	@Column(name="QHARD")
	private int qHard;
	
	@Column(name="CONTENT")
	private String content;//题目内容
	
	@Column(name="SCORE")
	private int score;//分值
	
	//备选答案A
	@Column(name="OPT_A")
	private String optA;
	
	@Column(name="OPT_B")
	private String optB;
	
	@Column(name="OPT_C")
	private String optC;
	
	@Column(name="OPT_D")
	private String optD;
	
	@Column(name="OPT_E")
	private String optE;
	
	@Column(name="ANSWER")
	@Lob()
	private String answer;//正确答案

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeExamStore getExamStore() {
		return examStore;
	}

	public void setExamStore(TeeExamStore examStore) {
		this.examStore = examStore;
	}

	public int getQType() {
		return qType;
	}

	public void setQType(int type) {
		qType = type;
	}

	public int getQHard() {
		return qHard;
	}

	public void setQHard(int hard) {
		qHard = hard;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getOptA() {
		return optA;
	}

	public void setOptA(String optA) {
		this.optA = optA;
	}

	public String getOptB() {
		return optB;
	}

	public void setOptB(String optB) {
		this.optB = optB;
	}

	public String getOptC() {
		return optC;
	}

	public void setOptC(String optC) {
		this.optC = optC;
	}

	public String getOptD() {
		return optD;
	}

	public void setOptD(String optD) {
		this.optD = optD;
	}

	public String getOptE() {
		return optE;
	}

	public void setOptE(String optE) {
		this.optE = optE;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}
	
	
}
