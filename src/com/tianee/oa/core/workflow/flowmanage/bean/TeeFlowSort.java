package com.tianee.oa.core.workflow.flowmanage.bean;
import org.hibernate.annotations.Index;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="FLOW_SORT")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class TeeFlowSort  implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="FLOW_SORT_seq_gen")
	@SequenceGenerator(name="FLOW_SORT_seq_gen", sequenceName="FLOW_SORT_seq")
	@Column(name="SID")
	private int sid;
	
	@Column(name="ORDER_NO")
	private int orderNo;//排序号
	
	@Column(name="SORT_NAME")
	private String sortName;//分类名称
	
	@OneToMany(mappedBy="flowSort",fetch=FetchType.LAZY)
	private List<TeeFlowType> flowTypeSet;//流程集合

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public void setFlowTypeSet(List<TeeFlowType> flowTypeSet) {
		this.flowTypeSet = flowTypeSet;
	}

	public List<TeeFlowType> getFlowTypeSet() {
		return flowTypeSet;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getSid() {
		return sid;
	}
	
	
}
