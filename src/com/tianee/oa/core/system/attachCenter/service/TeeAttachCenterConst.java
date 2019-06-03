package com.tianee.oa.core.system.attachCenter.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tianee.oa.oaconst.TeeAttachmentModelKeys;

public class TeeAttachCenterConst {

	public static List<Map<String, String>> getAttachListMap() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> Attachmap = getAttachAttachMaps();
		list.add(Attachmap);
		return list;
	}

	/**
	 * @function: 获取所有附件模块
	 * @author: wyw
	 * @data: 2014年10月12日
	 * @return Map<String,String>
	 */
	public static Map<String, String> getAttachAttachMaps() {
		Map<String, String> Attachmap = new LinkedHashMap<String, String>();
		Attachmap.put(TeeAttachmentModelKeys.SYSTEM, "系统");
		Attachmap.put(TeeAttachmentModelKeys.workFlow, "工作流公共附件");
		Attachmap.put(TeeAttachmentModelKeys.workFlowFeedBack, "工作流会签");
		Attachmap.put(TeeAttachmentModelKeys.workFlowDoc, "工作流文档");
		Attachmap.put(TeeAttachmentModelKeys.imgupload, "编辑器图片");
		Attachmap.put(TeeAttachmentModelKeys.PERSON, "人员信息模块");
		Attachmap.put(TeeAttachmentModelKeys.TMP, "临时文件");
		Attachmap.put(TeeAttachmentModelKeys.diary, "工作日志");
		Attachmap.put(TeeAttachmentModelKeys.NOTIFY, "公告通知");
		Attachmap.put(TeeAttachmentModelKeys.NEWS, "新闻");
		Attachmap.put(TeeAttachmentModelKeys.EMAIL, "邮件");
		Attachmap.put(TeeAttachmentModelKeys.MEETING, "会议申请");
		Attachmap.put(TeeAttachmentModelKeys.VEHICLE, "车辆");
		Attachmap.put(TeeAttachmentModelKeys.FILE_NET_DISK, "公共网盘");
		Attachmap.put(TeeAttachmentModelKeys.FILE_NET_DISK_PERSON, "个人网盘");
		Attachmap.put(TeeAttachmentModelKeys.VOTE, "投票");
		Attachmap.put(TeeAttachmentModelKeys.WORD_MODEL, "套红模板");
		Attachmap.put(TeeAttachmentModelKeys.exam, "在线考试");
		Attachmap.put(TeeAttachmentModelKeys.assetsRecord, "固定资产");
		Attachmap.put(TeeAttachmentModelKeys.assetsInfo, "固定资产信息");
		Attachmap.put(TeeAttachmentModelKeys.DAM, "公文档案");
		Attachmap.put(TeeAttachmentModelKeys.BOOK, "图书");
		Attachmap.put(TeeAttachmentModelKeys.RECRUIT_REQUIREMENTS, "招聘需求");
		Attachmap.put(TeeAttachmentModelKeys.RECRUIT_PLAN, "招聘计划");
		Attachmap.put(TeeAttachmentModelKeys.TRAINING_PLAN, "培训计划");
		Attachmap.put(TeeAttachmentModelKeys.coWork, "任务管理");
		Attachmap.put(TeeAttachmentModelKeys.hrPool, "人力资源人才库");
		Attachmap.put(TeeAttachmentModelKeys.CRM, "客户关系管理");
		Attachmap.put(TeeAttachmentModelKeys.CRM_PRODUCTS, "产品管理");
		Attachmap.put(TeeAttachmentModelKeys.CRM_AFTER_SALE_SERV, "售后服务管理");
		Attachmap.put(TeeAttachmentModelKeys.CRM_CUSTOMER_CANTRACT, "合同管理");
		Attachmap.put(TeeAttachmentModelKeys.CRM_COMPETITOR, "CRM竞争对手");
		Attachmap.put(TeeAttachmentModelKeys.CONTRACT, "合同信息");

		return Attachmap;
	}

}
