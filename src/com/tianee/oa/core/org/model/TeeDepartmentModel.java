package com.tianee.oa.core.org.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tianee.oa.webframe.httpModel.TeeBaseModel;




@SuppressWarnings("serial")
public class TeeDepartmentModel extends TeeBaseModel implements Comparable{
	
	private int uuid = 0;
	private String deptName;//部门名称
	private int deptNo;//部门排序号(用户同一级次部门排序)
	private String deptFunc;//部门职能
	private String telNo;//电话号码
	private String faxNo;//传真号码
	private String address;//部门地址
	private String pId;//父节点uuid
	private String pName;//父节点名称
	private String manager;
	private String managerName;
	private String manager2;
	private String manager2Name;
	private String leader1;//LEADER1
	private String leader1Name;
	private String leader2;
	private String leader2Name;
	private int deptParentId;
	private int deptType = 1;//部门类型  1：部门   2：分支机构  3：全局部门
	private String subordinateUnitsCode; //所属单位(存储代码表CODE值)
	private String subordinateUnits; //所属单位(存储代码表VALUE值)
	private String uniqueId;//唯一标示，做系统对接用
	private String deptFullId;
	
	
	private String  infoReportUserName;//信息上报员姓名
	private int infoReportUserId;//信息上报员主键
	
	private boolean gzDept;//是否关注此部门
    
	private int unitUuid; //所属单位ID
		
	public boolean isGzDept() {
		return gzDept;
	}
	public void setGzDept(boolean gzDept) {
		this.gzDept = gzDept;
	}
	public String getInfoReportUserName() {
		return infoReportUserName;
	}
	public void setInfoReportUserName(String infoReportUserName) {
		this.infoReportUserName = infoReportUserName;
	}
	public int getInfoReportUserId() {
		return infoReportUserId;
	}
	public void setInfoReportUserId(int infoReportUserId) {
		this.infoReportUserId = infoReportUserId;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getLeader1() {
		return leader1;
	}
	public void setLeader1(String leader1) {
		this.leader1 = leader1;
	}
	public String getLeader2() {
		return leader2;
	}
	public void setLeader2(String leader2) {
		this.leader2 = leader2;
	}
	public String getManagerName() {
		return managerName;
	}
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	public String getLeader1Name() {
		return leader1Name;
	}
	public void setLeader1Name(String leader1Name) {
		this.leader1Name = leader1Name;
	}
	public String getLeader2Name() {
		return leader2Name;
	}
	public void setLeader2Name(String leader2Name) {
		this.leader2Name = leader2Name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public int getUuid() {
		return uuid;
	}
	public void setUuid(int uuid) {
		this.uuid = uuid;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptFunc() {
		return deptFunc;
	}
	public void setDeptFunc(String deptFunc) {
		this.deptFunc = deptFunc;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getManager2() {
		return manager2;
	}
	public void setManager2(String manager2) {
		this.manager2 = manager2;
	}
	public String getManager2Name() {
		return manager2Name;
	}
	public void setManager2Name(String manager2Name) {
		this.manager2Name = manager2Name;
	}
	/**
	 * 重写
	 */
	@Override
	public int compareTo(Object obj ) {
		TeeDepartmentModel modul = (TeeDepartmentModel)obj;
		// TODO Auto-generated method stub
		 return this.getDeptName().compareTo(modul.getDeptName());
	}
	
	public static void main(String[] args) {
		TeeDepartmentModel modul1 = new TeeDepartmentModel();
		modul1.setDeptName("11");
		TeeDepartmentModel modul2 = new TeeDepartmentModel();
		modul2.setDeptName("");
		TeeDepartmentModel modul3 = new TeeDepartmentModel();
		modul3.setDeptName("1");
		
		TeeDepartmentModel modul4 = new TeeDepartmentModel();
		modul4.setDeptName("q");
		
		List<TeeDepartmentModel> list = new ArrayList<TeeDepartmentModel>();
		list.add(modul1);
		list.add(modul2);
		list.add(modul3);
		list.add(modul4);
		Collections.sort(list);
		for (int i = 0; i < list.size(); i++) {
			TeeDepartmentModel ca = list.get(i);
			System.out.println(ca.getDeptName());
		}
	}
	public int getDeptParentId() {
		return deptParentId;
	}
	public void setDeptParentId(int deptParentId) {
		this.deptParentId = deptParentId;
	}
	public int getDeptType() {
		return deptType;
	}
	public void setDeptType(int deptType) {
		this.deptType = deptType;
	}
	public String getDeptFullId() {
		return deptFullId;
	}
	public void setDeptFullId(String deptFullId) {
		this.deptFullId = deptFullId;
	}
	public String getSubordinateUnitsCode() {
		return subordinateUnitsCode;
	}
	public void setSubordinateUnitsCode(String subordinateUnitsCode) {
		this.subordinateUnitsCode = subordinateUnitsCode;
	}
	public String getSubordinateUnits() {
		return subordinateUnits;
	}
	public void setSubordinateUnits(String subordinateUnits) {
		this.subordinateUnits = subordinateUnits;
	}
	public int getUnitUuid() {
		return unitUuid;
	}
	public void setUnitUuid(int unitUuid) {
		this.unitUuid = unitUuid;
	}
	
}
