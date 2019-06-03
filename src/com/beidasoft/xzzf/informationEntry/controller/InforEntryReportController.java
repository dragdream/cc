package com.beidasoft.xzzf.informationEntry.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryReport;
import com.beidasoft.xzzf.informationEntry.model.InforEntryReportModel;
import com.beidasoft.xzzf.informationEntry.service.InforEntryReportService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 子表结案报告书CONTROLLER类
 */
@Controller
@RequestMapping("inforEntryReportCtrl")
public class InforEntryReportController {

    @Autowired
    private InforEntryReportService inforEntryReportService;

    /**
     * 保存子表结案报告书数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InforEntryReportModel model, HttpServletRequest request) {

    	return inforEntryReportService.save(model, request);
    }
    /**
     * 获取子表结案报告书数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        InforEntryReportModel model = new InforEntryReportModel();

        InforEntryReport beanInfo = inforEntryReportService.getbyCaseId(id);
        
        //判断是否为空
        if(beanInfo == null){
        	json.setRtState(false);
        	return json;
        }
        
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);

        // 单独处理时间类型转换
        if(beanInfo.getPunishDate() != null) {
        	model.setPunishDateStr(TeeDateUtil.format(beanInfo.getPunishDate(), "yyyy年MM月dd日"));
        }

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
