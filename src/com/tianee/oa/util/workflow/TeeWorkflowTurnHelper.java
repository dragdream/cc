package com.tianee.oa.util.workflow;

import java.util.Calendar;
import java.util.List;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;

public class TeeWorkflowTurnHelper {

	private TeeWorkFlowServiceContextInterface flowServiceContext;
	
	/**
	 * 插入下一步骤 主办人
	 * @param flowPrcs
	 * @param person
	 * @return
	 */
	public boolean insertNextPrcsOpPerson(TeeFlowRunPrcs oldflowRunPrcs,TeePerson person){
		if(oldflowRunPrcs == null){
			return false;
		}
		TeeFlowRun flowRun = null;
				   flowRun = oldflowRunPrcs.getFlowRun();
		
		TeeFlowRunPrcs nextPrcs = new TeeFlowRunPrcs();
		nextPrcs.setCreateTime(Calendar.getInstance());
		nextPrcs.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
		nextPrcs.setDelFlag(0);
		nextPrcs.setFlowPrcs(null);
		nextPrcs.setFlowRun(flowRun);
		//flowRunPrcs.setFromUser(null);
		nextPrcs.setPrcsId(1);
		nextPrcs.setOpFlag(1);
		nextPrcs.setTopFlag(0);
		nextPrcs.setPrcsUser(person);
		nextPrcs.setFlag(1);
		flowServiceContext.getFlowRunPrcsService().save(nextPrcs);
		return true;
	}
	/**
	 * 插入下一步骤 经办人
	 * @param flowPrcs
	 * @param person
	 * @return
	 */
	public boolean insertNextPrcsPerson(TeeFlowRunPrcs oldflowRunPrcs,List<TeePerson> personList){
		
		if(oldflowRunPrcs == null){
			return false;
		}
		TeeFlowRun flowRun = null;
		   flowRun = oldflowRunPrcs.getFlowRun();
		   int prcsId = 0;
		   prcsId =   oldflowRunPrcs.getPrcsId();
		   prcsId = prcsId + 1;
		   
		for(int i=0;i<personList.size();i++){
			TeePerson p = (TeePerson)personList.get(i);
			TeeFlowRunPrcs nextPrcs = new TeeFlowRunPrcs();
			nextPrcs.setCreateTime(Calendar.getInstance());
			nextPrcs.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
			nextPrcs.setDelFlag(0);
			nextPrcs.setFlowPrcs(null);
			nextPrcs.setFlowRun(flowRun);
			//flowRunPrcs.setFromUser(null);
			nextPrcs.setPrcsId(prcsId);
			nextPrcs.setOpFlag(1);
			nextPrcs.setTopFlag(0);
			nextPrcs.setPrcsUser(p);
			nextPrcs.setFlag(0);
			flowServiceContext.getFlowRunPrcsService().save(nextPrcs);
		}
		return true;
	}
	

	public TeeWorkFlowServiceContextInterface getFlowServiceContext() {
		return flowServiceContext;
	}


	public void setFlowServiceContext(TeeWorkFlowServiceContextInterface flowServiceContext) {
		this.flowServiceContext = flowServiceContext;
	}
	
	
}
