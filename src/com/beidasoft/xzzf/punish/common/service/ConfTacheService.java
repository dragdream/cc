package com.beidasoft.xzzf.punish.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.dao.ConfTacheDao;
import com.beidasoft.xzzf.punish.common.model.ConfTacheModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class ConfTacheService extends TeeBaseService {
	
	@Autowired
	private ConfTacheDao confTacheDao;
	
	/**
	 * 获取环节信息列表
	 * 
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getTacheInfoList(TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		
		//查询条件list
		List<String> param = new ArrayList<String>();
		//基础hql
		String hql=" from ConfTache ";
		
		String tacheName = request.getParameter("confTacheName");
		if (StringUtils.isNotBlank(tacheName) ) {
			hql += " where confTacheName like '%" + tacheName + "%'";
		}
		
		long total = simpleDaoSupport.count("select count(confTacheCode) " + hql, param.toArray());
		datagrid.setTotal(total);
		@SuppressWarnings("unchecked")
		List<ConfTache> list = simpleDaoSupport.pageFind(hql + " order by confTacheIndex ", 
				(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		
		List<ConfTacheModel> models = new ArrayList<ConfTacheModel>();
		ConfTacheModel model = null;
		for (ConfTache row : list) {
			model = new ConfTacheModel();
			BeanUtils.copyProperties(row, model);
			models.add(model);
		}
		
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	/**
	 * 获取环节信息
	 * 
	 * @param confFlowId
	 * @return
	 */
	public List<ConfTacheModel> getConfTacheInfo(ConfTache confTache) {
		ConfTacheModel tm = null;
		List<ConfTacheModel> tacheMdlList = new ArrayList<ConfTacheModel>();
		List<ConfTache> tacheList = confTacheDao.getConfTacheInfo(confTache);
		
		for (ConfTache ct : tacheList) {
			tm = new ConfTacheModel();
			BeanUtils.copyProperties(ct, tm);
			tacheMdlList.add(tm);
		}
		return tacheMdlList;
	}
	
	/**
	 * 编辑环节信息
	 * 
	 * @param confTache
	 */
	public void edit(ConfTache confTache) {
		confTacheDao.saveOrUpdate(confTache);
	}
	
	/**
	 * 删除环节信息
	 * 
	 * @param id
	 */
	public void delete(String id) {
		confTacheDao.delete(id);
	}
	
	/**
	 * 通过Id查询
	 * @param id
	 * @return
	 */
	public ConfTache getById(String id) {
		return confTacheDao.get(id);
	}
}
