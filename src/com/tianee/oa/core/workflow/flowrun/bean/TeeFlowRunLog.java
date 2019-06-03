package com.tianee.oa.core.workflow.flowrun.bean;
import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.IndexColumn;

import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Entity
@Table(name="FLOW_RUN_LOG")
public class TeeFlowRunLog  implements Serializable{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_RUN_LOG_seq_gen")
	@SequenceGenerator(name="FLOW_RUN_LOG_seq_gen", sequenceName="FLOW_RUN_LOG_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="RUN_ID")
	@IndexColumn(name="FRL_RUNID")
	private int runId;
	
	@Column(name="RUN_NAME")
	private String runName;
	
	@Column(name="FLOW_ID")
	private int flowId;
	
	@Column(name="FLOW_NAME")
	private String flowName;
	
	@Column(name="PRCS_ID")
	private int prcsId;
	
	@Column(name="FLOW_PRCS")
	private int flowPrcs;
	
	@Column(name="FLOW_PRCS_NAME")
	private String prcsName;
	
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="TIME")
	private Calendar time;
	
	@Column(name="IP")
	private String ip;
	
	/**
	 * type类型：
	 * 1-发起 2-转交 3-结束 4-自动委托 5-手动委托 6-强制结束 7-恢复 8-删除 9-销毁 10-回退 11-工作移交
	 */
	@Column(name="TYPE")
	private int type;
	
	@Column(name="CONTENT")
	private String content;
	
	@Column(name="USERNAME")
	private String userName;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public String getRunName() {
		return runName;
	}

	public void setRunName(String runName) {
		this.runName = runName;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public int getPrcsId() {
		return prcsId;
	}

	public void setPrcsId(int prcsId) {
		this.prcsId = prcsId;
	}

	public int getFlowPrcs() {
		return flowPrcs;
	}

	public void setFlowPrcs(int flowPrcs) {
		this.flowPrcs = flowPrcs;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Calendar getTime() {
		return time;
	}

	public void setTime(Calendar time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
	/**
	 * 获取类型描述
	 * @param type
	 * @return
	 */
	public static String getLogTypeDesc(int type){
		switch(type){
			case 1:return "发起";
			case 2:return "转交";
			case 3:return "结束";
			case 4:return "自动委托";
			case 5:return "手动委托";
			case 6:return "强制结束";
			case 7:return "恢复";
			case 8:return "删除";
			case 9:return "销毁";
			case 10:return "回退";
			default:return "";
		}
	}
	
	/**
	 * 获取默认流程日志实例
	 * @return
	 */
	public static TeeFlowRunLog getInstance(){
		TeeFlowRunLog frl = new TeeFlowRunLog();
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		if(info==null){
			return frl;
		}
		frl.setIp(info.getIpAddress());
		frl.setTime(Calendar.getInstance());
		frl.setUserId(info.getUserSid());
		frl.setUserName(info.getUserName());
		return frl;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getPrcsName() {
		return prcsName;
	}

	public void setPrcsName(String prcsName) {
		this.prcsName = prcsName;
	}
	
	
}
