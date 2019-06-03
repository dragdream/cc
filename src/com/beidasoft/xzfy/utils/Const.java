package com.beidasoft.xzfy.utils;

/**
 * 公共常量
 * @author fyj
 *
 */
public interface Const {

    public class TYPE{

    	public final static String ZERO = "0";
    	
		public final static String ONE = "1";
		
		public final static String TWO = "2";
		
		public final static String THIRD = "3";
		
		public final static String FOUR = "4";
		
		public final static String FIVE = "5";
		
		public final static String SIX = "6";
		
		public final static String SEVEN = "7";
		
		public final static String EIGHT = "8";
		
		public final static String NINE = "9";
	}

	//上传
	public class UPLOAD{
		
		//上传文件地址
		public final static String FILE_UPLOAD_URL = "/xzfy/upload/";
	}
	
	// 案件状态
	public class CASESTATUS{
		
		//草稿
		public final static String CASE_DRAFT_CODE = "00";
		public final static String CASE_DRAFT_NAME = "登记中";
		
		//案件登记
		public final static String CASE_REGISTER_CODE = "01";
		public final static String CASE_REGISTER_NAME = "已登记";
		
		//案件受理
		public final static String CASE_ACCEPTENCE_CODE = "02";
		public final static String CASE_ACCEPTENCE_NAME = "受理中";
		
		//案件审理
		public final static String CASE_TRIAL_CODE = "03";
		public final static String CASE_TRIAL_NAME = "审理中";
		
		//案件结案
		public final static String CASE_CLOSE_CODE = "04";
		public final static String CASE_CLOSE_NAME = "结案中";
		
		//案件归档
		public final static String CASE_ARCHIVE_CODE = "05";
		public final static String CASE_ARCHIVE_NAME = "归档中";
		
		//案件完结
		public final static String CASE_COMMIT_CODE = "06";
		public final static String CASE_COMMIT_NAME = "已归档";
		
	}
	
	// 登记状态
	public class REGISTERSTATUS{
		
	}
	
	// 受理状态
	public class ACCEPTENCESTATUS{
		
		//立案受理
		public final static String ACCEPTENCE_ACCEPT_CODE = "01";
		public final static String ACCEPTENCE_ACCEPT_NAME = "立案受理";
		
		//不予受理
		public final static String ACCEPTENCE_REFUSE_CODE = "02";
		public final static String ACCEPTENCE_REFUSE_NAME = "不予受理";
		
		//补正
		public final static String ACCEPTENCE_CORRECTION_CODE = "03";
		public final static String ACCEPTENCE_CORRECTION_NAME = "补正";
		
		//告知
		public final static String ACCEPTENCE_INFORM_CODE = "04";
		public final static String ACCEPTENCE_INFORM_NAME = "告知";
		
		//转送
		public final static String ACCEPTENCE_FORWARD_CODE = "05";
		public final static String ACCEPTENCE_FORWARD_NAME = "转送";
		
		//其他
		public final static String ACCEPTENCE_OTHER_CODE = "06";
		public final static String ACCEPTENCE_OTHER_NAME = "其他";
		
		//受理前撤回
		public final static String ACCEPTENCE_RECALL_CODE = "07";
		public final static String ACCEPTENCE_RECALL_NAME = "受理前撤回";
		
	}
	
	//审理状态
	public class TRIALSTATUS{
		
	}
	
	//结案状态
	public class CLOSESTATUS{
		
	}
	
	//归档状态
	public class ARCHIVESTATUS{
		//正卷归档
		public final static String ARCHIVE_POSITIVE_CODE = "01";
		public final static String ARCHIVE_POSITIVE_NAME = "正卷归档";
		
		//副卷归档
		public final static String ARCHIVE_SECONDARY_CODE = "02";
		public final static String ARCHIVE_SECONDARY_NAME = "副卷归档";
	}
	
	//案件填报
	public class CASEFILL{
		
		//填报中
		public final static String FILL = "07";
		public final static String FILL_NAME = "填报中";
		//已填报
		public final static String FILL_TRIAL = "09";
		public final static String FILL_TRIAL_NAME = "已填报";
		//已提交
		public final static String FILL_COMIT = "10";	
		public final static String FILL_COMIT_NAME = "已提交";
		//退回
		public final static String FILL_RETURN = "08";
		public final static String FILL_RETURN_NAME = "退回";
	}
	
	//性别
	public class SEX{
		//男
		public final static String MAN = "01";
		//女
		public final static String WOMAN = "02";
		//其他
		public final static String OTHER = "99";
	}
	
	//结案类型
	public class SETTLETYPE{
		//驳回(复议请求)
		public final static String REJECT_CODE ="01";
		public final static String REJECT_NAME ="驳回(复议请求)";
		
		//维持(原具体行政行为)
		public final static String MAINTAIN_CODE = "02";
		public final static String MAINTAIN_NAME = "维持(原具体行政行为)";
		
		//终止(调解（和解）&其他)
		public final static String mediate_code = "03";
		public final static String mediate_name = "终止(调解（和解）&其他)";
		//确认违法
		public final static String ILLEGAL_CODE = "04";
		public final static String ILLEGAL_NAME = "确认违法";
		//责令履行
		public final static String PERFORM_CODE = "05";
		public final static String PERFORM_NAME = "责令履行";
		//撤销
		public final static String REVOKE_CODE = "06";
		public final static String REVOKE_NAME = "撤销";
		//变更
		public final static String CHANGE_CODE = "07";
		public final static String CHANGE_NAME = "变更";
		//其他
		public final static String OTHER_CODE = "08";
		public final static String OTHER_NAME = "其他";
	}
	//文件类型
	public class FILETYPE{
		//登记模块-申请人材料
		public final static String REGISTER_APPLY = "register_apply";
		//登记模块-被申请人材料
		public final static String REGISTER_RESP = "register_resp";
		//登记模块-第三人材料
		public final static String REGISTER_THIRD = "register_third";
		//登记模块-复议机关材料
		public final static String REGISTER_ORGAN = "register_organ";
		
		//受理模块
		
		
		//审理模块-调查管理
		public final static String  TRIAL_INVESTIGATION = "trial_investigation";
		//审理模块-听证管理
		public final static String  TRIAL_CASEHEARING= "trial_casehearing";
		//审理模块-集体讨论会
		public final static String  TRIAL_CASEDISCUSSION= "trial_casediscussion";
		//审理模块中止
		public final static String  TRIAL_BREAK= "trial_break";
		//审理模块-恢复
		public final static String  TRIAL_RECOVERY= "trial_recovery";
		//审理模块-延期
		public final static String  TRIAL_DELAY= "trial_delay";
		
		//结案模块
		
		
		
		//归档材料
		public final static String ARCHIVE_NAME="ARCHIVE_NAME";
	}
}
