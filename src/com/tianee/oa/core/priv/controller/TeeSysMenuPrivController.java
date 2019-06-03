package com.tianee.oa.core.priv.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.priv.model.TeeSysMenuPrivModel;
import com.tianee.oa.core.priv.service.TeeSysMenuPrivService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/sysMenuPrivManager")
public class TeeSysMenuPrivController extends BaseController{

	@Autowired
	TeeSysMenuPrivService menuPrivService;

	/**
	 * 新增或者删除
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	@ResponseBody
	public TeeJson  addOrUpdate(HttpServletRequest request, TeeSysMenuPrivModel model){
		TeeJson json = menuPrivService.addOrUpdate(model, request);
		return json;
	}
	
	/**
	 * by Id 查询
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectById")
	@ResponseBody
	public TeeJson  selectById(HttpServletRequest request, TeeSysMenuPrivModel model){
		TeeJson json = menuPrivService.selectById(model.getSid());
		return json;
	}
	
	/**
	 * by Id 删除
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/delById")
	@ResponseBody
	public TeeJson  delById(HttpServletRequest request, TeeSysMenuPrivModel model){
		TeeJson json = new TeeJson();
		menuPrivService.deleteById(model.getSid());
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 全部 删除
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@ResponseBody
	public TeeJson  deleteAll(HttpServletRequest request){
		TeeJson json = new TeeJson();
		menuPrivService.deleteAll();
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 删除 ByIds --批量删除
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteByIds")
	@ResponseBody
	public TeeJson  deleteByIds(HttpServletRequest request){
		TeeJson json = new TeeJson();
		menuPrivService.deleteByIds( request);
		json.setRtState(true);
		return json;
	}

	/**
	 * 按条件查询
	 * @author syl
	 * @date 2014-2-25
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryByTerm")
	@ResponseBody
	public TeeJson  queryByTerm(HttpServletRequest request, TeeSysMenuPrivModel model){
		TeeJson json = new TeeJson();
		String menuIds = TeeStringUtil.getString(request.getParameter("menuIds"));
		List<TeeSysMenuPrivModel> list= menuPrivService.selectByTerm(model, menuIds);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	
	@RequestMapping("/checkSysMenuPriv")
	public String  checkSysMenuPriv(HttpServletRequest request , HttpServletResponse response){
		TeeJson json = new TeeJson();

		boolean s=  menuPrivService.checkSysMenuPriv(request);
		return null;
	}
	
}
