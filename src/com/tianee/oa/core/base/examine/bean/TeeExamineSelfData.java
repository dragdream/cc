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
@Entity
@Table(name="EXAMINE_SELF_DATA")
public class TeeExamineSelfData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="EXAMINE_SELF_DATA_seq_gen")
	@SequenceGenerator(name="EXAMINE_SELF_DATA_seq_gen", sequenceName="EXAMINE_SELF_DATA_seq")
	@Column(name="SID")
	private int sid;//SID	int(11)	自增字段	是	自增	
	
	@ManyToOne()
	@Index(name="IDX6eb4b6d0ee7c48638060674eb24")
	@JoinColumn(name="TASK_ID")
	private TeeExamineTask task;//Task_ID	TeeExamineTask	任务
	
	@ManyToOne()
	@Index(name="IDX6ff1c956adc0422699cfa0c21b1")
	@JoinColumn(name="PARTICIPANT")
	private TeePerson participant;//		//被考核人
	
	@Lob
	@Column(name="SELF_DATA")
	private String selfData;//SELF_DATA	自评内容 [{item_id:2,score:20.4,desc:”测试”},{item_id:3,score:2.4,desc:”测试2”}]	考核内容：item_id为表EXAMINE_item.sidscore ：考核分数desc：考核描述

	@Column(name="SCORE_")
	private double score;//总分
	
	private long selfDate;//SELF_DATE	DATETIME	自评时间

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



	public String getSelfData() {
		return selfData;
	}

	public void setSelfData(String selfData) {
		this.selfData = selfData;
	}

	public long getSelfDate() {
		return selfDate;
	}

	public void setSelfDate(long selfDate) {
		this.selfDate = selfDate;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
}
