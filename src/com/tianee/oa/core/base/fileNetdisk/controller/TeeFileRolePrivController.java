package com.tianee.oa.core.base.fileNetdisk.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.service.TeeFileRolePrivService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/fileRolePriv")
public class TeeFileRolePrivController extends BaseController {
    @Autowired
    private TeeFileRolePrivService fileRolePrivService;

    /**
     * 新建或编辑角色权限
     * @date 2014-2-13
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/addOrUpdateFileRolePriv")
    @ResponseBody
    public TeeJson addOrUpdateFileRolePriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);

        String userPrivJson = request.getParameter("userPrivJson");
        String fileId = request.getParameter("fileId");
        String extend = request.getParameter("extend");
        List<Map<String, String>> list = TeeJsonUtil.JsonStr2MapList(userPrivJson);
        json = fileRolePrivService.addOrUpdateFileRolePriv(person, list, fileId,extend);
        return json;
    }
    /**
     * 删除网盘角色权限信息
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/deleteFileRolePriv")
    @ResponseBody
    public TeeJson deleteFileRolePriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileRolePrivService.deleteFileRolePriv(fileId);
        return json;
    }
    
    /**
     * 获取网盘角色权限信息
     * 
     * @date 2014-1-15
     * @author
     * @param request
     * @return
     */
    @RequestMapping("/getFileRolePriv")
    @ResponseBody
    public TeeJson getFileRolePriv(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        int fileId = TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
        json = fileRolePrivService.getFileUserPrivService(fileId);

        return json;
    }

}
