package com.tianee.oa.util.workflow;

import com.tianee.webframe.util.global.TeeSysProps;


public class TeeColumnType {
	public final static int VARCHAR=1;
	public final static int TEXT=2;
	public final static int DECIMAL=3;
	public final static int TIMESTAMP=4;
	public final static int INTEGER=5;
	public final static int DATETIME=6;
	
	
	/**
	 * 根据数据库方言，获取列类型
	 * @param columnType
	 * @return
	 */
	public static String getColumnType(int columnType){
		String dialect = TeeSysProps.getString("dialect");
		String columnTypeStr = null;
		if("oracle".equals(dialect)){
			switch(columnType){
			case VARCHAR:
				columnTypeStr = "varchar2(255)";
				break;
			case TEXT:
				columnTypeStr = "clob";
				break;
			case DECIMAL:
				columnTypeStr = "number(11,3)";
				break;
			case TIMESTAMP:
				columnTypeStr = "timestamp";
				break;
			case INTEGER:
				columnTypeStr = "number";
				break;
			case DATETIME:
				columnTypeStr = "date";
				break;
			}
		}else if("mysql".equals(dialect)){
			switch(columnType){
			case VARCHAR:
				columnTypeStr = "varchar(255)";
				break;
			case TEXT:
				columnTypeStr = "longtext";
				break;
			case DECIMAL:
				columnTypeStr = "decimal(11,3)";
				break;
			case TIMESTAMP:
				columnTypeStr = "timestamp";
				break;
			case INTEGER:
				columnTypeStr = "int(11)";
				break;
			case DATETIME:
				columnTypeStr = "datetime";
				break;
			}
		}else if("sqlserver".equals(dialect)){
			switch(columnType){
			case VARCHAR:
				columnTypeStr = "varchar(255)";
				break;
			case TEXT:
				columnTypeStr = "text";
				break;
			case DECIMAL:
				columnTypeStr = "decimal(11,3)";
				break;
			case TIMESTAMP:
				columnTypeStr = "timestamp";
				break;
			case INTEGER:
				columnTypeStr = "int";
				break;
			case DATETIME:
				columnTypeStr = "datetime";
				break;
			}
		}else if("dameng".equals(dialect)){
			switch(columnType){
			case VARCHAR:
				columnTypeStr = "varchar(255)";
				break;
			case TEXT:
				columnTypeStr = "clob";
				break;
			case DECIMAL:
				columnTypeStr = "decimal(11,3)";
				break;
			case TIMESTAMP:
				columnTypeStr = "datetime";
				break;
			case INTEGER:
				columnTypeStr = "int";
				break;
			case DATETIME:
				columnTypeStr = "datetime";
				break;
			}
		}else if("kingbase".equals(dialect)){
			switch(columnType){
			case VARCHAR:
				columnTypeStr = "varchar(255)";
				break;
			case TEXT:
				columnTypeStr = "clob";
				break;
			case DECIMAL:
				columnTypeStr = "decimal(11,3)";
				break;
			case TIMESTAMP:
				columnTypeStr = "TIMESTAMP (6) WITH TIME ZONE";
				break;
			case INTEGER:
				columnTypeStr = "int";
				break;
			case DATETIME:
				columnTypeStr = "datetime";
				break;
			}
		}
		return columnTypeStr;
	}
}
