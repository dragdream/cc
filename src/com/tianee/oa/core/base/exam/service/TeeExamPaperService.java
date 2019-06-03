package com.tianee.oa.core.base.exam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.bean.TeeExamStore;
import com.tianee.oa.core.base.exam.model.TeeExamPaperModel;
import com.tianee.oa.core.base.exam.model.TeeExamQuestionModel;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeDepository;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeExamPaperService extends TeeBaseService{
	
	@Autowired
	private TeeExamInfoService examInfoService;
	
	public TeeExamPaper addExamPaper(TeeExamPaper examPaper){
		getSimpleDaoSupport().save(examPaper);
		return examPaper;
	}
	
	public TeeExamPaper addExamPaperModel(TeeExamPaperModel examPaperModel){
		TeeExamPaper examPaper = new TeeExamPaper();
		BeanUtils.copyProperties(examPaperModel, examPaper);
		TeeExamStore examStore = (TeeExamStore)simpleDaoSupport.get(TeeExamStore.class,examPaperModel.getStoreId());
		examPaper.setExamStore(examStore);
		addExamPaper(examPaper);
		return examPaper;
	}
	
	public TeeExamPaper deleteExamPaper(TeeExamPaper examPaper){
		//删除考试信息
		List<TeeExamInfo> infos = simpleDaoSupport.find("from TeeExamInfo where examPaper.sid=?", new Object[]{examPaper.getSid()});
		for(TeeExamInfo info:infos){
			examInfoService.deleteExamInfo(info);
		}
		getSimpleDaoSupport().deleteByObj(examPaper);
		return examPaper;
	}
	
	public void updateExamPaper(TeeExamPaper examPaper){
		getSimpleDaoSupport().update(examPaper);
	}
	
	public void updateExamPaperModel(TeeExamPaperModel examPaperModel){
		TeeExamPaper examPaper = (TeeExamPaper) simpleDaoSupport.get(TeeExamPaper.class, examPaperModel.getSid());
		BeanUtils.copyProperties(examPaperModel, examPaper);
		TeeExamStore examStore = (TeeExamStore)simpleDaoSupport.get(TeeExamStore.class,examPaperModel.getStoreId());
		examPaper.setExamStore(examStore);
		updateExamPaper(examPaper);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeExamPaper oc where 1=1 ";
		//如果不是管理员的话，则进行权限校验
		
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		List rows = new ArrayList();
		List<TeeExamPaper> list = simpleDaoSupport.pageFind(hql+" order by oc.sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeExamPaper examPaper:list){
			TeeExamPaperModel model = new TeeExamPaperModel();
			BeanUtils.copyProperties(examPaper, model);
			model.setStoreId(examPaper.getExamStore().getSid());
			int scoreType=examPaper.getScoreType();
			if(scoreType==1){
				model.setScoreTypeDesc("根据试题分值以百分比计算");
			}else{
				model.setScoreTypeDesc("按试题分数计分");
				//获取试题分数
				List<TeeExamQuestion> questions = examPaper.getQuestionList();
				long totalCount = 0;
				for(TeeExamQuestion question:questions){
					totalCount+=question.getScore();
				}
				model.setScoreAll(Integer.parseInt(totalCount+""));
			}
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public TeeExamPaper getById(int sid){
		TeeExamPaper examPaper = (TeeExamPaper) simpleDaoSupport.get(TeeExamPaper.class, sid);
		return examPaper;
	}
	
	
	
/*	*//**
	 * 获取题库里面的所有试题信息
	 * @author ny
	 * @param examInfoId
	 * @return
	 *//*
	public List<TeeExamQuestion> getQuestionList(int paperId){
		TeeExamPaper paper = (TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,paperId);
		TeeExamStore store = (TeeExamStore)simpleDaoSupport.get(TeeExamStore.class,paper.getExamStore().getSid());
		String hql = "from TeeExamQuestion question  where 1=1 and question.examStore.sid='"+store.getSid()+"'";
		List<TeeExamQuestion> questions=simpleDaoSupport.find(hql, null);
		return questions;
	}*/

	/**
	 * 获取题库里面的所有试题信息
	 * @author ny
	 * @param examInfoId
	 * @return
	 */
	public TeeEasyuiDataGridJson getQuestionList(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		int paperId = TeeStringUtil.getInteger(requestDatas.get("paperId"),0);
		TeeExamPaper paper = (TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,paperId);
		String hql = "from TeeExamQuestion oc where 1=1 and oc.examStore.sid='"+paper.getExamStore().getSid()+"'";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeExamQuestion> questionList  = simpleDaoSupport.pageFind(hql+" order by oc.qType asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<TeeExamQuestionModel> models = new ArrayList<TeeExamQuestionModel>();
		for(TeeExamQuestion question:questionList){
			TeeExamQuestionModel model = new TeeExamQuestionModel();
			BeanUtils.copyProperties(question, model);
			if(question.getQType()==1){
				model.setqTypeDesc("单选");
			}else if(question.getQType()==2){
				model.setqTypeDesc("多选");
			}else{
				model.setqTypeDesc("主观");
			}
			if(question.getQHard()==1){
				model.setqHardDesc("低");
			}else if(question.getQHard()==2){
				model.setqHardDesc("中");
			}else{
				model.setqHardDesc("高");
			}
			if(this.isChecked(paperId, question.getSid())){
				model.setCheck(true);
			}else{
				model.setCheck(false);
			}
			model.setStoreName(paper.getExamStore().getStoreName());
			models.add(model);
		}
		datagird.setRows(models);
		datagird.setTotal(total);
		return datagird;
	}

	/**
	 * 随机获取试题
	 * @author ny
	 * @param sid
	 * @param count
	 * @param storeId
	 */
	public void autoSelectQuestion(int sid) {
		TeeExamPaper paper = (TeeExamPaper) simpleDaoSupport.get(TeeExamPaper.class, sid);
		String hql = "from TeeExamQuestion q where q.examStore.sid="+paper.getExamStore().getSid()+" order by rand(),q.qHard";
		List<TeeExamQuestion> list = simpleDaoSupport.pageFind(hql,0,paper.getQCount(),null);
		paper.setQuestionList(list);
		updateExamPaper(paper);
	}
	
	/**
	 * 判断当前试题是否已经选中
	 * @param paperId
	 * @param questionId
	 * @return
	 */
	public boolean isChecked(int paperId,int questionId){
		TeeExamPaper paper = (TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,paperId);
		List<TeeExamQuestion> questions = paper.getQuestionList();
		boolean flag=false;
		for(TeeExamQuestion question:questions){
			if(question.getSid()==questionId){
				flag =  true;
				break;
			}
		}
		return flag;
	}

/*	public void saveSelectedQuestion(String selectedList,int paperId) {
		//去掉已选择的试题
		String[] list = selectedList.split(",");
		List<Integer> newList= new ArrayList<Integer>();
		for(String idStr:list){
			int id=Integer.parseInt(idStr);
			if(!isChecked(paperId,id)){
				newList.add(id);
			}
		}
		//将新选择的试题更新到库里面
		TeeExamPaper paper = (TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,paperId);
		List<TeeExamQuestion> questions = paper.getQuestionList();
		for(int i=0;i<newList.size();i++){
			TeeExamQuestion question = (TeeExamQuestion)simpleDaoSupport.get(TeeExamQuestion.class,newList.get(i));
			questions.add(question);
		}
		paper.setQuestionList(questions);
		updateExamPaper(paper);
	}*/
	
	public void saveSelectedQuestion(String selectedList,int paperId) {
		List<TeeExamQuestion> questions = new ArrayList<TeeExamQuestion>();
		TeeExamPaper paper = (TeeExamPaper)simpleDaoSupport.get(TeeExamPaper.class,paperId);
		if(TeeUtility.isNullorEmpty(selectedList)){
			questions=null;
		}else{
			String[] list = selectedList.split(",");
			List<Integer> newList= new ArrayList<Integer>();
			for(String idStr:list){
				int id=Integer.parseInt(idStr);
				newList.add(id);
			}
			for(int i=0;i<newList.size();i++){
				TeeExamQuestion question = (TeeExamQuestion)simpleDaoSupport.get(TeeExamQuestion.class,newList.get(i));
				questions.add(question);
			}
		}
		paper.setQuestionList(questions);
		updateExamPaper(paper);
	}
}
