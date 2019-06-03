package com.tianee.oa.core.base.weibo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.weibo.service.TeeWeibPublishService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("TeeWeibPublishController")
public class TeeWeibPublishController {

	@Autowired
	private TeeWeibPublishService teeWeibPublishService;
	
	/**
	 * 发布微博信息
	 * */
	@ResponseBody
	@RequestMapping("/addPublish")
	public TeeJson addPublish(HttpServletRequest request){
		return teeWeibPublishService.addPublish(request);
	}
	
	/**
	 * 获取所有的微博信息（广场）
	 * */
	@ResponseBody
	@RequestMapping("/findAll")
	public TeeEasyuiDataGridJson findAll(TeeDataGridModel dm,HttpServletRequest request){
		return teeWeibPublishService.findAll(dm,request);
	}
	
	/**
	 * 获取所有的微博信息（收藏）
	 * */
	@ResponseBody
	@RequestMapping("/findCollectAll")
	public TeeEasyuiDataGridJson findCollectAll(TeeDataGridModel dm,HttpServletRequest request){
		return teeWeibPublishService.findCollectAll(dm,request);
	}
	
	
	/**
	 * 获取所有的微博信息（收藏）
	 * */
	@ResponseBody
	@RequestMapping("/findCollectAll2")
	public TeeEasyuiDataGridJson findCollectAll2(HttpServletRequest request){
		return teeWeibPublishService.findCollectAll2(request);
	}
	
	/**
	 * 获取所有部门
	 * */
	@ResponseBody
	@RequestMapping("/allDept")
	public TeeJson allDept(HttpServletRequest request){
		return teeWeibPublishService.allDept(request);
	}
	
   /**
    * 搜索部门
    * */
	@ResponseBody
	@RequestMapping("/searchDept")
	public TeeJson searchDept(HttpServletRequest request){
		return teeWeibPublishService.searchDept(request);
	}
	
	/**
	 * 根据微博信息id查询微博信息
	 * */
	@ResponseBody
	@RequestMapping("/findPublish")
	public TeeJson findPublish(int sid){
		return teeWeibPublishService.findPublish(sid);
	}
	
	/**
	 * 查询关于此换题的所有的微博信息
	 * */
	@ResponseBody
	@RequestMapping("/findTopicAll")
	public TeeEasyuiDataGridJson findTopicAll(HttpServletRequest request,TeeDataGridModel dm){
		return teeWeibPublishService.findTopicAll(request,dm);
	}
	
	/**
	 * 查询话题阅读的次数
	 * */
	@ResponseBody
	@RequestMapping("/readTopicCount")
	public TeeJson readTopicCount(String content){
		return teeWeibPublishService.readTopicCount(content);
	}
	
	/**
	 * 删除关于此话题讨论的微博
	 * */
	@ResponseBody
	@RequestMapping("/deletePublish")
	public TeeJson deletePublish(String content,int sid){
		return teeWeibPublishService.deletePublish(content,sid);
	}
	/**
	 * 查询所有的话题
	 * */
	@ResponseBody
	@RequestMapping("/findTopicPage")
	public TeeEasyuiDataGridJson findTopicPage(TeeDataGridModel dm){
		return teeWeibPublishService.findTopicPage(dm);
	}
	
	/**
	 * 查询话题有多少页
	 * */
	@ResponseBody
	@RequestMapping("/pageCount")
	public TeeJson pageCount(){
		return teeWeibPublishService.pageCount();
	}
	
	/**
	 * 根据微博id删除微博信息
	 * */
	@ResponseBody
	@RequestMapping("/deletePublishById")
	public TeeJson deletePublishById(int sid){
		return teeWeibPublishService.deletePublishById(sid);
	}
	 
	/**
	 * 根据微博id查询微博具体信息
	 * */
	@ResponseBody
	@RequestMapping("/findWeibInfo")
	public TeeJson findWeibInfo(HttpServletRequest request,int sid){
		return teeWeibPublishService.findWeibInfo(request,sid);
	}
	
	/**
	 * 查询关于此换题的所有的微博信息
	 * */
	@ResponseBody
	@RequestMapping("/findTopicAll2")
	public TeeEasyuiDataGridJson findTopicAll2(HttpServletRequest request){
		return teeWeibPublishService.findTopicAll2(request);
	}
}
