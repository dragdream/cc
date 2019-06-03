package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 流程关注
 * @author kakalion
 *
 */
@Entity
@Table(name="FLOW_RUN_CONCERN")
public class TeeFlowRunConcern {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_CONCERN_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_CONCERN_seq_gen", sequenceName="FLOW_RUN_CONCERN_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX14baee8649d6467d93e2b71f1c9")
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;//所关注的流程
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Index(name="IDX9ae661886da848778a52387a49e")
	@JoinColumn(name="PERSON_ID")
	private TeePerson person;//关注人
	
	@Column(name="CONCERN_TIME")
	private Calendar concernTime = Calendar.getInstance();//关注时间

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

	public TeePerson getPerson() {
		return person;
	}

	public void setPerson(TeePerson person) {
		this.person = person;
	}

	public Calendar getConcernTime() {
		return concernTime;
	}

	public void setConcernTime(Calendar concernTime) {
		this.concernTime = concernTime;
	}
	
	
	
}
