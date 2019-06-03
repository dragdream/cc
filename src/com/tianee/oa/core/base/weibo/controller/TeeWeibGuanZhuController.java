package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibGuanZhuService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("TeeWeibGuanZhuController")
public class TeeWeibGuanZhuController {

	@Autowired
	private TeeWeibGuanZhuService teeWeibGuanZhuService;
	
	/**
	 * 关注
	 * */
	@ResponseBody
	@RequestMapping("/addGuanZhu")
	public TeeJson addGuanZhu(HttpServletRequest request){
		return teeWeibGuanZhuService.addGuanZhu(request);
		
	}
	
	/**
	 * 取消关注
	 * */
	@ResponseBody
	@RequestMapping("/deleteGuanZhu")
	public TeeJson deleteGuanZhu(HttpServletRequest request){
		return teeWeibGuanZhuService.deleteGuanZhu(request);
		
	}
	
	/**
	 * 查看当前登录人是否关注此人
	 * */
	@ResponseBody
	@RequestMapping("/findGuanZhu")
	public TeeJson findGuanZhu(HttpServletRequest request){
		return teeWeibGuanZhuService.findGuanZhu(request);
	}
	
	/**
	 * 统计关注人数，统计被人关注的人数，统计当前人发布的微博信息数量
	 * */
	@ResponseBody
	@RequestMapping("/countPerson")
	public TeeJson countPerson(HttpServletRequest request){
		return teeWeibGuanZhuService.countPerson(request);
	}
	
	/**
	 * 查询未被当前登录人关注的人员（查出5条）
	 * */
	@ResponseBody
	@RequestMapping("/findByPerson")
	public TeeJson findByPerson(HttpServletRequest request){
		return teeWeibGuanZhuService.findByPerson(request);
	}
	
	/**
	 * 关注（部门）
	 * */
	@ResponseBody
	@RequestMapping("/addGuanZhuDept")
	public TeeJson addGuanZhuDept(HttpServletRequest request){
		return teeWeibGuanZhuService.addGuanZhuDept(request);
	}
	
	/**
	 * 取消关注（部门）
	 * */
	@ResponseBody
	@RequestMapping("/deleteGuanZhuDept")
	public TeeJson deleteGuanZhuDept(HttpServletRequest request){
		return teeWeibGuanZhuService.deleteGuanZhuDept(request);
	}
	
	/**
	 * 添加关注（关注话题）
	 * */
	@ResponseBody
	@RequestMapping("/addGzTopic")
	public TeeJson addGzTopic(HttpServletRequest request){
		return teeWeibGuanZhuService.addGzTopic(request);
	}
	
	/**
	 * 取消关注（话题）
	 * */
	@ResponseBody
	@RequestMapping("/deleteGzTopic")
	public TeeJson deleteGzTopic(HttpServletRequest request){
		return teeWeibGuanZhuService.deleteGzTopic(request);
	}
	
	/**
	 * 判断当前登录人是否关注此话题
	 * */
	@ResponseBody
	@RequestMapping("/loginUserGzTopic")
	public TeeJson loginUserGzTopic(HttpServletRequest request){
		return teeWeibGuanZhuService.loginUserGzTopic(request);
	}
	
	/**
	 * 话题关注（数量）
	 * */
	@ResponseBody
	@RequestMapping("/countTopic")
	public TeeEasyuiDataGridJson countTopic(String topicName){
		return teeWeibGuanZhuService.countTopic(topicName);
	}
	
	/**
	 * 查询所有被当前登录人关注的信息
	 * */
	@ResponseBody
    @RequestMapping("/findGzPersonAll")	
	public TeeEasyuiDataGridJson findGzPersonAll(HttpServletRequest request,TeeDataGridModel model){
		return teeWeibGuanZhuService.findGzPersonAll(request,model);
	}
	
	/**
	 * 查询当前登录人所有粉丝信息
	 * */
	@ResponseBody
	@RequestMapping("/findFansAll")
	public TeeEasyuiDataGridJson findFansAll(HttpServletRequest request,TeeDataGridModel model){
		return teeWeibGuanZhuService.findFansAll(request,model);
	}
	
	/**
	 * 删除粉丝
	 * */
	@ResponseBody
	@RequestMapping("/deleteFans")
	public TeeJson deleteFans(HttpServletRequest request){
		return teeWeibGuanZhuService.deleteFans(request);
	}
}
