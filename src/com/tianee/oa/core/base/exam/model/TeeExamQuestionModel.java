package com.tianee.oa.core.base.exam.model;

import java.io.Serializable;
import java.util.List;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;

/**
 * 试题
 *
 */
public class TeeExamQuestionModel implements Serializable{
	private int sid;
	
	private int storeId;//所属题库
	private String storeName;
	
	/**
	 * 题型种类
	 * 1、单选
	 * 2、多选
	 * 3、主观
	 */
	private int qType;
	private String qTypeDesc;
	/**
	 * 难度
	 * 1、低
	 * 2、中
	 * 3、高
	 */
	private int qHard;
	private String qHardDesc;
	
	private String content;//题目内容
	
	private int score;//分值
	
	private List<TeeAttachmentModel> attacheModels;
	
	//备选答案A
	private String optA;
	
	private String optB;
	
	private String optC;
	
	private String optD;
	
	private String optE;
	
	private String answer;//正确答案

	
	private boolean check;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public int getqType() {
		return qType;
	}

	public void setqType(int qType) {
		this.qType = qType;
	}

	public int getqHard() {
		return qHard;
	}

	public void setqHard(int qHard) {
		this.qHard = qHard;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<TeeAttachmentModel> getAttacheModels() {
		return attacheModels;
	}

	public void setAttacheModels(List<TeeAttachmentModel> attacheModels) {
		this.attacheModels = attacheModels;
	}

	public String getOptA() {
		return optA;
	}

	public void setOptA(String optA) {
		this.optA = optA;
	}

	public String getOptB() {
		return optB;
	}

	public void setOptB(String optB) {
		this.optB = optB;
	}

	public String getOptC() {
		return optC;
	}

	public void setOptC(String optC) {
		this.optC = optC;
	}

	public String getOptD() {
		return optD;
	}

	public void setOptD(String optD) {
		this.optD = optD;
	}

	public String getOptE() {
		return optE;
	}

	public void setOptE(String optE) {
		this.optE = optE;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getqTypeDesc() {
		return qTypeDesc;
	}

	public void setqTypeDesc(String qTypeDesc) {
		this.qTypeDesc = qTypeDesc;
	}

	public String getqHardDesc() {
		return qHardDesc;
	}

	public void setqHardDesc(String qHardDesc) {
		this.qHardDesc = qHardDesc;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
}
