package com.tianee.oa.core.base.attend.bean;
import org.hibernate.annotations.Index;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ATTEND_LEADER_RULE")
public class TeeAttendLeaderRule {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTEND_LEADER_RULE_seq_gen")
	@SequenceGenerator(name="ATTEND_LEADER_RULE_seq_gen", sequenceName="ATTEND_LEADER_RULE_seq")
	@Column(name="SID")
	private int sid;//自增id
	


	@ManyToMany(targetEntity=TeeDepartment.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="ATTEND_RULE_DEPT",        
			joinColumns={@JoinColumn(name="RULE_ID")},       
			inverseJoinColumns={@JoinColumn(name="DEPT_ID")}  ) 	
	private List<TeeDepartment> depts;//管辖范围部门Id
	
	@ManyToMany(targetEntity=TeePerson.class,   //多对多     
			fetch = FetchType.LAZY  ) 
			@JoinTable( name="ATTEND_RULE_PERSON",        
			joinColumns={@JoinColumn(name="RULE_ID")},       
			inverseJoinColumns={@JoinColumn(name="USER_ID")}  ) 	
	private List<TeePerson> users;//管辖范围人员Id
	
	@Column(name="LEADER_IDS" , length=600)
	private String leaderIds;//审批人员Id字符串

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	
	public List<TeeDepartment> getDepts() {
		return depts;
	}

	public void setDepts(List<TeeDepartment> depts) {
		this.depts = depts;
	}

	public List<TeePerson> getUsers() {
		return users;
	}

	public void setUsers(List<TeePerson> users) {
		this.users = users;
	}

	public String getLeaderIds() {
		return leaderIds;
	}

	public void setLeaderIds(String leaderIds) {
		this.leaderIds = leaderIds;
	}
	
	
}

