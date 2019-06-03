package com.tianee.oa.core.base.note.bean;
import org.hibernate.annotations.Index;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tianee.oa.core.org.bean.TeePerson;

/**
 * 
 * @author syl
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NOTES")
public class TeeNote {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="NOTES_seq_gen")
	@SequenceGenerator(name="NOTES_seq_gen", sequenceName="NOTES_seq")
	@Column(name="SID")
	private int sid;//自增id
	
	@ManyToOne()
	@Index(name="IDXae6a341725c742f4aafcf416381")
	@JoinColumn(name="USER_ID")
	private TeePerson user;//创建人
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_TIME")
	private Date createTime;//新建时间
	
	@Lob
	@Column(name="CONTENT")
	private String content;//便签内容
	
	@Column(name="COLOR")
	private String color;//颜色
	
	@Column(name="OPEN_FLAG")
	private int openFlag;//打开方式

	@Column(name="X")
	private int x;//x坐标
	
	@Column(name="Y")
	private int y;//y坐标
	
	@Column(name="WIDTH_")
	private int width = 100;//宽度
	
	@Column(name="HEIGHT_")
	private int height = 100;//高度
	
	
	@Column(name="left_")
	private String left;//宽度
	
	@Column(name="top_")
	private String top;//高度
	

	
	
	
	public String getLeft() {
		return left;
	}

	public String getTop() {
		return top;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public TeePerson getUser() {
		return user;
	}

	public void setUser(TeePerson user) {
		this.user = user;
	}



	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(int openFlag) {
		this.openFlag = openFlag;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	
}

