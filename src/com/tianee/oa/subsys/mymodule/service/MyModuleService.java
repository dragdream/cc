package com.tianee.oa.subsys.mymodule.service;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisModule;
import com.tianee.oa.subsys.bisengin.bean.BisTable;
import com.tianee.oa.subsys.bisengin.bean.BisTableField;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.bisengin.service.BisModuleService;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.subsys.bisengin.util.BisEngineUtil;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.expReplace.TeeExpFetcher;
import com.tianee.webframe.util.str.expReplace.TeeRegexpAnalyser;

@Service
public class MyModuleService extends TeeBaseService{
	@Autowired
	private BisViewService bisViewService;
	
	@Autowired
	private BisModuleService bisModuleService;
	
	@Autowired
	private BisEngineUtil bisEngineUtil;
	
	/**
	 * 视图查询
	 * @param requestData
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson dflist(Map requestData,TeeDataGridModel dm){
		TeeDataGridModel dataGridModel = new TeeDataGridModel();
		String moduleId = TeeStringUtil.getString(requestData.get("moduleId"));
		BisModule bisModule = (BisModule) simpleDaoSupport.get(BisModule.class, moduleId);
		BisView bisView = bisModule.getBisView();
		if(bisView!=null){
		requestData.put("dfid", bisView.getIdentity());
		}
		return bisViewService.dflist(dm, requestData);
	}
	
	/**
	 * 保存或更新模块数据
	 * @param requestData 请求数据
	 * @param loginUser 当前登陆人
	 * @param moduleId 模块ID
	 */
	@Transactional(readOnly=true)
	public void saveOrUpdate(Map requestData,TeePerson loginUser,String moduleId){
		String bisKey = (String)requestData.get("bisKey");
		requestData.remove("bisKey");
		requestData.remove("moduleId");
		BisModule bisModule = (BisModule) simpleDaoSupport.get(BisModule.class, moduleId);
		TeeForm form = bisModule.getForm();
		BisTable bisTable = bisModule.getBisTable();
		if(form==null || bisTable==null){
			return ;
		}
		
		Connection dbConn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		if(bisDataSource.getDataSource()==1){//内部
			dbConn = TeeDbUtility.getConnection(null);
		}else{//外部
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setDriverClass(bisDataSource.getDriverClass());
			dataSource.setPassWord(bisDataSource.getDriverPwd());
			dataSource.setUrl(bisDataSource.getDriverUrl());
			dataSource.setUserName(bisDataSource.getDriverUsername());
			dataSource.setConfigModel(bisDataSource.getConfigModel());
			dbConn = TeeDbUtility.getConnection(dataSource);
		}
		
		/**
		 * 根据条件过滤数据类型
		 */
		
		
		
		try{
			if(bisKey==null || "".equals(bisKey)){
				bisEngineUtil.doInsert(dbConn, requestData, bisTable);
			}else{
				requestData.put("bisKey", bisKey);
				bisEngineUtil.doUpdate(dbConn, requestData, bisTable);
			}
			dbConn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
	}
	
	/**
	 * 删除模块数据
	 * @param loginUser 当前操作人
	 * @param moduleId 模块ID
	 * @param bisKey 业务主键
	 */
	public void delete(TeePerson loginUser,String moduleId,String bisKey){
		BisModule bisModule = (BisModule) simpleDaoSupport.get(BisModule.class, moduleId);
		TeeForm form = bisModule.getForm();
		BisTable bisTable = bisModule.getBisTable();
		if(form==null || bisTable==null){
			return ;
		}
		
		Connection dbConn = null;
		BisDataSource bisDataSource = bisTable.getBisDataSource();
		if(bisDataSource.getDataSource()==1){//内部
			dbConn = TeeDbUtility.getConnection(null);
		}else{//外部
			TeeDataSource dataSource = new TeeDataSource();
			dataSource.setDriverClass(bisDataSource.getDriverClass());
			dataSource.setPassWord(bisDataSource.getDriverPwd());
			dataSource.setUrl(bisDataSource.getDriverUrl());
			dataSource.setUserName(bisDataSource.getDriverUsername());
			dataSource.setConfigModel(bisDataSource.getConfigModel());
			dbConn = TeeDbUtility.getConnection(dataSource);
		}
		
		BisTableField primaryKey = (BisTableField) simpleDaoSupport.unique("from BisTableField btf where btf.bisTable.sid="+bisTable.getSid()+" and btf.primaryKeyFlag=1", null);
		
		
		try{
			String sql = "delete from "+bisTable.getTableName()+" where "+primaryKey.getFieldName();
			if(primaryKey.getFieldType().equals("NUMBER")){//数字
				sql+=" = "+bisKey;
			}else{//字符串
				sql+=" = '"+bisKey+"'";
			}
			bisEngineUtil.executeUpdate(dbConn, sql, null);
			
			dbConn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(dbConn);
		}
	}
	
	/**
	 * 获取表单HTML渲染内容
	 * @param loginUser
	 * @param moduleId
	 * @param bisKey
	 * @return
	 */
	@Transactional(readOnly=true)
	public String getFormHtml(TeePerson loginUser,String moduleId,String bisKey,final int view){
		BisModule bisModule = (BisModule) simpleDaoSupport.get(BisModule.class, moduleId);
		TeeForm form = bisModule.getForm();
		BisTable bisTable = bisModule.getBisTable();
		if(form==null || bisTable==null){
			return null;
		}
		
		//业务表字段
		final List<BisTableField> fields = simpleDaoSupport.find("from BisTableField where bisTable.sid=?", new Object[]{bisTable.getSid()});
		BisTableField primaryField = null;
		for(BisTableField bisTableField:fields){
			if(bisTableField.getPrimaryKeyFlag()==1){
				primaryField = bisTableField;
				break;
			}
		}
		
		
		
		final Map data = new HashMap();
		
		if(!"".equals(bisKey) && bisKey!=null){
			//声明连接
			Connection conn = bisEngineUtil.getConnection(bisTable);
			DbUtils dbUtils = new DbUtils(conn);
			try{
				String sql = "select * from "+bisTable.getTableName()+" where "+primaryField.getFieldName();
				if(primaryField.getFieldType().equals("NUMBER")){//数字型
					sql+=" = "+bisKey;
				}else{//字符型
					sql+=" like '"+bisKey+"'";
				}
				
				data.putAll(dbUtils.queryToMap(sql));
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				TeeDbUtility.closeConn(conn);
			}
		}
		
		
		//解析映射关系
		final List<Map<String, String>> mappings = TeeJsonUtil.JsonStr2MapList(bisModule.getMapping());
		
		TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(form.getPrintModelShort());
		//先渲染表单
		String formHtml = analyser.replace(new String[]{"\\{DATA_[0-9]+\\}"}, new TeeExpFetcher(){
			@Override
			public String parse(String pattern) {
				pattern = pattern.replaceAll("\\{[A-Z a-z]+_", "").replace("}", "");
				//判断是否存在映射关系中字段
				boolean hasExist = false;
				StringBuffer ctrl = new StringBuffer();//声明控件字符串
				Map<String,String> mapping = null;
				for(Map<String,String> mapItem:mappings){
					if(pattern.equals(mapItem.get("FII"))){
						mapping = mapItem;
						hasExist = true;
						break;
					}
				}
				//如果该表单不存在映射规则中，则屏蔽掉
				if(!hasExist){
					return "";
				}
				String fieldName = mapping.get("TFI");
				String itemData = TeeStringUtil.getString(data.get(fieldName));
				String model = TeeStringUtil.getString(mapping.get("MODEL"));
				
				String TYPE = mapping.get("TYPE");//字段类型
				if("文本输入".equals(TYPE)){
					if(view==0){
						ctrl.append("<input type='text' class='BigInput' id='"+fieldName+"' name='"+fieldName+"' ");
						if(data.size()!=0){
							ctrl.append(" value='"+itemData+"'");
						}
						ctrl.append("/>");
					}else{
						ctrl.append(itemData);
					}
				}else if("整型输入".equals(TYPE)){
					if(view==0){
						ctrl.append("<input type='text' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
						ctrl.append(" onblur=\"blurFormatNumber(this,'#')\" onfocus='focusFormatNumber(this)'");
						if(data.size()!=0){
							ctrl.append(" value='"+itemData+"'");
						}
						ctrl.append("/>");
					}else{
						ctrl.append(itemData);
					}
				}else if("下拉选择".equals(TYPE)){
					List<Map<String, Object>> list = TeeSysCodeManager.getChildSysCodeListByParentCodeNo(model);
					if(view==0){
						ctrl.append("<select class='BigSelect' id='"+fieldName+"' name='"+fieldName+"'>");
						for(Map<String, Object> gg:list){
							ctrl.append("<option "+(data.size()!=0?(itemData.equals(gg.get("codeNo"))?"selected":""):"")+" value='"+gg.get("codeNo")+"'>"+gg.get("codeName")+"</option>");
						}
						ctrl.append("</select>");
					}else{
						for(Map<String, Object> gg:list){
							if(itemData.equals(gg.get("codeNo"))){
								ctrl.append(gg.get("codeName"));
								break;
							}
						}
					}
					
				}else if("双精度浮点".equals(TYPE)){
					if(view==0){
						ctrl.append("<input type='text' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
						ctrl.append(" onblur=\"blurFormatNumber(this,'#.##')\" onfocus='focusFormatNumber(this)'");
						if(data.size()!=0){
							ctrl.append(" value='"+itemData+"'");
						}
						ctrl.append("/>");
					}else{
						ctrl.append(itemData);
					}
				}else if("单选人员".equals(TYPE)){
					if(view==0){
						ctrl.append("<input type='hidden' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
						TeePerson person = null;
						if(data.size()!=0){
							person = (TeePerson) simpleDaoSupport.get(TeePerson.class, TeeStringUtil.getInteger(itemData, 0));
						}
						
						if(data.size()!=0){
							ctrl.append(" value='"+itemData+"'");
						}
						ctrl.append(" />");
						
						ctrl.append("<input type='text' readonly  class='BigInput' id='"+fieldName+"_S' ");
						if(data.size()!=0){
							ctrl.append(" value='"+person.getUserName()+"'");
						}
						ctrl.append(" />");
						ctrl.append("&nbsp;<a href='javascript:void(0)' onclick=\"selectSingleUser(['"+fieldName+"','"+fieldName+"_S'])\">选择</a>");
						ctrl.append("&nbsp;&nbsp;");
						ctrl.append("<a href='javascript:void(0)' onclick=\"clearData('"+fieldName+"','"+fieldName+"_S')\">清空</a>");
					}else{
						TeePerson person = (TeePerson) simpleDaoSupport.get(TeePerson.class, TeeStringUtil.getInteger(itemData, 0));
						ctrl.append(person.getUserName());
					}
				}else if("多选人员".equals(TYPE)){
					ctrl.append("<input type='hidden' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
					if(data.size()!=0){
						ctrl.append(" value='"+itemData+"'");
					}
					ctrl.append(" />");
					
					ctrl.append("<input type='text' readonly  class='BigInput' id='"+fieldName+"_S' ");
					if(data.size()!=0){
						
					}
					ctrl.append(" />");
					ctrl.append("&nbsp;<a href='javascript:void(0)' onclick=\"selectUser(['"+fieldName+"','"+fieldName+"_S'])\">选择</a>");
					ctrl.append("&nbsp;&nbsp;");
					ctrl.append("<a href='javascript:void(0)' onclick=\"clearData('"+fieldName+"','"+fieldName+"_S')\">清空</a>");
				}else if("单选角色".equals(TYPE)){
					ctrl.append("<input type='hidden' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
					TeeUserRole role = null;
					if(data.size()!=0){
						ctrl.append(" value='"+itemData+"'");
					}
					ctrl.append(" />");
					
					ctrl.append("<input type='text' readonly  class='BigInput' id='"+fieldName+"_S' ");
					if(data.size()!=0){
						
					}
					ctrl.append(" />");
					ctrl.append("&nbsp;<a href='javascript:void(0)' onclick=\"selectSingleRole(['"+fieldName+"','"+fieldName+"_S'])\">选择</a>");
					ctrl.append("&nbsp;&nbsp;");
					ctrl.append("<a href='javascript:void(0)' onclick=\"clearData('"+fieldName+"','"+fieldName+"_S')\">清空</a>");
				}else if("多选角色".equals(TYPE)){
					ctrl.append("<input type='hidden' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
					if(data.size()!=0){
						ctrl.append(" value='"+itemData+"'");
					}
					ctrl.append(" />");
					
					ctrl.append("<input type='text' readonly  class='BigInput' id='"+fieldName+"_S' ");
					if(data.size()!=0){
						
					}
					ctrl.append(" />");
					ctrl.append("&nbsp;<a href='javascript:void(0)' onclick=\"selectRole(['"+fieldName+"','"+fieldName+"_S'])\">选择</a>");
					ctrl.append("&nbsp;&nbsp;");
					ctrl.append("<a href='javascript:void(0)' onclick=\"clearData('"+fieldName+"','"+fieldName+"_S')\">清空</a>");
				}else if("单选部门".equals(TYPE)){
					ctrl.append("<input type='hidden' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
					TeeDepartment department = null;
					if(data.size()!=0){
						ctrl.append(" value='"+itemData+"'");
					}
					ctrl.append(" />");
					
					ctrl.append("<input type='text' readonly  class='BigInput' id='"+fieldName+"_S' ");
					if(data.size()!=0){
						
					}
					ctrl.append(" />");
					ctrl.append("&nbsp;<a href='javascript:void(0)' onclick=\"selectSingleDept(['"+fieldName+"','"+fieldName+"_S'])\">选择</a>");
					ctrl.append("&nbsp;&nbsp;");
					ctrl.append("<a href='javascript:void(0)' onclick=\"clearData('"+fieldName+"','"+fieldName+"_S')\">清空</a>");
				}else if("多选部门".equals(TYPE)){
					ctrl.append("<input type='hidden' class='BigInput' id='"+fieldName+"' name='"+fieldName+"'");
					if(data.size()!=0){
						ctrl.append(" value='"+itemData+"'");
					}
					ctrl.append(" />");
					
					ctrl.append("<input type='text' readonly class='BigInput' id='"+fieldName+"_S' ");
					if(data.size()!=0){
						
					}
					ctrl.append(" />");
					ctrl.append("&nbsp;<a href='javascript:void(0)' onclick=\"selectDept(['"+fieldName+"','"+fieldName+"_S'])\">选择</a>");
					ctrl.append("&nbsp;&nbsp;");
					ctrl.append("<a href='javascript:void(0)' onclick=\"clearData('"+fieldName+"','"+fieldName+"_S')\">清空</a>");
				}else if("日期选择".equals(TYPE)){
					if(view==0){
						ctrl.append("<input type='text' class='BigInput Wdate' readonly id='"+fieldName+"' name='"+fieldName+"' ");
						if(data.size()!=0){
							Date date = TeeDateUtil.parseDateByPattern(itemData);
							ctrl.append(" value='"+TeeDateUtil.format(date, "yyyy-MM-dd")+"' ");
						}
						ctrl.append(" onFocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd' })\" />");
					}else{
						Date date = TeeDateUtil.parseDateByPattern(itemData);
						ctrl.append(TeeDateUtil.format(date, "yyyy-MM-dd"));
					}
				}else if("日期时间选择".equals(TYPE)){
					if(view==0){
						ctrl.append("<input type='text' class='BigInput Wdate' readonly id='"+fieldName+"' name='"+fieldName+"' ");
						if(data.size()!=0){
							Date date = TeeDateUtil.parseDateByPattern(itemData);
							ctrl.append(" value='"+TeeDateUtil.format(date, "yyyy-MM-dd hh:mm:ss")+"' ");
						}
						ctrl.append(" onFocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss' })\" />");
					}else{
						Date date = TeeDateUtil.parseDateByPattern(itemData);
						ctrl.append(" value='"+TeeDateUtil.format(date, "yyyy-MM-dd hh:mm:ss")+"' ");
					}
				}
				
				return ctrl.toString();
			}
		});
		
		return formHtml;
	}
}
