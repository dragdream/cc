package com.tianee.oa.subsys.informationReport.service;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplateItem;
import com.tianee.oa.subsys.informationReport.dao.TeeTaskTemplateItemDao;
import com.tianee.oa.subsys.informationReport.model.TeeTaskTemplateItemModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeTaskTemplateItemService extends TeeBaseService {
	@Autowired
	private TeeTaskTemplateItemDao taskTemplateItemDao;
	
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeTaskTemplateItem item=(TeeTaskTemplateItem) simpleDaoSupport.get(TeeTaskTemplateItem.class,sid);
		if(item!=null){
			TeeTaskTemplateItemModel model=parseToModel(item);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}


	
	/**
	 * 实体类转换成model类型
	 * @param item
	 * @return
	 */
	private TeeTaskTemplateItemModel parseToModel(TeeTaskTemplateItem item) {
		TeeTaskTemplateItemModel model=new TeeTaskTemplateItemModel();
		BeanUtils.copyProperties(item, model);
		if(item.getTaskTemplate()!=null){
			model.setTaskTemplateName(item.getTaskTemplate().getTaskName());
			model.setTaskTemplateSid(item.getTaskTemplate().getSid());
		}
		
		if(("null").equals(item.getShowType())){
			model.setShowType("");
		}
		
		return model;
	}


    /**
     * 新建/编辑
     * @param request
     * @return
     */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int taskTemplateSid=TeeStringUtil.getInteger(request.getParameter("taskTemplateSid"),0);
		TeeTaskTemplateItemModel model=new TeeTaskTemplateItemModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		if(sid>0){//编辑
			TeeTaskTemplateItem item=(TeeTaskTemplateItem) simpleDaoSupport.get(TeeTaskTemplateItem.class,sid);
			BeanUtils.copyProperties(model, item);
			
			simpleDaoSupport.update(item);
		}else{//新建
			TeeTaskTemplateItem item=new TeeTaskTemplateItem();
			BeanUtils.copyProperties(model, item);
			TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,taskTemplateSid);
			item.setTaskTemplate(template);
			
			simpleDaoSupport.save(item);
			
			
			//每添加一个模版项  同时需要执行
			String dialect=TeeSysProps.getDialect();
			if(item.getFieldType()==1||item.getFieldType()==2||item.getFieldType()==5){//字符       单行文本   多行文本  下拉菜单
				if("mysql".equals(dialect.toLowerCase())){
					simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add column DATA_"+item.getSid()+" varchar(2000) ", null);
				}else if("oracle".equals(dialect.toLowerCase())){
					simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" varchar2(4000) ", null);
				}else if("sqlserver".equals(dialect.toLowerCase())){
					simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" varchar(8000) ", null);
				}else if("kingbase".equals(dialect.toLowerCase())){
					simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" varchar2(4000) ", null);
				}
				
			}else if(item.getFieldType()==3){//数字
				
				 if("mysql".equals(dialect.toLowerCase())){
					    simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add column DATA_"+item.getSid()+" double(11,2) ", null);
					}else if("oracle".equals(dialect.toLowerCase())){
						simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" numeric(11,2) ", null);
					}else if("sqlserver".equals(dialect.toLowerCase())){
						simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" numeric(11,2) ", null);
					}else if("kingbase".equals(dialect.toLowerCase())){
						simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" numeric(11,2) ", null);
					}
			}else if(item.getFieldType()==4){//日期时间
				 
			     
				 if("mysql".equals(dialect.toLowerCase())){
					    simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add column DATA_"+item.getSid()+" datetime  ", null);
					}else if("oracle".equals(dialect.toLowerCase())){
						simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" date  ", null);
					}else if("sqlserver".equals(dialect.toLowerCase())){
						simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" datetime  ", null);
					}else if("kingbase".equals(dialect.toLowerCase())){
						simpleDaoSupport.executeNativeUpdate(" alter table REP_TASK_DATA_"+template.getSid()+" add  DATA_"+item.getSid()+" date ", null);
					}
			}
           
		}
		json.setRtState(true);
		return json;
	}



	/**
	 * 根据任务模板主键获取任务模板项列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getItemListByTaskTemplateId(
			TeeDataGridModel dm, HttpServletRequest request) {
		//获取任务模板主键
		int taskTemplateSid=TeeStringUtil.getInteger(request.getParameter("taskTemplateSid"), 0);
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeeTaskTemplateItem  t where t.taskTemplate.sid=? ";
		List param = new ArrayList();
		param.add(taskTemplateSid);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by t.sid asc";

		List<TeeTaskTemplateItem> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeTaskTemplateItemModel> modelList = new ArrayList<TeeTaskTemplateItemModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskTemplateItemModel model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeTaskTemplateItem item=(TeeTaskTemplateItem) simpleDaoSupport.get(TeeTaskTemplateItem.class,sid);
		if(item!=null){
			simpleDaoSupport.deleteByObj(item);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	
	/**
	 * 根据任务模板主键获取任务模板项列表  不分页
	 * @param request
	 * @return
	 */
	public TeeJson getListByTemplateId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"), 0);
		String hql = " from TeeTaskTemplateItem  t where t.taskTemplate.sid=? ";
		List<TeeTaskTemplateItem> list=simpleDaoSupport.executeQuery(hql, new Object[]{taskTemplateId});
		List<TeeTaskTemplateItemModel> modelList = new ArrayList<TeeTaskTemplateItemModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskTemplateItemModel model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}



	
	
	
}
