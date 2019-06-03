package com.beidasoft.xzzf.punish.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.ConfFlow;
import com.beidasoft.xzzf.punish.common.dao.ConfFlowDao;
import com.beidasoft.xzzf.punish.common.model.ConfFlowModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class ConfFlowService extends TeeBaseService {
	
	@Autowired
	private ConfFlowDao confFlowDao;
	
	/**
	 * 获取环节流程信息列表
	 * 
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getFlowInfoList(TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		//查询条件list
		List<String> param = new ArrayList<String>();
		//基础hql
		String hql=" from ConfFlow where 1=1 ";
		
		int isStop = TeeStringUtil.getInteger(request.getParameter("isStop"), 0);
		if (isStop != -1) {
			hql += " and isStop = " + TeeStringUtil.getInteger(request.getParameter("isStop"), 0);
		}
		hql += " and isDelete = " + TeeStringUtil.getInteger(request.getParameter("isDelete"), 0);
		String flowName = request.getParameter("confFlowName");
		if (StringUtils.isNotBlank(flowName) ) {
			hql += " and confFlowName like '%" + flowName + "%'";
		}
		String tacheCode = request.getParameter("confTacheCode");
		if (StringUtils.isNotBlank(tacheCode) ) {
			hql += " and confTacheCode = '" + tacheCode + "'";
		}
		long total = simpleDaoSupport.count("select count(confFlowId) " + hql, param.toArray());
		datagrid.setTotal(total);
		@SuppressWarnings("unchecked")
		List<ConfFlow> list = simpleDaoSupport.pageFind(hql + " order by confTacheCode, confFlowIndex ", 
				(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		
		List<ConfFlowModel> models = new ArrayList<ConfFlowModel>();
		ConfFlowModel model = null;
		for (ConfFlow row : list) {
			model = new ConfFlowModel();
			BeanUtils.copyProperties(row, model);
			model.setCreateTimeStr(TeeStringUtil.getString(TeeDateUtil.format(row.getCreateTime()), ""));
			models.add(model);
		}
		
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	/**
	 * 获取环节流程信息
	 * 
	 * @param confFlowId
	 * @return
	 */
	public List<ConfFlowModel> getConfFlowInfo(ConfFlowModel confFlowModel) {
		ConfFlowModel fm = null;
		List<ConfFlowModel> flowMdlList = new ArrayList<ConfFlowModel>();
		List<ConfFlow> confFlowList = confFlowDao.getConfFlowInfo(confFlowModel);
		
		for (ConfFlow cf : confFlowList) {
			fm = new ConfFlowModel();
			BeanUtils.copyProperties(cf, fm);
			flowMdlList.add(fm);
		}
		return flowMdlList;
	}
	
	/**
	 * 编辑环节流程信息
	 * 
	 * @param confTache
	 */
	public void edit(ConfFlow confFlow) {
		confFlowDao.saveOrUpdate(confFlow);
	}
	
	/**
	 * 删除环节流程信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		confFlowDao.delete(id);
	}
	
	/**
	 * 通过环节Id  获取 系统定义流程信息
	 * 
	 * @param id
	 * @return
	 */
	public ConfFlow getById(String id) {
		ConfFlow flow = confFlowDao.get(id);
		return flow;
	}
}
