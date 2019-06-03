package com.tianee.oa.core.general.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.model.TeeFavoriteRecordModel;
import com.tianee.oa.core.general.service.TeeFavorateRecordService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("favoriteRecord")
public class TeeFavoriteRecordController {
	@Autowired()
	private TeeFavorateRecordService favorateRecordService;
	
	@ResponseBody
	@RequestMapping("addFavoriteRecord")
	public TeeJson addFavoriteRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeFavoriteRecordModel favoriteRecordModel = new TeeFavoriteRecordModel();
		TeeServletUtility.requestParamsCopyToObject(request, favoriteRecordModel);
		TeePerson loginUser = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		favoriteRecordModel.setUserId(loginUser.getUuid());
		if(favorateRecordService.addFavorateRecord(favoriteRecordModel)){
			json.setRtMsg("收藏成功");
			json.setRtState(true);
		}else{
			json.setRtMsg("此页面收藏已存在");
			json.setRtState(false);
		}
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("deleteFavoriteRecord")
	public TeeJson deleteFavoriteRecord(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		favorateRecordService.deleteFavoriateRecord(sid);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("list")
	public TeeEasyuiDataGridJson list(HttpServletRequest request,TeeDataGridModel dm){
		//TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		return favorateRecordService.listModels(person.getUuid(),dm);
	}
	
	
	
}
