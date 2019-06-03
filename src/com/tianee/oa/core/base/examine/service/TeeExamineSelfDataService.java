package com.tianee.oa.core.base.examine.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.examine.bean.TeeExamineGroup;
import com.tianee.oa.core.base.examine.bean.TeeExamineSelfData;
import com.tianee.oa.core.base.examine.bean.TeeExamineTask;
import com.tianee.oa.core.base.examine.dao.TeeExamineGroupDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineSelfDataDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineTaskDao;
import com.tianee.oa.core.base.examine.model.TeeExamineSelfDataModel;
import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 */
@Service
public class TeeExamineSelfDataService  extends TeeBaseService{


	@Autowired
	private TeeExamineSelfDataDao examineSelfDataDao;
	
	@Autowired
	private TeeExamineTaskDao examineTaskDao;
	
	
	
	@Autowired
	private TeeExamineGroupDao examineGroupDao;
	
	@Autowired
	private TeePersonDao personDao;



	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeExamineSelfDataModel model) throws IOException, ParseException {
		TeeJson json = new TeeJson();
		TeeExamineSelfData item = new TeeExamineSelfData();
		BeanUtils.copyProperties(model, item);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		
		TeeExamineTask task = examineTaskDao.getById(taskId);
		Date date = new Date();
		item.setSelfDate(date.getTime());
		item.setParticipant(person);
		item.setTask(task);
		
		//累加得分
		List<Map<String,String>> datas = TeeJsonUtil.JsonStr2MapList(model.getSelfData());
		double score = 0;
		for(Map data:datas){
			score+=TeeStringUtil.getDouble(data.get("score"), 0.0);
		}
		
		List<TeeExamineSelfData> list = examineSelfDataDao.getSelftData(taskId, person.getUuid());
		if(list.size() > 0){
			TeeExamineSelfData meetRoom  = list.get(0);
			if(meetRoom != null){
				int sid = meetRoom.getSid();
				BeanUtils.copyProperties(item, meetRoom);
				meetRoom.setSid(sid);
				meetRoom.setScore(score);
				examineSelfDataDao.updateObj(meetRoom);
			}else{
				json.setRtState(false);
				json.setRtMsg("该自评已被删除！");
				return json;
			}
		}else{
			item.setScore(score);
			examineSelfDataDao.add(item);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	
	}
	
	/**
	 * 获取自评的考核任务
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	public TeeJson getCurrUserSelf(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String selfType = TeeStringUtil.getString(request.getParameter("selfType") , "");
		List<TeeExamineTask> list =  examineTaskDao.getCurrUserSelfTask(person , selfType);
		List<TeeExamineTaskModel> modelList = new ArrayList<TeeExamineTaskModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeExamineTask task = list.get(i);
			
			String IsSelfAssessment = "0";
			if(selfType.equals("0")){
				List<TeeExamineSelfData> selfDataList = task.getSelfData();
				for (int j = 0; j <selfDataList.size(); j++) {
					TeePerson par = selfDataList.get(j).getParticipant();
					if(par.getUuid() == person.getUuid()){
						IsSelfAssessment = "1";//已自评
						break;
					}
				}
			}
			
			TeeExamineTaskModel taskModel = TeeExamineTaskService.parseModel(task, false,person);
			taskModel.setIsSelfAssessment(IsSelfAssessment);
			modelList.add(taskModel);
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public static TeeExamineSelfDataModel parseModel(TeeExamineSelfData room , boolean isSimple){
		TeeExamineSelfDataModel model = new TeeExamineSelfDataModel();
		if(room == null){
			return model;
		}
		
		BeanUtils.copyProperties(room, model);
		model.setParticipantId(room.getParticipant().getUuid() + "");
		model.setParticipantName(room.getParticipant().getUserName());
		Date date = new Date();
		date.setTime(room.getSelfDate());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.setSelfDateStr(TeeUtility.getDateTimeStr(date, format));
		return model;
	}
	
	
	/**
	 * 
	 * @author syl 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request, TeeExamineSelfDataModel model) {
		TeeJson json = new TeeJson();
		
		if(model.getSid() > 0){
			examineSelfDataDao.delById(model.getSid());
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 查询 BYId
	 * @author syl
	 * @date 2014-5-30
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeExamineSelfDataModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeExamineSelfData selfData = examineSelfDataDao.getById(model.getSid());
			if(selfData != null){
				TeeExamineSelfDataModel modelTemp = parseModel(selfData, false);
				json.setRtData(modelTemp);
				json.setRtState(true);
			}
		}
		json.setRtMsg("数据未找到！");
		return json;
	}
	/**
	 * 根据任务Id  获取自评信息 
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getSelfDataByTask(HttpServletRequest request, TeeExamineSelfDataModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		List<TeeExamineSelfData> list = examineSelfDataDao.getSelftData(taskId, person.getUuid());
		if(list.size() > 0){
			json.setRtData(parseModel(list.get(0), false));
			json.setRtState(true);
			json.setRtMsg("查询成功!");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("该记录已被删除!");
	
		return json;
	}
	
	
}
