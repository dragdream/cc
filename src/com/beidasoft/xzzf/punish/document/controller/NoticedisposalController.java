package com.beidasoft.xzzf.punish.document.controller;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.document.bean.DocNoticedisposal;
import com.beidasoft.xzzf.punish.document.model.NoticedisposalModel;
import com.beidasoft.xzzf.punish.document.service.ArticlesMainService;
import com.beidasoft.xzzf.punish.document.service.NoticedisposalService;
import com.beidasoft.xzzf.punish.document.service.SamplingevidencesService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/noticedisposalCtrl")
public class NoticedisposalController {

	@Autowired
	private TeeWenShuService wenShuService;
	
	@Autowired
	private NoticedisposalService noticedisposalService;
	
	@Autowired
	private ArticlesMainService articlesMainService;
	
	@Autowired
	private SamplingevidencesService samplingevidencesService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 抽样取证物品处理通知书
	 * @param docNoticedisposalModel
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("/saveDocInfo")
	public TeeJson save(NoticedisposalModel noticedisposalModel, HttpServletRequest request) throws Exception {

		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		
		// 实例化实体类对象
		DocNoticedisposal docNoticedisposal = new DocNoticedisposal();
		
		// 属性值传递
		BeanUtils.copyProperties(noticedisposalModel, docNoticedisposal);

		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(noticedisposalModel.getStartDataStr())) {
			docNoticedisposal.setStartData(TeeDateUtil.format(noticedisposalModel.getStartDataStr(),
					"yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(noticedisposalModel.getEndDataStr())) {
			docNoticedisposal.setEndData(TeeDateUtil.format(noticedisposalModel.getEndDataStr(),
					 "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(noticedisposalModel.getStampDateStr())) {
			docNoticedisposal.setStampDate(TeeDateUtil.format(noticedisposalModel.getStampDateStr(), 
					 "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(noticedisposalModel.getSendDateStr())) {
			docNoticedisposal.setSendDate(TeeDateUtil.format(noticedisposalModel.getSendDateStr(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(noticedisposalModel.getReceiptDateStr())) {
			docNoticedisposal.setReceiptDate(TeeDateUtil.format(noticedisposalModel.getReceiptDateStr(), 
					"yyyy年MM月dd日 HH时mm分"));
		}

		// 设置创建人相关信息
		if (StringUtils.isBlank(noticedisposalModel.getId())) {
			docNoticedisposal.setId(UUID.randomUUID().toString());
			docNoticedisposal.setCreateUserId(user.getUserId());
			docNoticedisposal.setCreateUserName(user.getUserName());
			docNoticedisposal.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建抽样取证物品处理通知书");
		} else {
			//设置创建人相关信息
			docNoticedisposal.setUpdateUserId(user.getUserId());
			docNoticedisposal.setUpdateUserName(user.getUserName());
			docNoticedisposal.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改抽样取证物品处理通知书");
		}
		
		docNoticedisposal.setDelFlg("0");
		// 保存 抽样取证物品处理通知书
		noticedisposalService.save(docNoticedisposal,request);

		json.setRtData(docNoticedisposal);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 获取单个抽样取证物品处理通知书（通过主键ID
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDocInfo")
	public TeeJson get(String id, HttpServletRequest request) {

		TeeJson json = new TeeJson();
		
		DocNoticedisposal docNoticedisposal = noticedisposalService.getById(id);
		
		NoticedisposalModel noticedisposalModel = new NoticedisposalModel();
		
		BeanUtils.copyProperties(docNoticedisposal, noticedisposalModel);

		// 单独处理时间类型转换
		if (docNoticedisposal.getStartData() != null) {
			noticedisposalModel.setStartDataStr(TeeDateUtil.format(docNoticedisposal.getStartData(), 
					"yyyy年MM月dd日"));
		}
		if (docNoticedisposal.getEndData() != null) {
			noticedisposalModel.setEndDataStr(TeeDateUtil.format(docNoticedisposal.getEndData(), 
					"yyyy年MM月dd日"));
		}
		if (docNoticedisposal.getStampDate() != null) {
			noticedisposalModel.setStampDateStr(TeeDateUtil.format(docNoticedisposal.getStampDate(), 
					"yyyy年MM月dd日"));
		}
		if (docNoticedisposal.getSendDate() != null) {
			noticedisposalModel.setSendDateStr(TeeDateUtil.format(docNoticedisposal.getSendDate(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docNoticedisposal.getReceiptDate() != null) {
			noticedisposalModel.setReceiptDateStr(TeeDateUtil.format(docNoticedisposal.getReceiptDate(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		json.setRtData(noticedisposalModel);
		json.setRtState(true);
		return json;
	}
	
	
	
}
