package com.tianee.oa.mobile.diary.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.news.service.TeeNewsService;
import com.tianee.oa.core.base.notify.service.TeeNotifyService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.mobile.diary.service.TeeMobileDiaryService;
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
@RequestMapping("/mobileDiaryController")
public class TeeMobileDiaryController {
	
	@Autowired
	TeeMobileDiaryService mobileDiaryService;

	
	/**
	 * 获取自己的日志
	 * @author syl
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getListDiary")
	@ResponseBody
	public TeeEasyuiDataGridJson getListDiary(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson json =  mobileDiaryService.query(dm, request, loginPerson);
		return json;
	}
	
	/**
	 * 获取自己的日志
	 * @author syl
	 * @createTime 2014-1-5
	 * @editTime 上午11:06:35
	 * @desc
	 */
	@RequestMapping("/getListShareDiary")
	@ResponseBody
	public TeeEasyuiDataGridJson queryShare(TeeDataGridModel dm,HttpServletRequest request) {
		Map map = new HashMap<String, String>();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson json =  mobileDiaryService.queryShare(dm, request, loginPerson);
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
	@RequestMapping("/getDiaryById")
	@ResponseBody
	public TeeJson getDiaryById(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeeJson json = mobileDiaryService.getDiaryById(request ,response);
		return json;
	}
	
	/**
	 * 新建并更新
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception {
		TeeJson json = mobileDiaryService.addOrUpdate(request ,response);
		return json;
	}
	
	/**
	 * 获取直属下级人员信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnderlings")
	@ResponseBody
	public TeeEasyuiDataGridJson getUnderlings(HttpServletRequest request,TeeDataGridModel dataGridModel) throws Exception {
		return mobileDiaryService.getUnderlings(request ,dataGridModel);
	}
	
	/**
	 * 获取直属下级人员的日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnderlingDiarys")
	@ResponseBody
	public TeeEasyuiDataGridJson getUnderlingDiarys(HttpServletRequest request,TeeDataGridModel dataGridModel) throws Exception {
		return mobileDiaryService.getUnderlingDiarys(request ,dataGridModel);
	}
}
