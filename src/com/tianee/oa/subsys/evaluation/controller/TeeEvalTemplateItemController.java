package com.tianee.oa.subsys.evaluation.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.fileNetdisk.model.TeeFileNetdiskModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.evaluation.model.TeeEvalTemplateItemModel;
import com.tianee.oa.subsys.evaluation.service.TeeEvalTemplateItemService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/TeeEvalTemplateItemController")
public class TeeEvalTemplateItemController extends BaseController{
	@Autowired
	private TeeEvalTemplateItemService templateItemService;
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson addOrUpdate(HttpServletRequest request , TeeEvalTemplateItemModel model) {
		TeeJson json = new TeeJson();
		json = templateItemService.addOrUpdate(request , model);
		return json;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public TeeJson deleteById(HttpServletRequest request , TeeEvalTemplateItemModel model) {
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "0");
		json = templateItemService.deleteByIdService(request ,sids);
		return json;
	}
	

	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getById(HttpServletRequest request , TeeEvalTemplateItemModel model) {
		TeeJson json = new TeeJson();
		json = templateItemService.getById(request , model);
		return json;
	}
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/getEvalTemplateItem")
	@ResponseBody
	public TeeJson getEvalTemplateItem(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String level=TeeStringUtil.getString(request.getParameter("level"), "0");
		json = templateItemService.getEvalTemplateItem(request , level);
		return json;
	}
	
	/**
	 * @author ny
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return templateItemService.datagird(dm, requestDatas);
	}
	
	

    /**
     * 获取个人文件夹目录树
     * @date 2014-2-21
     * @author 
     * @param request
     * @return
     */
    @RequestMapping("/getTemplateItemTree")
    @ResponseBody
    public TeeJson getPersonFolderTree(HttpServletRequest request) {
        TeeJson json = new TeeJson();
        int evalTemplateId = TeeStringUtil.getInteger(request.getParameter("evalTemplateId"), 0);
        json = templateItemService.getTemplateItemTree(evalTemplateId);
        return json;
    }
	
}