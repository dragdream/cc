package com.tianee.oa.core.workflow.workmanage.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunService;
import com.tianee.oa.core.workflow.workmanage.dao.TeeWorkQueryDao;
import com.tianee.oa.core.workflow.workmanage.model.TeeWorkQueryModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeWorkDestroyService extends TeeBaseService implements TeeWorkDestroyServiceInterface{
	
	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;
	
	@Autowired
	private TeeFlowRunDao flowRunDao;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkDestroyServiceInterface#query(java.util.Map, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@Transactional(readOnly=true)
	public TeeEasyuiDataGridJson query(Map queryParams,TeePerson loginUser,TeeDataGridModel dataGridModel){
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		
		//获取查询条件
		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);
		int beginUser = TeeStringUtil.getInteger(queryParams.get("beginUser"), 0);
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);
		String runName = TeeStringUtil.getString(queryParams.get("runName"));
		Date start = TeeStringUtil.getDate(queryParams.get("start"),"yyyy-MM-dd");
		Date end = TeeStringUtil.getDate(queryParams.get("end"),"yyyy-MM-dd");
		int delFlag = TeeStringUtil.getInteger(queryParams.get("delFlag"), 0);//删除标记
		String quickSearch = TeeStringUtil.getString(queryParams.get("quickSearch"));
		
		int firstResult = (dataGridModel.getPage()-1)*dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		
		List params = new ArrayList();
		
		//声明查询语句
		StringBuffer sql = new StringBuffer();
		sql.append("from TeeFlowRun fr where 1=1 ");
		
		if(delFlag==0){
			sql.append(" and fr.delFlag=1");
		}else{
			sql.append(" and fr.delFlag=0");
		}
		
		if(!"".equals(quickSearch)){
			sql.append(" and (fr.runName like '%"+TeeDbUtility.formatString(quickSearch)+"%' or fr.runId="+TeeStringUtil.getInteger(quickSearch, 0)+")");
		}
		
		
		if(flowId!=0){
			sql.append(" and fr.flowType.sid="+flowId);
		}
		
		if(beginUser!=0){
			sql.append(" and fr.beginPerson.uuid="+beginUser);
		}
		
		if(runId!=0){
			sql.append(" and fr.runId="+runId);
		}
		
		if(!"".equals(runName)){
			sql.append(" and fr.runName like '%"+runName+"%'");
		}
		int paraIndex = 0;
		if(start!=null){
			sql.append(" and fr.beginTime>=?");
			Calendar c = Calendar.getInstance();
			c.setTime(start);
			params.add(c);
		}
		
		if(end!=null){
			sql.append(" and fr.beginTime<=?");
			Calendar c = Calendar.getInstance();
			c.setTime(end);
			params.add(c);
		}
		
		
		List<TeeFlowRun> list = simpleDaoSupport.pageFind(sql.toString()+" order by fr.runId desc",firstResult,pageSize,params.toArray());
		long total = simpleDaoSupport.count("select count(*) "+sql.toString(), params.toArray());
		
		List<TeeWorkQueryModel> queryModelList = new ArrayList<TeeWorkQueryModel>();
		for(TeeFlowRun fr:list){
			TeeWorkQueryModel m = new TeeWorkQueryModel();
			m.setRunId(fr.getRunId());
			m.setFlowId(fr.getFlowType().getSid());
			Calendar beginTime = fr.getBeginTime();
			Calendar endTime = fr.getEndTime();
			m.setBeginTimeDesc(TeeDateUtil.format(beginTime==null?null:beginTime.getTime()));
			m.setEndTimeDesc(TeeDateUtil.format(endTime==null?null:endTime.getTime()));
			m.setRunName(fr.getRunName());
			m.setFlowName(fr.getFlowType().getFlowName());
			m.setBeginUserName(fr.getBeginPerson().getUserName());
			queryModelList.add(m);
		}
		
		json.setRows(queryModelList);
		json.setTotal(total);
		
		return json;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkDestroyServiceInterface#destroy(int)
	 */
	@Override
	public TeeFlowRun destroy(int runId){
		TeeFlowRun flowRun = flowRunDao.get(runId);
		if(flowRun==null){
			throw new TeeOperationException("流程["+runId+"]不存在");
		}
//		if(flowRun.getDelFlag()==0){
//			throw new TeeOperationException("该流程["+runId+"]未被删除，不允许销毁");
//		}
		//删除流程步骤实例
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunPrcs frp where frp.flowRun.runId="+runId, null);
		//删除会签意见
		simpleDaoSupport.executeUpdate("delete from TeeFeedBack fr where fr.flowRun.runId="+runId, null);
		//删除流程关注
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunConcern frc where frc.flowRun.runId="+runId, null);
		//删除流程日志
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunLog frl where frl.runId="+runId, null);
		//删除业务映射关系
		simpleDaoSupport.executeUpdate("delete from BisRunRelation where runId="+runId, null);
		//删除flow_run_view
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunView frv where frv.flowRun.runId="+runId, null);
		//删除flow_run_vars
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunVars frv where frv.flowRun.runId="+runId, null);
		//删除flow_run_doc
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunDoc frd where frd.flowRun.runId="+runId, null);
		//删除会签控件数据
		simpleDaoSupport.executeUpdate("delete from TeeFlowRunCtrlFeedback frcf where frcf.flowRun.runId="+runId, null);
		//删除相关计划
		simpleDaoSupport.executeUpdate("delete from TeeRunScheduleRel where flowRun.runId="+runId, null);
		//删除相关任务
		simpleDaoSupport.executeUpdate("delete from TeeRunTaskRel where flowRun.runId="+runId, null);
		//删除相关客户
		simpleDaoSupport.executeUpdate("delete from TeeRunCustomerRel where flowRun.runId="+runId, null);
		//删除相关流程
		simpleDaoSupport.executeUpdate("delete from TeeRunRel where flowRun.runId="+runId+" or flowRun1.runId="+runId, null);
		//删除收文与发文
		simpleDaoSupport.executeUpdate("delete from TeeDocumentDeliveryRecord where flowRun.runId="+runId, null);
		simpleDaoSupport.executeUpdate("delete from TeeDocumentDelivery where runId="+runId, null);
		simpleDaoSupport.executeUpdate("delete from TeeDocumentViewRecord where flowRun.runId="+runId, null);
		simpleDaoSupport.executeUpdate("delete from TeeDocumentView where runId="+runId, null);
		
		
		if(flowRun.getFlowType().getSid()!=0){
			//获取表名 
			String  tableName="tee_f_r_d_"+flowRun.getFlowType().getSid();
			simpleDaoSupport.executeNativeUpdate("delete from "+tableName+" where run_id="+runId, null);
		}
		
		//删除实体
		flowRunDao.deleteByObj(flowRun);
		
		return flowRun;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkDestroyServiceInterface#restore(int)
	 */
	@Override
	public TeeFlowRun restore(int runId){
		TeeFlowRun flowRun = flowRunDao.get(runId);
		if(flowRun==null){
			throw new TeeOperationException("流程["+runId+"]不存在");
		}
		if(flowRun.getDelFlag()==0){
			throw new TeeOperationException("该流程["+runId+"]已被还原");
		}
		flowRun.setDelFlag(0);
		flowRunDao.update(flowRun);
		
		simpleDaoSupport.executeUpdate("update TeeFlowRunPrcs set delFlag=0 where flowRun.runId="+runId, null);
		
		return flowRun;
	}

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkDestroyServiceInterface#setSimpleDaoSupport(com.tianee.webframe.dao.TeeSimpleDaoSupport)
	 */
	@Override
	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}

	
}
