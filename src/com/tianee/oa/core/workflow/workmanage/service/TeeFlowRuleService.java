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
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowSortDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.dao.TeeFlowRuleDao;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowRuleModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfo;

@Service
public class TeeFlowRuleService extends TeeBaseService implements TeeFlowRuleServiceInterface{
	
	@Autowired
	private TeeFlowRuleDao ruleDao;
	@Autowired
	private TeeFlowSortDao sortDao;
	@Autowired
	private TeeFlowTypeDao flowTypeDao;
	@Autowired
	private TeePersonDao personDao;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#deleteRule(int)
	 */
	@Override
	@TeeLoggingAnt(template="[{userName}]删除委托规则[{#.flowType.flowName}]，委托人：[{#.user.userName}]，" +
			"被委托人：[{#.toUser.userName}]",type="0002")
	public TeeFlowRule deleteRule(int ruleId){
		TeeFlowRule rule = ruleDao.get(ruleId);
		ruleDao.deleteByObj(rule);
		return rule;
	}
	
	 /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#addRule(int, int, java.util.Date, java.util.Date, java.lang.String, int)
	 */
    @Override
	public void addRule(int user,int toUser,Date startDate,Date endDate,String flowIdStr,int status){
    	
    	//获取当前委托人所委托的流程
		List<TeeFlowRule> rules = ruleDao.getDeligatorRules(user);
		List<TeeFlowType> rulesTypes = new ArrayList<TeeFlowType>();
		for(TeeFlowRule r:rules){
			rulesTypes.add(r.getFlowType());
		}
		
		int flowId = TeeStringUtil.getInteger(flowIdStr, 0);
		TeePerson person = personDao.selectPersonById(user);
		if(flowId==0){
			//获取有权限发起的流程
			List<TeeFlowType> ftList = flowTypeDao.getCreatablePrivFlowListByPerson(person);
			for(TeeFlowType ft:ftList){
				flowId = ft.getSid();
				//如果当前规则中不包含所选流程
				if(!rulesTypes.contains(ft)){
//					TeeFlowRule rule = new TeeFlowRule();
//		    		rule.setStatus(status);
//		    		rule.setBeginDate(startDate);
//		    		rule.setEndDate(endDate);
//		    		rule.setFlowType(ft);
//		    		rule.setToUser(personDao.get(toUser));
//		    		rule.setUser(personDao.get(user));
//		    		ruleDao.save(rule);
					
					//先判断是否有一直生效的该流程
					boolean alwaysValid = false;
					for(TeeFlowRule r:rules){
						if(r.getFlowType().getSid()==flowId && r.getStatus()==1){//一直有效
							alwaysValid = true;
							break;
						}
					}
					
					if(alwaysValid){//如果有一直有效的流程，则弹出报错信息
						continue;
					}
					
					if(status==1){//一直有效
						TeeFlowRule rule = new TeeFlowRule();
			    		rule.setStatus(status);
			    		rule.setBeginDate(startDate);
			    		rule.setEndDate(endDate);
			    		rule.setFlowType(ft);
			    		rule.setToUser(personDao.get(toUser));
			    		rule.setUser(personDao.get(user));
			    		ruleDao.save(rule);
					}else{
						boolean hasRepeated = false;//是否出现重叠
						for(TeeFlowRule r:rules){
							if(r.getFlowType().getSid()==flowId && 
									!(startDate.getTime()<r.getBeginDate().getTime() && endDate.getTime()<r.getBeginDate().getTime()) &&
									!(startDate.getTime()>r.getEndDate().getTime() && endDate.getTime()>r.getEndDate().getTime())){
								hasRepeated = true;
								break;
							}
						}
						
						if(hasRepeated){
							throw new TeeOperationException("该流程已存在当前时间内的委托，无法进行重叠设置！");
						}
						
						TeeFlowRule rule = new TeeFlowRule();
			    		rule.setStatus(status);
			    		rule.setBeginDate(startDate);
			    		rule.setEndDate(endDate);
			    		rule.setFlowType(ft);
			    		rule.setToUser(personDao.get(toUser));
			    		rule.setUser(personDao.get(user));
			    		ruleDao.save(rule);
			    		
					}
				}
			}
		}else{
			//先判断是否有一直生效的该流程
			boolean alwaysValid = false;
			for(TeeFlowRule r:rules){
				if(r.getFlowType().getSid()==flowId && r.getStatus()==1){//一直有效
					alwaysValid = true;
					break;
				}
			}
			
			if(alwaysValid){//如果有一直有效的流程，则弹出报错信息
				throw new TeeOperationException("该流程已设置一直有效，无法重复设置！");
			}
			
			
			if(status==1){//一直有效
				TeeFlowType ft = flowTypeDao.load(flowId);
				TeeFlowRule rule = new TeeFlowRule();
	    		rule.setStatus(status);
	    		rule.setBeginDate(startDate);
	    		rule.setEndDate(endDate);
	    		rule.setFlowType(ft);
	    		rule.setToUser(personDao.get(toUser));
	    		rule.setUser(personDao.get(user));
	    		ruleDao.save(rule);
			}else{
				boolean hasRepeated = false;//是否出现重叠
				for(TeeFlowRule r:rules){
					if(r.getFlowType().getSid()==flowId && 
							!(startDate.getTime()<r.getBeginDate().getTime() && endDate.getTime()<r.getBeginDate().getTime()) &&
							!(startDate.getTime()>r.getEndDate().getTime() && endDate.getTime()>r.getEndDate().getTime())){
						hasRepeated = true;
						break;
					}
				}
				
				if(hasRepeated){
					throw new TeeOperationException("该流程已存在当前时间内的委托，无法进行重叠设置！");
				}
				
				TeeFlowType ft = flowTypeDao.load(flowId);
				TeeFlowRule rule = new TeeFlowRule();
	    		rule.setStatus(status);
	    		rule.setBeginDate(startDate);
	    		rule.setEndDate(endDate);
	    		rule.setFlowType(ft);
	    		rule.setToUser(personDao.get(toUser));
	    		rule.setUser(personDao.get(user));
	    		ruleDao.save(rule);
	    		
			}
		}
    }
    
    
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#editRule(int, int, int, java.util.Date, java.util.Date, int, int)
	 */
    @Override
	public void editRule(int sid,int user,int toUser,Date startDate,Date endDate,int flowId,int status){
    	//获取当前委托人所委托的流程
		List<TeeFlowRule> rules = ruleDao.getDeligatorRules(user);
		List<TeeFlowType> rulesTypes = new ArrayList<TeeFlowType>();
		for(TeeFlowRule r:rules){
			rulesTypes.add(r.getFlowType());
		}
		
		TeePerson person = personDao.selectPersonById(user);
		
		//先判断是否有一直生效的该流程
		boolean alwaysValid = false;
		for(TeeFlowRule r:rules){
			if(r.getFlowType().getSid()==flowId && r.getStatus()==1 && sid!=r.getSid()){//一直有效
				alwaysValid = true;
				break;
			}
		}
		
		if(alwaysValid){//如果有一直有效的流程，则弹出报错信息
			throw new TeeOperationException("该流程已设置一直有效，无法重复设置！");
		}
		
		TeeFlowRule rule = (TeeFlowRule) simpleDaoSupport.get(TeeFlowRule.class, sid);
		
		if(status==1){//一直有效
			
    		rule.setStatus(status);
    		rule.setBeginDate(startDate);
    		rule.setEndDate(endDate);
    		rule.setToUser(personDao.get(toUser));
		}else{
			boolean hasRepeated = false;//是否出现重叠
			for(TeeFlowRule r:rules){
				if(r.getFlowType().getSid()==flowId && 
						!(startDate.getTime()<r.getBeginDate().getTime() && endDate.getTime()<r.getBeginDate().getTime()) &&
						!(startDate.getTime()>r.getEndDate().getTime() && endDate.getTime()>r.getEndDate().getTime())  && sid!=r.getSid()){
					hasRepeated = true;
					break;
				}
			}
			
			if(hasRepeated){
				throw new TeeOperationException("该流程已存在当前时间内的委托，无法进行重叠设置！");
			}
			
    		rule.setStatus(status);
    		rule.setBeginDate(startDate);
    		rule.setEndDate(endDate);
    		rule.setToUser(personDao.get(toUser));
		}
		
		ruleDao.update(rule);
    }
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#getRule(int)
	 */
    @Override
	public TeeFlowRuleModel getRule(int sid){
    	TeeFlowRule flowRule = (TeeFlowRule) simpleDaoSupport.get(TeeFlowRule.class, sid);
    	TeeFlowRuleModel flowRuleModel = new TeeFlowRuleModel();
    	
    	flowRuleModel.setFlowId(flowRule.getFlowType().getSid());
    	flowRuleModel.setFlowName(flowRule.getFlowType().getFlowName());
    	flowRuleModel.setUserId(flowRule.getUser().getUuid());
    	flowRuleModel.setUserName(flowRule.getUser().getUserName());
    	flowRuleModel.setToUser(flowRule.getToUser().getUuid());
    	flowRuleModel.setPrcsName(flowRule.getToUser().getUserName());
    	flowRuleModel.setStatus(flowRule.getStatus());
    	flowRuleModel.setBeginDate(flowRule.getBeginDate());
    	flowRuleModel.setEndDate(flowRule.getEndDate());
    	
    	return flowRuleModel;
    }
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#delAll(int, int)
	 */
    @Override
	public void delAll(int targetUserId,int flowId){
    	String hql="delete from TeeFlowRule where user.uuid="+targetUserId;
    	if(flowId>0){
    		hql+=" and flowType.sid="+flowId+" ";
    	}
    	simpleDaoSupport.executeUpdate(hql, null);
    }
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#getFlowTree()
	 */
    @Override
	public String  getFlowTree(){
    	
    	String data = "[";
    	
    	List<TeeFlowSort> sortList = sortDao.list();
    	for(TeeFlowSort sort : sortList){
    		//System.out.println(sort);
    		data+="{";
    		data+="id:0,text:'"+sort.getSortName()+"',isLeaf:false,children:[";
    		List<TeeFlowType> typeList =sort.getFlowTypeSet();
    		for(TeeFlowType type : typeList){
    			data+="{ id:"+type.getSid()+",text:'"+type.getFlowName()+"',isLeaf:true},";
    			
    		}
    		if(data.endsWith(",")){
    			data=data.substring(0, data.length()-1);
    			data+="]},";
    		}
    		if(data.endsWith(",")){
    			data=data.substring(0, data.length()-1);
    		}
    	}
    	data+="]";
    	return data;
    }
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#getDeligatorRules(int)
	 */
    @Override
	public List<TeeFlowRule> getDeligatorRules(int personId){
    	return ruleDao.getDeligatorRules(personId);
    }
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, int, java.lang.String, int)
	 */
    @Override
	@Transactional(readOnly = true)
   	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,int userId,String entrustStatus,int flowId) {
   		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
   		List<TeeFlowRuleModel> modelList = new ArrayList<TeeFlowRuleModel>();
   		try {
   			j.setTotal(ruleDao.getListCount(userId, entrustStatus,flowId));// 设置总记录数
   			modelList = ruleDao.getModelList(userId,entrustStatus,flowId,(dm.getPage()-1)*dm.getRows(),dm.getRows());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		
   		j.setRows(modelList);// 设置返回的行
   		return j;
   	}
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#returnRealPerson(com.tianee.oa.core.org.bean.TeePerson, int)
	 */
    @Override
	@Transactional(readOnly = true)
   	public List<TeePerson> returnRealPerson(TeePerson person,int flowTypeId) {
    	List<TeePerson> personList = new ArrayList<TeePerson>();

    	personList = ruleDao.getRealPerson(person,flowTypeId);
   		return personList;
   	}
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#entrustDatagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, int, java.lang.String, java.lang.String, int)
	 */
    @Override
	@Transactional(readOnly = true)
   	public TeeEasyuiDataGridJson entrustDatagrid(TeeDataGridModel dm,int userId,String qs,String entrustStatus,int flowId) {
   		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
   		List<TeeFlowRuleModel> modelList = new ArrayList<TeeFlowRuleModel>();
   		try {
   			j.setTotal(ruleDao.getEntrustListCount(userId,qs, entrustStatus,flowId));// 设置总记录数
   			modelList = ruleDao.getEntrustModelList(userId,qs,entrustStatus,flowId,(dm.getPage()-1)*dm.getRows(),dm.getRows());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		
   		j.setRows(modelList);// 设置返回的行
   		return j;
   	}
    
    /* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#entrustedDatagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, int, java.lang.String, java.lang.String, int)
	 */
    @Override
	@Transactional(readOnly = true)
   	public TeeEasyuiDataGridJson entrustedDatagrid(TeeDataGridModel dm,int userId,String qs,String entrustStatus,int flowId) {
   		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
   		List<TeeFlowRuleModel> modelList = new ArrayList<TeeFlowRuleModel>();
   		try {
   			j.setTotal(ruleDao.getEntrustedListCount(userId,qs, entrustStatus,flowId));// 设置总记录数
   			modelList = ruleDao.getEntrustedModelList(userId,qs, entrustStatus,flowId,(dm.getPage()-1)*dm.getRows(),dm.getRows());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		
   		j.setRows(modelList);// 设置返回的行
   		return j;
   	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeFlowRuleServiceInterface#setPersonDao(com.tianee.oa.core.org.dao.TeePersonDao)
	 */
	@Override
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
    
 
	    

}


