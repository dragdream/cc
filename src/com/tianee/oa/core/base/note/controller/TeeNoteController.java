package com.tianee.oa.core.base.note.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.bean.TeeBaseStream;
import com.tianee.oa.core.base.note.model.TeeNoteModel;
import com.tianee.oa.core.base.note.service.TeeNoteService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;


/**
 * 
 * @author syl
 *
 */
@Controller
@RequestMapping("/noteManage")
public class TeeNoteController {
	@Autowired
	private TeeNoteService noteService;
	
	
	/**
	 * @author syl
	 * 新增或者更新
	 * @param request
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeNoteModel model) {
		TeeJson json = new TeeJson();
		json = noteService.addOrUpdate(request , model);
		return json;
	}
	
	/**
	 * 更新位置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateLoc")
	@ResponseBody
	public TeeJson updateLoc(HttpServletRequest request , TeeNoteModel model) {
		return noteService.updateLoc(request , model);
	}
	/**
	 * 更新便签位置和大小
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePosAndSize")
	@ResponseBody
	public TeeJson updatePosAndSize(HttpServletRequest request , TeeNoteModel model) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		int x = TeeStringUtil.getInteger(request.getParameter("x"), 0);
		int y = TeeStringUtil.getInteger(request.getParameter("y"), 0);
		int width = TeeStringUtil.getInteger(request.getParameter("width"), 0);
		int height = TeeStringUtil.getInteger(request.getParameter("height"), 0);
		String color = TeeStringUtil.getString(request.getParameter("color"));
		noteService.updatePosAndSize(sid,x,y,width,height,color);
		return json;
	}
	
	/**
	 * ById
	 * @author syl
	 * @date 2014-2-13
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeNoteModel model) {
		TeeJson json = new TeeJson();
		json = noteService.getById(request , model);
		return json;
	}
	
	/**
	 * 个人桌面
	 * @author syl
	 * @date 2014-2-13
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectPersonalNote")
	@ResponseBody
	public TeeJson selectPersonalNote(HttpServletRequest request , TeeNoteModel model) {
		TeeJson json = new TeeJson();
		json = noteService.selectPersonalNote(request , model);
		return json;
	}
	
	/**
	 * 删除便签
	 * @param request
	 * @return
	 */
	@RequestMapping("/del")
	@ResponseBody
	public TeeJson del(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		noteService.del(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除便签
	 * @param request
	 * @return
	 */
	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request, TeeNoteModel model,HttpServletResponse response) {
		TeeJson json = new TeeJson();
		json = noteService.selectPersonalNote(request , model);
		
		List<TeeNoteModel> listModel = (List<TeeNoteModel>) json.getRtData();
		
		OutputStream ops = null;
		
		StringBuffer content = new StringBuffer();
		for(int i=0;i<listModel.size();i++){
			content.append(listModel.get(i).getCreateTimeStr()+"\r\n");
			content.append(listModel.get(i).getContent());
			if(i!=listModel.size()-1){
				content.append("\r\n\r\n\r\n--------------------------\r\n\r\n\r\n");
			}
		}
		
		byte b[] = content.toString().getBytes();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		
		try {
			response.setHeader("Cache-control", "private");
			response.setContentType("application/octet-stream");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader("Accept-Length", String.valueOf(b.length));
			response.setHeader("Content-Length", String.valueOf(b.length));
			response.setHeader("Content-disposition", "attachment; filename=\""
					+ URLEncoder.encode("便签"+sdf.format(new Date())+".txt", "UTF-8") + "\"");
			ops = response.getOutputStream();
			ops.write(b);
			ops.flush();
			
			
		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
		} finally {
			
		}
	}
}
