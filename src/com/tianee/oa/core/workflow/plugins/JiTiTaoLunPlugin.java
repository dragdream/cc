package com.tianee.oa.core.workflow.plugins;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beidasoft.xzzf.punish.document.bean.DocGroupDiscussion;
import com.beidasoft.xzzf.punish.document.service.GroupDiscussionService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsService;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowService;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

public class JiTiTaoLunPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterTurnnext() {
		// TODO Auto-generated method stub
		String primaryId = this.getFlowRunVars().get("PRIMARY_ID");//文书id
		GroupDiscussionService groupDiscussionService = (GroupDiscussionService) TeeBeanFactory.getBean("groupDiscussionService");
		DocGroupDiscussion docGroupDiscussion = groupDiscussionService.getById(primaryId);//集体讨论文书
		List<Map<String, String>> nameList = TeeJsonUtil.JsonStr2MapList(docGroupDiscussion.getParticipationStr());//参会领导数据
		boolean leaderFlag = false;
		for (int i = 0; i < nameList.size(); i++) {
			if(nameList.get(i).get("name").equals("李树江")){//是否需要总队领导签字
				leaderFlag = true;
			}
		}
		if(leaderFlag){
			boolean msgFlag = true;
			TeeSimpleDaoSupport simpleDaoSupport = (TeeSimpleDaoSupport) TeeBeanFactory.getBean("teeSimpleDaoSupport");
			List<Map> flowRunPrcsList = simpleDaoSupport.getMaps("select prcsUser.uuid as userSid,prcsUser.userName as userName,flag as flag from TeeFlowRunPrcs where flowRun.runId="+this.flowRunProxy.runId+" and prcsId="+this.flowRunProxy.prcsId, null);
			for (int i = 0; i < flowRunPrcsList.size(); i++) {
				if((int)flowRunPrcsList.get(i).get("flag")<3){//是否有未办理的    1，2未办理，3，4已办理
					msgFlag = false;
				}
			}
			if(msgFlag){
				//如果满足条件的话,给总队领导李树江插入一条代办数据
				TeeWorkflowService workflowService = (TeeWorkflowService) TeeBeanFactory.getBean("teeWorkflowService");
				Map requestParams = new HashMap();
				requestParams.put("runId", this.flowRunProxy.runId);//当前流程的runId
				requestParams.put("prcsId", this.flowRunProxy.prcsId+1);//初始化步骤的步骤号  默认当前序号+1
				requestParams.put("prcsUserId", "7");//李树江的人员主键id
				requestParams.put("createTimeStr", TeeDateUtil.format(new Date()));//待办创建的时间
				requestParams.put("flag", "1");//初始化一个办理状态 1是未接收
				requestParams.put("prcsName", "总队领导--李树江签字");//所创建的步骤名称
				requestParams.put("message", "相关领导已会签完毕，请您继续办理。");//短信提醒的内容
				requestParams.put("sms", "1");//是否进行短信提醒
				workflowService.addFlowRunPrcs(requestParams);
			}
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
	public void goBack(String prcsName, int prcsId, String content) {
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

}
