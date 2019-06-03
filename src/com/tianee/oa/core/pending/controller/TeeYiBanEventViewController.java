package com.tianee.oa.core.pending.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.pending.bean.TeeDaiBanEventView;
import com.tianee.oa.core.pending.bean.TeeYiBanEventView;
import com.tianee.oa.core.pending.model.TeeDaiBanEventModel;
import com.tianee.oa.core.pending.service.TeeYiBanEventViewService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("TeeYiBanEventViewController")
public class TeeYiBanEventViewController {
	@Autowired
	private TeeYiBanEventViewService ybeService;
	
	
	/*
	 * 显示列表 
	 */
	@ResponseBody
	@RequestMapping("list")
	public TeeEasyuiDataGridJson list1(HttpServletRequest request,TeeDataGridModel gridModel,TeeDaiBanEventModel chandlandlerModel){
		TeeEasyuiDataGridJson gridJson=new TeeEasyuiDataGridJson();
		TeePerson person =(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String name=person.getUserName();
		List<TeeDaiBanEventModel> teeCommonHandlerModel=new ArrayList<TeeDaiBanEventModel>();
		long total=ybeService.getTotal(chandlandlerModel,person);
		List<TeeYiBanEventView> chandler=ybeService.lists(gridModel.getFirstResult(), gridModel.getRows(),chandlandlerModel,person);
		for(TeeYiBanEventView  chandlers: chandler ){
			
			TeeDaiBanEventModel chandlerModel=new TeeDaiBanEventModel();
			//BeanUtils.copyProperties(chandlerModel,chandlers);
			chandlerModel.setId(chandlers.getId());
			chandlerModel.setTitle(chandlers.getTitle());
			chandlerModel.setContent(chandlers.getContent());
			chandlerModel.setAddress(chandlers.getAddress());
			//chandlerModel.setUuid(chandlers.getUuid());
			chandlerModel.setRecUser(chandlers.getRecUser());
			String time=TeeDateUtil.format(chandlers.getCreateTime());
			chandlerModel.setTime(time);
			chandlerModel.setSendUser(chandlers.getSendUser());
			chandlerModel.setFlag(chandlers.getFlag());
			//chandlerModel.setState(chandlers.getState());
			if(chandlers.getModel().equals("HR")){
				chandlerModel.setModel("人事");
			}else if( chandlers.getModel().equals("ZC")){
				chandlerModel.setModel("装财");
			}else if(chandlers.getModel().equals("DJ")){
				chandlerModel.setModel("党建");
			}else if(chandlers.getModel().equals("XT")){
				chandlerModel.setModel("协同");
			}else if(chandlers.getModel().equals("GW")){
				chandlerModel.setModel("公文");
			}else if(chandlers.getModel().equals("SP")){
				chandlerModel.setModel("审批");
			}else if(chandlers.getModel().equals("ZF")){
				chandlerModel.setModel("执法");
			}
			
			teeCommonHandlerModel.add(chandlerModel);
		}
		
		gridJson.setTotal(total);
		gridJson.setRows(teeCommonHandlerModel);
		return gridJson;
		
	}
}
