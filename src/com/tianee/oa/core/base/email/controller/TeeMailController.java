package com.tianee.oa.core.base.email.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;





import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.email.model.TeeMailBoxModel;
import com.tianee.oa.core.base.email.model.TeeMailModel;
import com.tianee.oa.core.base.email.model.TeeWebMailModel;
import com.tianee.oa.core.base.email.service.TeeMailService;
import com.tianee.oa.core.base.email.util.Page;
import com.tianee.oa.core.base.email.util.PageUtil;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.bean.TeeMailBody;
import com.tianee.oa.core.base.email.bean.TeeMailColor;
import com.tianee.oa.core.base.email.bean.TeeWebMail;
import com.tianee.oa.core.base.email.dao.TeeMailColorDao;
import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/mail")
public class TeeMailController {
	@Autowired
	TeeMailService mailService;
	@Autowired
	TeeMailColorDao colorDao;
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	
	@Autowired
	TeeAttachmentService attachmentService;
	
	public TeeMailService getMailService() {
		return mailService;
	}

	public void setMailService(TeeMailService mailService) {
		this.mailService = mailService;
	}

	public TeeBaseUpload getUpload() {
		return upload;
	}

	public void setUpload(TeeBaseUpload upload) {
		this.upload = upload;
	}
	
	@Autowired
	TeePersonService personService;

	/**
	 * 新增或者更新邮件
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateMail.action")
	@ResponseBody
	public TeeJson addOrUpdateMail(HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			String sid = request.getParameter("sid");
			String ifBody = request.getParameter("ifBody");
			String userListIds = request.getParameter("userListIds");
			String copyUserListIds = request.getParameter("copyUserListIds");
			String secretUserListIds = request.getParameter("secretUserListIds");
			String externalInput = request.getParameter("externalInput");
			String content = request.getParameter("content");
			String subject = request.getParameter("subject");
			String type = request.getParameter("type");
			String webmailCount = request.getParameter("webmailCount");
			String webmailHtml = "";
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.EMAIL);
			List<TeeAttachment> list = new ArrayList<TeeAttachment>();
			List<TeeAttachment> list1 = new ArrayList<TeeAttachment>();
			List<TeeAttachment> listAll = new ArrayList<TeeAttachment>();
			if(!TeeUtility.isNullorEmpty(sid)){
				if(!ifBody.equals("1")){
					list1 = attachmentService.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailService.getMailById(Integer.parseInt(sid)).getMailBody().getSid()));
				}else{
					list1 = attachmentService.getAttaches(TeeAttachmentModelKeys.EMAIL, String.valueOf(mailService.getMailBodyById(Integer.parseInt(sid)).getSid()));
				}
			}
			for(int i=0;i<attachments.size();i++){
				TeeAttachment attach = (TeeAttachment) attachments.get(i);
				list.add(attach);
			}
			listAll.addAll(list);
			listAll.addAll(list1);
			String message = mailService.addOrUpdateMail(userListIds,copyUserListIds,secretUserListIds,externalInput,content,subject,type,person,listAll,webmailCount,webmailHtml);
			json.setRtState(true);
			if(type.equals("0")){
				json.setRtMsg("已保存到草稿箱");
			}else{
				json.setRtMsg(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return json;
	}
	
	/**
	 * 新增或者更新邮件
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveOrUpdateMailBox.action")
	@ResponseBody
	public TeeJson saveOrUpdateMailBox(HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			String boxInput = request.getParameter("boxInput");
			String boxNo = request.getParameter("boxNo");
			String sid = request.getParameter("sid");
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			mailService.saveOrUpdateMailBox(person,boxInput , boxNo,sid);
			json.setRtState(true);
			json.setRtMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return json;
	}
	
	/**
	 * 移动分类箱
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/moveMailBox.action")
	@ResponseBody
	public TeeJson moveMailBox(HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			String boxId = request.getParameter("boxId");
			String mailId = request.getParameter("mailId");
			TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			mailService.moveMailBox(boxId,mailId);
			json.setRtState(true);
			json.setRtMsg("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return json;
	}
	
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/mailList.action")
	 public ModelAndView mailList(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/index1.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/readMailIndex.action")
	 public ModelAndView readMailIndex(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/readMailIndex.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  String type = request.getParameter("type");
		  String mailId = request.getParameter("mailId");
		  String ifBox = request.getParameter("ifBox");
		  List<TeeMailBoxModel> list = new ArrayList<TeeMailBoxModel>();
		  list = mailService.getBoxList(person,0);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  mav.addObject("type", type);
		  mav.addObject("mailId", mailId);
		  mav.addObject("ifBox", ifBox);
		  mav.addObject("boxList",list);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/mailIndex.action")
	 public ModelAndView mailIndex(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/index.jsp");
		  //ModelAndView mav = new ModelAndView("/system/core/mail/test.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  String type = request.getParameter("type");
		  //String mailId = request.getParameter("mailId");
		  //String ifBox = request.getParameter("ifBox");
		  List<TeeMailBoxModel> list = new ArrayList<TeeMailBoxModel>();
		  list = mailService.getBoxList(person,0);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  //System.out.println("type:"+type);
		  mav.addObject("type", type);
		  //mav.addObject("mailId", mailId);
		  //mav.addObject("ifBox", ifBox);
		  mav.addObject("boxList",list);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/menu")
	 public ModelAndView menu(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/menu.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  List<TeeMailBoxModel> list = new ArrayList<TeeMailBoxModel>();
		  list = mailService.getBoxList(person,0);
		  long receiveCount = mailService.getListCount(0,person);
		  long saveCount = mailService.getListCount(1,person);
		  long sendCount = mailService.getListCount(2,person);
		  long deleteCount = mailService.getListCount(3,person);
		  String mailColor = getPersonColor("MAIL", person);
		  long attachSize = mailService.getMailAttachSize(person,"email")/1024/1024;
		  long max = 0;
		  if(person.getEmailCapacity() != null  && person.getEmailCapacity() != 0){
			  max = person.getEmailCapacity();
		  }
		  mav.addObject("color", mailColor);
		  mav.addObject("mailAttachSize", attachSize);
		  mav.addObject("max", max);
		  mav.addObject("receiveCount", receiveCount);
		  mav.addObject("saveCount", saveCount);
		  mav.addObject("sendCount", sendCount);
		  mav.addObject("deleteCount", deleteCount);
		  mav.addObject("boxList",list);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/readMailMenu.action")
	 public ModelAndView readMailMenu(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/readMailMenu.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  List<TeeMailBoxModel> list = new ArrayList<TeeMailBoxModel>();
		  list = mailService.getBoxList(person,0);
		  long receiveCount = mailService.getListCount(0,person);
		  long saveCount = mailService.getListCount(1,person);
		  long sendCount = mailService.getListCount(2,person);
		  long deleteCount = mailService.getListCount(3,person);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  mav.addObject("receiveCount", receiveCount);
		  mav.addObject("saveCount", saveCount);
		  mav.addObject("sendCount", sendCount);
		  mav.addObject("deleteCount", deleteCount);
		  mav.addObject("boxList",list);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/body/{selectType}/{key}/{type}/{ifBox}/{lookAll}/{order}/{date}/{orderRule}")
	 public ModelAndView body(HttpServletRequest request,@PathVariable("selectType") String selectType,@PathVariable("key") String key,@PathVariable("type") String type,@PathVariable("ifBox") String ifBox,@PathVariable("lookAll") String lookAll,@PathVariable("order") String order,@PathVariable("date") String date,@PathVariable("orderRule") String orderRule) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/body.jsp");
		  Page<TeeMailModel> page = new Page<TeeMailModel>(PageUtil.PAGE_SIZE);
		  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  int t = 0;
		  int box = 0;
		  if(!TeeUtility.isNullorEmpty(ifBox)){
			  box = Integer.parseInt(ifBox);
		  }
		  if(!TeeUtility.isNullorEmpty(type)){
			  t = Integer.parseInt(type);
		  }
		  if(key.equals("null")){
			  key = "";
		  }
		  if(!TeeUtility.isNullorEmpty(key)){
			  try {
				  key = URLDecoder.decode(key,"UTF-8");
			  } catch (UnsupportedEncodingException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
			  }
		  }
		  int count = mailService.getListGroupCount(selectType,key,t,person,0,box,lookAll,order,date);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  int[] pageParams = PageUtil.init(page, request);
		  list = mailService.getListGroup(selectType,key,t,person,0,box,lookAll,order,date,orderRule,pageParams[0],pageParams[1]);
		  page.setResult(list);
		  page.setTotalCount(count);
		  mav.addObject("page", page);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/readMailBody.action")
	 public ModelAndView readMailBody(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/readMailbody.jsp");
		  String mailId = request.getParameter("mailId");
		  String type = request.getParameter("type");
		  String ifBox = request.getParameter("ifBox");
		  int t = 0;
		  int box = 0;
		  if(!TeeUtility.isNullorEmpty(ifBox)){
			  box = Integer.parseInt(ifBox);
		  }
		  if(box==0){
			  if(!TeeUtility.isNullorEmpty(type)){
				  t = Integer.parseInt(type)+4;
			  }
		  }else{
			  t = box;
		  }
		  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  
		  list = mailService.getListGroup("0","",t,person,Integer.parseInt(mailId),box,"0","0","0","0",0,1);
		  TeeMailModel mailModel = new TeeMailModel();
		  mailModel = list.get(0);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  mav.addObject("select", "");
		  mav.addObject("mailModel", mailModel);
		  return mav;
	  }
	 
		/**
		 * 获取附件模型
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/getAttachModel.action")
		@ResponseBody
		public TeeJson getAttachModel(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String sid = request.getParameter("sid");
				String ifBody = request.getParameter("ifBody");
				List<TeeAttachmentModel> list = mailService.getMailAttachModelList(Integer.parseInt(sid),Integer.parseInt(ifBody));
				json.setRtState(true);
				json.setRtData(list);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/choseMailAddress.action")
	 public ModelAndView choseMailAddress(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/choseMailAddress.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  String mailColor = getPersonColor("MAIL", person);
		  List<TeeWebMailModel> list = mailService.getWebMailDefault(person);
		  TeeWebMailModel model = new TeeWebMailModel();
		  if(list.size()>0){
			  model = list.get(0);
		  }
		  mav.addObject("webMail", model);
		  mav.addObject("person", person);
		  mav.addObject("color", mailColor);
		  return mav;
	  }
	 
	 /**
	  * spring mvc 返回mav 用el表达式获取值
	  * @param request
	  * @return
	  */
	 @RequestMapping(value = "/mailBody.action")
	 public ModelAndView mailBody(HttpServletRequest request) {
		  ModelAndView mav = new ModelAndView("/system/core/mail/mailBody.jsp");
		  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		  String mailColor = getPersonColor("MAIL", person);
		  mav.addObject("color", mailColor);
		  return mav;
	  }
	
		/**
		 * 删除邮件
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/delMail.action")
		@ResponseBody
		public TeeJson delMail(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String ids = request.getParameter("mailIds");
				String ifBody = request.getParameter("ifBody");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				mailService.delMail(ids,ifBody);
				json.setRtState(true);
				json.setRtMsg("删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
		
		/**
		 * 销毁邮件
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/destroyMails.action")
		@ResponseBody
		public TeeJson destroyMails(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String ids = request.getParameter("mailIds");
				String ifBody = request.getParameter("ifBody");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				//如果是外部邮箱，删除对应的mail_body
				if(!TeeUtility.isNullorEmpty(ids)){
					String[] mailIds = ids.split(",");
					for(int i=0;i<mailIds.length;i++){
						TeeMail mail = mailService.getMailById(Integer.parseInt(mailIds[i]));
						if(mail.getMailBody().getIfWebMail()==1){
							mailService.deleteMailBody(mail.getMailBody().getSid());
						}
					}
				}
				mailService.destroyMails(ids,ifBody);
				//这里找到每一个邮件对应的附件，将其删掉
				
				json.setRtState(true);
				json.setRtMsg("销毁成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
		
		/**
		 * 移动邮件
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/moveMail.action")
		@ResponseBody
		public TeeJson moveMail(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String ids = request.getParameter("mailIds");
				String ifBody = request.getParameter("ifBody");
				String boxId = request.getParameter("boxId");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				mailService.moveMail(ids,ifBody,boxId);
				json.setRtState(true);
				json.setRtMsg("移动成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
		
		/**
		 * 标记全部邮件
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/markMail.action")
		@ResponseBody
		public TeeJson markMail(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String id = request.getParameter("id");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				mailService.markMail(id,person.getUuid());
				json.setRtState(true);
				json.setRtMsg("移动成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
		
		/**
		 * 删除全部邮件
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/clearMail.action")
		@ResponseBody
		public TeeJson clearMail(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String id = request.getParameter("id");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				mailService.clearMail(id,person.getUuid());
				json.setRtState(true);
				json.setRtMsg("移动成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
		
		/**
		 * 重命名分类箱
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/renameBox.action")
		@ResponseBody
		public TeeJson renameBox(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String id = request.getParameter("id");
				String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				mailService.renameBox(id,name);
				json.setRtState(true);
				json.setRtMsg("移动成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
		
		/**
		 * 删除分类箱
		 * @param request
		 * @param para
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/deleteBox.action")
		@ResponseBody
		public TeeJson deleteBox(HttpServletRequest request){
			TeeJson json = new TeeJson();
			try {
				String id = request.getParameter("id");
				TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				mailService.deleteBox(id);
				json.setRtState(true);
				json.setRtMsg("移动成功");
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return json;
		}
	
		 /**
		  * spring mvc 返回mav 用el表达式获取值
		  * @param request
		  * @return
		  */
		 @RequestMapping(value = "/selectMail/{type}/{ifBox}/{selectType}/{key}/{lookAll}/{order}/{date}/{orderRule}")
		 public ModelAndView selectMail(HttpServletRequest request,@PathVariable("type") String type,@PathVariable("ifBox") String ifBox,@PathVariable("selectType") String selectType,@PathVariable("key") String key,@PathVariable("lookAll") String lookAll,@PathVariable("order") String order,@PathVariable("date") String date,@PathVariable("orderRule") String orderRule) {
			  ModelAndView mav = new ModelAndView("/system/core/mail/body.jsp");
			  Page<TeeMailModel> page = new Page<TeeMailModel>(PageUtil.PAGE_SIZE);
			  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
			  int[] pageParams = PageUtil.init(page, request);
			  try {
				  key = URLDecoder.decode(key,"UTF-8");
			  } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
			  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			  list = mailService.selectMail(type,ifBox,selectType,person,key,lookAll,order,date,orderRule,pageParams[0],pageParams[1]);
			  int count = mailService.selectMailCount(type,ifBox,selectType,person,key,lookAll,order,date);
			  page.setResult(list);
		      page.setTotalCount(count);
			  String mailColor = getPersonColor("MAIL", person);
			  mav.addObject("color", mailColor);
			  mav.addObject("page", page);
			  return mav;
		  }
		 
		 /**
		  * spring mvc 返回mav 用el表达式获取值
		  * @param request
		  * @return
		  */
		 @RequestMapping(value = "/selectMailById.action")
		 public ModelAndView selectMailById(HttpServletRequest request) {
			 //ModelAndView mav = new ModelAndView("/system/core/mail/readMailIndex.jsp");
			 ModelAndView mav = new ModelAndView("/system/core/mail/readMailbody.jsp");
			  String ifBody = request.getParameter("ifBody");
			  String mailId = request.getParameter("mailId");
			  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
			  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			  //list = mailService.selectMailById(ifBody,person,id);
			  //TeeMailModel mailModel = new TeeMailModel();
			  //mailModel = list.get(0);
			  //mav.addObject("mailModel", mailModel);
			  String mailColor = getPersonColor("MAIL", person);
			  mav.addObject("color", mailColor);
			  mav.addObject("ifBody",ifBody);
			  mav.addObject("mailId", mailId);
			  mav.addObject("select", "select");
			  return mav;
		  }
		 
		 /**
		  * spring mvc 返回mav 用el表达式获取值
		  * @param request
		  * @return
		  */
		 @RequestMapping(value = "/selectMailByIdToBody.action")
		 public ModelAndView selectMailByIdToBody(HttpServletRequest request) {
			 ModelAndView mav = new ModelAndView("/system/core/mail/readMailbody.jsp");
			  String ifBody = request.getParameter("ifBody");
			  String mailId = request.getParameter("mailId");
			  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
			  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			  list = mailService.selectMailById(ifBody,person,mailId);
			  TeeMailModel mailModel = new TeeMailModel();
			  mailModel = list.get(0);
			  String mailColor = getPersonColor("MAIL", person);
			  mav.addObject("color", mailColor);
			  mav.addObject("mailModel", mailModel);
			  mav.addObject("select", "select");
			  return mav;
		  }
		 
		 /**
		  * spring mvc 返回mav 用el表达式获取值
		  * @param request
		  * @return
		  */
		 @RequestMapping(value = "/createMail.action")
		 public ModelAndView createMail(HttpServletRequest request) {
			 ModelAndView mav = new ModelAndView("/system/core/mail/newIndex.jsp");
			 TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			 String mailColor = getPersonColor("MAIL", person);
			 mav.addObject("color", mailColor);
			 return mav;
		 }
		 
		 /**
		  * spring mvc 返回mav 用el表达式获取值
		  * @param request
		  * @return
		  */
		 @RequestMapping(value = "/moreMenu.action")
		 public ModelAndView moreMenu(HttpServletRequest request) {
			 ModelAndView mav = new ModelAndView("/system/core/mail/newIndex.jsp");
			 String mailId = request.getParameter("mailId");
			  String type = request.getParameter("type");
			  String ifBox = request.getParameter("ifBox");
			  String checkType = request.getParameter("checkType");
			  int t = 0;
			  int box = 0;
			  if(!TeeUtility.isNullorEmpty(ifBox)){
				  box = Integer.parseInt(ifBox);
			  }
			  if(box==0){
				  if(!TeeUtility.isNullorEmpty(type)){
					  t = Integer.parseInt(type)+4;
				  }
			  }else{
				  t = box;
			  }
			  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
			  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			  
			  list = mailService.getListGroup("0","",t,person,Integer.parseInt(mailId),box,"0","0","0","0",0,1);
			  TeeMailModel mailModel = new TeeMailModel();
			  mailModel = list.get(0);
			  String mailColor = getPersonColor("MAIL", person);
			  mav.addObject("color", mailColor);
			  mav.addObject("mailModel", mailModel);
			  mav.addObject("type", checkType);
			  return mav;
		  }
		 
			/**
			 * 移至收信箱
			 * @param request
			 * @param para
			 * @return
			 * @throws Exception
			 */
			@RequestMapping("/moveToReceive.action")
			@ResponseBody
			public TeeJson moveToReceive(HttpServletRequest request){
				TeeJson json = new TeeJson();
				try {
					String id = request.getParameter("mailId");
					TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
					mailService.moveToReceive(id);
					json.setRtState(true);
					json.setRtMsg("已移动至收信箱");
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				return json;
			}
			
			/**
			 * 设置邮件箱肤色
			 * @param request
			 * @param para
			 * @return
			 * @throws Exception
			 */
			@RequestMapping("/setMailColor.action")
			@ResponseBody
			public TeeJson setMailColor(HttpServletRequest request){
				TeeJson json = new TeeJson();
				try {
					String color = request.getParameter("color");
					TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
					mailService.setMailColor(color,person);
					json.setRtState(true);
					json.setRtMsg("肤色设置成功");
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				return json;
			}
			
			public String getPersonColor(String name,TeePerson person){
				String color = "#0072C6";
				List<TeeMailColor> list = new ArrayList<TeeMailColor>();
				TeeMailColor mailColor = new TeeMailColor();
				try {
					list = colorDao.checkColor(person,"MAIL");
					if(list.size()>0){
						mailColor = list.get(0);
						color = mailColor.getColorValue();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				return color;
			}
			
			/**
			 * 设置桌面邮件模块
			 * @param request
			 * @param para ：type 0 全部 1未读，maxSize 最多返回多少条
			 * @return
			 * @throws Exception
			 */
			@RequestMapping("/selectMailForPortlet.action")
			@ResponseBody
			public TeeJson selectMailForPortlet(HttpServletRequest request){
				TeeJson json = new TeeJson();
				List<TeeMailModel> list = new ArrayList<TeeMailModel>();
				try {
					String type = request.getParameter("type");
					String maxSize = request.getParameter("maxSize");
					TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
					list = mailService.selectMailForPortlet(person,Integer.parseInt(type),Integer.parseInt(maxSize));
					//System.out.println("list长度："+list.size());
					json.setRtState(true);
					json.setRtData(list);
					json.setRtMsg("读取成功");
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				return json;
			}
			
			 /**
			  * 其他模块调用
			  * spring mvc 返回mav 用el表达式获取值
			  * @param request
			  * @return
			  */
			 @RequestMapping(value = "/otherMail.action")
			 public ModelAndView otherMail(HttpServletRequest request) {
				 ModelAndView mav = new ModelAndView("/system/core/mail/newIndex.jsp");
				 TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				 String mailColor = getPersonColor("MAIL", person);
				 String userIds = request.getParameter("userIds");
				 String subject = request.getParameter("subject");
				 String userNames = "";
				 TeeMailModel model = new TeeMailModel();
				 if(TeeUtility.isNullorEmpty(subject)){
					 subject = "";
				 }
				 model.setSubject(subject);
				 if(!TeeUtility.isNullorEmpty(userIds)){
					 if(userIds.endsWith(",")){
						 model.setUserListIds(userIds);
						 userIds = userIds.substring(0, userIds.length()-1);
						 String[] userIdStr = userIds.split(",");
						 for(String userId : userIdStr){
							 userNames += personService.getPersonByUuids(userId).get(0).getUserName()+",";
						 }
					 }else{
						 String[] userIdStr = userIds.split(",");
						 for(String userId : userIdStr){
							 userNames += personService.getPersonByUuids(userId).get(0).getUserName()+",";
						 }
						 userIds = userIds + ",";
						 model.setUserListIds(userIds);
					 }
				 }
				 model.setUserListNames(userNames);
				 mav.addObject("color", mailColor);
				 mav.addObject("otherModel", model);
				 return mav;
			 }
			 
			 /**
			  * spring mvc 返回mav 用el表达式获取值
			  * @param request
			  * @return
			  */
			 @RequestMapping(value = "/readMailForOther.action")
			 public ModelAndView readMailForOther(HttpServletRequest request) {
				  ModelAndView mav = new ModelAndView("/system/core/mail/readMailForOther.jsp");
				  String mailId = request.getParameter("mailId");
				  String type = request.getParameter("type");
				  String ifBox = request.getParameter("ifBox");
				  int t = 0;
				  int box = 0;
				  if(!TeeUtility.isNullorEmpty(ifBox)){
					  box = Integer.parseInt(ifBox);
				  }
				  if(box==0){
					  if(!TeeUtility.isNullorEmpty(type)){
						  t = Integer.parseInt(type)+4;
					  }
				  }else{
					  t = box;
				  }
				  List<TeeMailModel> list = new ArrayList<TeeMailModel>();
				  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
				  
				  list = mailService.getListGroup("0","",t,person,Integer.parseInt(mailId),box,"0","0","0","0",0,1);
				  TeeMailModel mailModel = new TeeMailModel();
				  mailModel = list.get(0);
				  String mailColor = getPersonColor("MAIL", person);
				  mav.addObject("color", mailColor);
				  mav.addObject("select", "");
				  mav.addObject("mailModel", mailModel);
				  return mav;
			  }
			 
				/**
				 * 设置桌面邮件模块
				 * @param request
				 * @param para ：type 0 全部 1未读，maxSize 最多返回多少条
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/selectMailForQueryIndex.action")
				@ResponseBody
				public TeeJson selectMailForQueryIndex(HttpServletRequest request){
					TeeJson json = new TeeJson();
					List<TeeMailModel> list = new ArrayList<TeeMailModel>();
					try {
						String key = request.getParameter("key");
						String maxSize = request.getParameter("maxSize");
						if(TeeUtility.isNullorEmpty(maxSize)){
							maxSize = "100";
						}
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						list = mailService.selectMailForQueryIndex(person,key,Integer.parseInt(maxSize));
						//System.out.println("list长度："+list.size());
						json.setRtState(true);
						json.setRtData(list);
						json.setRtMsg("读取成功");
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
			 
				 /**
				  * spring mvc 返回mav 用el表达式获取值
				  * @param request
				  * @return
				  */
				 @RequestMapping(value = "/setWebMailIndex.action")
				 public ModelAndView setWebMailIndex(HttpServletRequest request) {
					  ModelAndView mav = new ModelAndView("/system/core/mail/webMailIndex.jsp");
					  TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
					  String mailColor = getPersonColor("MAIL", person);
					  List<TeeWebMailModel> list = new ArrayList<TeeWebMailModel>();
					  list = mailService.setWebMailIndex(person);
					  mav.addObject("webMailList", list);
					  mav.addObject("person", person);
					  mav.addObject("color", mailColor);
					  return mav;
				  }
				 
			    /**
				 * 新增或者更新邮件
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/addOrUpdateWebMail.action")
				@ResponseBody
				public TeeJson addOrUpdateWebMail(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						TeeWebMail webMail = new TeeWebMail();
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						String sid = request.getParameter("sid");
						String loginType = request.getParameter("loginType");
						String popServer = request.getParameter("popServer");
						String pop3Port = request.getParameter("pop3Port");
						String pop3Ssl = request.getParameter("pop3Ssl");
						String smtpServer = request.getParameter("smtpServer");
						String smtpPort = request.getParameter("smtpPort");
						String smtpSsl = request.getParameter("smtpSsl");
						String emailUser = request.getParameter("emailUser");
						String emailPass = request.getParameter("emailPass");
						String checkFlag = request.getParameter("checkFlag");
						String isDefault = request.getParameter("isDefault");
						String recvDel = request.getParameter("recvDel");
						if(!TeeUtility.isNullorEmpty(sid)){
							webMail.setSid(Integer.parseInt(sid));
						}
						webMail.setCheckFlag(checkFlag);
						webMail.setEmailPass(emailPass);
						webMail.setEmailUser(emailUser);
						webMail.setIsDefault(Integer.parseInt(isDefault));
						webMail.setLoginType(loginType);
						webMail.setPop3Port(pop3Port);
						webMail.setPop3Ssl(pop3Ssl);
						webMail.setPopServer(popServer);
						webMail.setRecvDel(Integer.parseInt(recvDel));
						webMail.setSmtpPort(smtpPort);
						webMail.setSmtpServer(smtpServer);
						webMail.setSmtpSsl(smtpSsl);
						webMail.setUser(person);
						mailService.saveOrUpdateWebMail(webMail,person);
						json.setRtState(true);
						json.setRtMsg("保存成功");
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
				 /**
				 * 根据id获取外部邮箱
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/getWebMailById.action")
				@ResponseBody
				public TeeJson getWebMailById(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						String sid = request.getParameter("sid");
						
						TeeWebMail webMail = mailService.getWebMailById(Integer.parseInt(sid));
						TeeWebMailModel model = new TeeWebMailModel();
						BeanUtils.copyProperties(model, webMail);
						model.setUser(null);
						json.setRtData(model);
						json.setRtState(true);
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
				 /**
				 * 根据id删除外部邮箱
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/delWebMail.action")
				@ResponseBody
				public TeeJson delWebMailById(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						String sid = request.getParameter("sid");
						TeeWebMail webMail = mailService.getWebMailById(Integer.parseInt(sid));
						mailService.delWebMail(webMail);
						json.setRtMsg("删除成功");
						json.setRtState(true);
					} catch (Exception e) {
						json.setRtMsg("删除失败");
						json.setRtState(false);
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
				 /**
				 * 根据id设置默认邮箱
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/defaultWebMail.action")
				@ResponseBody
				public TeeJson defaultWebMail(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						String sid = request.getParameter("sid");
						mailService.defaultWebMail(Integer.parseInt(sid),person);
						json.setRtMsg("设置成功");
						json.setRtState(true);
					} catch (Exception e) {
						json.setRtMsg("设置失败");
						json.setRtState(false);
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
				/**
				 * 销毁全部已删除邮件
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/destroyMail.action")
				@ResponseBody
				public TeeJson destroyMail(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						String id = request.getParameter("id");
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						mailService.destroyMail(id,person.getUuid());
						json.setRtState(true);
						json.setRtMsg("销毁成功");
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
				/**
				 * 清空草稿箱、已发送邮件
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/clearMailT.action")
				@ResponseBody
				public TeeJson clearMailT(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						String value = request.getParameter("value");
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						mailService.clearMailT(value,person.getUuid());
						json.setRtState(true);
						json.setRtMsg("操作成功");
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				

				/**
				 * 移动邮件到收信箱
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/moveReceive.action")
				@ResponseBody
				public TeeJson moveReceive(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						String ids = request.getParameter("mailIds");
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						mailService.moveReceive(ids);
						json.setRtState(true);
						json.setRtMsg("移动成功");
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
				/**
				 * 删除草稿箱、已发送邮件
				 * @param request
				 * @param para
				 * @return
				 * @throws Exception
				 */
				@RequestMapping("/delSingleMailBody.action")
				@ResponseBody
				public TeeJson delSingleMailBody(HttpServletRequest request){
					TeeJson json = new TeeJson();
					try {
						String value = request.getParameter("value");
						String id = request.getParameter("id");
						TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
						mailService.delSingleMailBody(id,value,person.getUuid());
						json.setRtState(true);
						json.setRtMsg("操作成功");
					} catch (Exception e) {
						e.printStackTrace();
						// TODO: handle exception
					}
					return json;
				}
				
			/**
				 * 收件箱，自定义邮箱批量导出邮件
				 * @param request
				 */
				@RequestMapping("/exportEml")
				@ResponseBody
				public void exportEml(HttpServletRequest request,HttpServletResponse response){
					String  mailIds=request.getParameter("mailIds");
					//mailService.exportEml(mailIds);
					mailService.exportEml(mailIds,response);
				}
				
				/**
				 * 发件箱，草稿箱
				 * @param request
				 */
				@RequestMapping("/exportEmls")
				@ResponseBody
				public void exportEmls(HttpServletRequest request,HttpServletResponse response){
					String  bodyIds=request.getParameter("bodyIds");
					//mailService.exportEml(mailIds);
					mailService.exportEmls(bodyIds,response);
				}
}
