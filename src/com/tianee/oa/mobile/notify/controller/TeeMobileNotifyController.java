package com.tianee.oa.mobile.notify.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.news.service.TeeNewsService;
import com.tianee.oa.core.base.notify.service.TeeNotifyService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.news.service.TeeMobileNewsService;
import com.tianee.oa.mobile.notify.service.TeeMobileNotifyService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 手机端  -- 公告
 * @author syl
 */
@Controller
@RequestMapping("/mobileNotifyController")
public class TeeMobileNotifyController extends BaseController{
	
	@Autowired
	TeeMobileNotifyService mobileNotifyService;
	
	@Autowired
	private TeeNotifyService notifyService;


	
	/**
	 * 获取公告state为 -1 全部  0 未读 1 已读
	 * @author syl
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getListNotify")
	@ResponseBody
	public TeeEasyuiDataGridJson getReadNews(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson json =  mobileNotifyService.query(dm, request, loginPerson);
		return json;
	}
	
	/**
	 * 根据Id 获取 公告对象
	 * @author syl
	 * @date 2014-4-15
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNotifyById")
	@ResponseBody
	public TeeJson getNotifyById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = mobileNotifyService.getNotifyById(request ,response);
		return json;
	}
	
	

	
}
