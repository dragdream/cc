package com.tianee.oa.core.base.examine.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tianee.oa.core.base.examine.dao.TeeExamineTaskDao;
import com.tianee.oa.core.base.examine.model.TeeExamineTaskModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 
 * @author syl
 */
@Service
public class TeeExamineTaskService extends TeeBaseService {
	@Autowired
	private TeeExamineTaskDao examineTaskDao;
	
	@Autowired
	private TeeExamineGroupDao examineGroupDao;
	
	@Autowired
	private TeeExamineDataDao examineDataDao;
	
	@Autowired
	private TeeExamineItemDao examineItemDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSmsManager smsManager;


	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeExamineTaskModel model) throws IOException, ParseException {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeExamineTask task = new TeeExamineTask();
		BeanUtils.copyProperties(model, task);
		
		if(!TeeUtility.isNullorEmpty(model.getRankmanIds())){//考核人-- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getRankmanIds());
			task.setRankman(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getParticipantIds())){//被考核人-- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getParticipantIds());
			task.setParticipant(listDept);
		}
		int groupId = TeeStringUtil.getInteger(request.getParameter("groupId"), 0);
		TeeExamineGroup group = examineGroupDao.getById(groupId);//指标项目
		task.setGroup(group);
		task.setCreater(person);
		
		if(!TeeUtility.isNullorEmpty(model.getTaskBeginStr()) ){
			task.setTaskBegin(TeeUtility.parseDate("yyyy-MM-dd", model.getTaskBeginStr()));
		}
		
		if(!TeeUtility.isNullorEmpty(model.getTaskEndStr()) ){
			task.setTaskEnd(TeeUtility.parseDate("yyyy-MM-dd", model.getTaskEndStr()));
		}
		if(task.getTaskBegin() == null){
			task.setTaskBegin(new Date());
		}
		
		if(model.getSid() > 0){
			TeeExamineTask oldTask  = examineTaskDao.getById(model.getSid());
			if(oldTask != null){
				int sid = oldTask.getSid();
				
				Map oldMap=new HashMap<Integer, String>();
				Map newMap=new HashMap<Integer, String>();
				
				if(oldTask.getRankman()!=null&&oldTask.getRankman().size()>0){
					for (TeePerson p : oldTask.getRankman()) {
						oldMap.put(p.getUuid(), p.getUserName());
					}
				}
				
				if(task.getRankman()!=null&&task.getRankman().size()>0){
					for (TeePerson p : task.getRankman()) {
						newMap.put(p.getUuid(), p.getUserName());
					}
				}
				
				//判断考核人员是否发生了变化
				if(!(oldMap.toString()).equals(newMap.toString())){//考核人员发生了变化
					
					//重置权重
					List weight = new ArrayList();
					List<TeePerson> rankmans = task.getRankman();
					
					if(rankmans!=null){
						for(TeePerson p:rankmans){
							Map data = new HashMap();
							data.put("uid", p.getUuid());
							data.put("w", 1);//默认权重为1
							weight.add(data);
						}
						
					}		
					task.setWeight(TeeJsonUtil.toJson(weight));	
				}else{//考核人员没有发生变化  所以权重还是取之前的数据
					task.setWeight(oldTask.getWeight());
				}
				
				
				BeanUtils.copyProperties(task, oldTask);
				examineTaskDao.updateObj(oldTask);
			}else{
				json.setRtState(false);
				json.setRtMsg("该考核指标任务已被删除！");
				return json;
			}
		}else{
			
			//设置默认权重
			List weight = new ArrayList();
			List<TeePerson> rankmans = task.getRankman();
			
			if(rankmans!=null){
				for(TeePerson p:rankmans){
					Map data = new HashMap();
					data.put("uid", p.getUuid());
					data.put("w", 1);//默认权重为1
					weight.add(data);
				}
				
			}
			
			task.setWeight(TeeJsonUtil.toJson(weight));
			examineTaskDao.add(task);
		}
		String sms = TeeStringUtil.getString(model.getSms(), "");
		if(sms.equals("1")){
			Map requestData = new HashMap();
			if(model.getIsSelfAssessment().equals("1")){
				requestData.put("content", "请查看考核任务并对自己进行自评！ 标题：" + model.getTaskTitle());
				requestData.put("userListIds",model.getParticipantIds());
				requestData.put("remindUrl", "/system/core/base/examine/self/index.jsp");
			}else{
				requestData.put("content", "请查看考核任务！ 标题：" + model.getTaskTitle());
				requestData.put("userListIds",model.getRankmanIds() );
				requestData.put("remindUrl", "/system/core/base/examine/manage/manager.jsp");
			}
			requestData.put("sendTime", model.getTaskBeginStr() );
			requestData.put("moduleNo", "034");
			smsManager.sendSms(requestData, person);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	
	/**
	 * 通用列表  ----   
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request , TeeExamineTaskModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		Map mapCount = examineTaskDao.selectPostExamineTask(loginPerson,model, false , 0 , 0 ,dm);
		j.setTotal(TeeStringUtil.getLong(mapCount.get("count") , 0));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
		
		Map mapData = examineTaskDao.selectPostExamineTask(loginPerson,model,true, firstIndex ,dm.getRows() ,dm);
		List<TeeExamineTask> list = (List<TeeExamineTask>)mapData.get("data");
		List<TeeExamineTaskModel> modelList = new ArrayList<TeeExamineTaskModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeExamineTaskModel modeltemp = parseModel(list.get(i), false ,loginPerson);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	

	/**
	 * 通用列表   ----查询   
	 * @param dm
	 * @return
	 * @throws ParseException 
	 */
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson queryDatagrid(TeeDataGridModel dm,HttpServletRequest request , TeeExamineTaskModel model) throws ParseException {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		Map mapCount = examineTaskDao.selectQueryExamineTask(loginPerson,model, false , 0 , 0 ,dm);
		j.setTotal(TeeStringUtil.getLong(mapCount.get("count") , 0));// 设置总记录数
		int firstIndex = 0;
		firstIndex = (dm.getPage()-1) * dm.getRows() ;//获取开始索引位置
		Object parm[] = {};
		
		Map mapData = examineTaskDao.selectQueryExamineTask(loginPerson,model,true, firstIndex ,dm.getRows() ,dm);
		List<TeeExamineTask> list = (List<TeeExamineTask>)mapData.get("data");
		List<TeeExamineTaskModel> modelList = new ArrayList<TeeExamineTaskModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				TeeExamineTaskModel modeltemp = parseModel(list.get(i), false , loginPerson);
				modelList.add(modeltemp);
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public static TeeExamineTaskModel parseModel(TeeExamineTask room , boolean isSimple ,TeePerson loginPerson){
		TeeExamineTaskModel model = new TeeExamineTaskModel();
		if(room == null){
			return model;
		}
	
		BeanUtils.copyProperties(room, model);
		if(room.getGroup() != null){
			model.setGroupId(room.getGroup().getSid() + "");
			model.setGroupName(room.getGroup().getExamineName());
		}
		String rankmanIds = "";
		String rankmanNames = "";
		String participantIds = "";
		String participantNames = "";
		List<Map> dataModelList = new ArrayList<Map>();
		if(!isSimple){
			List<TeePerson> rankman = room.getRankman();
			for (int i = 0; i < rankman.size(); i++) {
				rankmanIds = rankmanIds + rankman.get(i).getUuid() + ",";
				rankmanNames = rankmanNames + rankman.get(i).getUserName() + ",";
			}
			List<TeeExamineData> dataList = room.getExamineData();//考核数据
			List<TeePerson> partici = room.getParticipant();//被考核人
			for (int i = 0; i < partici.size(); i++) {
				participantIds = participantIds + partici.get(i).getUuid() + ",";
				participantNames = participantNames + partici.get(i).getUserName() + ",";
				
				Map map = new HashMap();
				map.put("userId", partici.get(i).getUuid());
				map.put("userName", partici.get(i).getUserName());
				int  isExamine = 0;
				for (int j = 0; j < dataList.size(); j++) {
					TeeExamineData data = dataList.get(j);
					//考核数据  的被考核人 是被考核人列表中  and 考核数据  的考核人和系统当前登录人
					if(data.getParticipant().getUuid() == partici.get(i).getUuid() && data.getRankman().getUuid()==loginPerson.getUuid()){
						isExamine = 1;
						dataList.remove(data);
						break;
					}
				}
				map.put("isExamine", isExamine);
				dataModelList.add(map);
			}
		}
		model.setParticipantList(dataModelList);
		model.setParticipantIds(participantIds);
		model.setParticipantNames(participantNames);
		model.setRankmanIds(rankmanIds);
		model.setRankmanNames(rankmanNames);
		
		String anonymityDesc = "实名";
		if(room.getAnonymity().equals("1")){
			anonymityDesc = "匿名";
		}
		model.setAnonymityDesc(anonymityDesc);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date currDate = new Date();
		String currDateStr = TeeUtility.getDateTimeStr(currDate, format);
		model.setTaskBeginStr(TeeUtility.getDateTimeStr(room.getTaskBegin(), format));
		if(room.getTaskEnd() != null ){
			model.setTaskEndStr(TeeUtility.getDateTimeStr(room.getTaskEnd(), format));
		}
		
		
		int state = 0;//0-生效 1-终止 2-待生效
		if(!TeeUtility.isNullorEmpty(model.getTaskEndStr())  
				&& model.getTaskEndStr().compareTo(currDateStr) < 0){
			state = 1;
		}
		if(!TeeUtility.isNullorEmpty(model.getTaskBeginStr())  
				&& model.getTaskBeginStr().compareTo(currDateStr) > 0){
			state = 2;
		}
		model.setState(state);
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
	public TeeJson deleteById(TeeExamineTaskModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			//删除自评和其他评价
			simpleDaoSupport.executeUpdate("delete from TeeExamineData where task.sid=?", new Object[]{model.getSid()});
			simpleDaoSupport.executeUpdate("delete from TeeExamineSelfData where task.sid=?", new Object[]{model.getSid()});
			examineTaskDao.delById(model.getSid());
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 查询 ById  
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectById(HttpServletRequest request, TeeExamineTaskModel model) {
		TeeJson json = new TeeJson();
		TeePerson loginPerson = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(model.getSid() > 0){
			TeeExamineTask group = examineTaskDao.getById(model.getSid());
			if(group != null){
				json.setRtData(parseModel(group, false ,loginPerson));
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该记录已被删除!");
	
		return json;
	}
	
	/**
	 * 更新考核任务 状态  
	 * @author syl
	 * @date 2014-5-28
	 * @param request
	 * @return
	 */
	public TeeJson updateState(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String state = TeeStringUtil.getString(request.getParameter("state"), "0");//1:终止 0-生效
		
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamineTask task = examineTaskDao.getById(sid);
		if(task == null){
			json.setRtState(false);
			json.setRtMsg("该考核任务已被删除！");
			return json;
		}
		if(state.equals("1")){
			task.setTaskEnd(TeeUtility.getYestday(new Date()));
		}else{
			task.setTaskEnd(null);
		}
		examineTaskDao.updateObj(task);
		json.setRtState(true);
		return json;
	}
		
	/**
	 * 查询考核任务被考核人   ById  
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectParticipantById(HttpServletRequest request, TeeExamineTaskModel model) {
		TeeJson json = new TeeJson();
		List modelList = new ArrayList();
		if(model.getSid() > 0){
			TeeExamineTask group = examineTaskDao.getById(model.getSid());
			if(group != null){
				List<TeePerson> personList = group.getParticipant();
				for (int i = 0; i < personList.size(); i++) {
					Map map = new HashMap();
					TeePerson p =  personList.get(i);
					map.put("userId", p.getUuid());
					map.put("userName",p.getUserName());
					TeeDepartment dept  = p.getDept();
					TeeUserRole role = p.getUserRole();
					String deptName = "";
					String userRoleName = "";
					if(dept != null){
						deptName = dept.getDeptName();
					}
					if(role != null){
						userRoleName = role.getRoleName();
					}
					map.put("deptName", deptName);
					map.put("userRoleName",userRoleName);
					modelList.add(map);
				}
				json.setRtData(modelList);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该记录已被删除!");
	
		return json;
	}
	
	/**
	 * 查询考核任务  考核详情  
	 * @author syl
	 * @date 2014-5-24
	 * @param request
	 * @param model
	 * @return
	 */
	public List<Map> queryExamineInfo(HttpServletRequest request, TeeExamineTaskModel model) {
		 List<Map> list = new ArrayList<Map>();
		if(model.getSid() > 0){
			TeeExamineTask group = examineTaskDao.getById(model.getSid());
			if(group != null){
				List<TeePerson> personList = group.getParticipant();//被考核人
				List<TeePerson> personList2 = group.getRankman();//考核人
				//整个考核任务   已考核人员
				List<TeeExamineData> dataList = examineDataDao.getExamineData(model.getSid(), 0, 0);
				for (int i = 0; i < personList2.size(); i++) {
					TeePerson temp = personList2.get(i);
					Map map = new HashMap();
					map.put("userName", temp.getUserName());
					List<TeePerson> personListTemp = new ArrayList<TeePerson>() ;//未被考核人
					personListTemp.addAll(personList);
					List<TeePerson> personListTemp2 = new ArrayList<TeePerson>();//已被考核人
					for (int j = 0; j < dataList.size(); j++) {
						TeeExamineData data = dataList.get(j);
						if(data.getRankman().getUuid() ==  temp.getUuid()){//是考核人
							personListTemp.remove(data.getParticipant());//删除被考核人
							personListTemp2.add(data.getParticipant());
						}
					}
					map.put("noExamine", personListTemp);
					map.put("examine", personListTemp2);
					list.add(map);
				}
			}
		}

		return list;
	}
	
	/**
	 * 考核查阅
	 * @author syl
	 * @date 2014-5-31
	 * @param request
	 * @param model
	 * @return
	 */
	public Map queryExamineDetail(HttpServletRequest request, TeeExamineTaskModel model) {
		Map data = new HashMap();
		List<Map> list = new ArrayList<Map>();
		List<Map> itemDescList = new ArrayList<Map>();
		if(model.getSid() > 0){
			TeeExamineTask group = examineTaskDao.getById(model.getSid());
			if(group != null){
				List<TeePerson> personList = group.getParticipant();//被考核人
//				List<TeePerson> personList2 = group.getRankman();//考核人
				//整个考核任务   已考核人员
//				List<TeeExamineData> dataList = examineDataDao.getExamineData(model.getSid(), 0, 0);
				
				//考核项目明细
				List<TeeExamineItem> itemList = examineItemDao.getAllByGroupId(group.getGroup().getSid());
				Map<String , Object> map = new HashMap<String , Object>();
			
				for (int j = 0; j < itemList.size(); j++) {
					TeeExamineItem item = itemList.get(j);//项目明细
					map.put("" + item.getSid(), item.getItemName());
					Map itemDesc = new HashMap();
					itemDesc.put("itemId", item.getSid());
					itemDesc.put("itemName", item.getItemName());
					itemDescList.add(itemDesc);
				}
				
				//获取当前人员的最终得分
//				getFinalScore(taskId, userId);
				for(TeePerson p:personList){
					Map finalScoreMap = getFinalScore(model.getSid(), p.getUuid());
					finalScoreMap.put("deptName", p.getDept().getDeptName());
					finalScoreMap.put("userRoleName", p.getUserRole().getRoleName());
					finalScoreMap.put("userName", p.getUserName());
					finalScoreMap.put("userSid", p.getUuid());
					list.add(finalScoreMap);
				}
//				Map mapTemp = getExemineInfo(dataList);
//				//循环Map
//				Iterator iter = mapTemp.keySet().iterator();
//				while (iter.hasNext()) {
//					Integer  key = (Integer)iter.next();
//				    Map value = (Map)mapTemp.get(key);
//				    list.add(value);
//				}
			}
		}
		data.put("itemList", itemDescList);
		data.put("data", list);
		return data;
	}
	
	
	public Map getExemineInfo(List<TeeExamineData> dataList){
		Map mapTemp = new HashMap();
		for (int k = 0; k < dataList.size(); k++) {//所有考核数据
			TeeExamineData dataTemp = dataList.get(k);
			if(!TeeUtility.isNullorEmpty(mapTemp.get(dataTemp.getParticipant().getUuid()))){//如果包含的话
				Map temp  = (Map)mapTemp.get(dataTemp.getParticipant().getUuid());
				int count = TeeStringUtil.getInteger(temp.get("count") , 0);
				count = count + 1;
				temp.put("count", count);
				String examineDataStr = dataTemp.getExamineData();
				if(!TeeUtility.isNullorEmpty(examineDataStr)){
					List<Map<String, String>> list2 =  TeeJsonUtil.JsonStr2MapList(examineDataStr);
					for (int l = 0; l < list2.size(); l++) {
						Map<String , String > mapTemp2 = list2.get(l);
						int itemId = TeeStringUtil.getInteger(mapTemp2.get("itemId") , 0);
						double score = TeeStringUtil.getDouble(temp.get("" + itemId) , 0);//获取之前的
						double score2 = TeeStringUtil.getDouble(mapTemp2.get("score") , 0);//获取新的
						temp.put("" + itemId, score +score2 );
					}
				}
			}else{
				//set.add(dataTemp.getParticipant());//被考核人员
				Map temp  =  new HashMap();
				temp.put("userName", dataTemp.getParticipant().getUserName());
				String deptName = "";
				String userRoleName = "";
				if(dataTemp.getParticipant().getDept() != null){
					deptName = dataTemp.getParticipant().getDept().getDeptName();
				}
				if(dataTemp.getParticipant().getUserRole() != null){
					userRoleName = dataTemp.getParticipant().getUserRole().getRoleName();
				}
				temp.put("deptName", deptName);
				temp.put("userRoleName", userRoleName);
				temp.put("userSid", dataTemp.getParticipant().getUuid());
				temp.put("count", 1 );
				
				String examineDataStr = dataTemp.getExamineData();
				if(!TeeUtility.isNullorEmpty(examineDataStr)){
					List<Map<String, String>> list2 =  TeeJsonUtil.JsonStr2MapList(examineDataStr);
					for (int l = 0; l < list2.size(); l++) {
						Map<String , String > mapTemp2 = list2.get(l);
						int itemId = TeeStringUtil.getInteger(mapTemp2.get("itemId") , 0);
						temp.put("" + itemId, TeeStringUtil.getDouble(mapTemp2.get("score") , 0));
					}
				}
				mapTemp.put(dataTemp.getParticipant().getUuid(), temp);
				//mapTemp.put("userId", dataTemp.getParticipant().getUuid());
			}
		}
		return mapTemp;
	}
	/**
	 * 导出考核总分
	 * @author syl
	 * @date 2014-5-31
	 * @param request
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportExamineTotal(HttpServletRequest request){
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamineTask task = examineTaskDao.getById(sid);
		if(task != null){
			//获取被考核人员
			List<TeePerson> participants = task.getParticipant();
			//考核项目明细
			List<TeeExamineItem> itemList = examineItemDao.getAllByGroupId(task.getGroup().getSid());
			Map<String , Object> map = new HashMap<String , Object>();
			for(TeePerson p:participants){
				Map mapTemp = getFinalScore(task.getSid(), p.getUuid());
				TeeDataRecord dbrec = new TeeDataRecord();
			    dbrec.addField("部门", p.getDept().getDeptName());
		        dbrec.addField("姓名", p.getUserName());
		        dbrec.addField("角色", p.getUserRole().getRoleName());
		        for (int j = 0; j < itemList.size(); j++) {
		        	TeeExamineItem item = itemList.get(j);//项目明细
		        	dbrec.addField(item.getItemName(), mapTemp.get(item.getSid()+""));
		        }
		        dbrec.addField("总和",mapTemp.get("ALL"));
				list.add(dbrec);
			}
//			Map mapTemp = getExemineInfo(dataList);
//			//循环Map
//			Iterator iter = mapTemp.keySet().iterator();
//			while (iter.hasNext()) {
//				Integer  key = (Integer)iter.next();
//			    Map value = (Map)mapTemp.get(key);
//			    TeeDataRecord dbrec = new TeeDataRecord();
//			    dbrec.addField("部门", value.get("deptName"));
//		        dbrec.addField("姓名", value.get("userName"));
//		        dbrec.addField("角色", value.get("userRoleName"));
//		        int count = TeeStringUtil.getInteger(value.get("count") , 0);
//		        double total = 0;
//		        for (int j = 0; j < itemList.size(); j++) {
//		        	TeeExamineItem item = itemList.get(j);//项目明细
//		        	double score = TeeStringUtil.getDouble(value.get(item.getSid() + "") , 0);
//		        	total = total + score;
//		        	dbrec.addField(item.getItemName(), score/count);
//		        }
//		        dbrec.addField("总和",total/count);
//		        list.add(dbrec);
//			}
		}
		return list;
	}
	
	/**
	 * 导出考核总分明细
	 * @author syl
	 * @date 2014-5-31
	 * @param request
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportDetailToCsv(HttpServletRequest request){
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamineTask task = examineTaskDao.getById(sid);
		if(task != null){
			//整个考核任务   已考核人员
			List<TeeExamineData> dataList = examineDataDao.getExamineData(task.getSid(), 0, 0);
			//考核项目明细
			List<TeeExamineItem> itemList = examineItemDao.getAllByGroupId(task.getGroup().getSid());
			Map<String , Object> map = new HashMap<String , Object>();
			for (int j = 0; j < itemList.size(); j++) {
				TeeExamineItem item = itemList.get(j);//项目明细
				map.put("" + item.getSid(), item.getItemName());
			}
			for (int i = 0; i < dataList.size(); i++) {
				TeeExamineData dataTemp = dataList.get(i);
			    TeeDataRecord dbrec = new TeeDataRecord();
			    dbrec.addField("部门", dataTemp.getParticipant().getDept().getDeptName());
		        dbrec.addField("评分人", dataTemp.getRankman().getUserName());
		        dbrec.addField("被考核人", dataTemp.getParticipant().getUserName());
				String examineDataStr = dataTemp.getExamineData();
				if(!TeeUtility.isNullorEmpty(examineDataStr)){
					List<Map<String, String>> list2 =  TeeJsonUtil.JsonStr2MapList(examineDataStr);
					for (int l = 0; l < list2.size(); l++) {
						Map<String , String > mapTemp2 = list2.get(l);
						int itemId = TeeStringUtil.getInteger(mapTemp2.get("itemId") , 0);
						if(!TeeUtility.isNullorEmpty(map.get(itemId + ""))){
							double score = TeeStringUtil.getDouble(mapTemp2.get("score") , 0);
							String itemDesc = TeeStringUtil.getString(mapTemp2.get("itemDesc"), "" );
							if(!TeeUtility.isNullorEmpty(itemDesc)){
								itemDesc =  "(" + itemDesc+ ")";
							}
							dbrec.addField((String)map.get(itemId + ""), score + itemDesc);
						}
					}
				}
				list.add(dbrec);
			}
			
		}
		return list;
	}
	
	
	public List<Map<String, String>> getTaskWeightList(int taskId){
		TeeExamineTask task = examineTaskDao.getById(taskId);
		List<Map<String, String>> weightModel = TeeJsonUtil.JsonStr2MapList(task.getWeight());
		for(Map data:weightModel){
			TeePerson p = personDao.get(TeeStringUtil.getInteger(data.get("uid"), 0));
			if(p!=null){
				data.put("name", p.getUserName());
			}
		}
		return weightModel;
	}
	
	public void updateTaskWeightList(int taskId,String weightModel){
		TeeExamineTask task = examineTaskDao.getById(taskId);
		task.setWeight(weightModel);
	}
	
	/**
	 * 获取该人各项得分和最终得分
	 * @return
	 */
	public Map getFinalScore(int taskId,int userId){
		Map FinalScore = new HashMap();
		//获取考核任务
		TeeExamineTask task = examineTaskDao.getById(taskId);
		//获取当前人的自评
		TeeExamineSelfData selfData = (TeeExamineSelfData) simpleDaoSupport.unique("from TeeExamineSelfData where task.sid=? and participant.uuid=?", 
				new Object[]{taskId,userId});
		
		//获取其他人对当前人的评价
		List<TeeExamineData> examDatas =  simpleDaoSupport.find("from TeeExamineData where task.sid=? and participant.uuid=?", taskId,userId);
		
		//获取考核指标权重
		List<Map<String,String>> weightModel = TeeJsonUtil.JsonStr2MapList(task.getWeight());
		
		//获取考核人
		List<TeePerson> rankman = task.getRankman();
		
		//获取当前任务的所有考核指标
		TeeExamineGroup examineGroup = task.getGroup();
		List<TeeExamineItem> items = examineGroup.getItems();
		//计算整体权重
		double weightAll = 0;//默认为被考核人的权重，值为1
		if(selfData!=null){
			weightAll=1;//如果有自评  权重加1
		}
		for(Map<String,String> weight:weightModel){
			weightAll+=TeeStringUtil.getDouble(weight.get("w"), 0.0);
		}
		
		//计算每一考核项的总分值
		double itemScore = 0;
		double scoreAll = 0;
		for(TeeExamineItem item:items){
			itemScore = 0;
			//先加入自评
			if(selfData!=null){			
				//获取自评json列表
				List<Map<String,String>> itemScoreMaps = TeeJsonUtil.JsonStr2MapList(selfData.getSelfData());
				for(Map<String,String> itemScoreMap:itemScoreMaps){
					//如果是当前考核指标，则计算自评考核项
					if(String.valueOf(item.getSid()).equals(itemScoreMap.get("itemId"))){
						itemScore+=TeeStringUtil.getDouble(itemScoreMap.get("score"),0.0)*1;
						break;
					}
				}
				
			}else{
				itemScore+=0;
			}
			
			//加入考核人评价
			for(TeePerson p:rankman){
				for(TeeExamineData examData:examDatas){
					if(examData.getRankman()==p){
						List<Map<String,String>> itemScoreMaps = TeeJsonUtil.JsonStr2MapList(examData.getExamineData());
						for(Map<String,String> itemScoreMap:itemScoreMaps){
							//如果是当前考核指标，则计算自评考核项
							if(String.valueOf(item.getSid()).equals(itemScoreMap.get("itemId"))){
								for(Map<String,String> weight:weightModel){
									if(weight.get("uid").equals(String.valueOf(p.getUuid()))){
										itemScore+=TeeStringUtil.getDouble(itemScoreMap.get("score"),0.0)*TeeStringUtil.getDouble(weight.get("w"), 0.0);
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			}
			double tmp = 0;
			if(weightAll!=0){
				tmp = BigDecimal.valueOf(itemScore/weightAll).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			FinalScore.put(String.valueOf(item.getSid()), tmp);//考核项ID中加入分数
			scoreAll+=tmp;
		}
		
		FinalScore.put("ALL", BigDecimal.valueOf(scoreAll).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());//加入总分
		
		return FinalScore;
	}
	
}
