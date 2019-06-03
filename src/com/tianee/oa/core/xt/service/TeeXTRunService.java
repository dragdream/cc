package com.tianee.oa.core.xt.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.hibernate.engine.query.spi.ReturnMetadata;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.xt.bean.TeeXTReply;
import com.tianee.oa.core.xt.bean.TeeXTRun;
import com.tianee.oa.core.xt.bean.TeeXTRunPrcs;
import com.tianee.oa.core.xt.model.TeeXTRunModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeXTRunService extends TeeBaseService{

	@Autowired
	private TeePersonService personService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	/**
	 * 新建/编辑
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request,TeeXTRunModel model) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    TeeJson json=new TeeJson();
	    //获取当前登陆人
	    TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    //获取页面上传来的主键
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
	    TeeXTRun xtRun=null;
	    if(sid>0){//编辑
	    	xtRun=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
	    	xtRun.setAdvanceRemind(model.getAdvanceRemind());
	    	xtRun.setAutoStop(model.getAutoStop());
	    	xtRun.setContent(model.getContent());
	    	//xtRun.setCreateTime(Calendar.getInstance());
	    	//xtRun.setCreateUser(loginUser);
            xtRun.setDeadLine(model.getDeadLine());
            //到期时间
            String deadLineTimeStr=model.getDeadLineTimeStr();
            xtRun.setDeadLineTime(TeeDateUtil.parseCalendar(deadLineTimeStr));
            //获取正文
            int docAttachId=TeeStringUtil.getInteger(request.getParameter("docAttachId"), 0);
            TeeAttachment doc=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,docAttachId);
            xtRun.setDoc(doc);
            
            xtRun.setDocType(model.getDocType());
            xtRun.setImportantLevel(model.getImportantLevel());
            xtRun.setOptPriv(model.getOptPriv());
            xtRun.setRemark(model.getRemark());
            xtRun.setSmsRemind(model.getSmsRemind());
            xtRun.setStatus(model.getStatus());
            xtRun.setSubject(model.getSubject());
            xtRun.setUserIds(model.getUserIds());
	    	//判断是保存还是发送
            if(model.getStatus()==1){
            	xtRun.setSendTime(Calendar.getInstance());
            }
            
            //保存到数据库
            simpleDaoSupport.save(xtRun);
            
            //处理附件
            List<TeeAttachment> attList=attachmentService.getAttachmentsByIds(model.getAttachmentSidStr());
            if(attList!=null&&attList.size()>0){
            	for (TeeAttachment teeAttachment : attList) {
            		teeAttachment.setModelId(xtRun.getSid()+"");
            		simpleDaoSupport.update(teeAttachment);
				}
            }
            
            if(model.getStatus()==1){//发送（生成中间表数据）  
            	List<TeePerson>prcsUserList=personService.getPersonByUuids(model.getUserIds());
            	if(prcsUserList!=null&&prcsUserList.size()>0){
            		for (TeePerson teePerson : prcsUserList) {
						TeeXTRunPrcs prcs=new TeeXTRunPrcs();
						prcs.setOpinionType(0);
						prcs.setPrcsUser(teePerson);
						prcs.setStatus(0);
						prcs.setXtRun(xtRun);
						prcs.setDeleteStatus(0);
						prcs.setSmsRemind(0);
						simpleDaoSupport.save(prcs);
						
						//发送系统消息
		            	Map requestData1 = new HashMap();
				    	requestData1.put("content", loginUser.getUserName()+"发起协同：《"+model.getSubject()+"》");
				    	requestData1.put("userListIds", teePerson.getUuid());
				    	requestData1.put("moduleNo", "100");
				    	requestData1.put("remindUrl","/system/core/xt/handle.jsp?xtRunId="+xtRun.getSid()+"&prcsId="+prcs.getSid());
				    	smsManager.sendSms(requestData1, loginUser);
				    	
				    	
				    	//发短信
				    	if(xtRun.getSmsRemind()==1){
				    		if(!TeeUtility.isNullorEmpty(teePerson.getMobilNo())){
				    			TeeSmsSendPhone sms=new TeeSmsSendPhone();
				    			sms.setContent(loginUser.getUserName()+"发起协同：《"+model.getSubject()+"》");
				    		    sms.setFromId(loginUser.getUuid());
				    		    sms.setFromName(loginUser.getUserName());
				    		    sms.setPhone(teePerson.getMobilNo());
				    		    sms.setSendFlag(0);
				    		    sms.setSendNumber(0);
				    		    sms.setSendTime(Calendar.getInstance());
				    		    sms.setToId(teePerson.getUuid());
				    		    sms.setToName(teePerson.getUserName());
				    		    
				    		    simpleDaoSupport.save(sms);
				    		}
				    	}
					}
            	}
            	
            	
            }
           json.setRtData(xtRun.getSid());
	    }else{//新建
	    	xtRun=new TeeXTRun();
	    	xtRun.setAdvanceRemind(model.getAdvanceRemind());
	    	xtRun.setAutoStop(model.getAutoStop());
	    	xtRun.setContent(model.getContent());
	    	xtRun.setCreateTime(Calendar.getInstance());
	    	xtRun.setCreateUser(loginUser);
            xtRun.setDeadLine(model.getDeadLine());
            //到期时间
            String deadLineTimeStr=model.getDeadLineTimeStr();
            xtRun.setDeadLineTime(TeeDateUtil.parseCalendar(deadLineTimeStr));
            
            //获取正文
            int docAttachId=TeeStringUtil.getInteger(request.getParameter("docAttachId"), 0);
            TeeAttachment doc=(TeeAttachment) simpleDaoSupport.get(TeeAttachment.class,docAttachId);
            xtRun.setDoc(doc);
            
            xtRun.setDocType(model.getDocType());
            xtRun.setImportantLevel(model.getImportantLevel());
            xtRun.setOptPriv(model.getOptPriv());
            xtRun.setRemark(model.getRemark());
            xtRun.setSmsRemind(model.getSmsRemind());
            xtRun.setStatus(model.getStatus());
            xtRun.setSubject(model.getSubject());
            xtRun.setUserIds(model.getUserIds());
            
            //判断是保存还是发送
            if(model.getStatus()==1){
            	xtRun.setSendTime(Calendar.getInstance());
            }
            
            //保存到数据库
            simpleDaoSupport.save(xtRun);
            
            //处理附件
            List<TeeAttachment> attList=attachmentService.getAttachmentsByIds(model.getAttachmentSidStr());
            if(attList!=null&&attList.size()>0){
            	for (TeeAttachment teeAttachment : attList) {
            		teeAttachment.setModelId(xtRun.getSid()+"");
            		simpleDaoSupport.update(teeAttachment);
				}
            }
            
            if(model.getStatus()==1){//发送（生成中间表数据）
            	List<TeePerson>prcsUserList=personService.getPersonByUuids(model.getUserIds());
            	if(prcsUserList!=null&&prcsUserList.size()>0){
            		for (TeePerson teePerson : prcsUserList) {
						TeeXTRunPrcs prcs=new TeeXTRunPrcs();
						prcs.setOpinionType(0);
						prcs.setPrcsUser(teePerson);
						prcs.setStatus(0);
						prcs.setXtRun(xtRun);
						prcs.setDeleteStatus(0);
						prcs.setSmsRemind(0);
						simpleDaoSupport.save(prcs);
						
						
						//发送系统消息
		            	Map requestData1 = new HashMap();
				    	requestData1.put("content", loginUser.getUserName()+"发起协同：《"+model.getSubject()+"》");
				    	requestData1.put("userListIds", teePerson.getUuid());
				    	requestData1.put("moduleNo", "100");
				    	requestData1.put("remindUrl","/system/core/xt/handle.jsp?xtRunId="+xtRun.getSid()+"&prcsId="+prcs.getSid());
				    	smsManager.sendSms(requestData1, loginUser);
				    	
				    	
				    	//发短信
				    	if(xtRun.getSmsRemind()==1){
				    		if(!TeeUtility.isNullorEmpty(teePerson.getMobilNo())){
				    			TeeSmsSendPhone sms=new TeeSmsSendPhone();
				    			sms.setContent(loginUser.getUserName()+"发起协同：《"+model.getSubject()+"》");
				    		    sms.setFromId(loginUser.getUuid());
				    		    sms.setFromName(loginUser.getUserName());
				    		    sms.setPhone(teePerson.getMobilNo());
				    		    sms.setSendFlag(0);
				    		    sms.setSendNumber(0);
				    		    sms.setSendTime(Calendar.getInstance());
				    		    sms.setToId(teePerson.getUuid());
				    		    sms.setToName(teePerson.getUserName());
				    		    
				    		    simpleDaoSupport.save(sms);
				    		}
				    	}
						
					}
            	}
            }
           json.setRtData(xtRun.getSid());
	    }
	    json.setRtState(true);
	    
	    
	    
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
		TeeXTRun xtRun=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
		if(xtRun!=null){
			TeeXTRunModel model=parseToModel(xtRun);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");
		}
		return json;
	}



	/**
	 * 实体类转换成model类型
	 * @param xtRun
	 * @return
	 */
	private TeeXTRunModel parseToModel(TeeXTRun xtRun) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeXTRunModel model=new TeeXTRunModel();
		BeanUtils.copyProperties(xtRun, model);
		//处理创建时间
		if(xtRun.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(xtRun.getCreateTime().getTime()));
		}
		//处理创建人
		if(xtRun.getCreateUser()!=null){
			model.setCreateUserId(xtRun.getCreateUser().getUuid());
			model.setCreateUserName(xtRun.getCreateUser().getUserName());
			
			model.setAvatar(TeeStringUtil.getInteger(xtRun.getCreateUser().getAvatar(), 0));
			
			//所属部门
			if(xtRun.getCreateUser().getDept()!=null){
				model.setCreateUserDeptName(xtRun.getCreateUser().getDept().getDeptName());	
			}
			//所属角色
			if(xtRun.getCreateUser().getUserRole()!=null){
			    model.setCreateUserRoleName(xtRun.getCreateUser().getUserRole().getRoleName());	
			}
			
		}
		//处理到期时间
		if(xtRun.getDeadLineTime()!=null){
			model.setDeadLineTimeStr(sdf.format(xtRun.getDeadLineTime().getTime()));
		}
		//处理正文附件
		if(xtRun.getDoc()!=null){
			model.setDocAttachId(xtRun.getDoc().getSid());
		}
		//处理办理人员
		if(!TeeUtility.isNullorEmpty(xtRun.getUserIds())){
		   String userNames="";
		   List<TeePerson>prcsUserList=personService.getPersonByUuids(xtRun.getUserIds());
		   if(prcsUserList!=null&&prcsUserList.size()>0){
			   for (TeePerson teePerson : prcsUserList) {
				   userNames+=teePerson.getUserName()+",";
			    }
		   }
		   
		   if(userNames.endsWith(",")){
			   userNames=userNames.substring(0,userNames.length()-1);
		   }
		   model.setUserNames(userNames);
		}
		//处理附件
		List<TeeAttachmentModel> attList=attachmentService.getAttacheModels("xtattach", xtRun.getSid()+"");
		model.setAttList(attList);
		//处理事项级别
		if(xtRun.getImportantLevel()==1){
			model.setImportantLevelStr("普通");
		}else if(xtRun.getImportantLevel()==2){
			model.setImportantLevelStr("重要");
		}else if(xtRun.getImportantLevel()==3){
			model.setImportantLevelStr("非常重要");
		}
		
		//处理发送时间
		if(xtRun.getSendTime()!=null){
			model.setSendTimeStr(sdf.format(xtRun.getSendTime().getTime()));
		}
		
		if(xtRun.getParent()!=null&&xtRun.getParent().getCreateUser()!=null){
			model.setParentCreateUserName(xtRun.getParent().getCreateUser().getUserName());
		}else{
			model.setParentCreateUserName("");
		}
		return model;
	}



	
	/**
	 * 根据状态获取列表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getDaiFaList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), 0);//0=待发
		String subject=TeeStringUtil.getString(request.getParameter("subject"));//标题
		int importantLevel=TeeStringUtil.getInteger(request.getParameter("importantLevel"), 0);//事项级别
		
		String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		
		List param=new ArrayList();
		String hql=" from TeeXTRun where createUser.uuid=? and status=? ";
		param.add(loginUser.getUuid());
		param.add(status);
		if(!TeeUtility.isNullorEmpty(subject)){
			hql+=" and subject like ? ";
			param.add("%"+subject+"%");
		}
		
		if(importantLevel!=0){
			hql+=" and importantLevel=? ";
			param.add(importantLevel);
		}
		
		if(!TeeUtility.isNullorEmpty(startTime)){
			startTime=startTime+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime);
			hql+=" and createTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			endTime=endTime+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime);
			hql+=" and createTime<=? ";
			param.add(e);	
		}
		
		long total=simpleDaoSupport.count("select count(sid) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeXTRun> list=simpleDaoSupport.pageFind(hql+" order by createTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		
		List<TeeXTRunModel> rows=new ArrayList<TeeXTRunModel>();
		TeeXTRunModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeXTRun teeXTRun : list) {
				model=parseToModel(teeXTRun);
				rows.add(model);
			}
		}
	
		json.setRows(rows);
		return json;
	}



	/**
	 * 批量删除
	 * @param request
	 * @return
	 */
	public TeeJson delete(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String ids=TeeStringUtil.getString(request.getParameter("ids"));
		String idArray[]=ids.split(",");
		TeeXTRun xtRun=null;
		for (String idStr : idArray) {
			int sid=TeeStringUtil.getInteger(idStr,0);
			xtRun=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
		    if(xtRun!=null){
		    	//删除字表
		    	simpleDaoSupport.executeNativeUpdate(" delete from xt_run_prcs where xt_run_id=? ",new Object[]{xtRun.getSid()} );
		    	//删除主表
		    	simpleDaoSupport.deleteByObj(xtRun);
		    }
		}
		json.setRtState(true);
		json.setRtMsg("删除成功！");
		return json;
	}



	/**
	 * 发送
	 * @param request
	 * @return
	 */
	public TeeJson send(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeXTRun xtRun=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
		if(xtRun!=null){
			//修改事项的状态
			xtRun.setStatus(1);
			//设置发送时间
			xtRun.setSendTime(Calendar.getInstance());
			//往中间表插入记录
			List<TeePerson>prcsUserList=personService.getPersonByUuids(xtRun.getUserIds());
        	if(prcsUserList!=null&&prcsUserList.size()>0){
        		for (TeePerson teePerson : prcsUserList) {
					TeeXTRunPrcs prcs=new TeeXTRunPrcs();
					prcs.setOpinionType(0);
					prcs.setPrcsUser(teePerson);
					prcs.setStatus(0);
					prcs.setXtRun(xtRun);
					prcs.setDeleteStatus(0);
					prcs.setSmsRemind(0);
					simpleDaoSupport.save(prcs);
					
					//发送系统消息
	            	Map requestData1 = new HashMap();
			    	requestData1.put("content", loginUser.getUserName()+"发起协同：《"+xtRun.getSubject()+"》");
			    	requestData1.put("userListIds", teePerson.getUuid());
			    	requestData1.put("moduleNo", "100");
			    	requestData1.put("remindUrl","/system/core/xt/handle.jsp?xtRunId="+xtRun.getSid()+"&prcsId="+prcs.getSid());
			    	smsManager.sendSms(requestData1, loginUser);
			    	
			    	
			    	//发短信
			    	if(xtRun.getSmsRemind()==1){
			    		if(!TeeUtility.isNullorEmpty(teePerson.getMobilNo())){
			    			TeeSmsSendPhone sms=new TeeSmsSendPhone();
			    			sms.setContent(loginUser.getUserName()+"发起协同：《"+xtRun.getSubject()+"》");
			    		    sms.setFromId(loginUser.getUuid());
			    		    sms.setFromName(loginUser.getUserName());
			    		    sms.setPhone(teePerson.getMobilNo());
			    		    sms.setSendFlag(0);
			    		    sms.setSendNumber(0);
			    		    sms.setSendTime(Calendar.getInstance());
			    		    sms.setToId(teePerson.getUuid());
			    		    sms.setToName(teePerson.getUserName());
			    		    
			    		    simpleDaoSupport.save(sms);
			    		}
			    	}
					
				}
        	}
        	
        	json.setRtState(true);	
		}else{
			json.setRtState(false);
            json.setRtMsg("该事项已不存在!");
		}
		return json;
	}


    /**
     * 获取已发列表
     * @param request
     * @param dm
     * @return
     */
	public TeeEasyuiDataGridJson getYiFaList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int status=TeeStringUtil.getInteger(request.getParameter("status"), -1);// 1=已发未终止  2=已发已终止
		String subject=TeeStringUtil.getString(request.getParameter("subject"));//标题
		int importantLevel=TeeStringUtil.getInteger(request.getParameter("importantLevel"), 0);//事项级别
		
		//发起时间
		String startTime=TeeStringUtil.getString(request.getParameter("startTime"));
		String endTime=TeeStringUtil.getString(request.getParameter("endTime"));
		
		//流程期限
		String startTime1=TeeStringUtil.getString(request.getParameter("startTime1"));
		String endTime1=TeeStringUtil.getString(request.getParameter("endTime1"));
		
		List param=new ArrayList();
		String hql=" from TeeXTRun where createUser.uuid=? and status in (1,2)  ";
		param.add(loginUser.getUuid());
		if(status!=-1){
			hql+=" and status=? ";
			param.add(status);
		}
		
		if(!TeeUtility.isNullorEmpty(subject)){
			hql+=" and subject like ? ";
			param.add("%"+subject+"%");
		}
		
		if(importantLevel!=0){
			hql+=" and importantLevel=? ";
			param.add(importantLevel);
		}
		
		if(!TeeUtility.isNullorEmpty(startTime)){
			startTime=startTime+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime);
			hql+=" and sendTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime)){
			endTime=endTime+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime);
			hql+=" and sendTime<=? ";
			param.add(e);	
		}
		
		//流程期限
		if(!TeeUtility.isNullorEmpty(startTime1)){
			startTime1=startTime1+" 00:00:00";
			Calendar s=TeeDateUtil.parseCalendar(startTime1);
			hql+=" and deadLineTime>=? ";
			param.add(s);	
		}
		
		if(!TeeUtility.isNullorEmpty(endTime1)){
			endTime1=endTime1+" 23:59:59";
			Calendar e=TeeDateUtil.parseCalendar(endTime1);
			hql+=" and deadLineTime<=? ";
			param.add(e);	
		}
		
		long total=simpleDaoSupport.count("select count(sid) "+hql, param.toArray());
		json.setTotal(total);
		List<TeeXTRun> list=simpleDaoSupport.pageFind(hql+" order by sendTime desc ",(dm.getPage() - 1) * dm.getRows(),dm.getRows(), param.toArray());
		
		List<TeeXTRunModel> rows=new ArrayList<TeeXTRunModel>();
		TeeXTRunModel model=null;
		if(list!=null&&list.size()>0){
			for (TeeXTRun teeXTRun : list) {
				model=parseToModel(teeXTRun);
				rows.add(model);
			}
		}
	
		json.setRows(rows);
		return json;
	}



	/**
	 * 撤销
	 * @param request
	 * @return
	 */
	public TeeJson revoke(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
        TeeXTRun xtRun=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
        if(xtRun!=null){
        	//删除子表数据
	    	simpleDaoSupport.executeNativeUpdate(" delete from xt_run_prcs where xt_run_id=? ",new Object[]{xtRun.getSid()} );
            //修改主表状态和发送时间
	    	xtRun.setStatus(0);
	    	xtRun.setSendTime(null);
	    	simpleDaoSupport.update(xtRun);
            json.setRtState(true);
        }else{
        	json.setRtState(false);
        	json.setRtMsg("该事项已不存在！");
        }
		return json;
	}



	/**
	 * 删除指定附件
	 * @param request
	 * @return
	 */
	public TeeJson deleteAttach(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取附件attSids
		String attSids=TeeStringUtil.getString(request.getParameter("attSids"));
		if(attSids.endsWith(",")){
			attSids=attSids.substring(0,attSids.length()-1);
		}
		List<TeeAttachment> list=attachmentService.getAttachmentsByIds(attSids);
		if(list!=null&&list.size()>0){
			for (TeeAttachment teeAttachment : list) {
				attachmentService.deleteAttach(teeAttachment);
			}
		}
		json.setRtState(true);
		return json;
	}



	
	/**
	 * 标准正文修改正文
	 * @param request
	 * @return
	 */
	public TeeJson updateContent(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String content=TeeStringUtil.getString(request.getParameter("content"));
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeXTRun run=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
		if(run!=null){
			run.setContent(content);
		    simpleDaoSupport.update(run);
		    json.setRtState(true);
		}else{
			json.setRtState(false);
		}
		return json;
	}



	
	/**
	 * 转发
	 * @param request
	 * @return
	 */
	public TeeJson repeat(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
	    TeeJson json=new TeeJson();
	    int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"), 0);
	    String remark=TeeStringUtil.getString(request.getParameter("remark"));
		String attachmentSidStr=TeeStringUtil.getString(request.getParameter("attachmentSidStr"));
		String userIds=TeeStringUtil.getString(request.getParameter("userIds"));
		
		int repeatFy=TeeStringUtil.getInteger(request.getParameter("repeatFy"),0);  
		int repeatYj=TeeStringUtil.getInteger(request.getParameter("repeatYj"),0);
		
		//获取父级的信息
		TeeXTRun parent=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,parentId);
		
		TeeXTRun run=new TeeXTRun();
		run.setAdvanceRemind(parent.getAdvanceRemind());
		run.setAutoStop(parent.getAutoStop());
		run.setContent(parent.getContent());
		run.setCreateTime(Calendar.getInstance());
		run.setCreateUser(loginUser);
		run.setDeadLine(parent.getDeadLine());
		run.setDeadLineTime(parent.getDeadLineTime());
		
		run.setDocType(parent.getDocType());
		run.setImportantLevel(parent.getImportantLevel());
		run.setOptPriv(parent.getOptPriv());
		run.setParent(parent);
		run.setRemark(remark);
		run.setRepeatFy(repeatFy);
		run.setRepeatYj(repeatYj);
		run.setSendTime(Calendar.getInstance());
		run.setSmsRemind(parent.getSmsRemind());
		run.setStatus(1);
		run.setSubject(parent.getSubject());
		run.setUserIds(userIds);
		
		//处理正文
		if(parent.getDocType()!=1){//不是标准正文
			TeeAttachment doc=parent.getDoc();
			TeeAttachment newDoc=attachmentService.clone(doc, "xtdoc", loginUser);
			run.setDoc(newDoc);
		}
		//先保存
		simpleDaoSupport.save(run);
		//处理附件
        List<TeeAttachment> attList=attachmentService.getAttachmentsByIds(attachmentSidStr);
        if(attList!=null&&attList.size()>0){
        	for (TeeAttachment teeAttachment : attList) {
        		teeAttachment.setModelId(run.getSid()+"");
        		simpleDaoSupport.update(teeAttachment);
			}
        }
        //拷贝原来的附件
        List<TeeAttachment> oldAttList=attachmentService.getAttaches("xtattach", parentId+"");
	    if(oldAttList!=null){
	    	for (TeeAttachment teeAttachment : oldAttList) {
	    		TeeAttachment a=attachmentService.clone(teeAttachment, "xtattach", loginUser);
			    a.setModelId(run.getSid()+"");
			    simpleDaoSupport.update(a);
	    	}
	    }
	    //中间表插入数据
	    List<TeePerson>prcsUserList=personService.getPersonByUuids(userIds);
    	if(prcsUserList!=null&&prcsUserList.size()>0){
    		for (TeePerson teePerson : prcsUserList) {
				TeeXTRunPrcs prcs=new TeeXTRunPrcs();
				prcs.setOpinionType(0);
				prcs.setPrcsUser(teePerson);
				prcs.setStatus(0);
				prcs.setXtRun(run);
				prcs.setDeleteStatus(0);
				prcs.setSmsRemind(0);
				simpleDaoSupport.save(prcs);
				
				//发送系统消息
            	Map requestData1 = new HashMap();
		    	requestData1.put("content", loginUser.getUserName()+"发起协同：《"+run.getSubject()+"(由"+parent.getCreateUser().getUserName()+"原发)》");
		    	requestData1.put("userListIds", teePerson.getUuid());
		    	requestData1.put("moduleNo", "100");
		    	requestData1.put("remindUrl","/system/core/xt/handle.jsp?xtRunId="+run.getSid()+"&prcsId="+prcs.getSid());
		    	smsManager.sendSms(requestData1, loginUser);
		    	
		    	
		    	//发短信
		    	if(run.getSmsRemind()==1){
		    		if(!TeeUtility.isNullorEmpty(teePerson.getMobilNo())){
		    			TeeSmsSendPhone sms=new TeeSmsSendPhone();
		    			sms.setContent(loginUser.getUserName()+"发起协同：《"+run.getSubject()+"(由"+parent.getCreateUser().getUserName()+"原发)》");
		    		    sms.setFromId(loginUser.getUuid());
		    		    sms.setFromName(loginUser.getUserName());
		    		    sms.setPhone(teePerson.getMobilNo());
		    		    sms.setSendFlag(0);
		    		    sms.setSendNumber(0);
		    		    sms.setSendTime(Calendar.getInstance());
		    		    sms.setToId(teePerson.getUuid());
		    		    sms.setToName(teePerson.getUserName());
		    		    
		    		    simpleDaoSupport.save(sms);
		    		}
		    	}
			}
    	}
    	json.setRtState(true);
	    return json;
	}



	
	/**
	 * 获取原协同的意见
	 * @param request
	 * @return
	 */
	public TeeJson getParentOpinion(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeXTRun run=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,sid);
		List<Map> returnData=new ArrayList<Map>();
		if(run.getParent()!=null){
			getParentOpinionList(run.getParent(),returnData);
		}
		
		json.setRtState(true);
		json.setRtData(returnData);
		return json;
	}



	private void getParentOpinionList(TeeXTRun run, List<Map> returnData) {
		TeeXTRunModel model=parseToModel(run);		
		Map m=new HashMap();
		m.put("fy", model.getRemark());
		m.put("sid", model.getSid());
		m.put("createUserName",model.getCreateUserName());
		m.put("createTimeStr", model.getCreateTimeStr());
		
		returnData.add(m);
		
		//System.out.println(run.getSid());
		
		if(run.getParent()!=null){
			getParentOpinionList(run.getParent(),returnData);
		}
	}



	/**
	 * 获取绘制流程图的数据
	 * @param request
	 * @return
	 */
	public TeeJson getFlowData(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<Map> returnData=new ArrayList<Map>();
		int xtRunId=TeeStringUtil.getInteger(request.getParameter("xtRunId"),0);
		TeeXTRun run=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,xtRunId);	
		
		int  isManager=0;
		if(run!=null){
			//1.首先判断当前登陆人是不是流程创建者  
			if(run.getCreateUser().getUuid()==loginUser.getUuid()){
				isManager=1;
			}else{
				isManager=0;
			}
			
			Map m=new HashMap();
			m.put("name", run.getCreateUser().getUserName());
			m.put("symbolSize", new Object[]{40,40});
			m.put("symbol", "image://images/icon_fq.png");
			m.put("color", "#fff");
			m.put("mcHereShow",true);
			
			//获取一级节点
			List<TeeXTRunPrcs> prcsList=simpleDaoSupport.executeQuery(" from TeeXTRunPrcs where xtRun.sid=? and prePrcs is null ", new Object[]{xtRunId});
			List<Map> children=new ArrayList<Map>();
			
			Map m1=null;
			if(prcsList!=null&&prcsList.size()>0){
				for(TeeXTRunPrcs prcs:prcsList){
					m1=new HashMap();
					m1.put("name", prcs.getPrcsUser().getUserName());
					m1.put("symbolSize", new Object[]{40,40});
					m1.put("color", "#fff");
					m1.put("prcsId",prcs.getSid());
					m1.put("mcHereShow",true);
					//判断是否有催办的操作
					if(isManager==1){//是流程创建者
						//判断办理过程是否删除
						if(prcs.getDeleteStatus()==1){//已删除
							m1.put("doUrge", 0);
						}else{
							if(prcs.getStatus()==0||prcs.getStatus()==1){
								m1.put("doUrge", 1);
							}else{
								m1.put("doUrge", 0);
							}
						}
					}else{
						m1.put("doUrge", 0);
					}
					
					//判断图标显示
					if(prcs.getDeleteStatus()==1){//已删除
						m1.put("symbol", "image://images/icon_ys.png");
					}else{//未删除
						if(prcs.getStatus()==0){//未接收
							m1.put("symbol", "image://images/icon_wy.png");
						}else if(prcs.getStatus()==1){//已接收
							m1.put("symbol", "image://images/icon_yy.png");
						}else{//已办
							m1.put("symbol", "image://images/icon_yb.png");
						}
					}
					//判断是否有下一级几点
					addChidren(prcs,m1,isManager);
					
					children.add(m1);
				}
			}
			m.put("children",children);
			returnData.add(m);
			
			json.setRtState(true);
			json.setRtData(returnData);
		}else{
			json.setRtState(false);
			json.setRtMsg("信息获取失败！");	
		}
		return json;
	}


    //判断是否还有下一级节点  递归
	private void addChidren(TeeXTRunPrcs prcs, Map m,int isManager) {
		List<TeeXTRunPrcs> prcsList=simpleDaoSupport.executeQuery(" from TeeXTRunPrcs where prePrcs.sid=? and  xtRun.sid=? ", new Object[]{prcs.getSid(),prcs.getXtRun().getSid()});
		if(prcsList!=null&&prcsList.size()>0){
			Map map=null;
			List<Map> children=new ArrayList<Map>();
			for (TeeXTRunPrcs teeXTRunPrcs : prcsList) {
				map=new HashMap();
				map.put("name", teeXTRunPrcs.getPrcsUser().getUserName());
				map.put("symbolSize", new Object[]{38,38});
				//map.put("symbol", "circle");
				map.put("color", "#fff");
				map.put("prcsId",teeXTRunPrcs.getSid());
				map.put("mcHereShow",true);
				//判断是否有催办的操作
				if(isManager==1){//是流程创建者
					//判断办理过程是否删除
					if(teeXTRunPrcs.getDeleteStatus()==1){//已删除
						map.put("doUrge", 0);
					}else{
						if(teeXTRunPrcs.getStatus()==0||teeXTRunPrcs.getStatus()==1){
							map.put("doUrge", 1);
						}else{
							map.put("doUrge", 0);
						}
					}
				}else{
					map.put("doUrge", 0);
				}
				
				//判断图标显示
				if(teeXTRunPrcs.getDeleteStatus()==1){//已删除
					map.put("symbol", "image://images/icon_ys.png");
				}else{//未删除
					if(teeXTRunPrcs.getStatus()==0){//未接收
						map.put("symbol", "image://images/icon_wy.png");
					}else if(teeXTRunPrcs.getStatus()==1){//已接收
						map.put("symbol", "image://images/icon_yy.png");
					}else{//已办
						map.put("symbol", "image://images/icon_yb.png");
					}
				}

				//判断是否有下一级几点
				addChidren(teeXTRunPrcs,map,isManager);
				children.add(map);
			}
			
			m.put("children", children);
		}
		
	}



	/**
	 * 催办
	 * @param request
	 * @return
	 */
	public TeeJson doUrge(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		TeeJson json=new TeeJson();
		int prcsId=TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
		String fy=TeeStringUtil.getString(request.getParameter("fy"));
		TeeXTRunPrcs prcs=(TeeXTRunPrcs) simpleDaoSupport.get(TeeXTRunPrcs.class,prcsId);
		if(prcs!=null){
			
			if(prcs.getXtRun()!=null){
				//发送系统消息
	        	Map requestData1 = new HashMap();
	        	String mess=loginUser.getUserName()+"请您尽快处理协同：《"+prcs.getXtRun().getSubject()+"》。";
	        	if(!TeeUtility.isNullorEmpty(fy)){
	        		mess+="附言："+fy;
	        	}
		    	requestData1.put("content",mess );
		    	requestData1.put("userListIds",prcs.getPrcsUser().getUuid());
		    	requestData1.put("moduleNo", "100");
		    	requestData1.put("remindUrl","/system/core/xt/handle.jsp?xtRunId="+prcs.getXtRun().getSid()+"&prcsId="+prcs.getSid());
		    	smsManager.sendSms(requestData1, loginUser);
		    	
		    	json.setRtState(true);
			}else{
				json.setRtState(false);
			}
		}else{
			json.setRtState(false);
		}
		
		return json;
	}



	/**
	 * 数据迁移
	 * @param request
	 * @return
	 */
	public TeeJson dataSync(HttpServletRequest request) {
		
		/**{
			"subject": "测试事项20180920",
			"importantLevel": "3",
			"smsRemind": "1",
			"userIds": "12000",
			"deadLine": "事项期限，整型，单位秒",
			"deadLineTimeStr": "2018-12-31 12:12:12",
			"advanceRemind": "60",
			"autoStop": "1",
			"optPriv": "128",
			"createTimeStr": "2018-09-20 14:20:23",
			"sendTimeStr": "2018-09-21 10:20:55",
			"createUserId": "admin",
			"status": "2",
			"remark": "测试数据迁移",
			"docType": "1",
			"content": "你好啊  哈哈哈哈哈",
			"doc": {
				"name": "附件名称",
				"path": "实际物理路径"
			},
			"attach": [{
				"name": "附件名称",
				"path": "实际物理路径"
			}],
			"repeatFy": "0",
			"repeatYj": "0",
			"parentId": "0",
			"prcsInfos": [{
				"prcsUserId": "admin",
				"receiveTimeStr": "2018-09-22 14:22:22",
				"handleTimeStr": "2018-09-22 17:28:33",
				"status": "2",
				"opinionType": "2",
				"opinionContent": "同意，按照规定办理",
				"deleteStatus": "0",
		        "smsRemind":"1",
		        "attach": [{
				       "name": "附件名称",
				       "path": "实际物理路径"
			         }],
		       "replys":[{
		               "createTimeStr":"2018-09-22 14:22:12",
		               "createUserId":"admin1",
		               "content":"1111",
		               "attach": [{
				          "name": "附件名称",
				          "path": "实际物理路径"
			             }]
		              },
		              {
		               "createTimeStr":"2018-09-22 17:27:32",
		               "createUserId":"ceshi1",
		               "content":"2222",
		               "attach": [{
				          "name": "附件名称",
				          "path": "实际物理路径"
			             }]
		              }]
			},{
				"prcsUserId": "admin1",
				"receiveTimeStr": "2018-09-23 14:22:22",
				"handleTimeStr": "2018-09-23 17:28:33",
				"status": "2",
				"opinionType": "2",
				"opinionContent": "同意",
				"deleteStatus": "0",
		        "smsRemind":"1",
		        "attach": [{
				       "name": "附件名称",
				       "path": "实际物理路径"
			         }],
		       "replys":[{
		               "createTimeStr":"2018-09-23 18:22:12",
		               "createUserId":"ceshi2",
		               "content":"3333",
		               "attach": [{
				          "name": "附件名称",
				          "path": "实际物理路径"
			             }]
		              }]
			}]
		}**/
		
		
		TeeJson json=new TeeJson();
		//获取传来的json字符串
		String dataStr=TeeStringUtil.getString(request.getParameter("dataStr"));
		//转换成json
		Map<String,String> dataMap=TeeJsonUtil.JsonStr2Map(dataStr);
		//*****************************处理父类************************************
		//创建一个TeeXtRun对象
		TeeXTRun xtRun=new TeeXTRun();
		xtRun.setAdvanceRemind(TeeStringUtil.getInteger(dataMap.get("advanceRemind"),0));//提前xx秒提醒
		xtRun.setAutoStop(TeeStringUtil.getInteger(dataMap.get("autoStop"), 0));//是否到期自动终止
		xtRun.setContent(TeeStringUtil.getString(dataMap.get("content")));
		
		
		//处理创建时间
		String createTimeStr=TeeStringUtil.getString(dataMap.get("createTimeStr"));
		if(!TeeUtility.isNullorEmpty(createTimeStr)){
			xtRun.setCreateTime(TeeDateUtil.parseCalendar(createTimeStr));
		}
		
		//处理创建人
		//获取创建人的登录名
		String createUserId=TeeStringUtil.getString(dataMap.get("createUserId"));
		TeePerson createUser=personService.getPersonByUserId(createUserId);
		xtRun.setCreateUser(createUser);
		
		
		
		xtRun.setDeadLine(TeeStringUtil.getInteger(dataMap.get("deadLine"), 0));//事项期限
		
		//处理事项到期时间
		String deadLineTimeStr=TeeStringUtil.getString(dataMap.get("deadLineTimeStr"));
		if(!TeeUtility.isNullorEmpty(deadLineTimeStr)){
			xtRun.setDeadLineTime(TeeDateUtil.parseCalendar(deadLineTimeStr));//事项到期时间
		}
		
		
		
		
		xtRun.setDocType(TeeStringUtil.getInteger(dataMap.get("docType"), 1));//正文类型
		xtRun.setImportantLevel(TeeStringUtil.getInteger(dataMap.get("importantLevel"), 1));//事项级别
		xtRun.setOptPriv(TeeStringUtil.getInteger(dataMap.get("optPriv"), 0));//允许操作权限
		
        //处理原事项
		int parentId=TeeStringUtil.getInteger(dataMap.get("parentId"), 0);
		TeeXTRun parent=(TeeXTRun) simpleDaoSupport.get(TeeXTRun.class,parentId);
		if(parent!=null){
			xtRun.setParent(parent);
		}
		
		
		xtRun.setRemark(TeeStringUtil.getString(dataMap.get("remark")));
		xtRun.setRepeatFy(TeeStringUtil.getInteger(dataMap.get("repeatFy"), 0));
		xtRun.setRepeatYj(TeeStringUtil.getInteger(dataMap.get("repeatYj"), 0));
		
		
		//处理发送时间
		String sendTimeStr=TeeStringUtil.getString(dataMap.get("sendTimeStr"));
		if(!TeeUtility.isNullorEmpty(sendTimeStr)){
			xtRun.setSendTime(TeeDateUtil.parseCalendar(sendTimeStr));
		}
		
		
		
		
		xtRun.setSmsRemind(TeeStringUtil.getInteger(dataMap.get("smsRemind"),0));//是否短信提醒
		xtRun.setStatus(TeeStringUtil.getInteger(dataMap.get("status"), 0));
		xtRun.setSubject(TeeStringUtil.getString(dataMap.get("subject")));//标题
		
		//处理办理人员信息
		String userUuids="";
		String userIds=	TeeStringUtil.getString(dataMap.get("userIds"));
		String[] userIdArray=userIds.split(",");
		if(userIdArray!=null&&userIdArray.length>0){
			for(String userId : userIdArray) {
				TeePerson person=personService.getPersonByUserId(userId);
				if(person!=null){
					userUuids+=person.getUuid()+",";
				}
			}
		}
		if(userUuids.endsWith(",")){
			userUuids=userUuids.substring(0, userUuids.length()-1);
		}
		xtRun.setUserIds(userUuids);//办理人员id字符串分隔
		
		//*****处理正文
		//xtRun.setDoc();
		//*****处理附件
		
		//保存TeeXtRun对象
		simpleDaoSupport.save(xtRun);
		
		
		
		
		//*****************************处理子类*********************************
		String prcsInfos=TeeStringUtil.getString(dataMap.get("prcsInfos"));
		List<Map<String,String>> prcsMapList=TeeJsonUtil.JsonStr2MapList(prcsInfos);
		if(prcsMapList!=null&&prcsMapList.size()>0){
			TeeXTRunPrcs prcs=null;//创建一个子类对象
			for (Map<String, String> prcsMap : prcsMapList) {
				
				prcs=new TeeXTRunPrcs();
				prcs.setDeleteStatus(TeeStringUtil.getInteger(prcsMap.get("deleteStatus"), 0));
				
				//处理处理时间
				String handleTimeStr=TeeStringUtil.getString(prcsMap.get("handleTimeStr"));
				if(!TeeUtility.isNullorEmpty(handleTimeStr)){
					prcs.setHandleTime(TeeDateUtil.parseCalendar(handleTimeStr));
				}
				
				
				
				prcs.setOpinionContent(TeeStringUtil.getString(prcsMap.get("opinionContent")));
				prcs.setOpinionType(TeeStringUtil.getInteger(prcsMap.get("opinionType"), 0));
				
				//处理办理人员
				String prcsUserId=TeeStringUtil.getString(prcsMap.get("prcsUserId"));
				TeePerson prcsUser=personService.getPersonByUserId(prcsUserId);
				prcs.setPrcsUser(prcsUser);
				
				//处理接收时间
				String receiveTimeStr=TeeStringUtil.getString(prcsMap.get("receiveTimeStr"));
				if(!TeeUtility.isNullorEmpty("receiveTimeStr")){
					prcs.setReceiveTime(TeeDateUtil.parseCalendar(receiveTimeStr));
				}
				
				prcs.setSmsRemind(TeeStringUtil.getInteger(prcsMap.get("smsRemind"), 0));
				prcs.setStatus(TeeStringUtil.getInteger(prcsMap.get("status"), 0));
				prcs.setXtRun(xtRun);
				
				
				//****处理附件
				//保存TeeXtRunPrcs对象
				simpleDaoSupport.save(prcs);
				
				//处理回复
				String replys=TeeStringUtil.getString(prcsMap.get("replys"));
				List<Map<String,String>> replyMapList=TeeJsonUtil.JsonStr2MapList(replys);
				if(replyMapList!=null&&replyMapList.size()>0){
					//创建一个回复对象
					TeeXTReply reply=null;
					for (Map<String, String> replyMap : replyMapList) {
						reply=new  TeeXTReply();
						reply.setContent(TeeStringUtil.getString(replyMap.get("content")));
						
						//处理创建时间
						String crTimeStr=TeeStringUtil.getString(replyMap.get("createTimeStr"));
						if(!TeeUtility.isNullorEmpty(crTimeStr)){
							reply.setCreateTime(TeeDateUtil.parseCalendar(crTimeStr));
						}
						
						//处理创建人
						String crUserId=TeeStringUtil.getString(replyMap.get("createUserId"));
						TeePerson crUser=personService.getPersonByUserId(crUserId);
						reply.setCreateUser(crUser);
						reply.setXtRunPrcs(prcs);
						
						//****处理附件
						
						simpleDaoSupport.save(reply);
						
					}
				}
				
			}
		}
		
		json.setRtState(true);
		return json;
	}

}
