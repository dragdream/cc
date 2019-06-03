package com.tianee.test.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.test.service.TeeUserService;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/test")
public class TeeTestController {
	
	@Autowired
	private TeeUserService d;
	
	@ResponseBody
	@RequestMapping("/test")
	public TeeJson test(HttpServletRequest request){
		TeeJson json = new TeeJson();
		d.test();
		
		return json;
	}

	
	public static void main(String[] args) throws Exception{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Connection conn = DriverManager.getConnection("jdbc:sqlserver://192.168.31.78:1433;DatabaseName=oaop", "sa", "oaop2014");
		DbUtils dbUtils = new DbUtils(conn);
		Map map = dbUtils.queryToMap("select * from org_cache");
		System.out.println(map.get("value_"));
	}
}
