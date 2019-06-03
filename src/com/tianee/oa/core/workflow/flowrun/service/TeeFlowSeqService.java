package com.tianee.oa.core.workflow.flowrun.service;

import java.sql.Connection;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.stereotype.Service;

import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeFlowSeqService extends TeeBaseService implements TeeFlowSeqServiceInterface{
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowSeqServiceInterface#getFlowTypeNumbering(int)
	 */
	@Override
	public int getFlowTypeNumbering(int flowId){
		int counting = 0;
		String dialect = TeeSysProps.getString("dialect");
		Connection conn = TeeDbUtility.getConnection(null);
		DbUtils dbUtils = new DbUtils(conn);
		Map data = null;
		try {
			if("mysql".equals(dialect)){
				data = dbUtils.queryToMap("select max(SEQ_ID) as MAX_SEQ_ID from FLOW_SEQ_"+flowId);
			}else if("sqlserver".equals(dialect)){
				data = dbUtils.queryToMap("select max(SEQ_ID) as MAX_SEQ_ID from FLOW_SEQ_"+flowId);
			}else if("oracle".equals(dialect)){//
				data = dbUtils.queryToMap("select FLOW_SEQ_"+flowId+".currval as MAX_SEQ_ID from dual");
			}else if("dameng".equals(dialect)){//
				data = dbUtils.queryToMap("select FLOW_SEQ_"+flowId+".currval as MAX_SEQ_ID from dual");
			}else if("kingbase".equals(dialect)){//
				data = dbUtils.queryToMap("select FLOW_SEQ_"+flowId+".currval as MAX_SEQ_ID from dual");
			}
			counting = TeeStringUtil.getInteger(data.get("MAX_SEQ_ID"), 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		return counting==0?1:counting;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowSeqServiceInterface#deleteFlowTypeNumbering(int)
	 */
	@Override
	public void deleteFlowTypeNumbering(int flowId){
		String dialect = TeeSysProps.getString("dialect");
		Connection conn = TeeDbUtility.getConnection(null);
		DbUtils dbUtils = new DbUtils(conn);
		try {
			if("mysql".equals(dialect)){
				dbUtils.executeUpdate("drop table FLOW_SEQ_"+flowId);
			}else if("sqlserver".equals(dialect)){
				dbUtils.executeUpdate("drop table FLOW_SEQ_"+flowId);
			}else if("oracle".equals(dialect)){
				dbUtils.executeUpdate("drop sequence FLOW_SEQ_"+flowId);
			}else if("dameng".equals(dialect)){
				dbUtils.executeUpdate("drop sequence FLOW_SEQ_"+flowId);
			}else if("kingbase".equals(dialect)){
				dbUtils.executeUpdate("drop sequence FLOW_SEQ_"+flowId);
			}
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowSeqServiceInterface#generateFlowTypeNumbering(int)
	 */
	@Override
	public int generateFlowTypeNumbering(int flowId){
		int counting = 0;
		String dialect = TeeSysProps.getString("dialect");
		Connection conn = TeeDbUtility.getConnection(null);
		DbUtils dbUtils = new DbUtils(conn);
		Object key = null;
		try {
			if("mysql".equals(dialect)){
				key = dbUtils.executeInsert("insert into FLOW_SEQ_"+flowId+"(EXT_) values(0)", dialect, null);
			}else if("sqlserver".equals(dialect)){
				key = dbUtils.executeInsert("insert into FLOW_SEQ_"+flowId+"(EXT_) values(0)", dialect, null);
			}else if("oracle".equals(dialect)){
				Map data = dbUtils.queryToMap("select FLOW_SEQ_"+flowId+".nextval as NEXTVAL from dual");
				key = data.get("NEXTVAL");
			}else if("dameng".equals(dialect)){
				Map data = dbUtils.queryToMap("select FLOW_SEQ_"+flowId+".nextval as NEXTVAL from dual");
				key = data.get("NEXTVAL");
			}else if("kingbase".equals(dialect)){
				Map data = dbUtils.queryToMap("select FLOW_SEQ_"+flowId+".nextval as NEXTVAL from dual");
				key = data.get("NEXTVAL");
			}
			conn.commit();
			counting = TeeStringUtil.getInteger(key, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
		
		return counting==0?1:counting;
	}
	
	/* (non-Javadoc)
	 * @see com.tianee.oa.core.workflow.flowrun.service.TeeFlowSeqServiceInterface#createFlowTypeNumbering(int)
	 */
	@Override
	public void createFlowTypeNumbering(int flowId){
		String dialect = TeeSysProps.getString("dialect");
		Connection conn = TeeDbUtility.getConnection(null);
		DbUtils dbUtils = new DbUtils(conn);
		try {
			if("mysql".equals(dialect)){
				dbUtils.executeUpdate("create table FLOW_SEQ_"+flowId+"(SEQ_ID int(11) primary key AUTO_INCREMENT,EXT_ int(11))");
			}else if("sqlserver".equals(dialect)){
				dbUtils.executeUpdate("create table FLOW_SEQ_"+flowId+"(SEQ_ID int primary key IDENTITY(1,1),EXT_ int)");
			}else if("oracle".equals(dialect)){
				dbUtils.executeUpdate("create sequence FLOW_SEQ_"+flowId+" MINVALUE 1 MAXVALUE 9223372036854775807 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE");
			}else if("dameng".equals(dialect)){
				dbUtils.executeUpdate("create sequence FLOW_SEQ_"+flowId+" MINVALUE 1 MAXVALUE 9223372036854775807 INCREMENT BY 1 START WITH 1 CACHE 20  NOCYCLE");
			}else if("kingbase".equals(dialect)){
				dbUtils.executeUpdate("create sequence FLOW_SEQ_"+flowId+" MINVALUE 1 MAXVALUE 9223372036854775807 INCREMENT BY 1 START WITH 1 CACHE 20  NO CYCLE");
			}
			conn.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}finally{
			TeeDbUtility.closeConn(conn);
		}
	}
}
