package com.beidasoft.zfjd.supervise.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.supervise.bean.SupOrganization;
import com.beidasoft.zfjd.supervise.model.SupOrganizationModel;
import com.beidasoft.zfjd.supervise.service.SupOrganizationService;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 执法部门监督部门关系表CONTROLLER类
 */
@Controller
@RequestMapping("supOrganizationCtrl")
public class SupOrganizationController {

    @Autowired
    private SupOrganizationService supOrganizationService;

    /**
     * 保存执法部门监督部门关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
	@RequestMapping("save")
	public TeeJson save(SupOrganizationModel supOrganizationModel){
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		SupOrganization supOrganization = new SupOrganization();
		BeanUtils.copyProperties(supOrganizationModel, supOrganization);
		supOrganization.setId(UUID.randomUUID().toString());

		supOrganizationService.save(supOrganizationModel);
		
		json.setRtState(true);
		return json;
	}
    /**
     * 获取执法部门监督部门关系表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/get")
    public TeeJson get(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        SupOrganizationModel model = new SupOrganizationModel();

        SupOrganization beanInfo = supOrganizationService.getById(id);
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
}
