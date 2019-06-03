package com.tianee.oa.core.system.initSysData.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.core.system.initSysData.service.TeeInitSysDataService;
import com.tianee.oa.oaconst.TeeSysParaKeys;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@RequestMapping("teeInitSysDataController")
public class TeeInitSysDataController {

	@Autowired
	private TeeInitSysDataService initSysDataService;
	
	/**
	 * @author zhp
	 * @createTime 2014-1-17
	 * @editTime 上午11:10:38
	 * @desc
	 */
	@RequestMapping("/initSysParaData")
	@ResponseBody
	public TeeJson initSysParaData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		/**
		 * 请将要初始化的参数 写到这里面
		 */
		Map initParaDataMap = new HashMap<String, String>();
		initParaDataMap.put(TeeSysParaKeys.NOTIFY_AUDITING_SINGLE, "1");//公告通知是否需要审批 ...
		initParaDataMap.put(TeeSysParaKeys.NOTIFY_AUDITING_ALL,"");//公告通知 审批人
		initParaDataMap.put(TeeSysParaKeys.NOTIFY_AUDITING_EXCEPTION,"");//公告通知无需审批人
		initSysDataService.init(initParaDataMap);
		initSysDataService.doInitData();
		
		try {
			json.setRtState(true);
			json.setRtMsg("初始化成功！");
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除失败");
			e.printStackTrace();
		}
		return json;
	}
	public TeeInitSysDataService getInitSysDataService() {
		return initSysDataService;
	}
	public void setInitSysDataService(TeeInitSysDataService initSysDataService) {
		this.initSysDataService = initSysDataService;
	}
	
	
}
