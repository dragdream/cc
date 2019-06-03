package com.beidasoft.xzzf.punish.document.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocHearingReport;
import com.beidasoft.xzzf.punish.document.dao.HearingReportDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class HearingReportService extends TeeBaseService{

	@Autowired
	private HearingReportDao hearingReportDao;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * @param hearingReport
	 * @throws Exception 
	 */

	public void save(DocHearingReport hearingReport , HttpServletRequest request) throws Exception{
		String runId = request.getParameter("runId");
		String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
		// 获取文号
		if ("99".equals(prcsId)) {
			PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
			//获取登录人的信息
			TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment department = user.getDept();
			String dept = department.getSubordinateUnits();
			
//			String numb = "（"+docSiteSanction.getDocArea() + "）文当罚字〔" +docSiteSanction.getDocYear()+"〕第" + docSiteSanction.getDocNum()+"号";
			flowInfo.setContentsCode("");
			flowInfo.setContentsDate(TeeDateUtil.format(hearingReport.getReportDate(),"yyyyMMdd"));
			flowInfo.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(hearingReport.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfo.setContentsPages(page);
			flowInfo.setContentsFilepath(path);
			flowInfo.setContentsRemark(request.getParameter(""));
			flowInfo.setContentsResponer(dept);
			flowInfo.setPunishDocId(hearingReport.getId());
			flowService.update(flowInfo);
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(hearingReport.getBaseId());
		
		baseDate.setXzcftzbgSealDate(hearingReport.getReportDate());
		
		baseDateService.save(baseDate);
		hearingReportDao.saveOrUpdate(hearingReport);
	}
	

	/**
	 * @param id
	 * @return
	 */
	public DocHearingReport getById(String id) {
		return hearingReportDao.get(id);
	}
	
	/**
	 *更新行政处罚事先告知书
	 * @param administrativePenality
	 */
	public void update(DocHearingReport hearingReport) {
		hearingReportDao.update(hearingReport);
	}
}
