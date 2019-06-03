package com.tianee.oa.subsys.doc.bean;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;

/**
 * 公文分发记录表
 * @author kakalion
 *
 */
@Entity
@Table(name = "DOCUMENT_DELIVERY_REC")
public class TeeDocumentDeliveryRecord {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne()
	@JoinColumn(name="RUN_ID")
	private TeeFlowRun flowRun;
	
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
	
	@Column(name="SEND_TIME")
	private Calendar sendTime;//发送时间
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SEND_USER_ID")
	private TeePerson sendUser;//发送人
	
	@Column(name="is_rec_remind")
	private  int  isRecRemind;//是否进行签收消息 提醒  0不提醒  1提醒
	
	@Column(name="content_priv")
	private  int  contentPriv;//内容权限
	
	public int getContentPriv() {
		return contentPriv;
	}

	public void setContentPriv(int contentPriv) {
		this.contentPriv = contentPriv;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeFlowRun getFlowRun() {
		return flowRun;
	}

	public void setFlowRun(TeeFlowRun flowRun) {
		this.flowRun = flowRun;
	}

	public int getFlowId() {
		return flowId;
	}

	public void setFlowId(int flowId) {
		this.flowId = flowId;
	}

	public String getGwlx() {
		return gwlx;
	}

	public void setGwlx(String gwlx) {
		this.gwlx = gwlx;
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

	public Calendar getSendTime() {
		return sendTime;
	}

	public void setSendTime(Calendar sendTime) {
		this.sendTime = sendTime;
	}

	public TeePerson getSendUser() {
		return sendUser;
	}

	public void setSendUser(TeePerson sendUser) {
		this.sendUser = sendUser;
	}

	public int getIsRecRemind() {
		return isRecRemind;
	}

	public void setIsRecRemind(int isRecRemind) {
		this.isRecRemind = isRecRemind;
	}
	
	
}
