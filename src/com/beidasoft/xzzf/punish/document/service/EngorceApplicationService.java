package com.beidasoft.xzzf.punish.document.service;


import java.util.Map;

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
import com.beidasoft.xzzf.punish.document.bean.DocEngorceApplication;
import com.beidasoft.xzzf.punish.document.dao.EngorceApplicationDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class EngorceApplicationService extends TeeBaseService{

	@Autowired
	private EngorceApplicationDao engorceApplicationDao;
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
	 * 保存强制执行申请书
	 * @param docEngorceApplication
	 * @throws Exception 
	 */
	public void save(DocEngorceApplication docEngorceApplication , HttpServletRequest request) throws Exception{
		String runId = request.getParameter("runId");
		String docNum = docEngorceApplication.getEnforceSequence();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docEngorceApplication.setEnforceAddr(map.get("DocArea"));
				docEngorceApplication.setEnforceYear(map.get("DocYear"));
				docEngorceApplication.setEnforceSequence(map.get("DocNum"));
				
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docEngorceApplication.getEnforceAddr());
				content.put("文号字", docEngorceApplication.getEnforceYear());
				content.put("文号", docEngorceApplication.getEnforceSequence());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docEngorceApplication.getEnforceAddr() + "）文强执字〔" +docEngorceApplication.getEnforceYear()+"〕第" + docEngorceApplication.getEnforceSequence()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docEngorceApplication.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docEngorceApplication.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docEngorceApplication.getId());

				
				flowService.update(flowInfo);
			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docEngorceApplication.getBaseId());
		baseDate.setQzzxsqsSealDate(docEngorceApplication.getLawUnitDate());//强制执行申请书盖章时间
		baseDateService.save(baseDate);
		engorceApplicationDao.saveOrUpdate(docEngorceApplication);
	}
	
	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocEngorceApplication getById(String id) {
		return engorceApplicationDao.get(id);
	}
	
	/**
	 *更新行政处罚事先告知书
	 * @param administrativePenality
	 */
	public void update(DocEngorceApplication docEngorceApplication) {
		engorceApplicationDao.update(docEngorceApplication);
	}
}
