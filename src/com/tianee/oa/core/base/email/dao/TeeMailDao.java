package com.tianee.oa.core.base.email.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeMailBox;
import com.tianee.oa.core.base.email.bean.TeeMailColor;
import com.tianee.oa.core.base.email.model.TeeMailBoxModel;
import com.tianee.oa.core.base.email.model.TeeMailModel;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class TeeMailDao extends TeeBaseDao<TeeMail>{
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeMailBodyDao bodyDao;
	@Autowired
	TeeMailBoxDao boxDao;
	@Autowired
	TeeAttachmentDao attachmentDao;
	
	/**
	 * 保存或更新 操作
	 * 
	 * @param o
	 */
	public void updateMail(int mailId,int boxId) {
		String hql = "update TeeMail mail set mail.mailBox="+boxId+",mail.deleteFlag = 0 where mail.sid = "+mailId;
		deleteOrUpdateByQuery(hql, null);
	}
	public void deleteMailByBody(TeeMailBody mailBody){
		String hql = "delete from TeeMail mail where mail.mailBody = "+mailBody.getSid();
		deleteOrUpdateByQuery(hql, null);
	}
	
	public List<TeeMail> getMailByBody(TeeMailBody mailBody,int type){
		List<TeeMail> list = new ArrayList<TeeMail>();
		String hql = "select mail from TeeMail mail where mail.mailBody = "+mailBody.getSid()+" and mail.receiveType = "+type;
		list = find(hql, null);
		return list;
	}
	
	public List<TeeMailModel> getListGroup(String selectType,String key,int type,TeePerson person,int mailId,int box,String lookAll,String order,String date,String orderRule,int first,int maxSize){
    	List<Object> values = new ArrayList<Object>();
    	String hql = "";
    	if(box==0){
	    	switch (type) {
			case 0://收信箱
				hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag in (0,2)  and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			case 1://草稿箱
				hql = "select mailBody from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 0";
				break;
			case 2://已发送
				hql = "select mailBody from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mailBody.delFlag = 0";
				break;
			case 3://已删除
				hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag = 1  and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			case 4://收信箱邮件
				hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.sid = "+mailId +" and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			case 5://草稿箱邮件
				hql = "select mailBody from TeeMailBody mailBody where mailBody.sid = "+mailId;
				break;
			case 6://已发送邮件
				hql = "select mailBody from TeeMailBody mailBody where mailBody.sid = "+mailId;
				break;
			case 7://已删除邮件
				hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.sid = "+mailId+" and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			}
    	}else{
    		hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.mailBox = "+type+" and mail.deleteFlag in (0,2)";
    		if(mailId!=0){
    			hql+=" and mail.sid = "+mailId;
    		}
    	}
    	if((Integer.parseInt(lookAll)!=0)&&(type==0||type==3||type==4||type==7||box!=0)){
    		hql += " and mail.readFlag = 0";
    	}
    	if(!date.equals("0")){
    		hql += " and mailBody.sendTime >= '"+date+" 00:00:00' and mailBody.sendTime <= '"+date+" 23:59:59'";
    	}
    	String rule = "asc";
    	if(orderRule.equals("1")){
    		rule = "desc";
    	}
		int n = Integer.parseInt(selectType);
		switch (n) {
		case 0:
			hql += " and mailBody.content like ?";
			values.add("%"+key+"%");
			break;

		case 1:
			hql += " and mailBody.subject like ?";
			values.add("%"+key+"%");
			break;
		case 2:
			hql += " and mailBody.fromuser.userName like ?";
			values.add("%"+key+"%");
			break;
		}
		switch (Integer.parseInt(order)) {
		case 0:
			hql += " order by mailBody.sendTime " + rule;
			break;
		case 1:
			hql += " order by mailBody.nameOrder " + rule;
			break;
		case 2:
			hql += " order by mailBody.subject " + rule;
			break;
		}
   		List<Object> objList = pageModelFind(hql, first, maxSize, values);
   		List<TeeMailBody> bodyList = bodyDao.pageFindByList(hql, first, maxSize, values);
   		List<TeeMailModel> modelList = new ArrayList<TeeMailModel>();
   		if(type==0||type==3||type==4||type==7||box!=0){
	   		for(int i = 0;i<objList.size();i++){
	   			TeeMailModel model = new TeeMailModel();
	   			Object[] obj = (Object[]) objList.get(i);
	   			TeeMail mail = (TeeMail) obj[0];
	   			if(type==4||type==7||(box!=0&&mailId!=0)){
		   			mail.setReadFlag(1);
		   			saveOrUpdate(mail);
	   			}
	   			TeeMailBody mailBody = (TeeMailBody)obj[1];
	   			List<TeeMail> list1 = null;
	   			List<TeeMail> list2 = null;
	   			List<TeeMail> list3 = null;
	   			if(box!=1){
		   			list1 = getMailByBody(mailBody, 0);
		   			list2 = getMailByBody(mailBody, 1);
		   			list3 = getMailByBody(mailBody, 2);
	   			}else{
	   				list1 = getMailByBody(mail.getMailBody(), 0);
		   			list2 = getMailByBody(mail.getMailBody(), 1);
		   			list3 = getMailByBody(mail.getMailBody(), 2);
	   			}
	   			String userListIds = "";
	   			String userListNames = "";
	   			String copyUserListIds = "";
	   			String copyUserListNames = "";
	   			String secretUserListIds = "";
	   			String secretUserListNames = "";
	   			List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
	   			List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
	   			List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
	   			for(TeeMail mail1 : list1){
	   				userListIds = mail1.getToUser().getUuid()+",";
	   				userListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				users.add(s);
	   			}
	   			for(TeeMail mail1 : list2){
	   				copyUserListIds = mail1.getToUser().getUuid()+",";
	   				copyUserListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				copyUsers.add(s);
	   			}
	   			for(TeeMail mail1 : list3){
	   				secretUserListIds = mail1.getToUser().getUuid()+",";
	   				secretUserListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				secretUsers.add(s);
	   			}
	   			String content = mailBody.getContent();
	  	        content = content.replaceAll("\'", "\\\\'");
		        content = content.replaceAll("\n", "");
		        content = content.replaceAll("\\\n", "");
		        content = content.replaceAll("\r", "");
		        content = content.replaceAll("\t","");
		        content = content.replaceAll("[\n-\r]", "");
		        //content = content.replaceAll("\"", "\\\\\"");
			    String fromWebmail = mailBody.getFromWebMail();
			    if(!TeeUtility.isNullorEmpty(fromWebmail)){
			    	fromWebmail = fromWebmail.replaceAll("<", "&lt");
				    fromWebmail = fromWebmail.replaceAll(">", "&gt");
			    }
			    String toWebMail = mailBody.getToWebMail();
			    if(!TeeUtility.isNullorEmpty(toWebMail)){
			    	toWebMail = toWebMail.replaceAll("<", "&lt");
				    toWebMail = toWebMail.replaceAll(">", "&gt");
			    }
	   			model.setCcWebMail(mailBody.getCcWebMail());
	   			model.setIfWebMail(mailBody.getIfWebMail());
	   			model.setIsHtml(mailBody.getIsHtml());
	   			model.setLargeAttachment(mailBody.getLargeAttachment());
	   			model.setToWebMail(toWebMail);
	   			model.setFromWebMail(fromWebmail);
	   			model.setWebMailUid(mailBody.getWebMailUid());
	   			model.setWebMailId(mailBody.getWebMailId());
	   			model.setUsers(users);
	   			model.setIfBody(0);
	   			model.setSecretUsers(secretUsers);
	   			model.setCopyUsers(copyUsers);
	   			model.setUserListIds(userListIds);
	   			model.setCopyUserListNames(copyUserListNames);
	   			model.setUserListNames(userListNames);
	   			model.setCopyUserListIds(copyUserListIds);
	   			model.setSecretUserListIds(secretUserListIds);
	   			model.setSecretUserListNames(secretUserListNames);
	   			model.setContent(content);
	   			model.setDeleteFlag(mail.getDeleteFlag());
	   			model.setFromId(mailBody.getFromuser());
	   			model.setImportant(mailBody.getImportant());
	   			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
	   			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
	   			for(TeeAttachment attach:attaches){
	   				TeeAttachmentModel m = new TeeAttachmentModel();
	   				BeanUtils.copyProperties(attach, m);
	   				m.setUserId(attach.getUser().getUuid()+"");
	   				m.setUserName(attach.getUser().getUserName());
	   				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
	   				attachmodels.add(m);
	   			}
	   			model.setMailAttachMentModel(attachmodels);
	   			model.setMailBody(mailBody);
	   			model.setMailBox(mail.getMailBox());
	   			model.setReadFlag(mail.getReadFlag());
	   			model.setReceipt(mail.getReceipt());
	   			model.setToWebmail(mailBody.getToWebmail());
	   			model.setWebmailCount(mailBody.getWebmailCount());
	   			model.setWebmailHtml(mailBody.getWebmailHtml());
	   			model.setReceiveType(mail.getReceiveType());
	   			model.setSendFlag(mailBody.getSendFlag());
	   			model.setSendTime(mailBody.getSendTime());
	   			model.setSubject(mailBody.getSubject());
	   			model.setToId(mail.getToUser());
	   			model.setSmsRemind(mailBody.getSmsRemind());
	   			model.setSid(mail.getSid());
	   			modelList.add(model);
	   		}
   		}else{
   			for(int i = 0;i<bodyList.size();i++){
	   			TeeMailModel model = new TeeMailModel();
	   			TeeMailBody mailBody = bodyList.get(i);
	   			String content = mailBody.getContent();
	  	        content = content.replaceAll("\'", "\\\\'");
		        content = content.replaceAll("\n", "");
		        content = content.replaceAll("\\\n", "");
		        content = content.replaceAll("\r", "");
		        content = content.replaceAll("\t","");
		        content = content.replaceAll("[\n-\r]", "");
		        //content = content.replaceAll("\"", "\\\"");
	   			model.setContent(content);
	   			model.setIfBody(1);
	   			model.setSubject(mailBody.getSubject());
	   			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
	   			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
	   			for(TeeAttachment attach:attaches){
	   				TeeAttachmentModel m = new TeeAttachmentModel();
	   				BeanUtils.copyProperties(attach, m);
	   				m.setUserId(attach.getUser().getUuid()+"");
	   				m.setUserName(attach.getUser().getUserName());
	   				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
	   				attachmodels.add(m);
	   			}
	   			model.setMailAttachMentModel(attachmodels);
	   			model.setSid(mailBody.getSid());
	   			model.setSendTime(mailBody.getSendTime());
	   			model.setSmsRemind(mailBody.getSmsRemind());
	   			model.setFromId(mailBody.getFromuser());
	   			model.setFromWebMail(mailBody.getFromWebMail());
	   			model.setToWebmail(mailBody.getToWebmail());
	   			List<TeeMail> list1 = getMailByBody(mailBody, 0);
	   			List<TeeMail> list2 = getMailByBody(mailBody, 1);
	   			List<TeeMail> list3 = getMailByBody(mailBody, 2);
	   			String userListIds = "";
	   			String userListNames = "";
	   			String copyUserListIds = "";
	   			String copyUserListNames = "";
	   			String secretUserListIds = "";
	   			String secretUserListNames = "";
	   			List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
	   			List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
	   			List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
	   			for(TeeMail mail1 : list1){
	   				userListIds = mail1.getToUser().getUuid()+",";
	   				userListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				users.add(s);
	   			}
	   			for(TeeMail mail1 : list2){
	   				copyUserListIds = mail1.getToUser().getUuid()+",";
	   				copyUserListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				copyUsers.add(s);
	   			}
	   			for(TeeMail mail1 : list3){
	   				secretUserListIds = mail1.getToUser().getUuid()+",";
	   				secretUserListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				secretUsers.add(s);
	   			}
			    String fromWebmail = mailBody.getFromWebMail();
			    if(!TeeUtility.isNullorEmpty(fromWebmail)){
				    fromWebmail = fromWebmail.replaceAll("<", "&lt");
				    fromWebmail = fromWebmail.replaceAll(">", "&gt");
			    }
			    String toWebMail = mailBody.getToWebMail();
			    if(!TeeUtility.isNullorEmpty(toWebMail)){
			    	toWebMail = toWebMail.replaceAll("<", "&lt");
			    	toWebMail = toWebMail.replaceAll(">", "&gt");
			    }
	   			model.setCcWebMail(mailBody.getCcWebMail());
	   			model.setIfWebMail(mailBody.getIfWebMail());
	   			model.setIsHtml(mailBody.getIsHtml());
	   			model.setLargeAttachment(mailBody.getLargeAttachment());
	   			model.setToWebMail(toWebMail);
	   			model.setFromWebMail(fromWebmail);
	   			model.setWebMailUid(mailBody.getWebMailUid());
	   			model.setWebMailId(mailBody.getWebMailId());
	   			model.setUsers(users);
	   			model.setSecretUsers(secretUsers);
	   			model.setToWebmail(mailBody.getToWebmail());
	   			model.setWebmailCount(mailBody.getWebmailCount());
	   			model.setWebmailHtml(mailBody.getWebmailHtml());
	   			model.setCopyUsers(copyUsers);
	   			model.setUserListIds(userListIds);
	   			model.setCopyUserListNames(copyUserListNames);
	   			model.setUserListNames(userListNames);
	   			model.setCopyUserListIds(copyUserListIds);
	   			model.setSecretUserListIds(secretUserListIds);
	   			model.setSecretUserListNames(secretUserListNames);
	   			modelList.add(model);
   			}
   		}
   		return modelList;
	}
	
	public int getListGroupCount(String selectType,String key,int type,TeePerson person,int mailId,int box,String lookAll,String order,String date){
    	int count = 0;
    	List<Object> values = new ArrayList<Object>();
    	String hql = "";
    	if(box==0){
	    	switch (type) {
			case 0://收信箱
				hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag in (0,2)  and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			case 1://草稿箱
				hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 0";
				break;
			case 2://已发送
				hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mailBody.delFlag = 0";
				break;
			case 3://已删除
				hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag = 1  and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			case 4://收信箱邮件
				hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.sid = "+mailId +" and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			case 5://草稿箱邮件
				hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.sid = "+mailId;
				break;
			case 6://已发送邮件
				hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.sid = "+mailId;
				break;
			case 7://已删除邮件
				hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.sid = "+mailId+" and (mail.mailBox is null or mail.mailBox.sid = 0)";
				break;
			}
    	}else{
    		hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.mailBox = "+type+" and mail.deleteFlag in (0,2)";
    		if(mailId!=0){
    			hql+=" and mail.sid = "+mailId;
    		}
    	}
		int n = Integer.parseInt(selectType);
		switch (n) {
		case 0:
			hql += " and mailBody.content like ?";
			values.add("%"+key+"%");
			break;

		case 1:
			hql += " and mailBody.subject like ?";
			values.add("%"+key+"%");
			break;
		case 2:
			hql += " and mailBody.fromuser.userName like ?";
			values.add("%"+key+"%");
			break;
		}
    	if((Integer.parseInt(lookAll)!=0)&&(type==0||type==3||type==4||type==7||box!=0)){
    		hql += " and mail.readFlag = 0";
    	}
    	if(!date.equals("0")){
    		hql += " and mailBody.sendTime >= '"+date+" 00:00:00' and mailBody.sendTime <= '"+date+" 23:59:59'";
    	}
   		count = new Long(countByList(hql, values)).intValue();
   		return count;
	}
	
	public long getListCount(int type,TeePerson person,int boxId){
		long count = 0;
    	String hql = "";
    	switch (type) {
		case 0://收信箱数量
			hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag in (0) and (mail.mailBox is null or mail.mailBox.sid = 0)";
			break;
		case 1://草稿箱数量
			hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 0";
			break;
		case 2://已发送数量
			hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mailBody.delFlag = 0";
			break;
		case 3://已删除数量
			hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag = 1  and (mail.mailBox is null or mail.mailBox.sid = 0)";
			break;
		default://分类箱中的邮件数量
			hql = "select count(mail.sid) from TeeMail mail where mail.mailBox = "+boxId+ " and mail.deleteFlag in (0)";
			break;
		}

    	count = countByList(hql,null);

   		return count;
	}
	
	 /**
	 * page TeeDataGridModel.page 当前页
	 * rows TeeDataGridModel.rows 每页显示记录数
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List<Object> getList(String hql, List<Object> param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		//List<Object> list = new ArrayList<Object>();
		return q.list();
	}
	
	public List<TeeMailBoxModel> getBoxList(TeePerson person,int boxId){
    	String hql = "";
    	if(boxId==0){
    		hql = "select mailBox from TeeMailBox mailBox where mailBox.userManager = '"+person.getUuid()+"' ";
    	}else{
    		hql = "select mailBox from TeeMailBox mailBox where mailBox.userManager = '"+person.getUuid()+"' and mailBox.sid="+boxId;
    	}
   		List<TeeMailBox> boxList = boxDao.find(hql, null);
   		List<TeeMailBoxModel> modelList = new ArrayList<TeeMailBoxModel>();
   			
   			for(int i = 0;i<boxList.size();i++){
   				TeeMailBoxModel model = new TeeMailBoxModel();
	   			TeeMailBox mailBox = boxList.get(i);
	   			model.setBoxName(mailBox.getBoxName());
	   			model.setBoxNo(mailBox.getBoxNo());
	   			model.setSid(mailBox.getSid());
	   			model.setDefaultCount(mailBox.getDefaultCount());
	   			model.setMailCount(getListCount(5,person,mailBox.getSid()));
	   			model.setUserManager(person);
	   			modelList.add(model);
   			}
   		
   		return modelList;
	}
	
	public List<TeeMailModel> selectMail(String types,String ifBox,String selectType,TeePerson person,String key,String lookAll,String order,String date,String orderRule,int first,int maxSize){
		List<Object> values = new ArrayList<Object>();
		List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		String hql= "";
		int box = Integer.parseInt(ifBox);
		int type = Integer.parseInt(types);
		if(box==0){
	    	switch (type) {
			case 0://收信箱
				hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag in (0,2)  and (mail.mailBox is null or mail.mailBox.sid = 0) ";
				break;
			case 1://草稿箱
				hql = "select mailBody from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 0 ";
				break;
			case 2://已发送
				hql = "select mailBody from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mailBody.delFlag = 0 ";
				break;
			case 3://已删除
				hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag = 1  and (mail.mailBox is null or mail.mailBox.sid = 0) ";
				break;
	    	}
		}else{
			hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.mailBox = "+type+" and mail.deleteFlag in (0,2)";
		}
		//hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"'  and mailBody.sendFlag = 1";
		int n = Integer.parseInt(selectType);
		switch (n) {
		case 0:
			hql += " and mailBody.content like '%"+key+"%'";
			break;

		case 1:
			hql += " and mailBody.subject like '%"+key+"%'";
			break;
		case 2:
			hql += " and mailBody.fromuser.userName like '%"+key+"%'";
			break;
		}
    	if(Integer.parseInt(lookAll)!=0){
    		hql += " and mail.readFlag = 0";
    	}
    	if(!date.equals("0")){
    		hql += " and mailBody.sendTime >= '"+date+" 00:00:00' and mailBody.sendTime <= '"+date+" 23:59:59'";
    	}
    	String rule = "asc";
    	if(orderRule.equals("1")){
    		rule = "desc";
    	}
		switch (Integer.parseInt(order)) {
		case 0:
			hql += " order by mailBody.sendTime " + rule;
			break;
		case 1:
			hql += " order by mailBody.nameOrder " + rule;
			break;
		case 2:
			hql += " order by mailBody.subject " + rule;
			break;
		}
		List<Object> objList = pageModelFind(hql, first, maxSize, null);
   		for(int i = 0;i<objList.size();i++){
   			TeeMailModel model = new TeeMailModel();
   			Object[] obj = (Object[]) objList.get(i);
   			TeeMailBody mailBody = (TeeMailBody)obj[1];
   			TeeMail mail = (TeeMail) obj[0];
   			List<TeeMail> list1 = null;
   			List<TeeMail> list2 = null;
   			List<TeeMail> list3 = null;
   			list1 = getMailByBody(mailBody, 0);
   			list2 = getMailByBody(mailBody, 1);
   			list3 = getMailByBody(mailBody, 2);
   			String userListIds = "";
   			String userListNames = "";
   			String copyUserListIds = "";
   			String copyUserListNames = "";
   			String secretUserListIds = "";
   			String secretUserListNames = "";
   			List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
   			List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
   			List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
   			for(TeeMail mail1 : list1){
   				userListIds = mail1.getToUser().getUuid()+",";
   				userListNames = mail1.getToUser().getUserName()+",";
   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
   				users.add(s);
   			}
   			for(TeeMail mail1 : list2){
   				copyUserListIds = mail1.getToUser().getUuid()+",";
   				copyUserListNames = mail1.getToUser().getUserName()+",";
   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
   				copyUsers.add(s);
   			}
   			for(TeeMail mail1 : list3){
   				secretUserListIds = mail1.getToUser().getUuid()+",";
   				secretUserListNames = mail1.getToUser().getUserName()+",";
   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
   				secretUsers.add(s);
   			}
		    String fromWebmail = mailBody.getFromWebMail();
		    if(!TeeUtility.isNullorEmpty(fromWebmail)){
		    	fromWebmail = fromWebmail.replaceAll("<", "&lt");
			    fromWebmail = fromWebmail.replaceAll(">", "&gt");
		    }
		    String toWebMail = mailBody.getToWebMail();
		    if(!TeeUtility.isNullorEmpty(toWebMail)){
		    	toWebMail = toWebMail.replaceAll("<", "&lt");
			    toWebMail = toWebMail.replaceAll(">", "&gt");
		    }
   			model.setCcWebMail(mailBody.getCcWebMail());
   			model.setIfWebMail(mailBody.getIfWebMail());
   			model.setIsHtml(mailBody.getIsHtml());
   			model.setLargeAttachment(mailBody.getLargeAttachment());
   			model.setToWebMail(toWebMail);
   			model.setFromWebMail(fromWebmail);
   			model.setWebMailUid(mailBody.getWebMailUid());
   			model.setWebMailId(mailBody.getWebMailId());
   			model.setUsers(users);
   			model.setIfBody(0);
   			model.setSecretUsers(secretUsers);
   			model.setCopyUsers(copyUsers);
   			model.setUserListIds(userListIds);
   			model.setCopyUserListNames(copyUserListNames);
   			model.setUserListNames(userListNames);
   			model.setCopyUserListIds(copyUserListIds);
   			model.setSecretUserListIds(secretUserListIds);
   			model.setSecretUserListNames(secretUserListNames);
   			String content = mailBody.getContent();
  	        content = content.replaceAll("\'", "\\\\'");
	        content = content.replaceAll("\n", "");
	        content = content.replaceAll("\\\n", "");
	        content = content.replaceAll("\r", "");
	        content = content.replaceAll("\t","");
	        content = content.replaceAll("[\n-\r]", "");
   			model.setContent(content);
   			model.setDeleteFlag(mail.getDeleteFlag());
   			model.setFromId(mailBody.getFromuser());
   			model.setImportant(mailBody.getImportant());
   			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
   			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
   			for(TeeAttachment attach:attaches){
   				TeeAttachmentModel m = new TeeAttachmentModel();
   				BeanUtils.copyProperties(attach, m);
   				m.setUserId(attach.getUser().getUuid()+"");
   				m.setUserName(attach.getUser().getUserName());
   				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
   				attachmodels.add(m);
   			}
   			model.setMailAttachMentModel(attachmodels);
   			model.setMailBody(mailBody);
   			model.setMailBox(mail.getMailBox());
   			model.setReadFlag(mail.getReadFlag());
   			model.setReceipt(mail.getReceipt());
   			model.setReceiveType(mail.getReceiveType());
   			model.setSendFlag(mailBody.getSendFlag());
   			model.setSendTime(mailBody.getSendTime());
   			model.setSubject(mailBody.getSubject());
   			model.setToId(mail.getToUser());
   			model.setSmsRemind(mailBody.getSmsRemind());
   			model.setSid(mail.getSid());
   			model.setToWebmail(mailBody.getToWebmail());
   			model.setWebmailCount(mailBody.getWebmailCount());
   			model.setWebmailHtml(mailBody.getWebmailHtml());
   			list.add(model);
   		
    	}
		return list;
			
			
	}
		
		public List<TeeMailModel> selectMailById(String ifBody,TeePerson person,String id){
			List<TeeMailModel> list = new ArrayList<TeeMailModel>();
			List<Object> values = new ArrayList<Object>();
			String hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.sid = "+id ;
			String hql1 = "select mailBody from TeeMailBody mailBody where mailBody.sid = "+id;
			List<Object> objList1 = getList(hql, values);
			List<Object> objList2 = getList(hql1, values);
			if(ifBody.equals("0")){
				for(int i = 0;i<objList1.size();i++){
		   			TeeMailModel model = new TeeMailModel();
		   			Object[] obj = (Object[]) objList1.get(i);
		   			TeeMailBody mailBody = (TeeMailBody)obj[1];
		   			TeeMail mail = (TeeMail) obj[0];
		   			List<TeeMail> list1 = null;
		   			List<TeeMail> list2 = null;
		   			List<TeeMail> list3 = null;
		   			list1 = getMailByBody(mailBody, 0);
		   			list2 = getMailByBody(mailBody, 1);
		   			list3 = getMailByBody(mailBody, 2);
		   			String userListIds = "";
		   			String userListNames = "";
		   			String copyUserListIds = "";
		   			String copyUserListNames = "";
		   			String secretUserListIds = "";
		   			String secretUserListNames = "";
		   			List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
		   			List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
		   			List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
		   			for(TeeMail mail1 : list1){
		   				userListIds = mail1.getToUser().getUuid()+",";
		   				userListNames = mail1.getToUser().getUserName()+",";
		   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
		   				users.add(s);
		   			}
		   			for(TeeMail mail1 : list2){
		   				copyUserListIds = mail1.getToUser().getUuid()+",";
		   				copyUserListNames = mail1.getToUser().getUserName()+",";
		   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
		   				copyUsers.add(s);
		   			}
		   			for(TeeMail mail1 : list3){
		   				secretUserListIds = mail1.getToUser().getUuid()+",";
		   				secretUserListNames = mail1.getToUser().getUserName()+",";
		   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
		   				secretUsers.add(s);
		   			}
				    String fromWebmail = mailBody.getFromWebMail();
				    if(!TeeUtility.isNullorEmpty(fromWebmail)){
				    	fromWebmail = fromWebmail.replaceAll("<", "&lt");
					    fromWebmail = fromWebmail.replaceAll(">", "&gt");
				    }
				    String toWebMail = mailBody.getToWebMail();
				    if(!TeeUtility.isNullorEmpty(toWebMail)){
				    	toWebMail = toWebMail.replaceAll("<", "&lt");
					    toWebMail = toWebMail.replaceAll(">", "&gt");
				    }
		   			model.setCcWebMail(mailBody.getCcWebMail());
		   			model.setIfWebMail(mailBody.getIfWebMail());
		   			model.setIsHtml(mailBody.getIsHtml());
		   			model.setLargeAttachment(mailBody.getLargeAttachment());
		   			model.setToWebMail(toWebMail);
		   			model.setFromWebMail(fromWebmail);
		   			model.setWebMailUid(mailBody.getWebMailUid());
		   			model.setWebMailId(mailBody.getWebMailId());
		   			model.setUsers(users);
		   			model.setIfBody(0);
		   			model.setSecretUsers(secretUsers);
		   			model.setCopyUsers(copyUsers);
		   			model.setUserListIds(userListIds);
		   			model.setCopyUserListNames(copyUserListNames);
		   			model.setUserListNames(userListNames);
		   			model.setCopyUserListIds(copyUserListIds);
		   			model.setSecretUserListIds(secretUserListIds);
		   			model.setSecretUserListNames(secretUserListNames);
		   			String content = mailBody.getContent();
		  	        content = content.replaceAll("\'", "\\\\'");
			        content = content.replaceAll("\n", "");
			        content = content.replaceAll("\\\n", "");
			        content = content.replaceAll("\r", "");
			        content = content.replaceAll("\t","");
			        content = content.replaceAll("[\n-\r]", "");
		   			model.setContent(content);
		   			model.setDeleteFlag(mail.getDeleteFlag());
		   			model.setFromId(mailBody.getFromuser());
		   			model.setImportant(mailBody.getImportant());
		   			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
		   			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		   			for(TeeAttachment attach:attaches){
		   				TeeAttachmentModel m = new TeeAttachmentModel();
		   				BeanUtils.copyProperties(attach, m);
		   				m.setUserId(attach.getUser().getUuid()+"");
		   				m.setUserName(attach.getUser().getUserName());
		   				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
		   				attachmodels.add(m);
		   			}
		   			model.setMailAttachMentModel(attachmodels);
		   			model.setMailBody(mailBody);
		   			model.setMailBox(mail.getMailBox());
		   			model.setReadFlag(mail.getReadFlag());
		   			model.setReceipt(mail.getReceipt());
		   			model.setReceiveType(mail.getReceiveType());
		   			model.setSendFlag(mailBody.getSendFlag());
		   			model.setSendTime(mailBody.getSendTime());
		   			model.setSubject(mailBody.getSubject());
		   			model.setToId(mail.getToUser());
		   			model.setSmsRemind(mailBody.getSmsRemind());
		   			model.setSid(mail.getSid());
		   			model.setToWebmail(mailBody.getToWebmail());
		   			model.setWebmailCount(mailBody.getWebmailCount());
		   			model.setWebmailHtml(mailBody.getWebmailHtml());
		   			list.add(model);
		   		}
			}else{
				for(int j = 0;j<objList2.size();j++){
		   			TeeMailModel model = new TeeMailModel();
		   			TeeMailBody mailBody = (TeeMailBody) objList2.get(j);
		   			String content = mailBody.getContent();
		  	        content = content.replaceAll("\'", "\\\\'");
			        content = content.replaceAll("\n", "");
			        content = content.replaceAll("\\\n", "");
			        content = content.replaceAll("\r", "");
			        content = content.replaceAll("\t","");
			        content = content.replaceAll("[\n-\r]", "");
		   			model.setContent(content);
		   			model.setIfBody(1);
		   			model.setSubject(mailBody.getSubject());
		   			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailBody.getSid()));
		   			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		   			for(TeeAttachment attach:attaches){
		   				TeeAttachmentModel m = new TeeAttachmentModel();
		   				BeanUtils.copyProperties(attach, m);
		   				m.setUserId(attach.getUser().getUuid()+"");
		   				m.setUserName(attach.getUser().getUserName());
		   				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
		   				attachmodels.add(m);
		   			}
		   			model.setMailAttachMentModel(attachmodels);
		   			model.setSid(mailBody.getSid());
		   			model.setSendTime(mailBody.getSendTime());
		   			model.setSmsRemind(mailBody.getSmsRemind());
		   			model.setFromId(mailBody.getFromuser());
		   			model.setFromWebMail(mailBody.getFromWebMail());
		   			model.setToWebmail(mailBody.getToWebmail());
		   			List<TeeMail> list1 = getMailByBody(mailBody, 0);
		   			List<TeeMail> list2 = getMailByBody(mailBody, 1);
		   			List<TeeMail> list3 = getMailByBody(mailBody, 2);
		   			String userListIds = "";
		   			String userListNames = "";
		   			String copyUserListIds = "";
		   			String copyUserListNames = "";
		   			String secretUserListIds = "";
		   			String secretUserListNames = "";
		   			List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
		   			List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
		   			List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
		   			for(TeeMail mail1 : list1){
		   				userListIds = mail1.getToUser().getUuid()+",";
		   				userListNames = mail1.getToUser().getUserName()+",";
		   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
		   				users.add(s);
		   			}
		   			for(TeeMail mail1 : list2){
		   				copyUserListIds = mail1.getToUser().getUuid()+",";
		   				copyUserListNames = mail1.getToUser().getUserName()+",";
		   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
		   				copyUsers.add(s);
		   			}
		   			for(TeeMail mail1 : list3){
		   				secretUserListIds = mail1.getToUser().getUuid()+",";
		   				secretUserListNames = mail1.getToUser().getUserName()+",";
		   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
		   				secretUsers.add(s);
		   			}
				    String fromWebmail = mailBody.getFromWebMail();
				    if(!TeeUtility.isNullorEmpty(fromWebmail)){
				    	fromWebmail = fromWebmail.replaceAll("<", "&lt");
					    fromWebmail = fromWebmail.replaceAll(">", "&gt");
				    }
				    String toWebMail = mailBody.getToWebMail();
				    if(!TeeUtility.isNullorEmpty(toWebMail)){
				    	toWebMail = toWebMail.replaceAll("<", "&lt");
					    toWebMail = toWebMail.replaceAll(">", "&gt");
				    }
		   			model.setCcWebMail(mailBody.getCcWebMail());
		   			model.setIfWebMail(mailBody.getIfWebMail());
		   			model.setIsHtml(mailBody.getIsHtml());
		   			model.setLargeAttachment(mailBody.getLargeAttachment());
		   			model.setToWebMail(toWebMail);
		   			model.setFromWebMail(fromWebmail);
		   			model.setWebMailUid(mailBody.getWebMailUid());
		   			model.setWebMailId(mailBody.getWebMailId());
		   			model.setUsers(users);
		   			model.setSecretUsers(secretUsers);
		   			model.setCopyUsers(copyUsers);
		   			model.setUserListIds(userListIds);
		   			model.setCopyUserListNames(copyUserListNames);
		   			model.setUserListNames(userListNames);
		   			model.setCopyUserListIds(copyUserListIds);
		   			model.setSecretUserListIds(secretUserListIds);
		   			model.setSecretUserListNames(secretUserListNames);
		   			model.setToWebmail(mailBody.getToWebmail());
		   			model.setWebmailCount(mailBody.getWebmailCount());
		   			model.setWebmailHtml(mailBody.getWebmailHtml());
		   			list.add(model);
				}
			}
		return list;
	}
		/**
		 * 分页
		 * 
		 * @param hql
		 *            : hql语句
		 * @param firstResult
		 *            : 开始索引
		 * @param pageSize
		 *            : 去多少记录
		 * @param param
		 *            : 参数
		 * @return
		 */
		public List<Object> pageModelFind(final String hql, int firstResult, int pageSize,
				List list) {
			Session session = this.getSession();
			List<Object> result = null;
			Query query = session.createQuery(hql);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					query.setParameter(i, list.get(i));
				}
			}
			query.setFirstResult(firstResult);
			query.setMaxResults(pageSize);
			result = query.list();
			return result;

		}
		
		public int selectMailCount(String types,String ifBox,String selectType,TeePerson person,String key,String lookAll,String order,String date){
			List<Object> values = new ArrayList<Object>();
			List<TeeMailModel> list = new ArrayList<TeeMailModel>();
			int count = 0;
			String hql= "";
			int box = Integer.parseInt(ifBox);
			int type = Integer.parseInt(types);
			if(box==0){
		    	switch (type) {
				case 0://收信箱
					hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag in (0,2)  and (mail.mailBox is null or mail.mailBox.sid = 0) ";
					break;
				case 1://草稿箱
					hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 0 ";
					break;
				case 2://已发送
					hql = "select count(mailBody.sid) from TeeMailBody mailBody where mailBody.fromuser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mailBody.delFlag = 0 ";
					break;
				case 3://已删除
					hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag = 1  and (mail.mailBox is null or mail.mailBox.sid = 0) ";
					break;
		    	}
			}else{
				hql = "select count(mail.sid) from TeeMail mail left join mail.mailBody mailBody where mail.mailBox = "+type+" and mail.deleteFlag in (0,2)";
			}
			int n = Integer.parseInt(selectType);
			switch (n) {
			case 0:
				hql += " and mailBody.content like '%"+key+"%'";
				break;

			case 1:
				hql += " and mailBody.subject like '%"+key+"%'";
				break;
			case 2:
				hql += " and mailBody.fromuser.userName like '%"+key+"%'";
				break;
			}
	    	if(Integer.parseInt(lookAll)!=0){
	    		hql += " and mail.readFlag = 0";
	    	}
	    	if(!date.equals("0")){
	    		hql += " and mailBody.sendTime >= '"+date+" 00:00:00' and mailBody.sendTime <= '"+date+" 23:59:59'";
	    	}
	    	count = new Long(count(hql, null)).intValue();
			return count;
		}
		
		public List<TeeMailModel> selectMailForPortlet(TeePerson person,int type,int maxSize){
	    	String hql = "";
			hql = "select mail,mailBody from TeeMail mail left join mail.mailBody mailBody where mail.toUser = '"+person.getUuid()+"' and mailBody.sendFlag = 1 and mail.deleteFlag in (0,2)  and (mail.mailBox is null or mail.mailBox.sid = 0)";
			if(type==1){
				hql += " and mail.readFlag = 0";
			}
			hql += " order by mailBody.sendTime desc";
	   		List<Object> objList = pageModelFind(hql, 0, maxSize, null);
	   		List<TeeMailModel> modelList = new ArrayList<TeeMailModel>();
	   		for(int i = 0;i<objList.size();i++){
	   			TeeMailModel model = new TeeMailModel();
	   			Object[] obj = (Object[]) objList.get(i);
	   			TeeMail mail = (TeeMail) obj[0];
	   			TeeMailBody mailBody = (TeeMailBody)obj[1];
	   			List<TeeMail> list1 = null;
	   			List<TeeMail> list2 = null;
	   			List<TeeMail> list3 = null;
   				list1 = getMailByBody(mail.getMailBody(), 0);
	   			list2 = getMailByBody(mail.getMailBody(), 1);
	   			list3 = getMailByBody(mail.getMailBody(), 2);
	   			String userListIds = "";
	   			String userListNames = "";
	   			String copyUserListIds = "";
	   			String copyUserListNames = "";
	   			String secretUserListIds = "";
	   			String secretUserListNames = "";
	   			List<String[]> users = new ArrayList<String[]>();//id,name,readFlag
	   			List<String[]> copyUsers = new ArrayList<String[]>();//id,name,readFlag
	   			List<String[]> secretUsers = new ArrayList<String[]>();//id,name,readFlag
	   			for(TeeMail mail1 : list1){
	   				userListIds = mail1.getToUser().getUuid()+",";
	   				userListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				users.add(s);
	   			}
	   			for(TeeMail mail1 : list2){
	   				copyUserListIds = mail1.getToUser().getUuid()+",";
	   				copyUserListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				copyUsers.add(s);
	   			}
	   			for(TeeMail mail1 : list3){
	   				secretUserListIds = mail1.getToUser().getUuid()+",";
	   				secretUserListNames = mail1.getToUser().getUserName()+",";
	   				String[] s = new String[] {mail1.getToUser().getUuid() + "",mail1.getToUser().getUserName() + "",mail1.getReadFlag() + ""}; 
	   				secretUsers.add(s);
	   			}
				//model.setUsers(users);
	   			model.setIfBody(0);
	   			model.setSecretUsers(secretUsers);
	   			//model.setCopyUsers(copyUsers);
	   			//model.setUserListIds(mailBody.getFromuser().getUuid() + "");//代替formId 发送Id
	   			//model.setCopyUserListNames(copyUserListNames);
	   			//model.setUserListNames(userListNames);
	   			//model.setCopyUserListIds(copyUserListIds);
	   			//model.setSecretUserListIds(secretUserListIds);
	   			//model.setSecretUserListNames(secretUserListNames);
	   			String content = mailBody.getContent();
	  	        content = content.replaceAll("\'", "\\\\'");
		        content = content.replaceAll("\n", "");
		        content = content.replaceAll("\\\n", "");
		        content = content.replaceAll("\r", "");
		        content = content.replaceAll("\t","");
		        content = content.replaceAll("[\n-\r]", "");
	   			model.setContent(content);
	   			model.setDeleteFlag(mail.getDeleteFlag());
	   		/*	model.setFromId(mailBody.getFromuser());*/
	   			model.setImportant(mailBody.getImportant());
	   			/*model.setMailAttachMent(mailBody.getMailAttachMent());
	   			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
	   			for(TeeAttachment attach:mailBody.getMailAttachMent()){
	   				TeeAttachmentModel m = new TeeAttachmentModel();
	   				BeanUtils.copyProperties(attach, m);
	   				m.setUserId(attach.getUser().getUuid()+"");
	   				m.setUserName(attach.getUser().getUserName());
	   				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
	   				attachmodels.add(m);
	   			}
	   			model.setMailAttachMentModel(attachmodels);*/
	   			/*model.setMailBody(mailBody);*/
	   			model.setMailBox(mail.getMailBox());
	   			model.setReadFlag(mail.getReadFlag());
	   			model.setReceipt(mail.getReceipt());
	   			model.setToWebmail(mailBody.getToWebmail());
	   			model.setWebmailCount(mailBody.getWebmailCount());
	   			model.setWebmailHtml(mailBody.getWebmailHtml());
	   			model.setReceiveType(mail.getReceiveType());
	   			model.setSendFlag(mailBody.getSendFlag());
	   			model.setSendTime(mailBody.getSendTime());
	   			model.setSubject(mailBody.getSubject());
	   			//model.setToId(mail.getToUser());
	   			model.setSmsRemind(mailBody.getSmsRemind());
	   			model.setSid(mail.getSid());
	   			model.setFormIdStr(mailBody.getFromuser().getUuid() +"");
	   			model.setFormName(mailBody.getFromuser().getUserName());
	   			modelList.add(model);
	   		}
	   		return modelList;
		}
		
		//为首页查询
		public List<TeeMailModel> selectMailForQueryIndex(TeePerson person,String key,int maxSize){
			String hql = "from TeeMail mail where mail.toUser.uuid="+person.getUuid()+" and mail.deleteFlag=0 and mail.mailBody.sendFlag=1 and mail.mailBody.delFlag=0 and mail.mailBody.subject like ? order by mail.mailBody.sendTime desc";
			List<TeeMailModel> modelList = new ArrayList();
			List<TeeMail> list = executeQuery(hql, new Object[]{"%"+key+"%"});
			for(TeeMail mail:list){
				TeeMailModel m = new TeeMailModel();
				m.setSid(mail.getSid());
				m.setSubject(mail.getMailBody().getSubject());
				modelList.add(m);
			}
			return modelList;
		}
		
		//获取本人附件占用空间
		
		public long getMailAttachSize(TeePerson person,String modelId){
			long size = 0;
			List<TeeAttachment> list = new ArrayList<TeeAttachment>();
			String hql = "select attachment from TeeAttachment attachment where attachment.model = '"+modelId+"' and attachment.user.uuid = '"+person.getUuid()+"'";
			list = attachmentDao.find(hql, null);
			for(TeeAttachment a : list){
				size+=a.getSize();
			}
			return size;
		}


		/**
		 * 获取当前登录人 未读邮件总数
		 * @param person
		 * @return
		 */
		public long getNotReadingCount(TeePerson person) {
			List param = new ArrayList();
			param.add(person.getUuid());
			String hql = " from TeeMail where toUser.uuid = ? and mailBody.sendFlag = 1 and readFlag=0 and deleteFlag in (0,2)";	
			long counter = countByList("select count(*) " + hql, param);
			return counter;
		}
		
		public long getListCount1(int i, Integer personId, Map requestData,long total) {
			long num=0;
			int nums= Integer.parseInt(String.valueOf(total)); ;
			Object[] params=new Object[nums];
			List<Object> param = new ArrayList<Object>();
			TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
			//int parentTaskId = TeeStringUtil.getInteger(requestData.get("parentTaskId"), 0);
			
			String taskTitle = TeeStringUtil.getString(requestData.get("taskTitle"));
			int status = TeeStringUtil.getInteger(requestData.get("status"),-2);
			int createUserId = TeeStringUtil.getInteger(requestData.get("createUserId"),0);
			int chargerId = TeeStringUtil.getInteger(requestData.get("chargerId"),0);
			
			Calendar c1=Calendar.getInstance();
			//System.out.println(c1.getTimeInMillis());
				String hql = "";
				hql = "select count(sid) from TeeCoWorkTask task  where 1=1";
				
				if(!TeeUtility.isNullorEmpty(taskTitle) && !"null".equals(taskTitle) && taskTitle !=null){
					hql+=" and task.taskTitle like '%"+taskTitle+"%'";
	//				param.add("%"+taskTitle+"%");
				}
				
				/*if(!"-1".equals(status) && !"".equals(status)){
					hql+=" and task.status = "+status;
				}*/
				
				if( createUserId >0){
					hql+=" and task.createUser.uuid = "+createUserId;
				}
				
				if(!"0".equals(chargerId) && chargerId >0){
					hql+=" and task.charger.uuid = "+chargerId;
				}
				
				if(personId==1 || personId==0){
					hql+=" and task.charger.uuid ="+loginUser.getUuid()+"";
				}else if(personId==2){
					hql+=" and (exists (select 1 from task.joiners j where j.uuid in ("+loginUser.getUuid()+")) and task.status !=1)";
				}else if(personId==3){
					hql+=" and task.createUser.uuid ="+loginUser.getUuid()+"";
				}
				
					
					if("-1".equals(status) || "".equals(status) || i == -1){
						hql+=" and task.status BETWEEN 0 AND 9";
					}
					
					if("-2".equals(i) || "".equals(i) || i == -2){
						hql+=" and task.status BETWEEN 0 AND 9";
					}else if("0".equals(i)  || i == 0){
							hql+=" and task.status BETWEEN 0 AND 3 ";
					}else if("4".equals(i)  || i == 4){
						    hql+=" and task.status BETWEEN 4 AND 6 ";
					}else if(("9".equals(i)  || i == 9)   ){
						hql+=" and task.status BETWEEN 4 AND 6 and endTime < ?";
						param.add(c1);

					}else if("8".equals(i)  || i == 8){
					    hql+=" and task.status = "+i;
					}else if("7".equals(i)  || i == 7){
					    hql+=" and task.status = "+i;
					}
			long count = countByList(hql, param);
			return count;
}
		private void taskTitleContains(String string) {
			// TODO Auto-generated method stub
			
		}
}
