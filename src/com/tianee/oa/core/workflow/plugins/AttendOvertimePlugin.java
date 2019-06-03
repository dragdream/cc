package com.tianee.oa.core.workflow.plugins;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;




import com.tianee.oa.core.base.attend.bean.TeeAttendOvertime;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.util.str.TeeStringUtil;

public class AttendOvertimePlugin extends TeeWfPlugin{

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
			
			//日期
			String submitTimeStr=this.flowRunDatas.get(formItemIdentities.get("加班日期"));
			long submitTime=new SimpleDateFormat("yyyy-MM-dd").parse(submitTimeStr).getTime();
			
			
			//开始时间//结束时间
			String[] startTimeArr  = this.flowRunDatas.get(formItemIdentities.get("加班开始时间")).split(":");
			String[] endTimeArr = this.flowRunDatas.get(formItemIdentities.get("加班结束时间")).split(":");
			Calendar time = Calendar.getInstance();
			time.setTimeInMillis(submitTime);
			time.set(Calendar.HOUR_OF_DAY, TeeStringUtil.getInteger(startTimeArr[0] , 0));
			time.set(Calendar.MINUTE, TeeStringUtil.getInteger(startTimeArr[1] , 0));
			long startTime = time.getTimeInMillis();
			time.set(Calendar.HOUR_OF_DAY, TeeStringUtil.getInteger(endTimeArr[0] , 0));
			time.set(Calendar.MINUTE, TeeStringUtil.getInteger(endTimeArr[1] , 0));
			long endTime = time.getTimeInMillis();
			
			
			//加班时长
			
			
			//外出原因
			String overTimeDesc=this.flowRunDatas.get(formItemIdentities.get("加班事由"));
			
			
			//加班时长
			double overHours=(endTime-startTime)/1000.00/60.00/60.00*1.00;
			BigDecimal decimal=new BigDecimal(overHours).setScale(2,RoundingMode.HALF_UP);
			
			//审批状态  1=已批准    
			//办理状态  0=外出未回

			//插入到TeeAttendOvertime表中
			TeeAttendOvertime overtime=new TeeAttendOvertime();
			overtime.setAllow(1);
			overtime.setStatus(1);
			overtime.setCreateTime(createTime);
			
			overtime.setEndTime(endTime);
			overtime.setStartTime(startTime);
			overtime.setOvertimeDesc(overTimeDesc);
			overtime.setSubmitTime(submitTime);
			overtime.setUser(user);
			overtime.setOverHours(decimal);
			simpleDaoSupport.save(overtime);
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
