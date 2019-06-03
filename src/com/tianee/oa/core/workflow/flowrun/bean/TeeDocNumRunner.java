package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="DOC_NUM_RUNNER")
public class TeeDocNumRunner {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="DOC_NUM_RUNNER_seq_gen")
	@SequenceGenerator(name="DOC_NUM_RUNNER_seq_gen", sequenceName="DOC_NUM_RUNNER_seq")
	private int sid;
	
	@ManyToOne()
	@Index(name="IDX984e858c5f4e404c91364512b84")
	@JoinColumn(name="DOC_NUM")
	private TeeDocNum docNum;
	
	@Column(name="DOC_WORD")
	private String word;//文件字
	
	@Column(name="DOC_YEAR")
	private String year;//文件年
	
	@Column(name="NUM")
	private int num;//文号
	
	@Column(name="COUNT_NUM")
	private int countNum;//文号计数值
	
	@Column(name="CREATE_TIME")
	private Calendar createTime = Calendar.getInstance();//创建时间
	
	@ManyToOne()
	@Index(name="IDX1aa9806d52724d41939d16237ba")
	@JoinColumn(name="CREATE_USER")
	private TeePerson createUser;//创建人
	
	private int runId;//所属流程ID
	
	private int flowId;//流程类型ID

	
	
	
	
	
	public int getCountNum() {
		return countNum;
	}

	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public TeePerson getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TeePerson createUser) {
		this.createUser = createUser;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setDocNum(TeeDocNum docNum) {
		this.docNum = docNum;
	}

	public TeeDocNum getDocNum() {
		return docNum;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	
	
}
