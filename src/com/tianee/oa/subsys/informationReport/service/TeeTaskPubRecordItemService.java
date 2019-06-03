package com.tianee.oa.subsys.informationReport.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskPubRecordItem;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplate;
import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplateItem;
import com.tianee.oa.subsys.informationReport.dao.TeeTaskPubRecordItemDao;
import com.tianee.oa.subsys.informationReport.model.TeeTaskPubRecordItemModel;
import com.tianee.oa.subsys.informationReport.model.TeeTaskTemplateModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeTaskPubRecordItemService extends TeeBaseService{
	@Autowired
	private TeeTaskPubRecordItemDao taskPubRecordItemDao;
	
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	/**
	 * 获取我的上报列表   0=待上报   1=已上报
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getMyReport(TeeDataGridModel dm,
			HttpServletRequest request,TeeTaskPubRecordItemModel models) {
		//获取发布状态
		int flag=TeeStringUtil.getInteger(request.getParameter("flag"),-1);
		
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " from TeeTaskPubRecordItem  t  left outer join t.dept  d  where t.flag=? and ( t.user.uuid=? or d.infoReportUser.uuid=? )";
		List param = new ArrayList();
		param.add(flag);
		param.add(loginPerson.getUuid());
		param.add(loginPerson.getUuid());
		if(models.getRepType()>0){
			hql+="and t.taskTemplate.repType=?";
			param.add(models.getRepType());
		}
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by t.createTime desc";
		
		List<TeeTaskPubRecordItem> list = simpleDaoSupport.pageFindByList("select t  "+ hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查
		List<TeeTaskPubRecordItemModel> modelList = new ArrayList<TeeTaskPubRecordItemModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskPubRecordItemModel model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}


	
	/**
	 * 实体类转换成model类型
	 * @param teeTaskPubRecordItem
	 * @return
	 */
	private TeeTaskPubRecordItemModel parseToModel(
			TeeTaskPubRecordItem teeTaskPubRecordItem) {
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeTaskPubRecordItemModel model=new TeeTaskPubRecordItemModel();
		BeanUtils.copyProperties(teeTaskPubRecordItem, model);
		if(teeTaskPubRecordItem.getCreateTime()!=null){
		     model.setCreateTimeStr(sdf.format(teeTaskPubRecordItem.getCreateTime().getTime()));
		}
		if(teeTaskPubRecordItem.getDept()!=null){
			model.setDeptId(teeTaskPubRecordItem.getDept().getUuid());
			model.setDeptName(teeTaskPubRecordItem.getDept().getDeptName());
		}
		if(teeTaskPubRecordItem.getTaskPubRecord()!=null){
			model.setTaskPubRecordId(teeTaskPubRecordItem.getTaskPubRecord().getSid());
		}
		
		
		if(teeTaskPubRecordItem.getTaskTemplate()!=null){
			model.setTaskTemplateId(teeTaskPubRecordItem.getTaskTemplate().getSid());
			model.setTaskTemplateName(teeTaskPubRecordItem.getTaskTemplate().getTaskName());
		    model.setTaskType(teeTaskPubRecordItem.getTaskTemplate().getTaskType());
		    model.setPubType(teeTaskPubRecordItem.getTaskTemplate().getPubType());
		    
		    if(teeTaskPubRecordItem.getTaskTemplate().getPubType()==1){// 1=按人员 2=按部门
		    	model.setPubTypeDesc("按人员");
		    }else{
		    	TeeDepartment dept=teeTaskPubRecordItem.getDept();
		    	if(dept!=null){
		    		model.setPubTypeDesc("按部门("+dept.getDeptName()+")");
		    	}	
		    }
		    
		  //拼接频次描述
		    String modelDesc="";
		    String taskTypeDesc="";
		    String model_=teeTaskPubRecordItem.getTaskTemplate().getModel();
		    Map m=TeeJsonUtil.JsonStr2Map(model_);
		    if(teeTaskPubRecordItem.getTaskTemplate().getTaskType()==1){//日报
		    	modelDesc="每天"+m.get("rbTime")+"开始";
		    	taskTypeDesc="日报";
		    }else if(teeTaskPubRecordItem.getTaskTemplate().getTaskType()==2){//周报
		    	String weekDesc="";
		    	taskTypeDesc="周报";
		    	if(TeeStringUtil.getInteger(m.get("week"),0)==1){
		    		weekDesc="一";
		    	}else if(TeeStringUtil.getInteger(m.get("week"),0)==2){
		    		weekDesc="二";
		    	}else if(TeeStringUtil.getInteger(m.get("week"),0)==3){
		    		weekDesc="三";
		    	}else if(TeeStringUtil.getInteger(m.get("week"),0)==4){
		    		weekDesc="四";
		    	}else if(TeeStringUtil.getInteger(m.get("week"),0)==5){
		    		weekDesc="五";
		    	}else if(TeeStringUtil.getInteger(m.get("week"),0)==6){
		    		weekDesc="六";
		    	}else if(TeeStringUtil.getInteger(m.get("week"),0)==7){
		    		weekDesc="日";
		    	}
		    	
		    	modelDesc="每周"+weekDesc+m.get("zbTime")+"开始";
		    }else if(teeTaskPubRecordItem.getTaskTemplate().getTaskType()==3){//月报
		    	taskTypeDesc="月报";
		    	modelDesc="每月"+m.get("ybDate")+"号"+m.get("ybTime")+"开始";
		    }else if(teeTaskPubRecordItem.getTaskTemplate().getTaskType()==4){//季报
		    	taskTypeDesc="季报";
		    	modelDesc="每季度第"+m.get("jbMonth")+"个月"+m.get("jbDate")+"号"+m.get("jbTime")+"开始";
		    }else if(teeTaskPubRecordItem.getTaskTemplate().getTaskType()==5){//年报
		    	taskTypeDesc="年报";
		    	modelDesc="每年"+m.get("nbMonth")+"月"+m.get("nbDate")+"号"+m.get("nbTime")+"开始";
		    }else if(teeTaskPubRecordItem.getTaskTemplate().getTaskType()==6){//一次性
		    	taskTypeDesc="一次性";
		    	modelDesc=m.get("ycxTime")+"开始 ";
		    }
		    model.setTaskTypeDesc(taskTypeDesc);
		    model.setPc(modelDesc);
		}
		
		return model;
	}



	/**
	 * 汇报
	 * @param request
	 * @return
	 */
	public TeeJson report(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		HttpServletRequest multipartRequest = null;
		if(request instanceof MultipartHttpServletRequest){//如果request可以强制转换的话
			multipartRequest = (MultipartHttpServletRequest) request;
		}else{
			multipartRequest = request;
		}
		
		int pubRecordItemId=TeeStringUtil.getInteger(multipartRequest.getParameter("pubRecordItemId"),0);
		int pubRecordId=TeeStringUtil.getInteger(multipartRequest.getParameter("pubRecordId"),0);
		int taskTemplateId=TeeStringUtil.getInteger(multipartRequest.getParameter("taskTemplateId"),0);
		
		//根据任务模板id 获取任务模板项 
		List<TeeTaskTemplateItem> itemList=simpleDaoSupport.executeQuery(" from TeeTaskTemplateItem t where t.taskTemplate.sid=? ", new Object[]{taskTemplateId});
		if(itemList!=null&&itemList.size()>0){
			String sql=" insert into rep_task_data_"+taskTemplateId+"(RECORD_ITEM_ID,REP_TASK_PUB_RECORD_ID,REP_TASK_TEMPLATE_ID,CREATE_TIME,CREATE_USER_ID";
		    String valueSql="  values(?,?,?,?,?";
			List<Object> param=new ArrayList<Object>();
		    param.add(pubRecordItemId);
		    param.add(pubRecordId);
		    param.add(taskTemplateId);
		    param.add(Calendar.getInstance());
		    param.add(loginUser.getUuid());
		    
		    String showType="";
		    SimpleDateFormat sdf=null;
			for (int i=0;i<itemList.size();i++) {
				sql+=",DATA_"+itemList.get(i).getSid();
				valueSql+=",?";
				if(itemList.get(i).getFieldType()==1||itemList.get(i).getFieldType()==2||itemList.get(i).getFieldType()==5){   //1=单行文本    2=多行文本       5=下拉菜单
					param.add(TeeStringUtil.getString(multipartRequest.getParameter("DATA_"+itemList.get(i).getSid())));
				}else if(itemList.get(i).getFieldType()==3){//  3=数字文本
					param.add(TeeStringUtil.getDouble(multipartRequest.getParameter("DATA_"+itemList.get(i).getSid()),0));
				}else if(itemList.get(i).getFieldType()==4){//  4=日期时间
					showType=itemList.get(i).getShowType();
					sdf=new SimpleDateFormat(showType);
					try {
						if(!TeeUtility.isNullorEmpty(multipartRequest.getParameter("DATA_"+itemList.get(i).getSid()))){
							param.add(sdf.parse(multipartRequest.getParameter("DATA_"+itemList.get(i).getSid())));
						}else{
							param.add(null);
						}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}		
				}
			}
			sql+=")"+ valueSql+") ";
			//System.out.println(sql);
			simpleDaoSupport.executeNativeUpdate(sql,param.toArray());
		}else{//没有任务模板项
			simpleDaoSupport.executeNativeUpdate(" insert into rep_task_data_"+taskTemplateId+"(RECORD_ITEM_ID,REP_TASK_PUB_RECORD_ID,REP_TASK_TEMPLATE_ID,CREATE_TIME) values(?,?,?,?) ", new Object[]{pubRecordItemId,pubRecordId,taskTemplateId,Calendar.getInstance()});
		}
		
		List<TeeAttachment> attachments=null;
		if(request instanceof MultipartHttpServletRequest){//如果request可以强制转换的话
			//保存附件
			MultipartHttpServletRequest multipartRequest1 = (MultipartHttpServletRequest) request;
			try {
				attachments = upload.manyAttachUpload(multipartRequest1, TeeAttachmentModelKeys.repTask);
			} catch (IOException e) {
				json.setRtState(false);
				e.printStackTrace();
			}
		}
		
		//修改任务发布记录项的状态
		TeeTaskPubRecordItem item=(TeeTaskPubRecordItem) simpleDaoSupport.get(TeeTaskPubRecordItem.class,pubRecordItemId);
		item.setFlag(1);
		simpleDaoSupport.update(item);
		
		if(request instanceof MultipartHttpServletRequest){//如果request可以强制转换的话
			//設置附件的modelId
	        if(attachments!=null&&attachments.size()>0){
	        	for (TeeAttachment teeAttachment : attachments) {
	        		teeAttachment.setModelId(pubRecordItemId+"");
				}
	        }
		}
		
		
		json.setRtState(true);	
		return json;
	}



	/**
	 * 根据任务发布项的主键  获取发布数据
	 * @param request
	 * @return
	 */
	public TeeJson getRepDataByRecordItemId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取前台页面传来的任务发布记录项的主键
		int taskPubRecordItemId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordItemId"),0);
		TeeTaskPubRecordItem recordItem=(TeeTaskPubRecordItem) simpleDaoSupport.get(TeeTaskPubRecordItem.class,taskPubRecordItemId);
		List  returnList=new ArrayList();
		List<Map> dataList=new ArrayList<Map>();
		List<TeeAttachment> attList=null;
	    List<Map> attachs = new ArrayList<Map>();
		if(recordItem!=null){
			//任务模板
			TeeTaskTemplate taskTemplate=recordItem.getTaskTemplate();
			if(taskTemplate!=null){
				//获取任务模板项列表      
				List<TeeTaskTemplateItem> templateItemList=simpleDaoSupport.executeQuery(" from TeeTaskTemplateItem t where t.taskTemplate.sid=? ", new Object[]{taskTemplate.getSid()});
				//获取数据库中的数据
				Map<Object,Object> dataMap=simpleDaoSupport.executeNativeUnique(" select * from rep_task_data_"+taskTemplate.getSid()+" where record_item_id=? ", new Object[]{taskPubRecordItemId});
				if(templateItemList!=null&&templateItemList.size()>0){
					Map map=null;
					for (TeeTaskTemplateItem teeTaskTemplateItem : templateItemList) {
						map=new HashMap();
						map.put("fieldName", teeTaskTemplateItem.getFieldName());
						if(teeTaskTemplateItem.getFieldType()==4){//日期时间
							SimpleDateFormat sdf=new SimpleDateFormat(teeTaskTemplateItem.getShowType());
							map.put("fieldValue",sdf.format(dataMap.get("DATA_"+teeTaskTemplateItem.getSid())));
						}else{
							map.put("fieldValue",dataMap.get("DATA_"+teeTaskTemplateItem.getSid()));
						}
						
						dataList.add(map);
					}
				}
			}
			
			//获取附件
			attList=attachmentService.getAttaches(TeeAttachmentModelKeys.repTask, String.valueOf(taskPubRecordItemId));
			if(attList!= null && attList.size() > 0){
				 for(int i=0;i<attList.size();i++){
					 TeeAttachment f = (TeeAttachment)attList.get(i);
					 Map map = new HashMap<String, String>();
					 map.put("sid", f.getSid());
					 map.put("priv", 31);
					 map.put("ext", f.getExt());
					 map.put("fileName", f.getFileName());
					 attachs.add(map);
				 }
			 }
		}
		
		returnList.add(dataList);
		returnList.add(attachs);
		
		json.setRtState(true);
		json.setRtData(returnList);
		return json;
	}



	/**
	 * 获取历史汇报列表
	 * @param request
	 * @return
	 */
	public TeeJson getHistoryReportList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
		int taskPubRecordItemId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordItemId"), 0);
		
		TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,taskTemplateId);
		
		SimpleDateFormat sdf=null;
		if(template!=null){
			if(template.getTaskType()==1){//1=日报           
				sdf=new SimpleDateFormat("yyyy年MM月dd日");
			}else if(template.getTaskType()==3){//3=月报
				sdf=new SimpleDateFormat("yyyy年MM月");
			}else if(template.getTaskType()==5){// 5=年报
				sdf=new SimpleDateFormat("yyyy年");
			}else if(template.getTaskType()==6){//  6=一次性
				sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
			}else if(template.getTaskType()==2){//  2=周报  
				sdf=new SimpleDateFormat("yyyy年MM月");
			}else if(template.getTaskType()==4){//  4=季报       
				sdf=new SimpleDateFormat("yyyy年");
			}
		}
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " select t from TeeTaskPubRecordItem  t  left outer join t.dept  d  where t.flag=1 and ( t.user.uuid=? or d.infoReportUser.uuid=? ) and t.sid!=?  and t.taskTemplate.sid=?  ";
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		param.add(loginPerson.getUuid());
		param.add(taskPubRecordItemId);
		param.add(taskTemplateId);
		hql += " order by t.createTime desc";
		
		List<TeeTaskPubRecordItem> list = simpleDaoSupport.executeQuery(hql, param.toArray());

		List<TeeTaskPubRecordItemModel> modelList = new ArrayList<TeeTaskPubRecordItemModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskPubRecordItemModel model = parseToModel(list.get(i));
				
				if(template.getTaskType()==1||template.getTaskType()==3||template.getTaskType()==5||template.getTaskType()==6){
					model.setCreateTimeStr(sdf.format(list.get(i).getCreateTime().getTime()));
				}else if(template.getTaskType()==2){//周报
					String zbDesc=sdf.format(list.get(i).getCreateTime().getTime())+"第"+list.get(i).getCreateTime().get(Calendar.WEEK_OF_MONTH)+"周";
					model.setCreateTimeStr(zbDesc);
				}else if(template.getTaskType()==4){//季报
					String jbDesc=sdf.format(list.get(i).getCreateTime().getTime());
					int month=list.get(i).getCreateTime().get(Calendar.MONTH)+1;
					if(month==1||month==2||month==3){
						jbDesc+="第一季度";
					}else if(month==4||month==5||month==6){
						jbDesc+="第二季度";
					}else if(month==7||month==8||month==9){
						jbDesc+="第三季度";
					}else{
						jbDesc+="第四季度";
					}
					
					model.setCreateTimeStr(jbDesc);
				}
				
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}



	/**
	 * 根据任务发布记录  获取发布数据
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getRepDataListByRecordId(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		//获取任务发布记录主键
		int taskPubRecordId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordId"),0);
		//获取任务模板主键
		int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
		
		//获取需要显示的任务模板项目
		List<TeeTaskTemplateItem> itemList=simpleDaoSupport.executeQuery(" from TeeTaskTemplateItem t where t.taskTemplate.sid=? and t.showAtList=1 ", new Object[]{taskTemplateId});

		String hql = " from rep_task_data_"+taskTemplateId+"  t where t.rep_task_pub_record_id = ? ";
		List param = new ArrayList();
		param.add(taskPubRecordId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countSQLByList("select count(t.record_item_id) "+hql, param));
		hql += " order by t.create_time desc";
		List<Map> list = simpleDaoSupport.executeNativeQuery("select t.* "+hql, param.toArray(), 0, 1000000000);	
		List<Map> footer=new ArrayList<Map>();
		Map footerMap=new HashMap();
		SimpleDateFormat sdf=null;
		if(list!=null&&list.size()>0){
			for (Map map : list) {
				
		        for (TeeTaskTemplateItem item:itemList) {
					if(item.getFieldType()==4){//日期时间做处理
						sdf=new SimpleDateFormat(item.getShowType());
						map.put("DATA_"+item.getSid(),sdf.format(map.get("DATA_"+item.getSid())));
					}
					
				}
		        
		        int userId=TeeStringUtil.getInteger(map.get("CREATE_USER_ID"), 0);
		        TeePerson reportUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);		
			    if(reportUser!=null){
			    	map.put("CREATE_USER_NAME", reportUser.getUserName());
			    	map.put("CREATE_USER_DEPT_NAME", reportUser.getDept().getDeptName());
			    }else{
			    	map.put("CREATE_USER_NAME","");
			    	map.put("CREATE_USER_DEPT_NAME","");
			    }
			}	
		}
		
		j.setRows(list);// 设置返回的行
		
		
		//設置footer
		 for (TeeTaskTemplateItem item:itemList) {
			//进行统计处理
				if(("SUM").equals(item.getCal())){//求和
					Map m=simpleDaoSupport.executeNativeUnique("select sum(DATA_"+item.getSid()+") as SUM1  from rep_task_data_"+taskTemplateId+"  t where t.rep_task_pub_record_id =?  ", new Object[]{taskPubRecordId});
					if(!TeeUtility.isNullorEmpty(m.get("SUM1"))){
						footerMap.put("DATA_"+item.getSid(),"总值:"+new BigDecimal(m.get("SUM1")+"").setScale(2));
					}else{
						footerMap.put("DATA_"+item.getSid(),"总值:"+new BigDecimal(0));
					}
					
					
				}else if(("AVG").equals(item.getCal())){//平均
					Map m=simpleDaoSupport.executeNativeUnique("select avg(DATA_"+item.getSid()+") as AVG1  from rep_task_data_"+taskTemplateId+"  t where t.rep_task_pub_record_id =? ", new Object[]{taskPubRecordId});
					if(!TeeUtility.isNullorEmpty(m.get("AVG1"))){
						footerMap.put("DATA_"+item.getSid(),"平均值:"+new BigDecimal(m.get("AVG1")+"").setScale(2));
					}else{
						footerMap.put("DATA_"+item.getSid(),"平均值:"+new BigDecimal(0));
					}
					
				}else if(("MAX").equals(item.getCal())){//最大
					Map m=simpleDaoSupport.executeNativeUnique("select max(DATA_"+item.getSid()+") as MAX1 from rep_task_data_"+taskTemplateId+"  t where t.rep_task_pub_record_id =?  ", new Object[]{taskPubRecordId});
					
					if(!TeeUtility.isNullorEmpty(m.get("MAX1"))){
						footerMap.put("DATA_"+item.getSid(),"最大值:"+new BigDecimal(m.get("MAX1")+"").setScale(2));
					}else{
						footerMap.put("DATA_"+item.getSid(),"最大值:"+new BigDecimal(0));
					}
				}else if(("MIN").equals(item.getCal())){//最小 
					Map m=simpleDaoSupport.executeNativeUnique("select min(DATA_"+item.getSid()+") as MIN1 from rep_task_data_"+taskTemplateId+"  t where t.rep_task_pub_record_id =?  ", new Object[]{taskPubRecordId});
					if(!TeeUtility.isNullorEmpty(m.get("MIN1"))){
						footerMap.put("DATA_"+item.getSid(),"最小值:"+new BigDecimal(m.get("MIN1")+"").setScale(2));
					}else{
						footerMap.put("DATA_"+item.getSid(),"最小值:"+new BigDecimal(0));
					}
					
				}
		 }
		 footerMap.put("isFooter", 1);
		 footer.add(footerMap);
		 j.setFooter(footer);
		return j;
	}


	/**
	 * 获取历史汇报列表(根据上报人人主键)
	 * @param request
	 * @return
	 */
	public TeeJson getHistoryReportListByUserId(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
		int taskPubRecordItemId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordItemId"), 0);
		
		int createUserId=TeeStringUtil.getInteger(request.getParameter("createUserId"), 0);
		TeeTaskTemplate template=(TeeTaskTemplate) simpleDaoSupport.get(TeeTaskTemplate.class,taskTemplateId);
		
		SimpleDateFormat sdf=null;
		if(template!=null){
			if(template.getTaskType()==1){//1=日报           
				sdf=new SimpleDateFormat("yyyy年MM月dd日");
			}else if(template.getTaskType()==3){//3=月报
				sdf=new SimpleDateFormat("yyyy年MM月");
			}else if(template.getTaskType()==5){// 5=年报
				sdf=new SimpleDateFormat("yyyy年");
			}else if(template.getTaskType()==6){//  6=一次性
				sdf=new SimpleDateFormat("yyyy年MM月dd日  HH时mm分ss秒");
			}else if(template.getTaskType()==2){//  2=周报  
				sdf=new SimpleDateFormat("yyyy年MM月");
			}else if(template.getTaskType()==4){//  4=季报       
				sdf=new SimpleDateFormat("yyyy年");
			}
		}
		
		String hql = " select t from TeeTaskPubRecordItem  t  left outer join t.dept  d  where t.flag=1 and ( t.user.uuid=? or d.infoReportUser.uuid=? ) and t.sid!=?  and t.taskTemplate.sid=?  ";
		List param = new ArrayList();
		param.add(createUserId);
		param.add(createUserId);
		param.add(taskPubRecordItemId);
		param.add(taskTemplateId);
		hql += " order by t.createTime desc";
		
		List<TeeTaskPubRecordItem> list = simpleDaoSupport.executeQuery(hql, param.toArray());

		List<TeeTaskPubRecordItemModel> modelList = new ArrayList<TeeTaskPubRecordItemModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeTaskPubRecordItemModel model = parseToModel(list.get(i));
				
				if(template.getTaskType()==1||template.getTaskType()==3||template.getTaskType()==5||template.getTaskType()==6){
					model.setCreateTimeStr(sdf.format(list.get(i).getCreateTime().getTime()));
				}else if(template.getTaskType()==2){//周报
					String zbDesc=sdf.format(list.get(i).getCreateTime().getTime())+"第"+list.get(i).getCreateTime().get(Calendar.WEEK_OF_MONTH)+"周";
					model.setCreateTimeStr(zbDesc);
				}else if(template.getTaskType()==4){//季报
					String jbDesc=sdf.format(list.get(i).getCreateTime().getTime());
					int month=list.get(i).getCreateTime().get(Calendar.MONTH)+1;
					if(month==1||month==2||month==3){
						jbDesc+="第一季度";
					}else if(month==4||month==5||month==6){
						jbDesc+="第二季度";
					}else if(month==7||month==8||month==9){
						jbDesc+="第三季度";
					}else{
						jbDesc+="第四季度";
					}
					
					model.setCreateTimeStr(jbDesc);
				}
				
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}
 


}
