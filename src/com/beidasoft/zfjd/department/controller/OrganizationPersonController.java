package com.beidasoft.zfjd.department.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.department.bean.OrganizationPerson;
import com.beidasoft.zfjd.department.model.OrganizationPersonModel;
import com.beidasoft.zfjd.department.service.OrganizationPersonService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 执法部门人员关系表CONTROLLER类
 */
@Controller
@RequestMapping("organizationPersonCtrl")
public class OrganizationPersonController {

    @Autowired
    private OrganizationPersonService organizationPersonService;

    /**
     * 保存执法部门人员关系表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/save")
    public TeeJson save(OrganizationPersonModel model, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		try {
			//创建实例化实体类对象
			OrganizationPerson organizationPerson = new OrganizationPerson();
			BeanUtils.copyProperties(model, organizationPerson);
			
			organizationPerson.setId(UUID.randomUUID().toString());
			organizationPersonService.save(model);
	        json.setRtState(true);

		} catch (Exception e) {
			// TODO: handle exception
	        json.setRtState(false);
	        System.out.println("OrganizationPersonCotroller.save"+e.getMessage());

		}
        return json;
    }
    /**
     * 获取执法部门人员关系表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/get")
    public TeeJson get(String id, HttpServletRequest request) {
    	TeeJson json = new TeeJson();
        OrganizationPersonModel model = new OrganizationPersonModel();
    	try {
    		OrganizationPerson beanInfo = organizationPersonService.getById(id);
            //实体类-->model传值
            BeanUtils.copyProperties(beanInfo, model);
            if(!TeeUtility.isNullorEmpty(model)){
            	json.setRtData(model);
                json.setRtState(true);
            }else{
                json.setRtState(false);
            }
		} catch (Exception e) {
			// TODO: handle exception
            json.setRtState(false);
		}
        return json;
    }
}
