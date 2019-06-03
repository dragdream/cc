package com.tianee.oa.core.workflow.plugins;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.tianee.oa.core.base.attend.bean.TeeAttendLeave;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeBeanFactory;

public class AttendLeavePlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		
		return null;
	}

	@Override
	public void afterTurnnext(){
		try {
			//获取流程发起人ID
			int beginUserId = this.flowRunProxy.getBeginUserUuid();
			//流程发起人
			TeePerson user=(TeePerson) simpleDaoSupport.get(TeePerson.class,beginUserId);
			//获取新建时间
			long createTime=Calendar.getInstance().getTimeInMillis();
			
			
			
			//开始时间//结束时间
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
			long beginTime=sdf.parse(this.flowRunDatas.get(formItemIdentities.get("请假开始日期"))).getTime();
			long endTime=sdf.parse(this.flowRunDatas.get(formItemIdentities.get("请假结束日期"))).getTime();
			
			//请假天数
			TeeAttendDutyService attendDutyService = (TeeAttendDutyService) TeeBeanFactory.getBean("teeAttendDutyService");
			String startDesc=this.flowRunDatas.get(formItemIdentities.get("请假开始日期"));
			String endDesc=this.flowRunDatas.get(formItemIdentities.get("请假结束日期"));
			TeeJson json=attendDutyService.getTimeDiffByDutyConfig(startDesc, endDesc, user);
			double days=(Double) json.getRtData();
			//请假原因
			String leaveDesc=this.flowRunDatas.get(formItemIdentities.get("请假事由"));
			
			//审批状态  1=已批准  Allow  
			//办理状态  0=未销假

			//请假类型
			int leaveType=0;
			String leaveTypeDesc=this.flowRunDatas.get(formItemIdentities.get("请假类别"));
			if("事假".equals(leaveTypeDesc)){
				leaveType=1;
			}else if("病假".equals(leaveTypeDesc)){
				leaveType=2;
			}else if("年假".equals(leaveTypeDesc)){
				leaveType=3;
			}else if("工伤假".equals(leaveTypeDesc)){
				leaveType=5;
			}else if("婚假".equals(leaveTypeDesc)){
				leaveType=6;
			}else if("丧假".equals(leaveTypeDesc)){
				leaveType=7;
			}else if("产假".equals(leaveTypeDesc)){
				leaveType=8;
			}else if("探亲假".equals(leaveTypeDesc)){
				leaveType=9;
			}else if("公假".equals(leaveTypeDesc)){
				leaveType=10;
			}else if("其他".equals(leaveTypeDesc)){
				leaveType=4;
			}
			/**
			 * <option value="1">事假</option>
			<option value="2">病假</option>
			<option value="3">年假</option>
			<option value="5">工伤假</option>
			<option value="6">婚假</option>
			<option value="7">丧假</option>
			<option value="8">产假</option>
			<option value="9">探亲假</option>
			<option value="10">公假</option>
			<option value="4">其他</option>
 */
			
			//插入到attendout表中
			TeeAttendLeave leave=new TeeAttendLeave();
			leave.setAllow(1);
			leave.setStatus(0);
			leave.setCreateTime(createTime);
			
			BigDecimal decimal = new BigDecimal(days);
			leave.setAnnualLeave(decimal);
			
			leave.setEndTime(endTime);
			leave.setStartTime(beginTime);
			leave.setLeaveDesc(leaveDesc);
            leave.setLeaveType(leaveType);
			leave.setUser(user);
			
			simpleDaoSupport.save(leave);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public TeeJsonProxy beforeSave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterSave() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preTurnNextFilter(Map preTurnNextData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String afterRendered() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void goBack(String prcsName, int prcsId, String content) {
		// TODO Auto-generated method stub
		
	}

}
