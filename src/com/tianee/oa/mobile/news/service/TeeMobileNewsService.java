package com.tianee.oa.mobile.news.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.news.bean.TeeNewsInfo;
import com.tianee.oa.core.base.news.dao.TeeNewsDao;
import com.tianee.oa.core.base.news.model.TeeNewsModel;
import com.tianee.oa.core.base.news.service.TeeNewsService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.global.TeeMobileConst;
import com.tianee.oa.mobile.news.dao.TeeMobileNewsDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeMobileNewsService  extends TeeBaseService{

	@Autowired
	TeeMobileNewsDao mobileNewsDao;
	
	@Autowired
	TeeNewsDao newsDao;
	
	@Autowired
	TeeNewsService newsService;
	
	@Autowired
	private TeeAttachmentService attachmentService;
	
	

	/**
	 * 登录获取新闻列表
	 * @author syl
	 * @date 2013-11-13
	 * @param request
	 * @param response
	 * @return
	 */
    public TeeJson getListNews(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		int listNewsCount = TeeStringUtil.getInteger(request.getParameter("listNewsCount") , TeeMobileConst.DEFAULT_LIST_COUNTS_NEWS);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int state = -1;
		try {
			List<TeeNewsModel>  list = mobileNewsDao.getList(person, state, listNewsCount);
			json.setRtData(list);
	    } catch (Exception ex) {
	    	json.setRtMsg(ex.getMessage());
	    	json.setRtState(false);
	    	json.setRtData("");
	    	System.out.println(ex.getMessage());
	    	return json;
	    }
		String sid = request.getSession().getId();
		   // return "/mobile/news/read.jsp;JSESSIONID=" + sid;
     	json.setRtState(true);
     	json.setRtMsg("获取数据成功");
	    return json ;
    }
	
 /*   *//**
     * 读取新闻
     * @param dm
     * @param request
     * @return
     *//*
	public TeeEasyuiDataGridJson getReadNews(TeeDataGridModel dm,HttpServletRequest request){
		Map map = new HashMap<String, String>();
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"),"");
		map.put("subject", subject);
		map.put("typeId", typeId);
		map.put("content", content);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);
		TeeEasyuiDataGridJson json =  newsService.getReadNews(dm, map,state,loginPerson);
		return json;
	}
			*/
    
    /**
     * 根据Id  获取数据
     * @author syl
     * @date 2014-4-15
     * @param request
     * @param response
     * @return
     */
    public TeeJson getNewsById(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int isLooked = TeeStringUtil.getInteger(request.getParameter("isLooked"), 0);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		try {
			TeeNews  news = newsDao.getById(sid);
			if(news == null){
				json.setRtState(false);
				json.setRtMsg("该新闻已被删除！");
				return json;
			}
			 boolean exists = newsService.checkExistsInfo(person, news);
			 if(!exists){//不存在
				TeeNewsInfo info = new TeeNewsInfo();
				info.setIsRead(1);
				info.setReadTime(new Date());
				info.setToUser(person);
				info.setNews(news);
				newsService.addNewInfo(info);
				news.getInfos().add(info);
				newsService.updateNewsService(news);
			 }
			newsService.addCount(sid , news.getClickCount() + 1);//点击次数 + 1
			TeeNewsModel model = parseModel(news);
			json.setRtData(model);
	    } catch (Exception ex) {
	    	json.setRtMsg(ex.getMessage());
	    	json.setRtState(false);
	    	json.setRtData("");
	    	System.out.println(ex.getMessage());
	    	return json;
	    }
		String sessionId = request.getSession().getId();
		   // return "/mobile/news/read.jsp;JSESSIONID=" + sid;
     	json.setRtState(true);
     	json.setRtMsg("获取数据成功");
	    return json ;
    }
	
    
    /**
	 * 转换新闻模型
	 * @author syl
	 * @date 2014-4-15
	 * @param news
	 * @return
	 */
	public TeeNewsModel parseModel(TeeNews news){
		TeeNewsModel m = new TeeNewsModel();
		if( news == null){
			return m;
		}
		
		if(news.getProvider() != null){
			m.setProvider1(news.getProvider().getUserName());
		}else{
			m.setProvider1("");
		}
		
		BeanUtils.copyProperties(news, m);
		m.setNewsTimeStr(TeeUtility.getDateTimeStr(news.getNewsTime()));
		String typeDesc = "";
		if(!TeeUtility.isNullorEmpty(m.getTypeId())){
			typeDesc = TeeSysCodeManager.getChildSysCodeNameCodeNo("NEWS_TYPE", m.getTypeId());
		}
		m.setTypeDesc(typeDesc);
		
		/*附件*/
		//List<TeeAttachment> attaches = news.getAttachments();
		 List<TeeAttachment> attaches = null;
		 if(news != null){
			 attaches = attachmentService.getAttaches(TeeAttachmentModelKeys.NEWS, String.valueOf(news.getSid()));
		 }
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		for(TeeAttachment attach:attaches){
			TeeAttachmentModel attachModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachModel);
			attachModel.setUserId(attach.getUser().getUuid()+"");
			attachModel.setUserName(attach.getUser().getUserName());
			attachModel.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachModel.setSizeDesc(TeeFileUtility.getFileSizeDesc(attach.getSize()));
			attachmodels.add(attachModel);
		}
		m.setAttachmentsModel(attachmodels);
		return m;
	}

}


