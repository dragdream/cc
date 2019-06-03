package com.tianee.oa.core.base.message.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.message.bean.TeeMessage;
import com.tianee.oa.core.base.message.model.TeeMessageModel;
import com.tianee.oa.core.base.message.service.TeeMessageService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.socket.MessagePusher;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;


@Controller
@RequestMapping("/messageManage")
public class TeeMessageController extends BaseController {

	@Autowired
	private TeeMessageService messageServ;
	
	
	/**
	 * 新增
	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addMessage(HttpServletRequest request , TeeMessageModel message) {
		String toIds = TeeStringUtil.getString(request.getParameter("toIds"), "") ;
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = new TeeJson();
		messageServ.addMessage(message , toIds , person);
		json.setRtData(message);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * byId 查询
	 * @param request
	 * @return
	 */
	@RequestMapping("selectById")
	@ResponseBody
	public TeeJson selectById(HttpServletRequest request) {
		String sid = request.getParameter("sid");
		TeeJson json = new TeeJson();
		if(TeeUtility.isNullorEmpty(sid)){
			TeeMessage message = messageServ.selectById(sid);
			json.setRtData(message);
			json.setRtState(true);
		}
		
		json.setRtState(false);
		json.setRtMsg("没有相关记录");
		return json;
	}
	
	
	
	/**
	 * by 收信人 查询
	 * @param request
	 * @return
	 */
	@RequestMapping("selectByFromId")
	@ResponseBody
	public TeeJson selectByFromId(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		int personId = person.getUuid();
		TeeJson json = new TeeJson();
		List<TeeMessage> list = messageServ.selectByFromId(person.getUserId());
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}

	
	/**
	 * 获取通未确认用个列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getComfireNoList")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm ,HttpServletRequest request) {
		return messageServ.datagrid(dm,request);
	}
	
	
	/**
	 * 获取与人员对话通用列表
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getMessageListByPersonId")
	@ResponseBody
	public TeeEasyuiDataGridJson getMessageListByPersonId(TeeDataGridModel dm ,HttpServletRequest request) {
		return messageServ.getMessageListByPersonId(dm,request);
	}
	
	
	/**
	 * 消息查询
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getQueryMessage")
	@ResponseBody
	public TeeEasyuiDataGridJson getQueryMessage(TeeDataGridModel dm ,TeeMessageModel model ,HttpServletRequest request) {
		return messageServ.getQueryMessage(dm,model , request);
	}
	
	
	/**
	 * by sids 以逗号分隔
	 * @param request
	 * @return
	 */
	@RequestMapping("delByIds")
	@ResponseBody
	public TeeJson delByIds(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String ids = TeeStringUtil.getString(request.getParameter("ids"));
		int personId = person.getUuid();
		TeeJson json = new TeeJson();
		long count = messageServ.delBySids(ids);
		json.setRtData(count);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 灯芯删除状态
	 * @param request
	 * @return
	 */
	@RequestMapping("updateDeleteFlag")
	@ResponseBody
	public TeeJson updateDeleteFlag(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String ids = TeeStringUtil.getString(request.getParameter("ids"));
		TeeJson json = new TeeJson();
		long count = messageServ.updateDeleteFlag(ids , person );
		json.setRtData(count);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 发送消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendMessage.action")
	@ResponseBody
	public TeeJson sendMessage(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String toUserId = TeeStringUtil.getString(request.getParameter("toUserId"));
	    String content = TeeStringUtil.getString(request.getParameter("content"));
	    String fontSize = TeeStringUtil.getString(request.getParameter("fontSize"));
	    String fontFmly = TeeStringUtil.getString(request.getParameter("fontFmly"));
	    String dvc = TeeStringUtil.getString(request.getParameter("dvc"));
	    
	    Map data = new HashMap();
	    data.put("t", "1");
	    data.put("from", person.getUserId());
	    data.put("from_", person.getUserName());
	    data.put("to", toUserId);
	    if(toUserId.endsWith(",")){
	    	data.put("to", toUserId.substring(0, toUserId.length()-1));
	    }
	    data.put("time", TeeDateUtil.format(new Date()));
	    data.put("c", content);
	    data.put("fs", fontSize);
	    data.put("ff", fontFmly);
	    data.put("url", "");
	    data.put("msgId", UUID.randomUUID().toString().replace("-", ""));
	    MessagePusher.push2Im(data);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 启动时获取message消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOfflineMessages.action")
	@ResponseBody
	public TeeJson getOfflineMessages(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<String> msgList = new ArrayList();
		json.setRtData(msgList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据userId设置已读状态
	 * @param request
	 * @return
	 */
	@RequestMapping("/readAllByUserId.action")
	@ResponseBody
	public TeeJson readAllByUserId(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String from = TeeStringUtil.getString(request.getParameter("from"));
		messageServ.readAllByUserId(loginUser.getUserId(),from);
		json.setRtState(true);
		return json;
	}
	
	public void setMessageServ(TeeMessageService messageServ) {
		this.messageServ = messageServ;
	}
	
	
}



	