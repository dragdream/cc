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

import com.beidasoft.xzzf.informationEntry.bean.InforEntryPunish;
import com.beidasoft.xzzf.informationEntry.model.InforEntryPunishModel;
import com.beidasoft.xzzf.informationEntry.service.InforEntryPunishService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 字表处罚决定CONTROLLER类
 */
@Controller
@RequestMapping("inforEntryPunishCtrl")
public class InforEntryPunishController {

    @Autowired
    private InforEntryPunishService inforEntryPunishService;

    /**
     * 保存字表处罚决定数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InforEntryPunishModel model, HttpServletRequest request) {

        return inforEntryPunishService.save(model, request);
    }
    /**
     * 获取字表处罚决定数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        InforEntryPunishModel model = new InforEntryPunishModel();

        InforEntryPunish beanInfo = inforEntryPunishService.getbyCaseId(id);
        
        //判断是否为空
        if(beanInfo == null){
        	json.setRtState(false);
        	return json;
        }
        
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);

        // 单独处理时间类型转换
        if(beanInfo.getInspectDate() != null) {
        	model.setInspectDateStr(TeeDateUtil.format(beanInfo.getInspectDate(), "yyyy年MM月dd日"));
        }
        if(beanInfo.getFilingDate() != null) {
        	model.setFilingDateStr(TeeDateUtil.format(beanInfo.getFilingDate(), "yyyy年MM月dd日"));
        }

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
