package com.beidasoft.xzfy.organ.model.response;

import java.util.List;

import com.beidasoft.xzfy.base.model.response.Response;
import com.beidasoft.xzfy.organ.bean.OrganInfo;

public class OrganListResponse extends Response{
	
	//组织机关列表
	private List<OrganInfo> rows;

	//总记录数
	private int total;
	
	//继承
	public OrganListResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

	public List<OrganInfo> getRows() {
		return rows;
	}

	public void setRows(List<OrganInfo> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
