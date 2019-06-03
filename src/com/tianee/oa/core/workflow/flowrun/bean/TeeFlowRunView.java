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
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;

/**
 * 流程查阅
 * @author kakalion
 *
 */
@Entity
@Table(name="FLOW_RUN_VIEW")
public class TeeFlowRunView {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_VIEW_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_VIEW_seq_gen", sequenceName="FLOW_RUN_VIEW_seq")
	@Column(name="SID")
	private int sid;

	@ManyToOne
	@Index(name="IDXef7ee67b0a03458192e3edc9863")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;//所属流程
	
	@Column(name="PRCS_ID")
	private int prcsId;//实际步骤
	
	@ManyToOne
	@Index(name="IDX57000ad439cf4e3b9538b875d6c")
	@JoinColumn(name="FLOW_ID")
	private TeeFlowProcess flowPrcs;//设计步骤
	
	@ManyToOne
	@Index(name="IDXdd9ea21bc249419c87cdae47bb2")
	@JoinColumn(name="VIEW_PERSON")
	private TeePerson viewPerson;//查阅人
	
	@Column(name="VIEW_TIME")
	private Calendar viewTime;//查看时间
	
	@Column(name="VIEW_FLAG")
	private int viewFlag=0;//查看标记
	
	@Column(name="CREATE_TIME")
	private Calendar createTime = Calendar.getInstance();//创建时间

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}

	public TeeFlowProcess getFlowPrcs() {
		return flowPrcs;
	}

	public void setFlowPrcs(TeeFlowProcess flowPrcs) {
		this.flowPrcs = flowPrcs;
	}

	public TeePerson getViewPerson() {
		return viewPerson;
	}

	public void setViewPerson(TeePerson viewPerson) {
		this.viewPerson = viewPerson;
	}

	public Calendar getViewTime() {
		return viewTime;
	}

	public void setViewTime(Calendar viewTime) {
		this.viewTime = viewTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public void setViewFlag(int viewFlag) {
		this.viewFlag = viewFlag;
	}

	public int getViewFlag() {
		return viewFlag;
	}
	
}
