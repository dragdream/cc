package com.beidasoft.xzzf.punish.document.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocTransferlist;
import com.beidasoft.xzzf.punish.document.dao.TransferlistDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TransferlistService extends TeeBaseService {

	@Autowired
	private TransferlistDao transferlistDao;
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
	@Autowired
	private ConfTacheService confTacheService;
	/**
	 * 保存证据材料移送清单
	 * @param docTransferlist
	 * @throws Exception 
	 */
	public void saveDocInfo(DocTransferlist docTransferlist , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docTransferlist.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docTransferlist.setDocArea(map.get("DocArea"));
				docTransferlist.setDocYear(map.get("DocYear"));
				docTransferlist.setDocNum(map.get("DocNum"));
				
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docTransferlist.getDocArea());
				content.put("文号字", docTransferlist.getDocYear());
				content.put("文号", docTransferlist.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docTransferlist.getDocArea() + "）文移证〔" +docTransferlist.getDocYear()+"〕第" + docTransferlist.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docTransferlist.getReciveLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docTransferlist.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docTransferlist.getId());
				flowService.update(flowInfo);
			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docTransferlist.getBaseId());
		//根据LinkId查询环节名
		ConfTache confTache = confTacheService.getById(docTransferlist.getLawLinkId());
		if ("调查取证".equals(confTache.getConfTacheName())) {
			// 单独处理时间类型转换
			baseDate.setZjclysqdDcqzArriveDate(docTransferlist.getMoveLawUnitDate());
			baseDate.setZjclysqdDcqzReceiveDate(docTransferlist.getReciveLawUnitDate());
		} else if ("责改告知".equals(confTache.getConfTacheName())) {
			baseDate.setZjclysqdZggzArriveDate(docTransferlist.getMoveLawUnitDate());
			baseDate.setZjclysqdZggzReceiveDate(docTransferlist.getReciveLawUnitDate());
		}
		baseDateService.save(baseDate);
		
		transferlistDao.saveOrUpdate(docTransferlist);
	}
	
	/**
	 * 查询证据材料移送清单（根据ID）
	 * @param id
	 * @return
	 */
	public DocTransferlist getDocInfo(String id) {
		return transferlistDao.get(id);
	}
	
}
