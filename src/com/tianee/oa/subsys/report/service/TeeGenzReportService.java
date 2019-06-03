package com.tianee.oa.subsys.report.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.report.bean.TeeGenzReport;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.exps.TeeOperationException;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeGenzReportService extends TeeBaseService{
	
	public TeeEasyuiDataGridJson datagrid(Map request,TeeDataGridModel dm){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		int resId = TeeStringUtil.getInteger(request.get("resId"),0);
		String resName = TeeStringUtil.getString(request.get("resName"));
		
		String hql = "from TeeGenzReport where 1=1 ";
		if(resId!=0){
			hql+=" and resId="+resId;
		}
		if(!"".equals(resName)){
			hql+=" and resName like '%"+TeeDbUtility.formatString(resName)+"%'";
		}
		
		List list = simpleDaoSupport.pageFind(hql+" order by resId asc", dm.getFirstResult(), dm.getRows(), null);
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setRows(list);
		dataGridJson.setTotal(total);
		
		return dataGridJson;
	}
	
	public void refresh(){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
            Class.forName(TeeSysProps.getString("GENZ_DB_DRIVER"));
            conn = DriverManager.getConnection(TeeSysProps.getString("GENZ_DB_URL"), TeeSysProps.getString("GENZ_DB_USER"), TeeSysProps.getString("GENZ_DB_PWD"));
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select res_id,res_name,res_type,res_order from t_res where res_type in (18,10008,4,8)");
            
            simpleDaoSupport.executeUpdate("delete from TeeGenzReport", null);
            
            TeeGenzReport genzReport = null;
            while(rs.next()){
            	genzReport = new TeeGenzReport();
            	genzReport.setResId(rs.getInt("res_id"));
            	genzReport.setResName(rs.getString("res_name"));
            	genzReport.setResOrder(rs.getInt("res_order"));
            	genzReport.setResType(rs.getInt("res_type"));
            	simpleDaoSupport.save(genzReport);
            }
		
		} catch (Exception ex) {
			throw new TeeOperationException(ex.getMessage());
        }  finally{
        	TeeDbUtility.close(stmt, rs);
        	TeeDbUtility.closeConn(conn);
        }
	}
}
