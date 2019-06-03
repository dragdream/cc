package com.beidasoft.xzzf.punish.document.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocPublishNotice;
import com.beidasoft.xzzf.punish.document.dao.PublishNoticeDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class PublishNoticeService extends TeeBaseService{

	@Autowired
	private PublishNoticeDao publishNoticeDao;
	@Autowired
	private PunishBaseDao punishBaseDao;
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
	 * 行政处罚听证通知书
	 * @param docPublishNotice
	 * @throws Exception 
	 */
	public void saveDocInfo(DocPublishNotice docPublishNotice , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docPublishNotice.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docPublishNotice.setDocArea(map.get("DocArea"));
				docPublishNotice.setDocYear(map.get("DocYear"));
				docPublishNotice.setDocNum(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docPublishNotice.getDocArea());
				content.put("文号年", docPublishNotice.getDocYear());
				content.put("文号", docPublishNotice.getDocNum());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docPublishNotice.getDocArea() + "）文听通字〔" +docPublishNotice.getDocYear()+"〕第" + docPublishNotice.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docPublishNotice.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docPublishNotice.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docPublishNotice.getId());
				flowService.update(flowInfo);

			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docPublishNotice.getBaseId());
		baseDate.setXzcftztzsStartDate(docPublishNotice.getDocDate());
		baseDate.setXzcftztzsSealDate(docPublishNotice.getLawUnitDate());
		baseDate.setXzcftztzsArriveDate(docPublishNotice.getDeliverDate());
		baseDate.setXzcftztzsRecelveDate(docPublishNotice.getReceiverDate());
		baseDateService.save(baseDate);
		
		publishNoticeDao.saveOrUpdate(docPublishNotice);
		//根据baseId查询，然后将听证时间存到PunishBase表中
		PunishBase baseInfo = punishBaseDao.get(docPublishNotice.getBaseId());
		if (baseInfo != null) {
			baseInfo.setHearingDate(docPublishNotice.getDocDate());
			punishBaseDao.saveOrUpdate(baseInfo);
		}
	}

	/**
	 * 行政处罚听证通知书（根据ID）
	 * @param id
	 * @return
	 */
	public DocPublishNotice getById(String id) {
		return publishNoticeDao.get(id);
	}

	/**
	 * 行政处罚听证通知书
	 * @param docPublishNotice
	 */
	public void updateDocInfo(DocPublishNotice docPublishNotice) {
		publishNoticeDao.update(docPublishNotice);
	}
}
