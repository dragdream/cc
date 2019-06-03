package com.tianee.oa.core.priv.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.tianee.oa.core.org.bean.TeePerson;

@Entity
public class TeePersonResPriv {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PERSON_RES_PRIV_seq_gen")
	@SequenceGenerator(name="PERSON_RES_PRIV_seq_gen", sequenceName="PERSON_RES_PRIV_seq")
	private int personResPrivId;
	
	@OneToOne
	@JoinColumn(referencedColumnName="resCode")
	private TeeResource resource;//资源
	
	@OneToOne
	@JoinColumn(referencedColumnName="privCode")
	private TeePrivilege priv;//权限
	
	@OneToOne
	private TeePerson user;//用户
	
	private int privValue;//权限值
	
	public int getPersonResPrivId() {
		return personResPrivId;
	}
	public void setPersonResPrivId(int personResPrivId) {
		this.personResPrivId = personResPrivId;
	}
	public TeeResource getResource() {
		return resource;
	}
	public void setResource(TeeResource resource) {
		this.resource = resource;
	}
	public TeePrivilege getPriv() {
		return priv;
	}
	public void setPriv(TeePrivilege priv) {
		this.priv = priv;
	}
	public TeePerson getUser() {
		return user;
	}
	public void setUser(TeePerson user) {
		this.user = user;
	}
	public void setPrivValue(int privValue) {
		this.privValue = privValue;
	}
	public int getPrivValue() {
		return privValue;
	}
	
	
}
