package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
@Table(name="PERSONALITY_SETS")
public class TeePersonalitySettings {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PERSONALITY_SETS_seq_gen")
	@SequenceGenerator(name="PERSONALITY_SETS_seq_gen", sequenceName="PERSONALITY_SETS_seq")
	private int sid;
	
	@OneToOne
	@JoinColumn(name="USER_ID")
	private TeePerson person;
	
	/**
	 * {left:[1,2,3,4],middle:[1,2,3,4],right:[1,2,3,4]}
	 */
	@Column(name="WF_PANEL_POS")
	@Lob()
	private String wfPanelPosModel = "{}";//用户新建工作的面板位置模型
	
	/**
	 * [1,2,3,4,5]
	 */
	@Column(name="WF_STARS")
	@Lob()
	private String wfStarsModel = "[]";//工作流标星模型

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getPerson() {
		return person;
	}

	public void setPerson(TeePerson person) {
		this.person = person;
	}

	public String getWfPanelPosModel() {
		return wfPanelPosModel;
	}

	public void setWfPanelPosModel(String wfPanelPosModel) {
		this.wfPanelPosModel = wfPanelPosModel;
	}

	public String getWfStarsModel() {
		return wfStarsModel;
	}

	public void setWfStarsModel(String wfStarsModel) {
		this.wfStarsModel = wfStarsModel;
	}
	
}
