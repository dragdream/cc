package com.tianee.thirdparty.itf.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.notify.bean.TeeNotify;
import com.tianee.oa.core.base.notify.dao.TeeNotifyDao;
import com.tianee.oa.core.base.notify.dao.TeeNotifyInfoDao;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.dao.TeeSysParaDao;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.core.phoneSms.service.TeeSmsSendPhoneService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cms.bean.DocumentInfo;
import com.tianee.oa.subsys.cms.model.DocumentInfoModel;
import com.tianee.oa.subsys.cms.service.CmsDocumentService;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeJiGuanDangJianService extends TeeBaseService{

	@Autowired
	private TeeSimpleDaoSupport teeSimpleDaoSupport;
	
	@Autowired
	private TeeSmsSender smsSender;
	
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
	 * 调用内部提醒
	 * */
	public TeeJson sendSmsSender(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String userId = request.getParameter("userId");
		String content = request.getParameter("content");
		List<TeePerson> find = teeSimpleDaoSupport.find("from TeePerson  where userId = ? and deleteStatus <> '1' ", new Object[]{userId});
        if(find!=null && find.size()>0){
        	TeePerson person=find.get(0);
    		Map requestData = new HashMap();
			requestData.put("moduleNo", "093");
			requestData.put("userListIds", person.getUuid());
			requestData.put("content", content);
			requestData.put("remindUrl", "");
			smsSender.sendSms(requestData, null);
			json.setRtMsg("发送成功");
			json.setRtState(true);
        }else{
        	json.setRtMsg("发送失败");
			json.setRtState(false);
        }
		return json;
	}

	/**
	 * 发布通知公告
	 * */
	public TeeJson sendAnnouncement(HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		String userId = request.getParameter("userId");
		TeePerson loginPerson = (TeePerson)simpleDaoSupport.unique("from TeePerson where userId =? and deleteStatus=0", new Object[]{userId});
//		request.getSession().setAttribute(TeeConst.LOGIN_USER, loginPerson);
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		TeeRequestInfo requestInfo = TeeRequestInfoContext.getRequestInfo();
		Map session = new HashMap();
		session.put(TeeConst.LOGIN_USER, loginPerson);
		requestInfo.setSession(session);
		requestInfo.setUserId(loginPerson.getUserId());
		requestInfo.setUserSid(loginPerson.getUuid());
		requestInfo.setUserName(loginPerson.getUserName());
		//TeeRequestInfoContext.setRequestInfo(requestInfo);
		/**
		 * 发布状态
		 */
		int publish = 1;
		TeePerson auditerPerson  = null;
		SimpleDateFormat  format=new SimpleDateFormat("yyyy-MM-dd");
		String beginDateStr=format.format(new Date());
		Date beginDate=null;
		try {
			beginDate = format.parse(beginDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endDate = null;
		List<TeeAttachment> attachments = new ArrayList<TeeAttachment>();
		TeeNotify notify = new TeeNotify();
		int allPriv = 0;
		int sid = 0;
		/*获取发布范围人员*/
//		List<TeePerson> listDept=new ArrayList<TeePerson>();
//		if(!TeeUtility.isNullorEmpty(userId)){//申发布权限- 人员
//			String[] split = userId.split(",");
//			for(int i=0;i<split.length;i++){
//				TeePerson person = (TeePerson)simpleDaoSupport.unique("from TeePerson where userId =? and deleteStatus=0", new Object[]{split[i]});
//				listDept.add(person);
//			}
//			notify.setPostUser(listDept);
//		}
		if(sid > 0){
			TeeNotify oldNotify = notifyDao.getById(sid);
			if(oldNotify != null){
				//同步cms网站
				addOrUpdDocument(request,sid,attachments,loginPerson);
				oldNotify.setSubject(subject);
				String oldPublish = TeeStringUtil.getString(oldNotify.getPublish(), "0");
				if(oldPublish.equals("0")){
					oldNotify.setPublish(publish + "");
				}
				oldNotify.setPublish(String.valueOf(publish));
				oldNotify.setContentStyle("0");
				oldNotify.setSynchronizeCMS("1");
				oldNotify.setDocContentSid(Integer.valueOf(0));
				oldNotify.setBeginDate(beginDate);
				oldNotify.setEndDate(endDate);
				oldNotify.setFromPerson(loginPerson);
				oldNotify.setAllPriv(1);
				//添加新附件
				for(TeeAttachment attach:attachments){
					attach.setModelId(String.valueOf(oldNotify.getSid()));
					attachmentDao.update(attach);
				}
				oldNotify.setContent(content);
				oldNotify.setPostDept(notify.getPostDept());
				oldNotify.setPostUser(notify.getPostUser());
				oldNotify.setPostUserRole(notify.getPostUserRole());
				oldNotify.setTop("0");
				oldNotify.setTypeId("01");
				oldNotify.setAuditer(auditerPerson);
				oldNotify.setSendTime(new Date());
				oldNotify.setCreateDate(oldNotify.getCreateDate());
				
				int attaSid = Integer.valueOf(0);
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
			//同步cms网站
			DocumentInfo documentInfo = addOrUpdDocument(request,sid,attachments,loginPerson);
			notify.setIsAddFlag("1");
			notify.setCmsDocumentId(documentInfo.getSid());
			notify.setSubject(subject);
			notify.setPublish(String.valueOf(publish));
			notify.setContentStyle("0");
			notify.setSynchronizeCMS("1");
			notify.setDocContentSid(Integer.valueOf(0));
			notify.setAuditer(auditerPerson);
			notify.setBeginDate(beginDate);
			notify.setEndDate(endDate);
			notify.setFromPerson(loginPerson);
			notify.setAllPriv(1);
			
			if(publish != 0){
				notify.setSendTime(new Date()); 
			}
			notify.setContent(content);
			notify.setTop("0");
			notify.setTypeId("01");
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
			
			json.setRtMsg("公告通知发布成功!");
		}
		List<TeePerson> listDept = personDao.getAllUserNoDelete();
		Map requestData = new HashMap();
		requestData.put("content", "请查看公告："+subject);
		requestData.put("userList",listDept);
		requestData.put("moduleNo", "021");
		requestData.put("remindUrl", "/system/core/base/notify/person/readNotify.jsp?id="+notify.getSid()+"&isLooked=0&sid="+notify.getSid());
		smsSender.sendSms(requestData, null);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 同步到cms网站对应栏目下
	 * @param request
	 */
	public DocumentInfo addOrUpdDocument(HttpServletRequest request,int sid,List<TeeAttachment> attachments,TeePerson loginPerson){
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String typeId = "01";
		String attachmentSidStr = TeeStringUtil.getString(request.getParameter("attachmentSidStr"),"0");
		DocumentInfoModel documentInfoModel = (DocumentInfoModel) TeeServletUtility.request2Object(request, DocumentInfoModel.class);
		TeeNotify notify = notifyDao.getById(sid);
		
		//设置文化执法门户站点id
		Map channelMap = simpleDaoSupport.getMap("select info.sid as sid, info.siteId as siteId from ChannelInfo info where typeId = '"+typeId+"'", null);
		documentInfoModel.setSiteId((int)channelMap.get("siteId"));
		//设置栏目id
		documentInfoModel.setChnlId((int)channelMap.get("sid"));
		documentInfoModel.setDocTitle(subject);
		documentInfoModel.setHtmlContent(request.getParameter("content"));
		String attachmentId = TeeStringUtil.getString(0);
		documentInfoModel.setDocAttachmentId(Integer.valueOf(attachmentId));
		if(sid > 0 && "1".equals(notify.getIsAddFlag())){//修改
			
			documentInfoModel.setDocAttachmentId(notify.getDocContentSid());
			documentService.updateDocument(documentInfoModel, loginPerson);
			
			for (TeeAttachment teeAttachment : attachments) {
				TeeAttachment attachment = attachmentService.clone(teeAttachment, TeeAttachmentModelKeys.cms, loginPerson);
				attachment.setModelId(notify.getCmsDocumentId()+"");
				attachmentDao.addAttac(attachment);
			}
			return null;
		}else{
			//设置电子文档内容的附件id
			documentInfoModel.setDocAttachmentId(TeeStringUtil.getInteger(attachmentSidStr, 0));
			DocumentInfo doc =documentService.addDocument(documentInfoModel,loginPerson);
			//添加附件
			for (TeeAttachment attach : attachments) {
				TeeAttachment attachment = attachmentService.clone(attach, TeeAttachmentModelKeys.cms, loginPerson);
				attachment.setModelId(doc.getSid()+"");
				attachmentDao.addAttac(attachment);
			}
			return doc;
		}
	}
}
