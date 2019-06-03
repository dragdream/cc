package com.tianee.oa.core.attachment.bean;
import org.hibernate.annotations.Index;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 附件空间，用于附件存储位置，可进行均衡等
 * @author kakalion
 *
 */
@Entity
@Table(name="ATTACH_SPACE")
@Cacheable()
public class TeeAttachmentSpace {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="ATTACH_SPACE_seq_gen")
	@SequenceGenerator(name="ATTACH_SPACE_seq_gen", sequenceName="ATTACH_SPACE_seq")
	private int sid;
	
	@Column(name="SPACE_PATH")
	private String spacePath;//空间路径
	
	@Column(name="WEIGHT")
	private int weight=1;//权重
	
	@Column(name="STATUS")
	private int status=0;//开启状态

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSpacePath() {
		return spacePath;
	}

	public void setSpacePath(String spacePath) {
		this.spacePath = spacePath;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
