package com.tianee.oa.subsys.ereport.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.core.org.service.TeeUserRoleService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.bisengin.bean.BisViewListItem;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.subsys.ereport.bean.TeeEreport;
import com.tianee.oa.subsys.ereport.model.TeeEreportModel;
import com.tianee.oa.subsys.report.bean.TeeSeniorReportCat;
import com.tianee.oa.subsys.report.bean.TeeSeniorReportTemplate;
import com.tianee.oa.subsys.report.model.TeeSeniorReportTemplateModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
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

@Service
public class TeeEreportService extends TeeBaseService{

	
	@Autowired
	private  TeePersonService personService;
	
	@Autowired
	private  TeeDeptService deptService;
	
	
	@Autowired
	private  TeeUserRoleService roleService;
	
	@Autowired
	private BisViewService bisViewService;
	
	/**
	 * 获取我创建的报表 （系统管理员  可以查看全部报表）
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getEreportList(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	
		String  counthql="";
		String  hql="";
		List list=new ArrayList();
		//判断是不是系统管理员
		if(personService.checkIsAdminPriv(loginUser)){//当前登陆人是系统管理员
			counthql=" select count(r) from TeeEreport r";
			hql=" from TeeEreport  ";
		}else{
			counthql=" select count(r) from TeeEreport r where r.crUser.uuid=? ";
			hql=" from TeeEreport where crUser.uuid=?  ";
			list.add(loginUser.getUuid());
		}
		
		long total=simpleDaoSupport.count(counthql, list.toArray());
		json.setTotal(total);
		List<TeeEreport> resultList=simpleDaoSupport.pageFind(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), list.toArray());
		List<TeeEreportModel> modelList=new ArrayList<TeeEreportModel>();
		TeeEreportModel model=null;
		if(resultList!=null&&resultList.size()>0){
			for (TeeEreport teeEreport : resultList) {
				model=parseToModel(teeEreport);
				modelList.add(model);
			}
		}
		
		json.setRows(modelList);
		return json;
	}

	
	
	/**
	 * 实体类转换成model
	 * @param teeEreport
	 * @return
	 */
	private TeeEreportModel parseToModel(TeeEreport teeEreport) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeEreportModel model=new TeeEreportModel();
		BeanUtils.copyProperties(teeEreport, model);
		//报表分类
		if(teeEreport.getCat()!=null){
			model.setCatId(teeEreport.getCat().getSid());
			model.setCatName(teeEreport.getCat().getName());
			model.setCatColor(teeEreport.getCat().getColor());
		}
		//报表创建人
		if(teeEreport.getCrUser()!=null){
			model.setCrUserName(teeEreport.getCrUser().getUserName());
			model.setCrUserId(teeEreport.getCrUser().getUuid());
		}
		//所属视图
		if(teeEreport.getBisView()!=null){
			model.setBisViewIdentity(teeEreport.getBisView().getIdentity());
		}
		
		//创建时间
		if(teeEreport.getCrTime()!=null){
			model.setCrTimeStr(sdf.format(teeEreport.getCrTime().getTime()));
		}
		
		
		if(teeEreport.getChartType()==1){
			model.setChartTypeDesc("柱状图");
		}else if(teeEreport.getChartType()==2){
			model.setChartTypeDesc("条形图");
		}else if(teeEreport.getChartType()==3){
			model.setChartTypeDesc("折线图");
		}else if(teeEreport.getChartType()==4){
			model.setChartTypeDesc("饼状图");
		}else if(teeEreport.getChartType()==5){
			model.setChartTypeDesc("双轴图");
		}
		
		//人员权限    部门权限  角色权限
		String userIds="";
		String userNames="";
		String deptIds="";
		String deptNames="";
		String roleIds="";
		String roleNames="";
		if(teeEreport.getUsers()!=null&&teeEreport.getUsers().size()>0){
			for (TeePerson person : teeEreport.getUsers()) {
				userIds+=person.getUuid()+",";
				userNames+=person.getUserName()+",";
			}
		}
		
		if(teeEreport.getDepts()!=null&&teeEreport.getDepts().size()>0){
			for (TeeDepartment dept : teeEreport.getDepts()) {
				deptIds+=dept.getUuid()+",";
				deptNames+=dept.getDeptName()+",";
			}
		}
		
		if(teeEreport.getDepts()!=null&&teeEreport.getDepts().size()>0){
			for (TeeDepartment dept : teeEreport.getDepts()) {
				deptIds+=dept.getUuid()+",";
				deptNames+=dept.getDeptName()+",";
			}
		}
		if(teeEreport.getRoles()!=null&&teeEreport.getRoles().size()>0){
			for (TeeUserRole role : teeEreport.getRoles()) {
				roleIds+=role.getUuid()+",";
				roleNames+=role.getRoleName()+",";
			}
		}
		
		if(userIds.endsWith(",")){
			userIds=userIds.substring(0, userIds.length()-1);
		}
		if(userNames.endsWith(",")){
			userNames=userNames.substring(0, userNames.length()-1);
		}
		if(deptIds.endsWith(",")){
			deptIds=deptIds.substring(0, deptIds.length()-1);
		}
		if(deptNames.endsWith(",")){
			deptNames=deptNames.substring(0, deptNames.length()-1);
		}
		if(roleIds.endsWith(",")){
			roleIds=roleIds.substring(0, roleIds.length()-1);
		}
		if(roleNames.endsWith(",")){
			roleNames=roleNames.substring(0, roleNames.length()-1);
		}
		
		model.setUserIds(userIds);
		model.setUserNames(userNames);
		model.setDeptIds(deptIds);
		model.setDeptNames(deptNames);
		model.setRoleIds(roleIds);
		model.setRoleNames(roleNames);
		return model;
	}



	
	/**
	 * 创建报表
	 * @param request
	 * @return
	 */
	public TeeJson create(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取当前登陆人
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的标题
		String title=TeeStringUtil.getString(request.getParameter("title"));
		TeeEreport report=new TeeEreport();
		report.setCrTime(Calendar.getInstance());
		report.setCrUser(loginUser);
		report.setTitle(title);
		simpleDaoSupport.save(report);
		json.setRtData(report.getSid());
		json.setRtState(true);
		json.setRtMsg("创建成功！");
		return json;
	}



	/**
	 * 删除报表
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeEreport report=(TeeEreport) simpleDaoSupport.get(TeeEreport.class,sid);
		if(report!=null){
			
			//删除中间表中的数据
			simpleDaoSupport.executeNativeUpdate(" delete from e_report_dept where e_report_id=? ", new Object[]{sid});
			simpleDaoSupport.executeNativeUpdate(" delete from e_report_user where e_report_id=? ", new Object[]{sid});
			simpleDaoSupport.executeNativeUpdate(" delete from e_report_role where e_report_id=? ", new Object[]{sid});
			
			//删除报表
			simpleDaoSupport.deleteByObj(report);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("报表不存在！");
		}
		return json;
	}



	/**
	 * 权限设置
	 * @param request
	 * @return
	 */
	public TeeJson privSetting(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取页面上传来的sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		String userIds=TeeStringUtil.getString(request.getParameter("userIds"));
		String deptIds=TeeStringUtil.getString(request.getParameter("deptIds"));
		String roleIds=TeeStringUtil.getString(request.getParameter("roleIds"));
		
		//获取对应的TeeEreport
		TeeEreport report=(TeeEreport) simpleDaoSupport.get(TeeEreport.class,sid);
		if(report!=null){
			//清空原先的中间表数据
			report.getUsers().clear();
			report.getRoles().clear();
			report.getDepts().clear();
			
			Set<TeePerson> personSet=null;
			Set<TeeUserRole> roleSet=null;
			Set<TeeDepartment> deptSet=null;
			
			if(!TeeUtility.isNullorEmpty(userIds)){
                List<TeePerson> personList=personService.getPersonByUuids(userIds);
                personSet=new HashSet<TeePerson>(personList);
			}
			
			if(!TeeUtility.isNullorEmpty(deptIds)){
                List<TeeDepartment> deptList=deptService.getDeptByUuids(deptIds);
                deptSet=new HashSet<TeeDepartment>(deptList);
			}
			
			if(!TeeUtility.isNullorEmpty(roleIds)){
                List<TeeUserRole> roleList=roleService.getUserRoleByUuids(roleIds);
                roleSet=new HashSet<TeeUserRole>(roleList);
			}
			
			report.setUsers(personSet);
			report.setDepts(deptSet);
			report.setRoles(roleSet);
			
			simpleDaoSupport.update(report);
			
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}



	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getInfoBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取sid
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeEreport report=(TeeEreport) simpleDaoSupport.get(TeeEreport.class,sid);
		if(report!=null){
			TeeEreportModel model=parseToModel(report);
			json.setRtData(model);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	
	/**
	 * 修改操作
	 * @param request
	 * @return
	 */
	public TeeJson update(HttpServletRequest request,TeeEreportModel model) {
		TeeJson json=new TeeJson();
		int sid=model.getSid();
		TeeEreport report=(TeeEreport) simpleDaoSupport.get(TeeEreport.class,sid);
		if(report!=null){
			BisView bisView=(BisView) simpleDaoSupport.get(BisView.class,model.getBisViewIdentity());
			TeeSeniorReportCat cat=(TeeSeniorReportCat) simpleDaoSupport.get(TeeSeniorReportCat.class,model.getCatId());
					
			report.setBisView(bisView);
			report.setCat(cat);
			
			report.setChartType(model.getChartType());
			report.setDimension(model.getDimension());
			report.setOrderStr(model.getOrderStr());
			report.setShaft(model.getShaft());
			report.setSpindle(model.getSpindle());
			report.setTitle(model.getTitle());
			report.setConditionExp(model.getConditionExp());
			report.setConditionItems(model.getConditionItems());
			
			
			simpleDaoSupport.update(report);
			json.setRtState(true);
		}else{
			json.setRtState(false);
			json.setRtMsg("该报表不存在！");
		}
		return json;
	}



	
	/**
	 * 渲染图表
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson renderGraphics(HttpServletRequest request,
			TeeEreportModel model) {
		//获取方言
		final String dialect=TeeSysProps.getDialect();
		Map data=new HashMap();
		TeeJson json=new TeeJson();
		//获取标题
	    String title=TeeStringUtil.getString(model.getTitle());
	    //获取图表类型
	    int chartType=TeeStringUtil.getInteger(model.getChartType(), 0);
	    //获取前台传来的所属视图
	    String bisViewIdentity=TeeStringUtil.getString(model.getBisViewIdentity());
	    //获取前台页面传来的维度
	    String  dimension=TeeStringUtil.getString(model.getDimension());
	    //获取前台页面传来的主轴数值   {"0":{"title":"借方金额","statistics":"总计","fieldValue":"借方金额1"}}
	    String spindle=TeeStringUtil.getString(model.getSpindle());
	    //获取前台页面传来的次轴数值    {"0":{"title":"借方金额","statistics":"总计","fieldValue":"借方金额1"}}
	    String shaft=TeeStringUtil.getString(model.getShaft());
	    //获取前台页面传来的排序  {"orderField":"SUM(余额)","orderDesc":"desc"}
	    String orderStr=TeeStringUtil.getString(model.getOrderStr());
	    
	    
	    //获取前台页面传来的条件表达式
	    String conditionExp=TeeStringUtil.getString(model.getConditionExp());
	    //获取前台页面传来的条件项目
	    String contionItems=TeeStringUtil.getString(model.getConditionItems());
	    final List<Map<String, String>> conditionItemsList=TeeJsonUtil.JsonStr2MapList(contionItems);
	    
	    
	    
	    
	    //判断视图  和  维度
	    if(!TeeUtility.isNullorEmpty(bisViewIdentity)&&!TeeUtility.isNullorEmpty(dimension)&&!TeeUtility.isNullorEmpty(spindle)){
	    	String chartTypeDesc="";
		    if(chartType==1){//柱状图
		    	chartTypeDesc="column";
		    }else if(chartType==2){//条形图
		    	chartTypeDesc="bar";
		    }else if(chartType==3){//折线图
		    	chartTypeDesc="line";
		    }else if(chartType==4){//饼状图
		    	chartTypeDesc="pie";
		    }else if(chartType==5){//双轴图
		    	chartTypeDesc="xy";
		    }
		    
		    //拼接sql语句
		    BisView bisView=(BisView) simpleDaoSupport.get(BisView.class,model.getBisViewIdentity());
		    String bisViewSql=bisViewService.getSqlByView(bisView.getIdentity());
		    String sql="select ";
		    
		    //分析维度
		    String dimensionAlias="";//维度别名
		    if(dimension.startsWith("{Y}")){//日期(年)
		    	String d=dimension.substring(3, dimension.length());
		    	dimensionAlias=d+"(年)";
		    	dimension=TeeDbUtility.DATE2CHAR_Y(dialect, d);
		    }else if(dimension.startsWith("{M}")){//日期(月)
		    	String d=dimension.substring(3, dimension.length());
		    	dimensionAlias=d+"(月)";
		    	dimension=TeeDbUtility.DATE2CHAR_Y_M(dialect, d);
		    }else if(dimension.startsWith("{D}")){//日期(日)
		    	String d=dimension.substring(3, dimension.length());
		    	dimensionAlias=d+"(日)";
		    	dimension=TeeDbUtility.DATE2CHAR_Y_M_D(dialect, d);
		    }else{//其他
		    	dimensionAlias=dimension;
		    }
		    	
		    
		    //分析主轴数值
		    List<Map<String, String>> spindleMapList=TeeJsonUtil.JsonStr2MapList(spindle);
		    String statistics="";
		    for (Map m:spindleMapList) {
		    	if(m.get("statistics").equals("总计")){
		    		statistics="SUM";
		    	}else if(m.get("statistics").equals("平均值")){
		    		statistics="AVG";
		    	}else if(m.get("statistics").equals("计数")){
		    		statistics="COUNT";
		    	}else if(m.get("statistics").equals("最大值")){
		    		statistics="MAX";
		    	}else if(m.get("statistics").equals("最小值")){
		    		statistics="MIN";
		    	}
				sql+="  "+statistics+"("+m.get("fieldValue")+") as \""+m.get("title")+"("+m.get("statistics")+")\",";
			} 
		    
		    
		    List<Map<String, String>> shaftMapList=TeeJsonUtil.JsonStr2MapList(shaft);
		    if(chartType==5){//双轴图   加次轴数值  
				    String statistics1="";
				    for (Map m:shaftMapList) {
				    	if(m.get("statistics").equals("总计")){
				    		statistics1="SUM";
				    	}else if(m.get("statistics").equals("平均值")){
				    		statistics1="AVG";
				    	}else if(m.get("statistics").equals("计数")){
				    		statistics1="COUNT";
				    	}else if(m.get("statistics").equals("最大值")){
				    		statistics1="MAX";
				    	}else if(m.get("statistics").equals("最小值")){
				    		statistics1="MIN";
				    	}
						sql+="  "+statistics1+"("+m.get("fieldValue")+") as \""+m.get("title")+"("+m.get("statistics")+")\",";
					} 
		    	
		    }
		    
		    
		 
		    sql+=dimension+" as \""+dimensionAlias+"\" from ("+bisViewSql+") tmp ";
		    
		    
		    //替换条件表达式
		    //先创建一个正则分析器
		    TeeRegexpAnalyser analyser = new TeeRegexpAnalyser(conditionExp);
		    //通过正则，提取出指定的条件项，然后替换成相应的条件表达式
		    String newConditionExp = analyser.replace(new String[]{"\\[[0-9]+\\]"}, new TeeExpFetcher() {
				
				@Override
				public String parse(String pattern) {
					// TODO Auto-generated method stub
					int index = Integer.parseInt(pattern.replace("[", "").replace("]", ""));
				    if(conditionItemsList!=null&&conditionItemsList.size()>0){
				    	if(index <= conditionItemsList.size()){
				    		//获取当前时间
				    		Calendar newTime=Calendar.getInstance();
				    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				    		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM");
				    		
					    	String field=conditionItemsList.get(index-1).get("field");
					    	String oper=conditionItemsList.get(index-1).get("oper");
					    	String type=conditionItemsList.get(index-1).get("type");
					    	String v1=conditionItemsList.get(index-1).get("v1");
					    	String v2=conditionItemsList.get(index-1).get("v2");
				    		
					    	String sqlStr="";
					    	if(("TEXT").equals(type)){//文本类型
					    		if(("等于").equals(oper)){
					    			sqlStr=" "+field+" like '"+ v1+"' ";
					    		}else if(("不等于").equals(oper)){
					    			sqlStr=" "+field+" not  like '"+ v1+"' ";
					    		}else if(("开头包含").equals(oper)){
					    			sqlStr=" "+field+" like '"+ v1+"%' ";
					    		}else if(("结尾包含").equals(oper)){
					    			sqlStr=" "+field+" like '%"+ v1+"' ";
					    		}else if(("包含").equals(oper)){
					    			sqlStr=" "+field+" like '%"+ v1+"%' ";
					    		}else if(("不包含").equals(oper)){
					    			sqlStr=" "+field+" not like '%"+ v1+"%' ";
					    		}	
					    		
					    	}else if(("NUMBER").equals(type)){//数字类型
					    		if(("等于").equals(oper)){
					    			sqlStr=" "+field+" = "+v1+" ";
					    		}else if(("不等于").equals(oper)){
					    			sqlStr=" "+field+" != "+v1+" ";
					    		}else if(("大于").equals(oper)){
					    			sqlStr=" "+field+" > "+v1+" ";
					    		}else if(("大于等于").equals(oper)){
					    			sqlStr=" "+field+" >= "+v1+" ";
					    		}else if(("小于").equals(oper)){
					    			sqlStr=" "+field+" < "+v1+" ";
					    		}else if(("小于等于").equals(oper)){
					    			sqlStr=" "+field+" <= "+v1+" ";
					    		}else if(("区间").equals(oper)){
					    			sqlStr=" ("+field+" >= "+v1+" and "+field+" <= "+v2+") ";
					    		}
					    	}else if(("DATE").equals(type)){//日期类型
					    		if(("等于").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" like '"+v1+"' ";
					    		}else if(("不等于").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" not like '"+v1+"' ";
					    		}else if(("大于").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" > '"+v1+"' ";
					    		}else if(("大于等于").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" >= '"+v1+"' ";
					    		}else if(("小于").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" < '"+v1+"' ";
					    		}else if(("小于等于").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" <= '"+v1+"' ";
					    		}else if(("区间").equals(oper)){
					    			sqlStr=" ("+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" >= '"+v1+"' and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" <= '"+v2+"')  ";
					    		}else if(("上年").equals(oper)){
					    		     //获取上年
					    			 int lastYear=newTime.get(Calendar.YEAR)-1;
					    			 sqlStr=" "+TeeDbUtility.DATE2CHAR_Y(dialect, field)+" = '"+lastYear+"' ";
					    			 
					    		}else if(("上季").equals(oper)){
					    			//获取当前月份
					    			int month=newTime.get(Calendar.MONTH)+1;
					    			//获取当前年份
					    			int year=newTime.get(Calendar.YEAR);
					    			String s="";//开始
					    			String e="";//结束
					    			if(month==1||month==2||month==3){
										s=(year-1)+"-"+"10";
										e=(year-1)+"-"+"12";
									}else if(month==4||month==5||month==6){
										s=year+"-"+"01";
										e=year+"-"+"03";
									}else if(month==7||month==8||month==9){
										s=year+"-"+"04";
										e=year+"-"+"06";
									}else {
										s=year+"-"+"07";
										e=year+"-"+"09";
									}
					    			
					    			sqlStr=" ("+TeeDbUtility.DATE2CHAR_Y_M(dialect, field)+" >= '"+s+"' and  "+TeeDbUtility.DATE2CHAR_Y_M(dialect, field)+" <= '"+e+"') ";
					    			
					    		}else if(("上月").equals(oper)){
					    			//获取上月
					    			Calendar l=(Calendar) newTime.clone();
					    			l.set(Calendar.DATE,1);
					    			l.add(Calendar.MONTH, -1);
					    			
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M(dialect, field)+" = '"+sdf1.format(l.getTime())+"' ";
					    		}else if(("上周").equals(oper)){
					    			Calendar z1=(Calendar) newTime.clone();//上周周一
					    			Calendar z7=(Calendar) newTime.clone();//上周周日
					    			int dayofweek = newTime.get(Calendar.DAY_OF_WEEK)-1;
					    			if(dayofweek==0){//周日
					    				dayofweek=7;
					    			}
					    			z1.add(Calendar.DATE, -(dayofweek-1)-7);
					    			z7.add(Calendar.DATE, +(7-dayofweek)-7);
					    			
					    			sqlStr=" ("+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" >= '"+sdf.format(z1.getTime())+"'  and  "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" <= '"+sdf.format(z7.getTime())+"') ";
					    			
					    			
					    		}else if(("昨日").equals(oper)){
					    			//获取昨天
					    			Calendar yesterday=(Calendar) newTime.clone();
					    			yesterday.add(Calendar.DAY_OF_MONTH, -1);
					    			
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" = '"+sdf.format(yesterday.getTime())+"' ";
					    			
					    		}else if(("本年").equals(oper)){
					    			 //获取本年
					    			 int nowYear=newTime.get(Calendar.YEAR);
					    			 sqlStr=" "+TeeDbUtility.DATE2CHAR_Y(dialect, field)+" = '"+nowYear+"' ";
					    		}else if(("本季").equals(oper)){
					    			//获取当前月份
					    			int month=newTime.get(Calendar.MONTH)+1;
					    			//获取当前年份
					    			int year=newTime.get(Calendar.YEAR);
					    			String s="";//开始
					    			String e="";//结束
					    			if(month==1||month==2||month==3){
										s=year+"-"+"01";
										e=year+"-"+"03";
									}else if(month==4||month==5||month==6){
										s=year+"-"+"04";
										e=year+"-"+"06";
									}else if(month==7||month==8||month==9){
										s=year+"-"+"07";
										e=year+"-"+"09";
									}else {
										s=year+"-"+"10";
										e=year+"-"+"12";
									}
					    			
					    			sqlStr=" ("+TeeDbUtility.DATE2CHAR_Y_M(dialect, field)+" >= '"+s+"' and  "+TeeDbUtility.DATE2CHAR_Y_M(dialect, field)+" <= '"+e+"') ";
					    		}else if(("本月").equals(oper)){
					    			sqlStr=" "+TeeDbUtility.DATE2CHAR_Y_M(dialect, field)+" = '"+sdf1.format(newTime.getTime())+"' ";
					    		}else if(("本周").equals(oper)){
					    			Calendar z1=(Calendar) newTime.clone();
					    			Calendar z7=(Calendar) newTime.clone();
					    			int dayofweek = newTime.get(Calendar.DAY_OF_WEEK)-1;
					    			if(dayofweek==0){//周日
					    				dayofweek=7;
					    			}
					    			z1.add(Calendar.DATE, -(dayofweek-1));
					    			z7.add(Calendar.DATE, +(7-dayofweek));
					    			
					    			sqlStr=" ("+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" >= '"+sdf.format(z1.getTime())+"'  and  "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" <= '"+sdf.format(z7.getTime())+"') ";
					    			
					    			
					    		}else if(("最近天数").equals(oper)){
					    			Calendar c=(Calendar) newTime.clone();
					    			int day=TeeStringUtil.getInteger(v1,0);
					    			c.add(Calendar.DAY_OF_MONTH, -day);
					    			 sqlStr=" ("+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" >= '"+sdf.format(c.getTime())+"'  and "+TeeDbUtility.DATE2CHAR_Y_M_D(dialect, field)+" <= '"+sdf.format(newTime.getTime())+"' ) ";
					    		}
					    			
					    	}
					    	
					    	return sqlStr;
					    }else{
					    	return "1!=1";
					    }
				    }else{
				    	return "1!=1";
				    }
				}
			});
		    
		    //加上过滤条件
		    if(!TeeUtility.isNullorEmpty(newConditionExp)){
		    	sql+=" where "+newConditionExp;
		    }
		    
		    
		    //加上维度    加上bisView的sql
		    sql+="  group by "+dimension;
		    
		    //判断排序
		    Map orderMap=TeeJsonUtil.JsonStr2Map(orderStr);
		    String orderField=TeeStringUtil.getString(orderMap.get("orderField"));
		    String orderDesc=TeeStringUtil.getString(orderMap.get("orderDesc"));
		    
		    if(!TeeUtility.isNullorEmpty(orderField)&&!TeeUtility.isNullorEmpty(orderDesc)){
		    	sql+=" order by "+orderField+" "+orderDesc;
		    }
		    
		    data.put("title",title);
		    data.put("chartTypeDesc",chartTypeDesc);
		    
		    //查询
		    Connection conn = null;
		    try{
		    	conn = bisViewService.getConnectionByView(model.getBisViewIdentity());
		    	
		    	DbUtils dbUtils = new DbUtils(conn);
		    	sql = sql.toUpperCase();
		    	List<Map> resultList = dbUtils.queryToMapList(sql);
		    	
		    	List<String> categories=new ArrayList<String>();
		    	List<Map> series=new ArrayList<Map>();//其他返回数据
		    	List<Map> xySeries=new ArrayList<Map>();//双轴图返回数据
		    	List <List> pieSeries=new ArrayList<List>();//饼状图返回数据
		    	if(resultList!=null&&resultList.size()>0){
		    		Map seriesMap=null;
		    		Map xySeriesMap=null;
		    		List seriesList=null;
		    		List xySeriesList=null;
		    		List pieList=null;
		    		for (Map map : resultList) {
		    			categories.add(TeeStringUtil.getString(map.get(dimensionAlias)));
					}
		    		
		    		if(chartType==1||chartType==2||chartType==3){//条形图    柱状图   折线图
		    			//循环主轴数值
			    		for (Map m:spindleMapList) {
			    			seriesMap=new HashMap();
			    			seriesList=new ArrayList();
			    			seriesMap.put("name",m.get("title")+"("+m.get("statistics")+")");
			    			for(Map map : resultList){
			    				seriesList.add(map.get(m.get("title")+"("+m.get("statistics")+")"));
			    			}
			    			seriesMap.put("data",seriesList);
			    			
			    			series.add(seriesMap);
			 			} 
			    		data.put("series", series);
			    		
		    		}else if(chartType==4){//饼状图
		    			for(Map map : resultList){
		    				pieList=new ArrayList();
		    				pieList.add(map.get(dimensionAlias));
		    				
		    				//循环主轴数值
				    		for (Map m:spindleMapList) {
				    			pieList.add(map.get(m.get("title")+"("+m.get("statistics")+")"));
				 			} 
				    		
				    		pieSeries.add(pieList);
		    			}
		    			
		    			data.put("series", pieSeries);
		    		}else if(chartType==5){//双轴图
		    			//循环主轴数值
			    		for (Map m:spindleMapList) {
			    			xySeriesMap=new HashMap();
			    			xySeriesList=new ArrayList();
			    			xySeriesMap.put("name",m.get("title")+"("+m.get("statistics")+")");
			    			xySeriesMap.put("type","column");
			    			xySeriesMap.put("yAxis",1);
			    			for(Map map : resultList){
			    				xySeriesList.add(map.get(m.get("title")+"("+m.get("statistics")+")"));
			    			}
			    			xySeriesMap.put("data",xySeriesList);
			    			
			    			xySeries.add(xySeriesMap);
			 			} 
			    		
			    		
			    		//循环次轴
			    		for (Map m:shaftMapList) {
			    			xySeriesMap=new HashMap();
			    			xySeriesList=new ArrayList();
			    			xySeriesMap.put("name",m.get("title")+"("+m.get("statistics")+")");
			    			xySeriesMap.put("type","line");
			    			//xySeriesMap.put("yAxis",2);
			    			for(Map map : resultList){
			    				xySeriesList.add(map.get(m.get("title")+"("+m.get("statistics")+")"));
			    			}
			    			xySeriesMap.put("data",xySeriesList);
			    			
			    			xySeries.add(xySeriesMap);
			 			} 
			    		
			    		data.put("series", xySeries);
		    		}
		    		
		    		
		    	}
		    	
		    	
		    	data.put("categories", categories);
		    	
		    	
		    	json.setRtData(data);
		    }catch(Exception ex){
		    	ex.printStackTrace();
		    }finally{
		    	TeeDbUtility.closeConn(conn);
		    }
		    
	    }else{
	    	json.setRtData(data);
	    }
	    json.setRtState(true);
	    return json;
	}



	/**
	 * 根据报表分类   权限  获取当前登录人  可以查看的报表
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson datagridViews(HttpServletRequest request,
			TeeDataGridModel dm) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		String hql = "from TeeEreport t where ";
		
		//加上权限控制
		hql+=" (exists (select 1 from t.users userPriv where userPriv.uuid="+loginUser.getUuid()+") or exists (select 1 from t.depts deptPriv where deptPriv.uuid="+loginUser.getDept().getUuid()+") or exists (select 1 from t.roles rolePriv where rolePriv.uuid="+loginUser.getUserRole().getUuid()+")) ";
		
		
		int catId = TeeStringUtil.getInteger(request.getParameter("catId"), 0);
		if(catId!=0){
			hql+=" and t.cat.sid="+catId;
		}
		
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeEreport> list = simpleDaoSupport.pageFind(hql+" order by t.crTime desc ", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<TeeEreportModel> modelList = new ArrayList<TeeEreportModel>();
		TeeEreportModel model=null;
		for(TeeEreport r:list){
			model = parseToModel(r);
			modelList.add(model);
		}	
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}



	/**
	 * 报表中心   渲染报表
	 * @param request
	 * @return
	 */
	public TeeJson renderGraph(HttpServletRequest request) {
		TeeJson json=null;;
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		TeeEreport report=(TeeEreport) simpleDaoSupport.get(TeeEreport.class,sid);
		if(report!=null){
			TeeEreportModel model=parseToModel(report);
			json= renderGraphics(request,model);
			json.setRtState(true);
		}else{
			json=new TeeJson();
			json.setRtState(false);
		}
		return json;
	}



	
	
   public static void main(String[] args) {
	  System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
  }
	
   

   /**
    * 导出
    * */
	public String exportXml(String sid){
		//BisView bisView = (BisView) simpleDaoSupport.get(BisView.class,identity );
		TeeEreport ereport = (TeeEreport)simpleDaoSupport.get(TeeEreport.class,Integer.parseInt(sid));
			Element root;   
			root=new Element("ereport");
			
			root.addContent(new Element("sid").setText(TeeStringUtil.getString(ereport.getSid())));
			root.addContent(new Element("title").setText(TeeStringUtil.getString(ereport.getTitle())));
			root.addContent(new Element("cat").setText(TeeStringUtil.getString(ereport.getCat().getSid())));
			root.addContent(new Element("crUser").setText(TeeStringUtil.getString(ereport.getCrUser().getUuid())));
			root.addContent(new Element("bisView").setText(TeeStringUtil.getString(ereport.getBisView().getIdentity())));
			root.addContent(new Element("chartType").setText(TeeStringUtil.getString(ereport.getChartType())));
			root.addContent(new Element("dimension").setText(TeeStringUtil.getString(ereport.getDimension())));
			root.addContent(new Element("spindle").setText(TeeStringUtil.getString(ereport.getSpindle())));
			//root.addContent(new Element("dataSource").setText("1"));
			root.addContent(new Element("shaft").setText(TeeStringUtil.getString(ereport.getShaft())));
			root.addContent(new Element("orderStr").setText(TeeStringUtil.getString(ereport.getOrderStr())));
			//root.addContent(new Element("crTime").setText(TeeStringUtil.getString(ereport.getCrTime())));
			root.addContent(new Element("conditionExp").setText(TeeStringUtil.getString(ereport.getConditionExp())));
			root.addContent(new Element("conditionItems").setText(TeeStringUtil.getString(ereport.getConditionItems())));
			
			Element roles = new Element("roles");
			Set<TeeUserRole> userRole = ereport.getRoles();
			for(TeeUserRole role:userRole){
				Element itemRole = new Element("role");
				itemRole.addContent(new Element("uuid").setText(TeeStringUtil.getString(role.getUuid())));
				//itemRole.addContent(new Element("roleName").setText(TeeStringUtil.getString(role.getRoleName())));
				//itemRole.addContent(new Element("roleNo").setText(TeeStringUtil.getString(role.getRoleNo())));
				roles.addContent(itemRole);
			}
			root.addContent(roles);
			
			
			Element users = new Element("users");
			Set<TeePerson> userList = ereport.getUsers();
			for(TeePerson user:userList){
				Element itemUser = new Element("user");
				itemUser.addContent(new Element("uuid").setText(TeeStringUtil.getString(user.getUuid())));
				//itemUser.addContent(new Element("userName").setText(TeeStringUtil.getString(user.getUserName())));
				users.addContent(itemUser);
			}
			root.addContent(users);
			
			
			Element depts = new Element("depts");
			Set<TeeDepartment> deptList = ereport.getDepts();
			for(TeeDepartment dept:deptList){
				Element itemDept = new Element("dept");
				itemDept.addContent(new Element("uuid").setText(TeeStringUtil.getString(dept.getUuid())));
				//itemDept.addContent(new Element("userName").setText(TeeStringUtil.getString(dept.getDeptName())));
				depts.addContent(itemDept);
			}
			root.addContent(depts);
			
			Document doc = new Document(root);   
			XMLOutputter out = new XMLOutputter();   
			
			String str = out.outputString(doc);
		
        
		return str;
	}
	
	/**
	 * 导入
	 * */
	public void importXml(InputStream in) throws JDOMException{
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(in);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		
		String sid = root.getChildText("sid");
		String title = root.getChildText("title");
		String cat = root.getChildText("cat");
		String crUser = root.getChildText("crUser");
		String bisView = root.getChildText("bisView");
		String chartType = root.getChildText("chartType");
		String dimension = root.getChildText("dimension");
		String spindle = root.getChildText("spindle");
		String shaft = root.getChildText("shaft");
		String orderStr = root.getChildText("orderStr");
		//String crTime = root.getChildText("crTime");
		String conditionExp = root.getChildText("conditionExp");
		String conditionItems = root.getChildText("conditionItems");
		
		TeeEreport port = new TeeEreport();
		port.setSid(Integer.parseInt(sid));
		port.setTitle(title);
		TeeSeniorReportCat cat1=(TeeSeniorReportCat)simpleDaoSupport.get(TeeSeniorReportCat.class,Integer.parseInt(cat));
		port.setCat(cat1);
		//TeePerson user=(TeePerson)simpleDaoSupport.get(TeePerson.class,Integer.parseInt(crUser));
		TeePerson user=new TeePerson();
		user.setUuid(Integer.parseInt(crUser));
		port.setCrUser(user);
		//BisView bis=(BisView)simpleDaoSupport.get(BisView.class,bisView);
		BisView bis=new BisView();
		bis.setIdentity(bisView);
	    port.setBisView(bis);
	    port.setChartType(Integer.parseInt(chartType));
	    port.setDimension(dimension);
	    port.setSpindle(spindle);
	    port.setShaft(shaft);
	    port.setOrderStr(orderStr);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    port.setCrTime(calendar);
	    port.setConditionExp(conditionExp);
	    port.setConditionItems(conditionItems);
	    
	    Set<TeeUserRole> roleList=new HashSet<TeeUserRole>();
	    Element roles = root.getChild("roles");
		List<Element>  itemRole= roles.getChildren();
		for(Element role:itemRole){
			String roleId = role.getChild("uuid").getText();
			//TeeUserRole ur=(TeeUserRole)simpleDaoSupport.get(TeeUserRole.class,Integer.parseInt(roleId));
		    TeeUserRole ur=new TeeUserRole();
		    ur.setUuid(Integer.parseInt(roleId));
			roleList.add(ur);
		}
		port.setRoles(roleList);
		
		Set<TeePerson> userList=new HashSet<TeePerson>();
	    Element users = root.getChild("users");
		List<Element>  itemUser= users.getChildren();
		for(Element user1:itemUser){
			String userId = user1.getChild("uuid").getText();
			//TeePerson u=(TeePerson)simpleDaoSupport.get(TeePerson.class,Integer.parseInt(userId));
			TeePerson u=new TeePerson();
			u.setUuid(Integer.parseInt(userId));
			userList.add(u);
		}
		port.setUsers(userList);
		
		Set<TeeDepartment> deptList=new HashSet<TeeDepartment>();
	    Element depts = root.getChild("depts");
		List<Element>  itemDept= depts.getChildren();
		for(Element dept:itemDept){
			String deptId = dept.getChild("uuid").getText();
			TeeDepartment d=new TeeDepartment();
			d.setUuid(Integer.parseInt(deptId));
			//TeeDepartment d=(TeeDepartment)simpleDaoSupport.get(TeeDepartment.class,Integer.parseInt(roleId));
		    deptList.add(d);
		}
		port.setDepts(deptList);
		simpleDaoSupport.save(port);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
