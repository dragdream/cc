package com.beidasoft.xzzf.clue.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.clue.bean.Clue;
import com.beidasoft.xzzf.clue.bean.ClueCodeDetail;
import com.beidasoft.xzzf.clue.bean.ClueLeaderOpinion;
import com.beidasoft.xzzf.clue.bean.ClueReply;
import com.beidasoft.xzzf.clue.model.ClueLeaderOpinionModel;
import com.beidasoft.xzzf.clue.model.ClueModel;
import com.beidasoft.xzzf.clue.model.ClueReplyModel;
import com.beidasoft.xzzf.clue.service.ClueCodeDetailService;
import com.beidasoft.xzzf.clue.service.ClueInformerService;
import com.beidasoft.xzzf.clue.service.ClueLeaderOpinionService;
import com.beidasoft.xzzf.clue.service.ClueReplyService;
import com.beidasoft.xzzf.clue.service.ClueService;
import com.beidasoft.xzzf.common.bean.Region;
import com.beidasoft.xzzf.common.service.ClueRegionService;
import com.beidasoft.xzzf.common.service.CodeService;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.service.CaseAppointedInfoService;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("clueController")
public class ClueController {

	@Autowired
	private ClueService clueService;

	@Autowired
	private ClueInformerService informerService;

	@Autowired
	private TeeAttachmentService attachmentService;

	@Autowired
	private ClueCodeDetailService codeDetailService;

	@Autowired
	private ClueRegionService regionService;

	@Autowired
	private ClueLeaderOpinionService opinionService;

	@Autowired
	private CaseAppointedInfoService appointedService;

	@Autowired
	private ClueReplyService clueReplyService;

	@Autowired
	private CodeService codeService;

	@Autowired
	private PunishBaseService baseService;

	@Autowired
	private DocWenhaoService wenhaoService;

	/**
	 * 保存线索信息和举报人信息
	 * 
	 * @param clueModel
	 * @param attaches
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(ClueModel clueModel, String attaches, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Clue clue = new Clue();

		// 保存线索信息
		clue = clueService.save(clueModel, request);

		// 处理所带附件
		if (!attaches.isEmpty() && attaches != null) {
			String sp[] = attaches.split(",");
			for (String attachId : sp) {
				TeeAttachment attachment = attachmentService.getById(Integer
						.parseInt(attachId));
				attachment.setModelId(clue.getId() + "");
				attachmentService.addAttachment(attachment);
			}
		}

		json.setRtData(clue);
		json.setRtState(true);

		return json;
	}

	/**
	 * 更新线索和联系人信息
	 * 
	 * @param clueModel
	 * @param attaches
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(ClueModel clueModel, String attaches, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		Clue clue = new Clue();

		// 实例化实体类对象

		// 处理所带附件
		if (!attaches.isEmpty() && attaches != null) {
			String sp[] = attaches.split(",");
			for (String attachId : sp) {
				TeeAttachment attachment = attachmentService.getById(Integer.parseInt(attachId));
				attachment.setModelId(clue.getId() + "");
				attachmentService.updateAttachment(attachment);
			}
		}

		// 保存线索相关信息
		clue = clueService.update(clueModel, request);

		json.setRtData(clue);
		json.setRtState(true);

		return json;
	}

	/**
	 * 通过ID获取一个线索对象返回JSON类型
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();

		// 通过ID 获取线索信息
		Clue clue = clueService.get(id);
		ClueModel clueModel = clueService.beanToModel(clue);

		json.setRtData(clueModel);
		return json;
	}

	/**
	 * 个人界面的分页方法
	 * 
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,
			ClueModel queryModel, HttpServletRequest request) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);
		// 通过分页获取线索信息数据的List
		long total = clueService.getTotal(queryModel, user);

		List<ClueModel> modelList = new ArrayList<ClueModel>();
		List<Clue> clues = clueService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel, user);

		for (Clue clue : clues) {

			ClueModel clueModel = new ClueModel();
			BeanUtils.copyProperties(clue, clueModel);

			// 单独处理时间
			clueModel.setCreateTimeStr(TeeDateUtil.format(clue.getCreateTime(),
					"yyyy年MM月dd日"));
			clueModel.setReportDateStr(TeeDateUtil.format(clue.getReportDate(),
					"yyyy年MM月dd日"));
			clueModel.setDocumentDateStr(TeeDateUtil.format(
					clue.getDocumentDate(), "yyyy年MM月dd日"));

			List<ClueCodeDetail> TypeList = codeDetailService.findTypeList();
			clueModel.setTypeList(TypeList);

			if ("04".equals(clue.getReportForm())) {
				List<Region> proviceList = regionService.findProviceList();
				clueModel.setProvinceList(proviceList);
				List<Region> cityList = regionService.findCityList(clue
						.getAnotherProvince());
				clueModel.setCityList(cityList);
			} else {
				List<ClueCodeDetail> FormList = codeDetailService
						.findFormList();
				clueModel.setFormList(FormList);
				List<ClueCodeDetail> SourceList = codeDetailService
						.findSourceList(clueModel.getReportForm());
				clueModel.setSourceList(SourceList);
			}

			modelList.add(clueModel);
		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;
	}

	/**
	 * 公共池的分页方法
	 * 
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllWaitClues")
	public TeeEasyuiDataGridJson findWaitClues(TeeDataGridModel dataGridModel,
			ClueModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取线索信息数据的List
		long total = clueService.getWaitTotals(queryModel);

		List<ClueModel> modelList = new ArrayList<ClueModel>();
		List<Clue> clues = clueService.getAllWaitClues(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);

		for (Clue clue : clues) {

			ClueModel clueModel = new ClueModel();
			BeanUtils.copyProperties(clue, clueModel);

			// 单独处理时间
			clueModel.setCreateTimeStr(TeeDateUtil.format(clue.getCreateTime(),
					"yyyy年MM月dd日"));
			clueModel.setReportDateStr(TeeDateUtil.format(clue.getReportDate(),
					"yyyy年MM月dd日"));
			clueModel.setDocumentDateStr(TeeDateUtil.format(
					clue.getDocumentDate(), "yyyy年MM月dd日"));

			List<ClueCodeDetail> TypeList = codeDetailService.findTypeList();
			clueModel.setTypeList(TypeList);

			List<ClueCodeDetail> FormList = codeDetailService.findFormList();
			clueModel.setFormList(FormList);

			if ("04".equals(clue.getReportForm())) {
				List<Region> proviceList = regionService.findProviceList();
				clueModel.setProvinceList(proviceList);
				List<Region> cityList = regionService.findCityList(clue
						.getAnotherProvince());
				clueModel.setCityList(cityList);
			} else {

				List<ClueCodeDetail> SourceList = codeDetailService
						.findSourceList(clueModel.getReportForm());
				clueModel.setSourceList(SourceList);
			}

			modelList.add(clueModel);
		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;
	}

	/**
	 * 确认界面的分页方法
	 * 
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAllAdmitClues")
	public TeeEasyuiDataGridJson findAdmitClues(TeeDataGridModel dataGridModel,
			ClueModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();

		// 通过分页获取线索信息数据的List
		long total = clueService.getAdmitTotals(queryModel);

		List<ClueModel> modelList = new ArrayList<ClueModel>();
		List<Clue> clues = clueService.getAllAdmitClues(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);
		for (Clue clue : clues) {

			ClueModel clueModel = new ClueModel();
			BeanUtils.copyProperties(clue, clueModel);

			// 单独处理时间
			clueModel.setCreateTimeStr(TeeDateUtil.format(clue.getCreateTime(),
					"yyyy年MM月dd日"));
			clueModel.setReportDateStr(TeeDateUtil.format(clue.getReportDate(),
					"yyyy年MM月dd日"));
			clueModel.setDocumentDateStr(TeeDateUtil.format(
					clue.getDocumentDate(), "yyyy年MM月dd日"));

			List<ClueCodeDetail> TypeList = codeDetailService.findTypeList();
			clueModel.setTypeList(TypeList);

			if ("04".equals(clue.getReportForm())) {
				List<Region> proviceList = regionService.findProviceList();
				clueModel.setProvinceList(proviceList);
				List<Region> cityList = regionService.findCityList(clue
						.getAnotherProvince());
				clueModel.setCityList(cityList);
			} else {
				List<ClueCodeDetail> FormList = codeDetailService
						.findFormList();
				clueModel.setFormList(FormList);
				List<ClueCodeDetail> SourceList = codeDetailService
						.findSourceList(clueModel.getReportForm());
				clueModel.setSourceList(SourceList);
			}

			// 如果状态为 线索确认以后 则获取领导意见的list集合
			if (clue.getStaus() >= 20) {
				List<ClueLeaderOpinion> opinionlist = opinionService
						.getListByClueId(clue.getId());
				if (opinionlist.size() > 0) {

					List<ClueLeaderOpinionModel> opinionModelList = new ArrayList<ClueLeaderOpinionModel>();
					for (ClueLeaderOpinion c : opinionlist) {
						ClueLeaderOpinionModel model = new ClueLeaderOpinionModel();

						BeanUtils.copyProperties(c, model);
						model.setCreateTimeStr(TeeDateUtil.format(
								c.getCreateTime(), "yyyy年MM月dd日"));
						opinionModelList.add(model);
					}

					clueModel.setOpinionList(opinionModelList);
				}
			}

			modelList.add(clueModel);
		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;
	}

	/**
	 * 从公共池取线索信息到个人池进行处理
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doIt")
	public TeeJson update(String clueId, HttpServletRequest request) {
		TeeJson json = new TeeJson();

		// 获取登录人信息
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		Clue clue = clueService.get(clueId);

		int staus = clue.getStaus();
		// 如果状态为待受理（待处理）
		if (staus == 10) {
			clue.setStaus(11);
		}
		// 保存受理人信息
		clue.setAcceptUserId(user.getUuid());
		clue.setAcceptUserName(user.getUserName());

		// 执行更新操作
		clueService.update(clue);

		json.setRtState(true);

		return json;
	}

	/**
	 * 提交功能
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitClue")
	public TeeJson submitClue(String id) {
		TeeJson json = new TeeJson();

		Clue clue = clueService.get(id);
		int staus = clue.getStaus();

		if (staus == 0) {// 如果状态为保存
			clue.setStaus(10); // 设置为待受理（待处理）
		}

		else if (staus == 11) { // 如果状态为待受理（处理中）
			clue.setStaus(20);// 设置为待确认
		}

		else if (staus == 20) {// 如果状态为待确认
			clue.setStaus(40); // 设置为待审批
		}

		clueService.update(clue);

		json.setRtState(true);

		return json;
	}

	/**
	 * 受理确认环节修改线索需要提交的部分信息
	 * 
	 * @param clueModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/acceptToUpdate")
	public TeeJson acceptToUpdate(ClueModel clueModel,
			HttpServletRequest request) {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		Clue clue = clueService.get(clueModel.getId());
		int staus = clue.getStaus();

		if (staus == 11) {// 案件受理环节
			clue.setIsAccept(clueModel.getIsAccept());

			if (clueModel.getIsAccept() == 1) { // 如果确定受理
				clue.setDealDepart(clueModel.getDealDepart());
				clue.setDealTime(clueModel.getDealTime());

				if (clueModel.getDealTime() == 40) {
					clue.setDealTimeRemark(clueModel.getDealTimeRemark());
				} else {
					clue.setDealTimeRemark(0);
				}
				clue.setSubmitContent(clueModel.getSubmitContent());
			} else { // 如果不受理
				clue.setDenialReason(clueModel.getDenialReason());
			}

		} else if (staus == 20) {// 案件确认环节
			if ("1".equals(request.getParameter("isCreateAcc"))) { // 受理单界面
				clue.setDealContent(clueModel.getDealContent());
				if (StringUtils.isNotBlank(clueModel.getLeaderTimeStr())) {
					clue.setLeaderTime(TeeDateUtil.format(
							clueModel.getLeaderTimeStr(), "yyyy年MM月dd日"));
				}
				if (StringUtils.isNotBlank(clueModel.getAcceptTimeStr())) {
					clue.setAcceptTime(TeeDateUtil.format(
							clueModel.getAcceptTimeStr(), "yyyy年MM月dd日"));
				}
				clue.setLeaderContent(clueModel.getLeaderContent());
			} else { // 线索确认界面
				clue.setIsAdmit(clueModel.getIsAdmit());
				if (clueModel.getIsAdmit() == 1) { // 如果确认通过直接生成受理单
					clue.setDealDepart(clueModel.getDealDepart());
					clue.setDealTime(clueModel.getDealTime());
					if (clueModel.getDealTime() == 40) {
						clue.setDealTimeRemark(clueModel.getDealTimeRemark());
					} else {
						clue.setDealTimeRemark(0);
					}
					clue.setDealContent(clueModel.getDealContent());
				}
			}

		} else if (staus == 40) { // 如果是领导审批环节
			String prcsId = TeeStringUtil.getString(
					request.getParameter("prcsId"), "");
			if (!"".equals(prcsId)) {
				if (clueModel.getFlowRunId() != 0) { // 存runId
					clue.setFlowRunId(clueModel.getFlowRunId());
				}
				if ("4".equals(prcsId)) { // 如果是回到了确认时候的受理单，状态改为20
					clue.setStaus(20);
				}
				if ("3".equals(prcsId)) { // 如果是领导审批
					ClueLeaderOpinion opinion = new ClueLeaderOpinion();
					opinion.setId(UUID.randomUUID().toString());
					opinion.setClueId(clue.getId());
					opinion.setLeadersId(user.getUuid() + "");
					opinion.setLeadersName(user.getUserName());
					opinion.setLeadersOpinion(request
							.getParameter("leadersOpinion"));
					opinion.setCreateTime(TeeDateUtil.format(
							request.getParameter("leaderDateStr"),
							"yyyy年MM月dd日"));
					// 保存领导意见
					opinionService.saveLeaderOpinion(opinion);
				}
				if ("2".equals(prcsId)) { // 如果是部门负责人意见
					clue.setLeaderContent(clueModel.getLeaderContent());
					if (!"".equals(clueModel.getLeaderTimeStr())) {
						clue.setLeaderTime(TeeDateUtil.format(
								clueModel.getLeaderTimeStr(), "yyyy年MM月dd日"));
					}
				}
			}
		}

		clueService.update(clue);
		json.setRtData(clue);
		json.setRtState(true);

		return json;
	}

	/**
	 * 线索发送到案件指派去生成案件
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ClueToPunish")
	public TeeJson ClueToPunish(String id, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		CaseAppointedInfo caseinfo = new CaseAppointedInfo();

		Clue clue = clueService.get(id);

		if (clue.getStaus() == 20) { // 如果是案件确认阶段
			clue.setStaus(30); // 状态改为办理中

			// 线索表到指派表 因字段名不同需要依次赋值
			caseinfo.setId(UUID.randomUUID().toString());
			caseinfo.setOrganizationId(clue.getDealDepart());

			caseinfo.setCreateTime(Calendar.getInstance());
			caseinfo.setTaskType(10);
			caseinfo.setTaskRec(10);
			caseinfo.setTaskRecId(clue.getId());
			caseinfo.setTaskRecName("线索");
			caseinfo.setTaskSendPersonId(clue.getCreateUserId());
			caseinfo.setTaskSendPersonName(clue.getAcceptUserName());
			caseinfo.setLitigantType(clue.getReportedType() + "");
			caseinfo.setTaskSendTime(Calendar.getInstance().getTime());

			// clue表的被举报人信息 ---> appoint表的当事人信息赋值
			if (clue.getReportedType() == 1) {// 如果被举报人是 个人

				caseinfo.setTaskName("举报送审立案-公民-" + clue.getDocumentTitle());

				caseinfo.setPsnName(clue.getPersonalReportedName());
				caseinfo.setPsnTel(clue.getPersonalReportedTel());
				caseinfo.setPsnAddress(clue.getPersonalReportedAddress());
				caseinfo.setCheckAddr(clue.getPersonalReportedAddress());

			} else if (clue.getReportedType() == 2) {// 如果被举报人是 组织单位

				caseinfo.setTaskName("举报送审立案-组织-" + clue.getDocumentTitle());
				caseinfo.setOrganName(clue.getOrganReportedName());
				caseinfo.setOrganLeadingTel(clue.getOrganReportedPersonTel());
				caseinfo.setOrganAddress(clue.getOrganReportedAddress());
				caseinfo.setOrganLeadingName(clue.getOrganReportedPersonName());
				caseinfo.setOrganType(clue.getOrganReportedPersonType() + "");
				caseinfo.setCheckAddr(clue.getOrganReportedAddress());
				caseinfo.setOrganCodeType("1"); // 设置为唯一信用代码 （目前线索没有选项，都是
												// 唯一信用代码）
				caseinfo.setOrganCode(clue.getOrganReportedCode()); // 设置单位唯一社会信用代码

			} else if (clue.getReportedType() == 3) {// 如果被举报人是 个体工商户

				caseinfo.setTaskName("举报送审立案-个体工商户-" + clue.getDocumentTitle());
				caseinfo.setPsnName(clue.getPersonalReportedName());
				caseinfo.setPsnTel(clue.getPersonalReportedTel());
				caseinfo.setPsnAddress(clue.getPersonalReportedAddress());
				caseinfo.setPsnShopName(clue.getPersonalReportedShopName());
				caseinfo.setCheckAddr(clue.getPersonalReportedAddress());

			}
			caseinfo.setReportForm(clue.getReportForm());
			caseinfo.setReportrec(clue.getClueSource());
			caseinfo.setCheckContent(clue.getReportContent());

			appointedService.save(caseinfo);

		} else if (clue.getStaus() == 40) { // 如果是案件审批阶段
			clue.setStaus(20);
		}
		//获取文号
		Map<String, String> wenhao = wenhaoService.getMaxNum(request);
		String docYear = wenhao.get("DocYear");
		String docNum = wenhao.get("DocNum");
		clue.setDocNums("京文执案举["+docYear+"]"+docNum+"号");
		
		// 保存线索信息
		clueService.update(clue);

		json.setRtState(true);
		json.setRtData(clue);

		return json;
	}

	/**
	 * 受理人查看需要回复的线索列表
	 * 
	 * @param gridModel
	 * @param queryModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/replyTable")
	public TeeEasyuiDataGridJson replyTable(TeeDataGridModel gridModel,
			ClueModel queryModel, HttpServletRequest request) {
		TeeEasyuiDataGridJson gridJson = new TeeEasyuiDataGridJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		List<Clue> clues = clueService.replylistByPage(gridModel, queryModel,
				user);
		List<ClueModel> clueModels = new ArrayList<ClueModel>();
		for (Clue clue : clues) {
			ClueModel clueModel = new ClueModel();
			BeanUtils.copyProperties(clue, clueModel);

			clueModel.setCreateTimeStr(TeeDateUtil.format(clue.getCreateTime(),
					"yyyy年MM月dd日"));
			clueModel.setReportDateStr(TeeDateUtil.format(clue.getReportDate(),
					"yyyy年MM月dd日"));
			clueModel.setDocumentDateStr(TeeDateUtil.format(
					clue.getDocumentDate(), "yyyy年MM月dd日"));

			clueModels.add(clueModel);
		}

		long total = clueService.replylistCount(queryModel, user);

		gridJson.setRows(clueModels);
		gridJson.setTotal(total);

		return gridJson;
	}

	/**
	 * 获取回复列表
	 * 
	 * @param clueReplyModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getReply")
	public TeeJson getReply(ClueReplyModel clueReplyModel,
			HttpServletRequest request) {
		TeeJson result = new TeeJson();
		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		List<ClueReply> clueReplies = clueReplyService.findClueReply(
				clueReplyModel, user);

		result.setRtData(clueReplies);
		result.setRtState(true);

		return result;
	}

	/**
	 * 获取回复列表 区分回复类型
	 * 
	 * @param clueReplyModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getReplyByClueId")
	public TeeJson getReplyClueId(ClueReplyModel clueReplyModel,
			HttpServletRequest request) {
		TeeJson result = new TeeJson();

		TeePerson person = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		List<ClueReply> clueReplies = clueReplyService.findClueReply(
				clueReplyModel, person);
		List<ClueReplyModel> replyModels = new ArrayList<ClueReplyModel>();
		ClueReplyModel model = null;
		for (ClueReply reply : clueReplies) {
			model = new ClueReplyModel();
			BeanUtils.copyProperties(reply, model);
			String replyTypeStr = "";
			if (reply.getReplyType() == 1) {
				replyTypeStr = "阶段回复";
			} else {
				replyTypeStr = "最终回复";
			}
			model.setReplyTypeStr(replyTypeStr);
			model.setReplyDateStr(TeeDateUtil.format(reply.getReplyDate(),
					"yyyy-MM-dd HH:mm"));

			replyModels.add(model);
		}

		result.setRtData(replyModels);
		result.setRtState(true);

		return result;
	}

	/**
	 * 线索办结处理完毕
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/closedReply")
	public TeeJson closedReply(String id, HttpServletRequest request) {
		TeeJson result = new TeeJson();

		ClueReply clueReply = clueReplyService.getById(id);
		clueReply.setState("20");

		clueReplyService.update(clueReply);

		result.setRtData(clueReply);
		result.setRtState(true);
		return result;
	}

	/**
	 * 线索对外回复
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/outsideReply")
	public TeeJson outsideReply(String id, HttpServletRequest request) {
		TeeJson result = new TeeJson();

		ClueReply clueReply = clueReplyService.getById(id);
		clueReply.setState("30");

		clueReplyService.update(clueReply);

		result.setRtData(clueReply);
		result.setRtState(true);
		return result;
	}

	/**
	 * 更改最终回复为阶段性回复
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeReply")
	public TeeJson changeReply(String id, HttpServletRequest request) {
		TeeJson result = new TeeJson();

		ClueReply clueReply = clueReplyService.getById(id);
		clueReply.setReplyType(1);

		clueReplyService.update(clueReply);

		result.setRtData(clueReply);
		result.setRtState(true);
		return result;
	}

	/**
	 * 获取回复分页列表
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getReplyTable")
	public TeeEasyuiDataGridJson getReplyTable(TeeDataGridModel dataGridModel,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson result = new TeeEasyuiDataGridJson();
		String id = TeeStringUtil.getString(request.getParameter("id"), "");
		if (id != "") {
			// 通过分页获取线索信息数据的List
			long total = clueReplyService.getReplyListSize(id);

			List<ClueReplyModel> modelList = new ArrayList<ClueReplyModel>();
			List<ClueReply> clueReplys = clueReplyService.getReplyList(id,
					dataGridModel.getFirstResult(), dataGridModel.getRows());
			if (clueReplys.size() > 0) {
				for (ClueReply clueReply : clueReplys) {
					ClueReplyModel replyModel = new ClueReplyModel();
					BeanUtils.copyProperties(clueReply, replyModel);
					replyModel.setReplyDateStr(TeeDateUtil.format(
							clueReply.getReplyDate(), "yyyy年MM月dd日"));
					modelList.add(replyModel);
				}
			}
			result.setRows(modelList);
			result.setTotal(total);
		} else {
			result.setRows(null);
			result.setTotal((long) 0);
		}

		return result;
	}

	/**
	 * 执法队新增线索回复
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addClueReply")
	public TeeJson addClueReply(ClueReplyModel model, HttpServletRequest request) {
		TeeJson json = new TeeJson();

		TeePerson user = (TeePerson) request.getSession().getAttribute(
				TeeConst.LOGIN_USER);

		ClueReply reply = new ClueReply();
		BeanUtils.copyProperties(model, reply);

		reply.setId(UUID.randomUUID().toString());
		reply.setCreateUserId(user.getUuid());
		reply.setCreateUserName(user.getUserName());

		String clueId = model.getClueId();

		Clue clue = new Clue();
		clue = clueService.get(clueId);
		if (clue == null) {
			json.setRtState(false);
			json.setRtMsg("查无此线索请检查");
		}
		if (model.getReplyType() == 2) {
			String baseId = TeeStringUtil.getString(
					request.getParameter("baseId"), "");
			if (baseId != "") {// 修改案件状态为已最终回复
				PunishBase base = baseService.getbyid(baseId);
				base.setIsFinalReply(1);
				baseService.update(base);
			}
			clue.setStaus(33); // 线索状态改为最终回复待处理
		} else {
			clue.setStaus(31); // 线索状态改为阶段回复待处理
		}
		clueService.update(clue);

		// 初始化字段
		reply.setRemark(""); // 回复操作备注
		reply.setReplyDate(Calendar.getInstance().getTime());// 回复时间
		reply.setIsReplyExternal(0); // 是否对外回复
		reply.setReplyExternalType("");// 对外回复类型
		reply.setIsChangeType(0); // 是否切换回复状态
		reply.setState("10"); // 当前状态
		reply.setTransferUserId(0); // 处理人id
		reply.setTransferUserName(""); // 处理人名

		clueReplyService.saveReply(reply);
		json.setRtData(reply);
		json.setRtState(true);

		return json;
	}

	/**
	 * 获取回复详情
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getReplyContent")
	public TeeJson getReplyContent(String id) {
		TeeJson result = new TeeJson();

		ClueReply clueReply = clueReplyService.getById(id);
		ClueReplyModel replyModel = new ClueReplyModel();

		BeanUtils.copyProperties(clueReply, replyModel);
		replyModel.setReplyDateStr(TeeDateUtil.format(clueReply.getReplyDate(),
				"yyyy年MM月dd日"));
		if (replyModel.getReplyType() == 1) {
			replyModel.setReplyTypeStr("阶段性回复");
		} else {
			replyModel.setReplyTypeStr("对外回复");
		}
		result.setRtData(replyModel);
		result.setRtState(true);
		return result;
	}

	/**
	 * 执法队回复时更新线索状态
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/changeReplyStaus")
	public TeeJson changeReplyStaus(HttpServletRequest request) {
		TeeJson json = new TeeJson();
		String clueId = TeeStringUtil.getString(request.getParameter("clueId"),
				"");
		int staus = TeeStringUtil.getInteger(request.getParameter("staus"), 0);
		Clue clue = new Clue();
		if (!"".equals(clueId) && staus != 0) {
			clue = clueService.get(clueId);
			clue.setStaus(staus);

			clueService.update(clue);
			json.setRtState(true);
		} else {
			json.setRtState(false);
		}
		json.setRtData(clue);
		return json;
	}

	/**
	 * 根据领域查询举报受理量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCountBydomainType")
	public TeeJson getCountBydomainType() {
		TeeJson json = new TeeJson();

		List<Map<String, Object>> map = clueService.getCountBydomainType();

		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		for (int i = 0; i < map.size(); i++) {
			if((map.get(i)).get("namea").toString() != null){
				// 领域类型为key，值为value
				mapList.put("D" + (map.get(i)).get("namea").toString(),
						Integer.parseInt((map.get(i)).get("num").toString()));
			}
		}
		
		// 如果为空，赋值为o
		if (mapList.get("D10") == null) {
			mapList.put("D10", 0);
		}
		if (mapList.get("D20") == null) {
			mapList.put("D20", 0);
		}
		if (mapList.get("D30") == null) {
			mapList.put("D30", 0);
		}
		if (mapList.get("D40") == null) {
			mapList.put("D40", 0);
		}
		if (mapList.get("D50") == null) {
			mapList.put("D50", 0);
		}
		if (mapList.get("D60") == null) {
			mapList.put("D60", 0);
		}
		if (mapList.get("D70") == null) {
			mapList.put("D70", 0);
		}

		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}
	
	/**
	 * 根据领域查询举报办结量
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCountByReport")
	public TeeJson getCountByReport() {
		TeeJson json = new TeeJson();

		List<Map<String, Object>> map = clueService.getCountByReport();

		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		for (int i = 0; i < map.size(); i++) {
			if((map.get(i)).get("namea").toString() != null){
				// 领域类型为key，值为value
				mapList.put("D" + (map.get(i)).get("namea").toString(),
						Integer.parseInt((map.get(i)).get("num").toString()));
			}
		}
			
		// 如果为空，赋值为o
		if (mapList.get("D10") == null) {
			mapList.put("D10", 0);
		}
		if (mapList.get("D20") == null) {
			mapList.put("D20", 0);
		}
		if (mapList.get("D30") == null) {
			mapList.put("D30", 0);
		}
		if (mapList.get("D40") == null) {
			mapList.put("D40", 0);
		}
		if (mapList.get("D50") == null) {
			mapList.put("D50", 0);
		}
		if (mapList.get("D60") == null) {
			mapList.put("D60", 0);
		}
		if (mapList.get("D70") == null) {
			mapList.put("D70", 0);
		}

		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
	}
	
	/**
 	 * 根据部门查询举报半结量
 	 * @return
 	 */
	@ResponseBody
	@RequestMapping("/getCountByDept")
 	public TeeJson getCountByDept(){
		TeeJson json = new TeeJson();

		List<Map<String, Object>> map = clueService.getCountByDept();

		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		for (int i = 0; i < map.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("D" + (map.get(i)).get("namea").toString(),
					Integer.parseInt((map.get(i)).get("num").toString()));
		}

		// 如果为空，赋值为o
		if (mapList.get("D18") == null) {
			mapList.put("D18", 0);
		}
		if (mapList.get("D10") == null) {
			mapList.put("D10", 0);
		}
		if (mapList.get("D11") == null) {
			mapList.put("D11", 0);
		}
		if (mapList.get("D12") == null) {
			mapList.put("D12", 0);
		}
		if (mapList.get("D13") == null) {
			mapList.put("D13", 0);
		}
		if (mapList.get("D14") == null) {
			mapList.put("D14", 0);
		}

		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
 	}
	
	/**
 	 * 查询已办结的方法
 	 * @return
 	 */
	@ResponseBody
	@RequestMapping("/getCountByStaus")
 	public TeeJson getCountByStaus(){
		TeeJson json = new TeeJson();
		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		List<Map<String, Object>> map = clueService.getCountByStaus();


		for (int i = 0; i < map.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("SY",Integer.parseInt((map.get(i)).get("num").toString()));
		}
		
		List<Map<String, Object>> mapw = clueService.getCountByStausW();


		for (int i = 0; i < mapw.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("SW",Integer.parseInt((mapw.get(i)).get("num").toString()));
		}
		
		// 如果为空，赋值为o
		if (mapList.get("SY") == null) {
			mapList.put("SY", 0);
		}
		if (mapList.get("SW") == null) {
			mapList.put("SW", 0);
		}
		
		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
 	}
	
	/**
 	 * 查询今天的新增的举报办理
 	 * @return
 	 */
	@ResponseBody
	@RequestMapping("/getCountByDay")
 	public TeeJson getCountByDay(){
		TeeJson json = new TeeJson();
		// 返回的map
		Map<String, Integer> mapList = new HashMap<String, Integer>();

		List<Map<String, Object>> map = clueService.getCountByStaus();


		for (int i = 0; i < map.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("SY",Integer.parseInt((map.get(i)).get("num").toString()));
		}
		
		List<Map<String, Object>> mapw = clueService.getCountByDay();


		for (int i = 0; i < mapw.size(); i++) {
			// 领域类型为key，值为value
			mapList.put("DAY",Integer.parseInt((mapw.get(i)).get("num").toString()));
		}
		
		// 如果为空，赋值为o
		if (mapList.get("DAY") == null) {
			mapList.put("DAY", 0);
		}
		
		json.setRtData(mapList);
		if (mapList.isEmpty()) {
			json.setRtState(false);
		} else {
			json.setRtState(true);
		}
		return json;
 	}
	
	/**
 	 * 获取liutiejing桌面数据
 	 * @return
 	 */
	@ResponseBody
	@RequestMapping("/getLiutiejingData")
 	public TeeJson getLiutiejingData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List dataList = new ArrayList();
		dataList.add(clueService.getLeaderCount("day", 0, 0));
		dataList.add(clueService.getLeaderCount("day", 11, 0));
		dataList.add(clueService.getLeaderCount("day", 14, 0));
		dataList.add(clueService.getLeaderCount("day", 13, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 11, 0));
		dataList.add(clueService.getLeaderCount("year", 14, 0));
		dataList.add(clueService.getLeaderCount("year", 13, 0));
		dataList.add(clueService.getLeaderCount("all", 0, 1));
		json.setRtData(dataList);
		json.setRtState(true);
		return json;
	}
	/**
	 * 获取wangningzhi桌面数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getWangningzhiData")
	public TeeJson getWangningzhiData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List dataList = new ArrayList();
		dataList.add(clueService.getLeaderCount("day", 0, 0));
		dataList.add(clueService.getLeaderCount("day", 12, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 12, 0));
		dataList.add(clueService.getLeaderCount("all", 0, 1));
		json.setRtData(dataList);
		json.setRtState(true);
		return json;
	}
	/**
	 * 获取zhoudaqing桌面数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getZhoudaqingData")
	public TeeJson getZhoudaqingData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List dataList = new ArrayList();
		dataList.add(clueService.getLeaderCount("day", 0, 0));
		dataList.add(clueService.getLeaderCount("day", 10, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 10, 0));
		dataList.add(clueService.getLeaderCount("all", 0, 1));
		json.setRtData(dataList);
		json.setRtState(true);
		return json;
	}
	/**
	 * 获取liangzuguo桌面数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLiangzuguoData")
	public TeeJson getLiangzuguoData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List dataList = new ArrayList();
		dataList.add(clueService.getLeaderCount("day", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 2));
		dataList.add(clueService.getLeaderCount("year", 0, 1));
		json.setRtData(dataList);
		json.setRtState(true);
		return json;
	}
	/**
	 * 获取lishujiang桌面数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLishujiangData")
	public TeeJson getLishujiangData(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List dataList = new ArrayList();
		dataList.add(clueService.getLeaderCount("day", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 0));
		dataList.add(clueService.getLeaderCount("year", 0, 2));
		dataList.add(clueService.getLeaderCount("year", 0, 1));
		json.setRtData(dataList);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取线索过程中的拟办理单位
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDepartList")
	public TeeJson getDepartList(){
		TeeJson json = new TeeJson();
		
		List <TeeDepartmentModel> departList = clueService.getDepartList();
		json.setRtData(departList);
		json.setRtState(true);
		
		return json;
	}
}