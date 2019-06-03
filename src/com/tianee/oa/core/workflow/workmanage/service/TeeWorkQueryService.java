package com.tianee.oa.core.workflow.workmanage.service;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunConcern;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunLog;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunConcernDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunLogDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.core.workflow.workmanage.dao.TeeWorkQueryDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeFlowRunPrcsModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;
import com.tianee.webframe.util.thread.TeeRequestInfo;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeWorkQueryService extends TeeBaseService implements TeeWorkQueryServiceInterface {

	@Autowired
	private TeeWorkQueryDao workQueryDao;

	@Autowired
	private TeeFlowPrivServiveInterface flowPrivService;

	@Autowired
	private TeeFlowTypeDao flowTypeDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeFlowRunConcernDao flowRunConcern;

	@Autowired
	private TeeFlowRunPrcsDao flowRunPrcsDao;

	@Autowired
	private TeeFlowRunDao flowRunDao;

	@Autowired
	private TeeFlowRunLogDao flowRunLogDao;

	@Autowired
	private TeeFlowFormServiceInterface formService;

	@Autowired
	private TeeSmsManager smsManager;
	
	/*	*//**
	 * 查询与高级查询
	 * 
	 * @param queryParams
	 * @param loginUser
	 * @param dataGridModel
	 * @return
	 */
	/*
	 * @Transactional(readOnly=true) public TeeEasyuiDataGridJson query(Map
	 * queryParams,TeePerson loginUser,TeeDataGridModel dataGridModel){
	 * TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
	 * 
	 * Session session = workQueryDao.getSession(); Map<String,Object>
	 * parameters = new HashMap<String,Object>(); //获取查询条件 int flowId =
	 * TeeStringUtil.getInteger(queryParams.get("flowId"), 0);//流程id，为0则是全部流程
	 * int status = TeeStringUtil.getInteger(queryParams.get("status"),
	 * 0);//1：进行中，2：已办结 int type =
	 * TeeStringUtil.getInteger(queryParams.get("type"), 0);//0：全部 1：我发起 2：我经办
	 * 3：我管理 4：我关注 int runId =
	 * TeeStringUtil.getInteger(queryParams.get("runId"), 0);//流水号 int beginUser
	 * = TeeStringUtil.getInteger(queryParams.get("beginUser"), 0);//发起人 String
	 * runName = TeeStringUtil.getString(queryParams.get("runName"));//流程标题 Date
	 * start1 =
	 * TeeStringUtil.getDate(queryParams.get("start1"),"yyyy-MM-dd");//开始时间段1
	 * Date start2 =
	 * TeeStringUtil.getDate(queryParams.get("start2"),"yyyy-MM-dd");//开始时间段2
	 * int opType = TeeStringUtil.getInteger(queryParams.get("opType"),
	 * 0);//操作类型，0：查询/高级查询，1：导出html报表，2：导出excel报表。 int firstResult =
	 * (dataGridModel.getPage()-1)*dataGridModel.getRows(); int pageSize =
	 * dataGridModel.getRows(); String sort = dataGridModel.getSort(); String
	 * order = dataGridModel.getOrder();
	 * 
	 * //声明查询语句 StringBuffer sql = new StringBuffer(); String select = "";
	 * if(flowId!=0){ if(sort==null || order==null){ sort = "frd.RUN_ID"; order
	 * = "desc"; }else if("RUN_ID".equals(sort)){ sort = "frd.RUN_ID"; }
	 * 
	 * select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME," + "fr.DEL_FLAG, " +
	 * "fr.END_TIME, " + "fr.FORM_VERSION, " + "fr.RUN_NAME, " + "fr.FLOW_ID, "
	 * + "fr.FORM_ID,frd.*,ft.FLOW_NAME "; }else{ if(sort==null || order==null){
	 * sort = "RUN_ID"; order = "desc"; } select = "select fr.BEGIN_PERSON," +
	 * "fr.BEGIN_TIME," + "fr.DEL_FLAG, " + "fr.END_TIME, " +
	 * "fr.FORM_VERSION, " + "fr.RUN_NAME, " + "fr.FLOW_ID, " + "fr.FORM_ID," +
	 * "fr.RUN_ID,ft.FLOW_NAME "; } String selectCount = "select count(*) ";
	 * 
	 * if(flowId!=0){
	 * sql.append("from FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"+flowId+
	 * " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID"
	 * ); }else{ sql.append(
	 * "from FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID"
	 * ); }
	 * 
	 * if(flowId!=0){ sql.append(" and fr.FLOW_ID="+flowId); } if(runId!=0){
	 * sql.append(" and fr.RUN_ID="+runId); } if(!"".equals(runName)){ String
	 * runNameOper = TeeStringUtil.getString(queryParams.get("runNameOper"));
	 * if("=".equals(runNameOper)){
	 * sql.append(" and fr.RUN_NAME like '"+runName+"'"); }else
	 * if("<>".equals(runNameOper)){
	 * sql.append(" and fr.RUN_NAME not like '"+runName+"'"); }else
	 * if("like1".equals(runNameOper)){//开始于
	 * sql.append(" and fr.RUN_NAME like '"+runName+"%'"); }else
	 * if("like2".equals(runNameOper)){//包含
	 * sql.append(" and fr.RUN_NAME like '%"+runName+"%'"); }else
	 * if("like3".equals(runNameOper)){//结束于
	 * sql.append(" and fr.RUN_NAME like '%"+runName+"'"); } } //拼写时间
	 * if(start1!=null){ sql.append(" and fr.BEGIN_TIME >= :start1"); Calendar c
	 * = Calendar.getInstance(); c.setTime(start1); parameters.put("start1", c);
	 * } if(start2!=null){ start2.setHours(23); start2.setMinutes(59);
	 * start2.setSeconds(59); Calendar c = Calendar.getInstance();
	 * c.setTime(start2); sql.append(" and fr.BEGIN_TIME <= :start2");
	 * parameters.put("start2", c); }
	 * 
	 * //流程状态 if(status==1){ sql.append(" and fr.END_TIME is null"); }else
	 * if(status==2){ sql.append(" and fr.END_TIME is not null"); }
	 *//**
	 * 查询范围SQL语句组装
	 */
	/*
	 * 
	 * //我发起的 String myBegin = "fr.BEGIN_PERSON="+loginUser.getUuid()+"";
	 * 
	 * //我经办的 String myPrcs =
	 * "exists (select 1 from FLOW_RUN_PRCS frp where frp.RUN_ID=fr.RUN_ID and frp.PRCS_USER="
	 * +loginUser.getUuid()+")";
	 * 
	 * //我管理的（这里需要拿到当前人所管理的几个流程和其对应的管理部门串 StringBuffer myManage = new
	 * StringBuffer(); //管理权限 List managePrivList =
	 * flowPrivService.getQueryPostDeptsByAllFlow(loginUser, "1"); //监控权限 List
	 * monitorPrivList = flowPrivService.getQueryPostDeptsByAllFlow(loginUser,
	 * "2"); //查询权限 List searchPrivList =
	 * flowPrivService.getQueryPostDeptsByAllFlow(loginUser, "3"); //编辑权限 List
	 * editPrivList = flowPrivService.getQueryPostDeptsByAllFlow(loginUser,
	 * "4");
	 * 
	 * Map queryData = null; //所有权限汇总 List allPrivList = new ArrayList();
	 * for(int i=0;i<managePrivList.size();i++){ queryData = (Map)
	 * managePrivList.get(i); allPrivList.add(queryData); } for(int
	 * i=0;i<monitorPrivList.size();i++){ queryData = (Map)
	 * monitorPrivList.get(i); allPrivList.add(queryData); } for(int
	 * i=0;i<searchPrivList.size();i++){ queryData = (Map)
	 * searchPrivList.get(i); allPrivList.add(queryData); } for(int
	 * i=0;i<editPrivList.size();i++){ queryData = (Map) editPrivList.get(i);
	 * allPrivList.add(queryData); }
	 * 
	 * 
	 * if(allPrivList.size()!=0){//有权限 for(int i=0;i<allPrivList.size();i++){
	 * queryData = (Map) allPrivList.get(i);
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
	 * myManage.append("(fr.FLOW_ID="+queryData.get("flowTypeId")); String
	 * postDeptIds = String.valueOf(queryData.get("postDeptIds"));
	 * if(!postDeptIds.equals("0")){//加入限定部门条件 if(postDeptIds.endsWith(",")){
	 * postDeptIds = postDeptIds.substring(0,postDeptIds.length()-1); }
	 * myManage.
	 * append(" and exists (select 1 from DEPARTMENT dept where dept.uuid in ("
	 * +postDeptIds+") and p.dept_id=dept.uuid)"); } myManage.append(")");
	 * if(i!=allPrivList.size()-1){ myManage.append(" or "); } } } }else{
	 * myManage.append(" fr.FLOW_ID=0 "); }
	 * 
	 * //我关注的 String myConcern =
	 * "exists (select 1 from FLOW_RUN_CONCERN frc where frc.RUN_ID=fr.RUN_ID and frc.PERSON_ID="
	 * +loginUser.getUuid()+")";
	 * 
	 * //我查阅的 String myLookup =
	 * "exists (select 1 from FLOW_RUN_VIEW frv where frv.VIEW_PERSON="
	 * +loginUser.getUuid()+" and frv.RUN_ID=fr.RUN_ID)";
	 * 
	 * if(type==0){//所有范围 sql.append(" and (("+myBegin+") or ("+myPrcs+") ");
	 * if(myManage.length()!=0){ sql.append(" or ("+myManage.toString()+")"); }
	 * sql.append(" or ("+myConcern+")"); sql.append(" or ("+myLookup+")");
	 * sql.append(")"); }else if(type==1){//我发起的 sql.append(" and "+myBegin);
	 * }else if(type==2){//我经办的 sql.append(" and "+myPrcs); }else
	 * if(type==3){//我管理的 sql.append(" and ("+myManage+")"); }else
	 * if(type==4){//我关注的 sql.append(" and "+myConcern); }else
	 * if(type==5){//我查阅的 sql.append(" and "+myLookup); }else
	 * if(type==6){//指定发起人 sql.append(" and fr.BEGIN_PERSON="+beginUser+"");
	 * if(myManage.length()!=0){ sql.append(" and ("+myManage.toString()+")"); }
	 * } // System.out.println(sql.toString()); //条件公式 String condFormula =
	 * TeeStringUtil.getString(queryParams.get("condFormula"));
	 * 
	 * 
	 * String dialect = TeeSysProps.getString("dialect"); //拼写DATA相关 final
	 * StringBuffer dataSql = new StringBuffer(); StringBuffer extraSql = new
	 * StringBuffer(); //条件项目 final Map conditionItem = new HashMap(); Pattern p
	 * = Pattern.compile("^DATA_[0-9]+$"); Matcher mt = null; String oper =
	 * null; String value = null; if(flowId!=0){ Set<String> key =
	 * queryParams.keySet(); for(String keyName:key){ mt = p.matcher(keyName);
	 * if(mt.find()){ oper =
	 * TeeStringUtil.getString(queryParams.get(keyName+"_OPER")); value =
	 * TeeStringUtil.getString(queryParams.get(keyName));
	 * if("like1".equals(oper)){//开始于 conditionItem.put(keyName,
	 * TeeDbUtility.TO_CHAR(dialect,"frd."+keyName)+" like '"+value+"%'"); }else
	 * if("like2".equals(oper)){//包含于 conditionItem.put(keyName,
	 * TeeDbUtility.TO_CHAR(dialect,"frd."+keyName)+" like '%"+value+"%'");
	 * }else if("like3".equals(oper)){//结束于 conditionItem.put(keyName,
	 * TeeDbUtility.TO_CHAR(dialect,"frd."+keyName)+" like '%"+value+"'");
	 * }else{ //解决oracle clob字段为空的问题 if("".equals(value) && "=".equals(oper) &&
	 * TeeSysProps.getDialect().equals("oracle")){ conditionItem.put(keyName,
	 * TeeDbUtility.TO_CHAR(dialect,"frd."+keyName)+" is null"); }else{
	 * conditionItem.put(keyName,
	 * TeeDbUtility.TO_CHAR(dialect,"frd."+keyName)+" "+oper+" '"+value+"'"); }
	 * //parameters.put(keyName, queryParams.get(keyName)); }
	 * extraSql.append(" and "+conditionItem.get(keyName)); } } }
	 * 
	 * if(!"".equals(condFormula)){//开启条件公式查询 TeeRegexpAnalyser analyser = new
	 * TeeRegexpAnalyser(); analyser.setText(condFormula); String text =
	 * analyser.replace(new String[]{"\\[[0-9]+\\]"}, new TeeExpFetcher(){
	 * 
	 * @Override public String parse(String pattern) { // TODO Auto-generated
	 * method stub String item = pattern.substring(1, pattern.length()-1);
	 * String sql = TeeStringUtil.getString(conditionItem.get("DATA_"+item));
	 * if(!"".equals(sql)){ return sql; } return "1=1"; }
	 * 
	 * }); dataSql.append(" and ("+text+")"); }else{//普通条件查询
	 * dataSql.append(extraSql); }
	 * 
	 * sql.append(dataSql); SQLQuery queryCount =
	 * session.createSQLQuery(selectCount+sql.toString());
	 * sql.append(" order by "+sort+" "+order);
	 * 
	 * 
	 * 
	 * SQLQuery query = session.createSQLQuery(select+sql.toString());
	 * query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); Set<String>
	 * keys = parameters.keySet(); for(String k:keys){ query.setParameter(k,
	 * parameters.get(k)); queryCount.setParameter(k, parameters.get(k)); }
	 * if(opType == 0){
	 * query.setFirstResult((dataGridModel.getPage()-1)*dataGridModel
	 * .getRows()); query.setMaxResults(dataGridModel.getRows()); } List<Map>
	 * list = query.list(); long total =
	 * Long.parseLong(queryCount.uniqueResult().toString());
	 * 
	 * TeePerson beginPerson = null; List<Map> resultList = new
	 * ArrayList<Map>(); TeeFlowRunConcern concern = null; for(Map fr:list){ Map
	 * map = new HashMap(); map.put("runId",
	 * TeeStringUtil.getInteger(fr.get("RUN_ID"), 0)); map.put("flowId",
	 * TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0)); map.put("beginTimeDesc",
	 * TeeDateUtil.format((Timestamp)fr.get("BEGIN_TIME")));
	 * map.put("endTimeDesc",
	 * TeeDateUtil.format((Timestamp)fr.get("END_TIME"))); map.put("runName",
	 * TeeStringUtil.getString(fr.get("RUN_NAME"))); map.put("flowId",
	 * TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0)); map.put("delFlag",
	 * fr.get("DEL_FLAG"));
	 * 
	 * beginPerson =
	 * personDao.load(TeeStringUtil.getInteger(fr.get("BEGIN_PERSON"), 0));
	 * map.put("flowName", fr.get("FLOW_NAME"));
	 * 
	 * //查询指定发起人 map.put("prcsUser", beginPerson.getUserName());
	 *//**
	 * 渲染操作
	 */
	/*
	 * map.put("viewGraph", 1);//流程图
	 * 
	 * int delFlag = TeeStringUtil.getInteger(fr.get("DEL_FLAG"), 0);
	 * 
	 * if(delFlag!=1){ if(fr.get("END_TIME")==null){ //判断是否有关注 concern =
	 * flowRunConcern.getConcern(TeeStringUtil.getInteger(fr.get("RUN_ID"), 0),
	 * loginUser.getUuid()); if(concern!=null){ map.put("cancelConcern",
	 * 1);//取消关注 }else{ map.put("concern", 1);//关注 } } }
	 * 
	 * boolean hasManagePriv = false; boolean hasMonitorPriv = false; boolean
	 * hasSearchPriv = false; boolean hasEditPriv = false; //查找出当前管理权限 for(int
	 * i=0;i<managePrivList.size();i++){ queryData = (Map)
	 * managePrivList.get(i);
	 * if(TeeStringUtil.getInteger(queryData.get("flowTypeId"),
	 * 0)==TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0)){
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("0")){//加入限定部门条件
	 * if
	 * (TeeStringUtil.existString(TeeStringUtil.parseStringArray(queryData.get(
	 * "postDeptIds")), beginPerson.getDept().getUuid()+"")){ hasManagePriv =
	 * true; } }else{//有全部门权限 hasManagePriv = true; } } break; } } //查找出当前监控权限
	 * for(int i=0;i<monitorPrivList.size();i++){ queryData = (Map)
	 * monitorPrivList.get(i);
	 * if(TeeStringUtil.getInteger(queryData.get("flowTypeId"),
	 * 0)==TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0)){
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("0")){//加入限定部门条件
	 * if
	 * (TeeStringUtil.existString(TeeStringUtil.parseStringArray(queryData.get(
	 * "postDeptIds")), beginPerson.getDept().getUuid()+"")){ hasMonitorPriv =
	 * true; } }else{//有全部门权限 hasMonitorPriv = true; } } break; } } //查找出当前编辑权限
	 * for(int i=0;i<editPrivList.size();i++){ queryData = (Map)
	 * editPrivList.get(i);
	 * if(TeeStringUtil.getInteger(queryData.get("flowTypeId"),
	 * 0)==TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0)){
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("")){//有权限
	 * if(!String.valueOf(queryData.get("postDeptIds")).equals("0")){//加入限定部门条件
	 * if
	 * (TeeStringUtil.existString(TeeStringUtil.parseStringArray(queryData.get(
	 * "postDeptIds")), beginPerson.getDept().getUuid()+"")){ hasEditPriv =
	 * true; } }else{//有全部门权限 hasEditPriv = true; } } break; } }
	 * 
	 * if(delFlag!=1){ //管理权限 if(hasManagePriv){
	 * if(fr.get("END_TIME")!=null){//已结束 boolean hasExistUnhandledWorks =
	 * flowRunPrcsDao
	 * .hasExsitUnhandedWorks(TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
	 * if(hasExistUnhandledWorks){//存在未办理完成的工作，则可以进行恢复 map.put("recover", 1); }
	 * }else{ map.put("end", 1); } map.put("doDelete", 1); }
	 * 
	 * //编辑权限 if(hasEditPriv){ if(fr.get("END_TIME")!=null){//已结束
	 * map.put("doEdit", 1);//编辑权限 } } }
	 * 
	 * if(fr.get("END_TIME")==null){//已结束 map.put("doUrge", 1);//催办 }
	 * 
	 * 
	 * //获取流程的当前不步骤的名称 String
	 * hql="from TeeFlowRunPrcs  prcs where prcs.flowRun.runId="
	 * +(TeeStringUtil.getInteger(fr.get("RUN_ID"),
	 * 0))+" order by prcs.prcsId desc"; List<TeeFlowRunPrcs>
	 * prcsList=simpleDaoSupport.executeQuery(hql, null); TeeFlowRunPrcs
	 * flowRunPrcs=new TeeFlowRunPrcs(); if(prcsList.size()>0){
	 * flowRunPrcs=prcsList.get(0); } if(fr.get("END_TIME")==null){
	 * if(flowRunPrcs.getFlowPrcs()!=null){ map.put("CURRENT_STEP",
	 * "第"+flowRunPrcs
	 * .getPrcsId()+"步："+flowRunPrcs.getFlowPrcs().getPrcsName()); }else{
	 * map.put("CURRENT_STEP", "第"+flowRunPrcs.getPrcsId()+"步"); }
	 * 
	 * }else{ map.put("CURRENT_STEP", "已结束"); }
	 * 
	 * 
	 * resultList.add(map); }
	 * 
	 * json.setRows(resultList); json.setTotal(total);
	 * 
	 * return json; }
	 */

	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#query(java.util.Map, com.tianee.oa.core.org.bean.TeePerson, com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson query(Map queryParams, TeePerson loginUser,
			TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		String dialect = TeeSysProps.getString("dialect"); 
		Session session = workQueryDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 1：进行中，2：已办结
		int type = TeeStringUtil.getInteger(queryParams.get("type"), 0);// 0：全部
																		// 1：我发起
																		// 2：我经办
																		// 3：我管理
																		// 4：我关注
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		int beginUser = TeeStringUtil.getInteger(queryParams.get("beginUser"),
				0);// 发起人
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题
		String showFields = TeeStringUtil.getString(queryParams.get("showFields"));// 显示字段
		Date start1 = TeeStringUtil.getDate(queryParams.get("start1"),
				"yyyy-MM-dd");// 开始时间段1
		Date start2 = TeeStringUtil.getDate(queryParams.get("start2"),
				"yyyy-MM-dd");// 开始时间段2
		int opType = TeeStringUtil.getInteger(queryParams.get("opType"), 0);// 操作类型，0：查询/高级查询，1：导出html报表，2：导出excel报表。
		/*
		 * int firstResult =
		 * (dataGridModel.getPage()-1)*dataGridModel.getRows(); int pageSize =
		 * dataGridModel.getRows();
		 */
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();
		
		//动态归档表后缀拼接
		TeeRequestInfo  requestInfo = TeeRequestInfoContext.getRequestInfo();
		
		String archives = TeeStringUtil.getString(requestInfo.getRequest().get("thread_local_archives"));
		archives = archives.replace("-", "");
		String flow_run_table = ("".equals(archives)?"flow_run":"oaop_archives.flow_run_"+archives);
		String flow_process_table = ("".equals(archives)?"flow_process":"oaop_archives.flow_process_"+archives);
		String flow_run_prcs_table = ("".equals(archives)?"flow_run_prcs":"oaop_archives.flow_run_prcs_"+archives);
		String flow_run_view_table = ("".equals(archives)?"flow_run_view":"oaop_archives.flow_run_view_"+archives);
		String flow_run_concern_table = ("".equals(archives)?"flow_run_concern":"oaop_archives.flow_run_concern_"+archives);
		String flow_type_table = ("".equals(archives)?"flow_type":"oaop_archives.flow_type_"+archives);
		String person_table = ("".equals(archives)?"person":"oaop_archives.person_"+archives);
		String department_table = ("".equals(archives)?"department":"oaop_archives.department_"+archives);

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		List<String> dataTypes = new ArrayList<String>();//进阶查询  表单字段类型
		TeeFlowType ft = null;
		if (flowId != 0) {// 指定流程
			if (sort == null || order == null) {
				sort = "frd.RUN_ID";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "frd.RUN_ID";
			}

			select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME,"+TeeDbUtility.TO_CHAR(dialect, sort)+" as sort1,"
					+ "fr.RUN_ID," + "fr.DEL_FLAG, " + "fr.END_TIME, "
					+ "fr.FORM_VERSION, " + "fr.RUN_NAME, " + "fr.FLOW_ID, "
					+ "fr.FORM_ID,";

			ft = flowTypeDao.load(flowId);
			queryFieldModel = ft.getQueryFieldModel();

			//如果有显示字段，则拼接显示字段值
			if(!"".equals(showFields)){
				String sp[] = TeeStringUtil.parseStringArray(showFields);
				List<TeeFormItem> items = formService
						.getLatestFormItemsByOriginForm(ft.getForm());
				for (TeeFormItem fi : items) {
					for (String tmp : sp) {
						if (tmp.equals(fi.getName())) {
							datas.add(tmp);
							dataTypes.add(fi.getXtype());
							select += "frd." + tmp + ",";
							break;
						}
					}
				}

			}else if (queryFieldModel != null) {
				queryFieldModel = queryFieldModel.substring(1,
						queryFieldModel.length() - 1);
				String sp[] = TeeStringUtil.parseStringArray(queryFieldModel);
				List<TeeFormItem> items = formService
						.getLatestFormItemsByOriginForm(ft.getForm());
				for (TeeFormItem fi : items) {
					for (String tmp : sp) {
						if (tmp.equals(fi.getTitle())) {
							datas.add(fi.getName());
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			
			
			select += "ft.FLOW_NAME ";

			//
			sql.append(" from "+flow_run_table+" fr left outer join "+person_table+" p on p.uuid=fr.begin_person left outer join "+flow_type_table+" ft on ft.SID=fr.FLOW_ID left outer join "+("".equals(archives)?"":"oaop_archives.")+"tee_f_r_d_"
					+ flowId+("".equals(archives)?"":"_"+archives)
					+ " frd on frd.RUN_ID=fr.RUN_ID where fr.DEL_FLAG=0 and fr.IS_SAVE=1 ");
			sql.append(" and fr.FLOW_ID=" + flowId);
		} else {// 全部流程
			if (sort == null || order == null) {
				sort = "RUN_ID";
				order = "desc";
			}
			select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME,"+TeeDbUtility.TO_CHAR(dialect, sort)+" as sort1,"
					+ "fr.DEL_FLAG, " + "fr.END_TIME, " + "fr.FORM_VERSION, "
					+ "fr.RUN_NAME, " + "fr.FLOW_ID, " + "fr.FORM_ID,"
					+ "fr.RUN_ID,ft.FLOW_NAME ";

			sql.append("from "+flow_run_table+" fr left outer join "+person_table+" p on p.uuid=fr.begin_person left outer join "+flow_type_table+" ft on ft.SID=fr.FLOW_ID where fr.DEL_FLAG=0 and fr.IS_SAVE=1 ");

		}

		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			String runNameOper = TeeStringUtil.getString(queryParams
					.get("runNameOper"));
			if ("=".equals(runNameOper)) {
				sql.append(" and fr.RUN_NAME like '" + runName + "'");
			} else if ("<>".equals(runNameOper)) {
				sql.append(" and fr.RUN_NAME not like '" + runName + "'");
			} else if ("like1".equals(runNameOper)) {// 开始于
				sql.append(" and fr.RUN_NAME like '" + runName + "%'");
			} else if ("like2".equals(runNameOper)) {// 包含
				sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
			} else if ("like3".equals(runNameOper)) {// 结束于
				sql.append(" and fr.RUN_NAME like '%" + runName + "'");
			}
		}
		// 拼写时间
		if (start1 != null) {
			sql.append(" and fr.BEGIN_TIME >= :start1");
			Calendar c = Calendar.getInstance();
			c.setTime(start1);
			parameters.put("start1", c);
		}
		if (start2 != null) {
			start2.setHours(23);
			start2.setMinutes(59);
			start2.setSeconds(59);
			Calendar c = Calendar.getInstance();
			c.setTime(start2);
			sql.append(" and fr.BEGIN_TIME <= :start2");
			parameters.put("start2", c);
		}

		// 流程状态
		if (status == 1) {
			sql.append(" and fr.END_TIME is null");
		} else if (status == 2) {
			sql.append(" and fr.END_TIME is not null");
		}

		/**
		 * 查询范围SQL语句组装
		 */

		// 我发起的
		String myBegin = "fr.BEGIN_PERSON=" + loginUser.getUuid() + "";

		// 我经办的
		String myPrcs = "exists (select 1 from "+flow_run_prcs_table+" frp where frp.RUN_ID=fr.RUN_ID and frp.PRCS_USER="
				+ loginUser.getUuid() + ")";

		// 我管理的（这里需要拿到当前人所管理的几个流程和其对应的管理部门串
		StringBuffer myManage = new StringBuffer();
		// 管理权限
		List managePrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "1");
		// 监控权限
		List monitorPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "2");
		// 查询权限
		List searchPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "3");
		// 编辑权限
		List editPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "4");

		Map queryData = null;
		// 所有权限汇总
		List allPrivList = new ArrayList();
		for (int i = 0; i < managePrivList.size(); i++) {
			queryData = (Map) managePrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < monitorPrivList.size(); i++) {
			queryData = (Map) monitorPrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < searchPrivList.size(); i++) {
			queryData = (Map) searchPrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < editPrivList.size(); i++) {
			queryData = (Map) editPrivList.get(i);
			allPrivList.add(queryData);
		}
        //获取真正有权限的集合
		List  realPrivList = new ArrayList();
		Map m=null;
		if (allPrivList.size() != 0) {
			for (int i = 0; i < allPrivList.size(); i++) {
				m = (Map) allPrivList.get(i);
				if (!String.valueOf(m.get("postDeptIds")).equals("")) {// 有权限
					realPrivList.add(m);
				}
			}
		}
		if (realPrivList.size() != 0) {// 有权限
			for (int i = 0; i < realPrivList.size(); i++) {
				queryData = (Map) realPrivList.get(i);
				if (!String.valueOf(queryData.get("postDeptIds")).equals("")) {// 有权限
					myManage.append("(fr.FLOW_ID="
							+ queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData
							.get("postDeptIds"));
					if (!postDeptIds.equals("0")) {// 加入限定部门条件
						if (postDeptIds.endsWith(",")) {
							postDeptIds = postDeptIds.substring(0,
									postDeptIds.length() - 1);
						}
						myManage.append(" and exists (select 1 from "+department_table+" dept where dept.uuid in ("
								+ postDeptIds + ") and p.dept_id=dept.uuid)");
					}
					myManage.append(")");
					if (i != realPrivList.size() - 1) {
						myManage.append(" or ");
					}
				}
			}
		} else {
			myManage.append(" fr.FLOW_ID=0 ");
		}

		// 我关注的
		String myConcern = "exists (select 1 from "+flow_run_concern_table+" frc where frc.RUN_ID=fr.RUN_ID and frc.PERSON_ID="
				+ loginUser.getUuid() + ")";

		// 我查阅的
		String myLookup = "exists (select 1 from "+flow_run_view_table+" frv where frv.VIEW_PERSON="
				+ loginUser.getUuid() + " and frv.RUN_ID=fr.RUN_ID)";

		if (type == 0) {// 所有范围
			sql.append(" and ((" + myBegin + ") or (" + myPrcs + ") ");
			if (myManage.length() != 0) {
				sql.append(" or (" + myManage.toString() + ")");
			}
			sql.append(" or (" + myConcern + ")");
			sql.append(" or (" + myLookup + ")");
			sql.append(")");
		} else if (type == 1) {// 我发起的
			sql.append(" and " + myBegin);
		} else if (type == 2) {// 我经办的
			sql.append(" and " + myPrcs);
		} else if (type == 3) {// 我管理的
			sql.append(" and (" + myManage + ")");
		} else if (type == 4) {// 我关注的
			sql.append(" and " + myConcern);
		} else if (type == 5) {// 我查阅的
			sql.append(" and " + myLookup);
		} else if (type == 6) {// 指定发起人
			sql.append(" and fr.BEGIN_PERSON=" + beginUser + "");
			//判读昂指定的发起人是不是当前登陆人  如果是  则不进行管理权限判断
			if(loginUser.getUuid()!=beginUser){
				if (myManage.length() != 0) {
					sql.append(" and (" + myManage.toString() + ")");
				}
			}
			
		}
		// System.out.println("====="+sql.toString());
		// 条件公式
		String condFormula = TeeStringUtil.getString(queryParams
				.get("condFormula"));

		
		// 拼写DATA相关
		final StringBuffer dataSql = new StringBuffer();
		StringBuffer extraSql = new StringBuffer();
		// 条件项目
		final Map conditionItem = new HashMap();
		Pattern p = Pattern.compile("^DATA_[0-9]+$");
		Matcher mt = null;
		String oper = null;
		String value = null;
		if (flowId != 0) {
			Set<String> key = queryParams.keySet();
			for (String keyName : key) {
				mt = p.matcher(keyName);
				if (mt.find()) {
					oper = TeeStringUtil.getString(queryParams.get(keyName
							+ "_OPER"));
					value = TeeStringUtil.getString(queryParams.get(keyName));
					
					if(value!=null&&!("").equals(value)){
						if ("like1".equals(oper)) {// 开始于
							conditionItem.put(keyName,
									TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
											+ " like '" + value + "%'");
						} else if ("like2".equals(oper)) {// 包含于
							conditionItem.put(keyName,
									TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
											+ " like '%" + value + "%'");
						} else if ("like3".equals(oper)) {// 结束于
							conditionItem.put(keyName,
									TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
											+ " like '%" + value + "'");
						} else {
							// 解决oracle clob字段为空的问题
							if ("".equals(value) && "=".equals(oper)
									&& TeeSysProps.getDialect().equals("oracle")) {
								conditionItem.put(
										keyName,
										TeeDbUtility.TO_CHAR(dialect, "frd."
												+ keyName)
												+ " is null");
							} else {
								conditionItem.put(
										keyName,
										TeeDbUtility.TO_CHAR(dialect, "frd."
												+ keyName)
												+ " " + oper + " '" + value + "'");
							}
					}
						// parameters.put(keyName, queryParams.get(keyName));
						extraSql.append(" and " + conditionItem.get(keyName));
					}	
				}
			}
		}

		if (!"".equals(condFormula)) {// 开启条件公式查询
			TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
			analyser.setText(condFormula);
			String text = analyser.replace(new String[] { "\\[[0-9]+\\]" },
					new TeeExpFetcher() {

						@Override
						public String parse(String pattern) {
							// TODO Auto-generated method stub
							String item = pattern.substring(1,
									pattern.length() - 1);
							String sql = TeeStringUtil.getString(conditionItem
									.get("DATA_" + item));
							if (!"".equals(sql)) {
								return sql;
							}
							return "1=1";
						}

					});
			dataSql.append(" and (" + text + ")");
		} else {// 普通条件查询
			dataSql.append(extraSql);
		}

		sql.append(dataSql);
		
		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));//高级检索SQL
		if(!"".equals(SQL_)){
			sql.append(" and ("+SQL_+")");
		}
		
//		sql.append(" and exists (select 1 from flow_run_ctrl_fb where flow_run_ctrl_fb.run_id=fr.RUN_ID and flow_run_ctrl_fb.item_id=13 and flow_run_ctrl_fb.content like '%2%')");
		
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		sql.append(" order by  sort1 " + order);

		
		SQLQuery query = session.createSQLQuery(select + sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Set<String> keys = parameters.keySet();

		for (String k : keys) {
			query.setParameter(k, parameters.get(k));
			queryCount.setParameter(k, parameters.get(k));
		}
		if (opType == 0) {
			query.setFirstResult((dataGridModel.getPage() - 1)
					* dataGridModel.getRows());
			query.setMaxResults(dataGridModel.getRows());
		}
		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();
		TeeFlowRunConcern concern = null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Set<String> keys0 = null;
		for (Map fr : list) {
			Map map = new HashMap();
			keys0 = fr.keySet();
			for(String key:keys0){
				if(key.startsWith("DATA_")){
					map.put(key, fr.get(key));
				}
			}
			
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			if(fr.get("BEGIN_TIME")!=null){
				map.put("beginTimeDesc",
				sdf.format((Date)fr.get("BEGIN_TIME")));//TeeDateUtil.format((Timestamp) fr.get("BEGIN_TIME"))	
			}else{
				map.put("beginTimeDesc","");
			}
			if(fr.get("END_TIME")!=null){
				map.put("endTimeDesc",
				sdf.format((Date)fr.get("END_TIME")));//TeeDateUtil.format((Timestamp) fr.get("END_TIME"))
				map.put("endFlag",1);
			}else{
				map.put("endTimeDesc","");
				map.put("endFlag",0);
			}
			
			
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("delFlag", fr.get("DEL_FLAG"));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			map.put("flowName", fr.get("FLOW_NAME"));

			// 查询指定发起人
			map.put("prcsUser", beginPerson.getUserName());

			/**
			 * 渲染操作
			 */
			map.put("viewGraph", 1);// 流程图

			int delFlag = TeeStringUtil.getInteger(fr.get("DEL_FLAG"), 0);

			if (delFlag != 1) {
				if (fr.get("END_TIME") == null) {
					// 判断是否有关注
					concern = flowRunConcern.getConcern(
							TeeStringUtil.getInteger(fr.get("RUN_ID"), 0),
							loginUser.getUuid());
					if (concern != null) {
						map.put("cancelConcern", 1);// 取消关注
					} else {
						map.put("concern", 1);// 关注
					}
				}
			}

			boolean hasManagePriv = false;
			boolean hasMonitorPriv = false;
			boolean hasSearchPriv = false;
			boolean hasEditPriv = false;
			// 查找出当前管理权限
			for (int i = 0; i < managePrivList.size(); i++) {
				queryData = (Map) managePrivList.get(i);
				if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
						.getInteger(fr.get("FLOW_ID"), 0)) {
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("")) {// 有权限
						if (!String.valueOf(queryData.get("postDeptIds"))
								.equals("0")) {// 加入限定部门条件
							if (TeeStringUtil.existString(TeeStringUtil
									.parseStringArray(queryData
											.get("postDeptIds")), beginPerson
									.getDept().getUuid() + "")) {
								hasManagePriv = true;
							}
						} else {// 有全部门权限
							hasManagePriv = true;
						}
					}
					break;
				}
			}
			// 查找出当前监控权限
			for (int i = 0; i < monitorPrivList.size(); i++) {
				queryData = (Map) monitorPrivList.get(i);
				if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
						.getInteger(fr.get("FLOW_ID"), 0)) {
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("")) {// 有权限
						if (!String.valueOf(queryData.get("postDeptIds"))
								.equals("0")) {// 加入限定部门条件
							if (TeeStringUtil.existString(TeeStringUtil
									.parseStringArray(queryData
											.get("postDeptIds")), beginPerson
									.getDept().getUuid() + "")) {
								hasMonitorPriv = true;
							}
						} else {// 有全部门权限
							hasMonitorPriv = true;
						}
					}
					break;
				}
			}
			// 查找出当前编辑权限
			for (int i = 0; i < editPrivList.size(); i++) {
				queryData = (Map) editPrivList.get(i);
				if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
						.getInteger(fr.get("FLOW_ID"), 0)) {
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("")) {// 有权限
						if (!String.valueOf(queryData.get("postDeptIds"))
								.equals("0")) {// 加入限定部门条件
							if (TeeStringUtil.existString(TeeStringUtil
									.parseStringArray(queryData
											.get("postDeptIds")), beginPerson
									.getDept().getUuid() + "")) {
								hasEditPriv = true;
							}
						} else {// 有全部门权限
							hasEditPriv = true;
						}
					}
					break;
				}
			}

			if (delFlag != 1) {
				// 管理权限
				if (hasManagePriv) {
					if (fr.get("END_TIME") != null) {// 已结束
							map.put("recover", 1);//恢复 （恢复流程可将任意办理过的节点进行恢复执行）
					} else {
						map.put("end", 1);
					}
					map.put("doDelete", 1);
				}

				// 编辑权限
				if (hasEditPriv) {
					if (fr.get("END_TIME") != null) {// 已结束
						map.put("doEdit", 1);// 编辑权限
					}
				}
			}

			if (fr.get("END_TIME") == null) {// 已结束
				map.put("doUrge", 1);// 催办
			}

			/*
			 * //获取流程的当前不步骤的名称 String
			 * hql="from TeeFlowRunPrcs  prcs where prcs.flowRun.runId="
			 * +(TeeStringUtil.getInteger(fr.get("RUN_ID"),
			 * 0))+" order by prcs.prcsId desc"; List<TeeFlowRunPrcs>
			 * prcsList=simpleDaoSupport.executeQuery(hql, null); TeeFlowRunPrcs
			 * flowRunPrcs=new TeeFlowRunPrcs(); if(prcsList.size()>0){
			 * flowRunPrcs=prcsList.get(0); }
			 */
			if (fr.get("END_TIME") == null) {
				//根据runId 查询flow_run_prcs中满足runId的并且倒序排序的第一条flow_run_prcs的记录
				String h="select prcs.prcs_id as PRCS_ID,fp.PRCS_NAME as PRCS_NAME from "+flow_run_prcs_table+" prcs left outer join "+flow_process_table+" fp on fp.sid=prcs.flow_prcs where prcs.run_id="
						  +(TeeStringUtil.getInteger(fr.get("RUN_ID"),0))+" order by prcs.prcs_Id desc";	
				
				List<Map> prcsList=simpleDaoSupport.executeNativeQuery(h, null, 0, 1);
				Map flowRunPrcs = null;
				if(prcsList!=null && prcsList.size()!=0){
					flowRunPrcs = prcsList.get(0);
				}
				
				
				
				if( !TeeUtility.isNullorEmpty(flowRunPrcs.get("PRCS_NAME"))){
					 
					 map.put("CURRENT_STEP",
								"第" + flowRunPrcs.get("PRCS_ID") + "步："
										+ flowRunPrcs.get("PRCS_NAME"));
				}else{
					map.put("CURRENT_STEP", "第" + flowRunPrcs.get("PRCS_ID") + "步");
				}
				/*if (fr.get("PRCS_NAME") != null
						&& !("").equals(fr.get("PRCS_NAME"))) {
					map.put("CURRENT_STEP",
							"第" + fr.get("PRCS_ID") + "步："
									+ fr.get("PRCS_NAME"));
				} else {
					map.put("CURRENT_STEP", "第" + fr.get("PRCS_ID") + "步");
				}*/

			} else {
				map.put("CURRENT_STEP", "已结束");
			}

			if (ft != null) {
				if(dataTypes.size()==0){//普通查询
					for (String data : datas) {
						map.put(data, TeeStringUtil.clob2String(fr.get(data)));
					}
				}else{//进阶查询
					SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm");
					for(int i=0;i<datas.size();i++){
						if(("xfeedback").equals(dataTypes.get(i))){//会签控件
							//根据runId获取会签控件详情  和  itemId
							int itemId=TeeStringUtil.getInteger(datas.get(i).substring(5,datas.get(i).length()), 0);
							String hql=" from TeeFlowRunCtrlFeedback fb where itemId=? and flowRun.runId=? ";
							List<TeeFlowRunCtrlFeedback> fbList=simpleDaoSupport.executeQuery(hql, new Object[]{itemId,map.get("runId")}); 
							Map mm=null;
							List<Map> mapList=new ArrayList<Map>();
							for (TeeFlowRunCtrlFeedback fb : fbList) {
								mm=new HashMap<String,String>();
								mm.put("content",fb.getContent());
								if(fb.getCreateUser()!=null){
									mm.put("createUserName", fb.getCreateUser().getUserName());
								}else{
									mm.put("createUserName", "");
								}
								if(fb.getCreateTime()!=null){
									mm.put("createTime",sdf1.format(fb.getCreateTime().getTime()));							
								}else{
									mm.put("createTime","");
								}
								mapList.add(mm);
							}
							
							map.put(datas.get(i),mapList);
							map.put(datas.get(i)+"_type","xfeedback");
							
						}else if(("xlist").equals(dataTypes.get(i))){//列表控件
							
							map.put(datas.get(i), TeeStringUtil.clob2String(fr.get(datas.get(i))));
							map.put(datas.get(i)+"_type","xlist");
						}else{
							map.put(datas.get(i), TeeStringUtil.clob2String(fr.get(datas.get(i))));
							map.put(datas.get(i)+"_type","");
						}
					}
					
				}
				
			}

			resultList.add(map);
		}

		json.setRows(resultList);
		json.setTotal(total);

		return json;
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#setFlowPrivService(com.tianee.oa.core.workflow.workmanage.service.TeeFlowPrivServiveInterface)
	 */
	@Override
	public void setFlowPrivService(TeeFlowPrivServiveInterface flowPrivService) {
		this.flowPrivService = flowPrivService;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#setPersonDao(com.tianee.oa.core.org.dao.TeePersonDao)
	 */
	@Override
	public void setPersonDao(TeePersonDao personDao) {
		this.personDao = personDao;
	}



	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#exportExcel(java.util.Map, com.tianee.oa.core.org.bean.TeePerson, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void exportExcel(Map params, TeePerson loginUser,
			HttpServletResponse response) {
		List l = getExcelResultList(params, loginUser);//获取查询结果
		List<String> head=(List<String>) l.get(0);
		List<Map> resultList=(List<Map>) l.get(1);
		//获取当前时间
		Calendar c = Calendar.getInstance(); 
		String time="["+ c.get(Calendar.YEAR)+(c.get(Calendar.MONTH) + 1)+ c.get(Calendar.DAY_OF_MONTH)+c.get(Calendar.HOUR_OF_DAY)+c.get(Calendar.MINUTE)+"]";
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("工作查询列表信息");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 12); // 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("宋体"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			font.setItalic(false); // 是否使用斜体
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setFont(font);
			
			
			HSSFFont font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 12); // 字体高度
			font1.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font1.setFontName("宋体"); // 字体
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // 宽度
			font1.setItalic(false); // 是否使用斜体
			HSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style1.setFont(font1);
			
			
		
			
			
			HSSFCell cell = row.createCell((short) 0);
			
			// 设置表头
			for (int i=0;i<head.size();i++) {
				if(TeeStringUtil.getString(head.get(i)).startsWith("List_")){//列表控件标题
					String value[]=TeeStringUtil.getString(head.get(i)).split("_");
					cell.setCellValue(value[value.length-1]);
				}else{
					cell.setCellValue(TeeStringUtil.getString(head.get(i)));
				}
				
				cell.setCellStyle(style);
				cell = row.createCell((short) (i + 1));
			}
			int maxRows = 0;//每条记录所占的最大行
			int currRows = 1;//目前行数
			// 设置内容
			for (int m = 0; m < resultList.size(); m++) {
				maxRows = 1;//每次都重置maxRows最大行数
				HSSFRow r = sheet.createRow((int) (currRows));
				
			    for(int n=0;n<head.size();n++){
			    	
			    	sheet.autoSizeColumn(n);
			    	
			    	if("xfeedback".equals(resultList.get(m).get("type_"+head.get(n)))){
			    		
			    		List<TeeFlowRunCtrlFeedback> fbList = (List<TeeFlowRunCtrlFeedback>) (resultList.get(m).get(head.get(n)));
			    		
			    		if(maxRows<fbList.size()){//记住该条记录的最大占用行数
			    			maxRows = fbList.size();
			    		}
			    		for(int k=0;k<fbList.size();k++){
			    			if(sheet.getRow((int) (currRows+k))==null){
			    				r = sheet.createRow((int) (currRows+k));
			    			}else{
			    				r = sheet.getRow((int) (currRows+k));
			    			}
				    		cell = r.createCell((short) (n));
				    		cell.setCellStyle(style1);
				    		cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			    			cell.setCellValue(fbList.get(k).getContent()+"["+fbList.get(k).getUserName()+" "+TeeDateUtil.format(fbList.get(k).getCreateTime().getTime(),"yyyy-MM-dd HH:mm")+"]");
			    		}
			    		
			    	}else if("xlist".equals(resultList.get(m).get("type_"+head.get(n)))){
			    		
			    		List data =(List) resultList.get(m).get(head.get(n));
			    		
			    		if(maxRows<data.size()){//记住该条记录的最大占用行数
			    			maxRows = data.size();
			    		}
			    		for(int k=0;k<data.size();k++){
			    			if(sheet.getRow((int) (currRows+k))==null){
			    				r = sheet.createRow((int) (currRows+k));
			    			}else{
			    				r = sheet.getRow((int) (currRows+k));
			    			}
				    		cell = r.createCell((short) (n));
			    			cell.setCellValue(TeeStringUtil.getString(data.get(k)));
			    			cell.setCellStyle(style1);
			    			cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			    		}
			    	
			    		
			    	}else{
			    		cell = r.createCell((short) (n));
			    		cell.setCellStyle(style1);
			    		cell.setCellValue(TeeStringUtil.getString(resultList.get(m).get(head.get(n))));
			    		cell.setCellType(HSSFCell.CELL_TYPE_STRING); 
			    	}			
			    }
			    
			    currRows+=maxRows;
			    
			    //合并单元格
			    for(int n=0;n<head.size();n++){
			    	if(!"xfeedback".equals(resultList.get(m).get("type_"+head.get(n)))&&!"xlist".equals(resultList.get(m).get("type_"+head.get(n)))){
			    		CellRangeAddress cra=new CellRangeAddress(currRows-maxRows, currRows-1, n, n);
			    		sheet.addMergedRegion(cra);
			    	}			
			    }
			    
			}

			// 设置每一列的宽度
			sheet.setDefaultColumnWidth(10);
			String fileName = "列表信息"+time+".xls";
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

/* (non-Javadoc)
 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#getResultList(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
 */
@Override
public List<Map> getResultList(Map queryParams, TeePerson loginUser) {
		Session session = workQueryDao.getSession();
		List l=new ArrayList();
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 1：进行中，2：已办结
		int type = TeeStringUtil.getInteger(queryParams.get("type"), 0);// 0：全部
																		// 1：我发起
																		// 2：我经办
																		// 3：我管理
																		// 4：我关注
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		int beginUser = TeeStringUtil.getInteger(queryParams.get("beginUser"),
				0);// 发起人
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题
		Date start1 = TeeStringUtil.getDate(queryParams.get("start1"),
				"yyyy-MM-dd");// 开始时间段1
		Date start2 = TeeStringUtil.getDate(queryParams.get("start2"),
				"yyyy-MM-dd");// 开始时间段2
		int opType = TeeStringUtil.getInteger(queryParams.get("opType"), 0);// 操作类型，0：查询/高级查询，1：导出html报表，2：导出excel报表。

		// 获取勾选的runIds
		String runIds = TeeStringUtil.getString(queryParams.get("runIds"));

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		List<String> dataTiles = new ArrayList<String>();
		TeeFlowType ft = null;
		List<String> head=new ArrayList<String>();
		if (flowId != 0) {// 指定流程
            
			head.add("流水号");
			head.add("工作名称");
			head.add("所属流程");
			head.add("开始时间");
			head.add("流程发起人");
			head.add("状态");
			head.add("当前步骤");
			
			select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME,"
					+ "fr.RUN_ID," + "fr.DEL_FLAG, " + "fr.END_TIME, "
					+ "fr.FORM_VERSION, " + "fr.RUN_NAME, " + "fr.FLOW_ID, "
					+ "fr.FORM_ID,";

			ft = flowTypeDao.load(flowId);
			queryFieldModel = ft.getQueryFieldModel();

			if (queryFieldModel != null) {
				queryFieldModel = queryFieldModel.substring(1,
						queryFieldModel.length() - 1);
				String sp[] = TeeStringUtil.parseStringArray(queryFieldModel);
				List<TeeFormItem> items = formService
						.getLatestFormItemsByOriginForm(ft.getForm());
				for (TeeFormItem fi : items) {
					for (String tmp : sp) {
						if (tmp.equals(fi.getTitle())) {
							datas.add(fi.getName());
							dataTiles.add(fi.getTitle());
							head.add(fi.getTitle());
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			//
			sql.append(" from FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and fr.IS_SAVE=1 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID");
			sql.append(" and fr.FLOW_ID=" + flowId);
		
			
		} else {// 全部流程

			head.add("流水号");
			head.add("工作名称");
			head.add("所属流程");
			head.add("开始时间");
			head.add("流程发起人");
			head.add("状态");
			head.add("当前步骤");
			
			select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME,"
					+ "fr.DEL_FLAG, " + "fr.END_TIME, " + "fr.FORM_VERSION, "
					+ "fr.RUN_NAME, " + "fr.FLOW_ID, " + "fr.FORM_ID,"
					+ "fr.RUN_ID,ft.FLOW_NAME ";

			sql.append("from FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0  and IS_SAVE=1 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID");

		}

		// 勾选指定的流程
		if (!("0").equals(runIds) && !("").equals(runIds)) {
			sql.append(" and fr.RUN_ID in (" + runIds + ")");
		}

		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			String runNameOper = TeeStringUtil.getString(queryParams
					.get("runNameOper"));
			if ("=".equals(runNameOper)) {
				sql.append(" and fr.RUN_NAME like '" + runName + "'");
			} else if ("<>".equals(runNameOper)) {
				sql.append(" and fr.RUN_NAME not like '" + runName + "'");
			} else if ("like1".equals(runNameOper)) {// 开始于
				sql.append(" and fr.RUN_NAME like '" + runName + "%'");
			} else if ("like2".equals(runNameOper)) {// 包含
				sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
			} else if ("like3".equals(runNameOper)) {// 结束于
				sql.append(" and fr.RUN_NAME like '%" + runName + "'");
			}
		}
		// 拼写时间
		if (start1 != null) {
			sql.append(" and fr.BEGIN_TIME >= :start1");
			Calendar c = Calendar.getInstance();
			c.setTime(start1);
			parameters.put("start1", c);
		}
		if (start2 != null) {
			start2.setHours(23);
			start2.setMinutes(59);
			start2.setSeconds(59);
			Calendar c = Calendar.getInstance();
			c.setTime(start2);
			sql.append(" and fr.BEGIN_TIME <= :start2");
			parameters.put("start2", c);
		}

		// 流程状态
		if (status == 1) {
			sql.append(" and fr.END_TIME is null");
		} else if (status == 2) {
			sql.append(" and fr.END_TIME is not null");
		}

		/**
		 * 查询范围SQL语句组装
		 */

		// 我发起的
		String myBegin = "fr.BEGIN_PERSON=" + loginUser.getUuid() + "";

		// 我经办的
		String myPrcs = "exists (select 1 from FLOW_RUN_PRCS frp where frp.RUN_ID=fr.RUN_ID and frp.PRCS_USER="
				+ loginUser.getUuid() + ")";

		// 我管理的（这里需要拿到当前人所管理的几个流程和其对应的管理部门串
		StringBuffer myManage = new StringBuffer();
		// 管理权限
		List managePrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "1");
		// 监控权限
		List monitorPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "2");
		// 查询权限
		List searchPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "3");
		// 编辑权限
		List editPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "4");

		Map queryData = null;
		// 所有权限汇总
		List allPrivList = new ArrayList();
		for (int i = 0; i < managePrivList.size(); i++) {
			queryData = (Map) managePrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < monitorPrivList.size(); i++) {
			queryData = (Map) monitorPrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < searchPrivList.size(); i++) {
			queryData = (Map) searchPrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < editPrivList.size(); i++) {
			queryData = (Map) editPrivList.get(i);
			allPrivList.add(queryData);
		}

		
		//获取真正有权限的集合
		List  realPrivList = new ArrayList();
		Map m1=null;
		if (allPrivList.size() != 0) {
			for (int i = 0; i < allPrivList.size(); i++) {
				m1 = (Map) allPrivList.get(i);
				if (!String.valueOf(m1.get("postDeptIds")).equals("")) {// 有权限
					realPrivList.add(m1);
				}
			}
		}
		
		
		if (realPrivList.size() != 0) {// 有权限
			for (int i = 0; i < realPrivList.size(); i++) {
				queryData = (Map) realPrivList.get(i);
				if (!String.valueOf(queryData.get("postDeptIds")).equals("")) {// 有权限
					myManage.append("(fr.FLOW_ID="
							+ queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData
							.get("postDeptIds"));
					if (!postDeptIds.equals("0")) {// 加入限定部门条件
						if (postDeptIds.endsWith(",")) {
							postDeptIds = postDeptIds.substring(0,
									postDeptIds.length() - 1);
						}
						myManage.append(" and exists (select 1 from DEPARTMENT dept where dept.uuid in ("
								+ postDeptIds + ") and p.dept_id=dept.uuid)");
					}
					myManage.append(")");
					if (i != realPrivList.size() - 1) {
						myManage.append(" or ");
					}
				}
			}
		} else {
			myManage.append(" fr.FLOW_ID=0 ");
		}

		// 我关注的
		String myConcern = "exists (select 1 from FLOW_RUN_CONCERN frc where frc.RUN_ID=fr.RUN_ID and frc.PERSON_ID="
				+ loginUser.getUuid() + ")";

		// 我查阅的
		String myLookup = "exists (select 1 from FLOW_RUN_VIEW frv where frv.VIEW_PERSON="
				+ loginUser.getUuid() + " and frv.RUN_ID=fr.RUN_ID)";

		if (type == 0) {// 所有范围
			sql.append(" and ((" + myBegin + ") or (" + myPrcs + ") ");
			if (myManage.length() != 0) {
				sql.append(" or (" + myManage.toString() + ")");
			}
			sql.append(" or (" + myConcern + ")");
			sql.append(" or (" + myLookup + ")");
			sql.append(")");
		} else if (type == 1) {// 我发起的
			sql.append(" and " + myBegin);
		} else if (type == 2) {// 我经办的
			sql.append(" and " + myPrcs);
		} else if (type == 3) {// 我管理的
			sql.append(" and (" + myManage + ")");
		} else if (type == 4) {// 我关注的
			sql.append(" and " + myConcern);
		} else if (type == 5) {// 我查阅的
			sql.append(" and " + myLookup);
		} else if (type == 6) {// 指定发起人
			sql.append(" and fr.BEGIN_PERSON=" + beginUser + "");
			if (myManage.length() != 0) {
				sql.append(" and (" + myManage.toString() + ")");
			}
		}
		// System.out.println(sql.toString());
		// 条件公式
		String condFormula = TeeStringUtil.getString(queryParams
				.get("condFormula"));

		String dialect = TeeSysProps.getString("dialect");
		// 拼写DATA相关
		final StringBuffer dataSql = new StringBuffer();
		StringBuffer extraSql = new StringBuffer();
		// 条件项目
		final Map conditionItem = new HashMap();
		Pattern p = Pattern.compile("^DATA_[0-9]+$");
		Matcher mt = null;
		String oper = null;
		String value = null;
		if (flowId != 0) {
			Set<String> key = queryParams.keySet();
			for (String keyName : key) {
				mt = p.matcher(keyName);
				if (mt.find()) {
					oper = TeeStringUtil.getString(queryParams.get(keyName
							+ "_OPER"));
					value = TeeStringUtil.getString(queryParams.get(keyName));
					if ("like1".equals(oper)) {// 开始于
						conditionItem.put(keyName,
								TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
										+ " like '" + value + "%'");
					} else if ("like2".equals(oper)) {// 包含于
						conditionItem.put(keyName,
								TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
										+ " like '%" + value + "%'");
					} else if ("like3".equals(oper)) {// 结束于
						conditionItem.put(keyName,
								TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
										+ " like '%" + value + "'");
					} else {
						// 解决oracle clob字段为空的问题
						if ("".equals(value) && "=".equals(oper)
								&& TeeSysProps.getDialect().equals("oracle")) {
							conditionItem.put(
									keyName,
									TeeDbUtility.TO_CHAR(dialect, "frd."
											+ keyName)
											+ " is null");
						} else {
							conditionItem.put(
									keyName,
									TeeDbUtility.TO_CHAR(dialect, "frd."
											+ keyName)
											+ " " + oper + " '" + value + "'");
						}
						// parameters.put(keyName, queryParams.get(keyName));
					}
					extraSql.append(" and " + conditionItem.get(keyName));
				}
			}
		}

		if (!"".equals(condFormula)) {// 开启条件公式查询
			TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
			analyser.setText(condFormula);
			String text = analyser.replace(new String[] { "\\[[0-9]+\\]" },
					new TeeExpFetcher() {

						@Override
						public String parse(String pattern) {
							// TODO Auto-generated method stub
							String item = pattern.substring(1,
									pattern.length() - 1);
							String sql = TeeStringUtil.getString(conditionItem
									.get("DATA_" + item));
							if (!"".equals(sql)) {
								return sql;
							}
							return "1=1";
						}

					});
			dataSql.append(" and (" + text + ")");
		} else {// 普通条件查询
			dataSql.append(extraSql);
		}

		sql.append(dataSql);
		
		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));//SQL
		if(!"".equals(SQL_)){
			sql.append(" and ("+SQL_+")");
		}
		
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		sql.append(" order by fr.run_id desc");

		SQLQuery query = session.createSQLQuery(select + sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Set<String> keys = parameters.keySet();

		for (String k : keys) {
			query.setParameter(k, parameters.get(k));
			queryCount.setParameter(k, parameters.get(k));
		}
		/*
		 * if(opType == 0){
		 * query.setFirstResult((dataGridModel.getPage()-1)*dataGridModel
		 * .getRows()); query.setMaxResults(dataGridModel.getRows()); }
		 */
		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();
		TeeFlowRunConcern concern = null;
		for (Map fr : list) {
			Map map = new HashMap();
			map.put("流水号", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			
			
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			
			/*map.put("endTimeDesc",
					TeeDateUtil.format((Timestamp) fr.get("END_TIME")));*/
			map.put("工作名称", TeeStringUtil.getString(fr.get("RUN_NAME")));
			
			
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("delFlag", fr.get("DEL_FLAG"));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			map.put("所属流程", fr.get("FLOW_NAME"));
					
			
			map.put("开始时间",
					TeeDateUtil.format((Timestamp) fr.get("BEGIN_TIME")));
			

			// 查询指定发起人
			map.put("流程发起人", beginPerson.getUserName());
			
			
			
			//状态
			if(TeeDateUtil.format((Timestamp) fr.get("END_TIME"))!=null&&TeeDateUtil.format((Timestamp) fr.get("END_TIME"))!=""){
				map.put("状态","已结束");
			}else{
				map.put("状态","执行中");
			}

			/**
			 * 渲染操作
			 */
			map.put("viewGraph", 1);// 流程图

			int delFlag = TeeStringUtil.getInteger(fr.get("DEL_FLAG"), 0);

			if (delFlag != 1) {
				if (fr.get("END_TIME") == null) {
					// 判断是否有关注
					concern = flowRunConcern.getConcern(
							TeeStringUtil.getInteger(fr.get("RUN_ID"), 0),
							loginUser.getUuid());
					if (concern != null) {
						map.put("cancelConcern", 1);// 取消关注
					} else {
						map.put("concern", 1);// 关注
					}
				}
			}

			boolean hasManagePriv = false;
			boolean hasMonitorPriv = false;
			boolean hasSearchPriv = false;
			boolean hasEditPriv = false;
			// 查找出当前管理权限
			for (int i = 0; i < managePrivList.size(); i++) {
				queryData = (Map) managePrivList.get(i);
				if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
						.getInteger(fr.get("FLOW_ID"), 0)) {
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("")) {// 有权限
						if (!String.valueOf(queryData.get("postDeptIds"))
								.equals("0")) {// 加入限定部门条件
							if (TeeStringUtil.existString(TeeStringUtil
									.parseStringArray(queryData
											.get("postDeptIds")), beginPerson
									.getDept().getUuid() + "")) {
								hasManagePriv = true;
							}
						} else {// 有全部门权限
							hasManagePriv = true;
						}
					}
					break;
				}
			}
			// 查找出当前监控权限
			for (int i = 0; i < monitorPrivList.size(); i++) {
				queryData = (Map) monitorPrivList.get(i);
				if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
						.getInteger(fr.get("FLOW_ID"), 0)) {
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("")) {// 有权限
						if (!String.valueOf(queryData.get("postDeptIds"))
								.equals("0")) {// 加入限定部门条件
							if (TeeStringUtil.existString(TeeStringUtil
									.parseStringArray(queryData
											.get("postDeptIds")), beginPerson
									.getDept().getUuid() + "")) {
								hasMonitorPriv = true;
							}
						} else {// 有全部门权限
							hasMonitorPriv = true;
						}
					}
					break;
				}
			}
			// 查找出当前编辑权限
			for (int i = 0; i < editPrivList.size(); i++) {
				queryData = (Map) editPrivList.get(i);
				if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
						.getInteger(fr.get("FLOW_ID"), 0)) {
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("")) {// 有权限
						if (!String.valueOf(queryData.get("postDeptIds"))
								.equals("0")) {// 加入限定部门条件
							if (TeeStringUtil.existString(TeeStringUtil
									.parseStringArray(queryData
											.get("postDeptIds")), beginPerson
									.getDept().getUuid() + "")) {
								hasEditPriv = true;
							}
						} else {// 有全部门权限
							hasEditPriv = true;
						}
					}
					break;
				}
			}

			if (delFlag != 1) {
				// 管理权限
				if (hasManagePriv) {
					if (fr.get("END_TIME") != null) {// 已结束
						boolean hasExistUnhandledWorks = flowRunPrcsDao
								.hasExsitUnhandedWorks(TeeStringUtil
										.getInteger(fr.get("RUN_ID"), 0));
						if (hasExistUnhandledWorks) {// 存在未办理完成的工作，则可以进行恢复
							map.put("recover", 1);
						}
					} else {
						map.put("end", 1);
					}
					map.put("doDelete", 1);
				}

				// 编辑权限
				if (hasEditPriv) {
					if (fr.get("END_TIME") != null) {// 已结束
						map.put("doEdit", 1);// 编辑权限
					}
				}
			}

			if (fr.get("END_TIME") == null) {// 已结束
				map.put("doUrge", 1);// 催办
			}
			// 获取流程的当前的步骤
			if (fr.get("END_TIME") == null) {
				//根据runId 查询flow_run_prcs中满足runId的并且倒序排序的第一条flow_run_prcs的记录
				String h="from TeeFlowRunPrcs  prcs where prcs.flowRun.runId="
						  +(TeeStringUtil.getInteger(fr.get("RUN_ID"),0))+" order by prcs.prcsId desc";				
				List<TeeFlowRunPrcs> prcsList=simpleDaoSupport.pageFind(h, 0, 1, null);
				TeeFlowRunPrcs flowRunPrcs=new TeeFlowRunPrcs();
				if(prcsList.size()>0){
					 flowRunPrcs=prcsList.get(0);
				}
				
				
				
				if( !TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs())&&!TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs().getPrcsName())){
					 
					 map.put("当前步骤",
								"第" + flowRunPrcs.getPrcsId() + "步："
										+ flowRunPrcs.getFlowPrcs().getPrcsName());
				}else{
					map.put("当前步骤", "第" + flowRunPrcs.getPrcsId() + "步");
				}

			} else {
				map.put("当前步骤", "已结束");
			}

			if (ft != null) {
				for (int i=0;i<datas.size();i++) {	
					map.put(dataTiles.get(i), TeeStringUtil.clob2String(fr.get(datas.get(i))));
				}
			}
			resultList.add(map);
		}
		
		l.add(head);
		l.add(resultList);
		return l;
	}




/* (non-Javadoc)
 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#getExcelResultList(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
 */
@Override
public List<Map> getExcelResultList(Map queryParams, TeePerson loginUser) {
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	Session session = workQueryDao.getSession();
	List l=new ArrayList();
	
	Map<String, Object> parameters = new HashMap<String, Object>();
	// 获取查询条件
	int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
	int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 1：进行中，2：已办结
	int type = TeeStringUtil.getInteger(queryParams.get("type"), 0);// 0：全部
																	// 1：我发起
																	// 2：我经办
																	// 3：我管理
																	// 4：我关注
	int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
	int beginUser = TeeStringUtil.getInteger(queryParams.get("beginUser"),
			0);// 发起人
	String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题
	Date start1 = TeeStringUtil.getDate(queryParams.get("start1"),
			"yyyy-MM-dd");// 开始时间段1
	Date start2 = TeeStringUtil.getDate(queryParams.get("start2"),
			"yyyy-MM-dd");// 开始时间段2
	int opType = TeeStringUtil.getInteger(queryParams.get("opType"), 0);// 操作类型，0：查询/高级查询，1：导出html报表，2：导出excel报表。

	// 获取勾选的runIds
	String runIds = TeeStringUtil.getString(queryParams.get("runIds"));

	String selectCount = "select count(*) ";

	// 查询字段模型
	String queryFieldModel = null;

	// 声明查询语句
	StringBuffer sql = new StringBuffer();
	String select = "";
	
	List<String> datas = new ArrayList<String>();//自定义显示字段   DATA_1,DATA_2
	List<String> dataTiles = new ArrayList<String>();//自定义显示字段名称   会签，列表
	List<String> dataType = new ArrayList<String>();//自定义显示字段类型    
	List<String> dataModel = new ArrayList<String>();//列表控件model
	
	TeeFlowType ft = null;
	List<String> head=new ArrayList<String>();
	if (flowId != 0) {// 指定流程
        
		head.add("流水号");
		head.add("工作名称");
		head.add("所属流程");
		head.add("开始时间");
		head.add("流程发起人");
		head.add("状态");
		head.add("当前步骤");
		
		select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME,"
				+ "fr.RUN_ID," + "fr.DEL_FLAG, " + "fr.END_TIME, "
				+ "fr.FORM_VERSION, " + "fr.RUN_NAME, " + "fr.FLOW_ID, "
				+ "fr.FORM_ID,";

		ft = flowTypeDao.load(flowId);
		queryFieldModel = ft.getQueryFieldModel();

		if (queryFieldModel != null) {
			queryFieldModel = queryFieldModel.substring(1,
					queryFieldModel.length() - 1);
			String sp[] = TeeStringUtil.parseStringArray(queryFieldModel);
			List<TeeFormItem> items = formService
					.getLatestFormItemsByOriginForm(ft.getForm());
			for (String tmp : sp) {
				for (TeeFormItem fi : items) {
					if (tmp.equals(fi.getTitle())) {
						datas.add(fi.getName());
						dataType.add(fi.getXtype());
						dataModel.add(fi.getModel());
						select += "frd." + fi.getName() + ",";
						if(("xlist").equals(fi.getXtype())){//列表控件
							List<Map<String, String>> modelList=TeeJsonUtil.JsonStr2MapList(fi.getModel());
							if(modelList!=null&&modelList.size()>0){
								for (Map<String, String> map : modelList) {
									head.add("List_"+fi.getName()+"_"+map.get("title"));
									dataTiles.add("List_"+fi.getName()+"_"+map.get("title"));
								}
							}
							
						}else{//普通控件
							head.add(fi.getTitle());
							dataTiles.add(fi.getTitle());
						}
						
						break;
					}
				}
			}
		}
		select += "ft.FLOW_NAME ";

		//
		sql.append(" from FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
				+ flowId
				+ " frd where fr.DEL_FLAG=0 and fr.IS_SAVE=1 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID");
		sql.append(" and fr.FLOW_ID=" + flowId);
	
		
	} else {// 全部流程

		head.add("流水号");
		head.add("工作名称");
		head.add("所属流程");
		head.add("开始时间");
		head.add("流程发起人");
		head.add("状态");
		head.add("当前步骤");
		
		select = "select fr.BEGIN_PERSON," + "fr.BEGIN_TIME,"
				+ "fr.DEL_FLAG, " + "fr.END_TIME, " + "fr.FORM_VERSION, "
				+ "fr.RUN_NAME, " + "fr.FLOW_ID, " + "fr.FORM_ID,"
				+ "fr.RUN_ID,ft.FLOW_NAME ";

		sql.append("from FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0  and IS_SAVE=1 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID");

	}

	// 勾选指定的流程
	if (!("0").equals(runIds) && !("").equals(runIds)) {
		sql.append(" and fr.RUN_ID in (" + runIds + ")");
	}

	if (runId != 0) {
		sql.append(" and fr.RUN_ID=" + runId);
	}
	if (!"".equals(runName)) {
		String runNameOper = TeeStringUtil.getString(queryParams
				.get("runNameOper"));
		if ("=".equals(runNameOper)) {
			sql.append(" and fr.RUN_NAME like '" + runName + "'");
		} else if ("<>".equals(runNameOper)) {
			sql.append(" and fr.RUN_NAME not like '" + runName + "'");
		} else if ("like1".equals(runNameOper)) {// 开始于
			sql.append(" and fr.RUN_NAME like '" + runName + "%'");
		} else if ("like2".equals(runNameOper)) {// 包含
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		} else if ("like3".equals(runNameOper)) {// 结束于
			sql.append(" and fr.RUN_NAME like '%" + runName + "'");
		}
	}
	// 拼写时间
	if (start1 != null) {
		sql.append(" and fr.BEGIN_TIME >= :start1");
		Calendar c = Calendar.getInstance();
		c.setTime(start1);
		parameters.put("start1", c);
	}
	if (start2 != null) {
		start2.setHours(23);
		start2.setMinutes(59);
		start2.setSeconds(59);
		Calendar c = Calendar.getInstance();
		c.setTime(start2);
		sql.append(" and fr.BEGIN_TIME <= :start2");
		parameters.put("start2", c);
	}

	// 流程状态
	if (status == 1) {
		sql.append(" and fr.END_TIME is null");
	} else if (status == 2) {
		sql.append(" and fr.END_TIME is not null");
	}

	/**
	 * 查询范围SQL语句组装
	 */

	// 我发起的
	String myBegin = "fr.BEGIN_PERSON=" + loginUser.getUuid() + "";

	// 我经办的
	String myPrcs = "exists (select 1 from FLOW_RUN_PRCS frp where frp.RUN_ID=fr.RUN_ID and frp.PRCS_USER="
			+ loginUser.getUuid() + ")";

	// 我管理的（这里需要拿到当前人所管理的几个流程和其对应的管理部门串
	StringBuffer myManage = new StringBuffer();
	// 管理权限
	List managePrivList = flowPrivService.getQueryPostDeptsByAllFlow(
			loginUser, "1");
	// 监控权限
	List monitorPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
			loginUser, "2");
	// 查询权限
	List searchPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
			loginUser, "3");
	// 编辑权限
	List editPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
			loginUser, "4");

	Map queryData = null;
	// 所有权限汇总
	List allPrivList = new ArrayList();
	for (int i = 0; i < managePrivList.size(); i++) {
		queryData = (Map) managePrivList.get(i);
		allPrivList.add(queryData);
	}
	for (int i = 0; i < monitorPrivList.size(); i++) {
		queryData = (Map) monitorPrivList.get(i);
		allPrivList.add(queryData);
	}
	for (int i = 0; i < searchPrivList.size(); i++) {
		queryData = (Map) searchPrivList.get(i);
		allPrivList.add(queryData);
	}
	for (int i = 0; i < editPrivList.size(); i++) {
		queryData = (Map) editPrivList.get(i);
		allPrivList.add(queryData);
	}

	if (allPrivList.size() != 0) {// 有权限
		for (int i = 0; i < allPrivList.size(); i++) {
			queryData = (Map) allPrivList.get(i);
			if (!String.valueOf(queryData.get("postDeptIds")).equals("")) {// 有权限
				myManage.append("(fr.FLOW_ID="
						+ queryData.get("flowTypeId"));
				String postDeptIds = String.valueOf(queryData
						.get("postDeptIds"));
				if (!postDeptIds.equals("0")) {// 加入限定部门条件
					if (postDeptIds.endsWith(",")) {
						postDeptIds = postDeptIds.substring(0,
								postDeptIds.length() - 1);
					}
					myManage.append(" and exists (select 1 from DEPARTMENT dept where dept.uuid in ("
							+ postDeptIds + ") and p.dept_id=dept.uuid)");
				}
				myManage.append(")");
				if (i != allPrivList.size() - 1) {
					myManage.append(" or ");
				}
			}
		}
	} else {
		myManage.append(" fr.FLOW_ID=0 ");
	}

	// 我关注的
	String myConcern = "exists (select 1 from FLOW_RUN_CONCERN frc where frc.RUN_ID=fr.RUN_ID and frc.PERSON_ID="
			+ loginUser.getUuid() + ")";

	// 我查阅的
	String myLookup = "exists (select 1 from FLOW_RUN_VIEW frv where frv.VIEW_PERSON="
			+ loginUser.getUuid() + " and frv.RUN_ID=fr.RUN_ID)";

	if (type == 0) {// 所有范围
		sql.append(" and ((" + myBegin + ") or (" + myPrcs + ") ");
		if (myManage.length() != 0) {
			sql.append(" or (" + myManage.toString() + ")");
		}
		sql.append(" or (" + myConcern + ")");
		sql.append(" or (" + myLookup + ")");
		sql.append(")");
	} else if (type == 1) {// 我发起的
		sql.append(" and " + myBegin);
	} else if (type == 2) {// 我经办的
		sql.append(" and " + myPrcs);
	} else if (type == 3) {// 我管理的
		sql.append(" and (" + myManage + ")");
	} else if (type == 4) {// 我关注的
		sql.append(" and " + myConcern);
	} else if (type == 5) {// 我查阅的
		sql.append(" and " + myLookup);
	} else if (type == 6) {// 指定发起人
		sql.append(" and fr.BEGIN_PERSON=" + beginUser + "");
		if (myManage.length() != 0) {
			sql.append(" and (" + myManage.toString() + ")");
		}
	}
	// System.out.println(sql.toString());
	// 条件公式
	String condFormula = TeeStringUtil.getString(queryParams
			.get("condFormula"));

	String dialect = TeeSysProps.getString("dialect");
	// 拼写DATA相关
	final StringBuffer dataSql = new StringBuffer();
	StringBuffer extraSql = new StringBuffer();
	// 条件项目
	final Map conditionItem = new HashMap();
	Pattern p = Pattern.compile("^DATA_[0-9]+$");
	Matcher mt = null;
	String oper = null;
	String value = null;
	if (flowId != 0) {
		Set<String> key = queryParams.keySet();
		for (String keyName : key) {
			mt = p.matcher(keyName);
			if (mt.find()) {
				oper = TeeStringUtil.getString(queryParams.get(keyName
						+ "_OPER"));
				value = TeeStringUtil.getString(queryParams.get(keyName));
				if ("like1".equals(oper)) {// 开始于
					conditionItem.put(keyName,
							TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
									+ " like '" + value + "%'");
				} else if ("like2".equals(oper)) {// 包含于
					conditionItem.put(keyName,
							TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
									+ " like '%" + value + "%'");
				} else if ("like3".equals(oper)) {// 结束于
					conditionItem.put(keyName,
							TeeDbUtility.TO_CHAR(dialect, "frd." + keyName)
									+ " like '%" + value + "'");
				} else {
					// 解决oracle clob字段为空的问题
					if ("".equals(value) && "=".equals(oper)
							&& TeeSysProps.getDialect().equals("oracle")) {
						conditionItem.put(
								keyName,
								TeeDbUtility.TO_CHAR(dialect, "frd."
										+ keyName)
										+ " is null");
					} else {
						conditionItem.put(
								keyName,
								TeeDbUtility.TO_CHAR(dialect, "frd."
										+ keyName)
										+ " " + oper + " '" + value + "'");
					}
					// parameters.put(keyName, queryParams.get(keyName));
				}
				extraSql.append(" and " + conditionItem.get(keyName));
			}
		}
	}

	if (!"".equals(condFormula)) {// 开启条件公式查询
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser();
		analyser.setText(condFormula);
		String text = analyser.replace(new String[] { "\\[[0-9]+\\]" },
				new TeeExpFetcher() {

					@Override
					public String parse(String pattern) {
						// TODO Auto-generated method stub
						String item = pattern.substring(1,
								pattern.length() - 1);
						String sql = TeeStringUtil.getString(conditionItem
								.get("DATA_" + item));
						if (!"".equals(sql)) {
							return sql;
						}
						return "1=1";
					}

				});
		dataSql.append(" and (" + text + ")");
	} else {// 普通条件查询
		dataSql.append(extraSql);
	}

	sql.append(dataSql);
	
	String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));//SQL
	if(!"".equals(SQL_)){
		sql.append(" and ("+SQL_+")");
	}
	
	SQLQuery queryCount = session.createSQLQuery(selectCount
			+ sql.toString());
	sql.append(" order by fr.run_id desc");

	SQLQuery query = session.createSQLQuery(select + sql.toString());
	query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	Set<String> keys = parameters.keySet();

	for (String k : keys) {
		query.setParameter(k, parameters.get(k));
		queryCount.setParameter(k, parameters.get(k));
	}
	/*
	 * if(opType == 0){
	 * query.setFirstResult((dataGridModel.getPage()-1)*dataGridModel
	 * .getRows()); query.setMaxResults(dataGridModel.getRows()); }
	 */
	List<Map> list = query.list();
	long total = Long.parseLong(queryCount.uniqueResult().toString());

	TeePerson beginPerson = null;
	List<Map> resultList = new ArrayList<Map>();
	TeeFlowRunConcern concern = null;
	for (Map fr : list) {
		Map map = new HashMap();
		map.put("流水号", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
		
		
		map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
		
		/*map.put("endTimeDesc",
				TeeDateUtil.format((Timestamp) fr.get("END_TIME")));*/
		map.put("工作名称", TeeStringUtil.getString(fr.get("RUN_NAME")));
		
		
		map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
		map.put("delFlag", fr.get("DEL_FLAG"));

		beginPerson = personDao.load(TeeStringUtil.getInteger(
				fr.get("BEGIN_PERSON"), 0));
		map.put("所属流程", fr.get("FLOW_NAME"));
				
		
		map.put("开始时间",
				TeeDateUtil.format((Timestamp) fr.get("BEGIN_TIME")));
		

		// 查询指定发起人
		map.put("流程发起人", beginPerson.getUserName());
		
		
		
		//状态
		if(TeeDateUtil.format((Timestamp) fr.get("END_TIME"))!=null&&TeeDateUtil.format((Timestamp) fr.get("END_TIME"))!=""){
			map.put("状态","已结束");
		}else{
			map.put("状态","执行中");
		}

		/**
		 * 渲染操作
		 */
		map.put("viewGraph", 1);// 流程图

		int delFlag = TeeStringUtil.getInteger(fr.get("DEL_FLAG"), 0);

		if (delFlag != 1) {
			if (fr.get("END_TIME") == null) {
				// 判断是否有关注
				concern = flowRunConcern.getConcern(
						TeeStringUtil.getInteger(fr.get("RUN_ID"), 0),
						loginUser.getUuid());
				if (concern != null) {
					map.put("cancelConcern", 1);// 取消关注
				} else {
					map.put("concern", 1);// 关注
				}
			}
		}

		boolean hasManagePriv = false;
		boolean hasMonitorPriv = false;
		boolean hasSearchPriv = false;
		boolean hasEditPriv = false;
		// 查找出当前管理权限
		for (int i = 0; i < managePrivList.size(); i++) {
			queryData = (Map) managePrivList.get(i);
			if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
					.getInteger(fr.get("FLOW_ID"), 0)) {
				if (!String.valueOf(queryData.get("postDeptIds"))
						.equals("")) {// 有权限
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("0")) {// 加入限定部门条件
						if (TeeStringUtil.existString(TeeStringUtil
								.parseStringArray(queryData
										.get("postDeptIds")), beginPerson
								.getDept().getUuid() + "")) {
							hasManagePriv = true;
						}
					} else {// 有全部门权限
						hasManagePriv = true;
					}
				}
				break;
			}
		}
		// 查找出当前监控权限
		for (int i = 0; i < monitorPrivList.size(); i++) {
			queryData = (Map) monitorPrivList.get(i);
			if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
					.getInteger(fr.get("FLOW_ID"), 0)) {
				if (!String.valueOf(queryData.get("postDeptIds"))
						.equals("")) {// 有权限
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("0")) {// 加入限定部门条件
						if (TeeStringUtil.existString(TeeStringUtil
								.parseStringArray(queryData
										.get("postDeptIds")), beginPerson
								.getDept().getUuid() + "")) {
							hasMonitorPriv = true;
						}
					} else {// 有全部门权限
						hasMonitorPriv = true;
					}
				}
				break;
			}
		}
		// 查找出当前编辑权限
		for (int i = 0; i < editPrivList.size(); i++) {
			queryData = (Map) editPrivList.get(i);
			if (TeeStringUtil.getInteger(queryData.get("flowTypeId"), 0) == TeeStringUtil
					.getInteger(fr.get("FLOW_ID"), 0)) {
				if (!String.valueOf(queryData.get("postDeptIds"))
						.equals("")) {// 有权限
					if (!String.valueOf(queryData.get("postDeptIds"))
							.equals("0")) {// 加入限定部门条件
						if (TeeStringUtil.existString(TeeStringUtil
								.parseStringArray(queryData
										.get("postDeptIds")), beginPerson
								.getDept().getUuid() + "")) {
							hasEditPriv = true;
						}
					} else {// 有全部门权限
						hasEditPriv = true;
					}
				}
				break;
			}
		}

		if (delFlag != 1) {
			// 管理权限
			if (hasManagePriv) {
				if (fr.get("END_TIME") != null) {// 已结束
					boolean hasExistUnhandledWorks = flowRunPrcsDao
							.hasExsitUnhandedWorks(TeeStringUtil
									.getInteger(fr.get("RUN_ID"), 0));
					if (hasExistUnhandledWorks) {// 存在未办理完成的工作，则可以进行恢复
						map.put("recover", 1);
					}
				} else {
					map.put("end", 1);
				}
				map.put("doDelete", 1);
			}

			// 编辑权限
			if (hasEditPriv) {
				if (fr.get("END_TIME") != null) {// 已结束
					map.put("doEdit", 1);// 编辑权限
				}
			}
		}

		if (fr.get("END_TIME") == null) {// 已结束
			map.put("doUrge", 1);// 催办
		}
		// 获取流程的当前的步骤
		if (fr.get("END_TIME") == null) {
			//根据runId 查询flow_run_prcs中满足runId的并且倒序排序的第一条flow_run_prcs的记录
			String h="from TeeFlowRunPrcs  prcs where prcs.flowRun.runId="
					  +(TeeStringUtil.getInteger(fr.get("RUN_ID"),0))+" order by prcs.prcsId desc";				
			List<TeeFlowRunPrcs> prcsList=simpleDaoSupport.pageFind(h, 0, 1, null);
			TeeFlowRunPrcs flowRunPrcs=new TeeFlowRunPrcs();
			if(prcsList.size()>0){
				 flowRunPrcs=prcsList.get(0);
			}
			
			
			
			if( !TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs())&&!TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs().getPrcsName())){
				 
				 map.put("当前步骤",
							"第" + flowRunPrcs.getPrcsId() + "步："
									+ flowRunPrcs.getFlowPrcs().getPrcsName());
			}else{
				map.put("当前步骤", "第" + flowRunPrcs.getPrcsId() + "步");
			}

		} else {
			map.put("当前步骤", "已结束");
		}

		
		if (ft != null) {
			for (int i=0;i<datas.size();i++) {
				//判断类型
				if(("xlist").equals(dataType.get(i))){//列表控件
					List<String>headList=new ArrayList<String>();
					
					List<Map<String,String>> datas0 = TeeJsonUtil.JsonStr2MapList(TeeStringUtil.clob2String(fr.get(datas.get(i))));
					
				
					//获取当前列表控件的model模型
					List<Map<String, String>> modelList=TeeJsonUtil.JsonStr2MapList(dataModel.get(i));
					if(modelList!=null&&modelList.size()>0){
						for (Map<String, String> m : modelList) {
							headList.add(m.get("title"));
						}
					}
					
					
					List list0=null;
					if(headList!=null&&headList.size()>0){
						for (int n=0;n<headList.size();n++) {
							list0=new ArrayList();
							for (int m = 0; m < datas0.size(); m++) {
								list0.add(datas0.get(m).get(headList.get(n)));
							}
							map.put("List_"+datas.get(i)+"_"+headList.get(n), list0);
							map.put("type_List_"+datas.get(i)+"_"+headList.get(n),"xlist");
						}
					}	
					
				}else if(("xfeedback").equals(dataType.get(i))){//会签控件
				    //根据runId获取会签控件详情  和  itemId
					int itemId=TeeStringUtil.getInteger(datas.get(i).substring(5,datas.get(i).length()), 0);
					String hql=" from TeeFlowRunCtrlFeedback fb where itemId=? and flowRun.runId=? ";
					List<TeeFlowRunCtrlFeedback> fbList=simpleDaoSupport.executeQuery(hql, new Object[]{itemId,map.get("流水号")}); 
					map.put(dataTiles.get(i), fbList);
					map.put("type_"+dataTiles.get(i), "xfeedback");
				}else{
					map.put(dataTiles.get(i), TeeStringUtil.clob2String(fr.get(datas.get(i))));
					map.put("type_"+dataTiles.get(i), "");
				}
				
			}
		}
		
		resultList.add(map);
	}
	
	l.add(head);
	l.add(resultList);
	l.add(flowId);
	return l;
}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.workmanage.service.TeeWorkQueryServiceInterface#getHasQueryPrivFlowTypeIds(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public TeeJson getHasQueryPrivFlowTypeIds(Map queryParams, TeePerson loginUser) {
		TeeJson json=new TeeJson();
		String dialect = TeeSysProps.getString("dialect"); 
		Session session = workQueryDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
        

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		List<String> dataTypes = new ArrayList<String>();//进阶查询  表单字段类型
		TeeFlowType ft = null;
		
		select = " select distinct(ft.SID) ";

		sql.append("from flow_run fr,person p,flow_type ft where fr.DEL_FLAG=0 and fr.IS_SAVE=1  and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID");

		

		/**
		 * 查询范围SQL语句组装
		 */

		// 我发起的
		String myBegin = "fr.BEGIN_PERSON=" + loginUser.getUuid() + "";

		// 我经办的
		String myPrcs = "exists (select 1 from flow_run_prcs frp where frp.RUN_ID=fr.RUN_ID and frp.PRCS_USER="
				+ loginUser.getUuid() + ")";

		// 我管理的（这里需要拿到当前人所管理的几个流程和其对应的管理部门串
		StringBuffer myManage = new StringBuffer();
		// 管理权限
		List managePrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "1");
		// 监控权限
		List monitorPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "2");
		// 查询权限
		List searchPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "3");
		// 编辑权限
		List editPrivList = flowPrivService.getQueryPostDeptsByAllFlow(
				loginUser, "4");

		Map queryData = null;
		// 所有权限汇总
		List allPrivList = new ArrayList();
		for (int i = 0; i < managePrivList.size(); i++) {
			queryData = (Map) managePrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < monitorPrivList.size(); i++) {
			queryData = (Map) monitorPrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < searchPrivList.size(); i++) {
			queryData = (Map) searchPrivList.get(i);
			allPrivList.add(queryData);
		}
		for (int i = 0; i < editPrivList.size(); i++) {
			queryData = (Map) editPrivList.get(i);
			allPrivList.add(queryData);
		}
        //获取真正有权限的集合
		List  realPrivList = new ArrayList();
		Map m=null;
		if (allPrivList.size() != 0) {
			for (int i = 0; i < allPrivList.size(); i++) {
				m = (Map) allPrivList.get(i);
				if (!String.valueOf(m.get("postDeptIds")).equals("")) {// 有权限
					realPrivList.add(m);
				}
			}
		}
		if (realPrivList.size() != 0) {// 有权限
			for (int i = 0; i < realPrivList.size(); i++) {
				queryData = (Map) realPrivList.get(i);
				if (!String.valueOf(queryData.get("postDeptIds")).equals("")) {// 有权限
					myManage.append("(fr.FLOW_ID="
							+ queryData.get("flowTypeId"));
					String postDeptIds = String.valueOf(queryData
							.get("postDeptIds"));
					if (!postDeptIds.equals("0")) {// 加入限定部门条件
						if (postDeptIds.endsWith(",")) {
							postDeptIds = postDeptIds.substring(0,
									postDeptIds.length() - 1);
						}
						myManage.append(" and exists (select 1 from department dept where dept.uuid in ("
								+ postDeptIds + ") and p.dept_id=dept.uuid)");
					}
					myManage.append(")");
					if (i != realPrivList.size() - 1) {
						myManage.append(" or ");
					}
				}
			}
		} else {
			myManage.append(" fr.FLOW_ID=0 ");
		}
		// 我关注的
		String myConcern = "exists (select 1 from flow_run_concern frc where frc.RUN_ID=fr.RUN_ID and frc.PERSON_ID="
				+ loginUser.getUuid() + ")";

		// 我查阅的
		String myLookup = "exists (select 1 from flow_run_view frv where frv.VIEW_PERSON="
				+ loginUser.getUuid() + " and frv.RUN_ID=fr.RUN_ID)";

		
		sql.append(" and ((" + myBegin + ") or (" + myPrcs + ") ");
		if (myManage.length() != 0) {
			sql.append(" or (" + myManage.toString() + ")");
		}
		sql.append(" or (" + myConcern + ")");
		sql.append(" or (" + myLookup + ")");
		sql.append(")");
		
		SQLQuery query = session.createSQLQuery(select + sql.toString());
		//query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List<Integer> list = query.list();
		
		String privFlowIds="";
		if(list!=null&&list.size()>0){
			for (Integer integer : list) {
				privFlowIds+=integer+",";
			}
		}
		
		if(privFlowIds.endsWith(",")){
			privFlowIds=privFlowIds.substring(0, privFlowIds.length()-1);
		}

		json.setRtState(true);
		json.setRtData(privFlowIds);
		return json;
	}


	/**
	 * 获取流程恢复的步骤
	 */
	@Override
	public TeeEasyuiDataGridJson getRecoverFlowRunPrcsList(Map params, TeePerson loginUser) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		int runId=TeeStringUtil.getInteger(params.get("runId"),0);
		List<TeeFlowRunPrcs> list=simpleDaoSupport.executeQuery(" from TeeFlowRunPrcs where flowRun.runId=? and topFlag=1 and delFlag=0 ", new Object[]{runId});
		List<TeeFlowRunPrcsModel> modelList=new ArrayList<TeeFlowRunPrcsModel>();
		if(list!=null&&list.size()>0){
			for (TeeFlowRunPrcs frp : list) {
				TeeFlowRunPrcsModel m = new TeeFlowRunPrcsModel();
				BeanUtils.copyProperties(frp, m);
				m.setBeginTimeDesc(TeeDateUtil.format(frp.getBeginTime()));
				m.setCreateTimeDesc(TeeDateUtil.format(frp.getCreateTime()));
				m.setEndTimeDesc(TeeDateUtil.format(frp.getEndTime()));
				m.setFlowPrcsId(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getSid());
				m.setRunId(frp.getFlowRun().getRunId());
				m.setPrcsName(frp.getFlowPrcs()==null?"":frp.getFlowPrcs().getPrcsName());
				m.setPassedTime(TeeDateUtil.getPassedTimeDesc(frp.getBeginTime(), frp.getEndTime()));
				m.setPrcsUserId(frp.getPrcsUser().getUuid());
				m.setPrcsUserName(frp.getPrcsUser().getUserName());
				m.setOtherUserId(frp.getOtherUser()==null?0:frp.getOtherUser().getUuid());
				m.setOtherUserName(frp.getOtherUser()==null?"":frp.getOtherUser().getUserName());
				m.setFromUserId(frp.getFromUser()==null?0:frp.getFromUser().getUuid());
				m.setFromUserName(frp.getFromUser()==null?"":frp.getFromUser().getUserName());
				m.setPrcsType(frp.getFlowPrcs()==null?0:frp.getFlowPrcs().getPrcsType());
				modelList.add(m);
			}
		}
		
		json.setRows(modelList);
		json.setTotal((long)modelList.size());
		return json;
	}


	/**
	 * 流程恢复
	 */
	@Override
	public TeeJson recover(Map params, TeePerson loginUser) {
		TeeJson json=new TeeJson();
		int runId=TeeStringUtil.getInteger(params.get("runId"),0);
		int frpSid=TeeStringUtil.getInteger(params.get("frpSid"),0);
		
	
		TeeFlowRunPrcs frp=(TeeFlowRunPrcs) simpleDaoSupport.get(TeeFlowRunPrcs.class,frpSid);
		TeeFlowRunPrcs newFrp = new TeeFlowRunPrcs();
		BeanUtils.copyProperties(frp, newFrp);
		newFrp.setSid(0);
		newFrp.setEndTime(null);
		newFrp.setEndTimeStamp(0L);
		newFrp.setFlag(1);
		newFrp.setPrcsId(flowRunPrcsDao.getMaxPrcsId(runId)+1);
		newFrp.setBeginTime(null);
		newFrp.setBeginTimeStamp(0L);
		simpleDaoSupport.save(newFrp);
		
		
		TeeFlowRun fr=(TeeFlowRun) simpleDaoSupport.get(TeeFlowRun.class,runId);
		//修改flowRun的状态  结束时间置空
		fr.setEndTime(null);
		simpleDaoSupport.update(fr);
		 
		
		//发消息
		Map requestData1 = new HashMap();
    	requestData1.put("content", "["+loginUser.getUserName()+"]将流程恢复到步骤["+newFrp.getFlowPrcs().getPrcsName()+"]，请及时查收与办理。");
    	requestData1.put("userListIds", newFrp.getPrcsUser().getUuid());
    	requestData1.put("moduleNo", "006");
    	requestData1.put("remindUrl","/system/core/workflow/flowrun/prcs/index.jsp?runId="+runId+"&frpSid="+newFrp.getSid()+"&flowId="+fr.getFlowType().getSid()+"&view=1");
    	smsManager.sendSms(requestData1, loginUser);
    	
    	//写入流程日志
    	TeeFlowRunLog log=new TeeFlowRunLog();
    	log.setContent("["+loginUser.getUserName()+"]将流程恢复到步骤["+frp.getFlowPrcs().getPrcsName()+"]");
    	log.setFlowId(fr.getFlowType().getSid());
    	log.setFlowName(fr.getFlowType().getFlowName());
    	log.setFlowPrcs(frp.getFlowPrcs().getSid());
    	log.setIp(TeeRequestInfoContext.getRequestInfo().getIpAddress());
    	log.setPrcsId(newFrp.getPrcsId());
    	log.setPrcsName(frp.getFlowPrcs().getPrcsName());
    	log.setRunId(runId);
    	log.setRunName(fr.getRunName());
    	log.setTime(Calendar.getInstance());
    	log.setType(7);
    	log.setUserId(loginUser.getUuid());
    	log.setUserName(loginUser.getUserName());
    	simpleDaoSupport.save(log);
	    json.setRtState(true);
		return json;
	}

	
}
