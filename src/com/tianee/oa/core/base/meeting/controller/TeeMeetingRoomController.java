package com.tianee.oa.core.base.meeting.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.meeting.model.TeeMeetingRoomModel;
import com.tianee.oa.core.base.meeting.service.TeeMeetingRoomService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/meetRoomManage")
public class TeeMeetingRoomController extends BaseController{
	@Autowired
	private TeeMeetingRoomService meetingService;
	

	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 * 获取所有会议室
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllRoom")
	@ResponseBody
	public TeeJson getAllRoom(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.getAllRoom(request , model);
		return json;
	}
	
	/**
	 * 获取所有会议室
	 * @author wgg
	 * @date 2016-11-2
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getAllRoomEasyui")
	@ResponseBody
	public TeeEasyuiDataGridJson getAllRoomEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException{
		TeeMeetingRoomModel model = new TeeMeetingRoomModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return meetingService.getAllRoomEasyui(dm,request , model);
	}
	
	/**
	 * 获取所有会议室  --- 根据申请权限
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectPostMeetRoom")
	@ResponseBody
	public TeeJson selectPostMeetRoom(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.selectPostMeetRoom(request , model);
		return json;
	}
	
	
	/**
	 * 删除  byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.deleteByIdService(request , model);
		return json;
	}
	
	
	/**
	 * 删除  所有会议室
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public TeeJson deleteAll(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.deleteAllService(request , model);
		return json;
	}
	
	
	

	/**
	 * 查询 byId
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		json = meetingService.getById(request , model);
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getRecordById")
	@ResponseBody
	public TeeJson getRecordById(HttpServletRequest request , TeeMeetingRoomModel model) {
		TeeJson json = new TeeJson();
		int roomId = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = meetingService.getRecordById(roomId);
		return json;
	}
	
	
	
	/**
	 * 判断会议室下是否存在正在进行或者有预定的会议。
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/isExistUnfinishedMeeting")
	@ResponseBody
	public TeeJson  isExistUnfinishedMeeting(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid=Integer.parseInt(request.getParameter("sid"));
		json = meetingService.isExistUnfinishedMeeting(sid);
		return json;
	}
	
	/**
	 * 查看指定日期指定会议室的占用情况
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMeetroomUseage")
	@ResponseBody
	public TeeJson getMeetroomUseage(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int roomId = TeeStringUtil.getInteger(request.getParameter("roomId"), 0);
		String date = TeeStringUtil.getString(request.getParameter("date"));
		json.setRtData(meetingService.getMeetroomUseage(roomId,date));
		return json;
	}
}