package com.tianee.oa.subsys.doc.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 公文分发
 * @author kakalion
 *
 */
@Entity
@Table(name = "DOCUMENT_DELIVERY")
public class TeeDocumentDelivery {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="RUN_ID")
	private int runId;
	
	@Column(name="FLOW_ID")
	private int flowId;//流程Id
	
	@Column(name="GWLX")
	private String gwlx;//公文类型
	
	@Column(name="BT")
	private String bt;//公文标题
	
	@Column(name="ZH")
	private String zh;//字号
	
	@Column(name="BH")
	private String bh;//编号
	
	@Column(name="MMDJ")
	private String mmdj;//秘密等级
	
	@Column(name="JJCD")
	private String jjcd;//紧急程度
	
	@Column(name="FWDW")
	private String fwdw;//发文单位
	
	@Column(name="ZS")
	private String zs;//主送
	
	@Column(name="CS")
	private String cs;//抄送
	
	@Column(name="GYFS")
	private String gyfs;//共印份数
	
	@Column(name="FJ")
	private String fj;//附件
	
	@Column(name="ZTC")
	private String ztc;//主题词
	
	@Column(name="BZ")
	private String bz;//备注
	
	@Column(name="REC_DEPT")
	private int recDept;//收文单位
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;//发送时间
	
	@Column(name="REC_TIME")
	private Calendar recTime;//签收时间
	
	@Column(name="FLAG_")
	private int flag;//0：待签收  1：已签收  2：拒签
	
	@Column(name="USER_ID")
	private int recUser;//签收人
	
	@Column(name="SEND_USER_ID")
	private int sendUser;//发送人
	
	@Column(name="READ_TIME")
	private Calendar readTime;//阅读时间
	
	@Column(name="HAS_TO_DOC")
	private int hasToDoc;//是否已转公文
	
	@Column(name="REMARK_")
	private String remark;//备注
	
	@ManyToOne
	@JoinColumn(name="REC_ID")
	private TeeDocumentDeliveryRecord record;
	
	@Column(name="print_num")
	private int printNum;//打印份数
	
	@Column(name="is_download")
	private int isDownLoad;//是否可以下载
	
	
	@Column(name="printed_num")
	private int printedNum;//打印份数
	
	
	public int getPrintedNum() {
		return printedNum;
	}

	public void setPrintedNum(int printedNum) {
		this.printedNum = printedNum;
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

	public String getFwdw() {
		return fwdw;
	}

	public void setFwdw(String fwdw) {
		this.fwdw = fwdw;
	}

	public int getRecDept() {
		return recDept;
	}

	public void setRecDept(int recDept) {
		this.recDept = recDept;
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

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getRecUser() {
		return recUser;
	}

	public void setRecUser(int recUser) {
		this.recUser = recUser;
	}

	public int getSendUser() {
		return sendUser;
	}

	public void setSendUser(int sendUser) {
		this.sendUser = sendUser;
	}

	public String getGwlx() {
		return gwlx;
	}

	public void setGwlx(String gwlx) {
		this.gwlx = gwlx;
	}

	public Calendar getReadTime() {
		return readTime;
	}

	public void setReadTime(Calendar readTime) {
		this.readTime = readTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public TeeDocumentDeliveryRecord getRecord() {
		return record;
	}

	public void setRecord(TeeDocumentDeliveryRecord record) {
		this.record = record;
	}

	public int getHasToDoc() {
		return hasToDoc;
	}

	public void setHasToDoc(int hasToDoc) {
		this.hasToDoc = hasToDoc;
	}
	
	
}
