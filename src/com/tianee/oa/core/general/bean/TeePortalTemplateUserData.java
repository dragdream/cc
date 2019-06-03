package com.tianee.oa.core.general.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 门户模板
 * @author kakalion
 *
 */
@Entity
@Table(name="PORTAL_TEMPLATE_U_DATA")
public class TeePortalTemplateUserData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PORTAL_TPL_U_DATA_seq_gen")
	@SequenceGenerator(name="PORTAL_TPL_U_DATA_seq_gen", sequenceName="PORTAL_TPL_U_DATA_seq")
	@Column(name="SID")
	private int sid;
	
	@ManyToOne()
	@JoinColumn(name="PORTAL_TEMPLATE_ID")
	private TeePortalTemplate portalTemplate;
	
	@ManyToOne()
	@JoinColumn(name="USER_ID")
	private TeePerson user;
	
	@Lob
	@Column(name="PORTAL_DATA")
	private String portalData;//门户数据   json串  格式参照person表中desktop字段

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePortalTemplate getPortalTemplate() {
		return portalTemplate;
	}

	public void setPortalTemplate(TeePortalTemplate portalTemplate) {
		this.portalTemplate = portalTemplate;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}

	public String getPortalData() {
		return portalData;
	}

	public void setPortalData(String portalData) {
		this.portalData = portalData;
	}
	
	
}
