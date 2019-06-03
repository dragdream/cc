package com.beidasoft.xzfy.organPerson.model.response;

import java.util.List;

import com.beidasoft.xzfy.base.model.response.Response;
import com.beidasoft.xzfy.organPerson.bean.OrganPersonInfo;

public class OrganPersonListResponse extends Response{
	
	//组织机关列表
	private List<OrganPersonInfo> rows;

	//总记录数
	private int total;
	
	//继承
	public OrganPersonListResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

	public List<OrganPersonInfo> getRows() {
		return rows;
	}

	public void setRows(List<OrganPersonInfo> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
