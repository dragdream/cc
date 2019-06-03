package com.tianee.oa.core.tpl.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 公共模版
 * @author kakalion
 *
 */
@Entity
@Table(name="PUB_TEMPLATE")
public class TeePubTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PUB_TEMPLATE_seq_gen")
	@SequenceGenerator(name="PUB_TEMPLATE_seq_gen", sequenceName="PUB_TEMPLATE_seq")
	private int sid;
	
	@Column(name="TPL_NAME")
	private String tplName;
	
	@Column(name="TPL_DESC")
	private String tplDesc;
	
	@Lob
	@Column(name="TPL_CONTENT")
	private String tplContent;
	
	@Column(name="SORT_NO")
	private int sortNo;

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTplName() {
		return tplName;
	}

	public void setTplName(String tplName) {
		this.tplName = tplName;
	}

	public String getTplDesc() {
		return tplDesc;
	}

	public void setTplDesc(String tplDesc) {
		this.tplDesc = tplDesc;
	}

	public String getTplContent() {
		return tplContent;
	}

	public void setTplContent(String tplContent) {
		this.tplContent = tplContent;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getSortNo() {
		return sortNo;
	}
	
}
