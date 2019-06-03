package com.tianee.oa.quartzjob;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.dao.TeeSysLogDao;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.global.TeeSysProps;

@Service
public class TeeLogTimer extends TeeBaseService{
	@Autowired
	private TeeSysLogDao logDao;
	
	public void doTimmer() {
		if(TeeSysProps.getProps()==null){
			return;
		}
		
		
		String tableName = "";
		try {
			String tableDest = "SYS_LOG";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			tableName = "SYS_LOG"+"_"+sdf.format(new Date());
			String createTable  = "create table "+ tableName +" like  "+tableDest;
			String query = "insert into " + tableName +" select * from " + tableDest +" where month(time)=month(now())";
			String deleteSql = "delete from " +tableDest+" where month(time)=month(now())";
			//System.out.println(createTable);
			logDao.exectSql(createTable);
			//System.out.println(query);
			logDao.exectSql(query);
			//System.out.println(deleteSql);
			logDao.exectSql(deleteSql);
			
			/**
			 * 将归档的表明保存起来
			 */
			String iSql = "insert into archives(table_name) values('"+tableName+"')";
			logDao.exectSql(iSql);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
