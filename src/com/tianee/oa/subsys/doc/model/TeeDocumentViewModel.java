package com.tianee.oa.subsys.doc.model;

import java.util.Calendar;

import javax.persistence.Column;

public class TeeDocumentViewModel {
	private String uuid;//自增id
	private int runId;
	private int flowId;//流程Id
	private String bt;//公文标题
	private String gwlx;//公文类型
	private String zh;//字号
	private String bh;//编号
	private String mmdj;//秘密等级
	private String fwdw;
	private String zs;//主送
	
	private String cs;//抄送
	
	private String gyfs;//共印份数
	
	private String fj;//附件
	
	private String ztc;//主题词
	
	private String bz;//备注
	private int flag;
	private String jjcd;//紧急程度
	private int recUser;//接收人
	private String recUserName;
	private int sendUser;//发送人
	private String sendUserName;
	private Calendar sendTime;//发送时间
	private Calendar recTime;//接收时间
	private int hasToDoc;//是否已转公文
    private int printNum;//打印份数
	
	private int isDownLoad;//是否可以下载
	
	private int contentPriv;//内容权限
	public int getContentPriv() {
		return contentPriv;
	}
	public void setContentPriv(int contentPriv) {
		this.contentPriv = contentPriv;
	}
	public int getPrintNum() {
		return printNum;
	}
	public void setPrintNum(int printNum) {
		this.printNum = printNum;
	}
	public int getIsDownLoad() {
		return isDownLoad;
	}
	public void setIsDownLoad(int isDownLoad) {
		this.isDownLoad = isDownLoad;
	}
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getRunId() {
		return runId;
	}
	public void setRunId(int runId) {
		this.runId = runId;
	}
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getGwlx() {
		return gwlx;
	}
	public void setGwlx(String gwlx) {
		this.gwlx = gwlx;
	}
	public String getZh() {
		return zh;
	}
	public void setZh(String zh) {
		this.zh = zh;
	}
	public String getBh() {
		return bh;
	}
	public void setBh(String bh) {
		this.bh = bh;
	}
	public String getMmdj() {
		return mmdj;
	}
	public void setMmdj(String mmdj) {
		this.mmdj = mmdj;
	}
	public String getJjcd() {
		return jjcd;
	}
	public void setJjcd(String jjcd) {
		this.jjcd = jjcd;
	}
	public int getRecUser() {
		return recUser;
	}
	public void setRecUser(int recUser) {
		this.recUser = recUser;
	}
	public String getRecUserName() {
		return recUserName;
	}
	public void setRecUserName(String recUserName) {
		this.recUserName = recUserName;
	}
	public int getSendUser() {
		return sendUser;
	}
	public void setSendUser(int sendUser) {
		this.sendUser = sendUser;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public String getFwdw() {
		return fwdw;
	}
	public void setFwdw(String fwdw) {
		this.fwdw = fwdw;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Calendar getSendTime() {
		return sendTime;
	}
	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}
	public Calendar getRecTime() {
		return recTime;
	}
	public void setRecTime(Calendar recTime) {
		this.recTime = recTime;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getGyfs() {
		return gyfs;
	}
	public void setGyfs(String gyfs) {
		this.gyfs = gyfs;
	}
	public String getFj() {
		return fj;
	}
	public void setFj(String fj) {
		this.fj = fj;
	}
	public String getZtc() {
		return ztc;
	}
	public void setZtc(String ztc) {
		this.ztc = ztc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public int getFlowId() {
		return flowId;
	}
	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}
	public int getHasToDoc() {
		return hasToDoc;
	}
	public void setHasToDoc(int hasToDoc) {
		this.hasToDoc = hasToDoc;
	}
	
	
	
}
