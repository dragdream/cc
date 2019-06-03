package com.tianee.oa.core.workflow.flowrun.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workFlowFrame.msgsender.TeeFlowMsgSenderInterface;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeService;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunView;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowPrintTemplateModel;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrintTemplateServiceInterface;
import com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeWorkFlowConst;
import com.tianee.oa.util.workflow.TeeWorkflowHelperInterface;
import com.tianee.oa.util.workflow.ctrl.TeeCtrl;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFeedBackModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowProcessModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowTypeModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

@Service
public class TeeWorkflowHandler extends TeeBaseService implements TeeWorkflowHandlerInterface{
	@Autowired
	private TeeFlowRunViewServiceInterface flowRunViewService;
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	@Autowired
	private TeeFlowFormServiceInterface flowFormService;
	@Autowired
	private TeeWorkflowHelperInterface workflowHelper;
	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;
	@Autowired
	private TeeFlowRunPrcsDao flowRunPrcsDao;
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	@Autowired
	private TeeFlowPrivServiveInterface flowPrivServive;
	@Autowired
	private TeeFlowPrintTemplateServiceInterface flowPrintTemplateService;	
	@Autowired
	private TeeWorkFlowFeedBackService flowFeedBackServic;
	
	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeFlowMsgSenderInterface msgSender;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	
	@Autowired
	private TeeAttachmentService attachService;
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getHandlerData(int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getHandlerData(int runId,int frpSid,TeePerson loginUser){
		TeeJson json = new TeeJson();
		TeeFlowRunPrcs frp = flowRunPrcsService.get(frpSid);
		//判断是否存在该步骤工作
		if(frp==null){
			throw new TeeOperationException("该工作不存在");
		}
		
		//判断是否是可办理的工作
		if(!isHandleable(json,frp)){
			throw new TeeOperationException("该工作已转交至下一步骤或已办理完毕");
		}
		
		//判断该工作是否已经删除
		TeeFlowRun fr = frp.getFlowRun();
		if(fr.getDelFlag()==1){
			throw new TeeOperationException("该工作已删除");
		}
		
		//判断当前步骤实例办理类型。
		int topFlag = frp.getTopFlag();
		if(topFlag==3){//先接收者为主办,则将当前步骤设置为主办，其他步骤的topFlag设置为1
			frp.setOpFlag(1);
		}
		
		//获取当前流程最新表单
		TeeForm form = fr.getFlowType().getForm();
		int formVersion = fr.getFormVersion();
		form = flowFormService.getVersionForm(form.getFormGroup(), formVersion);
		
		//获取流程数据
		Map<String,String> datas = workflowHelper.getFlowRunData(runId, fr.getFlowType().getSid());
		
		/**
		 * 这里更新步骤数据信息，更新上一步骤办理人 为4 !
		 */
		if(frp.getPrcsId() > 1 && frp.getFlag()==1){
			updatePrePrcsUserState4(runId, frp.getPrcsId()-1);
		}
		/**
		 * 这里更新当前步骤办理人状态为 2
		 */
		if(frp.getFlag()==1){
			frp.setFlag(2);
			frp.setBeginTime(Calendar.getInstance());
			frp.setBeginTimeStamp(Calendar.getInstance().getTime().getTime());
		}
		
		if(frp.getOpFlag()==3){//先接收为主办
			boolean isExistHand = flowRunPrcsService.checkFlowRunPrcsIsExtisHand(runId, frp.getPrcsId(), frp.getFlowPrcs().getSid());
			if(!isExistHand){//如果不存在主办人的话，则指定当前人为主办人
				frp.setTopFlag(1);
				flowRunPrcsService.update(frp);
			}
		}
		
		String html = getFormHandleHtmlModel(frp.getFlowRun().getFlowType(),form,frp,datas,form.getFormItems(),loginUser);
		
		//拼写JSON返回数据信息
		Map params = new HashMap();
		params.put("form", html);
		params.put("script", form.getScript());
		params.put("css", form.getCss());
		json.setRtData(params);
		json.setRtState(true);
		
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getEditHtmlModel(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getEditHtmlModel(int runId,TeePerson loginUser){
		TeeJson json = new TeeJson();
		
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runId);
		
		if(flowRun==null){
			throw new TeeOperationException("该流程数据不存在！");
		}
		
//		if(flowRun.getEndTime()==null){
//			throw new TeeOperationException("该流程未结束，无法进行编辑");
//		}
		
		//判断当前编辑权限
		boolean hasPriv = false;
		loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginUser.getUuid());
		String depts = flowPrivServive.getQueryPostDeptsByFlowType(flowRun.getFlowType().getSid(), loginUser, "4");
		if("0".equals(depts)){
			hasPriv = true;
		}else{
			String sp[] = depts.split(",");
			TeeDepartment dept = flowRun.getBeginPerson().getDept();
			for(String id:sp){
				if(dept!=null && id.equals(dept.getUuid()+"")){
					hasPriv = true;
					break;
				}
			}
		}
		
		if(!hasPriv){
			throw new TeeOperationException("您没有该流程的编辑权限，无法进行编辑。");
		}
		
//		List<TeeFlowPriv> privList = simpleDaoSupport.find("from TeeFlowPriv priv where priv.flowType.sid="+flowRun.getFlowType().getSid()+" and priv.privType=4 and "
//				+ "exists (select 1 from priv.)", null);
//		if(privList.size()==0){
//			throw new TeeOperationException("您没有该流程的编辑权限，无法编辑数据");
//		}
		
		//获取当前流程最新表单
		TeeForm form = flowRun.getFlowType().getForm();
		int formVersion = flowRun.getFormVersion();
		form = flowFormService.getVersionForm(form.getFormGroup(), formVersion);
		
		//获取流程数据
		Map<String,String> datas = workflowHelper.getFlowRunData(runId, flowRun.getFlowType().getSid());
		
		String html = getFormEditHtmlModel(flowRun, datas);
		
		//拼写JSON返回数据信息
		Map params = new HashMap();
		params.put("form", html);
		params.put("script", form.getScript());
		params.put("css", form.getCss());
		json.setRtData(params);
		json.setRtState(true);
		
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getFormPrintExplore(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getFormPrintExplore(int formId,TeePerson loginUser){
		TeeJson json = new TeeJson();
		
		//获取最新表单数据
		TeeForm form = flowFormService.getLatestVersion(formId);
		
		String content = flowFormService.printExplore(form.getSid(),loginUser);
		
		Map data = new HashMap();
		data.put("html", content);
		data.put("script", form.getScript());
		data.put("css", form.getCss());
		
		json.setRtData(data);
		json.setRtState(true);
		
		return json;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getHistoryFormPrintExplore(int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getHistoryFormPrintExplore(int formId,TeePerson loginUser){
		TeeJson json = new TeeJson();
		
		//获取最新表单数据
		TeeForm form = (TeeForm) simpleDaoSupport.get(TeeForm.class,formId);
		
		String content = flowFormService.printExplore(form.getSid(),loginUser);
		
		Map data = new HashMap();
		data.put("html", content);
		data.put("script", form.getScript());
		data.put("css", form.getCss());
		
		json.setRtData(data);
		json.setRtState(true);
		
		return json;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#turnNextFreeFlow(int, int, int, int, java.lang.String, java.lang.String, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson turnNextFreeFlow(int runId,int flowId,int prcsId,int flowPrcs,String opUsers,String prcsUsers,TeePerson person){
		TeeJson json = new TeeJson();
		/**
		 * 更新当前步骤所有办理人状态为 3
		 */
		TeeFlowRunPrcs frp = flowRunPrcsService.findByComplex(runId, flowId, prcsId, flowPrcs, person.getUuid());
		TeeFlowRun currFlowRun = frp.getFlowRun();
		if(opUsers.endsWith(",")){
			opUsers = opUsers.substring(0,opUsers.length()-1);
		}
		TeePersonService tps =  flowServiceContext.getPersonService();
		TeePerson opPerson =tps.selectByUuid(opUsers);
		
		//更新上一步骤 办理人状态 3
		updatePrePrcsUserState3(runId, prcsId);
		//插入下一步骤 主办人
		TeeFlowRunPrcs flowRunPrcs =  new TeeFlowRunPrcs();
		flowRunPrcs.setCreateTime(Calendar.getInstance());
		flowRunPrcs.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
		flowRunPrcs.setDelFlag(0);
	    flowRunPrcs.setFlowPrcs(null);
		flowRunPrcs.setFlowRun(currFlowRun);
		//flowRunPrcs.setFromUser(null);
		flowRunPrcs.setPrcsId(prcsId + 1 );
		flowRunPrcs.setOpFlag(1);
		flowRunPrcs.setTopFlag(0);
		flowRunPrcs.setPrcsUser(opPerson);
		flowRunPrcs.setFlag(1);
		flowRunPrcsService.save(flowRunPrcs);
		//插入下一步骤 经办人
		if(prcsUsers != null && !"".equals(prcsUsers)){
			if(prcsUsers.endsWith(",")){
				prcsUsers = prcsUsers.substring(0,prcsUsers.length()-1);
			}
			String[] prcUseArray = prcsUsers.split(",");
			for(int i=0;i<prcUseArray.length;i++){
				
				TeeFlowRunPrcs flowRunPrcsChild =  new TeeFlowRunPrcs();
				flowRunPrcsChild.setCreateTime(Calendar.getInstance());
				flowRunPrcsChild.setDelFlag(0);
				flowRunPrcsChild.setFlowPrcs(null);
				flowRunPrcsChild.setFlowRun(currFlowRun);
				//flowRunPrcs.setFromUser(null);
				flowRunPrcsChild.setPrcsId(prcsId + 1 );
				flowRunPrcsChild.setOpFlag(0);//经办人 这里一定是 0
				flowRunPrcsChild.setTopFlag(0);
				TeePerson temPerson = flowServiceContext.getPersonService().selectByUuid(prcUseArray[i]);
				flowRunPrcsChild.setPrcsUser(temPerson);
				flowRunPrcsChild.setFlag(1);
				flowRunPrcsService.save(flowRunPrcsChild);
			}
			
		}
		
		json.setRtMsg("转交成功!");
		json.setRtState(true);
		return json;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#turnEndFreeFlow(int, int, int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson turnEndFreeFlow(int runId,int flowId,int prcsId,int flowPrcs,TeePerson person){
		TeeJson json = new TeeJson();
		TeeFlowRunPrcs frp = flowRunPrcsService.findByComplex(runId, flowId, prcsId, flowPrcs, person.getUuid());
		TeeFlowRun currFlowRun = frp.getFlowRun();
		currFlowRun.setEndTime(Calendar.getInstance());
		flowServiceContext.getFlowRunService().update(currFlowRun);
		updateAllPrcsUserState4(runId,prcsId);
		json.setRtMsg("结束流程成功！!");
		json.setRtState(true);
		return json;
	}
	

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#countersignHandler(int, int, int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson countersignHandler(int runId,int flowId,int prcsId,int flowPrcs,TeePerson person){
		TeeJson json = new TeeJson();
		updateCurrPrcsUserState3(runId, prcsId, person.getUuid()+"");
		json.setRtMsg("会签完毕!");
		json.setRtState(true);
		return json;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateAllPrcsUserState4(int, int)
	 */
	@Override
	public void updateAllPrcsUserState4(int runId,int prcsId){
		String hql = "update TeeFlowRunPrcs p set p.flag = 4,p.endTime = ? where p.prcsId = "+prcsId + " and p.flowRun.runId = "+runId;
		flowServiceContext.getFlowRunPrcsService().updateAllPrcsUserState4(hql);
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updatePrePrcsUserState4(int, int)
	 */
	@Override
	public void updatePrePrcsUserState4(int runId,int prcsId){
		String hql = "update TeeFlowRunPrcs p set p.flag = 4 where p.prcsId = "+prcsId + " and p.flowRun.runId = "+runId;
		flowServiceContext.getFlowRunPrcsService().update(hql);
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateCurrPrcsUserState2(int, int, java.lang.String)
	 */
	@Override
	public void updateCurrPrcsUserState2(int runId,int prcsId,String personUuid){
		String hql = "update TeeFlowRunPrcs p set p.flag = 2,p.beginTime = ? where p.prcsId = "+prcsId + " and p.flowRun.runId = "+runId + "and p.prcsUser.uuid = '"+personUuid+"'";
		flowServiceContext.getFlowRunPrcsService().updateCurrPrcsUserState2(hql);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateRunName(int, java.lang.String)
	 */
	@Override
	public void updateRunName(int runId,String runName){
		simpleDaoSupport.executeUpdate("update TeeFlowRun set runName=?,oRunName=? where runId=?", new Object[]{runName,runName,runId});
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateRunLevel(int, int)
	 */
	@Override
	public void updateRunLevel(int runId,int level){
		simpleDaoSupport.executeUpdate("update TeeFlowRun set level=? where runId=?", new Object[]{level,runId});
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateCurrPrcsUserState3(int, int, java.lang.String)
	 */
	@Override
	public void updateCurrPrcsUserState3(int runId,int prcsId,String personUuid){
		String hql = "update TeeFlowRunPrcs p set p.flag = 3 where p.prcsId = "+prcsId + " and p.flowRun.runId = "+runId + "and p.prcsUser.uuid = '"+personUuid+"'";
		flowServiceContext.getFlowRunPrcsService().update(hql);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updatePrePrcsUserState3(int, int)
	 */
	@Override
	public void updatePrePrcsUserState3(int runId,int prcsId){
		String hql = "update TeeFlowRunPrcs p set p.flag = 3 where p.prcsId = "+prcsId + " and p.flowRun.runId = "+runId;
		flowServiceContext.getFlowRunPrcsService().update(hql);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#insetNextPrcsOpUser(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, java.lang.String)
	 */
	@Override
	public boolean insetNextPrcsOpUser(TeeFlowRunPrcs currFrp,String opUser){
		if(currFrp == null){
			return false;
		}
		if(opUser == null || "".equals(opUser)){
			return false;
		}
		TeePerson p = flowServiceContext.getPersonService().selectByUuid(opUser);
		TeeFlowRunPrcs frp = new TeeFlowRunPrcs();
		frp.setBeginTime(Calendar.getInstance());
		frp.setBeginTimeStamp(Calendar.getInstance().getTime().getTime());
		frp.setCreateTime(Calendar.getInstance());
		frp.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
		frp.setDelFlag(currFrp.getDelFlag());
		frp.setFlowPrcs(null);
		frp.setFlowRun(currFrp.getFlowRun());
		//flowRunPrcs.setFromUser(null);
		frp.setPrcsId(currFrp.getPrcsId() + 1);
		frp.setOpFlag(1);
		frp.setTopFlag(currFrp.getTopFlag());
		frp.setPrcsUser(p);
		frp.setFlag(1);
		
		return true;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#insetNextPrcs(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, java.lang.String)
	 */
	@Override
	public List<TeeFlowRunPrcs> insetNextPrcs(TeeFlowRunPrcs currFrp,String prcsUsers){
		List<TeeFlowRunPrcs> frps = null;
			 frps = new ArrayList<TeeFlowRunPrcs>();
		if(currFrp == null){
			return null;
		}
		if(prcsUsers == null || "".equals(prcsUsers)){
			return null;
		}
		String[] prcsUsersArr = prcsUsers.split(",");
		for(int i=0;i<prcsUsersArr.length;i++){
			TeePerson p = flowServiceContext.getPersonService().selectByUuid(prcsUsersArr[i]);
			TeeFlowRunPrcs frp = new TeeFlowRunPrcs();
			frp.setBeginTime(Calendar.getInstance());
			frp.setBeginTimeStamp(Calendar.getInstance().getTime().getTime());
			frp.setCreateTime(Calendar.getInstance());
			frp.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
			frp.setDelFlag(currFrp.getDelFlag());
			frp.setFlowPrcs(null);
			frp.setFlowRun(currFrp.getFlowRun());
			//flowRunPrcs.setFromUser(null);
			frp.setPrcsId(currFrp.getPrcsId() + 1);
			frp.setOpFlag(0);
			frp.setTopFlag(currFrp.getTopFlag());
			frp.setPrcsUser(p);
			frp.setFlag(1);
		}
		
		return null;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getHandlerOptPrivData(int, int, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getHandlerOptPrivData(int runId,int frpSid,TeePerson loginUser){
		TeeJson json = new TeeJson();
		TeeFlowRunPrcs frp = flowRunPrcsService.get(frpSid);
		//判断是否存在该步骤工作
		if(frp==null){
			json.setRtState(false);
			json.setRtMsg("没有找到该步骤工作信息数据");
			return json;
		}
		
		//判断是否是可办理的工作
		if(!isHandleable(json,frp)){
			json.setRtState(false);
			json.setRtMsg("该工作已转交至下一步骤或已办理完毕");
			return json;
		}
		
		//判断该工作是否已经删除
		TeeFlowRun fr = frp.getFlowRun();
		if(fr.getDelFlag()==1){
			json.setRtState(false);
			json.setRtMsg("该工作已删除");
			return json;
		}
		
		//判断该流程是否存在子流程
		List<TeeFlowRun> frs = flowRunPrcsDao.getChildFlowRuns(runId);
		boolean hasChildUnhandledFlow = false;
		TeeFlowProcess childProcess = null;
		if(frs.size()!=0){
			int parentFlowPrcsSids[] = TeeStringUtil.parseIntegerArray(frp.getParent());
			//如果存在子流程，则判断当前步骤的上一个步骤是否在触发子流程中
			for(TeeFlowRun tmp:frs){
				childProcess = (TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class, tmp.getPChildFlowPrcs());
				for(int pFpSidTmp:parentFlowPrcsSids){
					TeeFlowRunPrcs nFrp = flowRunPrcsDao.get(tmp.getPSrouceFrpSid());
					if(pFpSidTmp==nFrp.getFlowPrcs().getSid() 
							&& tmp.getEndTime()==null){
						hasChildUnhandledFlow = true;
						break;
					}
				}
				if(hasChildUnhandledFlow){
					break;
				}
			}
			
		}
		
		
		/**
		 * 需要获取操作按钮 
		 * 这个人是否显示转交按钮 ！【转交】
		 * 这个人是否显示回退按钮 ！【回退】
		 * 这个人是否显示会签按钮 ！【会签】
		 * 这个人是否显示结束按钮 ！【结束】
		 */
		Map optPrivMap = flowServiceContext.getFlowOptionPrivHelper().initworkHandlerOptionPriv(frp);
		
		if(hasChildUnhandledFlow && childProcess!=null){
			if(childProcess.getPrcsWait()==0){
				optPrivMap.put(TeeWorkFlowConst.TURN_STATE, null);
			}
			optPrivMap.put("hasChildUnhandledFlow", "");
		}
		
		//拼写JSON返回数据信息
		optPrivMap.put("runId", fr.getRunId());
		optPrivMap.put("runName", fr.getRunName());
		optPrivMap.put("level", fr.getLevel());
		
		//获取流程模型
		TeeFlowTypeModel ftModel = new TeeFlowTypeModel();
		TeeFlowType ft = fr.getFlowType();
		BeanUtils.copyProperties(ft, ftModel);
		optPrivMap.put("flowType", ftModel);
		
		//获取流程步骤实例模型
		TeeFlowRunPrcsModel frpModel = new TeeFlowRunPrcsModel();
		BeanUtils.copyProperties(frp, frpModel);
		optPrivMap.put("flowRunPrcs", frpModel);
		
		//获取流程步骤模型，如果为空则设置为null
		TeeFlowProcessModel fpModel = new TeeFlowProcessModel();
		if(frp.getFlowPrcs()!=null){
			BeanUtils.copyProperties(frp.getFlowPrcs(), fpModel);
		}else{
			fpModel = null;
		}
		
		TeeFlowProcess fp = frp.getFlowPrcs();
		Set<TeeFlowProcess> childs = fp.getNextProcess();
		for(TeeFlowProcess fpp:childs){
			if(fpp.getPrcsType()==2){
				optPrivMap.put("turnEndId", fpp.getSid());
			}
		}
		
		optPrivMap.put("flowPrcs", fpModel);
		
		//判断当前流程类型是否有关联的AIP签批模板存在  1=存在   0=不存在
		int isExistsAipTemplate=0;
		List<TeeFlowPrintTemplateModel> modelList=null;
		if(fr.getFlowType()!=null){
			modelList = flowPrintTemplateService.selectModulByFlowType(TeeStringUtil.getString(fr.getFlowType().getSid()));
		}
		if(modelList!=null&&modelList.size()>0){
			isExistsAipTemplate=1;
		}
		
		optPrivMap.put("isExistsAipTemplate", isExistsAipTemplate);
		
		json.setRtState(true);
		json.setRtData(optPrivMap);
		
		return json;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateCurrPrcsUserState(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public boolean  updateCurrPrcsUserState(TeeFlowRunPrcs frp){
		if(frp == null){
			return false;
		}
		frp.setBeginTime(Calendar.getInstance());
		frp.setBeginTimeStamp(Calendar.getInstance().getTime().getTime());
		frp.setFlag(2);
		flowServiceContext.getFlowRunPrcsService().update(frp);
		return true;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#isHandleable(com.tianee.webframe.httpmodel.TeeJson, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs)
	 */
	@Override
	public boolean isHandleable(TeeJson json,TeeFlowRunPrcs frp){
		/**
		 * 如果结束时间不等于 null 则该步骤已经结束 不可办理
		 */
		if((frp.getFlag()!=1 && frp.getFlag()!=2) || frp.getEndTime() != null){
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getFormHandleHtmlModel(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType, com.tianee.oa.core.workflow.formmanage.bean.TeeForm, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, java.util.Map, java.util.List, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public String getFormHandleHtmlModel(final TeeFlowType flowType,final TeeForm form,final TeeFlowRunPrcs frp,final Map<String,String> datas,final List<TeeFormItem> formItems,final TeePerson loginUser){
		String html = "";
		TeeRegexpAnalyser ra = new TeeRegexpAnalyser();
		
		//判断是全局表单  还是  独立表单
		if(frp.getFlowPrcs()!=null){
			if(frp.getFlowPrcs().getFormSelect()==1){//全局
				ra.setText(form.getPrintModelShort());
			}else{//独立表单
				ra.setText(frp.getFlowPrcs().getFormShort());
			}
		}else{
			ra.setText(form.getPrintModelShort());
		}

		
		//先处理宏标记（会签意见）
		html = ra.replace(new String[]{"#\\[MACRO_SIGN[0-9]*[\\*]*\\]\\[[^\\]]*]"}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				//System.out.println(pattern);
				return handlerFeedBackMacroTag(frp.getFlowRun(),pattern,loginUser);
			}
			
		});
		ra.setText(html);
		
		
		//处理附件宏标记
		html = ra.replace(new String[]{"#\\[MACRO_ATTACH[0-9]*\\]"}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				//System.out.println(pattern);
				return handlerAttachMacroTag(frp.getFlowRun(),pattern,loginUser);
			}
			
		});
		ra.setText(html);
		
		
		html = ra.replace(new String[]{"\\{DATA_[0-9]+\\}","#\\[MACRO_[a-zA-Z_]+\\]"}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				if(pattern.startsWith("#[MACRO_") && pattern.endsWith("]")){
					return handlerMacroTag1(frp.getFlowRun(),pattern,frp,loginUser);
				}
				
				//获取当前控件
				TeeFormItem item = findFormItemByItemId(pattern,formItems);
				if(item==null){
					return "{item is null}";
				}
				//获取控件控制
				TeeCtrl ctrl = TeeCtrl.getInstanceOf(item.getXtype());
				if(ctrl==null){
					return "{ctrl is null}";
				}
				pattern = pattern.replace("{", "").replace("}", "");
				
				//组合控件值
				TeeFlowFormData flowFormData = new TeeFlowFormData();
				flowFormData.setData(datas==null?null:datas.get(pattern));
				flowFormData.setItemId(item.getItemId());
				flowFormData.setRunId(frp.getFlowRun().getRunId());
				
				ctrl.setContext(flowServiceContext);
				return ctrl.getCtrlHtml4Process(item, flowFormData, flowType, form, frp,datas==null?new HashMap():datas);
			}
		});
		return html;
	}
	
	
	
	/**
	 * 单独处理附件宏标记
	 * @param flowRun
	 * @param pattern
	 * @param loginUser
	 * @return
	 */
	protected String handlerAttachMacroTag(TeeFlowRun flowRun, String pattern,
			TeePerson loginUser) {
		String html="";
		//#[MACRO_ATTACH]  #[MACRO_ATTACH1]
		
		//根据流程主键获取公共附件
		List<TeeAttachmentModel> attList=attachService.getAttacheModels(TeeAttachmentModelKeys.workFlow, String.valueOf(flowRun.getRunId()));
		
		//附件排序号
		int attachNum=TeeStringUtil.getInteger(pattern.substring(14,pattern.length()-1), 0);
		if(attList!=null&&attList.size()>0){
			if(attachNum==0){//取出所有的公共附件
				for (TeeAttachmentModel teeAttachmentModel : attList) {
					html+="<div class=\"macroatt\"  sid="+teeAttachmentModel.getSid()+"  filename="+teeAttachmentModel.getFileName()+" userid="+teeAttachmentModel.getUserId()+"    ext="+teeAttachmentModel.getExt()+"    username="+teeAttachmentModel.getUserName()+"   sizedesc="+teeAttachmentModel.getSizeDesc()+" createtimedesc="+teeAttachmentModel.getCreateTimeDesc()+">"+teeAttachmentModel.getFileName()+"</div>";
				}
			}else{//取出指定排序的公共附件
				if(attachNum>attList.size()){//不存在
					return "";
				}else{
					TeeAttachmentModel model=attList.get(attachNum-1);
					html+="<div class=\"macroatt\" sid="+model.getSid()+"  filename="+model.getFileName()+"    userid="+model.getUserId()+"  ext="+model.getExt()+"    username="+model.getUserName()+" sizedesc="+model.getSizeDesc()+" createtimedesc="+model.getCreateTimeDesc()+">"+model.getFileName()+"</div>";
					
				}
			}
		}else{
			return "";
		}
		
		return html;
	}

	/**
	 * 单独处理会签宏标记
	 * @param flowRun
	 * @param pattern
	 * @param flowRunPrcs
	 * @param loginUser
	 * @return
	 */
	protected String handlerFeedBackMacroTag(TeeFlowRun flowRun, String pattern, TeePerson loginUser) {
		  String html="";
		//#[MACRO_SIGN8*][{C}{SD}/{U} {Y}年{M}月{D}日]
		/**
		 *  #[MACRO_SIGN][]
			#[MACRO_SIGN1][]
			#[MACRO_SIGN2][{C} {U}]
			#[MACRO_SIGN*][]
			#[MACRO_SIGN1*][]
			#[MACRO_SIGN2*][{C} {U}]
		 */
		 
		String[] patternArr=pattern.split("\\]\\[");
		String token = patternArr[0];
		String geshi=patternArr[1].substring(0,patternArr[1].length()-1);
		 //是否包含回退意见
	    int isHasBack=0;
	    if(geshi.contains("{C}")&&!geshi.contains("{B}")){//只有C  没有B
	    	isHasBack=0;
	    }else if(geshi.contains("{B}")&&!geshi.contains("{C}")){//只有B 没有C
	    	isHasBack=1;
	    }else if(geshi.contains("{B}")&&geshi.contains("{C}")){//B和C都有
	    	isHasBack=0;
	    }else if(!geshi.contains("{B}")&&!geshi.contains("{C}")){//B和C都没有
	    	isHasBack=0;
	    }
	   
		if(token.endsWith("*")){//步骤设计编号
			//编号
		    String num=TeeStringUtil.getString(token.substring(12,token.length()-1));
		   
		    List<Map<String,String>> fbList = flowFeedBackServic.selectFeedBackByRunIdAndFlowProcessPrcsId(flowRun,num,isHasBack);	
			
			if(fbList!=null&&fbList.size()>0){
				String s="";
				for (Map<String,String> m : fbList) {
					s=geshi;
					if(!TeeUtility.isNullorEmpty(s)){
						s=s.replace("{C}", TeeUtility.cutHtml(m.get("C")))
								.replace("{B}",TeeUtility.cutHtml(m.get("B")) )
								.replace("{U}",TeeStringUtil.getString(m.get("U")) )
								.replace("{R}",TeeStringUtil.getString(m.get("R")) )
								.replace("{SD}",TeeStringUtil.getString(m.get("SD")))
								.replace("{LD}",TeeStringUtil.getString(m.get("LD")))
								.replace("{P}", TeeStringUtil.getString(m.get("P")))
								.replace("{Y}",TeeStringUtil.getString(m.get("Y")))
								.replace("{M}",TeeStringUtil.getString(m.get("M")))
								.replace("{D}",TeeStringUtil.getString(m.get("D")))
								.replace("{H}",TeeStringUtil.getString(m.get("H")))
								.replace("{I}",TeeStringUtil.getString(m.get("I")))
								.replace("{S}",TeeStringUtil.getString(m.get("S")))
								.replace("{IMAGE}","<img src='"+TeeStringUtil.getString(m.get("IMAGE"))+"'/>");
					}
					
				    html+="<div>"+s+"</div>";
				}
			}
		    
		}else{//步骤实际编号  （直接是feed_back的prcsId）
			//编号
		    String num=TeeStringUtil.getString(token.substring(12,token.length()));
            List<Map<String,String>> fbList = flowFeedBackServic.selectFeedBackByRunIdAndPrcsId(flowRun,num,isHasBack);	
			
			if(fbList!=null&&fbList.size()>0){
				String s="";
				for (Map<String,String> m : fbList) {
					s=geshi;
					if(!TeeUtility.isNullorEmpty(s)){
						s=s.replace("{C}", TeeUtility.cutHtml(m.get("C")))
								.replace("{B}",TeeUtility.cutHtml(m.get("B")) )
								.replace("{U}",TeeStringUtil.getString(m.get("U")) )
								.replace("{R}",TeeStringUtil.getString(m.get("R")) )
								.replace("{SD}",TeeStringUtil.getString(m.get("SD")))
								.replace("{LD}",TeeStringUtil.getString(m.get("LD")))
								.replace("{P}", TeeStringUtil.getString(m.get("P")))
								.replace("{Y}",TeeStringUtil.getString(m.get("Y")))
								.replace("{M}",TeeStringUtil.getString(m.get("M")))
								.replace("{D}",TeeStringUtil.getString(m.get("D")))
								.replace("{H}",TeeStringUtil.getString(m.get("H")))
								.replace("{I}",TeeStringUtil.getString(m.get("I")))
								.replace("{S}",TeeStringUtil.getString(m.get("S")));
					}
					
				    html+="<div>"+s+"</div>";
				}
			}
		}
		
		return html;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getFormEditHtmlModel(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, java.util.Map)
	 */
	@Override
	public String getFormEditHtmlModel(final TeeFlowRun flowRun,final Map<String,String> datas){
		String html = "";
		TeeRegexpAnalyser ra = new TeeRegexpAnalyser();
		final TeeForm form = flowRun.getForm();
		final List<TeeFormItem> formItems = form.getFormItems();
		ra.setText(form.getPrintModelShort());
		html = ra.replace(new String[]{"\\{DATA_[0-9]+\\}","#\\[MACRO_[a-zA-Z_]+\\]"}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				if(pattern.startsWith("#[MACRO_") && pattern.endsWith("]")){
					return handlerMacroTag(flowRun,pattern);
				}
				
				//获取当前控件
				TeeFormItem item = findFormItemByItemId(pattern,formItems);
				if(item==null){
					return "{item is null}";
				}
				//获取控件控制
				TeeCtrl ctrl = TeeCtrl.getInstanceOf(item.getXtype());
				if(ctrl==null){
					return "{ctrl is null}";
				}
				pattern = pattern.replace("{", "").replace("}", "");
				
				//组合控件值
				TeeFlowFormData flowFormData = new TeeFlowFormData();
				flowFormData.setData(datas==null?null:datas.get(pattern));
				flowFormData.setItemId(item.getItemId());
				flowFormData.setRunId(flowRun.getRunId());
				
				ctrl.setContext(flowServiceContext);
				return ctrl.getCtrlHtml4Edit(flowRun, item, flowFormData, datas==null?new HashMap():datas);
			}
		});
		return html;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getFormPrintHtmlModel(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType, com.tianee.oa.core.workflow.formmanage.bean.TeeForm, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, java.util.Map, java.util.List, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public String getFormPrintHtmlModel(final TeeFlowType flowType,final TeeForm form,final TeeFlowRun flowRun,final TeeFlowRunPrcs flowRunPrcs,final Map<String,String> datas,final List<TeeFormItem> formItems,final TeePerson loginUser){
		String html = "";
		TeeRegexpAnalyser ra = new TeeRegexpAnalyser();
		if(flowRunPrcs!=null){
			if(flowRunPrcs.getFlowPrcs()!=null){
				if(flowRunPrcs.getFlowPrcs().getFormSelect()==1){//全局表单
					ra.setText(form.getPrintModelShort());
				}else{//独立表单
					ra.setText(flowRunPrcs.getFlowPrcs().getFormShort());
				}	
			}else{
				ra.setText(form.getPrintModelShort());
			}
		}else{
			ra.setText(form.getPrintModelShort());
		}
		
		
		//先处理宏标记（会签意见）
		html = ra.replace(new String[]{"#\\[MACRO_SIGN[0-9]*[\\*]*\\]\\[[^\\]]*]"}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				//System.out.println(pattern);
				return handlerFeedBackMacroTag(flowRun,pattern,loginUser);
			}
			
		});
		ra.setText(html);
		
		//处理附件宏标记
		html = ra.replace(new String[]{"#\\[MACRO_ATTACH[0-9]*\\]"}, new TeeExpFetcher(){

			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				//System.out.println(pattern);
				return handlerAttachMacroTag(flowRun,pattern,loginUser);
			}
			
		});
		ra.setText(html);
		
		html = ra.replace(new String[]{"\\{DATA_[0-9]+\\}","#\\[MACRO_[a-zA-Z_]+\\]"}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				//如果是宏标记，则进行红标记处理
				if(pattern.startsWith("#[MACRO_") && pattern.endsWith("]")){
					return handlerMacroTag1(flowRun,pattern,flowRunPrcs,loginUser);
				}
				
				//获取当前控件
				TeeFormItem item = findFormItemByItemId(pattern,formItems);
				if(item==null){
					return "{item is null}";
				}
				//获取控件控制
				TeeCtrl ctrl = TeeCtrl.getInstanceOf(item.getXtype());
				if(ctrl==null){
					return "{ctrl is null}";
				}
				pattern = pattern.replace("{", "").replace("}", "");
				
				//组合控件值
				TeeFlowFormData flowFormData = new TeeFlowFormData();
				flowFormData.setData(datas==null?null:datas.get(pattern));
				flowFormData.setItemId(item.getItemId());
				flowFormData.setRunId(flowRun.getRunId());
				
				ctrl.setContext(flowServiceContext);
				return ctrl.getCtrlHtml4Print(item, flowFormData, flowType, form, flowRun,flowRunPrcs, datas);
			}
		});
		return html;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#handlerMacroTag(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, java.lang.String)
	 */
	@Override
	public String handlerMacroTag(TeeFlowRun flowRun,String pattern){
		String token = pattern.substring(8,pattern.length()-1);
		
		if("FORM_NAME".equals(token)){//表单名称
			token = flowRun.getForm().getFormName();
		}else if("RUN_NAME".equals(token)){//流程名称
			token = flowRun.getRunName();
		}else if("NUMBERING".equals(token)){//编号计数器
			token = String.valueOf(flowRun.getFlowType().getNumbering());
		}else if("BEGIN_TIME".equals(token)){//流程开始时间
			token = TeeDateUtil.getDateTimeStr(flowRun.getBeginTime().getTime());
		}else if("END_TIME".equals(token)){//流程结束时间
			token = TeeDateUtil.getDateTimeStr(flowRun.getEndTime()==null?null:flowRun.getEndTime().getTime());
		}else if("RUN_ID".equals(token)){//流水号
			token = String.valueOf(flowRun.getRunId());
		}else if("BEGIN_USERNAME".equals(token)){//流程发起人真实姓名
			token = flowRun.getBeginPerson().getUserName();
		}else if("BEGIN_USERID".equals(token)){//流程发起人ID
			token = flowRun.getBeginPerson().getUserId();
		}else{
			token = pattern;
		}
		
		return token;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#handlerMacroTag1(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, java.lang.String, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public String handlerMacroTag1(TeeFlowRun flowRun,String pattern,TeeFlowRunPrcs flowRunPrcs,TeePerson loginUser){
		String token = pattern.substring(8,pattern.length()-1);
		
		if("FORM_NAME".equals(token)){//表单名称
			token = flowRun.getForm().getFormName();
		}else if("RUN_NAME".equals(token)){//流程名称
			token = flowRun.getRunName();
		}else if("NUMBERING".equals(token)){//编号计数器
			token = String.valueOf(flowRun.getFlowType().getNumbering());
		}else if("BEGIN_TIME".equals(token)){//流程开始时间
			token = TeeDateUtil.getDateTimeStr(flowRun.getBeginTime().getTime());
		}else if("END_TIME".equals(token)){//流程结束时间
			token = TeeDateUtil.getDateTimeStr(flowRun.getEndTime()==null?null:flowRun.getEndTime().getTime());
		}else if("RUN_ID".equals(token)){//流水号
			token = String.valueOf(flowRun.getRunId());
		}else if("BEGIN_USERNAME".equals(token)){//流程发起人真实姓名
			token = flowRun.getBeginPerson().getUserName();
		}else if("BEGIN_USERID".equals(token)){//流程发起人ID
			token = flowRun.getBeginPerson().getUserId();
		}else if("FEEDBACK".equals(token)){//会签意见
			List fbList = flowFeedBackServic.selectPrcsPrivedFeedBackByRunId(flowRun.getRunId(),flowRunPrcs==null?0:flowRunPrcs.getSid(),loginUser.getUuid());	
		    String html="";
			for (Object obj: fbList) {
				TeeFeedBackModel fbModel=(TeeFeedBackModel)obj;
		    	html = html + "<div style='font-size:12px'><span class='stepClazz'><b>第"+fbModel.getPrcsId()+"步</b>&nbsp;&nbsp;"+fbModel.getPrcsName()+"</span>&nbsp;&nbsp;&nbsp;&nbsp;<span class='uNameClazz'>"+fbModel.getUserName()+"</span>&nbsp;&nbsp;<span class='timeClazz'>"+fbModel.getEditTimeDesc()+"</span></div>"+"<div><span class='contentClazz'>"+fbModel.getContent()+"</span></div>";
			}
		    token=html;
		}else{
			token = pattern;
		}
		
		return token;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#findFormItemByItemId(int, java.util.List)
	 */
	@Override
	public TeeFormItem findFormItemByItemId(int itemId,List<TeeFormItem> items){
		for(TeeFormItem it:items){
			if(it.getItemId()==itemId){
				return it;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#saveFlowRunData(int, int, int, java.util.Map, java.util.Map)
	 */
	@Override
	public void saveFlowRunData(int runId,int flowId,int frpSid,Map datas,Map requestParam){
		workflowHelper.saveOrUpdateFlowRunData(runId, flowId,frpSid, datas,requestParam);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#editFlowRunData(int, int, int, java.util.Map)
	 */
	@Override
	public void editFlowRunData(int runId,int flowId,int frpSid,Map datas){
		TeeFlowRun fr = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runId);
		//记录日志
		TeeFlowRunLog log = TeeFlowRunLog.getInstance();
		log.setFlowName(fr.getFlowType().getFlowName());
		log.setRunId(fr.getRunId());
		log.setRunName(fr.getRunName());
		log.setTime(Calendar.getInstance());
		log.setFlowId(flowId);
		log.setContent("编辑流程表单信息");
		simpleDaoSupport.save(log);
		
		workflowHelper.saveOrUpdateFlowRunData(runId, flowId,frpSid, datas,null);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#findFormItemByItemId(java.lang.String, java.util.List)
	 */
	@Override
	public TeeFormItem findFormItemByItemId(String id,List<TeeFormItem> items){
		id = id.replaceAll("\\{[A-Z a-z]+_", "").replace("}", "");
		return findFormItemByItemId(Integer.parseInt(id),items);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#findFormItemByItemTitle(java.lang.String, java.util.List)
	 */
	@Override
	public TeeFormItem findFormItemByItemTitle(String title,List<TeeFormItem> items){
		for(TeeFormItem it:items){
			if(it.getTitle().equals(title)){
				return it;
			}
		}
		return null;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#view(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson view(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的runId
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		//处理传阅逻辑
		TeeFlowRun fr = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		if(fr!=null){
			TeePerson viewUser = null;
			TeeFlowRunView flowRunView = null;
			StringBuffer personStr = new StringBuffer();
		
			int viewPersons[] = TeeStringUtil.parseIntegerArray(request.getParameter("viewPerson"));
			for(int personId:viewPersons){
			  viewUser = (TeePerson) simpleDaoSupport.get(TeePerson.class,personId);
			  personStr.append(personId+",");
					
			  flowRunView = new TeeFlowRunView();
			  flowRunView.setCreateTime(Calendar.getInstance());
			  flowRunView.setFlowPrcs(frp.getFlowPrcs());
			  flowRunView.setFlowRun(fr);
			  flowRunView.setPrcsId(frp.getPrcsId());
			  flowRunView.setViewPerson(viewUser);
			  flowRunViewService.addFlowRunView(flowRunView);
			}
             
			//发送传阅短消息
			msgSender.sendViewSms(personStr.toString(), fr);
			
			json.setRtState(true);
			json.setRtMsg("传阅成功！");
		}
		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getInfosByFrpSid(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getInfosByFrpSid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的runId
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		Map data = new HashMap();
		data.put("flowId", frp.getFlowRun().getFlowType().getSid());
		if(frp.getFlowPrcs()==null){
			data.put("prcsId", "0");
			//加签权限
			data.put("addPrcsUserPriv", 1);
		}else{
			data.put("prcsId", frp.getFlowPrcs().getSid());
			//加签权限
			data.put("addPrcsUserPriv", frp.getFlowPrcs().getAddPrcsUserPriv());
		}
		data.put("runId", frp.getFlowRun().getRunId());
		
		json.setRtData(data);
		return json;
	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#updateSaveStatus(int)
	 */
	@Override
	public void updateSaveStatus(int runId) {
		TeeFlowRun  run=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		if(run!=null){
			run.setIsSave(1);
	        simpleDaoSupport.update(run);
		}

	}

	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#intervention(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson intervention(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		int runId=TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int currFrpSid=TeeStringUtil.getInteger(request.getParameter("currFrpSid"), 0);
		int flowId=TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		
		int  toFpSid=TeeStringUtil.getInteger(request.getParameter("toFpSid"), 0);
		int userId=TeeStringUtil.getInteger(request.getParameter("userId"),0);
		String userIds=TeeStringUtil.getString(request.getParameter("userIds"));
		
		TeeFlowProcess toFp=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,toFpSid);
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		TeeFlowRunPrcs currFrp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,currFrpSid);
		TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,flowId);
		
		//获取当前改流程   prcsId流程序号(累加)的最大值
		int prcsId = 0;
		if(toFp.getPrcsType()!=5){//非聚合节点实例
			prcsId = (int) flowRunPrcsDao.getMaxPrcsId(runId)+1;
		}else{//聚合节点实例
			prcsId = flowRunPrcsDao.getFreeFlowMaxPrcsId(runId)+1;
		}
		
		if(toFp!=null){
			if(toFp.getOpFlag()==1){//指定主办人
			    TeePerson user=(TeePerson) simpleDaoSupport.get(TeePerson.class,userId);
			    
			    TeeFlowRunPrcs frp=new TeeFlowRunPrcs();
				
			    frp.setCreateTime(Calendar.getInstance());
				frp.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
				frp.setFlag(1);
				frp.setFlowPrcs(toFp);
				frp.setFlowRun(flowRun);
				frp.setFlowType(flowType);
				frp.setOpFlag(toFp.getOpFlag());
				frp.setTopFlag(1);
				if(currFrp!=null&&currFrp.getFlowPrcs()!=null){
					frp.setParent(currFrp.getFlowPrcs().getSid()+"");
				}
				frp.setPrcsId(prcsId);
				frp.setPrcsUser(user);
				
				simpleDaoSupport.save(frp);
				
				
				//消息提醒
				Map requestData = new HashMap();
				requestData.put("content",loginUser.getUserName()+"通过强制干预，将您设置为 ["+flowRun.getRunName()+"] 流程的 ["+toFp.getPrcsName()+"]步骤的办理人，请及时办理。" );
				requestData.put("userListIds",userId );
				requestData.put("moduleNo", "006" );
				requestData.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?runId="+runId+"&frpSid="+frp.getSid()+"&flowId="+flowId+"&view=1");
				smsManager.sendSms(requestData, loginUser);
				
			}else{//无主办会签   先接收为主办
				TeeFlowRunPrcs flowRunPrcs=null;
				List<TeePerson> userList=personService.getPersonByUuids(userIds);
				if(userList!=null&&userList.size()>0){
					for (TeePerson teePerson : userList) {
						flowRunPrcs=new TeeFlowRunPrcs();
						flowRunPrcs.setCreateTime(Calendar.getInstance());
						flowRunPrcs.setCreateTimeStamp(Calendar.getInstance().getTime().getTime());
						flowRunPrcs.setFlag(1);
						flowRunPrcs.setFlowPrcs(toFp);
						flowRunPrcs.setFlowRun(flowRun);
						flowRunPrcs.setFlowType(flowType);
						flowRunPrcs.setOpFlag(toFp.getOpFlag());
						flowRunPrcs.setTopFlag(0);
						if(currFrp!=null&&currFrp.getFlowPrcs()!=null){
							flowRunPrcs.setParent(currFrp.getFlowPrcs().getSid()+"");
						}
						flowRunPrcs.setPrcsId(prcsId);
						flowRunPrcs.setPrcsUser(teePerson);
						
						simpleDaoSupport.save(flowRunPrcs);
						
						
						//消息提醒
						Map requestData = new HashMap();
						requestData.put("content",loginUser.getUserName()+"通过强制干预，将您设置为 ["+flowRun.getRunName()+"] 流程的 ["+toFp.getPrcsName()+"]步骤的办理人，请及时办理。" );
						requestData.put("userListIds",teePerson.getUuid() );
						requestData.put("moduleNo", "006" );
						requestData.put("remindUrl", "/system/core/workflow/flowrun/prcs/index.jsp?runId="+runId+"&frpSid="+flowRunPrcs.getSid()+"&flowId="+flowId+"&view=1");
						smsManager.sendSms(requestData, loginUser);
					}
					
					
					
				}
				
			}
			
			
			//将原来的步骤设置成已办结      办理类型 1：指定主办人 2：无主办会签 3：先接受为主办人
			simpleDaoSupport.executeNativeUpdate(" update flow_run_prcs set flag=4 where run_Id=? and flow_prcs=? and prcs_Id=? ", new Object[]{runId,currFrp.getFlowPrcs().getSid(),currFrp.getPrcsId()});
			
			
			
		}
		
		
		
		json.setRtState(true);
	
		return json;
	}

	
	
	//处理列表控件   将列表控件中的数据插入中间表
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#saveXlistData(int, int, java.util.Map)
	 */
	@Override
	public void saveXlistData(int runId, int flowId,Map requestData) {
		String dialect=TeeSysProps.getDialect();
		//根据flowId   获取表单信息
		TeeFlowRun  flowrun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		TeeForm form=flowrun.getForm();
		List<TeeFormItem> formItems=form.getFormItems();
		
		//创建一个集合存放是xlist的FormItem
		List<TeeFormItem> xlistItems=new ArrayList<TeeFormItem>();
		for (TeeFormItem teeFormItem : formItems) {
			if("xlist".equals(teeFormItem.getXtype())){
				xlistItems.add(teeFormItem);
			}
		}
		
		if(xlistItems!=null&&xlistItems.size()>0){
			for (TeeFormItem item : xlistItems) {
				TeeHTMLImgTag htmlImgTag = new TeeHTMLImgTag();
				htmlImgTag.analyse(item.getContent());
				
				String model=item.getModel();
				List<Map<String,String>> modelList=TeeJsonUtil.JsonStr2MapList(model);
				//用来存放表头和字段名称之间的对应关系
				Map<String,String> relatedMap=new HashMap<String, String>();
				for (Map<String,String> m:modelList) {
					relatedMap.put(m.get("title"),m.get("fieldName"));
				}
				
				
				String  tableName=TeeStringUtil.getString(htmlImgTag.getAttributes().get("tablename"));
				//获取原来该item的值
				Map oldDataMap=workflowHelper.getFlowRunData(runId);
			    String oldData=TeeStringUtil.getString(oldDataMap.get("DATA_"+item.getItemId()));
			    //获取前台新传来的值
			    String newData=TeeStringUtil.getString(requestData.get("DATA_"+item.getItemId()));
			   
			    //不相等   说明列表数据发生了改变  则进行操作
			    if(!"".equals(newData) && !newData.equals(oldData)){
			    	//首先    先清除中间表中之前相关的数据
			    	simpleDaoSupport.executeNativeUpdate(" delete from "+tableName+" where RUN_ID="+runId,null);
			        //插入新的数据
			    	List<Map<String, String>>  dataList=TeeJsonUtil.JsonStr2MapList(newData);
			        
			    	if(dataList!=null&&dataList.size()>0){
			    		for (Map<String, String> map : dataList) {
							if("mysql".equals(dialect)||"sqlserver".equals(dialect)){
								String sql=" insert into "+tableName+"(RUN_ID,FLOW_ID";
								String values="values ("+runId+","+flowId;
								for (String key:map.keySet()) {
									if(!TeeUtility.isNullorEmpty(relatedMap.get(key))){
										sql+=","+relatedMap.get(key).toUpperCase();
										values+=",'"+map.get(key)+"'";
									}
								}
								
								sql=sql+")"+values+")";
								simpleDaoSupport.executeNativeUpdate(sql, null);
							}else if("oracle".equals(dialect)){
								String sql=" insert into "+tableName+"(SID,RUN_ID,FLOW_ID";
								String values="values ("+tableName+"_SEQ"+".nextval,"+runId+","+flowId;
								for (String key:map.keySet()) {
									if(!TeeUtility.isNullorEmpty(relatedMap.get(key))){
										sql+=","+relatedMap.get(key).toUpperCase();
										values+=",'"+map.get(key)+"'";
									}
								}
								
								sql=sql+")"+values+")";
								simpleDaoSupport.executeNativeUpdate(sql, null);
							}
						}	
			    	}
			    }
			}
		}
		
		
		
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowHandlerInterface#getFlowIdByRunId(int)
	 */
	@Override
	public int getFlowIdByRunId(int runId) {
		TeeFlowRun flowRun=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		TeeFlowType type=flowRun.getFlowType();	
		return type.getSid();
	}
	
	
	

}