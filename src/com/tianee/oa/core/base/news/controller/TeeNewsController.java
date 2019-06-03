package com.tianee.oa.core.base.news.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.news.bean.TeeNewsInfo;
import com.tianee.oa.core.base.news.model.TeeNewsModel;
import com.tianee.oa.core.base.news.service.TeeNewsService;
import com.tianee.oa.core.base.notify.bean.TeeNotifyInfo;
import com.tianee.oa.core.base.notify.model.TeeNotifyInfoModel;
import com.tianee.oa.core.base.notify.model.TeeNotifyModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeSimpleService;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 新闻管理 模块
 * @author zhp
 * @createTime 2013-12-26
 * @desc
 */
@Controller
@RequestMapping("/teeNewsController")
public class TeeNewsController {

	@Autowired
	private TeeNewsService newsService;

	@Autowired
	private TeeAttachmentService attachmentService;


	/**
	 * 添加新闻
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午11:07:23
	 * @desc
	 */
	@RequestMapping("/addNews")
	@ResponseBody
	public TeeJson addNews(HttpServletRequest request){
		TeeJson json = newsService.addOrUpdate(request);
		return json;
	}

	@RequestMapping("/updateNews")
	@ResponseBody
	public TeeJson updateNews(HttpServletRequest request){
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		Date newsTime = TeeStringUtil.getDate(request.getParameter("newsTime"),"yyyy-MM-dd HH:mm");
		
		String anonymityYn = TeeStringUtil.getString(request.getParameter("anonymityYn"),"0");//0 实名 1 匿名 2 进制评论
		TeeJson json = new TeeJson();
		TeeNews news = null;
		try {
			news = newsService.getTeeNewsById(sid);
			news.setSubject(subject);
				news.setNewsTime(newsTime);
			news.setContent(content);
			news.setAnonymityYn(anonymityYn);
			newsService.updateNewsService(news);
			json.setRtState(true);
			json.setRtMsg("更新新闻成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
			json.setRtMsg("更新新闻失败!");
		}
		return json;
	}
	
	/**
	 * 获取新闻 state为 -1 全部  0 未读 1 已读
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getReadNews")
	@ResponseBody
	public TeeEasyuiDataGridJson getReadNews(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"),"");
		map.put("subject", subject);
		map.put("typeId", typeId);
		map.put("content", content);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);
		return newsService.getReadNews(dm, map,state,loginPerson);
	}
	
	
	
	/**
	 * 获取新闻 state为 -1 全部  0 未读 1 已读
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getReadPicNews")
	@ResponseBody
	public TeeEasyuiDataGridJson getReadPicNews(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"),"");
		map.put("subject", subject);
		map.put("typeId", typeId);
		map.put("content", content);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);
		return newsService.getReadPicNews(dm, map,state,loginPerson);
	}
	/**
	 * 新闻管理 模块 获取管理者的新闻
	 * @author zhp
	 * @createTime 2014-2-18
	 * @editTime 上午12:04:17
	 * @desc
	 */
	@RequestMapping("/getManageNews")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageNews(TeeDataGridModel dm,HttpServletRequest request) {
			Map map = new HashMap<String, String>();
			String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
			String content = TeeStringUtil.getString(request.getParameter("content"),"");
			
			String typeId = TeeStringUtil.getString(request.getParameter("typeId"),"");
			String state = TeeStringUtil.getString(request.getParameter("state"),"-1");
			map.put("subject", subject);
			map.put("content", content);
			map.put("typeId", typeId);
			map.put("state", state);
			TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			
		return newsService.getManageNews(request,dm, map , loginPerson);
	}
	
	/**
	 * count 为条数 -1为全部 || state为 状态 0 未读 1已读 -1全部
	 * @author zhp
	 * @createTime 2014-2-13
	 * @editTime 下午02:08:09
	 * @desc
	 */
	@RequestMapping("/getAllNoReadNews")
	@ResponseBody
	public TeeJson getAllNoReadNews(HttpServletRequest request) {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int count = TeeStringUtil.getInteger(request.getParameter("count"),-1);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);
		TeeJson json = new TeeJson();
		List list = null;
		try {
			list = newsService.getAllNewsListByState(loginPerson,state,count);
			json.setRtData(list);
			json.setRtState(true);
			json.setRtMsg("获取新闻成功!");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("获取新闻失败!");
		}
		return json;
		
	}
	
	@RequestMapping("/updateNewsState")
	@ResponseBody
	public TeeJson updateNewsState(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),-1);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),0);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		try {
			newsService.updateNewsPublishState(sid,state);
			 json.setRtState(true);
		     json.setRtMsg("更新新闻状态成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
		    json.setRtMsg("更新新闻状态失败!");
		}
		
		return json;
	}
	
	@RequestMapping("/getNews")
	@ResponseBody
	public TeeJson getNews(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		int isLooked = TeeStringUtil.getInteger(request.getParameter("isLooked"),0);
		TeeNews news = null;
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			 news = newsService.getTeeNewsById(sid);
			 if(news == null){
				 json.setRtState(false);
				 json.setRtMsg("该新闻已被删除！");
				 return json;
			 }
			 if(isLooked != 1){
				 boolean exists = newsService.checkExistsInfo(loginPerson, news);
				 if(!exists){//不存在
					TeeNewsInfo info = new TeeNewsInfo();
					info.setIsRead(1);
					info.setReadTime(new Date());
					info.setToUser(loginPerson);
					info.setNews(news);
					newsService.addNewInfo(info);
					news.getInfos().add(info);
					newsService.updateNewsService(news);
				 }
			 }
			 newsService.addCount(sid , news.getClickCount() + 1);//点击次数 + 1
			 TeeNewsModel m = new TeeNewsModel();
			 List<Map> attachs = new ArrayList<Map>();
			 List<TeeAttachment> sorceAttachs = null;
			 if(news != null){
				 sorceAttachs = attachmentService.getAttaches(TeeAttachmentModelKeys.NEWS, String.valueOf(news.getSid()));
			 }
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
			 BeanUtils.copyProperties(news, m);
			 m.setProvider1(news.getProvider().getUserName());
			 m.setFromDeptName(news.getProvider().getDept().getDeptName());
			 m.setAttachmentsMode(attachs);
			 
			 String toRolesIds = "";//发布部门的id串
			 String toRolesNames = "";//发布部门的名字
			 String toDeptIds = "";//发布部门的id串
			 String toDeptNames = "";//发布部门的id串
			 String toUserNames = "";//发布人的名字
			 String toUserIds = "";//发布人的id串
			 List<TeePerson> pList = news.getPostUser();
			 List<TeeDepartment> dList = news.getPostDept();
			 List<TeeUserRole> rList = news.getPostUserRole();
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
			 if(!TeeUtility.isNullorEmpty(news.getTypeId())){
				typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE", news.getTypeId());
			 }
			 m.setTypeDesc(typeDesc);
			 m.setToDeptIds(toDeptIds);
			 m.setToDeptNames(toDeptNames);
			 m.setToUserIds(toUserIds);
			 m.setToUserNames(toUserNames);
			 m.setToRolesIds(toRolesIds);
			 m.setToRolesNames(toRolesNames);
			 
			 
			 
			 json.setRtState(true);
			 json.setRtData(m);
		     json.setRtMsg("获取新闻成功!");
		} catch (Exception e) {
			e.printStackTrace();
			json.setRtState(false);
		    json.setRtMsg("查询新闻失败!");
		}
		return json;
	}
	
	/**
	 * 删除 byId
	 * @author syl
	 * @date 2014-3-4
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request) throws Exception{
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		TeeJson json =  newsService.deleteByIdService(sid);
		return json;
	}
	
	
	/**
	 * 查看新闻阅读情况
	 * @author syl
	 * @date 2014-3-4
	 * @param request
	 * @return
	 */
	@RequestMapping("/getNewsLookDatail")
	@ResponseBody
	public TeeJson getNewsLookDatail(HttpServletRequest request){
		//int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		TeeJson json = newsService.getNewsInfo(request);
		
		return json;
	}
	
	/**
	 * 清空人员阅读情况数据
	 * @author syl
	 * @date 2014-3-4
	 * @param request
	 * @return
	 */
	@RequestMapping("/clearReadInfo")
	@ResponseBody
	public TeeJson clearReadInfo(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("id"),0);
		TeeJson json = newsService.clearReadInfo(sid);
		return json;
	}
	
	
	/**
	 * 全部已读
	 * @param request
	 * @return
	 */
	@RequestMapping("/readAll")
	@ResponseBody
	public TeeJson readAll(HttpServletRequest request){
		
		Map map = TeeServletUtility.getParamMap(request);
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//获取当前登录的用户未读新闻列表
		List<TeeNewsModel> list=newsService.getNoReadNewsList(map,0,loginUser);
		
		
		for (TeeNewsModel teeNewsModel : list) {
			//改变未读新闻的状态
			newsService.readNews(teeNewsModel.getSid(),0,loginUser);
		}
		
		json.setRtState(true);
		return json;
	}
	
	public TeeNewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(TeeNewsService newsService) {
		this.newsService = newsService;
	}


	
	
}
