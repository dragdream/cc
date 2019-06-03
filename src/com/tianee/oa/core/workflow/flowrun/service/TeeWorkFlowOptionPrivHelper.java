package com.tianee.oa.core.workflow.flowrun.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.oaconst.TeeWorkFlowConst;
import com.tianee.webframe.service.TeeBaseService;

@Service
public  class TeeWorkFlowOptionPrivHelper extends TeeBaseService implements TeeWorkFlowOptionPrivHelperInterface{

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelperInterface#checkPrivTurnNext(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public int checkPrivTurnNext(TeeFlowRunPrcs flowRunPrcs){
		if(flowRunPrcs == null){
			return 0;
		}
		/**
		 * 判断流程是否可办理
		 */
		if((flowRunPrcs.getFlag()!=1 && flowRunPrcs.getFlag()!=2) || flowRunPrcs.getEndTime() != null){
			return 0;
		};
		/**
		 * 判断是否为主办人
		 */
		if(flowRunPrcs.getTopFlag() == 1){
			return 1;
		}else{
			return 2;
		}
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelperInterface#checkPrivTurnBack(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType)
	 */
	@Override
	public int checkPrivTurnBack(TeeFlowType flowType){
		if(flowType == null ){
			return 0;
		}
		/**
		 * 如果是自有流程 则任何步骤都允许回退
		 */
		if(flowType.getType() == 2){
			return 0;
		}
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelperInterface#checkPrivTurnBack(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public int checkPrivTurnBack(TeeFlowRunPrcs flowRunPrcs){
		TeeFlowType flowType = flowRunPrcs.getFlowRun().getFlowType();
		if(flowType == null || flowRunPrcs.getTopFlag()==0){//如果是经办人或者流程为空，则不允许回退
			return 0;
		}
		/**
		 * 自由流程暂时先不允许回退
		 */
		if(flowType.getType() == 2){
			return 0;
		}else if(flowType.getType()==1){//固定流程
			TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
			if(fp.getGoBack()!=0 && flowRunPrcs.getPrcsId()!=1){//如果允许回退则显示回退按钮
				return fp.getGoBack();
			}
		}
		return 0;
		
		
		
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelperInterface#checkPrivTurnend(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public int checkPrivTurnend(TeeFlowRunPrcs flowRunPrcs){
		TeeFlowType flowType = flowRunPrcs.getFlowRun().getFlowType();
		if(flowType == null ){
			return 0;
		}
		/**
		 * 判断自由流程是否存在预设步骤，如果存在预设步骤，则不允许结束
		 * 经办人也不允许结束
		 */
		if(flowType.getType() == 2){
			String hql = "select count(*) from TeeFlowRunPrcs frp where frp.flowRun.runId="+flowRunPrcs.getFlowRun().getRunId()+" and frp.prcsId>"+flowRunPrcs.getPrcsId();
			long count = simpleDaoSupport.count(hql, null);
//			System.out.println(count);
			if(count>0){
				return 0;
			}
			if(flowRunPrcs.getTopFlag()==0){
				return 0;
			}
			return 1;
		}
		return 0;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkFlowOptionPrivHelperInterface#initworkHandlerOptionPriv(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public Map initworkHandlerOptionPriv(TeeFlowRunPrcs flowRunPrcs){
		
		Map m = new HashMap<String, String>();
		
		int  turnState = checkPrivTurnNext(flowRunPrcs);
		int turnBackState = checkPrivTurnBack(flowRunPrcs);
		int turnEndState = checkPrivTurnend(flowRunPrcs);
		m.put(TeeWorkFlowConst.TURN_STATE, turnState);
		m.put(TeeWorkFlowConst.TURN_BACK, turnBackState);
		m.put(TeeWorkFlowConst.TURN_END, turnEndState);
		return m;
	}
	
}
