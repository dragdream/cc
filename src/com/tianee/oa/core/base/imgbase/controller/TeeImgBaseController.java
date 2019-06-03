package com.tianee.oa.core.base.imgbase.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.imgbase.model.TeeImgBaseModel;
import com.tianee.oa.core.base.imgbase.service.TeeImgBaseService;
import com.tianee.oa.core.base.imgbase.utility.Base64Private;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/teeImgBaseController")
public class TeeImgBaseController {
	@Autowired
	TeeImgBaseService imgService;
	@Autowired
	@Qualifier("teeBaseUpload")
	private TeeBaseUpload upload;
	@Autowired
	private TeeAttachmentService attachmentService;
	
	@RequestMapping("/addImgBase")
	@ResponseBody
	public TeeJson addImgBase(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeImgBaseModel imgBaseModel = new TeeImgBaseModel();
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, imgBaseModel);
		imgService.addImgBase(imgBaseModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editImgBase")
	@ResponseBody
	public TeeJson editImgBase(HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		TeeImgBaseModel imgBaseModel = new TeeImgBaseModel();
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, imgBaseModel);
		imgService.editImgBase(imgBaseModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delImgBase")
	@ResponseBody
	public TeeJson delImgBase(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeImgBaseModel imgBaseModel = new TeeImgBaseModel();
		imgBaseModel.setSid(sid);
		imgService.delImgBase(imgBaseModel);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/getImgBase")
	@ResponseBody
	public TeeJson getImgBase(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeeImgBaseModel model = imgService.getById(sid);
		json.setRtData(model);
		json.setRtMsg("获取成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return imgService.datagrid(dm, requestDatas);
	}
	


	/**
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping("/getImgBaseTree")
    @ResponseBody
    public TeeJson getImgBaseTree(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        String isManager = TeeStringUtil.getString(request.getParameter("isManager"), "0");
        json = imgService.getImgBaseTree(person,id,isManager);
        return json;
    }
    
    
	/**
	 * 
	 * @param request
	 * @return
	 */
    @RequestMapping("/getPictureList")
    @ResponseBody
    public TeeJson getPictureList(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        String id = TeeStringUtil.getString(request.getParameter("id"), "");
        String sortType = TeeStringUtil.getString(request.getParameter("sortType"), "");
        int curPage = TeeStringUtil.getInteger(request.getParameter("curPage"), 0);
        int pageSize = TeeStringUtil.getInteger(request.getParameter("pageSize"), 15);
        json = imgService.getPictureList(person,id,sortType,pageSize,curPage);
        return json;
    }
    
    
    /**
     * 图片输出
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/loadImage")
    @ResponseBody
	public void loadImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String filePath = TeeStringUtil.getString(request.getParameter("filePath"), "");
		InputStream in = new FileInputStream(new File(new String(Base64Private.decode(filePath.getBytes()))));
		BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
		try {
			byte b[] = new byte[1024];
			int len = in.read(b);
			while (len > 0) {
				bout.write(b, 0, len);
				len = in.read(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bout.close();
			in.close();
		}
	}
    
    /**
     * 附件处理
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/dealAttach")
    @ResponseBody
    public TeeJson dealAttach(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TeeJson json = new TeeJson();
    	String attachIds = TeeStringUtil.getString(request.getParameter("attachIds"), "");
    	String sid = TeeStringUtil.getString(request.getParameter("sid"), "");
    	json = imgService.dealAttach(attachIds,sid);
    	return json;
    }
    
    
    /**
     * 图片输出
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/zipDownFile")
    @ResponseBody
    public void zipDownFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	String paths = TeeStringUtil.getString(request.getParameter("paths"), "");
    	Calendar cur = Calendar.getInstance();
    	String strZipName=TeeUtility.getDateTimeStr(cur.getTime(),new SimpleDateFormat("yyyyMMddhhmmss"))+".zip";
    	OutputStream ops = null;
    	response.setHeader("Cache-control", "private");
    	response.setContentType("application/octet-stream");
    	response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Cache-Control", "maxage=3600");
		response.setHeader("Pragma", "public");
		response.setHeader("Content-disposition", "attachment; filename=\""+ URLEncoder.encode(strZipName, "UTF-8") + "\"");
		ops = response.getOutputStream();
    	ZipOutputStream out = new ZipOutputStream(ops); 
    	byte[] buffer = new byte[1024];
    	if(!TeeUtility.isNullorEmpty(paths)){
    		if(paths.endsWith(",")){
    			paths = paths.substring(0,paths.length()-1);
    		}
    		String[] pathList=paths.split(",");
    		for(String path:pathList){
    			File file = new File(new String(Base64Private.decode(path.getBytes())));
    			FileInputStream fis = new FileInputStream(file);
    			out.putNextEntry(new ZipEntry(file.getName()));
    		    int len;
    			while((len = fis.read(buffer))>0) {
    				out.write(buffer,0,len);
    			}
    			out.closeEntry();
    			fis.close();
    		}
    	}
    	out.flush();
    	out.close();
    }
    
    /**
     * 图片输出
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/deleteAll")
    @ResponseBody
    public TeeJson deleteAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TeeJson json = new TeeJson();
    	String paths = TeeStringUtil.getString(request.getParameter("paths"), "");
    	json = imgService.deleteAll(paths);
    	return json;
    }
    
    
    /**
     * 新建文件夹
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/createFolder")
    @ResponseBody
	public TeeJson createFolder(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TeeJson json = new TeeJson();
		String sid = TeeStringUtil.getString(request.getParameter("sid"), "");
		String folderName = TeeStringUtil.getString(request.getParameter("folderName"), "");
		String filePath="";
		if(imgService.isNumeric(sid)){
			TeeImgBaseModel model = imgService.getById(Integer.parseInt(sid));
			filePath=model.getImgDir()+"/"+folderName;
		}else{
			filePath=new String(Base64Private.decode(sid.getBytes()))+"/"+folderName;
		}
		File file = new File(filePath);
		if(!file.exists()){
			file.mkdir();
		}
		json.setRtMsg("文件夹新建成功！");
		json.setRtState(true);
		return json;
	}
    /**
     * 删除文件夹
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/deleteFolder")
    @ResponseBody
    public TeeJson deleteFolder(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TeeJson json = new TeeJson();
    	String sid = TeeStringUtil.getString(request.getParameter("sid"), "");
    	String filePath="";
    	if(!imgService.isNumeric(sid)){
    		filePath=new String(Base64Private.decode(sid.getBytes()));
    	}
    	File file = new File(filePath);
    	TeeFileUtility.deleteAll(file);
    	json.setRtMsg("文件夹删除成功！");
    	json.setRtState(true);
    	return json;
    }
    
    /**
     * 判断是否有权限
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/isPriv")
    @ResponseBody
    public TeeJson isPriv(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TeeJson json = new TeeJson();
    	String rootId = TeeStringUtil.getString(request.getParameter("rootId"), "");
    	String privType = TeeStringUtil.getString(request.getParameter("privType"), "");
    	TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
    	if(!TeeUtility.isNullorEmpty(rootId)){
    		TeeImgBaseModel model = imgService.getById(Integer.parseInt(rootId));
    		if(!TeeUtility.isNullorEmpty(privType) && privType.equals("upload")){
    			if(imgService.hasPriv(String.valueOf(person.getUuid()), model.getUploadUserIds()) || imgService.hasPriv(String.valueOf(person.getDept().getUuid()), model.getUploadDeptIds()) || imgService.hasPriv(String.valueOf(person.getUserRole().getUuid()), model.getUploadRoleIds())){
    				json.setRtData(true);
    			}else{
    				json.setRtData(false);
    			}
    		}
    		if(!TeeUtility.isNullorEmpty(privType) && privType.equals("manager")){
    			if(imgService.hasPriv(String.valueOf(person.getUuid()), model.getManagerUserIds()) || imgService.hasPriv(String.valueOf(person.getDept().getUuid()), model.getManagerDeptIds()) || imgService.hasPriv(String.valueOf(person.getUserRole().getUuid()), model.getManagerRoleIds())){
    				json.setRtData(true);
    			}else{
    				json.setRtData(false);
    			}
    		}
    		json.setRtMsg("获取权限成功");
        	json.setRtState(true);
        	
    	}else{
    		json.setRtMsg("权限判断失败！");
        	json.setRtState(false);
    	}
    	return json;
    }

    
    /**
     * 复制文件
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/copyOrCutFiles")
    @ResponseBody
    public TeeJson copyOrCutFiles(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TeeJson json = new TeeJson();
    	String sid = TeeStringUtil.getString(request.getParameter("sid"), "");//目标sid
    	String paths  =  TeeStringUtil.getString(request.getParameter("paths"),"");
    	String type  =  TeeStringUtil.getString(request.getParameter("type"),"");
    	String filePath="";
    	if(imgService.isNumeric(sid)){
    		TeeImgBaseModel model = imgService.getById(Integer.parseInt(sid));
			filePath=model.getImgDir();
    	}else{
    		filePath=new String(Base64Private.decode(sid.getBytes()));
    	}
    	imgService.copyOrCutFiles(filePath,paths,type);
    	json.setRtMsg("文件夹删除成功！");
    	json.setRtState(true);
    	return json;
    }
}
