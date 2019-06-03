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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;

/**
 * 公文权限设置
 * @author kakalion
 *
 */
@Entity
@Table(name = "DOCUMENT_FLOW_PRIV")
public class TeeDocumentFlowPriv {
	@Id
	@GeneratedValue(generator = "system-uuid")  
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(columnDefinition = "char(32)",name="UUID")
	private String uuid;//自增id
	
	@Column(name="TYPE_")
	private int type;//类型 1：发文类型   2：收文类型
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FLOW_ID")
	@Index(name="DOCUMENT_FLOW_PRIV_FLOW_ID")
	private TeeFlowType flowType;//所属流程
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="DOCUMENT_FLOW_PRIV_DEPTS",
			joinColumns={@JoinColumn(name="DOCUMENT_FLOW_PRIV_DID")},
			inverseJoinColumns={@JoinColumn(name="DEPT_ID")})
	private Set<TeeDepartment> privDepts = new HashSet(0);//权限部门，表示哪些部门有该流程发起的权限
	
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="DOCUMENT_FLOW_PRIV_ROLES",
			joinColumns={@JoinColumn(name="DOCUMENT_FLOW_PRIV_RID")},
			inverseJoinColumns={@JoinColumn(name="ROLE_ID")})
	private Set<TeeUserRole> privRoles = new HashSet(0);//权限角色
	
	@ManyToMany(cascade={CascadeType.MERGE,CascadeType.PERSIST},fetch=FetchType.LAZY)
	@JoinTable(name="DOCUMENT_FLOW_PRIV_USERS",
			joinColumns={@JoinColumn(name="DOCUMENT_FLOW_PRIV_UID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID")})
	private Set<TeePerson> privUsers = new HashSet(0);//权限人员

	public Set<TeeUserRole> getPrivRoles() {
		return privRoles;
	}

	public void setPrivRoles(Set<TeeUserRole> privRoles) {
		this.privRoles = privRoles;
	}

	public Set<TeePerson> getPrivUsers() {
		return privUsers;
	}

	public void setPrivUsers(Set<TeePerson> privUsers) {
		this.privUsers = privUsers;
	}

	@Lob
	@Column(name="FIELDMAPPING")
	private String fieldMapping = "{}";//json格式字段映射     {表单字段1:内置字段,表单字段2:内置字段}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public TeeFlowType getFlowType() {
		return flowType;
	}

	public void setFlowType(TeeFlowType flowType) {
		this.flowType = flowType;
	}

	public Set<TeeDepartment> getPrivDepts() {
		return privDepts;
	}

	public void setPrivDepts(Set<TeeDepartment> privDepts) {
		this.privDepts = privDepts;
	}

	public String getFieldMapping() {
		return fieldMapping;
	}

	public void setFieldMapping(String fieldMapping) {
		this.fieldMapping = fieldMapping;
	}
	
	
}
