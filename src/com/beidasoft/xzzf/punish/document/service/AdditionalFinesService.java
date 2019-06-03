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
import com.beidasoft.xzzf.punish.document.bean.DocAdditionalFines;
import com.beidasoft.xzzf.punish.document.dao.AdditionalFinesDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class AdditionalFinesService extends TeeBaseService {
	@Autowired
	private AdditionalFinesDao additionalFinesDao;
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
	 * 行政处罚加处罚款决定书的保存
	 * 
	 * @param additionalFines
	 * @throws Exception 
	 */
	public void save(DocAdditionalFines additionalFines , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = additionalFines.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				additionalFines.setDocArea(map.get("DocArea"));
				additionalFines.setDocYear(map.get("DocYear"));
				additionalFines.setDocNum(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", additionalFines.getDocArea());
				content.put("文号字", additionalFines.getDocYear());
				content.put("文号", additionalFines.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+additionalFines.getDocArea() + "）文加罚字〔" +additionalFines.getDocYear()+"〕第" + additionalFines.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(additionalFines.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(additionalFines.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(additionalFines.getId());
				flowService.update(flowInfo);
			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(additionalFines.getBaseId());
		baseDate.setXzcfjcfkjdsSealDate(additionalFines.getLawUnitDate());//行政处罚加处罚款决定书签章时间
		baseDate.setXzcfjcfkjdsArriveDate(additionalFines.getSendTime());//行政处罚加处罚款决定书送达时间
		baseDate.setXzcfjcfkjdsRecelveDate(additionalFines.getReceiverSignatureDate());//行政处罚加处罚款决定书签收时间
		baseDateService.save(baseDate);
		
		additionalFinesDao.saveOrUpdate(additionalFines);
	}

	public DocAdditionalFines getById(String id) {
		return additionalFinesDao.get(id);
	}
}
