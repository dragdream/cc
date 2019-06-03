package com.tianee.oa.core.workflow.flowrun.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunView;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunViewDao;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunViewModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowRunViewService extends TeeBaseService implements TeeFlowRunViewServiceInterface{
	@Autowired
	private TeeFlowRunViewDao flowRunViewDao;
	
	@Autowired
	private TeeSmsSender smsSender;
	
	@Autowired
	private TeeAttachmentService attachmentService;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#addFlowRunView(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunView)
	 */
	@Override
	public void addFlowRunView(TeeFlowRunView flowRunView){
		flowRunViewDao.save(flowRunView);
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#viewLookup(int, int)
	 */
	@Override
	public void viewLookup(int runId,int personUuid){
		flowRunViewDao.viewLookup(runId,personUuid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#flowRunUrge(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public void flowRunUrge(int runId,TeePerson loginPerson){
		Map requestData = new HashMap();
		requestData.put("moduleNo", "006");
		
		List<TeeFlowRunPrcs> frpList = simpleDaoSupport.find("from TeeFlowRunPrcs frp where frp.flag in (1,2) and frp.flowRun.runId="+runId, null);
		for(TeeFlowRunPrcs frp:frpList){
			requestData.put("userListIds", frp.getPrcsUser().getUuid()+"");
			requestData.put("content", loginPerson.getUserName()+"向您发送一条催办提醒，请及时办理工作["+frp.getFlowRun().getRunName()+"]！");
			requestData.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?" +
					"runId="+frp.getFlowRun().getRunId()+
					"&flowId="+frp.getFlowRun().getFlowType().getSid()+
					"&frpSid="+frp.getSid());
			smsSender.sendSms(requestData, loginPerson);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#getViewsList(int)
	 */
	@Override
	public List<TeeFlowRunView> getViewsList(int runId){
		return flowRunViewDao.find("from TeeFlowRunView frv where frv.flowRun.runId=? order by frv.viewFlag desc frv.viewPerson.userNo asc", new Object[]{runId});
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#getViewsModelList(int)
	 */
	@Override
	public List<TeeFlowRunViewModel> getViewsModelList(int runId){
		List<TeeFlowRunView> list = getViewsList(runId);
		List<TeeFlowRunViewModel> modelList = new ArrayList<TeeFlowRunViewModel>();
		for(TeeFlowRunView frv:list){
			TeeFlowRunViewModel model = new TeeFlowRunViewModel();
			model.setSid(frv.getSid());
			model.setPrcsId(frv.getPrcsId());
			model.setViewFlag(frv.getViewFlag());
			model.setViewTimeDesc(TeeDateUtil.format(frv.getViewTime()));
			model.setViewUsername(frv.getViewPerson().getUserName());
			modelList.add(model);
		}
		return modelList;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#getDocModel(int)
	 */
	@Override
	@Transactional(readOnly=true)
	public TeeAttachmentModel getDocModel(int runId){
		Object attachId = (Object) simpleDaoSupport.unique("select docAttach.sid from TeeFlowRunDoc where flowRun.runId="+runId, null);
		TeeAttachmentModel model = null;
		if(attachId!=null){
			model = attachmentService.getModelById(TeeStringUtil.getInteger(attachId, 0));
		}
		return model;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunViewServiceInterface#checkFbRequired(int, com.tianee.oa.core.org.bean.TeePerson, int)
	 */
	@Override
	public TeeJson checkFbRequired(int runId, TeePerson loginPerson, int frpSid) {
		TeeJson json=new TeeJson();
		TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		TeeFlowProcess process=frp.getFlowPrcs();
		//
		
		
		List<TeeFlowRunCtrlFeedback> fbList=null;
		if(process!=null){//固定流程
			String hql=" from TeeFlowRunCtrlFeedback fb where fb.flowRun.runId=? and fb.createUser.uuid=? and fb.flowPrcs.sid=?";
			fbList=simpleDaoSupport.executeQuery(hql, new Object[]{runId,loginPerson.getUuid(),process.getSid()});
		}else{//自由流程
			String hql=" from TeeFlowRunCtrlFeedback fb where fb.flowRun.runId=? and fb.createUser.uuid=?";
			fbList=simpleDaoSupport.executeQuery(hql, new Object[]{runId,loginPerson.getUuid()});
		}
		if(fbList.size()>0){
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}
}
