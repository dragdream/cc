package com.tianee.oa.core.base.fileNetdisk.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.service.TeeFileUserPrivService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/fileUserPriv")
public class TeeFileUserPrivController extends BaseController {
    @Autowired
    private TeeFileUserPrivService fileUserPrivService;

    /**
     * 新建文件网盘
     * 
     * @date 2014-1-4
     * @author
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/addFileUserPriv")
    @ResponseBody
    public TeeJson addFileUserPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

        String userPrivJson = request.getParameter("userPrivJson");
        String fileId = request.getParameter("fileId");
        String extend = request.getParameter("extend");
        List<Map<String, String>> list = TeeJsonUtil.JsonStr2MapList(userPrivJson);
        json = fileUserPrivService.addFileUserPriv(person, list, fileId,extend);
        return json;
    }

    /**
     * 获取网盘权限信息
     * 
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/getFileUserPriv")
    @ResponseBody
    public TeeJson getFileUserPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileUserPrivService.getFileUserPrivService(fileId);
        return json;
    }
    
    

    /**
     * 获取网盘权限信息(个人文件柜)
     * 
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/getFileNetdiskPersonPriv")
    @ResponseBody
    public TeeJson getFileNetdiskPersonPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileUserPrivService.getFileNetdiskPersonPrivService(fileId,person);
        return json;
    }
    /**
     * 删除网盘人员权限信息
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/deleteFileUserPriv")
    @ResponseBody
    public TeeJson deleteFileUserPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileUserPrivService.deleteFileUserPriv(fileId);
        return json;
    }
    

}
