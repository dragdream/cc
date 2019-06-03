package com.tianee.oa.core.workflow.plugins;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.tianee.oa.core.base.attend.bean.TeeAttendOut;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;

public class AttendOutPlugin extends TeeWfPlugin{

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
			String submitTimeStr=this.flowRunDatas.get(formItemIdentities.get("外出日期"));
			long submitTime=new SimpleDateFormat("yyyy-MM-dd").parse(submitTimeStr).getTime();
			
			
			//开始时间//结束时间
			String[] startTimeArr  = this.flowRunDatas.get(formItemIdentities.get("外出开始时间")).split(":");
			String[] endTimeArr = this.flowRunDatas.get(formItemIdentities.get("外出结束时间")).split(":");
			Calendar time = Calendar.getInstance();
			time.setTimeInMillis(submitTime);
			time.set(Calendar.HOUR_OF_DAY, TeeStringUtil.getInteger(startTimeArr[0] , 0));
			time.set(Calendar.MINUTE, TeeStringUtil.getInteger(startTimeArr[1] , 0));
			long startTime = time.getTimeInMillis();
			time.set(Calendar.HOUR_OF_DAY, TeeStringUtil.getInteger(endTimeArr[0] , 0));
			time.set(Calendar.MINUTE, TeeStringUtil.getInteger(endTimeArr[1] , 0));
			long endTime = time.getTimeInMillis();
			
			
			//外出天数
			TeeAttendDutyService attendDutyService = (TeeAttendDutyService) TeeBeanFactory.getBean("teeAttendDutyService");
			String startDesc=this.flowRunDatas.get(formItemIdentities.get("外出日期"))+" "+this.flowRunDatas.get(formItemIdentities.get("外出开始时间"));
			String endDesc=this.flowRunDatas.get(formItemIdentities.get("外出日期"))+" "+this.flowRunDatas.get(formItemIdentities.get("外出结束时间"));
			TeeJson json=attendDutyService.getTimeDiffByDutyConfig(startDesc, endDesc, user);
			double days=(Double) json.getRtData();
			//外出原因
			String outDesc=this.flowRunDatas.get(formItemIdentities.get("外出事由"));
			
			//审批状态  1=已批准    
			//办理状态  0=外出未回

			//插入到attendout表中
			TeeAttendOut out=new TeeAttendOut();
			out.setAllow(1);
			out.setStatus(0);
			out.setCreateTime(createTime);
			
			BigDecimal decimal = new BigDecimal(days);
			out.setDays(decimal);
			
			out.setEndTime(endTime);
			out.setStartTime(startTime);
			out.setOutDesc(outDesc);
			out.setSubmitTime(submitTime);
			out.setUser(user);
			
			simpleDaoSupport.save(out);
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
