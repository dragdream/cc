package com.tianee.oa.core.base.calendar.controller;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.calendar.model.TeeCalendarAffairModel;
import com.tianee.oa.core.base.calendar.service.TeeCalAffairService;
import com.tianee.oa.core.base.calendar.service.TeeCalendarAffairService;
import com.tianee.oa.core.base.calendar.service.TeeLeaderCalendarAffairService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.service.TeePersonManagerI;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.str.TeeStringUtil;



/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/calendarManage")
public class TeeCalendarController extends BaseController {
	@Autowired
	private TeeCalendarAffairService calendarService;
	
	@Autowired
	private TeeLeaderCalendarAffairService leaderCalendarAffairService;
	
	@Autowired
	private TeeCalAffairService calAffairService;
	
	
	
	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeePersonManagerI personManagerI;
	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param message
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response, TeeCalendarAffairModel model) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = new TeeJson();
		if(model.getCalAffType() == 1){//周期性事务
			json = calAffairService.addOrUpdateAffair(request , response, model, person);
		}else {
			json  = calendarService.addOrUpdateCal(request , model,  person);
		}
		return json;
	}
	
	/**
	 * @author syl
	 *  快速 新增或者更新  拖拉
 	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/updateChangeCal")
	@ResponseBody
	public TeeJson updateChangeCal(HttpServletRequest request , TeeCalendarAffairModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = new TeeJson();
		json  = calendarService.updateChangeCal(model,  person);
		return json;
	}
	
	/**
	 * @author syl
	 *  更新--- 更改状态
 	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/updateOverStatus")
	@ResponseBody
	public TeeJson updateOverStatus(HttpServletRequest request , TeeCalendarAffairModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = new TeeJson();
		json  = calendarService.updateOverStatus(model,  person);
		return json;
	}
	
	

	/**
	 * @author syl
	 * byId 查询
	 * @param request
	 * @return
	 */
	@RequestMapping("selectById")
	@ResponseBody
	public TeeJson selectById(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		TeeJson json = calendarService.selectById(sid );;
		return json;
	}
	
	
	/**
	 * @author syl
	 * byId 删除
	 * @param request
	 * @return
	 */
	@RequestMapping("deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		TeeJson json = calendarService.delById(sid );;
		return json;
	}
	
	/**
	 * @author syl
	 * byId 删除多个
	 * @param request
	 * @return
	 */
	@RequestMapping("delByIds")
	@ResponseBody
	public TeeJson delByIds(HttpServletRequest request) {
		String sid = request.getParameter("sids");
		TeeJson json = calendarService.delByIds(sid );;
		return json;
	}
	
	
	
	
	/**
	 * @author syl
	 * 根据开始时间和结束时间  查询
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("selectByTime")
	@ResponseBody
	public TeeJson selectByTime(HttpServletRequest request) throws ParseException {
		TeeJson json = calendarService.selectByTime(request );
		return json;
	}
	
	
	/**
	 * @author syl
	 * 个人日程查询
	 * @param request
	 * @return
	 */
	@RequestMapping("queryCal")
	public String queryCal(HttpServletRequest request ,TeeCalendarAffairModel model) {
		 List<Map<String , String >> list = calendarService.queryCal(request ,model );
		 request.setAttribute("calendarList", list);
		 return "/system/core/base/calendar/queryshow.jsp";
	}

	
	/**
	 * 人日程安排导出
	 * @author syl
	 * @date 2014-1-22
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportCalendarToCsv")
	public String exportToCsv(HttpServletRequest request ,TeeCalendarAffairModel model, HttpServletResponse response ) throws Exception {
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		response.setCharacterEncoding("GBK");
		try {
			String fileName = URLEncoder.encode("日程数据导出.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = calendarService.exportCalendarToCsv(request, model);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	/**
	 * 个人日程安排导出
	 * @author syl
	 * @date 2014-1-22
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/importCalendar")
	@ResponseBody
	public TeeJson importCalendar(HttpServletRequest request , HttpServletResponse response ) throws Exception {
		response.setCharacterEncoding("GBK");
		TeeJson json = calendarService.importCalendar(request);
		return json;
	}
	
	
	/*   以下是领导日程安排查询   */
	
	/**
	 * @author syl
	 * 领导日程安排查询 --- 获取带权限的部门树   日+ 周视图
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("getLeaderPostDept")
	@ResponseBody
	public TeeJson getLeaderPostDept(HttpServletRequest request ){
		TeeJson json = leaderCalendarAffairService.getLeaderPostDept(request);
		return json;
	}
	
	/**
	 * @author syl
	 * 领导日程安排查询 --- 获取带权限的部门树  月视图
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("getLeaderMonthPostDept")
	@ResponseBody
	public TeeJson getLeaderMonthPostDept(HttpServletRequest request ){
		TeeJson json = leaderCalendarAffairService.getLeaderMonthPostDept(request);
		return json;
	}
	
	
	/**
	 * @author syl
	 * 领导日程安排查询 ----   获取日程 周视图查询
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@RequestMapping("getCalOfWeekByUserIds")
	@ResponseBody
	public TeeJson getCalByWeek(HttpServletRequest request ) throws Exception{
		TeeJson json = leaderCalendarAffairService.getCalByWeekAndUserIds(request);
		return json;
	}
	
	
	/**
	 * @author syl
	 * 领导日程安排查询 ----   获取日程 日视图查询
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@RequestMapping("getCalOfDayByUserIds")
	@ResponseBody
	public TeeJson getCalByDay(HttpServletRequest request ) throws Exception{
		TeeJson json = leaderCalendarAffairService.getCalOfDayByUserIds(request);
		return json;
	}
	
	
	/**
	 * @author syl
	 * 领导日程安排查询 ----   获取日程 月视图查询
	 * @param request
	 * @return
	 * @throws Exception 
	 * @throws ParseException 
	 */
	@RequestMapping("getCalOfMonthByUserId")
	@ResponseBody
	public TeeJson getCalOfMonthByUserId(HttpServletRequest request ) throws Exception{
		TeeJson json = leaderCalendarAffairService.getCalOfMonthByUserId(request);
		return json;
	}
	

	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param message
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/leaderAddOrUpdate")
	@ResponseBody
	public TeeJson leaderAddOrUpdate(HttpServletRequest request, HttpServletResponse response , TeeCalendarAffairModel model) throws ParseException {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String userIds = TeeStringUtil.getString(request.getParameter("userIds") , "");
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeJson json = new TeeJson();
		if(sid > 0){
			if(model.getCalAffType() == 1){//周期性事务
				json = calAffairService.addOrUpdateAffair(request , response, model, person);
			}else {
				json  = calendarService.addOrUpdateCal(request ,model,  person);
			}
		}else{
			json  = leaderCalendarAffairService.addCalAff(request , model,  person , userIds);
		}
		return json;
	}
	
	
	/**
	 * @author syl
	 * 领导日程安排查询
	 * @param request
	 * @return
	 */
	@RequestMapping("leaderQuery")
	public String leaderQuery(HttpServletRequest request ,TeeCalendarAffairModel model) {
		 List<Map<String , String >> list = leaderCalendarAffairService.queryCal(request ,model );
		 request.setAttribute("calendarList", list);
		 return "/system/core/base/calendar/leader/queryshow.jsp";
	}
	
	
	
	/**
	 * 领导日程安排导出
	 * @author syl
	 * @date 2014-1-22
	 * @param request
	 * @param model
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportLeaderCalendarToCsv")
	public String exportLeaderToCsv(HttpServletRequest request ,TeeCalendarAffairModel model, HttpServletResponse response ) throws Exception {
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		response.setCharacterEncoding("GBK");

		try {
			String fileName = URLEncoder.encode("领导日程安排导出.csv", "UTF-8");
			fileName = fileName.replaceAll("\\+", "%20");
			response.setHeader("Cache-control", "private");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ fileName + "\"");
			ArrayList<TeeDataRecord> dbL = leaderCalendarAffairService.exportLeaderCalendarToCsv(request, model);
			TeeCSVUtil.CVSWrite(response.getWriter(), dbL);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	
	
	
	
	/**
	 * @author syl
	 * 桌面模块
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("desktop")
	@ResponseBody
	public TeeJson desktop (HttpServletRequest request) throws ParseException {
		TeeJson json = calendarService.getDesktop(request );
		return json;
	}

	
	

	/**
	 * 获取部门人员 ---带权限，按模块设置
	 * @author syl
	 * @date 2013-11-16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSelectUserByDept.action")
	@ResponseBody
	public TeeJson getSelectUserByDept(HttpServletRequest request) throws Exception{
		String moduleId = TeeStringUtil.getString(request.getParameter("moduleId"), "");
		String userFilter = TeeStringUtil.getString(request.getParameter("userFilter"), "");
		Map map = new HashMap();
		map.put("moduleId", moduleId);//模块Id
		map.put("userFilter", userFilter);//过滤人员I的字符串
		TeeJson json = personManagerI.getSelectUserByDept(request, map);
		return json;
	}
	
	/**
	 * 桌面小模块数据请求
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/desktopPortletDataRequest.action")
	@ResponseBody
	public TeeJson desktopPortletDataRequest(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		String date = TeeStringUtil.getString(request.getParameter("date"));
		json.setRtData(calendarService.desktopPortletDataRequest(loginPerson, date));
		return json;
	}
	
	/**
	 * 桌面小模块数据请求，获取月数据
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/desktopPortletData4GetMonthData.action")
	@ResponseBody
	public TeeJson desktopPortletData4GetMonthData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);	
		int year = TeeStringUtil.getInteger(request.getParameter("year"),0);
		int month = TeeStringUtil.getInteger(request.getParameter("month"),0);
		json.setRtData(calendarService.desktopPortletData4GetMonthData(loginPerson, year,month));
		return json;
	}
	
	
	public void setCalendarService(TeeCalendarAffairService calendarService) {
		this.calendarService = calendarService;
	}


	public void setCalAffairService(TeeCalAffairService calAffairService) {
		this.calAffairService = calAffairService;
	}


	public void setLeaderCalendarAffairService(
			TeeLeaderCalendarAffairService leaderCalendarAffairService) {
		this.leaderCalendarAffairService = leaderCalendarAffairService;
	}
	
	
	

	
	
}