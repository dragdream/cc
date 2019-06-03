package com.tianee.oa.sync.log.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.sync.config.bean.TeeOutSystemConfig;
import com.tianee.oa.sync.config.service.TeeOutSystemConfigService;
import com.tianee.oa.sync.log.bean.TeeOutSystemSyncLog;
import com.tianee.oa.sync.log.dao.TeeSyncLogDao;
import com.tianee.oa.sync.log.model.TeeSyncLogModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.HttpClientUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeSyncLogService extends TeeBaseService{

	@Autowired
	private TeeOutSystemConfigService configService;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSyncLogDao logDao;
	
	@Autowired
	private TeeDeptDao deptDao;
	/**
	 * 根据id获取日志信息
	 * @param request
	 * @return
	 */
	public TeeJson getSyncLogById(HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
			if (sid > 0) {
				TeeOutSystemSyncLog log = (TeeOutSystemSyncLog) simpleDaoSupport.get(TeeOutSystemSyncLog.class, sid);
				TeeSyncLogModel model = parseToModel(log);
				json.setRtData(model);
				json.setRtState(true);
			}else{
				json.setRtState(false);
				json.setRtMsg("获取失败！");
			}
		return json;
	}

	/**
	 * 删除日志信息
	 * @param request
	 * @return
	 */
	public TeeJson delSyncLogById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String idString = request.getParameter("ids");
		if (!TeeUtility.isNullorEmpty(idString)) {
			String[] ids = idString.split(",");
			for (String id : ids) {
				simpleDaoSupport.delete(TeeOutSystemSyncLog.class, Integer.valueOf(id));
			}
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败！");
		}
		return json;
	}

	/**
	 * 分页查询
	 * @param model
	 * @param queryModel
	 * @return
	 */
	public TeeEasyuiDataGridJson getPage(TeeDataGridModel model,
			TeeSyncLogModel queryModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		String hql = "from TeeOutSystemSyncLog where syncFlag=0";
		String personName = queryModel.getSubmitUserName();
		String minCreateTime = queryModel.getMinCreateTime();
		String maxCreateTime = queryModel.getMaxCreateTime();
		
		if (!TeeUtility.isNullorEmpty(personName)) {
			hql += " and submitUserName like '%"+personName+"%'";
		} 
		//处理日期
		List<Object> param=new ArrayList<Object>();
		if(!TeeUtility.isNullorEmpty(minCreateTime)){
			Calendar min=TeeDateUtil.parseCalendar(minCreateTime);
			hql+=" and crTime>=?";
			param.add(min);	
		}
		
		if(!TeeUtility.isNullorEmpty(maxCreateTime)){
			Calendar max=TeeDateUtil.parseCalendar(maxCreateTime);
			hql+=" and crTime<=?";
			param.add(max);	
		}
		//处理返回结果
		Long total = simpleDaoSupport.count("select count(sid) " + hql, param.toArray());
		List<TeeOutSystemSyncLog> logList = simpleDaoSupport.pageFind(hql+" order by crTime desc", model.getFirstResult(), model.getRows(), param.toArray());
		json.setTotal(total);
		
		List<TeeSyncLogModel> logs = new ArrayList<TeeSyncLogModel>();
		TeeSyncLogModel logModel = null;
		if (logList != null && logList.size() > 0) {
			for (TeeOutSystemSyncLog log : logList) {
				logModel = parseToModel(log);
				logs.add(logModel);
			}
		}
		json.setRows(logs);
		return json;
	}

	/**
	 * 实体类转换model
	 * @param log
	 * @return
	 */
	private TeeSyncLogModel parseToModel(TeeOutSystemSyncLog log) {
		TeeSyncLogModel model = new TeeSyncLogModel();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BeanUtils.copyProperties(log, model);
		if (log != null) {
			//创建时间
			Calendar crTime = log.getCrTime();
			int configId = log.getConfigId();
			if(crTime != null){
				String createTimeStr = df.format(log.getCrTime().getTime());
				model.setCreateTimeStr(createTimeStr);
			}
			//所属系统
			if (configId > 0) {
				TeeOutSystemConfig config = (TeeOutSystemConfig) simpleDaoSupport.get(TeeOutSystemConfig.class, configId);
				if(config!=null){
					model.setSystemName(config.getSystemName());
				}
			}
		}
		
		return model;
	}

	/**
	 * 重新同步数据
	 * @param sid
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public TeeJson syncLog(int sid,HttpServletRequest request) throws UnsupportedEncodingException {
		TeeJson json = new TeeJson();
		TeeOutSystemSyncLog log = (TeeOutSystemSyncLog) simpleDaoSupport.get(TeeOutSystemSyncLog.class, sid);
		if (log != null) {
			//获取具体操作 0：新增人员 1：修改人员 2：人员离职 3：新增部门 4：修改部门 5：删除部门
			String operation = log.getOperation();
			//获取配置url
			int configId = TeeStringUtil.getInteger(log.getConfigId(), 0);
			TeeOutSystemConfig config = (TeeOutSystemConfig) simpleDaoSupport.get(TeeOutSystemConfig.class, configId);
			String param = log.getRequestJson();
			if(config!=null){
				//处理响应
				String respJson = HttpClientUtil.requestGet(config.getSystemUrl()+"?json="+URLEncoder.encode(param,"UTF-8"));
				boolean status = (boolean) JSONObject.fromObject(respJson).get("status");
				//新增人员
				if (!TeeUtility.isNullorEmpty(respJson)) {
					if (status) {
						//状态更新
						log.setSyncFlag("1");
						logDao.update(log);
						json.setRtState(true);
					}else{
						json.setRtState(false);
						json.setRtMsg("同步失败！");
					}
				}
			}else{
				json.setRtState(false);
				json.setRtMsg("同步失败！");
			}
			
		}
		return json;
	}

}
