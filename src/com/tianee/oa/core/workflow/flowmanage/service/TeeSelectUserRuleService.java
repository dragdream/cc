package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowService;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeSelectUserRuleService extends TeeBaseService implements TeeSelectUserRuleServiceInterface{
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeFlowRunPrcsDao frpDao;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#leadByBeginUser(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule)
	 */
	@Override
	public Set<TeePerson> leadByBeginUser(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,TeeSelectUserRule rule){
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.add(flowRun.getBeginPerson());
		set.addAll(leadByDeptFilter(flowRun, frp, nextPrcs, person, rule,set));
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#leadByCurrentPrcsUser(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule)
	 */
	@Override
	public Set<TeePerson> leadByCurrentPrcsUser(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,TeeSelectUserRule rule){
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.add(person);
		set.addAll(leadByDeptFilter(flowRun, frp, nextPrcs, person, rule,set));
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#leadByAllPrcsUser(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule)
	 */
	@Override
	public Set<TeePerson> leadByAllPrcsUser(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,TeeSelectUserRule rule){
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.addAll(frpDao.getPersonsByFlowRun(flowRun.getRunId()));
		set.addAll(leadByDeptFilter(flowRun, frp, nextPrcs, person, rule,set));
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#leadByThisPrcsUsers(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule)
	 */
	@Override
	public Set<TeePerson> leadByThisPrcsUsers(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,TeeSelectUserRule rule){
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.addAll(frpDao.getPersonsByCurPrcsUsers(flowRun.getRunId(), frp.getFlowPrcs().getSid(), frp.getPrcsId()));
		set.addAll(leadByDeptFilter(flowRun, frp, nextPrcs, person, rule,set));
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#leadByPrePrcsUsers(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule)
	 */
	@Override
	public Set<TeePerson> leadByPrePrcsUsers(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,TeeSelectUserRule rule){
		Set<TeePerson> set = new HashSet<TeePerson>();
		set.addAll(frpDao.getPersonsByPrePrcs(frp));
		set.addAll(leadByDeptFilter(flowRun, frp, nextPrcs, person, rule,set));
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#leadByDeptFilter(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule, java.util.Set)
	 */
	@Override
	public Set<TeePerson> leadByDeptFilter(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,TeeSelectUserRule rule,Set<TeePerson> personSet){
		Set<TeePerson> set = new HashSet<TeePerson>();
		int deptType = rule.getDeptType();
		
		switch(deptType){
		case 1://无约束
			break;
		case 2://所属部门
			set.addAll(findByDirectDept(personSet));
			break;
		case 3://所属部门主管领导
			set.addAll(findByDirectDeptLeader1(personSet));
			break;
		case 4://所属部门分管领导
			set.addAll(findByDirectDeptLeader2(personSet));
			break;
		case 5://上级部门
			set.addAll(findByParentDept(personSet));
			break;
		case 6://上级部门主管领导
			set.addAll(findByParentDeptLeader1(personSet));
			break;
		case 7://上级部门分管领导
			set.addAll(findByParentDeptLeader2(personSet));
			break;
		case 8://下级部门
			set.addAll(findByChildDept(personSet));
			break;
		case 9://下级部门主管领导
			set.addAll(findByChildDeptLeader1(personSet));
			break;
		case 10://下级部门分管领导
			set.addAll(findByChildDeptLeader2(personSet));
			break;
		}
		
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByDirectDept(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByDirectDept(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer deptIds = new StringBuffer();
		for(TeePerson p : persons){
			deptIds.append(p.getDept().getUuid()+",");
//			for(TeeDepartment dept:p.getPostDept()){
//				deptIds.append(dept.getUuid()+",");
//			}
		}
		if(deptIds.length()!=0){
			deptIds.deleteCharAt(deptIds.length()-1);
		}
		set.addAll(personDao.getPersonByDeptIds(deptIds.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByDirectDeptLeader1(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByDirectDeptLeader1(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer leaderIds = new StringBuffer();
		for(TeePerson p : persons){
			leaderIds.append(p.getDept().getLeader1()+",");
		}
		if(leaderIds.length()!=0){
			leaderIds.deleteCharAt(leaderIds.length()-1);
		}
		set.addAll(personDao.getPersonByUuids(leaderIds.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByDirectDeptLeader2(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByDirectDeptLeader2(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer leaderIds = new StringBuffer();
		for(TeePerson p : persons){
			leaderIds.append(p.getDept().getLeader2()+",");
		}
		if(leaderIds.length()!=0){
			leaderIds.deleteCharAt(leaderIds.length()-1);
		}
		set.addAll(personDao.getPersonByUuids(leaderIds.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByParentDept(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByParentDept(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer parentDeptIds = new StringBuffer();
		TeeDepartment parentDept = null;
		for(TeePerson p : persons){
			parentDept = p.getDept().getDeptParent();
			if(parentDept!=null){
				parentDeptIds.append(parentDept.getUuid()+",");
			}
		}
		if(parentDeptIds.length()!=0){
			parentDeptIds.deleteCharAt(parentDeptIds.length()-1);
		}
		set.addAll(personDao.getPersonByDeptIds(parentDeptIds.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByParentDeptLeader1(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByParentDeptLeader1(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer parentDeptLeader1Ids = new StringBuffer();
		TeeDepartment parentDept = null;
		for(TeePerson p : persons){
			parentDept = p.getDept().getDeptParent();
			if(parentDept!=null){
				parentDeptLeader1Ids.append(parentDept.getLeader1()+",");
			}
		}
		if(parentDeptLeader1Ids.length()!=0){
			parentDeptLeader1Ids.deleteCharAt(parentDeptLeader1Ids.length()-1);
		}
		set.addAll(personDao.getPersonByUuids(parentDeptLeader1Ids.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByParentDeptLeader2(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByParentDeptLeader2(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer parentDeptLeader2Ids = new StringBuffer();
		TeeDepartment parentDept = null;
		for(TeePerson p : persons){
			parentDept = p.getDept().getDeptParent();
			if(parentDept!=null){
				parentDeptLeader2Ids.append(parentDept.getLeader2()+",");
			}
		}
		if(parentDeptLeader2Ids.length()!=0){
			parentDeptLeader2Ids.deleteCharAt(parentDeptLeader2Ids.length()-1);
		}
		set.addAll(personDao.getPersonByUuids(parentDeptLeader2Ids.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByChildDept(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByChildDept(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer childDeptIds = new StringBuffer();
		List<TeeDepartment> childDept = null;
		for(TeePerson p : persons){
			childDept = p.getDept().getChildren();
			for(TeeDepartment dept:childDept){
				childDeptIds.append(dept.getUuid()+",");
			}
		}
		if(childDeptIds.length()!=0){
			childDeptIds.deleteCharAt(childDeptIds.length()-1);
		}
		set.addAll(personDao.getPersonByDeptIds(childDeptIds.toString()));
		return set;
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByChildDeptLeader1(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByChildDeptLeader1(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer childDeptLeader1Ids = new StringBuffer();
		List<TeeDepartment> childDept = null;
		for(TeePerson p : persons){
			childDept = p.getDept().getChildren();
			for(TeeDepartment dept:childDept){
				childDeptLeader1Ids.append(dept.getLeader1()+",");
			}
		}
		if(childDeptLeader1Ids.length()!=0){
			childDeptLeader1Ids.deleteCharAt(childDeptLeader1Ids.length()-1);
		}
		set.addAll(personDao.getPersonByUuids(childDeptLeader1Ids.toString()));
		return set;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#findByChildDeptLeader2(java.util.Set)
	 */
	@Override
	public Set<TeePerson> findByChildDeptLeader2(Set<TeePerson> persons){
		Set<TeePerson> set = new HashSet<TeePerson>();
		StringBuffer childDeptLeader2Ids = new StringBuffer();
		List<TeeDepartment> childDept = null;
		for(TeePerson p : persons){
			childDept = p.getDept().getChildren();
			for(TeeDepartment dept:childDept){
				childDeptLeader2Ids.append(dept.getLeader2()+",");
			}
		}
		if(childDeptLeader2Ids.length()!=0){
			childDeptLeader2Ids.deleteCharAt(childDeptLeader2Ids.length()-1);
		}
		set.addAll(personDao.getPersonByUuids(childDeptLeader2Ids.toString()));
		return set;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface#setPersonDao(com.tianee.oa.core.org.dao.TeePersonDao)
	 */
	@Override
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}

}
