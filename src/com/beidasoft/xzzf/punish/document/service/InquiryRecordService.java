package com.beidasoft.xzzf.punish.document.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocInquiryRecord;
import com.beidasoft.xzzf.punish.document.dao.InquiryRecordDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;

@Service
public class InquiryRecordService extends TeeBaseService{
	
	@Autowired
	private InquiryRecordDao inquiryRecorddao;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 *  保存
	 *  
	 * @param inquiryRecord
	 * @throws Exception 
	 */
	public void save(DocInquiryRecord inquiryRecord, HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
		//获取登录人的信息
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDepartment department = user.getDept();
		String dept = department.getSubordinateUnits();
		
		flowInfo.setContentsCode("");
		flowInfo.setContentsDate(TeeDateUtil.format(inquiryRecord.getAskedSignatureDate(),"yyyyMMdd"));
		flowInfo.setContentsNumber("");
		//调用方法获取文书页数（int-->String）
		String nums = wenshuService.getPages(inquiryRecord.getId(), runId, request);
		String numsArr[] = nums.split(",");
		String page = numsArr[0];
		String path = numsArr[1];
		flowInfo.setContentsPages(page);
		flowInfo.setContentsFilepath(path);
		flowInfo.setContentsRemark(request.getParameter(""));
		flowInfo.setContentsResponer(dept);
		flowInfo.setPunishDocId(inquiryRecord.getId());

		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(inquiryRecord.getBaseId());
		baseDate.setDcxwblStartDate(inquiryRecord.getAskTimeStart());
		baseDate.setDcxwblEndDate(inquiryRecord.getAskTimeEnd());
		baseDateService.save(baseDate);
		
		inquiryRecorddao.saveOrUpdate(inquiryRecord);
		flowService.update(flowInfo);
	}
	
	/**
	 * 通过ID 获取单个调查询问笔录的记录
	 * 
	 * @param id
	 * @return
	 */
	public DocInquiryRecord getById(String id) {
		return inquiryRecorddao.get(id);
	}
	
	/**
	 * 更新
	 * 
	 * @param inquiryRecord
	 */
	public void update(DocInquiryRecord inquiryRecord) {
		inquiryRecorddao.update(inquiryRecord);
	}
	
}
