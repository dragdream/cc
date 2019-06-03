package com.tianee.oa.core.workflow.workmanage.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeWorkQueryServiceInterface {

	/**
	 * 查询与高级查询
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson query(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel);


	public abstract void setFlowPrivService(
			TeeFlowPrivServiveInterface flowPrivService);




	public abstract void setPersonDao(TeePersonDao personDao);


	/**
	 * 导出excel表格
	 * 
	 * @param request
	 * @param response
	 */
	public abstract void exportExcel(Map params, TeePerson loginUser,
			HttpServletResponse response);

	public abstract List<Map> getResultList(Map queryParams, TeePerson loginUser);

	public abstract List<Map> getExcelResultList(Map queryParams,
			TeePerson loginUser);

	/**
	 * 
	 * @param params
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson getHasQueryPrivFlowTypeIds(Map queryParams,
			TeePerson loginUser);

	
	/**
	 * 流程恢复的步骤
	 * @param params
	 * @param loginUser
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson  getRecoverFlowRunPrcsList(Map params,TeePerson loginUser);


	/**
	 * 流程恢复
	 * @param params
	 * @param loginUser
	 * @return
	 */
	public abstract TeeJson recover(Map params,
			TeePerson loginUser);
}