package com.beidasoft.xzzf.punish.document.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocHearingRecord;
import com.beidasoft.xzzf.punish.document.dao.HearingRecordDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class HearingRecordService extends TeeBaseService{
	
	@Autowired
	private HearingRecordDao recordDao;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 保存
	 * @param record
	 * @throws Exception 
	 */
	public void saveDocInfo(DocHearingRecord record , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
		// 获取文号
		if ("2".equals(prcsId)) {
			PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
			//获取登录人的信息
			TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment department = user.getDept();
			String dept = department.getSubordinateUnits();
			
//			String numb = "（"+docSiteSanction.getDocArea() + "）文当罚字〔" +docSiteSanction.getDocYear()+"〕第" + docSiteSanction.getDocNum()+"号";
			flowInfo.setContentsCode("");
			flowInfo.setContentsDate(TeeDateUtil.format(record.getHearingTimeStart(),"yyyyMMdd"));
			flowInfo.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(record.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfo.setContentsPages(page);
			flowInfo.setContentsFilepath(path);
			flowInfo.setContentsRemark(request.getParameter(""));
			flowInfo.setContentsResponer(dept);
			flowInfo.setPunishDocId(record.getId());
			flowService.update(flowInfo);
		}
		
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(record.getBaseId());
		baseDate.setXzcftzblStartDate(record.getHearingTimeStart());
		baseDate.setXzcftzblEndDate(record.getHearingTimeEnd());
		baseDateService.save(baseDate);
		recordDao.saveOrUpdate(record);
	}
	
	/**
	 * 根据id 获取单个 实体类
	 * 
	 * @param id
	 * @return
	 */
	public DocHearingRecord getDocInfo(String id) {
		return recordDao.get(id);
	}
}
