package com.tianee.oa.core.workflow.flowrun.bean;
import org.hibernate.annotations.Index;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name="FLOW_RUN_FB_CTRL")
public class TeeFlowRunFeedbackCtrl {
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	private int sid;
	
//	@Column(name="CONTENT")
	private String content;
	
//	@Column(name="CREATE_TIME")
	private Calendar createTime;
	
	
}
