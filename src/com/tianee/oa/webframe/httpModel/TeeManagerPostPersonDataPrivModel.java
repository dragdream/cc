package com.tianee.oa.webframe.httpModel;

import java.util.List;

import com.tianee.oa.core.org.bean.TeePerson;
/**
 * 人员管理权限模型
 * @author think
 *
 */
public class TeeManagerPostPersonDataPrivModel {
	private String privType ;//权限类型       0-为空   ；  ALL-所有 ；  -其他
	
	private List<TeePerson> personList;//人员数组
	
	private  List<Integer> personIds;//人员Idlist
	
	private  List<String> personUserIds;//人员userId list


	public String getPrivType() {
		return privType;
	}

	public void setPrivType(String privType) {
		this.privType = privType;
	}

	public List<TeePerson> getPersonList() {
		return personList;
	}

	public void setPersonList(List<TeePerson> personList) {
		this.personList = personList;
	}

	public List<Integer> getPersonIds() {
		return personIds;
	}

	public void setPersonIds(List<Integer> personIds) {
		this.personIds = personIds;
	}

	public List<String> getPersonUserIds() {
		return personUserIds;
	}

	public void setPersonUserIds(List<String> personUserIds) {
		this.personUserIds = personUserIds;
	}

	
	
}
