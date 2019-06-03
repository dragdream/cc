package com.tianee.oa.core.base.meeting.controller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.swetake.util.Qrcode;
import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.base.meeting.service.TeeMeetingAttendConfirmService;
import com.tianee.oa.core.base.meeting.service.TeeMeetingService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/meetManage")
public class TeeMeetingController{
	@Autowired
	private TeeMeetingService meetingService;
	
	@Autowired
	private TeeMeetingAttendConfirmService attendConfirmService;
	
	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		TeeMeetingModel model = new TeeMeetingModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		if(model.getSid()>0){
			json = meetingService.updateObj(request , model);
		}else{
			json = meetingService.addObj(request , model);
		}
//		json = meetingService.addOrUpdate(request , model);
		return json;
	}
	
	@RequestMapping("/addSummary")
	@ResponseBody
	public TeeJson addSummary(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		TeeMeetingModel model = new TeeMeetingModel();
		//将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.addSummary(request, model);
		return json;
	}
	/**
	 * 拖拉更改会议信息
	 * @author syl
	 * @date 2014-2-15
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateChangeMeet")
	@ResponseBody
	public TeeJson updateChangeMeet(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.updateChangeMeet(request , model);
		return json;
	}
	
	
	/**
	 * 获取系统当前登录人 所有申请会议记录
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getPersonalAllMeet")
	@ResponseBody
	public TeeJson getPersonalAllMeet(HttpServletRequest request ) throws ParseException {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getPersonalAllMeet(request , model);
		return json;
	}
	
	/**
	 * 获取所有数据  byTime
	 * @author syl
	 * @date 2014-2-17
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getAllMeetByTime")
	@ResponseBody
	public TeeJson getAllMeetByTime(HttpServletRequest request ) throws ParseException {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getAllMeetByTime(request , model);
		return json;
	}
	
	
	
	/**
	 * 获取系统当前登录人  申请会议记录    --- 根据会议状态
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getPersonalMeetByStatus")
	@ResponseBody
	public TeeJson getPersonalMeetByStatus(HttpServletRequest request ) throws ParseException {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getPersonalMeetByStatus(request , model);
		return json;
	}
	
	
	/**
	 * 申请会议记录    --- 根据会议状态
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getPersonalMeetByStatusEasyui.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getPersonalMeetByStatusEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return meetingService.getPersonalMeetByStatusEasyui(dm,request , model);
	}
	
	
	/**
	 * 获取系统当前登录人  会议审批记录    --- 根据会议状态
	 * @author syl
	 * @date 2014-1-30
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getLeaderMeetByStatus")
	@ResponseBody
	public TeeJson getLeaderMeetByStatus(HttpServletRequest request ) throws ParseException {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getLeaderMeetByStatus(request , model);
		return json;
	}
	/**
	 * 申请会议记录    --- 根据会议状态
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/getLeaderMeetByStatusEasyui.action")
	@ResponseBody
	public TeeEasyuiDataGridJson getLeaderMeetByStatusEasyui(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return meetingService.getLeaderMeetByStatusEasyui(dm,request , model);
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
	public TeeJson deleteById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.deleteByIdService(request , model);
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
	public TeeJson getById(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getById(request , model);
		return json;
	}
	
	/**
	 * 查询会议纪要
	 * @param request
	 * @return
	 */
	@RequestMapping("/getById2")
	@ResponseBody
	public TeeJson getById2(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getById2(request , model);
		return json;
	}
	
	
	/**
	 * 审批 ---批准/未批准
	 * @author syl
	 * @date 2014-2-15
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/approve")
	@ResponseBody
	public TeeJson approve(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.approve(request , model);
		return json;
	}
	
	/**
	 * 获取各种会议会议管理  数量
	 * @author syl
	 * @date 2014-2-15
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getLeaderApproveCount")
	@ResponseBody
	public TeeJson getLeaderApproveCount(HttpServletRequest request ) {
		TeeJson json = new TeeJson();
		//将request中的对应字段映值射到目标对象的属性中
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = meetingService.getLeaderApproveCount(request , model);
		return json;
	}
	
	/**
	 * 会议查询  ---  获取通用列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/queryList.action")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm ,HttpServletRequest request) throws ParseException {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return meetingService.datagrid(dm,request , model);
	}

	
	@RequestMapping("/getMyMeeting")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyMeeting(TeeDataGridModel dm ,HttpServletRequest request) throws Exception {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return meetingService.getMyMeeting(dm,request,model);
	}
	
	
	@RequestMapping("/getSummary")
	@ResponseBody
	public TeeEasyuiDataGridJson getSummary(TeeDataGridModel dm ,HttpServletRequest request) throws Exception {
		TeeMeetingModel model = new TeeMeetingModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return meetingService.getSummary(dm,request,model);
	}
	
	@RequestMapping("/addMeetingTopic")
	@ResponseBody
	public TeeJson addMeetingTopic(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		int meetingSid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String topicContent = TeeStringUtil.getString(request.getParameter("content"), "");
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = meetingService.addMeetingTopic(request , meetingSid,topicContent,loginPerson);
		return json;
	}
	@RequestMapping("/getMeetingTopic")
	@ResponseBody
	public TeeJson getMeetingTopic(HttpServletRequest request ) throws ParseException {
		TeeJson json = new TeeJson();
		int meetingSid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = meetingService.getMeetingTopic(request , meetingSid);
		return json;
	}
	
	
	@RequestMapping("/download")
	public void dowload(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		int meetingSid = TeeStringUtil.getInteger(request.getParameter("sid"),
				0);
		TeeMeetingModel meetmodel = meetingService.selectMeeting(meetingSid);
		
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		response.setContentType("text/html");
		response.setCharacterEncoding("gb2312");
		response.setHeader("Cache-control", "private");
		response.setContentType("application/octet-stream");
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Cache-Control", "maxage=3600");
		response.setHeader("Pragma", "public");
		response.setHeader(
				"Content-disposition",
				"attachment; filename=\""
						+ URLEncoder.encode("会议信息导出[" + meetmodel.getMeetName()
								+ "].html", "UTF-8") + "\"");

		PrintWriter out = response.getWriter();
        out.print("<div class='table-c'>");
		out.print("<table width='950' border='0' cellspacing='0' cellpadding='0'>");
		out.print("<tr>");
		out.print("<td class='td1'>");
		out.print("会议名称:");
		out.print("</td>");
		out.print("<td class='td2'>");
		out.print(meetmodel.getMeetName());
		out.print("</td>");
		out.print("</tr>");

		out.print("<tr>");
		out.print("<td class='td1'>");
		out.print("会议主题:" );
		out.print("</td>");
		out.print("<td class='td2'>");
		out.print( meetmodel.getSubject());
		out.print("</td>");
		out.print("</tr>");

		out.print("<tr>");
		out.print("<td class='td1'>");
		out.print("会议时间:" );
		out.print("</td>");
		out.print("<td class='td2'>");
		out.print(  meetmodel.getStartTimeStr() + "至"
				+ meetmodel.getEndTimeStr());
		out.print("</td>");
		out.print("</tr>");

//		out.print("<tr>");
//		out.print("<td class='td1'>");
//		out.print("参会人:" );
//		out.print("</td>");
//		out.print("<td class='td2'>");
//		out.print( meetmodel.getAttendeeName());
//		out.print("</td>");
//		out.print("</tr>");
		
		out.print("<tr>");
		out.print("<td class='td1'>");
		out.print("参会人:" );
		out.print("</td>");
		out.print("<td class='td2'>");
//		out.print( meetmodel.getAttendeeName());
		Map requestMap = new HashMap();
		requestMap.put("meetingId", meetingSid);
		TeeJson json = attendConfirmService.showMeetingAttendInfo(requestMap, person);
		List<Map> datas = (List<Map>) json.getRtData();
		for(Map data:datas){
			if("1".equals(data.get("attendFlag"))){
				out.print(data.get("userName")+",");
			}
		}
		
		out.print("</td>");
		out.print("</tr>");

		out.print("<tr>");
		out.print("<td class='td1'>");
		out.print("会议描述:" );
		out.print("</td>");
		out.print("<td class='td2'>");
		out.print( meetmodel.getMeetDesc());
		out.print("</td>");
		out.print("</tr>");

		out.print("<tr>");
		out.print("<td class='td1'>");
		out.print("<button onclick='javascript:show();'>");
		out.print("点击后展开会议纪要");
		out.print("</button>");
		out.print("</td>");
		
		out.print("<td >");
		out.print("<div id='hyjy' style='display:none'>");
		out.print(meetmodel.getSubmary()); // 查出当前会议下的会议纪要能容
		out.print("</div>");
		out.print("</td>");
		
		out.print("</tr>");
		out.print("</table>");
		out.print("</div>");

		

		out.print("<script type='text/javascript' charset='UTF-8'>");
		out.print("function show(){"
				+ "if(document.getElementById('hyjy').style.display == 'none'){"
				+ "document.getElementById('hyjy').style.display= 'block';"
				+ "}else{"
				+ "document.getElementById('hyjy').style.display = 'none';"
				+ "}" + "}");

		out.print("</script >");
		
		out.print("<style type='text/css'>");
		out.print(".table-c table{border-right:1px solid gray;border-bottom:1px solid gray;margin-left:100px;}");
		out.print(".table-c table td{border-left:1px solid gray;border-top:1px solid gray; }");
		out.print(".td1{background-color:lightgrey; width:140; height:35}");
		out.print(".td2{width:810; height:35}");
		
		out.print("</style>");
			
        }
      
	
	/**
	 * 我的会议    二维码生成
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/qrCodeDownload")
	public void qrCodeDownload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//获取当前登录的用户
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    //获取会议主键
		int meetingId=TeeStringUtil.getInteger(request.getParameter("meetingId"), 0);
		Qrcode rcode = new Qrcode();
		rcode.setQrcodeVersion(6); // 这个值最大40，值越大可以容纳的信息越多，够用就行了
		
		String url ="/system/mobile/phone/meeting/meetingDetail.jsp?meetingId="+meetingId;
		byte[] content = null;
		content = url.getBytes("utf-8");
		BufferedImage bufImg = new BufferedImage(127, 127,
				BufferedImage.TYPE_INT_RGB); // 图片的大小
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, 127, 127);
		gs.setColor(Color.BLACK);

		// 输出内容> 二维码
		if (content.length > 0 && content.length < 800) {
			boolean[][] codeOut = rcode.calQrcode(content);
			for (int i = 0; i < codeOut.length; i++) {
				for (int j = 0; j < codeOut.length; j++) {
					if (codeOut[j][i]) {
						gs.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		} else {
		}
		gs.dispose();
		bufImg.flush();
		
		response.setHeader("Pragma", "no-cache");         
        response.setHeader("Cache-Control", "no-cache");         
        response.setDateHeader("Expires", 0);         
        response.setContentType("image/jpeg");         
		
        // 将图像输出到Servlet输出流中。         
        ServletOutputStream sos = response.getOutputStream();         
        ImageIO.write(bufImg, "jpeg", sos);         
        sos.close();         
	}	
	
	
	
	/**
	 * 获取会议室审批人集合
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMeetroomAuditors")
	@ResponseBody
	public TeeJson getMeetroomAuditors(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		json.setRtData(meetingService.getMeetroomAuditors(0));
		return json;
	}
	
	/**
	 * 结束会议室的使用
	 * */
	@ResponseBody
	@RequestMapping("/jieShuMeeting")
	public TeeJson jieShuMeeting(HttpServletRequest request){
		return meetingService.jieShuMeeting(request);
	}
	
	
}
