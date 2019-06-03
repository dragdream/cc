package com.tianee.oa.subsys.report.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.dbutils.DbUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.oa.subsys.bisengin.bean.BisView;
import com.tianee.oa.subsys.bisengin.service.BisViewService;
import com.tianee.oa.subsys.report.bean.TeeGenzReport;
import com.tianee.oa.subsys.report.bean.TeeSeniorReportCat;
import com.tianee.oa.subsys.report.bean.TeeSeniorReportTemplate;
import com.tianee.oa.subsys.report.model.TeeSeniorReportTemplateModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDataSource;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeSeniorReportService extends TeeBaseService{
	
	@Autowired
	private BisViewService bisViewService;
	
	public void addReport(TeeSeniorReportTemplateModel reportTemplateModel){
		TeeSeniorReportTemplate reportTemplate = new TeeSeniorReportTemplate();
		Model2Entity(reportTemplateModel, reportTemplate);
		
		int reportLimit = 0;
		long reportCount = 0;
		//做报表控制限制
		try {
			reportLimit = (Integer) TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "getReportLimit", null);
			reportCount = simpleDaoSupport.count("select count(uuid) from TeeSeniorReportTemplate", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(reportCount>=reportLimit){
			throw new TeeOperationException("报表数量超过系统限制最大值"+reportLimit);
		}
		
		if(reportTemplateModel.getResId()!=0){
			TeeGenzReport genzReport = new TeeGenzReport();
			genzReport.setResId(reportTemplateModel.getResId());
			reportTemplate.setGenzReport(genzReport);
		}
		simpleDaoSupport.save(reportTemplate);
		reportTemplateModel.setUuid(reportTemplate.getUuid());
	}
	
	public void updateReport(TeeSeniorReportTemplateModel reportTemplateModel){
		TeeSeniorReportTemplate reportTemplate = new TeeSeniorReportTemplate();
		Model2Entity(reportTemplateModel, reportTemplate);
		
		if(reportTemplateModel.getResId()!=0){
			TeeGenzReport genzReport = new TeeGenzReport();
			genzReport.setResId(reportTemplateModel.getResId());
			reportTemplate.setGenzReport(genzReport);
		}else{
			reportTemplate.setGenzReport(null);
		}
		
		simpleDaoSupport.update(reportTemplate);
		reportTemplateModel.setUuid(reportTemplate.getUuid());
	}
	
	public void delReport(String uuid){
		simpleDaoSupport.delete(TeeSeniorReportTemplate.class, uuid);
	}
	
	public TeeEasyuiDataGridJson datagrid(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from TeeSeniorReportTemplate t where 1=1 ";
		int catId = TeeStringUtil.getInteger(requestData.get("catId"), 0);
		if(catId!=0){
			hql+=" and t.reportCat.sid="+catId;
		}
		
		String tplName = TeeStringUtil.getString(requestData.get("tplName"));
		if(!"".equals(tplName)){
			hql+=" and t.tplName like '%"+tplName+"%'";
		}
		
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeSeniorReportTemplate> list = simpleDaoSupport.pageFind(hql+" order by reportCat asc sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<TeeSeniorReportTemplateModel> modelList = new ArrayList<TeeSeniorReportTemplateModel>();
		for(TeeSeniorReportTemplate entity:list){
			TeeSeniorReportTemplateModel m = new TeeSeniorReportTemplateModel();
			Entity2Model(entity, m);
			modelList.add(m);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeEasyuiDataGridJson datagridViews(Map requestData,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		
		
		String hql = "from TeeSeniorReportTemplate t where ";
		
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			hql+=" (exists (select 1 from t.userPriv userPriv where userPriv.uuid="+loginUser.getUuid()+") or exists (select 1 from t.deptPriv deptPriv where deptPriv.uuid="+loginUser.getDept().getUuid()+")) ";
		}else{
			hql+=" 1=1 ";
		}
		
		int catId = TeeStringUtil.getInteger(requestData.get("catId"), 0);
		if(catId!=0){
			hql+=" and t.reportCat.sid="+catId;
		}
		
		String tplName = TeeStringUtil.getString(requestData.get("tplName"));
		if(!"".equals(tplName)){
			hql+=" and t.tplName like '%"+tplName+"%'";
		}
		
		hql+=" and t.status=1 ";
		
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeSeniorReportTemplate> list = simpleDaoSupport.pageFind(hql+"order by reportCat.sortNo asc sid asc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		List<TeeSeniorReportTemplateModel> modelList = new ArrayList<TeeSeniorReportTemplateModel>();
		for(TeeSeniorReportTemplate entity:list){
			TeeSeniorReportTemplateModel m = new TeeSeniorReportTemplateModel();
			Entity2Model(entity, m);
			m.setBody("");
			m.setCtParams("");
			m.setCtView("");
			m.setFooter("");
			m.setGroup("");
			m.setHeader("");
			m.setModel("");
			m.setDeptPrivIds("");
			m.setDeptPrivNames("");
			m.setUserPrivIds("");
			m.setUserPrivNames("");
			modelList.add(m);
		}
		
		dataGridJson.setRows(modelList);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public TeeSeniorReportTemplateModel getReport(String uuid){
		TeeSeniorReportTemplate entity =  (TeeSeniorReportTemplate) simpleDaoSupport.get(TeeSeniorReportTemplate.class,uuid);
		TeeSeniorReportTemplateModel model  = new TeeSeniorReportTemplateModel();
		Entity2Model(entity,model);
		return model;
	}
	
	public void Model2Entity(TeeSeniorReportTemplateModel model,TeeSeniorReportTemplate entity){
		BeanUtils.copyProperties(model, entity);
		int userIds[] = TeeStringUtil.parseIntegerArray(model.getUserPrivIds());
		TeePerson p = null;
		for(int uuid:userIds){
			p = new TeePerson();
			p.setUuid(uuid);
			entity.getUserPriv().add(p);
		}
		
		int deptIds[] = TeeStringUtil.parseIntegerArray(model.getDeptPrivIds());
		TeeDepartment d = null;
		for(int uuid:deptIds){
			d = new TeeDepartment();
			d.setUuid(uuid);
			entity.getDeptPriv().add(d);
		}
		
		//设置分类
		entity.setReportCat((TeeSeniorReportCat)simpleDaoSupport.get(TeeSeniorReportCat.class, model.getCatId()));
		
	}
	
	public void Entity2Model(TeeSeniorReportTemplate entity,TeeSeniorReportTemplateModel model){
		BeanUtils.copyProperties(entity, model);
		StringBuffer ids = new StringBuffer();
		StringBuffer names = new StringBuffer();
		
		Set<TeePerson> userPriv = entity.getUserPriv();
		for(TeePerson p:userPriv){
			ids.append(p.getUuid()+",");
			names.append(p.getUserName()+",");
		}
		
		if(ids.length()!=0){
			ids.deleteCharAt(ids.length()-1);
			names.deleteCharAt(names.length()-1);
		}
		
		model.setUserPrivIds(ids.toString());
		model.setUserPrivNames(names.toString());
		
		if(ids.length()!=0){
			ids.delete(0, ids.length());
			names.delete(0, names.length());
		}
		
		Set<TeeDepartment> deptPriv = entity.getDeptPriv();
		for(TeeDepartment d:deptPriv){
			ids.append(d.getUuid()+",");
			names.append(d.getDeptName()+",");
		}
		
		model.setDeptPrivIds(ids.toString());
		model.setDeptPrivNames(names.toString());
		
		TeeSeniorReportCat cat = entity.getReportCat();
		if(cat!=null){
			model.setCatId(cat.getSid());
			model.setCatName(cat.getName());
			model.setColor(cat.getColor());
		}
		
		TeeGenzReport genzReport = entity.getGenzReport();
		if(genzReport!=null){
			model.setResId(genzReport.getResId());
			model.setResName(genzReport.getResName());
		}
	}
	
	/**
	 * 改变报表状态
	 * @param sid
	 * @param status
	 */
	public void changeStatus(String ids,int status){
		simpleDaoSupport.executeUpdate("update TeeSeniorReportTemplate t set t.status=? where "+TeeDbUtility.IN("t.uuid", ids), new Object[]{status});
	}
	
	/**
	 * 报表数据整合
	 * @param requestData
	 * @param dm
	 * @return
	 */
	public Object reportDatas(final Map requestData,TeeDataGridModel dm){
		String reportId = TeeStringUtil.getString(requestData.get("reportId"));
		TeePerson loginUser = (TeePerson) requestData.get(TeeConst.LOGIN_USER);
		TeeSeniorReportTemplate reportTemplate = 
				(TeeSeniorReportTemplate) simpleDaoSupport.get(TeeSeniorReportTemplate.class, reportId);
		requestData.put("dfid", reportTemplate.getDataIdentity());
		List<Map> list = null;
		BisView bisView = (BisView) simpleDaoSupport.get(BisView.class, reportTemplate.getDataIdentity());
		Connection conn = null;
		try{
			if(bisView.getType()==1){//设计模式
				
				String fromSql = bisViewService.formatSql(bisView.getFromSql(), dm, requestData, bisView, loginUser);
				String whereSql = bisViewService.formatSql(bisView.getWhereSql(), dm, requestData, bisView, loginUser);
				String selectSql = bisViewService.formatSql(bisView.getSelectSql(), dm, requestData, bisView, loginUser);
				if("".equals(whereSql)){
					whereSql = " 1=1 ";
				}
				
				final BisDataSource bisDataSource = bisView.getDataSource();
				String dialect = TeeSysProps.getDialect();
				if (bisDataSource.getDataSource() == 1) {// 内部数据源
					conn = TeeDbUtility.getConnection(null);
				} else {// 外部数据源
					TeeDataSource dataSource = new TeeDataSource();
					dataSource.setDriverClass(bisDataSource.getDriverClass());
					dataSource.setPassWord(bisDataSource.getDriverPwd());
					dataSource.setUrl(bisDataSource.getDriverUrl());
					dataSource.setUserName(bisDataSource.getDriverUsername());
					dataSource.setConfigModel(bisDataSource.getConfigModel());
					conn = TeeDbUtility.getConnection(dataSource);
				}
				DbUtils dbUtils = new DbUtils(conn);

//				TeePerson loginUser = (TeePerson) simpleDaoSupport.get(TeePerson.class,
//						TeeRequestInfoContext.getRequestInfo().getUserSid());
				
				Map<String,String> model = TeeJsonUtil.JsonStr2Map(reportTemplate.getModel());
				Map<String,String> rows = TeeJsonUtil.JsonStr2Map(model.get("rows"));
				Map<String,String> cols = TeeJsonUtil.JsonStr2Map(model.get("cols"));
				Map<String,String> sums = TeeJsonUtil.JsonStr2Map(model.get("sums"));
//				List<Map<String,String>> conditions = TeeJsonUtil.JsonStr2MapList(bisView.getDesignModel());
				
//				if(conditions!=null){
//					for(Map<String,String> item:conditions){
//						whereSql += " and "+item.get("exp");
//					}
//				}
				
//				System.out.println(whereSql);
				Map<String,String> designModel = TeeJsonUtil.JsonStr2Map(bisView.getDesignModel());
				List tables = TeeJsonUtil.JsonStr2MapList(designModel.get("tables"));
				boolean singleTable = tables.size()==1;
				
				List<String> rowsHeader = new ArrayList();//行表头
				List<String> colsHeader = new ArrayList();//列表头
				Map rowsData = new HashMap();//行数据
				Map colsData = new HashMap();//列数据
				List sumsValues = new ArrayList();
				
				//处理行和列数据
				getHeaders(rows,rowsHeader,rowsData,dbUtils,fromSql+" where "+whereSql,selectSql,singleTable);
				getHeaders(cols,colsHeader,colsData,dbUtils,fromSql+" where "+whereSql,selectSql,singleTable);
				
				if(sums!=null){
					if(rowsHeader.size()!=0 && colsHeader.size()==0){
						BigDecimal datas[] = new BigDecimal[rowsHeader.size()];
						int i = 0;
						for(String row:rowsHeader){
							datas[i++] = getValueByRow(dialect, rows, row, sums, dbUtils, fromSql+" where "+whereSql);
						}
						sumsValues.add(datas);
					}else if(rowsHeader.size()==0 && colsHeader.size()!=0){
						BigDecimal datas[] = new BigDecimal[colsHeader.size()];
						int i = 0;
						for(String col:colsHeader){
							datas[i++] = getValueByCol(dialect, cols, col, sums, dbUtils, fromSql+" where "+whereSql);
						}
						sumsValues.add(datas);
					}else{
						for(String row:rowsHeader){
							BigDecimal datas[] = new BigDecimal[colsHeader.size()];
							int i = 0;
							for(String col:colsHeader){
								datas[i++] = getValueByMulti(dialect,rows,row,cols,col,sums,dbUtils,fromSql+" where "+whereSql);
							}
							sumsValues.add(datas);
						}
					}
				}
				
				Map wrapData = new HashMap();
				wrapData.put("rowsHeader", rowsHeader);
				wrapData.put("colsHeader", colsHeader);
				wrapData.put("sumsValues", sumsValues);
				wrapData.put("rows", rows);
				wrapData.put("cols", cols);
				wrapData.put("sums", sums);
				wrapData.put("rowsData", rowsData);
				wrapData.put("colsData", colsData);
				
				
				int height = 40;
				int width = 120;
				//生成表头统计图形
				BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = buffImg.createGraphics();
				// 创建一个随机数生成器类         
		        Random random = new Random();         
		        // 将图像填充为白色         
		        g.setColor(Color.WHITE);       
		        g.fillRect(0, 0, width, height);
		        // 创建字体，字体的大小应该根据图片的高度来定。         
		        Font font = new Font("Fixedsys", Font.PLAIN, 12);         
		        // 设置字体。         
		        g.setFont(font);         
		        // 画边框。         
		        g.setColor(Color.gray);         
		        
		        if(rowsHeader.size()!=0 && colsHeader.size()==0){
		        	 g.drawLine(0, 0, width, height);
		        	 
		        	 g.setFont(new Font("Fixedsys", Font.PLAIN,9));
		        	g.rotate(0.2);
		        	String desc = "";
		        	if("SUM".equals(sums.get("type"))){
		        		desc = "求和";
		        	}else if("COUNT".equals(sums.get("type"))){
		        		desc = "计数";
		        	}else if("DISTINCT".equals(sums.get("type"))){
		        		desc = "投影";
		        	}else if("AVG".equals(sums.get("type"))){
		        		desc = "平均";
		        	}else if("MAX".equals(sums.get("type"))){
		        		desc = "最大";
		        	}else if("MIN".equals(sums.get("type"))){
		        		desc = "最小";
		        	}
		        	g.drawString(sums.get("name")+"("+desc+")", 30, 0);
		        	
		        	g.rotate(0.2);
		        	g.drawString(rows.get("name"), 10, 10);
			        	
		        }else if(rowsHeader.size()==0 && colsHeader.size()!=0){
		        	g.drawLine(0, 0, width, height);
		        	 
		        	 g.setFont(new Font("Fixedsys", Font.PLAIN,9));
			        	g.rotate(0.2);
			        	g.drawString(cols.get("name"), 30, 0);
			        	
			        	g.rotate(0.2);
			        	String desc = "";
			        	if("SUM".equals(sums.get("type"))){
			        		desc = "求和";
			        	}else if("COUNT".equals(sums.get("type"))){
			        		desc = "计数";
			        	}else if("DISTINCT".equals(sums.get("type"))){
			        		desc = "投影";
			        	}else if("AVG".equals(sums.get("type"))){
			        		desc = "平均";
			        	}else if("MAX".equals(sums.get("type"))){
			        		desc = "最大";
			        	}else if("MIN".equals(sums.get("type"))){
			        		desc = "最小";
			        	}
			        	g.drawString(sums.get("name")+"("+desc+")", 10, 10);
			        	
		        }else if(rowsHeader.size()!=0 && colsHeader.size()!=0){
		        	g.drawLine(0, 0, width-40, height);
		        	g.drawLine(0, 0, width, height-15);
		        	
		        	g.setFont(new Font("Fixedsys", Font.PLAIN,9));
		        	g.rotate(0.01);
		        	g.drawString(cols.get("name"), 50, 10);
		        	g.rotate(0.22);
		        	if("SUM".equals(sums.get("type"))){
		        		g.drawString(sums.get("name")+"(合计)", 50, 10);
		        	}else if("COUNT".equals(sums.get("type"))){
		        		g.drawString(sums.get("name")+"(总数)", 50, 10);
		        	}else if("DISTINCT".equals(sums.get("type"))){
		        		g.drawString(sums.get("name")+"(投影)", 50, 10);
		        	}else if("AVG".equals(sums.get("type"))){
		        		g.drawString(sums.get("name")+"(平均)", 50, 10);
		        	}else if("MAX".equals(sums.get("type"))){
		        		g.drawString(sums.get("name")+"(最大)", 50, 10);
		        	}else if("MIN".equals(sums.get("type"))){
		        		g.drawString(sums.get("name")+"(最小)", 50, 10);
		        	}
		        	g.rotate(0.01);
		        	g.drawString(rows.get("name"), 8, 22);
		        }
		        
		        
		        ByteArrayOutputStream output = new ByteArrayOutputStream();
		        ImageIO.write(buffImg, "jpeg", output);
		        
		        byte b[] = output.toByteArray();
		        BASE64Encoder base64Encoder = new BASE64Encoder();
		        wrapData.put("label", base64Encoder.encode(b));
				
				return wrapData;
			}else{//SQL模式
				TeeEasyuiDataGridJson dataGridJson = bisViewService.dflist(dm, requestData);
				return dataGridJson.getRows();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
		return null;
	}
	
	/**
	 * 获取值
	 * @param row
	 * @param col
	 * @param dbUtils
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getValueByRow(String dialect,Map<String,String> rows,String row,Map<String,String> sums,DbUtils dbUtils,String sql) throws Exception{
		if(rows.size()==0){
			return new BigDecimal(0);
		}
		if("DATE".equals(rows.get("fieldType")) || "DATETIME".equals(rows.get("fieldType"))){//处理日期时间
			String date = rows.get("date");
			String dateRange = rows.get("dateRange");
			if("YEAR".equals(date)){
				sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, rows.get("field"))+"='"+row+"'";
			}else if("MONTH".equals(date)){
				String sp[] = dateRange.split("\\|");
				sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, rows.get("field"))+"='"+sp[0]+"'";
				sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, rows.get("field"))+"='"+row+"'";
			}else if("DATE".equals(date)){
				String sp[] = dateRange.split("\\|");
				sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, rows.get("field"))+"='"+sp[0]+"'";
				sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, rows.get("field"))+"='"+sp[1]+"'";
				sql+=" and "+TeeDbUtility.DATE2CHAR_D(dialect, rows.get("field"))+"='"+row+"'";
			}
		}else if("NUMBER".equals(rows.get("fieldType"))){//处理数字类型
			sql+=" and "+rows.get("field")+"="+row;
		}else{//处理字符串型
			sql+=" and "+rows.get("field")+"='"+row+"'";
		}
		
		
		Map tmp = null;
		if("COUNT".equals(sums.get("type"))){//计数
			tmp = dbUtils.queryToMap("select COUNT("+sums.get("field")+") as T from "+sql);
		}else if("DISTINCT".equals(sums.get("type"))){//投影计数
			tmp = dbUtils.queryToMap("select COUNT(DISTINCT("+sums.get("field")+")) as T from "+sql);
		}else if("SUM".equals(sums.get("type"))){//总和
			tmp = dbUtils.queryToMap("select SUM("+sums.get("field")+") as T from "+sql);
		}else if("MAX".equals(sums.get("type"))){//最大
			tmp = dbUtils.queryToMap("select MAX("+sums.get("field")+") as T from "+sql);
		}else if("MIN".equals(sums.get("type"))){//最小
			tmp = dbUtils.queryToMap("select MIN("+sums.get("field")+") as T from "+sql);
		}else if("AVG".equals(sums.get("type"))){//平均数
			tmp = dbUtils.queryToMap("select AVG("+sums.get("field")+") as T from "+sql);
		}
		
		BigDecimal bigDecimal = new BigDecimal(TeeStringUtil.getString(tmp.get("T"), "0"));
		return bigDecimal;
	}
	
	private BigDecimal getValueByCol(String dialect,Map<String,String> cols,String col,Map<String,String> sums,DbUtils dbUtils,String sql) throws Exception{
		if(cols.size()==0){
			return new BigDecimal(0);
		}
		if("DATE".equals(cols.get("fieldType")) || "DATETIME".equals(cols.get("fieldType"))){//处理日期时间
			String date = cols.get("date");
			String dateRange = cols.get("dateRange");
			if("YEAR".equals(date)){
				sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, cols.get("field"))+"='"+col+"'";
			}else if("MONTH".equals(date)){
				String sp[] = dateRange.split("\\|");
				sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, cols.get("field"))+"='"+sp[0]+"'";
				sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, cols.get("field"))+"='"+col+"'";
			}else if("DATE".equals(date)){
				String sp[] = dateRange.split("\\|");
				sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, cols.get("field"))+"='"+sp[0]+"'";
				sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, cols.get("field"))+"='"+sp[1]+"'";
				sql+=" and "+TeeDbUtility.DATE2CHAR_D(dialect, cols.get("field"))+"='"+col+"'";
			}
		}else if("NUMBER".equals(cols.get("fieldType"))){//处理数字类型
			sql+=" and "+cols.get("field")+"="+col;
		}else{//处理字符串型
			sql+=" and "+cols.get("field")+"='"+col+"'";
		}
		
		Map tmp = null;
		if("COUNT".equals(sums.get("type"))){//计数
			tmp = dbUtils.queryToMap("select COUNT("+sums.get("field")+") as T from "+sql);
		}else if("DISTINCT".equals(sums.get("type"))){//投影计数
			tmp = dbUtils.queryToMap("select COUNT(DISTINCT("+sums.get("field")+")) as T from "+sql);
		}else if("SUM".equals(sums.get("type"))){//总和
			tmp = dbUtils.queryToMap("select SUM("+sums.get("field")+") as T from "+sql);
		}else if("MAX".equals(sums.get("type"))){//最大
			tmp = dbUtils.queryToMap("select MAX("+sums.get("field")+") as T from "+sql);
		}else if("MIN".equals(sums.get("type"))){//最小
			tmp = dbUtils.queryToMap("select MIN("+sums.get("field")+") as T from "+sql);
		}else if("AVG".equals(sums.get("type"))){//平均数
			tmp = dbUtils.queryToMap("select AVG("+sums.get("field")+") as T from "+sql);
		}
		
		BigDecimal bigDecimal = new BigDecimal(TeeStringUtil.getString(tmp.get("T"), "0"));
		return bigDecimal;
	}
	
	/**
	 * 获取值
	 * @param row
	 * @param col
	 * @param dbUtils
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	private BigDecimal getValueByMulti(String dialect,Map<String,String> rows,String row,Map<String,String> cols,String col,Map<String,String> sums,DbUtils dbUtils,String sql) throws Exception{
		
		if(rows.size()!=0){
			if("DATE".equals(rows.get("fieldType")) || "DATETIME".equals(rows.get("fieldType"))){//处理日期时间
				String date = rows.get("date");
				String dateRange = rows.get("dateRange");
				if("YEAR".equals(date)){
					sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, rows.get("field"))+"='"+row+"'";
				}else if("MONTH".equals(date)){
					String sp[] = dateRange.split("\\|");
					sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, rows.get("field"))+"='"+sp[0]+"'";
					sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, rows.get("field"))+"='"+row+"'";
				}else if("DATE".equals(date)){
					String sp[] = dateRange.split("\\|");
					sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, rows.get("field"))+"='"+sp[0]+"'";
					sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, rows.get("field"))+"='"+sp[1]+"'";
					sql+=" and "+TeeDbUtility.DATE2CHAR_D(dialect, rows.get("field"))+"='"+row+"'";
				}
			}else if("NUMBER".equals(rows.get("fieldType"))){//处理数字类型
				sql+=" and "+rows.get("field")+"="+row;
			}else{//处理字符串型
				sql+=" and "+rows.get("field")+"='"+row+"'";
			}
		}
		
		if(cols.size()!=0){
			if("DATE".equals(cols.get("fieldType")) || "DATETIME".equals(cols.get("fieldType"))){//处理日期时间
				String date = cols.get("date");
				String dateRange = cols.get("dateRange");
				if("YEAR".equals(date)){
					sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, cols.get("field"))+"='"+col+"'";
				}else if("MONTH".equals(date)){
					String sp[] = dateRange.split("\\|");
					sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, cols.get("field"))+"='"+sp[0]+"'";
					sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, cols.get("field"))+"='"+col+"'";
				}else if("DATE".equals(date)){
					String sp[] = dateRange.split("\\|");
					sql+=" and "+TeeDbUtility.DATE2CHAR_Y(dialect, cols.get("field"))+"='"+sp[0]+"'";
					sql+=" and "+TeeDbUtility.DATE2CHAR_M(dialect, cols.get("field"))+"='"+sp[1]+"'";
					sql+=" and "+TeeDbUtility.DATE2CHAR_D(dialect, cols.get("field"))+"='"+col+"'";
				}
			}else if("NUMBER".equals(cols.get("fieldType"))){//处理数字类型
				sql+=" and "+cols.get("field")+"="+col;
			}else{//处理字符串型
				sql+=" and "+cols.get("field")+"='"+col+"'";
			}
		}
		
		
		Map tmp = null;
		if("COUNT".equals(sums.get("type"))){//计数
			tmp = dbUtils.queryToMap("select COUNT("+sums.get("field")+") as T from "+sql);
		}else if("DISTINCT".equals(sums.get("type"))){//投影计数
			tmp = dbUtils.queryToMap("select COUNT(DISTINCT("+sums.get("field")+")) as T from "+sql);
		}else if("SUM".equals(sums.get("type"))){//总和
			tmp = dbUtils.queryToMap("select SUM("+sums.get("field")+") as T from "+sql);
		}else if("MAX".equals(sums.get("type"))){//最大
			tmp = dbUtils.queryToMap("select MAX("+sums.get("field")+") as T from "+sql);
		}else if("MIN".equals(sums.get("type"))){//最小
			tmp = dbUtils.queryToMap("select MIN("+sums.get("field")+") as T from "+sql);
		}else if("AVG".equals(sums.get("type"))){//平均数
			tmp = dbUtils.queryToMap("select AVG("+sums.get("field")+") as T from "+sql);
		}
		
		BigDecimal bigDecimal = new BigDecimal(TeeStringUtil.getString(tmp.get("T"), "0"));
		return bigDecimal;
	}
	
	/**
	 * 获取各项头数据
	 * @param model
	 * @param header
	 * @param dbUtils
	 * @throws Exception 
	 */
	private void getHeaders(Map<String,String> model,List header,Map data,DbUtils dbUtils,String sql,String selectSql,boolean singleTable) throws Exception{
		if(model.size()==0){
			return;
		}
		//处理列
		if("DATE".equals(model.get("fieldType")) || "DATETIME".equals(model.get("fieldType"))){//处理日期时间
			String date = model.get("date");
			String dateRange = model.get("dateRange");
			if("YEAR".equals(date)){
				String sp[] = dateRange.split(",");
				for(String y:sp){
					header.add(y);
				}
			}else if("MONTH".equals(date)){
				String sp[] = dateRange.split("\\|");
				String sp1[] = sp[1].split(",");
				for(String y:sp1){
					header.add(y);
				}
			}else if("DATE".equals(date)){
				String sp[] = dateRange.split("\\|");
				String sp1[] = sp[2].split(",");
				for(String y:sp1){
					header.add(y);
				}
			}
		}else{//处理普通数据
			
			//将selectSql拆分，并找到与投影相同的表字段
			String field = model.get("field");
			String sp[] = selectSql.split(",");
			String select = "";
			for(String sp0:sp){
				if(sp0.indexOf(field.split("\\.")[0])!=-1){
					select+=","+sp0;
				}
			}
			
			List<Map> datas = null;
			if(singleTable){
				datas = dbUtils.queryToMapList("select distinct("+model.get("field")+") as D from "+sql);
			}else{
				datas = dbUtils.queryToMapList("select distinct("+model.get("field")+") as D"+select+" from "+sql);
			}
			for(Map d:datas){
				header.add(d.get("D")+"");
				data.put(d.get("D"), d);
			}
			
		}
	}
	
	/**
	 * 批量设置
	 * @param uuids
	 * @param userPrivs
	 * @param deptPrivs
	 */
	public void batchSettings(String ids,String userPrivs,String deptPrivs){
		String sp1[] = TeeStringUtil.parseStringArray(userPrivs);
		String sp2[] = TeeStringUtil.parseStringArray(deptPrivs);
		
		List<TeeSeniorReportTemplate> list = simpleDaoSupport.find("from TeeSeniorReportTemplate where "+TeeDbUtility.IN("uuid", ids), null);
		TeePerson p = null;
		TeeDepartment d = null;
		for(TeeSeniorReportTemplate reportTemplate:list){
			reportTemplate.getUserPriv().clear();
			reportTemplate.getDeptPriv().clear();
			
			for(String uid:sp1){
				if("".equals(uid)){
					continue;
				}
				p = new TeePerson();
				p.setUuid(Integer.parseInt(uid));
				reportTemplate.getUserPriv().add(p);
			}
			
			for(String uid:sp2){
				if("".equals(uid)){
					continue;
				}
				d = new TeeDepartment();
				d.setUuid(Integer.parseInt(uid));
				reportTemplate.getDeptPriv().add(d);
			}
		}
		
	}
	
	
	
	/**
	 * @param uuid
	 * @return
	 */
	public String exportXml(String uuid){
		TeeSeniorReportTemplateModel model = getReport(uuid);
		Element root;   
		root=new Element("Report");
		
		root.addContent(new Element("uuid").setText(TeeStringUtil.getString(model.getUuid())));
		root.addContent(new Element("tplName").setText(TeeStringUtil.getString(model.getTplName())));
		root.addContent(new Element("header").setText(TeeStringUtil.getString(model.getHeader())));
		root.addContent(new Element("body").setText(TeeStringUtil.getString(model.getBody())));
		root.addContent(new Element("footer").setText(TeeStringUtil.getString(model.getFooter())));
		root.addContent(new Element("dataIdentity").setText(TeeStringUtil.getString(model.getDataIdentity())));
		root.addContent(new Element("pageSize").setText(TeeStringUtil.getString(model.getPageSize())));
		root.addContent(new Element("group").setText(TeeStringUtil.getString(model.getGroup())));
		root.addContent(new Element("chartType").setText(TeeStringUtil.getString(model.getChartType())));
		root.addContent(new Element("reverse").setText(TeeStringUtil.getString(model.getReverse())));
		root.addContent(new Element("params").setText(TeeStringUtil.getString(model.getParams())));
		root.addContent(new Element("status").setText(TeeStringUtil.getString(model.getStatus())));
		root.addContent(new Element("ctType").setText(TeeStringUtil.getString(model.getCtType())));
		root.addContent(new Element("ctView").setText(TeeStringUtil.getString(model.getCtView())));
		root.addContent(new Element("ctReport").setText(TeeStringUtil.getString(model.getCtReport())));
		root.addContent(new Element("ctParams").setText(TeeStringUtil.getString(model.getCtParams())));
		root.addContent(new Element("model").setText(TeeStringUtil.getString(model.getModel())));
		
		Document doc = new Document(root);   
        XMLOutputter out = new XMLOutputter();   
        
        String str = out.outputString(doc);
        
		return str;
	}
	
	
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
		
		String uuid = root.getChildText("uuid");
		String tplName = root.getChildText("tplName");
		String header = root.getChildText("header");
		String body = root.getChildText("body");
		String footer = root.getChildText("footer");
		String dataIdentity = root.getChildText("dataIdentity");
		int pageSize = TeeStringUtil.getInteger(root.getChildText("pageSize"), 0);
		String group = root.getChildText("group");
		String chartType = root.getChildText("chartType");
		int reverse = TeeStringUtil.getInteger(root.getChildText("reverse"),0);
		String params = root.getChildText("params");
		int status = TeeStringUtil.getInteger(root.getChildText("status"),0);
		int ctType = TeeStringUtil.getInteger(root.getChildText("ctType"),0);
		String ctView = root.getChildText("ctView");
		String ctReport = root.getChildText("ctReport");
		String ctParams = root.getChildText("ctParams");
		String model = root.getChildText("model");
		
		
		
		TeeSeniorReportTemplate seniorReportTemplate = new TeeSeniorReportTemplate();
		seniorReportTemplate.setUuid(uuid);
		seniorReportTemplate.setTplName(tplName);
		seniorReportTemplate.setHeader(header);
		seniorReportTemplate.setBody(body);
		seniorReportTemplate.setFooter(footer);
		seniorReportTemplate.setDataIdentity(dataIdentity);
		seniorReportTemplate.setPageSize(pageSize);
		seniorReportTemplate.setGroup(group);
		seniorReportTemplate.setChartType(chartType);
		seniorReportTemplate.setReverse(reverse);
		seniorReportTemplate.setParams(params);
		seniorReportTemplate.setStatus(status);
		seniorReportTemplate.setCtView(ctView);
		seniorReportTemplate.setCtType(ctType);
		seniorReportTemplate.setCtReport(ctReport);
		seniorReportTemplate.setCtParams(ctParams);
		seniorReportTemplate.setModel(model);
		
		simpleDaoSupport.save(seniorReportTemplate);
		
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
