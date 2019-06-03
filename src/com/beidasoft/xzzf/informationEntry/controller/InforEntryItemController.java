package com.beidasoft.xzzf.informationEntry.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryItem;
import com.beidasoft.xzzf.informationEntry.model.InforEntryItemModel;
import com.beidasoft.xzzf.informationEntry.service.InforEntryItemService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 子表物品清单CONTROLLER类
 */
@Controller
@RequestMapping("inforEntryItemCtrl")
public class InforEntryItemController {

    @Autowired
    private InforEntryItemService inforEntryItemService;

    /**
     * 保存子表物品清单数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(InforEntryItemModel model, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        InforEntryItem beanInfo = new InforEntryItem();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);
        
		beanInfo.setId(UUID.randomUUID().toString());
		beanInfo.setCaseId(model.getCaseId());
		beanInfo.setCreateName(user.getUserName());
		beanInfo.setCreateId(TeeStringUtil.getString(user.getUuid()));
		beanInfo.setCreateDate(new Date());
		beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));

        inforEntryItemService.save(beanInfo);
        
        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 获取子表物品清单数据
     *
     * @param id
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        InforEntryItemModel model = new InforEntryItemModel();

        InforEntryItem beanInfo = inforEntryItemService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);



        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 获取分页查询
     *
     * @param id
     * @param request
     * @return 
     */
    @ResponseBody
    @RequestMapping("/listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,String caseId,HttpServletRequest request){
    	TeeEasyuiDataGridJson json = inforEntryItemService.listByPage(dataGridModel, caseId, request);
    	return json;
    }
    
    /**
     * 根据id删除物品
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteById")
    public TeeJson deleteById(String id,HttpServletRequest request){
    	TeeJson json = inforEntryItemService.deleteById(id);
    	return json;
    }
}
