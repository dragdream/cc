package com.beidasoft.xzfy.caseQuery.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.beidasoft.xzfy.caseQuery.bean.CaseListInfo;
import com.beidasoft.xzfy.caseQuery.bean.CaseSearchInfo;
import com.beidasoft.xzfy.utils.Const;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@SuppressWarnings("rawtypes")
@Repository
public class CaseListDao extends TeeBaseDao{
	
	public List<CaseListInfo> getCaseAcceptList(TeePerson loginUser,
			String status,CaseSearchInfo accept) {
		
		//拼接SQL
		StringBuffer str = new StringBuffer();		
		
		//当前用户受理(审理,结案，归档)过的记录
		String acceptedSQL = createAcceptedSQL(loginUser);
		
		//当前用户需要受理(审理,结案，归档)的记录
		String needAcceptSQL = createNeedAcceptSQL(loginUser, status);
		
		//查询用户受理过(审理,结案，归档)的和需要用户受理(审理,结案，归档)案件记录数
		str.append("select * from(");
		str.append(needAcceptSQL);
		if( !StringUtils.isEmpty(acceptedSQL)){
			str.append(" union ");
			str.append(acceptedSQL);
		}
		str.append(") tab where 1=1");
		
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
		str.append(" order by tab.applicationDate DESC");
		
		System.out.println(str.toString());
		//分页
		int start = (accept.getPage()-1)* accept.getRows();
		
		//获取session,设置分页信息
		Session session = this.getSession();
		Query query = session.createSQLQuery(str.toString());
		query.setFirstResult(start);
		query.setMaxResults(accept.getRows());
		@SuppressWarnings("unchecked")
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
				+ "a.MERGER_CASE_NUM as mergerCase "
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
			str.append(" and a.CASE_SUB_STATUS_CODE is null");
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
