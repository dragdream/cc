package com.tianee.oa.core.workflow.flowrun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workFlowFrame.dataloader.TeeFixedFlowDataLoaderInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeWorkflowRunApiService extends TeeBaseService{
	
	@Autowired
	private TeeFixedFlowDataLoaderInterface dataLoaderInterface;
	
	/**
	 * 通过任务ID，获取下一步骤的信息
	 * @param frpSid
	 * @return
	 */
	public List getNextProcessList(int frpSid){
		
		Map requestData = new HashMap();
		
		TeeFlowRunPrcs flowRunPrcs = 
				(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class, frpSid);
		
		requestData.put("frpSid", frpSid);
		requestData.put("runId", flowRunPrcs.getFlowRun().getRunId());
		requestData.put("flowId", flowRunPrcs.getFlowType().getSid());
		
		Map data = dataLoaderInterface.getTurnHandlerData(requestData, flowRunPrcs.getPrcsUser());
		
		List processList = new ArrayList();
		
		List<Map> prcsNodeInfos = (List<Map>) data.get("prcsNodeInfos");
//		List<Integer> allNextPrcsNodes = (List<Integer>) data.get("allNextPrcsNodes");
//		List<Integer> diabledChildFlowNodes = (List<Integer>) data.get("diabledChildFlowNodes");
//		List<Integer> parallelPrcsNodes = (List<Integer>) data.get("parallelPrcsNodes");
//		List<Integer> disabledPrcsNodes = (List<Integer>) data.get("disabledPrcsNodes");
//		List<Map> childFlowNodeInfos = (List<Map>) data.get("prcsNodeInfos");
		
		
		for(Map prcsInfo:prcsNodeInfos){
			Map info = new HashMap();
			info.put("prcsId", prcsInfo.get("prcsId"));
			info.put("prcsName", prcsInfo.get("prcsName"));
			info.put("opFlag", prcsInfo.get("opFlag"));
			info.put("opUser", prcsInfo.get("opUser"));
			info.put("opUserDesc", prcsInfo.get("opUserDesc"));
			info.put("prcsUser", prcsInfo.get("prcsUser"));
			info.put("prcsUserDesc", prcsInfo.get("prcsUserDesc"));
			
			processList.add(info);
		}
		
		return processList;
	}
	
}
