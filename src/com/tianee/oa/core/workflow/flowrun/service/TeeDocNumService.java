package com.tianee.oa.core.workflow.flowrun.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeDocNum;
import com.tianee.oa.core.workflow.flowrun.bean.TeeDocNumRunner;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeDocNumService extends TeeBaseService implements TeeDocNumServiceInterface{
	private static Object lock = new Object();
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#addDocNumModel(com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel)
	 */
	@Override
	public void addDocNumModel(TeeDocNumModel docNumModel){
		TeeDocNum docNum = new TeeDocNum();
		modelToEntity(docNumModel,docNum);
		//设置resetStamp
		docNum.setResetStamp(Calendar.getInstance().get(Calendar.YEAR));
		simpleDaoSupport.save(docNum);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#deleteDocNumById(int)
	 */
	@Override
	public void deleteDocNumById(int sid){
		//同时删除所有流程记录
		simpleDaoSupport.executeUpdate("delete from TeeDocNumRunner where docNum.sid=?", new Object[]{sid});
		simpleDaoSupport.delete(TeeDocNum.class, sid);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#getById(int)
	 */
	@Override
	public TeeDocNumModel getById(int sid){
		TeeDocNum docNum = (TeeDocNum) simpleDaoSupport.get(TeeDocNum.class, sid);
		TeeDocNumModel model = new TeeDocNumModel();
		entityToModel(docNum, model);
		return model;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#updateDocNumModel(com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel)
	 */
	@Override
	public void updateDocNumModel(TeeDocNumModel docNumModel){
		TeeDocNum docNum = (TeeDocNum) simpleDaoSupport.get(TeeDocNum.class, docNumModel.getSid());
		docNum.getDeptPriv().clear();
		docNum.getRolePriv().clear();
		docNum.getUserPriv().clear();
		//不修改 resetStamp
		docNumModel.setResetStamp(docNum.getResetStamp());
		
		modelToEntity(docNumModel,docNum);
		simpleDaoSupport.update(docNum);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#generateDocNumRunner(com.tianee.oa.core.org.bean.TeePerson, int, int, int)
	 */
	@Override
	public TeeDocNumRunner generateDocNumRunner(TeePerson loginUser,int sid,int runId,int flowId){
		synchronized (lock) {
			String hql = "from TeeDocNumRunner dnr where dnr.runId="+runId+" and dnr.flowId="+flowId+" and dnr.docNum.sid="+sid;
			TeeDocNumRunner docNumRunner = (TeeDocNumRunner) simpleDaoSupport.unique(hql, null);
			TeeDocNum docNum = (TeeDocNum) simpleDaoSupport.get(TeeDocNum.class, sid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			
			//如果该流程没有生成过文号，则先插入一条记录
			if(docNumRunner==null){
				
				//拼接flowIds
				String flowIdsArray[] = TeeStringUtil.parseStringArray(docNum.getFlowIds());
				boolean isAllDocNum=false;
				//查看是否包含0，0为所有流程
				for(int i=0;i<flowIdsArray.length;i++){
					if(flowIdsArray[i].equals("0")){
						isAllDocNum = true;
						break;
					}
				}
				if("".equals(docNum.getFlowIds()) || docNum.getFlowIds()==null){
					//无文号绑定，抛出异常
					throw new TeeOperationException("创建文号失败，原因：[该文号未绑定流程]");
				}
				
//				String hql1 = "select max(dnr.num) from TeeDocNumRunner dnr where dnr.docNum.sid=? and dnr.word=? and dnr.year=? "+(isAllDocNum?"":" and dnr.flowId in ("+docNum.getFlowIds()+")");
//				long maxNum = simpleDaoSupport.count(hql1, new Object[]{docNum.getSid(),docNum.getDocName(),sdf.format(new Date())});
				int maxNum = docNum.getCurrNum();
				docNum.setCurrNum(maxNum+1);
				
				//修改文号的计数值
				int countNum=docNum.getCountNum();
				docNum.setCountNum(countNum+1);
				
				docNumRunner = new TeeDocNumRunner();
				docNumRunner.setCreateUser((TeePerson)simpleDaoSupport.get(TeePerson.class, loginUser.getUuid()));
				docNumRunner.setDocNum(docNum);
				docNumRunner.setFlowId(flowId);
				docNumRunner.setNum(Integer.parseInt(""+(maxNum+1)));
				docNumRunner.setCountNum(Integer.parseInt(""+(countNum+1)));
				docNumRunner.setRunId(runId);
				docNumRunner.setWord(docNum.getDocName());
				docNumRunner.setYear(sdf.format(new Date()));
				simpleDaoSupport.save(docNumRunner);
			}
			return docNumRunner;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#generateDocNum(com.tianee.oa.core.org.bean.TeePerson, int, int, int)
	 */
	@Override
	public String generateDocNum(TeePerson loginUser,int sid,int runId,int flowId){
		TeeDocNumRunner docNumRunner = generateDocNumRunner(loginUser,sid,runId,flowId);
		TeeDocNum docNum = docNumRunner.getDocNum();
		String docStyle = docNum.getDocStyle();
		docStyle = docStyle.replace("${名称}", docNumRunner.getWord());
		docStyle = docStyle.replace("${年}", docNumRunner.getYear());
		docStyle = docStyle.replace("${文号}", docNumRunner.getNum()+"");
		docStyle = docStyle.replace("${计数}", docNumRunner.getCountNum()+"");
		return docStyle;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#diynamicEditDocNum(com.tianee.oa.core.org.bean.TeePerson, int, int, int)
	 */
	@Override
	public String diynamicEditDocNum(TeePerson loginUser,int runId,int flowId,int editNum){
		String hql = "from TeeDocNumRunner dnr where dnr.runId="+runId+" and dnr.flowId="+flowId+"";
		TeeDocNumRunner docNumRunner = (TeeDocNumRunner) simpleDaoSupport.unique(hql, null);//获取当前文号日志
		boolean isReset = checkIsReset(docNumRunner.getDocNum(),docNumRunner);
		if(!isReset){//如果没清零，则更新当前编号
			if(editNum>docNumRunner.getDocNum().getCurrNum()){
				docNumRunner.getDocNum().setCurrNum(editNum);
			}
		}
		
		//判断修改后的文号是否重复
		if(editNum!=docNumRunner.getNum()){
			long count = simpleDaoSupport.count("select count(*) from TeeDocNumRunner dnr where dnr.docNum.sid="+docNumRunner.getDocNum().getSid()+" and dnr.num="+editNum+" and dnr.year='"+docNumRunner.getYear()+"'", null);
			if(count!=0){
				throw new TeeOperationException("修改失败：该文号值已被使用！");
			}
		}
		
		docNumRunner.setNum(editNum);
		String docStyle = docNumRunner.getDocNum().getDocStyle();
		if(editNum>docNumRunner.getDocNum().getCurrNum()){
			docNumRunner.getDocNum().setCurrNum(editNum);
		}
		docStyle = docStyle.replace("${名称}", docNumRunner.getWord());
		docStyle = docStyle.replace("${年}", docNumRunner.getYear()); 
		docStyle = docStyle.replace("${文号}", docNumRunner.getNum()+"");
		docStyle = docStyle.replace("${计数}", docNumRunner.getCountNum()+"");
		return docStyle;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#checkExistsDocNum(int, int)
	 */
	@Override
	public boolean checkExistsDocNum(int runId,int flowId){
		String hql = "from TeeDocNumRunner dnr where dnr.runId="+runId+" and dnr.flowId="+flowId+"";
		TeeDocNumRunner docNumRunner = (TeeDocNumRunner) simpleDaoSupport.unique(hql, null);
		return docNumRunner!=null;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#checkIsReset(com.tianee.oa.core.workflow.flowrun.bean.TeeDocNum, com.tianee.oa.core.workflow.flowrun.bean.TeeDocNumRunner)
	 */
	@Override
	public boolean checkIsReset(TeeDocNum docNum,TeeDocNumRunner docNumRunner){
		if(docNum.getResetStamp()!=Integer.parseInt(docNumRunner.getYear()) 
				&& (docNum.getResetStamp()-Integer.parseInt(docNumRunner.getYear()))%docNum.getResetYear()==0){
			return true;
		}else{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#getCurDocAndMaxDoc(int, int)
	 */
	@Override
	public Map getCurDocAndMaxDoc(int runId,int flowId){
		String hql = "from TeeDocNumRunner dnr where dnr.runId="+runId+" and dnr.flowId="+flowId+"";
		TeeDocNumRunner docNumRunner = (TeeDocNumRunner) simpleDaoSupport.unique(hql, null);
		Map map = new HashMap();
//		boolean isReset = checkIsReset(docNumRunner.getDocNum(),docNumRunner);
//		if(isReset){//如果曾经清零过，则获取非当年的历史最大编号
//			String hql1 = "select max(dnr.num) from TeeDocNumRunner dnr where dnr.docNum.sid=? and dnr.year=? ";
//			long maxNum = simpleDaoSupport.count(hql1, new Object[]{docNumRunner.getDocNum().getSid(),docNumRunner.getYear()});
//			map.put("curNum", docNumRunner.getNum());
//			map.put("maxNum", maxNum);
//		}else{
//			map.put("curNum", docNumRunner.getNum());
//			map.put("maxNum", docNumRunner.getDocNum().getCurrNum());
//		}
		map.put("curNum", docNumRunner.getNum());
		map.put("maxNum", docNumRunner.getDocNum().getCurrNum());
		
		return map;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#modelToEntity(com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel, com.tianee.oa.core.workflow.flowrun.bean.TeeDocNum)
	 */
	@Override
	public void modelToEntity(TeeDocNumModel docNumModel,TeeDocNum docNum){
		BeanUtils.copyProperties(docNumModel, docNum);
		int userPrivIds [] = TeeStringUtil.parseIntegerArray(docNumModel.getUserPrivIds());
		for(int uid:userPrivIds){
			if(uid==0){
				continue;
			}
			docNum.getUserPriv().add((TeePerson)simpleDaoSupport.get(TeePerson.class, uid));
		}
		
		int deptPrivIds [] = TeeStringUtil.parseIntegerArray(docNumModel.getDeptPrivIds());
		for(int did:deptPrivIds){
			if(did==0){
				continue;
			}
			docNum.getDeptPriv().add((TeeDepartment)simpleDaoSupport.get(TeeDepartment.class, did));
		}
		
		int rolePrivIds [] = TeeStringUtil.parseIntegerArray(docNumModel.getRolePrivIds());
		for(int rid:rolePrivIds){
			if(rid==0){
				continue;
			}
			docNum.getRolePriv().add((TeeUserRole)simpleDaoSupport.get(TeeUserRole.class, rid));
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#entityToModel(com.tianee.oa.core.workflow.flowrun.bean.TeeDocNum, com.tianee.oa.core.workflow.flowrun.model.TeeDocNumModel)
	 */
	@Override
	public void entityToModel(TeeDocNum docNum,TeeDocNumModel docNumModel){
		BeanUtils.copyProperties(docNum, docNumModel);
		String ids = "";
		String names = "";
		
		Set<TeePerson> userPriv = docNum.getUserPriv();
		for(TeePerson person:userPriv){
			ids+=person.getUuid()+",";
			names+=person.getUserName()+",";
		}
		docNumModel.setUserPrivIds(ids);
		docNumModel.setUserPrivNames(names);
		ids="";
		names="";
		
		Set<TeeDepartment> deptPriv = docNum.getDeptPriv();
		for(TeeDepartment dept:deptPriv){
			ids+=dept.getUuid()+",";
			names+=dept.getDeptName()+",";
		}
		docNumModel.setDeptPrivIds(ids);
		docNumModel.setDeptPrivNames(names);
		ids="";
		names="";
		
		Set<TeeUserRole> rolePriv = docNum.getRolePriv();
		for(TeeUserRole role:rolePriv){
			ids+=role.getUuid()+",";
			names+=role.getRoleName()+",";
		}
		docNumModel.setRolePrivIds(ids);
		docNumModel.setRolePrivNames(names);
		ids="";
		names="";
		
		//文号设置
		int flowIds[] = TeeStringUtil.parseIntegerArray(docNum.getFlowIds());
		for(int i=0;i<flowIds.length;i++){
			TeeFlowType ft = (TeeFlowType) simpleDaoSupport.get(TeeFlowType.class, flowIds[i]);
			if(flowIds[i]==0){
				ids+="0";
				names+="[全部流程]";
			}else if(ft==null){
				ids+="-1";
				names+="[该流程已删除]";
			}else{
				ids+=flowIds[i];
				names+=ft.getFlowName();
			}
			
			if(i!=flowIds.length-1){
				ids+=",";
				names+=",";
			}
		}
		
		
		docNumModel.setFlowIds(ids);
		docNumModel.setFlowNames(names);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#datagrid(com.tianee.oa.webframe.httpModel.TeeDataGridModel, java.util.Map)
	 */
	@Override
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeDocNum dn ";
		List<TeeDocNum> list = simpleDaoSupport.pageFind(hql+"order by dn.orderNo asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeDocNumModel> models = new ArrayList<TeeDocNumModel>();
		for(TeeDocNum docNum:list){
			TeeDocNumModel model = new TeeDocNumModel();
			entityToModel(docNum, model);
			models.add(model);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#listHistory(com.tianee.oa.webframe.httpModel.TeeDataGridModel, java.util.Map)
	 */
	@Override
	public TeeEasyuiDataGridJson listHistory(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		int docNumId = TeeStringUtil.getInteger(requestDatas.get("docNumId"), 0);//文号ID
		String hql = "from TeeDocNumRunner dnr ";
		if(docNumId!=0){
			hql+=" where dnr.docNum.sid="+docNumId;
		}
		List<TeeDocNumRunner> list = simpleDaoSupport.pageFind(hql+"order by dnr.sid desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<Map> models = new ArrayList<Map>();
		TeeFlowRun fr = null;
		for(TeeDocNumRunner runner:list){
			Map model = new HashMap();
			fr = (TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class, runner.getRunId());
			model.put("sid", runner.getSid());
			model.put("word", runner.getWord());
			model.put("year", runner.getYear());
			model.put("num", runner.getNum());
			model.put("docName", runner.getDocNum().getDocName());
			model.put("docId", runner.getDocNum().getSid());
			Date date=runner.getCreateTime().getTime();
			model.put("crTime", sdf.format(date));
			model.put("crUser", runner.getCreateUser().getUserName());
			model.put("runId", runner.getRunId());
			if(fr!=null){
				model.put("runName", fr.getRunName());
			}else{
				model.put("runName", "该工作已删除");
			}
			
			models.add(model);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#getDocNumListByPriv(com.tianee.oa.webframe.httpModel.TeeDataGridModel, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List<TeeDocNumModel> getDocNumListByPriv(TeeDataGridModel dm,TeePerson loginPerson){
		loginPerson = (TeePerson) simpleDaoSupport.get(TeePerson.class, loginPerson.getUuid());
		
		String hql = "from TeeDocNum dn where 1=1 and ";
		hql+="(exists (select 1 from dn.deptPriv deptPriv where deptPriv.uuid="+loginPerson.getDept().getUuid()+") or " +
				"exists (select 1 from dn.rolePriv rolePriv where rolePriv.uuid="+loginPerson.getUserRole().getUuid()+") or " +
						"exists (select 1 from dn.userPriv userPriv where userPriv.uuid="+loginPerson.getUuid()+"))";
		
		List<TeeDocNum> list = simpleDaoSupport.pageFind(hql+" order by dn.orderNo asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeDocNumModel> models = new ArrayList<TeeDocNumModel>();
		for(TeeDocNum docNum:list){
			TeeDocNumModel model = new TeeDocNumModel();
			entityToModel(docNum, model);
			models.add(model);
		}
		
		return models;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#clear(int)
	 */
	@Override
	public void clear(int sid){
		TeeDocNum docNum = (TeeDocNum) simpleDaoSupport.get(TeeDocNum.class, sid);
		docNum.setCurrNum(0);
		
		simpleDaoSupport.executeUpdate("delete from TeeDocNumRunner where docNum.sid="+sid, null);
	}

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeDocNumServiceInterface#delHistoryBySid(int)
	 */
	@Override
	public TeeJson delHistoryBySid(int sid) {
		TeeJson json=new TeeJson();
		TeeDocNumRunner runner=(TeeDocNumRunner) simpleDaoSupport.get(TeeDocNumRunner.class,sid);
		if(runner!=null){
			simpleDaoSupport.deleteByObj(runner);
            json.setRtState(true);
            json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该文号日志已经不存在了！");
		}
		return json;
	}
}
