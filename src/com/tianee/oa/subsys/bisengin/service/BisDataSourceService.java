package com.tianee.oa.subsys.bisengin.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.bisengin.bean.BisDataSource;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class BisDataSourceService extends TeeBaseService{
	
	
	public void add(BisDataSource bisDataSource){
		simpleDaoSupport.save(bisDataSource);
	}
	
	public void update(BisDataSource bisDataSource){
		simpleDaoSupport.update(bisDataSource);
	}
	
	public void delete(int sid){
		simpleDaoSupport.delete(BisDataSource.class,sid);
	}
	
	public BisDataSource get(int sid){
		return (BisDataSource) simpleDaoSupport.get(BisDataSource.class, sid);
	}
	
	public TeeEasyuiDataGridJson datagrid(){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String hql = "from BisDataSource";
		long total = simpleDaoSupport.count("select count(*) "+hql, null);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(simpleDaoSupport.find(hql+" order by sid asc", null));
		return dataGridJson;
	}

	
	
	/**
	 * 连接测试
	 * @param request
	 * @return
	 */
	public TeeJson testConnect(HttpServletRequest request) {
		Connection conn = null;
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			BisDataSource dataSource=(BisDataSource) simpleDaoSupport.get(BisDataSource.class,sid);
			
			try {
				Class.forName(dataSource.getDriverClass());
				conn = DriverManager.getConnection(dataSource.getDriverUrl(), dataSource.getDriverUsername(), dataSource.getDriverPwd());
			
				if(conn!=null){
					json.setRtState(true);
					json.setRtMsg("连接成功！");
				}else{
					json.setRtState(false);
					json.setRtMsg("连接失败！");
				}
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg(e.toString());
				e.printStackTrace();
			}finally{
				if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else{
			json.setRtState(false);
			json.setRtMsg("数据源不存在！");
		}
		return json;
	}

	
	/**
	 * 新建编辑页面连接测试
	 * @param request
	 * @return
	 */
	public TeeJson testConn(HttpServletRequest request) {
		Connection conn = null;
		TeeJson json=new TeeJson();
		String driverClass=TeeStringUtil.getString(request.getParameter("driverClass"));
		String driverUrl=TeeStringUtil.getString(request.getParameter("driverUrl"));
		String driverUsername=TeeStringUtil.getString(request.getParameter("driverUsername"));
		String driverPwd=TeeStringUtil.getString(request.getParameter("driverPwd"));
			
			try {
				Class.forName(driverClass);
				conn = DriverManager.getConnection(driverUrl,driverUsername, driverPwd);
			
				if(conn!=null){
					json.setRtState(true);
					json.setRtMsg("连接成功！");
				}else{
					json.setRtState(false);
					json.setRtMsg("连接失败！");
				}
			} catch (Exception e) {
				json.setRtState(false);
				json.setRtMsg(e.toString());
				e.printStackTrace();
			}finally{
				if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		return json;
	}
}
