package com.tianee.oa.mobile.email.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.email.WebMailSender;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeWebMailSendTask;
import com.tianee.oa.core.base.email.dao.TeeMailBodyDao;
import com.tianee.oa.core.base.email.dao.TeeMailDao;
import com.tianee.oa.core.base.email.model.TeeEmailBodyModel;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.core.base.email.model.TeeWebMailModel;
import com.tianee.oa.core.base.email.service.TeeMailService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileEmailService  extends TeeBaseService{

	@Autowired
	private TeeAttachmentService attachmentService;
	
	@Autowired
	private TeeMailBodyDao mailBodyDao;
	
	@Autowired
	private TeeMailDao mailDao;
	
	
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeMailService mailService;
	
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * 邮件列表（收件箱）
	 * 
	 * @date 2014年6月10日
	 * @author syl
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getEmailListService(TeeDataGridModel dm, HttpServletRequest request, TeeEmailModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String hql = " from TeeMail where toUser.uuid = ? and mailBody.sendFlag = 1 and deleteFlag in (0,2) and (mailBox is null or mailBox.sid = 0)";
		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailBodyDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by mailBody.sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMail> list = mailDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailModel(list.get(i) ,true);
				modeltemp.setContent("");
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	/**
	 * 邮件列表（已发送邮件箱）
	 * 
	 * @date 2014年6月10日
	 * @author syl
	 * @param dm
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getSendEmailList(TeeDataGridModel dm, HttpServletRequest request, TeeEmailBodyModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String hql = " from TeeMailBody where fromuser.uuid = ? and sendFlag = 1 and delFlag =0";// 已发送邮箱

		List param = new ArrayList();
		param.add(loginPerson.getUuid());
		// 设置总记录数
		j.setTotal(mailDao.countByList("select count(*) " + hql, param));// 设置总记录数
		hql += " order by sendTime desc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeMailBody> list = mailBodyDao.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeEmailModel> modelList = new ArrayList<TeeEmailModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeEmailModel modeltemp = parseMailBodyModel(list.get(i) , true);
				modeltemp.setContent("");
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
	public TeeEmailModel parseMailModel(TeeMail obj , boolean isSimple) {
		TeeEmailModel model = new TeeEmailModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);
		TeeMailBody mailBody = obj.getMailBody();
		if (mailBody != null) {
			if (mailBody.getFromuser() != null) {
				model.setFromUserId(mailBody.getFromuser().getUuid());
				model.setFromUserName(mailBody.getFromuser().getUserName());
				model.setFromUserUserId(mailBody.getFromuser().getUserId());
			} else {
				model.setFromUserName(mailBody.getFromWebMail());
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSendTime())) {
				model.setSendTimeStr(TeeUtility.getDateStrByFormat(mailBody.getSendTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
			} else {
				model.setSendTimeStr("");
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSubject())) {
				model.setSubject(mailBody.getSubject());
			} else {
				model.setSubject("");
			}
			model.setMailBodySid(mailBody.getSid());
			
			if(!isSimple){
				List<TeeMail> userMailList = mailDao.getMailByBody(mailBody, 0);
				List<TeeMail> copyUserMailList = mailDao.getMailByBody(mailBody, 1);
				List<TeeMail> secretUserMailList = mailDao.getMailByBody(mailBody, 2);

				List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
				List<Map<String, String>> copyUserList = new ArrayList<Map<String, String>>();
				List<Map<String, String>> secretUserList = new ArrayList<Map<String, String>>();

				StringBuffer buffer = new StringBuffer();
				StringBuffer userMailBuffer = new StringBuffer();

				StringBuffer ids = new StringBuffer();//收件人Id字符串
				StringBuffer userIds = new StringBuffer();//收件人userId字符串
				StringBuffer userNames = new StringBuffer();//收件姓名
				StringBuffer copyIds = new StringBuffer();//抄送Id字符串
				StringBuffer copyUserIds = new StringBuffer();//抄送人userId字符串
				StringBuffer copyUserNames = new StringBuffer();//抄送人姓名
				
				Set set = new HashSet();
				
				StringBuffer allIds = new StringBuffer();//所有人，包括发件人、抄送、发送人
				StringBuffer allUserIds = new StringBuffer();
				StringBuffer allUserNames = new StringBuffer();
				if(mailBody.getFromuser()!=null){
					allIds.append(mailBody.getFromuser().getUuid()+",");
					allUserIds.append(mailBody.getFromuser().getUserId() + ",");
					allUserNames.append(mailBody.getFromuser().getUserName() + ",");
					set.add(mailBody.getFromuser().getUuid());
				}
				
				
				
				//key（人员id）：value(阅读标志)
				Map<Integer,String> readFlagMap = new HashMap<Integer, String>();
				//key（人员id）：value(人员名称)
				Map<Integer,String> userMap = new HashMap<Integer, String>();
			
				for (TeeMail mail : userMailList) {
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					if(mail.getToUser()!=null){
						
						buffer.append(mail.getToUser().getUserName());
						readFlagMap.put(mail.getToUser().getUuid(), String.valueOf(mail.getReadFlag()));
						userMap.put(mail.getToUser().getUuid(), mail.getToUser().getUserName());
						ids.append(mail.getToUser().getUuid() + ",");
						userIds.append(mail.getToUser().getUserId() + ",");
						userNames.append(mail.getToUser().getUserName() + ",");
						
						if(!set.contains(mail.getToUser().getUuid())){
							set.add(mail.getToUser().getUuid());
							allIds.append(mail.getToUser().getUuid() + ",");
							allUserIds.append(mail.getToUser().getUserId() + ",");
							allUserNames.append(mail.getToUser().getUserName() + ",");
						}
					}
				}
				userMailBuffer.append(buffer.toString());
				for (TeeMail mail : copyUserMailList) {
					if (buffer.length() > 0) {
						buffer.append(",");
					}
					buffer.append(mail.getToUser().getUserName());
					readFlagMap.put(mail.getToUser().getUuid(), String.valueOf(mail.getReadFlag()));
					userMap.put(mail.getToUser().getUuid(), mail.getToUser().getUserName());
					copyIds.append(mail.getToUser().getUuid() + ",");
					copyUserIds.append(mail.getToUser().getUserId() + ",");
					copyUserNames.append(mail.getToUser().getUserName() + ",");
					if(!set.contains(mail.getToUser().getUuid())){
						set.add(mail.getToUser().getUuid());
						allIds.append(mail.getToUser().getUuid() + ",");
						allUserIds.append(mail.getToUser().getUserId() + ",");
						allUserNames.append(mail.getToUser().getUserName() + ",");
					}
				}
				
				for (TeeMail mail : secretUserMailList) {
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
				
				if(allIds.toString().endsWith(",")){
					allIds.deleteCharAt(allIds.length()-1);
					allUserNames.deleteCharAt(allUserNames.length()-1);
				}
				
				if(copyIds.toString().endsWith(",")){
					copyIds.deleteCharAt(copyIds.length()-1);
					copyUserNames.deleteCharAt(copyUserNames.length()-1);
				}
				
				if(userIds.toString().endsWith(",")){
					userIds.deleteCharAt(userIds.length()-1);
					userNames.deleteCharAt(userNames.length()-1);
				}
				
				//扩展数据
				Map<String , String > extendData = new HashMap<String, String>();
				extendData.put("ids", ids.toString());
				extendData.put("userIds", userIds.toString());
				extendData.put("userNames", userNames.toString());
				extendData.put("copyIds", copyIds.toString());
				extendData.put("copyUserIds", copyUserIds.toString());
				extendData.put("copyUserNames", copyUserNames.toString());
				extendData.put("allIds", allIds.toString());
				extendData.put("allUserIds", allUserIds.toString());
				extendData.put("allUserNames", allUserNames.toString());
				model.setExtendData(extendData);
				
				String recipient = TeeUtility.null2Empty(mailBody.getRecipient());
				String carbonCopy = TeeUtility.null2Empty(mailBody.getCarbonCopy());
				String blindCarbonCopy = TeeUtility.null2Empty(mailBody.getBlindCarbonCopy());
				
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

				List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
				List<TeeAttachment> attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
				for (TeeAttachment attach : attaches) {
					TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
					BeanUtils.copyProperties(attach, attachmentModel);
					attachmentModel.setUserId(attach.getUser().getUuid() + "");
					attachmentModel.setUserName(attach.getUser().getUserName());
					attachmentModel.setPriv(1 + 2);// 一共五个权限好像
					attachmentModel.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));						// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
					attachmodels.add(attachmentModel);
				}
				model.setAttachMentModel(attachmodels);

				model.setImportant(mailBody.getImportant());

				String fromWebMail = mailBody.getFromWebMail();
				if (!TeeUtility.isNullorEmpty(fromWebMail)) {
					fromWebMail = fromWebMail.replaceAll("<", "&lt");
					fromWebMail = fromWebMail.replaceAll(">", "&gt");
				}
				String toWebMail = mailBody.getToWebMail();
				if (!TeeUtility.isNullorEmpty(toWebMail)) {
					toWebMail = toWebMail.replaceAll("<", "&lt");
					toWebMail = toWebMail.replaceAll(">", "&gt");
				}
				model.setFromWebMail(fromWebMail);
				model.setToWebMail(toWebMail);
				model.setToWebmail(mailBody.getToWebmail());
				model.setWebmailHtml(mailBody.getWebmailHtml());
				model.setWebmailCount(mailBody.getWebmailCount());
				model.setIfWebMail(mailBody.getIfWebMail());

				model.setWebMailId(mailBody.getWebMailId());
				model.setWebMailUid(mailBody.getWebMailUid());
				model.setCcWebMail(mailBody.getCcWebMail());
				model.setIsHtml(mailBody.getIsHtml());
				model.setLargeAttachment(mailBody.getLargeAttachment());
				model.setNameOrder(mailBody.getNameOrder());
				
				/*if (obj.getToUser() != null) {
					model.setToUserId(obj.getToUser().getUuid());
					model.setToUserName(obj.getToUser().getUserName());
				}*/
			}else{
				long count  = attachmentService.getAttachesCount(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
				model.setAttachmentCount(count);	
				int spanDay = TeeUtility.getDaySpan(obj.getMailBody().getSendTime() , new Date());//取得间隔数
				SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
				if(spanDay > 7){
					model.setSendTimeStr(TeeUtility.getDateTimeStr(obj.getMailBody().getSendTime()  ,format));
				}else if(spanDay < 1){
					model.setSendTimeStr("今天");
				}else{
					model.setSendTimeStr(spanDay + "天前");
				}
			}
		}
	
		return model;
	}
    
	/**
	 * 获取邮件详情  byId
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEmailModel model = null;
		if (sid > 0) {
			TeeMail obj = mailDao.get(sid);
			if (obj != null) {
				int readFlag = obj.getReadFlag();
				TeeMailBody mailBody = obj.getMailBody();
				if (readFlag == 0) {
					obj.setReadFlag(1);
					int receipt = obj.getReceipt();
					if (receipt == 1) {
						if (mailBody != null) {
							TeePerson sendUser = mailBody.getFromuser();
							String content = "邮件阅读反馈，" + person.getUserName() + " 已阅读你的邮件！主题为：" + mailBody.getSubject();
							Map<String, String> requestData = new HashMap<String, String>();
							requestData.put("content", content);
							requestData.put("userListIds", String.valueOf(sendUser.getUuid()));
							requestData.put("moduleNo", "019");
							//smsManager.sendSms(requestData, person);
						}
					}
					mailDao.update(obj);
				}
				model = parseMailModel(obj  , false);
				model.setFromWebMail(mailBody.getFromWebMail());
				model.setToWebMail(mailBody.getToWebMail());

				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtMsg("查询失败");
		return json;
	}
	
	/**
	 * 获取邮件详情，根据MailBody表的sid（草稿箱）
	 * 
	 * @date 2014-3-8
	 * @author syl
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getEmailDetailByMailBodyId(HttpServletRequest request) {
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = 0;
		String sidStr = request.getParameter("sid");
		if (TeeUtility.isInteger(sidStr)) {
			sid = Integer.parseInt(sidStr);
		}
		TeeJson json = new TeeJson();
		TeeEmailModel model = null;
		if (sid > 0) {
			String hql = " from TeeMail  where mailBody.sid =?  and (toUser.uuid=? or mailBody.fromuser.uuid =? )";
			Object[] param = {sid,person.getUuid(),person.getUuid()};
			
			List<TeeMail> mailList = mailDao.executeQuery(hql, param);
			if(mailList != null && mailList.size()>0){
				TeeMail obj = mailList.get(0);
				if (obj != null) {
					model = parseMailModel(obj , false);
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
	 * 封装对象
	 * 
	 * @date 2014-3-17
	 * @author
	 * @param obj
	 * @return
	 */
	public TeeEmailModel parseMailBodyModel(TeeMailBody mailBody , boolean isSimple) {
		TeeEmailModel model = new TeeEmailModel();
		if (mailBody == null) {
			return model;
		}
		BeanUtils.copyProperties(mailBody, model);
		if (mailBody != null) {
			if (mailBody.getFromuser() != null) {
				model.setFromUserId(mailBody.getFromuser().getUuid());
				model.setFromUserName(mailBody.getFromuser().getUserName());
			} else {
				model.setFromUserName("");
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSendTime())) {
				model.setSendTimeStr(TeeUtility.getDateStrByFormat(mailBody.getSendTime(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
			} else {
				model.setSendTimeStr("");
			}
			if (!TeeUtility.isNullorEmpty(mailBody.getSubject())) {
				model.setSubject(mailBody.getSubject());
			} else {
				model.setSubject("");
			}
			model.setMailBodySid(mailBody.getSid());

			if(!isSimple){
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

				List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
				List<TeeAttachment> attaches =  attachmentService.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
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
			}else{
				long count  = attachmentService.getAttachesCount(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
				model.setAttachmentCount(count);	
				int spanDay = TeeUtility.getDaySpan(mailBody.getSendTime() , new Date());//取得间隔数
				SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd");
				if(spanDay > 7){
					model.setSendTimeStr(TeeUtility.getDateTimeStr(mailBody.getSendTime()  ,format));
				}else if(spanDay < 1){
					model.setSendTimeStr("今天");
				}else{
					model.setSendTimeStr(spanDay + "天前");
				}
			}
		}
		model.setReadFlag(1);
		return model;
	}
	 /**
     * 新建或者更新 获取数据
     * @author syl
     * @date 2014-4-15
     * @param request
     * @param response
     * @return
     * @throws IOException 
     */
    public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws IOException{
		TeeJson json = new TeeJson();
		String message = "";
		Map emailModel  = new HashMap();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachAll = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.EMAIL);
		int sid = TeeStringUtil.getInteger(multipartRequest.getParameter("sid"),0);
		String attachment_id_old = TeeStringUtil.getString(multipartRequest.getParameter("attachment_id_old"));
		String delExistattachIds = TeeStringUtil.getString(multipartRequest.getParameter("delExistattachIds"));//编辑的时候，删除已存在的附件Id字符串，以逗号分隔
		String userListIds = request.getParameter("userListIds");
		String copyUserListIds = request.getParameter("copyUserListIds");
		String secretUserListIds = request.getParameter("secretUserListIds");
		String externalInput = request.getParameter("externalInput");
//		System.out.println(externalInput);
		String content = request.getParameter("content");
		String subject = request.getParameter("subject");
		String optType = TeeStringUtil.getString(request.getParameter("optType"));// 操作类型 ：空-新建0-收件箱回复  1- 发件箱 2- 收件箱回复全部  3- 收件箱转发  
		String receipt = "0";//是否请求阅读收条 0-不请求  1-请求  如果是1，则收件人收到邮件之后，系统会自动给发件人发一个短消息（收条）
		String type = "1";
		String webmailHtml = "";
		
		
		//获取外部邮箱
		TeeWebMailModel model = new TeeWebMailModel();
		List<TeeWebMailModel> webList = mailService.getWebMailDefault(person);
		if (webList.size() > 0) {
			model = webList.get(0);
		}

		if(sid > 0) {// 编辑、转发、回复获取附件等数据
			
		}
		String webmailCount = "1";
		String smsRemind = "1";//是否发送短消息
		TeeMailBody mailBody = new TeeMailBody();
		if (!TeeUtility.isNullorEmpty(webmailCount)) {
			mailBody.setWebmailCount(Integer.parseInt(webmailCount));
		}



		mailBody.setWebmailHtml(webmailHtml);
		mailBody.setFromuser(person);
		mailBody.setNameOrder(person.getUserName());
		mailBody.setContent(content);
		mailBody.setCompressContent("");
		mailBody.setFromWebMail("");
		mailBody.setImportant("");
		mailBody.setSendFlag("1");// 发送
		mailBody.setSendTime(new Date());
		mailBody.setSize(100);
		mailBody.setSmsRemind(smsRemind);
		mailBody.setSubject(subject);
		mailBody.setToWebmail(externalInput);
		//mailBody.setSid(sid);
		mailBodyDao.saveOrUpdate(mailBody);
		
		List<TeeAttachment> attachments = new ArrayList<TeeAttachment>();
		if(optType.equals("1") || optType.equals("3")){//再次编辑+转发
			attachments = attachmentService.getAttachmentsByIds(attachment_id_old);//原有数据上传附件
		}
		if(attachments!= null && attachments.size()>0){
			for(TeeAttachment attachment:attachments){
				//复制附件
				TeeAttachment newAttachment = attachmentService.clone(attachment, attachment.getModel(), person);
				attachAll.add(newAttachment);
			}
		}
		//处理邮件附件
		for(TeeAttachment attach:attachAll){
			attach.setModelId(String.valueOf(mailBody.getSid()));
			simpleDaoSupport.update(attach);
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
//				requestData.put("remindUrl", "/system/core/email/readSmsEmailBody.jsp?sid=" + mail.getSid()) ;
				requestData.put("remindUrl", "/system/core/email/readEmailByMailId.jsp?sid="+mail.getSid()+"&closeOptFlag=1") ;
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
//					message = sender.sendWebMail(model.getSmtpServer(), model.getSmtpPort(), model.getLoginType(), subject, model.getEmailUser(), model.getEmailPass(), webMail, subject, content,
//							attachments);
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
	    return json ;
    }

    
    /**
     * 获取最近联系人  最近30条记录
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public TeeJson getRecentContacters(TeePerson loginUSer){
		TeeJson json = new TeeJson();
		String hql = "select distinct(mail.toUser) from TeeMail mail where mail.mailBody.fromuser.uuid="+loginUSer.getUuid()+" order by mail.sid desc";
		List<TeePerson> toUsers = simpleDaoSupport.pageFind(hql, 0, 30, null);
		List list = new ArrayList();
		
		for(TeePerson person:toUsers){
			Map map = new HashMap();
			map.put("userSid", person.getUuid());
			map.put("userName", person.getUserName());
			
			list.add(map);
		}
		
		json.setRtData(list);
		return json;
    }
}


