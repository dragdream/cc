package com.beidasoft.xzfy.homePage.service;
/**
 * sql语句常量
 * @author Henry
 * @Date 2019/04/30
 */
public class SqlConstant {

	//案件状态统计
	public static final String CASE_STATUS_STATS_SQL = 
	"select CASE_STATUS_CODE ,CASE_STATUS,count(*) "+
	"from fy_case_handling "+
	"where 1=1 "+
	//"and DEAL_MAN1_ID = ? "+
	"and APPLICATION_DATE > ? "+
	"group by CASE_STATUS_CODE ,CASE_STATUS "
	;
	
	//案件受理情况统计
	public static final String CASE_ACCEPTED_STATS_SQL = 
			  "select sum(case when accept_status='01' then 1 else 0 end) as total01,--立案受理 "+
					"\n sum(case when accept_status='02' then 1 else 0 end) as total02,--不予受理       "+
					"\n sum(case when accept_status='03' then 1 else 0 end) as total03,--补正           "+
					"\n sum(case when accept_status='04' then 1 else 0 end) as total04,--告知           "+
					"\n sum(case when accept_status='05' then 1 else 0 end) as total05,--转送           "+
					"\n sum(case when accept_status='06' then 1 else 0 end) as total06,--其他           "+
					"\n sum(case when accept_status='07' then 1 else 0 end) as total07--受理前撤回      "+
					"\n from (                                                                           "+
					"\n select case_id,                                                                  "+
					//"--一个案件只取一次受理状态                                                       "+
					"\n max(deal_result) as accept_status                                                "+
					"\n from fy_process_flow t                                                           "+
					"\n where 1=1 "+
					//"and t.created_user= ?  "+
					"\n and t.process_item='03' --受理环节                                               "+
					"\n group by case_id ) tt   ";
	
	//案件结案情况统计
	public static final String CASE_CLOSED_STATS_SQL = 
			   "select sum(case when SETTLE_TYPE_CODE='01' then 1 else 0 end) as total01,--驳回        "+
					"\n  sum(case when SETTLE_TYPE_CODE='02' then 1 else 0 end) as total02,--维持             "+
					"\n  sum(case when SETTLE_TYPE_CODE='03' then 1 else 0 end) as total03,--确认违法         "+
					"\n  sum(case when SETTLE_TYPE_CODE='04' then 1 else 0 end) as total04,--撤销             "+
					"\n  sum(case when SETTLE_TYPE_CODE='05' then 1 else 0 end) as total05,--变更             "+
					"\n  sum(case when SETTLE_TYPE_CODE='06' then 1 else 0 end) as total06,--责令履行         "+
					"\n  sum(case when SETTLE_TYPE_CODE='07' then 1 else 0 end) as total07,--终止-调解(和解)  "+
					"\n  sum(case when SETTLE_TYPE_CODE='08' then 1 else 0 end) as total08,--终止-其他        "+
					"\n  sum(case when SETTLE_TYPE_CODE='99' then 1 else 0 end) as total99 --其他             "+
					"\n from fy_case_handling t                                                                "+
					"\n where 1=1                                                                              "+
					"\n and APPLICATION_DATE > ? "+
					//"and t.deal_man1_id = ?                                                          "+
					"\n and t.case_status_code>='04' --结案以及之后的环节                                                        "+
					"\n and t.settle_type_code is not null ";
}
