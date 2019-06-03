package com.tianee.oa.core.base.exam.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.exam.bean.TeeExamPaper;
import com.tianee.oa.core.base.exam.bean.TeeExamQuestion;
import com.tianee.oa.core.base.exam.bean.TeeExamStore;
import com.tianee.oa.core.base.exam.model.TeeExamQuestionModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeExamQuestionService extends TeeBaseService{
	
	@Autowired
	private TeeAttachmentDao attachmentDao;
	
	public TeeExamQuestion addExamQuestion(TeeExamQuestion examQuestion){
		getSimpleDaoSupport().save(examQuestion);
		return examQuestion;
	}
	
	public TeeExamQuestion addExamQuestionModel(TeeExamQuestionModel examQuestionModel){
		TeeExamQuestion examQuestion = new TeeExamQuestion();
		BeanUtils.copyProperties(examQuestionModel, examQuestion);
		
		TeeExamStore examStore = (TeeExamStore)simpleDaoSupport.get(TeeExamStore.class,examQuestionModel.getStoreId());
		examQuestion.setExamStore(examStore);
		List attaches = examQuestionModel.getAttacheModels();
		addExamQuestion(examQuestion);
		for(int i=0;i<attaches.size();i++){
			TeeAttachment attach = (TeeAttachment) attaches.get(i);
			attach.setModelId(String.valueOf(examQuestion.getSid()));
			simpleDaoSupport.update(attach);
		}
		return examQuestion;
	}
	
	public TeeExamQuestion deleteExamQuestion(TeeExamQuestion examQuestion){
		simpleDaoSupport.executeUpdate("delete from TeeExamData where examInfo.sid=?", new Object[]{examQuestion.getSid()});
		List<TeeExamPaper> papers = simpleDaoSupport.find("from TeeExamPaper paper where exists (select 1 from paper.questionList questionList where questionList.sid=?)",  new Object[]{examQuestion.getSid()});
		for(TeeExamPaper paper:papers){
			paper.getQuestionList().remove(examQuestion);
		}
		getSimpleDaoSupport().deleteByObj(examQuestion);
		return examQuestion;
	}
	
	public void updateExamQuestion(TeeExamQuestion examQuestion){
		getSimpleDaoSupport().update(examQuestion);
	}
	
	public void updateExamQuestionModel(TeeExamQuestionModel examQuestionModel){
		TeeExamQuestion examQuestion = (TeeExamQuestion) simpleDaoSupport.get(TeeExamQuestion.class, examQuestionModel.getSid());
		BeanUtils.copyProperties(examQuestionModel, examQuestion);
		TeeExamStore examStore = (TeeExamStore)simpleDaoSupport.get(TeeExamStore.class,examQuestionModel.getStoreId());
		examQuestion.setExamStore(examStore);
		List attaches = examQuestionModel.getAttacheModels();
		if(attaches!=null && attaches.size()>0){
			for(int i=0;i<attaches.size();i++){
				TeeAttachment attach = (TeeAttachment) attaches.get(i);
				attach.setModelId(String.valueOf(examQuestion.getSid()));
				simpleDaoSupport.update(attach);
			}
		}
		updateExamQuestion(examQuestion);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		//所属题库
		int storeId=TeeStringUtil.getInteger(requestDatas.get("storeId"),0);
		
		
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		String hql = "from TeeExamQuestion oc where 1=1 ";
		
		if(storeId!=0){
			hql+=" and oc.examStore.sid="+storeId;
		}
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		
		List rows = new ArrayList();
		List<TeeExamQuestion> list = simpleDaoSupport.pageFind(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		for(TeeExamQuestion examQuestion:list){
			TeeExamQuestionModel model = new TeeExamQuestionModel();
			BeanUtils.copyProperties(examQuestion, model);
			model.setStoreId(examQuestion.getExamStore().getSid());
			model.setStoreName(examQuestion.getExamStore().getStoreName());
			int qHard = examQuestion.getQHard();
			int qType = examQuestion.getQType();
			if(qHard==1){
				model.setqHardDesc("低");
			}else if(qHard==2){
				model.setqHardDesc("中");
			}else{
				model.setqHardDesc("高");
			}
			if(qType==1){
				model.setqTypeDesc("单选");
			}else if(qType==2){
				model.setqTypeDesc("多选");
			}else{
				model.setqTypeDesc("主观");
			}
			//获取附件
			List<TeeAttachment> attaches = attachmentDao.getAttaches(TeeAttachmentModelKeys.exam, String.valueOf(examQuestion.getSid()));
			List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
			for(TeeAttachment attach:attaches){
				TeeAttachmentModel m = new TeeAttachmentModel();
				BeanUtils.copyProperties(attach, m);
				m.setUserId(attach.getUser().getUuid()+"");
				m.setUserName(attach.getUser().getUserName());
				m.setPriv(1+2+4+8+16+32);//一共五个权限好像     1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
				attachmodels.add(m);
			}
			model.setAttacheModels(attachmodels);
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	public TeeExamQuestion getById(int sid){
		TeeExamQuestion examQuestion = (TeeExamQuestion) simpleDaoSupport.get(TeeExamQuestion.class, sid);
		return examQuestion;
	}
	
	public List<TeeExamQuestion> getQuestionList(int storeId){
		String hql = "from TeeExamQuestion question where question.examStore.sid="+storeId;
		List<TeeExamQuestion> list = simpleDaoSupport.find(hql, null);
		return list;
	}
}
