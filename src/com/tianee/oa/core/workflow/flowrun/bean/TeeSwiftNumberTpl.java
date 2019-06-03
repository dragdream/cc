package com.tianee.oa.core.workflow.flowrun.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 动态流水号模板
 * @author kakalion
 *
 */
@Entity
@Table(name="SWIFT_NUMBER_TPL")
public class TeeSwiftNumberTpl {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="SWIFT_NUMBER_seq_gen")
	@SequenceGenerator(name="SWIFT_NUMBER_seq_gen", sequenceName="SWIFT_NUMBER_seq")
	@Column(name="SID")
	private int sid;
	
	/**
	 * 分组表达式
	 * 支持动态替换标记：${人员}  ${部门}  ${角色}  ${单位}   同时支持静态内容：京办发
	 */
	@Column(name="GROUP_EXP")
	private String groupExp;
	
	@Lob
	@Column(name="FLOW_PRIV")
	private String flowPriv;//流程权限      flowId逗号分隔
	
	@Column(name="COUNTING_TYPE")
	private int countingType = 1;//计算类型  1：一直累加    2：按年累加   3：按月累加   4：按日累加

	@Column(name="NUMBER_BIT")
	private int numberBit;//编号位数
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getGroupExp() {
		return groupExp;
	}

	public void setGroupExp(String groupExp) {
		this.groupExp = groupExp;
	}

	public String getFlowPriv() {
		return flowPriv;
	}

	public void setFlowPriv(String flowPriv) {
		this.flowPriv = flowPriv;
	}

	public int getCountingType() {
		return countingType;
	}

	public void setCountingType(int countingType) {
		this.countingType = countingType;
	}

	public int getNumberBit() {
		return numberBit;
	}

	public void setNumberBit(int numberBit) {
		this.numberBit = numberBit;
	}
	
	
}
