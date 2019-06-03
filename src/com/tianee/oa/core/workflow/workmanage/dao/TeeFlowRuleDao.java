package com.tianee.oa.core.workflow.workmanage.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.workmanage.bean.TeeFlowRule;
import com.tianee.oa.core.workflow.workmanage.model.TeeFlowRuleModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TeeFlowRuleDao extends TeeBaseDao<TeeFlowRule> {
	/**
	 * page TeeDataGridModel.page 当前页 rows TeeDataGridModel.rows 每页显示记录数
	 * 
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List<Object> loadList(String hql, int page, int rows,
			List<Object> param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/**
	 * page TeeDataGridModel.page 当前页 rows TeeDataGridModel.rows 每页显示记录数
	 * 
	 * @param hql
	 * @param page
	 * @param rows
	 * @param param
	 * @return
	 */
	public List<Object> getList(String hql, List<Object> param) {
		Query q = getSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		// List<Object> list = new ArrayList<Object>();
		return q.list();
	}

	/**
	 * 通过body_seq_id和to_id索引所有seq_id,可能有重复,getSmsSeqId方法只能取一条会引起bug
	 * 
	 * @param toId
	 * @param seqId
	 * @return
	 * @throws Exception
	 */

	public List<String> getSmsSeqIds(String toId, String seqUuId) throws Exception {
		List<String> list = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		String hql = "select sms from TeeSms sms left join sms.bodyId sb where sb.uuid = '"
				+ seqUuId + "' and sms.toId = '" + toId + "'";
		List<Object> objList = getList(hql, values);

		for (int i = 0; i < objList.size(); i++) {

			Object[] obj = (Object[]) objList.get(i);
			TeeSms sms = (TeeSms) obj[0];
			list.add(sms.getUuid());

		}

		return list;
	}

	/**
	 * @param toId
	 * @param seqId
	 * @return
	 * @throws Exception
	 */

	public int remindCheck(String personUuid) throws Exception {
		int result = 0;

		String sql = "SELECT COUNT(*) FROM SMS T1 ,SMS_BODY T0 WHERE TO_ID='"
				+ personUuid
				+ "' AND REMIND_FLAG = '1' AND  T1.BODY_ID= T0.SID  "
				+ "and DELETE_FLAG in (0, 2) "
				+ "AND "
				+ "T0.SEND_TIME<=?"
				+ " AND (T1.REMIND_TIME IS NULL OR "
				+ "T1.REMIND_TIME<=?"
				+ ")";

		Session session = getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setTimestamp(0, new Timestamp(new Date().getTime()));
		query.setTimestamp(1, new Timestamp(new Date().getTime()));
		query.uniqueResult();
		result = (Integer.parseInt(query.uniqueResult().toString()));
		// System.out.println(result);
		return result;
	}

	/**
	 * @param toId
	 * @param seqId
	 * @return
	 * @throws Exception
	 */

	public Long getListCount(int personId, String entrustStatus,int flowId)
			throws Exception {
		String data = "";
		String sql = "";

		if (entrustStatus.equals("0")
				|| TeeUtility.isNullorEmpty(entrustStatus)) {
			sql = "SELECT FLOW_RULE.*,FLOW_NAME,USER_NAME from FLOW_RULE LEFT JOIN FLOW_TYPE ON (FLOW_TYPE.SID=FLOW_RULE.FLOW_ID) LEFT JOIN person ON (FLOW_RULE.TO_USER=person.UUID) where FLOW_RULE.USER_ID="
					+ personId + "";
		} else {
			sql = "SELECT FLOW_RULE.*,FLOW_NAME,USER_NAME from FLOW_RULE LEFT JOIN FLOW_TYPE ON (FLOW_TYPE.SID=FLOW_RULE.FLOW_ID) LEFT JOIN person ON (FLOW_RULE.TO_USER=person.UUID) where FLOW_RULE.TO_USER="
					+ personId + " and STATUS = 1";
		}

		if(flowId>0){
			sql+=" And FLOW_RULE.FLOW_ID="+flowId+" ";
		}
		Session session = getSession();
		String smsIds = "";
		SQLQuery query = session.createSQLQuery(sql);
		return (long) query.list().size();
	}

	public Long getEntrustListCount(int personId,String qs, String entrustStatus,int flowId)
			throws Exception {
		String data = "";
		String sql = "";

		if (entrustStatus.equals("0")
				|| TeeUtility.isNullorEmpty(entrustStatus)) {
			sql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.otherUser.uuid="+personId;
		} else {
			sql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.otherUser.uuid="
					+ personId
					+ " and frp.flowRun.runName like '%"
					+ entrustStatus
					+ "%'";
		}
		
		if(!"".equals(qs)){
			sql+=" and (frp.flowRun.runName like '%"+TeeDbUtility.formatString(qs)+"%' or frp.flowRun.runId="+TeeStringUtil.getInteger(qs, 0)+") ";
		}

		if(flowId>0){
            sql+=" and frp.flowType.sid="+flowId +"  ";
		}
		Session session = getSession();
		String smsIds = "";
		Query query = session.createQuery(sql);
		Long l = (Long) query.uniqueResult();
		return l==null?0:l;
	}

	public Long getEntrustedListCount(int personId,String qs, String entrustStatus,int flowId)
			throws Exception {
		String data = "";
		String sql = "";

		if (entrustStatus.equals("0")
				|| TeeUtility.isNullorEmpty(entrustStatus)) {
			sql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid="
					+ personId + " and frp.otherUser is not null";
		} else {
			sql = "select count(frp) from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid="
				+ personId + " and frp.otherUser is not null and frp.flowRun.runName like '%"
					+ entrustStatus + "%'";
		}
		
		if(!"".equals(qs)){
			sql+=" and (frp.flowRun.runName like '%"+TeeDbUtility.formatString(qs)+"%' or frp.flowRun.runId="+TeeStringUtil.getInteger(qs, 0)+") ";
		}

		if(flowId>0){
			sql+=" and frp.flowType.sid="+flowId+" ";
		}
		Session session = getSession();
		String smsIds = "";
		Query query = session.createQuery(sql);
		Long c = (Long) query.uniqueResult();
		return c==null?0:c;
	}

	public List<TeeFlowRuleModel> getModelList(int personId,
			String entrustStatus,int flowTypeId, int index, int page) throws Exception {
		String data = "";
		String sql = "";
		if (entrustStatus.equals("0")
				|| TeeUtility.isNullorEmpty(entrustStatus)) {
			sql = "SELECT FLOW_RULE.*,FLOW_NAME,USER_NAME from FLOW_RULE LEFT JOIN FLOW_TYPE ON (FLOW_TYPE.SID=FLOW_RULE.FLOW_ID) LEFT JOIN person ON (FLOW_RULE.TO_USER=person.UUID) where FLOW_RULE.USER_ID="
					+ personId + "";
		} else {
			sql = "SELECT FLOW_RULE.*,FLOW_NAME,USER_NAME from FLOW_RULE LEFT JOIN FLOW_TYPE ON (FLOW_TYPE.SID=FLOW_RULE.FLOW_ID) LEFT JOIN person ON (FLOW_RULE.TO_USER=person.UUID) where FLOW_RULE.TO_USER="
					+ personId + " and STATUS = 1";
		}

		if(flowTypeId>0){
			sql+= " and FLOW_RULE.FLOW_ID="+flowTypeId+"  ";
		}
		Session session = getSession();
		String smsIds = "";
		SQLQuery query = session.createSQLQuery(sql);
		query.setFirstResult(index);
		query.setMaxResults(page);
		List<Object[]> objList = query.list();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TeeFlowRuleModel> list = new ArrayList<TeeFlowRuleModel>();
		for (Object[] o : objList) {
			TeeFlowRuleModel model = new TeeFlowRuleModel();
			int sid = TeeStringUtil.getInteger(o[0], 0);
			Date beginDate = TeeStringUtil.getDate(o[1]);
			Date endDate = TeeStringUtil.getDate(o[2]);
			int status = TeeStringUtil.getInteger(o[3], 0);
			int flowId = TeeStringUtil.getInteger(o[4], 0);
			int toUser = TeeStringUtil.getInteger(o[5], 0);
			int userId = TeeStringUtil.getInteger(o[6], 0);
			String flowName = TeeStringUtil.getString(o[7]);
			String userName = TeeStringUtil.getString(o[8]);
			model.setSid(sid);
			model.setFlowName(flowName);
			model.setUserName(userName);
			// 判断状态
			Date date = new Date();
			boolean flag1 = date.after(beginDate);
			boolean flag2 = date.before(endDate);

			int statusFlag = 0;
			String dateDesc = "";
			String flowStatus = "";
			if (status == 1) {
//				if (!sdf.format(beginDate).equals("1900-01-01 00:00:00")
//						&& !sdf.format(endDate).equals("1900-01-01 00:00:00")) {
//					dateDesc = sdf.format(beginDate) + "--"
//							+ sdf.format(endDate);
//					if (flag1 && flag2)
//						statusFlag = 1;
//				} else if (!sdf.format(beginDate).equals("1900-01-01 00:00:00")) {
//					dateDesc = "开始于" + sdf.format(beginDate);
//					if (flag1)
//						statusFlag = 1;
//				} else if (!sdf.format(endDate).equals("1900-01-01 00:00:00")) {
//					dateDesc = "截止于" + sdf.format(endDate);
//					if (flag2)
//						statusFlag = 1;
//				} else {
					dateDesc = "一直有效";
//					statusFlag = 1;
//				}
				
			}else{
				dateDesc = "开始于" + sdf.format(beginDate);
				dateDesc += "截止于" + sdf.format(endDate);
			}
//			if (statusFlag == 1) {
//				flowStatus = "生效";
//			} else {
//				flowStatus = "失效";
//			}
			if(status!=1){//非一直有效
				if (flag1 && flag2){
					flowStatus = "2";
				}else{
					flowStatus = "0";
				}
			}else{
				flowStatus = "1";
			}
			String validTime = dateDesc;
			model.setValidTime(validTime);
			model.setFlowStatus(flowStatus);
			model.setPrcsName(getUserName(userId));
			list.add(model);

		}

		return list;
	}

	public List<TeeFlowRuleModel> getEntrustModelList(int personId,String qs,
			String entrustStatus,int flowId,int index, int page) throws Exception {
		String data = "";
		String sql = "";
		sql = "from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.otherUser.uuid="+personId;
		if (!entrustStatus.equals("0")
				&& !TeeUtility.isNullorEmpty(entrustStatus)) {
			sql += " AND frp.flowRun.runName like '%" + entrustStatus + "%'";
		}
		if(!"".equals(qs)){
			sql+=" and (frp.flowRun.runName like '%"+TeeDbUtility.formatString(qs)+"%' or frp.flowRun.runId="+TeeStringUtil.getInteger(qs, 0)+") ";
		}
		
		if(flowId>0){
			sql+=" and frp.flowType.sid="+flowId+" ";
		}
		
		sql += " order by frp.flowRun.runId desc";
		Session session = getSession();
		String smsIds = "";
		Query query = session.createQuery(sql);
		query.setFirstResult(index);
		query.setMaxResults(page);
		List<TeeFlowRunPrcs> frpList = query.list();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TeeFlowRuleModel> list = new ArrayList<TeeFlowRuleModel>();
		for (TeeFlowRunPrcs frp : frpList) {
			TeeFlowRuleModel model = new TeeFlowRuleModel();
			model.setSid(frp.getFlowRun().getRunId());
			model.setFlowName(frp.getFlowRun().getFlowType().getFlowName());
			model.setWorkName(frp.getFlowRun().getRunName());
			model.setPrcsName(frp.getFlowPrcs()==null?"":frp.getFlowPrcs().getPrcsName());
			String flowStatus = "";
			switch (frp.getFlag()) {
			case 1:
				flowStatus = "未接收";
				break;
			case 2:
				flowStatus = "办理中";
				break;
			case 3:
				flowStatus = "转交下一步";
				break;
			case 4:
				flowStatus = "已办结";
				break;
			case 5:
				flowStatus = "自由流程预设步骤";
				break;
			}
			model.setFlowStatus(flowStatus);
			model.setEntrustTime(frp.getCreateTime().getTime());
			model.setUserName(frp.getPrcsUser().getUserName());
			model.setRunId(frp.getFlowRun().getRunId());
			list.add(model);
		}

		return list;
	}

	public String getPrcsName(int flowId, int prcsId) {

		String sql = "select PRCS_NAME from FLOW_PROCESS WHERE FLOW_ID="
				+ flowId + " and PRCS_ID=" + prcsId;
		Session session = getSession();
		String smsIds = "";
		SQLQuery query = session.createSQLQuery(sql);
		Object obj = query.uniqueResult();

		return (String) obj;

	}

	public String getUserName(int uuid) {

		String sql = "select USER_NAME from person WHERE UUID=" + uuid;
		Session session = getSession();
		String smsIds = "";
		SQLQuery query = session.createSQLQuery(sql);
		Object obj = query.uniqueResult();

		return (String) obj;

	}

	public List<TeeFlowRuleModel> getEntrustedModelList(int personId,String qs,
			String entrustStatus,int flowId, int index, int page) throws Exception {
		String data = "";
		String sql = "";
		sql = "from TeeFlowRunPrcs frp where frp.flowRun.delFlag=0 and frp.prcsUser.uuid="+personId+" and frp.otherUser is not null ";
		if (!entrustStatus.equals("0")
				&& !TeeUtility.isNullorEmpty(entrustStatus)) {
			sql += " AND frp.flowRun.runName like '%" + entrustStatus + "%'";
		}
		if(!"".equals(qs)){
			sql+=" and (frp.flowRun.runName like '%"+TeeDbUtility.formatString(qs)+"%' or frp.flowRun.runId="+TeeStringUtil.getInteger(qs, 0)+") ";
		}
		
		if(flowId>0){
			sql+=" and frp.flowType.sid="+flowId+" ";
			
		}
		sql += " order by frp.flowRun.runId desc";
		Session session = getSession();
		String smsIds = "";
		Query query = session.createQuery(sql);
		query.setFirstResult(index);
		query.setMaxResults(page);
		List<TeeFlowRunPrcs> frpList = query.list();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TeeFlowRuleModel> list = new ArrayList<TeeFlowRuleModel>();
		for (TeeFlowRunPrcs frp : frpList) {
			TeeFlowRuleModel model = new TeeFlowRuleModel();
			model.setSid(frp.getFlowRun().getRunId());
			model.setFlowName(frp.getFlowRun().getFlowType().getFlowName());
			model.setWorkName(frp.getFlowRun().getRunName());
			model.setPrcsName(frp.getFlowPrcs()==null?"":frp.getFlowPrcs().getPrcsName());
			String flowStatus = "";
			switch (frp.getFlag()) {
			case 1:
				flowStatus = "未接收";
				break;
			case 2:
				flowStatus = "办理中";
				break;
			case 3:
				flowStatus = "转交下一步";
				break;
			case 4:
				flowStatus = "已办结";
				break;
			case 5:
				flowStatus = "自由流程预设步骤";
				break;
			}
			model.setFlowStatus(flowStatus);
			model.setEntrustTime(frp.getCreateTime().getTime());
			model.setUserName(frp.getPrcsUser().getUserName());
			String users = "";
			if(frp.getFromUser()!=null){
				users+="（"+frp.getFromUser().getUserName()+"）";
			}
			if(frp.getOtherUser()!=null){
				users+="（"+frp.getOtherUser().getUserName()+"）";
			}
			model.setUserName(users);
			model.setRunId(frp.getFlowRun().getRunId());
			list.add(model);
		}

		return list;
	}

	/**
	 * 标记标记位
	 * 
	 * @param conn
	 * @param field
	 * @param value
	 * @param seqId
	 * @param tableName
	 * @throws SQLException
	 */
	public void updateFlag(String field, String value, String seqId,
			String tableName) throws Exception {
		String sql = "UPDATE " + tableName + " SET " + field + " = '" + value
				+ "'" + " WHERE SID IN (" + seqId + ")";
		Session session = getSession();
		String smsIds = "";
		SQLQuery query = session.createSQLQuery(sql);
		query.executeUpdate();

	}

	/**
	 * 
	 * @param personId
	 * @param entrustStatus
	 * @param index
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<TeePerson> getRealPerson(TeePerson person, int flowTypeId) {

		List<TeePerson> personList = new ArrayList<TeePerson>();
		String sql = "";
		sql = "from TeeFlowRule rule where rule.user.uuid="
				+ person.getUuid()
				+ " and rule.flowType.sid="
				+ flowTypeId
				+ "";
		
		Session session = getSession();
		Query query = session.createQuery(sql);
//		query.setParameter(0, new Date());
//		query.setParameter(1, new Date());
		
		List<TeeFlowRule> rules = query.list();
		for(TeeFlowRule rule:rules){
			if(rule.getStatus()==1){//委托规则一直生效
				personList.add(rule.getToUser());		
			}else{//委托规则不是一直生效
				Date currDate=new Date();
				if(currDate.getTime()>=rule.getBeginDate().getTime()&&currDate.getTime()<=rule.getEndDate().getTime()){
					personList.add(rule.getToUser());		
				}	
			}	
		}
		
//		Iterator<TeePerson> it = query.iterate();
//
//		while (it != null && it.hasNext()) {
//			personList.add(it.next());
//		}

		return personList;
	}
	
	/**
	 * 获取委托人的规则
	 * @param personId
	 * @return
	 */
	public List<TeeFlowRule> getDeligatorRules(int personId){
		return super.find("from TeeFlowRule rule where rule.user.uuid=?", new Object[]{personId});
	}
}
