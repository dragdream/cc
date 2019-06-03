package com.tianee.oa.subsys.bisengin.service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisForm;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.bean.BusinessCat;
import com.tianee.oa.subsys.bisengin.bean.BusinessModel;
import com.tianee.oa.subsys.bisengin.dao.BisTableDao;
import com.tianee.oa.subsys.bisengin.dao.BisTableFieldDao;
import com.tianee.oa.subsys.bisengin.dao.BusinessModelDao;
import com.tianee.oa.subsys.bisengin.model.BusModel;
import com.tianee.oa.subsys.cowork.bean.TeeCoWorkTask;
import com.tianee.oa.subsys.cowork.model.TeeCoWorkTaskModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class BusinessModelService extends TeeBaseService {

	@Autowired
	private BusinessModelDao businessModelDao;
	@Autowired
	private BisTableFieldDao bisTableFieldDao;
	@Autowired
	private TeePersonDao personDao;
	@Autowired
	private TeeDeptDao deptDao;
	@Autowired
	private TeeUserRoleDao roleDao;
	@Autowired
	private BisTableDao bisTableDao;

	@Autowired
	private TeeAttachmentDao attachmentDao;

	public TeeEasyuiDataGridJson datagrid(BusModel model, TeeDataGridModel dm) {
		TeeEasyuiDataGridJson dataGridJson = businessModelDao.datagrid(model,
				dm);
		List<BusinessModel> list = dataGridJson.getRows();
		List<BusModel> modelList = new ArrayList<BusModel>();
		for (BusinessModel obj : list) {
			BusModel m = new BusModel();
			m = parseReturnModel(obj, false);
			modelList.add(m);
		}
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}

	public void deleteBusinessModelById(BusinessModel model) {
		businessModelDao.deleteByObj(model);
	}

	public BusinessModel getBusinessModelById(String bisKey) {
		BusinessModel model = businessModelDao.get(bisKey);
		return model;
	}

	public TeeJson addOrUpdate(BusModel model, String type) {
		TeeJson json = new TeeJson();

		if ("update".equals(type)) {// 更新操作
			BusinessModel bm = businessModelDao.get(model.getBisKey());
			bm = parseObj(model, bm);
			businessModelDao.update(bm);
			json.setRtMsg("更新成功");
		} else {// 新建操作
			BusinessModel bm = new BusinessModel();
			bm = parseObj(model, bm);
			bm.setCreateTime(new Date());
			businessModelDao.save(bm);
			json.setRtMsg("新建成功");
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 转换成返回对象
	 * 
	 * @param obj
	 * @param isEdit
	 * @return
	 */
	public BusModel parseReturnModel(BusinessModel obj, boolean isEdit) {

		BusModel model = new BusModel();
		if (obj == null) {
			return model;
		}
		BeanUtils.copyProperties(obj, model);

		if (obj.getBisForm() != null && obj.getBisForm().getSid() != 0) {
			model.setBisFormId(obj.getBisForm().getSid());
			model.setBisFormName(obj.getBisForm().getFormName());
		}
		if (obj.getBusinessCat() != null && obj.getBusinessCat().getSid() != 0) {
			model.setBusinessCatId(obj.getBusinessCat().getSid());
			model.setBusinessCatName(obj.getBusinessCat().getCatName());
		}
		
		if(obj.getFlowTye()!=null){
			model.setFlowId(obj.getFlowTye().getSid());
			model.setFlowName(obj.getFlowTye().getFlowName());
		}
		
		if(obj.getFlowProcess()!=null){
			model.setFlowStep(obj.getFlowProcess().getSid());
		}
		
		List<Map<String, String>> list=TeeJsonUtil.JsonStr2MapList(obj.getMapping());
		if(list!=null&&list.size()>0){
			for (Map map : list) {
				int flowItemId=TeeStringUtil.getInteger(map.get("flowItemId"),0);
				int formId=obj.getFlowTye().getForm().getSid();
				List<TeeFormItem> formItemList=simpleDaoSupport.executeQuery(" from TeeFormItem where form.sid=? and itemId=? ", new Object[]{formId,flowItemId});
				TeeFormItem formItem=null;
				if(formItemList!=null&&formItemList.size()>0){
					formItem=formItemList.get(0);
				}
				
				if(formItem!=null){
					map.put("flowFieldName", formItem.getTitle());
				}
				
				int tableFieldId=TeeStringUtil.getInteger(map.get("tableFieldId"),0);
				BisTableField tableField=(BisTableField) simpleDaoSupport.get(BisTableField.class,tableFieldId);
			    if(tableField!=null){
			    	map.put("tableFieldName", tableField.getFieldDesc());
			    }
			    
			    model.getMappingList().add(map);
			}
		}
		
		
		return model;
	}

	/**
	 * 转换成保存对象
	 * 
	 * @param model
	 * @param obj
	 * @return
	 */
	public BusinessModel parseObj(BusModel model, BusinessModel obj) {
		if (model == null) {
			return obj;
		}
		try {
			BeanUtils.copyProperties(model, obj);
			
			//绑定流程
			if(model.getFlowId()!=0){
				TeeFlowType flowType=(TeeFlowType) simpleDaoSupport.get(TeeFlowType.class,model.getFlowId());
			    obj.setFlowTye(flowType);
			}
            //触发步骤
			if(model.getFlowStep()!=0){
				TeeFlowProcess process=(TeeFlowProcess) simpleDaoSupport.get(TeeFlowProcess.class,model.getFlowStep());
			    obj.setFlowProcess(process);
			}	
			BisForm bisForm = new BisForm();
			bisForm.setSid(model.getBisFormId());

			BusinessCat businessCat = new BusinessCat();
			businessCat.setSid(model.getBusinessCatId());

			obj.setBisForm(bisForm);
			obj.setBusinessCat(businessCat);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 根据主键获取model
	 * 
	 * @param bisKey
	 * @return
	 */
	public BusModel getBusModelByBisKey(String bisKey) {
		BusinessModel bm = businessModelDao.get(bisKey);
		BusModel model = parseReturnModel(bm, false);
		return model;
	}

	/**
	 * 显示表格
	 * 
	 * @param bisKey
	 * @param searchModel
	 * @return
	 */
	public TeeEasyuiDataGridJson getTable(String bisKey, String searchModel,
			TeeDataGridModel dm, HttpServletRequest request) {
		// 获取当前登录的用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 当前登录的部门
		TeeDepartment dept1 = person.getDept();
		// 当前登录的角色
		TeeUserRole role1 = person.getUserRole();

		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的名称
		String tableName = table.getTableName();
		// 获取查询条件
		String condition = bm.getDisplayCondition();
		// 获取排序的字段
		String orders = bm.getOrderField();
		Map<String, String> map = TeeJsonUtil.JsonStr2Map(orders);
		String orderType = map.get("orderType");
		String orderFieldId = map.get("fieldId");
		String order = bisTableFieldDao.get(Integer.parseInt(orderFieldId))
				.getFieldName();
		// 获取查询字段
		List<String> querys = new ArrayList<String>();
		List<String> qTypes = new ArrayList<String>();
		String queryIds = bm.getQueryFields();
		String[] queryId = queryIds.split(",");
		for (int i = 0; i < queryId.length; i++) {
			if("".equals(queryId[i])){
				continue;
			}
			// 根据字段id 获取字段对象
			BisTableField field = bisTableFieldDao.get(Integer
					.parseInt(queryId[i]));
			querys.add(field.getFieldName().toUpperCase());
			qTypes.add(field.getFieldType());
		}
		// 获取显示的字段
		String displayIds = bm.getHeaderFields();
		String[] displayId = displayIds.split(",");
		// 定义存放显示类型 和 显示模型的集合
		List<String> disPlayNames = new ArrayList<String>();
		List<String> displayTypes = new ArrayList<String>();
		List<String> displayModels = new ArrayList<String>();
		for (int i = 0; i < displayId.length; i++) {
			// 根据字段id 获取字段对象
			BisTableField field = bisTableFieldDao.get(Integer
					.parseInt(displayId[i]));
			disPlayNames.add(field.getFieldName().toUpperCase());
			displayTypes.add(field.getFieldDisplayType());
			displayModels.add(field.getFieldControlModel());
		}

		// 转换searchModel
		Map searchMap = TeeJsonUtil.JsonStr2Map(searchModel);

		// 创建数据库连接对象
		Connection dbConn = null;
		List<Object> param = new ArrayList<Object>();
		List<TeeAttachment> attaches = null;
		try {
			// 获取连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);

			if (!TeeUtility.isNullorEmpty(tableName)) {
				String sql = "select *";
				String sql1 = "";
				String sql2 = "";

				if (!TeeUtility.isNullorEmpty(condition)) {

					condition = condition.replace("$[USER_NAME]",
							person.getUserName()).replace("$[USER_ID]",
							person.getUuid() + "");
					if (dept1 != null) {
						condition = condition.replace("$[DEPT_NAME]",
								dept1.getDeptName()).replace("$[DEPT_ID]",
								dept1.getUuid() + "");
					}
					if (role1 != null) {
						condition = condition.replace("$[ROLE_NAME]",
								role1.getRoleName()).replace("$[ROLE_ID]",
								role1.getUuid() + "");
					}

					sql1 = " from " + tableName + " where 1=1 and " + condition;
					// 循环遍历查询条件
					for (int i = 0; i < querys.size(); i++) {
						String type = qTypes.get(i);
						String beginDate="";
						String endDate="";
						if (("DATE").equals(type)) {
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$begin"))){
								beginDate = searchMap.get(
										querys.get(i).toUpperCase() + "$begin").toString();
							}
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$end"))){
								endDate = searchMap.get(
										querys.get(i).toUpperCase() + "$end").toString();
							}
							
							if (!("").equals(beginDate)
									&& !("").equals(endDate)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd", beginDate);
								Date end = TeeDateUtil.parseDate("yyyy-MM-dd",
										endDate);
								sql1 += "  and " + querys.get(i) + ">=? and  "
										+ querys.get(i) + "<=?";
								param.add(begin);
								param.add(end);
							} else if (!("").equals(beginDate)
									&& ("").equals(endDate)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd", beginDate);
								sql1 += "  and " + querys.get(i) + ">=? ";
								param.add(begin);
							} else if (("").equals(beginDate)
									&& !("").equals(endDate)) {
								Date end = TeeDateUtil.parseDate("yyyy-MM-dd",
										endDate);
								sql1 += "  and " + querys.get(i) + "<=? ";
								param.add(end);
							}

						} else if (("DATETIME").equals(type)) {
							String beginDateTime="";
							String endDateTime="";
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$begin"))){
								beginDateTime = searchMap.get(
										querys.get(i).toUpperCase() + "$begin").toString();
							}
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$end"))){
								endDateTime = searchMap.get(
										querys.get(i).toUpperCase() + "$end").toString();
							}
							
							if (!("").equals(beginDateTime)
									&& !("").equals(endDateTime)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", beginDateTime);
								Date end = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", endDateTime);
								sql1 += "  and " + querys.get(i) + ">=? and  "
										+ querys.get(i) + "<=?";
								param.add(begin);
								param.add(end);
							} else if (!("").equals(beginDateTime)
									&& ("").equals(endDateTime)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", beginDateTime);
								sql1 += "  and " + querys.get(i) + ">=? ";
								param.add(begin);
							} else if (("").equals(beginDateTime)
									&& !("").equals(endDateTime)) {
								Date end = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", endDateTime);
								sql1 += "  and " + querys.get(i) + "<=? ";
								param.add(end);
							}

						}else if(("CHAR").equals(type)||("VARCHAR").equals(type)||("TEXT").equals(type)){//字符类型   字符串类型  文本类型
							String fieldValue = searchMap.get(querys.get(i).toUpperCase())
									.toString();
							if (!("").equals(fieldValue) && fieldValue != null) {
								sql1 += "  and " + querys.get(i) + " like ? ";
								param.add("%"+fieldValue+"%");
							}
						}else {
							String fieldValue = searchMap.get(querys.get(i).toUpperCase())
									.toString();
							if (!("").equals(fieldValue) && fieldValue != null) {
								sql1 += "  and " + querys.get(i) + "=?";
								param.add(fieldValue);
							}
						}
					}
				} else {
					sql1 = " from " + tableName +" where 1=1 ";
					// 循环遍历查询条件
					for (int i = 0; i < querys.size(); i++) {
						String type = qTypes.get(i);
						/*if ((" from " + tableName).equals(sql1)) {
							if (("DATE").equals(type)) {
								String beginDate="";
								String endDate="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(querys.get(i).toUpperCase() + "$begin"))){
									beginDate = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								
								if(!TeeUtility.isNullorEmpty(searchMap.get(querys.get(i).toUpperCase() + "$end"))){
									endDate = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								
								if (!("").equals(beginDate)
										&& !("").equals(endDate)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  where " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";
									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDate)
										&& ("").equals(endDate)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									sql1 += "  where " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDate)
										&& !("").equals(endDate)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  where " + querys.get(i) + "<=? ";
									param.add(end);
								}

							} else if (("DATETIME").equals(type)) {
								
								String beginDateTime="";
								String endDateTime="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								
								if (!("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  where " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";
									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDateTime)
										&& ("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									sql1 += "  where " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  where " + querys.get(i) + "<=? ";
									param.add(end);
								}

							}else if(("CHAR").equals(type)||("VARCHAR").equals(type)||("TEXT").equals(type)){//字符类型   字符串类型  文本类型
								String fieldValue = searchMap.get(querys.get(i).toUpperCase())
										.toString();
								if (!("").equals(fieldValue) && fieldValue != null) {
									sql1 += "  and " + querys.get(i) + "like ? ";
									param.add("%"+fieldValue+"%");
								}
							} else {
								if (searchMap.get(querys.get(i).toUpperCase()) != null) {
									String fieldValue = searchMap.get(
											querys.get(i).toUpperCase()).toString();
									if (!("").equals(fieldValue)
											&& fieldValue != null) {
										sql1 += "  where " + querys.get(i)
												+ "=?";
										param.add(fieldValue);
									}

								}

							}
						} else {*/

							if (("DATE").equals(type)) {
								String beginDate="";
								String endDate="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDate = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDate = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								

								if (!("").equals(beginDate)
										&& !("").equals(endDate)) {

									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  and " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";

									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDate)
										&& ("").equals(endDate)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									sql1 += "  and " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDate)
										&& !("").equals(endDate)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  and " + querys.get(i) + "<=? ";
									param.add(end);
								}

							} else if (("DATETIME").equals(type)) {
								
								String beginDateTime="";
								String endDateTime="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty( searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								
								if (!("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  and " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";
									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDateTime)
										&& ("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									sql1 += "  and " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  and " + querys.get(i) + "<=? ";
									param.add(end);
								}

							}else if(("CHAR").equals(type)||("VARCHAR").equals(type)||("TEXT").equals(type)){//字符类型   字符串类型  文本类型
								String fieldValue = searchMap.get(querys.get(i).toUpperCase())
										.toString();
								if (!("").equals(fieldValue) && fieldValue != null) {
									sql1 += "  and " + querys.get(i) + " like ? ";
									param.add("%"+fieldValue+"%");
								}
							} else {
								String fieldValue = searchMap
										.get(querys.get(i).toUpperCase()).toString();
								if (!("").equals(fieldValue)
										&& fieldValue != null) {
									sql1 += "  and " + querys.get(i) + "=?";
									param.add(fieldValue);
								}
							}
						/*}*/
					}
				}
				
			//	sql1 += "  order by " + order + "  " + orderType + " ";
				sql2 = sql + sql1+"  order by " + order + "  " + orderType + " ";

				List<Map> data = dbUtils.queryToMapList(sql2, param.toArray(),
						dm.getRows() * (dm.getPage() - 1), dm.getRows());
				
				// 复选 单选 下拉 单选或者多选人员 部门 角色 赋值汉字描述
				if (data != null) {
					for (Map map2 : data) {
						
						
						for (int i = 0; i < disPlayNames.size(); i++) {
							if (("RADIO").equals(displayTypes.get(i))
									|| ("SELECT").equals(displayTypes.get(i))) {// 单选和下拉
								String controlModel = displayModels.get(i);
								Map<String, String> m = TeeJsonUtil
										.JsonStr2Map(controlModel);
								String name = TeeSysCodeManager
										.getChildSysCodeNameCodeNo(m
												.get("sysno"), TeeStringUtil
												.getString(map2
														.get(disPlayNames
																.get(i))));
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC", name);
							} else if (("CHECKBOX").equals(displayTypes.get(i))) {
								String code = map2.get(disPlayNames.get(i))
										.toString();
								if (("0").equals(code)) {
									map2.put(disPlayNames.get(i).toUpperCase() + "_DESC", "否");
								} else if (("1").equals(code)) {
									map2.put(disPlayNames.get(i).toUpperCase() + "_DESC", "是");
								}
							} else if (("SINGLEPERSON").equals(displayTypes
									.get(i))) {// 单选人员
								String personId = TeeStringUtil.getString(map2
										.get(disPlayNames.get(i)));
								TeePerson p = personDao.get(TeeStringUtil
										.getInteger(personId, 0));
								if (p != null) {
									String personName = p.getUserName();
									map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
											personName);
								}

							} else if (("MANYPERSON").equals(displayTypes
									.get(i))) {// 多选人员
								String personIds = TeeStringUtil.getString(
										map2.get(disPlayNames.get(i)), "0");
								List<TeePerson> list = personDao
										.getPersonByUuids(personIds);
								String personNames = "";
								for (int j = 0; j < list.size(); j++) {
									if (j == list.size() - 1) {
										personNames += list.get(j)
												.getUserName();
									} else {
										personNames += list.get(j)
												.getUserName() + ",";
									}
								}
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
										personNames);
							} else if (("SINGLEDEPT").equals(displayTypes
									.get(i))) {// 单选部门
								TeeDepartment dept = deptDao.get(TeeStringUtil
										.getInteger(
												map2.get(disPlayNames.get(i)),
												0));
								if (dept != null) {
									String deptName = dept.getDeptName();
									map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
											deptName);
								}

							} else if (("MANYDEPT").equals(displayTypes.get(i))) {// 多选部门
								List<TeeDepartment> list = deptDao
										.getDeptListByUuids(TeeStringUtil
												.getString(map2
														.get(disPlayNames
																.get(i)), "0"));
								String deptNames = "";
								for (int j = 0; j < list.size(); j++) {
									if (j == list.size() - 1) {
										deptNames += list.get(j).getDeptName();
									} else {
										deptNames += list.get(j).getDeptName()
												+ ",";
									}
								}
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
										deptNames);
							} else if (("SINGLEROLE").equals(displayTypes
									.get(i))) {// 单选角色
								TeeUserRole role = roleDao.get(TeeStringUtil
										.getInteger(
												map2.get(disPlayNames.get(i)),
												0));
								if (role != null) {
									String roleName = role.getRoleName();
									map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
											roleName);
								}

							} else if (("MANYROLE").equals(displayTypes.get(i))) {// 多选角色
								String roleIds = TeeStringUtil.getString(map2
										.get(disPlayNames.get(i)));
								String ids[] = roleIds.split(",");
								String roleNames = "";
								for (int j = 0; j < ids.length; j++) {
									TeeUserRole role = roleDao
											.get(TeeStringUtil.getInteger(
													ids[j], 0));
									if (role != null) {
										String roleName = role.getRoleName();
										if (j == ids.length - 1) {
											roleNames += roleName;
										} else {
											roleNames += roleName + ",";
										}
									}

								}
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
										roleNames);
							} else if (("CURRENTUSER").equals(displayTypes
									.get(i))) {// 当前用户
								int userId = Integer.parseInt(map2.get(
										disPlayNames.get(i)).toString());
								// 根据用户id 获取用户对象
								TeePerson p = personDao.get(userId);
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
										p.getUserName());
							} else if (("CURRENTDEPT").equals(displayTypes
									.get(i))) {// 当前部门
								int deptId = Integer.parseInt(map2.get(
										disPlayNames.get(i)).toString());
								// 根据部门id 获取部门对象
								TeeDepartment dept = deptDao.get(deptId);
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
										dept.getDeptName());
							} else if (("CURRENTROLE").equals(displayTypes
									.get(i))) {// 当前角色
								int roleId = Integer.parseInt(map2.get(
										disPlayNames.get(i)).toString());
								// 根据角色主键 获取角色对象
								TeeUserRole role = roleDao.get(roleId);
								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC",
										role.getRoleName());
							} else if (("CURRENTTIME").equals(displayTypes
									.get(i))) {// 当前时间
								Date date = (Date) map2
										.get(disPlayNames.get(i));
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								String time = format.format(date);

								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC", time);
							} else if (("CURRENTDATE").equals(displayTypes
									.get(i))) {// 当前日期
								Date date = (Date) map2
										.get(disPlayNames.get(i));
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd");
								String time = format.format(date);

								map2.put(disPlayNames.get(i).toUpperCase() + "_DESC", time);
							} else if (("DATE").equals(displayTypes.get(i))) {
								Object value = map2.get(disPlayNames.get(i));
								Map<String, String> map1 = TeeJsonUtil
										.JsonStr2Map(displayModels.get(i));
								String datedisplay = map1.get("datedisplay");
								if (value != null) {
									if (value instanceof Calendar) {
										value = TeeDateUtil.format(
												((Calendar) value).getTime(),
												datedisplay);
									} else if (value instanceof Date) {
										value = TeeDateUtil.format(
												((Date) value), datedisplay);
									} else if (value instanceof java.sql.Date) {
										value = TeeDateUtil.format(
												((java.sql.Date) value),
												datedisplay);
									}
								}
								map2.put(disPlayNames.get(i).toUpperCase(), value);
							} else if (("FILEUPLOAD").equals(displayTypes
									.get(i))) {// 文件上传
								// 获取文件列表
								Object value = map2.get(disPlayNames.get(i));
								attaches = attachmentDao
										.getAttachmentsByIds(String
												.valueOf(value));
								List<TeeAttachmentModel> modelList = new ArrayList();
								TeeAttachmentModel model = null;
								for (TeeAttachment atta : attaches) {
									model=new TeeAttachmentModel();
									model.setSid(atta.getSid());
									model.setFileName(atta.getFileName());
									model.setExt(atta.getExt());
									modelList.add(model);
								}
								
								map2.put(disPlayNames.get(i).toUpperCase(), modelList);
							}
						}
					}
				}
				// System.out.println("查询结果的长度"+data);
				String sql3 = "select count(*) as c " + sql1;
				Map map1 = dbUtils.queryToMap(sql3, param.toArray());

				long total = Long.parseLong(map1.get("c").toString());
				if (data == null) {
					dataGridJson.setRows(new ArrayList());
					dataGridJson.setTotal(0L);
				} else {
					//將data里面map的键值  全部设置成大写
					List <Map>d=new ArrayList<Map>();
					Map mm=null;
					for (Map m : data) {
						mm=new HashMap();
						for(Object key:m.keySet()){
							mm.put(TeeStringUtil.getString(key).toUpperCase(), m.get(key));
						}
						d.add(mm);
					}
					dataGridJson.setRows(d);
					dataGridJson.setTotal(total);
				}
			}

		} catch (Exception e) {
			dataGridJson.setRows(new ArrayList());
			dataGridJson.setTotal(0L);
			e.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}
		return dataGridJson;

	}

	/**
	 * 获取业务建模的shortContent 替换其中的控件 动态渲染页面
	 * 
	 * @param bisKey
	 * @param operate
	 * @return
	 */
	public TeeJson getModelContent(String bisKey, String operate,
			HttpServletRequest request) {
		// 获取当前登录的用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		// 根据bisKey获取bisForm对象
		BusinessModel model = businessModelDao.get(bisKey);
		BisForm form = model.getBisForm();
		// 获取bisForm绑定的表格
		BisTable table = form.getBisTable();
		// 获取表格的id
		int tableId = table.getSid();
		// 根据表格的id 获取字段的集合
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);
		// 获取shortContent
		String shortContent = form.getShortContent();
		// 根据字段的类型 显示格式 替换shortContent中的内容
		for (BisTableField field : fieldList) {
			// 获取字段的显示类型
			String fieldName = field.getFieldName();
			String fieldName1 = field.getFieldName().toUpperCase();
			String fieldDisplayType = field.getFieldDisplayType();
			String fieldControlModel = field.getFieldControlModel();
			int required = field.getIsRequired();
			String fieldDesc = field.getFieldDesc();
			if (!("").equals(fieldDisplayType) && fieldDisplayType != null) {
				if (required == 1) {
					if ("NUMBER".equals(fieldDisplayType)) {// 数字显示格式
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", "<input type=\"text\" name=\""
								+ fieldName1 + "\" title=\"" + fieldDesc
								+ "\" id=\"" + fieldName1
								+ "\" required class=\"Text\" />");
					} else if ("INPUT".equals(fieldDisplayType)) {// 单行输入框
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", "<input type=\"text\" name=\""
								+ fieldName1 + "\" title=\"" + fieldDesc
								+ "\"  id=\"" + fieldName1
								+ "\" required class=\"Text\" required/>");
					} else if ("TEXTAREA".equals(fieldDisplayType)) {// 多行输入框
						shortContent = shortContent
								.replace(
										"${" + fieldName1 + "}",
										"<textarea type=\"text\" name=\""
												+ fieldName1
												+ "\" id=\""
												+ fieldName1
												+ "\"    title=\""
												+ fieldDesc
												+ "\"   class=\"TextArea\" required></textarea>");
					} else if ("RADIO".equals(fieldDisplayType)) {// 单选按钮
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						String html = "";
						if (map.get("sysno") != null
								&& !("").equals(map.get("sysno"))) {
							// 获取系统编码下的子项
							List<Map<String, Object>> codes = TeeSysCodeManager
									.getChildSysCodeListByParentCodeNo(map
											.get("sysno"));
							if (codes != null) {
								for (Map code : codes) {
									html += "<input type=\"radio\" id=\""
											+ fieldName1
											+ codes.indexOf(code)
											+ "\" "
											+ ((codes.indexOf(code)) == 0 ? "checked"
													: "") + " value=\""
											+ code.get("codeNo") + "\" name=\""
											+ fieldName1 + "\"   title=\""
											+ fieldDesc + "\"  required/>"
											+ "<label for='" + fieldName1
											+ codes.indexOf(code) + "'>"
											+ code.get("codeName")
											+ "</label>&nbsp;&nbsp;";
								}
								shortContent = shortContent.replace("${"
										+ fieldName1+ "}", html);
							}

						}
					} else if ("CHECKBOX".equals(fieldDisplayType)) {// 复选按钮
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						String html = "<input type=\"checkbox\" name=\""
								+ fieldName1 + "\" id=\"" + fieldName1
								+ "\" lable=\"" + map.get("lable")
								+ "\" value=\"1\" required title=\""
								+ fieldDesc + "\" />" + map.get("lable")
								+ "&nbsp;&nbsp;";

						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if ("SELECT".equals(fieldDisplayType)) {// 下拉列表
						String html = "<select name=\"" + fieldName1
								+ "\" id=\"" + fieldName1
								+ "\"  class=\"Text\"   title=\"" + fieldDesc
								+ "\"  required>";
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (map.get("sysno") != null
								&& !("").equals(map.get("sysno"))) {
							// 获取系统编码下的子项
							List<Map<String, Object>> codes = TeeSysCodeManager
									.getChildSysCodeListByParentCodeNo(map
											.get("sysno"));
							if (codes != null) {
								for (Map code : codes) {
									html += "<option value=\""
											+ code.get("codeNo") + "\">"
											+ code.get("codeName")
											+ "</option>";
								}
								html += "</select>";
								shortContent = shortContent.replace("${"
										+ fieldName1+ "}", html);
							}

						}
					} else if ("DATE".equals(fieldDisplayType)) {// 日期类型
						String html = "";
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (!("").equals(map.get("datedisplay"))
								&& map.get("datedisplay") != null) {
							html += "<input type=\"text\" id="
									+ fieldName1
									+ " name="
									+ fieldName1
									+ " size=\"18\" maxlength=\"19\"  onClick=\"WdatePicker({dateFmt:'"
									+ map.get("datedisplay")
									+ "'})\"  class=\"Text Wdate\" required title=\""
									+ fieldDesc + "\"  />";
						} else {
							html += "<input type=\"text\"  id="
									+ fieldName1
									+ " name="
									+ fieldName1
									+ " size=\"18\" maxlength=\"19\"  onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"  class=\"Text Wdate\" title=\""
									+ fieldDesc + "\" required />";
						}
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("SINGLEPERSON").equals(fieldDisplayType)) {// 单选人员
						String html = "<input id=\""
								+ fieldName1
								+ "\" name=\""
								+ fieldName1
								+ "\" type=\"hidden\"> "
								+ "<input id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"Text\" onClick=\"selectSingleUser(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\" type=\"text\" readonly=\"readonly\" title=\""
								+ fieldDesc
								+ "\"  required />"
								+ "&nbsp;<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("MANYPERSON").equals(fieldDisplayType)) {// 多选人员
						String html = "<input id=\""
								+ fieldName1
								+ "\" name=\""
								+ fieldName1
								+ "\" type=\"hidden\" >"
								+ "<textarea  id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"TextArea\" readonly=\"readonly\" onClick=\"selectUser(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"  title=\""
								+ fieldDesc
								+ "\" required></textarea>"
								+ "&nbsp;<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("SINGLEDEPT").equals(fieldDisplayType)) {// 单选部门
						String html = "<input id=\""
								+ fieldName1
								+ "\" name=\""
								+ fieldName1
								+ "\"  type=\"text\" style=\"display:none;\"/>"
								+ "<input id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ "  type=\"text\" class=\"Text readonly\" readonly onClick=\"selectSingleDept([ '"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"   title=\""
								+ fieldDesc
								+ "\"  required/>"
								+ "&nbsp;<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData( '"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("MANYDEPT").equals(fieldDisplayType)) {// 多选部门
						String html = "<input type=\"hidden\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" value=\"\"/>"
								+ "<textarea cols=30  id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"TextArea\" onClick=\"selectDept(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\" readonly  title=\""
								+ fieldDesc
								+ "\"  required></textarea>"
								+ "&nbsp;<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc');\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("SINGLEROLE").equals(fieldDisplayType)) {// 单选角色
						String html = " <input type=\"hidden\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" value=\"\">"
								+ "<input id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"Text\" readonly \" onClick=\"selectSingleRole(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\" title=\""
								+ fieldDesc
								+ "\" required/>"
								+ "&nbsp;<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("MANYROLE").equals(fieldDisplayType)) {// 多选角色
						String html = " <input type=\"hidden\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" value=\"\">"
								+ "<textarea cols=60 id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"TextArea\" readonly  onClick=\"selectRole(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\" title=\""
								+ fieldDesc
								+ "\" required></textarea>"
								+ "&nbsp;<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("ASSOCIATE").equals(fieldDisplayType)) {// 关联主表
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);

						int mainTablePkId = Integer.parseInt(map.get("key"));
						// 根据主表主键的id 获取主键的名称
						BisTableField ff = bisTableFieldDao.get(mainTablePkId);
						String mainTablePkName = ff.getFieldName();
						String html = "<button type=\"button\" id=\"\" name=\"\""
								+ " onclick=\"showMainTableDialog("
								+ field.getSid()
								+ ")\" class=\"Associate\">选择</button>";
						html += "<input type=\"hidden\" name=\"" + fieldName1
								+ "\" id=\"" + fieldName1 + "\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTUSER").equals(fieldDisplayType)) {// 当前用户
						String html = "<input type=\"hidden\" value=\""
								+ person.getUuid()
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\"/>"
								+ "<input type=\"text\" value=\""
								+ person.getUserName()
								+ "\"  name=\""
								+ fieldName1
								+ "_Desc\" id=\""
								+ fieldName1
								+ "_Desc\"  readonly=\"readonly\" class=\"Text\" title=\""
								+ fieldDesc + "\" required/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTDEPT").equals(fieldDisplayType)) {// 当前部门
						String html = "<input type=\"hidden\" value=\""
								+ person.getDept().getUuid()
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\"/>"
								+ "<input type=\"text\" value=\""
								+ person.getDept().getDeptName()
								+ "\"  name=\""
								+ fieldName1
								+ "_Desc\" id=\""
								+ fieldName1
								+ "_Desc\"  readonly=\"readonly\" class=\"Text\" title=\""
								+ fieldDesc + "\" required/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTROLE").equals(fieldDisplayType)) {// 当前角色
						String html = "<input type=\"hidden\" value=\""
								+ person.getUserRole().getUuid()
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\"/>"
								+ "<input type=\"text\" value=\""
								+ person.getUserRole().getRoleName()
								+ "\"  name=\""
								+ fieldName1
								+ "_Desc\" id=\""
								+ fieldName1
								+ "_Desc\"  readonly=\"readonly\" class=\"Text\" title=\""
								+ fieldDesc + "\" required />";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTDATE").equals(fieldDisplayType)) {// 当前日期
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						String value = format.format(d);
						String html = "<input type=\"text\" value=\""
								+ value
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" readonly=\"readonly\" class=\"Text\" title=\""
								+ fieldDesc + "\" required/>";
						shortContent = shortContent.replace("${" + fieldName
								+ "}", html);
					} else if (("CURRENTTIME").equals(fieldDisplayType)) {// 当前时间
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String value = format.format(d);
						String html = "<input type=\"text\" value=\""
								+ value
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" readonly=\"readonly\" class=\"Text\" title=\""
								+ fieldDesc + "\" required/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("FILEUPLOAD").equals(fieldDisplayType)) {// 上传控件
						String html = "<div id=\""
								+ fieldName1
								+ "FC\"></div>"
								+ "<div id=\""
								+ fieldName1
								+ "RC\"></div>"
								+ "<a id=\""
								+ fieldName1
								+ "UH\" class=\"add_swfupload\">"
								+ "<img src=\"/common/images/upload/batch_upload.png\"/>快速上传"
								+ "</a>" + "<input id=\"" + fieldName1
								+ "\" class=\"upload\" name=\"" + fieldName1
								+ "\" type=\"hidden\"/>" + "<input id=\""
								+ field.getSid() + "\"  type=\"hidden\"/>";
					}

				} else if (required == 0) {
					if ("NUMBER".equals(fieldDisplayType)) {// 数字显示格式
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", "<input type=\"text\" name=\""
								+ fieldName1 + "\"  id=\"" + fieldName1
								+ "\"  class=\"Text\" />");
					} else if ("INPUT".equals(fieldDisplayType)) {// 单行输入框
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", "<input type=\"text\" name=\""
								+ fieldName1 + "\" id=\"" + fieldName1
								+ "\"  class=\"Text\" />");
					} else if ("TEXTAREA".equals(fieldDisplayType)) {// 多行输入框
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", "<textarea type=\"text\" name=\""
								+ fieldName1 + "\" id=\"" + fieldName1
								+ "\" class=\"TextArea\"></textarea>");
					} else if ("RADIO".equals(fieldDisplayType)) {// 单选按钮
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						String html = "";
						if (map.get("sysno") != null
								&& !("").equals(map.get("sysno"))) {
							// 获取系统编码下的子项
							List<Map<String, Object>> codes = TeeSysCodeManager
									.getChildSysCodeListByParentCodeNo(map
											.get("sysno"));
							if (codes != null) {
								for (Map code : codes) {
									html += "<input type=\"radio\" id=\""
											+ fieldName1
											+ codes.indexOf(code)
											+ "\" "
											+ ((codes.indexOf(code)) == 0 ? "checked"
													: "") + " value=\""
											+ code.get("codeNo") + "\" name=\""
											+ fieldName1 + "\"/>"
											+ "<label for='" + fieldName1
											+ codes.indexOf(code) + "'>"
											+ code.get("codeName")
											+ "</label>&nbsp;&nbsp;";
								}
								shortContent = shortContent.replace("${"
										+ fieldName1+ "}", html);
							}

						}
					} else if ("CHECKBOX".equals(fieldDisplayType)) {// 复选按钮
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						String html = "<input type=\"checkbox\" name=\""
								+ fieldName1 + "\" id=\"" + fieldName1
								+ "\" lable=\"" + map.get("lable")
								+ "\" value=\"1\"/>" + map.get("lable")
								+ "&nbsp;&nbsp;";

						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if ("SELECT".equals(fieldDisplayType)) {// 下拉列表
						String html = "<select name=\"" + fieldName1
								+ "\" id=\"" + fieldName1
								+ "\"  class=\"Text\">";
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (map.get("sysno") != null
								&& !("").equals(map.get("sysno"))) {
							// 获取系统编码下的子项
							List<Map<String, Object>> codes = TeeSysCodeManager
									.getChildSysCodeListByParentCodeNo(map
											.get("sysno"));
							if (codes != null) {
								for (Map code : codes) {
									html += "<option value=\""
											+ code.get("codeNo") + "\">"
											+ code.get("codeName")
											+ "</option>";
								}
								html += "</select>";
								shortContent = shortContent.replace("${"
										+ fieldName1 + "}", html);
							}

						}
					} else if ("DATE".equals(fieldDisplayType)) {// 日期类型
						String html = "";
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (!("").equals(map.get("datedisplay"))
								&& map.get("datedisplay") != null) {
							html += "<input type=\"text\" id="
									+ fieldName1
									+ " name="
									+ fieldName1
									+ " size=\"18\" maxlength=\"19\"  onClick=\"WdatePicker({dateFmt:'"
									+ map.get("datedisplay")
									+ "'})\"  class=\"Text\"   />";
						} else {
							html += "<input type=\"text\"  id="
									+ fieldName1
									+ " name="
									+ fieldName1
									+ " size=\"18\" maxlength=\"19\"  onClick=\"WdatePicker({dateFmt:'yyyy-MM-dd'})\"  class=\"Text\"  />";
						}
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("SINGLEPERSON").equals(fieldDisplayType)) {// 单选人员
						String html = "<input id=\""
								+ fieldName1
								+ "\" name=\""
								+ fieldName1
								+ "\" type=\"hidden\"> "
								+ "<input id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"Text\" onClick=\"selectSingleUser(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"  readonly=\"readonly\" />"
								+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("MANYPERSON").equals(fieldDisplayType)) {// 多选人员
						String html = "<input id=\""
								+ fieldName1
								+ "\" name=\""
								+ fieldName1
								+ "\" type=\"hidden\" >"
								+ "<textarea  id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"TextArea\" readonly=\"readonly\" onClick=\"selectUser(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"></textarea>"
								+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("SINGLEDEPT").equals(fieldDisplayType)) {// 单选部门
						String html = "<input id=\""
								+ fieldName1
								+ "\" name=\""
								+ fieldName1
								+ "\"  type=\"text\" style=\"display:none;\"/>"
								+ "<input id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ "  type=\"text\" class=\"Text readonly\" readonly onClick=\"selectSingleDept([ '"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"/>"
								+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData( '"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("MANYDEPT").equals(fieldDisplayType)) {// 多选部门
						String html = "<input type=\"hidden\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" value=\"\"/>"
								+ "<textarea cols=30  id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"TextArea\" onClick=\"selectDept(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\" readonly></textarea>"
								+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc');\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("SINGLEROLE").equals(fieldDisplayType)) {// 单选角色
						String html = " <input type=\"hidden\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" value=\"\">"
								+ "<input id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"Text\" readonly \" onClick=\"selectSingleRole(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"/>"
								+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("MANYROLE").equals(fieldDisplayType)) {// 多选角色
						String html = " <input type=\"hidden\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\" value=\"\">"
								+ "<textarea cols=60 id=\""
								+ fieldName1
								+ "_Desc\" name=\""
								+ fieldName1
								+ "_Desc\""
								+ " class=\"TextArea\" readonly  onClick=\"selectRole(['"
								+ fieldName1
								+ "','"
								+ fieldName1
								+ "_Desc'])\"></textarea>"
								+ "<a href=\"javascript:void(0);\" class=\"orgClear\" onClick=\"clearData('"
								+ fieldName1 + "','" + fieldName1
								+ "_Desc')\">清空</a>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);

					} else if (("ASSOCIATE").equals(fieldDisplayType)) {// 关联主表
						Map<String, String> map = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);

						int mainTablePkId = Integer.parseInt(map.get("key"));
						// 根据主表主键的id 获取主键的名称
						BisTableField ff = bisTableFieldDao.get(mainTablePkId);
						String mainTablePkName = ff.getFieldName();
						String html = "<button type=\"button\" id=\"\" name=\"\""
								+ " onclick=\"showMainTableDialog("
								+ field.getSid()
								+ ")\" class=\"Associate\">选择</button>";
						html += "<input type=\"hidden\" name=\"" + fieldName1
								+ "\" id=\"" + fieldName1 + "\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTUSER").equals(fieldDisplayType)) {// 当前用户
						String html = "<input type=\"hidden\" value=\""
								+ person.getUuid()
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\"/>"
								+ "<input type=\"text\" value=\""
								+ person.getUserName()
								+ "\"  name=\""
								+ fieldName1
								+ "_Desc\" id=\""
								+ fieldName1
								+ "_Desc\"  readonly=\"readonly\" class=\"Text\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTDEPT").equals(fieldDisplayType)) {// 当前部门
						String html = "<input type=\"hidden\" value=\""
								+ person.getDept().getUuid()
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\"/>"
								+ "<input type=\"text\" value=\""
								+ person.getDept().getDeptName()
								+ "\"  name=\""
								+ fieldName1
								+ "_Desc\" id=\""
								+ fieldName1
								+ "_Desc\"  readonly=\"readonly\" class=\"Text\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTROLE").equals(fieldDisplayType)) {// 当前角色
						String html = "<input type=\"hidden\" value=\""
								+ person.getUserRole().getUuid()
								+ "\" name=\""
								+ fieldName1
								+ "\" id=\""
								+ fieldName1
								+ "\"/>"
								+ "<input type=\"text\" value=\""
								+ person.getUserRole().getRoleName()
								+ "\"  name=\""
								+ fieldName1
								+ "_Desc\" id=\""
								+ fieldName1
								+ "_Desc\"  readonly=\"readonly\" class=\"Text\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTDATE").equals(fieldDisplayType)) {// 当前日期
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						String value = format.format(d);
						String html = "<input type=\"text\" value=\"" + value
								+ "\" name=\"" + fieldName1 + "\" id=\""
								+ fieldName1
								+ "\" readonly=\"readonly\" class=\"Text\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("CURRENTTIME").equals(fieldDisplayType)) {// 当前时间
						Date d = new Date();
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String value = format.format(d);
						String html = "<input type=\"text\" value=\"" + value
								+ "\" name=\"" + fieldName1 + "\" id=\""
								+ fieldName1
								+ "\" readonly=\"readonly\" class=\"Text\"/>";
						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					} else if (("FILEUPLOAD").equals(fieldDisplayType)) {// 上传控件
						String html = "<div id=\""
								+ fieldName1
								+ "FC\"></div>"
								+ "<div id=\""
								+ fieldName1
								+ "RC\"></div>"
								+ "<a id=\""
								+ fieldName1
								+ "UH\" class=\"add_swfupload\">"
								+ "<img src=\"/common/images/upload/batch_upload.png\"/>快速上传"
								+ "</a>" + "<input id=\"" + fieldName1
								+ "\" class=\"upload\" name=\"" + fieldName1
								+ "\" type=\"hidden\"/>" + "<input id=\""
								+ field.getSid() + "\"  type=\"hidden\"/>";

						shortContent = shortContent.replace("${" + fieldName1
								+ "}", html);
					}

				}
			} else if (("").equals(fieldDisplayType)
					|| fieldDisplayType == null || fieldDisplayType == "") {// 字段显示类型是空
				shortContent = shortContent.replace("${" + fieldName1 + "}",
						"<input type=\"hidden\" name=\"" + fieldName1
								+ "\" id=\"" + fieldName1
								+ "\" class=\"form-control\"/>");

			}

		}
		json.setRtState(true);
		json.setRtData(shortContent);
		return json;
	}

	/**
	 * 根据bisKey获取表格的主键
	 * 
	 * @param bisKey
	 * @return
	 */
	public TeeJson getPkIdByBisKey(String bisKey) {
		TeeJson json = new TeeJson();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的id
		int tableId = table.getSid();
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);

		// 主键
		String key = "";
		for (BisTableField f : fieldList) {
			if (f.getPrimaryKeyFlag() == 1) {
				key = f.getFieldName();
			}

		}
		json.setRtData(key.toUpperCase());
		json.setRtState(true);
		return json;
	}

	/**
	 * 根据主键获取纪录
	 * 
	 * @param pkId
	 * @param bisKey
	 * @return
	 */
	public TeeJson getRecordByPkId(int pkId, String bisKey) {
		TeeJson json = new TeeJson();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的名称
		String tableName = table.getTableName();
		// 获取表格的id
		int tableId = table.getSid();
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);
		// 主键
		String key = "";
		for (BisTableField f : fieldList) {
			if (f.getPrimaryKeyFlag() == 1) {
				key = f.getFieldName();
			}
		}
		String sql = "select * from " + tableName + " where " + key + "="
				+ pkId;
		// 创建数据库连接对象
		Connection dbConn = null;
		try {
			// 获取数据库连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);
			Map map = dbUtils.queryToMap(sql);
			// 过滤map，第一步需要循环去获取fieldList列表字段集合
			for (BisTableField f : fieldList) {
				if (map.containsKey(f.getFieldName())) {
					// 去判断当前field的显示类型
					if (("SINGLEPERSON").equals(f.getFieldDisplayType())) {// 单选人员
						TeePerson p = personDao.get(TeeStringUtil.getInteger(
								map.get(f.getFieldName()), 0));
						if (p != null) {
							String personName = p.getUserName();
							//System.out.println(personName);
							map.put(f.getFieldName().toUpperCase() + "_Desc", personName);
						}

					} else if (("MANYPERSON").equals(f.getFieldDisplayType())) {// 多选人员
						String personIds = TeeStringUtil.getString(
								map.get(f.getFieldName()), "0");
						List<TeePerson> list = personDao
								.getPersonByUuids(personIds);
						String personNames = "";
						for (int j = 0; j < list.size(); j++) {
							if (j == list.size() - 1) {
								personNames += list.get(j).getUserName();
							} else {
								personNames += list.get(j).getUserName() + ",";
							}
						}
						map.put(f.getFieldName().toUpperCase() + "_Desc", personNames);
					} else if (("SINGLEDEPT").equals(f.getFieldDisplayType())) {// 单选部门
						String deptId = TeeStringUtil.getString(map.get(f.getFieldName()));
						TeeDepartment dept = deptDao.get(TeeStringUtil
								.getInteger(deptId, 0));
						if (dept != null) {
							String deptName = dept.getDeptName();
							//System.out.println(deptName);
							map.put(f.getFieldName().toUpperCase() + "_Desc", deptName);
						}

					} else if (("MANYDEPT").equals(f.getFieldDisplayType())) {// 多选部门
						String deptIds = TeeStringUtil.getString(
								map.get(f.getFieldName()), "0");
						List<TeeDepartment> list = deptDao
								.getDeptListByUuids(deptIds);
						String deptNames = "";
						for (int j = 0; j < list.size(); j++) {
							if (j == list.size() - 1) {
								deptNames += list.get(j).getDeptName();
							} else {
								deptNames += list.get(j).getDeptName() + ",";
							}
						}
						map.put(f.getFieldName().toUpperCase() + "_Desc", deptNames);
					} else if (("SINGLEROLE").equals(f.getFieldDisplayType())) {// 单选角色
						TeeUserRole role = roleDao.get(TeeStringUtil
								.getInteger(map.get(f.getFieldName()), 0));
						if (role != null) {
							String roleName = role.getRoleName();
							map.put(f.getFieldName().toUpperCase() + "_Desc", roleName);
						}

					} else if (("MANYROLE").equals(f.getFieldDisplayType())) {// 多选角色
						String roleIds = TeeStringUtil.getString(map.get(f
								.getFieldName()));
						String ids[] = roleIds.split(",");
						String roleNames = "";
						for (int j = 0; j < ids.length; j++) {
							TeeUserRole role = roleDao.get(TeeStringUtil
									.getInteger(ids[j], 0));
							if (role != null) {
								String roleName = role.getRoleName();
								if (j == ids.length - 1) {
									roleNames += roleName;
								} else {
									roleNames += roleName + ",";
								}
							}
						}
						map.put(f.getFieldName().toUpperCase() + "_Desc", roleNames);
					} else if (("CURRENTUSER").equals(f.getFieldDisplayType())) {// 当前用户
						int userId = Integer.parseInt(map.get(f.getFieldName())
								.toString());
						// 根据用户id 获取用户对象
						TeePerson p = personDao.get(userId);
						map.put(f.getFieldName().toUpperCase() + "_Desc", p.getUserName());
					} else if (("CURRENTDEPT").equals(f.getFieldDisplayType())) {// 当前部门
						int deptId = Integer.parseInt(map.get(f.getFieldName())
								.toString());
						// 根据部门id 获取部门对象
						TeeDepartment dept = deptDao.get(deptId);
						map.put(f.getFieldName().toUpperCase() + "_Desc", dept.getDeptName());
					} else if (("CURRENTROLE").equals(f.getFieldDisplayType())) {// 当前角色
						int roleId = Integer.parseInt(map.get(f.getFieldName())
								.toString());
						// 根据角色主键 获取角色对象
						TeeUserRole role = roleDao.get(roleId);
						map.put(f.getFieldName().toUpperCase() + "_Desc", role.getRoleName());
					} else if (("CURRENTTIME").equals(f.getFieldDisplayType())) {// 当前时间
						Date date = (Date) map.get(f.getFieldName());
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String time = format.format(date);

						map.put(f.getFieldName().toUpperCase(), time);
					} else if (("CURRENTDATE").equals(f.getFieldDisplayType())) {// 当前日期
						Date date = (Date) map.get(f.getFieldName());
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						String time = format.format(date);

						map.put(f.getFieldName().toUpperCase(), time);
					} else if (("DATE").equals(f.getFieldDisplayType())) {
						Object value = map.get(f.getFieldName());
						Map<String, String> map1 = TeeJsonUtil.JsonStr2Map(f
								.getFieldControlModel());
						String datedisplay = map1.get("datedisplay");
						if (value != null) {
							if (value instanceof Calendar) {
								value = TeeDateUtil.format(
										((Calendar) value).getTime(),
										datedisplay);
							} else if (value instanceof Date) {
								value = TeeDateUtil.format(((Date) value),
										datedisplay);
							} else if (value instanceof java.sql.Date) {
								value = TeeDateUtil.format(
										((java.sql.Date) value), datedisplay);
							}
						}
						map.put(f.getFieldName().toUpperCase(), value);
						//System.out.println(map);
					}
				}
			}
			dbConn.commit();
			
			//将map集合的所有的健轉換成大寫
//			Map mm=new HashMap();
//			for (Object k:map.keySet()) {
//				mm.put(TeeStringUtil.getString(k).toUpperCase(),map.get(k) );
//			}
			
			
			json.setRtData(map);
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(dbConn);
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}

		return json;
	}

	/**
	 * 添加或者编辑表格中的数据
	 * 
	 * @param bisKey
	 * @param operate
	 * @param param
	 * @return
	 * @throws SQLException
	 */
	public TeeJson addOrUpdateRecord(String bisKey, String operate,
			String param, int pkId) {
		TeeJson json = new TeeJson();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的名称
		String tableName = table.getTableName();
		// 获取表格的数据源
		BisDataSource dataSource = table.getBisDataSource();
		// 获取数据库的类型
		String dbType = dataSource.getDbType();
		// 获取表格的id
		int tableId = table.getSid();
		// 根据表格的id 获取字段的集合
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);
		// 将页面上传来的参数转换成Map对象

		Map map = TeeJsonUtil.JsonStr2Map(param);
		Map<String, Object> map1 = new HashMap<String, Object>();
		/**
		 * 字段类型 NUMBER数字类型 DATE日期类型 DATETIME日期和时间类型 CHAR字符类型 VARCHAR字符串类型 TEXT
		 */
		String key = "";// 主键
		String generatePlugin="";//主键生成策略
		// 存放类型的集合
		Map<String, String> map2 = new HashMap<String, String>();

		// 存放字段类型是附件FILEUPLOAD
		String attIds = "";
		for (BisTableField f : fieldList) {
			map2.put(f.getFieldName(), f.getFieldType());
			if (f.getPrimaryKeyFlag() == 1) {
				key = f.getFieldName();
				generatePlugin=f.getGeneratePlugin();
			}

			// 判断请求的数据是否在字段列表中，不存在则继续
			if (!map.containsKey(f.getFieldName().toUpperCase())) {
				continue;
			}

			String fieldName = f.getFieldName();
			String fieldType = f.getFieldType();
			String fieldValue = (String) map.get(fieldName.toUpperCase());
			String fieldDisplayType = f.getFieldDisplayType();
			if ("FILEUPLOAD".equals(fieldDisplayType)) {
				String[] ids = fieldValue.split(",");
				for (String id : ids) {
					attIds += id + ",";
				}
			}

			if ("DATE".equals(fieldType)) {
				Date value = TeeDateUtil.format(fieldValue, "yyyy-MM-dd");
				map1.put(fieldName, value);
			} else if ("DATETIME".equals(fieldType)) {
				Date value = TeeDateUtil.format(fieldValue,
						"yyyy-MM-dd HH:mm:ss");
				map1.put(fieldName, value);
			} else if ("NUMBER".equals(fieldType)) {
				Double value = TeeStringUtil.getDouble(fieldValue, 0.00);
				map1.put(fieldName, value);
			} else {
				map1.put(fieldName, fieldValue);
			}
		}

		// 定义一个List集合存放字段对应的值
		List<Object> list = new ArrayList<Object>();
		Set<String> keys = map1.keySet();
		// 根据数据库类型不同拼接不同的sql语句
		String sql = "";

		// 创建数据库连接对象
		Connection dbConn = null;
		try {
			// 获取数据库连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);

			if ("MYSQL".equals(dbType) || "SQLSERVER".equals(dbType) || "DAMENG".equals(dbType)) {// Mysql
																		// and
																		// sqlServer
				if (("add").equals(operate)) {// 新建
					sql += "insert into " + tableName + "(";
					for (String insertedField : keys) {
						list.add(map1.get(insertedField));
						sql += insertedField + ",";
					}
					if (sql.endsWith(",")) {
						sql = sql.substring(0, sql.length() - 1);
					}
					sql += ") values(";
					for (String insertedField : keys) {
						sql += "?,";
					}
					if (sql.endsWith(",")) {
						sql = sql.substring(0, sql.length() - 1);
					}
					sql += ") ";
					// 将存放到list中的参数 转换成数组形式
					Object[] para = new Object[list.size()];
					for (int i = 0; i < list.size(); i++) {
						para[i] = list.get(i);
					}

					Object primaryId = dbUtils.executeInsert(sql, dbType, para);

					// 附件处理
					if (attIds.endsWith(",")) {
						attIds = attIds.substring(0, attIds.length() - 1);
					}
					List<TeeAttachment> attachments = attachmentDao
							.getAttachmentsByIds(attIds);
					for (TeeAttachment teeAttachment : attachments) {
						teeAttachment.setModelId(primaryId.toString());
						attachmentDao.update(teeAttachment);
					}

				} else if (("update").equals(operate)) {// 编辑
					sql += "update " + tableName + " set ";
					for (String fieldName : keys) {
							sql += fieldName + "=?" + ",";
							list.add(map1.get(fieldName));		
					}

					if (sql.endsWith(",")) {
						sql = sql.substring(0, sql.length() - 1);
					}

					// 将存放到list中的参数 转换成数组形式
					Object[] para = new Object[list.size()];
					for (int i = 0; i < list.size(); i++) {
						para[i] = list.get(i);
					}

					sql += " where " + key + "=" + pkId;
					
					
					dbUtils.executeUpdate(sql, para);
					// 附件处理  处理新上传的附件
					if (attIds.endsWith(",")) {
						attIds = attIds.substring(0, attIds.length() - 1);
					}
					List<TeeAttachment> attachments = attachmentDao
							.getAttachmentsByIds(attIds);
					for (TeeAttachment teeAttachment : attachments) {
						teeAttachment.setModelId(String.valueOf(pkId));
						attachmentDao.update(teeAttachment);
					}
					
					
					
					//执行完sql语句  重新更改记录的附件值
					String s= "update " + tableName + " set ";
					List<String> l=new ArrayList<String>();
					for (String fieldName : keys) {						
						//根据fieldName和表格table获取对应的字段对象
						String h="from BisTableField field where field.bisTable.sid="+table.getSid()+" and field.fieldName='"+fieldName+"'";
						BisTableField field=(BisTableField) simpleDaoSupport.unique(h, null);
						if(("FILEUPLOAD").equals(field.getFieldDisplayType())){//附件类型
							//获取附件表中的id
							List<TeeAttachment> AttList=attachmentDao.getAttaches("BIS_ITEM_"+field.getSid(), String.valueOf(pkId));
							String Ids="";
							for (TeeAttachment teeAttachment : AttList) {
								Ids+=teeAttachment.getSid()+",";
							}
							if(Ids.endsWith(",")){
								Ids=Ids.substring(0,Ids.length()-1);
							}
							
							s+= fieldName + "=?" + ",";
							l.add(Ids);	
		
						}
					}
					if (s.endsWith(",")) {
						s = s.substring(0, s.length() - 1);
					}
						// 将存放到list中的参数 转换成数组形式
						Object[] p = new Object[l.size()];
						for (int i = 0; i < l.size(); i++) {
							p[i] = l.get(i);
						}
						if(!("update " + tableName + " set ").equals(s)){
							s += " where " + key + "=" + pkId;
							dbUtils.executeUpdate(s, p);
						}
						
					
					
					
				}

			}else if ("ORACLE".equals(dbType) || "KINGBASE".equals(dbType)){//oracle数据库
				if (("add").equals(operate)) {// 新建
					sql += "insert into " + tableName + "("+key+",";
					//先要找到主键的字段  最后要拼成 insert into stu_table(sid,f1,f2,f3) values(seq.nextval,?,?,?)
					
					
					for (String insertedField : keys) {
						list.add(map1.get(insertedField));
						sql += insertedField + ",";
					}
					if (sql.endsWith(",")) {
						sql = sql.substring(0, sql.length() - 1);
					}
					sql += ") values("+generatePlugin+".nextVal,";
					for (String insertedField : keys) {
						sql += "?,";
					}
					if (sql.endsWith(",")) {
						sql = sql.substring(0, sql.length() - 1);
					}
					sql += ") ";
					// 将存放到list中的参数 转换成数组形式
					Object[] para = new Object[list.size()];
					
					for (int i = 0; i < list.size(); i++) {
						para[i] = list.get(i);
					}

					Object primaryId = dbUtils.executeInsert(sql, dbType, para);

					// 附件处理
					if (attIds.endsWith(",")) {
						attIds = attIds.substring(0, attIds.length() - 1);
					}
					List<TeeAttachment> attachments = attachmentDao
							.getAttachmentsByIds(attIds);
					for (TeeAttachment teeAttachment : attachments) {
						teeAttachment.setModelId(primaryId.toString());
						attachmentDao.update(teeAttachment);
					}

				} else if (("update").equals(operate)) {// 编辑
					sql += "update " + tableName + " set ";
					for (String fieldName : keys) {
							sql += fieldName + "=?" + ",";
							list.add(map1.get(fieldName));		
					}

					if (sql.endsWith(",")) {
						sql = sql.substring(0, sql.length() - 1);
					}

					// 将存放到list中的参数 转换成数组形式
					Object[] para = new Object[list.size()];
					for (int i = 0; i < list.size(); i++) {
						para[i] = list.get(i);
					}

					sql += " where " + key + "=" + pkId;
					
					
					dbUtils.executeUpdate(sql, para);
					// 附件处理  处理新上传的附件
					if (attIds.endsWith(",")) {
						attIds = attIds.substring(0, attIds.length() - 1);
					}
					List<TeeAttachment> attachments = attachmentDao
							.getAttachmentsByIds(attIds);
					for (TeeAttachment teeAttachment : attachments) {
						teeAttachment.setModelId(String.valueOf(pkId));
						attachmentDao.update(teeAttachment);
					}
					
					
					
					//执行完sql语句  重新更改记录的附件值
					String s= "update " + tableName + " set ";
					List<String> l=new ArrayList<String>();
					for (String fieldName : keys) {						
						//根据fieldName和表格table获取对应的字段对象
						String h="from BisTableField field where field.bisTable.sid="+table.getSid()+" and field.fieldName='"+fieldName+"'";
						BisTableField field=(BisTableField) simpleDaoSupport.unique(h, null);
						if(("FILEUPLOAD").equals(field.getFieldDisplayType())){//附件类型
							//获取附件表中的id
							List<TeeAttachment> AttList=attachmentDao.getAttaches("BIS_ITEM_"+field.getSid(), String.valueOf(pkId));
							String Ids="";
							for (TeeAttachment teeAttachment : AttList) {
								Ids+=teeAttachment.getSid()+",";
							}
							if(Ids.endsWith(",")){
								Ids=Ids.substring(0,Ids.length()-1);
							}
							
							s+= fieldName + "=?" + ",";
							l.add(Ids);	
		
						}
					}
					if (s.endsWith(",")) {
						s = s.substring(0, s.length() - 1);
					}
						// 将存放到list中的参数 转换成数组形式
						Object[] p = new Object[l.size()];
						for (int i = 0; i < l.size(); i++) {
							p[i] = l.get(i);
						}
						if(!("update " + tableName + " set ").equals(s)){
							s += " where " + key + "=" + pkId;
							dbUtils.executeUpdate(s, p);
						}
						
					
					
					
				}
			}

			dbConn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(dbConn);
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}

		json.setRtState(true);
		return json;

	}

	/**
	 * 根据字段的主键 获取字段的对象
	 * 
	 * @param fieldId
	 * @return
	 */
	public TeeJson getFieldById(int fieldId) {
		TeeJson json = new TeeJson();
		BisTableField field = bisTableFieldDao.get(fieldId);
		json.setRtData(field);
		json.setRtState(true);
		return json;
	}

	/**
	 * 获取主表的列表数据
	 * 
	 * @param tableId
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getMainTableRecordsList(int tableId,
			String sqlFilter, TeeDataGridModel dm, HttpServletRequest request) {
		// 获取当前登录的用户信息,
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 当前登录的部门
		TeeDepartment dept = person.getDept();
		// 当前登录的角色
		TeeUserRole role = person.getUserRole();

		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 根据表格的主键 获取表格的名称
		BisTable table = bisTableDao.get(tableId);
		String mainTableName = table.getTableName();
		String sql = "select * from " + mainTableName;
		if (sqlFilter != null && !("").equals(sqlFilter)
				&& !("null").equals(sqlFilter)) {
			sqlFilter = sqlFilter.replace("$[USER_NAME]", person.getUserName())
					.replace("$[USER_ID]", person.getUuid() + "");
			if (dept != null) {
				sqlFilter = sqlFilter.replace("$[DEPT_NAME]",
						dept.getDeptName()).replace("$[DEPT_ID]",
						dept.getUuid() + "");
			}
			if (role != null) {
				sqlFilter = sqlFilter.replace("$[ROLE_NAME]",
						role.getRoleName()).replace("$[ROLE_ID]",
						role.getUuid() + "");
			}

			sql += " where " + sqlFilter;
		}
		Connection dbConn = null;
		try {

			// 获取连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);

			List<Map> data = dbUtils.queryToMapList(sql, null, dm.getRows()
					* (dm.getPage() - 1), dm.getRows());
			String sql3 = "select count(*) as c from " + mainTableName;
			Map map = dbUtils.queryToMap(sql3, null);

			long total = Long.parseLong(map.get("c").toString());
			dataGridJson.setRows(data);
			dataGridJson.setTotal(total);
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(dbConn);
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}
		return dataGridJson;
	}

	/**
	 * 查看
	 * 
	 * @param bisKey
	 * @param pkId
	 * @return
	 */
	public TeeJson view(String bisKey, int pkId) {
		List<TeeAttachmentModel> modelList  = new ArrayList<TeeAttachmentModel>();
        
		TeeJson json = new TeeJson();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的名称
		String tableName = table.getTableName();
		// 获取表格的id
		int tableId = table.getSid();
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);
		// 主键
		String key = "";
		for (BisTableField f : fieldList) {
			if (f.getPrimaryKeyFlag() == 1) {
				key = f.getFieldName();
			}
		}
		String sql = "select * from " + tableName + " where " + key + "="
				+ pkId;
		// 创建数据库连接对象
		Connection dbConn = null;
		try {
			// 获取数据库连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);
			Map map = dbUtils.queryToMap(sql);
			// 过滤map，第一步需要循环去获取fieldList列表字段集合
			for (BisTableField f : fieldList) {
				if (map.containsKey(f.getFieldName().toUpperCase())) {
					// 去判断当前field的显示类型
					if (("SINGLEPERSON").equals(f.getFieldDisplayType())) {// 单选人员
						TeePerson p = personDao.get(TeeStringUtil.getInteger(
								map.get(f.getFieldName().toUpperCase()), 0));
						if (p != null) {
							String personName = p.getUserName();
							map.put(f.getFieldName().toUpperCase() + "_Desc", personName);
						}

					} else if (("MANYPERSON").equals(f.getFieldDisplayType())) {// 多选人员
						List<TeePerson> list = personDao
								.getPersonByUuids(TeeStringUtil.getString(
										map.get(f.getFieldName()), "0"));
						String personNames = "";
						for (int j = 0; j < list.size(); j++) {
							if (j == list.size() - 1) {
								personNames += list.get(j).getUserName();
							} else {
								personNames += list.get(j).getUserName() + ",";
							}
						}
						map.put(f.getFieldName().toUpperCase() + "_Desc", personNames);
					} else if (("SINGLEDEPT").equals(f.getFieldDisplayType())) {// 单选部门
						TeeDepartment dept = deptDao.get(TeeStringUtil
								.getInteger(map.get(f.getFieldName().toUpperCase()), 0));
						if (dept != null) {
							String deptName = dept.getDeptName();
							map.put(f.getFieldName().toUpperCase() + "_Desc", deptName);
						}
					} else if (("MANYDEPT").equals(f.getFieldDisplayType())) {// 多选部门
						List<TeeDepartment> list = deptDao
								.getDeptListByUuids(TeeStringUtil.getString(
										map.get(f.getFieldName().toUpperCase()), "0"));
						String deptNames = "";
						for (int j = 0; j < list.size(); j++) {
							if (j == list.size() - 1) {
								deptNames += list.get(j).getDeptName();
							} else {
								deptNames += list.get(j).getDeptName() + ",";
							}
						}
						map.put(f.getFieldName().toUpperCase() + "_Desc", deptNames);
					} else if (("SINGLEROLE").equals(f.getFieldDisplayType())) {// 单选角色
						TeeUserRole role = roleDao.get(TeeStringUtil
								.getInteger(map.get(f.getFieldName().toUpperCase()), 0));
						if (role != null) {
							String roleName = role.getRoleName();
							map.put(f.getFieldName().toUpperCase() + "_Desc", roleName);
						}

					} else if (("MANYROLE").equals(f.getFieldDisplayType())) {// 多选角色
						String roleIds = TeeStringUtil.getString(map.get(f
								.getFieldName().toUpperCase()));
						String ids[] = roleIds.split(",");
						String roleNames = "";
						for (int j = 0; j < ids.length; j++) {
							TeeUserRole role = roleDao.get(TeeStringUtil
									.getInteger(ids[j], 0));
							if (role != null) {
								String roleName = role.getRoleName();
								if (j == ids.length - 1) {
									roleNames += roleName;
								} else {
									roleNames += roleName + ",";
								}
							}
						}
						map.put(f.getFieldName().toUpperCase() + "_Desc", roleNames);
					} else if (("CURRENTUSER").equals(f.getFieldDisplayType())) {// 当前用户
						int userId = Integer.parseInt(map.get(f.getFieldName().toUpperCase())
								.toString());
						// 根据用户id 获取用户对象
						TeePerson p = personDao.get(userId);
						map.put(f.getFieldName().toUpperCase() + "_DESC", p.getUserName());
					} else if (("CURRENTDEPT").equals(f.getFieldDisplayType())) {// 当前部门
						int deptId = Integer.parseInt(map.get(f.getFieldName().toUpperCase())
								.toString());
						// 根据部门id 获取部门对象
						TeeDepartment dept = deptDao.get(deptId);
						map.put(f.getFieldName().toUpperCase() + "_DESC", dept.getDeptName());
					} else if (("CURRENTROLE").equals(f.getFieldDisplayType())) {// 当前角色
						int roleId = Integer.parseInt(map.get(f.getFieldName().toUpperCase())
								.toString());
						// 根据角色主键 获取角色对象
						TeeUserRole role = roleDao.get(roleId);
						map.put(f.getFieldName().toUpperCase() + "_DESC", role.getRoleName());
					} else if (("CURRENTTIME").equals(f.getFieldDisplayType())) {// 当前时间
						Date date = (Date) map.get(f.getFieldName().toUpperCase());
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String time = format.format(date);

						map.put(f.getFieldName().toUpperCase() + "_DESC", time);
					} else if (("CURRENTDATE").equals(f.getFieldDisplayType())) {// 当前日期
						Date date = (Date) map.get(f.getFieldName().toUpperCase());
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						String time = format.format(date);

						map.put(f.getFieldName().toUpperCase() + "_DESC", time);
					}
				}
			}

			dbConn.commit();
			// 获取shortContent
			String shortContent = form.getShortContent();
			// 根据字段的类型 显示格式 替换shortContent中的内容
			for (BisTableField field : fieldList) {
				// 获取字段的显示类型
				String fieldName = field.getFieldName().toUpperCase();
				String fieldDisplayType = field.getFieldDisplayType();
				String fieldControlModel = field.getFieldControlModel();

				if (!("").equals(fieldDisplayType) && fieldDisplayType != null) {
					if ("NUMBER".equals(fieldDisplayType)) {// 数字显示格式
						shortContent = shortContent.replace("${" + fieldName
								+ "}", map.get(fieldName.toUpperCase()) + "");
					} else if ("INPUT".equals(fieldDisplayType)) {// 单行输入框
						shortContent = shortContent.replace("${" + fieldName
								+ "}", map.get(fieldName.toUpperCase()) + "");
					} else if ("TEXTAREA".equals(fieldDisplayType)) {// 多行输入框
						shortContent = shortContent.replace("${" + fieldName
								+ "}", map.get(fieldName.toUpperCase()) + "");
					} else if ("RADIO".equals(fieldDisplayType)) {// 单选按钮
						Map<String, String> map1 = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						String html = null;
						if (map1.get("sysno") != null
								&& !("").equals(map1.get("sysno"))) {
							// 获取系统编码下的子项
							List<Map<String, Object>> codes = TeeSysCodeManager
									.getChildSysCodeListByParentCodeNo(map1
											.get("sysno"));
							if (codes != null) {
								for (Map code : codes) {
									if (code.get("codeNo").equals(
											map.get(fieldName.toUpperCase()))) {
										html = (String) code.get("codeName");
									}
								}
								shortContent = shortContent.replace("${"
										+ fieldName + "}", html);
							}

						}
					} else if ("CHECKBOX".equals(fieldDisplayType)) {// 复选按钮
						Map<String, String> map1 = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (("0").equals(map.get(fieldName.toUpperCase()))) {
							shortContent = shortContent.replace("${"
									+ fieldName + "}", "&nbsp;&nbsp;");
						} else {
							shortContent = shortContent.replace("${"
									+ fieldName + "}", map1.get("lable")
									+ "&nbsp;&nbsp;");
						}

					} else if ("SELECT".equals(fieldDisplayType)) {// 下拉列表
						String html = "";
						Map<String, String> map1 = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (map1.get("sysno") != null
								&& !("").equals(map1.get("sysno"))) {
							// 获取系统编码下的子项
							List<Map<String, Object>> codes = TeeSysCodeManager
									.getChildSysCodeListByParentCodeNo(map1
											.get("sysno"));
							if (codes != null) {
								for (Map code : codes) {
									if (code.get("codeNo").equals(
											map.get(fieldName.toUpperCase()))) {
										html = (String) code.get("codeName");
									}
								}
								shortContent = shortContent.replace("${"
										+ fieldName + "}", html);
							}

						}
					} else if ("DATE".equals(fieldDisplayType)) {// 日期类型
						Map<String, String> map1 = TeeJsonUtil
								.JsonStr2Map(fieldControlModel);
						if (!("").equals(map1.get("datedisplay"))
								&& map1.get("datedisplay") != null) {
							String dateStr="";
							if (map.get(fieldName.toUpperCase()) instanceof Calendar) {
								dateStr = TeeDateUtil.format(
										((Calendar) map.get(fieldName.toUpperCase())).getTime(),
										map1.get("datedisplay"));
							} else if (map.get(fieldName.toUpperCase()) instanceof Date) {
								dateStr = TeeDateUtil.format(
										((Date) map.get(fieldName.toUpperCase())), map1.get("datedisplay"));
							} else if (map.get(fieldName.toUpperCase()) instanceof java.sql.Date) {
								dateStr = TeeDateUtil.format(
										((java.sql.Date) map.get(fieldName.toUpperCase())),
										map1.get("datedisplay"));
							}else if (map.get(fieldName.toUpperCase()) instanceof java.lang.String) {
								dateStr = TeeStringUtil.getString(map.get(fieldName.toUpperCase()));
							}
							shortContent = shortContent.replace("${"
									+ fieldName + "}", dateStr);
							
							
						} else {
							Date date = (Date) map.get(fieldName.toUpperCase());
							SimpleDateFormat f = new SimpleDateFormat(
									"yyyy-MM-dd");
							String dateStr = f.format(date);
							shortContent = shortContent.replace("${"
									+ fieldName + "}", dateStr);
						}

					} else if (("SINGLEPERSON").equals(fieldDisplayType)) {// 单选人员

						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("MANYPERSON").equals(fieldDisplayType)) {// 多选人员
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("SINGLEDEPT").equals(fieldDisplayType)) {// 单选部门
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("MANYDEPT").equals(fieldDisplayType)) {// 多选部门
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("SINGLEROLE").equals(fieldDisplayType)) {// 单选角色
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));
					} else if (("MANYROLE").equals(fieldDisplayType)) {// 多选角色
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("ASSOCIATE").equals(fieldDisplayType)) {
						shortContent = shortContent.replace("${" + fieldName
								+ "}", "");

					} else if (("CURRENTUSER").equals(fieldDisplayType)) {// 当前用户
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("CURRENTDEPT").equals(fieldDisplayType)) {// 当前部门
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("CURRENTROLE").equals(fieldDisplayType)) {// 当前角色
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("CURRENTDATE").equals(fieldDisplayType)) {// 当前日期
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("CURRENTTIME").equals(fieldDisplayType)) {// 当前时间
						shortContent = shortContent.replace(
								"${" + fieldName + "}",
								TeeStringUtil.getString(map.get(fieldName.toUpperCase()
										+ "_DESC")));

					} else if (("FILEUPLOAD").equals(fieldDisplayType)) {// 附件
						String html = "";
						String attIds = (String) map.get(fieldName.toUpperCase());
						if (!TeeUtility.isNullorEmpty(attIds)) {
							List<TeeAttachment> attList = attachmentDao
									.getAttachmentsByIds(attIds);
						
							TeeAttachmentModel model = null;
							for (TeeAttachment atta : attList) {
								model = new TeeAttachmentModel();
								model.setSid(atta.getSid());
								model.setFileName(atta.getFileName());
								model.setExt(atta.getExt());
								modelList.add(model);
							}
							for (TeeAttachment teeAttachment : attList) {
								html += "<p class='attach' fileName='"
										+ teeAttachment.getFileName()
										+ "' ext='" + teeAttachment.getExt()
										+ "' sid='" + teeAttachment.getSid()
										+ "'></p>";
							}
							
							shortContent = shortContent.replace("${"
									+ fieldName + "}", html);
						}else{
							shortContent = shortContent.replace("${"
									+ fieldName + "}","");
							
						}

					} else if (("").equals(fieldDisplayType)
							|| fieldDisplayType == null
							|| fieldDisplayType == "") {// 字段显示类型是空
						shortContent = shortContent.replace("${" + fieldName
								+ "}", "<input type=\"hidden\" name=\""
								+ fieldName.toUpperCase() + "\" id=\"" + fieldName.toUpperCase() + "\"/>");
					}

				}
			}
			/*
			 * Map result=new HashMap(); result.put("content",shortContent);
			 * result.put("attList",modelList);
			 */
			List result = new ArrayList();
			result.add(shortContent);
			result.add(modelList);
			json.setRtState(true);
			json.setRtData(result);
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(dbConn);
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}

		return json;
	}
	/*public TeeJson get(String bisKey, int pkId){
		TeeJson json=new TeeJson();
		
		 cc=businessModelDao.get(bisKey);
		TeeUserInfoModel userInfoModel=new TeeUserInfoModel();
		BeanUtils.copyProperties(userInfo,userInfoModel);
		
		userInfoModel.setBirthdays(TeeDateUtil.format(userInfo.getBirthday(),"yyyy-MM-dd"));
		userInfoModel.setDeptId(userInfo.getDept().getUuid());
		userInfoModel.setDeptName(userInfo.getDept().getDeptName());
	return json;
	}*/
	/**
	 * 删除
	 * 
	 * @param bisKey
	 * @param pkId
	 * @return
	 */
	public TeeJson del(String bisKey, int pkId) {
		TeeJson json = new TeeJson();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的名称
		String tableName = table.getTableName();
		// 获取表格的id
		int tableId = table.getSid();
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);
		// 主键
		String key = "";
		for (BisTableField f : fieldList) {
			if (f.getPrimaryKeyFlag() == 1) {
				key = f.getFieldName();
			}
		}
		String sql = "delete from " + tableName + " where " + key + "=" + pkId;
		// 创建数据库连接对象
		Connection dbConn = null;
		try {
			// 获取数据库连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);
			dbUtils.executeUpdate(sql);
			dbConn.commit();
			json.setRtMsg("删除成功！");
			json.setRtState(true);
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(dbConn);
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}

		return json;
	}

	/**
	 * 导出
	 * 
	 * @param bisKey
	 * @param pageNumber
	 * @param pageSize
	 * @param disPlayNames
	 */
	public void export(String bisKey, String searchModel, int pageNumber,
			int pageSize, HttpServletResponse response,
			HttpServletRequest request) {
		// 获取当前登录的用户
		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 当前登录的部门
		TeeDepartment dept1 = person.getDept();
		// 当前登录的角色
		TeeUserRole role1 = person.getUserRole();

		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的名称
		String tableName = table.getTableName();
		// 获取查询条件
		String condition = bm.getDisplayCondition();
		// 获取排序的字段
		String orders = bm.getOrderField();
		Map<String, String> map = TeeJsonUtil.JsonStr2Map(orders);
		String orderType = map.get("orderType");
		String orderFieldId = map.get("fieldId");
		String order = bisTableFieldDao.get(Integer.parseInt(orderFieldId))
				.getFieldName();
		// 获取查询字段
		List<String> querys = new ArrayList<String>();
		List<String> qTypes = new ArrayList<String>();
		String queryIds = bm.getQueryFields();
		String[] queryId = queryIds.split(",");
		for (int i = 0; i < queryId.length; i++) {
			// 根据字段id 获取字段对象
			BisTableField field = bisTableFieldDao.get(TeeStringUtil.getInteger(queryId[i], 0));
			if(field!=null){
				querys.add(field.getFieldName());
				qTypes.add(field.getFieldType());
			}
			
		}
		// 获取显示的字段
		String displayIds = bm.getHeaderFields();
		String[] displayId = displayIds.split(",");
		// 定义存放显示类型 和 显示模型的集合
		List<BisTableField> displayList = new ArrayList<BisTableField>();
		List<String> disPlayNames = new ArrayList<String>();
		List<String> displayTypes = new ArrayList<String>();
		List<String> displayModels = new ArrayList<String>();
		for (int i = 0; i < displayId.length; i++) {
			// 根据字段id 获取字段对象
			BisTableField field = bisTableFieldDao.get(Integer
					.parseInt(displayId[i]));
			disPlayNames.add(field.getFieldName());
			displayTypes.add(field.getFieldDisplayType());
			displayModels.add(field.getFieldControlModel());
			displayList.add(field);
		}

		// 转换searchModel
		Map searchMap = TeeJsonUtil.JsonStr2Map(searchModel);

		List<Map> data = null;

		// 创建数据库连接对象
		Connection dbConn = null;
		try {
			List<Object> param = new ArrayList<Object>();
			// 获取连接
			dbConn = TeeDbUtility.getConnection();
			dbConn.setAutoCommit(false);// 设置为不自动提交（建议）
			// 获取数据库操作对象
			DbUtils dbUtils = new DbUtils(dbConn);

			if (tableName != "" && tableName != null) {
				String sql = "select *";
				String sql1 = "";
				String sql2 = "";

				if (!TeeUtility.isNullorEmpty(condition)) {

					condition = condition.replace("$[USER_NAME]",
							person.getUserName()).replace("$[USER_ID]",
							person.getUuid() + "");
					if (dept1 != null) {
						condition = condition.replace("$[DEPT_NAME]",
								dept1.getDeptName()).replace("$[DEPT_ID]",
								dept1.getUuid() + "");
					}
					if (role1 != null) {
						condition = condition.replace("$[ROLE_NAME]",
								role1.getRoleName()).replace("$[ROLE_ID]",
								role1.getUuid() + "");
					}

					sql1 = " from " + tableName + " where 1=1  and " + condition;
					// 循环遍历查询条件
					for (int i = 0; i < querys.size(); i++) {
						String type = qTypes.get(i);

						if (("DATE").equals(type)) {
							String beginDate="";
							String endDate="";
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$begin"))){
								beginDate = searchMap.get(
										querys.get(i).toUpperCase() + "$begin").toString();
							}
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$end"))){
								endDate = searchMap.get(
										querys.get(i).toUpperCase() + "$end").toString();
							}
							if (!("").equals(beginDate)
									&& !("").equals(endDate)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd", beginDate);
								Date end = TeeDateUtil.parseDate("yyyy-MM-dd",
										endDate);
								sql1 += "  and " + querys.get(i) + ">=? and  "
										+ querys.get(i) + "<=?";
								param.add(begin);
								param.add(end);
							} else if (!("").equals(beginDate)
									&& ("").equals(endDate)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd", beginDate);
								sql1 += "  and " + querys.get(i) + ">=? ";
								param.add(begin);
							} else if (("").equals(beginDate)
									&& !("").equals(endDate)) {
								Date end = TeeDateUtil.parseDate("yyyy-MM-dd",
										endDate);
								sql1 += "  and " + querys.get(i) + "<=? ";
								param.add(end);
							}

						} else if (("DATETIME").equals(type)) {
							String beginDateTime="";
							String endDateTime="";
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$begin"))){
								beginDateTime = searchMap.get(
										querys.get(i).toUpperCase() + "$begin").toString();
							}
							if(!TeeUtility.isNullorEmpty(searchMap.get(
									querys.get(i).toUpperCase() + "$end"))){
								endDateTime = searchMap.get(
										querys.get(i).toUpperCase() + "$end").toString();
							}
							
							if (!("").equals(beginDateTime)
									&& !("").equals(endDateTime)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", beginDateTime);
								Date end = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", endDateTime);
								sql1 += "  and " + querys.get(i) + ">=? and  "
										+ querys.get(i) + "<=?";
								param.add(begin);
								param.add(end);
							} else if (!("").equals(beginDateTime)
									&& ("").equals(endDateTime)) {
								Date begin = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", beginDateTime);
								sql1 += "  and " + querys.get(i) + ">=? ";
								param.add(begin);
							} else if (("").equals(beginDateTime)
									&& !("").equals(endDateTime)) {
								Date end = TeeDateUtil.parseDate(
										"yyyy-MM-dd HH:mm:ss", endDateTime);
								sql1 += "  and " + querys.get(i) + "<=? ";
								param.add(end);
							}

						} else if(("CHAR").equals(type)||("VARCHAR").equals(type)||("TEXT").equals(type)){//字符类型   字符串类型  文本类型
							String fieldValue = searchMap.get(querys.get(i).toUpperCase())
									.toString();
							if (!("").equals(fieldValue) && fieldValue != null) {
								sql1 += "  and " + querys.get(i) + " like ? ";
								param.add("%"+fieldValue+"%");
							}
						}else {
							String fieldValue = searchMap.get(querys.get(i).toUpperCase())
									.toString();
							if (!("").equals(fieldValue) && fieldValue != null) {
								sql1 += "  and " + querys.get(i) + "=?";
								param.add(fieldValue);
							}
						}
					}
				} else {
					sql1 = " from " + tableName+" where 1=1 ";
					// 循环遍历查询条件
					for (int i = 0; i < querys.size(); i++) {
						String type = qTypes.get(i);
						/*if ((" from " + tableName).equals(sql1)) {
							if (("DATE").equals(type)) {

								String beginDate="";
								String endDate="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDate = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDate = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								
								if (!("").equals(beginDate)
										&& !("").equals(endDate)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  where " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";
									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDate)
										&& ("").equals(endDate)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									sql1 += "  where " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDate)
										&& !("").equals(endDate)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  where " + querys.get(i) + "<=? ";
									param.add(end);
								}

							} else if (("DATETIME").equals(type)) {
								String beginDateTime="";
								String endDateTime="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								
								if (!("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  where " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";
									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDateTime)
										&& ("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									sql1 += "  where " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  where " + querys.get(i) + "<=? ";
									param.add(end);
								}

							} else if(("CHAR").equals(type)||("VARCHAR").equals(type)||("TEXT").equals(type)){//字符类型   字符串类型  文本类型
								String fieldValue = searchMap.get(querys.get(i).toUpperCase())
										.toString();
								if (!("").equals(fieldValue) && fieldValue != null) {
									sql1 += "  and " + querys.get(i) + " like ? ";
									param.add("%"+fieldValue+"%");
								}
							}else {
								if (searchMap.get(querys.get(i).toUpperCase()) != null) {
									String fieldValue = searchMap.get(
											querys.get(i).toUpperCase()).toString();
									if (!("").equals(fieldValue)
											&& fieldValue != null) {
										sql1 += "  where " + querys.get(i)
												+ "=?";
										param.add(fieldValue);
									}

								}

							}
						} else {*/

							if (("DATE").equals(type)) {
								String beginDate="";
								String endDate="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDate = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDate = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								

								if (!("").equals(beginDate)
										&& !("").equals(endDate)) {

									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  and " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";

									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDate)
										&& ("").equals(endDate)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd", beginDate);
									sql1 += "  and " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDate)
										&& !("").equals(endDate)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd", endDate);
									sql1 += "  and " + querys.get(i) + "<=? ";
									param.add(end);
								}

							} else if (("DATETIME").equals(type)) {
								
								String beginDateTime="";
								String endDateTime="";
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$begin"))){
									beginDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$begin").toString();
								}
								if(!TeeUtility.isNullorEmpty(searchMap.get(
										querys.get(i).toUpperCase() + "$end"))){
									endDateTime = searchMap.get(
											querys.get(i).toUpperCase() + "$end").toString();
								}
								
								if (!("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  and " + querys.get(i)
											+ ">=? and  " + querys.get(i)
											+ "<=?";
									param.add(begin);
									param.add(end);
								} else if (!("").equals(beginDateTime)
										&& ("").equals(endDateTime)) {
									Date begin = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss",
											beginDateTime);
									sql1 += "  and " + querys.get(i) + ">=? ";
									param.add(begin);
								} else if (("").equals(beginDateTime)
										&& !("").equals(endDateTime)) {
									Date end = TeeDateUtil.parseDate(
											"yyyy-MM-dd HH:mm:ss", endDateTime);
									sql1 += "  and " + querys.get(i) + "<=? ";
									param.add(end);
								}

							} else if(("CHAR").equals(type)||("VARCHAR").equals(type)||("TEXT").equals(type)){//字符类型   字符串类型  文本类型
								String fieldValue = searchMap.get(querys.get(i).toUpperCase())
										.toString();
								if (!("").equals(fieldValue) && fieldValue != null) {
									sql1 += "  and " + querys.get(i) + " like ? ";
									param.add("%"+fieldValue+"%");
								}
							}else {
								String fieldValue = searchMap
										.get(querys.get(i).toUpperCase()).toString();
								if (!("").equals(fieldValue)
										&& fieldValue != null) {
									sql1 += "  and " + querys.get(i) + "=?";
									param.add(fieldValue);
								}
							}
						/*}*/
					}
				}
				sql1 += "  order by " + order + "  " + orderType + " ";
				sql2 = sql + sql1;

				data = dbUtils.queryToMapList(sql2, param.toArray(), pageSize
						* (pageNumber - 1), pageSize);
				// 复选 单选 下拉 单选或者多选人员 部门 角色 赋值汉字描述
				if (data != null) {
					for (Map map2 : data) {
						for (int i = 0; i < disPlayNames.size(); i++) {
							if (("RADIO").equals(displayTypes.get(i))
									|| ("SELECT").equals(displayTypes.get(i))) {// 单选和下拉
								String controlModel = displayModels.get(i);
								Map<String, String> m = TeeJsonUtil
										.JsonStr2Map(controlModel);
								String name = TeeSysCodeManager
										.getChildSysCodeNameCodeNo(m
												.get("sysno"), TeeStringUtil
												.getString(map2
														.get(disPlayNames
																.get(i).toUpperCase())));
								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc", name);
							} else if (("CHECKBOX").equals(displayTypes.get(i))) {
								String code = map2.get(disPlayNames.get(i).toUpperCase())
										.toString();
								if (("0").equals(code)) {
									map2.put(disPlayNames.get(i).toUpperCase() + "_Desc", "否");
								} else if (("1").equals(code)) {
									map2.put(disPlayNames.get(i).toUpperCase() + "_Desc", "是");
								}
							} else if (("SINGLEPERSON").equals(displayTypes
									.get(i))) {// 单选人员
								String personId = TeeStringUtil.getString(map2
										.get(disPlayNames.get(i).toUpperCase()));
								TeePerson p = personDao.get(TeeStringUtil
										.getInteger(personId, 0));
								if (p != null) {
									String personName = p.getUserName();
									map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
											personName);
								}

							} else if (("MANYPERSON").equals(displayTypes
									.get(i))) {// 多选人员
								String personIds = TeeStringUtil.getString(
										map2.get(disPlayNames.get(i).toUpperCase()), "0");
								List<TeePerson> list = personDao
										.getPersonByUuids(personIds);
								String personNames = "";
								for (int j = 0; j < list.size(); j++) {
									if (j == list.size() - 1) {
										personNames += list.get(j)
												.getUserName();
									} else {
										personNames += list.get(j)
												.getUserName() + ",";
									}
								}
								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
										personNames);
							} else if (("SINGLEDEPT").equals(displayTypes
									.get(i))) {// 单选部门
								TeeDepartment dept = deptDao.get(TeeStringUtil
										.getInteger(
												map2.get(disPlayNames.get(i).toUpperCase()),
												0));
								if (dept != null) {
									String deptName = dept.getDeptName();
									map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
											deptName);
								}

							} else if (("MANYDEPT").equals(displayTypes.get(i))) {// 多选部门
								List<TeeDepartment> list = deptDao
										.getDeptListByUuids(TeeStringUtil
												.getString(map2
														.get(disPlayNames
																.get(i).toUpperCase()), "0"));
								String deptNames = "";
								for (int j = 0; j < list.size(); j++) {
									if (j == list.size() - 1) {
										deptNames += list.get(j).getDeptName();
									} else {
										deptNames += list.get(j).getDeptName()
												+ ",";
									}
								}
								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
										deptNames);
							} else if (("SINGLEROLE").equals(displayTypes
									.get(i))) {// 单选角色
								TeeUserRole role = roleDao.get(TeeStringUtil
										.getInteger(
												map2.get(disPlayNames.get(i).toUpperCase()),
												0));
								if (role != null) {
									String roleName = role.getRoleName();
									map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
											roleName);
								}

							} else if (("MANYROLE").equals(displayTypes.get(i))) {// 多选角色
								String roleIds = TeeStringUtil.getString(map2
										.get(disPlayNames.get(i).toUpperCase()));
								String ids[] = roleIds.split(",");
								String roleNames = "";
								for (int j = 0; j < ids.length; j++) {
									TeeUserRole role = roleDao
											.get(TeeStringUtil.getInteger(
													ids[j], 0));
									if (role != null) {
										String roleName = role.getRoleName();
										if (j == ids.length - 1) {
											roleNames += roleName;
										} else {
											roleNames += roleName + ",";
										}
									}

								}
								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
										roleNames);
							} else if (("CURRENTUSER").equals(displayTypes
									.get(i))) {// 当前用户
								int userId = Integer.parseInt(map2.get(
										disPlayNames.get(i).toUpperCase()).toString());
								// 根据用户id 获取用户对象
								TeePerson p = personDao.get(userId);
								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
										p.getUserName());
							} else if (("CURRENTDEPT").equals(displayTypes
									.get(i))) {// 当前部门
								int deptId = Integer.parseInt(map2.get(
										disPlayNames.get(i).toUpperCase()).toString());
								// 根据部门id 获取部门对象
								TeeDepartment dept = deptDao.get(deptId);
								map2.put(disPlayNames.get(i).toUpperCase()+ "_Desc",
										dept.getDeptName());
							} else if (("CURRENTROLE").equals(displayTypes
									.get(i))) {// 当前角色
								int roleId = Integer.parseInt(map2.get(
										disPlayNames.get(i).toUpperCase()).toString());
								// 根据角色主键 获取角色对象
								TeeUserRole role = roleDao.get(roleId);
								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc",
										role.getRoleName());
							} else if (("CURRENTTIME").equals(displayTypes
									.get(i))) {// 当前时间
								Date date = (Date) map2
										.get(disPlayNames.get(i).toUpperCase());
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								String time = format.format(date);

								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc", time);
							} else if (("CURRENTDATE").equals(displayTypes
									.get(i))) {// 当前日期
								Date date = (Date) map2
										.get(disPlayNames.get(i).toUpperCase());
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd");
								String time = format.format(date);

								map2.put(disPlayNames.get(i).toUpperCase() + "_Desc", time);
							} else if (("DATE").equals(displayTypes.get(i))) {
								Object value = map2.get(disPlayNames.get(i).toUpperCase());
								Map<String, String> map1 = TeeJsonUtil
										.JsonStr2Map(displayModels.get(i));
								String datedisplay = map1.get("datedisplay");
								if (value != null) {
									if (value instanceof Calendar) {
										value = TeeDateUtil.format(
												((Calendar) value).getTime(),
												datedisplay);
									} else if (value instanceof Date) {
										value = TeeDateUtil.format(
												((Date) value), datedisplay);
									} else if (value instanceof java.sql.Date) {
										value = TeeDateUtil.format(
												((java.sql.Date) value),
												datedisplay);
									}
								}
								map2.put(disPlayNames.get(i).toUpperCase(), value);
							}
						}
					}
				}
			}

			HSSFWorkbook wb = new HSSFWorkbook();
			// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
			HSSFSheet sheet = wb.createSheet("列表信息");
			// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow((int) 0);
			// 第四步，创建单元格，并设置值表头 设置表头居中
			HSSFFont font = wb.createFont();
			font.setFontHeightInPoints((short) 12); // 字体高度
			font.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font.setFontName("宋体"); // 字体
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
			font.setItalic(false); // 是否使用斜体
			
			
			HSSFFont font1 = wb.createFont();
			font1.setFontHeightInPoints((short) 12); // 字体高度
			font1.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
			font1.setFontName("宋体"); // 字体
			font1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL); // 宽度
			font1.setItalic(false); // 是否使用斜体
			
			//表头样式
			HSSFCellStyle style = wb.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style.setFont(font);
		
			//内容样式
			HSSFCellStyle style1 = wb.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
			style1.setFont(font1);
			
			HSSFCell cell = row.createCell((short) 0);
			// 设置表头
			for (int i = 0; i < displayList.size(); i++) {
				cell.setCellValue(displayList.get(i).getFieldDesc());
				cell.setCellStyle(style);
				cell = row.createCell((short) (i + 1));
			}

			// 设置内容
			if(data!=null&&data.size()>0){
				for (int i = 0; i < data.size(); i++) {
					HSSFRow r = sheet.createRow((int) (i + 1));
					for (int j = 0; j < displayList.size(); j++) {
						if (("RADIO").equals(displayList.get(j)
								.getFieldDisplayType())
								|| ("SELECT").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("CHECKBOX").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("SINGLEPERSON").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("MANYPERSON").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("SINGLEROLE").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("MANYROLE").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("SINGLEDEPT").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("MANYDEPT").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("CURRENTUSER").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("CURRENTDEPT").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("CURRENTROLE").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("CURRENTDATE").equals(displayList.get(j)
										.getFieldDisplayType())
								|| ("CURRENTTIME").equals(displayList.get(j)
										.getFieldDisplayType())) {
							cell = r.createCell((short) (j));
							cell.setCellValue(TeeStringUtil.getString(data.get(i)
									.get(displayList.get(j).getFieldName().toUpperCase()
											+ "_Desc")));
							cell.setCellStyle(style1);
						} else {
							cell = r.createCell((short) (j));
							cell.setCellValue(TeeStringUtil.getString(data.get(i)
									.get(displayList.get(j).getFieldName().toUpperCase())));
							cell.setCellStyle(style1);
						}

					}
				}

			}
			
			// 设置每一列的宽度
			sheet.setDefaultColumnWidth(10);
			String fileName = "列表信息.xls";
			response.setHeader("Content-disposition", "attachment;filename="
					+ URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/msexcel;charset=UTF-8");
			OutputStream out = response.getOutputStream();
			wb.write(out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			TeeDbUtility.rollback(dbConn);
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}

	}

	/**
	 * 根据bisKey获取表格的字段名称 和 是否必填 的map集合
	 * 
	 * @param bisKey
	 * @return
	 */
	public TeeJson getFieldRequiredMap(String bisKey) {
		TeeJson json = new TeeJson();
		Map<String, BisTableField> map = new HashMap<String, BisTableField>();
		// 根据主键获取业务模型对象
		BusinessModel bm = businessModelDao.get(bisKey);
		// 获取表单对象
		BisForm form = bm.getBisForm();
		// 获取表格
		BisTable table = form.getBisTable();
		// 获取表格的id
		int tableId = table.getSid();
		String hql = "from BisTableField f where f.bisTable.sid=" + tableId;
		// 表格字段集合
		List<BisTableField> fieldList = bisTableFieldDao
				.executeQuery(hql, null);

		for (BisTableField f : fieldList) {
			map.put(f.getFieldName(), f);
		}

		json.setRtData(map);
		return json;
	}

	/**
	 * 根据附件id 获取附件集合
	 * 
	 * @param attIds
	 * @return
	 */
	public TeeJson getAttachList(String attIds) {
		TeeJson json=new TeeJson();
		List<TeeAttachment>attaches = attachmentDao
				.getAttachmentsByIds(String
						.valueOf(attIds));
		List<TeeAttachmentModel> modelList = new ArrayList();
		TeeAttachmentModel model = null;
		for (TeeAttachment atta : attaches) {
			model = new TeeAttachmentModel();
			model.setSid(atta.getSid());
			model.setFileName(atta.getFileName());
			model.setExt(atta.getExt());
			modelList.add(model);
		}
		json.setRtData(modelList);
		json.setRtState(true);
		return json;
	}
}
