package com.tianee.oa.core.workflow.flowrun.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowArchive;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowArchiveModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

public interface TeeFlowArchiveServiceInterface {

	/**
	 * 获取所有的归档列表（未删除）
	 * @param dm
	 * @param request
	 * @return
	 */
	public abstract TeeEasyuiDataGridJson getArchiveList(TeeDataGridModel dm,
			HttpServletRequest request);

	/**
	 * 将实体类转换成model
	 * @param flowArchive
	 * @param model
	 */
	public abstract void entityToModel(TeeFlowArchive flowArchive,
			TeeFlowArchiveModel model);

	/**
	 * 获取归档相关数据
	 * @param request
	 * @return
	 */
	public abstract TeeJson getArchiveCount(HttpServletRequest request);

	/**
	 * 流程数据归档
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	public abstract void doArchive(HttpServletRequest request,
			HttpServletResponse response) throws IOException;

	/**
	 * 删除流程归档数据（假删除）
	 * @param request
	 * @return
	 */
	public abstract TeeJson del(HttpServletRequest request);

	/**
	 *  根据归档日期获取归档列表     以判读该归档日期是否已经进行过归档操作
	 * @param request
	 * @return
	 */
	public abstract TeeJson getArchiveListByDate(HttpServletRequest request);

}