package com.tianee.oa.subsys.zhidao.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 标签
 * @author xsy
 *
 */
@Entity
@Table(name = "ZD_LABEL")
public class TeeZhiDaoLabel {
	@Id
	@Column(name="LABEL_NAME")
	private String labelName;//主键
	

	@Column(name="CLICK0")
	private int click;


	public String getLabelName() {
		return labelName;
	}


	public int getClick() {
		return click;
	}


	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}


	public void setClick(int click) {
		this.click = click;
	}
	
	
	
}
