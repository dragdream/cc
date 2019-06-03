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
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.base.examine.bean.TeeExamineData;
import com.tianee.oa.core.base.examine.bean.TeeExamineGroup;
import com.tianee.oa.core.base.examine.bean.TeeExamineItem;
import com.tianee.oa.core.base.examine.bean.TeeExamineSelfData;
import com.tianee.oa.core.base.examine.bean.TeeExamineTask;
import com.tianee.oa.core.base.examine.dao.TeeExamineDataDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineGroupDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineItemDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineSelfDataDao;
import com.tianee.oa.core.base.examine.dao.TeeExamineTaskDao;
import com.tianee.oa.core.base.examine.model.TeeExamineDataModel;
import com.tianee.oa.core.base.examine.model.TeeExamineItemModel;
import com.tianee.oa.core.base.examine.model.TeeExamineSelfDataModel;
import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
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
public class TeeExamineDataService  extends TeeBaseService{


	@Autowired
	private TeeExamineDataDao examineDataDao;
	
	@Autowired
	private TeeExamineTaskDao examineTaskDao;
	
	@Autowired
	private TeeExamineSelfDataDao examineSelfDataDao;
	
	@Autowired
	private TeeExamineItemDao examineItemDao;
	
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
	public TeeJson addOrUpdate(HttpServletRequest request, TeeExamineDataModel model) throws IOException, ParseException {
		TeeJson json = new TeeJson();
		TeeExamineData data = new TeeExamineData();
		BeanUtils.copyProperties(model, data);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		int participantId = TeeStringUtil.getInteger(model.getParticipantId() , 0);// 被考核人
		TeePerson parPerson = personDao.selectPersonById(participantId);
		
		
		TeeExamineTask task = examineTaskDao.getById(taskId);
		Date date = new Date();
		data.setExamineDate(date.getTime());
		
		data.setParticipant(parPerson);
		data.setTask(task);
		data.setRankman(person);
		
		//累加得分
		List<Map<String,String>> datas = TeeJsonUtil.JsonStr2MapList(model.getExamineData());
		double score = 0;
		for(Map data0:datas){
			score+=TeeStringUtil.getDouble(data0.get("score"), 0.0);
		}
		
		List<TeeExamineData> list = examineDataDao.getExamineData(taskId, person.getUuid() ,participantId);
		if(list.size() > 0){
			TeeExamineData meetRoom  = list.get(0);
			if(meetRoom != null){
				int sid = meetRoom.getSid();
				BeanUtils.copyProperties(data, meetRoom);
				meetRoom.setSid(sid);
				meetRoom.setScore(score);
				examineDataDao.updateObj(meetRoom);
			}else{
				json.setRtState(false);
				json.setRtMsg("该自评已被删除！");
				return json;
			}
		}else{
			data.setScore(score);
			examineDataDao.add(data);
		}
		

		//
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	
	}
	

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeExamineDataModel parseModel(TeeExamineData room , boolean isSimple){
		TeeExamineDataModel model = new TeeExamineDataModel();
		if(room == null){
			return model;
		}
		BeanUtils.copyProperties(room, model);
		
		Date date = new Date();
		date.setTime(room.getExamineDate());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.setExamineDateStr(TeeUtility.getDateTimeStr(date, format));
		return model;
	}

	
	
	/**
	 * 获取考核数据
	 * @author syl
	 * @date 2014-5-28
	 * @param request
	 * @return
	 */
	public TeeJson getSelfData(HttpServletRequest request){
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int taskId = TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		//任务对象
		TeeExamineTask task = examineTaskDao.getById(taskId);
		
		if(task == null){
			json.setRtState(false);
			json.setRtMsg("该考核任务已被删除");
			return json;
		}
		Map map = new HashMap();
		TeeExamineDataModel dataModel =  new TeeExamineDataModel();
		
		List<TeeExamineItemModel> itemModelList = new ArrayList<TeeExamineItemModel>();
		boolean isSelfData = true;
		TeeExamineSelfDataModel selfData = new TeeExamineSelfDataModel();
		if(userId > 0  && task.getIsSelfAssessment().equals("1")){//是否需要自评
			List<TeeExamineSelfData> selfDataList = examineSelfDataDao.getSelftData(taskId, userId);
			if(selfDataList.size() == 0){
				isSelfData = false;
			}else{
				selfData = TeeExamineSelfDataService.parseModel(selfDataList.get(0),false);
			}
		}
		
		if(isSelfData && userId > 0){//自评且选择人员是 查询考核指标集
			TeeExamineGroup group = task.getGroup();
			List<TeeExamineItem> list = examineItemDao.getAllByGroupId(group.getSid());
			for (int i = 0; i < list.size(); i++) {
				itemModelList.add(TeeExamineItemService.parseModel(list.get(i), false));
			}
			
			List<TeeExamineData> dataList = examineDataDao.getExamineData(taskId,person.getUuid() , userId );
			if(dataList.size() > 0){
				TeeExamineData data = dataList.get(0);
				dataModel = parseModel(data, false);
			}
			//itemModelList 
		}
		
		map.put("task", TeeExamineTaskService.parseModel(task, true, person) );
		map.put("data", dataModel);
		map.put("isSelfData", isSelfData);
		map.put("selfData", selfData);
		map.put("itemList", itemModelList);
		json.setRtData(map);
		json.setRtState(true);
		return json;
	}
	
	
	
	/**
	 * 获取有权限的考核任务
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @return
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getPostExamineTask(TeeDataGridModel dm,HttpServletRequest request , TeeExamineTaskModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map mapCount = examineTaskDao.getPostExamineTask(loginPerson, false , 0 , 0 ,dm);
		j.setTotal(TeeStringUtil.getLong(mapCount.get("count") , 0));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
		Map mapData = examineTaskDao.getPostExamineTask(loginPerson,true, firstIndex ,dm.getRows() ,dm);
		List<TeeExamineTask> list = (List<TeeExamineTask>)mapData.get("data");
		List<TeeExamineTaskModel> modelList = new ArrayList<TeeExamineTaskModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeExamineTaskModel modeltemp = TeeExamineTaskService.parseModel(list.get(i), false,loginPerson);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	
}
