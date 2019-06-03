package com.beidasoft.xzzf.informationEntry.controller;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryDecision;
import com.beidasoft.xzzf.informationEntry.model.InforEntryDecisionModel;
import com.beidasoft.xzzf.informationEntry.service.InforEntryDecisionService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 子表查封扣押决定书CONTROLLER类
 */
@Controller
@RequestMapping("inforEntryDecisionCtrl")
public class InforEntryDecisionController {

    @Autowired
    private InforEntryDecisionService inforEntryDecisionService;

    /**
     * 保存子表查封扣押决定书数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InforEntryDecisionModel model, HttpServletRequest request) {
    	return inforEntryDecisionService.save(model, request);
    }
    /**
     * 获取子表查封扣押决定书数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        InforEntryDecisionModel model = new InforEntryDecisionModel();

        InforEntryDecision beanInfo = inforEntryDecisionService.getbyCaseId(id);
        if (TeeUtility.isNullorEmpty(beanInfo)) {
        	json.setRtState(false);
			return json;
		}
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);
        //处理时间类型
        if (!TeeUtility.isNullorEmpty(beanInfo.getStartDate())) {
        	model.setStartDateStr(TeeDateUtil.format(beanInfo.getStartDate(),"yyyy年MM月dd日"));
		}
        if (!TeeUtility.isNullorEmpty(beanInfo.getEndDate())) {
        	model.setEndDateStr(TeeDateUtil.format(beanInfo.getEndDate(),"yyyy年MM月dd日"));
		}
        if (!TeeUtility.isNullorEmpty(beanInfo.getArrvelDate())) {
        	model.setArrvelDateStr(TeeDateUtil.format(beanInfo.getArrvelDate(),"yyyy年MM月dd日"));
        }
        if (!TeeUtility.isNullorEmpty(beanInfo.getReceiptDate())) {
        	model.setReceiptDateStr(TeeDateUtil.format(beanInfo.getReceiptDate(),"yyyy年MM月dd日"));
        }
        
        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
