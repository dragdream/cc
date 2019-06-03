package com.tianee.oa.subsys.crm.core.chances.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.crm.core.chances.model.TeeCrmChancesModel;
import com.tianee.oa.subsys.crm.core.chances.service.TeeCrmChancesService;
import com.tianee.oa.subsys.crm.core.contacts.model.TeeCrmContactsModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;

@Controller
@RequestMapping("/crmChancesController")
public class TeeCrmChancesController extends BaseController {
	
	@Autowired
	private TeeCrmChancesService chancesServices;
	
	/**
	 * 添加商机
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdate")
	public TeeJson addOrUpdate(HttpServletRequest request,TeeCrmChancesModel model){
		TeeJson json = new TeeJson();
		json = chancesServices.addOrUpdate(request,model);
		return json;
	}
	
	/**
	 * 商机列表
	 * @param dm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm, HttpServletRequest request ){
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return chancesServices.datagird(dm, requestDatas);
	}
	
	/**
	 * 获取商机详情
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getInfoBySid")
	public TeeJson getInfoBySid(HttpServletRequest request,TeeCrmChancesModel model){
		TeeJson json = new TeeJson();
		json = chancesServices.getInfoBySid(request,model);
		return json;
	}
	
	/**
	 * 输单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lostOrder")
	public TeeJson lostOrder(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = chancesServices.lostOrder(request);
		return json;
	}
	
	/**
	 * 赢单
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/winOrder")
	public TeeJson winOrder(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json  = chancesServices.winOrder(request);
		return json;
	}
	
	/**
	 * 无效
	 * @param request
	 * @return
	 */
	@ResponseBody
    @RequestMapping("/invalid")
	public TeeJson invalid(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = chancesServices.invalid(request);
		return json;
	}
	
	/**
	 * 删除 商机
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delById")
	public TeeJson delById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json = chancesServices.delById(request);
		return json;
	}
	
	
	/**
	 * 查询某客户下的商机数据
	 * @param request
	 * @return
	 */
	@RequestMapping("selectChances")
	@ResponseBody
	public TeeJson selectChances(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeCrmChancesModel> list = chancesServices.selectChances(request);
		if(list.size()>0){
			json.setRtData(list);
			json.setRtMsg("查询成功！");
			json.setRtState(true);
		}else{
			json.setRtMsg("查询失败！");
			json.setRtState(false);
		}
		
		return json;
	}

}
