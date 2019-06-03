package com.tianee.oa.sync.config.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tianee.oa.sync.config.bean.TeeOutSystemConfig;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeOutSystemConfigService extends TeeBaseService{

	/**
	 * 分页查询
	 * @param model
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson dataGrid(TeeDataGridModel model, HttpServletRequest request) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeOutSystemConfig";
		List<TeeOutSystemConfig> configList = simpleDaoSupport.pageFind(hql, model.getFirstResult(), model.getRows(), null);
		Long total = simpleDaoSupport.count("select count(sid) " + hql, null);
		json.setRows(configList);
		json.setTotal(total);
		return json;
	}

	/**
	 * 添加配置
	 * @param request
	 * @return
	 */
	public TeeJson saveConfig(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String systemName = request.getParameter("systemName");
		String systemUrl = request.getParameter("systemUrl");
		
		TeeOutSystemConfig config = new TeeOutSystemConfig();
		config.setSystemName(systemName);
		config.setSystemUrl(systemUrl);
		simpleDaoSupport.save(config);
		json.setRtState(true);
		return json;
	}

	/**
	 * 根据id获取对象信息
	 * @param request
	 * @return
	 */
	public TeeJson getConfig(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	
		if (sid > 0) {
			TeeOutSystemConfig config = (TeeOutSystemConfig) simpleDaoSupport.get(TeeOutSystemConfig.class,sid);
			json.setRtData(config);
			json.setRtState(true);
		}else{
			json.setRtMsg("读取数据失败！");
			json.setRtState(false);
		}
		return json;
	}

	/**
	 * 修改配置
	 * @param request
	 * @return
	 */
	public TeeJson updateConfig(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String systemName = request.getParameter("systemName");
		String systemUrl = request.getParameter("systemUrl");
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		
		if (sid > 0) {
			TeeOutSystemConfig config = (TeeOutSystemConfig) simpleDaoSupport.get(TeeOutSystemConfig.class, sid);
			config.setSystemName(systemName);
			config.setSystemUrl(systemUrl);
			simpleDaoSupport.update(config);
			
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("修改失败！");
		}
		return json;
	}

	
	/**
	 * 删除配置
	 * @param request
	 * @return
	 */
	public TeeJson delConfig(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if (sid > 0) {
			simpleDaoSupport.delete(TeeOutSystemConfig.class,sid);
			json.setRtState(true);
		}else{
			json.setRtData("删除失败！");
			json.setRtState(false);
		}
		return json;
	}
}
