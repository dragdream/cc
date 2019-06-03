package com.tianee.thirdparty.openfire.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.openfire.TeeOpenfireUtil;

@Controller
@RequestMapping("openfire")
public class OpenFireController {
	
	@ResponseBody
	@RequestMapping("syncUsers")
	public TeeJson syncUsers(){
		TeeJson json = new TeeJson();
		
		Connection conn = null;
		List<Map> list = null;
		try{
			conn = TeeDbUtility.getConnection(null);
			DbUtils dbUtils = new DbUtils(conn);
			list = dbUtils.queryToMapList("select user_id,user_name from person");
			json.setRtState(true);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
		if(list!=null){
			for(Map data:list){
				TeeOpenfireUtil.createUser(String.valueOf(data.get("user_id")), String.valueOf(data.get("user_name")));
			}
		}
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("clearUsers")
	public TeeJson clearUsers(){
		TeeJson json = new TeeJson();
		
		Connection conn = null;
		List<Map> list = null;
		try{
			conn = TeeDbUtility.getConnection(null);
			DbUtils dbUtils = new DbUtils(conn);
			list = dbUtils.queryToMapList("select user_id,user_name from person");
			json.setRtState(true);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
		if(list!=null){
			for(Map data:list){
				TeeOpenfireUtil.delUser(String.valueOf(data.get("user_id")));
			}
		}
		
		return json;
	}
}
