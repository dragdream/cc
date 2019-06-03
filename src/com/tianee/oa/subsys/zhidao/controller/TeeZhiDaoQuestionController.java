package com.tianee.oa.subsys.zhidao.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.zhidao.service.TeeZhiDaoQuestionService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/zhiDaoQuestionController")
public class TeeZhiDaoQuestionController {

	@Autowired
	private TeeZhiDaoQuestionService  zhiDaoQuestionService;
	
	/**
	 * 新建/编辑问题
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.addOrUpdate(request);
	}
	
	
	/**
	 * 获取所有相关问题的子总页数
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getRelationsTotalPages")
	@ResponseBody
	public TeeJson getRelationsTotalPages(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getRelationsTotalPages(request);
	}
	
	
	/**
	 * 根据页码和标题 获取相关问题
	 */
	@RequestMapping("/getRelationsByTitle")
	@ResponseBody
	public TeeJson getRelationsByTitle(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getRelationsByTitle(request);
	}
	
	
	/**
	 * 获取已解决和待解决的问题数量
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getHandledAndNoHandledCount")
	@ResponseBody
	public TeeJson getHandledAndNoHandledCount(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getHandledAndNoHandledCount(request);
	}
	
	/**
	 * 获取我创建的问题
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/getMyQuestion")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyQuestion(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getMyQuestion(request,dm);
	}
	
	
	/**
	 * 获取我的参与
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getMyParticipate")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyParticipate(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getMyParticipate(request,dm);
	}
	
	/**
	 * 获取待解决的问题   所有
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getNoHandledQuestion")
	@ResponseBody
	public TeeEasyuiDataGridJson getNoHandledQuestion(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getNoHandledQuestion(request,dm);
	}
	
	
	/**
	 * 获取最近已解决的问题
	 * @param dm
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getHandledQuestion")
	@ResponseBody
	public TeeEasyuiDataGridJson getHandledQuestion(TeeDataGridModel dm,HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getHandledQuestion(request,dm);
	}
	
	
	
	/***
	 * 根据主键删除
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/delBySid")
	@ResponseBody
	public TeeJson delBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.delBySid(request);
	}
	
	
	/**
	 * 根据主键获取详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getInfoBySid")
	@ResponseBody
	public TeeJson getInfoBySid(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getInfoBySid(request);
	}
	
	
	/**
	 * 采纳
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/adopt")
	@ResponseBody
	public TeeJson adopt(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.adopt(request);
	}
	
	/**
	 * 改变问题的点击量
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/updateClick")
	@ResponseBody
	public TeeJson updateClick(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.updateClick(request);
	}
	
	
	/**
	 * 获取检索结果
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	@RequestMapping("/getSearchResult")
	@ResponseBody
	public TeeEasyuiDataGridJson getSearchResult(HttpServletRequest request , HttpServletResponse response) throws ParseException, IOException {	
		return zhiDaoQuestionService.getSearchResult(request);
	}
}
