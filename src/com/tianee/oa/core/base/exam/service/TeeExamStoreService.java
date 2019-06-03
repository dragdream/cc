package com.tianee.oa.core.base.exam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.bean.TeeExamStore;
import com.tianee.oa.core.base.exam.model.TeeExamStoreModel;
import com.tianee.oa.core.base.officeProducts.bean.TeeOfficeDepository;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeExamStoreService extends TeeBaseService{
	
	@Autowired
	private TeeExamQuestionService examQuestionService;
	
	@Autowired
	private TeeExamPaperService examPaperService;
	
	public TeeExamStore addExamStore(TeeExamStore examStore){
		getSimpleDaoSupport().save(examStore);
		return examStore;
	}
	
	public TeeExamStore addExamStoreModel(TeeExamStoreModel examStoreModel){
		TeeExamStore examStore = new TeeExamStore();
		BeanUtils.copyProperties(examStoreModel, examStore);
		addExamStore(examStore);
		return examStore;
	}
	
	public TeeExamStore deleteExamStore(TeeExamStore examStore){
		//获取paper
		List<TeeExamPaper> papers = simpleDaoSupport.find("from TeeExamPaper where examStore.sid=?", new Object[]{examStore.getSid()});
		for(TeeExamPaper paper:papers){
			examPaperService.deleteExamPaper(paper);
		}
		
		List<TeeExamQuestion> questions = simpleDaoSupport.find("from TeeExamQuestion where examStore.sid=?", new Object[]{examStore.getSid()});
		for(TeeExamQuestion question:questions){
			examQuestionService.deleteExamQuestion(question);
		}
		
		getSimpleDaoSupport().deleteByObj(examStore);
		return examStore;
	}
	
	public void updateExamStore(TeeExamStore examStore){
		getSimpleDaoSupport().update(examStore);
	}
	
	public void updateExamStoreModel(TeeExamStoreModel examStoreModel){
		TeeExamStore examStore = (TeeExamStore) simpleDaoSupport.get(TeeExamStore.class, examStoreModel.getSid());
		BeanUtils.copyProperties(examStoreModel, examStore);
		updateExamStore(examStore);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeExamStore oc where 1=1 ";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List rows = new ArrayList();
		List<TeeExamStore> list = simpleDaoSupport.pageFind(hql+" order by oc.sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeExamStore examStore:list){
			TeeExamStoreModel model = new TeeExamStoreModel();
			BeanUtils.copyProperties(examStore, model);
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public TeeExamStore getById(int sid){
		TeeExamStore examStore = (TeeExamStore) simpleDaoSupport.get(TeeExamStore.class, sid);
		return examStore;
	}
	
	public List<TeeExamStoreModel> getCatListWithNoPriv(int deposId){
		String hql = "from TeeExamStore oc where oc.officeDepository.sid="+deposId;
		List<TeeExamStoreModel> models = new ArrayList<TeeExamStoreModel>();
		List<TeeExamStore> list = simpleDaoSupport.find(hql, null);
		for(TeeExamStore oc:list){
			TeeExamStoreModel m = new TeeExamStoreModel();
			models.add(m);
		}
		return models;
	}

	
	
	/**
	 * 获取所有的题库信息不分页
	 * @param request
	 * @return
	 */
	public TeeJson getAllExamStore(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql = "from TeeExamStore oc where 1=1 ";
		List<TeeExamStore> list = simpleDaoSupport.executeQuery(hql+" order by oc.sid asc", null);
		List<TeeExamStoreModel> modelList=new ArrayList<TeeExamStoreModel>();
		for(TeeExamStore examStore:list){
			TeeExamStoreModel model = new TeeExamStoreModel();
			BeanUtils.copyProperties(examStore, model);
			modelList.add(model);
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return  json;
	}
}
