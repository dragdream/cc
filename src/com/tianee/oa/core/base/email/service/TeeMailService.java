package com.tianee.oa.core.base.email.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.filters.StringInputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.email.EmailFileWriter;
import com.tianee.oa.core.base.email.WebMailSender;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeMailBox;
import com.tianee.oa.core.base.email.bean.TeeMailColor;
import com.tianee.oa.core.base.email.bean.TeeWebMail;
import com.tianee.oa.core.base.email.dao.TeeMailBodyDao;
import com.tianee.oa.core.base.email.dao.TeeMailBoxDao;
import com.tianee.oa.core.base.email.dao.TeeMailColorDao;
import com.tianee.oa.core.base.email.dao.TeeMailDao;
import com.tianee.oa.core.base.email.dao.TeeWebMailDao;
import com.tianee.oa.core.base.email.model.TeeMailBoxModel;
import com.tianee.oa.core.base.email.model.TeeMailModel;
import com.tianee.oa.core.base.email.model.TeeWebMailModel;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.file.TeeZipUtil;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeMailService extends TeeBaseService{
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
    
    /**
     * 验证邮箱地址是否正确
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
	    try{
	        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	        Pattern regex = Pattern.compile(check);
	        Matcher matcher = regex.matcher(email);
	        flag = matcher.matches();
        }catch(Exception e){
	        flag = false;
	    }
	    return flag;
    }
    /**
     * 新增或者更新邮件
     * @param userListIds
     * @param copyUserListIds
     * @param secretUserListIds
     * @param externalInput
     * @param content
     * @param subject
     * @param person
     */
    @Transactional(readOnly=false)
	public String addOrUpdateMail(String userListIds, String copyUserListIds,
			String secretUserListIds, String externalInput, String content,
			String subject, String type,TeePerson person,List<TeeAttachment> attachList,String webmailCount,String webmailHtml) {
		// TODO Auto-generated method stub
    	String message = "发送成功";
    	try{
			
			TeeWebMailModel model = new TeeWebMailModel();
			List<TeeWebMailModel> webList = getWebMailDefault(person);
			if(webList.size()>0){
				model = webList.get(0);
			}
			TeeMailBody mailBody = new TeeMailBody();
			if(!TeeUtility.isNullorEmpty(webmailCount)){
				mailBody.setWebmailCount(Integer.parseInt(webmailCount));
			}
			mailBody.setWebmailHtml(webmailHtml);
			mailBody.setFromuser(person);
			mailBody.setNameOrder(person.getUserName());
			mailBody.setContent(content);
			mailBody.setCompressContent("");
			mailBody.setFromWebMail("");
			mailBody.setImportant("");
			if(type.equals("0")){
				mailBody.setSendFlag("0");
			}else{
				mailBody.setSendFlag("1");
			}
			mailBody.setSendTime(new Date());
			mailBody.setSize(100);
			mailBody.setSmsRemind("0");
			mailBody.setSubject(subject);
			mailBody.setToWebmail(externalInput);
			mailBodyDao.saveOrUpdate(mailBody);
			
			//处理附件
			for(TeeAttachment attach:attachList){
				attach.setModelId(String.valueOf(mailBody.getSid()));
				simpleDaoSupport.update(attach);
			}
			
			mailDao.deleteMailByBody(mailBody);
			if(!TeeUtility.isNullorEmpty(userListIds)){
				if(userListIds.endsWith(",")){
					userListIds = userListIds.substring(0, userListIds.length()-1);
				}
				String[] userListId = userListIds.split(",");
				for(String userId : userListId){
					TeeMail mail = new TeeMail();
					mail.setToUser(personDao.load(Integer.parseInt(userId)));
					mail.setReceiveType(0);
					mail.setReceipt(0);
					mail.setReadFlag(0);
					mail.setMailBox(null);
					mail.setMailBody(mailBody);
					mail.setDeleteFlag(0);
					mailDao.saveOrUpdate(mail);
				}
			}
			if(!TeeUtility.isNullorEmpty(copyUserListIds)){
				if(copyUserListIds.endsWith(",")){
					copyUserListIds = copyUserListIds.substring(0, copyUserListIds.length()-1);
				}
				String[] userListId = copyUserListIds.split(",");
				for(String userId : userListId){
					TeeMail mail = new TeeMail();
					mail.setToUser(personDao.load(Integer.parseInt(userId)));
					mail.setReceiveType(1);
					mail.setReceipt(0);
					mail.setReadFlag(0);
					mail.setMailBox(null);
					mail.setMailBody(mailBody);
					mail.setDeleteFlag(0);
					mailDao.saveOrUpdate(mail);
				}
			}
			if(!TeeUtility.isNullorEmpty(secretUserListIds)){
				if(secretUserListIds.endsWith(",")){
					secretUserListIds = secretUserListIds.substring(0, secretUserListIds.length()-1);
				}
				String[] userListId = secretUserListIds.split(",");
				for(String userId : userListId){
					TeeMail mail = new TeeMail();
					mail.setToUser(personDao.load(Integer.parseInt(userId)));
					mail.setReceiveType(2);
					mail.setReceipt(0);
					mail.setReadFlag(0);
					mail.setMailBox(null);
					mail.setMailBody(mailBody);
					mail.setDeleteFlag(0);
					mailDao.saveOrUpdate(mail);
				}
			}
			if(!TeeUtility.isNullorEmpty(externalInput)&&type.equals("1")){
				WebMailSender sender = new WebMailSender();
				externalInput = externalInput.substring(0, externalInput.length());
				String[] webMails = externalInput.split(";");
				for(String webMail : webMails){
					if(checkEmail(webMail)&&model!=null&&!TeeUtility.isNullorEmpty(model.getSmtpServer())){
						//System.out.println("执行发送外部邮件:"+webMail);
						message = sender.sendWebMail(model.getSmtpServer(),model.getSmtpPort(),model.getLoginType(),subject,model.getEmailUser(),model.getEmailPass(), webMail, subject, content, attachList);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return message;
	}
    /**
     * 新增或者更新分类箱
     */
    @Transactional(readOnly=false)
	public void saveOrUpdateMailBox(TeePerson person,String boxName ,String boxNo,String sid) {
		// TODO Auto-generated method stub
		int boxNoInt = TeeStringUtil.getInteger(boxNo, 0);
    	try{
			TeeMailBox mailBox = new TeeMailBox();
			if(!TeeUtility.isNullorEmpty(sid)){
				mailBox.setSid(Integer.parseInt(sid));
			}
			mailBox.setBoxName(boxName);
			mailBox.setBoxNo(boxNoInt);
			mailBox.setDefaultCount(0);
			mailBox.setUserManager(person);
			mailBoxDao.saveOrUpdate(mailBox);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    /**
     * 新增或者更新分类箱
     */
    @Transactional(readOnly=false)
	public void moveMailBox(String boxId,String mailId) {
		// TODO Auto-generated method stub
		try{
			mailDao.updateMail(Integer.parseInt(mailId),Integer.parseInt(boxId));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    /**
     * 获取邮件列表（分4种类型）
     * @param type
     * @param person
     * @return
     */
    public List<TeeMailModel> getListGroup(String selectType,String key,int type,TeePerson person,int mailId,int box,String lookAll,String order,String date,String orderRule,int f,int s){
    	List<TeeMailModel> list = new ArrayList<TeeMailModel>();
    	list = mailDao.getListGroup(selectType,key,type,person,mailId,box,lookAll,order,date,orderRule,f,s);
    	return list;
    }
    
    /**
     * 获取邮件列表数量（分4种类型）
     * @param type
     * @param person
     * @return
     */
    public int getListGroupCount(String selectType,String key,int type,TeePerson person,int mailId,int box,String lookAll,String order,String date){
    	int count = 0;
    	count = mailDao.getListGroupCount(selectType,key,type,person,mailId,box,lookAll,order,date);
    	return count;
    }
    
    /**
     * 获取邮件分类箱
     * @param person
     * @return
     */
    public List<TeeMailBoxModel> getBoxList(TeePerson person,int boxId){
    	List<TeeMailBoxModel> list = new ArrayList<TeeMailBoxModel>();
    	list = mailDao.getBoxList(person,boxId);
    	return list;
    }
    
    /**
     * 获取邮件数量（分4种类型）
     * @param type
     * @param person
     * @return
     */
    public long getListCount(int type,TeePerson person){
    	long count = 0;
    	count = mailDao.getListCount(type,person,0);
    	return count;
    }
    
    public List<TeeAttachmentModel> getMailAttachModelList(int id,int ifBody){
    	List<TeeAttachment> list = new ArrayList<TeeAttachment>();
    	List<TeeAttachmentModel> list1 = new ArrayList<TeeAttachmentModel>();
    	TeeMailBody body = new TeeMailBody();
    	if(ifBody==0||ifBody==3||ifBody==4||ifBody==7){
        	TeeMail mail = mailDao.get(id);
        	body = mail.getMailBody();
        	list = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(body.getSid()));
    		for(TeeAttachment attach:list){
    			TeeAttachmentModel m = new TeeAttachmentModel();
    			BeanUtils.copyProperties(attach, m);
    			m.setUserId(attach.getUser().getUuid()+"");
    			m.setUserName(attach.getUser().getUserName());
    			m.setPriv(1+2);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
    			list1.add(m);
    		}
    	}else{
    		body = mailBodyDao.get(id);
    		list = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(body.getSid()));
    		for(TeeAttachment attach:list){
    			TeeAttachmentModel m = new TeeAttachmentModel();
    			BeanUtils.copyProperties(attach, m);
    			m.setUserId(attach.getUser().getUuid()+"");
    			m.setUserName(attach.getUser().getUserName());
    			m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
    			list1.add(m);
    		}
    	}
    	return list1;
    }
    
	/**
	 * 删除邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void delMail(String ids,String ifBody) {
		ids = TeeStringUtil.formatIdsQuote(ids);
		if (!"".equals(ids)) {
			simpleDaoSupport.executeUpdate(
					"update TeeMail mail set mail.deleteFlag=1,mail.mailBox = null where mail.sid in ("
							+ ids + ")", null);
		}
	}
	
	/**
	 * 销毁邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void destroyMails(String ids,String ifBody) {
		ids = TeeStringUtil.formatIdsQuote(ids);
		if (!"".equals(ids)) {
			/*simpleDaoSupport.executeUpdate(
					"delete from TeeMail mail where mail.sid in ("
							+ ids + ")", null);*/
			simpleDaoSupport.executeUpdate(" update TeeMail  set deleteFlag=2  where sid in ("
							+ ids + ")", null);
		}
	}
	
	/**
	 * 移动邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void moveMail(String ids,String ifBody,String boxId) {
		ids = TeeStringUtil.formatIdsQuote(ids);
		if (!"".equals(ids)) {
			simpleDaoSupport.executeUpdate(
					"update TeeMail mail set mail.mailBox.sid="+boxId+",mail.deleteFlag = 0 where mail.sid in ("
							+ ids + ")", null);
		}
	}
	
	/**
	 * 移至收信箱
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void moveToReceive(String id) {
		if (!"".equals(id)) {
			simpleDaoSupport.executeUpdate(
					"update TeeMail mail set mail.mailBox = null,mail.deleteFlag = 0 where mail.sid ="+id, null);
		}
	}
	
	/**
	 * 标记全部邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void markMail(String id,int personId) {
		id = id.trim();
		int i = 0;
		String hql = "";
		if (!"".equals(id)) {
			if(id.indexOf("_")!=-1){
				i = Integer.parseInt(id.split("_")[1]);
				hql = "update TeeMail mail set mail.readFlag = 1 where mail.mailBox = "+i;
			}else{
				i = Integer.parseInt(id);
				switch (i) {
				case 0:
					hql = "update TeeMail mail set mail.readFlag = 1 where exists (select 1 from  TeeMailBody mailBody where mail.mailBody = mailBody.sid and mailBody.sendFlag = 1) and mail.toUser = '"+personId+"' and mail.deleteFlag in (0,2)  and mail.mailBox is null";
					break;

				case 1:
					hql = "";
					break;
				case 2:
					hql = "";
					break;
				case 3:
					hql = "update TeeMail mail set mail.readFlag = 1  where exists (select 1 from  TeeMailBody mailBody where mail.mailBody = mailBody.sid and mailBody.sendFlag = 1) and mail.toUser = '"+personId+"' and mail.deleteFlag = 1  and mail.mailBox is null";
					break;
				}
			}
			simpleDaoSupport.executeUpdate(hql, null);
		}
	}
	
	/**
	 * 清空邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void clearMail(String id,int personId) {
		id = id.trim();
		int i = 0;
		String hql = "";
		if (!"".equals(id)) {
			if(id.indexOf("_")!=-1){
				i = Integer.parseInt(id.split("_")[1]);
				hql = "update TeeMail mail set mail.deleteFlag = 1,mail.mailBox = null where mail.mailBox = "+i;
			}else{
				i = Integer.parseInt(id);
				switch (i) {
				case 0:
					hql = "update TeeMail mail set mail.deleteFlag = 1,mail.mailBox = null where exists (select 1 from  TeeMailBody mailBody where mail.mailBody = mailBody.sid and mailBody.sendFlag = 1) and mail.toUser = '"+personId+"' and mail.deleteFlag in (0,2)  and mail.mailBox is null";
					break;

				case 1:
					hql = "";
					break;
				case 2:
					hql = "";
					break;
				case 3:
					hql = "update TeeMail mail set mail.deleteFlag = 1,mail.mailBox = null  where exists (select 1 from  TeeMailBody mailBody where mail.mailBody = mailBody.sid and mailBody.sendFlag = 1) and mail.toUser = '"+personId+"' and mail.deleteFlag = 1  and mail.mailBox is null";
					break;
				}
			}
			simpleDaoSupport.executeUpdate(hql, null);
		}
	}
	
	/**
	 * 销毁已删除邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void destroyMail(String id,int personId) {
		id = id.trim();
		String hql = "";
		if (!"".equals(id)) {
	
			hql = "delete from TeeMail mail where exists (select 1 from  TeeMailBody mailBody where mail.mailBody = mailBody.sid and mailBody.sendFlag = 1) and mail.toUser = '"+personId+"' and mail.deleteFlag = 1  and mail.mailBox is null";

		}
			simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
		
	}
	
	/**
	 * 重命名分类箱
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void renameBox(String id,String name) {
		if (!"".equals(id)) {
			simpleDaoSupport.executeUpdate(
					"update TeeMailBox box set box.boxName='"+name+"' where box.sid ="+id, null);
		}
	}
	
	/**
	 * 删除分类箱
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void deleteBox(String id) {
		if (!"".equals(id)) {
			simpleDaoSupport.executeUpdate(
					"delete from TeeMail mail where mail.mailBox.sid ="+id, null);
			
			simpleDaoSupport.executeUpdate(
					"delete from TeeMailBox box where box.sid ="+id, null);
		}
	}
	
	public List<TeeMailModel> selectMail(String type,String ifBox,String selectType,TeePerson person ,String key,String lookAll,String order,String date,String orderRule,int f,int s){
		List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		list = mailDao.selectMail(type,ifBox,selectType,person,key,lookAll,order,date,orderRule,f,s);
		return list;
	}
	
	public int selectMailCount(String type,String ifBox,String selectType,TeePerson person ,String key,String lookAll,String order,String date){
		int count = 0;
		count = mailDao.selectMailCount(type,ifBox,selectType,person,key,lookAll,order,date);
		return count;
	}
	
	public List<TeeMailModel> selectMailById(String ifBody,TeePerson person ,String id){
		List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		list = mailDao.selectMailById(ifBody,person,id);
		return list;
	}
	
	public TeeMail getMailById(int id){
		return mailDao.get(id);
	}
	
	public TeeMailBody getMailBodyById(int id){
		return mailBodyDao.get(id);
	}
	
	/**
	 * 设置肤色
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void setMailColor(String color,TeePerson person) {
		List<TeeMailColor> list = new ArrayList<TeeMailColor>();
		TeeMailColor mailColor = new TeeMailColor();
		try {
			list = colorDao.checkColor(person,"MAIL");
			if(list.size()>0){
				mailColor = list.get(0);
				mailColor.setColorValue("#"+color);
				colorDao.updateMailColor(mailColor);
			}else{
				mailColor.setColorValue("#"+color);
				mailColor.setUser(person);
				mailColor.setModularName("MAIL");
				colorDao.save(mailColor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public List<TeeMailModel> selectMailForPortlet(TeePerson person ,int type,int maxSize){
		List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		list = mailDao.selectMailForPortlet(person,type,maxSize);
		return list;
	}
	
	public List<TeeMailModel> selectMailForQueryIndex(TeePerson person ,String key,int maxSize){
		List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		list = mailDao.selectMailForQueryIndex(person,key,maxSize);
		return list;
	}
	
	public long getMailAttachSize(TeePerson person ,String modelId){
		long attachSize = 0;
		attachSize = mailDao.getMailAttachSize(person,modelId);
		return attachSize;
	}
	
	public List<TeeWebMailModel> setWebMailIndex(TeePerson person){
		List<TeeWebMailModel> list = new ArrayList<TeeWebMailModel>();
		list = webDao.setWebMailIndex(person);
		return list;
	}
	
   /**
     * 新增或者更新外部邮箱
     */
    @Transactional(readOnly=false)
	public void saveOrUpdateWebMail(TeeWebMail webMail,TeePerson person) {
		// TODO Auto-generated method stub
		try{
			webDao.saveOrUpdate(webMail);
			if(webMail.getIsDefault()==1){
				defaultWebMail(webMail.getSid(),person);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
    
    /**
     * 根据id获取外部邮箱
     */
	public TeeWebMail getWebMailById(int sid) {
		// TODO Auto-generated method stub
		TeeWebMail webMail = new TeeWebMail();
		try{
			webMail = webDao.get(sid);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return webMail;
	}
	
    /**
     * 根据id获取外部邮箱
     */
	public void delWebMail(TeeWebMail webMail) {
		try{
			webDao.deleteByObj(webMail);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 设置默认邮箱
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void defaultWebMail(int id,TeePerson perosn) {
		simpleDaoSupport.executeUpdate("update TeeWebMail mail set mail.isDefault = 1 where mail.sid = "+ id +" and mail.user.uuid="+perosn.getUuid(), null);
		simpleDaoSupport.executeUpdate("update TeeWebMail mail set mail.isDefault = 0 where mail.sid != "+ id +" and mail.user.uuid="+perosn.getUuid(), null);
	}
	
	public List<TeeWebMailModel> getWebMailDefault(TeePerson person){
		List<TeeWebMailModel> list = new ArrayList<TeeWebMailModel>();
		list = webDao.getWebMailDefault(person);
		return list;
	}
	
	
	/**
	 * 清空草稿箱、已发送邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void clearMailT(String id,int personId) {
		id = id.trim();
		String hql = "";
		if (!"".equals(id)) {
			if(id.equals("1")){
				hql = "delete from TeeMailBody mailBody where mailBody.fromuser = '"+personId+"' and mailBody.sendFlag = 0";
			}else if(id.equals("2")){
				hql = "update TeeMailBody mailBody set mailBody.delFlag = 1 where mailBody.fromuser = '"+personId+"' and mailBody.sendFlag = 1";
			}
		}
			simpleDaoSupport.deleteOrUpdateByQuery(hql, null);	
	}

	
	/**
	 * 移动多个邮件到收件箱
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void moveReceive(String ids) {
		ids = TeeStringUtil.formatIdsQuote(ids);
		if (!"".equals(ids)) {
			simpleDaoSupport.executeUpdate(
					"update TeeMail mail set mail.mailBox = null,mail.deleteFlag = 0 where mail.sid in ("
							+ ids + ")", null);
		}
	}
	
	/**
	 * 删除草稿箱、已发送邮件
	 * 
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void delSingleMailBody(String ids,String value,int personId) {
		ids = ids.trim();
		String hql = "";
		ids = TeeStringUtil.formatIdsQuote(ids);
		if (!"".equals(ids)&&!"".equals(value)) {
			if(value.equals("1")){
				hql = "delete from TeeMailBody mailBody where mailBody.fromuser = '"+personId+"' and mailBody.sendFlag = 0 and mailBody.sid in ("+ ids + ")";
				simpleDaoSupport.deleteOrUpdateByQuery(hql, null);	
			}else if(value.equals("2")){
				//hql = "update TeeMailBody mailBody set mailBody.delFlag = 1 where mailBody.fromuser = '"+personId+"' and mailBody.sendFlag = 1 and mailBody.sid in ("+ ids + ")";
			    //System.out.println(ids);
			    String []idArray=ids.split(",");
			    if(idArray!=null&&idArray.length>0){
			    	for (String str : idArray) {
			    		//判断TeeMail是否有已经阅读的  有则逻辑删除    否则物理删除
						List<TeeMail> mailList=simpleDaoSupport.executeQuery(" from TeeMail where readFlag=1 and mailBody.sid=? ", new Object[]{TeeStringUtil.getInteger(str, 0)});
					    if(mailList!=null&&mailList.size()>0){//逻辑删除
					    	hql = "update TeeMailBody mailBody set mailBody.delFlag = 1 where mailBody.fromuser = '"+personId+"' and mailBody.sendFlag = 1 and mailBody.sid in ("+ str + ")";
					    	simpleDaoSupport.deleteOrUpdateByQuery(hql, null);	
					    }else{//物理删除
					    	hql=" delete from TeeMail where mailBody.sid=? ";
					    	simpleDaoSupport.executeUpdate(hql, new Object[]{TeeStringUtil.getInteger(str,0)});
					    	
					    	hql=" delete from TeeMailBody where sid=? ";
					    	simpleDaoSupport.executeUpdate(hql, new Object[]{TeeStringUtil.getInteger(str,0)});
					    }
			    	}
			    }
			}
		}
			
	}
	
	public void deleteMailBody(int sid){
		mailBodyDao.delete(sid);
	}
	
/**
	 * 批量导出邮件
	 * @param mailIds
	 *//*
	public void exportEml(String mailIds) {
	    //根据邮件id字符串获取邮件实体集合
		List<TeeMail>mailList=new ArrayList<TeeMail>();
		String[]Ids=mailIds.split(",");
		for (String id : Ids) {
			TeeMail mail=mailDao.get(Integer.parseInt(id));
			mailList.add(mail);
		}
		
		
		EmailFileWriter writer=new EmailFileWriter();
		List<TeeAttachment> attList=new ArrayList<TeeAttachment>();
        for (TeeMail mail : mailList) { 
        	//附件
        	String hql="from TeeAttachment att where att.model='email' and att.modelId="+(mail.getMailBody().getSid()+"");
        	attList=simpleDaoSupport.executeQuery(hql,null);	
        	for (TeeAttachment att : attList) {
        		writer.addFile(new String[]{att.getFilePath(),att.getFileName()});
			}
        	
        	//抄送
        	String cs=mail.getMailBody().getCarbonCopy();
        	List<TeePerson> list=personDao.getPersonByUuids(cs);
        	String csNames="";
        	for (TeePerson teePerson : list) {
        		csNames+=teePerson.getUserName()+",";
			}
        	if(csNames.endsWith(",")){
        		csNames.substring(0, csNames.length()-1);
        	}
        	writer.setCc("");

        	//密送
        	String ms=mail.getMailBody().getBlindCarbonCopy();
        	List<TeePerson> list1=personDao.getPersonByUuids(ms);
        	String msNames="";
        	for (TeePerson teePerson : list1) {
        		msNames+=teePerson.getUserName()+",";
			}
        	if(msNames.endsWith(",")){
        		msNames.substring(0, msNames.length()-1);
        	}
        	writer.setBcc("");
        	
        	writer.setCharset("utf-8");
        	writer.setContent(mail.getMailBody().getContent());
        	writer.setContentType("text/html");
        	writer.setDisplayName("");
        	writer.setFrom(mail.getMailBody().getFromuser().getUserName());
        	writer.setPort(25);
        	writer.setSentDate(mail.getMailBody().getSendTime());
        	writer.setServer("");
        	writer.setSubject(mail.getMailBody().getSubject());
        	writer.setTo(mail.getToUser().getUserName());
        	
        	try {
				writer.writeTo("d:/测试.eml");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		 		
	}*/

	
         /**
		 * 收件箱和自定义邮箱批量导出邮件 
		 * @param mailIds
		 */
		public void exportEml(String mailIds,HttpServletResponse response) {
			OutputStream output =null;
			InputStream zipInputStream=null;
			Calendar c = Calendar.getInstance(); 
			String time="["+ c.get(Calendar.YEAR)+(c.get(Calendar.MONTH) + 1)+ c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+"]";
			//创建临时下载文件夹
    		String uuid = UUID.randomUUID().toString().replace("-", "");
    		String nameDesc =time+"邮件";
			try {
		    //根据邮件id字符串获取邮件实体集合
			List<TeeMail>mailList=new ArrayList<TeeMail>();
			String[]Ids=mailIds.split(",");
			for (String id : Ids) {
				TeeMail mail=mailDao.get(Integer.parseInt(id));
				mailList.add(mail);
			}
			
			 
			
			EmailFileWriter writer=new EmailFileWriter();
			List<TeeAttachment> attList=new ArrayList<TeeAttachment>();
			File file=null;
			
	        for (TeeMail mail : mailList) {
	        	
	        	//附件
	        	String hql="from TeeAttachment att where att.model='email' and att.modelId="+(mail.getMailBody().getSid()+"");
	        	attList=simpleDaoSupport.executeQuery(hql,null);	
	        	File f=null;
	        	for (TeeAttachment att : attList) {
	        		f=new File(att.getFilePath());
	        		if(f.exists()){
	        			writer.addFile(new String[]{att.getFilePath(),att.getFileName()});
	        		}	
				}
	        	
	        	//抄送
	        	String cs=mail.getMailBody().getCarbonCopy();
	        	List<TeePerson> list=personDao.getPersonByUuids(cs);
	        	String csNames="";
	        	for (TeePerson teePerson : list) {
	        		csNames+=teePerson.getUserName()+",";
				}
	        	if(csNames.endsWith(",")){
	        		csNames.substring(0, csNames.length()-1);
	        	}
	        	writer.setCc("");

	        	//密送
	        	String ms=mail.getMailBody().getBlindCarbonCopy();
	        	List<TeePerson> list1=personDao.getPersonByUuids(ms);
	        	String msNames="";
	        	for (TeePerson teePerson : list1) {
	        		msNames+=teePerson.getUserName()+",";
				}
	        	if(msNames.endsWith(",")){
	        		msNames.substring(0, msNames.length()-1);
	        	}
	        	writer.setBcc("");
	        	
	        	writer.setCharset("utf-8");
	        	writer.setContent(mail.getMailBody().getContent());
	        	writer.setContentType("text/html");
	        	writer.setDisplayName("");
	        	if(mail.getMailBody().getFromuser()!=null){
	        		writer.setFrom(mail.getMailBody().getFromuser().getUserName());
	        	}else{
	        		writer.setFrom(mail.getMailBody().getFromWebMail());
	        	}
	        	writer.setPort(25);
	        	writer.setSentDate(mail.getMailBody().getSendTime());
	        	writer.setServer("");
	        	writer.setSubject(mail.getMailBody().getSubject());
	        	writer.setTo(mail.getToUser().getUserName());
	        	
	        	
	        	//去除临时文件名称中的非法字符
	        	String name=mail.getMailBody().getSubject()+".eml";
	        	name=name.replace("\\", "");
	        	name=name.replace("/", "");
	        	name=name.replace(":", "");
	        	name=name.replace("*", "");
	        	name=name.replace("?", "");
	        	name=name.replace("\"", "");
	        	name=name.replace(">", "");
	        	name=name.replace("<", "");
	        	name=name.replace("|", "");
	        	
	        	String fileName=TeeSysProps.getTmpPath()+"/"+uuid+"/"+name;
	            file=new File(fileName);
	        	if(!file.getParentFile().exists()){
	        		//创建文件
					file.getParentFile().mkdirs();
//					file.createNewFile();
	        	}
	        	
	        	writer.writeTo(file.getPath());
	        	
	        	
			}
	        
	        
    		//生成zip文件
    		TeeZipUtil.zip(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip", TeeSysProps.getTmpPath()+"/"+uuid);
    		
    		File zipFile = new File(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip");
    			
    		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(nameDesc, "UTF-8")
    				.replace("+", " ")+".zip");
    		response.setHeader("Accept-Ranges", "bytes");
    		response.setHeader("Cache-Control", "maxage=3600");
    		response.setHeader("Pragma", "public");
    		response.setHeader("Accept-Length", String.valueOf(zipFile.length()));
    		response.setHeader("Content-Length", String.valueOf(zipFile.length()));
    		
    		output = response.getOutputStream();
    		
    		zipInputStream = new FileInputStream(new File(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip"));
    		byte b[] = new byte[8192];
    		int length = 0;
			
    		while((length=zipInputStream.read(b))!=-1){
				output.write(b, 0, length);
				output.flush();
			}
    			
			} catch (Exception e) {
				
				e.printStackTrace();
			}finally{
				try {
					output.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					zipInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//将临时文件夹删除
    		TeeFileUtility.deleteAll(TeeSysProps.getTmpPath()+"/"+uuid);
			
		}
	
		
		
		         /**
				 * 发件箱和草稿箱批量导出邮件 
				 * @param mailIds
				 */
				public void exportEmls(String bodyIds,HttpServletResponse response) {
					OutputStream output =null;
					InputStream zipInputStream=null;
					Calendar c = Calendar.getInstance(); 
					String time="["+ c.get(Calendar.YEAR)+(c.get(Calendar.MONTH) + 1)+ c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+"]";
					try {
				    //根据邮件id字符串获取邮件实体集合
					List<TeeMailBody>bodyList=new ArrayList<TeeMailBody>();
					String[]Ids=bodyIds.split(",");
					for (String id : Ids) {
						TeeMailBody body=mailBodyDao.get(Integer.parseInt(id));
						bodyList.add(body);
					}
					
					 //创建临时下载文件夹
		    		String uuid = UUID.randomUUID().toString().replace("-", "");
		    		String nameDesc =time+"邮件";
					
					EmailFileWriter writer=new EmailFileWriter();
					List<TeeAttachment> attList=new ArrayList<TeeAttachment>();
					List<TeeMail> mailList=new ArrayList<TeeMail>();
					String to="";
					File file=null;
			        for (TeeMailBody body : bodyList) {
			        	
			        	//附件
			        	String hql="from TeeAttachment att where att.model='email' and att.modelId="+(body.getSid()+"");
			        	attList=simpleDaoSupport.executeQuery(hql,null);	
			        	File f=null;
			        	for (TeeAttachment att : attList) {
			        		f=new File(att.getFilePath());
			        		if(f.exists()){
			        			writer.addFile(new String[]{att.getFilePath(),att.getFileName()});
			        		}
			        		
						}
			        	
			        	//抄送
			        	String cs=body.getCarbonCopy();
			        	List<TeePerson> list=personDao.getPersonByUuids(cs);
			        	String csNames="";
			        	for (TeePerson teePerson : list) {
			        		csNames+=teePerson.getUserName()+",";
						}
			        	if(csNames.endsWith(",")){
			        		csNames.substring(0, csNames.length()-1);
			        	}
			        	writer.setCc("");

			        	//密送
			        	String ms=body.getBlindCarbonCopy();
			        	List<TeePerson> list1=personDao.getPersonByUuids(ms);
			        	String msNames="";
			        	for (TeePerson teePerson : list1) {
			        		msNames+=teePerson.getUserName()+",";
						}
			        	if(msNames.endsWith(",")){
			        		msNames.substring(0, msNames.length()-1);
			        	}
			        	writer.setBcc("");
			        	
			        	writer.setCharset("utf-8");
			        	writer.setContent(body.getContent());
			        	writer.setContentType("text/html");
			        	writer.setDisplayName("");
			        	writer.setFrom(body.getFromuser().getUserName());
			        	writer.setPort(25);
			        	writer.setSentDate(body.getSendTime());
			        	writer.setServer("");
			        	writer.setSubject(body.getSubject());
			        	
			        	//根据mailboody获取对应的mail集合
			        	String h="from TeeMail mail where mail.mailBody.sid="+body.getSid();
			        	mailList=mailDao.executeQuery(h, null);
			        	if(mailList.size()>0){
			        		to=mailList.get(0).getToUser().getUserName();
			        	}
			        	
			        	writer.setTo(to);
	
			            file=new File(TeeSysProps.getTmpPath()+"/"+uuid+"/"+body.getSubject()+".eml");
			        	if(!file.getParentFile().exists()){
			        		//创建文件
							file.getParentFile().mkdirs();
							//file.createNewFile();
			        	}
			        	writer.writeTo(file.getPath());
						
						
					}
			        
			        
			     
		    		//生成zip文件
		    		TeeZipUtil.zip(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip", TeeSysProps.getTmpPath()+"/"+uuid);
		    		
		    		File zipFile = new File(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip");
		    		
		    		//将临时文件夹删除
		    		TeeFileUtility.deleteAll(TeeSysProps.getTmpPath()+"/"+uuid);
		    		
		    		response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(nameDesc, "UTF-8")
		    				.replace("+", " ")+".zip");
		    		response.setHeader("Accept-Ranges", "bytes");
		    		response.setHeader("Cache-Control", "maxage=3600");
		    		response.setHeader("Pragma", "public");
		    		response.setHeader("Accept-Length", String.valueOf(zipFile.length()));
		    		response.setHeader("Content-Length", String.valueOf(zipFile.length()));
		    		
		    		output = response.getOutputStream();
		    		
		    		zipInputStream = new FileInputStream(new File(TeeSysProps.getTmpPath()+"/"+uuid+"_/"+nameDesc+".zip"));
		    		byte b[] = new byte[8192];
		    		int length = 0;
					
		    		while((length=zipInputStream.read(b))!=-1){
						output.write(b, 0, length);
						output.flush();
					}
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						try {
							output.close();
							zipInputStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			
}
