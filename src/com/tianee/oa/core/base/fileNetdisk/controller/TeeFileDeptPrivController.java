package com.tianee.oa.core.base.fileNetdisk.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.service.TeeFileDeptPrivService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/fileDeptPriv")
public class TeeFileDeptPrivController extends BaseController {
    @Autowired
    private TeeFileDeptPrivService fileDeptPrivService;

    /**
     * 新建或编辑部门权限
     * @date 2014-2-13
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/addOrUpdateFileDeptPriv")
    @ResponseBody
    public TeeJson addOrUpdateFileDeptPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

        String userPrivJson = request.getParameter("userPrivJson");
        String fileId = request.getParameter("fileId");
        String extend = request.getParameter("extend");
        List<Map<String, String>> list = TeeJsonUtil.JsonStr2MapList(userPrivJson);
        json = fileDeptPrivService.addOrUpdateFileDeptPriv(person, list, fileId,extend);
        return json;
    }

    /**
     * 获取网盘部门权限信息
     * 
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/getFileDeptPriv")
    @ResponseBody
    public TeeJson getFileDeptPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileDeptPrivService.getFileUserPrivService(fileId);

        return json;
    }
    
    /**
     * 删除网盘部门权限信息
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/deleteFileDeptPriv")
    @ResponseBody
    public TeeJson deleteFileDeptPriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileDeptPrivService.deleteFileDeptPriv(fileId);
        return json;
    }
    
    
    

}
