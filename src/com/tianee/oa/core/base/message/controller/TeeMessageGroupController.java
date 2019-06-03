package com.tianee.oa.core.base.message.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.tianee.oa.core.base.message.model.TeeMessageGroupModel;
import com.tianee.oa.core.base.message.service.TeeMessageGroupService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.cache.RedisClient;

@Controller
@RequestMapping("/messageGroupManage")
public class TeeMessageGroupController {
	@Autowired
	private TeeMessageGroupService messageGroupServ;
	
	/**
	 * 新增或者更新
	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeMessageGroupModel model) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = messageGroupServ.addOrUpdateGroup(model, person);
		return json;
	}
	
	
	
	/**
	 * 获取  byId
	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/selectById")
	@ResponseBody
	public TeeJson selectById(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		String id = request.getParameter("id");
		TeeJson json = messageGroupServ.selectById(id);
		return json;
	}
	
	
	/**
	 * 获取  根据系统登录人获取群组
	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/selectListByLoginPerson")
	@ResponseBody
	public TeeEasyuiDataGridJson selectListByLoginPerson(TeeDataGridModel dm,HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		return messageGroupServ.selectListByLoginPerson(dm,person);
	}
	
	/**
	 * 获取  根据系统登录人获取群组
	 * @param request
	 * @param message
	 * @return
	 */
	@RequestMapping("/getMyGroup")
	@ResponseBody
	public TeeJson getMyGroup(HttpServletRequest request) {
		TeePerson person = (TeePerson)request.getSession().getAttribute("LOGIN_USER");
		TeeJson json = messageGroupServ.getMyGroup(person);
		return json;
	}

	
	/**
	 * 更改状态 by Id
	 * @param  id  
	 * @param flag 状态 0 正常 1-停用
	 */
	@RequestMapping("/updateFlagById")
	@ResponseBody
	public TeeJson updateFlagById(HttpServletRequest request ) {
		String id = request.getParameter("id");
		String groupflag = request.getParameter("groupflag");
		TeeJson json = messageGroupServ.updateFlagById(id, groupflag);
		return json;
	}
	
	
	/**
	 * 删除by Id
	 * @param  id  
	 * @param flag 状态 0 正常 1-停用
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request ) {
		String id = request.getParameter("id");
		TeeJson json = messageGroupServ.delById(id);
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
	    
		Jedis jedis = null;
		List<String> msgList = new ArrayList();
		
		json.setRtData(msgList);
		json.setRtState(true);
		return json;
	}
	
	
	public void setMessageGroupServ(TeeMessageGroupService messageGroupServ) {
		this.messageGroupServ = messageGroupServ;
	}
	
	
}


	
	
	