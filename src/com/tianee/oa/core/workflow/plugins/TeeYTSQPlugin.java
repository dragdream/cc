package com.tianee.oa.core.workflow.plugins;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.proxy.TeeJsonProxy;

//议题申请流程插件
public class TeeYTSQPlugin extends TeeWfPlugin{

	@Override
	public TeeJsonProxy beforeTurnnext() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//流程转交之后调用的方法
	@Override
	public void afterTurnnext() {
		//获取流程主键
		int runId=this.flowRunProxy.getRunId();
		//获取所属步骤
		int flowPrcs=this.flowRunProxy.getFlowPrcs();
		
		int prcsId = this.getFlowRunProxy().getPrcsId();
		
		
		//获取当前主办人
		int prcsUserUuid = this.flowRunProxy.getPrcsUserUuId();
		TeePerson prcsUser=(TeePerson) simpleDaoSupport.get(TeePerson.class,prcsUserUuid);
		//System.out.println(prcsUser.getUserName());
		if(prcsUser!=null&&("zhangwei").equals(prcsUser.getUserId())){
		   //如果当前步骤的办理人是张伟   则把他在分管领导意见区域的内容  copy  到  主管领导意见区域
			//获取会签意见
			String hql=" from TeeFlowRunCtrlFeedback where itemId=? and createUser.uuid=? and flowPrcs.sid=? and flowRun.runId=? and prcsId=? ";
			List<TeeFlowRunCtrlFeedback> list=simpleDaoSupport.executeQuery(hql, new Object[]{25,prcsUser.getUuid(),flowPrcs,runId,prcsId});
			if(list!=null&&list.size()>0){
				TeeFlowRunCtrlFeedback fb=list.get(0);
				TeeFlowRunCtrlFeedback fb1=new TeeFlowRunCtrlFeedback();
			    BeanUtils.copyProperties(fb, fb1);
			    fb1.setSid(0);
			    fb1.setItemId(26);
			    //修改所属设计步骤
			    TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,2017);
			    fb1.setFlowPrcs(process);
			    simpleDaoSupport.save(fb1);
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
