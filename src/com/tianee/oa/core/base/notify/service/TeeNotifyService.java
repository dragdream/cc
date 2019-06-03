package com.tianee.oa.core.base.notify.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyInfoDao;
import com.tianee.oa.core.base.notify.model.TeeNotifyInfoModel;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.phoneSms.service.TeeSmsSendPhoneService;
import com.tianee.oa.core.priv.bean.TeePersonResPriv;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModelIdConst;
import com.tianee.oa.oaconst.TeeSysParaKeys;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.model.DocumentInfoModel;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.oa.subsys.weixin.msg.util.SMessage;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeManagerPostPersonDataPrivModel;
import com.tianee.oa.webservice.model.Attachment;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
/**
 * 公告通知 服务层
 * @author zhp
 * @createTime 2013-12-26
 * @desc
 */
@Service
public class TeeNotifyService extends TeeBaseService {

	@Autowired
	private TeeNotifyDao notifyDao;
	
	@Autowired
	private TeeNotifyInfoDao notifyInfoDao;
	
	@Autowired
	private TeePersonManagerI personManagerI;
	
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeUserRoleDao userRoleDao;
	
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	@Autowired
	TeeAttachmentService attachmentService;
	
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeeSysParaDao sysParaDao;
	
	
	@Autowired
	private TeeSmsManager smsManager;
	
	@Autowired
	private TeeSmsSendPhoneService smsSendPhoneService;
	
	@Autowired
	private CmsDocumentService documentService;
	
	/**
	 * 添加或者编辑
	 * @author syl
	 * @date 2014-3-11
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public TeeJson addOrUpdateNotify(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String subject = TeeStringUtil.getString(multipartRequest.getParameter("subject"),"");
		String top = TeeStringUtil.getString(multipartRequest.getParameter("top"), "0");
		String contentStyle = TeeStringUtil.getString(multipartRequest.getParameter("contentStyle"), "0");
		String synchronizeCMS = TeeStringUtil.getString(multipartRequest.getParameter("synchronizeCMS"), "0");
		String toId = TeeStringUtil.getString(multipartRequest.getParameter("toDeptIds"),"");
		String privId = TeeStringUtil.getString(multipartRequest.getParameter("toRolesIds"),"");
		String userId = TeeStringUtil.getString(multipartRequest.getParameter("toUserIds"),"");//  
		String smsRemind = TeeStringUtil.getString(multipartRequest.getParameter("smsRemind"),"0");//  短信提醒
		List<TeePerson> pList = new ArrayList<TeePerson>();
		
		String content = TeeStringUtil.getString(multipartRequest.getParameter("content"),"");
		String attachmentSidStr = TeeStringUtil.getString(multipartRequest.getParameter("attachmentSidStr"),"0");
		String typeId = TeeStringUtil.getString(multipartRequest.getParameter("typeId"),"");
		/**
		 * 判断一下是否需要审批
		 */
		//		boolean isAuding = notifyService.isNeedAduing();
		
		/**
		 * 审批人
		 */
		int auditer = TeeStringUtil.getInteger(multipartRequest.getParameter("auditer"),0);
		
		/**
		 * 发布状态
		 */
		int publish = TeeStringUtil.getInteger(multipartRequest.getParameter("publish"),0);
		
		TeePerson auditerPerson  = null;
		if(auditer > 0){
			auditerPerson = personDao.selectPersonById(auditer);
		}
		
		Date beginDate = TeeStringUtil.getDate(multipartRequest.getParameter("beginDate"),"yyyy-MM-dd");
		Date endDate = TeeStringUtil.getDate(multipartRequest.getParameter("endDate"),"yyyy-MM-dd");
		List<TeeAttachment> attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.NOTIFY);
		TeeNotify notify = new TeeNotify();
		int allPriv = 0;
		int sid = TeeStringUtil.getInteger(multipartRequest.getParameter("sid"), 0);
		/*获取发布范围人员*/
		if(TeeUtility.isNullorEmpty(toId) && TeeUtility.isNullorEmpty(userId) && TeeUtility.isNullorEmpty(privId)){
			pList = personDao.getAllUserNoDelete();
//			List<TeeDepartment> listDept = deptDao.getAllDept();
//			notify.setPostDept(listDept);
			allPriv = 1;
		}else{
			
			//根据部门的id  获取部门的dept_full_id
			String Ids="";
			String[]dIds=toId.split(",");	
			for (String str : dIds) {
		    
				if(!TeeUtility.isNullorEmpty(str)){
					int deptId=Integer.parseInt(str);
					TeeDepartment d=deptDao.get(deptId);
					String fullId=d.getDeptFullId();
					List<TeeDepartment> departmentList=deptDao.getAllChildDept(fullId);
					//System.out.println("子部门的个数"+departmentList.size());
					String deptIds="";
					for (TeeDepartment dept : departmentList) {
						deptIds+=dept.getUuid()+",";
					}
					Ids+=deptIds;	
				}	
			}
			if(Ids.endsWith(",")){
				Ids=Ids.substring(0,Ids.lastIndexOf(","));
			}	
			
			pList = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(loginPerson, userId, Ids, privId);
			if(!TeeUtility.isNullorEmpty(toId)){//发布权限  ---部门
				List<TeeDepartment> listDept = deptDao.getDeptListByUuids(toId);
				notify.setPostDept(listDept);
			}
			if(!TeeUtility.isNullorEmpty(userId)){//申发布权限- 人员
				List<TeePerson> listDept = personDao.getPersonByUuids(userId);
				notify.setPostUser(listDept);
			}
			
			if(!TeeUtility.isNullorEmpty(privId)){//fa发布权限 -- 角色
				List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(privId);
				notify.setPostUserRole(listRole);
			}
		}

		
		if(sid > 0){
			TeeNotify oldNotify = notifyDao.getById(sid);
			if(oldNotify != null){
				if(publish == 1 && "1".equals(synchronizeCMS)){
					//同步cms网站
					addOrUpdDocument(request,sid,attachments);
				}
				oldNotify.setSubject(subject);
				String oldPublish = TeeStringUtil.getString(oldNotify.getPublish(), "0");
				if(oldPublish.equals("0")){
					oldNotify.setPublish(publish + "");
				}
				oldNotify.setPublish(String.valueOf(publish));
				oldNotify.setContentStyle(contentStyle);
				oldNotify.setSynchronizeCMS(synchronizeCMS);
				oldNotify.setDocContentSid(Integer.valueOf(attachmentSidStr));
				oldNotify.setBeginDate(beginDate);
				oldNotify.setEndDate(endDate);
				oldNotify.setFromPerson(loginPerson);
				oldNotify.setAllPriv(allPriv);
				//添加新附件
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(oldNotify.getSid()));
					attachmentDao.update(attach);
				}
				oldNotify.setContent(content);
				oldNotify.setPostDept(notify.getPostDept());
				oldNotify.setPostUser(notify.getPostUser());
				oldNotify.setPostUserRole(notify.getPostUserRole());
				oldNotify.setTop(top);
				oldNotify.setTypeId(typeId);
				oldNotify.setAuditer(auditerPerson);
				oldNotify.setSendTime(new Date());
				oldNotify.setCreateDate(oldNotify.getCreateDate());
				
				int attaSid = Integer.valueOf(attachmentSidStr);
				TeeAttachment docAttachment = attachmentService.getById(attaSid);
				if (docAttachment != null) {
					docAttachment.setModelId(oldNotify.getSid()+"");
					attachmentDao.update(docAttachment);;
				}
				
				notifyDao.updateNotify(oldNotify);
				notify.setSid(oldNotify.getSid());
				json.setRtMsg("更新公告通知成功!");
			}else{
				json.setRtState(false);
				json.setRtMsg("改公告已被删除!");
				return json;
			}
		}else{
			if(publish == 1 && "1".equals(synchronizeCMS)){
				//同步cms网站
				DocumentInfo documentInfo = addOrUpdDocument(request,sid,attachments);
				notify.setIsAddFlag("1");
				notify.setCmsDocumentId(documentInfo.getSid());
			}
			notify.setSubject(subject);
			notify.setPublish(String.valueOf(publish));
			notify.setContentStyle(contentStyle);
			notify.setSynchronizeCMS(synchronizeCMS);
			notify.setDocContentSid(Integer.valueOf(attachmentSidStr));
			notify.setAuditer(auditerPerson);
			notify.setBeginDate(beginDate);
			notify.setEndDate(endDate);
			notify.setFromPerson(loginPerson);
			notify.setAllPriv(allPriv);
			
			if(publish != 0){
				notify.setSendTime(new Date()); 
			}
			notify.setContent(content);
			notify.setTop(top);
			notify.setTypeId(typeId);
			SimpleDateFormat  sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createDateStr=sf.format(new Date());
			Date createDate=null;
			try {
				createDate = sf.parse(createDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notify.setCreateDate(createDate);//设置公告的创建时间
			notifyDao.addNotify(notify);
			
			//添加新附件
			for(TeeAttachment attach:attachments){
				attach.setModelId(String.valueOf(notify.getSid()));
				attachmentDao.update(attach);
			}
			
			json.setRtMsg("添加公告通知成功!");
		}
		
		//短信提醒有权限的人员
		if("1".equals(String.valueOf(publish)) && smsRemind.equals("1")){
			Map requestData = new HashMap();
			requestData.put("content", "请查看公告："+subject);
			requestData.put("userList",pList );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "021");
			requestData.put("remindUrl", "/system/core/base/notify/person/readNotify.jsp?id="+notify.getSid()+"&isLooked=0&sid="+notify.getSid());
			//手机端事务提醒
			requestData.put("remindUrl1", "/system/mobile/phone/notify/notifyInfo.jsp?id="+notify.getSid()+"&isLooked=0&sid="+notify.getSid());
			
			smsManager.sendSms(requestData, loginPerson);
		}
		
		String phoneSms = TeeStringUtil.getString(request.getParameter("phoneSms"));
		if(!"".equals(phoneSms) && "1".equals(publish+"")){//发送手机短信
			Map requestData = new HashMap();
			requestData.put("smsContent", "请查看公告："+subject);
			requestData.put("hasRemindPrivPersonList",pList);
			requestData.put("sendTime","");
			requestData.put(TeeConst.LOGIN_USER,loginPerson);
			smsSendPhoneService.sendPhoneSms(requestData);
		}
		
		//短信提醒提醒审批人员
		String audSmsRemind = TeeStringUtil.getString(multipartRequest.getParameter("audSmsRemind"),"0");//  短信提醒
		if("2".equals(publish + "")){//待审批
			Map requestData = new HashMap();
			requestData.put("content", "您有待审批的公告，请及时处理！");
			requestData.put("userListIds",auditer );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "021");
			requestData.put("remindUrl", "/system/core/base/notify/audit/index.jsp");
			smsManager.sendSms(requestData, loginPerson);
			
			List l=new ArrayList();
			l.add(auditerPerson);
			requestData = new HashMap();
			requestData.put("smsContent", "您有待审批的公告，请及时处理！");
			requestData.put("hasRemindPrivPersonList",l);
			requestData.put("sendTime","");
			requestData.put(TeeConst.LOGIN_USER,loginPerson);
			smsSendPhoneService.sendPhoneSms(requestData);
		}
		//发送到企业微信号
//		String wechat = TeeStringUtil.getString(request.getParameter("wechat"));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//		String release_time = sdf.format(new Date());
//		String content_html = "<table width=\"100%\" align=\"center\">"
//        +"<tr><td  width=\"100%\"><center>发布部门：&nbsp;"+notify.getFromPerson().getUserName()
//        +"&nbsp;&nbsp;发布人：&nbsp;"+notify.getFromPerson().getDept().getDeptName()+"&nbsp;&nbsp;发布于：&nbsp;"+release_time+"</center></td></tr>"
//        +"<tr><td  colspan=\"2\" valign=\"top\"><span>"+content+"</span></td></tr>"
//        +"</table>";
//		TeeSysPara sysPara1 = sysParaDao.getSysPara("WE_CHAT_CORPID");
//		TeeSysPara sysPara2 = sysParaDao.getSysPara("WE_CHAT_SECRET");
//		if(sysPara1 !=null && sysPara2 != null){
//			if("1".equals(wechat)&&"1".equals(publish+"")){
//				String result = SMessage.releaseWechat(sysPara1.getParaValue(),sysPara2.getParaValue(),subject,content_html,"1");
//			}
//		}
		
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 审批通过后同步到cms网站对应栏目下
	 * @param request
	 */
	public DocumentInfo addOrUpdAudDocument(TeeNotify notify,TeePerson loginUser){
		DocumentInfoModel documentInfoModel = new DocumentInfoModel();
		documentInfoModel.setDocId(notify.getCmsDocumentId());
		//不选择类型默认存在通知公告栏目下
		String typeId = notify.getTypeId();
		if (TeeUtility.isNullorEmpty(typeId)) {
			typeId = "03";
		}
		Map channelMap = simpleDaoSupport.getMap("select info.sid as sid, info.siteId as siteId from ChannelInfo info where typeId = '"+typeId+"'", null);
		//设置文化执法门户站点id
		documentInfoModel.setSiteId((int)channelMap.get("siteId"));
		//设置栏目id 
		documentInfoModel.setChnlId((int)channelMap.get("sid"));
		documentInfoModel.setDocTitle(notify.getSubject());
		documentInfoModel.setHtmlContent(notify.getContent());
		int docContentSid = notify.getDocContentSid();
		documentInfoModel.setDocAttachmentId(Integer.valueOf(docContentSid));
		List<TeeAttachment> attachments = attachmentService.getAttaches(TeeAttachmentModelKeys.NOTIFY, notify.getSid()+"");
		
		if("1".equals(notify.getIsAddFlag())){//修改
			documentInfoModel.setDocAttachmentId(notify.getDocContentSid());
			documentService.updateDocument(documentInfoModel, loginUser);
			
			for (TeeAttachment attach : attachments) {
				TeeAttachment attachment = attachmentService.clone(attach, TeeAttachmentModelKeys.cms, loginUser);
				attachment.setModelId(notify.getCmsDocumentId()+"");
				attachmentDao.addAttac(attachment);
			}
			return null;
		}else{
			//设置电子文档内容的附件id
			documentInfoModel.setDocAttachmentId(TeeStringUtil.getInteger(notify.getDocContentSid(), 0));
			DocumentInfo doc =documentService.addDocument(documentInfoModel,loginUser);
			//添加附件
			for (TeeAttachment teeAttachment : attachments) {
				TeeAttachment attachment = attachmentService.clone(teeAttachment, TeeAttachmentModelKeys.cms, loginUser);
				attachment.setModelId(doc.getSid()+"");
				attachmentService.addAttachment(attachment);
			}
			return doc;
		}
		
	}
	
	/**
	 * 同步到cms网站对应栏目下
	 * @param request
	 */
	public DocumentInfo addOrUpdDocument(HttpServletRequest request,int sid,List<TeeAttachment> attachments){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		String subject = TeeStringUtil.getString(multipartRequest.getParameter("subject"),"");
		String typeId = TeeStringUtil.getString(multipartRequest.getParameter("typeId"),"03");
		String attachmentSidStr = TeeStringUtil.getString(multipartRequest.getParameter("attachmentSidStr"),"0");
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		DocumentInfoModel documentInfoModel = (DocumentInfoModel) TeeServletUtility.request2Object(request, DocumentInfoModel.class);
		TeeNotify notify = notifyDao.getById(sid);
		
		//设置文化执法门户站点id
		Map channelMap = simpleDaoSupport.getMap("select info.sid as sid, info.siteId as siteId from ChannelInfo info where typeId = '"+typeId+"'", null);
		documentInfoModel.setSiteId((int)channelMap.get("siteId"));
		//设置栏目id
		documentInfoModel.setChnlId((int)channelMap.get("sid"));
		documentInfoModel.setDocTitle(subject);
		documentInfoModel.setHtmlContent(request.getParameter("content"));
		String attachmentId = TeeStringUtil.getString(multipartRequest.getParameter("attachmentSidStr"),"0");
		documentInfoModel.setDocAttachmentId(Integer.valueOf(attachmentId));
		if(sid > 0 && "1".equals(notify.getIsAddFlag())){//修改
			
			documentInfoModel.setDocAttachmentId(notify.getDocContentSid());
			documentService.updateDocument(documentInfoModel, loginUser);
			
			for (TeeAttachment teeAttachment : attachments) {
				TeeAttachment attachment = attachmentService.clone(teeAttachment, TeeAttachmentModelKeys.cms, loginUser);
				attachment.setModelId(notify.getCmsDocumentId()+"");
				attachmentDao.addAttac(attachment);
			}
			return null;
		}else{
			//设置电子文档内容的附件id
			documentInfoModel.setDocAttachmentId(TeeStringUtil.getInteger(attachmentSidStr, 0));
			DocumentInfo doc =documentService.addDocument(documentInfoModel,loginUser);
			//添加附件
			for (TeeAttachment attach : attachments) {
				TeeAttachment attachment = attachmentService.clone(attach, TeeAttachmentModelKeys.cms, loginUser);
				attachment.setModelId(doc.getSid()+"");
				attachmentDao.addAttac(attachment);
			}
			return doc;
		}
	}
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-2-24
	 * @editTime 下午11:51:07
	 * @desc
	 */
	public void updateNotify(TeeNotify notify){
		notifyDao.update(notify);
	}
	
	/**
	 * 查看公告  ---- 已读和未读
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getNotifyList(TeeDataGridModel dm,Map paraMap,TeePerson person,int isRed) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String typeId = TeeStringUtil.getString(paraMap.get("typeId"));
		j.setTotal(notifyDao.getPersonalNoReadCount(isRed, person,typeId));// 设置总记录数
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		
		List<TeeNotify> notifys = notifyDao.getPersonalNoRead(isRed, person, dm,typeId);
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setFromPersonName(n.getFromPerson().getUserName());
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		return j;
	}
	
	
	/**
	 * 查看公告    --- 查询
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson query(TeeDataGridModel dm,HttpServletRequest request,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeNotifyModel model = new TeeNotifyModel();
		model.setReadType(-1);//设置为全部状态（包含已读和未读）
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map map = notifyDao.getPersonalQuery(person, dm, model, "0",request);
		long count = (Long)map.get("notifyCount");
		j.setTotal(count);// 设置总记录数
		
		
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		map = notifyDao.getPersonalQuery(person, dm, model, "1",request);
		List<TeeNotify> notifys = (List<TeeNotify>)map.get("notifyList");
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setContent("");
				m.setShortContent("");
				m.setFromPersonName(n.getFromPerson().getUserName());
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		return j;
	}
	
	
	
	/**
	 * 公告管理    --- 查询
	 * @author syl
	 * @date 2014-3-13
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson managerQuery(TeeDataGridModel dm,HttpServletRequest request,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeeNotifyModel model = new TeeNotifyModel();
		Map mapTemp = new HashMap();
		mapTemp.put(TeeConst.LOGIN_USER, person);
		//将request中的对应字段映值射到目标对象的属性中
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(mapTemp, TeeModelIdConst.NOTIFY_POST_PRIV, "0");
		
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map map = notifyDao.managerQuery(person, dm, model, "0" , dataPrivModel);
		long count = (Long)map.get("notifyCount");
		j.setTotal(count);// 设置总记录数
		
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		map = notifyDao.managerQuery(person, dm, model, "1" ,dataPrivModel);
		List<TeeNotify> notifys = (List<TeeNotify>)map.get("notifyList");
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		return j;
	}
	
	
	
	
	
	

	/**
	 * 获取公告通知  state = 0 未读公告 1 已读公告 count = -1 为全部  count > 0 时候为 0 ~ count 条
	 * @author zhp
	 * @createTime 2014-2-13
	 * @editTime 下午02:02:51
	 * @desc
	 */
	@Transactional(readOnly = true)
	public List getAllNotifyListByState(TeePerson person,int state,int count,TeeNotifyModel model) {
	/*	List paraList = new ArrayList();
		String subject = (String)paraMap.get("subject");
		
		paraList.add(person.getUuid());
		String hql = "from TeeNotify n where  n.publish = '1'  and   exists (select 1 from n.infos info where info.toUser.uuid = "+person.getUuid()+"";
		if(state == -1){
			hql = hql + ")";
		}else{
			hql = hql + "and info.isRead = "+state+" )";
		}
		if(!TeeUtility.isNullorEmpty(subject)){
			hql = hql + "and n.subject like '%"+subject+"%' )";
		}*/
		List<TeeNotify> notifys  = notifyDao.selectNotifyToDiskAndMainQuery(person, model, count, state);
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = new TeeNotifyModel();
				BeanUtils.copyProperties(n, m);
				m.setFromPersonName(n.getFromPerson().getUserName());
				notifysmodel.add(m);
			}
		}
		return notifysmodel;
	}
	
	/**
	 * 获取 要管理的公告
	 * @author syl
	 * @date 2014-3-12
	 * @param dm  通用列表模型
	 * @param paraMap 
	 * @param person 系统登录人
	 * @param isRed  状态
	 * @return
	 */
	/**
	 * @author syl
	 * @date 2014-3-16
	 * @param dm
	 * @param paraMap
	 * @param person
	 * @param isRed
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getManageNotify(HttpServletRequest request , TeeDataGridModel dm,Map paraMap,TeePerson person,int isRed) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"));
		String subject = TeeStringUtil.getString(request.getParameter("subject"));
		String content = TeeStringUtil.getString(request.getParameter("content"));
		List paraList = new ArrayList();
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		boolean isSuperAdmin = TeePersonService.checkIsSuperAdmin(person, "");
		String hql = "from TeeNotify n where 1=1 ";
	
		if(!"".equals(typeId)){
			hql+=" and n.typeId='"+typeId+"'";
		}
		
		if(!TeeUtility.isNullorEmpty(content)){
			hql = hql + " and n.content like '%"+content+"%'";
		}
		
		if(!TeeUtility.isNullorEmpty(subject)){
			hql = hql + " and n.subject like  '%"+subject+"%'";
		}
		
		
		Map map = new HashMap();
		map.put(TeeConst.LOGIN_USER, person);
		//数据管理权限
		TeeManagerPostPersonDataPrivModel dataPrivModel = personManagerI.getManagerPostPersonIdsPriv(map, TeeModelIdConst.NOTIFY_POST_PRIV, "0");
		if(dataPrivModel.getPrivType().equals("0")){//空
			paraList.add(person.getUuid());
			hql = hql + " and n.fromPerson.uuid = ?";//加上自己创建的
		}else if(dataPrivModel.getPrivType().equals("ALL")){//所有
			// hql = "from TeeNews n where  1 = 1";
		}else{
			List<Integer> pIdList = dataPrivModel.getPersonIds();//获取权限
			pIdList.add(person.getUuid());
			if(pIdList.size() > 0){
				String personIdsSql =  TeeDbUtility.IN("n.fromPerson.uuid", pIdList);
				hql = hql + " and " + personIdsSql;
			}else{
				j.setTotal(0L);
				j.setRows(notifysmodel);// 设置返回的行
				return j;
			}
		}
		String totalHql = " select count(*) " + hql;
		j.setTotal(notifyDao.countByList(totalHql, paraList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("fromPersonName")){
				dm.setSort("fromPerson");
			}
			hql += " order by " + dm.getSort() + " "+dm.getOrder();
		}else{
			hql += " order by n.top desc,n.createDate desc" ;
		}
		
		List<TeeNotify> notifys = notifyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),paraList);// 查询
		
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		return j;
	}
	
	/**
	 * 根据Id 获取对象
	 * @author syl
	 * @date 2014-3-12
	 * @param sid
	 * @return
	 */
	public TeeNotify getNotifyById(int sid){
		return notifyDao.getById(sid);
	}
	
	
	
	/**
	 * 获取对象 byId
	 * @author syl
	 * @date 2014-3-12
	 * @param sid
	 * @return
	 */
	public TeeJson selectById(int sid){
		TeeJson json = new TeeJson();
		TeeNotify notify = notifyDao.getById(sid);
		if(notify != null ){
			TeeNotifyModel model = parseNotifyModelById(notify, false);
			json.setRtState(true);
			json.setRtData(model);
		}else{
			json.setRtState(false);
			json.setRtMsg("改公告已被删除！");
		}
		return json;
	}
	
	
	
	
	/**
	 * 获取 TeeNotifyInfo 获取阅读人 列表 详情
	 * @author syl
	 * @date 2014-3-14
	 * @param request
	 * @return
	 */
	public TeeJson getNotifyInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		List<TeeNotifyInfoModel> resultList = new ArrayList<TeeNotifyInfoModel>();
		TeeNotify notify = notifyDao.getById(sid);
		if(notify == null){
			json.setRtMsg("改公告已被删除");
			json.setRtState(false);
			return json;
		}
		List<TeePerson> pList = notify.getPostUser();
		List<TeeDepartment> dList = notify.getPostDept();
		List<TeeUserRole> rList = notify.getPostUserRole();
		String uuids = "";//人员Ids
		String deptIds = "";//部门Ids
		String roleIds = "";//角色Ids
		if(pList != null && pList.size() > 0){
			for (int i = 0; i < pList.size(); i++) {
				uuids = uuids + pList.get(i).getUuid() + ",";
			}
		}
		
		String Ids="";
		if(dList != null && dList.size() > 0){
			for (int i = 0; i < dList.size(); i++) {
				deptIds = deptIds + dList.get(i).getUuid() + ",";
			}
			String[]dIds=deptIds.split(",");	
			for (String str : dIds) {
				int deptId=Integer.parseInt(str);
				TeeDepartment d=deptDao.get(deptId);
				String fullId=d.getDeptFullId();
				List<TeeDepartment> departmentList=deptDao.getAllChildDept(fullId);
				//System.out.println("子部门的个数"+departmentList.size());
				String deptIdss="";
				for (TeeDepartment dept : departmentList) {
					deptIdss+=dept.getUuid()+",";
				}
				Ids+=deptIdss;		
			}
			if(Ids.endsWith(",")){
				Ids=Ids.substring(0,Ids.lastIndexOf(","));
			}		
			
		}
		
		if(rList != null && rList.size() > 0){
			for (int i = 0; i < rList.size(); i++) {
				roleIds = roleIds + rList.get(i).getUuid() + ",";
			}
		}
		List<TeePerson> personList = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(loginPerson, uuids, Ids, roleIds);
		if(notify.getAllPriv()==1){
			personList = personDao.getAllUser();
		}
		List<TeeNotifyInfo> infoList = notify.getInfos();
		Map map = new HashMap();
		if (infoList != null && infoList.size() > 0) {
			for (TeeNotifyInfo n : infoList) {
				TeeNotifyInfoModel m = new TeeNotifyInfoModel();
				if(n.getToUser()!=null){
					if(n.getToUser().getUserRole()!=null){
						m.setRoleName(n.getToUser().getUserRole().getRoleName());
					}
					if(n.getToUser().getDept()!=null){
						m.setDeptName(n.getToUser().getDept().getDeptName());
					}
				}
				
				m.setIsRead(n.getIsRead());
				m.setSid(n.getSid());
				if(n.getToUser()!=null){
					m.setUserName(n.getToUser().getUserName());
				}
				
				if(n.getIsRead() == 0){
					m.setStateDesc("未读");
				}else{
					m.setStateDesc("已读");
				}
				if(n.getReadTime() != null){
					SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String temTime = sf.format(n.getReadTime());
					m.setReadTime1(temTime);
				}
				
				if(n.getToUser()!=null){
					map.put(n.getToUser().getUuid(), m);
				}
				//resultList.add(m);
			}
		}
		
		for (int i = 0; i < personList.size(); i++) {
			TeeNotifyInfoModel m = new TeeNotifyInfoModel();
			TeePerson temp = personList.get(i);
			if(!"1".equals(temp.getDeleteStatus())){
				Object obj = map.get(temp.getUuid());
				if(obj != null){
					TeeNotifyInfoModel infoM = (TeeNotifyInfoModel)obj;
					resultList.add(infoM);
				}else{
					if(temp.getUserRole()!=null){
						m.setRoleName(temp.getUserRole().getRoleName());
					}
					if(temp.getDept()!=null){
						m.setDeptName(temp.getDept().getDeptName());
					}
					
					m.setIsRead(0);
					m.setSid(0);
					m.setUserName(temp.getUserName());
					m.setStateDesc("未读");
					resultList.add(m);
				}
			}
		}
		json.setRtData(resultList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 清空阅读详情 
	 * @author syl
	 * @date 2014-3-14
	 * @param request
	 * @return
	 */
	public TeeJson clearNotifyInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		notifyInfoDao.deleteByNotify(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取 待审批的公告通知
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午06:48:02
	 * @desc
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getAudNotify(TeeDataGridModel dm,Map paraMap,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List paraList = new ArrayList();
		paraList.add(person.getUuid());
		String hql = "from TeeNotify n where n.auditer.uuid = ? and n.publish = 2";
		String totalHql = " select count(*) " + hql;
		j.setTotal(notifyDao.countByList(totalHql, paraList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("fromPersonName")){
				dm.setSort("fromPerson");
			}
			hql += " order by " + dm.getSort() + " "+dm.getOrder();
		}else{
			hql += " order by sendTime desc";
		}
		List<TeeNotify> notifys = notifyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),paraList);// 查询
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setFromPersonName(n.getFromPerson().getUserName());
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		return j;
	}
	
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getAuditedNotify(TeeDataGridModel dm,Map paraMap,TeePerson person) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		List paraList = new ArrayList();
		paraList.add(person.getUuid());
		String hql = "from TeeNotify n where n.auditer.uuid = ? and n.publish in (1,3)";
		String totalHql = " select count(*) " + hql;
		j.setTotal(notifyDao.countByList(totalHql, paraList));// 设置总记录数
		if (dm.getSort() != null) {// 设置排序
			if(dm.getSort().equals("fromPersonName")){
				dm.setSort("fromPerson");
			}
			hql += " order by " + dm.getSort() + " "+dm.getOrder();
		}else{
			hql += " order by sendTime desc";
		}
		List<TeeNotify> notifys = notifyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),paraList);// 查询
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n , true);
				m.setFromPersonName(n.getFromPerson().getUserName());
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		return j;
	}
	
	
	/**
	 * 转换     通知公告 模版
	 * @author syl
	 * @date 2014-3-12
	 * @param n
	 * @param isSimple 是否简单模式
	 * @return
	 */
	public TeeNotifyModel parseNotifyModelById(TeeNotify n , boolean isSimple ){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<TeeAttachment> sorceAttachs = null;
		List<Map> attachs = new ArrayList<Map>();
		TeeAttachment docContentAttachment = new TeeAttachment();
		Map contentMap = new HashMap<>();
		TeeNotifyModel m = new TeeNotifyModel();
		BeanUtils.copyProperties(n, m);
		if(null!=n.getFromPerson()){
			m.setFromPersonName(n.getFromPerson().getUserName());
			m.setFromDeptName(n.getFromPerson().getDept().getDeptName());
		}else{
			m.setFromPersonName("");
			m.setFromDeptName("");
		}
		if(!isSimple){
			sorceAttachs = attachmentService.getAttaches(TeeAttachmentModelKeys.NOTIFY, String.valueOf(n.getSid()));
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
			m.setAttachmentsMode(attachs);
			// 1表示内容上传的形式是电子文档形式
			if ("1".equals(n.getContentStyle())) {
				TeeAttachment docContent = attachmentService.getById(n.getDocContentSid());
				contentMap.put("sid", docContent.getSid());
				contentMap.put("priv", 31);
				contentMap.put("ext", docContent.getExt());
				contentMap.put("module", docContent.getModel());
				contentMap.put("fileName", docContent.getFileName());
			}
			m.setContentMap(contentMap);
			m.setAttachmentSidStr(String.valueOf(n.getDocContentSid()));
		}
		m.setSynchronizeCMS(n.getSynchronizeCMS());
		String toRolesIds = "";//发布部门的id串
		String toRolesNames = "";//发布部门的名字
		String toDeptIds = "";//发布部门的id串
		String toDeptNames = "";//发布部门的id串
		String toUserNames = "";//发布人的名字
		String toUserIds = "";//发布人的id串
		List<TeePerson> pList = n.getPostUser();
		List<TeeDepartment> dList = n.getPostDept();
		List<TeeUserRole> rList = n.getPostUserRole();
		for (int i = 0; i < pList.size(); i++) {
			toUserIds = toUserIds +  (pList.get(i).getUuid() + ",");
			toUserNames = toUserNames + pList.get(i).getUserName() + ",";
		}
		
		for (int i = 0; i < dList.size(); i++) {
			toDeptIds = toDeptIds + dList.get(i).getUuid() + ",";
			toDeptNames=  toDeptNames + dList.get(i).getDeptName() + ",";
		}
		
		for (int i = 0; i < rList.size(); i++) {
			toRolesIds = toRolesIds + rList.get(i).getUuid() + ",";
			toRolesNames=  toRolesNames + rList.get(i).getRoleName() + ",";
		}
		
		String typeDesc = "";
		if(!TeeUtility.isNullorEmpty(n.getTypeId())){
			typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NOTIFY_TYPE", n.getTypeId());
		}
		m.setTypeDesc(typeDesc);
		m.setToDeptIds(toDeptIds);
		m.setToDeptNames(toDeptNames);
		m.setToUserIds(toUserIds);
		m.setToUserNames(toUserNames);
		m.setToRolesIds(toRolesIds);
		m.setToRolesNames(toRolesNames);
		
		//审核人
		String auditerId = "";
		String auditerName = "";
		if(n.getAuditer() != null){
			auditerId = n.getAuditer().getUuid() + "";
			auditerName = n.getAuditer().getUserName();
		}
		m.setAuditerId(auditerId);
		m.setAuditerName(auditerName);
		
		if(n.getSendTime()!=null){
			m.setSendTimeDesc(sdf.format(n.getSendTime()));
		}
		
		
		return m;
	}
	



	/**
	 * 获取 无需审批的人员
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午06:06:34
	 * @desc
	 */
	public String getNotifyAuditingExcPerson(){
		String resultValue = "";
		
		String hql = "from TeeSysPara p where p.paraName = ?";
		TeeSysPara auditingExceptionPara =  (TeeSysPara) simpleDaoSupport.loadSingleObject(hql, new Object[]{TeeSysParaKeys.NOTIFY_AUDITING_EXCEPTION});//无需审批的人
		if(auditingExceptionPara != null ){
			resultValue = auditingExceptionPara.getParaValue();
		}
		return resultValue;
	}
	
	/**
	 * 获取审批人 !@#$%^&*
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午08:09:40
	 * @desc
	 */
	public String getNotifyAuditingPerson(){
		String resultValue = "";
		
		String hql = "from TeeSysPara p where p.paraName = ?";
		TeeSysPara auditingPara =  (TeeSysPara) simpleDaoSupport.loadSingleObject(hql, new Object[]{TeeSysParaKeys.NOTIFY_AUDITING_ALL});//无需审批的人
		if(auditingPara != null ){
			resultValue = auditingPara.getParaValue();
		}
		return resultValue;
	}
	
	/**
	 * 获取审核公告通知权限的人员
	 * @author zhp
	 * @createTime 2014-1-20
	 * @editTime 下午10:00:51
	 * @desc
	 */
	public List getNotifyAduingPerson(){
		TeeSysPara sysPara = (TeeSysPara)simpleDaoSupport.loadSingleObject("from TeeSysPara p where p.paraName = '"+TeeSysParaKeys.NOTIFY_AUDITING_ALL+"'", null);
		String paraValue = "";
		List<TeePerson>  persons = null;
		List list = new ArrayList<Map>();
		if(!TeeUtility.isNullorEmpty(sysPara)){
			paraValue = sysPara.getParaValue();
		}else{
			return null;
		}
		paraValue = TeeUtility.formatIdsQuote(paraValue);
		if(!TeeUtility.isNullorEmpty(paraValue)){
			persons = simpleDaoSupport.find("from TeePerson p where p.uuid in ("+paraValue+")", null);
		}
		if(TeeUtility.isNullorEmpty(persons)){
			return null;
		}else{
			for(int i=0;i<persons.size();i++){
				TeePerson p = persons.get(i);
				Map map = new HashMap<String, String>(); 
				map.put("name", p.getUserName());
				map.put("value", p.getUuid());
				list.add(map);
			}
		}
		return  list;
	}
	
	/**
	 * 获取公告通知系统参数
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 下午05:47:25
	 * @desc
	 */
	public TeeJson getNotifySysPara(){
		TeeJson json = new TeeJson();
		List<TeeSysPara> sysParaList = sysParaDao.getSysParaByNames(TeeSysParaKeys.NOTIFY_AUDITING_ALL + "," + TeeSysParaKeys.NOTIFY_AUDITING_EXCEPTION + "," + TeeSysParaKeys.NOTIFY_AUDITING_SINGLE );
		TeeSysPara auditingPara = null;///需要审批的人
		TeeSysPara auditingExceptionPara = null;//无需审批的人
		String  isAuditing = "";//是否需要批示
		if(sysParaList.size()> 0){
			for (int i = 0; i < sysParaList.size(); i++) {
				TeeSysPara temp = sysParaList.get(i);
				if(temp.getParaName().equals(TeeSysParaKeys.NOTIFY_AUDITING_ALL)){
					auditingPara = temp;
				}else if(temp.getParaName().equals(TeeSysParaKeys.NOTIFY_AUDITING_EXCEPTION)){
					auditingExceptionPara = temp;
				}else if(temp.getParaName().equals(TeeSysParaKeys.NOTIFY_AUDITING_SINGLE)){
					isAuditing = temp.getParaValue();
				}
			}
		}

		String auditingPersonIds = "";
		String auditingPersonNames = "";
		
		String auditingExceptionPersonIds = "";
		String auditingExceptionPersonNames = "";
		//获取公告审批人
		if(auditingPara != null){
			String pValue = auditingPara.getParaValue();
			pValue = TeeUtility.formatIdsQuote(pValue);
			if(!TeeUtility.isNullorEmpty(pValue)){
				String pTemp[] = personDao.getPersonNameAndUuidByUuids(pValue);
				auditingPersonIds = pTemp[0];
				auditingPersonNames = pTemp[1];
			}
		}
		//获取无需审批人
		if(auditingExceptionPara != null){
			String pValue = auditingExceptionPara.getParaValue();
			pValue = TeeUtility.formatIdsQuote(pValue);
			if(!TeeUtility.isNullorEmpty(pValue)){
				String pTemp[] = personDao.getPersonNameAndUuidByUuids(pValue);
				auditingExceptionPersonIds = pTemp[0];
				auditingExceptionPersonNames = pTemp[1];
			}
		}
		Map map = new HashMap<String, String>();
		map.put("rangeAllId", auditingPersonIds);
		map.put("rangeAllIdDesc", auditingPersonNames);
		map.put("rangeExceptionId", auditingExceptionPersonIds);
		map.put("rangeExceptionIdDesc", auditingExceptionPersonNames);
		map.put("isAuditing", isAuditing);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 判断公告通知是否需要审批
	 * @author zhp
	 * @createTime 2014-1-20
	 * @editTime 下午11:40:52
	 * @desc
	 */
	public boolean isNeedAduing(){
		boolean result = false;
		String sign = TeeSysParaKeys.NOTIFY_AUDITING_SINGLE;
		String paraValue = "0";
		TeeSysPara sysPara = (TeeSysPara)simpleDaoSupport.loadSingleObject("from TeeSysPara p where p.paraName = '"+TeeSysParaKeys.NOTIFY_AUDITING_SINGLE+"'", null);
		if(TeeUtility.isNullorEmpty(sysPara)){
			return true;
		}
		paraValue = sysPara.getParaValue();
		return paraValue.equals("0")?false:true;
	}
	
	/**
	 * 对于当前人发布的公告 是否需要审批 判断一下 当前人是否在 无需审批人里面!@#$%^
	 * @author zhp
	 * @createTime 2014-1-22
	 * @editTime 上午06:30:28
	 * @desc
	 */
	public boolean isNeedAduing2Person(TeePerson person){
		boolean isNeed = isNeedAduing();
		//如果在 无需审批人之列 排除
		if(isNeed){
			String needPersons = getNotifyAuditingExcPerson();
			String personUuid = String.valueOf(person.getUuid());
			if(TeeUtility.findIn(personUuid, needPersons))
				isNeed = false;
		 }
		//如果在 审批人只列 排除
		if(isNeed){
			String audPersons = getNotifyAuditingPerson();
			String personUuid = String.valueOf(person.getUuid());
			if(TeeUtility.findIn(personUuid, audPersons))
				isNeed = false;
		 }
		return isNeed;
	}
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-1-21
	 * @editTime 上午12:56:59
	 * @desc
	 */
	public TeePerson getAuditer(int auditer){
		return (TeePerson)simpleDaoSupport.loadSingleObject("from TeePerson p where p.uuid = ?", new Object[]{auditer});
	}
	
	/**
	 * 
	 * @author zhp
	 * @createTime 2014-3-3
	 * @editTime 上午01:23:34
	 * @desc
	 */
	public void updateNotifyState(int sid,TeePerson person){
		String hql = "update TeeNotifyInfo info set info.isRead = 1,info.readTime = ? where   exists (select 1 from info.notifys n  where n.sid = ? ) and info.toUser.uuid = ?";
		notifyDao.executeUpdate(hql, new Object[]{new Date(),sid,person.getUuid()});
	}
	
	/**
	 * 查看消息 ---  未读  添加查看公告人员
	 * @author syl
	 * @date 2014-3-13
	 * @param person
	 */
	public TeeJson setNotifyInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		int isLooked = TeeStringUtil.getInteger(request.getParameter("isLooked"),0);
		//System.out.println("id:"+sid+"   isLooked:"+isLooked);
		if(sid > 0){
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeNotify notify = notifyDao.getById(sid);
			if(isLooked != 1){
				boolean exists = notifyInfoDao.checkExistsInfo(person, notify);
				if(!exists){//不存在
					TeeNotifyInfo info = new TeeNotifyInfo();
					info.setIsRead(1);
					info.setReadTime(new Date());
					info.setToUser(person);
					info.setNotify(notify);
					notifyInfoDao.addNotify(info);
				}
			 }
			 TeeNotifyModel m = parseNotifyModelById(notify , false);
			 json.setRtState(true);
			 json.setRtData(m);
		}else{
			json.setRtState(false);
		    json.setRtMsg("无公告");
		}
		return json;
	}
	
	/**
	 * 更改 公告状态为 结束 或者生效
	 * @author zhp
	 * @createTime 2014-3-3
	 * @editTime 上午01:27:03
	 * @desc
	 */
	public void updateNotifyPublish(int sid , String publish){
		List list = new ArrayList();
		list.add(publish);
		String hql = "update TeeNotify n set n.publish = ? ";
		if(publish.equals("1")){//生效
			hql = hql + ",n.sendTime = ?";
			list.add(new Date());
		}
		hql = hql + " where n.sid = "+sid;
		notifyDao.executeUpdateByList(hql, list);
	}
	
	
	
	
	/**
	 * 根据Id  删除u
	 * @author syl
	 * @date 2014-3-13
	 * @param request
	 */
	public TeeJson delNotifyById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		if(sid > 0){
			TeeNotify notify = notifyDao.getById(sid);
			if(notify != null){
				notifyInfoDao.deleteByNotify(notify.getSid());
				notifyDao.deleteByObj(notify);
				//删除cms网站对应的文档信息
				if (notify.getCmsDocumentId() > 0) {
					documentService.deleteDocumentByDocId(notify.getCmsDocumentId());
				}
				json.setRtState(true);
			}else{
				json.setRtState(false);
				json.setRtMsg("没有找到相关公告！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("获取公告出错！");
		}
		return json;
	}
	
	
	/**
	 * 
	 * @author syl
	 * @date 2014-3-15
	 * @param request
	 * @return
	 */
	public TeeJson audNotify(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeNotify notify = notifyDao.getById(sid);
		Date sendTime = TeeStringUtil.getDate(request.getParameter("sendTime"),"yyyy-MM-dd HH:mm:ss");
		Date beginDate = TeeStringUtil.getDate(request.getParameter("beginDate"),"yyyy-MM-dd");
		Date endDate = TeeStringUtil.getDate(request.getParameter("endDate"),"yyyy-MM-dd");
		String publish = TeeStringUtil.getString(request.getParameter("publish"),"2");
		String reason = TeeStringUtil.getString(request.getParameter("reason"),"");
		
		String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"),"0");
		String top=TeeStringUtil.getString(request.getParameter("top"));
		if(notify != null){
			notify.setSendTime(sendTime);
			notify.setBeginDate(beginDate);
			notify.setEndDate(endDate);
			notify.setPublish(publish);
			notify.setTop(top);
			if(!"1".equals(publish)){//不通过
				notify.setReason(reason);
			}else{//通过
				notify.setSendTime(new Date());
				if ("1".equals(notify.getSynchronizeCMS())) {
					//同步cms网站
					DocumentInfo documentInfo = addOrUpdAudDocument(notify,loginPerson);
					if (documentInfo != null) {
						notify.setIsAddFlag("1");
						notify.setCmsDocumentId(documentInfo.getSid());
					}
				}
			}
			notifyDao.updateNotify(notify);
			json.setRtState(true);
			json.setRtMsg("审批公告成功!");
			
			
			//发送短信
			
			if(smsRemind.equals("1") && publish.equals("1")){
				//System.out.println("公告审批通过");
				//审批通过
				List<TeePerson> pList = notify.getPostUser();
				List<TeeDepartment> dList = notify.getPostDept();
				List<TeeUserRole> rList = notify.getPostUserRole();
				String uuids = "";//人员Ids
				String deptIds = "";//部门Ids
				String roleIds = "";//角色Ids
				if(pList != null && pList.size() > 0){
					for (int i = 0; i < pList.size(); i++) {
						uuids = uuids + pList.get(i).getUuid() + ",";
					}
				}
				String Ids="";
				if(dList != null && dList.size() > 0){
					for (int i = 0; i < dList.size(); i++) {
						deptIds = deptIds + dList.get(i).getUuid() + ",";
					}
					
					String[]dIds=deptIds.split(",");	
					for (String str : dIds) {
						int deptId=Integer.parseInt(str);
						TeeDepartment d=deptDao.get(deptId);
						String fullId=d.getDeptFullId();
						List<TeeDepartment> departmentList=deptDao.getAllChildDept(fullId);
						//System.out.println("子部门的个数"+departmentList.size());
						String deptIdss="";
						for (TeeDepartment dept : departmentList) {
							deptIdss+=dept.getUuid()+",";
						}
						Ids+=deptIdss;		
					}
					if(Ids.endsWith(",")){
						Ids=Ids.substring(0,Ids.lastIndexOf(","));
					}	
					
					System.out.println("ids:"+Ids);
				}
				
				if(rList != null && rList.size() > 0){
					for (int i = 0; i < rList.size(); i++) {
						roleIds = roleIds + rList.get(i).getUuid() + ",";
					}
				}
				List<TeePerson> personList = personManagerI.getPersonByUuidsOrDeptIdsOrRoleIds(loginPerson, uuids, Ids, roleIds);
				
				System.out.println("ren:"+personList.size());
				Map requestData = new HashMap();
				requestData.put("content", "请查看公告通知：" + notify.getSubject());
				requestData.put("userList",personList );
				//requestData.put("sendTime", );
				requestData.put("moduleNo", "021");
				requestData.put("remindUrl", "/system/core/base/notify/person/readNotify.jsp?id="+notify.getSid()+"&isLooked=0");
				smsManager.sendSms(requestData, loginPerson);
			}
			
			Map requestData = new HashMap();
			String desc = "已通过";
			if(publish.equals("3")){
				desc = "未通过";
			}
			requestData.put("content", "你的公告申请"+desc );
			requestData.put("userListIds",notify.getFromPerson().getUuid() + "" );
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "021");
			requestData.put("remindUrl", "/system/core/base/notify/manage/index.jsp");
			smsManager.sendSms(requestData, loginPerson);
			

		}else{
			json.setRtState(false);
			json.setRtMsg("改公告已被删除!");
		}
		return json;
	}
	
	
	
	
	public List<TeeNotifyModel> getNoReadNotifyList(Map paraMap,TeePerson person,int isRed) {
		String typeId = TeeStringUtil.getString(paraMap.get("typeId"));
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		
		List<TeeNotify> notifys = notifyDao.getNoReadNotifyList(isRed,person,typeId);
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setFromPersonName(n.getFromPerson().getUserName());
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		return notifysmodel;
	}
	
	
	
/**
 * 阅读公告
 * @param request
 * @return
 */
	public TeeJson readNotify(int sid,int isLooked,TeePerson person){
		TeeJson json = new TeeJson();
		if(sid > 0){
			TeeNotify notify = notifyDao.getById(sid);
			if(isLooked != 1){
				boolean exists = notifyInfoDao.checkExistsInfo(person, notify);
				if(!exists){//不存在
					TeeNotifyInfo info = new TeeNotifyInfo();
					info.setIsRead(1);
					info.setReadTime(new Date());
					info.setToUser(person);
					info.setNotify(notify);
					notifyInfoDao.addNotify(info);
				}
			 }
			 TeeNotifyModel m = parseNotifyModelById(notify , false);
			 json.setRtState(true);
			 json.setRtData(m);
		}else{
			json.setRtState(false);
		    json.setRtMsg("无公告");
		}
		return json;
	}
	
	public TeeNotifyDao getNotifyDao() {
		return notifyDao;
	}

	public void setNotifyDao(TeeNotifyDao notifyDao) {
		this.notifyDao = notifyDao;
	}

	/**
	 * 批量删除公告
	 * @param request
	 * @return
	 */
	public TeeJson delNotifyBatch(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String ids=request.getParameter("ids");
		String []idArray=ids.split(",");
		int sid=0;
		TeeNotify notify =null;
		for (String idStr : idArray) {
			sid=TeeStringUtil.getInteger(idStr, 0);
			if (sid > 0){
				notify = notifyDao.getById(sid);
				if (notify != null) {
					notifyInfoDao.deleteByNotify(notify.getSid());
					notifyDao.deleteByObj(notify);
					//删除cms网站对应的文档信息
					if (notify.getCmsDocumentId() > 0) {
						documentService.deleteDocumentByDocId(notify.getCmsDocumentId());
					}
					json.setRtState(true);
				} else {
					json.setRtState(false);
					json.setRtMsg("没有找到相关公告！");
				}
			} else {
				json.setRtState(false);
				json.setRtMsg("获取公告出错！");
			}
		}
		return json;
	}

	/**
	 * 删除cms网站附件
	 * @param request
	 * @return
	 */
	public TeeJson delCmsAttachment(HttpServletRequest request) {
		TeeJson teeJson = new TeeJson();
		String attachmentId = request.getParameter("attachmentId");
		String notifyId = request.getParameter("id");
		TeeNotify notify = notifyDao.getById(Integer.valueOf(notifyId));
		TeeAttachment attachment = attachmentService.getById(Integer.valueOf(attachmentId));
		
		if (notify != null) {
			List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.cms, notify.getCmsDocumentId()+"");
			if (attaches != null && attaches.size() > 0) {
				for (TeeAttachment teeAttachment : attaches) {
					String newMd5 = attachment.getMd5();
					String oldMd5 = teeAttachment.getMd5();
					if (newMd5.equals(oldMd5)) {
						attachmentService.deleteAttach(teeAttachment);
						attachmentService.deleteAttach(attachment);
					}
				}
			}
		}else{
			teeJson.setRtState(false);
			teeJson.setRtMsg("获取公告出错！");
		}
		
		teeJson.setRtState(true);
		return teeJson;
	}

	/**根据类型获取公告信息
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getNotifyByTypeId(HttpServletRequest request,
			TeeDataGridModel dm,TeePerson loginUser) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"));
		List<TeeNotifyModel> notifysmodel = new ArrayList<TeeNotifyModel>();
		String hql = "from TeeNotify n where 1=1 ";
		if(!"".equals(typeId)){
			hql+=" and n.typeId='"+typeId+"'";
		}
		//将主角色和辅助角色uuid拼接作为查询条件
		List<TeeUserRole> userRoleOther = loginUser.getUserRoleOther();
		String userRoleStr = "";
		if (userRoleOther != null && userRoleOther.size() > 0) {
			for (TeeUserRole role : userRoleOther) {
				userRoleStr = userRoleStr + role.getUuid() + ",";
			}
		}
		userRoleStr += loginUser.getUserRole().getUuid();
		//将部门及辅助部门uuid拼接
		List<TeeDepartment> deptOther = loginUser.getDeptIdOther();
		String deptStr = "";
		if (deptOther != null && deptOther.size() > 0) {
			for (TeeDepartment dept : deptOther) {
				deptStr = deptStr + dept.getUuid() + ",";
			}
		}
		deptStr += loginUser.getDept().getUuid();
		
		hql += " and n.publish = '"+1+"'";
		hql += " and (n.allPriv = 1 "
				+ " or (n.allPriv = 0 and (exists (select 1 from n.postDept d where d.uuid in ("+deptStr+")))"
				+ " or (exists (select 1 from n.postUser u where u.uuid in ("+loginUser.getUuid()+")))"
				+ " or (exists (select 1 from n.postUserRole r where r.uuid in("+userRoleStr+")))))";
		Long total = notifyDao.count("select count(sid) "+hql, null);
		j.setTotal(total);//设置总记录数
		hql += " order by n.top desc,n.createDate desc" ;
		
		List<TeeNotify> notifys = notifyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(),null);// 查询
		if (notifys != null && notifys.size() > 0) {
			for (TeeNotify n : notifys) {
				TeeNotifyModel m = parseNotifyModelById(n, true);
				m.setContent("");
				m.setShortContent("");
				notifysmodel.add(m);
			}
		}
		j.setRows(notifysmodel);// 设置返回的行
		
		return j;
	}
	
}
