package com.tianee.oa.subsys.doc.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 公文接收映射，部门与人员的映射
 * @author kakalion
 *
 */
@Entity
@Table(name = "DOCUMENT_REC_MAPPING")
public class TeeDocumentRecMapping {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEPT_ID")
	@Index(name="DOCUMENT_REC_MAPPING_DEPT_ID")
	private TeeDepartment dept;//部门
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="DOCUMENT_RECMAP_USERS",
			joinColumns={@JoinColumn(name="DOCUMENT_REC_MAP_ID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private Set<TeePerson> privUsers = new HashSet(0);//收文人员

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public TeeDepartment getDept() {
		return dept;
	}

	public void setDept(TeeDepartment dept) {
		this.dept = dept;
	}

	public Set<TeePerson> getPrivUsers() {
		return privUsers;
	}

	public void setPrivUsers(Set<TeePerson> privUsers) {
		this.privUsers = privUsers;
	}
}
