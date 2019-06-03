package com.tianee.oa.subsys.bisengin.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import com.tianee.webframe.bean.TeeBaseEntity;

/**
 * 业务-内部流程引擎关系表
 * @author kakalion
 *
 */
@Entity
@Table(name="BIS_RUN_RELATION")
public class BisRunRelation extends TeeBaseEntity{
	@Column(name="BIS_ID")
	@Index(name="BISID_INDEX")
	private String bisId;
	
	@Column(name="BIS_TB_NAME")
	@Index(name="BIS_TB_NAME_INDEX")
	private String bisTable;
	
	@Column(name="RUN_ID")
	@Index(name="RUNID_INDEX")
	private int runId;

	public String getBisId() {
		return bisId;
	}

	public void setBisId(String bisId) {
		this.bisId = bisId;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public void setBisTable(String bisTable) {
		this.bisTable = bisTable;
	}

	public String getBisTable() {
		return bisTable;
	}
	
}
