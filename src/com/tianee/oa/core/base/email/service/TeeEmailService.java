package com.tianee.oa.core.base.email.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.email.WebMailSender;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeWebMail;
import com.tianee.oa.core.base.email.bean.TeeWebMailSendTask;
import com.tianee.oa.core.base.email.dao.TeeMailBodyDao;
import com.tianee.oa.core.base.email.dao.TeeMailBoxDao;
import com.tianee.oa.core.base.email.dao.TeeMailColorDao;
import com.tianee.oa.core.base.email.dao.TeeMailDao;
import com.tianee.oa.core.base.email.dao.TeeWebMailDao;
import com.tianee.oa.core.base.email.model.TeeEmailBodyModel;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.core.base.email.model.TeeMailBoxModel;
import com.tianee.oa.core.base.email.model.TeeWebEmailModel;
import com.tianee.oa.core.base.email.model.TeeWebMailModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeEmailService extends TeeBaseService {
	@Autowired
	private TeeMailDao mailDao;
	@Autowired
	private TeeMailBodyDao mailBodyDao;
	@Autowired
	private TeeMailBoxDao mailBoxDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeMailColorDao colorDao;
	@Autowired
	private TeeWebMailDao webDao;
	@Autowired
	private TeeSmsSender smsSender;
	@Autowired
	private TeeAttachmentDao attachmentDao;
	@Autowired
    private  TeeAttachmentService  attachmentService ;

	@Autowired
	TeeMailService mailService;
	@Autowired
	private TeeSmsManager smsManager;

	@Transactional(readOnly = false)
	public TeeJson addOrUpdateMail(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		String message = "发送成功！";
		Map emailModel  = new HashMap();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			int sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
			String ifBody = request.getParameter("ifBody");
			
			String userListIds = request.getParameter("userListIds");
			String copyUserListIds = request.getParameter("copyUserListIds");
			String secretUserListIds = request.getParameter("secretUserListIds");
			String externalInput = request.getParameter("externalInput");
//			System.out.println(externalInput);
			int pubType=TeeStringUtil.getInteger(request.getParameter("pubType"), 0);
			String content = request.getParameter("content");
			String subject = request.getParameter("subject");
			String type = request.getParameter("type");
			String webmailCount = request.getParameter("webmailCount");
			String webmailHtml = "";
			String attachmentSidStr = request.getParameter("attachmentSidStr");

			String smsRemind = TeeStringUtil.getString(request.getParameter("smsRemind"), "0");
			String receipt = TeeStringUtil.getString(request.getParameter("receipt"), "0");
			
			String dbAttachSid = TeeStringUtil.getString(request.getParameter("dbAttachSid"), "0");
			String cloneAttachFlag = TeeStringUtil.getString(request.getParameter("cloneAttachFlag"), "0");//转发、再次编辑克隆附件，0-否；1-克隆
			String emailLevel = TeeStringUtil.getString(request.getParameter("emailLevel"), "0");
			

			TeeWebMailModel model = new TeeWebMailModel();
			List<TeeWebMailModel> webList = mailService.getWebMailDefault(person);
			if (webList.size() > 0) {
				model = webList.get(0);
			}

			if(sid > 0) {// 编辑、转发、回复获取附件等数据
				
			}
			
			TeeMailBody mailBody = new TeeMailBody();
			if (!TeeUtility.isNullorEmpty(webmailCount)) {
				mailBody.setWebmailCount(Integer.parseInt(webmailCount));
			}

            mailBody.setPubType(pubType);
			mailBody.setWebmailHtml(webmailHtml);
			mailBody.setFromuser(person);
			mailBody.setNameOrder(person.getUserName());
			mailBody.setContent(content);
			mailBody.setCompressContent("");
			mailBody.setFromWebMail("");
			mailBody.setImportant("");
			mailBody.setEmailLevel(emailLevel);//级别
			if (type.equals("0")) {// 草稿
				mailBody.setSendFlag("0");
				message = "保存成功！";
			} else {// 发送
				mailBody.setSendFlag("1");
			}
			mailBody.setSendTime(new Date());
			mailBody.setSmsRemind(smsRemind);
			mailBody.setSubject(subject);
			mailBody.setToWebmail(externalInput);
			mailBody.setSid(sid);
			mailBodyDao.saveOrUpdate(mailBody);
			
			List<TeeAttachment> attachAll = new ArrayList<TeeAttachment>();
			List<TeeAttachment> dbAttachSidList = getAttachmentsByIds(dbAttachSid);//数据库原有附件
			List<TeeAttachment> attachments = getAttachmentsByIds(attachmentSidStr);//新上传附件
			
			//处理邮件附件
			if("1".equals(cloneAttachFlag)){//克隆
				if(dbAttachSidList!= null && dbAttachSidList.size()>0){
					for(TeeAttachment attachment:dbAttachSidList){
						//复制附件
						TeeAttachment newAttachment = attachmentService.clone(attachment, attachment.getModel(), person);
						attachAll.add(newAttachment);
					}
				}
			}else{
				attachAll.addAll(dbAttachSidList);
			}
			if(attachments!= null && attachments.size()>0){
				attachAll.addAll(attachments);
			}
			if(attachAll!= null && attachAll.size()>0){
				for(TeeAttachment attach:attachAll){
					attach.setModelId(String.valueOf(mailBody.getSid()));
					simpleDaoSupport.update(attach);
				}
			}
			
			emailModel.put("sid", mailBody.getSid());
			mailDao.deleteMailByBody(mailBody);
			//收件人名称
			StringBuffer recipientBuffer = new StringBuffer();
			//抄送人名称
			StringBuffer carbonCopyBuffer = new StringBuffer();
			//密送人名称
			StringBuffer blindCarbonCopyBuffer = new StringBuffer();
			
			List<Map<String, Object>> sendEmailUserList = new ArrayList<Map<String,Object>>();
			
			List<String> userIdList = new ArrayList<String>();
			if(pubType==0){//指定人员
				if (!TeeUtility.isNullorEmpty(userListIds)) {// 过滤重复 收件人
					if (userListIds.endsWith(",")) {
						userListIds = userListIds.substring(0, userListIds.length() - 1);
					}
					String[] userListId = userListIds.split(",");
					for (String userId : userListId) {
						if(TeeUtility.isNullorEmpty(userId)){
							continue;
						}
						if(recipientBuffer.length()>0){
							recipientBuffer.append(",");
						}
						recipientBuffer.append(userId);
						if(userIdList.contains(userId)){
							continue;
						}
						userIdList.add(userId);
						TeePerson toUser = personDao.load(Integer.parseInt(userId));
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("receiveType", 0);//收件人
						map.put("toUserObj", toUser);//收件人
						sendEmailUserList.add(map);
					}
				}
			}else{//全部人员
				List<TeePerson> allPersonList=personDao.getAllUserNoDelete();
				for (TeePerson teePerson : allPersonList) {
					if(TeeUtility.isNullorEmpty(teePerson)){
						continue;
					}
					if(recipientBuffer.length()>0){
						recipientBuffer.append(",");
					}
					recipientBuffer.append(teePerson.getUuid());
					if(userIdList.contains(teePerson.getUuid())){
						continue;
					}
					userIdList.add(teePerson.getUuid()+"");
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("receiveType", 0);//收件人
					map.put("toUserObj", teePerson);//收件人
					sendEmailUserList.add(map);
				}
			}
			
			if (!TeeUtility.isNullorEmpty(copyUserListIds)) {// 过滤重复 抄送人
				if (copyUserListIds.endsWith(",")) {
					copyUserListIds = copyUserListIds.substring(0, copyUserListIds.length() - 1);
				}
				String[] userListId = copyUserListIds.split(",");
				for (String userId : userListId) {
					if(TeeUtility.isNullorEmpty(userId)){
						continue;
					}
					if(carbonCopyBuffer.length()>0){
						carbonCopyBuffer.append(",");
					}
					carbonCopyBuffer.append(userId);
					if(userIdList.contains(userId)){
						continue;
					}
					userIdList.add(userId);
					TeePerson toUser = personDao.load(Integer.parseInt(userId));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("receiveType", 1);//收件人
					map.put("toUserObj", toUser);//抄送人
					sendEmailUserList.add(map);
				}
			}
			if (!TeeUtility.isNullorEmpty(secretUserListIds)) {// 过滤重复 密送人
				if (secretUserListIds.endsWith(",")) {
					secretUserListIds = secretUserListIds.substring(0, secretUserListIds.length() - 1);
				}
				String[] userListId = secretUserListIds.split(",");
				for (String userId : userListId) {
					if(TeeUtility.isNullorEmpty(userId)){
						continue;
					}
					
					if(blindCarbonCopyBuffer.length()>0){
						blindCarbonCopyBuffer.append(",");
					}
					blindCarbonCopyBuffer.append(userId);
					if(userIdList.contains(userId)){
						continue;
					}
					userIdList.add(userId);
					TeePerson toUser = personDao.load(Integer.parseInt(userId));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("receiveType", 2);//收件人
					map.put("toUserObj", toUser);//密送人
					sendEmailUserList.add(map);
				}
			}
			mailBody.setRecipient(recipientBuffer.toString());
			mailBody.setCarbonCopy(carbonCopyBuffer.toString());
			mailBody.setBlindCarbonCopy(blindCarbonCopyBuffer.toString());
			mailBodyDao.update(mailBody);
			
			for(Map<String,Object> map:sendEmailUserList){//遍历人员并发送
				int receiveType = (Integer)map.get("receiveType");
				TeePerson toUser = (TeePerson)map.get("toUserObj");
				TeeMail mail = new TeeMail();
				mail.setToUser(toUser);
				mail.setReceiveType(receiveType);
				mail.setReceipt(Integer.parseInt(receipt));
				mail.setReadFlag(0);
				mail.setMailBox(null);
				mail.setMailBody(mailBody);
				mail.setDeleteFlag(0);
				mailDao.saveOrUpdate(mail);
				
				if ("1".equals(smsRemind) && "1".equals(type)) {//消息提醒&&发送
					Map<String, String> requestData = new HashMap<String, String>();
					requestData.put("content", "来自 " + person.getUserName() + " 的邮件！主题：" + subject);
					requestData.put("userListIds", String.valueOf(toUser.getUuid()));
					requestData.put("moduleNo", "019");
//					requestData.put("remindUrl", "/system/core/email/readSmsEmailBody.jsp?sid=" + mail.getSid()) ;
					requestData.put("remindUrl", "/system/core/email/readEmailByMailId.jsp?sid="+mail.getSid()+"&closeOptFlag=1") ;
					//手机事务提醒
					requestData.put("remindUrl1", "/system/mobile/phone/email/emailInfo.jsp?sid="+mail.getSid()+"&closeOptFlag=1") ;
					smsManager.sendSms(requestData, person);
				}
			}
			
			if (!TeeUtility.isNullorEmpty(externalInput) && type.equals("1")) {// 外部邮件&&发送，存入发送列表中
				WebMailSender sender = new WebMailSender();
				externalInput = externalInput.substring(0, externalInput.length());
				String[] webMails = externalInput.split(";");
				for (String webMail : webMails) {
					if (mailService.checkEmail(webMail) && model != null && !TeeUtility.isNullorEmpty(model.getSmtpServer())) {
						// System.out.println("执行发送外部邮件:"+webMail);
//						message = sender.sendWebMail(model.getSmtpServer(), model.getSmtpPort(), model.getLoginType(), subject, model.getEmailUser(), model.getEmailPass(), webMail, subject, content,
//								attachments);
						TeeWebMailSendTask mailSendTask = new TeeWebMailSendTask();
						mailSendTask.setFrom(model.getSid());
						mailSendTask.setMailBodyId(mailBody.getSid());
						mailSendTask.setStatus(0);//未发送
						mailSendTask.setTo(webMail);
						simpleDaoSupport.save(mailSendTask);
					}
				}
			}
			
			json.setRtData(emailModel);
			json.setRtMsg(message);
			json.setRtState(true);
			return json;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据附件sid获取附件
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param attachIds
	 * @return
	 */
	public List<TeeAttachment> getAttachmentsByIds(String attachIds) {
		return attachmentDao.getAttachmentsByIds(attachIds);
	}

	/**
	 * 邮件列表（收件箱）
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getEmailListByTypeService(TeeDataGridModel dm, HttpServletRequest request, TeeEmailModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String hql = " from TeeMail where toUser.uuid = ? and mailBody.sendFlag = 1 and deleteFlag=0  and (mailBox is null or mailBox.sid = 0)";
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailBodyDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by mailBody.emailLevel desc, mailBody.sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMail> list = mailDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 邮件列表（已删除邮件箱）
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getDelEmailListService(TeeDataGridModel dm, HttpServletRequest request, TeeEmailModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);

		String hql = " from TeeMail where toUser.uuid = ? and mailBody.sendFlag = 1 and deleteFlag =1 and (mailBox is null or mailBox.sid = 0)";

		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailBodyDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by mailBody.emailLevel desc, mailBody.sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMail> list = mailDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 邮件列表（自定义邮箱）
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getEmailBoxListByIdService(TeeDataGridModel dm, HttpServletRequest request) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String mailBoxSid = TeeStringUtil.getString(requestDatas.get("mailBoxSid"), "0");

		String hql = " from TeeMail where toUser.uuid = ? and mailBox.sid=? and deleteFlag in (0)";

		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		param.add(Integer.parseInt(mailBoxSid));

		// 设置总记录数
		j.setTotal(mailBodyDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by mailBody.emailLevel desc, mailBody.sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMail> list = mailDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 封装对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public TeeEmailModel parseMailModel(TeeMail obj) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeEmailModel model = new TeeEmailModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		TeeMailBody mailBody = obj.getMailBody();
		if (mailBody != null) {
			//发布范围
			model.setPubType(mailBody.getPubType());
			//邮件级别
			String levelDesc = "";
			if(!TeeUtility.isNullorEmpty(mailBody.getEmailLevel())){
				levelDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("EMAIL_JJCD", mailBody.getEmailLevel());
			}
			model.setEmailLevelDesc(levelDesc);
			if (mailBody.getFromuser() != null) {
				model.setFromUserId(mailBody.getFromuser().getUuid());
				model.setFromUserName(mailBody.getFromuser().getUserName());
			} else {
				model.setFromUserName("");
			}
			
			if (!TeeUtility.isNullorEmpty(mailBody.getSendTime())) {
				model.setSendTimeStr(TeeUtility.getDateStrByFormat(mailBody.getSendTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
			} else {
				model.setSendTimeStr("");
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSubject())) {
				model.setSubject(mailBody.getSubject());
			} else {
				model.setSubject("");
			}
			model.setMailBodySid(mailBody.getSid());

			List<TeeMail> userMailList = mailDao.getMailByBody(mailBody, 0);
			List<TeeMail> copyUserMailList = mailDao.getMailByBody(mailBody, 1);
			List<TeeMail> secretUserMailList = mailDao.getMailByBody(mailBody, 2);

			List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> copyUserList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> secretUserList = new ArrayList<Map<String, String>>();

			StringBuffer buffer = new StringBuffer();
			StringBuffer userMailBuffer = new StringBuffer();

			//key（人员id）：value(阅读标志)
			Map<Integer,String> readFlagMap = new HashMap<Integer, String>();
			//key（人员id）：value(人员名称)
			Map<Integer,String> userMap = new HashMap<Integer, String>();
			
			for (TeeMail mail : userMailList) {
				/*
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(mail.getToUser().getUuid()));
				map.put("userName", mail.getToUser().getUserName());
				map.put("readFlag", String.valueOf(mail.getReadFlag()));
				
				userList.add(map);
				*/
				if (buffer.length() > 0) {
					buffer.append(",");
				}
				if(mail.getToUser()!=null){
					buffer.append(mail.getToUser().getUserName());
					readFlagMap.put(mail.getToUser().getUuid(), String.valueOf(mail.getReadFlag()));
					userMap.put(mail.getToUser().getUuid(), mail.getToUser().getUserName());
				}
			}
			userMailBuffer.append(buffer.toString());
			for (TeeMail mail : copyUserMailList) {
				/*
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(mail.getToUser().getUuid()));
				map.put("userName", mail.getToUser().getUserName());
				map.put("readFlag", String.valueOf(mail.getReadFlag()));
				copyUserList.add(map);
				*/
				if (buffer.length() > 0) {
					buffer.append(",");
				}
				buffer.append(mail.getToUser().getUserName());
				readFlagMap.put(mail.getToUser().getUuid(), String.valueOf(mail.getReadFlag()));
				userMap.put(mail.getToUser().getUuid(), mail.getToUser().getUserName());
			}
			for (TeeMail mail : secretUserMailList) {
				/*
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(mail.getToUser().getUuid()));
				map.put("userName", mail.getToUser().getUserName());
				map.put("readFlag", String.valueOf(mail.getReadFlag()));
				secretUserList.add(map);
				*/
				if (buffer.length() > 0) {
					buffer.append(",");
				}
				buffer.append(mail.getToUser().getUserName());
				readFlagMap.put(mail.getToUser().getUuid(), String.valueOf(mail.getReadFlag()));
				userMap.put(mail.getToUser().getUuid(), mail.getToUser().getUserName());
			}
			if (buffer.length() <= 0) {
				buffer.append(obj.getToUser().getUserName());
			}
			
			//String recipient = TeeUtility.null2Empty(mailBody.getRecipient());
			String carbonCopy = TeeUtility.null2Empty(mailBody.getCarbonCopy());
			String blindCarbonCopy = TeeUtility.null2Empty(mailBody.getBlindCarbonCopy());
			
			
			//获取收件人
			String recipient="";
			String hql="from TeeMail mail  where mail.mailBody.sid="+obj.getMailBody().getSid()+" and receiveType=0 ";
			List<TeeMail> mailList=simpleDaoSupport.executeQuery(hql, null);
			for (TeeMail teeMail : mailList) {
				recipient+=teeMail.getToUser().getUuid()+",";
			}
			if(recipient.endsWith(",")){
				recipient=recipient.substring(0, recipient.length()-1);	
			}
			String [] recipientArray = recipient.split(",");
			for(String userId:recipientArray){
				if(TeeUtility.isNullorEmpty(userId)){
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", userId);
				map.put("userName", userMap.get(Integer.parseInt(userId)));
				map.put("readFlag", readFlagMap.get(Integer.parseInt(userId)));
				userList.add(map);
				
			}
			
			String [] carbonCopyArray = carbonCopy.split(",");
			for(String userId:carbonCopyArray){
				if(TeeUtility.isNullorEmpty(userId)){
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", userId);
				map.put("userName", userMap.get(Integer.parseInt(userId)));
				map.put("readFlag", readFlagMap.get(Integer.parseInt(userId)));
				copyUserList.add(map);
				
			}
			String [] blindCarbonCopyArray = blindCarbonCopy.split(",");
			for(String userId:blindCarbonCopyArray){
				if(TeeUtility.isNullorEmpty(userId)){
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", userId);
				map.put("userName", userMap.get(Integer.parseInt(userId)));
				map.put("readFlag", readFlagMap.get(Integer.parseInt(userId)));
				secretUserList.add(map);
				
			}

			model.setToUserName(userMailBuffer.toString());
			model.setToUserNameAll(buffer.toString());
			model.setUserList(userList);
			model.setCopyUserList(copyUserList);
			model.setSecretUserList(secretUserList);
			model.setContent(mailBody.getContent());
			model.setEmailLevelDesc(levelDesc);
			model.setEmailLevel(mailBody.getEmailLevel());

			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, attachmentModel);
				attachmentModel.setUserId(attach.getUser().getUuid() + "");
				attachmentModel.setUserName(attach.getUser().getUserName());
				attachmentModel.setPriv(1 + 2);// 一共五个权限好像
												// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(attachmentModel);
			}
			model.setAttachMentModel(attachmodels);

			model.setImportant(mailBody.getImportant());

			String fromWebMail = mailBody.getFromWebMail();
			if (!TeeUtility.isNullorEmpty(fromWebMail)) {
				fromWebMail = fromWebMail.replaceAll("<", "&lt;");
				fromWebMail = fromWebMail.replaceAll(">", "&gt;");
			}
			String toWebMail = mailBody.getToWebMail();
			if (!TeeUtility.isNullorEmpty(toWebMail)) {
				toWebMail = toWebMail.replaceAll("<", "&lt;");
				toWebMail = toWebMail.replaceAll(">", "&gt;");
			}
			model.setFromWebMail(fromWebMail);
			model.setToWebMail(toWebMail);
			model.setToWebmail(mailBody.getToWebmail());
			;
			model.setWebmailHtml(mailBody.getWebmailHtml());
			model.setWebmailCount(mailBody.getWebmailCount());
			model.setIfWebMail(mailBody.getIfWebMail());

			model.setWebMailId(mailBody.getWebMailId());
			model.setWebMailUid(mailBody.getWebMailUid());
			model.setCcWebMail(mailBody.getCcWebMail());
			model.setIsHtml(mailBody.getIsHtml());
			model.setLargeAttachment(mailBody.getLargeAttachment());
			model.setNameOrder(mailBody.getNameOrder());

		}

		if (obj.getToUser() != null) {
			model.setToUserId(obj.getToUser().getUuid());
			model.setToUserName(obj.getToUser().getUserName());
		}
		if (obj.getToUser().getDept() != null) {
			//model.setToUserId(obj.getToUser().getUuid());
			model.setDeptName(obj.getToUser().getDept().getDeptName());
		}
		if (obj.getToUser().getUserRole() != null) {
			
			model.setRoleName(obj.getToUser().getUserRole().getRoleName());
		}

		if(obj.getReadTime()!=null){
			model.setReadTimeStr(sdf.format(obj.getReadTime()));
		}
		return model;
	}

	/**
	 * 封装对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public TeeEmailModel parseMailBodyModel(TeeMailBody mailBody) {
		TeeEmailModel model = new TeeEmailModel();
		if (mailBody == null) {
			return model;
		}
		BeanUtils.copyProperties(mailBody, model);
		if (mailBody != null) {
			//邮件级别
			String levelDesc = "";
			if(!TeeUtility.isNullorEmpty(model.getEmailLevel())){
				levelDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("EMAIL_JJCD", model.getEmailLevel());
			}
			model.setEmailLevelDesc(levelDesc);
			if (mailBody.getFromuser() != null) {
				model.setFromUserId(mailBody.getFromuser().getUuid());
				model.setFromUserName(mailBody.getFromuser().getUserName());
			} else {
				model.setFromUserName("");
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSendTime())) {
				model.setSendTimeStr(TeeUtility.getDateStrByFormat(mailBody.getSendTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm")));
			} else {
				model.setSendTimeStr("");
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSubject())) {
				model.setSubject(mailBody.getSubject());
			} else {
				model.setSubject("");
			}
			model.setMailBodySid(mailBody.getSid());

			List<TeeMail> userMailList = mailDao.getMailByBody(mailBody, 0);// 收件人
			List<TeeMail> copyUserMailList = mailDao.getMailByBody(mailBody, 1); // 抄送人
			List<TeeMail> secretUserMailList = mailDao.getMailByBody(mailBody, 2);// 密送人

			List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> copyUserList = new ArrayList<Map<String, String>>();
			List<Map<String, String>> secretUserList = new ArrayList<Map<String, String>>();

			StringBuffer buffer = new StringBuffer();
			StringBuffer userMailBuffer = new StringBuffer();

			for (TeeMail mail : userMailList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(mail.getToUser().getUuid()));
				map.put("userName", mail.getToUser().getUserName());
				map.put("readFlag", String.valueOf(mail.getReadFlag()));
				userList.add(map);
				if (buffer.length() > 0) {
					buffer.append(",");
				}
				buffer.append(mail.getToUser().getUserName());
			}
			userMailBuffer.append(buffer.toString());
			for (TeeMail mail : copyUserMailList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(mail.getToUser().getUuid()));
				map.put("userName", mail.getToUser().getUserName());
				map.put("readFlag", String.valueOf(mail.getReadFlag()));
				copyUserList.add(map);
				if (buffer.length() > 0) {
					buffer.append(",");
				}
				buffer.append(mail.getToUser().getUserName());
			}
			for (TeeMail mail : secretUserMailList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sid", String.valueOf(mail.getToUser().getUuid()));
				map.put("userName", mail.getToUser().getUserName());
				map.put("readFlag", String.valueOf(mail.getReadFlag()));
				secretUserList.add(map);
				if (buffer.length() > 0) {
					buffer.append(",");
				}
				buffer.append(mail.getToUser().getUserName());
			}
			if (buffer.length() <= 0) {
				buffer.append(userMailBuffer.toString());
			}
			model.setToUserName(userMailBuffer.toString());
			model.setToUserNameAll(buffer.toString());
			model.setUserList(userList);
			model.setCopyUserList(copyUserList);
			model.setSecretUserList(secretUserList);
			model.setContent(mailBody.getContent());
			model.setEmailLevelDesc(levelDesc);
			model.setEmailLevel(mailBody.getEmailLevel());

			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			List<TeeAttachment> attaches =  attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
			for (TeeAttachment attach : attaches) {
				TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, attachmentModel);
				attachmentModel.setUserId(attach.getUser().getUuid() + "");
				attachmentModel.setUserName(attach.getUser().getUserName());
				attachmentModel.setPriv(1 + 2);// 一共五个权限好像
												// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(attachmentModel);
			}
			model.setAttachMentModel(attachmodels);

		}

		return model;
	}

	/**
	 * 根据sid查看详情
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getEmailDetailByIdService(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = 0;
		String sidStr = request.getParameter("sid");
		if (TeeUtility.isInteger(sidStr)) {
			sid = Integer.parseInt(sidStr);
		}
		TeeJson json = new TeeJson();
		TeeEmailModel model = null;
		if (sid > 0) {
			TeeMail obj = mailDao.get(sid);
			if (obj != null) {
				model = parseMailModel(obj);
				/*
				 * TeeMailBody mailBody = obj.getMailBody();
				 * model.setFromWebMail(mailBody.getFromWebMail());
				 * model.setToWebMail(mailBody.getToWebMail());
				 */
				
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}
	

	/**
	 * 获取邮件详情，根据MailBody表的sid（草稿箱）
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getEmailDetailByMailBodyIdService(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = 0;
		String sidStr = request.getParameter("sid");
		if (TeeUtility.isInteger(sidStr)) {
			sid = Integer.parseInt(sidStr);
		}
		TeeJson json = new TeeJson();
		TeeEmailModel model = null;
		if (sid > 0) {
			String hql = " from TeeMailBody where sid =?";
			Object[] param = {sid};
			
			List mailList = mailDao.executeQuery(hql, param);
			if(mailList != null && mailList.size()>0){
				TeeMailBody obj = (TeeMailBody) mailList.get(0);
				if (obj != null) {
					model = parseMailBodyModel(obj);
					json.setRtData(model);
					json.setRtState(true);
					json.setRtMsg("查询成功!");
					return json;
				}
			}
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 阅读邮件详情
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson readEmailDetailByIdService(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = 0;
		String sidStr = request.getParameter("sid");
		if (TeeUtility.isInteger(sidStr)) {
			sid = Integer.parseInt(sidStr);
		}
		TeeJson json = new TeeJson();
		TeeEmailModel model = null;
		if (sid > 0) {
			TeeMail obj = mailDao.get(sid);
			if (obj != null) {
				int readFlag = obj.getReadFlag();
				TeeMailBody mailBody = obj.getMailBody();
				if (readFlag == 0) {
					obj.setReadFlag(1);
					obj.setReadTime(new Date());
					int receipt = obj.getReceipt();
					if (receipt == 1) {
						if (mailBody != null) {
							TeePerson sendUser = mailBody.getFromuser();
							String content = "邮件阅读反馈，" + person.getUserName() + " 已阅读你的邮件！主题为：" + mailBody.getSubject();
							Map<String, String> requestData = new HashMap<String, String>();
							requestData.put("content", content);
							requestData.put("userListIds", String.valueOf(sendUser.getUuid()));
							requestData.put("moduleNo", "019");
							smsManager.sendSms(requestData, person);
						}
					}
					mailDao.update(obj);
				}
				model = parseMailModel(obj);

				model.setFromWebMail(mailBody.getFromWebMail());
				model.setToWebMail(mailBody.getToWebMail());

				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}else{
				json.setRtData(null);
				json.setRtState(true);
				return json;
			}
		}
		json.setRtState(false);
		return json;
	}

	/**
	 * 邮件列表（已发送邮件箱）
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getSendEmailListService(TeeDataGridModel dm, HttpServletRequest request, TeeEmailBodyModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String hql = " from TeeMailBody where fromuser.uuid = ? and sendFlag = 1 and delFlag =0";// 已发送邮箱

		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by emailLevel desc, sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMailBody> list = mailBodyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailBodyModel(list.get(i));
				modeltemp.setContent("");
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	/**
	 * 邮件列表（草稿邮件箱）
	 * 
	 * @date 2014年6月10日
	 * @author
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getEmailDraftBoxListService(TeeDataGridModel dm, HttpServletRequest request, TeeEmailBodyModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);

		String hql = " from TeeMailBody where fromuser.uuid = ? and sendFlag=0";

		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by emailLevel desc, sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMailBody> list = mailBodyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailBodyModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 邮件查询
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson mailSearch(TeeDataGridModel dm, Map requestDatas){
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		int box = TeeStringUtil.getInteger(requestDatas.get("box"), 1);//邮箱类型
		int status = TeeStringUtil.getInteger(requestDatas.get("status"), 0);//邮件状态
		Calendar startTime = TeeDateUtil.parseCalendar(TeeStringUtil.getString(requestDatas.get("startTime")));
		Calendar endTime = TeeDateUtil.parseCalendar(TeeStringUtil.getString(requestDatas.get("endTime")));
		int sendUser = TeeStringUtil.getInteger(requestDatas.get("sendUser"), 0);
		String webSendUser = TeeStringUtil.getString(requestDatas.get("webSendUser"));
		String subject = TeeStringUtil.getString(requestDatas.get("subject"));
		String content = TeeStringUtil.getString(requestDatas.get("content"));
		
		List param = new ArrayList();
		StringBuffer hql = new StringBuffer();
		if(box==1){//收件箱
			hql.append("from TeeMail mail,TeeMailBody body where mail.mailBody=body ");
			hql.append(" and body.sendFlag=1 and body.delFlag=0 and mail.deleteFlag=0 and mail.toUser.uuid="+loginPerson.getUuid()+"");
			if(status==1){//未读
				hql.append(" and mail.readFlag=0");
			}else if(status==2){//已读
				hql.append(" and mail.readFlag=1");
			}
		}else if(box==2){//草稿
			hql.append("from TeeMailBody body where 1=1 ");
			hql.append(" and body.sendFlag=0 and body.delFlag=0 and body.fromuser.uuid="+loginPerson.getUuid());
		}else if(box==3){//已发送
			hql.append("from TeeMailBody body where 1=1 ");
			hql.append(" and body.sendFlag=1 and body.delFlag=0 and body.fromuser.uuid="+loginPerson.getUuid());
		}else if(box==4){//已删除
			hql.append("from TeeMail mail,TeeMailBody body where mail.mailBody=body ");
			hql.append(" and  mail.deleteFlag=1 and mail.toUser.uuid="+loginPerson.getUuid()+" ");
			if(status==1){//未读
				hql.append(" and mail.readFlag=0");
			}else if(status==2){//已读
				hql.append(" and mail.readFlag=1");
			}
		}
		
		if(startTime!=null){
			hql.append(" and body.sendTime>=?");
			param.add(startTime.getTime());
		}
		if(endTime!=null){
			hql.append(" and body.sendTime<=?");
			param.add(endTime.getTime());
		}
		
		if(sendUser!=0){
			hql.append(" and body.fromuser.uuid="+sendUser);
		}
		
		if(!"".equals(webSendUser)){
			hql.append(" and body.fromWebMail like '%"+TeeDbUtility.formatString(webSendUser)+"%'");
		}
		
		if(!"".equals(subject)){
			hql.append(" and body.subject like '%"+TeeDbUtility.formatString(subject)+"%'");
		}
		
		if(!"".equals(content)){
			hql.append(" and body.content like '%"+TeeDbUtility.formatString(content)+"%'");
		}
		
		long count = simpleDaoSupport.count("select count(body.sid) "+hql.toString(), param.toArray());
		j.setTotal(count);
		if(dm.getOrder()==null){
			hql.append(" order by "+dm.getOrder()+" "+dm.getSort());
		}
		
		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if(box==1 || box==4){
			List list = simpleDaoSupport.pageFind("select mail "+hql.toString(), dm.getFirstResult(), dm.getRows(), param.toArray());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					TeeEmailModel modeltemp = null;
					modeltemp = parseMailModel((TeeMail)list.get(i));
					modelList.add(modeltemp);
				}
			}
		}else{
			List list = simpleDaoSupport.pageFind("select body "+hql.toString(), dm.getFirstResult(), dm.getRows(), param.toArray());
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					TeeEmailModel modeltemp = null;
					modeltemp = parseMailBodyModel((TeeMailBody)list.get(i));
					modelList.add(modeltemp);
				}
			}
		}
		
		
		j.setRows(modelList);// 设置返回的行
		
		return j;
	}

	/**
	 * 设置邮件阅读状态
	 * 
	 * @date 2014-3-8
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson setEmailReadFlagByIdService(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String sidStr = request.getParameter("sid");
		String readFlag = TeeStringUtil.getString(request.getParameter("readFlag"), "");

		if (TeeUtility.isNullorEmpty(sidStr)) {
			sidStr = "0";
		}
		if (sidStr.endsWith(",")) {
			sidStr = sidStr.substring(0, sidStr.length() - 1);
		}
		String rtMsg = "已标记为已读邮件！";
		if (!TeeUtility.isNullorEmpty(readFlag)) {
			if ("0".equals(readFlag)) {// 标记为未阅读
				String hql = " update TeeMail set readFlag=0  where sid in(" + sidStr + ") and deleteFlag=0 and readFlag=1 ";
				mailDao.executeUpdate(hql, null);
				rtMsg = "已标记为未读邮件！";

			} else {
				String hql = " from TeeMail  where sid in(" + sidStr + ") and mailBody.sendFlag ='1'  and deleteFlag=0 and readFlag=0";
				List<TeeMail> mailList = mailDao.executeQuery(hql, null);
				for (TeeMail mail : mailList) {
					int receipt = mail.getReceipt();
					if (receipt == 1) {
						TeeMailBody mailBody = mail.getMailBody();
						if (receipt == 1) {
							if (mailBody != null) {
								TeePerson sendUser = mailBody.getFromuser();
								String content = "邮件阅读反馈，" + person.getUserName() + " 已阅读你的邮件！主题为：" + mailBody.getSubject();
								Map<String, String> requestData = new HashMap<String, String>();
								requestData.put("content", content);
								requestData.put("userListIds", String.valueOf(sendUser.getUuid()));
								requestData.put("moduleNo", "019");
								smsManager.sendSms(requestData, person);
							}
						}
					}
					mail.setReadFlag(1);
					mailDao.update(mail);
				}
			}
		}
		json.setRtState(true);
		json.setRtMsg(rtMsg);
		return json;
	}

	/**
	 * 设置全部邮件为已阅读状态
	 * 
	 * @date 2014年6月22日
	 * @author
	 * @param request
	 * @return
	 */
	public TeeJson setAllEmailReadFlagService(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Object[] param = { person.getUuid() };
		int optFlag = 0;
		String hql = " from TeeMail where toUser.uuid = ? and mailBody.sendFlag = 1 and readFlag=0 and deleteFlag=0 and (mailBox is null or mailBox.sid = 0)";
		List<TeeMail> mailList = mailDao.executeQuery(hql, param);
		for (TeeMail mail : mailList) {
			int receipt = mail.getReceipt();
			if (receipt == 1) {
				TeeMailBody mailBody = mail.getMailBody();
				if (receipt == 1) {
					if (mailBody != null) {
						TeePerson sendUser = mailBody.getFromuser();
						String content = "邮件阅读反馈，" + person.getUserName() + " 已阅读你的邮件！主题为：" + mailBody.getSubject();
						Map<String, String> requestData = new HashMap<String, String>();
						requestData.put("content", content);
						requestData.put("userListIds", String.valueOf(sendUser.getUuid()));
						requestData.put("moduleNo", "019");
						smsManager.sendSms(requestData, person);
					}
				}
			}
			mail.setReadFlag(1);
			mailDao.update(mail);
		}
		if (mailList.size() > 0) {
			optFlag = 1;
		}
		Map map = new HashMap();
		map.put("optFlag", optFlag);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取文件夹分类
	 * @date 2014年6月22日
	 * @author 
	 * @param request
	 * @return
	 */
	public List<Map<String, String>> getBoxListService(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeMailBoxModel> list = mailService.getBoxList(person, 0);
		List<Map<String, String>> mailBoxList = new ArrayList<Map<String, String>>();
		if (list != null && list.size() > 0) {
			for (TeeMailBoxModel boxModel : list) {
				Map<String, String> boxMap = new HashMap<String, String>();
				boxMap.put("sid", String.valueOf(boxModel.getSid()));
				boxMap.put("boxNo", String.valueOf(boxModel.getBoxNo()));
				boxMap.put("boxName", boxModel.getBoxName());
				boxMap.put("mailCount", String.valueOf(boxModel.getMailCount()));
				boxMap.put("defaultCount", String.valueOf(boxModel.getDefaultCount()));
				boxMap.put("userManager", String.valueOf(boxModel.getUserManager().getUuid()));
				mailBoxList.add(boxMap);
			}
		}
		return mailBoxList;
	}
	
	/**
	 * 获取系统登录人未阅读邮件总数
	 * @date 2014年6月25日
	 * @author 
	 * @param request
	 * @return
	 */
	public long getNotReadingCountService(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		long counter = mailDao.getNotReadingCount(person);
		return counter;
	}
	
	
	

	/**
	 * 外部邮箱列表
	 * @date 2014年6月26日
	 * @author 
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getWebEmailList(TeeDataGridModel dm, HttpServletRequest request, TeeWebEmailModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String hql = " from TeeWebMail webMail where webMail.user.uuid =?";
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailBodyDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by sid desc";

		int firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeWebMail> list = webDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeWebEmailModel> modelList = new ArrayList<TeeWebEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeWebEmailModel modeltemp = parseWebEMailModel(list.get(i));
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}

	
	
	/**
	 * 转换webEmail对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public TeeWebEmailModel parseWebEMailModel(TeeWebMail obj) {
		TeeWebEmailModel model = new TeeWebEmailModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		
		model.setSid(obj.getSid());;
		TeePerson person = personDao.get(obj.getUser().getUuid());
		if(person != null){
			model.setUserId(person.getUuid());
			model.setUserName(person.getUserName());
		}
		model.setIsDefault(obj.getIsDefault());
		model.setRecvDel(obj.getRecvDel());
		model.setPopServer(obj.getPopServer());
		model.setSmtpServer(obj.getSmtpServer());;
		model.setLoginType(obj.getLoginType());
		model.setSmtpPass(obj.getSmtpPass());
		
		model.setPop3Port(obj.getPop3Port());
		model.setSmtpSsl(obj.getSmtpSsl());
		model.setQuotaLimit(obj.getQuotaLimit());
		model.setEmailUid(obj.getEmailUid());
		model.setCheckFlag(obj.getCheckFlag());
		model.setPriority(obj.getPriority());
		
		return model;
	}
	
	
    /**
     * 删除信息
     * @date 2014年5月27日
     * @author 
     * @param sids
     * @return
     */
    public TeeJson deleteWebMailById(String sids){
    	TeeJson json = new TeeJson();
    	webDao.delObjByIds(sids);
    	json.setRtState(true);
        json.setRtMsg("删除成功!");
    	return json;
    }

    /**
     * 获取邮件的签阅详情
     * @param sid
     * @return
     */
	public TeeEasyuiDataGridJson getSignDetail(int sid,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
        String hql="from TeeMail  mail where mail.mailBody.sid=" +sid;
        long total=simpleDaoSupport.count(" select count(*) "+hql, null);
        json.setTotal(total);
        
        List<TeeMail> mailList = mailDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);
        List<TeeEmailModel> modelList=new ArrayList<TeeEmailModel>();
        if (mailList != null) {
			for (int i = 0; i < mailList.size(); i++) {
				TeeEmailModel modeltemp = parseMailModel(mailList.get(i));
				modelList.add(modeltemp);
			}
		}  
        json.setRows(modelList);
		return json;
	}

	/**
	 * 收回邮件
	 * @param ids
	 * @return
	 */
	public TeeJson back(String ids,HttpServletRequest request) {
		TeeJson json=new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取选中的TeeMail
		String h="from TeeMail mail where mail.sid in " +"("+ids+")";
		List<TeeMail> mailList=simpleDaoSupport.executeQuery(h, null);
		for (TeeMail teeMail : mailList) {
			//消息提醒
			Map<String, String> requestData = new HashMap<String, String>();
			requestData.put("content", "来自 " + teeMail.getMailBody().getFromuser().getUserName() + " ，主题为" +teeMail.getMailBody().getSubject()+"的邮件已被撤回");
			requestData.put("userListIds", String.valueOf(teeMail.getToUser().getUuid()));
			requestData.put("moduleNo", "019");
			//requestData.put("remindUrl", "");
			smsManager.sendSms(requestData,person);	
			
		}
		
		//删除
		String hql="delete TeeMail m where m.sid in"+"("+ids+")";
		simpleDaoSupport.executeUpdate(hql, null);
		
		json.setRtState(true);
		return json;
	}

	/**
	 * 自定义邮箱  全部标记为已读
	 * @param request
	 * @return
	 */
	public TeeJson setAllEmailReadFlagCustom(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int mailBoxSid=TeeStringUtil.getInteger(request.getParameter("mailBoxSid"), -1);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Object[] param = { person.getUuid(),mailBoxSid};
		int optFlag = 0;
		String hql = " from TeeMail where toUser.uuid = ? and mailBox.sid=? and deleteFlag in (0,2) and readFlag=0";
		List<TeeMail> mailList = mailDao.executeQuery(hql, param);
		for (TeeMail mail : mailList) {
			int receipt = mail.getReceipt();
			if (receipt == 1) {
				TeeMailBody mailBody = mail.getMailBody();
				if (receipt == 1) {
					if (mailBody != null) {
						TeePerson sendUser = mailBody.getFromuser();
						String content = "邮件阅读反馈，" + person.getUserName() + " 已阅读你的邮件！主题为：" + mailBody.getSubject();
						Map<String, String> requestData = new HashMap<String, String>();
						requestData.put("content", content);
						requestData.put("userListIds", String.valueOf(sendUser.getUuid()));
						requestData.put("moduleNo", "019");
						smsManager.sendSms(requestData, person);
					}
				}
			}
			mail.setReadFlag(1);
			mailDao.update(mail);
		}
		if (mailList.size() > 0) {
			optFlag = 1;
		}
		Map map = new HashMap();
		map.put("optFlag", optFlag);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
}