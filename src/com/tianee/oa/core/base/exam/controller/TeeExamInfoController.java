package com.tianee.oa.core.base.exam.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.exam.bean.TeeExamInfo;
import com.tianee.oa.core.base.exam.bean.TeeExamRecord;
import com.tianee.oa.core.base.exam.model.TeeExamInfoModel;
import com.tianee.oa.core.base.exam.service.TeeExamDataService;
import com.tianee.oa.core.base.exam.service.TeeExamInfoService;
import com.tianee.oa.core.base.vote.service.TeeExportExcel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("TeeExamInfoController")
public class TeeExamInfoController {
	@Autowired
	TeeExamInfoService examInfoService;
	
	@Autowired
	private  TeeExamDataService   examDataService;
	
	@RequestMapping("/addExamInfo")
	@ResponseBody
	public TeeJson addExamInfo(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeExamInfoModel examInfoModel = new TeeExamInfoModel();
		TeeServletUtility.requestParamsCopyToObject(request, examInfoModel);
		examInfoService.addExamInfoModel(request,examInfoModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editExamInfo")
	@ResponseBody
	public TeeJson editExamInfo(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeExamInfoModel examInfoModel = new TeeExamInfoModel();
		TeeServletUtility.requestParamsCopyToObject(request, examInfoModel);
		examInfoService.updateExamInfoModel(examInfoModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	@RequestMapping("/stopExamInfo")
	@ResponseBody
	public TeeJson stopExamInfo(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeExamInfoModel examInfoModel = new TeeExamInfoModel();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamInfo examInfo = examInfoService.getById(sid);
		Calendar cl = Calendar.getInstance();
		examInfo.setEndTime(cl);
		examInfoService.updateExamInfo(examInfo);
		json.setRtMsg("终止成功");
		json.setRtState(true);
		return json;		
	}
	@RequestMapping("/delExamInfo")
	@ResponseBody
	public TeeJson delExamInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamInfo examInfo = examInfoService.getById(sid);
		examInfoService.deleteExamInfo(examInfo);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getExamInfo")
	@ResponseBody
	public TeeJson getExamInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeExamInfo examInfo = examInfoService.getById(sid);
		TeeExamInfoModel model = new TeeExamInfoModel();
		BeanUtils.copyProperties(examInfo, model);
		model.setPaperId(examInfo.getExamPaper().getSid());
		if(examInfo.getStartTime()!=null){
			model.setStartTimeDesc(formatter.format(examInfo.getStartTime().getTime()));
		}
		if(examInfo.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(examInfo.getEndTime().getTime()));
		}
		String attendUserIds = "";
		String attendUserNames = "";
		Set<TeePerson> attendUsers = examInfo.getParticipant();
		for(TeePerson p:attendUsers){
			attendUserIds+=p.getUuid()+",";
			attendUserNames+=p.getUserName()+",";
		}
		String subExaminerIds = "";
		String subExaminerNames = "";
		Set<TeePerson> subExaminers = examInfo.getSubExaminer();
		for(TeePerson p:subExaminers){
			subExaminerIds+=p.getUuid()+",";
			subExaminerNames+=p.getUserName()+",";
		}
		model.setSubExaminerIds(subExaminerIds);
		model.setSubExaminerNames(subExaminerNames);
		model.setAttendUserIds(attendUserIds);
		model.setAttendUserNames(attendUserNames);
		json.setRtData(model);
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return examInfoService.datagrid(dm, requestDatas);
	}
	@RequestMapping("/getMyExamInfo")
	@ResponseBody
	public TeeEasyuiDataGridJson getMyExamInfo(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return examInfoService.getMyExamInfo(dm, requestDatas);
	}
	/**
	 * 发布考试信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendExamInfo")
	@ResponseBody
	public TeeJson sendExamInfo(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		examInfoService.sendExamInfo(sid);
		json.setRtMsg("发布成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/isCommited")
	@ResponseBody
	public TeeJson isCommited(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int userId = TeeStringUtil.getInteger(request.getParameter("userId"), 0);
		int iscommited = examInfoService.isCommited(sid,userId);
		Map map = new HashMap();
		map.put("isCommited", iscommited);
		json.setRtData(map);
		json.setRtMsg("发布成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/isCheckUser")
	@ResponseBody
	public TeeJson isCheckUser(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePerson loginUser = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		boolean isCheckUser = examInfoService.isCheckUser(sid,loginUser);
		Map map = new HashMap();
		map.put("isCheckUser", isCheckUser);
		json.setRtData(map);
		json.setRtMsg("");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/getSingleExamList")
	@ResponseBody
	public TeeEasyuiDataGridJson getSingleExamList(TeeDataGridModel dm,HttpServletRequest request){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		requestDatas.put("sid",sid);
		return examInfoService.getSingleExamList(dm, requestDatas);
	}
	
	@RequestMapping("/exportExcel")
	public void dowload(HttpServletRequest request ,HttpServletResponse response) throws Exception{

		OutputStream ops = null;
		try {
			int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
			TeeExamInfo teif = examInfoService.selectScore(sid);// 根据试卷id
			String examName = teif.getExamName();
		
			response.setHeader("Cache-control", "private");
			response.setContentType("application/msexcel");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader(
					"Content-disposition",
					"attachment; filename=\""
							+ URLEncoder.encode(
									"导出[" +examName+ "].xls", "UTF-8")
							+ "\"");

			ops = response.getOutputStream();
		
		writeExcel(ops, teif);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			ops.close();
		}
	}
	
	public OutputStream writeExcel(OutputStream ops,TeeExamInfo teif) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建Excel工作表对象
		HSSFSheet sheet = workbook.createSheet("成绩表");// 创建一个(Sheet 导出全部的成绩信息,按成绩排序）工作表
	    HSSFSheet sheet1 = workbook.createSheet("成绩表1");// 创建一个(Sheet1导出全部的成绩信息，按部门 asc与成绩desc）工作表
	    
	    HSSFCellStyle style= workbook.createCellStyle();
	    HSSFFont fontStyle = workbook.createFont();
	    fontStyle.setFontName("宋体");    //设置字体样式
	    fontStyle.setFontHeightInPoints((short)10);      //设置字体高度
	    fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);	
	
	    style.setFont(fontStyle);
	    
		HSSFRow oneRow = sheet.createRow(0); // 第一行：创建一大标题行
		oneRow.setHeight((short) 0x190);
		
		HSSFCell cell = null;
		cell = oneRow.createCell(0);
		cell.setCellValue("考试名称");
		cell.setCellStyle(style);
		
		cell = oneRow.createCell(1);
		cell.setCellValue("参加考试人");
		cell.setCellStyle(style);
		
		cell = oneRow.createCell(2);
		cell.setCellValue("所在部门");
		cell.setCellStyle(style);
		
		cell = oneRow.createCell(3);
		cell.setCellValue("成绩");
		cell.setCellStyle(style);
		
		int i=0;
		List<TeeExamInfoModel> modelList=new ArrayList<TeeExamInfoModel>();
		/*List<TeeExamRecord> list= examInfoService.select(teif.getSid());*/
		Set<TeePerson> persons = teif.getParticipant();
		TeeExamInfoModel model =null;
		int realScore=0;
		for (TeePerson person : persons) {
			model = new TeeExamInfoModel();
			BeanUtils.copyProperties(teif, model);
			model.setOwnId(person.getUuid());
			model.setOwnName(person.getUserName());
			if(person.getDept()!=null){
				model.setOwnDeptName(person.getDept().getDeptName());
				model.setOwnDeptId(person.getDept().getUuid());
			}else{
				model.setOwnDeptName("");
				model.setOwnDeptId(0);
			}
			realScore=examDataService.getRealScore(teif.getSid(), person.getUuid());
		    model.setRealScore(realScore);
			modelList.add(model);
		}
		/**
		 * 根据分数倒序排列
		 */
	    Collections.sort(modelList, new Comparator<TeeExamInfoModel>(){  	   
	            public int compare(TeeExamInfoModel o1, TeeExamInfoModel o2) {   
	                if(o1.getRealScore() > o2.getRealScore()){  
	                    return -1;  
	                }  
	                if(o1.getRealScore() == o2.getRealScore()){  
	                    return 0;  
	                }  
	                return 1;  
	            }  
	      }); 
		
		
		for (TeeExamInfoModel m : modelList) {
				HSSFRow tworRow = sheet.createRow(i + 1); // 第2行是数据
				tworRow.setHeight((short) 0x190);
				tworRow.createCell(0).setCellValue(teif.getExamName());
				tworRow.createCell(1).setCellValue(m.getOwnName());
				tworRow.createCell(2).setCellValue(m.getOwnDeptName());
				tworRow.createCell(3).setCellValue(m.getRealScore());
				i++;
		}
				 
		 HSSFRow yiRow = sheet1.createRow(0); // 第一行：创建一大标题行
		 yiRow.setHeight((short) 0x190);
		 HSSFCell cell1 = null;// excel HSSFCell的格子单元     
			cell1 = yiRow.createCell(0);
			cell1.setCellValue("考试名称");
			cell1.setCellStyle(style);
			
			cell1 = yiRow.createCell(1);
			cell1.setCellValue("参加考试人");
			cell1.setCellStyle(style);
			
			cell1 = yiRow.createCell(2);
			cell1.setCellValue("所在部门");
			cell1.setCellStyle(style);
			
			cell1 = yiRow.createCell(3);
			cell1.setCellValue("成绩");
			cell1.setCellStyle(style);
					
		 int j=0;
		 //先根据部门排序  再根据分数排序
	     Collections.sort(modelList, new Comparator<TeeExamInfoModel>(){  	   
	            public int compare(TeeExamInfoModel o1, TeeExamInfoModel o2) {   
	                if(o1.getOwnDeptId() > o2.getOwnDeptId()){  
	                    return 1;  
	                }  
	                if(o1.getOwnDeptId() == o2.getOwnDeptId()){ 
	                	if(o1.getRealScore()>o2.getRealScore()){
	                		return -1;
	                	}
	                	if(o1.getRealScore()==o2.getRealScore()){
	                		return 0;
	                	}
	                    return 1;  
	                }  
	                return -1;  
	            }  
	       });
	     
	     for (TeeExamInfoModel m : modelList) {
				HSSFRow erRow = sheet1.createRow(j + 1); // 第2行是数据
				erRow.setHeight((short) 0x190);
				erRow.createCell(0).setCellValue(teif.getExamName());
				erRow.createCell(1).setCellValue(m.getOwnName());
				erRow.createCell(2).setCellValue(m.getOwnDeptName());
				erRow.createCell(3).setCellValue(m.getRealScore());
				j++;
		}
			/*List<TeeExamRecord> lists= examInfoService.selectbm(teif.getSid());
			 for (TeeExamRecord ter : lists) {
			HSSFRow erRow = sheet1.createRow(j + 1); // 第2行是数据
			erRow.setHeight((short) 0x190);
			erRow.createCell(0).setCellValue(ter.getTeeExamInfo().getExamName());
			erRow.createCell(1).setCellValue(ter.getParticipant().getUserName());
			erRow.createCell(2).setCellValue(ter.getParticipant().getDept().getDeptName());
			erRow.createCell(3).setCellValue(ter.getScore());
			j++;
			 }*/
			 
		 workbook.write(ops);
			ops.flush();

		return ops;
		
		
	}
	
}
