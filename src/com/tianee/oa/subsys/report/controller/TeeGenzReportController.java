package com.tianee.oa.subsys.report.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.general.bean.TeeSysPara;
import com.tianee.oa.core.general.service.TeeSysParaService;
import com.tianee.oa.subsys.report.bean.TeeGenzReport;
import com.tianee.oa.subsys.report.service.TeeGenzReportService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("/gezReport")
public class TeeGenzReportController {
	
	@Autowired
	private TeeGenzReportService genzReportService;
	
	@Autowired
	private TeeSysParaService paraService;
	
	@ResponseBody
	@RequestMapping("/datagrid")
	public TeeEasyuiDataGridJson datagrid(HttpServletRequest request,TeeDataGridModel dm){
		Map requestData = TeeServletUtility.getParamMap(request);
		return genzReportService.datagrid(requestData, dm);
	}
	
	@ResponseBody
	@RequestMapping("/refresh")
	public TeeJson refresh(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		genzReportService.refresh();
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/testConnect")
	public TeeJson testConnect(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		String GENZ_DB_DRIVER = TeeStringUtil.getString(request.getParameter("GENZ_DB_DRIVER"));
		String GENZ_DB_URL = TeeStringUtil.getString(request.getParameter("GENZ_DB_URL"));
		String GENZ_DB_USER = TeeStringUtil.getString(request.getParameter("GENZ_DB_USER"));
		String GENZ_DB_PWD = TeeStringUtil.getString(request.getParameter("GENZ_DB_PWD"));
		
		Connection conn = null;
		try {
            Class.forName(GENZ_DB_DRIVER);
            conn = DriverManager.getConnection(GENZ_DB_URL, GENZ_DB_USER, GENZ_DB_PWD);
		
		} catch (Exception ex) {
			ex.printStackTrace();
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
        } finally{
        	TeeDbUtility.closeConn(conn);
        }
		
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/getParams")
	public TeeJson getParams(HttpServletRequest request){
		List<TeeSysPara> list = paraService.getAllSysPara();
		TeeJson json = new TeeJson();
		Map data = new HashMap();
		for(TeeSysPara sysPara:list){
			if("GENZ_ADDR".equals(sysPara.getParaName())
					|| "GENZ_DB_DRIVER".equals(sysPara.getParaName())
					|| "GENZ_DB_URL".equals(sysPara.getParaName())
					|| "GENZ_DB_USER".equals(sysPara.getParaName())
					|| "GENZ_DB_PWD".equals(sysPara.getParaName())){
				data.put(sysPara.getParaName(), sysPara.getParaValue());
			}
		}
		json.setRtData(data);
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateParams")
	public TeeJson updateParams(HttpServletRequest request){
		TeeJson json = new TeeJson();
		json.setRtState(true);
		
		String GENZ_ADDR = TeeStringUtil.getString(request.getParameter("GENZ_ADDR"));
		String GENZ_DB_DRIVER = TeeStringUtil.getString(request.getParameter("GENZ_DB_DRIVER"));
		String GENZ_DB_URL = TeeStringUtil.getString(request.getParameter("GENZ_DB_URL"));
		String GENZ_DB_USER = TeeStringUtil.getString(request.getParameter("GENZ_DB_USER"));
		String GENZ_DB_PWD = TeeStringUtil.getString(request.getParameter("GENZ_DB_PWD"));
		
		paraService.updateSysPara("GENZ_ADDR", GENZ_ADDR);
		paraService.updateSysPara("GENZ_DB_DRIVER", GENZ_DB_DRIVER);
		paraService.updateSysPara("GENZ_DB_URL", GENZ_DB_URL);
		paraService.updateSysPara("GENZ_DB_USER", GENZ_DB_USER);
		paraService.updateSysPara("GENZ_DB_PWD", GENZ_DB_PWD);
		
		TeeSysProps.getProps().setProperty("GENZ_ADDR", GENZ_ADDR);
		TeeSysProps.getProps().setProperty("GENZ_DB_DRIVER", GENZ_DB_DRIVER);
		TeeSysProps.getProps().setProperty("GENZ_DB_URL", GENZ_DB_URL);
		TeeSysProps.getProps().setProperty("GENZ_DB_USER", GENZ_DB_USER);
		TeeSysProps.getProps().setProperty("GENZ_DB_PWD", GENZ_DB_PWD);
		
		return json;
	}
}
