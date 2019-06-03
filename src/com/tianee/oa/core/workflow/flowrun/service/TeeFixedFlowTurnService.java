package com.tianee.oa.core.workflow.flowrun.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 固定流程转交Service
 * 用于固定流程转交与转交前业务逻辑
 * @author kakalion
 *
 */
@Service
public class TeeFixedFlowTurnService extends TeeBaseService implements TeeFixedFlowTurnServiceInterface{
	
	@Autowired
	private TeeWorkFlowServiceContextInterface context;
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#turnEnd(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void turnEnd(Map requestData,TeePerson loginPerson){
		int prcsId = TeeStringUtil.getInteger(requestData.get("prcsId"), 0);
		int flowPrcs = TeeStringUtil.getInteger(requestData.get("flowPrcs"), 0);
		int runId = TeeStringUtil.getInteger(requestData.get("runId"), 0);
		
		//更新当前办理人节点为已办结
		simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs frp set frp.flag=4 " +
				"where frp.flowRun.runId=? and frp.prcsId=? and (frp.flowPrcs.sid=? or frp.flowPrcs is null) and frp.prcsUser.uuid=?", 
				new Object[]{runId,prcsId,flowPrcs,loginPerson.getUuid()});
		
		//更新流程实例为已办结
		simpleDaoSupport.executeUpdate("update TeeFlowRun fr set fr.endTime=? where fr.runId=?", 
				new Object[]{Calendar.getInstance(),runId});
		
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#setContext(com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface)
	 */
	@Override
	public void setContext(TeeWorkFlowServiceContextInterface context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#getContext()
	 */
	@Override
	public TeeWorkFlowServiceContextInterface getContext() {
		return context;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#setSimpleDaoSupport(com.tianee.webframe.dao.TeeSimpleDaoSupport)
	 */
	@Override
	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#getSimpleDaoSupport()
	 */
	@Override
	public TeeSimpleDaoSupport getSimpleDaoSupport() {
		return simpleDaoSupport;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#preTurnNext(java.util.Map)
	 */
	@Override
	public void preTurnNext(Map requestData) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFixedFlowTurnServiceInterface#turnNext(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void turnNext(Map requestData, TeePerson loginPerson) {
		// TODO Auto-generated method stub
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		int prcsId = TeeStringUtil.getInteger(requestData.get("prcsId"), 0);
		int flowPrcs = TeeStringUtil.getInteger(requestData.get("flowPrcs"), 0);
		int runId = TeeStringUtil.getInteger(requestData.get("runId"), 0);
		int flowId = TeeStringUtil.getInteger(requestData.get("flowId"), 0);
		int uuid = loginPerson.getUuid();//当前登陆人id
		String turnJsonModel = TeeStringUtil.getString(requestData.get("turnJsonModel"));//转交json模型
		String message;//消息渲染
		
		//将转交模型json转换为List
		List<Map<String,String>> turnModelList = jsonUtil.JsonStr2MapList(turnJsonModel);
		
		//获取当前流程步骤实例
		TeeFlowRunPrcs frp = this.getContext().getFlowRunPrcsService().findByComplex(runId, flowId, prcsId, flowPrcs, uuid);
		frp.setFlag(3);
		frp.setEndTime(Calendar.getInstance());
		frp.setEndTimeStamp(Calendar.getInstance().getTime().getTime());
		
		
		
	}
	
	
}
