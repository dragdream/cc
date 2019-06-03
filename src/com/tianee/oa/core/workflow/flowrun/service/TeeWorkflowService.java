package com.tianee.oa.core.workflow.flowrun.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.general.service.TeeSmsSender;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.phoneSms.bean.TeeSmsSendPhone;
import com.tianee.oa.core.workFlowFrame.dataloader.TeeSimpleDataLoaderInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowSort;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowProcessDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowSortDao;
import com.tianee.oa.core.workflow.flowmanage.dao.TeeFlowTypeDao;
import com.tianee.oa.core.workflow.flowmanage.service.TeeSelectUserRuleServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunCtrlFeedback;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunDoc;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunVars;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunView;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunPrcsDao;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.core.workflow.formmanage.service.TeeFlowFormServiceInterface;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.oa.webframe.httpModel.core.workflow.TeeWorkFlowInfoModel;
import com.tianee.webframe.dao.TeeSimpleDaoSupport;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.secure.Base64Private;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeWorkflowService extends TeeBaseService implements
		TeeWorkflowServiceInterface {
	@Autowired
	private TeeFlowRunPrcsDao frpDao;

	@Autowired
	private TeeFlowRunDao flowRunDao;

	@Autowired
	private TeeFlowProcessDao flowProcessDao;

	@Autowired
	private TeeSimpleDaoSupport simpleDaoSupport;

	@Autowired
	private TeePersonService personService;

	@Autowired
	private TeeSelectUserRuleServiceInterface selectUserRuleService;

	@Autowired
	private TeeFlowTypeDao flowTypeDao;

	@Autowired
	private TeeFlowSortDao flowTypeSortDao;

	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeFlowFormServiceInterface formService;

	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private TeeFlowRunDocServiceInterface flowRunDocService;

	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;

	@Autowired
	private TeeSimpleDataLoaderInterface simpleDataLoaderInterface;

	@Autowired
	private TeeSmsSender smsSender;

	@Autowired
	private TeeSmsManager smsManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #createNewWork(com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType,
	 * com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public int createNewWork(TeeFlowType ft, TeePerson beginUser) {
		int runId = 0;
		/**
		 * 判断该发起人有没有权限进行发起
		 */

		return runId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getNoReceivedWorks(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson getNoReceivedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		int firstResult = (dataGridModel.getPage() - 1)
				* dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {
			if (sort == null || order == null) {
				sort = "fr.RUN_ID";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.TOP_FLAG, "
					+ "frp.FLAG, " + "frp.FLOW_PRCS, " + "frp.PRCS_ID, "
					+ "fr.FLOW_ID, " + "fr.RUN_NAME, " + "fr.BEGIN_PERSON, "
					+ "fr.RUN_ID,";

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
						if (tmp.equals(fi.getName())) {
							datas.add(tmp);
							select += "frd." + tmp + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp,FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID");
			sql.append(" and fr.FLOW_ID=" + flowId);

		} else {
			if (sort == null || order == null) {
				sort = "fr.RUN_ID";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, " + "frp.FLAG, " + "frp.FLOW_PRCS, "
					+ "fr.FLOW_ID, " + "fr.RUN_NAME, " + "fr.RUN_ID, "
					+ "fr.BEGIN_PERSON, " + "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID");
		}

		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		}

		// 流程状态
		if (status == 1) {// 超时
			sql.append(" and frp.TIMEOUT_FLAG=1");
		} else if (status == 2) {// 未超时
			sql.append(" and frp.TIMEOUT_FLAG=2");
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and frp.flag = 1 and fr.END_TIME is null and frp.PRCS_USER="
				+ loginUser.getUuid());
		sql.append(" order by " + sort + " " + order);

		SQLQuery query = session.createSQLQuery(select + sql.toString());
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		query.setFirstResult((dataGridModel.getPage() - 1)
				* dataGridModel.getRows());
		query.setMaxResults(dataGridModel.getRows());

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp beginTime = null;
		for (Map fr : list) {
			Map map = new HashMap();
			beginTime = (Timestamp) fr.get("CREATE_TIME");
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime",
					fr.get("BEGIN_TIME") == null ? "" : fr.get("BEGIN_TIME")
							.toString());
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : fr.get("END_TIME")
							.toString());
			map.put("createTime", fr.get("CREATE_TIME") == null ? ""
					: TeeDateUtil.format(new Date(beginTime.getTime())));
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("frpSid", fr.get("FRP_SID"));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());

			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));
			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */
			if (topFlag == 0) {// 经办
				map.put("prcsHandle", "会签");
			} else {// 主办
				map.put("opHandle", "主办");
			}

			if (ftTmp.getDelegate() != 0 && topFlag == 1) {
				map.put("delegate", "委托");
			}
			map.put("suspend", "挂起");
			map.put("doExport", "导出");

			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}

			resultList.add(map);
		}

		json.setRows(resultList);
		json.setTotal(total);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getReceivedWorks(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getReceivedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		String flowIds = TeeStringUtil.getString(queryParams.get("flowIds"));// 流程ID串
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题
		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));// 外部SQL

		// 开始时间
		String startTime = TeeStringUtil
				.getString(queryParams.get("beginTime"));
		// 结束时间
		String endTime = TeeStringUtil.getString(queryParams.get("endTime"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beginTimeDate = null;
		Calendar endTimeDate = null;
		if (!("").equals(startTime)) {
			Date date = null;
			try {
				date = sdf.parse(startTime + " 00:00:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			beginTimeDate = Calendar.getInstance();
			beginTimeDate.setTime(date);
		}
		if (!("").equals(endTime)) {
			Date date = null;
			try {
				date = sdf.parse(endTime + " 23:59:59");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endTimeDate = Calendar.getInstance();
			endTimeDate.setTime(date);
		}

		int firstResult = (dataGridModel.getPage() - 1)
				* dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {// 指定流程
			if (sort == null || order == null) {
				sort = "frp.sid";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID,"
					+ "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, "
					+ "frp.END_TIME, "
					+ "frp.TOP_FLAG, "
					+ "frp.FLAG, "
					+ "frp.FLOW_PRCS, "
					+ "frp.TIMEOUT_FLAG, "
					+ "frp.PRCS_ID, "
					+ "frp.BACK_FLAG, "
					+ "fr.FLOW_ID, "
					+ "fr.RUN_NAME, "
					+ "fr.SEND_FLAG, "
					+ "fr.END_TIME as ETIME, "
					+ "fr.BEGIN_PERSON, "
					+ "fr.LEVEL_, "
					+ "(select max(doc_attach_id) from flow_run_doc where flow_run_doc.run_id=fr.run_id) as DOC_ATTACH_ID , "
					+ "fp.PRCS_TYPE," + "fr.RUN_ID,";

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
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS,FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");
			sql.append(" and fr.FLOW_ID=" + flowId);

		} else {// 全部流程
			if (sort == null || order == null) {
				sort = "frp.sid";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID,"
					+ "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, "
					+ "frp.END_TIME, "
					+ "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, "
					+ "frp.FLAG, "
					+ "frp.FLOW_PRCS, "
					+ "frp.TIMEOUT_FLAG, "
					+ "frp.BACK_FLAG, "
					+ "fr.FLOW_ID, "
					+ "fr.END_TIME as ETIME, "
					+ "fr.RUN_NAME, "
					+ "fr.RUN_ID, "
					+ "fr.SEND_FLAG, "
					+ "fr.BEGIN_PERSON, "
					+ "fr.LEVEL_, "
					+ "ft.FLOW_NAME, "
					+ "fp.PRCS_TYPE,"
					+ "(select max(doc_attach_id) from flow_run_doc where flow_run_doc.run_id=fr.run_id) as DOC_ATTACH_ID  ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS ,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");

			if (!"".equals(flowIds)) {
				sql.append(" and " + TeeDbUtility.IN("fr.FLOW_ID", flowIds));
			}
		}

		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%"
					+ TeeDbUtility.formatString(runName) + "%'");
		}
		// String queryStr = "";
		if (flowSortId > 0) {
			sql.append(" and ft.SORT_ID=" + flowSortId);
		}

		// 流程状态
		if (status == 1) {// 超时
			sql.append(" and frp.TIMEOUT_FLAG=1");
		} else if (status == 2) {// 未超时
			sql.append(" and frp.TIMEOUT_FLAG=2");
		}

		List<Calendar> dateParam = new ArrayList<Calendar>();
		// 开始时间
		if (!"".equals(startTime)) {
			sql.append(" and frp.BEGIN_TIME >=? ");
			dateParam.add(beginTimeDate);

		}
		// 结束时间
		if (!"".equals(endTime)) {
			sql.append(" and frp.BEGIN_TIME <=?");
			dateParam.add(endTimeDate);
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and frp.flag in (1,2) and fr.END_TIME is null and frp.PRCS_USER="
				+ loginUser.getUuid() + " and frp.suspend=0");

		// sql.append(" and frp.flag in (1,2) and frp.PRCS_USER="
		// + loginUser.getUuid() + " and frp.suspend=0");

		if (!"".equals(SQL_)) {
			sql.append(" and (" + SQL_ + ")");
		}

		SQLQuery query = session.createSQLQuery(select + sql.toString()
				+ " order by fr.LEVEL_ desc ," + sort + " " + order);
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		for (int i = 0; i < dateParam.size(); i++) {
			query.setParameter(i, dateParam.get(i));
			queryCount.setParameter(i, dateParam.get(i));
		}

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		query.setFirstResult((dataGridModel.getPage() - 1)
				* dataGridModel.getRows());
		query.setMaxResults(dataGridModel.getRows());

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp beginTime = null;
		List<TeeAttachmentModel> attaches = null;
		TeeAttachmentModel doc = null;
		for (Map fr : list) {
			attaches = new ArrayList();
			Map map = new HashMap();
			beginTime = (Timestamp) fr.get("BEGIN_TIME");
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime", fr.get("BEGIN_TIME") == null ? ""
					: TeeDateUtil.format(new Date(beginTime.getTime())));
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : fr.get("END_TIME")
							.toString());
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("flag", fr.get("FLAG"));
			map.put("frpSid", fr.get("FRP_SID"));
			map.put("timeoutFlag", fr.get("TIMEOUT_FLAG"));
			map.put("level", fr.get("LEVEL_"));
			map.put("backFlag", fr.get("BACK_FLAG"));
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow, fr.get("RUN_ID")
							.toString()));
			map.put("doc",
					attachmentService.getModelById(TeeStringUtil.getInteger(
							fr.get("DOC_ATTACH_ID"), 0)));

			map.put("endFlag", fr.get("ETIME"));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());

			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));
			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */
			if (topFlag == 0) {// 经办
				map.put("prcsHandle", "会签");
			} else {// 主办
				map.put("opHandle", "主办");
			}

			if (ftTmp.getDelegate() != 0 && topFlag == 1) {
				map.put("delegate", "委托");
			}
			map.put("suspend", "挂起");
			map.put("doExport", "导出");

			int prcsType = TeeStringUtil.getInteger(fr.get("PRCS_TYPE"), 0);
			// 获取所属的RunId
			// int rId=TeeStringUtil.getInteger(fr.get("RUN_ID"), 0);
			// //获取第一步所属设计步骤
			// String
			// sql1="select frp.flow_prcs as FLOW_PRCS from flow_run_prcs  frp where frp.run_id="+rId+" and frp.prcs_id=1";
			// Map m=simpleDaoSupport.executeNativeUnique(sql1, null);
			// 获取第一步所属设计步骤的主键
			int firstPrcsSid = 0;
			// if(m!=null){
			// firstPrcsSid=TeeStringUtil.getInteger(m.get("FLOW_PRCS"), 0);
			// }
			// System.out.println("第一步所属设计步骤的主键："+firstPrcsSid);
			// 获取回退标记
			int backFlag = TeeStringUtil.getInteger(fr.get("BACK_FLAG"), 0);
			// 获取所属设计步骤
			// int flowPrcs=TeeStringUtil.getInteger(fr.get("FLOW_PRCS"),0);
			if (TeeStringUtil.getInteger(fr.get("PRCS_ID"), 0) == 1
					|| (backFlag == 1 && prcsType == 1)) {
				map.put("doDelete", "删除");
			}

			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}

			resultList.add(map);
		}

		json.setRows(resultList);
		json.setTotal(total);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getConcernedWorks(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getConcernedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		List params = new ArrayList();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		int firstResult = (dataGridModel.getPage() - 1)
				* dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();

		String selectCount = "select count(*) ";

		StringBuffer sql = new StringBuffer();
		sql.append("from TeeFlowRun fr where fr.isSave=1 and exists (select 1 from TeeFlowRunConcern frc where frc.flowRun=fr and frc.person.uuid="
				+ loginUser.getUuid() + ") ");

		if (flowId != 0) {
			sql.append(" and fr.flowType.sid=" + flowId);
		}

		if (runId != 0) {
			sql.append(" and fr.runId=" + runId);
		}

		if (!"".equals(runName)) {
			sql.append(" and fr.runName like ?");
			params.add("%" + runName + "%");
		}
		if (flowSortId > 0) {
			sql.append(" and fr.flowType.flowSort.sid=" + flowSortId);
		}

		String _order = null;
		if (sort == null) {
			_order = " order by fr.runId desc";
		} else {
			_order = " order by " + sort + " " + order;
		}

		List<TeeFlowRun> list = simpleDaoSupport.pageFindByList(sql.toString()
				+ _order, firstResult, pageSize, params);
		long total = simpleDaoSupport.count(selectCount + sql.toString(),
				params.toArray());

		List<Map> maps = new ArrayList();
		TeeFlowRunDoc doc = null;
		for (TeeFlowRun fr : list) {
			Map map = new HashMap();
			map.put("runId", fr.getRunId());
			map.put("flowId", fr.getFlowType().getSid());
			map.put("beginTime", TeeDateUtil.format(fr.getBeginTime()));
			map.put("endTime", TeeDateUtil.format(fr.getEndTime()));
			map.put("runName", fr.getRunName());
			map.put("flowName", fr.getFlowType().getFlowName());
			map.put("beginPerson", fr.getBeginPerson().getUserName());
			map.put("endFlag", fr.getEndTime());
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow,
					String.valueOf(fr.getRunId())));
			doc = flowRunDocService.getFlowRunDocByRunId(fr.getRunId());
			if (doc != null) {
				map.put("doc", attachmentService.getModelById(doc
						.getDocAttach().getSid()));
			} else {
				map.put("doc", null);
			}

			map.put("doExport", 1);
			map.put("doExport", 1);

			maps.add(map);
		}

		json.setRows(maps);
		json.setTotal(total);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getViewsWorks(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getViewsWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		List params = new ArrayList();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		int firstResult = (dataGridModel.getPage() - 1)
				* dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();

		String selectCount = "select count(*) ";

		StringBuffer sql = new StringBuffer();
		sql.append("from TeeFlowRunView frv where frv.viewPerson.uuid="
				+ loginUser.getUuid()
				+ " and exists (select 1 from TeeFlowRun fr where fr.runId=frv.flowRun and fr.isSave=1 )");

		if (flowId != 0) {
			sql.append(" and frv.flowRun.flowType.sid=" + flowId);
		}

		if (runId != 0) {
			sql.append(" and frv.flowRun.runId=" + runId);
		}

		if (!"".equals(runName)) {
			sql.append(" and frv.flowRun.runName like ?");
			params.add("%" + runName + "%");
		}
		if (flowSortId > 0) {
			sql.append(" and frv.flowRun.flowType.flowSort.sid=" + flowSortId);
		}
		String _order = null;
		if (sort == null) {
			_order = " order by frv.createTime desc";
		} else {
			_order = " order by " + sort + " " + order;
		}

		List<TeeFlowRunView> list = simpleDaoSupport.pageFindByList(
				sql.toString() + _order, firstResult, pageSize, params);
		long total = simpleDaoSupport.count(selectCount + sql.toString(),
				params.toArray());

		List<Map> maps = new ArrayList();
		TeeFlowRunDoc doc = null;
		TeeFlowRun fr = null;
		for (TeeFlowRunView frv : list) {
			Map map = new HashMap();
			fr = frv.getFlowRun();
			map.put("runId", fr.getRunId());
			map.put("flowId", fr.getFlowType().getSid());
			map.put("beginTime", TeeDateUtil.format(fr.getBeginTime()));
			map.put("endTime", TeeDateUtil.format(fr.getEndTime()));
			map.put("runName", fr.getRunName());
			map.put("flowName", fr.getFlowType().getFlowName());
			map.put("beginPerson", fr.getBeginPerson().getUserName());
			map.put("viewFlag", frv.getViewFlag());
			map.put("endFlag", frv.getFlowRun().getEndTime());
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow,
					String.valueOf(fr.getRunId())));
			doc = flowRunDocService.getFlowRunDocByRunId(fr.getRunId());
			if (doc != null) {
				map.put("doc", attachmentService.getModelById(doc
						.getDocAttach().getSid()));
			} else {
				map.put("doc", null);
			}

			map.put("doExport", 1);

			maps.add(map);
		}

		json.setRows(maps);
		json.setTotal(total);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getSuspendedWorks(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getSuspendedWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		int firstResult = (dataGridModel.getPage() - 1)
				* dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {
			if (sort == null || order == null) {
				sort = "frp.sid";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.TOP_FLAG, "
					+ "frp.FLAG, " + "frp.FLOW_PRCS, " + "frp.PRCS_ID, "
					+ "fr.FLOW_ID, " + "fr.RUN_NAME, " + "fr.BEGIN_PERSON, "
					+ "doc.DOC_ATTACH_ID, " + "fr.RUN_ID,";

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
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");
			sql.append(" and fr.FLOW_ID=" + flowId);

		} else {
			if (sort == null || order == null) {
				sort = "frp.sid";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, " + "frp.FLAG, " + "frp.FLOW_PRCS, "
					+ "fr.FLOW_ID, " + "fr.RUN_NAME, " + "fr.RUN_ID, "
					+ "fr.BEGIN_PERSON, " + "doc.DOC_ATTACH_ID, "
					+ "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");
		}

		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		}

		if (flowSortId > 0) {
			sql.append(" and ft.SORT_ID=" + flowSortId);
		}

		// 流程状态
		if (status == 1) {// 超时
			sql.append(" and frp.TIMEOUT_FLAG=1");
		} else if (status == 2) {// 未超时
			sql.append(" and frp.TIMEOUT_FLAG=2");
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and fr.END_TIME is null and frp.PRCS_USER="
				+ loginUser.getUuid() + " and frp.suspend=1");

		SQLQuery query = session.createSQLQuery(select + sql.toString()
				+ " order by " + sort + " " + order);
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		query.setFirstResult((dataGridModel.getPage() - 1)
				* dataGridModel.getRows());
		query.setMaxResults(dataGridModel.getRows());

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp beginTime = null;
		for (Map fr : list) {
			Map map = new HashMap();
			beginTime = (Timestamp) fr.get("BEGIN_TIME");
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime", fr.get("BEGIN_TIME") == null ? ""
					: TeeDateUtil.format(new Date(beginTime.getTime())));
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : fr.get("END_TIME")
							.toString());
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("frpSid", fr.get("FRP_SID"));
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow, fr.get("RUN_ID")
							.toString()));
			map.put("doc",
					attachmentService.getModelById(TeeStringUtil.getInteger(
							fr.get("DOC_ATTACH_ID"), 0)));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());

			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));
			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */
			if (topFlag == 0) {// 经办
				map.put("prcsHandle", "会签");
			} else {// 主办
				map.put("opHandle", "主办");
			}

			if (ftTmp.getDelegate() != 0 && topFlag == 1) {
				map.put("delegate", "委托");
			}
			map.put("suspend", "挂起");
			map.put("doExport", "导出");
			if (TeeStringUtil.getInteger(fr.get("PRCS_ID"), 0) == 1) {
				map.put("doDelete", "删除");
			}

			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}

			resultList.add(map);
		}

		json.setRows(resultList);
		json.setTotal(total);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getNoHandledWorks(com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson getNoHandledWorks(TeePerson person,
			TeeDataGridModel m) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<TeeWorkFlowInfoModel> result = new ArrayList<TeeWorkFlowInfoModel>();// 结果数组
		int firstResult = (m.getPage() - 1) * m.getRows();
		int pageSize = m.getRows();
		String sort = m.getSort();
		String order = m.getOrder();

		if (sort == null || order == null) {
			sort = "frp.sid";
			order = "desc";
		}

		List<TeeFlowRunPrcs> prcsList = frpDao.getNoHandledWorks(person,
				firstResult, pageSize, sort, order);
		/**
		 * 组装model对象
		 */

		for (int i = 0; i < prcsList.size(); i++) {
			TeeFlowRun flowRun = null;
			TeeFlowRunPrcs flowPrcs = prcsList.get(i);
			TeeWorkFlowInfoModel model = new TeeWorkFlowInfoModel();
			flowRun = flowPrcs.getFlowRun();
			if (flowRun != null) {

				Calendar ca = flowRun.getBeginTime();
				TeePerson p = flowRun.getBeginPerson();
				TeeFlowType ft = flowRun.getFlowType();
				if (ca != null) {
					model.setBeginTime(TeeDateUtil.format(ca.getTime()));
				}
				if (p != null) {
					model.setBeginUserName(p.getUserName());
				}
				if (ft != null) {
					model.setFlowId(ft.getSid());
					model.setFlowName(TeeStringUtil.fileEmptyString(
							ft.getFlowName(), ""));
				}
				model.setRunName(flowRun.getRunName());
				model.setRunId(flowRun.getRunId());

			}
			if (flowPrcs != null) {
				Calendar crt = flowPrcs.getCreateTime();
				Calendar dt = flowPrcs.getEndTime();
				TeePerson fromUser = flowPrcs.getFromUser();
				TeeFlowProcess fp = flowPrcs.getFlowPrcs();
				TeePerson prcsUser = flowPrcs.getPrcsUser();
				String sFlowPrcName = "";
				if (crt != null) {
					model.setCreateTime(TeeDateUtil.format(crt.getTime()));
				}
				if (dt != null) {
					model.setEndTime(TeeDateUtil.format(dt.getTime()));
				}
				model.setDelFlag(flowPrcs.getDelFlag());
				if (fromUser != null) {
					model.setFromUserId(fromUser.getUuid() + "");
				}
				model.setOpFlag(flowPrcs.getOpFlag());
				model.setPrcsFlag(flowPrcs.getFlag());
				model.setPrcsId(flowPrcs.getPrcsId());
				if (fp != null) {
					sFlowPrcName = fp.getPrcsName();
				}
				model.setPrcsName("第" + flowPrcs.getPrcsId() + "步："
						+ sFlowPrcName);
				if (prcsUser != null) {
					model.setPrcsUserId(prcsUser.getUuid() + "");
					model.setPrcsUserName(prcsUser.getUserName());
				}
				model.setTimeout(flowPrcs.getTimeout());
				model.setTimeoutFlag(flowPrcs.getTimeoutFlag());
				model.setTopFlag(flowPrcs.getTopFlag());
				model.setFrpSid(flowPrcs.getSid());
			}
			result.add(model);
		}

		dataGridJson.setRows(result);
		dataGridJson.setTotal(frpDao.getNoHandledWorksCount(person));
		return dataGridJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getCurrStepDescByRunId(int, java.lang.Object)
	 */
	@Override
	public String getCurrStepDescByRunId(int runId, Object endTime) {
		String currStepName = "";
		if (runId > 0) {
			if (endTime != null && !("").equals(endTime)) {
				currStepName = "已结束";
			} else {
				String hql = " select fp.prcsName,frp.prcsId from TeeFlowRunPrcs frp left join frp.flowPrcs fp where frp.flowRun.runId=? order by frp.sid desc";
				List<Object> param = new ArrayList<Object>();
				param.add(runId);
				List<Object[]> list = simpleDaoSupport.executeQueryByList(hql,
						param);
				if (("").equals(list.get(0)[0]) || list.get(0)[0] == null) {
					currStepName = "第" + list.get(0)[1] + "步";
				} else {
					currStepName = "第" + list.get(0)[1] + "步：" + list.get(0)[0];
				}
			}

		}
		return currStepName;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getHandledWorks(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	@Transactional(readOnly = true)
	public TeeEasyuiDataGridJson getHandledWorks(Map queryParams,
			TeePerson loginUser, TeeDataGridModel dataGridModel) {
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();

		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		String flowIds = TeeStringUtil.getString(queryParams.get("flowIds"));// 流程ID串
		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));// 外部SQL
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		// 开始时间
		String startTime = TeeStringUtil
				.getString(queryParams.get("beginTime"));
		// 结束时间
		String endTime = TeeStringUtil.getString(queryParams.get("endTime"));

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beginTimeDate = null;
		Calendar endTimeDate = null;
		if (!("").equals(startTime)) {
			Date date = null;
			try {
				date = sdf.parse(startTime + " 00:00:00");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			beginTimeDate = Calendar.getInstance();
			beginTimeDate.setTime(date);
		}
		if (!("").equals(endTime)) {
			Date date = null;
			try {
				date = sdf.parse(endTime + " 23:59:59");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endTimeDate = Calendar.getInstance();
			endTimeDate.setTime(date);
		}

		int firstResult = (dataGridModel.getPage() - 1)
				* dataGridModel.getRows();
		int pageSize = dataGridModel.getRows();
		String sort = dataGridModel.getSort();
		String order = dataGridModel.getOrder();

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {
			if (sort == null || order == null) {
				sort = "frp.sid";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID,"
					+ "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, "
					+ "frp.END_TIME, "
					+ "frp.TOP_FLAG, "
					+ "frp.FLAG, "
					+ "frp.FLOW_PRCS, "
					+ "fr.LEVEL_, "
					+ "frp.PRCS_ID, "
					+ "frp.BACK_FLAG, "
					+ "fr.FLOW_ID, "
					+ "fr.END_TIME as ETIME, "
					+ "fr.SEND_FLAG, "
					+ "fr.RUN_NAME, "
					+ "fr.BEGIN_PERSON, "
					+ "(select max(doc_attach_id) from flow_run_doc where flow_run_doc.run_id=fr.run_id) as DOC_ATTACH_ID , "
					+ "fr.RUN_ID,";

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
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN fr on frp.RUN_ID=fr.RUN_ID left outer join PERSON p on p.uuid=fr.begin_person left outer join FLOW_TYPE ft on ft.SID=frp.FLOW_ID left outer join tee_f_r_d_"
					+ flowId + " frd on frd.RUN_ID=fr.RUN_ID where ");

			sql.append(" frp.SID in (select max(FLOW_RUN_PRCS.sid) MAXID  from FLOW_RUN_PRCS  where PRCS_USER="
					+ loginUser.getUuid()
					+ " and FLOW_ID="
					+ flowId
					+ " and FLOW_RUN_PRCS.DEL_FLAG=0 and (FLOW_RUN_PRCS.flag in (3,4) or FLOW_RUN_PRCS.END_TIME is not null)  group by FLOW_RUN_PRCS.RUN_ID,FLOW_RUN_PRCS.PRCS_USER)");

		} else {
			if (sort == null || order == null) {
				sort = "frp.sid";
				order = "desc";
			} else if ("RUN_ID".equals(sort)) {
				sort = "fr.RUN_ID";
			}

			select = "select frp.SID as FRP_SID,"
					+ "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, "
					+ "frp.END_TIME, "
					+ "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, "
					+ "frp.FLAG, "
					+ "frp.FLOW_PRCS, "
					+ "frp.FROM_USER, "
					+ "frp.OTHER_USER, "
					+ "frp.BACK_FLAG, "
					+ "fr.FLOW_ID, "
					+ "fr.SEND_FLAG, "
					+ "fr.LEVEL_, "
					+ "fr.END_TIME as ETIME, "
					+ "fr.RUN_NAME, "
					+ "fr.RUN_ID, "
					+ "fr.BEGIN_PERSON , "
					+ "(select max(doc_attach_id) from flow_run_doc where flow_run_doc.run_id=fr.run_id) as DOC_ATTACH_ID , "
					+ "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN fr on frp.RUN_ID=fr.RUN_ID left outer join PERSON p on p.uuid=fr.begin_person left outer join FLOW_TYPE ft on ft.SID=frp.FLOW_ID where ");
			sql.append(" frp.SID in (select max(FLOW_RUN_PRCS.sid) MAXID  from FLOW_RUN_PRCS  where PRCS_USER="
					+ loginUser.getUuid()
					+ " and FLOW_RUN_PRCS.DEL_FLAG=0 and (FLOW_RUN_PRCS.flag in (3,4) or FLOW_RUN_PRCS.END_TIME is not null)  group by FLOW_RUN_PRCS.RUN_ID,FLOW_RUN_PRCS.PRCS_USER)");

			if (!"".equals(flowIds)) {
				sql.append(" and " + TeeDbUtility.IN("fr.FLOW_ID", flowIds));
			}
		}

		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		}
		if (flowSortId > 0) {
			sql.append(" and ft.SORT_ID=" + flowSortId);
		}

		List<Calendar> dateParam = new ArrayList<Calendar>();
		// 开始时间
		if (!"".equals(startTime)) {
			sql.append(" and frp.END_TIME >=? ");
			dateParam.add(beginTimeDate);

		}
		// 结束时间
		if (!"".equals(endTime)) {
			sql.append(" and frp.END_TIME <=?");
			dateParam.add(endTimeDate);
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and (frp.flag in (3,4) or fr.end_time is not null)");
		// System.out.println(sql.toString());

		if (!"".equals(SQL_)) {
			sql.append(" and (" + SQL_ + ")");
		}

		SQLQuery query = session.createSQLQuery(select + sql.toString()
				+ " order by " + sort + " " + order);
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());

		for (int i = 0; i < dateParam.size(); i++) {
			query.setParameter(i, dateParam.get(i));
			queryCount.setParameter(i, dateParam.get(i));
		}

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		query.setFirstResult((dataGridModel.getPage() - 1)
				* dataGridModel.getRows());
		query.setMaxResults(dataGridModel.getRows());

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp time = null;
		for (Map fr : list) {
			Map map = new HashMap();
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime",
					fr.get("BEGIN_TIME") == null ? "" : fr.get("BEGIN_TIME")
							.toString());
			time = (Timestamp) fr.get("END_TIME");
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : TeeDateUtil
							.format(new Date(time.getTime())));
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("frpSid", fr.get("FRP_SID"));
			map.put("level", fr.get("LEVEL_"));
			map.put("backFlag", fr.get("BACK_FLAG"));
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow, fr.get("RUN_ID")
							.toString()));
			map.put("doc",
					attachmentService.getModelById(TeeStringUtil.getInteger(
							fr.get("DOC_ATTACH_ID"), 0)));

			time = (Timestamp) fr.get("ETIME");
			map.put("endFlag", time);

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());
			if (beginPerson.getUuid() == loginUser.getUuid()) {
				map.put("beginFalg", "true");
			}
			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));

			// 获取当前流程的走到了哪一步
			String currStepDesc = getCurrStepDescByRunId(
					TeeStringUtil.getInteger(fr.get("RUN_ID"), 0), time);

			map.put("currStepDesc", currStepDesc);

			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */

			map.put("doExport", "导出");

			List noReceivedDelegate = frpDao
					.getNoReceivedDelegatedWorks(TeeStringUtil.getInteger(
							fr.get("FRP_SID"), 0));

			if (noReceivedDelegate.size() != 0) {
				boolean hasReceive = false;
				for (int i = 0; i < noReceivedDelegate.size(); i++) {
					Object obj[] = (Object[]) noReceivedDelegate.get(i);
					if (!obj[1].toString().equals("1")) {
						hasReceive = true;
						break;
					}
				}
				if (!hasReceive) {
					map.put("doBackDelegate", "收回委托");
				}
			} else {
				if (TeeStringUtil.getInteger(fr.get("FLAG"), 0) == 3
						&& TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0) == 1) {
					map.put("doBack", "收回");
				}
			}

			if (TeeStringUtil.getInteger(fr.get("FLAG"), 0) == 3) {
				// map.put("doBack", "收回");
				map.put("doUrge", "催办");
			}

			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}

			resultList.add(map);
		}

		json.setRows(resultList);
		json.setTotal(total);

		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getExtraWorksByFlowRun(int, java.lang.String)
	 */
	@Override
	public List<TeeWorkFlowInfoModel> getExtraWorksByFlowRun(int runId,
			String uuid) {
		List<TeeWorkFlowInfoModel> result = new ArrayList<TeeWorkFlowInfoModel>();// 结果数组

		List<TeeFlowRunPrcs> prcsList = frpDao.getExtraWorksByFlowRun(runId,
				uuid);
		/**
		 * 组装model对象!
		 */

		for (int i = 0; i < prcsList.size(); i++) {
			TeeFlowRun flowRun = null;
			TeeFlowRunPrcs flowPrcs = prcsList.get(i);
			TeeWorkFlowInfoModel model = new TeeWorkFlowInfoModel();
			flowRun = flowPrcs.getFlowRun();
			if (flowRun != null) {

				Calendar ca = flowRun.getBeginTime();
				TeePerson p = flowRun.getBeginPerson();
				TeeFlowType ft = flowRun.getFlowType();
				System.out.println("这是测试type:" + ft);
				if (ca != null) {
					model.setBeginTime(TeeDateUtil.format(ca.getTime()));
				}
				if (p != null) {
					model.setBeginUserName(p.getUserName());
				}
				if (ft != null) {
					model.setFlowId(ft.getSid());
					model.setFlowName(TeeStringUtil.fileEmptyString(
							ft.getFlowName(), ""));
				}
				model.setRunName(flowRun.getRunName());
				model.setRunId(flowRun.getRunId());

			}
			if (flowPrcs != null) {
				Calendar crt = flowPrcs.getCreateTime();
				Calendar dt = flowPrcs.getEndTime();
				TeePerson fromUser = flowPrcs.getFromUser();
				TeeFlowProcess fp = flowPrcs.getFlowPrcs();
				TeePerson prcsUser = flowPrcs.getPrcsUser();
				String sFlowPrcName = "";
				if (crt != null) {
					model.setCreateTime(TeeDateUtil.format(crt.getTime()));
				}
				if (dt != null) {
					model.setEndTime(TeeDateUtil.format(dt.getTime()));
				}
				model.setDelFlag(flowPrcs.getDelFlag());
				if (fromUser != null) {
					model.setFromUserId(fromUser.getUuid() + "");
				}
				model.setOpFlag(flowPrcs.getOpFlag());
				model.setPrcsFlag(flowPrcs.getFlag());
				model.setPrcsId(flowPrcs.getPrcsId());
				if (fp != null) {
					sFlowPrcName = fp.getPrcsName();
				}
				model.setPrcsName("第" + flowPrcs.getPrcsId() + "步："
						+ sFlowPrcName);
				if (prcsUser != null) {
					model.setPrcsUserId(prcsUser.getUuid() + "");
					model.setPrcsUserName(prcsUser.getUserName());
				}
				model.setTimeout(flowPrcs.getTimeout());
				model.setTimeoutFlag(flowPrcs.getTimeoutFlag());
				model.setTopFlag(flowPrcs.getTopFlag());

			}
			result.add(model);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getPrcsUsersByProcess
	 * (com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess,
	 * com.tianee.oa.core.org.bean.TeeDepartment,
	 * com.tianee.oa.core.org.bean.TeeUserRole)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<TeePerson> getPrcsUsersByProcess(TeeFlowProcess flowProcess,
			TeeDepartment deptFilter, TeeUserRole roleFilter) {
		// TODO Auto-generated method stub

		List<TeePerson> allPersons = simpleDaoSupport
				.find("from TeePerson p where exists "
						+ "(select 1 from TeeFlowProcess fp where fp=? and ("
						+ "exists (select 1 from fp.prcsUser fp_prcsUser where fp_prcsUser=p) or "
						+ "exists (select 1 from fp.prcsDept fp_prcsDept where fp_prcsDept=p.dept or exists (select 1 from p.deptIdOther deptIdOther where deptIdOther=fp_prcsDept)) or "
						+ "exists (select 1 from fp.prcsRole fp_prcsRole where fp_prcsRole=p.userRole or exists (select 1 from p.userRoleOther userRoleOther where userRoleOther=fp_prcsRole))"
						+ ")) order by p.userNo asc",
						new Object[] { flowProcess });

		// 获取用户下所有人
		// List<TeePerson> allPersons =
		// dao.find("from TeePerson person order by person.userNo asc", new
		// Object[]{});
		//
		// Set<TeePerson> prcsPersons = flowProcess.getPrcsUser();
		// Set<TeeDepartment> prcsDepts = flowProcess.getPrcsDept();
		// Set<TeeUserRole> prcsRoles = flowProcess.getPrcsRole();
		//
		boolean isInDept = false;
		boolean isInRole = false;
		//
		// //循环所有用户，找到该用户是否在经办授权范围内
		// for(TeePerson curPerson:allPersons){
		// //判断该人是否有人员经办权限
		// for(TeePerson p:prcsPersons){
		// if(p.getUuid() == curPerson.getUuid()){
		// newPrcsPersons.add(curPerson);
		// continue;
		// }
		// }
		//
		// //判断该人是否有部门经办权限
		// isInDept = isInDepartment(curPerson,prcsDepts);
		// if(isInDept){
		// newPrcsPersons.add(curPerson);
		// continue;
		// }
		//
		// //判断该人是否有角色经办权限
		// isInRole = isInUserRole(curPerson,prcsRoles);
		// if(isInRole){
		// newPrcsPersons.add(curPerson);
		// continue;
		// }
		//
		// }
		//
		// 通过过滤角色与过滤部门，进行再筛选
		Iterator<TeePerson> it = allPersons.iterator();
		TeePerson itPerson = null;
		while (it.hasNext()) {
			itPerson = it.next();
			// 判断是否该人存在于过滤部门和过滤角色中
			if (deptFilter != null && roleFilter == null) {
				isInRole = true;
				isInDept = isInDepartment(itPerson, deptFilter);
			} else if (deptFilter == null && roleFilter != null) {
				isInRole = isInUserRole(itPerson, roleFilter);
				isInDept = true;
			} else if (deptFilter != null && roleFilter != null) {
				isInDept = isInDepartment(itPerson, deptFilter);
				isInRole = isInUserRole(itPerson, roleFilter);
			} else {
				isInRole = true;
				isInDept = true;
			}

			if (!isInDept || !isInRole) {
				it.remove();
			}
		}

		return allPersons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #hasPrcsPrivByDefault(com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess)
	 */
	@Override
	public boolean hasPrcsPrivByDefault(TeePerson person,
			TeeFlowProcess flowProcess) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #isInDepartment(com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.core.org.bean.TeeDepartment)
	 */
	@Override
	public boolean isInDepartment(TeePerson person, TeeDepartment deptFilter) {
		// TODO Auto-generated method stub
		boolean inDept = false;
		boolean inDeptOther = false;

		if (deptFilter == null) {
			return false;
		}

		// 判断用户是否直属于该部门
		TeeDepartment dept = person.getDept();
		if (dept != null) {
			if (deptFilter.getUuid() == dept.getUuid()) {
				inDept = true;
			}
		}

		// 判断用户辅助部门中是否存在于该部门
		List<TeeDepartment> deptList = person.getDeptIdOther();
		for (TeeDepartment deptOther : deptList) {
			if (deptFilter.getUuid() == deptOther.getUuid()) {
				inDeptOther = true;
				break;
			}
		}

		return inDept || inDeptOther;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #isInUserRole(com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.core.org.bean.TeeUserRole)
	 */
	@Override
	public boolean isInUserRole(TeePerson person, TeeUserRole roleFilter) {
		// TODO Auto-generated method stub
		boolean inRole = false;
		boolean inRoleOther = false;

		if (roleFilter == null) {
			return false;
		}

		// 判断当前人角色是否直属于该角色
		TeeUserRole role = person.getUserRole();
		if (role != null) {
			if (role.getUuid() == roleFilter.getUuid()) {
				inRole = true;
			}
		}

		List<TeeUserRole> roleList = person.getUserRoleOther();
		for (TeeUserRole userRole : roleList) {
			if (userRole.getUuid() == roleFilter.getUuid()) {
				inRoleOther = true;
				break;
			}
		}

		return inRole || inRoleOther;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #isInDepartment(com.tianee.oa.core.org.bean.TeePerson, java.util.Set)
	 */
	@Override
	public boolean isInDepartment(TeePerson person,
			Set<TeeDepartment> deptFilterList) {
		// TODO Auto-generated method stub
		boolean isInDept = false;
		for (TeeDepartment dept : deptFilterList) {
			isInDept = isInDepartment(person, dept);
			if (isInDept) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #isInUserRole(com.tianee.oa.core.org.bean.TeePerson, java.util.Set)
	 */
	@Override
	@Transactional(readOnly = true)
	public boolean isInUserRole(TeePerson person,
			Set<TeeUserRole> roleFilterList) {
		// TODO Auto-generated method stub
		boolean isInRole = false;
		for (TeeUserRole role : roleFilterList) {
			isInRole = isInUserRole(person, role);
			if (isInRole) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getPrcsUsersBySelectRule
	 * (com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun,
	 * com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs,
	 * com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess,
	 * com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<TeePerson> getPrcsUsersBySelectRule(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person) {
		List<TeeSelectUserRule> ruleList = nextPrcs.getSelectRules();
		Set<TeePerson> persons = new HashSet<TeePerson>();

		// 根据规则选人
		for (TeeSelectUserRule rule : ruleList) {
			persons.addAll(getPrcsUsersByRule(flowRun, frp, nextPrcs, person,
					rule));
		}

		return persons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getPrcsUsersByRule(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun,
	 * com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs,
	 * com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess,
	 * com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.core.workflow.flowmanage.bean.TeeSelectUserRule)
	 */
	@Override
	@Transactional(readOnly = true)
	public Set<TeePerson> getPrcsUsersByRule(TeeFlowRun flowRun,
			TeeFlowRunPrcs frp, TeeFlowProcess nextPrcs, TeePerson person,
			TeeSelectUserRule rule) {
		// TODO Auto-generated method stub
		Set<TeePerson> persons = new HashSet<TeePerson>();

		int userType = rule.getUserType();

		switch (userType) {
		case 1:// 流程发起人
			persons.addAll(selectUserRuleService.leadByBeginUser(flowRun, frp,
					nextPrcs, person, rule));
			break;
		case 2:// 当前办理人
			persons.addAll(selectUserRuleService.leadByCurrentPrcsUser(flowRun,
					frp, nextPrcs, person, rule));
			break;
		case 3:// 该流程所有经办人
			persons.addAll(selectUserRuleService.leadByAllPrcsUser(flowRun,
					frp, nextPrcs, person, rule));
			break;
		case 4:// 本步骤经办人
			persons.addAll(selectUserRuleService.leadByThisPrcsUsers(flowRun,
					frp, nextPrcs, person, rule));
			break;
		case 5:// 上一步骤经办人
			persons.addAll(selectUserRuleService.leadByPrePrcsUsers(flowRun,
					frp, nextPrcs, person, rule));
			break;
		}

		// 通过角色过滤
		TeeUserRole role = rule.getUserRole();
		if (role != null) {
			TeePerson p = null;
			TeeUserRole r = null;
			if (role != null) {
				Iterator<TeePerson> it = persons.iterator();
				while (it.hasNext()) {
					p = it.next();
					r = p.getUserRole();
					if (r == null || r.getUuid() != role.getUuid()) {
						it.remove();
					}
				}
			}
		}

		return persons;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #setSimpleDaoSupport(com.tianee.webframe.dao.TeeSimpleDaoSupport)
	 */
	@Override
	public void setSimpleDaoSupport(TeeSimpleDaoSupport simpleDaoSupport) {
		this.simpleDaoSupport = simpleDaoSupport;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #setPersonService(com.tianee.oa.core.org.service.TeePersonService)
	 */
	@Override
	public void setPersonService(TeePersonService personService) {
		this.personService = personService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #setSelectUserRuleService(com.tianee.oa.core.workflow.flowmanage.service.
	 * TeeSelectUserRuleServiceInterface)
	 */
	@Override
	public void setSelectUserRuleService(
			TeeSelectUserRuleServiceInterface selectUserRuleService) {
		this.selectUserRuleService = selectUserRuleService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getCreatablePrivFlowListByPerson(com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List<TeeFlowType> getCreatablePrivFlowListByPerson(TeePerson person) {
		// TODO Auto-generated method stub
		return flowTypeDao.getCreatablePrivFlowListByPerson(person);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getHandableFlowType2SelectCtrl(com.tianee.oa.core.org.bean.TeePerson,
	 * java.util.Map)
	 */
	@Override
	public List<TeeZTreeModel> getHandableFlowType2SelectCtrl(TeePerson person,
			Map requestMap) {
		// TODO Auto-generated method stub
		int flowSortId = TeeStringUtil.getInteger(requestMap.get("flowSortId"),
				0);
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();
		person = personService.selectByUuid(person.getUuid() + "");

		List<TeeFlowType> typeList = getCreatablePrivFlowListByPerson(person);

		String queryStr = "";
		if (flowSortId > 0) {
			queryStr = " where sid=" + flowSortId;
		}

		List<TeeFlowSort> sortList = simpleDaoSupport.find("from TeeFlowSort "
				+ queryStr + " order by orderNo asc", null);

		if (flowSortId > 0) {
			TeeZTreeModel m1 = new TeeZTreeModel();
			m1.setName("全部流程");
			m1.setTitle("全部流程");
			m1.setId("0");
			list.add(m1);
			m1.setIconSkin("wfNode2");

			for (TeeFlowSort fs : sortList) {
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId("sort" + fs.getSid());
				m.setName(fs.getSortName());
				m.setTitle(fs.getSortName());
				m.setDisabled(true);
				m.setOpen(true);
				list.add(m);
				m.setIconSkin("wfNode1");
			}
			for (TeeFlowType ft : typeList) {
				if (ft.getFlowSort() == null
						|| ft.getFlowSort().getSid() != flowSortId) {
					continue;
				}
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(ft.getSid()));
				m.setName(ft.getFlowName());
				m.setTitle(ft.getFlowName());
				m.setpId("sort"
						+ (ft.getFlowSort() == null ? 0 : ft.getFlowSort()
								.getSid()));
				list.add(m);
				m.setIconSkin("wfNode2");
			}
		} else {
			TeeZTreeModel m1 = new TeeZTreeModel();
			m1.setName("全部流程");
			m1.setTitle("全部流程");
			m1.setId("0");
			list.add(m1);
			m1.setIconSkin("wfNode2");

			TeeZTreeModel m2 = new TeeZTreeModel();
			m2.setId("sort" + 0);
			m2.setName("默认分类");
			m2.setTitle("默认分类");
			m2.setDisabled(true);
			m2.setOpen(true);
			list.add(m2);
			m2.setIconSkin("wfNode1");

			for (TeeFlowSort fs : sortList) {
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId("sort" + fs.getSid());
				m.setName(fs.getSortName());
				m.setTitle(fs.getSortName());
				m.setDisabled(true);
				m.setOpen(true);
				list.add(m);
				m.setIconSkin("wfNode1");
			}
			for (TeeFlowType ft : typeList) {
				TeeZTreeModel m = new TeeZTreeModel();
				m.setId(String.valueOf(ft.getSid()));
				m.setName(ft.getFlowName());
				m.setTitle(ft.getFlowName());
				m.setpId("sort"
						+ (ft.getFlowSort() == null ? 0 : ft.getFlowSort()
								.getSid()));
				list.add(m);
				m.setIconSkin("wfNode2");
			}
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getFlowType2SelectCtrl()
	 */
	@Override
	public List<TeeZTreeModel> getFlowType2SelectCtrl() {
		// TODO Auto-generated method stub
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();

		List<TeeFlowType> typeList = simpleDaoSupport.find(
				"from TeeFlowType order by orderNo asc", null);

		List<TeeFlowSort> sortList = simpleDaoSupport.find(
				"from TeeFlowSort order by orderNo asc", null);

		TeeZTreeModel m1 = new TeeZTreeModel();
		m1.setName("全部流程");
		m1.setTitle("全部流程");
		m1.setId("0");
		list.add(m1);
		m1.setIconSkin("wfNode2");

		TeeZTreeModel m2 = new TeeZTreeModel();
		m2.setId("sort" + 0);
		m2.setName("默认分类");
		m2.setTitle("默认分类");
		m2.setDisabled(true);
		m2.setOpen(true);
		list.add(m2);
		m2.setIconSkin("wfNode1");

		for (TeeFlowSort fs : sortList) {
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId("sort" + fs.getSid());
			m.setName(fs.getSortName());
			m.setTitle(fs.getSortName());
			m.setDisabled(true);
			m.setOpen(true);
			list.add(m);
			m.setIconSkin("wfNode1");
		}

		for (TeeFlowType ft : typeList) {
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId(String.valueOf(ft.getSid()));
			m.setName(ft.getFlowName());
			m.setTitle(ft.getFlowName());
			m.setpId("sort"
					+ (ft.getFlowSort() == null ? 0 : ft.getFlowSort().getSid()));
			list.add(m);
			m.setIconSkin("wfNode2");
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getCreatablePrivFlowListModelsByPerson
	 * (com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List<Map> getCreatablePrivFlowListModelsByPerson(TeePerson person) {
		// TODO Auto-generated method stub
		List<Map> list = new ArrayList<Map>();
		List<TeeFlowType> typeList = getCreatablePrivFlowListByPerson(person);

		List<TeeFlowSort> sortList = flowTypeSortDao.list();
		for (TeeFlowSort fs : sortList) {
			Map fsMap = new HashMap();
			fsMap.put("sortName", fs.getSortName());
			fsMap.put("sortId", fs.getSid());
			List<Map> ftList = new ArrayList<Map>();
			fsMap.put("flowTypes", ftList);

			for (TeeFlowType ft : typeList) {
				if (ft.getFlowSort() != null
						&& ft.getFlowSort().getSid() == fs.getSid()) {
					Map ftMap = new HashMap();
					ftMap.put("flowName", ft.getFlowName());
					ftMap.put("flowId", ft.getSid());
					ftMap.put("hasDoc", ft.getHasDoc());
					ftMap.put("runNamePriv", ft.getRunNamePriv());
					if (ft.getForm() == null) {
						continue;
					}
					TeeForm form = formService.getLatestVersion(ft.getForm());
					ftMap.put("formId", form.getSid());
					ftMap.put("type", ft.getType());
					ftMap.put("comment", ft.getComment());
					ftList.add(ftMap);
				}
			}
			if (ftList.size() == 0) {
				continue;
			}
			list.add(fsMap);
		}

		// 增加默认分类节点
		Map fsMap = new HashMap();
		fsMap.put("sortName", "默认分类");
		fsMap.put("sortId", 0);
		List<Map> ftList = new ArrayList<Map>();
		fsMap.put("flowTypes", ftList);
		for (TeeFlowType ft : typeList) {
			if (ft.getFlowSort() == null) {
				Map ftMap = new HashMap();
				ftMap.put("flowName", ft.getFlowName());
				ftMap.put("flowId", ft.getSid());
				ftMap.put("hasDoc", ft.getHasDoc());
				ftMap.put("runNamePriv", ft.getRunNamePriv());
				TeeForm form = formService.getLatestVersion(ft.getForm());
				ftMap.put("formId", form.getSid());
				ftMap.put("type", ft.getType());
				ftMap.put("comment", ft.getComment());
				ftList.add(ftMap);
			}
		}
		list.add(fsMap);

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #isInPersons(com.tianee.oa.core.org.bean.TeePerson, java.util.Set)
	 */
	@Override
	public boolean isInPersons(TeePerson person, Set<TeePerson> persons) {
		// TODO Auto-generated method stub
		if (persons == null) {
			return false;
		}

		for (TeePerson p : persons) {
			if (p.getUuid() == person.getUuid()) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #quickSearch(java.lang.String, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List<Map> quickSearch(String psnName, TeePerson person) {
		List<Map> list = new ArrayList();

		String hql = "from TeeFlowRun fr where (fr.runId="
				+ TeeStringUtil.getInteger(psnName, 0)
				+ " "
				+ ("".equals(psnName) ? "" : " or fr.runName like '%" + psnName
						+ "%'")
				+ ") and exists (select 1 from TeeFlowRunPrcs frp where frp.flowRun=fr and frp.prcsUser.uuid="
				+ person.getUuid() + ") order by fr.runId desc";
		List<TeeFlowRun> frList = simpleDaoSupport.find(hql, null);

		for (TeeFlowRun fr : frList) {
			Map map = new HashMap();
			map.put("runName", fr.getRunName());
			map.put("runId", fr.getRunId());
			map.put("flowId", fr.getFlowType().getSid());
			map.put("time", TeeDateUtil.format(fr.getBeginTime()));
			list.add(map);
		}

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #saveCtrlFeedback(int, int, java.lang.String, java.lang.String,
	 * java.lang.String, com.tianee.oa.core.org.bean.TeePerson,
	 * java.lang.String, java.lang.String, int, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void saveCtrlFeedback(int runId, int itemId, String content,
			String signData, String rand, TeePerson loginUser, String sealData,
			String hwData, int frpSid, String picData, String h5Data,
			String mobiData) {
		TeeFlowRunCtrlFeedback ctrlFeedback = new TeeFlowRunCtrlFeedback();
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(
				TeeFlowRun.class, runId);
		TeePerson person = personDao.selectPersonById(loginUser.getUuid());

		// 根据frpSid 获取所属设计步骤
		TeeFlowRunPrcs frp = (TeeFlowRunPrcs) simpleDaoSupport.get(
				TeeFlowRunPrcs.class, frpSid);

		TeeFlowProcess process = null;
		if (frp != null) {
			process = frp.getFlowPrcs();
		}

		ctrlFeedback.setFlowPrcs(process);
		ctrlFeedback.setPrcsId(frp.getPrcsId());
		ctrlFeedback.setContent(content);
		ctrlFeedback.setCreateUser(person);
		ctrlFeedback.setFlowRun(flowRun);
		ctrlFeedback.setItemId(itemId);
		ctrlFeedback.setSignData(signData);
		ctrlFeedback.setRand(rand);
		ctrlFeedback.setSealData(sealData);
		ctrlFeedback.setHwData(hwData);
		ctrlFeedback.setUserName(person.getUserName());
		ctrlFeedback.setDeptName(person.getDept().getDeptName());
		ctrlFeedback.setDeptNamePath(person.getDept().getDeptFullName());
		ctrlFeedback.setRoleName(person.getUserRole().getRoleName());
		ctrlFeedback.setPicData(picData);
		ctrlFeedback.setH5Data(h5Data);
		ctrlFeedback.setMobiData(mobiData);
		String base64 = getBase64(person);
		List<TeeFlowRunCtrlFeedback> find = simpleDaoSupport
				.find("from TeeFlowRunCtrlFeedback where prcsId=? and createUser.uuid=? and flowRun.runId= ? ",
						new Object[] { frp.getPrcsId(), person.getUuid(), runId });
		if (find != null && find.size() > 0) {
			TeeFlowRunCtrlFeedback feedback = find.get(0);
			feedback.setContent(content);
			if (!"".equals(base64)) {
				feedback.setSignature(base64);
			}
			simpleDaoSupport.update(feedback);
		} else {
			ctrlFeedback.setSignature(base64);
			simpleDaoSupport.save(ctrlFeedback);
		}
	}

	public String getBase64(TeePerson person) {
		String encode = "";
		if (person != null) {
			TeeAttachment attach = person.getAttach();
			if (attach != null && attach.getSid() > 0) {
				InputStream in = null;
				byte[] data1 = null;
				// 读取图片字节数组
				try {
					in = new FileInputStream(attach.getFilePath());
					data1 = new byte[in.available()];
					in.read(data1);
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 对字节数组Base64编码
				BASE64Encoder encoder1 = new BASE64Encoder();
				if (data1 != null) {
					encode = encoder1.encode(data1);// 返回Base64编码过的字节数组字符串
					encode = "data:image/png;base64,"
							+ encode.replaceAll("\r|\n", "");
				}
			}
		}
		return encode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getCtrlFeedbacks(int, int, int, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map> getCtrlFeedbacks(int runId, int itemId, int sortField,
			String order) {
		String hql = "from TeeFlowRunCtrlFeedback c where c.flowRun.runId="
				+ runId + " and c.itemId=" + itemId;

		if (TeeUtility.isNullorEmpty(order)) {// order是空的话 默认asc
			order = "asc";
		}

		if (sortField == 1) {// 按照会签时间排序
			hql += " order by c.sid " + order + " ";
		} else if (sortField == 2) {// 按照用户排序号排序
			hql += " order by c.createUser.userNo " + order + " ";
		} else if (sortField == 3) {// 角色排序号
			hql += " order by c.createUser.userRole.roleNo " + order + " ";
		} else if (sortField == 4) {// 部门排序号
			hql += " order by c.createUser.dept.deptNo " + order + " ";
		}

		List<TeeFlowRunCtrlFeedback> feedbacks = simpleDaoSupport.find(hql,
				null);
		List<Map> list = new ArrayList<Map>();
		for (TeeFlowRunCtrlFeedback feedback : feedbacks) {
			Map data = new HashMap();
			data.put("sid", feedback.getSid());
			data.put("runId", feedback.getFlowRun().getRunId());
			data.put("content", feedback.getContent());
			data.put("signData", feedback.getSignData());
			data.put("sealData", feedback.getSealData());
			data.put("hwData", feedback.getHwData());
			data.put("picData", feedback.getPicData());
			data.put("h5Data", feedback.getH5Data());
			data.put("mobiData", feedback.getMobiData());
			data.put("prcsId", feedback.getPrcsId());
			if (feedback.getCreateTime() != null) {
				/*
				 * data.put("createTime", TeeDateUtil.format(feedback
				 * .getCreateTime().getTime(), "yyyy年MM月dd日"));
				 */
				data.put("createTime", feedback.getCreateTime().getTime());
			} else {
				data.put("createTime", null);
			}
			if (feedback.getCreateUser() != null) {
				data.put("userSid", feedback.getCreateUser().getUuid());
				data.put("userId", feedback.getCreateUser().getUserId());
			}
			data.put("userName", feedback.getUserName());
			data.put("deptName", feedback.getDeptName());
			data.put("deptNamePath", feedback.getDeptNamePath());
			data.put("roleName", feedback.getRoleName());
			data.put("rand", feedback.getRand());
			data.put("signature", feedback.getSignature());

			list.add(data);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #delCtrlFeedbacks(int)
	 */
	@Override
	public void delCtrlFeedbacks(int sid) {
		simpleDaoSupport.delete(TeeFlowRunCtrlFeedback.class, sid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #suspend(int)
	 */
	@Override
	public void suspend(int frpSid) {
		simpleDaoSupport.executeUpdate(
				"update TeeFlowRunPrcs frp set frp.suspend=1 where frp.sid="
						+ frpSid, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #unsuspend(int)
	 */
	@Override
	public void unsuspend(int frpSid) {
		simpleDaoSupport.executeUpdate(
				"update TeeFlowRunPrcs frp set frp.suspend=0 where frp.sid="
						+ frpSid, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #exportExcel(java.util.Map, com.tianee.oa.core.org.bean.TeePerson,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void exportExcel(Map params, TeePerson loginUser,
			HttpServletResponse response) {
		List resultList = new ArrayList();

		String type = TeeStringUtil.getString(params.get("type"));
		if ("1".equals(type)) {
			resultList = getReceivedWorksList(params, loginUser);
		} else if ("2".equals(type)) {
			resultList = getHandledWorksList(params, loginUser);
		} else if ("4".equals(type)) {
			resultList = getConcernedWorksList(params, loginUser);
		} else if ("3".equals(type)) {
			resultList = getViewsWorksList(params, loginUser);
		} else if ("5".equals(type)) {
			resultList = getSuspendedWorksList(params, loginUser);
		}

		// 获取当前时间
		Calendar c = Calendar.getInstance();
		String time = "[" + c.get(Calendar.YEAR) + (c.get(Calendar.MONTH) + 1)
				+ c.get(Calendar.DAY_OF_MONTH) + c.get(Calendar.HOUR_OF_DAY)
				+ c.get(Calendar.MINUTE) + "]";

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
			style1.setFont(font1);

			HSSFCell cell = row.createCell((short) 0);
			List<String> head = new ArrayList<String>();
			// 设置表头

			if ("1".equals(type) || "2".equals(type) || "5".equals(type)) {
				cell.setCellValue("流水号");
				cell.setCellStyle(style);
				cell = row.createCell((short) (1));

				cell.setCellValue("流程名称");
				cell.setCellStyle(style);
				cell = row.createCell((short) (2));

				cell.setCellValue("当前步骤");
				cell.setCellStyle(style);
				cell = row.createCell((short) (3));

				cell.setCellValue("发起人");
				cell.setCellStyle(style);
				cell = row.createCell((short) (4));

				cell.setCellValue("接收时间");
				cell.setCellStyle(style);
				cell = row.createCell((short) (5));

				head.add("runId");
				head.add("flowName");
				head.add("prcsDesc");
				head.add("beginPerson");
				head.add("beginTime");
			} else {
				cell.setCellValue("流水号");
				cell.setCellStyle(style);
				cell = row.createCell((short) (1));

				cell.setCellValue("流程名称");
				cell.setCellStyle(style);
				cell = row.createCell((short) (2));

				cell.setCellValue("发起人");
				cell.setCellStyle(style);
				cell = row.createCell((short) (3));

				cell.setCellValue("接收时间");
				cell.setCellStyle(style);
				cell = row.createCell((short) (4));

				head.add("runId");
				head.add("flowName");
				// head.add("prcsDesc");
				head.add("beginPerson");
				head.add("beginTime");
			}
			// 设置内容
			Map map = new HashMap();
			for (int m = 0; m < resultList.size(); m++) {
				HSSFRow r = sheet.createRow((int) (m + 1));
				map = (Map) resultList.get(m);
				for (int n = 0; n < head.size(); n++) {
					cell = r.createCell((short) (n));
					cell.setCellValue(TeeStringUtil.getString(map.get(head
							.get(n))));
					cell.setCellStyle(style1);
				}
			}

			// 设置每一列的宽度
			sheet.setDefaultColumnWidth(10);
			String fileName = "列表信息" + time + ".xls";
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

	// 获取挂起工作列表
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getSuspendedWorksList(java.util.Map,
	 * com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List getSuspendedWorksList(Map queryParams, TeePerson loginUser) {
		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题
		String runIds = TeeStringUtil.getString(queryParams.get("runIds"));// 勾选的
		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.TOP_FLAG, "
					+ "frp.FLAG, " + "frp.FLOW_PRCS, " + "frp.PRCS_ID, "
					+ "fr.FLOW_ID, " + "fr.RUN_NAME, " + "fr.BEGIN_PERSON, "
					+ "doc.DOC_ATTACH_ID, " + "fr.RUN_ID,";

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
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");
			sql.append(" and fr.FLOW_ID=" + flowId);

		} else {

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, " + "frp.FLAG, " + "frp.FLOW_PRCS, "
					+ "fr.FLOW_ID, " + "fr.RUN_NAME, " + "fr.RUN_ID, "
					+ "fr.BEGIN_PERSON, " + "doc.DOC_ATTACH_ID, "
					+ "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");
		}

		if (!("0").equals(runIds)) {
			sql.append(" and fr.RUN_ID in (" + runIds + ")");
		}
		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		}

		if (flowSortId > 0) {
			sql.append(" and ft.SORT_ID=" + flowSortId);
		}

		// 流程状态
		if (status == 1) {// 超时
			sql.append(" and frp.TIMEOUT_FLAG=1");
		} else if (status == 2) {// 未超时
			sql.append(" and frp.TIMEOUT_FLAG=2");
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and fr.END_TIME is null and frp.PRCS_USER="
				+ loginUser.getUuid() + " and frp.suspend=1");

		SQLQuery query = session.createSQLQuery(select + sql.toString()
				+ " order by fr.RUN_ID desc");

		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));
		if (!"".equals(SQL_)) {
			sql.append(" and (" + SQL_ + ")");
		}

		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp beginTime = null;
		for (Map fr : list) {
			Map map = new HashMap();
			beginTime = (Timestamp) fr.get("BEGIN_TIME");
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime", fr.get("BEGIN_TIME") == null ? ""
					: TeeDateUtil.format(new Date(beginTime.getTime())));
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : fr.get("END_TIME")
							.toString());
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("frpSid", fr.get("FRP_SID"));
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow, fr.get("RUN_ID")
							.toString()));
			map.put("doc",
					attachmentService.getModelById(TeeStringUtil.getInteger(
							fr.get("DOC_ATTACH_ID"), 0)));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());

			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));
			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */
			if (topFlag == 0) {// 经办
				map.put("prcsHandle", "会签");
			} else {// 主办
				map.put("opHandle", "主办");
			}

			if (ftTmp.getDelegate() != 0 && topFlag == 1) {
				map.put("delegate", "委托");
			}
			map.put("suspend", "挂起");
			map.put("doExport", "导出");
			if (TeeStringUtil.getInteger(fr.get("PRCS_ID"), 0) == 1) {
				map.put("doDelete", "删除");
			}

			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}

			resultList.add(map);
		}
		return resultList;
	}

	// 获取我已办结的流程列表信息
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getHandledWorksList(java.util.Map,
	 * com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List getHandledWorksList(Map queryParams, TeePerson loginUser) {

		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		String flowIds = TeeStringUtil.getString(queryParams.get("flowIds"));// 流程ID串
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		String runIds = TeeStringUtil.getString(queryParams.get("runIds"));// 勾选的

		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.TOP_FLAG, "
					+ "frp.FLAG, " + "frp.FLOW_PRCS, " + "fr.LEVEL_, "
					+ "frp.PRCS_ID, " + "frp.BACK_FLAG, " + "fr.FLOW_ID, "
					+ "fr.END_TIME as ETIME, " + "fr.SEND_FLAG, "
					+ "fr.RUN_NAME, " + "fr.BEGIN_PERSON, "
					+ "doc.DOC_ATTACH_ID, " + "fr.RUN_ID,";

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
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID");
			sql.append(" and fr.FLOW_ID=" + flowId);

		} else {

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, " + "frp.FLAG, " + "frp.FLOW_PRCS, "
					+ "frp.FROM_USER, " + "frp.OTHER_USER, "
					+ "frp.BACK_FLAG, " + "fr.FLOW_ID, " + "fr.SEND_FLAG, "
					+ "fr.LEVEL_, " + "fr.END_TIME as ETIME, "
					+ "fr.RUN_NAME, " + "fr.RUN_ID, " + "fr.BEGIN_PERSON, "
					+ "doc.DOC_ATTACH_ID, " + "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID");

			if (!"".equals(flowIds)) {
				sql.append(" and " + TeeDbUtility.IN("fr.FLOW_ID", flowIds));
			}
		}

		sql.append(" and frp.PRCS_ID=(select max(frp1.PRCS_ID) from FLOW_RUN_PRCS frp1 where frp.RUN_ID=frp1.RUN_ID and frp.PRCS_USER=frp1.PRCS_USER AND frp1.flag IN (3, 4) and frp1.PRCS_USER="
				+ loginUser.getUuid() + ")");

		if (!("0").equals(runIds)) {
			sql.append(" and fr.RUN_ID in (" + runIds + ")");
		}
		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		}
		if (flowSortId > 0) {
			sql.append(" and ft.SORT_ID=" + flowSortId);
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and (frp.flag in (3,4) or fr.end_time is not null)");

		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));
		if (!"".equals(SQL_)) {
			sql.append(" and (" + SQL_ + ")");
		}

		SQLQuery query = session.createSQLQuery(select + sql.toString()
				+ " order by fr.run_id desc");
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp time = null;
		for (Map fr : list) {
			Map map = new HashMap();
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime",
					fr.get("BEGIN_TIME") == null ? "" : fr.get("BEGIN_TIME")
							.toString());
			time = (Timestamp) fr.get("END_TIME");
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : TeeDateUtil
							.format(new Date(time.getTime())));
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("frpSid", fr.get("FRP_SID"));
			map.put("level", fr.get("LEVEL_"));
			map.put("backFlag", fr.get("BACK_FLAG"));
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow, fr.get("RUN_ID")
							.toString()));
			map.put("doc",
					attachmentService.getModelById(TeeStringUtil.getInteger(
							fr.get("DOC_ATTACH_ID"), 0)));

			time = (Timestamp) fr.get("ETIME");
			map.put("endFlag", time);

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());
			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));
			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */

			map.put("doExport", "导出");

			List noReceivedDelegate = frpDao
					.getNoReceivedDelegatedWorks(TeeStringUtil.getInteger(
							fr.get("FRP_SID"), 0));

			if (noReceivedDelegate.size() != 0) {
				boolean hasReceive = false;
				for (int i = 0; i < noReceivedDelegate.size(); i++) {
					Object obj[] = (Object[]) noReceivedDelegate.get(i);
					if (!obj[1].toString().equals("1")) {
						hasReceive = true;
						break;
					}
				}
				if (!hasReceive) {
					map.put("doBackDelegate", "收回委托");
				}
			} else {
				if (TeeStringUtil.getInteger(fr.get("FLAG"), 0) == 3
						&& TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0) == 1) {
					map.put("doBack", "收回");
				}
			}

			if (TeeStringUtil.getInteger(fr.get("FLAG"), 0) == 3) {
				// map.put("doBack", "收回");
				map.put("doUrge", "催办");
			}

			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}
			resultList.add(map);
		}
		return resultList;
	}

	// 获取已关注的流程列表
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getConcernedWorksList(java.util.Map,
	 * com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List getConcernedWorksList(Map queryParams, TeePerson loginUser) {

		List params = new ArrayList();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		String runIds = TeeStringUtil.getString(queryParams.get("runIds"));// 勾选的

		String selectCount = "select count(*) ";

		StringBuffer sql = new StringBuffer();
		sql.append("from TeeFlowRun fr where fr.isSave=1 and exists (select 1 from TeeFlowRunConcern frc where frc.flowRun=fr and frc.person.uuid="
				+ loginUser.getUuid() + ") ");

		if (flowId != 0) {
			sql.append(" and fr.flowType.sid=" + flowId);
		}

		if (!("0").equals(runIds)) {
			sql.append(" and fr.runId in (" + runIds + ")");
		}

		if (runId != 0) {
			sql.append(" and fr.runId=" + runId);
		}

		if (!"".equals(runName)) {
			sql.append(" and fr.runName like ?");
			params.add("%" + runName + "%");
		}
		if (flowSortId > 0) {
			sql.append(" and fr.flowType.flowSort.sid=" + flowSortId);
		}

		String _order = " order by fr.runId desc ";

		List<TeeFlowRun> list = simpleDaoSupport.executeQuery(sql.toString()
				+ _order, params.toArray());
		// pageFindByList(sql.toString()+_order, 0, Integer.MAX_VALUE, params);
		long total = simpleDaoSupport.count(selectCount + sql.toString(),
				params.toArray());

		List<Map> maps = new ArrayList();
		TeeFlowRunDoc doc = null;
		for (TeeFlowRun fr : list) {
			Map map = new HashMap();
			map.put("runId", fr.getRunId());
			map.put("flowId", fr.getFlowType().getSid());
			map.put("beginTime", TeeDateUtil.format(fr.getBeginTime()));
			map.put("endTime", TeeDateUtil.format(fr.getEndTime()));
			map.put("runName", fr.getRunName());
			map.put("flowName", fr.getFlowType().getFlowName());
			map.put("beginPerson", fr.getBeginPerson().getUserName());
			map.put("endFlag", fr.getEndTime());
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow,
					String.valueOf(fr.getRunId())));
			doc = flowRunDocService.getFlowRunDocByRunId(fr.getRunId());
			if (doc != null) {
				map.put("doc", attachmentService.getModelById(doc
						.getDocAttach().getSid()));
			} else {
				map.put("doc", null);
			}

			map.put("doExport", 1);
			map.put("doExport", 1);

			maps.add(map);
		}
		return maps;
	}

	// 我的查阅
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getViewsWorksList(java.util.Map, com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List getViewsWorksList(Map queryParams, TeePerson loginUser) {
		List params = new ArrayList();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		String runIds = TeeStringUtil.getString(queryParams.get("runIds"));// 勾选的

		String selectCount = "select count(*) ";

		StringBuffer sql = new StringBuffer();
		sql.append("from TeeFlowRunView frv where frv.viewPerson.uuid="
				+ loginUser.getUuid()
				+ " and exists (select 1 from TeeFlowRun fr where fr.runId=frv.flowRun and fr.isSave=1 )");

		if (flowId != 0) {
			sql.append(" and frv.flowRun.flowType.sid=" + flowId);
		}

		if (!("0").equals(runIds)) {
			sql.append(" and frv.flowRun.runId in (" + runIds + ")");
		}

		if (runId != 0) {
			sql.append(" and frv.flowRun.runId=" + runId);
		}

		if (!"".equals(runName)) {
			sql.append(" and frv.flowRun.runName like ?");
			params.add("%" + runName + "%");
		}
		if (flowSortId > 0) {
			sql.append(" and frv.flowRun.flowType.flowSort.sid=" + flowSortId);
		}
		String _order = " order by frv.flowRun.runId desc ";

		List<TeeFlowRunView> list = simpleDaoSupport.executeQuery(
				sql.toString() + _order, params.toArray());
		// sql.toString() + _order, firstResult, pageSize, params);
		long total = simpleDaoSupport.count(selectCount + sql.toString(),
				params.toArray());

		List<Map> maps = new ArrayList();
		TeeFlowRunDoc doc = null;
		TeeFlowRun fr = null;
		for (TeeFlowRunView frv : list) {
			Map map = new HashMap();
			fr = frv.getFlowRun();
			map.put("runId", fr.getRunId());
			map.put("flowId", fr.getFlowType().getSid());
			map.put("beginTime", TeeDateUtil.format(fr.getBeginTime()));
			map.put("endTime", TeeDateUtil.format(fr.getEndTime()));
			map.put("runName", fr.getRunName());
			map.put("flowName", fr.getFlowType().getFlowName());
			map.put("beginPerson", fr.getBeginPerson().getUserName());
			map.put("viewFlag", frv.getViewFlag());
			map.put("endFlag", frv.getFlowRun().getEndTime());
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow,
					String.valueOf(fr.getRunId())));
			doc = flowRunDocService.getFlowRunDocByRunId(fr.getRunId());
			if (doc != null) {
				map.put("doc", attachmentService.getModelById(doc
						.getDocAttach().getSid()));
			} else {
				map.put("doc", null);
			}

			map.put("doExport", 1);

			maps.add(map);
		}

		return maps;

	}

	// 获取我的待办的流程列表
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getReceivedWorksList(java.util.Map,
	 * com.tianee.oa.core.org.bean.TeePerson)
	 */
	@Override
	public List getReceivedWorksList(Map queryParams, TeePerson loginUser) {
		Session session = frpDao.getSession();
		Map<String, Object> parameters = new HashMap<String, Object>();
		// 获取查询条件
		int flowSortId = TeeStringUtil.getInteger(
				queryParams.get("flowSortId"), 0);// 流程类型分类id，为0则是全部分类

		int flowId = TeeStringUtil.getInteger(queryParams.get("flowId"), 0);// 流程id，为0则是全部流程
		String flowIds = TeeStringUtil.getString(queryParams.get("flowIds"));// 流程ID串
		int status = TeeStringUtil.getInteger(queryParams.get("status"), 0);// 0：全部
																			// 1：已超时，2：未超时
		int runId = TeeStringUtil.getInteger(queryParams.get("runId"), 0);// 流水号
		String runName = TeeStringUtil.getString(queryParams.get("runName"));// 流程标题

		String runIds = TeeStringUtil.getString(queryParams.get("runIds"));// 勾选的
		String selectCount = "select count(*) ";

		// 查询字段模型
		String queryFieldModel = null;

		// 声明查询语句
		StringBuffer sql = new StringBuffer();
		String select = "";
		List<String> datas = new ArrayList<String>();
		TeeFlowType ft = null;
		if (flowId != 0) {// 指定流程

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.TOP_FLAG, "
					+ "frp.FLAG, " + "frp.FLOW_PRCS, " + "frp.TIMEOUT_FLAG, "
					+ "frp.PRCS_ID, " + "frp.BACK_FLAG, " + "fr.FLOW_ID, "
					+ "fr.RUN_NAME, " + "fr.SEND_FLAG, "
					+ "fr.END_TIME as ETIME, " + "fr.BEGIN_PERSON, "
					+ "fr.LEVEL_, " + "doc.DOC_ATTACH_ID, " + "fp.PRCS_TYPE,"
					+ "fr.RUN_ID,";

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
							select += "frd." + fi.getName() + ",";
							break;
						}
					}
				}
			}
			select += "ft.FLOW_NAME ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft,tee_f_r_d_"
					+ flowId
					+ " frd where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and frd.RUN_ID=fr.RUN_ID and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");
			sql.append(" and fr.FLOW_ID=" + flowId);

		} else {// 全部流程

			select = "select frp.SID as FRP_SID," + "frp.BEGIN_TIME,"
					+ "frp.CREATE_TIME, " + "frp.END_TIME, " + "frp.PRCS_ID, "
					+ "frp.TOP_FLAG, " + "frp.FLAG, " + "frp.FLOW_PRCS, "
					+ "frp.TIMEOUT_FLAG, " + "frp.BACK_FLAG, " + "fr.FLOW_ID, "
					+ "fr.END_TIME as ETIME, " + "fr.RUN_NAME, "
					+ "fr.RUN_ID, " + "fr.SEND_FLAG, " + "fr.BEGIN_PERSON, "
					+ "fr.LEVEL_, " + "ft.FLOW_NAME, " + "fp.PRCS_TYPE,"
					+ "doc.DOC_ATTACH_ID ";

			sql.append("from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 ");

			if (!"".equals(flowIds)) {
				sql.append(" and " + TeeDbUtility.IN("fr.FLOW_ID", flowIds));
			}
		}

		if (!("0").equals(runIds)) {
			sql.append(" and fr.RUN_ID  in (" + runIds + ")");

		}
		if (runId != 0) {
			sql.append(" and fr.RUN_ID=" + runId);
		}
		if (!"".equals(runName)) {
			sql.append(" and fr.RUN_NAME like '%" + runName + "%'");
		}
		// String queryStr = "";
		if (flowSortId > 0) {
			sql.append(" and ft.SORT_ID=" + flowSortId);
		}

		// 流程状态
		if (status == 1) {// 超时
			sql.append(" and frp.TIMEOUT_FLAG=1");
		} else if (status == 2) {// 未超时
			sql.append(" and frp.TIMEOUT_FLAG=2");
		}

		/**
		 * 查询范围SQL语句组装
		 */

		sql.append(" and frp.flag in (1,2) and fr.END_TIME is null and frp.PRCS_USER="
				+ loginUser.getUuid() + " and frp.suspend=0");

		String SQL_ = TeeStringUtil.getString(queryParams.get("SQL_"));
		if (!"".equals(SQL_)) {
			sql.append(" and (" + SQL_ + ")");
		}

		SQLQuery query = session.createSQLQuery(select + sql.toString()
				+ " order by fr.LEVEL_ desc , fr.run_id desc");
		SQLQuery queryCount = session.createSQLQuery(selectCount
				+ sql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

		List<Map> list = query.list();
		long total = Long.parseLong(queryCount.uniqueResult().toString());

		TeePerson beginPerson = null;
		List<Map> resultList = new ArrayList<Map>();

		TeeFlowType ftTmp = null;
		TeeFlowProcess fp = null;
		int topFlag = 0;
		Timestamp beginTime = null;
		List<TeeAttachmentModel> attaches = null;
		TeeAttachmentModel doc = null;
		for (Map fr : list) {
			attaches = new ArrayList();
			Map map = new HashMap();
			beginTime = (Timestamp) fr.get("BEGIN_TIME");
			map.put("runId", TeeStringUtil.getInteger(fr.get("RUN_ID"), 0));
			map.put("flowId", TeeStringUtil.getInteger(fr.get("FLOW_ID"), 0));
			map.put("beginTime", fr.get("BEGIN_TIME") == null ? ""
					: TeeDateUtil.format(new Date(beginTime.getTime())));
			map.put("endTime",
					fr.get("END_TIME") == null ? "" : fr.get("END_TIME")
							.toString());
			map.put("runName", TeeStringUtil.getString(fr.get("RUN_NAME")));
			map.put("flowName", fr.get("FLOW_NAME"));
			map.put("flag", fr.get("FLAG"));
			map.put("frpSid", fr.get("FRP_SID"));
			map.put("timeoutFlag", fr.get("TIMEOUT_FLAG"));
			map.put("level", fr.get("LEVEL_"));
			map.put("backFlag", fr.get("BACK_FLAG"));
			map.put("attaches", attachmentService.getAttacheModels(
					TeeAttachmentModelKeys.workFlow, fr.get("RUN_ID")
							.toString()));
			map.put("doc",
					attachmentService.getModelById(TeeStringUtil.getInteger(
							fr.get("DOC_ATTACH_ID"), 0)));

			map.put("endFlag", fr.get("ETIME"));

			beginPerson = personDao.load(TeeStringUtil.getInteger(
					fr.get("BEGIN_PERSON"), 0));
			topFlag = TeeStringUtil.getInteger(fr.get("TOP_FLAG"), 0);
			map.put("beginPerson", beginPerson.getUserName());

			ftTmp = flowTypeDao.load(TeeStringUtil.getInteger(
					fr.get("FLOW_ID"), 0));
			if (TeeStringUtil.getInteger(fr.get("FLOW_PRCS"), 0) != 0) {
				fp = flowProcessDao.load(TeeStringUtil.getInteger(
						fr.get("FLOW_PRCS"), 0));
				map.put("prcsDesc",
						"第" + fr.get("PRCS_ID") + "步：" + fp.getPrcsName());
			} else {
				map.put("prcsDesc", "第" + fr.get("PRCS_ID") + "步");
			}

			/**
			 * 渲染操作
			 */
			if (topFlag == 0) {// 经办
				map.put("prcsHandle", "会签");
			} else {// 主办
				map.put("opHandle", "主办");
			}

			if (ftTmp.getDelegate() != 0 && topFlag == 1) {
				map.put("delegate", "委托");
			}
			map.put("suspend", "挂起");
			map.put("doExport", "导出");

			int prcsType = TeeStringUtil.getInteger(fr.get("PRCS_TYPE"), 0);

			// 获取回退标记
			int backFlag = TeeStringUtil.getInteger(fr.get("BACK_FLAG"), 0);
			if (TeeStringUtil.getInteger(fr.get("PRCS_ID"), 0) == 1
					|| (backFlag == 1 && prcsType == 1)) {
				map.put("doDelete", "删除");
			}
			if (ft != null) {
				for (String data : datas) {
					map.put(data, TeeStringUtil.clob2String(fr.get(data)));
				}
			}
			resultList.add(map);
		}
		return resultList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getMyBeginRunList(javax.servlet.http.HttpServletRequest,
	 * com.tianee.oa.core.org.bean.TeePerson,
	 * com.tianee.oa.webframe.httpModel.TeeDataGridModel)
	 */
	@Override
	public TeeEasyuiDataGridJson getMyBeginRunList(HttpServletRequest request,
			TeePerson person, TeeDataGridModel dm) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String runName = TeeStringUtil.getString(request
				.getParameter("runName"));
		int status = TeeStringUtil
				.getInteger(request.getParameter("status"), 0);
		String beginTimeStr = TeeStringUtil.getString(request
				.getParameter("beginTime"));
		String endTimeStr = TeeStringUtil.getString(request
				.getParameter("endTime"));
		Calendar beginTime = null;
		Calendar endTime = null;
		try {
			if (!TeeUtility.isNullorEmpty(beginTimeStr)) {
				Date date = sdf.parse(beginTimeStr + " 00:00:00");
				beginTime = Calendar.getInstance();
				beginTime.setTime(date);
			}

			if (!TeeUtility.isNullorEmpty(endTimeStr)) {
				Date date = sdf.parse(endTimeStr + " 23:59:59");
				endTime = Calendar.getInstance();
				endTime.setTime(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int firstResult = (dm.getPage() - 1) * dm.getRows();
		int pageSize = dm.getRows();
		String sort = dm.getSort();
		String order = dm.getOrder();

		if (sort == null || order == null) {
			sort = " runId";
			order = " desc";
		}
		List<Map> mapList = new ArrayList<Map>();
		List params = new ArrayList();
		String hql = " from TeeFlowRun where beginPerson.uuid=? and delFlag=0 ";
		params.add(person.getUuid());
		if (runId != 0) {
			hql += " and runId=?  ";
			params.add(runId);
		}
		if (!TeeUtility.isNullorEmpty(runName)) {
			hql += " and runName  like ?  ";
			params.add("%" + runName + "%");
		}

		if (status == 1) {// 执行中
			hql += " and  endTime is null ";
		} else if (status == 2) {// 已结束
			hql += " and  endTime is not null ";
		}

		if (beginTime != null) {
			hql += " and beginTime>= ?  ";
			params.add(beginTime);
		}

		if (endTime != null) {
			hql += " and beginTime<= ?  ";
			params.add(endTime);
		}

		List<TeeFlowRun> list = simpleDaoSupport.pageFind(hql + " order by "
				+ sort + order, firstResult, pageSize, params.toArray());
		long tatal = simpleDaoSupport.count("select count(*)" + hql,
				params.toArray());
		Map map = null;
		for (TeeFlowRun teeFlowRun : list) {
			map = new HashMap();
			map.put("runId", teeFlowRun.getRunId());
			map.put("runName", teeFlowRun.getRunName());
			if (teeFlowRun.getBeginTime() != null) {
				map.put("beginTimeStr",
						sdf.format(teeFlowRun.getBeginTime().getTime()));
			}

			if (teeFlowRun.getEndTime() == null) {
				map.put("status", 0);
			} else {
				map.put("status", 1);
			}
			mapList.add(map);
		}
		dataGridJson.setRows(mapList);
		dataGridJson.setTotal(tatal);
		return dataGridJson;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getFlowRunVars(int)
	 */
	@Override
	public List<TeeFlowRunVars> getFlowRunVars(int runId) {
		return simpleDaoSupport.find("from TeeFlowRunVars where flowRun.runId="
				+ runId, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #view(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 获取当前流程下未办理完成的FlowRunPrcs集合
		List<Map> prcsList = flowRunPrcsService.getUnhandledWorks(runId,
				loginUser.getUuid());
		if (prcsList != null && prcsList.size() != 0) {
			Map data = prcsList.get(0);
			TeeFlowRunPrcs flowRunPrcs = flowRunPrcsService.get(TeeStringUtil
					.getInteger(data.get("FRP_SID"), 0));
			if (flowRunPrcs.getFlowPrcs() != null) {// 固定流程
				if (!TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs()
						.getOuterPage())) {// 如果外部连接不为空，则调到外部页面
					TeeFlowType ft = flowRunPrcs.getFlowType();
					// 获取流程变量
					List<TeeFlowRunVars> vars = getFlowRunVars(runId);

					JSONObject jsonObject = new JSONObject();
					jsonObject
							.put("runId", flowRunPrcs.getFlowRun().getRunId());
					if (flowRunPrcs != null) {
						jsonObject.put("frpSid", flowRunPrcs.getSid());
						jsonObject.put("prcsId", flowRunPrcs.getPrcsId());
						TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
						if (fp != null) {
							jsonObject.put("prcsName", fp.getPrcsName());
							jsonObject.put("flowPrcs", fp.getSid());
						} else {
							jsonObject.put("prcsName", "");
							jsonObject.put("flowPrcs", "");
						}
						jsonObject.put("prcsUser", flowRunPrcs.getPrcsUser()
								.getUuid());
						jsonObject.put("flag", flowRunPrcs.getFlag());
					} else {
						jsonObject.put("prcsName", "");
						jsonObject.put("flowPrcs", "");
						jsonObject.put("frpSid", 0);
						jsonObject.put("prcsId", 0);
						jsonObject.put("prcsUser", 0);
						jsonObject.put("flag", 4);
					}

					jsonObject.put("flowId", ft.getSid());
					Map<String, String> varsMap = new HashMap();
					for (TeeFlowRunVars var : vars) {
						varsMap.put(var.getVarKey(), var.getVarValue());
					}
					jsonObject.put("vars", varsMap);
					jsonObject.put("runName", flowRunPrcs.getFlowRun()
							.getRunName());

					String encoding = new String(
							Base64Private.encode(jsonObject.toString()
									.getBytes()));

					response.sendRedirect(flowRunPrcs.getFlowPrcs()
							.getOuterPage() + "?token=" + encoding);

				} else {// 直接跳到办理页面
					response.sendRedirect("/system/core/workflow/flowrun/prcs/index.jsp?runId="
							+ runId
							+ "&flowId="
							+ data.get("FLOW_ID")
							+ "&frpSid=" + data.get("FRP_SID"));
				}
			} else {// 自由流程
				response.sendRedirect("/system/core/workflow/flowrun/prcs/index.jsp?runId="
						+ runId
						+ "&flowId="
						+ data.get("FLOW_ID")
						+ "&frpSid="
						+ data.get("FRP_SID"));
			}
		} else {
			TeeFlowRun flowRun = flowRunDao.get(runId);
			if (!TeeUtility.isNullorEmpty(flowRun.getFlowType().getOuterPage())) {// 如果外部连接不为空，则调到外部页面

				TeeFlowType ft = flowRun.getFlowType();
				// 获取流程变量
				List<TeeFlowRunVars> vars = getFlowRunVars(runId);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("runId", runId);
				jsonObject.put("prcsName", "");
				jsonObject.put("flowPrcs", "");
				jsonObject.put("frpSid", 0);
				jsonObject.put("prcsId", 0);
				jsonObject.put("prcsUser", 0);
				jsonObject.put("flag", 4);

				jsonObject.put("flowId", ft.getSid());
				Map<String, String> varsMap = new HashMap();
				for (TeeFlowRunVars var : vars) {
					varsMap.put(var.getVarKey(), var.getVarValue());
				}
				jsonObject.put("vars", varsMap);
				jsonObject.put("runName", flowRun.getRunName());

				String encoding = new String(Base64Private.encode(jsonObject
						.toString().getBytes()));

				response.sendRedirect(ft.getOuterPage() + "?token=" + encoding);

			} else {// 直接跳到预览界面
				response.sendRedirect("/system/core/workflow/flowrun/print/index.jsp?runId="
						+ runId + "&view=1");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #toView(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void toView(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid = TeeStringUtil
				.getInteger(request.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil
				.getInteger(request.getParameter("flowId"), 0);
		int view = TeeStringUtil.getInteger(request.getParameter("view"), 0);

		TeeFlowRun flowRun = flowRunDao.get(runId);
		TeeFlowRunPrcs flowRunPrcs = flowRunPrcsService.get(frpSid);
		TeeFlowType ft = flowRun.getFlowType();
		if (TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs())
				|| TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs()
						.getOuterPage())) {
			response.sendRedirect("/system/core/workflow/flowrun/print/index.jsp?runId="
					+ runId
					+ "&frpSid="
					+ frpSid
					+ "&flowId="
					+ flowId
					+ "&view=" + 1);
		} else {
			// 获取流程变量
			List<TeeFlowRunVars> vars = getFlowRunVars(runId);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("runId", flowRunPrcs.getFlowRun().getRunId());
			int flag = 0;
			if (flowRunPrcs != null) {
				jsonObject.put("frpSid", flowRunPrcs.getSid());
				jsonObject.put("prcsId", flowRunPrcs.getPrcsId());
				TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
				if (fp != null) {
					jsonObject.put("prcsName", fp.getPrcsName());
					jsonObject.put("flowPrcs", fp.getSid());
				} else {
					jsonObject.put("prcsName", "");
					jsonObject.put("flowPrcs", "");
				}
				jsonObject.put("prcsUser", flowRunPrcs.getPrcsUser().getUuid());
				flag = flowRunPrcs.getFlag();
				jsonObject.put("flag", flowRunPrcs.getFlag());
			} else {
				jsonObject.put("prcsName", "");
				jsonObject.put("flowPrcs", "");
				jsonObject.put("frpSid", 0);
				jsonObject.put("prcsId", 0);
				jsonObject.put("prcsUser", 0);
				flag = 4;
				jsonObject.put("flag", 4);
			}

			jsonObject.put("flowId", ft.getSid());
			Map<String, String> varsMap = new HashMap();
			for (TeeFlowRunVars var : vars) {
				varsMap.put(var.getVarKey(), var.getVarValue());
			}
			jsonObject.put("vars", varsMap);
			jsonObject.put("runName", flowRunPrcs.getFlowRun().getRunName());

			String encoding = new String(Base64Private.encode(jsonObject
					.toString().getBytes()));
			response.sendRedirect(flowRunPrcs.getFlowPrcs().getOuterPage()
					+ "?token=" + encoding);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #toFlowRun(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void toFlowRun(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int frpSid = TeeStringUtil
				.getInteger(request.getParameter("frpSid"), 0);
		TeeFlowRunPrcs flowRunPrcs = null;

		if (frpSid != 0) {
			flowRunPrcs = (TeeFlowRunPrcs) simpleDaoSupport.get(
					TeeFlowRunPrcs.class, frpSid);
		} else {
			// 获取当前登陆在指定流程上的最后一个办理步骤
			List<TeeFlowRunPrcs> frpList = simpleDaoSupport.find(
					"from TeeFlowRunPrcs where prcsUser.uuid="
							+ loginPerson.getUuid() + " and flowRun.runId="
							+ runId + " order by sid desc", null);
			if (frpList.size() != 0) {
				flowRunPrcs = frpList.get(0);
			} else {
				TeeFlowRun flowRun = flowRunDao.get(runId);

				// 获取流程变量
				List<TeeFlowRunVars> vars = getFlowRunVars(runId);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("runId", flowRun.getRunId());
				if (flowRunPrcs != null) {
					jsonObject.put("frpSid", flowRunPrcs.getSid());
					TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
					if (fp != null) {
						jsonObject.put("prcsName", fp.getPrcsName());
						jsonObject.put("flowPrcs", fp.getSid());
						jsonObject.put("prcsId", fp.getSortNo());
					} else {
						jsonObject.put("prcsName", "");
						jsonObject.put("flowPrcs", "");
						jsonObject.put("prcsId", 0);
					}
					jsonObject.put("prcsUser", flowRunPrcs.getPrcsUser()
							.getUuid());
					jsonObject.put("flag", flowRunPrcs.getFlag());
					jsonObject.put("backFlag", flowRunPrcs.getBackFlag());
				} else {
					jsonObject.put("prcsName", "");
					jsonObject.put("flowPrcs", "");
					jsonObject.put("frpSid", 0);
					jsonObject.put("prcsId", 0);
					jsonObject.put("prcsUser", 0);
					jsonObject.put("flag", 4);
					jsonObject.put("backFlag", 0);
				}

				jsonObject.put("flowId", flowRun.getFlowType().getSid());

				Map<String, String> varsMap = new HashMap();
				for (TeeFlowRunVars var : vars) {
					varsMap.put(var.getVarKey(), var.getVarValue());
				}
				jsonObject.put("vars", varsMap);
				jsonObject.put("runName", flowRun.getRunName());

				String encoding = new String(Base64Private.encode(jsonObject
						.toString().getBytes()));

				Map requestData = new HashMap();
				requestData.put("runId", flowRun.getRunId());
				requestData.put("flowId", flowRun.getFlowType().getSid());

				response.sendRedirect(flowRun.getFlowType().getOuterPage()
						+ "?token=" + encoding);
				return;
			}
		}
		if (flowRunPrcs == null) {
			throw new TeeOperationException("该工作已删除");
		}

		TeeFlowRun flowRun = flowRunPrcs.getFlowRun();
		TeeFlowType ft = flowRun.getFlowType();
		if (TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs())
				|| TeeUtility.isNullorEmpty(flowRunPrcs.getFlowPrcs()
						.getOuterPage())) {
			if (flowRunPrcs.getFlag() == 1 || flowRunPrcs.getFlag() == 2) {
				response.sendRedirect("/system/core/workflow/flowrun/prcs/index.jsp?runId="
						+ runId
						+ "&frpSid="
						+ flowRunPrcs.getSid()
						+ "&flowId=" + ft.getSid() + "&view=" + 1);
			} else {
				response.sendRedirect("/system/core/workflow/flowrun/print/index.jsp?runId="
						+ runId
						+ "&frpSid="
						+ flowRunPrcs.getSid()
						+ "&flowId=" + ft.getSid() + "&view=" + 1);
			}

		} else {
			// 获取流程变量
			List<TeeFlowRunVars> vars = getFlowRunVars(runId);

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("runId", flowRunPrcs.getFlowRun().getRunId());
			if (flowRunPrcs != null) {
				jsonObject.put("frpSid", flowRunPrcs.getSid());
				TeeFlowProcess fp = flowRunPrcs.getFlowPrcs();
				if (fp != null) {
					jsonObject.put("prcsName", fp.getPrcsName());
					jsonObject.put("flowPrcs", fp.getSid());
					jsonObject.put("prcsId", fp.getSortNo());
				} else {
					jsonObject.put("prcsName", "");
					jsonObject.put("flowPrcs", "");
					jsonObject.put("prcsId", 0);
				}
				jsonObject.put("prcsUser", flowRunPrcs.getPrcsUser().getUuid());
				jsonObject.put("flag", flowRunPrcs.getFlag());
				jsonObject.put("backFlag", flowRunPrcs.getBackFlag());
			} else {
				jsonObject.put("prcsName", "");
				jsonObject.put("flowPrcs", "");
				jsonObject.put("frpSid", 0);
				jsonObject.put("prcsId", 0);
				jsonObject.put("prcsUser", 0);
				jsonObject.put("flag", 4);
				jsonObject.put("backFlag", 0);
			}

			jsonObject.put("flowId", ft.getSid());
			Map<String, String> varsMap = new HashMap();
			for (TeeFlowRunVars var : vars) {
				varsMap.put(var.getVarKey(), var.getVarValue());
			}
			jsonObject.put("vars", varsMap);
			jsonObject.put("runName", flowRunPrcs.getFlowRun().getRunName());

			String encoding = new String(Base64Private.encode(jsonObject
					.toString().getBytes()));

			Map requestData = new HashMap();
			requestData.put("runId", flowRunPrcs.getFlowRun().getRunId());
			requestData.put("flowId", ft.getSid());
			requestData.put("frpSid", flowRunPrcs.getSid());
			try {
				simpleDataLoaderInterface.getHandlerData(requestData,
						loginPerson);
			} catch (Exception e) {
			}

			response.sendRedirect(flowRunPrcs.getFlowPrcs().getOuterPage()
					+ "?token=" + encoding);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getPrivFlowTypeIds(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getPrivFlowTypeIds(HttpServletRequest request) {
		// 获取当前登陆人
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		Session session = frpDao.getSession();
		TeeJson json = new TeeJson();
		// 获取类型
		int type = TeeStringUtil.getInteger(request.getParameter("type"), 0);
		String sql = "";
		if (type == 1) {// 我的待办
			sql = "select distinct(ft.SID) as flowId  from FLOW_RUN_PRCS frp left outer join FLOW_PROCESS fp on fp.sid=frp.FLOW_PRCS ,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1 and frp.flag in (1,2) and fr.END_TIME is null and frp.PRCS_USER="
					+ loginUser.getUuid() + " and frp.suspend=0 ";
		} else if (type == 2) {// 我已办结
			sql = "select distinct(ft.SID) as flowId from FLOW_RUN_PRCS frp ,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID  and frp.SID=(select max(frp1.SID) from FLOW_RUN_PRCS frp1 where frp.RUN_ID=frp1.RUN_ID and frp.PRCS_USER=frp1.PRCS_USER and frp1.flag in (3,4) and frp1.PRCS_USER="
					+ loginUser.getUuid() + ") ";
		} else if (type == 3) {// 我的查阅
			sql = " select distinct(fr.FLOW_ID) from FLOW_RUN_VIEW frv left outer join FLOW_RUN fr on fr.RUN_ID=frv.RUN_ID  where frv.VIEW_PERSON="
					+ loginUser.getUuid() + " and fr.IS_SAVE=1  ";
		} else if (type == 4) {// 我的关注
			sql = " select distinct(fr.FLOW_ID) from FLOW_RUN fr where fr.IS_SAVE=1 and exists (select 1 from FLOW_RUN_CONCERN frc where frc.RUN_ID=fr.RUN_ID and frc.PERSON_ID="
					+ loginUser.getUuid() + ") ";
		} else if (type == 5) {// 挂起工作
			sql = "select distinct(ft.SID) from FLOW_RUN_PRCS frp left outer join FLOW_RUN_DOC doc on doc.RUN_ID=frp.RUN_ID,FLOW_RUN fr,PERSON p,FLOW_TYPE ft where fr.DEL_FLAG=0 and p.uuid=fr.begin_person and ft.SID=fr.FLOW_ID and frp.RUN_ID=fr.RUN_ID and fr.IS_SAVE=1  and fr.END_TIME is null and frp.PRCS_USER="
					+ loginUser.getUuid() + " and frp.suspend=1 ";
		}

		String flowIds = "";
		SQLQuery query = session.createSQLQuery(sql);
		List<Integer> list = query.list();
		if (list != null && list.size() > 0) {
			for (int m : list) {
				flowIds += m + ",";
			}
		}
		if (flowIds.endsWith(",")) {
			flowIds = flowIds.substring(0, flowIds.length() - 1);
		}
		json.setRtState(true);
		json.setRtData(flowIds);
		return json;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getPrivFlowTypes(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public List<TeeZTreeModel> getPrivFlowTypes(HttpServletRequest request) {

		String privFlowIds = TeeStringUtil.getString(request
				.getParameter("privFlowIds"));

		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();

		String hql = "from TeeFlowType ft where 1=1 ";

		if (!TeeUtility.isNullorEmpty(privFlowIds)) {
			hql += " and " + TeeDbUtility.IN("ft.sid", privFlowIds) + " ";
		}
		hql += " order by orderNo asc";

		// 先查出相关流程
		List<TeeFlowType> typeList = simpleDaoSupport.find(hql, null);

		int flag = 0;
		Set<TeeFlowSort> sortSet = new HashSet<TeeFlowSort>();
		if (typeList != null && typeList.size() > 0) {
			for (TeeFlowType flowType : typeList) {
				if (!TeeUtility.isNullorEmpty(flowType.getFlowSort())) {
					sortSet.add(flowType.getFlowSort());
				} else {
					flag = 1;
				}
			}
		}
		List<TeeFlowSort> sortList = new ArrayList<TeeFlowSort>(sortSet);
		/*
		 * //流程分类 List<TeeFlowSort> sortList = simpleDaoSupport.find(
		 * "from TeeFlowSort order by orderNo asc", null);
		 */

		TeeZTreeModel m1 = new TeeZTreeModel();
		m1.setName("全部流程");
		m1.setTitle("全部流程");
		m1.setId("0");
		list.add(m1);
		m1.setIconSkin("wfNode2");

		if (flag == 1) {
			TeeZTreeModel m2 = new TeeZTreeModel();
			m2.setId("sort" + 0);
			m2.setName("默认分类");
			m2.setTitle("默认分类");
			m2.setDisabled(true);
			m2.setOpen(true);
			list.add(m2);
			m2.setIconSkin("wfNode1");
		}

		for (TeeFlowSort fs : sortList) {
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId("sort" + fs.getSid());
			m.setName(fs.getSortName());
			m.setTitle(fs.getSortName());
			m.setDisabled(true);
			m.setOpen(true);
			list.add(m);
			m.setIconSkin("wfNode1");
		}

		for (TeeFlowType ft : typeList) {
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId(String.valueOf(ft.getSid()));
			m.setName(ft.getFlowName());
			m.setTitle(ft.getFlowName());
			m.setpId("sort"
					+ (ft.getFlowSort() == null ? 0 : ft.getFlowSort().getSid()));
			list.add(m);
			m.setIconSkin("wfNode2");
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tianee.oa.core.workflow.flowrun.service.TeeWorkflowServiceInterface
	 * #getFixedFlowType2SelectCtrl()
	 */
	@Override
	public List<TeeZTreeModel> getFixedFlowType2SelectCtrl() {
		List<TeeZTreeModel> list = new ArrayList<TeeZTreeModel>();

		List<TeeFlowType> typeList = simpleDaoSupport.find(
				"from TeeFlowType where type=1   order by orderNo asc", null);

		List<TeeFlowSort> sortList = simpleDaoSupport.find(
				"from TeeFlowSort order by orderNo asc", null);

		/*
		 * TeeZTreeModel m1 = new TeeZTreeModel(); m1.setName("全部流程");
		 * m1.setTitle("全部流程"); m1.setId("0"); list.add(m1);
		 * m1.setIconSkin("wfNode2");
		 */

		TeeZTreeModel m2 = new TeeZTreeModel();
		m2.setId("sort" + 0);
		m2.setName("默认分类");
		m2.setTitle("默认分类");
		m2.setDisabled(true);
		m2.setOpen(true);
		list.add(m2);
		m2.setIconSkin("wfNode1");

		for (TeeFlowSort fs : sortList) {
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId("sort" + fs.getSid());
			m.setName(fs.getSortName());
			m.setTitle(fs.getSortName());
			m.setDisabled(true);
			m.setOpen(true);
			list.add(m);
			m.setIconSkin("wfNode1");
		}

		for (TeeFlowType ft : typeList) {
			TeeZTreeModel m = new TeeZTreeModel();
			m.setId(String.valueOf(ft.getSid()));
			m.setName(ft.getFlowName());
			m.setTitle(ft.getFlowName());
			m.setpId("sort"
					+ (ft.getFlowSort() == null ? 0 : ft.getFlowSort().getSid()));
			list.add(m);
			m.setIconSkin("wfNode2");
		}
		return list;
	}

	/**
	 * 撤销
	 * */
	@Override
	public TeeJson doCheXiao(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestData = new HashMap();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		int deleteOrUpdateByQuery = flowRunDao.deleteOrUpdateByQuery(
				"update TeeFlowRun set delFlag=1 where runId=?",
				new Object[] { runId });
		List<TeeFlowRunPrcs> find = frpDao.find(
				"from TeeFlowRunPrcs where flowRun.runId=?",
				new Object[] { runId });
		if (find != null && find.size() > 0) {
			for (TeeFlowRunPrcs prcs : find) {
				prcs.setDelFlag(1);
				frpDao.update(prcs);
				int userId = prcs.getPrcsUser().getUuid();
				requestData.put("moduleNo", "093");
				requestData.put("userListIds", userId);
				requestData.put("content", "[" + runId + "]此流程已被流程发起人撤销");
				requestData.put("remindUrl", "");
				smsSender.sendSms(requestData, person);
			}
		}
		if (deleteOrUpdateByQuery > 0) {
			json.setRtState(true);
			json.setRtMsg("撤销成功！");
		}
		return json;
	}

	/**
	 * 克隆附件到指定附件
	 * */
	@Override
	public TeeJson keLongAttachment(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		String attIds = TeeStringUtil.getString(request.getParameter("attIds"));
		int kjId = TeeStringUtil.getInteger(request.getParameter("kjId"), 0);
		
		//删除之前的附件
		simpleDaoSupport.deleteOrUpdateByQuery(
				"delete from TeeAttachment where modelId=?",
				new Object[] { kjId + "_" + runId });
		
		TeeAttachment attach=null;
		TeeAttachment attachment = null;
		List<TeeAttachmentModel> modelList=new ArrayList<TeeAttachmentModel>();
		if(!TeeUtility.isNullorEmpty(attIds)){//附件id
			if(attIds.endsWith(",")){
				attIds=attIds.substring(0, attIds.length()-1);
				String[] attIdArr=attIds.split(",");
				if(attIdArr!=null){
					for (int i = 0; i < attIdArr.length; i++) {
						attach = (TeeAttachment) simpleDaoSupport.get(
								TeeAttachment.class, TeeStringUtil.getInteger(attIdArr[i], 0));
						
						if (attach != null) {
							attachment = attachmentService.clone(attach, "workFlowUploadCtrl",
									person);
							attachment.setModelId(kjId + "_" + runId);
							attachmentService.updateAttachment(attachment);
							
							modelList.add(attachmentService.getModelById(attachment.getSid()));
						}
					}
				}
			}
		}
		
		
		
		json.setRtData(modelList);
		return json;
	}
	
	public TeeJson addFlowRunPrcs(Map requestParams) {
		TeePerson loginUser = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 获取前台页面传来的流程序号（累加）
		int prcsId = TeeStringUtil
				.getInteger(requestParams.get("prcsId"), 0);
		// runId
		int runId = TeeStringUtil.getInteger(requestParams.get("runId"), 0);
		// 办理人主键
		int prcsUserId = TeeStringUtil.getInteger(
				requestParams.get("prcsUserId"), 0);
		// 创建时间字符窜
		String createTimeStr = TeeStringUtil.getString(requestParams.get("createTimeStr"));
		// 获取办理状态
		int flag = TeeStringUtil.getInteger(requestParams.get("flag"), 0);
		// 获取页面传来的流程步骤的名称
		String prcsName = TeeStringUtil.getString(requestParams.get("prcsName"));
		// 获取是否发消息
		int message = TeeStringUtil.getInteger(requestParams.get("message"),
				0);
		// 获取是否发短信
		int sms = TeeStringUtil.getInteger(requestParams.get("sms"), 0);

		// 获取流程的办理人员
		TeePerson prcsUser = (TeePerson) simpleDaoSupport.get(TeePerson.class,
				prcsUserId);

		// 将创建时间转换成Calendar类型和long类型
		Calendar createTime = TeeDateUtil.parseCalendar(createTimeStr);
		long createTimeStamp = 0;
		if (createTime != null) {
			createTimeStamp = createTime.getTimeInMillis();
		} else {
			createTime = Calendar.getInstance();
			createTimeStamp = createTime.getTimeInMillis();
		}

		// 根据runId 获取一些信息
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(
				TeeFlowRun.class, runId);
		// 流程类型
		TeeFlowType ft = null;
		if (flowRun != null) {
			ft = flowRun.getFlowType();
		}
		// 流程步骤
		TeeFlowProcess process = null;
		List<TeeFlowProcess> processList = simpleDaoSupport.executeQuery(
				"from TeeFlowProcess where prcsName=? and flowType.sid=? ",
				new Object[] { prcsName, ft == null ? 0 : ft.getSid() });
		if (processList != null && processList.size() > 0) {
			process = processList.get(0);
		}

		// 创建流程步骤实例对象
		TeeFlowRunPrcs prcs = new TeeFlowRunPrcs();
		prcs.setPrcsId(prcsId);
		prcs.setFlowRun(flowRun);
		prcs.setFlowType(ft);
		prcs.setFlowPrcs(process);
		prcs.setPrcsUser(prcsUser);
		if (flag == 4) {// 已办结
			prcs.setBeginTime(createTime);
			prcs.setBeginTimeStamp(createTimeStamp);
			prcs.setEndTime(createTime);
			prcs.setEndTimeStamp(createTimeStamp);
		}

		prcs.setCreateTime(createTime);
		prcs.setCreateTimeStamp(createTimeStamp);
		prcs.setFlag(flag);
		prcs.setOpFlag(1);
		prcs.setTopFlag(1);
		simpleDaoSupport.save(prcs);

		// 发消息提醒
		if (message == 1) {
			Map requestData1 = new HashMap();
			requestData1.put("content", "您有工作[" + flowRun.getRunId() + "]["
					+ flowRun.getRunName() + "]已转交至下一步[" + prcsName
					+ "],请及时办理。");
			requestData1.put("userListIds", prcsUser.getUuid());
			requestData1.put("moduleNo", "006");
			requestData1.put("remindUrl", "/workflow/toFlowRun.action?runId="
					+ runId + "&frpSid=" + prcs.getSid());
			smsManager.sendSms(requestData1, loginUser);
		}

		// 发短信
		if (sms == 1) {
			if (!TeeUtility.isNullorEmpty(prcsUser.getMobilNo())) {
				TeeSmsSendPhone smsSendPhone = new TeeSmsSendPhone();
				smsSendPhone.setContent("您有工作[" + flowRun.getRunId() + "]["
						+ flowRun.getRunName() + "]已转交至下一步[" + prcsName
						+ "],请及时办理。");
				smsSendPhone.setFromId(loginUser.getUuid());
				smsSendPhone.setFromName(loginUser.getUserName());
				smsSendPhone.setPhone(prcsUser.getMobilNo());
				smsSendPhone.setSendFlag(0);
				smsSendPhone.setSendNumber(0);
				smsSendPhone.setSendTime(Calendar.getInstance());
				smsSendPhone.setToId(prcsUser.getUuid());
				smsSendPhone.setToName(prcsUser.getUserName());

				simpleDaoSupport.save(smsSendPhone);
			}
		}

		json.setRtState(true);
		json.setRtData(prcs.getSid());
		return json;
	}

	/**
	 * 手动创建流程实例
	 */
	@Override
	public TeeJson addFlowRunPrcs(HttpServletRequest request) {
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 获取前台页面传来的流程序号（累加）
		int prcsId = TeeStringUtil
				.getInteger(request.getParameter("prcsId"), 0);
		// runId
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"), 0);
		// 办理人主键
		int prcsUserId = TeeStringUtil.getInteger(
				request.getParameter("prcsUserId"), 0);
		// 创建时间字符窜
		String createTimeStr = TeeStringUtil.getString(request
				.getParameter("createTimeStr"));
		// 获取办理状态
		int flag = TeeStringUtil.getInteger(request.getParameter("flag"), 0);
		// 获取页面传来的流程步骤的名称
		String prcsName = TeeStringUtil.getString(request
				.getParameter("prcsName"));
		// 获取是否发消息
		int message = TeeStringUtil.getInteger(request.getParameter("message"),
				0);
		// 获取是否发短信
		int sms = TeeStringUtil.getInteger(request.getParameter("sms"), 0);

		// 获取流程的办理人员
		TeePerson prcsUser = (TeePerson) simpleDaoSupport.get(TeePerson.class,
				prcsUserId);

		// 将创建时间转换成Calendar类型和long类型
		Calendar createTime = TeeDateUtil.parseCalendar(createTimeStr);
		long createTimeStamp = 0;
		if (createTime != null) {
			createTimeStamp = createTime.getTimeInMillis();
		} else {
			createTime = Calendar.getInstance();
			createTimeStamp = createTime.getTimeInMillis();
		}

		// 根据runId 获取一些信息
		TeeFlowRun flowRun = (TeeFlowRun) simpleDaoSupport.get(
				TeeFlowRun.class, runId);
		// 流程类型
		TeeFlowType ft = null;
		if (flowRun != null) {
			ft = flowRun.getFlowType();
		}
		// 流程步骤
		TeeFlowProcess process = null;
		List<TeeFlowProcess> processList = simpleDaoSupport.executeQuery(
				"from TeeFlowProcess where prcsName=? and flowType.sid=? ",
				new Object[] { prcsName, ft == null ? 0 : ft.getSid() });
		if (processList != null && processList.size() > 0) {
			process = processList.get(0);
		}

		// 创建流程步骤实例对象
		TeeFlowRunPrcs prcs = new TeeFlowRunPrcs();
		prcs.setPrcsId(prcsId);
		prcs.setFlowRun(flowRun);
		prcs.setFlowType(ft);
		prcs.setFlowPrcs(process);
		prcs.setPrcsUser(prcsUser);
		if (flag == 4) {// 已办结
			prcs.setBeginTime(createTime);
			prcs.setBeginTimeStamp(createTimeStamp);
			prcs.setEndTime(createTime);
			prcs.setEndTimeStamp(createTimeStamp);
		}

		prcs.setCreateTime(createTime);
		prcs.setCreateTimeStamp(createTimeStamp);
		prcs.setFlag(flag);
		prcs.setOpFlag(1);
		prcs.setTopFlag(1);
		simpleDaoSupport.save(prcs);

		// 发消息提醒
		if (message == 1) {
			Map requestData1 = new HashMap();
			requestData1.put("content", "您有工作[" + flowRun.getRunId() + "]["
					+ flowRun.getRunName() + "]已转交至下一步[" + prcsName
					+ "],请及时办理。");
			requestData1.put("userListIds", prcsUser.getUuid());
			requestData1.put("moduleNo", "006");
			requestData1.put("remindUrl", "/workflow/toFlowRun.action?runId="
					+ runId + "&frpSid=" + prcs.getSid());
			smsManager.sendSms(requestData1, loginUser);
		}

		// 发短信
		if (sms == 1) {
			if (!TeeUtility.isNullorEmpty(prcsUser.getMobilNo())) {
				TeeSmsSendPhone smsSendPhone = new TeeSmsSendPhone();
				smsSendPhone.setContent("您有工作[" + flowRun.getRunId() + "]["
						+ flowRun.getRunName() + "]已转交至下一步[" + prcsName
						+ "],请及时办理。");
				smsSendPhone.setFromId(loginUser.getUuid());
				smsSendPhone.setFromName(loginUser.getUserName());
				smsSendPhone.setPhone(prcsUser.getMobilNo());
				smsSendPhone.setSendFlag(0);
				smsSendPhone.setSendNumber(0);
				smsSendPhone.setSendTime(Calendar.getInstance());
				smsSendPhone.setToId(prcsUser.getUuid());
				smsSendPhone.setToName(prcsUser.getUserName());

				simpleDaoSupport.save(sms);
			}
		}

		json.setRtState(true);
		json.setRtData(prcs.getSid());
		return json;
	}

	/**
	 * 手动修改流程步骤实例的状态 修改成结束状态
	 */
	@Override
	public TeeJson updateFlowRunPrcs(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		// 获取前台页面传来的参数
		int frpSid = TeeStringUtil
				.getInteger(request.getParameter("frpSid"), 0);
		int flag = TeeStringUtil.getInteger(request.getParameter("flag"), 0);

		TeeFlowRunPrcs prcs = (TeeFlowRunPrcs) simpleDaoSupport.get(
				TeeFlowRunPrcs.class, frpSid);
		if (prcs != null) {// 流程实例存在
			prcs.setFlag(flag);

			if (flag == 4) {// 办结
				Calendar endTime = Calendar.getInstance();
				prcs.setEndTime(endTime);
				prcs.setEndTimeStamp(endTime.getTimeInMillis());
			}

			simpleDaoSupport.update(prcs);
			json.setRtState(true);
		} else {
			json.setRtState(false);
			json.setRtMsg("该流程步骤实例已不存在！");
		}
		return json;
	}

	/**
	 * 根据form 和 控件名称 获取表单控件
	 * 
	 * @param form
	 * @param ctrlTitle
	 * @return
	 */
	public List<TeeFormItem> getFormItemsByFormAndCtrTitle(TeeForm form,
			String ctrlTitle) {
		String hql = " from TeeFormItem where form=? and title=?  ";
		List<TeeFormItem> list = simpleDaoSupport.executeQuery(hql,
				new Object[] { form, ctrlTitle });
		return list;
	}

	// 获取中间表中控件的值
	@Override
	public List getCtrlValue(String ctrlName, String tableName, int runId) {
		List list = simpleDaoSupport.getBySql("select to_char(" + ctrlName
				+ ") from " + tableName + " where RUN_ID=? ",
				new Object[] { runId });
		return list;
	}
}
