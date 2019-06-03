package com.tianee.oa.mobile.news.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.news.service.TeeNewsService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.news.service.TeeMobileNewsService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 手机端  -- 新闻
 * @author syl
 */
@Controller
@RequestMapping("/mobileNewsContoller")
public class TeeMobileNewsController extends BaseController{
	
	@Autowired
	TeeMobileNewsService mobileNewsService;
	
	@Autowired
	private TeeNewsService newsService;

	/**
	 * 获取新闻  最新 n条数数据   - 默认为10条
	 * @author syl
	 * @date 2014-4-14
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getListNews")
	@ResponseBody
	public TeeJson getListNews(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = mobileNewsService.getListNews(request ,response);
		return json;
	}
	
	
	/**
	 * 获取新闻 state为 -1 全部  0 未读 1 已读
	 * @author zhp
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getListNews2")
	@ResponseBody
	public TeeEasyuiDataGridJson getReadNews(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		String subject = TeeStringUtil.getString(request.getParameter("subject"),"");
		String content = TeeStringUtil.getString(request.getParameter("content"),"");
		String typeId = TeeStringUtil.getString(request.getParameter("typeId"),"");
		String keywords = TeeStringUtil.getString(request.getParameter("keywords"),"");
		map.put("subject", subject);
		map.put("typeId", typeId);
		map.put("content", content);
		map.put("keywords", keywords);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int state = TeeStringUtil.getInteger(request.getParameter("state"),-1);
		TeeEasyuiDataGridJson json =  newsService.getReadNews(dm, map,state,loginPerson);
		return json;
	}
	
	/**
	 * 根据Id 获取 新闻对象
	 * @author syl
	 * @date 2014-4-15
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNewsById")
	@ResponseBody
	public TeeJson getNewsById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = mobileNewsService.getNewsById(request ,response);
		return json;
	}
	
	

	
}
