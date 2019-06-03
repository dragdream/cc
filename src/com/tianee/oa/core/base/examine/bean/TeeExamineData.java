package com.tianee.oa.core.base.examine.bean;
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

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 */
@Entity
@Table(name="EXAMINE_DATA")
public class TeeExamineData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAMINE_DATA_seq_gen")
	@SequenceGenerator(name="EXAMINE_DATA_seq_gen", sequenceName="EXAMINE_DATA_seq")
	@Column(name="SID")
	private int sid;//SID	int(11)	自增字段	是	自增	
	
	@ManyToOne()
	@Index(name="IDX21a012ce91b84e48a25b31b8afc")
	@JoinColumn(name="TASK_ID")
	private TeeExamineTask task;//Task_ID	TeeExamineTask	任务
	
	@ManyToOne()
	@Index(name="IDX1b3bb973104b43f4add779ffbb3")
	@JoinColumn(name="RANKMAN")
	private TeePerson rankman;//		//考核人
	
	@ManyToOne()
	@Index(name="IDXe007e1209b884db6aff30df2943")
	@JoinColumn(name="PARTICIPANT")
	private TeePerson participant;//		//被考核人
	
	@Lob
	@Column(name="EXAMINE_DATA")
	private String examineData;//SELF_DATA	内容 [{item_id:2,score:20.4,desc:”测试”},{item_id:3,score:2.4,desc:”测试2”}]	考核内容：item_id为表EXAMINE_item.sidscore ：考核分数desc：考核描述

	@Column(name="EXAMINE_DATE")
	private long examineDate;//SELF_DATE	DATETIME	自评时间
	
	@Column(name="SCORE_")
	private double score;//总分

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeExamineTask getTask() {
		return task;
	}

	public void setTask(TeeExamineTask task) {
		this.task = task;
	}

	public TeePerson getParticipant() {
		return participant;
	}

	public void setParticipant(TeePerson participant) {
		this.participant = participant;
	}

	public TeePerson getRankman() {
		return rankman;
	}

	public void setRankman(TeePerson rankman) {
		this.rankman = rankman;
	}

	public String getExamineData() {
		return examineData;
	}

	public void setExamineData(String examineData) {
		this.examineData = examineData;
	}

	public long getExamineDate() {
		return examineDate;
	}

	public void setExamineDate(long examineDate) {
		this.examineDate = examineDate;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


		
}
