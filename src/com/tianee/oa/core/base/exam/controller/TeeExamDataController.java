package com.tianee.oa.core.base.exam.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.exam.bean.TeeExamData;
import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.bean.TeeExamRecord;
import com.tianee.oa.core.base.exam.bean.TeeExamStore;
import com.tianee.oa.core.base.exam.model.TeeExamDataModel;
import com.tianee.oa.core.base.exam.model.TeeExamInfoModel;
import com.tianee.oa.core.base.exam.model.TeeExamQuestionModel;
import com.tianee.oa.core.base.exam.service.TeeExamDataService;
import com.tianee.oa.core.base.exam.service.TeeExamInfoService;
import com.tianee.oa.core.base.exam.service.TeeExamQuestionService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeExamDataController")
public class TeeExamDataController {
	@Autowired
	TeeExamDataService examDataService;
	@Autowired
	TeeExamInfoService examInfoService;
	@Autowired
	TeeExamQuestionService questionService;
	
	@RequestMapping("/addExamData")
	@ResponseBody
	public TeeJson addExamData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		//TeeServletUtility.requestParamsCopyToObject(request, examDataModel);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		List<TeeExamQuestion> questions = examDataService.getQuestionList(examInfoId);
		/**
		 * 交卷时先把之前保存的信息删除，然后再保存
		 */
		examDataService.delExamData(person.getUuid(),examInfoId);
		
		for(TeeExamQuestion question:questions){
			TeeExamDataModel examDataModel = new TeeExamDataModel();
			String answer =TeeStringUtil.getString(request.getParameter("opt"+question.getSid()),"");
			examDataModel.setAnswer(answer);
			examDataModel.setQuestionId(question.getSid());
			examDataModel.setUserId(person.getUuid());
			examDataModel.setExamInfoId(examInfoId);
			if(answer.equals(question.getAnswer())){
				examDataModel.setScore(question.getScore());
			}else{
				examDataModel.setScore(0);
			}
			examDataService.addExamDataModel(examDataModel);
		}
		examDataService.updateExamRecord2(person.getUuid(),examInfoId);
		
		int hasZhuG=0;//是否有主观题
		for (TeeExamQuestion teeExamQuestion : questions) {
		   if(teeExamQuestion.getQType()==3){//主观题
			   hasZhuG=1;
			   break;
		   }
		}
		if(hasZhuG==0){//没有主观题  计算总分
			examDataService.updateScoreAll(examInfoId, person.getUuid());
		}
		
		json.setRtState(true);
		json.setRtMsg("交卷成功");
		return json;
	}
	
	@RequestMapping("/saveExamData")
	@ResponseBody
	public TeeJson saveExamData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		List<TeeExamQuestion> questions = examDataService.getQuestionList(examInfoId);
		/**
		 * 交卷时先把之前保存的信息删除，然后再保存
		 */
		examDataService.delExamData(person.getUuid(),examInfoId);
		
		for(TeeExamQuestion question:questions){
			TeeExamDataModel examDataModel = new TeeExamDataModel();
			String answer =TeeStringUtil.getString(request.getParameter("opt"+question.getSid()),"");
			examDataModel.setAnswer(answer);
			examDataModel.setQuestionId(question.getSid());
			examDataModel.setUserId(person.getUuid());
			examDataModel.setExamInfoId(examInfoId);
			if(answer.equals(question.getAnswer())){
				examDataModel.setScore(question.getScore());
			}else{
				examDataModel.setScore(0);
			}
			examDataService.addExamDataModel(examDataModel);
		}
		examDataService.updateScoreAll(examInfoId, person.getUuid());
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}

	@RequestMapping("/updateExamData")
	@ResponseBody
	public TeeJson updateExamData(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int questionId = TeeStringUtil.getInteger(request.getParameter("questionId"), 0);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		int answer=TeeStringUtil.getInteger(request.getParameter("score"), 0);
		TeeExamData examData = examDataService.getExamData(userId,questionId,examInfoId);
		if(examData!=null){
			TeeExamQuestion q = examData.getExamQuest();
			if(answer>q.getScore()){
				json.setRtMsg("得分不得超过试题分值！");
				json.setRtState(false);
			}else{
				examData.setScore(answer);
				examDataService.updateExamData(examData);
				json.setRtState(true);
			}
		}
		
		return json;
	}
	
	
	@RequestMapping("/updateExamRecord")
	@ResponseBody
	public TeeJson updateExamRecord(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
	    examDataService.updateExamRecord(userId,examInfoId);
	    examDataService.updateScoreAll(examInfoId, userId);
		json.setRtState(true);
		return json;
	}
	@RequestMapping("/getExamData")
	@ResponseBody
	public TeeJson getExamData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int questionId = TeeStringUtil.getInteger(request.getParameter("questionId"), 0);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		TeeExamData examData = examDataService.getExamData(userId,questionId,examInfoId);
		TeeExamDataModel model = new TeeExamDataModel();
		BeanUtils.copyProperties(examData, model);
		model.setAnswer(examData.getAnswer());
		if(examData.getExamInfo()!=null){
			model.setExamInfoId(examData.getExamInfo().getSid());
		}
		if(examData.getExamQuest()!=null){
			
			model.setQuestionId(examData.getExamQuest().getSid());
		}
		if(examData.getParticipant()!=null){
			model.setUserId(examData.getParticipant().getUuid());
		}
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getQuestionList")
	@ResponseBody
	public TeeJson getQuestionList(HttpServletRequest request){
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		TeeJson json = new TeeJson();
		List<TeeExamQuestion> questionList = examDataService.getQuestionList(examInfoId);
		List<TeeExamQuestionModel> models = new ArrayList<TeeExamQuestionModel>();
		for(TeeExamQuestion question:questionList){
			TeeExamQuestionModel model = new TeeExamQuestionModel();
			BeanUtils.copyProperties(question, model);
			models.add(model);
		}
		json.setRtData(models);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getRealScore")
	@ResponseBody
	public TeeJson getRealScore(HttpServletRequest request){
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		TeeJson json = new TeeJson();
		int realScore = examDataService.getRealScore(examInfoId,userId);
		Map map = new HashMap();
		map.put("realScore", realScore);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getExamTimes")
	@ResponseBody
	public TeeJson getExamTimes(HttpServletRequest request){
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		TeeJson json = new TeeJson();
		TeeExamPaper  paper = examDataService.getExamTimes(examInfoId);
		Map map = new HashMap();
		map.put("examTimes", paper.getExamTimes());
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getStartExamTime")
	@ResponseBody
	public TeeJson getStartExamTime(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		Calendar startTime = examDataService.getStartExamTime(examInfoId,person.getUuid());
		Calendar endTime = Calendar.getInstance();
		TeeExamInfo examInfo = examInfoService.getById(examInfoId);
		int examTimes = examInfo.getExamPaper().getExamTimes();
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeDesc ="";
		String endTimeDesc ="";
		if(startTime!=null){
			startTimeDesc=formatter.format(startTime.getTime());
			endTime = startTime;
			
		}else{
			startTimeDesc=formatter.format(cl.getTime());
			endTime = cl;
		}
		endTime.add(Calendar.MINUTE, examTimes);
		endTimeDesc=formatter.format(endTime.getTime());
		
		Map map = new HashMap();
		map.put("startExamTime",startTimeDesc);
		map.put("endExamTime",endTimeDesc);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/addExamRecord")
	@ResponseBody
	public TeeJson addExamRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		TeeExamInfo examInfo = examInfoService.getById(examInfoId);
		TeeExamRecord record = new TeeExamRecord();
		record.setTeeExamInfo(examInfo);
		record.setParticipant(person);
		Calendar cl =Calendar.getInstance();
		record.setStartExamTime(cl);
		boolean checkStatus =examDataService.isHasSubjective(examInfo.getExamPaper().getSid());
		if(checkStatus){
			record.setChecked(false);
		}else{
			record.setChecked(true);
		}
		examDataService.addExamRecord(record);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/isHasSubjective")
	@ResponseBody
	public TeeJson isHasSubjective(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamInfo examInfo = examInfoService.getById(examInfoId);
		boolean flag = examDataService.isHasSubjective(examInfo.getExamPaper().getSid());
		Map map = new HashMap();
		map.put("flag",flag);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 判断当前用户当前试卷是否已经交卷
	 * @param request
	 * @return
	 */
	@RequestMapping("/isSubExam")
	@ResponseBody
	public TeeJson isSubExam(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		boolean flag = examDataService.isSubExam(examInfoId,person.getUuid());
		Map map = new HashMap();
		map.put("flag",flag);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	
	@RequestMapping("/getStartTimes")
	@ResponseBody
	public TeeJson getStartTimes(HttpServletRequest request){
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int examInfoId = TeeStringUtil.getInteger(request.getParameter("examInfoId"), 0);
		TeeJson json = new TeeJson();
		long startTimes= examDataService.getStartTimes(examInfoId,person.getUuid());
		Map map = new HashMap();
		map.put("startTimes",startTimes);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/isSaved")
	@ResponseBody
	public TeeJson isSaved(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		boolean isSaved = examInfoService.isSaved(sid,userId);
		Map map = new HashMap();
		map.put("isSaved", isSaved);
		json.setRtData(map);
		json.setRtMsg("发布成功");
		json.setRtState(true);
		return json;
	}
}
