package com.beidasoft.xzzf.informationEntry.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryStaff;
import com.beidasoft.xzzf.informationEntry.model.InforEntryStaffModel;
import com.beidasoft.xzzf.informationEntry.service.InforEntryStaffService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 子表执法人员CONTROLLER类
 */
@Controller
@RequestMapping("inforEntryStaffCtrl")
public class InforEntryStaffController {

    @Autowired
    private InforEntryStaffService inforEntryStaffService;
    

    /**
     * 保存子表执法人员数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InforEntryStaffModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        InforEntryStaff beanInfo = new InforEntryStaff();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);







        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取子表执法人员数据
     *
     * @param id
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        InforEntryStaffModel model = new InforEntryStaffModel();

        InforEntryStaff beanInfo = inforEntryStaffService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);






        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 获取子表执法人员数据
     *
     * @param id
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping("/getStaffList")
    public TeeEasyuiDataGridJson getStaffList(HttpServletRequest request,TeeDataGridModel dataGridModel) {
    	TeeEasyuiDataGridJson json = inforEntryStaffService.getStaffList(request, dataGridModel);
        return json;
    }
    
}
