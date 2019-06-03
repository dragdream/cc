package com.tianee.oa.core.base.attend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.attend.bean.TeeAttendDutyTable;
import com.tianee.oa.core.base.attend.service.TeeAttendDutyTableService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("attendDutyTable")
public class TeeAttendDutyTableController {
	
	@Autowired
	private TeeAttendDutyTableService attendDutyTableService;
	
	@ResponseBody
	@RequestMapping("addDutyTable")
	public TeeJson addDutyTable(HttpServletRequest request){
		String date = request.getParameter("date");
		int userIds[] = TeeStringUtil.parseIntegerArray(request.getParameter("userIds"));
		for(int userId:userIds){
			if(userId==0){
				continue;
			}
			TeeAttendDutyTable attendDutyTable = attendDutyTableService.getDutyTablesByDateAndUser(date, userId);
			if(attendDutyTable==null){
				attendDutyTable = new TeeAttendDutyTable();
				attendDutyTable.setPbDate(date);
				
				TeePerson user = new TeePerson();
				user.setUuid(userId);
				attendDutyTable.setUser(user);
				attendDutyTableService.addDutyTableRecord(attendDutyTable);
			}
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping("updateDutyTable")
	public TeeJson updateDutyTable(HttpServletRequest request){
		String datas = TeeStringUtil.getString(request.getParameter("datas"));
		List<Map<String,String>> mapList = TeeJsonUtil.JsonStr2MapList(datas);
		for(Map<String,String> map:mapList){
			TeeAttendDutyTable attendDutyTable = attendDutyTableService.getDutyTableBySid(Integer.parseInt(map.get("sid")));
			attendDutyTable.setJsonData(map.get("jsonData"));
			attendDutyTableService.updateDutyTableRecord(attendDutyTable);
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping("deleteDutyTable")
	public TeeJson deleteDutyTable(HttpServletRequest request){
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		attendDutyTableService.deleteById(id);
		return null;
	}
	
	@ResponseBody
	@RequestMapping("getDutyTablesByDate")
	public TeeJson getDutyTablesByDate(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String date = request.getParameter("date");
		
		List<TeeAttendDutyTable> tables = attendDutyTableService.getDutyTablesByDate(date);
		List<Map> datas = new ArrayList();
		for(TeeAttendDutyTable table:tables){
			Map data  = new HashMap();
			data.put("sid", table.getSid());
			data.put("name", table.getUser().getUserName());
			data.put("jsonData", JSONObject.fromObject(TeeStringUtil.getString(table.getJsonData(), "{}")));
			datas.add(data);
		}
		
		json.setRtState(true);
		json.setRtData(datas);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("getDutyTablesByDateAndUser")
	public TeeJson getDutyTablesByDateAndUser(HttpServletRequest request){
		return null;
	}
}
