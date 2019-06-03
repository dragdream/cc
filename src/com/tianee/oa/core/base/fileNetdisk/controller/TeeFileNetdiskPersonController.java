package com.tianee.oa.core.base.fileNetdisk.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskPersonService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 个人网盘
 * @author wyw
 *
 */
@Controller
@RequestMapping("/fileNetdiskPerson")
public class TeeFileNetdiskPersonController extends BaseController {
    @Autowired
    private TeeFileNetdiskPersonService fileNetdiskPersonService;


    /**
     * 获取个人文件夹目录树
     * @date 2014-2-21
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getPersonFolderTree")
    @ResponseBody
    public TeeJson getPersonFolderTree(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = fileNetdiskPersonService.getPersonFolderTreeService(person);
        return json;
    }
    
    /**
     * 获取共享目录树
     * @date 2014-2-21
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getShareFolderTree")
    @ResponseBody
    public TeeJson getShareFolderTree(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = fileNetdiskPersonService.getShareFolderTreeService(person);
        return json;
    }
    
   
    
    /**
     * 获取手机端共享网盘
     * @param request
     * @return
     */
    @RequestMapping("/getMobileShareFolderTree")
    @ResponseBody
    public TeeJson getMobileShareFolderTree(HttpServletRequest request) {
        return  fileNetdiskPersonService.getMobileShareFolderTree(request);
    }
    
    
    
    /**
     * 获取个人文件/文件夹通用个列表
     * @date 2014-2-21
     * @author 
     * @param dm
     * @param response
     * @return
     */
    @RequestMapping("/getPersonFilePage")
    @ResponseBody
    public TeeEasyuiDataGridJson getPersonFilePage(TeeDataGridModel dm, HttpServletRequest response) {
        return fileNetdiskPersonService.getPersonFilePageService(dm, response);
    }
    
    
    
    /**
     * 新建个人目录
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
        json = fileNetdiskPersonService.newSubFolderByIdService(sid, folderName, person);
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
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
        json = fileNetdiskPersonService.getFolderPathBySidService(sid,person);
        return json;
    }
    
    /**
     * 获取文件夹完整级别目录（个人文件柜共享）
     * 
     * @date 2014-2-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/getShareFolderPathBySid")
    @ResponseBody
    public TeeJson getShareFolderPathBySid(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
        json = fileNetdiskPersonService.getShareFolderPathBySidService(sid,person);
        return json;
    }
    
    
    
    /**
     * 上传附件
     * @date 2014-2-21
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

        json = fileNetdiskPersonService.uploadNetdiskFileServbice(sidStr, attachSids, person);
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
        int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
        json = fileNetdiskPersonService.getFileNetdiskByIdServbice(sid);
        return json;
    }

    /**
     * 编辑文件夹
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
        json = fileNetdiskPersonService.reNameFolderById(sid, folderName, person);
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
        json = fileNetdiskPersonService.reNameFileByIdService(sid, fileName, person);
        return json;
    }
    
    
    /**
     * 删除目录及文件(级联删除)
     * @date 2014-2-22
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/deleteFileBySid")
    @ResponseBody
    public TeeJson deleteFileBySid(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        String sids = request.getParameter("sids");
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = fileNetdiskPersonService.deleteFileBySid(sids,person);
        return json;
    }


    /**
     * 获取个人文件目录树（复制、剪贴目录树）
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
        json = fileNetdiskPersonService.getAllFolderTreeService(person,optionFlag,seleteSid);
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
        String fileIds = request.getParameter("fileIds");//拷贝目录id串
        int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);//目标id

        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        json = fileNetdiskPersonService.cutFileToFolderService(person, folderSid, fileIds);
        return json;
    }
    
    

    /**
     * 打包zip文件下载
     * @date 2014-2-22
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
        OutputStream ops = null;
        try {
            TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
            String zipFileName = "个人文件批量下载";
            TeeFileNetdisk rootFileNetdisk = fileNetdiskPersonService.getFileNetdiskObjById(folderSid);
            if (rootFileNetdisk != null) {
                zipFileName = TeeUtility.null2Empty(rootFileNetdisk.getFileName());
            }
            
            String fileNameStr = URLEncoder.encode(zipFileName + ".zip", "UTF-8");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Cache-Control", "maxage=3600");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileNameStr + "\"");

            ops = response.getOutputStream();
            fileNetdiskPersonService.downFileToZipBySidService(ops, zipFileName, sids,person);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return null;
    }
    
    
    
    /**
     * 获取个人文件/文件夹通用个列表（共享文件柜）
     * @date 2014-2-23
     * @author 
     * @param dm
     * @param response
     * @return
     */
    @RequestMapping("/getPersonFileSharePage")
    @ResponseBody
    public TeeEasyuiDataGridJson getPersonFileSharePage(TeeDataGridModel dm, HttpServletRequest response) {
        return fileNetdiskPersonService.getPersonFileSharePageService(dm, response);
    }
    
    
    /**
     * 获取文件权限（共享文件柜）
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
        json = fileNetdiskPersonService.getFilePrivValueBySid(sid, person);
        return json;
    }
    
    

    /**
     * 打包zip文件下载（共享文件柜）
     * @date 2014-2-22
     * @author 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/downFileShareToZipBySid")
    @ResponseBody
    public String downFileShareToZipBySid(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int folderSid = TeeStringUtil.getInteger(request.getParameter("folderSid"), 0);
        
        
        String sids = request.getParameter("sids");
        OutputStream ops = null;
        try {
            String zipFileName = "个人文件批量下载";
            TeeFileNetdisk rootFileNetdisk = fileNetdiskPersonService.getFileNetdiskObjById(folderSid);
            if (rootFileNetdisk != null) {
                zipFileName = TeeUtility.null2Empty(rootFileNetdisk.getFileName());
            }
            
            
            String fileNameStr = URLEncoder.encode(zipFileName + ".zip", "UTF-8");
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Cache-Control", "maxage=3600");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileNameStr + "\"");

            ops = response.getOutputStream();
            fileNetdiskPersonService.downFileShareToZipBySidService(ops, zipFileName, sids);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
        }
        return null;
    }
    
    /**
     * 复制文件到其他目录（个人文件柜）
     * @date 2014-2-24
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
        json = fileNetdiskPersonService.copyFileToFolderService(person, folderSid, fileIds);
        return json;
    }
    
    
    
    
    
    /**
     * 获取模块文件使用大小
     * @date 2014-3-1
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getModuleFileSize")
    @ResponseBody
    public TeeJson getModuleFileSize(HttpServletRequest request){
        TeeJson json = new TeeJson();
        String module = request.getParameter("module");
        TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
        if (TeeUtility.isNullorEmpty(module)) {
            module = TeeAttachmentModelKeys.FILE_NET_DISK_PERSON;
        }
        json = fileNetdiskPersonService.getModuleFileSizeService(person,module);
        return json;
    }
    
    /**
     * 获取当前用户文件容量大小
     * @function: 
     * @author: wyw
     * @data: 2015年9月17日
     * @param request
     * @return TeeJson
     */
    @RequestMapping("/getFolderCapacity")
    @ResponseBody
    public TeeJson getFolderCapacity(HttpServletRequest request){
    	TeeJson json = new TeeJson();
    	TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
    	json = fileNetdiskPersonService.getFolderCapacity(person);
    	return json;
    }
    
    
    
    
}
