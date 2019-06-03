package com.tianee.oa.core.base.attend.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.bean.TeeAttendConfig;
import com.tianee.oa.core.base.attend.model.TeeAttendConfigModel;
import com.tianee.oa.core.base.attend.model.TeeAttendHolidayModel;
import com.tianee.oa.core.base.attend.service.TeeAttendConfigService;
import com.tianee.oa.core.base.attend.service.TeeAttendSeniorConfigService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeLogConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/TeeAttendConfigController")
public class TeeAttendConfigController extends BaseController {
	@Autowired
	private TeeAttendConfigService attendConfigService;
	
	
	@Autowired
	private TeeAttendSeniorConfigService  seniorService;

	@RequestMapping("/addConfig")
	@ResponseBody
	public TeeJson addConfig(HttpServletRequest request, TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigService.addConfig(request, model);
		return json;
	}

	@RequestMapping("/updateConfig")
	@ResponseBody
	public TeeJson updateConfig(HttpServletRequest request, TeeAttendConfigModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigService.updateConfig(request, model);
		return json;
	}

	@RequestMapping("/addHoliday")
	@ResponseBody
	public TeeJson addHoliday(HttpServletRequest request, TeeAttendHolidayModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigService.addHoliday(request, model);
		return json;
	}

	@RequestMapping("/updateHoliday")
	@ResponseBody
	public TeeJson updateHoliday(HttpServletRequest request, TeeAttendHolidayModel model) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigService.updateHoliday(request, model);
		return json;
	}

	@RequestMapping("/setGeneral")
	@ResponseBody
	public TeeJson setGeneral(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json = attendConfigService.setGeneral(request);
		return json;
	}

	@RequestMapping("/getConfig")
	@ResponseBody
	public TeeJson getConfig(HttpServletRequest request, TeeAttendConfigModel model) {
		TeeJson json = new TeeJson();
		json = attendConfigService.getConfig(request, model);
		return json;
	}

	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request, TeeAttendConfigModel model) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = attendConfigService.deleteById(sid);
		return json;
	}

	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = attendConfigService.getById(sid);
		return json;
	}

	@RequestMapping("/deleteHolidayById")
	@ResponseBody
	public TeeJson deleteHolidayById(HttpServletRequest request, TeeAttendHolidayModel model) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = attendConfigService.deleteHolidayById(sid);
		return json;
	}

	@RequestMapping("/getHolidayById")
	@ResponseBody
	public TeeJson getHolidayById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = attendConfigService.getHolidayById(sid);
		return json;
	}

	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return attendConfigService.datagrid(dm, requestDatas);
	}

	@RequestMapping("/datagridHoliday")
	@ResponseBody
	public TeeEasyuiDataGridJson datagridHoliday(TeeDataGridModel dm, HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return attendConfigService.datagridHoliday(dm, requestDatas);
	}

	/**
	 * 考勤登记时间段 获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAttendTimes")
	@ResponseBody
	public TeeJson getAttendTimes(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map returned = attendConfigService.getAttendTimes();
		json.setRtData(returned);
		json.setRtMsg("参数获取成功");
		json.setRtState(true);
		return json;
	}

	/**
	 * 考勤登记时间段 设置
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveAttendTimes")
	@ResponseBody
	public TeeJson saveAttendTimes(HttpServletRequest request) throws Exception {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeeJson json = new TeeJson();
		attendConfigService.saveAttendTimes(requestDatas);
		json.setRtMsg("参数设置成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 考勤免签人员段设置
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNoRegisterPerson")
	@ResponseBody
	public TeeJson getNoRegisterPerson(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map returned = attendConfigService.getNoRegisterPerson();
		json.setRtData(returned);
		json.setRtMsg("参数获取成功");
		json.setRtState(true);
		return json;
	}

	/**
	 * 考勤免签人员段设置
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveNoRegisterPerson")
	@ResponseBody
	public TeeJson saveNoRegisterPerson(HttpServletRequest request) throws Exception {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		TeeJson json = new TeeJson();
		attendConfigService.saveNoRegisterPerson(requestDatas);
		json.setRtMsg("参数设置成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取当前登录人  当天的考勤排班类型   
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDutyConfigByUser")
	@ResponseBody
	public TeeJson getDutyConfigByUser(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		TeeAttendConfigModel model = attendConfigService.getDutyConfigByUser(request);
		json.setRtData(model);
		json.setRtMsg("排班类型获取成功！");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取当前登录人的考勤排班类型（移动端使用）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDutyConfigByUser4Mobile")
	@ResponseBody
	public TeeJson getDutyConfigByUser4Mobile(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map params = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(attendConfigService.getDutyConfigByUser4Mobile(params,loginPerson));
		json.setRtMsg("排班类型获取成功！");
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 获取指定年月的签到记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMonthAttendInfos4Mobile")
	@ResponseBody
	public TeeJson getMonthAttendInfos4Mobile(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map params = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(attendConfigService.getMonthAttendInfos4Mobile(params,loginPerson));
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取指定日期下的签到记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDateAttendRecords")
	@ResponseBody
	public TeeJson getDateAttendRecords(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map params = TeeServletUtility.getParamMap(request);
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json.setRtData(attendConfigService.getDateAttendRecords(params,loginPerson));
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取登记操作
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/isRegister")
	@ResponseBody
	public TeeJson isRegister(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map params = TeeServletUtility.getParamMap(request);
		params.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		int flag = attendConfigService.isRegister(params);
		json.setRtData(flag);
		json.setRtMsg("上下班登记操作区显示控制！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取登记操作
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRegisterTime")
	@ResponseBody
	public TeeJson getRegisterTime(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Map params = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String registerTime = attendConfigService.getRegisterTime(params,person);
		json.setRtData(registerTime);
		json.setRtMsg("获取登记时间成功！");
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取登记操作
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getServerTime")
	@ResponseBody
	public TeeJson getServerTime(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// sf.format(cl.getTime())
		json.setRtData(cl.getTimeInMillis());
		json.setRtMsg("获取登记时间成功！");
		json.setRtState(true);
		return json;
	}

	@RequestMapping("/getWorkDayList")
	@ResponseBody
	public TeeEasyuiDataGridJson getWorkDayList(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return attendConfigService.getWorkDayList(dm, requestDatas);
	}


	
	
	/**
	 * 获取当前登陆人  当前时间的排班类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAttendConfigByUserAndDate")
	@ResponseBody
	public TeeJson getAttendConfigByLoginUserAndDate(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
		String currDateStr=sdf.format(Calendar.getInstance().getTime());
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeAttendConfig config = seniorService.getAttendConfigByUserAndDate(loginUser, currDateStr);
		TeeAttendConfigModel configModel=null; 
		if(config!=null){//有排班   或者   休息
			configModel=attendConfigService.parseModel(config);
			json.setRtData(configModel);
		}else{//没有排班
			json.setRtData(null);
		}
		json.setRtState(true);
		return json;
	}
	
	
	
	
}