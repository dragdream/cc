package com.beidasoft.xzzf.punish.document.service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocPaymentinstallments;
import com.beidasoft.xzzf.punish.document.dao.PaymentinstallmentsDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class PaymentinstallmentsService extends TeeBaseService {
	@Autowired
	private PaymentinstallmentsDao paymentinstallmentsDao;
	@Autowired
	private TeeFlowRunDao flowRunDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 延期/分期缴纳罚款批准书的保存
	 * 
	 * @param paymentinstallments
	 * @throws Exception 
	 */
	public void save(DocPaymentinstallments paymentinstallments , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = paymentinstallments.getPaySquence();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				paymentinstallments.setPayAddr(map.get("DocArea"));
				paymentinstallments.setPayYear(map.get("DocYear"));
				paymentinstallments.setPaySquence(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", paymentinstallments.getPayAddr());
				content.put("文号年", paymentinstallments.getPayYear());
				content.put("文号", paymentinstallments.getPaySquence());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+paymentinstallments.getPayAddr() + "）文延缴字〔" +paymentinstallments.getPayYear()+"〕第" + paymentinstallments.getPaySquence()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(paymentinstallments.getStampDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(paymentinstallments.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(paymentinstallments.getId());
				flowService.update(flowInfo);
			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(paymentinstallments.getBaseId());
		//延期缴纳罚款
		if ("0".equals(paymentinstallments.getPayType())) {
			baseDate.setYqjnfkpzsDelayDate(paymentinstallments.getExtensionDate());
			baseDate.setYqjnfkpzsSealDate(paymentinstallments.getStampDate());
			baseDate.setYqjnfkpzsArriveDate(paymentinstallments.getSendDate());
			baseDate.setYqjnfkpzsRecelveDate(paymentinstallments.getReceiptDate());
		}
		//分期缴纳罚款
		if ("1".equals(paymentinstallments.getPayType())) {
			baseDate.setFqjnfkpzsOneDate(paymentinstallments.getFirstPhaseData());
			baseDate.setFqjnfkpzsTwoDate(paymentinstallments.getTwoPhaseData());
			baseDate.setFqjnfkpzsThreeDate(paymentinstallments.getThreePhaseData());
			baseDate.setFqjnfkpzsSealDate(paymentinstallments.getStampDate());
			baseDate.setFqjnfkpzsArriveDate(paymentinstallments.getSendDate());
			baseDate.setFqjnfkpzsRecelveDate(paymentinstallments.getReceiptDate());
		}
		baseDateService.save(baseDate);
		
		paymentinstallmentsDao.saveOrUpdate(paymentinstallments);

		String confFlowName = "";
		if (paymentinstallments.getPayType().equals("0")) {
			confFlowName += "延期缴纳罚款批准书";
		} else if (paymentinstallments.getPayType().equals("1")) {
			confFlowName += "分期缴纳罚款批准书";
		} else {
			confFlowName += "";
		}
		if (StringUtils.isBlank(runId)) {
			runId = "0";
		}
		//更新文书流程的FLOW名称
		PunishFLow flowInfos = punishFlowDao.getByRunId(runId);
		if (flowInfos != null) {
			flowInfos.setPunishPrcsName(confFlowName);
			punishFlowDao.saveOrUpdate(flowInfos);
		}

		// 更新工作流里的FLOW名称
		TeeFlowRun flowRun = flowRunDao.get(Integer.parseInt(runId));
		if (flowRun != null) {
			Pattern pattern = Pattern.compile("\\([0-9-: ]*\\)");
			Matcher matcher = pattern.matcher(flowRun.getRunName());
			while (matcher.find()) {
				confFlowName += matcher.group();
				break;
			}
			flowRun.setoRunName(confFlowName);
			flowRun.setRunName(confFlowName);
			flowRunDao.saveOrUpdate(flowRun);
		}
	}

	public DocPaymentinstallments getById(String id) {
		return paymentinstallmentsDao.get(id);
	}
}
