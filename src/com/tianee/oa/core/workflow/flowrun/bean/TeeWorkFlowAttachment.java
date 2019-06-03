//package com.tianee.oa.core.workflow.flowrun.bean;
//import org.hibernate.annotations.Index;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//import com.tianee.oa.core.attachment.bean.TeeAttachment;
//import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
//import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
//
//@Entity
//@Table(name = "flow_attachment")
//public class TeeWorkFlowAttachment {
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private int sid; //主键
//	
//	@ManyToOne
//	@Index(name="IDXb4c136bc5fc04c3cb037e0e0d96")
//	@JoinColumn(name="attach_id")
//	private TeeAttachment attach;
//	
//	@JoinColumn(name="run_id")
//	@ManyToOne(fetch=FetchType.LAZY)
//	@Index(name="IDX770d2af56cbf49edbb904291e3b")
//	private TeeFlowRun flowRun;
//	
//	@ManyToOne(fetch=FetchType.LAZY)
//	@Index(name="IDXa07142c40de24ea296ce3b837ae")
//	@JoinColumn(name="flow_prcs")
//	private TeeFlowProcess flowPrcs;
//	
//	@Column(name="prcs_id")
//	private int prcsId;
//	
//	public int getSid() {
//		return sid;
//	}
//
//	public void setSid(int sid) {
//		this.sid = sid;
//	}
//
//
//	public int getPrcsId() {
//		return prcsId;
//	}
//
//	public void setPrcsId(int prcsId) {
//		this.prcsId = prcsId;
//	}
//
//	public void setFlowPrcs(TeeFlowProcess flowPrcs) {
//		this.flowPrcs = flowPrcs;
//	}
//
//	public TeeFlowProcess getFlowPrcs() {
//		return flowPrcs;
//	}
//
//	public void setFlowRun(TeeFlowRun flowRun) {
//		this.flowRun = flowRun;
//	}
//
//	public TeeFlowRun getFlowRun() {
//		return flowRun;
//	}
//
//	public void setAttach(TeeAttachment attach) {
//		this.attach = attach;
//	}
//
//	public TeeAttachment getAttach() {
//		return attach;
//	}
//	
//	
//}
