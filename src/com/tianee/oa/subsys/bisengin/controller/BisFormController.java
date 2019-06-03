package com.tianee.oa.subsys.bisengin.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.subsys.bisengin.bean.BisForm;
import com.tianee.oa.subsys.bisengin.model.BisFormModel;
import com.tianee.oa.subsys.bisengin.service.BisFormService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("bisFormController")
public class BisFormController {

	@Autowired
	private BisFormService bisFormService;
	
	/**
	 * 获取所有表格的列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTableList")
	public TeeJson getTableList(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<Map> tableList=bisFormService.getTableList();
		json.setRtData(tableList);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	
	
	/**
	 * 创建表单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addOrUpdateBisForm")
	public TeeJson addOrUpdateBisForm(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisFormModel model = new BisFormModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		bisFormService.addOrUpdateBisForm(model);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
		
	}
	
	/**
	 * 获取所有的bisForm的列表
	 * @param request
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getBisFormList")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		BisFormModel model = new BisFormModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		return bisFormService.datagrid(model, dm);
	}
	
	/**
	 * 删除表单 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteBisForm")
	public  TeeJson deleteBisForm(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisFormModel model = new BisFormModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		bisFormService.deleteBisForm(model);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
		
	}
	
	
	/**
	 * 根据表单主键  获取表单的详情
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFormInfoById")
	public TeeJson getFormInfoById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		BisFormModel model = new BisFormModel();
	    int sid=Integer.parseInt(request.getParameter("sid"));
		//TeeServletUtility.requestParamsCopyToObject(request, model);
		json.setRtData(bisFormService.getFormInfoById(sid));
		json.setRtState(true);
		return json;
		
	}
	
	/**
	 * 获取表格字段
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getFields")
	public TeeJson getFields(HttpServletRequest request){
		TeeJson json = new TeeJson();
	    int formId=Integer.parseInt(request.getParameter("sid"));
		json.setRtData(bisFormService.getFields(formId));
		json.setRtState(true);
		return json;
	}
	/**
	 * 表单设计器保存
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateForm")
    public TeeJson updateForm(HttpServletRequest request){
    	TeeJson json = new TeeJson();
	    int formId=Integer.parseInt(request.getParameter("sid"));
	    String content=request.getParameter("content");
	    bisFormService.updateForm(formId,content);
		json.setRtMsg("保存成功");
		json.setRtState(true);
		return json;
    }
	
	/**
	 * 获取所有的业务表单列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAllBisFormList")
	public TeeJson getBisFormList(HttpServletRequest request){
		TeeJson json = new TeeJson();
	    List<BisFormModel> list=bisFormService.getBisFormList();
		json.setRtData(list);
		json.setRtState(true);
		return json;
		
	}
	
	/**
	 * 控件显示格式
	 * @param request
	 * @return
	 */
	@RequestMapping("/ctrlShow")
	public void ctrlShow(HttpServletRequest request,HttpServletResponse response){
		String fn = TeeStringUtil.getString(request.getParameter("fn"));
		int width = 28;
		for(int i=0;i<fn.length();i++){
			width+=14;
		}
		
		int height = 18;
		// 定义图像buffer         
        BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);         
        Graphics2D g = buffImg.createGraphics();
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。         
        Font font = new Font("System", Font.PLAIN, 14);
        // 设置字体。         
        g.setFont(font);         
        // 画边框。         
        g.setColor(Color.BLACK);
        g.drawString("【"+fn+"】", 0, 14);
        
        // 禁止图像缓存。         
        response.setHeader("Pragma", "no-cache");         
        response.setHeader("Cache-Control", "no-cache");         
        response.setDateHeader("Expires", 0);         
        response.setContentType("image/jpeg");         
        // 将图像输出到Servlet输出流中。         
        ServletOutputStream sos;
		try {
			sos = response.getOutputStream();
			ImageIO.write(buffImg, "jpeg", sos);         
	        sos.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
                
	}
}
