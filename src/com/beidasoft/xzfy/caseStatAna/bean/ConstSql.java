package com.beidasoft.xzfy.caseStatAna.bean;

public class ConstSql {
	
	public static final String CASE_STAT_INFO_SQL = "select CATEGORY  ,-- 行政管理类型\n"+
			" sum(case when t1.created_time < t1.application_date then 1 else 0 end) as last_period,-- 上期结转\n"+
			" sum(case when t1.created_time < t1.application_date then 0 else 1 end) as this_period,--本期新收\n"+
			" sum(case when t1.case_status_code >= '02' then 1 else 0 end) as accepted,-- 已受理\n"+
			" sum((select count(1) from fy_applicant t2 where t2.case_id=t1.case_id)) as applicant_total,-- 申请人总数\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='08')) as respondent_total_08,-- 被申请人(乡镇政府)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='07')) as respondent_total_07,-- 被申请人(县政府部门)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='06')) as respondent_total_06,-- 被申请人(县级政府)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='05')) as respondent_total_05,-- 被申请人(市级政府部门)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='04')) as respondent_total_04,-- 被申请人(市级政府)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='03')) as respondent_total_03,-- 被申请人(省级政府部门)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='02')) as respondent_total_02,-- 被申请人(省级政府)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='01')) as respondent_total_01,-- 被申请人(国务院部门)\n"+
			" sum((select count(1) from fy_respondent t3 where t3.case_id=t1.case_id and t3.respondent_type_code='99')) as respondent_total_99,-- 被申请人(其他)\n"+
			" sum(case t5.org_level_code when '07' then 1 else 0 end) as fy_org_total_07 ,-- 复议机关(县级政府部门)\n"+
			" sum(case t5.org_level_code when '06' then 1 else 0 end) as fy_org_total_06 ,-- 复议机关(县级政府)\n"+
			" sum(case t5.org_level_code when '05' then 1 else 0 end) as fy_org_total_05 ,-- 复议机关(市级政府部门)\n"+
			" sum(case t5.org_level_code when '04' then 1 else 0 end) as fy_org_total_04 ,-- 复议机关(市级政府)\n"+
			" sum(case t5.org_level_code when '03' then 1 else 0 end) as fy_org_total_03 ,-- 复议机关(省级政府部门)\n"+
			" sum(case t5.org_level_code when '02' then 1 else 0 end) as fy_org_total_02 ,-- 复议机关(省级政府)\n"+
			" sum(case t5.org_level_code when '01' then 1 else 0 end) as fy_org_total_01 ,-- 复议机关(国务院部门)\n"+
			" 0 as fy_org_total_00 ,-- 复议机关(国务院)\n"+
			" sum(case t1.application_item_code when '01' then 1 else 0 end) as item_total_01,--申请复议事项(行政处罚)\n"+
			" sum(case t1.application_item_code when '02' then 1 else 0 end) as item_total_02,--申请复议事项(行政强制措施)\n"+
			" sum(case t1.application_item_code when '03' then 1 else 0 end) as item_total_03,--申请复议事项(行政征收)\n"+
			" sum(case t1.application_item_code when '04' then 1 else 0 end) as item_total_04,--申请复议事项(行政许可)\n"+
			" sum(case t1.application_item_code when '05' then 1 else 0 end) as item_total_05,--申请复议事项(行政确权)\n"+
			" 0 as item_total_08,--申请复议事项(行政确认)\n"+
			" 0 as item_total_09,--申请复议事项(信息公开)\n"+
			" sum(case t1.application_item_code when '06' then 1 else 0 end) as item_total_06,--申请复议事项(举报投诉处理)\n"+
			" sum(case t1.application_item_code when '07' then 1 else 0 end) as item_total_07,--申请复议事项(行政不作为)\n"+
			" sum(case t1.application_item_code when '99' then 1 else 0 end) as item_total_99,--申请复议事项(其他)\n"+
			" sum(case when t1.case_status_code>='04' then 1 else 0 end) as trial_total,-- 已审结(总计)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='01' then 1 else 0 end) as trial_total_01,-- 已审结(驳回)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='02' then 1 else 0 end) as trial_total_02,-- 已审结(维持)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='03' then 1 else 0 end) as trial_total_03,-- 已审结(确认违法)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='04' then 1 else 0 end) as trial_total_04,-- 已审结(撤销)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='05' then 1 else 0 end) as trial_total_05,-- 已审结(变更)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='06' then 1 else 0 end) as trial_total_06,-- 已审结(责令履行)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='07' then 1 else 0 end) as trial_total_07,-- 已审结(中止-调解)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='08' then 1 else 0 end) as trial_total_08,-- 已审结(中止-其他)\n"+
			" sum(case when t1.case_status_code>='04' and t1.settle_type_code='99' then 1 else 0 end) as trial_total_99,-- 已审结(其他)\n"+
			" sum(case when t1.case_status_code<'04' then 1 else 0 end) as not_trial_total,-- 未审结\n"+
			" sum(case when t1.is_document_review=1 then 1 else 0 end) as file_check_total,-- 规范文件附带检查\n"+
			" sum(case when t1.is_compensation=1 then 1 else 0 end ) as compensation_total_js,-- 行政赔偿(件数)\n"+
			" sum(case when t1.is_compensation=1 then t1.COMPENSATION_AMOUNT else 0 end ) as compensation_total_je,-- 行政赔偿(金额)\n"+
			" 0 as file_total_01,-- 行政复议意见书(制发数) @todo\n"+
			" 0 as file_total_02-- 行政复议意见书(落实数) @todo\n"+
			"  from fy_case_handling t1 \n"+
			"  inner join fy_organ t5 \n"+
			"  on t1.organ_id=t5.org_id \n"+
			" where t1.application_date >=? \n"+
			" and t1.application_date < ? \n"+
			" group by CATEGORY";

}
