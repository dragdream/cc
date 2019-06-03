package com.beidasoft.xzfy.caseAcceptence.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseAcceptence.bean.CaseAcceptInfo;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseListInfo;
import com.beidasoft.xzfy.caseAcceptence.bean.CaseSearchInfo;
import com.beidasoft.xzfy.utils.Const;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@SuppressWarnings("rawtypes")
@Repository
public class CaseAcceptDao extends TeeBaseDao{

	/**
	 * 是否存在案件,true存在,false不存在
	 * @param caseId
	 * @return
	 */
	public boolean isExitCase(String caseId){
		boolean result = true;
		StringBuffer str = new StringBuffer();
		str.append("select count(*) from FY_CASE_HANDLING where case_id='");
		str.append(caseId).append("'");
		Long total = countSQLByList(str.toString(), null);
		int num = total.intValue();
		if(num==0){
			result = false;
		}
		return result;
	}
	
	/**
	 * 更新案件受理状态
	 * @param caseAccept
	 */
	public void updateCaseStatus(CaseAcceptInfo caseAccept){
		
		StringBuffer str = new StringBuffer();
		str.append("update FY_CASE_HANDLING set ");
		str.append(" CASE_STATUS_CODE='");
		str.append(caseAccept.getCaseStatusCode());
		str.append("',");
		str.append(" CASE_STATUS='");
		str.append(caseAccept.getCaseStatus());
		str.append("',");
		str.append(" CASE_SUB_STATUS_CODE='");
		str.append(caseAccept.getCaseChildeStatusCode());
		str.append("',");
		str.append(" CASE_SUB_STATUS='");
		str.append(caseAccept.getCaseChildeStatus());
		//更新人
		str.append("',");
		str.append(" MODIFIED_USER='");
		str.append(caseAccept.getModifiedUser());
		str.append("',");
		str.append(" MODIFIED_USER_ID='");
		str.append(caseAccept.getModifiedUserId());
		str.append("',");
		str.append(" MODIFIED_TIME='");
		str.append(caseAccept.getModifiedTime());
		
		str.append("' where CASE_ID='");
		str.append(caseAccept.getCaseId());
		str.append("'");
		executeNativeUpdate(str.toString(), null);
	}
	
	/**
	 * 案件受理完成
	 * @param undertaker
	 */
	public void caseAcceptCommit(CaseAcceptInfo caseAccept){
		
		StringBuffer str = new StringBuffer();
		str.append("update FY_CASE_HANDLING set ");
		str.append(" CASE_STATUS_CODE='");
		str.append(caseAccept.getCaseStatusCode());
		str.append("',");
		str.append(" CASE_STATUS='");
		str.append(caseAccept.getCaseStatus());
		str.append("',");
		str.append(" CASE_SUB_STATUS_CODE='");
		str.append(caseAccept.getCaseChildeStatusCode());
		str.append("',");
		str.append(" CASE_SUB_STATUS='");
		str.append(caseAccept.getCaseChildeStatus());
		str.append("',DEAL_MAN1_ID = '");
		str.append(caseAccept.getDealManFirstId());
		str.append("',DEAL_MAN2_ID = '");
		str.append(caseAccept.getDealManSecondId());
		str.append("' where CASE_ID='");
		str.append(caseAccept.getCaseId());
		str.append("'");
		executeNativeUpdate(str.toString(), null);
		
	}
	
	
	/**
	 * 获取并案案件列表
	 * @param loginUser
	 * @param status
	 * @param accept
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CaseListInfo> getCaseMergeList(TeePerson loginUser,
			String status,CaseSearchInfo accept,String mainCaseId){
		
		StringBuffer str = new StringBuffer();
		//基础SQL
		String baseSQL = createBaseSQL();
		str.append(baseSQL);
		//状态
		str.append(" and a.CASE_STATUS_CODE in(");
		str.append(status);
		str.append(")");
		//判空
		if( StringUtils.isEmpty(mainCaseId) ){
			//主案件ID为空，查询所有未合并的案件ID
			str.append(" and a.merger_case_num = 0");
			str.append(" and a.merger_case_id is null");
		}
		else{
			//主案件ID不为空,查询主案件合并的案件
			str.append(" and a.merger_case_id='");
			str.append(mainCaseId);
			str.append("'");
		}
		//排序
		str.append(" order by a.APPLICATION_DATE DESC");
		
		//分页
		int start = (accept.getPage()-1)* accept.getRows();
		
		//获取session,设置分页信息
		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		query.setFirstResult(start);
		query.setMaxResults(accept.getRows());
		List<Object[]> result = query.list();
		//返回结果封装成对象
		List<CaseListInfo> list = new ArrayList<CaseListInfo>();
		CaseListInfo resp = null;
		for(Object[] arr : result ){
			resp = com.beidasoft.xzfy.utils.StringUtils.arrayToObject(arr, CaseListInfo.class);
			list.add(resp);
		}
		return list;
	}
	
	
	/**
	 * 获取总记录数
	 * @param loginUser
	 * @param status
	 * @param accept
	 * @param mainCaseId
	 * @return
	 */
	public long getCaseMergeListSize(TeePerson loginUser,
			String status,CaseSearchInfo accept,String mainCaseId){
		
		StringBuffer str = new StringBuffer();
		str.append("select count(*) from(");
		//基础SQL
		String baseSQL = createBaseSQL();
		str.append(baseSQL);
		//状态
		str.append(" and a.CASE_STATUS_CODE in(");
		str.append(status);
		str.append(")");
		//判空
		if( StringUtils.isEmpty(mainCaseId) ){
			//主案件ID为空，查询所有未合并的案件ID
			str.append(" and a.merger_case_num = 0");
			str.append(" and a.merger_case_id is null");
		}
		else{
			//主案件ID不为空,查询主案件合并的案件
			str.append(" and a.merger_case_id='");
			str.append(mainCaseId);
			str.append("'");
		}
		//排序
		str.append(" order by a.APPLICATION_DATE DESC");
		str.append(")");
		
		//获取session,设置分页信息
		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		Object result = query.uniqueResult();
		long lon = 0;
		if(result instanceof BigInteger){
			BigInteger bigInt = (BigInteger) query.uniqueResult();
			lon = Long.parseLong(bigInt.toString());
		}else{
			lon = Long.parseLong(result.toString());
		}
		return lon;
	}
	
	
	/**
	 * 案件合并
	 * @param caseId
	 * @param mergeCaseIds
	 */
	public void caseMerge(String caseId,String mergeCaseIds){
		
		String[] ids = mergeCaseIds.split(",");
		StringBuffer batchIds = new StringBuffer();
		
		for(int i=0; i<ids.length-1; i++){
			batchIds.append("'").append(ids[i]).append("'").append(",");
		}
		batchIds.append("'").append(ids[ids.length-1]).append("'");
			
		StringBuffer str = new StringBuffer();
		str.append("update FY_CASE_HANDLING set MERGER_CASE_ID = '");
		str.append(caseId);
		str.append("' where case_id in (");
		str.append(batchIds);
		str.append(")");
		executeNativeUpdate(str.toString(), null);
	}
	
	
	/**
	 * 获取受理案件列表
	 * @param loginUser
	 * @param status
	 * @param accept
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<CaseListInfo> getCaseAcceptList(TeePerson loginUser,
			String status,CaseSearchInfo accept,String isNeedDeal) {
		
		List<CaseListInfo> list = new ArrayList<CaseListInfo>();
		
		//拼接SQL
		StringBuffer str = new StringBuffer();		
		
		//当前用户受理(审理,结案，归档)过的记录
		String acceptedSQL = createAcceptedSQL(loginUser);
		
		//当前用户需要受理(审理,结案，归档)的记录
		String needAcceptSQL = createNeedAcceptSQL(loginUser, status);
		
		if(Const.TYPE.ZERO.equals(isNeedDeal)){
			//查询用户受理过(审理,结案，归档)的和需要用户受理(审理,结案，归档)案件记录数
			str.append("select * from(");
			str.append(needAcceptSQL);
			if( !StringUtils.isEmpty(acceptedSQL)){
				str.append(" union ");
				str.append(acceptedSQL);
			}
			str.append(") tab where 1=1");
		}
		else{
			// 查询需要用户受理(审理,结案，归档)案件记录数
			if( !StringUtils.isEmpty(needAcceptSQL)){
				str.append(" select * from( ");
				str.append(needAcceptSQL);
				str.append(") tab where 1=1");
			}
			else{
				return list;
			}
		}
		
		//案件编号
		if( !StringUtils.isEmpty(accept.getCaseNum())){
			str.append(" and tab.caseNum like '%");
			str.append(accept.getCaseNum());
			str.append("%'");
		}
		//申请方式
		if( !StringUtils.isEmpty(accept.getPostType())){
			str.append(" and tab.postTypeCode = '");
			str.append(accept.getPostType());
			str.append("'");
		}
		//申请人
		if( !StringUtils.isEmpty(accept.getName())){
			str.append(" and tab.name like '%");
			str.append(accept.getName());
			str.append("%'");
		}
		
		//案件状态
		if( !StringUtils.isEmpty(accept.getCaseStatus())){
			str.append(" and tab.caseStatusCode = '");
			str.append(accept.getCaseStatus());
			str.append("'");
		}
		//开始时间
		if( !StringUtils.isEmpty(accept.getStart())){
			str.append(" and tab.applicationDate  >='");
			str.append(accept.getStart().replaceAll("-", "/"));
			str.append("'");
		}
		//结束时间
		if( !StringUtils.isEmpty(accept.getEnd())){
			str.append(" and tab.applicationDate <='");
			str.append(accept.getEnd().replaceAll("-", "/"));
			str.append("'");
		}
		//被申请人
		if( !StringUtils.isEmpty(accept.getRespName())){
			str.append(" and tab.respondentName like '%");
			str.append(accept.getRespName());
			str.append("%'");
		}
		
		//审批状态
		if( !StringUtils.isEmpty(accept.getApproveType())){
			str.append(" and tab.isApproval = '");
			str.append(accept.getApproveType());
			str.append("'");
		}
		str.append(" order by tab.applicationDate DESC");
		
		
		//分页
		int start = (accept.getPage()-1)* accept.getRows();
		
		//获取session,设置分页信息
		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		query.setFirstResult(start);
		query.setMaxResults(accept.getRows());
		List<Object[]> result = query.list();
		//返回结果封装成对象
		CaseListInfo resp = null;
		for(Object[] arr : result ){
			resp = com.beidasoft.xzfy.utils.StringUtils.arrayToObject(arr, CaseListInfo.class);
			list.add(resp);
		}
		return list;
	}
	
	/**
	 * 获取总记录数
	 * @param loginUser
	 * @param status
	 * @param accept
	 * @param isNeedDeal
	 * @return
	 */
	public long getCaseAcceptListSize(TeePerson loginUser,
			String status,CaseSearchInfo accept,String isNeedDeal) {
		
		//拼接SQL
		StringBuffer str = new StringBuffer();		
		str.append("select count(*) from (");
		//当前用户受理(审理,结案，归档)过的记录
		String acceptedSQL = createAcceptedSQL(loginUser);
		
		//当前用户需要受理(审理,结案，归档)的记录
		String needAcceptSQL = createNeedAcceptSQL(loginUser, status);
		
		if(Const.TYPE.ZERO.equals(isNeedDeal)){
			//查询用户受理过(审理,结案，归档)的和需要用户受理(审理,结案，归档)案件记录数
			str.append("select * from(");
			str.append(needAcceptSQL);
			if( !StringUtils.isEmpty(acceptedSQL)){
				str.append(" union ");
				str.append(acceptedSQL);
			}
			str.append(") tab where 1=1");
		}
		else{
			// 查询需要用户受理(审理,结案，归档)案件记录数
			if( !StringUtils.isEmpty(needAcceptSQL)){
				str.append(" select * from( ");
				str.append(needAcceptSQL);
				str.append(") tab where 1=1");
			}
			else{
				return 0;
			}
		}
		
		//案件编号
		if( !StringUtils.isEmpty(accept.getCaseNum())){
			str.append(" and tab.caseNum like '%");
			str.append(accept.getCaseNum());
			str.append("%'");
		}
		//申请方式
		if( !StringUtils.isEmpty(accept.getPostType())){
			str.append(" and tab.postTypeCode = '");
			str.append(accept.getPostType());
			str.append("'");
		}
		//申请人
		if( !StringUtils.isEmpty(accept.getName())){
			str.append(" and tab.name like '%");
			str.append(accept.getName());
			str.append("%'");
		}
		
		//案件状态
		if( !StringUtils.isEmpty(accept.getCaseStatus())){
			str.append(" and tab.caseStatusCode = '");
			str.append(accept.getCaseStatus());
			str.append("'");
		}
		//开始时间
		if( !StringUtils.isEmpty(accept.getStart())){
			str.append(" and tab.applicationDate  >='");
			str.append(accept.getStart().replaceAll("-", "/"));
			str.append("'");
		}
		//结束时间
		if( !StringUtils.isEmpty(accept.getEnd())){
			str.append(" and tab.applicationDate <='");
			str.append(accept.getEnd().replaceAll("-", "/"));
			str.append("'");
		}
		//被申请人
		if( !StringUtils.isEmpty(accept.getRespName())){
			str.append(" and tab.respondentName like '%");
			str.append(accept.getRespName());
			str.append("%'");
		}
		
		//审批状态
		if( !StringUtils.isEmpty(accept.getApproveType())){
			str.append(" and tab.isApproval = '");
			str.append(accept.getApproveType());
			str.append("'");
		}
		str.append(" order by tab.applicationDate DESC");
		
		str.append(") ");
		
		
		
		//获取session,设置分页信息
		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		Object result = query.uniqueResult();
		long lon = 0;
		if(result instanceof BigInteger){
			BigInteger bigInt = (BigInteger) query.uniqueResult();
			lon = Long.parseLong(bigInt.toString());
		}else{
			lon = Long.parseLong(result.toString());
		}
		return lon;
	}
	
	
	
	/**
	 * 获取当前用户所有登记的案件
	 * 
	 * @param userId
	 * @param curpage
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CaseListInfo> getCaseRegisterList(TeePerson loginUser,
			String status, CaseSearchInfo accept){
		StringBuffer str = new StringBuffer();
		String baseSQL = createBaseSQL();
		str.append(baseSQL);
		
		// 非系统管理员,查询创建的
		if (loginUser.getIsAdmin().equals(Const.TYPE.ZERO)) {
			str.append(" and a.CREATED_USER_ID ='");
			str.append(loginUser.getUuid());
			str.append("'");
		}
		// 管理员,查询所有登记状态的案件
		else {
			// 状态码
			if (!StringUtils.isEmpty(status)) {
				str.append(" and a.CASE_STATUS_CODE in(");
				str.append(status);
				str.append(")");
			}
		}
		// 案件编号
		if (!StringUtils.isEmpty(accept.getCaseNum())) {
			str.append(" and a.CASE_NUM like '%");
			str.append(accept.getCaseNum());
			str.append("%'");
		}
		// 申请方式
		if (!StringUtils.isEmpty(accept.getPostType())) {
			str.append(" and a.APPLICATION_TYPE_CODE = '");
			str.append(accept.getPostType());
			str.append("'");
		}
		// 申请人
		if (!StringUtils.isEmpty(accept.getName())) {
			str.append(" and b.NAME like '%");
			str.append(accept.getName());
			str.append("%'");
		}
		//案件状态
		if( !StringUtils.isEmpty(accept.getCaseStatus())){
			str.append(" and a.CASE_STATUS_CODE = '");
			str.append(accept.getCaseStatus());
			str.append("'");
		}
		// 开始时间
		if (!StringUtils.isEmpty(accept.getStart())) {
			str.append(" and a.APPLICATION_DATE  >='");
			str.append(accept.getStart().replaceAll("-", "/"));
			str.append("'");
		}
		// 结束时间
		if (!StringUtils.isEmpty(accept.getEnd())) {
			str.append(" and a.APPLICATION_DATE <='");
			str.append(accept.getEnd().replaceAll("-", "/"));
			str.append("'");
		}
		str.append(" order by a.CREATED_TIME DESC");
		
		// 分页
		int start = (accept.getPage() - 1) * accept.getRows();

		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		query.setFirstResult(start);
		query.setMaxResults(accept.getRows());
		//返回结果封装成对象
		List<Object[]> result = query.list();
		List<CaseListInfo> list = new ArrayList<CaseListInfo>();
		CaseListInfo resp = null;
		for (Object[] arr : result) {
			resp = com.beidasoft.xzfy.utils.StringUtils.arrayToObject(arr,CaseListInfo.class);
			list.add(resp);
		}
		return list;
	}
	
	/**
	 * 获取总记录数
	 * @param loginUser
	 * @param status
	 * @param accept
	 * @return
	 */
	public long getCaseRegisterListSize(TeePerson loginUser,
			String status, CaseSearchInfo accept){
		StringBuffer str = new StringBuffer();
		str.append("select count(*) from (");
		String baseSQL = createBaseSQL();
		str.append(baseSQL);
		
		// 非系统管理员,查询创建的
		if (loginUser.getIsAdmin().equals(Const.TYPE.ZERO)) {
			str.append(" and a.CREATED_USER_ID ='");
			str.append(loginUser.getUuid());
			str.append("'");
		}
		// 管理员,查询所有登记状态的案件
		else {
			// 状态码
			if (!StringUtils.isEmpty(status)) {
				str.append(" and a.CASE_STATUS_CODE in(");
				str.append(status);
				str.append(")");
			}
		}
		// 案件编号
		if (!StringUtils.isEmpty(accept.getCaseNum())) {
			str.append(" and a.CASE_NUM like '%");
			str.append(accept.getCaseNum());
			str.append("%'");
		}
		// 申请方式
		if (!StringUtils.isEmpty(accept.getPostType())) {
			str.append(" and a.APPLICATION_TYPE_CODE = '");
			str.append(accept.getPostType());
			str.append("'");
		}
		// 申请人
		if (!StringUtils.isEmpty(accept.getName())) {
			str.append(" and b.NAME like '%");
			str.append(accept.getName());
			str.append("%'");
		}
		//案件状态
		if( !StringUtils.isEmpty(accept.getCaseStatus())){
			str.append(" and a.CASE_STATUS_CODE = '");
			str.append(accept.getCaseStatus());
			str.append("'");
		}
		// 开始时间
		if (!StringUtils.isEmpty(accept.getStart())) {
			str.append(" and a.APPLICATION_DATE  >='");
			str.append(accept.getStart().replaceAll("-", "/"));
			str.append("'");
		}
		// 结束时间
		if (!StringUtils.isEmpty(accept.getEnd())) {
			str.append(" and a.APPLICATION_DATE <='");
			str.append(accept.getEnd().replaceAll("-", "/"));
			str.append("'");
		}
		str.append(" order by a.CREATED_TIME DESC");
		
		str.append(") ");
		
	
		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		//返回结果封装成对象
		Object result = query.uniqueResult();
		long lon = 0;
		if(result instanceof BigInteger){
			BigInteger bigInt = (BigInteger) query.uniqueResult();
			lon = Long.parseLong(bigInt.toString());
		}else{
			lon = Long.parseLong(result.toString());
		}
		return lon;
	}
	
	/************************************************************/
	
	/**
	 * 创建基础SQL语句
	 * @return
	 */
	public static String createBaseSQL(){
		//拼接SQL
		StringBuffer str = new StringBuffer();
		str.append("SELECT a.case_id as caseId,a.CASE_NUM as caseNum,"
				+ "a.APPLICATION_TYPE_CODE as postTypeCode, "
				+ "a.APPLICATION_TYPE as postType, "
				+ "b.NAME,"
				+ "a.RESPONDENT_NAME as respondentName,"
				+ "a.CASE_STATUS_CODE as caseStatusCode,"
				+ "a.CASE_STATUS as caseStatus,"
				+ "a.CASE_SUB_STATUS_CODE as caseChiledStatusCode,"
				+ "a.CASE_SUB_STATUS as caseChiledStatus,"
				+ "a.APPLICATION_DATE as applicationDate,"
				+ "a.APPLICATION_DATE as remainderTime,"
				+ "a.MERGER_CASE_NUM as mergerCase ,"
				+ "a.IS_APPROVAL as isApproval "
				+ "FROM FY_CASE_HANDLING a "
				+ "left join (select t.case_id,LISTAGG(t.name,',') WITHIN GROUP( ORDER BY t.case_id) as name "
				+ "from FY_APPLICANT t group by t.case_id "
				+ ") b ON b.CASE_ID = a.CASE_ID "
				+ "WHERE a.is_delete = 0");
		return str.toString();
	}
	
	/**
	 * 创建查询当前用户需要受理(审理,结案，归档)的查询SQL语句
	 * @param loginUser
	 * @param status
	 * @return
	 */
	public String createNeedAcceptSQL(TeePerson loginUser,String status){
		//拼接
		StringBuffer str = new StringBuffer();
		str.append(createBaseSQL());
		//非系统管理员
		if( loginUser.getIsAdmin().equals(Const.TYPE.ZERO) ){
			//查询需要当前用户需要受理(审理,结案，归档)的记录
			str.append(" and a.DEAL_MAN1_ID = '");
			str.append(loginUser.getUuid());
			str.append("'");
		}
		//状态码
		if( !StringUtils.isEmpty(status)){
			str.append(" and a.CASE_STATUS_CODE in(");
			str.append(status);
			str.append(")");
			//str.append(" and a.CASE_SUB_STATUS_CODE is null");
		}
		//过滤合并的案件
		str.append(" and a.MERGER_CASE_ID is null ");
		
		return str.toString();
	}
	
	/**
	 * 创建查询当前用户受理(审理,结案，归档)过的案件的SQL语句
	 * @param loginUser
	 * @param status
	 * @return
	 */
	public String createAcceptedSQL(TeePerson loginUser){
		//拼接
		StringBuffer str = new StringBuffer();
		//非系统管理员
		if( loginUser.getIsAdmin().equals(Const.TYPE.ZERO) ){
			str.append(createBaseSQL());
			//查询当前用户受理(审理,结案，归档)过的记录
			str.append(" and a.CREATED_USER_ID in(");
			str.append(" select t.case_id from fy_process_flow t where t.created_user_id = '");
			str.append(loginUser.getUuid());
			str.append("' group by t.case_id");
			str.append(")");
			
			//过滤合并的案件
			str.append(" and a.MERGER_CASE_ID is null ");
			
			return str.toString();
		}else{
			return null;
		}
	}
}
