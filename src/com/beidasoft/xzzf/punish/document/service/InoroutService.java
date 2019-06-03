package com.beidasoft.xzzf.punish.document.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocInorout;
import com.beidasoft.xzzf.punish.document.dao.InoroutDao;
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
public class InoroutService extends TeeBaseService {
	@Autowired
	private InoroutDao inoroutDao;
	@Autowired
	private TeeFlowRunDao flowRunDao;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 入库出库通知书的保存
	 * 
	 * @param inorout
	 * @throws Exception 
	 */
	public void save(DocInorout inorout , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
		PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
		// 获取文号
		if ("99".equals(prcsId)) {
			//获取登录人的信息
			TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment department = user.getDept();
			String dept = department.getSubordinateUnits();
			
			flowInfo.setContentsCode("");
			flowInfo.setContentsDate(TeeDateUtil.format(inorout.getReciveDate(),"yyyyMMdd"));
			flowInfo.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(inorout.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfo.setContentsPages(page);
			flowInfo.setContentsFilepath(path);
			flowInfo.setContentsRemark(request.getParameter(""));
			flowInfo.setContentsResponer(dept);
			flowInfo.setPunishDocId(inorout.getId());
			flowService.update(flowInfo);
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(inorout.getBaseId());
		
		baseDate.setRkckdArriveDate(inorout.getMoveDate());
		baseDate.setRkckdReceiveDate(inorout.getReciveDate());
		baseDateService.save(baseDate);
		inoroutDao.saveOrUpdate(inorout);

		String confFlowName = "";
		if (inorout.getInoutName().equals("0")) {
			confFlowName += "入库单";
		} else if (inorout.getInoutName().equals("1")) {
			confFlowName += "出库单";
		} else {
			confFlowName += "";
		}
		if (StringUtils.isBlank(runId)) {
			runId = "0";
		}
		//更新文书流程的FLOW名称
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
	}

	public DocInorout getById(String id) {
		return inoroutDao.get(id);
	}

}
