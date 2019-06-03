package com.beidasoft.xzzf.punish.classicCase.service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCase;
import com.beidasoft.xzzf.punish.classicCase.bean.ClassicCaseCollection;
import com.beidasoft.xzzf.punish.classicCase.dao.ClassicCaseCollectionDao;
import com.beidasoft.xzzf.punish.classicCase.model.ClassicCaseModel;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class ClassicCaseCollectionService extends TeeBaseService {
	@Autowired
	private ClassicCaseCollectionDao classicCaseCollectionDao;
	@Autowired
	private PunishBaseDao punishBaseDao;
	
	/**
	 * 根据id查询
	 */
	public TeeJson loadById(String id) {
		TeeJson json = new TeeJson();
		ClassicCaseCollection classicCaseCollection = classicCaseCollectionDao.loadById(id);
		json.setRtData(classicCaseCollection);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据案件id查询
	 */
	public TeeJson loadByCaseId(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		ClassicCaseCollection classicCaseCollection = classicCaseCollectionDao.loadByCaseId(id,request);
		json.setRtData(classicCaseCollection);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据案件id查询是否收藏
	 */
	public TeeJson isCollect(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		ClassicCaseCollection classicCaseCollection = classicCaseCollectionDao.loadByCaseId(id,request);
		if(TeeUtility.isNullorEmpty(classicCaseCollection)){
			json.setRtData(false);
		}else{
			json.setRtData(true);
		}
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 保存
	 */
	public TeeJson saveClassicCaseCollection(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		ClassicCaseCollection classicCaseCollection = new ClassicCaseCollection();
		classicCaseCollection.setId(UUID.randomUUID().toString());
		classicCaseCollection.setClassicCaseId(id);
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		classicCaseCollection.setPersonId(user.getUuid());
		classicCaseCollectionDao.saveClassicCaseCollection(classicCaseCollection);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除
	 */
	public TeeJson deleteClassicCaseCollection(String id,HttpServletRequest request) {
		TeeJson json = new TeeJson();
		classicCaseCollectionDao.deleteByObj(classicCaseCollectionDao.loadByCaseId(id,request));
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 分页查询
	 * @param dm
	 * @param request
	 * @param baseId
	 * @return
	 */
	public TeeEasyuiDataGridJson pageList(TeeDataGridModel dm, ClassicCase classicCase, HttpServletRequest request) {
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<ClassicCaseCollection> caseList = simpleDaoSupport.find("from ClassicCaseCollection where personId="+user.getUuid());
		StringBuffer ids = new StringBuffer("");
		for (int i = 0; i < caseList.size(); i++) {
			ids.append("'");
			ids.append(caseList.get(i).getClassicCaseId());
			ids.append("'");
			
			if(i < caseList.size()-1){
				ids.append(",");
			}
		}
		if(caseList.size()==0){
			ids.append("''");
		}
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		//查询条件list
		List param = new ArrayList();
		//基础hql
		String hql = " from ClassicCase  where 1=1 and delFlag=0 and id in("+ids+") ";
		//title查询条件
		if(!TeeUtility.isNullorEmpty(classicCase.getBaseTitle())){
			String title = TeeDbUtility.formatString(classicCase.getBaseTitle());
			hql += " and baseTitle like '%"+title+"%' ";
		}
		//sourceType查询条件
		if(!TeeUtility.isNullorEmpty(classicCase.getSourceType())){
			hql += " and sourceType="+classicCase.getSourceType();
		}
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<ClassicCase> list = simpleDaoSupport.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		List<ClassicCaseModel> models = new ArrayList<ClassicCaseModel>();
		ClassicCaseModel classicCaseModel = null;
		for(ClassicCase row : list) {
			classicCaseModel = new ClassicCaseModel();
			classicCaseModel = transferModel(row);
			models.add(classicCaseModel);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}

	//bean转model
	public ClassicCaseModel transferModel(ClassicCase classicCase){
		ClassicCaseModel model = new ClassicCaseModel();
		BeanUtils.copyProperties(classicCase, model);
		//转换登记接案时间
		if(!TeeUtility.isNullorEmpty(classicCase.getFilingDate())){
			model.setFilingDateStr(TeeDateUtil.format(classicCase.getFilingDate(), "yyyy-MM-dd"));
		}
		//转换立案受理时间
		if(!TeeUtility.isNullorEmpty(classicCase.getClosedDate())){
			model.setClosedDateStr(TeeDateUtil.format(classicCase.getClosedDate(), "yyyy-MM-dd"));
		}
		
		return model;
	}
}
