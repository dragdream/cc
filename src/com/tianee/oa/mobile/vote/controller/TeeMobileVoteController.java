package com.tianee.oa.mobile.vote.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.vote.model.TeeVoteModel;
import com.tianee.oa.core.base.vote.service.TeeVoteService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;

@Controller
@RequestMapping("mobileVoteController")
public class TeeMobileVoteController {
	
	@Autowired
	private TeeVoteService voteService;
	
	@ResponseBody
	@RequestMapping("getVoteList")
	public TeeEasyuiDataGridJson getVoteList(HttpServletRequest request,HttpServletResponse response,TeeDataGridModel dm) throws Exception{
		TeeEasyuiDataGridJson dataGridJson = voteService.voteDatagrid(dm, request, null);
		List<Map> mapList = new ArrayList();
		List<TeeVoteModel> voteList = dataGridJson.getRows();
		for(TeeVoteModel vm:voteList){
			Map data = new HashMap();
			data.put("subject", vm.getSubject());
			data.put("sid", vm.getSid());
			data.put("begin", vm.getBeginDateStr());
			data.put("end", vm.getEndDateStr());
			data.put("voteBtn", 0);//不显示操作按钮
			data.put("viewBtn", 0);//不显示操作按钮
			data.put("userName", vm.getCreateUserName());
			if("2".equals(vm.getPublish())){
				data.put("flag", 4);
				data.put("flagDesc", "已终止");
				if("1".equals(vm.getViewPriv())){
					data.put("viewBtn", 1);//显示查看投票结果按钮
				}
			}else{
				if(vm.getVoteStatus()==0){
					data.put("flag", 0);
					data.put("flagDesc", "未开始");
				}else if(vm.getVoteStatus()==1){
					data.put("flag", 1);
					data.put("flagDesc", "进行中");
					data.put("voteBtn", 1);//显示查看投票结果按钮
					
					if("1".equals(vm.getViewPriv())){
						data.put("viewBtn", 1);//显示查看投票结果按钮
					}
					
				}else if(vm.getVoteStatus()==2){
					data.put("flag", 2);
					data.put("flagDesc", "已结束");
					data.put("viewBtn", 1);//显示查看投票结果按钮
				}else if(vm.getVoteStatus()==3){
					data.put("flag", 3);
					data.put("flagDesc", "已投票");
				}
			}
			
			
			mapList.add(data);
		}
		
		
		dataGridJson.setRows(mapList);
		return dataGridJson;
	}
	
}
