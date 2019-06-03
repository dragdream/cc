package com.tianee.oa.core.workflow.plugins;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import com.tianee.oa.core.base.attend.bean.TeeAttendEvection;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeBeanFactory;

public class AttendEvectionPlugin extends TeeWfPlugin{

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
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long beginTime=sdf.parse(this.flowRunDatas.get(formItemIdentities.get("出差开始日期"))+" 00:00:00").getTime();
			long endTime=sdf.parse(this.flowRunDatas.get(formItemIdentities.get("出差结束日期"))+" 23:59:59").getTime();
			
			//出差天数
			TeeAttendDutyService attendDutyService = (TeeAttendDutyService) TeeBeanFactory.getBean("teeAttendDutyService");
			String startDesc=this.flowRunDatas.get(formItemIdentities.get("出差开始日期"))+" 00:00:00";
			String endDesc=this.flowRunDatas.get(formItemIdentities.get("出差结束日期"))+" 23:59:59";
			TeeJson json=attendDutyService.getTimeDiffByDutyConfig(startDesc, endDesc, user);
			double days=(Double) json.getRtData();
			//出差原因
			String evectionDesc=this.flowRunDatas.get(formItemIdentities.get("出差事由"));
			
			//出差地址
			String address=this.flowRunDatas.get(formItemIdentities.get("出差地址"));
			//审批状态  1=已批准    
			//办理状态  0=外出未回

			//插入到attendEvection表中
			TeeAttendEvection eve=new TeeAttendEvection();
			eve.setAllow(1);
			eve.setStatus(0);
			eve.setCreateTime(createTime);
			
			BigDecimal decimal = new BigDecimal(days);
			eve.setDays(decimal);
			eve.setEvectionAddress(address);
			eve.setEndTime(endTime);
			eve.setStartTime(beginTime);
			eve.setEvectionDesc(evectionDesc);
			eve.setUser(user);
			
			simpleDaoSupport.save(eve);
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
