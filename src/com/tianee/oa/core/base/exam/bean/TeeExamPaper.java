package com.tianee.oa.core.base.exam.bean;
import org.hibernate.annotations.Index;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 * 试卷
 * @author kakalion
 *
 */
@Entity
@Table(name="EXAM_PAPER")
public class TeeExamPaper {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAM_PAPER_seq_gen")
	@SequenceGenerator(name="EXAM_PAPER_seq_gen", sequenceName="EXAM_PAPER_seq")
	private int sid;
	
	/**
	 * 试卷标题
	 */
	@Column(name="P_TITLE")
	private String title;
	
	/**
	 * 分数类型
	 * 1、根据试题分值以百分比计算
	 * 2、按试题分数计分
	 */
	@Column(name="SCORE_TYPE")
	private int scoreType;
	
	/**
	 * 试卷总分
	 * 如果scoreType=2，则此项可以为空，
	 */
	@Column(name="SCORE_ALL")
	private int scoreAll;
	
	/**
	 * 试题数量
	 */
	@Column(name="QUESTION_COUNT")
	private int qCount;
	/**
	 * 考试时长 （单位为分钟）需为整数
	 */
	@Column(name="exam_times")
	private int examTimes;
	public int getExamTimes() {
		return examTimes;
	}

	public void setExamTimes(int examTimes) {
		this.examTimes = examTimes;
	}

	@ManyToOne
	@Index(name="IDX66d40938a9b84905b9dfe43e54b")
	private TeeExamStore examStore;//所属题库
	
	@Column(name="PAPER_DESC")
	@Lob()
	private String paperDesc;//试卷说明
	
	
	/**
	 * 试题list
	 */
	@ManyToMany
	private List<TeeExamQuestion> questionList = new ArrayList<TeeExamQuestion>();
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getScoreType() {
		return scoreType;
	}

	public void setScoreType(int scoreType) {
		this.scoreType = scoreType;
	}

	public int getScoreAll() {
		return scoreAll;
	}

	public void setScoreAll(int scoreAll) {
		this.scoreAll = scoreAll;
	}

	public int getQCount() {
		return qCount;
	}

	public void setQCount(int count) {
		qCount = count;
	}

	public TeeExamStore getExamStore() {
		return examStore;
	}

	public void setExamStore(TeeExamStore examStore) {
		this.examStore = examStore;
	}

	public String getPaperDesc() {
		return paperDesc;
	}

	public void setPaperDesc(String paperDesc) {
		this.paperDesc = paperDesc;
	}

	public List<TeeExamQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<TeeExamQuestion> questionList) {
		this.questionList = questionList;
	}
	
	
}
