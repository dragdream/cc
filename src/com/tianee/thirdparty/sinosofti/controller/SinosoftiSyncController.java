package com.tianee.thirdparty.sinosofti.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.thirdparty.sinosofti.service.SinosoftiSyncService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.db.TeeDbUtility;

@Controller
@RequestMapping("/sinosofti")
public class SinosoftiSyncController {
	
	@Autowired
	private SinosoftiSyncService syncService;
	
	@RequestMapping("/tbUser")
	public void tbUser(){
		try {
			syncService.userTimer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/tbDept")
	public void tbDept(){
		try {
			syncService.deptTimer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/addUser")
	public TeeJson addUser(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Connection conn=null;
		try{
			conn = TeeDbUtility.getConnection();
			//获取数据
			DbUtils dbUtils = new DbUtils(conn);
			String sql = "insert into person(delete_status,"
					+ ")";
			
		}catch(Exception ex){
			TeeDbUtility.rollback(conn);
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		return json;
	}
	
}
