package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeePersonalitySettings;
import com.tianee.oa.core.general.service.TeePersonalitySettingsService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 个性化设置controller
 * @author kakalion
 *
 */
@Controller
@RequestMapping("personalitySettings")
public class TeePersonalitySettingsController {
	@Autowired
	private TeePersonalitySettingsService settingsService;
	
	/**
	 * 更新新建工作的办理面板位置
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateWfPanelPosModel")
	public TeeJson updateWfPanelPosModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String model = TeeStringUtil.getString(request.getParameter("model"));
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		settingsService.updateWfPanelPosModel(model, loginPerson);
		
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getPersonalitySettings")
	public TeeJson getPersonalitySettings(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String model = TeeStringUtil.getString(request.getParameter("model"));
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeePersonalitySettings settings = settingsService.getSettings(loginPerson.getUuid());
		settings.setPerson(null);
		json.setRtData(settings);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更新工作标星模型
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateWfStarsModel")
	public TeeJson updateWfStarsModel(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String model = TeeStringUtil.getString(request.getParameter("model"));
		
		TeePerson loginPerson = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		settingsService.updateWfStarsModel(model, loginPerson);
		
		json.setRtState(true);
		return json;
	}

	public void setSettingsService(TeePersonalitySettingsService settingsService) {
		this.settingsService = settingsService;
	}
	
}
