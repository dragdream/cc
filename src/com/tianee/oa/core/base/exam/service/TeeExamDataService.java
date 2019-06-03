package com.tianee.oa.core.base.exam.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.exam.bean.TeeExamData;
import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.bean.TeeExamRecord;
import com.tianee.oa.core.base.exam.bean.TeeExamStore;
import com.tianee.oa.core.base.exam.model.TeeExamDataModel;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeDepository;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeExamDataService extends TeeBaseService{

	
	public TeeExamData addExamData(TeeExamData examData){
		getSimpleDaoSupport().save(examData);
		return examData;
	}
	
	public TeeExamRecord addExamRecord(TeeExamRecord record){
		getSimpleDaoSupport().save(record);
		return record;
	}
	public TeeExamData addExamDataModel(TeeExamDataModel examDataModel){
		TeeExamData examData = new TeeExamData();
		BeanUtils.copyProperties(examDataModel, examData);
		TeeExamQuestion question =(TeeExamQuestion)simpleDaoSupport.get(TeeExamQuestion.class, examDataModel.getQuestionId());
		TeePerson person =(TeePerson)simpleDaoSupport.get(TeePerson.class, examDataModel.getUserId());
		TeeExamInfo examInfo = (TeeExamInfo)simpleDaoSupport.get(TeeExamInfo.class,examDataModel.getExamInfoId());
		examData.setExamQuest(question);
		examData.setParticipant(person);
		examData.setExamInfo(examInfo);
		addExamData(examData);
		return examData;
	}
	
	
	public void updateExamData(TeeExamData examData){
		getSimpleDaoSupport().update(examData);
	}
	
	public void updateExamDataModel(TeeExamDataModel examDataModel){
		TeeExamData examData = (TeeExamData) simpleDaoSupport.get(TeeExamData.class, examDataModel.getSid());
		BeanUtils.copyProperties(examDataModel, examData);
		updateExamData(examData);
	}
	
	public TeeExamData getById(int sid){
		TeeExamData examData = (TeeExamData) simpleDaoSupport.get(TeeExamData.class, sid);
		return examData;
	}
	
	/**
	 * 更新总分
	 */
	public void updateScoreAll(int examInfoId,int userId){
		//获取考试信息
		TeeExamInfo examInfo = (TeeExamInfo) simpleDaoSupport.get(TeeExamInfo.class,examInfoId);
		//获取试卷
		TeeExamPaper paper = examInfo.getExamPaper();
		//获取考试记录
		TeeExamRecord examRecord = (TeeExamRecord) simpleDaoSupport.unique("from TeeExamRecord where participant.uuid=? and teeExamInfo.sid=?", 
				new Object[]{userId,examInfoId});
		
		
		if(paper.getScoreType()==1){//试卷百分比
			
			int scoreAll = paper.getScoreAll();//试卷总分
			//获取试题总分
			List<TeeExamQuestion> qList = paper.getQuestionList();
			int qScoreAll = 0;
			for(TeeExamQuestion q:qList){
				qScoreAll+=q.getScore();
			}
			int realScore = 0;
			double curScoreAll = 0;
			double curScore = 0;
			List<TeeExamData> dataList = simpleDaoSupport.find("from TeeExamData where participant.uuid=? and examInfo.sid=?", userId,examInfoId);
			for(TeeExamData data:dataList){
				for(TeeExamQuestion q:qList){
					if(data.getExamQuest()==q){
						double delta = Double.parseDouble(q.getScore()+"")/Double.parseDouble(qScoreAll+"");//计算系数
						curScoreAll = Double.parseDouble(scoreAll+"")*delta;
						curScore += curScoreAll/Double.parseDouble(q.getScore()+"")*Double.parseDouble(data.getScore()+"");
						break;
					}
				}
			}
			examRecord.setScore((int)curScore);
			
		}else{//试卷内容
			//获取答题总分
			long total = simpleDaoSupport.count("select sum(data.score) from TeeExamData data where data.participant.uuid=? "
					+ "and data.examInfo.sid=? ", new Object[]{userId,examInfoId});
			examRecord.setScore(Integer.parseInt(total+""));
		}
		simpleDaoSupport.update(examRecord);
	}
	
	public List<TeeExamQuestion> getQuestionList(int examInfoId){
		TeeExamInfo info = (TeeExamInfo)simpleDaoSupport.get(TeeExamInfo.class,examInfoId);
		TeeExamPaper paper = (TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,info.getExamPaper().getSid());
		TeeExamStore store = (TeeExamStore)simpleDaoSupport.get(TeeExamStore.class,info.getExamPaper().getExamStore().getSid());
		String hql = "from TeeExamQuestion question  where 1=1 and question.examStore.sid='"+store.getSid()+"'";
		List<TeeExamQuestion> questions  = (List<TeeExamQuestion>) simpleDaoSupport.filteredList(paper.getQuestionList(), "where 1=1 order by this.qType", null);;// simpleDaoSupport.find(hql, null);见证奇迹的时刻到了~~
		return questions;
	}
	
	/**
	 * 获取某一个人对一个问题的处理情况
	 * @param userId
	 * @param questionId
	 * @param examInfoId
	 * @return
	 */
	public TeeExamData getExamData(int userId, int questionId, int examInfoId) {
		String hql = "from TeeExamData data where data.participant.uuid="+userId+" and data.examInfo.sid="+examInfoId+" and data.examQuest.sid="+questionId;
//		List<TeeExamData> datas = new ArrayList<TeeExamData>();
		TeeExamData examData = (TeeExamData) simpleDaoSupport.unique(hql, null);
//		datas=simpleDaoSupport.find(hql, null);
//		if(datas.size()>0){
//			examData=datas.get(0);
//		}
		if(examData==null){
			examData = new TeeExamData();
		}
		return examData;
	}

	public int getRealScore(int examInfoId, int userId) {
		int realScoreInt=0;
//		String hql = "from TeeExamData data where data.participant.uuid="+userId+" and data.examInfo.sid="+examInfoId;
//		List<TeeExamData> datas = new ArrayList<TeeExamData>();
//		datas=simpleDaoSupport.find(hql, null);
//		for(TeeExamData examData:datas){
//			realScore+=examData.getScore();
//		}
		TeeExamRecord realScore = (TeeExamRecord) simpleDaoSupport.unique("from TeeExamRecord where participant.uuid=? and teeExamInfo.sid=?", new Object[]{userId,examInfoId});
		if(realScore!=null){
			realScoreInt = realScore.getScore();
		}
		return realScoreInt;
	}

	/**
	 * 根据考试信息获取当前试卷的考试时长
	 * @param examInfoId
	 * @return
	 */
	public TeeExamPaper getExamTimes(int examInfoId) {
		TeeExamInfo info = (TeeExamInfo)simpleDaoSupport.get(TeeExamInfo.class,examInfoId);
		TeeExamPaper paper = info.getExamPaper();
		return paper;
	}

	/**
	 * 判断是否存在主观题
	 * @param paperId
	 * @return
	 */
	public boolean isHasSubjective(int paperId) {
		boolean flag=false;
		TeeExamPaper paper =(TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,paperId);
		List<TeeExamQuestion> questions = paper.getQuestionList();
		for(TeeExamQuestion question:questions){
			if(question.getQType()==3){
				flag=true;
				break;
			}
		}
		return flag;
	}

	/**
	 *阅卷完毕 更新当前试卷为已阅,同时提交阅卷的时间
	 * @param userId
	 * @param examInfoId
	 */
	public void updateExamRecord(int userId, int examInfoId) {
		String hql = "from TeeExamRecord record where record.teeExamInfo.sid="+examInfoId+" and record.participant.uuid="+userId;
		TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql, null);
		record.setChecked(true);
		Calendar cl = Calendar.getInstance();
		record.setCheckExamTime(cl);
		simpleDaoSupport.update(record);
	}
	
	
	/**
	 *交卷时，更新保存上交卷时间
	 * @param userId
	 * @param examInfoId
	 */
	public void updateExamRecord2(int userId, int examInfoId) {
		String hql = "from TeeExamRecord record where record.teeExamInfo.sid="+examInfoId+" and record.participant.uuid="+userId;
		TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql, null);
		Calendar cl = Calendar.getInstance();
		record.setSubExamTime(cl);
		simpleDaoSupport.update(record);
	}

	/**
	 * 删除保存的试题信息
	 * @param userId
	 * @param examInfoId
	 */
	public void delExamData(int userId, int examInfoId) {
		String hql = "delete TeeExamData data where data.examInfo.sid="+examInfoId+" and data.participant.uuid="+userId;
		simpleDaoSupport.deleteOrUpdateByQuery(hql, null);
	}
	
	

	/**
	 * 判断当前用户当前试卷是否已经交卷
	 * @param examInfoId
	 * @param userId
	 * @return
	 */
	public boolean isSubExam(int examInfoId, int userId) {
		boolean flag=false;
		String hql = "from TeeExamRecord record where record.teeExamInfo.sid="+examInfoId+" and record.participant.uuid="+userId;
		TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql, null);
		if(record.getSubExamTime()==null){
			flag=true;
		}
		return flag;
	}

	/**
	 * 获取已经考试了多长时间
	 * @param examInfoId
	 * @param userId
	 * @return
	 */
	public long getStartTimes(int examInfoId,int userId) {
		long startTimes=0;
		String hql = "from TeeExamRecord record where record.teeExamInfo.sid="+examInfoId+" and record.participant.uuid="+userId;
		TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql, null);
		Calendar cl = Calendar.getInstance();
		if(record!=null && record.getStartExamTime()!=null){
			Calendar startTime = record.getStartExamTime();
			startTimes = cl.getTimeInMillis()-startTime.getTimeInMillis();
		}
		return startTimes/1000;
	}

	/**
	 * 获取考试开始时间
	 * @param examInfoId
	 * @param userId
	 * @return
	 */
	public Calendar getStartExamTime(int examInfoId, int userId) {
		String hql = "from TeeExamRecord record where record.teeExamInfo.sid="+examInfoId+" and record.participant.uuid="+userId;
		TeeExamRecord record = (TeeExamRecord)simpleDaoSupport.loadSingleObject(hql, null);
		Calendar cl =record.getStartExamTime();
		return cl;
	}
	
}
