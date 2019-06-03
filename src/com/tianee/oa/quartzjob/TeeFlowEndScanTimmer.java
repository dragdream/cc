package com.tianee.oa.quartzjob;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeFlowEndScanTimmer extends TeeBaseService{
	
	public void doTimmer(){
		//获取所有未结束的流程
		List<Integer> runIdList = simpleDaoSupport.find("select runId from TeeFlowRun where delFlag=0 and isSave=1 and endTime is null", null);
		for(int runId:runIdList){
			long count = simpleDaoSupport.count("select count(*) from TeeFlowRunPrcs where flowRun.runId="+runId+" and flag in (0,1,2)", null);
			if(count==0){
				simpleDaoSupport.executeUpdate("update TeeFlowRun set endTime=? where runId=?", new Object[]{Calendar.getInstance(),runId});
			}
		}
	}
}
