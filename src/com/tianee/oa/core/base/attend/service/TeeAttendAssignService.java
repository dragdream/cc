package com.tianee.oa.core.base.attend.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.attend.bean.TeeAttendAssign;
import com.tianee.oa.core.base.attend.model.TeeAttendAssignModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeAttendAssignService extends TeeBaseService{

	
	/**
	 * 添加外勤签到信息
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson add(HttpServletRequest request, TeeAttendAssignModel model) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeAttendAssign assign=new TeeAttendAssign();
		assign.setAddress(model.getAddress());
		assign.setAddrPoint(model.getAddrPoint());
		assign.setAttachIds(model.getAttachIds());
		assign.setCreateTime(Calendar.getInstance());
		assign.setRemark(model.getRemark());
		assign.setUser(loginUser);
		
		simpleDaoSupport.save(assign);
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 实体类转换成model
	 * @param assign
	 * @return
	 */
	private TeeAttendAssignModel parseToModel(TeeAttendAssign assign) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeAttendAssignModel model=new TeeAttendAssignModel();
		BeanUtils.copyProperties(assign, model);
		if(assign.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(assign.getCreateTime().getTime()));
		}
		
		if(assign.getUser()!=null){
			model.setUserId(assign.getUser().getUuid());
			model.setUserName(assign.getUser().getUserName());
		}
		
		return model;
	}



	
	/**
	 * 根据时间范围   获取列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getListByStatus(HttpServletRequest request,
			TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);
		String startTimeStr=TeeStringUtil.getString(request.getParameter("startTimeStr"));
		String endTimeStr=TeeStringUtil.getString(request.getParameter("endTimeStr"));
		
		int personId=TeeStringUtil.getInteger(request.getParameter("personId"),0);
		TeePerson loginUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,personId);
		if(loginUser==null){
			loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		}
		
		
		Calendar startTime=null;
		Calendar endTime=null;
		Calendar nowTime=Calendar.getInstance();
		//根据状态   获取时间段
		if(status==1){//今日
			startTime=(Calendar) nowTime.clone();
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			
			endTime=(Calendar) nowTime.clone();
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
			
		}else if(status==2){//昨日
			startTime=(Calendar) nowTime.clone();
			startTime.add(Calendar.DATE, -1);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			
			endTime=(Calendar) nowTime.clone();
			endTime.add(Calendar.DATE, -1);
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}else if(status==3){//本周
			//先获取今天是周几
			int week=nowTime.get(Calendar.DAY_OF_WEEK);
			if(week==1){
				week=7;
			}else{
				week=week-1;
			}
			
			startTime=(Calendar) nowTime.clone();
			startTime.add(Calendar.DATE, -(week-1));
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			
			endTime=(Calendar) nowTime.clone();
			endTime.add(Calendar.DATE, (7-week));
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}else if(status==4){//本月
			//获取当前的月数
			int month=nowTime.get(Calendar.MONTH);
			//获取当前月的最大天数
			int maxDays=nowTime.getActualMaximum(Calendar.DATE);
			
			startTime=(Calendar) nowTime.clone();
			startTime.set(Calendar.DAY_OF_MONTH,1);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			
			endTime=(Calendar) nowTime.clone();
			endTime.set(Calendar.DAY_OF_MONTH,maxDays);
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);
		}else if(status==5){//指定时间范围
			startTime=TeeDateUtil.parseCalendar(startTimeStr);
			startTime.set(Calendar.HOUR_OF_DAY, 0);
			startTime.set(Calendar.MINUTE, 0);
			startTime.set(Calendar.SECOND, 0);
			
			endTime=TeeDateUtil.parseCalendar(endTimeStr);
			endTime.set(Calendar.HOUR_OF_DAY, 23);
			endTime.set(Calendar.MINUTE, 59);
			endTime.set(Calendar.SECOND, 59);	
		}
		//System.out.println(TeeDateUtil.format(startTime.getTime(), "yyyy-MM-dd HH:mm:ss")+"~~"+TeeDateUtil.format(endTime.getTime(), "yyyy-MM-dd HH:mm:ss"));
		
		List list=new ArrayList();
		String hql=" from TeeAttendAssign where user.uuid=?  and   (createTime >= ?  and createTime <= ?) ";
		list.add(loginUser.getUuid());
		list.add(startTime);
		list.add(endTime);
		long total=simpleDaoSupport.count(" select count(*) "+hql, list.toArray());
		json.setTotal(total);
		List<TeeAttendAssign> resultList=simpleDaoSupport.pageFind(hql,(dm.getPage() - 1) * dm.getRows(),  dm.getRows(), list.toArray());
		List<TeeAttendAssignModel> rows=new ArrayList<TeeAttendAssignModel>();
		TeeAttendAssignModel model=null;
		if(resultList!=null&&resultList.size()>0){
			for (TeeAttendAssign teeAttendAssign : resultList) {
				model=parseToModel(teeAttendAssign);
				rows.add(model);
			}
		}
		json.setRows(rows);
		return json;
	}



	/**
	 * 获取详情
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request,
			TeeAttendAssignModel model) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeAttendAssign assign=(TeeAttendAssign) simpleDaoSupport.get(TeeAttendAssign.class,sid);
		if(assign!=null){
			TeeAttendAssignModel  m=parseToModel(assign);
			json.setRtData(m);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}


}
