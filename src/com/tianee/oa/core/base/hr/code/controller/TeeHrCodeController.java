package com.tianee.oa.core.base.hr.code.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.hr.code.bean.TeeHrCode;
import com.tianee.oa.core.base.hr.code.service.TeeHrCodeService;
import com.tianee.webframe.controller.BaseController;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;


@Controller
@RequestMapping("/hrCode")
public class TeeHrCodeController  extends BaseController{
	@Autowired
	TeeHrCodeService sysCodeServ;

	/**
	 * 新增或者更新参数
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateSysPara")
	@ResponseBody
	public TeeJson addUpdateSysCode(HttpServletRequest request, TeeHrCode para)
			{
		TeeJson json = sysCodeServ.addUpdatePara(para, request);
		return json;
	}

	
	/**
	 * 获取主编码
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysPara.action")
	@ResponseBody
	public TeeJson getSysPara(HttpServletRequest request)
			throws Exception {
	    TeeJson json = new TeeJson();
    	List<TeeHrCode> para = sysCodeServ.getSysPara();
    	//TeeHrCode code = para.get(6);
    	//System.out.println(para);
    	json.setRtData(para);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		
		return json;
	}
	
	/**
	 * 根据主编码的编码  获取所有下级所有 代码数据
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysParaByParent")
	@ResponseBody
	public TeeJson getSysParaByParent(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		TeeHrCode code = new TeeHrCode();
		int parentId = TeeStringUtil.getInteger(request.getParameter("parentId") , 0);
		code.setParentId(parentId);
		List<TeeHrCode> list = sysCodeServ.getSysParaByParent(code);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 根据主编码的编号  获取所有下级所有 代码数据
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysCodeByParentCodeNo")
	@ResponseBody
	public TeeJson getSysCodeByParentCodeNo(HttpServletRequest request ) throws Exception {
		TeeJson json = new TeeJson();
		String parentCodeNo = TeeStringUtil.getString(request.getParameter("codeNo"));
		List<Map> list = TeeHrCodeManager.getChildSysCodeListByParentCodeNo(parentCodeNo);
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getById")
	@ResponseBody
	public TeeJson getSysMenuById(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int  sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		TeeHrCode code = sysCodeServ.getById(sid);
		if(code == null){
			json.setRtState(false);
			json.setRtMsg("此编码已被删除！");
			return json;
		}
		json.setRtData(code);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除  子代码
	 * 
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delById")
	@ResponseBody
	public TeeJson delPara(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int  sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		sysCodeServ.deleteById(sid);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除主编码
	 * @author syl
	 * @date 2014-3-1
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delMainCode")
	@ResponseBody
	public TeeJson delMainCode(HttpServletRequest request) throws Exception {
		TeeJson json = new TeeJson();
		int  sid = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
		sysCodeServ.delByParentId(sid);
		json.setRtState(true);
		return json;
	}

	
	
}
