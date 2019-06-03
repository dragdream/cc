package com.tianee.oa.core.base.officeProducts.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.officeProducts.model.TeeOfficeProductModel;
import com.tianee.oa.core.base.officeProducts.service.TeeOfficeProductService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("officeProductController")
public class TeeOfficeProductController {
	
	@Autowired
	private TeeOfficeProductService officeProductService;
	
	@RequestMapping("/addProduct")
	@ResponseBody
	public TeeJson addProduct(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeOfficeProductModel productModel = new TeeOfficeProductModel();
		TeeServletUtility.requestParamsCopyToObject(request, productModel);
		officeProductService.addProductModel(productModel);
		
		json.setRtMsg("添加成功");
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/delProduct")
	@ResponseBody
	public TeeJson delProduct(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		officeProductService.delProduct(loginUser,sid);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@RequestMapping("/delProducts")
	@ResponseBody
	public TeeJson delProducts(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String sids = TeeStringUtil.getString(request.getParameter("sids"), "");
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		officeProductService.delProducts(loginUser,sids);
		json.setRtState(true);
		json.setRtMsg("删除成功");
		return json;
	}
	
	@RequestMapping("/updateProduct")
	@ResponseBody
	public TeeJson updateProduct(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeOfficeProductModel model = new TeeOfficeProductModel();
		TeeServletUtility.requestParamsCopyToObject(request, model);
		officeProductService.updateProductModel(model);
		json.setRtState(true);
		json.setRtMsg("更新成功");
		return json;
	}
	
	@RequestMapping("/getModelById")
	@ResponseBody
	public TeeJson getModelById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(officeProductService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException{
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return officeProductService.datagird(dm, requestDatas);
	}
	
	@RequestMapping("/getProductWithPriv")
	@ResponseBody
	public TeeJson getProductWithPriv(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		int catId = TeeStringUtil.getInteger(request.getParameter("catId"), 0);
		int regType = TeeStringUtil.getInteger(request.getParameter("regType"), 0);
		
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeOfficeProductModel> opModels = officeProductService.getProductWithPriv(loginUser, catId,regType);
		
		Iterator<TeeOfficeProductModel> it = opModels.iterator();
		while(it.hasNext()){
			TeeOfficeProductModel m = it.next();
//			if(m.getRegType()!=regType){
//				it.remove();
//			}
		}
		
		json.setRtData(opModels);
		return json;
	}
}
