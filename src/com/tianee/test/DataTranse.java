package com.tianee.test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.dbutils.DbUtils;
import org.apache.poi.hssf.converter.ExcelToHtmlUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class DataTranse {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		Connection mysqlConn = DriverManager.getConnection("jdbc:jtds:sqlserver://10.10.10.130:1433;DatabaseName=oaop","sa","qwe123!@#");

		DbUtils dbUtils = new DbUtils(mysqlConn);
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(new File("C:\\Users\\liteng\\Desktop\\BIS_VIEW.xls")));
		Sheet sheet = workbook.getSheet("BIS_VIEW");
		int rows = sheet.getRows();
		for(int i=1;i<rows;i++){
			Cell[] cells = sheet.getRow(i); 
			String id = cells[0].getContents();
			String sql = cells[7].getContents();
			dbUtils.executeUpdate("update bis_view set sql_=? where identity0='"+id+"'", new Object[]{sql});
		}
		
		//		DbUtils dbUtils = new DbUtils(mysqlConn);
//		//获取要导入的数据
//		List<Map> list = dbUtils.queryToMapList("select * from bis_test");
//		for(Map d:list){
//			TIMESTAMP timestamp = (TIMESTAMP) d.get("CR_TIME1");
//			System.out.println(timestamp.timestampValue().toString());
//		}
		//mysqlConn.commit();
//		Date date = new Date();
//		System.out.println(TeeDateUtil.parseDateByPattern("2014-12-21 12:23:23.0"));
		
	}

}
