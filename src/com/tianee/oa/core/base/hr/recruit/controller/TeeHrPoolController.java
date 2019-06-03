package com.tianee.oa.core.base.hr.recruit.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.hr.recruit.model.TeeHrPoolModel;
import com.tianee.oa.core.base.hr.recruit.service.TeeHrPoolService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/hrPoolController")
public class TeeHrPoolController {
	@Autowired 
	private TeeHrPoolService hrPoolService;


	/**
	 * 新增或者更新
	 * 
	 * @throws ParseException
	 * @throws IOException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request)
			throws ParseException, IOException {
		TeeJson json = new TeeJson();
		TeeHrPoolModel model = new TeeHrPoolModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = hrPoolService.addOrUpdate(request, model);
		return json;
	}

	/**
	 * 人才库管理界面
	 * 
	 * @date 2014-3-18
	 * @author
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 * @throws java.text.ParseException
	 */
	@RequestMapping("/getHrPoolList")
	@ResponseBody
	public TeeEasyuiDataGridJson getHrPoolList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException{
		TeeHrPoolModel model = new TeeHrPoolModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return hrPoolService.getHrPoolList(dm, request, model);
	}
	
	/**
	 * 查询
	 * @author syl
	 * @date 2014-6-22
	 * @param dm
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/queryHrPoolList")
	@ResponseBody
	public TeeEasyuiDataGridJson queryHrPoolList(TeeDataGridModel dm,
			HttpServletRequest request) throws ParseException{
		TeeHrPoolModel model = new TeeHrPoolModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return hrPoolService.queryHrPoolList(dm, request, model);
	}
	
	 /**
     * 根据sid查看详情
     * @date 2014-3-8
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getInfoById")
    @ResponseBody
    public TeeJson getInfoById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeHrPoolModel model = new TeeHrPoolModel();
        // 将request中的对应字段映值射到目标对象的属性中
        TeeServletUtility.requestParamsCopyToObject(request, model);
        json = hrPoolService.getInfoByIdService(request, model);
        return json;
    }
	
	
    /**
     * 删除
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteObjById")
    @ResponseBody
    public TeeJson deleteObjById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sidStr = request.getParameter("sids");
        json = hrPoolService.deleteObjByIdService(sidStr);
        return json;
    }


    /**
     * 发送   或者取消offer
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/sendoffer")
    @ResponseBody
    public TeeJson sendoffer(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sidStr = request.getParameter("sids");
        String  type =  TeeStringUtil.getString(request.getParameter("type"), "0");
        json = hrPoolService.sendoffer(sidStr , type);
        return json;
    }
    
    /**
     * 选择人才库
     * @author syl
     * @date 2014-6-27
     * @param request
     * @return
     */
    @RequestMapping("/selectPool")
    @ResponseBody
    public TeeJson selectPool(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeeHrPoolModel model = new TeeHrPoolModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
        json = hrPoolService.selectPool(request ,model);
        return json;
    }

    
    
    
	@RequestMapping("/downImportTemplate")
	@ResponseBody
	public void downLoadPdTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//计划编号、姓名、性别、出生日期、民族、居住城市、联系电话、email、户口所在地、婚姻状况、政治面貌、期望薪资、年龄、所学专业、学历、学位、简历
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("系统日志");
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 12); //字体高度
        font.setColor(HSSFFont.COLOR_NORMAL); //字体颜色
        font.setFontName("宋体"); //字体
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(false); //是否使用斜体
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		style.setFont(font);
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("计划编号");
		cell.setCellStyle(style);
		cell = row.createCell((short) 1);
		cell.setCellValue("姓名");
		cell.setCellStyle(style);
		cell = row.createCell((short) 2);
		cell.setCellValue("性别");
		cell.setCellStyle(style);
		cell = row.createCell((short) 3);
		cell.setCellValue("年龄");
		cell.setCellStyle(style);
		cell = row.createCell((short) 4);
		cell.setCellValue("出生日期");
		cell.setCellStyle(style);
		cell = row.createCell((short) 5);
		cell.setCellValue("民族");
		cell.setCellStyle(style);
		cell = row.createCell((short) 6);
		cell.setCellValue("现居住城市");
		cell.setCellStyle(style);
		cell = row.createCell((short) 7);
		cell.setCellValue("联系电话");
		cell.setCellStyle(style);
		cell = row.createCell((short) 8);
		cell.setCellValue("email");
		cell.setCellStyle(style);
		cell = row.createCell((short) 9);
		cell.setCellValue("户口所在地");
		cell.setCellStyle(style);
		cell = row.createCell((short) 10);
		cell.setCellValue("婚姻状况");
		cell.setCellStyle(style);
		cell = row.createCell((short) 11);
		cell.setCellValue("政治面貌");
		cell.setCellStyle(style);
		cell = row.createCell((short) 12);
		cell.setCellValue("期望薪资");
		cell.setCellStyle(style);
		cell = row.createCell((short) 13);
		cell.setCellValue("所学专业");
		cell.setCellStyle(style);
		cell = row.createCell((short) 14);
		cell.setCellValue("学历");
		cell.setCellStyle(style);
		cell = row.createCell((short) 15);
		cell.setCellValue("学位");
		cell.setCellStyle(style);
		cell = row.createCell((short) 16);
		cell.setCellValue("简历内容");
		cell.setCellStyle(style);
		
		//设置每一列的宽度
        sheet.setDefaultColumnWidth(10);
		String fileName = "人才库导入模板.xls";
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setContentType("application/msexcel;charset=UTF-8");
		OutputStream out=response.getOutputStream();
		wb.write(out);
		out.close();
	}
	
	
	/**
	 * 人才库批量导入
	 */
	@RequestMapping("/importHrPool")
	@ResponseBody
	public TeeJson importHumanDocInfo(HttpServletRequest request) throws Exception{
		TeeJson json = hrPoolService.importHrPool(request);
		return json;
	}
	
	
	
	
	/**
	 * 以Email的形式发送offer
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendEmail")
	@ResponseBody
	public TeeJson sendEmail(HttpServletRequest request) throws Exception{
		TeeJson json = hrPoolService.sendEmail(request);
		return json;
	}
}
