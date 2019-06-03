package com.beidasoft.zfjd.subjectPerson.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.subjectPerson.bean.TblSubjectPerson;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.beidasoft.zfjd.subjectPerson.service.TblSubjectPersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

/**
 * 主体人员关联表CONTROLLER类
 */
@Controller
@RequestMapping("tblSubjectPersonCtrl")
public class TblSubjectPersonController {

    @Autowired
    private TblSubjectPersonService SubjectPersonService;

    /**
     * 保存主体人员关联表数据
     *
     * @param model
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/saveDocInfo")
    public TeeJson saveDocInfo(TblSubjectPersonModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TblSubjectPerson beanInfo = new TblSubjectPerson();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);







        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    /**
     * 获取主体人员关联表数据
     *
     * @param id
     * @param request
     * @return 
     */

    @ResponseBody
    @RequestMapping("/getDocInfo")
    public TeeJson getDocInfo(String id, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TblSubjectPersonModel model = new TblSubjectPersonModel();

        //TblSubjectPerson beanInfo = SubjectPersonService.getById(id);
        //实体类-->model传值
        //BeanUtils.copyProperties(beanInfo, model);

        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,TblSubjectPersonModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		long total = SubjectPersonService.getTotal(queryModel);
		List<TblSubjectPersonModel> modelList = new ArrayList();
		List<TblSubjectPerson> departmentInfos = SubjectPersonService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);
		for (TblSubjectPerson departmentInfo : departmentInfos) {
			TblSubjectPersonModel infoModel = new TblSubjectPersonModel();
			BeanUtils.copyProperties(departmentInfo,infoModel);
			

			modelList.add(infoModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;

	}
}
