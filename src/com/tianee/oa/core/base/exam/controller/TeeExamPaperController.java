package com.tianee.oa.core.base.exam.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.model.TeeExamPaperModel;
import com.tianee.oa.core.base.exam.service.TeeExamPaperService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeExamPaperController")
public class TeeExamPaperController {
	@Autowired
	TeeExamPaperService examPaperService;
	
	@RequestMapping("/addExamPaper")
	@ResponseBody
	public TeeJson addExamPaper(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeExamPaperModel examPaperModel = new TeeExamPaperModel();
		TeeServletUtility.requestParamsCopyToObject(request, examPaperModel);
		examPaperService.addExamPaperModel(examPaperModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editExamPaper")
	@ResponseBody
	public TeeJson editExamPaper(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeExamPaperModel examPaperModel = new TeeExamPaperModel();
		TeeServletUtility.requestParamsCopyToObject(request, examPaperModel);
		examPaperService.updateExamPaperModel(examPaperModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delExamPaper")
	@ResponseBody
	public TeeJson delExamPaper(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamPaper examPaper = examPaperService.getById(sid);
		examPaperService.deleteExamPaper(examPaper);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getExamPaper")
	@ResponseBody
	public TeeJson getExamPaper(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamPaper examPaper = examPaperService.getById(sid);
		TeeExamPaperModel model = new TeeExamPaperModel();
		BeanUtils.copyProperties(examPaper, model);
		model.setStoreId(examPaper.getExamStore().getSid());
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return examPaperService.datagrid(dm, requestDatas);
	}
	
	

	@RequestMapping("/getQuestionList")
	@ResponseBody
	public TeeEasyuiDataGridJson getQuestionList(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return examPaperService.getQuestionList(dm, requestDatas);
	}

/*	*//**
	 *获取所有的试题信息（从题库）
	 * @param request
	 * @return
	 *//*
	@RequestMapping("/getQuestionList")
	@ResponseBody
	public TeeJson getQuestionList(HttpServletRequest request){
		int paperId = TeeStringUtil.getInteger(request.getParameter("paperId"), 0);
		TeeJson json = new TeeJson();
		List<TeeExamQuestion> questionList = examPaperService.getQuestionList(paperId);
		List<TeeExamQuestionModel> models = new ArrayList<TeeExamQuestionModel>();
		for(TeeExamQuestion question:questionList){
			TeeExamQuestionModel model = new TeeExamQuestionModel();
			BeanUtils.copyProperties(question, model);
			if(examPaperService.isChecked(paperId, question.getSid())){
				model.setCheck(true);
			}else{
				model.setCheck(false);
			}
			models.add(model);
		}
		json.setRtData(models);
		json.setRtState(true);
		return json;
	}*/
	
	/**
	 * 自动选题
	 * @param request
	 * @return
	 */
	@RequestMapping("/autoSelectQuestion")
	@ResponseBody
	public TeeJson autoSelectQuestion(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		examPaperService.autoSelectQuestion(sid);
		json.setRtState(true);
		json.setRtMsg("自动选题成功");
		return json;
	}
	
	/**
	 * 自动选题
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveSelectedQuestion")
	@ResponseBody
	public TeeJson saveSelectedQuestion(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int paperId = TeeStringUtil.getInteger(request.getParameter("paperId"), 0);
		String selectedList = TeeStringUtil.getString(request.getParameter("selectedList"),"");
		examPaperService.saveSelectedQuestion(selectedList,paperId);
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
}
