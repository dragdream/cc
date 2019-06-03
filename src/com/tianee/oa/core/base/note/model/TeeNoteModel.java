package com.tianee.oa.core.base.note.model;

import java.util.Date;

/**
 * 
 * @author syl
 *
 */

public class TeeNoteModel {
	private int sid;//自增id
	private String userId;//创建人
	private String userName;
	private Date createTime;//新建时间
	private String createTimeStr;
	private String content;//便签内容
	private String color;//颜色
	private int openFlag;//打开方式
	private int x;//x坐标
	private int y;//y坐标
	private int width = 100;//宽度
	private int height = 100;//高度
	private String top;
	private String left;
	
	
	public String getTop() {
		return top;
	}
	public String getLeft() {
		return left;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}

	
	
}

