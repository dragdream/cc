package com.tianee.oa.core.base.exam.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.commonword.bean.CommonWord;
import com.tianee.oa.core.base.exam.bean.TeeExamData;
import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.bean.TeeExamRecord;
import com.tianee.oa.core.base.exam.model.TeeExamInfoModel;
import com.tianee.oa.core.base.meeting.bean.TeeMeeting;
import com.tianee.oa.core.base.meeting.model.TeeMeetingModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeExamInfoService extends TeeBaseService {
	
	@Autowired
	private TeeSmsManager smsManager;

	@Autowired
	private  TeeExamDataService   examDataService;
	
	public TeeExamInfo addExamInfo(TeeExamInfo examInfo) {
		getSimpleDaoSupport().save(examInfo);
		return examInfo;
	}

	public TeeExamInfo addExamInfoModel(HttpServletRequest request, TeeExamInfoModel examInfoModel)
			throws ParseException {
		TeeExamInfo examInfo = new TeeExamInfo();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BeanUtils.copyProperties(examInfoModel, examInfo);
		String startTimeDesc = examInfoModel.getStartTimeDesc();
		String endTimeDesc = examInfoModel.getEndTimeDesc();
		TeeExamPaper paper = (TeeExamPaper) simpleDaoSupport.get(
				TeeExamPaper.class, examInfoModel.getPaperId());
		examInfo.setExamPaper(paper);
		int attendUserIds[] = TeeStringUtil.parseIntegerArray(examInfoModel
				.getAttendUserIds());
		int subExaminerIds[] = TeeStringUtil.parseIntegerArray(examInfoModel
				.getSubExaminerIds());
		// examInfo.getParticipant().clear();
		for (int uuid : attendUserIds) {
			if (uuid == 0) {
				continue;
			}
			TeePerson person = (TeePerson) simpleDaoSupport.get(
					TeePerson.class, uuid);
			if (person != null) {
				examInfo.getParticipant().add(person);
			}
		}
		// examInfo.getSubExaminer().clear();
		for (int uuid : subExaminerIds) {
			if (uuid == 0) {
				continue;
			}
			TeePerson person = (TeePerson) simpleDaoSupport.get(
					TeePerson.class, uuid);
			if (person != null) {
				examInfo.getSubExaminer().add(person);
			}
		}
		if (!TeeUtility.isNullorEmpty(startTimeDesc)) {
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(formatter.parse(startTimeDesc));
			examInfo.setStartTime(cl1);
		} else {
			Calendar cl1 = Calendar.getInstance();
			String curDateDesc = formatter.format(cl1.getTime()).substring(0,
					10)+" 00:00:00";
			cl1.setTime(formatter.parse(curDateDesc));
			examInfo.setStartTime(cl1);
		}
		if (!TeeUtility.isNullorEmpty(endTimeDesc)) {
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(endTimeDesc));
			examInfo.setEndTime(cl2);
		}
		addExamInfo(examInfo);
		try{
			TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
			//短信提醒参加考试人，进行考试
			Map requestData = new HashMap();
			requestData.put("content", "您有一个考试信息["+examInfo.getExamName()+"]，请及时参加考试。");
			requestData.put("userListIds",examInfoModel.getAttendUserIds());
			//requestData.put("sendTime", );
			requestData.put("moduleNo", "023" );
			requestData.put("remindUrl", "/system/core/base/exam/manage/myExam.jsp");
			smsManager.sendSms(requestData, loginUser);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return examInfo;
	}

	public TeeExamInfo deleteExamInfo(TeeExamInfo examInfo) {
		simpleDaoSupport.executeUpdate("delete from TeeExamData where examInfo.sid=?", new Object[]{examInfo.getSid()});
		simpleDaoSupport.executeUpdate("delete from TeeExamRecord where teeExamInfo.sid=?", new Object[]{examInfo.getSid()});
		getSimpleDaoSupport().deleteByObj(examInfo);
		return examInfo;
	}

	public void updateExamInfo(TeeExamInfo examInfo) {
		getSimpleDaoSupport().update(examInfo);
	}

	public void updateExamInfoModel(TeeExamInfoModel examInfoModel)
			throws Exception {
		TeeExamInfo examInfo = (TeeExamInfo) simpleDaoSupport.get(
				TeeExamInfo.class, examInfoModel.getSid());
		BeanUtils.copyProperties(examInfoModel, examInfo);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTimeDesc = examInfoModel.getStartTimeDesc();
		String endTimeDesc = examInfoModel.getEndTimeDesc();
		TeeExamPaper paper = (TeeExamPaper) simpleDaoSupport.get(
				TeeExamPaper.class, examInfoModel.getPaperId());
		examInfo.setExamPaper(paper);
		int attendUserIds[] = TeeStringUtil.parseIntegerArray(examInfoModel
				.getAttendUserIds());
		int subExaminerIds[] = TeeStringUtil.parseIntegerArray(examInfoModel
				.getSubExaminerIds());
		examInfo.getParticipant().clear();
		for (int uuid : attendUserIds) {
			if (uuid == 0) {
				continue;
			}
			TeePerson person = (TeePerson) simpleDaoSupport.get(
					TeePerson.class, uuid);
			if (person != null) {
				examInfo.getParticipant().add(person);
			}
		}
		examInfo.getSubExaminer().clear();
		for (int uuid : subExaminerIds) {
			if (uuid == 0) {
				continue;
			}
			TeePerson person = (TeePerson) simpleDaoSupport.get(
					TeePerson.class, uuid);
			if (person != null) {
				examInfo.getSubExaminer().add(person);
			}
		}
		if (!TeeUtility.isNullorEmpty(startTimeDesc)) {
			Calendar cl1 = Calendar.getInstance();
			cl1.setTime(formatter.parse(startTimeDesc));
			examInfo.setStartTime(cl1);
		}else{
			Calendar cl1 = Calendar.getInstance();
			String curDateDesc = formatter.format(cl1.getTime()).substring(0,
					10)+" 00:00:00";
			cl1.setTime(formatter.parse(curDateDesc));
			examInfo.setStartTime(cl1);
		}
		if (!TeeUtility.isNullorEmpty(endTimeDesc)) {
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(endTimeDesc));
			examInfo.setEndTime(cl2);
		}else{
			examInfo.setEndTime(null);
		}
		updateExamInfo(examInfo);
	}

	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, Map requestDatas) {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String hql = "from TeeExamInfo oc where 1=1 ";
		StringBuffer order = new StringBuffer();
		order.append(" order by oc.sid desc");
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List rows = new ArrayList();
		List<TeeExamInfo> list = simpleDaoSupport.pageFind(hql+order.toString(), dm.getRows()
				* (dm.getPage() - 1), dm.getRows(), null);
		for (TeeExamInfo examInfo : list) {
			TeeExamInfoModel model = new TeeExamInfoModel();
			BeanUtils.copyProperties(examInfo, model);
			model.setPaperName(examInfo.getExamPaper().getTitle());
			if (examInfo.getStartTime() != null) {
				model.setStartTimeDesc(formatter.format(examInfo.getStartTime()
						.getTime()));
			}
			if (examInfo.getEndTime() != null) {
				model.setEndTimeDesc(formatter.format(examInfo.getEndTime()
						.getTime()));
			}
			String attendUserIds = "";
			String attendUserNames = "";
			Set<TeePerson> attendUsers = examInfo.getParticipant();
			for (TeePerson p : attendUsers) {
				attendUserIds += p.getUuid() + ",";
				attendUserNames += p.getUserName() + ",";
			}
			String subExaminerIds = "";
			String subExaminerNames = "";
			Set<TeePerson> subExaminers = examInfo.getSubExaminer();
			for (TeePerson p : subExaminers) {
				subExaminerIds += p.getUuid() + ",";
				subExaminerNames += p.getUserName() + ",";
			}
			model.setSubExaminerIds(subExaminerIds);
			model.setSubExaminerNames(subExaminerNames);
			model.setAttendUserIds(attendUserIds);
			model.setAttendUserNames(attendUserNames);
			rows.add(model);
		}

		datagird.setRows(rows);
		datagird.setTotal(total);

		return datagird;
	}

	public TeeEasyuiDataGridJson getMyExamInfo(TeeDataGridModel dm,
			Map requestDatas) {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeePerson person = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		String hql = "from TeeExamInfo info where exists (select 1 from info.participant p where p.uuid="
				+ person.getUuid() + ")";
		long total = simpleDaoSupport.count("select count(*) " + hql, null);
		List rows = new ArrayList();
		List<TeeExamInfo> list = simpleDaoSupport.pageFind(hql+" order by info.sid desc", dm.getRows()
				* (dm.getPage() - 1), dm.getRows(), null);
		Calendar cl = Calendar.getInstance();
		for (TeeExamInfo examInfo : list) {
			TeeExamInfoModel model = new TeeExamInfoModel();
			String hql2 = "from TeeExamRecord record where record.teeExamInfo.sid="+examInfo.getSid()+" and record.participant.uuid="+person.getUuid();
			TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql2, null);
			BeanUtils.copyProperties(examInfo, model);
			if (examInfo.getStartTime() != null) {
				model.setStartTimeDesc(formatter.format(examInfo.getStartTime()
						.getTime()));
			}
			if (examInfo.getEndTime() != null) {
				model.setEndTimeDesc(formatter.format(examInfo.getEndTime()
						.getTime()));
			}
			model.setPaperName(examInfo.getExamPaper().getTitle());
			model.setOwnId(person.getUuid());
			model.setOwnName(person.getUserName());
			model.setScoreAll(examInfo.getExamPaper().getScoreAll());
			
			if(examInfo.getExamPaper().getScoreType()==2){
				//获取试题分数
				List<TeeExamQuestion> questions = examInfo.getExamPaper().getQuestionList();
				long totalCount = 0;
				for(TeeExamQuestion question:questions){
					totalCount+=question.getScore();
				}
				model.setScoreAll(Integer.parseInt(totalCount+""));
			}
			
			int days = examInfo.getCheckDays();
			Calendar cur = Calendar.getInstance();
			int differDay = 0;
			if(record!=null){ //不为空说明已交卷
				if(record.isChecked() && record.getCheckExamTime()!=null){//通过判断阅卷时间是否为空 知其是否也阅
					Calendar checkExamTime=record.getCheckExamTime();
					differDay = Integer.parseInt(String.valueOf((cur.getTimeInMillis()-checkExamTime.getTimeInMillis())/(1000*3600*24)));
				}else{//否则 获取的是交卷的时间
					Calendar subExamTime=record.getSubExamTime();
					if(subExamTime!=null){
						differDay = Integer.parseInt(String.valueOf((cur.getTimeInMillis()-subExamTime.getTimeInMillis())/(1000*3600*24)));
					}
				}
			}
			if((isCommited(examInfo.getSid(),person.getUuid()))==2){//2代表已经交卷
				if(differDay>=days && record.isChecked()){
					if((examInfo.getStartTime() != null&& examInfo.getStartTime().before(cl) && ((examInfo.getEndTime() != null && examInfo.getEndTime().after(cl)) || TeeUtility.isNullorEmpty(examInfo.getEndTime())))){
						model.setOpt(1);//查卷
					}
				}
			}else{
				if ((examInfo.getStartTime() != null&& examInfo.getStartTime().before(cl) && ((examInfo.getEndTime() != null && examInfo.getEndTime().after(cl)) || TeeUtility.isNullorEmpty(examInfo.getEndTime())))){
					 model.setOpt(2);//考试
				}
			}
			
			rows.add(model);
		}

		datagird.setRows(rows);
		datagird.setTotal(total);

		return datagird;
	}

	public TeeExamInfo getById(int sid) {
		TeeExamInfo examInfo = (TeeExamInfo) simpleDaoSupport.get(
				TeeExamInfo.class, sid);
		return examInfo;
	}

	/**
	 * 发布考试信息(不用了）
	 * 
	 * @param sid
	 */
	public void sendExamInfo(int sid) {
		TeeExamInfo examinfo = getById(sid);
		int paperId = examinfo.getExamPaper().getSid();// 获取到试卷Id
		int examInfoId = examinfo.getSid();// 渠道考试信息Id
		Set<TeePerson> persons = examinfo.getParticipant();
		for (TeePerson person : persons) {
			TeeExamData examData = new TeeExamData();
			examData.setExamInfo(examinfo);
			examData.setParticipant(person);
			addExamData(examData);
		}
	}

	public void addExamData(TeeExamData examData) {
		getSimpleDaoSupport().save(examData);
	}

	/**
	 * 判断是否已经提交过试卷,0 代表没有考过试，1，代表已经参加考试了，但是没有正常交卷，2代表惨叫考试了，并且正常交卷了
	 * 
	 * @param sid
	 * @param userId
	 * @return
	 */
	public int isCommited(int sid, int userId) { 
		int flag=0;
		String hql = "from TeeExamRecord record where record.teeExamInfo.sid="+sid+" and record.participant.uuid="+userId;
		TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql, null);
		if(record!=null){
			if(record.getSubExamTime()==null){
				flag=1;
			}else{
				flag=2;
			}
		}
		return flag;
	}

	public TeeEasyuiDataGridJson getSingleExamList(TeeDataGridModel dm,
			Map requestDatas) {
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int sid = (Integer) requestDatas.get("sid");
		String hql = "from TeeExamInfo info where info.sid='" + sid + "'";
		hql += " order by info.sid asc";
		long total = 0;
		int realScore=0;
		List rows = new ArrayList();
		List<TeeExamInfo> list = simpleDaoSupport.pageFind(hql, dm.getRows()
				* (dm.getPage() - 1), dm.getRows(), null);
		for (TeeExamInfo examInfo : list) {
			Set<TeePerson> persons = examInfo.getParticipant();
			total = persons.size();
			for (TeePerson person : persons) {
				TeeExamInfoModel model = new TeeExamInfoModel();
				BeanUtils.copyProperties(examInfo, model);
				if (examInfo.getStartTime() != null) {
					model.setStartTimeDesc(formatter.format(examInfo
							.getStartTime().getTime()));
				}
				if (examInfo.getEndTime() != null) {
					model.setEndTimeDesc(formatter.format(examInfo.getEndTime()
							.getTime()));
				}
				model.setPaperName(examInfo.getExamPaper().getTitle());
				model.setOwnId(person.getUuid());
				model.setOwnName(person.getUserName());
				model.setScoreAll(examInfo.getExamPaper().getScoreAll());
				
				realScore=examDataService.getRealScore(examInfo.getSid(), person.getUuid());
				
			    model.setRealScore(realScore);
				rows.add(model);
			}

		}

		Collections.sort(rows,new Comparator<TeeExamInfoModel>() {

			@Override
			public int compare(TeeExamInfoModel arg0, TeeExamInfoModel arg1) {
				// TODO Auto-generated method stub
				if(arg0.getRealScore()>arg1.getRealScore()){
					return -1;
				}else if(arg0.getRealScore()==arg1.getRealScore()){
					return 0;
				}else{
					return 1;
				}
				
			}
		});
		datagird.setRows(rows);
		datagird.setTotal(total);

		return datagird;
	}

	public boolean isCheckUser(int sid, TeePerson loginUser) {
		boolean flag = false;
		TeeExamInfo info = (TeeExamInfo)simpleDaoSupport.get(TeeExamInfo.class,sid);
		String userIds="";
		Set<TeePerson> subExaminers = info.getSubExaminer();
		for (TeePerson p : subExaminers) {
			userIds += p.getUuid() + ",";
		}
		if(!TeeUtility.isNullorEmpty(userIds)){
			if(userIds.endsWith(",")){
				userIds=userIds.substring(0, userIds.length()-1);
			}
			String[] ids=userIds.split(",");
			int loginUserId = loginUser.getUuid();
			for(String id:ids){
				int id2=Integer.parseInt(id);
				if(id2==loginUserId){
					flag = true;
					break;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断TeeExamData 里面是否有数据
	 * @param sid
	 * @param userId
	 * @return
	 */
	public boolean isSaved(int sid, int userId) {
		boolean flag = false;
		String hql ="from TeeExamData data where data.examInfo.sid="+sid+" and data.participant.uuid="+userId;
		List<TeeExamData> list = simpleDaoSupport.find(hql, null);
		if(list.size()>0){
			flag=true;
		}
		return flag;
	}

	public TeeExamInfo selectScore(int sid) {
		String hql ="from TeeExamInfo te where te.sid="+sid;
		TeeExamInfo teif = (TeeExamInfo) simpleDaoSupport.unique(hql,null);
		return teif ;
	}
public List< TeeExamRecord> select(int sid){
	String hql = "from TeeExamRecord ter where  ter.teeExamInfo.sid=?  order by ter.score desc ";
	List< TeeExamRecord>  list = (List<TeeExamRecord>) simpleDaoSupport.executeQuery(hql, new Object[]{sid}) ; 
	return  list;
	}

public List<TeeExamRecord> selectbm(int sid) {
	String hql = "from TeeExamRecord ter where  ter.teeExamInfo.sid=?  order by ter.participant.dept.deptName asc ,ter.score  desc ";
	List< TeeExamRecord>  list = (List<TeeExamRecord>) simpleDaoSupport.executeQuery(hql, new Object[]{sid}) ; 
	return  list;
}


}
