package com.tianee.oa.core.base.fileNetdisk.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 公共网盘
 * 
 * @author wyw
 * 
 */
@Controller
@RequestMapping("/fileNetdisk")
public class TeeFileNetdiskController extends BaseController {
	@Autowired
	private TeeFileNetdiskService fileNetdiskService;

	/**
	 * 新建文件网盘
	 * 
	 * @date 2014-1-4
	 * @author
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdateFileNetdisk")
	@ResponseBody
	public TeeJson addOrUpdateFileNetdisk(HttpServletRequest request, TeeFileNetdiskModel model) {
		TeeJson json = new TeeJson();
		try {
			TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			int sid = TeeStringUtil.getInteger(model.getSid(), 0);
			if (sid > 0) {
				json = fileNetdiskService.updateFileNetdiskService(model);
			} else {
				json = fileNetdiskService.addFileNetdiskService(person, model);
			}
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 获取文件夹目录
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileNetdiskList")
	@ResponseBody
	public TeeJson getFileNetdiskList(HttpServletRequest request) {
		TeeJson json = new TeeJson();

		/*
		 * int fileNo = TeeStringUtil.getInteger(request.getParameter("fileNo"),
		 * 0); String fileName = request.getParameter("fileName");
		 */
		json = fileNetdiskService.getFileNetdiskListServbice();
		return json;
	}

	/**
	 * 获取文件网盘列表,有权限（一级）
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRootPrivFolderList")
	@ResponseBody
	public TeeJson getRootPrivFolderList(HttpServletRequest request) {
		int menuSid = TeeStringUtil.getInteger(request.getParameter("menuSid"), 0);
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = fileNetdiskService.getRootPrivFolderList(person, menuSid);
		return json;
	}

	/**
	 * 获取文件网盘信息
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileNetdiskById")
	@ResponseBody
	public TeeJson getFileNetdiskById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestMap.put(TeeConst.LOGIN_USER, person);
		json = fileNetdiskService.getFileNetdiskByIdServbice(requestMap);
		return json;
	}

	/**
	 * 删除文件网盘信息
	 * 
	 * @date 2014-1-5
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/delFileNetdiskByIds")
	@ResponseBody
	public TeeJson delFileNetdiskByIds(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sid");
		json = fileNetdiskService.deleteFileBySid(sidStr);
		return json;
	}

	/**
	 * 上传附件
	 * 
	 * @date 2014-2-13
	 * @author
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/uploadNetdiskFile")
	@ResponseBody
	public TeeJson uploadNetdiskFile(HttpServletRequest request) throws IOException {
		TeeJson json = new TeeJson();
		String sidStr = request.getParameter("sid");
		String attachSids = request.getParameter("valuesHolder");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        String rootFolderPriv=request.getParameter("rootFolderPriv");
        String isRemind=request.getParameter("isRemind");
        int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		json = fileNetdiskService.uploadNetdiskFileServbice(sidStr, attachSids, rootFolderPriv,isRemind,person,taskId);
		return json;
	}

	/**
	 * 获取文件/文件夹通用个列表
	 * 
	 * @date 2014-2-13
	 * @author
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFileNetdiskPage")
	@ResponseBody
	public TeeEasyuiDataGridJson getFileNetdiskPage(TeeDataGridModel dm, HttpServletRequest response) {
		return fileNetdiskService.getFileNetdiskPage(dm, response);
	}
	
	
	
	/**
	 * 根据当前登陆人和文件夹主键   获取文件夹下文件和文件夹的集合
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFileNetdiskListByFolderIdAndUser")
	@ResponseBody
	public TeeJson getFileNetdiskPage( HttpServletRequest request) {
		return fileNetdiskService.getFileNetdiskListByFolderIdAndUser(request);
	}
	
	
	/**
	 * 获取文件/文件夹通用个列表
	 * 
	 * @date 2014-2-13
	 * @author
	 * @param dm
	 * @param portal
	 * @param response
	 * @return
	 */
	@RequestMapping("/getFileNetdiskPage2")
	@ResponseBody
	public TeeEasyuiDataGridJson getFileNetdiskPage2(TeeDataGridModel dm, HttpServletRequest response) {
		return fileNetdiskService.getFileNetdiskPage2(dm, response);
	}
	
	/**
	 * 新建文件夹(二级)
	 * 
	 * @date 2014-2-14
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/newFileFolderById")
	@ResponseBody
	public TeeJson newFileFolderById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String folderName = request.getParameter("folderName");
		json = fileNetdiskService.newFileFolderById(sid, folderName, person);
		return json;
	}

	/**
	 * 重命名文件夹
	 * 
	 * @date 2014-2-14
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/reNameFolderById")
	@ResponseBody
	public TeeJson reNameFolderById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String folderName = request.getParameter("folderName");
		json = fileNetdiskService.reNameFolderById(sid, folderName, person);
		return json;
	}

	/**
	 * 重命名文件
	 * 
	 * @date 2014-2-14
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/reNameFileById")
	@ResponseBody
	public TeeJson reNameFileById(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String fileName = request.getParameter("folderName");
		json = fileNetdiskService.reNameFileByIdService(sid, fileName, person);
		return json;
	}

	/**
	 * 获取文件夹完整级别目录
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFolderPathBySid")
	@ResponseBody
	public TeeJson getFolderPathBySid(HttpServletRequest request) {
		TeeJson json = new TeeJson();

		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = fileNetdiskService.getFolderPathBySid(sid);
		return json;
	}

	/**
	 * 删除目录及文件(级联删除)
	 * 
	 * @date 2014-2-15
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteFileBySid")
	@ResponseBody
	public TeeJson deleteFileBySid(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String sids = request.getParameter("sids");
		json = fileNetdiskService.deleteFileBySid(sids);
		return json;
	}

	/**
	 * 获取文件权限
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFilePrivValueBySid")
	@ResponseBody
	public TeeJson getFilePrivValueBySid(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json = fileNetdiskService.getFilePrivValueBySid(sid, person);
		return json;
	}

	/**
	 * 打包zip文件下载
	 * 
	 * @date 2014-2-16
	 * @author
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downFileToZipBySid")
	@ResponseBody
	public String downFileToZipBySid(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
		String sids = request.getParameter("sids");

		// InputStream is = null;
		OutputStream ops = null;
		try {
			String zipFileName = "批量下载";
			TeeFileNetdisk rootFileNetdisk = fileNetdiskService.getFileNetdiskObjById(folderSid);
			if (rootFileNetdisk != null) {
				zipFileName = rootFileNetdisk.getFileName();
			}

			String fileNameStr = URLEncoder.encode(zipFileName + ".zip", "UTF-8");
			response.setHeader("Accept-Ranges", "bytes");
			response.setHeader("Cache-Control", "maxage=3600");
			response.setHeader("Pragma", "public");
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileNameStr + "\"");

			ops = response.getOutputStream();
			fileNetdiskService.downFileToZipBySidService(ops, zipFileName, sids);

			/*
			 * is = new FileInputStream(zipFilePath); if (is != null) { byte[]
			 * buff = new byte[8192]; int byteread = 0; while((byteread =
			 * is.read(buff))!=-1){ ops.write(buff,0,byteread); ops.flush(); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			// if (is != null) {
			// is.close();
			// }
			// TeeFileUtility.deleteAll(zipFilePath);
		}
		return null;
	}

	/**
	 * 获取文件目录树（复制、剪贴目录树）
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param request
	 * @return
	 */
	@RequestMapping("/getAllFolderTree")
	@ResponseBody
	public TeeJson getAllFolderTree(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String optionFlag = request.getParameter("optionFlag");
		String seleteSid = request.getParameter("seleteSid");
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = fileNetdiskService.getAllFolderTreeService(person, optionFlag, seleteSid);
		return json;
	}

	/**
	 * 复制文件到其他目录
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/copyFileToFolder")
	@ResponseBody
	public TeeJson copyFileToFolder(HttpServletRequest request) throws IOException {
		String fileIds = request.getParameter("fileIds");
		int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);

		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = fileNetdiskService.copyFileToFolderService(person, folderSid, fileIds);
		return json;
	}

	/**
	 * 剪贴文件到其他目录
	 * 
	 * @date 2014-2-20
	 * @author
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/cutFileToFolder")
	@ResponseBody
	public TeeJson cutFileToFolder(HttpServletRequest request) throws IOException {
		String fileIds = request.getParameter("fileIds");
		int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);

		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		json = fileNetdiskService.cutFileToFolderService(person, folderSid, fileIds);
		return json;
	}

	/**
	 * 根据名称关键字检索公共网盘
	 * 
	 * @date 2014-3-2
	 * @author
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/queryFileNetdiskByName")
	@ResponseBody
	public TeeJson queryFileNetdiskByName(HttpServletRequest request) throws IOException {
		String fileName = TeeUtility.null2Empty(request.getParameter("fileName"));
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json = fileNetdiskService.queryFileNetdiskByName(person, fileName);
		return json;
	}

	/**
	 * 搜索某目录下的文件
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年3月29日
	 * @param dm
	 * @param request
	 * @return
	 * @throws java.text.ParseException
	 *             TeeEasyuiDataGridJson
	 */
	@RequestMapping("/getManageInfoList")
	@ResponseBody
	public TeeEasyuiDataGridJson getManageInfoList(TeeDataGridModel dm, HttpServletRequest request) throws java.text.ParseException {
		TeeFileNetdiskModel model = new TeeFileNetdiskModel();
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		return fileNetdiskService.getManageInfoList(dm, requestMap, loginPerson);
	}

	/**
	 * 获取公共文件夹目录树
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月20日
	 * @param request
	 * @return TeeJson
	 */
	@RequestMapping("/getNetdiskFolderTree")
	@ResponseBody
	public TeeJson getNetdiskFolderTree(HttpServletRequest request) {

		TeeFileNetdiskModel model = new TeeFileNetdiskModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		Map requestMap = TeeServletUtility.getParamMap(request);
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		requestMap.put(TeeConst.LOGIN_USER, person);

		TeeJson json = new TeeJson();
		json = fileNetdiskService.getNetdiskFolderTree(requestMap, model);
		return json;
	}

	/**
	 * 更新文件备注
	 * 
	 * @function:
	 * @author: wyw
	 * @data: 2015年7月21日
	 * @param request
	 * @return
	 * @throws ParseException
	 *             TeeJson
	 */
	@RequestMapping("/updateContent")
	@ResponseBody
	public TeeJson updateContent(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeFileNetdiskModel model = new TeeFileNetdiskModel();

		// 将request中的对应字段映值射到目标对象的属性中
		TeeServletUtility.requestParamsCopyToObject(request, model);
		json = fileNetdiskService.updateContent(request, model);
		return json;
	}
	@RequestMapping("/updateAutoIndex")
	@ResponseBody
	public TeeJson updateAutoIndex(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeFileNetdiskModel tfnm =new TeeFileNetdiskModel();
		TeeServletUtility.requestParamsCopyToObject(request, tfnm);
		fileNetdiskService.updateAutoIndex(tfnm);
		json.setRtState(true);
		return json;
			
	}

	@RequestMapping("/updateAutoIndexgb")
	@ResponseBody
	public TeeJson updateAutoIndexgb(HttpServletRequest request) throws ParseException {
		TeeJson json = new TeeJson();
		TeeFileNetdiskModel tfnm=new TeeFileNetdiskModel();
		TeeServletUtility.requestParamsCopyToObject(request, tfnm);
		fileNetdiskService.updateAutoIndexgb(tfnm);
		json.setRtState(true);
		return json;
			
	}
	
	/**
     * 新建公共网盘目录
     * 
     * @date 2014-2-14
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/newSubFolderById")
    @ResponseBody
    public TeeJson newSubFolderById(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        
        int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
        String folderName = request.getParameter("folderName");
        json = fileNetdiskService.newSubFolderByIdService(sid, folderName, person);
        return json;
    }
    
    /**
     * 添加星
     * */
    @ResponseBody
    @RequestMapping("/addPicCount")
    public TeeJson addPicCount(HttpServletRequest request){
    	return fileNetdiskService.addPicCount(request);
    }
    /**
     * 删除星
     * */
    @ResponseBody
    @RequestMapping("/deletePicCount")
    public TeeJson deletePicCount(HttpServletRequest request){
    	return fileNetdiskService.deletePicCount(request);
    }
    
    /**
     * 删除星
     * */
    @ResponseBody
    @RequestMapping("/updatePicCount")
    public TeeJson updatePicCount(HttpServletRequest request){
    	return fileNetdiskService.updatePicCount(request);
    }
}
