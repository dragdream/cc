package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocSealSeizureDecide;
import com.beidasoft.xzzf.punish.document.dao.SealSeizureDecideDao;
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

/**
 * 查封扣押处理决定书
 * */
@Service
public class SealSeizureDecideService extends TeeBaseService{
	
	@Autowired
	private SealSeizureDecideDao sealSeizureDecideDao;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeFlowRunDao flowRunDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 保存 查封扣押处理决定书
	 * @param sealSeizureDecide
	 * @throws Exception 
	 */
	public void save(DocSealSeizureDecide sealSeizureDecide , HttpServletRequest request) throws Exception {
		//更新文号
		String runId = request.getParameter("runId");
		String docNum = sealSeizureDecide.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				sealSeizureDecide.setDocArea(map.get("DocArea"));
				sealSeizureDecide.setDocYear(map.get("DocYear"));
				sealSeizureDecide.setDocNum(map.get("DocNum"));
				
				String NumType = "";
				if ("1".equals(sealSeizureDecide.getDocNumType())) {
					NumType = "封";
				} else if("2".equals(sealSeizureDecide.getDocNumType())){
					NumType = "扣";
				}
				PunishFLow flowInfos = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", sealSeizureDecide.getDocArea());
				content.put("文号年", sealSeizureDecide.getDocYear());
				content.put("文号", sealSeizureDecide.getDocNum());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				
				String numb = "（"+sealSeizureDecide.getDocArea() + "）文"+NumType+"处字〔" +sealSeizureDecide.getDocYear()+"〕第" + sealSeizureDecide.getDocNum()+"号";
				flowInfos.setContentsCode("");
				flowInfos.setContentsDate(TeeDateUtil.format(sealSeizureDecide.getLawUnitDate(),"yyyyMMdd"));
				flowInfos.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(sealSeizureDecide.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfos.setContentsPages(page);
				flowInfos.setContentsFilepath(path);
				flowInfos.setContentsRemark(request.getParameter(""));
				flowInfos.setContentsResponer(dept);
				flowInfos.setPunishDocId(sealSeizureDecide.getId());
				flowService.update(flowInfos);
				
				PunishBase base = baseService.getbyid(sealSeizureDecide.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(sealSeizureDecide.getPartyName());
				//单位信息
				base.setOrganName(sealSeizureDecide.getPartyName());
				//更新base对象
				baseService.update(base);                         
			    //把base表的数据添加base表子表
				PunishBaseDetail baseDetail = new PunishBaseDetail();
				BeanUtils.copyProperties(base, baseDetail);
				baseDetail.setCreateDate(Calendar.getInstance().getTime());
				baseDetail.setId(UUID.randomUUID().toString());
				BaseDetailService.save(baseDetail);
			}
		}
		
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(sealSeizureDecide.getBaseId());
		//查封处理决定书
		if ("1".equals(sealSeizureDecide.getDocNameType())) {
			// 鉴定时间（开始）
			baseDate.setCfcljdsAppraisalDate(sealSeizureDecide.getCheckupDateStart());
			// 鉴定时间（结束）
			baseDate.setCfcljdsEndDate(sealSeizureDecide.getCheckupDateEnd());
			// 行政机关落款印章时间
			baseDate.setCfcljdsSealDate(sealSeizureDecide.getLawUnitDate());
			// 送达时间
			baseDate.setCfcljdsArriveDate(sealSeizureDecide.getDeliverDate());
			// 受送达人签名或盖章时间
			baseDate.setCfcljdsReceiveDate(sealSeizureDecide.getReceiverDate());
			// 延长至
			baseDate.setCfcljdsDelayDate(sealSeizureDecide.getExtendDate());
			baseDate.setCfcljdsAppraisalDelayDate(sealSeizureDecide.getCheckupBeforBefore());
		}
		//扣押处理决定书
		if ("2".equals(sealSeizureDecide.getDocNameType())) {
			// 鉴定时间（开始）
			baseDate.setKycljdsAppraisalDate(sealSeizureDecide.getCheckupDateStart());
			// 鉴定时间（结束）
			baseDate.setKycljdsEndDate(sealSeizureDecide.getCheckupDateEnd());
			// 行政机关落款印章时间
			baseDate.setKycljdsSealDate(sealSeizureDecide.getLawUnitDate());
			// 送达时间
			baseDate.setKycljdsArriveDate(sealSeizureDecide.getDeliverDate());
			// 受送达人签名或盖章时间
			baseDate.setKycljdsReceiveDate(sealSeizureDecide.getReceiverDate());
			// 延长至
			baseDate.setKycljdsDelayDate(sealSeizureDecide.getExtendDate());
			baseDate.setKycljdsAppraisalDelayDate(sealSeizureDecide.getCheckupBeforBefore());
		}
		baseDateService.save(baseDate);
		
		sealSeizureDecideDao.saveOrUpdate(sealSeizureDecide);
	
		//获取流程名称
		String confFlowName = "";
		if (sealSeizureDecide.getDocNameType().equals("1")) {
			confFlowName += "查封处理决定书";
		} else if (sealSeizureDecide.getDocNameType().equals("2")) {
			confFlowName += "扣押处理决定书";
		} else {
			confFlowName += "";
		}
		if (StringUtils.isBlank(runId)) {
			runId = "0";
		}
		//更新文书流程的FLOW名称
		PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
		if (flowInfo != null) {
			flowInfo.setPunishPrcsName(confFlowName);
			punishFlowDao.saveOrUpdate(flowInfo);
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
		//获取
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocSealSeizureDecide getById(String id) {
		return sealSeizureDecideDao.get(id);
	}

	/**
	 * 更新 查封扣押处理决定书
	 * @param sealSeizureDecide
	 */
	public void update(DocSealSeizureDecide sealSeizureDecide) {
		sealSeizureDecideDao.update(sealSeizureDecide);
	}
}
