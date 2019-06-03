package com.tianee.oa.core.workflow.flowrun.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowArchive;
import com.tianee.oa.core.workflow.flowrun.model.TeeFlowArchiveModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class TeeFlowArchiveService extends TeeBaseService implements TeeFlowArchiveServiceInterface{

	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface#getArchiveList(com.tianee.oa.webframe.httpModel.TeeDataGridModel, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeEasyuiDataGridJson getArchiveList(TeeDataGridModel dm,
			HttpServletRequest request) {
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		
		String hql = "from TeeFlowArchive fa where deleteStatus=0 ";
		List<TeeFlowArchive> list = simpleDaoSupport.pageFind(hql+"order by fa.crTime desc", dm.getRows()*(dm.getPage()-1), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		List<TeeFlowArchiveModel> models = new ArrayList<TeeFlowArchiveModel>();
		TeeFlowArchiveModel model = null;
		for(TeeFlowArchive flowArchive:list){
			model = new TeeFlowArchiveModel();
			entityToModel(flowArchive, model);
			models.add(model);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(models);
		
		return dataGridJson;
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface#entityToModel(com.tianee.oa.core.workflow.flowrun.bean.TeeFlowArchive, com.tianee.oa.core.workflow.flowrun.model.TeeFlowArchiveModel)
	 */
	@Override
	public void entityToModel(TeeFlowArchive flowArchive,TeeFlowArchiveModel model){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
		BeanUtils.copyProperties(flowArchive, model);
		if(flowArchive.getCrTime()!=null){
			model.setCrTimeStr(sdf.format(flowArchive.getCrTime()));
		}
		if(flowArchive.getArchiveDate()!=null){
			model.setCrTimeStr(sdf1.format(flowArchive.getArchiveDate()));
		}
	}


	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface#getArchiveCount(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getArchiveCount(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	
		String archiveDateStr=TeeStringUtil.getString(request.getParameter("archiveDateStr"));
		Date archiveDate=null;
		try {
			archiveDate = sdf.parse(archiveDateStr+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c=Calendar.getInstance();
		c.setTime(archiveDate);
		int day = c.get(Calendar.DATE);  
		c.set(Calendar.DATE, day - 1); 
		Map map=new  HashMap();	
		long sumCount=simpleDaoSupport.count("select count(*) from TeeFlowRun where  beginTime <=? ",new Object[]{c} );
		long archiveCount=simpleDaoSupport.count("select count(*) from TeeFlowRun where  beginTime <=? and endTime is not null ",new Object[]{c} );
		long noArchiveCount=simpleDaoSupport.count("select count(*) from TeeFlowRun where  beginTime <=? and endTime is  null ",new Object[]{c} );
		
		map.put("sumCount", sumCount);
		map.put("archiveCount", archiveCount);
		map.put("noArchiveCount", noArchiveCount);
		
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface#doArchive(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doArchive(HttpServletRequest request,HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		
		//获取writer写出流
		PrintWriter printWriter = response.getWriter();
		printWriter.write("<style>p{font-size:12px;}</style>");
		printWriter.flush();
		printWriter.write("<p style='color:red;font-weight:bold;'>开始归档</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		printWriter.flush();
		
		SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat  sdf1=new SimpleDateFormat("yyyy-MM-dd");
		String archiveDesc=TeeStringUtil.getString(request.getParameter("archiveDesc"));
		String archiveDateStr=TeeStringUtil.getString(request.getParameter("archiveDateStr"));//页面上获取的日期
		String archiveDateStr1=archiveDateStr.replace("-","");
		Date archiveDate=null;
		Date archiveDate1=null;
		boolean status=true;//执行状态
		List<String>sucessTableNameList=new ArrayList<String>();
		try {
			archiveDate1=sdf1.parse(archiveDateStr);
			archiveDate = sdf.parse(archiveDateStr+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c=Calendar.getInstance();
		c.setTime(archiveDate);
		int day = c.get(Calendar.DATE);  
		c.set(Calendar.DATE, day - 1); 
		
		//获取需要归档的流程
		List<TeeFlowType> flowTypeList=simpleDaoSupport.executeQuery("select distinct(fr.flowType) from TeeFlowRun fr where  fr.beginTime <=? and fr.endTime is not null ", new Object[]{c});
//		for(TeeFlowType ft:flowTypeList){
//			
//		}
		printWriter.write("<p >正在处理"+flowTypeList.size()+"种流程类型</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
		printWriter.flush();
		
		//获取数据库方言
//		String dialect=TeeSysProps.getDialect();
//		if(("mysql").equals(dialect)){//mysql数据库
//			
//		}

	   /*******************在归档库里创建数据库表  并且根据条件进行相应的数据迁移 **************************/
		try {
			
			//person（整体）
			printWriter.write("<p >正在归档数据表person</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.person_"+archiveDateStr1+" as (select * from oaop.person where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.person_"+archiveDateStr1);
			printWriter.write("<p >数据表person归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//department（整体）
			printWriter.write("<p >正在归档数据表department</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.department_"+archiveDateStr1+" as (select * from oaop.department where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.department_"+archiveDateStr1);
			printWriter.write("<p >数据表department归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//role（整体）
			printWriter.write("<p >正在归档数据表user_role</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.user_role_"+archiveDateStr1+" as (select * from oaop.user_role where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.user_role_"+archiveDateStr1);
			printWriter.write("<p >数据表user_role归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_frocess_print_template（整体）
			printWriter.write("<p >正在归档数据表flow_frocess_print_template</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_frocess_print_template_"+archiveDateStr1+" as (select * from oaop.flow_frocess_print_template where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_frocess_print_template_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_frocess_print_template归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_ntko_print_template（整体）
			printWriter.write("<p >正在归档数据表flow_ntko_print_template</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_ntko_print_template_"+archiveDateStr1+" as (select * from oaop.flow_ntko_print_template where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_ntko_print_template_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_ntko_print_template归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_print_template（整体）
			printWriter.write("<p >正在归档数据表flow_print_template</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_print_template_"+archiveDateStr1+" as (select * from oaop.flow_print_template where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_print_template_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_print_template归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_priv_dept_post（整体）
			printWriter.write("<p >正在归档数据表flow_priv_dept_post</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_priv_dept_post_"+archiveDateStr1+" as (select * from oaop.flow_priv_dept_post where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_priv_dept_post_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_priv_dept_post归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_process（整体）
			printWriter.write("<p >正在归档数据表flow_process</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_process_"+archiveDateStr1+" as (select * from oaop.flow_process where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_process_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_process归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_sort（整体）
			printWriter.write("<p >正在归档数据表flow_sort</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_sort_"+archiveDateStr1+" as (select * from oaop.flow_sort where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_sort_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_sort归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_type（整体）
			printWriter.write("<p >正在归档数据表flow_type</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_type_"+archiveDateStr1+" as (select * from oaop.flow_type where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.flow_type_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_type归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//form（整体）
			printWriter.write("<p >正在归档数据表form</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.form_"+archiveDateStr1+" as (select * from oaop.form where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.form_"+archiveDateStr1);
			printWriter.write("<p >数据表form归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//form_item（整体）
			printWriter.write("<p >正在归档数据表form_item</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.form_item_"+archiveDateStr1+" as (select * from oaop.form_item where 1=1 )", null);
			sucessTableNameList.add("oaop_archives.form_item_"+archiveDateStr1);
			printWriter.write("<p >数据表form_item归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run（部分）
			printWriter.write("<p >正在归档数据表flow_run</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_"+archiveDateStr1+" as (select * from oaop.flow_run where begin_time<=? and end_time is not null )",new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_concern（部分）
			printWriter.write("<p >正在归档数据表flow_run_concern</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_concern_"+archiveDateStr1+" as (select * from oaop.flow_run_concern frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null) )",new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_concern_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_concern归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_ctrl_fb（部分）
			printWriter.write("<p >正在归档数据表flow_run_ctrl_fb</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_ctrl_fb_"+archiveDateStr1+" as (select * from oaop.flow_run_ctrl_fb frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )", new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_ctrl_fb_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_ctrl_fb归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_doc（部分）
			printWriter.write("<p >正在归档数据表flow_run_doc</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_doc_"+archiveDateStr1+" as (select * from oaop.flow_run_doc frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )",new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_doc_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_doc归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_log（部分）
			printWriter.write("<p >正在归档数据表flow_run_log</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_log_"+archiveDateStr1+" as (select * from oaop.flow_run_log frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )", new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_log_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_log归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_prcs（部分）
			printWriter.write("<p >正在归档数据表flow_run_prcs</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_prcs_"+archiveDateStr1+" as (select * from oaop.flow_run_prcs frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )", new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_prcs_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_prcs归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_upload_ctrl（部分）
			printWriter.write("<p >正在归档数据表flow_run_upload_ctrl</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_upload_ctrl_"+archiveDateStr1+" as (select * from oaop.flow_run_upload_ctrl frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )", new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_upload_ctrl_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_upload_ctrl归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//flow_run_view（部分）
			printWriter.write("<p >正在归档数据表flow_run_view</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.flow_run_view_"+archiveDateStr1+" as (select * from oaop.flow_run_view frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )", new Object[]{c});
			sucessTableNameList.add("oaop_archives.flow_run_view_"+archiveDateStr1);
			printWriter.write("<p >数据表flow_run_view归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//attachment（部分）不刪除
			printWriter.write("<p >正在归档数据表attachment</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.attachment_"+archiveDateStr1+" as (select * from oaop.attachment where model in ('workFlow','workFlowVoice','workFlowDocAip','workFlowUploadCtrl','workFlowNtkoPrintTpl','workFlowFeedBack','workFlowDoc','imgupload') )",null);
			sucessTableNameList.add("oaop_archives.attachment_"+archiveDateStr1);
			printWriter.write("<p >数据表attachment归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			
			//tee_f_r_d_xxx
			if(flowTypeList!=null&&flowTypeList.size()>0){
				for (TeeFlowType teeFlowType : flowTypeList) {	
					printWriter.write("<p >正在归档数据表tee_f_r_d_"+teeFlowType.getSid()+"</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					printWriter.flush();
					simpleDaoSupport.executeNativeUpdate(" create table oaop_archives.tee_f_r_d_"+teeFlowType.getSid()+"_"+archiveDateStr1+" as (select * from oaop.tee_f_r_d_"+teeFlowType.getSid()+" frc where exists (select 1 from flow_run fr where fr.run_id=frc.run_id and fr.begin_time <=? and fr.end_time is not null)  )", new Object[]{c});
					sucessTableNameList.add("oaop_archives.tee_f_r_d_"+teeFlowType.getSid()+"_"+archiveDateStr1);
					printWriter.write("<p >数据表tee_f_r_d_"+teeFlowType.getSid()+"归档完成</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
					printWriter.flush();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			printWriter.write("<p >错误信息："+e.toString()+"</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			
			status=false;//中途报错
			
			printWriter.write("<p >归档中断，正在回溯数据…</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
			printWriter.flush();
			//刪除报错之前已经创建的表
			if(sucessTableNameList!=null&&sucessTableNameList.size()>0){
				for (String tbName : sucessTableNameList) {
					simpleDaoSupport.executeNativeUpdate(" drop table "+ tbName, null);
				}
			}
			
		}finally{
			if(status){
				printWriter.write("<p >所有数据归档完毕，正在清理历史数据…</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				printWriter.flush();
				/****************************刪除原来数据库中多余的数据*****************************/
				//flow_run_concern（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_concern where exists (select 1 from oaop.flow_run fr where fr.run_id=oaop.flow_run_concern.run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
			    //flow_run_ctrl_fb（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_ctrl_fb where exists (select 1 from flow_run fr where fr.run_id=oaop.flow_run_ctrl_fb.run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
			    //flow_run_doc（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_doc where exists (select 1 from flow_run fr where fr.run_id=oaop.flow_run_doc.run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
			    //flow_run_log（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_log where exists (select 1 from flow_run fr where fr.run_id=oaop.flow_run_log.run_id and fr.begin_time <=? and fr.end_time is not null)" , new Object[]{c});
				//flow_run_prcs（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_prcs where exists (select 1 from flow_run fr where fr.run_id=oaop.flow_run_prcs.run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
			    //flow_run_upload_ctrl（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_upload_ctrl where exists (select 1 from flow_run fr where fr.run_id=oaop.flow_run_upload_ctrl.run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
			    //flow_run_view（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run_view where exists (select 1 from flow_run fr where fr.run_id=oaop.flow_run_view.run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
			    //tee_f_r_d_xxx
				if(flowTypeList!=null&&flowTypeList.size()>0){
					for (TeeFlowType teeFlowType : flowTypeList) {	
						simpleDaoSupport.executeNativeUpdate("  delete  from oaop.tee_f_r_d_"+teeFlowType.getSid()+" where exists (select 1 from flow_run fr where fr.run_id=oaop.tee_f_r_d_"+teeFlowType.getSid()+".run_id and fr.begin_time <=? and fr.end_time is not null) ", new Object[]{c});
					}
				}
				//flow_run（部分）
				simpleDaoSupport.executeNativeUpdate(" delete from oaop.flow_run where begin_time<=? and end_time is not null ",new Object[]{c});
				printWriter.write("<p >历史数据清理完毕</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				printWriter.flush();
				
				//往归档表中插入数据
				TeeFlowArchive flowArchive=new TeeFlowArchive();
				flowArchive.setArchiveDate(archiveDate1);
				flowArchive.setArchiveDesc(archiveDesc);
				flowArchive.setCrTime(new Date());
				flowArchive.setStatus(1);
				flowArchive.setDeleteStatus(0);
				flowArchive.setVersion(TeeSysProps.getString("VERSION"));
				simpleDaoSupport.save(flowArchive);
				
				printWriter.write("<p style='color:green;font-weight:bold;'>归档结束！</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				printWriter.flush();
				
			}else{//操作失败
				printWriter.write("<p style='color:green;font-weight:bold;'>归档结束！</p><script>window.scrollTo(0,document.body.scrollHeight);</script>");
				printWriter.flush();
			}
		}		
	}


	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface#del(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson del(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeFlowArchive archive=(TeeFlowArchive) simpleDaoSupport.get(TeeFlowArchive.class,sid);
		    if(archive!=null){
		    	archive.setDeleteStatus(1);
		    	simpleDaoSupport.save(archive);
		    	json.setRtState(true);
		    	json.setRtMsg("删除成功！");	
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("该归档数据不存在！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("该归档数据不存在！");
		}
		return json;
	}


	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowArchiveServiceInterface#getArchiveListByDate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public TeeJson getArchiveListByDate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
		String archiveDateStr=TeeStringUtil.getString(request.getParameter("archiveDateStr"));//页面上获取的日期
		Date archiveDate=null;
		try {
			archiveDate=sdf.parse(archiveDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hql = "from TeeFlowArchive fa where  archiveDate=? ";
		List<TeeFlowArchive> archiveList=simpleDaoSupport.executeQuery(hql, new Object[]{archiveDate});
		if(archiveList!=null&&archiveList.size()>0){
			json.setRtData(false);
		}else{
			json.setRtData(true);
		}
		json.setRtState(true);
		return json;
	}
	
	
	

}
