package com.tianee.oa.subsys.schedule.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.subsys.schedule.bean.TeeSchedule;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleReported;
import com.tianee.oa.subsys.schedule.bean.TeeScheduleShared;
import com.tianee.oa.subsys.schedule.model.TeeScheduleModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeScheduleService extends TeeBaseService{
	
	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public TeeSchedule save(TeeScheduleModel scheduleModel){
		TeeSchedule schedule = Model2Entity(scheduleModel);
		simpleDaoSupport.save(schedule);
		
		Map smsParams = new HashMap();
		
		//给批示人员发送提醒
		List<TeeScheduleReported> reporteds = schedule.getReportedRanges();
		for(TeeScheduleReported reported:reporteds){
			smsParams.clear();
			if(reported.getUser()!=null){
				smsParams.clear();
				smsParams.put("userListIds", reported.getUser().getUuid());
				smsParams.put("moduleNo", "043");
				smsParams.put("content", "新发起的计划["+schedule.getTitle()+"]需要您批示，请及时处理。");
				smsParams.put("remindUrl", "/system/subsys/schedule/manage/smsView.jsp?type=1&scheduleId="+schedule.getUuid()+"&extId="+reported.getUuid());
				//手机端事务提醒
				smsParams.put("remindUrl1", "/system/mobile/phone/schedule/detail.jsp?type=1&scheduleId="+schedule.getUuid()+"&extId="+reported.getUuid());
				smsSender.sendSms(smsParams, schedule.getUser());
			}
		}
		
		//给共享人员发送提醒
		List<TeeScheduleShared> shareds = schedule.getSharedRanges();
		for(TeeScheduleShared shared:shareds){
			smsParams.clear();
			if(shared.getUser()!=null){
				smsParams.clear();
				smsParams.put("userListIds", shared.getUser().getUuid());
				smsParams.put("moduleNo", "043");
				smsParams.put("content", "新发起的计划["+schedule.getTitle()+"]已共享给您，请查看。");
				smsParams.put("remindUrl", "/system/subsys/schedule/manage/smsView.jsp?type=2&scheduleId="+schedule.getUuid()+"&extId="+shared.getUuid());
				smsParams.put("remindUrl1", "/system/mobile/phone/schedule/detail.jsp?type=2&scheduleId="+schedule.getUuid()+"&extId="+shared.getUuid());
				smsSender.sendSms(smsParams, schedule.getUser());
			}
		}
		return schedule;
	}
	
	public TeeSchedule update(TeeScheduleModel scheduleModel){
		TeeSchedule schedule = 
				(TeeSchedule) simpleDaoSupport.get(TeeSchedule.class, scheduleModel.getUuid());
		
		TeeSchedule newSchedule = Model2Entity(scheduleModel);
		
		schedule.setTitle(newSchedule.getTitle());
		schedule.setType(newSchedule.getType());
		schedule.setRangeType(newSchedule.getRangeType());
		schedule.setCrTime(newSchedule.getCrTime());
		schedule.setContent(newSchedule.getContent());
		schedule.getReportedRanges().clear();
		//schedule.getReportedRanges().addAll(newSchedule.getReportedRanges());
		schedule.getSharedRanges().clear();
		//schedule.getSharedRanges().addAll(newSchedule.getSharedRanges());
		schedule.setTime1(newSchedule.getTime1());
		schedule.setTime2(newSchedule.getTime2());
		TeePerson manager = new TeePerson();
		manager.setUuid(scheduleModel.getManagerUserId());
		schedule.setManagerUser(manager);
		simpleDaoSupport.update(schedule);
		
		Map smsParams = new HashMap();
		//给批示人员发送提醒
		List<TeeScheduleReported> reporteds = newSchedule.getReportedRanges();
		for(TeeScheduleReported reported:reporteds){
			simpleDaoSupport.save(reported);
			smsParams.clear();
			if(reported.getUser()!=null){
				smsParams.clear();
				smsParams.put("userListIds", reported.getUser().getUuid());
				smsParams.put("moduleNo", "043");
				smsParams.put("content", "计划["+schedule.getTitle()+"]信息已变更，需要您批示，请及时处理。");
				smsParams.put("remindUrl", "/system/subsys/schedule/manage/smsView.jsp?type=1&scheduleId="+schedule.getUuid()+"&extId="+reported.getUuid());
				smsParams.put("remindUrl1", "/system/mobile/phone/schedule/detail.jsp?type=1&scheduleId="+schedule.getUuid()+"&extId="+reported.getUuid());
				smsSender.sendSms(smsParams, schedule.getUser());
			}
		}
		
		//给共享人员发送提醒
		List<TeeScheduleShared> shareds = newSchedule.getSharedRanges();
		for(TeeScheduleShared shared:shareds){
			simpleDaoSupport.save(shared);
			smsParams.clear();
			if(shared.getUser()!=null){
				smsParams.clear();
				smsParams.put("userListIds", shared.getUser().getUuid());
				smsParams.put("moduleNo", "043");
				smsParams.put("content", "计划["+schedule.getTitle()+"]已变更，请查看。");
				smsParams.put("remindUrl", "/system/subsys/schedule/manage/smsView.jsp?type=2&scheduleId="+schedule.getUuid()+"&extId="+shared.getUuid());
				smsParams.put("remindUrl1", "/system/mobile/phone/schedule/detail.jsp?type=2&scheduleId="+schedule.getUuid()+"&extId="+shared.getUuid());
				smsSender.sendSms(smsParams, schedule.getUser());
			}
		}
		return schedule;
	}
	
	public void delete(String uuid){
		simpleDaoSupport.executeUpdate("delete from TeeRunScheduleRel where schedule.uuid=?", new Object[]{uuid});
		simpleDaoSupport.delete(TeeSchedule.class, uuid);
	}
	
	public void finish(String summerize,String scheduleId){
		TeeSchedule schedule = (TeeSchedule) simpleDaoSupport.get(TeeSchedule.class,scheduleId);
		if(schedule.getTime2().before(Calendar.getInstance())){//设置超时
			schedule.setFlag(2);
		}else{
			schedule.setFlag(1);
		}
		schedule.setFinishTime(Calendar.getInstance());
		schedule.setSummarize(summerize);
		
	}
	
	public Map getPriv(int userId,String scheduleId){
		Map param = new HashMap();
		//判断是否有审批权限
		long shenpi = simpleDaoSupport.count("select count(*) from TeeScheduleReported where schedule.uuid=? and user.uuid=?", new Object[]{scheduleId,userId});
		long pinglun = simpleDaoSupport.count("select count(*) from TeeScheduleShared where schedule.uuid=? and user.uuid=?", new Object[]{scheduleId,userId});
		param.put("shenpi", shenpi!=0);
		param.put("pinglun", pinglun!=0);
		return param;
	}
	
	/**
	 * rangeType = 1：日计划 2：周计划  3：月计划  4：季度计划  5：半年计划  6：年计划
	 * @param rangeType
	 * @param c
	 * @return
	 */
	public static TeeScheduleModel getRangeTypeInfo(int rangeType,Calendar c){
		TeeScheduleModel model = new TeeScheduleModel();
		if(rangeType==1){
			
			String time = TeeDateUtil.format(c.getTime(), "yyyy-MM-dd HH:mm");
			model.setTime1Desc(time.split(" ")[0]+" 00:00");
			model.setTime2Desc(time.split(" ")[0]+" 23:59");
			model.setRangeDesc(time);
			
		}else if(rangeType==2){
			
			//设置周一
			c.set(Calendar.DAY_OF_WEEK, 2);
			model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
			
			//加一周
			c.add(Calendar.WEEK_OF_MONTH, 1);
			c.set(Calendar.DAY_OF_WEEK, 1);
			model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
			
			model.setRangeDesc("周一（"+model.getTime1Desc()+"）至周日（"+model.getTime2Desc()+"）");
			
			
		}else if(rangeType==3){
			
			//设置该月第一天
			c.set(Calendar.DAY_OF_MONTH, 1);
			model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
			
			//设置该月最后一天
			c.add(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
			model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
			
			model.setRangeDesc(model.getTime1Desc()+"至"+model.getTime2Desc()+"");
		}else if(rangeType==4){
			
			int month = c.get(Calendar.MONTH)+1;
			if(1<=month && month<=3){//第一季度
				//设置开始月第一天
				c.set(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
				
				//设置结束月最后一天
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
				model.setRangeDesc("第一季度");
			}else if(4<=month && month<=6){//第二季度
				//设置开始月第一天
				c.set(Calendar.MONTH, 3);
				c.set(Calendar.DAY_OF_MONTH, 1);
				model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
				
				//设置结束月最后一天
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
				model.setRangeDesc("第二季度");
			}else if(7<=month && month<=9){//第三季度
				//设置开始月第一天
				c.set(Calendar.MONTH, 6);
				c.set(Calendar.DAY_OF_MONTH, 1);
				model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
				
				//设置结束月最后一天
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
				model.setRangeDesc("第三季度");
			}else{//第四季度
				//设置开始月第一天
				c.set(Calendar.MONTH, 9);
				c.set(Calendar.DAY_OF_MONTH, 1);
				model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
				
				//设置结束月最后一天
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
				model.setRangeDesc("第四季度");
			}
			
		}else if(rangeType==5){
			int month = c.get(Calendar.MONTH)+1;
			if(1<=month && month<=6){
				//设置开始月第一天
				c.set(Calendar.MONTH, 0);
				c.set(Calendar.DAY_OF_MONTH, 1);
				model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
				
				//设置结束月最后一天
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
				model.setRangeDesc("上半年");
			}else{
				//设置开始月第一天
				c.set(Calendar.MONTH, 6);
				c.set(Calendar.DAY_OF_MONTH, 1);
				model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
				
				//设置结束月最后一天
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
				model.setRangeDesc("下半年");
			}
		}else if(rangeType==6){
			//设置开始月第一天
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
			model.setTime1Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 00:00");
			
			//设置结束月最后一天
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
			model.setTime2Desc(TeeDateUtil.format(c.getTime(), "yyyy-MM-dd")+" 23:59");
			model.setRangeDesc(TeeDateUtil.format(c.getTime(), "yyyy")+"年");
		}
		return model;
	}
	
	public TeeScheduleModel get(String uuid){
		TeeSchedule schedule = (TeeSchedule) simpleDaoSupport.get(TeeSchedule.class, uuid);
		return Entity2Model(schedule);
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("from TeeSchedule where 1=1 ");
		int type = TeeStringUtil.getInteger(requestData.get("type"), 0);
		List params = new ArrayList();
		if(type>0){
			sb.append(" and type="+type);
		}
		sb.append(" and (user.uuid="+loginUser.getUuid()+" or managerUser.uuid="+loginUser.getUuid()+")");
		
		
		int rangeType = TeeStringUtil.getInteger(requestData.get("rangeType"), -1);
		if(rangeType!=-1){
			sb.append(" and rangeType="+rangeType);
		}
		
		String title = TeeStringUtil.getString(requestData.get("title"));
		if(!"".equals(title)){
			sb.append(" and title like ?");
			params.add("%"+title+"%");
		}
		
		String crTime1 = TeeStringUtil.getString(requestData.get("crTime1"));
		String crTime2 = TeeStringUtil.getString(requestData.get("crTime2"));
		if(!"".equals(crTime1)){
			sb.append(" and crTime >= ?");
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd", crTime1));
		}
		if(!"".equals(crTime2)){
			sb.append(" and crTime <= ?");
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd", crTime2));
		}
		
		int flag = TeeStringUtil.getInteger(requestData.get("flag"), -1);
		if(flag!=-1){
			sb.append(" and flag = "+flag);
		}
		
		String order = null;
		if(dm.getSort()!=null){
			order = " order by "+dm.getSort()+" "+dm.getOrder();
		}else{
			order = " order by crTime desc";
		}
		
		List<TeeSchedule> list = simpleDaoSupport.pageFind(sb.toString()+order, dm.getFirstResult(), dm.getRows(), params.toArray());
		List<TeeScheduleModel> modelList = new ArrayList();
		
		long total = simpleDaoSupport.count("select count(*) "+sb.toString(), params.toArray());
		for(TeeSchedule schedule:list){
			modelList.add(Entity2Model(schedule));
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		
		return dataGridJson;
	}
	
	public static TeeScheduleModel Entity2Model(TeeSchedule schedule){
		TeeScheduleModel model = new TeeScheduleModel();
		BeanUtils.copyProperties(schedule, model);
		
		TeeScheduleModel m = getRangeTypeInfo(schedule.getRangeType(),(Calendar)schedule.getCrTime().clone());
		model.setRangeDesc(m.getRangeDesc());
		model.setTime1Desc(m.getTime1Desc());
		model.setTime2Desc(m.getTime2Desc());
		
		model.setCrTimeDesc(TeeDateUtil.format(schedule.getCrTime().getTime(), "yyyy-MM-dd"));
		
		if(schedule.getDept()!=null){
			model.setDeptId(schedule.getDept().getUuid());
			model.setDeptName(schedule.getDept().getDeptName());
		}
		
		if(schedule.getUser()!=null){
			model.setUserId(schedule.getUser().getUuid());
			model.setUserName(schedule.getUser().getUserName());
		}
		
		if(schedule.getManagerUser()!=null){
			model.setManagerUserId(schedule.getManagerUser().getUuid());
			model.setManagerUserName(schedule.getManagerUser().getUserName());
		}
		
		
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		List<TeeScheduleReported> reportedList = schedule.getReportedRanges();
		TeePerson p = null;
		for(int i=0;i<reportedList.size();i++){
			p = reportedList.get(i).getUser();
			if(p!=null){
				ids.append(p.getUuid()+"");
				names.append(p.getUserName());
				if(i!=reportedList.size()-1){
					ids.append(",");
					names.append(",");
				}
			}
		}
		model.setReportedRangesIds(ids.toString());
		model.setReportedRangesNames(names.toString());
		
		ids.delete(0, ids.length());
		names.delete(0, names.length());
		
		List<TeeScheduleShared> sharedList = schedule.getSharedRanges();
		for(int i=0;i<sharedList.size();i++){
			p = sharedList.get(i).getUser();
			if(p!=null){
				ids.append(p.getUuid()+"");
				names.append(p.getUserName());
				if(i!=sharedList.size()-1){
					ids.append(",");
					names.append(",");
				}
			}
		}
		model.setSharedRangesIds(ids.toString());
		model.setSharedRangesNames(names.toString());
		
		return model;
	}
	
	public static TeeSchedule Model2Entity(TeeScheduleModel scheduleModel){
		TeeSchedule schedule = new TeeSchedule();
		BeanUtils.copyProperties(scheduleModel, schedule);
		
		schedule.setCrTime(TeeDateUtil.parseCalendar("yyyy-MM-dd", scheduleModel.getCrTimeDesc()));
		schedule.setTime1(TeeDateUtil.parseCalendar(scheduleModel.getTime1Desc()));
		schedule.setTime2(TeeDateUtil.parseCalendar(scheduleModel.getTime2Desc()));
		TeePerson p = new TeePerson();
		p.setUuid(scheduleModel.getUserId());
		schedule.setUser(p);
		
		//负责人
		TeePerson manager = new TeePerson();
		manager.setUuid(scheduleModel.getManagerUserId());
		schedule.setManagerUser(manager);
		
		//汇报人
		String sp[] = scheduleModel.getReportedRangesIds().split(",");
		TeePerson tmp = null;
		TeeScheduleReported reported = null;
		
		for(String uuid:sp){
			if("".equals(uuid)){
				continue;
			}
			reported = new TeeScheduleReported();
			tmp = new TeePerson();
			tmp.setUuid(Integer.parseInt(uuid));
			
			reported.setSchedule(schedule);
			reported.setUser(tmp);
			
			schedule.getReportedRanges().add(reported);
		}
		
		//分享人
		TeeScheduleShared scheduleShared = null;
		sp = scheduleModel.getSharedRangesIds().split(",");
		for(String uuid:sp){
			if("".equals(uuid)){
				continue;
			}
			scheduleShared = new TeeScheduleShared();
			tmp = new TeePerson();
			tmp.setUuid(Integer.parseInt(uuid));
			
			scheduleShared.setSchedule(schedule);
			scheduleShared.setUser(tmp);
			
			schedule.getSharedRanges().add(scheduleShared);
		}
		
		TeeDepartment dept = new TeeDepartment();
		dept.setUuid(scheduleModel.getDeptId());
		schedule.setDept(dept);
		
		return schedule;
	}
	
	
	
	public List<TeeScheduleModel> getMySchedules(Map requestData){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		StringBuffer sb = new StringBuffer("from TeeSchedule where 1=1 ");
		List params = new ArrayList();
		
		sb.append(" and (user.uuid="+loginUser.getUuid()+" or managerUser.uuid="+loginUser.getUuid()+")");
		
		int rangeType = TeeStringUtil.getInteger(requestData.get("rangeType"), -1);
		if(rangeType!=-1){
			sb.append(" and rangeType="+rangeType);
		}
		
		String title = TeeStringUtil.getString(requestData.get("title"));
		if(!"".equals(title)){
			sb.append(" and title like ?");
			params.add("%"+title+"%");
		}
		
		String crTime1 = TeeStringUtil.getString(requestData.get("crTime1"));
		String crTime2 = TeeStringUtil.getString(requestData.get("crTime2"));
		if(!"".equals(crTime1)){
			sb.append(" and crTime >= ?");
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd", crTime1));
		}
		if(!"".equals(crTime2)){
			sb.append(" and crTime <= ?");
			params.add(TeeDateUtil.parseCalendar("yyyy-MM-dd", crTime2));
		}
		
		int flag = TeeStringUtil.getInteger(requestData.get("flag"), -1);
		if(flag!=-1){
			sb.append(" and flag = "+flag);
		}
		
		String order = null;
		
		order = " order by crTime desc";
		
		List<TeeSchedule> list = simpleDaoSupport.executeQueryByList(sb.toString()+order, params);
		List<TeeScheduleModel> modelList = new ArrayList();
		
		for(TeeSchedule schedule:list){
			modelList.add(Entity2Model(schedule));
		}
		return modelList;
	}
}
