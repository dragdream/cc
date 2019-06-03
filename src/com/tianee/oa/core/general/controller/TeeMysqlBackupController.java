package com.tianee.oa.core.general.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeeMysqlBackup;
import com.tianee.oa.core.general.service.TeeMysqlBackupService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@RequestMapping("/TeeMysqlBackupController")
public class TeeMysqlBackupController{
	@Autowired
	TeeMysqlBackupService backupService;
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) throws Exception {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return backupService.datagrid(dm, requestDatas);
	}
	
	
	@RequestMapping("/backSql")
	@ResponseBody
	public TeeJson backSql(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String dataName = TeeSysProps.getProps().getProperty("dialect");
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
		if(sid>0){
			if(dataName.equals("mysql")){
				TeeMysqlBackup backup = backupService.getById(sid);
				String mysqlBinPath = TeeSysProps.getRootPath()+"../mysql/bin/mysql";
				//String mysqlBinPath = "C:\\Program Files (x86)\\MySQL\\MySQL Server 5.5\\bin\\mysql";
				String mysqlPassword=TeeSysProps.getProps().getProperty("db_password");
				String mysqlUser=TeeSysProps.getProps().getProperty("db_username");
				String backupFilePath = backup.getPath();
				String command = "cmd /c \""+mysqlBinPath+"\" -u "+mysqlUser+" --password="+mysqlPassword+" oaop < "+backupFilePath;
				try {
					Process p = Runtime.getRuntime().exec(command);
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream(),"GBK"));
					String line = null;
					while((line=bufferedReader.readLine())!=null){
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				json.setRtState(true);
				json.setRtMsg("数据库还原成功！");
			}else{
				json.setRtState(false);
				json.setRtMsg("非mysql数据库，还原失败！");
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("没有找到相关备份数据！");
		}
		return json;
	}
}