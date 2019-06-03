package com.tianee.oa.core.workflow.flowrun.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunConcern;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunConcernDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeFlowRunConcernService extends TeeBaseService implements TeeFlowRunConcernServiceInterface{
	@Autowired
	private TeeFlowRunConcernDao flowRunConcernDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeFlowRunDao flowRunDao;

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunConcernServiceInterface#concern(int, int)
	 */
	@Override
	public void concern(int runId,int personId){
		TeePerson person = personDao.selectPersonById(personId);
		TeeFlowRun flowRun = flowRunDao.get(runId);
		
		TeeFlowRunConcern frc = null;
		frc = flowRunConcernDao.getConcern(flowRun, person);
		if(frc!=null){
			return;
		}
		frc = new TeeFlowRunConcern();
		frc.setFlowRun(flowRun);
		frc.setPerson(person);
		flowRunConcernDao.save(frc);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunConcernServiceInterface#getConcernUsersByRunId(int)
	 */
	@Override
	public List<TeePerson> getConcernUsersByRunId(int runId){
		return flowRunConcernDao.getConcernUsersByRunId(runId);
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunConcernServiceInterface#cancelConcern(int, int)
	 */
	@Override
	public TeeFlowRunConcern cancelConcern(int runId,int personUuid){
		return flowRunConcernDao.cancelConcern(runId, personUuid);
	}
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunConcernServiceInterface#setPersonDao(com.tianee.oa.core.org.dao.TeePersonDao)
	 */
	@Override
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}
}
