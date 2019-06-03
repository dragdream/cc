package com.tianee.oa.webframe.httpModel;


public class TeePortalModel extends TeeBaseModel{

	private int portalId;//id
	private String title;
	private String src;
	private int height;
	private String closable;
	private String collapsible;
	private int order;
	public int getPortalId() {
		return portalId;
	}
	public void setPortalId(int portalId) {
		this.portalId = portalId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getClosable() {
		return closable;
	}
	public void setClosable(String closable) {
		this.closable = closable;
	}
	public String getCollapsible() {
		return collapsible;
	}
	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
