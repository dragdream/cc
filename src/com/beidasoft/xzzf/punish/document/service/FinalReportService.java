package com.beidasoft.xzzf.punish.document.service;

import java.util.List;
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
import com.beidasoft.xzzf.punish.document.bean.DocFinalReport;
import com.beidasoft.xzzf.punish.document.dao.FinalReportDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class FinalReportService extends TeeBaseService {
	@Autowired
	private FinalReportDao finalReportDao;
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
	 * 保存结案报告信息
	 * @param docFinalReport
	 * @throws Exception 
	 */
	public void saveDocInfo(DocFinalReport docFinalReport , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docFinalReport.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docFinalReport.setDocArea(map.get("DocArea"));
				docFinalReport.setDocYear(map.get("DocYear"));
				docFinalReport.setDocNum(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docFinalReport.getDocArea());
				content.put("文号字", docFinalReport.getDocYear());
				content.put("文号", docFinalReport.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docFinalReport.getDocArea() + "）文执结〔" +docFinalReport.getDocYear()+"〕第" + docFinalReport.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docFinalReport.getMajorLeaderDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docFinalReport.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docFinalReport.getId());
				flowService.update(flowInfo);
			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docFinalReport.getBaseId());
		baseDate.setJabgSealDate(docFinalReport.getMajorLeaderDate());//结案报告领导签字时间
		baseDateService.save(baseDate);
		finalReportDao.saveOrUpdate(docFinalReport);

	}

	/**
	 * 查询结案报告信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocFinalReport getDocInfo(String id) {
		return finalReportDao.get(id);
	}

	/**
	 * 根据baseId和文书id查询文书预览地址
	 * @param baseId
	 * @param securityAdminId
	 * @return
	 */
	public TeeJson getSecurityAdminPath(String baseId, String securityAdminId){
		TeeJson json = new TeeJson();
		String hql = "from PunishFLow where baseId='"+baseId+"' and punishDocId='"+securityAdminId+"' and punishPrcsName='行政处罚决定书'";
		List<PunishFLow> list = punishFlowDao.find(hql, null);
		String path = list.get(0).getContentsFilepath();
		if(!TeeUtility.isNullorEmpty(path)){
			json.setRtData(path);
			json.setRtState(true);
		}
		return json;
	}
}
