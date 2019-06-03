package com.tianee.oa.core.general.bean;
import org.hibernate.annotations.Index;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
//@Entity
//@Table(name = "PORTLET_PERSONAL")
public class TeePortletPersonal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator="PORTLET_PERSONAL_seq_gen")
	@SequenceGenerator(name="PORTLET_PERSONAL_seq_gen", sequenceName="PORTLET_PERSONAL_seq")
	@Column(name="sid")
	private int sid;//id
	
	@ManyToOne
	@Index(name="IDXf478cc3f252d4b50a079e4995a8")
	@JoinColumn(name="portlet_id")//这是双向的 要写JoinColumn 对面要写 mappedBy
	private TeePortlet portletId;//桌面模块id
	
	@Column(name="user_id")
	private int userId;//人员id
	
	@Column(name="PORT_HEIGHT",columnDefinition="INT default 150")
	private int portHeight;//桌面高度
	
	@Column(name="PORT_COL",columnDefinition="INT default 1")
	private int portCol;//可在列数 可选（1、2、3）必须<=totalCol
	
	@Column(name="SORT_NO",columnDefinition="INT default 1")
	private int sortNo;//排序（在当前列的第几位即第几行）



	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}



	public TeePortlet getPortletId() {
		return portletId;
	}

	public void setPortletId(TeePortlet portletId) {
		this.portletId = portletId;
	}

	public int getPortHeight() {
		return portHeight;
	}

	public void setPortHeight(int portHeight) {
		this.portHeight = portHeight;
	}

	public int getPortCol() {
		return portCol;
	}

	public void setPortCol(int portCol) {
		this.portCol = portCol;
	}

	public int getSortNo() {
		return sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	
	
	
	
}
