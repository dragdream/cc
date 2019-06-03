package com.tianee.oa.core.xt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.xt.bean.TeeXTReply;
import com.tianee.oa.core.xt.bean.TeeXTRun;
import com.tianee.oa.core.xt.bean.TeeXTRunPrcs;
import com.tianee.oa.core.xt.model.TeeXTRunPrcsModel;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeXTRunPrcsService extends TeeBaseService{

	@Autowired
	private TeeAttachmentService attachmentService;
	
	
	/**
	 * 获取处理明细列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getHandleDetail(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		int xtRunId=TeeStringUtil.getInteger(request.getParameter("xtRunId"), 0);
		
		List param=new ArrayList();
		String hql=" from TeeXTRunPrcs where  xtRun.sid=? ";
		param.add(xtRunId);
		
		long total=simpleDaoSupport.count("select count(sid) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeXTRunPrcs> list=simpleDaoSupport.pageFind(hql+" order by handleTime desc,receiveTime asc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		
		List<TeeXTRunPrcsModel> rows=new ArrayList<TeeXTRunPrcsModel>();
		TeeXTRunPrcsModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeXTRunPrcs prcs : list) {
				model=parseToModel(prcs);
				rows.add(model);
			}
		}
	
		json.setRows(rows);
		return json;
	}

	
	
	/**
	 * 实体类转换成model
	 * @param prcs
	 * @return
	 */
	private TeeXTRunPrcsModel parseToModel(TeeXTRunPrcs prcs) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeXTRunPrcsModel model=new TeeXTRunPrcsModel();
		BeanUtils.copyProperties(prcs, model);
        if(prcs.getHandleTime()!=null){
        	model.setHandleTimeStr(sdf.format(prcs.getHandleTime().getTime()));
        }
		
        if(prcs.getPrcsUser()!=null){
        	model.setPrcsUserId(prcs.getPrcsUser().getUuid());
        	model.setPrcsUserName(prcs.getPrcsUser().getUserName());
        }
        
        if(prcs.getReceiveTime()!=null){
        	model.setReceiveTimeStr(sdf.format(prcs.getReceiveTime().getTime()));
        }
        
        if(prcs.getStatus()==0){
        	model.setStatusDesc("未接收");
        }else if(model.getStatus()==1){
        	model.setStatusDesc("已接收");
        }else if(model.getStatus()==2){
        	model.setStatusDesc("已办结");
        }
        
        if(prcs.getXtRun()!=null&&prcs.getXtRun().getSendTime()!=null){
        	model.setSendTimeStr(sdf.format(prcs.getXtRun().getSendTime().getTime()));
        }
        
        if(prcs.getXtRun()!=null){
        	
        	model.setOptPriv(prcs.getXtRun().getOptPriv());
        	model.setImportantLevel(prcs.getXtRun().getImportantLevel());
        	model.setXtRunId(prcs.getXtRun().getSid());
        	model.setSubject(prcs.getXtRun().getSubject());
        	if(prcs.getXtRun().getCreateUser()!=null){
        		model.setCreateUserName(prcs.getXtRun().getCreateUser().getUserName());
        	}
        	//事项状态处理
        	if(prcs.getXtRun().getStatus()==0){
        		model.setRunStatusDesc("未发送");
        	}else if(prcs.getXtRun().getStatus()==1){
        		model.setRunStatusDesc("未结束");
        	}else if(prcs.getXtRun().getStatus()==2){
        		model.setRunStatusDesc("已结束");
        	}
        	model.setDocType(prcs.getXtRun().getDocType());
        	//附件数量
        	List<TeeAttachment> list=attachmentService.getAttaches(TeeAttachmentModelKeys.XT_ATTACH, prcs.getXtRun().getSid()+"");
        	if(list!=null&&list.size()>0){
        		model.setRunAttNum(list.size());
        	}else{
        		model.setRunAttNum(0);
        	}
        	
        	if(prcs.getXtRun().getParent()!=null&&prcs.getXtRun().getParent().getCreateUser()!=null){
        		model.setParentCreateUserName(prcs.getXtRun().getParent().getCreateUser().getUserName());
        	}
        	
        }
        //处理时长
        if(prcs.getReceiveTime()!=null&&prcs.getHandleTime()!=null){
        	model.setPassedTime(TeeDateUtil.getPassedTimeDesc(prcs.getReceiveTime(), prcs.getHandleTime()));
        }else{
        	model.setPassedTime("");
        }
        
		
        if(model.getOpinionType()==1){
        	model.setOpinionTypeDesc("已阅");
        }else if(model.getOpinionType()==2){
        	model.setOpinionTypeDesc("同意");
        }else if(model.getOpinionType()==3){
        	model.setOpinionTypeDesc("不同意");
        }
        
        //处理附件
        List<TeeAttachmentModel> attList=attachmentService.getAttacheModels("xtprcsattach", prcs.getSid()+"");
        model.setAttList(attList);
        
        //获取回复的数量
        List<TeeXTReply> replyList=simpleDaoSupport.executeQuery(" from TeeXTReply where xtRunPrcs.sid=?  ",new Object[]{ prcs.getSid()});
        if(replyList!=null&&replyList.size()>0){
        	model.setReplyNum(replyList.size());
        }else{
        	model.setReplyNum(0);
        }
        
        return model;
	}



	/**
	 * 获取已经处理完的数目  和  列表
	 * @param request
	 * @return
	 */
	public TeeJson getHandledListAndNum(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取事项主键
		int xtRunId=TeeStringUtil.getInteger(request.getParameter("xtRunId"), 0);
		//获取处理完的列表
		List<TeeXTRunPrcs> prcsList=simpleDaoSupport.executeQuery(" from TeeXTRunPrcs where xtRun.sid=? and status=2 ", new Object[]{xtRunId});
		Map map=new HashMap();
		List<TeeXTRunPrcsModel> modelList=new ArrayList<TeeXTRunPrcsModel>();
		TeeXTRunPrcsModel model=null;
		if(prcsList!=null&&prcsList.size()>0){
			for (TeeXTRunPrcs prcs : prcsList) {
				model=parseToModel(prcs);
				modelList.add(model);		
			}
			 map.put("num",prcsList.size());
			 map.put("list",modelList);
		}else{
		   map.put("num",0);
		   map.put("list",modelList);
		}
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}



	
	/**
	 * 获取待办列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getDaiBanList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		
		String subject=TeeStringUtil.getString(request.getParameter("subject"));
		int createUserId=TeeStringUtil.getInteger(request.getParameter("createUserId"), 0);
		int importantLevel=TeeStringUtil.getInteger(request.getParameter("importantLevel"), 0);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), -1);
		//发起时间
		String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		//接收时间
		String startTime1=TeeStringUtil.getString(request.getParameter("startTime1"));
		String endTime1=TeeStringUtil.getString(request.getParameter("endTime1"));
		
		List param=new ArrayList();
		param.add(loginUser.getUuid());
		String hql=" from TeeXTRunPrcs where prcsUser.uuid=? and  status in (0,1)  and  deleteStatus!=1 ";
		
		if(!TeeUtility.isNullorEmpty(subject)){
			hql+=" and xtRun.subject like ? ";
			param.add("%"+subject+"%");
		}
		
		if(createUserId!=0){
			hql+=" and xtRun.createUser.uuid=? ";
			param.add(createUserId);
		}
		
		if(importantLevel!=0){
			hql+=" and xtRun.importantLevel=?  ";
			param.add(importantLevel);
		}
		
		if(status!=-1){
			hql+=" and status=?  ";
			param.add(status);
		}
		
		if(!TeeUtility.isNullorEmpty(startTime)){
			startTime=startTime+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime);
			hql+=" and xtRun.sendTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			endTime=endTime+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime);
			hql+=" and xtRun.sendTime<=? ";
			param.add(e);	
		}
		
		
		if(!TeeUtility.isNullorEmpty(startTime1)){
			startTime1=startTime1+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime1);
			hql+=" and receiveTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime1)){
			endTime1=endTime1+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime1);
			hql+=" and receiveTime<=? ";
			param.add(e);	
		}
		
		long total=simpleDaoSupport.count("select count(sid) "+hql,param.toArray());
		json.setTotal(total);
		List<TeeXTRunPrcs> list=simpleDaoSupport.pageFind(hql+" order by xtRun.sendTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(),param.toArray());
		
		List<TeeXTRunPrcsModel> rows=new ArrayList<TeeXTRunPrcsModel>();
		TeeXTRunPrcsModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeXTRunPrcs prcs : list) {
				model=parseToModel(prcs);
				rows.add(model);
			}
		}
	
		json.setRows(rows);
		return json;
	}



	/**
	 * 删除
	 * @param request
	 * @return
	 */
	public TeeJson delete(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,sid);
		if(prcs!=null){
			prcs.setDeleteStatus(1);
			simpleDaoSupport.update(prcs);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}
		return json;
	}



	
	/**
	 * 批量删除
	 * @param request
	 * @return
	 */
	public TeeJson delBatch(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		String[] idArray=ids.split(",");
		if(idArray!=null&&idArray.length>0){
			for (String str : idArray) {
				int sid=TeeStringUtil.getInteger(str,0);
				TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,sid);
			    if(prcs!=null){
			    	prcs.setDeleteStatus(1);
			    	simpleDaoSupport.update(prcs);
			    }
			}
		}
		json.setRtState(true);
		return json;
	}



	/**
	 * 获取已办  未删除的列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getYiBanList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		
		String subject=TeeStringUtil.getString(request.getParameter("subject"));
		int createUserId=TeeStringUtil.getInteger(request.getParameter("createUserId"), 0);
		int importantLevel=TeeStringUtil.getInteger(request.getParameter("importantLevel"), 0);
		//发起时间
		String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		//处理时间
		String startTime1=TeeStringUtil.getString(request.getParameter("startTime1"));
		String endTime1=TeeStringUtil.getString(request.getParameter("endTime1"));
		//事项状态    0=保存未发送  1=已发送未终止         2=已发送已终止
		int status=TeeStringUtil.getInteger(request.getParameter("status"),-1);
		
		
		List param=new ArrayList();
		param.add(loginUser.getUuid());
		String hql=" from TeeXTRunPrcs where prcsUser.uuid=? and  status=2  and  deleteStatus!=1 ";
		
		if(!TeeUtility.isNullorEmpty(subject)){
			hql+=" and xtRun.subject like ? ";
			param.add("%"+subject+"%");
		}
		
		if(createUserId!=0){
			hql+=" and xtRun.createUser.uuid=? ";
			param.add(createUserId);
		}
		
		if(importantLevel!=0){
			hql+=" and xtRun.importantLevel=?  ";
			param.add(importantLevel);
		}
		
		if(status!=-1){
			hql+=" and xtRun.status=?  ";
			param.add(status);
		}
		
		if(!TeeUtility.isNullorEmpty(startTime)){
			startTime=startTime+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime);
			hql+=" and xtRun.sendTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			endTime=endTime+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime);
			hql+=" and xtRun.sendTime<=? ";
			param.add(e);	
		}
		
		//办理时间
		if(!TeeUtility.isNullorEmpty(startTime1)){
			startTime1=startTime1+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime1);
			hql+=" and handleTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime1)){
			endTime1=endTime1+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime1);
			hql+=" and handleTime<=? ";
			param.add(e);	
		}
		
		long total=simpleDaoSupport.count("select count(sid) "+hql,param.toArray());
		json.setTotal(total);
		List<TeeXTRunPrcs> list=simpleDaoSupport.pageFind(hql+" order by handleTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(),param.toArray());
		
		List<TeeXTRunPrcsModel> rows=new ArrayList<TeeXTRunPrcsModel>();
		TeeXTRunPrcsModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeXTRunPrcs prcs : list) {
				model=parseToModel(prcs);
				rows.add(model);
			}
		}
	
		json.setRows(rows);
		return json;
	}



	
	/**
	 * 任务接受
	 * @param request
	 * @return
	 */
	public TeeJson receive(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,sid);
		if(prcs!=null){
			if(prcs.getReceiveTime()==null){
				prcs.setReceiveTime(Calendar.getInstance());
				prcs.setStatus(1);
				simpleDaoSupport.update(prcs);
			}
		}
		return json;
	}



	/**
	 * 保存 或者  提交
	 * @param request
	 * @return
	 */
	public TeeJson saveOrSubmit(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeJson json=new TeeJson();
		int type=TeeStringUtil.getInteger(request.getParameter("type"),0);//1=保存  2=提交
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int smsRemind=TeeStringUtil.getInteger(request.getParameter("smsRemind"),0);
		int opinionType=TeeStringUtil.getInteger(request.getParameter("opinionType"), 0);
		String attachmentSidStr=TeeStringUtil.getString(request.getParameter("attachmentSidStr"));
		String opinionContent=TeeStringUtil.getString(request.getParameter("opinionContent"));
		
		TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,sid);
		if(prcs!=null){
			prcs.setSmsRemind(smsRemind);
			prcs.setOpinionType(opinionType);
			prcs.setOpinionContent(opinionContent);
			//处理附件
			List<TeeAttachment> attList=attachmentService.getAttachmentsByIds(attachmentSidStr);
			if(attList!=null&&attList.size()>0){
				for (TeeAttachment teeAttachment : attList) {
					teeAttachment.setModelId(prcs.getSid()+"");
					simpleDaoSupport.update(teeAttachment);
				}
			}
			
			//判断是提交  还是  保存
			if(type==2){//提交
				prcs.setHandleTime(Calendar.getInstance());
				prcs.setStatus(2);
				
				//判断该事项是否还存在其他未办理的   若不存在  则把事项状态修改为已终止
				TeeXTRun xtRun=prcs.getXtRun();
				String hql=" from TeeXTRunPrcs where xtRun.sid=? and  status in(0,1) and sid!=? ";
				List<TeeXTRunPrcs> prcsList=simpleDaoSupport.executeQuery(hql, new Object[]{xtRun.getSid(),prcs.getSid()});
				if(prcsList==null||prcsList.size()==0){
					xtRun.setStatus(2);
					simpleDaoSupport.update(xtRun);
				}
				
				//发短信
				if(smsRemind==1){
		    		if(!TeeUtility.isNullorEmpty(xtRun.getCreateUser().getMobilNo())){
		    			TeeSmsSendPhone sms=new TeeSmsSendPhone();
		    			sms.setContent(loginUser.getUserName()+"已办理完协同：《"+xtRun.getSubject()+"》");
		    		    sms.setFromId(loginUser.getUuid());
		    		    sms.setFromName(loginUser.getUserName());
		    		    sms.setPhone(xtRun.getCreateUser().getMobilNo());
		    		    sms.setSendFlag(0);
		    		    sms.setSendNumber(0);
		    		    sms.setSendTime(Calendar.getInstance());
		    		    sms.setToId(xtRun.getCreateUser().getUuid());
		    		    sms.setToName(xtRun.getCreateUser().getUserName());
		    		    
		    		    simpleDaoSupport.save(sms);
		    		}
		    	}
			}
			simpleDaoSupport.update(prcs);
			json.setRtState(true);
			
		}else{
			json.setRtState(false);
		}
		
		return json;
	}


    /**
     * 根据主键获取详情
     * @param request
     * @return
     */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	    TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,sid);
		if(prcs!=null){
			TeeXTRunPrcsModel model=parseToModel(prcs);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
		}
	    return json;
	}

}
