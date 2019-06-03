package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 归档表名
 * @author ny
 */
@Entity
@Table(name="archives")
public class TeeArchives {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="archives_seq_gen")
	@SequenceGenerator(name="archives_seq_gen", sequenceName="archives_seq")
	private int sid;
	
	@Column(name="table_name")
	private String tableName;//名称
	
	@Column(name="module_no")
	private String moduleNo;//模块编号

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getModuleNo() {
		return moduleNo;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}
	
}
