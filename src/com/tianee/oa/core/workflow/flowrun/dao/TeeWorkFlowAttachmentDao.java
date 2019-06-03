//package com.tianee.oa.core.workflow.flowrun.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.springframework.stereotype.Repository;
//
//import com.tianee.oa.core.workflow.flowrun.bean.TeeWorkFlowAttachment;
//import com.tianee.webframe.dao.TeeBaseDao;
//import com.tianee.webframe.util.str.TeeUtility;
///**
// * 
// * @author zhp
// * @createTime 2013-10-15
// * @desc
// */
//@Repository
//public class TeeWorkFlowAttachmentDao extends TeeBaseDao<TeeWorkFlowAttachment>{
//
//	
//
//	/**
//	 * 增加附件
//	 * @param attach
//	 */
//	public void addAttac(TeeWorkFlowAttachment attach) {
//		save(attach);	
//	}
//	
//	/**
//	 * 通过runId 获取到流程附件
//	 * @author zhp
//	 * @createTime 2013-10-18
//	 * @editTime 下午07:23:15
//	 * @desc
//	 */
//	public List<TeeWorkFlowAttachment> getAttachmentsByRunId(int runId){
//		Session session = this.getSession();
//		String hql = "from TeeWorkFlowAttachment ta where ta.flowRun.runId = ?";
//		Query query = session.createQuery(hql);
//		 query.setParameter(0, runId);
//		return query.list();
//	}
//	/**
//	 * 更新 附件
//	 * @param attach
//	 */
//	public void updateOrg(TeeWorkFlowAttachment attach) {
//		update(attach);	
//	}
//	/**
//	 * 查询 附件
//	 * @author zhp
//	 * @createTime 2013-10-6
//	 * @editTime 下午01:56:19
//	 * @desc
//	 */
//	public List<TeeWorkFlowAttachment> getAttachmentsByIds(String attachIds){
//		if(TeeUtility.isNullorEmpty(attachIds)){
//			return new ArrayList<TeeWorkFlowAttachment>();
//		}
//		if(attachIds.endsWith(",")){
//			attachIds = attachIds.substring(0,attachIds.length()-1);
//		}
//		Session session = this.getSession();
//		String hql = "from TeeWorkFlowAttachment ta where ta.sid in ("+attachIds+")";
//		Query query = session.createQuery(hql);
//		return query.list();
//		
//	}
//	/**
//	 * 删除附件
//	 * @author zhp
//	 * @createTime 2013-10-6
//	 * @editTime 下午08:06:35
//	 * @desc
//	 */
//	public void deleteAttachs(String ids){
//		Session session = this.getSession();
//		String hql = "delete from TeeWorkFlowAttachment ta where ta.sid in ("+ids+")";
//		Query query = session.createQuery(hql);
//		query.executeUpdate();
//	}
//	/**
//	 * 根据附件id删除附件
//	 * @author zhp
//	 * @createTime 2013-10-19
//	 * @editTime 下午06:56:39
//	 * @desc
//	 */
//	public boolean deleteAttachById(int aid){
//		boolean result = false;
//		try {
//			Session session = this.getSession();
//			String hql = "delete from TeeWorkFlowAttachment ta where ta.sid = ?";
//			Query query = session.createQuery(hql);
//			query.setParameter(0, aid);
//			query.executeUpdate();
//			result = true;
//		} catch (Exception e) {
//			result = false;
//			e.printStackTrace();
//		}
//		return result;
//	}
//}
