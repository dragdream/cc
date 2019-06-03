package com.tianee.oa.subsys.supervise.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.supervise.bean.TeeSupervision;
import com.tianee.oa.subsys.supervise.bean.TeeSupervisionType;
import com.tianee.oa.subsys.supervise.model.TeeSupervisionModel;
import com.tianee.oa.subsys.supervise.model.TeeSupervisionTypeModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSupervisionService extends TeeBaseService{

	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	TeeAttachmentService attachmentService;
	
	/**
	 * 新建或者编辑
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) throws IOException {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//获取页面上传来的数据
		int sid=TeeStringUtil.getInteger(multipartRequest.getParameter("sid"),0);
		String supName=TeeStringUtil.getString(multipartRequest.getParameter("supName"));
		int typeId=TeeStringUtil.getInteger(multipartRequest.getParameter("typeId"),0);
		int leaderId=TeeStringUtil.getInteger(multipartRequest.getParameter("leaderId"),0);
		int managerId=TeeStringUtil.getInteger(multipartRequest.getParameter("managerId"), 0);
		String assistIds=TeeStringUtil.getString(multipartRequest.getParameter("assistIds"));
		Date beginTime=TeeStringUtil.getDate(multipartRequest.getParameter("beginTimeStr"),"yyyy-MM-dd");
		Date endTime=TeeStringUtil.getDate(multipartRequest.getParameter("endTimeStr"),"yyyy-MM-dd");
		String content=TeeStringUtil.getString(multipartRequest.getParameter("content"));
	    int status=TeeStringUtil.getInteger(multipartRequest.getParameter("status"),0);
	    //父任务主键
	    int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"),0);
	    TeeSupervision parent=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,parentId);
	    
	    
	    TeeSupervisionType type=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,typeId);
	    TeePerson leader=(TeePerson) simpleDaoSupport.get(TeePerson.class,leaderId);
	    TeePerson manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
	    TeePerson assist=null;
	    Set<TeePerson> assists=new HashSet<TeePerson>();
	    if(!("").equals(assistIds)){
	    	String []assistIdArray=assistIds.split(",");
	    	for (String str : assistIdArray) {
				int uuid=TeeStringUtil.getInteger(str,0);
				assist=(TeePerson) simpleDaoSupport.get(TeePerson.class,uuid);
				assists.add(assist);		
			}
	    }
	    
	    List<TeeAttachment> attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.supervision);
	    
	    TeeSupervision sup=null;
	    if(sid>0){//编辑
	    	sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
	    	sup.setBeginTime(beginTime);
	    	sup.setContent(content);
	    	sup.setEndTime(endTime);
	    	sup.setLeader(leader);
	    	sup.setManager(manager);
	    	sup.setSupName(supName);
	    	sup.setType(type);
	    	sup.getAssists().clear();
	    	sup.getAssists().addAll(assists);
	    	sup.setStatus(status);
	    	simpleDaoSupport.update(sup);
	    	
	    	//添加新附件
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(sup.getSid()));
				attachmentDao.update(attach);
			}
	    }else{//新建
	    	sup=new TeeSupervision();
	    	sup.setBeginTime(beginTime);
	    	sup.setContent(content);
	    	sup.setCreater(loginUser);
	    	sup.setCreaterTime(new Date());
	    	sup.setEndTime(endTime);
	    	sup.setLeader(leader);
	    	sup.setManager(manager);
	    	sup.setSupName(supName);
	    	sup.setType(type);
	    	sup.getAssists().addAll(assists);
	    	sup.setStatus(status);
	    	sup.setParent(parent);
	    	simpleDaoSupport.save(sup);
	    	
	    	//添加新附件
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(sup.getSid()));
				attachmentDao.update(attach);
			}
	    	
			
			//如果是直接发布的话    发送消息提醒
			if(status==7){//发布
				if(sup.getLeader()!=null){
					// 发送消息  给责任领导
				    Map requestData1 = new HashMap();
					requestData1.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为责任领导，请查看。");
					requestData1.put("userListIds", sup.getLeader().getUuid());
					requestData1.put("moduleNo", "061");
					requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
					smsManager.sendSms(requestData1, loginUser);
				
					//给责任领导发短信
	    			TeeSmsSendPhone s1=new TeeSmsSendPhone();
	    			s1.setContent("您有一个督办任务《"+sup.getSupName()+"》指派您为责任领导，请查看。");
	    			s1.setFromId(loginUser.getUuid());
	    			s1.setFromName(loginUser.getUserName());
	    			s1.setPhone(sup.getLeader().getMobilNo());
	    			s1.setSendFlag(0);
	    			s1.setSendNumber(0);
	    			s1.setSendTime(Calendar.getInstance());
	    			s1.setToId(sup.getLeader().getUuid());
	    			s1.setToName(sup.getLeader().getUserName());
		    		simpleDaoSupport.save(s1);
				
				}
				
					
			   if(sup.getManager()!=null){
				 //发送消息给主办人
					Map requestData2 = new HashMap();
					requestData2.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为主责部门办理人，请查看。");
					requestData2.put("userListIds", sup.getManager().getUuid());
					requestData2.put("moduleNo", "061");
					requestData2.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
					smsManager.sendSms(requestData2, loginUser);
			   
					 //给主责部门办理人发短信
					TeeSmsSendPhone s2=new TeeSmsSendPhone();
					s2.setContent("您有一个督办任务《"+sup.getSupName()+"》指派您为主责部门办理人，请查看。");
					s2.setFromId(loginUser.getUuid());
					s2.setFromName(loginUser.getUserName());
					s2.setPhone(sup.getManager().getMobilNo());
					s2.setSendFlag(0);
					s2.setSendNumber(0);
					s2.setSendTime(Calendar.getInstance());
					s2.setToId(sup.getManager().getUuid());
					s2.setToName(sup.getManager().getUserName());
	    		    simpleDaoSupport.save(s2);
			   
			   }

				
				if(!("").equals(assistIds)){
					//发送消息给协办人
					Map requestData3 = new HashMap();
					requestData3.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为配合部门办理人，请查看。");
					requestData3.put("userListIds", assistIds);
					requestData3.put("moduleNo", "061");
					requestData3.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
					smsManager.sendSms(requestData3, loginUser);
					
					
					Set<TeePerson>pList=sup.getAssists();
					for (TeePerson teePerson : pList) {
						
						//给协办人发短信
						TeeSmsSendPhone sms=new TeeSmsSendPhone();
		    			sms.setContent("您有一个督办任务《"+sup.getSupName()+"》指派您为配合部门办理人，请查看。");
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
		/*json.setRtData(sup);*/
		return json;
	}

	/**
	 * 根据分类主键   获取分类下的督办任务列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson getSupervisionListByTypeId(
			TeeDataGridModel dm, HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取分类主键
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		String hql = " from TeeSupervision s where s.type.sid=?";
		List param = new ArrayList();
		param.add(typeId);
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by  s.createrTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSupervision> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeSupervisionModel> modelList = new ArrayList<TeeSupervisionModel>();
		if (list != null) {
			TeeSupervisionModel model=null;
			for (int i = 0; i < list.size(); i++) {
				model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	
	/**
	 * 将实体类转换成model
	 * @param teeSupervision
	 * @return
	 */
	private TeeSupervisionModel parseToModel(TeeSupervision teeSupervision) {
		List<TeeAttachment> sorceAttachs = null;
		List<Map> attachs = new ArrayList<Map>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeSupervisionModel model=new TeeSupervisionModel();
		BeanUtils.copyProperties(teeSupervision, model);
		if(teeSupervision.getBeginTime()!=null){
			model.setBeginTimeStr(sdf.format(teeSupervision.getBeginTime()));
		}
		if(teeSupervision.getEndTime()!=null){
			model.setEndTimeStr(sdf.format(teeSupervision.getEndTime()));
		}
		if(teeSupervision.getCreater()!=null){
			model.setCreaterId(teeSupervision.getCreater().getUuid());
			model.setCreaterName(teeSupervision.getCreater().getUserName());
		}
		if(teeSupervision.getCreaterTime()!=null){
			model.setCreaterTimeStr(sdf.format(teeSupervision.getCreaterTime()));
		}
		if(teeSupervision.getType()!=null){
			model.setTypeId(teeSupervision.getType().getSid());
			model.setTypeName(teeSupervision.getType().getTypeName());
		}
		if(teeSupervision.getLeader()!=null){
			model.setLeaderId(teeSupervision.getLeader().getUuid());
			model.setLeaderName(teeSupervision.getLeader().getUserName());
		}
		if(teeSupervision.getManager()!=null){
			model.setManagerId(teeSupervision.getManager().getUuid());
			model.setManagerName(teeSupervision.getManager().getUserName());
			if(teeSupervision.getManager().getDept()!=null){
				model.setManagerNameAndDept(teeSupervision.getManager().getUserName()+"("+teeSupervision.getManager().getDept().getDeptName()+")");
			}else{
				model.setManagerNameAndDept(teeSupervision.getManager().getUserName());
			}
			
		}
		
	    Set<TeePerson> assists=teeSupervision.getAssists();
	    String assistNames="";
	    String assistIds="";
	    String assistNamesAndDept="";
	    for (TeePerson teePerson : assists) {
	    	assistNames+=teePerson.getUserName()+",";
	    	if(teePerson.getDept()!=null){
	    		assistNamesAndDept+=teePerson.getUserName()+"("+teePerson.getDept().getDeptName()+"),";
	    	}else{
	    		assistNamesAndDept+=teePerson.getUserName()+",";
	    	}
	    	assistIds+=teePerson.getUuid()+",";  	
		}
	    if(assistNames.endsWith(",")){
	    	assistNames=assistNames.substring(0,assistNames.length()-1);
	    }
	    if(assistIds.endsWith(",")){
	    	assistIds=assistIds.substring(0,assistIds.length()-1);
	    }
	    if(assistNamesAndDept.endsWith(",")){
	    	assistNamesAndDept=assistNamesAndDept.substring(0,assistNamesAndDept.length()-1);
	    }
	    model.setAssistIds(assistIds);
	    model.setAssistNames(assistNames);
	    model.setAssistNamesAndDept(assistNamesAndDept);
	    if(teeSupervision.getParent()!=null){
	    	model.setParentId(teeSupervision.getParent().getSid());
	    	model.setParentName(teeSupervision.getParent().getSupName());
	    }
	    
	    
	    //处理附件
	    sorceAttachs = attachmentService.getAttaches(TeeAttachmentModelKeys.supervision, String.valueOf(teeSupervision.getSid()));
		if(sorceAttachs!= null && sorceAttachs.size() > 0){
			 for(int i=0;i<sorceAttachs.size();i++){
				 TeeAttachment f = (TeeAttachment)sorceAttachs.get(i);
				 Map map = new HashMap<String, String>();
				 map.put("sid", f.getSid());
				 map.put("priv", 31);
				 map.put("ext", f.getExt());
				 map.put("fileName", f.getFileName());
				 attachs.add(map);
		     }
		}
		model.setAttachmentsMode(attachs);
		return model;
	}

	
	/**
	 * 根据主键  进行删除操作
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的 主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			//删除督办任务
			simpleDaoSupport.executeUpdate(" delete from TeeSupervision where sid=? ", new Object[]{sid});
			//删除任务催办记录
			simpleDaoSupport.executeUpdate(" delete from TeeSupervisionUrge where sup.sid=? ", new Object[]{sid});
			//删除反馈
			simpleDaoSupport.executeUpdate(" delete from TeeSupFeedBack where sup.sid=? ", new Object[]{sid});
			//删除回复
			simpleDaoSupport.executeUpdate(" delete from TeeSupFeedBackReply where sup.sid=? ", new Object[]{sid});
			//删除申请
			simpleDaoSupport.executeUpdate(" delete from TeeSupervisionApply where sup.sid=? ", new Object[]{sid});
			
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在");
		}
		return json;
	}

	/**
	 * 发布督办任务
	 * @param request
	 * @return
	 */
	public TeeJson publish(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
		    sup.setStatus(7);
		    simpleDaoSupport.update(sup);
		    
		   if(sup.getLeader()!=null){
			   // 发送消息  给责任领导
			    Map requestData1 = new HashMap();
				requestData1.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为责任领导，请查看。");
				requestData1.put("userListIds", sup.getLeader().getUuid());
				requestData1.put("moduleNo", "061");
				requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
				smsManager.sendSms(requestData1, loginUser); 
		   
		   
		       //给责任领导发短信
    			TeeSmsSendPhone s1=new TeeSmsSendPhone();
    			s1.setContent("您有一个督办任务《"+sup.getSupName()+"》指派您为责任领导，请查看。");
    			s1.setFromId(loginUser.getUuid());
    			s1.setFromName(loginUser.getUserName());
    			s1.setPhone(sup.getLeader().getMobilNo());
    			s1.setSendFlag(0);
    			s1.setSendNumber(0);
    			s1.setSendTime(Calendar.getInstance());
    			s1.setToId(sup.getLeader().getUuid());
    			s1.setToName(sup.getLeader().getUserName());
	    		 simpleDaoSupport.save(s1);
		   }
		
			if(sup.getManager()!=null){	
				//发送消息给主办人
				Map requestData2 = new HashMap();
				requestData2.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为主责部门办理人，请查看。");
				requestData2.put("userListIds", sup.getManager().getUuid());
				requestData2.put("moduleNo", "061");
				requestData2.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
				smsManager.sendSms(requestData2, loginUser);
			
			    //给主责部门办理人发短信
				TeeSmsSendPhone s2=new TeeSmsSendPhone();
				s2.setContent("您有一个督办任务《"+sup.getSupName()+"》指派您为主责部门办理人，请查看。");
				s2.setFromId(loginUser.getUuid());
				s2.setFromName(loginUser.getUserName());
				s2.setPhone(sup.getManager().getMobilNo());
				s2.setSendFlag(0);
				s2.setSendNumber(0);
				s2.setSendTime(Calendar.getInstance());
				s2.setToId(sup.getManager().getUuid());
				s2.setToName(sup.getManager().getUserName());
    		    simpleDaoSupport.save(s2);
			}
		
			
			
			
			String assIds="";
			Set<TeePerson>assists=sup.getAssists();
			for (TeePerson teePerson : assists) {
				assIds+=teePerson.getUuid()+",";
				
				//给协办人发短信
				TeeSmsSendPhone sms=new TeeSmsSendPhone();
    			sms.setContent("您有一个督办任务《"+sup.getSupName()+"》指派您为配合部门办理人，请查看。");
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
			
			if(assIds.endsWith(",")){
				assIds=assIds.substring(0,assIds.length()-1);
			}
			if(!("").equals(assIds)){
				//发送消息给主办人
				Map requestData3 = new HashMap();
				requestData3.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为配合部门办理人，请查看。");
				requestData3.put("userListIds", assIds);
				requestData3.put("moduleNo", "061");
				requestData3.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
				smsManager.sendSms(requestData3, loginUser);
			}
			

		    json.setRtState(true);
		    json.setRtMsg("发布成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}

	/**
	 * 根据主键  获取督办任务详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
			TeeSupervisionModel model=parseToModel(sup);
			
			json.setRtState(true);
			json.setRtMsg("数据获取成功！");
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

	
	/**
	 * 根据任务主键  获取任务状态  和  当前登陆人 在该任务中充当的角色
	 * @param request
	 * @return
	 */
	public TeeJson getStatusAndRole(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的任务主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		Map map=new HashMap();
		if(sid>0){
			TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
			TeeSupervisionModel model=parseToModel(sup);
			map.put("status", sup.getStatus());
			//判断当前登录人 是不是创建人
			if(loginUser.getUuid()==model.getCreaterId()){
				map.put("isCreater",1);
			}else{
				map.put("isCreater",0);
			}
			
			//判断当前登录人  是不是责任领导
			if(loginUser.getUuid()==model.getLeaderId()){
				map.put("isLeader",1);
			}else{
				map.put("isLeader",0);
			}
			
			//判断当前登录人  是不是主办人
			if(loginUser.getUuid()==model.getManagerId()){
				map.put("isManager",1);
			}else{
				map.put("isManager",0);
			}
			
			//判断当前登录人 是不是协办人
			String assistIds=model.getAssistIds();
			String []assistArray=assistIds.split(",");
			for (String str : assistArray) {
				int uuid=TeeStringUtil.getInteger(str, 0);
				if(uuid==loginUser.getUuid()){
					map.put("isAssist",1);
					break;
				}else{
					map.put("isAssist",0);
				}
			}
			
			json.setRtState(true);
			json.setRtData(map);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}

	
	/**
	 * 主办人签收任务
	 * @param request
	 * @return
	 */
	public TeeJson receive(HttpServletRequest request) {
		//获取当前登录人
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json=new TeeJson();
		//获取页面上传来的主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
		    sup.setStatus(1);
		    simpleDaoSupport.update(sup);
		    
		    String Ids="";
		    Set<TeePerson> users=sup.getAssists();
		    if(sup.getLeader()!=null){
		    	users.add(sup.getLeader());
		    }
		    for (TeePerson teePerson : users) {
		    	Ids+=teePerson.getUuid()+",";		
			}
		    if(Ids.endsWith(",")){
		    	Ids=Ids.substring(0, Ids.length()-1);
		    }
		    //给责任领导 和 协办人发送消息
			 Map requestData1 = new HashMap();
			 requestData1.put("content", "您有一个督办任务《"+sup.getSupName()+"》已被主办人签收，请及时办理。");
			 requestData1.put("userListIds", Ids);
			 requestData1.put("moduleNo", "061");
			 requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
			 smsManager.sendSms(requestData1, loginUser);
		    
		    
		    json.setRtState(true);
		    json.setRtMsg("签收成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("数据不存在！");
		}
		return json;
	}

	
	/**
	 * 根据状态    获取我的督办任务
	 * @param dm
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public TeeEasyuiDataGridJson getMySupListByStatus(TeeDataGridModel dm,
			HttpServletRequest request) throws Exception {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的状态
		String option=TeeStringUtil.getString(request.getParameter("option"));
		String hql="";
		List param=new ArrayList();
		if(("wjs").equals(option)){
			hql=" from TeeSupervision s where s.status=7 and ( s.leader=? or s.manager=? or exists(select 1 from s.assists a where  a.uuid=?  ) )";
		}else if(("clz").equals(option)){
			hql=" from TeeSupervision s where s.status in (1,2,4,5) and ( s.leader=? or s.manager=? or exists(select 1 from s.assists a where  a.uuid=?  ) )";
		}else if(("yzt").equals(option)){
			hql=" from TeeSupervision s where s.status=3 and ( s.leader=? or s.manager=? or exists(select 1 from s.assists a where  a.uuid=?  ) )";
		}else if(("ybj").equals(option)){
			hql=" from TeeSupervision s where s.status=6 and ( s.leader=? or s.manager=? or exists(select 1 from s.assists a where  a.uuid=?  ) )";
		}
		param.add(loginUser);
		param.add(loginUser);
		param.add(loginUser.getUuid());
		
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by  s.createrTime desc ";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeSupervision> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeSupervisionModel> modelList = new ArrayList<TeeSupervisionModel>();
		if (list != null) {
			TeeSupervisionModel model=null;
			for (int i = 0; i < list.size(); i++) {
				model = parseToModel(list.get(i));
				modelList.add(model);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	
	
	/**
	 * 根据主键 获取自己和所有孩子的集合
	 * @param sid
	 * @return
	 */
	public List<TeeSupervision> getAllChildrenAndSelfList(int sid,List<TeeSupervision> result){
		//获取当前的任务
		TeeSupervision sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
		result.add(sup);
		//获取当前任务的子任务
		List<TeeSupervision> list=simpleDaoSupport.executeQuery(" from TeeSupervision where parent.sid=? ", new Object[]{sid});
	    if(list!=null&&list.size()>0){
	    	for (TeeSupervision teeSupervision : list) {
	    		getAllChildrenAndSelfList(teeSupervision.getSid(),result);
			}
	    	
	    }
	    
	    return result;
	}

	
	
	/**
	 * 督办任务统计   按部门
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	public TeeEasyuiDataGridJson getSupCountByDept(TeeDataGridModel dm,
			HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的参数
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		String beginTimeStr1=TeeStringUtil.getString(request.getParameter("beginTimeStr1"));
		String beginTimeStr2=TeeStringUtil.getString(request.getParameter("beginTimeStr2"));	
		String hql=" select count(*) from TeeSupervision where 1=1 ";
		Date beginTime1=null;
		Date beginTime2=null;
		Date currentTime=new Date();
		String currentTimeStr=sdf.format(currentTime);
		currentTime=sdf.parse(currentTimeStr);
		
		
		if(!("").equals(beginTimeStr1)){
			beginTime1=sdf.parse(beginTimeStr1);
		}
		if(!("").equals(beginTimeStr2)){
			beginTime2=sdf.parse(beginTimeStr2);
		}
		
		
		List param=new ArrayList();
		if(typeId!=0){
			hql+="  and type.sid=? ";
			param.add(typeId);
		}
		if(beginTime1!=null){
			hql+="  and beginTime>=? ";
			param.add(beginTime1);
		}
		if(beginTime2!=null){
			hql+="  and beginTime<=? ";
			param.add(beginTime2);
		}
		
		
		List<Map> result=new ArrayList<Map>();
		
		//获取所有的部门
		String hql1=" from  TeeDepartment ";
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql1, null));// 设置总记录数

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeDepartment> deptList= simpleDaoSupport.pageFindByList(hql1, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
		
		Map map=null;
		List p=null;
		for (TeeDepartment dept : deptList) {
		   map=new HashMap();
		   p=new ArrayList();
		   p.addAll(param); 
		   p.add(dept.getUuid());
		   
		   
		   
		   long sumCount=simpleDaoSupport.count(hql+" and manager.dept.uuid=? ", p.toArray());//总数
		   long count1=simpleDaoSupport.count(hql+" and status=0 and manager.dept.uuid=? ",  p.toArray());//未发布
		   long count2=simpleDaoSupport.count(hql+" and status=7 and manager.dept.uuid=? ",  p.toArray());//待签收
		   long count5=simpleDaoSupport.count(hql+" and status=3 and manager.dept.uuid=? ",  p.toArray());//已暂停
		   long count6=simpleDaoSupport.count(hql+" and status=6 and manager.dept.uuid=? and realEndTime <= endTime  ",  p.toArray());//正常已办结
		   long count7=simpleDaoSupport.count(hql+" and status=6 and manager.dept.uuid=? and realEndTime > endTime ",  p.toArray());//逾期已办结
		  
		   p.add(currentTime);
		   
		   long count3=simpleDaoSupport.count(hql+" and status in (1,2,4,5) and manager.dept.uuid=? and endTime >=? ", p.toArray());//正常办理中
		   long count4=simpleDaoSupport.count(hql+" and status in (1,2,4,5) and manager.dept.uuid=? and endTime < ? ", p.toArray());//逾期办理中
		   
		   
		   map.put("deptName", dept.getDeptName());
		   map.put("sumCount", sumCount);
		   map.put("count1", count1);
		   map.put("count2", count2);
		   map.put("count3", count3);
		   map.put("count4", count4);
		   map.put("count5", count5);
		   map.put("count6", count6);
		   map.put("count7", count7);
		   
		   result.add(map);
			
		}
		
		j.setRows(result);// 设置返回的行
		return j;
	}

	
	
	/**
	 * 督办任务统计   按类别
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public List getSupCountByType(HttpServletRequest request) throws Exception {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//获取页面上传来的参数
        int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);

 		String beginTimeStr1=TeeStringUtil.getString(request.getParameter("beginTimeStr1"));
 		String beginTimeStr2=TeeStringUtil.getString(request.getParameter("beginTimeStr2"));	
 		String hql=" select count(*) from TeeSupervision where 1=1 ";
 		Date beginTime1=null;
 		Date beginTime2=null;
 		Date currentTime=new Date();
 		String currentTimeStr=sdf.format(currentTime);
 		currentTime=sdf.parse(currentTimeStr);
 		
 		
 		if(!("").equals(beginTimeStr1)){
 			beginTime1=sdf.parse(beginTimeStr1);
 		}
 		if(!("").equals(beginTimeStr2)){
 			beginTime2=sdf.parse(beginTimeStr2);
 		}
 		
 		
 		List param=new ArrayList();
 		if(deptId!=0){
 			hql+="  and manager.dept.uuid=? ";
 			param.add(deptId);
 		}
 		if(beginTime1!=null){
 			hql+="  and beginTime>=? ";
 			param.add(beginTime1);
 		}
 		if(beginTime2!=null){
 			hql+="  and beginTime<=? ";
 			param.add(beginTime2);
 		}
 		
         
         
		List<TeeSupervisionType> typeList=simpleDaoSupport.executeQuery(" from TeeSupervisionType t order by t.orderNum ",new Object[]{});		
		List<Map> typeMapList = new ArrayList();
		
		Map map=null;
		List p=null;
		for (TeeSupervisionType type : typeList) {
			if(type.getParent()==null){
				 map=new HashMap();
				 p=new ArrayList();
				 p.addAll(param); 
				 p.add(type.getSid()); 
				 long sumCount=simpleDaoSupport.count(hql+" and type.sid=? ", p.toArray());//总数
				 long count1=simpleDaoSupport.count(hql+" and status=0 and type.sid=? ",  p.toArray());//未发布
				 long count2=simpleDaoSupport.count(hql+" and status=7 and type.sid=? ",  p.toArray());//待签收
				 long count5=simpleDaoSupport.count(hql+" and status=3 and type.sid=? ",  p.toArray());//已暂停
				 long count6=simpleDaoSupport.count(hql+" and status=6 and type.sid=? and realEndTime <= endTime  ",  p.toArray());//正常已办结
				 long count7=simpleDaoSupport.count(hql+" and status=6 and type.sid=? and realEndTime > endTime ",  p.toArray());//逾期已办结
				  
				 p.add(currentTime);
				   
				 long count3=simpleDaoSupport.count(hql+" and status in (1,2,4,5) and type.sid=? and endTime >=? ", p.toArray());//正常办理中
				 long count4=simpleDaoSupport.count(hql+" and status in (1,2,4,5) and type.sid=? and endTime < ? ", p.toArray());//逾期办理中
				   
				  map.put("sid", type.getSid()); 
				  map.put("typeName", type.getTypeName());
				  map.put("sumCount", sumCount);
				  map.put("count1", count1);
				  map.put("count2", count2);
				  map.put("count3", count3);
				  map.put("count4", count4);
				  map.put("count5", count5);
				  map.put("count6", count6);
				  map.put("count7", count7);
				  map.put("children", new ArrayList()); 
				
				  typeMapList.add(map);
			}
		}
		//从一级节点开始往下找
		for(Map data:typeMapList){
			setChildInfos(typeList,typeMapList,data,hql,param);
		}
		return typeMapList;
	}

	private void setChildInfos(List<TeeSupervisionType> typeList,
			List<Map> typeMapList, Map typeMap,String hql,List param) throws Exception  {
		SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date currentTime=new Date();
 		String currentTimeStr=sdf.format(currentTime);
 		currentTime=sdf.parse(currentTimeStr);
		//先获取该节点下面的所有子节点
		List<Map> childList = new ArrayList();
		//将taskMap的所有子节点加入到childList中
		List p=null;
		Map map=null;
		for(TeeSupervisionType type:typeList){
			if(type.getParent()!=null){	
				if(type.getParent().getSid()==Integer.parseInt(typeMap.get("sid")+"")){
					map=new HashMap();
					map.put("typeName", type.getTypeName());
					p=new ArrayList();
				    p.addAll(param); 
					p.add(type.getSid()); 
					long sumCount=simpleDaoSupport.count(hql+" and type.sid=? ", p.toArray());//总数
					long count1=simpleDaoSupport.count(hql+" and status=0 and type.sid=? ",  p.toArray());//未发布
					long count2=simpleDaoSupport.count(hql+" and status=7 and type.sid=? ",  p.toArray());//待签收
					long count5=simpleDaoSupport.count(hql+" and status=3 and type.sid=? ",  p.toArray());//已暂停
					long count6=simpleDaoSupport.count(hql+" and status=6 and type.sid=? and realEndTime <= endTime  ",  p.toArray());//正常已办结
					long count7=simpleDaoSupport.count(hql+" and status=6 and type.sid=? and realEndTime > endTime ",  p.toArray());//逾期已办结
					  
					 p.add(currentTime);
					   
					 long count3=simpleDaoSupport.count(hql+" and status in (1,2,4,5) and type.sid=? and endTime >=? ", p.toArray());//正常办理中
					 long count4=simpleDaoSupport.count(hql+" and status in (1,2,4,5) and type.sid=? and endTime < ? ", p.toArray());//逾期办理中
					   
					  
					  map.put("sid", type.getSid());
					  map.put("typeName", type.getTypeName());
					  map.put("sumCount", sumCount);
					  map.put("count1", count1);
					  map.put("count2", count2);
					  map.put("count3", count3);
					  map.put("count4", count4);
					  map.put("count5", count5);
					  map.put("count6", count6);
					  map.put("count7", count7);
					  map.put("children", new ArrayList());
					  
					 childList.add(map);
				}	
			}	
		}
		((List)typeMap.get("children")).addAll(childList);
		
		for(Map data:childList){
			setChildInfos(typeList,typeMapList,data,hql,param);
		}
		
	}

	
	/**
	 * 督察督办任务  -----按状态
	 * @param dm
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public TeeEasyuiDataGridJson getSupCountByStatus(TeeDataGridModel dm,
			HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的参数
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		int deptId=TeeStringUtil.getInteger(request.getParameter("deptId"),0);
		
		String beginTimeStr1=TeeStringUtil.getString(request.getParameter("beginTimeStr1"));
		String beginTimeStr2=TeeStringUtil.getString(request.getParameter("beginTimeStr2"));	
		
		String hql=" select count(*) from TeeSupervision where 1=1 ";
		Date beginTime1=null;
		Date beginTime2=null;
		Date currentTime=new Date();
		String currentTimeStr=sdf.format(currentTime);
		currentTime=sdf.parse(currentTimeStr);
		
		
		if(!("").equals(beginTimeStr1)){
			beginTime1=sdf.parse(beginTimeStr1);
		}
		if(!("").equals(beginTimeStr2)){
			beginTime2=sdf.parse(beginTimeStr2);
		}
		
		
		List param=new ArrayList();
		if(deptId!=0){
			hql+="  and manager.dept.uuid=? ";
			param.add(deptId);
		}
		if(typeId!=0){
			hql+="  and type.sid=? ";
			param.add(typeId);
		}
		if(beginTime1!=null){
			hql+="  and beginTime>=? ";
			param.add(beginTime1);
		}
		if(beginTime2!=null){
			hql+="  and beginTime<=? ";
			param.add(beginTime2);
		}
		
		
		List<Map> result=new ArrayList<Map>();
		j.setTotal(TeeStringUtil.getLong("7", 0));//设置总的记录数
		
	
		   
		   
		Map map1=new HashMap();
		Map map2=new HashMap();   
		Map map3=new HashMap();   
		Map map4=new HashMap();   
		Map map5=new HashMap();   
		Map map6=new HashMap();   
		Map map7=new HashMap();   
		Map map8=new HashMap();
		
		long sumCount=simpleDaoSupport.count(hql, param.toArray());//总数
	    map8.put("status","总数");
	    map8.put("count", sumCount);
		long count1=simpleDaoSupport.count(hql+" and status=0  ",  param.toArray());//未发布
		map1.put("status","未发布");
	    map1.put("count", count1);
		long count2=simpleDaoSupport.count(hql+" and status=7  ",  param.toArray());//待签收
		map2.put("status","待签收");
	    map2.put("count", count2);
		long count5=simpleDaoSupport.count(hql+" and status=3  ",  param.toArray());//已暂停
		map5.put("status","已暂停");
	    map5.put("count", count5);
		long count6=simpleDaoSupport.count(hql+" and status=6  and realEndTime <= endTime  ",  param.toArray());//正常已办结
		map6.put("status","正常已办结");
	    map6.put("count", count6);
		long count7=simpleDaoSupport.count(hql+" and status=6  and realEndTime > endTime ",  param.toArray());//逾期已办结
		map7.put("status","逾期已办结");
	    map7.put("count", count7); 
	    
		param.add(currentTime);   
		long count3=simpleDaoSupport.count(hql+" and status in (1,2,4,5)  and endTime >=? ", param.toArray());//正常办理中
		map3.put("status","正常办理中");
	    map3.put("count", count3); 
		long count4=simpleDaoSupport.count(hql+" and status in (1,2,4,5)  and endTime < ? ", param.toArray());//逾期办理中
		map4.put("status","逾期办理中");
	    map4.put("count", count4);    
		   
		
		   
		result.add(map1);
		result.add(map2);
		result.add(map3);
		result.add(map4);
		result.add(map5);
		result.add(map6);
		result.add(map7);
		result.add(map8);

		
		j.setRows(result);// 设置返回的行
		return j;
	}

	
	/**
	 * 手机端新建/编辑
	 * @param request
	 * @return
	 */
	public TeeJson mobileAddOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		//获取页面上传来的数据
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String supName=TeeStringUtil.getString(request.getParameter("supName"));
		int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"),0);
		int leaderId=TeeStringUtil.getInteger(request.getParameter("leaderId"),0);
		int managerId=TeeStringUtil.getInteger(request.getParameter("managerId"), 0);
		String assistIds=TeeStringUtil.getString(request.getParameter("assistIds"));
		Date beginTime=TeeStringUtil.getDate(request.getParameter("beginTimeStr"),"yyyy-MM-dd");
		Date endTime=TeeStringUtil.getDate(request.getParameter("endTimeStr"),"yyyy-MM-dd");
		String content=TeeStringUtil.getString(request.getParameter("content"));
	    int status=TeeStringUtil.getInteger(request.getParameter("status"),0);
	    //父任务主键
	    int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"),0);
	    TeeSupervision parent=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,parentId);
	    
	    
	    TeeSupervisionType type=(TeeSupervisionType) simpleDaoSupport.get(TeeSupervisionType.class,typeId);
	    TeePerson leader=(TeePerson) simpleDaoSupport.get(TeePerson.class,leaderId);
	    TeePerson manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
	    TeePerson assist=null;
	    Set<TeePerson> assists=new HashSet<TeePerson>();
	    if(!("").equals(assistIds)){
	    	String []assistIdArray=assistIds.split(",");
	    	for (String str : assistIdArray) {
				int uuid=TeeStringUtil.getInteger(str,0);
				assist=(TeePerson) simpleDaoSupport.get(TeePerson.class,uuid);
				assists.add(assist);		
			}
	    }
	    
	    //附件主键
	    String attachIds=TeeStringUtil.getString(request.getParameter("attachIds"));
	    List<TeeAttachment> attachments = attachmentService.getAttachmentsByIds(attachIds);
	    
	    TeeSupervision sup=null;
	    if(sid>0){//编辑
	    	sup=(TeeSupervision) simpleDaoSupport.get(TeeSupervision.class,sid);
	    	sup.setBeginTime(beginTime);
	    	sup.setContent(content);
	    	sup.setEndTime(endTime);
	    	sup.setLeader(leader);
	    	sup.setManager(manager);
	    	sup.setSupName(supName);
	    	sup.setType(type);
	    	sup.getAssists().clear();
	    	sup.getAssists().addAll(assists);
	    	sup.setStatus(status);
	    	simpleDaoSupport.update(sup);
	    	
	    	//添加新附件
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(sup.getSid()));
				attachmentDao.update(attach);
			}
	    }else{//新建
	    	sup=new TeeSupervision();
	    	sup.setBeginTime(beginTime);
	    	sup.setContent(content);
	    	sup.setCreater(loginUser);
	    	sup.setCreaterTime(new Date());
	    	sup.setEndTime(endTime);
	    	sup.setLeader(leader);
	    	sup.setManager(manager);
	    	sup.setSupName(supName);
	    	sup.setType(type);
	    	sup.getAssists().addAll(assists);
	    	sup.setStatus(status);
	    	sup.setParent(parent);
	    	simpleDaoSupport.save(sup);
	    	
	    	//添加新附件
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(sup.getSid()));
				attachmentDao.update(attach);
			}
	    	
			
			//如果是直接发布的话    发送消息提醒
			if(status==7){//发布
				
				if(sup.getLeader()!=null){
					// 发送消息  给责任领导
				    Map requestData1 = new HashMap();
					requestData1.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为责任领导，请查看。");
					requestData1.put("userListIds", sup.getLeader().getUuid());
					requestData1.put("moduleNo", "061");
					requestData1.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
					smsManager.sendSms(requestData1, loginUser);
				}
				
					
			   if(sup.getManager()!=null){
				 //发送消息给主办人
					Map requestData2 = new HashMap();
					requestData2.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为主办人，请查看。");
					requestData2.put("userListIds", sup.getManager().getUuid());
					requestData2.put("moduleNo", "061");
					requestData2.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
					smsManager.sendSms(requestData2, loginUser);
			   }

				
				if(!("").equals(assistIds)){
					//发送消息给协办人
					Map requestData3 = new HashMap();
					requestData3.put("content", "您有一个督办任务《"+sup.getSupName()+"》指派您为协办人，请查看。");
					requestData3.put("userListIds", assistIds);
					requestData3.put("moduleNo", "061");
					requestData3.put("remindUrl","/system/subsys/supervise/handle/index.jsp?sid="+sup.getSid());
					smsManager.sendSms(requestData3, loginUser);
				}
				
			}
			
	    }
		json.setRtState(true);
		Map map=new HashMap();
		if(sup!=null&&sup.getType()!=null){
			map.put("typeName", sup.getType().getTypeName());
			map.put("typeSid", sup.getType().getSid());
		}
		json.setRtData(map);
		return json;
	}
	
	

	

}
