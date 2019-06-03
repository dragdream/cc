package com.tianee.oa.core.partthree.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.core.partthree.service.TeePartThreeService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("teePartThreeController")
public class TeePartThreeController {
	@Autowired
	private TeePartThreeService  teePartThreeService;
	
	
	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping("/doSave.action")
	@ResponseBody
	public TeeJson doSave(HttpServletRequest request){
		return teePartThreeService.doSave(request);
	}
	
	
	/**
	 * 获取初始化的数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInitData.action")
	@ResponseBody
	public TeeJson getInitData(HttpServletRequest request){
		return teePartThreeService.getInitData(request);
	}
	
	
	/**
	 * 获取超级密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPartThreePwd.action")
	@ResponseBody
	public TeeJson getPartThreePwd(HttpServletRequest request){
		return teePartThreeService.getPartThreePwd(request);
	}
	
	
	/**
	 * 修改超级密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatePartThreePwd.action")
	@ResponseBody
	public TeeJson updatePartThreePwd(HttpServletRequest request){
		return teePartThreeService.updatePartThreePwd(request);
	}
	
	
	
	/**
	 * 验证超级密码
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkPwd.action")
	@ResponseBody
	public TeeJson checkPwd(HttpServletRequest request){
		return teePartThreeService.checkPwd(request);
	}
	
}



