package com.beidasoft.xzzf.punish.document.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocGroupDiscussion;
import com.beidasoft.xzzf.punish.document.dao.GroupDiscussionDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class GroupDiscussionService extends TeeBaseService{

	@Autowired
	private GroupDiscussionDao groupDiscussionDao;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 保存案件集体讨论记录
	 * 
	 * @param docGroupDiscussion
	 * @throws Exception 
	 */
	public void save(DocGroupDiscussion docGroupDiscussion , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
		String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
		if (prcsId.equals("99") || prcsId.equals("98")) {
			//获取登录人的信息
			TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment department = user.getDept();
			String dept = department.getSubordinateUnits();
			
			flowInfo.setContentsCode("");
			flowInfo.setContentsDate(TeeDateUtil.format(docGroupDiscussion.getDiscussionTimeStart(),"yyyyMMdd"));
			flowInfo.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(docGroupDiscussion.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfo.setContentsPages(page);
			flowInfo.setContentsFilepath(path);
			flowInfo.setContentsRemark(request.getParameter(""));
			flowInfo.setContentsResponer(dept);
			flowInfo.setPunishDocId(docGroupDiscussion.getId());

			flowService.update(flowInfo);
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docGroupDiscussion.getBaseId());
		baseDate.setAjjttlAtartDate(docGroupDiscussion.getDiscussionTimeStart());//案件集体讨论开始时间
		baseDate.setAjjttlEndDate(docGroupDiscussion.getDiscussionTimeEnd());//案件集体讨论结束时间
		baseDateService.save(baseDate);
		groupDiscussionDao.saveOrUpdate(docGroupDiscussion);
	}

	/**
	 * 获取案件集体讨论记录文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocGroupDiscussion getById(String id) {
		return groupDiscussionDao.get(id);
	}

	/**
	 * 更新 案件集体讨论记录
	 * @param inspectionRecord
	 */
	public void update(DocGroupDiscussion docGroupDiscussion) {
		groupDiscussionDao.update(docGroupDiscussion);
	}
}
