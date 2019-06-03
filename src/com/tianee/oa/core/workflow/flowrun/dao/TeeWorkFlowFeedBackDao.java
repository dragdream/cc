package com.tianee.oa.core.workflow.flowrun.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFeedBack;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository
public class TeeWorkFlowFeedBackDao extends TeeBaseDao<TeeFeedBack> {

	/**
	 * 增加会签意见
	 * 
	 * @param TeeDepartment
	 */
	public void addFeedBack(TeeFeedBack feedBack) {
		save(feedBack);
	}

	/**
	 * 更新会签意见
	 * 
	 * @param TeePerson
	 */
	public void updateFeedBack(TeeFeedBack feedBack) {
		update(feedBack);
	}

	/**
	 * 查询 bySid
	 * 
	 * @param TeeFeedBack
	 */
	public TeeFeedBack selectTeeFeedBackById(int sid) {
		TeeFeedBack feedBack = (TeeFeedBack) get(sid);
		return feedBack;
	}

	/**
	 * 查询 by RunId
	 * 
	 * @param TeeFeedBack
	 */
	public List<TeeFeedBack> selectTeeFeedBackByRunId(int runId,int flowType,TeeFlowProcess flowPrcs) {

		Session session = super.getSession();
		Query query = null;
		//如果是自由流程
		if(flowType == 2){
			query = session.createQuery("from TeeFeedBack fb where  fb.flowRun.runId = ? order by fb.sid asc");
		}else{
			if(flowPrcs == null){
				return null;
			}
			query = session.createQuery("from TeeFeedBack fb where  fb.flowRun.runId = ? and ( fb.flowPrcs.feedbackViewType in(1,2) or (fb.flowPrcs.feedbackViewType = 3 and fb.flowPrcs.sid = "+flowPrcs.getSid()+" ) ) order by fb.sid asc");
		}
		
		query.setParameter(0, runId);

		return (List<TeeFeedBack>) query.list();
	}

	/**
	 * 
	 * @author zhp
	 * @createTime 2013-10-6
	 * @editTime 下午08:16:46
	 * @desc
	 */
	public void deleteFeedBacks(String ids) {
		Session session = this.getSession();
		String hql = "delete TeeFeedBack fb where fb.sid in (" + ids + ")";
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	/**
	 * 判断当前步骤是否已经会签过
	 * @param runId
	 * @param prcsId
	 * @param flowPrcs
	 * @param personId
	 * @return
	 */
	public boolean hasFeedback(TeeFlowRunPrcs frp) {
		Session session = this.getSession();
		String hql = "select count(*) from TeeFeedBack fb where fb.flowRun.runId=? and fb.prcsId=? and (fb.flowPrcs.sid=? or fb.flowPrcs is null) and fb.userPerson.uuid=?";
		Query query = session.createQuery(hql);
		long c = count(hql, new Object[]{frp.getFlowRun().getRunId(),frp.getPrcsId(),frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getSid(),frp.getPrcsUser().getUuid()});
		System.out.println(c);
		return c==0?false:true;
	}

}
