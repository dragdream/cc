package com.tianee.oa.core.general.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/sysPara")
public class TeeSysParaController {
	@Autowired
	TeeSysParaService sysParaServ;

	/**
	 * 新增或者更新参数
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateSysPara.action")
	@ResponseBody
	public TeeJson addUpdateSysPara(HttpServletRequest request, TeeSysPara para)
			throws Exception {
		TeeJson json = new TeeJson();
		if(para != null &&  !TeeUtility.isNullorEmpty(para.getParaName())  ){
		    sysParaServ.addUpdatePara(para);
		    json.setRtState(true);
			json.setRtMsg("保存成功");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("保存失败");
		return json;
	}

	
	/**
	 * 获取参数对象
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
	    String paraName = request.getParameter("paraName");
    	TeeSysPara para = sysParaServ.getSysPara(paraName);
    	//System.out.println(para);
    	json.setRtData(para);
    	json.setRtState(true);
		json.setRtMsg("查询成功！");
		return json;
	}	
	
	

	/**
	 * 批量保存---安全与设置
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateSysParaList")
	@ResponseBody
	public TeeJson addOrUpdateSysParaList(HttpServletRequest request, TeeSysPara para)
			throws Exception {
		TeeJson json = new TeeJson();
		/*if(para != null &&  !TeeUtility.isNullorEmpty(para.getParaValue())  ){
		    sysParaServ.addUpdatePara(para);
		    json.setRtState(true);
			json.setRtMsg("保存成功");
		}*/
		sysParaServ.addOrUpdateSysParaList(request);
		 json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	
	
	/**
	 * 批量保存---快捷展示区
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdatePortletSysPara")
	@ResponseBody
	public TeeJson addOrUpdatePortletSysPara(HttpServletRequest request, TeeSysPara para)
			throws Exception {
		TeeJson json = new TeeJson();
		/*if(para != null &&  !TeeUtility.isNullorEmpty(para.getParaValue())  ){
		    sysParaServ.addUpdatePara(para);
		    json.setRtState(true);
			json.setRtMsg("保存成功");
		}*/
		sysParaServ.addOrUpdatePortletSysPara(request);
		 json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	
	
	
	/**
	 * 批量获取参数  以逗号分隔
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSysParaList")
	@ResponseBody
	public TeeJson getSysParaList(HttpServletRequest request, TeeSysPara para)
			throws Exception {
		TeeJson json = new TeeJson();
		List<TeeSysPara> list = sysParaServ.getSysParaList(request);
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("保存成功");
		return json;
	}
	
	
	
	/**
	 * 获取参数是存人员信息（一般以逗号分隔 或者单个人员，例如不受Ip限制登录人员）
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getParaIsUser")
	@ResponseBody
	public TeeJson getParaIsUser(HttpServletRequest request)
			throws Exception {
		String paraName = TeeStringUtil.getString(request.getParameter("paraName")); 
		TeeJson json = sysParaServ.getParaIsUser(paraName) ;
		return json;
	}
	
	/**
	 * 获取所有系统参数
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午10:36:02
	 * @desc
	 */
	@RequestMapping("/getAllSysPara")
	@ResponseBody
	public TeeJson getAllSysPara(HttpServletRequest request, TeeSysPara para)
			throws Exception {
		TeeJson json = new TeeJson();
		List<TeeSysPara> list = sysParaServ.getAllSysPara();
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("获取所有系统参数成功!");
		return json;
	}
	
	/**
	 * 更新系统控件设置
	 * @param request
	 * @param para
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateSysPara")
	@ResponseBody
	public TeeJson updateSysPara(HttpServletRequest request, TeeSysPara para)
			throws Exception {
		TeeJson json = new TeeJson();
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		if(!TeePersonService.checkIsAdminPriv(loginUser)){
			throw new TeeOperationException("非管理员禁止更改系统参数");
		}
		sysParaServ.updateSysPara(para.getParaName(), para.getParaValue());
		json.setRtState(true);
		return json;
	}
}
