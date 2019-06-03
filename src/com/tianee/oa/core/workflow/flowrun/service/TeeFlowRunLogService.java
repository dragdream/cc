package com.tianee.oa.core.workflow.flowrun.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunLogDao;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowRunLogModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeFlowRunLogService extends TeeBaseService implements TeeFlowRunLogServiceInterface{
	@Autowired
	private TeeFlowRunLogDao flowRunLogDao;
	
	@Autowired
	private TeeFlowProcessDao flowProcessDao;
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogServiceInterface#save(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog)
	 */
	@Override
	public void save(TeeFlowRunLog frl){
		flowRunLogDao.save(frl);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogServiceInterface#saveLogAutomatically(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs, com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog)
	 */
	@Override
	public void saveLogAutomatically(TeeFlowRunPrcs frp,TeeFlowRunLog frl){
		TeeRequestInfo info = TeeRequestInfoContext.getRequestInfo();
		if(info==null){
			return;
		}
		TeeFlowProcess flowPrcs = frp.getFlowPrcs();
		String prcsName = flowPrcs==null?"":flowPrcs.getPrcsName();
		
		frl.setRunName(frp.getFlowRun().getRunName());
		frl.setFlowId(frp.getFlowRun().getFlowType().getSid());
		frl.setFlowName(frp.getFlowRun().getFlowType().getFlowName());
		frl.setPrcsName(prcsName);
		frl.setIp(info.getIpAddress());
		frl.setPrcsId(frp.getPrcsId());
		frl.setRunId(frp.getFlowRun().getRunId());
		frl.setTime(Calendar.getInstance());
		frl.setFlowPrcs(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getSid());
		frl.setUserId(info.getUserSid());
		frl.setUserName(info.getUserName());
		flowRunLogDao.save(frl);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogServiceInterface#datagrid(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson datagrid(TeeFlowRunLog frl,TeeDataGridModel datagridModel){
		StringBuffer hql = new StringBuffer("from TeeFlowRunLog frl where 1=1 ");
		List params = new ArrayList();
		if(frl.getType()!=0){
			hql.append("and frl.type=?");
			params.add(frl.getType());
		}
		
		List<TeeFlowRunLog> frlList = flowRunLogDao.pageFind(hql.toString(), (datagridModel.getPage()-1)*datagridModel.getRows(), datagridModel.getRows(), params.toArray());
		List<TeeFlowRunLogModel> modelList = new ArrayList<TeeFlowRunLogModel>();
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunLogServiceInterface#getFlowRunLogs(int)
	 */
	@Override
	public List<TeeFlowRunLogModel> getFlowRunLogs(int runId){
		String hql = "from TeeFlowRunLog frl where frl.runId="+runId+" order by frl.sid asc";
		List<TeeFlowRunLogModel> modelList = new ArrayList<TeeFlowRunLogModel>();
		List<TeeFlowRunLog> frlList = flowRunLogDao.find(hql, null);
		TeeFlowRunLogModel m = null;
		for(TeeFlowRunLog log:frlList){
			m = new TeeFlowRunLogModel();
			BeanUtils.copyProperties(log, m);
			m.setTimeDesc(TeeDateUtil.format(log.getTime()));
			m.setTypeDesc(TeeFlowRunLog.getLogTypeDesc(log.getType()));
			modelList.add(m);
		}
		
		return modelList;
	}



}
