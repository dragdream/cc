package com.tianee.oa.core.base.dam.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.tianee.oa.core.base.dam.bean.TeeFiles;
import com.tianee.oa.core.org.bean.TeePerson;



public class TeeFileBorrowModel{

	public int sid;
	
	
	
	//private TeePerson viewUser;//借阅人
	private int viewUserId;//借阅人主键
	private String viewUserName;//借阅人姓名
	
	//private Calendar viewTime;//借阅时间
	private String viewTimeStr;//借阅时间
	
	//private Calendar returnTime;//归还时间
	private String returnTimeStr;//归还时间
	
	private int returnFlag;//归还状态   0=未归还   1=归还中  2=已归还   

	
	//private TeePerson approver;//审批人
	private int approverId;//审批人主键
	private  String approverName;//审批人姓名
	
	//private Calendar approveTime;//审批时间
	private String approveTimeStr;//审批时间
	
	private int approveFlag;//审批状态   0=待审批  1=已批准   2=未批准
	
	
	
	//private TeeFiles file;//档案
	private int fileId;//档案主键
	private String fileTitle;//档案标题
	private String fileNumber;//档案编号
	private String fileUnit;//档案收发文单位
	private String fileRt;//档案保管期限
	private String fileMj;//档案密级
	private String fileHj;//档案缓急
	
	
	
	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public String getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getFileUnit() {
		return fileUnit;
	}

	public void setFileUnit(String fileUnit) {
		this.fileUnit = fileUnit;
	}

	public String getFileRt() {
		return fileRt;
	}

	public void setFileRt(String fileRt) {
		this.fileRt = fileRt;
	}

	public String getFileMj() {
		return fileMj;
	}

	public void setFileMj(String fileMj) {
		this.fileMj = fileMj;
	}

	public String getFileHj() {
		return fileHj;
	}

	public void setFileHj(String fileHj) {
		this.fileHj = fileHj;
	}

	public int getApproverId() {
		return approverId;
	}

	public void setApproverId(int approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproveTimeStr() {
		return approveTimeStr;
	}

	public void setApproveTimeStr(String approveTimeStr) {
		this.approveTimeStr = approveTimeStr;
	}

	public int getApproveFlag() {
		return approveFlag;
	}

	public void setApproveFlag(int approveFlag) {
		this.approveFlag = approveFlag;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	

	public int getViewUserId() {
		return viewUserId;
	}

	public void setViewUserId(int viewUserId) {
		this.viewUserId = viewUserId;
	}

	public String getViewUserName() {
		return viewUserName;
	}

	public void setViewUserName(String viewUserName) {
		this.viewUserName = viewUserName;
	}

	public String getViewTimeStr() {
		return viewTimeStr;
	}

	public void setViewTimeStr(String viewTimeStr) {
		this.viewTimeStr = viewTimeStr;
	}

	public String getReturnTimeStr() {
		return returnTimeStr;
	}

	public void setReturnTimeStr(String returnTimeStr) {
		this.returnTimeStr = returnTimeStr;
	}

	public int getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(int returnFlag) {
		this.returnFlag = returnFlag;
	}
	
	
	
	
	
}