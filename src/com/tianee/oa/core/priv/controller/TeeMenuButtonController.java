package com.tianee.oa.core.priv.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.priv.bean.TeeMenuButton;
import com.tianee.oa.core.priv.model.TeeMenuButtonModel;
import com.tianee.oa.core.priv.service.TeeMenuButtonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/menuButton")
public class TeeMenuButtonController {

	@Autowired
	TeeMenuButtonService menuButtonService;

	@ResponseBody
	@RequestMapping("/getButtonListByMenuId")
	public TeeEasyuiDataGridJson getButtonListByMenuId(int menuId){
		return menuButtonService.getButtonListByMenuId(menuId);
	}

	
	@RequestMapping(value="/getButtonById",method = RequestMethod.POST)
	@ResponseBody
	public TeeJson getButtonById(int id) {
		TeeJson json = new TeeJson();
		TeeMenuButton menuButton  = menuButtonService.getButtonById(id);
		TeeMenuButtonModel model = new TeeMenuButtonModel();
		BeanUtils.copyProperties(menuButton, model);		
		json.setRtState(true);
		json.setRtMsg("ok");
		json.setRtData(model);
		return json;
	}

	@RequestMapping(value="/addOrUpdateButton",method = RequestMethod.POST)
	@ResponseBody
	public TeeJson addOrUpdateButton(TeeMenuButtonModel model) {
		TeeJson json = new TeeJson();
		try {
			TeeMenuButton menuButton = new TeeMenuButton();
			BeanUtils.copyProperties(model, menuButton);
			if(menuButton.getId() == 0) {
				menuButtonService.save(menuButton);				
			}else {
				menuButtonService.update(menuButton);								
			}
			json.setRtState(true);
			json.setRtMsg("ok");
			json.setRtData(null);				
		}catch(Exception e) {
			json.setRtState(false);
			json.setRtMsg("保存错误");
			json.setRtData(null);
		}
		return json;
	}

	@RequestMapping(value="/deleteById",method = RequestMethod.POST)
	@ResponseBody
	public TeeJson deleteById(String id) {
		TeeJson json = new TeeJson();
		try {
			int iid = Integer.parseInt(id);
			menuButtonService.deleteById(iid);
			json.setRtState(true);
			json.setRtMsg("ok");
			json.setRtData(null);				
		}catch(Exception e) {
			json.setRtState(false);
			json.setRtMsg("删除错误");
			json.setRtData(null);
		}
		return json;
	}
	
	@RequestMapping("/getBtnPrivByMenuUuid.action")
	@ResponseBody
	public TeeJson getBtnPrivByMenuUuid(HttpServletRequest request,int menuId) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		List<Map> list = menuButtonService.getBtnPrivByMenuUuid(person.getUuid(),menuId);
				 
		/*
		List<TeeMenuButtonModel> listM = new ArrayList<TeeMenuButtonModel>();
		for (TeeMenuButton btn:list) {
			TeeMenuButtonModel model = new TeeMenuButtonModel();
			
			BeanUtils.copyProperties(btn, model);
			listM.add(model);
		}*/
		json.setRtState(true);
		json.setRtMsg("获取数据成功!");
		json.setRtData(list);
		return json;
	}

}
