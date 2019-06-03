package com.beidasoft.xzzf.punish.document.service;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.service.PowerSelectService;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocApprovaltable;
import com.beidasoft.xzzf.punish.document.dao.ApprovaltableDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class ApprovaltableService extends TeeBaseService{
	
	@Autowired
	private ApprovaltableDao approvaltabledao;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeFlowRunDao flowRunDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PowerSelectService powerSelectService;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 将信息存到审批表中
	 * @param userInfo
	 * @throws Exception 
	 */
	public void save(DocApprovaltable approvaltable, HttpServletRequest request) throws Exception{
		String runId = request.getParameter("runId");
		String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
		// 获取文号
		if ("99".equals(prcsId)) {
			PunishFLow flowInfos = punishFlowDao.getByRunId(runId);
			//获取登录人的信息
			TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment department = user.getDept();
			String dept = department.getSubordinateUnits();
			
			flowInfos.setContentsCode("");
			flowInfos.setContentsDate(TeeDateUtil.format(approvaltable.getLeaderTime(),"yyyyMMdd"));
			flowInfos.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(approvaltable.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfos.setContentsPages(page);
			flowInfos.setContentsFilepath(path);
			flowInfos.setContentsRemark(request.getParameter(""));
			flowInfos.setContentsResponer(dept);
			flowInfos.setPunishDocId(approvaltable.getId());
			flowService.update(flowInfos);
		 	BasePower basePower = powerSelectService.getById(approvaltable.getCauseAction());
		 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
		 	powerSelectService.save(basePower);
		 	
		 	PunishBase base = baseService.getbyid(approvaltable.getBaseId()); //baseId 取得base实体类
			// 更新base表当事人信息
			//个人信息
			base.setPsnName(approvaltable.getPartyName());
			base.setPsnSex(approvaltable.getPartyAddress());
			base.setPsnType(approvaltable.getPartyPhone());
			//单位信息
			base.setOrganName(approvaltable.getPartyName());
			base.setOrganCodeType(approvaltable.getPartyAddress());
			base.setOrganCode(approvaltable.getPartyPhone());
			base.setOrganType(approvaltable.getPartyType());
			base.setOrganLeadingName(approvaltable.getLeadingName());
			//更新base对象
			baseService.update(base);                         
		    //把base表的数据添加base表子表
			PunishBaseDetail baseDetail = new PunishBaseDetail();
			BeanUtils.copyProperties(base, baseDetail);
			baseDetail.setCreateDate(Calendar.getInstance().getTime());
			baseDetail.setId(UUID.randomUUID().toString());
			BaseDetailService.save(baseDetail);
		}
		approvaltabledao.saveOrUpdate(approvaltable);

		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(approvaltable.getBaseId());
		
		
		//更新FLOW名称       并保存案件实际那表对应时间
		String approvalMatterName = "";
		String approvalMatter = approvaltable.getApprovalMatter();
		if ("1".equals(approvalMatter)) {
			approvalMatterName = "抽样取证";
			baseDate.setSpbCyqzDate(approvaltable.getLeaderTime());//抽样取证审批时间
		} else if ("2".equals(approvalMatter)) {
			approvalMatterName = "抽样取证物品处理";
			baseDate.setSpbCyqzwqclDate(approvaltable.getLeaderTime());//抽样取证物品处理审批时间
		} else if ("3".equals(approvalMatter)) {
			approvalMatterName = "证据先行登记保存";
			baseDate.setSpbZjxxdjbcDate(approvaltable.getLeaderTime());//证据先行登记保存审批时间
		} else if ("4".equals(approvalMatter)) {
			approvalMatterName = "证据先行登记保存处理";
			baseDate.setSpbZjxxdjbcclDate(approvaltable.getLeaderTime());//证据先行登记保存处理审批时间
		} else if ("5".equals(approvalMatter)) {
			approvalMatterName = "查封/扣押";
			baseDate.setSpbCfkyDate(approvaltable.getLeaderTime());//查封扣押审批时间
		} else if ("6".equals(approvalMatter)) {
			approvalMatterName = "延长查封/扣押";
			baseDate.setSpbYccfkyDate(approvaltable.getLeaderTime());//延长查封扣押审批时间
		} else if ("7".equals(approvalMatter)) {
			approvalMatterName = "解除查封/扣押";
			baseDate.setSpbJccfkyDate(approvaltable.getLeaderTime());//解除查封扣押审批时间
		} else if ("8".equals(approvalMatter)) {
			approvalMatterName = "移送案件";
			baseDate.setSpbYsajDate(approvaltable.getLeaderTime());//移送案件审批时间
		} else if ("9".equals(approvalMatter)) {
			approvalMatterName = "案件延期办理";
			baseDate.setSpbAjyqblDate(approvaltable.getLeaderTime());//案件延期办理审批时间
		} else if ("10".equals(approvalMatter)) {
			approvalMatterName = "延期/分期缴纳罚款";
			baseDate.setSpbYqfqjnfkDate(approvaltable.getLeaderTime());//延期分期缴纳罚款审批时间
		} else if ("11".equals(approvalMatter)) {
			approvalMatterName = "出库";
			baseDate.setSpbCkDate(approvaltable.getLeaderTime());//出库审批时间
		} else if ("12".equals(approvalMatter)) {
			approvalMatterName = "撤销案件";
			baseDate.setSpbCxajDate(approvaltable.getLeaderTime());//撤销案件审批时间
		} else {
			approvalMatterName = "其他";
			baseDate.setSpbQtDate(approvaltable.getLeaderTime());//其他审批时间
		}
		baseDateService.save(baseDate);
		
		approvalMatterName += "-审批表";
		if (StringUtils.isBlank(runId)) {
			runId = "0";
		}
		PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
		if (flowInfo != null) {
			flowInfo.setPunishPrcsName(approvalMatterName);
			punishFlowDao.saveOrUpdate(flowInfo);
		}
		
		//更新工作流里的FLOW名称
		TeeFlowRun flowRun = flowRunDao.get(Integer.parseInt(runId));
		if (flowRun != null) {
			Pattern pattern = Pattern.compile("\\([0-9-: ]*\\)");
	        Matcher matcher = pattern.matcher(flowRun.getRunName());
			while (matcher.find()) {
				approvalMatterName += matcher.group();
				break;
			}
			
			flowRun.setoRunName(approvalMatterName);
			flowRun.setRunName(approvalMatterName);
			flowRunDao.saveOrUpdate(flowRun);
		}
	}
	
	/**
	 *通过uuid查询审批表数据
	 * @param userInfo
	 */
	public DocApprovaltable getByUID(String id){
		return approvaltabledao.get(id);
	}
	
}
