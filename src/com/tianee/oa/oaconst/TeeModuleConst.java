package com.tianee.oa.oaconst;

import java.util.LinkedHashMap;
import java.util.Map;

public class TeeModuleConst {
	/**
	 * 模块类别编码
	 */
	public static Map<String,String> MODULE_SORT_TYPE = new LinkedHashMap();
	public static Map<String,String> MODULE_SORT_TYPE_DETAIL = new LinkedHashMap();
	public static Map<String,String> MODULE_SORT_WX_APP_ID = new LinkedHashMap();
	public static Map<String,String> MODULE_SORT_WX_SECRET = new LinkedHashMap();
	public static Map<String,String> MODULE_SORT_DD_APP_ID = new LinkedHashMap();
	public static Map<String,String> MODULE_SORT_URL = new LinkedHashMap();
	public static Map<String,String> MODULE_SORT_COLOR = new LinkedHashMap();
	static{
//		MODULE_SORT_TYPE.put("000", "系统运行日志");
//		MODULE_SORT_TYPE.put("002", "部门管理");
//		MODULE_SORT_TYPE.put("003", "人员管理");
//		MODULE_SORT_TYPE.put("004", "角色管理");
//		MODULE_SORT_TYPE.put("005", "模块权限");
//		MODULE_SORT_TYPE.put("006", "工作流");
//		MODULE_SORT_TYPE.put("007", "桌面模块");
//		MODULE_SORT_TYPE.put("008", "菜单管理");
//		MODULE_SORT_TYPE.put("009", "系统代码");
//		MODULE_SORT_TYPE.put("010", "系统安全与设置");
//		MODULE_SORT_TYPE.put("011", "界面设置");
//		MODULE_SORT_TYPE.put("012", "印章管理");
//		MODULE_SORT_TYPE.put("013", "访问控制");
//		MODULE_SORT_TYPE.put("014", "资源管理");
//		MODULE_SORT_TYPE.put("015", "系统日志");
//		MODULE_SORT_TYPE.put("016", "内部短信");
//		MODULE_SORT_TYPE.put("017", "消息管理");
//		MODULE_SORT_TYPE.put("018", "工作日志");
//		MODULE_SORT_TYPE.put("019", "内部邮件");
//		MODULE_SORT_TYPE.put("020", "新闻管理");
//		MODULE_SORT_TYPE.put("021", "公告通知");
//		MODULE_SORT_TYPE.put("022", "日程安排");
//		MODULE_SORT_TYPE.put("023", "考勤管理");
//		MODULE_SORT_TYPE.put("024", "公共文件柜");
//		MODULE_SORT_TYPE.put("025", "个人文件柜");
//		MODULE_SORT_TYPE.put("026", "个人通讯簿");
//		MODULE_SORT_TYPE.put("027", "公共通讯簿");
//		MODULE_SORT_TYPE.put("028", "办公用品");
//		MODULE_SORT_TYPE.put("029", "投票管理");
//		MODULE_SORT_TYPE.put("030", "固定资产");
//		MODULE_SORT_TYPE.put("031", "会议管理");
//		MODULE_SORT_TYPE.put("032", "车辆管理");
//		MODULE_SORT_TYPE.put("033", "人事管理");
//		MODULE_SORT_TYPE.put("034", "考核绩效");
//		MODULE_SORT_TYPE.put("035", "我的任务");
//		
//		
//		
//		
//		
//		
//		
//		
//		MODULE_SORT_TYPE_DETAIL.put("000A", "启动日志");
//		MODULE_SORT_TYPE_DETAIL.put("002A", "添加部门");
//		MODULE_SORT_TYPE_DETAIL.put("002B", "修改部门");
//		MODULE_SORT_TYPE_DETAIL.put("002C", "删除部门");
//		MODULE_SORT_TYPE_DETAIL.put("003A", "添加人员");
//		MODULE_SORT_TYPE_DETAIL.put("003B", "修改人员");
//		MODULE_SORT_TYPE_DETAIL.put("003C", "删除人员");
//		MODULE_SORT_TYPE_DETAIL.put("003D", "清空密码");
//		MODULE_SORT_TYPE_DETAIL.put("003E", "登录操作");
//		MODULE_SORT_TYPE_DETAIL.put("003F", "注销操作");
//		MODULE_SORT_TYPE_DETAIL.put("003G", "修改密码");
//		MODULE_SORT_TYPE_DETAIL.put("003H", "批量设置");
//		MODULE_SORT_TYPE_DETAIL.put("003I", "导入人员");
//		MODULE_SORT_TYPE_DETAIL.put("004A", "增加角色");
//		MODULE_SORT_TYPE_DETAIL.put("004B", "修改角色");
//		MODULE_SORT_TYPE_DETAIL.put("004C", "删除角色");
//		
//		/*权限组管理*/
//		MODULE_SORT_TYPE_DETAIL.put("005A", "增加菜单组");
//		MODULE_SORT_TYPE_DETAIL.put("005B", "修改菜单组");
//		MODULE_SORT_TYPE_DETAIL.put("005C", "删除菜单组");
//
//		MODULE_SORT_TYPE_DETAIL.put("006A", "创建流程定义");
//		MODULE_SORT_TYPE_DETAIL.put("006B", "修改流程定义");
//		MODULE_SORT_TYPE_DETAIL.put("006C", "删除流程定义");
//		MODULE_SORT_TYPE_DETAIL.put("006D", "复制流程定义");
//		MODULE_SORT_TYPE_DETAIL.put("006E", "创建表单");
//		MODULE_SORT_TYPE_DETAIL.put("006F", "修改表单");
//		MODULE_SORT_TYPE_DETAIL.put("006G", "删除表单");
//		MODULE_SORT_TYPE_DETAIL.put("006H", "创建表单版本");
//		MODULE_SORT_TYPE_DETAIL.put("006I", "修改表单内容");
//		MODULE_SORT_TYPE_DETAIL.put("006J", "清空流程数据");
//		
//		/*印章管理*/
//		MODULE_SORT_TYPE_DETAIL.put("012A", "添加印章");
//		MODULE_SORT_TYPE_DETAIL.put("012B", "修改印章");
//		MODULE_SORT_TYPE_DETAIL.put("012C", "删除印章");
//		
//		/*访问控制*/
//		MODULE_SORT_TYPE_DETAIL.put("013A", "添加访问控制");
//		MODULE_SORT_TYPE_DETAIL.put("013B", "修改访问控制");
//		MODULE_SORT_TYPE_DETAIL.put("013C", "删除访问控制");
//		
//		MODULE_SORT_TYPE_DETAIL.put("018A", "新建日志");
//		MODULE_SORT_TYPE_DETAIL.put("018B", "修改日志");
//		MODULE_SORT_TYPE_DETAIL.put("018C", "删除日志");
//		
//		/*考勤*/
//		MODULE_SORT_TYPE_DETAIL.put("023A", "外出申请");
//		MODULE_SORT_TYPE_DETAIL.put("023B", "外出归来");
//		MODULE_SORT_TYPE_DETAIL.put("023C", "编辑外出申请");
//		MODULE_SORT_TYPE_DETAIL.put("023D", "删除外出申请");
//		MODULE_SORT_TYPE_DETAIL.put("023E", "请假申请");
//		MODULE_SORT_TYPE_DETAIL.put("023F", "申请销假");
//		MODULE_SORT_TYPE_DETAIL.put("023G", "编辑请假申请");
//		MODULE_SORT_TYPE_DETAIL.put("023H", "删除请假申请");
//		MODULE_SORT_TYPE_DETAIL.put("023I", "出差申请");
//		MODULE_SORT_TYPE_DETAIL.put("023J", "出差归来");
//		MODULE_SORT_TYPE_DETAIL.put("023K", "编辑出差申请");
//		MODULE_SORT_TYPE_DETAIL.put("023L", "删除出差申请");
//		MODULE_SORT_TYPE_DETAIL.put("023M", "考勤审批");
//		MODULE_SORT_TYPE_DETAIL.put("023N", "创建审批规则");
//		MODULE_SORT_TYPE_DETAIL.put("023O", "编辑审批规则");
//		MODULE_SORT_TYPE_DETAIL.put("023P", "删除审批规则");
//	
//
//
//		/*公共文件柜*/
//		MODULE_SORT_TYPE_DETAIL.put("024A", "批量下载");
//		MODULE_SORT_TYPE_DETAIL.put("024B", "上传文件");
//		MODULE_SORT_TYPE_DETAIL.put("024C", "新建文件夹");
//		MODULE_SORT_TYPE_DETAIL.put("024D", "删除文件");
//		MODULE_SORT_TYPE_DETAIL.put("024E", "复制文件");
//		MODULE_SORT_TYPE_DETAIL.put("024F", "移动文件");
//		MODULE_SORT_TYPE_DETAIL.put("024G", "重命名文件");
//		
//		/*公共通讯录管理*/
//		MODULE_SORT_TYPE_DETAIL.put("027A", "新增公共通讯薄分组");
//		MODULE_SORT_TYPE_DETAIL.put("027B", "清空公共通讯薄信息");
//		MODULE_SORT_TYPE_DETAIL.put("027C", "编辑公共通讯薄分组");
//		MODULE_SORT_TYPE_DETAIL.put("027D", "删除公共通讯薄分组");
//		MODULE_SORT_TYPE_DETAIL.put("027E", "新建公共通讯薄联系人");
//		MODULE_SORT_TYPE_DETAIL.put("027F", "修改公共通讯薄联系人");
//		MODULE_SORT_TYPE_DETAIL.put("027G", "删除公共通讯薄联系人");
//		
//
//		/*投票管理*/
//		MODULE_SORT_TYPE_DETAIL.put("029A", "新建投票");
//		MODULE_SORT_TYPE_DETAIL.put("029B", "编辑投票");
//		MODULE_SORT_TYPE_DETAIL.put("029C", "删除投票");
//	
//		/*固定资产管理*/
//		MODULE_SORT_TYPE_DETAIL.put("030D", "添加固定资产");
//		MODULE_SORT_TYPE_DETAIL.put("030E", "修改固定资产");
//		MODULE_SORT_TYPE_DETAIL.put("030F", "删除固定资产");
//		
//		/*会议*/
//		MODULE_SORT_TYPE_DETAIL.put("031A", "会议申请");
//		MODULE_SORT_TYPE_DETAIL.put("031B", "修改会议");
//		MODULE_SORT_TYPE_DETAIL.put("031C", "删除会议");
//		MODULE_SORT_TYPE_DETAIL.put("031D", "会议审批");
//		MODULE_SORT_TYPE_DETAIL.put("031E", "新建会议室");
//		MODULE_SORT_TYPE_DETAIL.put("031F", "修改会议室");
//		MODULE_SORT_TYPE_DETAIL.put("031G", "删除会议室");
		
		
	}
	
}
