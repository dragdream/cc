package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.dao.TeeSmsBodyDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowSortDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.dao.TeeFormDao;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowRuleDao;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowSeniorQueryDao;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowRuleModel;
import com.tianee.oa.core.workflow.workmanage.model.TeeSeniorQueryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeFlowSeniorQueryService extends TeeBaseService implements TeeFlowSeniorQueryServiceInterface{
	
	@Autowired
	private TeeFlowSeniorQueryDao queryDao;
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface#getQueryTplByFlowId(com.tianee.oa.core.org.bean.TeePerson, int)
	 */
	@Override
	public List<TeeSeniorQueryModel> getQueryTplByFlowId(TeePerson person,int flowId){
		

		 List<TeeSeniorQueryModel> list = new ArrayList<TeeSeniorQueryModel>();
		 
		 list = queryDao.getQueryTplByFlowId(person, flowId);

		
		 return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface#saveTpl(com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl)
	 */
	@Override
	@Transactional(readOnly=false)
	public void saveTpl(TeeFlowQueryTpl queryTpl){
		
		queryDao.save(queryTpl);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface#updateTpl(com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl)
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateTpl(TeeFlowQueryTpl queryTpl){
		
		queryDao.update(queryTpl);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface#deleteTpl(com.tianee.oa.core.workflow.workmanage.bean.TeeFlowQueryTpl)
	 */
	@Override
	@Transactional(readOnly=false)
	public void deleteTpl(TeeFlowQueryTpl queryTpl){
		
		queryDao.deleteByObj(queryTpl);
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface#getFormByFlowId(int)
	 */
	@Override
	public TeeForm getFormByFlowId(int flowId){
		
		TeeForm form = flowTypeService.get(flowId).getForm();
		
		return form;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowSeniorQueryServiceInterface#getById(int)
	 */
	@Override
	public TeeFlowQueryTpl getById(int tplId){
		
		TeeFlowQueryTpl queryTpl = queryDao.get(tplId);
		
		return queryTpl;
	}
	
 
	    

}


