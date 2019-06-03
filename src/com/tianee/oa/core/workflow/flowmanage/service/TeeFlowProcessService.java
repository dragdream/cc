package com.tianee.oa.core.workflow.flowmanage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectAutoUserRule;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.TeeFormParser;
import com.tianee.oa.webframe.httpModel.TeeSimpleModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowProcessModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFlowProcessService extends TeeBaseService implements TeeFlowProcessServiceInterface{
	@Autowired
	private TeeFlowProcessDao flowProcessDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeeUserRoleDao roleDao;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#save(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess)
	 */
	public void save(TeeFlowProcess flowProcess){
		flowProcessDao.save(flowProcess);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#get(int)
	 */
	public TeeFlowProcess get(int sid){
		return flowProcessDao.get(sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#load(int)
	 */
	public TeeFlowProcess load(int sid){
		return flowProcessDao.load(sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#delete(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess)
	 */
	public void delete(TeeFlowProcess flowProcess){
		flowProcessDao.delete(flowProcess);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#deleteNotReal(int)
	 */
	public void deleteNotReal(int sid){
		TeeFlowProcess fp = get(sid);
		
		//清除所有关联该步骤的关系
		List<TeeFlowProcess> fps = simpleDaoSupport.find("from TeeFlowProcess fp where exists (select 1 from fp.nextProcess nextProcess where nextProcess.sid=?)", new Object[]{sid});
		Set<TeeFlowProcess> tmp;
		for(TeeFlowProcess f:fps){
			tmp = f.getNextProcess();
			tmp.remove(fp);
		}
		
		fp.setoFlowType(fp.getFlowType());
		fp.setFlowType(null);
		
		//清除与步骤相关的数据
		fp.getPrcsDept().clear();
		fp.getPrcsRole().clear();
		fp.getPrcsUser().clear();
		fp.getNextProcess().clear();
		fp.getEmailToUsers().clear();
		
		flowProcessDao.update(fp);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#deleteReal(int)
	 */
	public void deleteReal(int sid){
		TeeFlowProcess fp = get(sid);
		
		//清除所有关联该步骤的关系
		List<TeeFlowProcess> fps = simpleDaoSupport.find("from TeeFlowProcess fp where exists (select 1 from fp.nextProcess nextProcess where nextProcess.sid=?)", new Object[]{sid});
		Set<TeeFlowProcess> tmp;
		for(TeeFlowProcess f:fps){
			tmp = f.getNextProcess();
			tmp.remove(fp);
		}
		
		//清除与步骤相关的数据
		fp.getPrcsDept().clear();
		fp.getPrcsRole().clear();
		fp.getPrcsUser().clear();
		fp.getNextProcess().clear();
		fp.getEmailToUsers().clear();
		
		flowProcessDao.delete(sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#update(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess)
	 */
	public void update(TeeFlowProcess flowProcess){
		flowProcessDao.update(flowProcess);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#updateByMap(java.util.Map, int)
	 */
	public void updateByMap(Map updateItem,int id){
		flowProcessDao.update(updateItem, id);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getStartNodeListByUserPriv(com.tianee.oa.core.org.bean.TeePerson)
	 */
	public List<TeeFlowProcess> getStartNodeListByUserPriv(TeePerson person){
		List<TeeFlowProcess> list = flowProcessDao.getStartNodeList(person);
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#findFlowProcessByFlowType(int)
	 */
	public List<TeeFlowProcess> findFlowProcessByFlowType(int flowTypeId){
		return flowProcessDao.findFlowProcessByFlowType(flowTypeId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#updateProcessLayout(java.lang.String, int)
	 */
	public void updateProcessLayout(String jsonModel,int flowId){
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
//		List<Map<String,String>> list = jsonUtil.JsonStr2MapList(jsonModel);
		Map<String,String> m = jsonUtil.JsonStr2Map(jsonModel);
		
		//获取步骤集合
//		List<TeeFlowProcess> prcsList = findFlowProcessByFlowType(flowId);
//		
		int prcsSeqId;
		int x;
		int y;
		prcsSeqId = TeeStringUtil.getInteger(m.get("prcsSeqId"), 0);
		x = TeeStringUtil.getInteger(m.get("x"), 0);
		y = TeeStringUtil.getInteger(m.get("y"), 0);
		
		simpleDaoSupport.executeUpdate("update TeeFlowProcess fp set fp.x=?,fp.y=? where fp.sid=?", new Object[]{x,y,prcsSeqId});
//		for(TeeFlowProcess fp:prcsList){
//			for(Map m:list){
//				prcsSeqId = TeeStringUtil.getInteger(m.get("prcsSeqId"), 0);
//				x = TeeStringUtil.getInteger(m.get("x"), 0);
//				y = TeeStringUtil.getInteger(m.get("y"), 0);
//				
//				if(fp.getSid()==prcsSeqId){
//					fp.setX(x);
//					fp.setY(y);
//					update(fp);
//				}
//			}
//		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getMaxPrcsId(int)
	 */
	public int getMaxPrcsId(int flowId){
		return flowProcessDao.getMaxPrcsId(flowId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getFlowProcessInfo2Json(int)
	 */
	public TeeFlowProcessModel getFlowProcessInfo2Json(int prcsSeqId){
		TeeFlowProcessModel m = new TeeFlowProcessModel();
	
		TeeFlowProcess fp = flowProcessDao.get(prcsSeqId);
		m.setFlowTypeId(fp.getFlowType().getSid());
		BeanUtils.copyProperties(fp, m);
		
		//获取当前所有步骤，除去当前节点
		TeeFlowType ft = fp.getFlowType();
		List<TeeFlowProcess> allPrcsList = flowProcessDao.sortedFlowProcessList(ft.getProcessList());
		Iterator it = allPrcsList.iterator();
		List prcsListModel = new ArrayList();
		while(it.hasNext()){
			TeeFlowProcess tmp = (TeeFlowProcess) it.next();
			if(tmp.getSid()!=prcsSeqId){
				Map map = new HashMap();
				map.put("sid", tmp.getSid());
				map.put("sortNo", tmp.getSortNo());
				map.put("prcsId", tmp.getPrcsId());
				map.put("prcsName", tmp.getPrcsName());
				prcsListModel.add(map);
			}
		}
		m.getParams().put("prcsList", prcsListModel);
		
		//获取该步骤的下一步骤集合
		List prcsNextListModel = new ArrayList();
		List<TeeFlowProcess> nextPrcsList = (List<TeeFlowProcess>) simpleDaoSupport.filteredSet(fp.getNextProcess(), "order by this.sortNo asc", null);
		for(TeeFlowProcess next:nextPrcsList){
			Map map = new HashMap();
			map.put("sid", next.getSid());
			map.put("sortNo", next.getSortNo());
			map.put("prcsId", next.getPrcsId());
			map.put("prcsName", next.getPrcsName());
			prcsNextListModel.add(map);
		}
		m.getParams().put("nextPrcsList", prcsNextListModel);
		
		
		
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		//获取步骤提醒人员
		if(!TeeUtility.isNullorEmpty(fp.getAlarmUserIds())){
			List<Map> list = simpleDaoSupport.getMaps("select userName as userName,uuid as uuid from TeePerson where uuid in ("+fp.getAlarmUserIds()+") ", null);
			for(int i=0;i<list.size();i++){
				ids.append(list.get(i).get("uuid"));
				names.append(list.get(i).get("userName"));
				if(i!=list.size()-1){
					ids.append(",");
					names.append(",");
				}
			}
			m.setAlarmUserIds(ids.toString());
			m.setAlarmUserNames(names.toString());
		}
		if(ids.length()!=0){
			ids.delete(0, ids.length());
			names.delete(0, names.length());
		}
		//获取步骤提醒部门
		if(!TeeUtility.isNullorEmpty(fp.getAlarmDeptIds())){
			List<Map> list = simpleDaoSupport.getMaps("select deptName as deptName,uuid as uuid from TeeDepartment where uuid in ("+fp.getAlarmDeptIds()+") ", null);
			for(int i=0;i<list.size();i++){
				ids.append(list.get(i).get("uuid"));
				names.append(list.get(i).get("deptName"));
				if(i!=list.size()-1){
					ids.append(",");
					names.append(",");
				}
			}
			m.setAlarmDeptIds(ids.toString());
			m.setAlarmDeptNames(names.toString());
		}
		if(ids.length()!=0){
			ids.delete(0, ids.length());
			names.delete(0, names.length());
		}
		//获取步骤提醒部门
		if(!TeeUtility.isNullorEmpty(fp.getAlarmRoleIds())){
			List<Map> list = simpleDaoSupport.getMaps("select roleName as roleName,uuid as uuid from TeeUserRole where uuid in ("+fp.getAlarmRoleIds()+") ", null);
			for(int i=0;i<list.size();i++){
				ids.append(list.get(i).get("uuid"));
				names.append(list.get(i).get("roleName"));
				if(i!=list.size()-1){
					ids.append(",");
					names.append(",");
				}
			}
			m.setAlarmRoleIds(ids.toString());
			m.setAlarmRoleNames(names.toString());
		}
		if(ids.length()!=0){
			ids.delete(0, ids.length());
			names.delete(0, names.length());
		}
		return m;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#addPrcsPriv(int, int[], int[], int[], java.lang.String[], java.lang.String[], java.lang.String)
	 */
	public void addPrcsPriv(int prcsId,int prcsUser[],int prcsDept[],int prcsRole[],String prcsUserSelectRule [],String prcsAutoSelectUser [],String prcsAutoSelectUser1){
		TeeFlowProcess flowProcess = flowProcessDao.get(prcsId);
		
		//先清空之前数据
		flowProcess.getPrcsDept().clear();
		flowProcess.getPrcsUser().clear();
		flowProcess.getPrcsRole().clear();
		flowProcess.getSelectRules().clear();
		flowProcess.getSelectAutoRules().clear();
		
		TeePerson p = null;
		TeeDepartment d = null;
		TeeUserRole r = null;
		TeeSelectUserRule userRule = null;
		
		//设置经办人员
		for(int uuid:prcsUser){
			p = personDao.get(uuid);
			if(p!=null){
				flowProcess.getPrcsUser().add(p);
			}
		}
		
		//设置经办部门
		for(int uuid:prcsDept){
			d = deptDao.get(uuid);
			if(d!=null){
				flowProcess.getPrcsDept().add(d);
			}
		}
		
		//设置经办角色
		for(int uuid:prcsRole){
			r = roleDao.get(uuid);
			if(r!=null){
				flowProcess.getPrcsRole().add(r);
			}
		}
		
		//配置办理规则1-3-roleID,4-3-roleID,2-3-roleID,1-2-roleID
		String ruleStr [] = null;
		for(String tmp:prcsUserSelectRule){
			if(!"".equals(tmp)){
				ruleStr = tmp.split("-");
				userRule = new TeeSelectUserRule();
				userRule.setUserType(TeeStringUtil.getInteger(ruleStr[0], 0));
				userRule.setDeptType(TeeStringUtil.getInteger(ruleStr[1], 0));
				if(!"0".equals(ruleStr[2])){
					r = roleDao.get(TeeStringUtil.getInteger(ruleStr[2], 0));
					userRule.setUserRole(r);
				}
				userRule.setProcess(flowProcess);
				flowProcess.getSelectRules().add(userRule);
			}
		}
		
		
		//自动选人规则  [1 2 3 4 5]前五着为单个 ;  当为  6-1-2,3 : 自动选择选择默认人员-主办人-经办人; 当为7-1 : 按表单字段选择 -表单项item_id
		String autoSelectRuleStr [] = null;
/*		case '1':return "自动选择流程发起人";
		case '2':return "自动选择本部门主管";
		case '3':return "自动选择本部门分管领导";
		case '4':return "自动选择上级部门主管领导";
		case '5':return "自动选择上级部门分管领导";
		case '6':return "自动选择默认人员";
		case '7':return "按表单字段选择";
		case '8':return "自动选择指定步骤主办人";
		case '9':return "自动选择本部门内有权限的人员";
		case '10':return "自动选择本部门内有权限的人员";*/
	
		for(String tmp:prcsAutoSelectUser){
			if(!"".equals(tmp)){
				autoSelectRuleStr = tmp.split("-");
				TeeSelectAutoUserRule autoUserRule  = new TeeSelectAutoUserRule();
				autoUserRule.setAutoType(TeeStringUtil.getInteger(autoSelectRuleStr[0], 0));
//				System.out.println(autoSelectRuleStr);
				if("1".equals(autoSelectRuleStr[0])){
					
				}else if("2".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("3".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("4".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("5".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("6".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoType(TeeStringUtil.getInteger(autoSelectRuleStr[0], 0));
					if(!autoSelectRuleStr[1].equals("0")){
						autoUserRule.setAutoOpUser(TeeStringUtil.getInteger(autoSelectRuleStr[1], 0));
					}else{
						autoUserRule.setAutoOpUser(0);
					}
					autoUserRule.setAutoPrcsUser(autoSelectRuleStr[2].replace("^", ","));
				}else if("7".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("8".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("9".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}else if("10".equals(autoSelectRuleStr[0])){
					autoUserRule.setAutoFieldId(autoSelectRuleStr[1]);
				}
				
				autoUserRule.setProcess(flowProcess);
				flowProcess.getSelectAutoRules().add(autoUserRule);
			}
		}
		
		flowProcess.setSelectAutoRules1(prcsAutoSelectUser1);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getPrcsPriv(int)
	 */
	@Transactional(readOnly=true)
	public TeeJson getPrcsPriv(int prcsId){
		TeeJson json = new TeeJson();
		TeeFlowProcess flowProcess = flowProcessDao.get(prcsId);
		
		Map map = new HashMap();
		
		//获取经办用户
		Set<TeePerson> prcsUser = flowProcess.getPrcsUser();
		List list = new ArrayList();
		for(TeePerson p:prcsUser){
			TeeSimpleModel m = new TeeSimpleModel();
			m.setName(p.getUserName());
			m.setValue(p.getUuid());
			list.add(m);
		}
		map.put("prcsUser", list);
		
		//获取经办部门
		Set<TeeDepartment> prcsDept = flowProcess.getPrcsDept();
		list = new ArrayList();
		for(TeeDepartment dept:prcsDept){
			TeeSimpleModel m = new TeeSimpleModel();
			m.setName(dept.getDeptName());
			m.setValue(dept.getUuid());
			list.add(m);
		}
		map.put("prcsDept", list);
		
		//获取经办角色
		Set<TeeUserRole> prcsRole = flowProcess.getPrcsRole();
		list = new ArrayList();
		for(TeeUserRole role:prcsRole){
			TeeSimpleModel m = new TeeSimpleModel();
			m.setName(role.getRoleName());
			m.setValue(role.getUuid());
			list.add(m);
		}
		map.put("prcsRole", list);
		
		//获取过滤经办规则
		list = new ArrayList();
		List<TeeSelectUserRule> ruleList = flowProcess.getSelectRules();
		for(TeeSelectUserRule userRule:ruleList){
			TeeUserRole r = userRule.getUserRole();
			int uuid = r==null?0:r.getUuid();
			String name = r==null?"":r.getRoleName();
			list.add(userRule.getUserType()+"-"+userRule.getDeptType()+"-{uuid:'"+uuid+"',name:'"+name+"'}");
		}
		map.put("prcsSelectRule", list);
		
		//获取自动选人规则
		list = new ArrayList();
		List<TeeSelectAutoUserRule> autoRuleList = flowProcess.getSelectAutoRules();
		for(TeeSelectAutoUserRule userRule:autoRuleList){
			int autoType = userRule.getAutoType(); 
			if(autoType == 6){//默认选人
				TeePerson opUser = personDao.get(userRule.getAutoOpUser());
				List<TeePerson> prcsUsers = personDao.getPersonByUuids(userRule.getAutoPrcsUser());
				String opUserId = "";
				String opUserName = "";
				
				String prcsUserIds = "";
				String prcsUserNames = "";
				if(opUser != null ){
					opUserId = opUser.getUuid() + "";
					opUserName = opUser.getUserName();
				}
				for(int i=0;i<prcsUsers.size();i++){
					TeePerson person = prcsUsers.get(i);
					prcsUserIds = prcsUserIds + person.getUuid();
					prcsUserNames = prcsUserNames + person.getUserName();
					if(i!=prcsUsers.size()-1){
						prcsUserIds+=",";
						prcsUserNames+=",";
					}
				}
				list.add(userRule.getAutoType()+"-{opUserId:'"+opUserId+"',opUserName:'"+opUserName+ "',prcsUserIds:'" + prcsUserIds + "',prcsUserNames:'" + prcsUserNames +"'}");	
			}else if(autoType == 1){
				list.add(userRule.getAutoType()+"");
			}else{
				list.add(userRule.getAutoType()+"-"+userRule.getAutoFieldId());
			}
			
		}
		map.put("prcsAutoSelectRule", list);
		map.put("prcsAutoSelectRule1", flowProcess.getSelectAutoRules1());
		
		json.setRtState(true);
		json.setRtData(map);
		
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getProcessList(int)
	 */
	public List<TeeFlowProcessModel> getProcessList(int flowId){
		//获取流程中步骤集合
		List<TeeFlowProcess> list = findFlowProcessByFlowType(flowId);
		//实例化步骤模型
		List<TeeFlowProcessModel> mList = new ArrayList<TeeFlowProcessModel>();
		for(TeeFlowProcess fp:list){
			TeeFlowProcessModel m = new TeeFlowProcessModel();
			BeanUtils.copyProperties(fp, m);
			Iterator<TeeFlowProcess> nextProcess = fp.getNextProcess().iterator();
			
			//获取当前步骤的下一步骤集合
			List list0 = new ArrayList();
			while(nextProcess!=null && nextProcess.hasNext()){
				list0.add(nextProcess.next().getSid());
			}
			
			m.getParams().put("nextPrcs", list0);
			mList.add(m);
		}
		
		return mList;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#updateCondition(int, java.lang.String)
	 */
	public void updateCondition(int prcsId,String conditionModel){
		TeeFlowProcess fp = flowProcessDao.get(prcsId);
		fp.setConditionModel(conditionModel);
		flowProcessDao.update(fp);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#updateFormValid(int, java.lang.String)
	 */
	public void updateFormValid(int prcsId,String formValidModel){
		TeeFlowProcess fp = flowProcessDao.get(prcsId);
		fp.setFormValidModel(formValidModel);
		flowProcessDao.update(fp);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getEndNode(int)
	 */
	public TeeFlowProcess getEndNode(int flowId){
		return flowProcessDao.getEndNode(flowId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getStartNode(int)
	 */
	public TeeFlowProcess getStartNode(int flowId){
		return flowProcessDao.getStartNode(flowId);
	}
	

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#hasRelatedFlowRunPrcs(int)
	 */
	public TeeJson hasRelatedFlowRunPrcs(int sid) {
		TeeJson json=new TeeJson();
		List<TeeFlowRunPrcs> list=simpleDaoSupport.executeQuery(" from TeeFlowRunPrcs frp where frp.flowPrcs.sid=? ", new Object[]{sid});
	    if(list!=null&&list.size()>0){
	    	json.setRtData(1);
	    }else{
	    	json.setRtData(0);
	    }
		json.setRtState(true);
		return json;
	}

	
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getBasicFormItems(javax.servlet.http.HttpServletRequest)
	 */
	public TeeJson getBasicFormItems(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前步骤的主键
		int prcsId=TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
		TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,prcsId);
		if(process!=null){
			if(process.getFlowType()!=null){
				TeeForm form=process.getFlowType().getForm();
				if(form!=null){
					List<TeeFormItem> itemList=simpleDaoSupport.executeQuery(" from TeeFormItem t where t.form.sid=? ", new Object[]{form.getSid()});
				    List<Map> mapList=new ArrayList<Map>();
				    Map map=null;
				    if(itemList!=null&&itemList.size()>0){
				    	for (TeeFormItem item : itemList) {
							map=new HashMap();
							map.put("sid",item.getSid());
							map.put("content", item.getContent());
							map.put("title",item.getTitle());
							map.put("name",item.getName());
							mapList.add(map);
						}
				    }
				    json.setRtState(true);
					json.setRtData(mapList);
				}else{
					json.setRtState(false);
					json.setRtMsg("该流程类型所绑定的表单不存在！");
				}
			}else{
				json.setRtState(false);
				json.setRtMsg("该流程类型信息获取失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("该流程步骤信息获取失败！");
		}
		return json;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#getFormShortById(javax.servlet.http.HttpServletRequest)
	 */
	public TeeJson getFormShortById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int prcsId=TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
		TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,prcsId);
		if(process!=null){
			String formShort=process.getFormShort();
			if(process.getFlowType()!=null){
				TeeForm form=process.getFlowType().getForm();
				if(form!=null){
					if(!TeeUtility.isNullorEmpty(formShort)){
						List<TeeFormItem> itemList=simpleDaoSupport.executeQuery(" from TeeFormItem t where t.form.sid=? ", new Object[]{form.getSid()});
					    if(itemList!=null&&itemList.size()>0){
					    	for (TeeFormItem item : itemList) {
					    		formShort=formShort.replace("{DATA_"+item.getItemId()+"}",item.getContent() );
							}
					    }
					    json.setRtState(true);
						json.setRtData(formShort);
					}else{
						json.setRtState(true);
						json.setRtData("");
					}	    
				}else{
					json.setRtState(false);
					json.setRtMsg("该流程类型所绑定的表单不存在！");
				}
			}else{
				json.setRtState(false);
				json.setRtMsg("该流程类型信息获取失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowmanage.service.TeeFlowProcessServiceInterface#updateFormShort(javax.servlet.http.HttpServletRequest)
	 */
	public TeeJson updateFormShort(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int prcsId=TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
		String content=TeeStringUtil.getString(request.getParameter("content"));
		TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,prcsId);
		//实例化表单转换器工具
		TeeFormParser formParser = new TeeFormParser();
		//抽取表单内容，获取各个控件
		String formShort = formParser.getShortModelFromHtml(content);
		
		if(process!=null){
			process.setFormShort(formShort);
			simpleDaoSupport.update(process);
			json.setRtState(true);
			json.setRtMsg("保存成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("步骤信息获取失败！");
		}
		return json;
	}

	
}
