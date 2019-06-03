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
import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;
import com.beidasoft.xzzf.punish.document.dao.ArticlesMainDao;
import com.beidasoft.xzzf.punish.document.model.ArticlesMainModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class ArticlesMainService extends TeeBaseService{

	@Autowired
	private ArticlesMainDao articlesMainDao;
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
	
	public void save(DocArticlesMain docArticlesMain, HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docArticlesMain.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docArticlesMain.setDocArea(map.get("DocArea"));
				docArticlesMain.setDocYear(map.get("DocYear"));
				docArticlesMain.setDocNum(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docArticlesMain.getDocArea());
				content.put("文号年", docArticlesMain.getDocYear());
				content.put("文号", docArticlesMain.getDocNum());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docArticlesMain.getDocArea() + "）文〔" +docArticlesMain.getDocYear()+"〕第" + docArticlesMain.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docArticlesMain.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docArticlesMain.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docArticlesMain.getId());
				flowService.update(flowInfo);
			}
		}
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docArticlesMain.getBaseId());
		baseDate.setWpqdSignDate(docArticlesMain.getSiteLeaderDate());//物品清单当事人签字
		baseDate.setWpqdSealDate(docArticlesMain.getLawUnitDate());//物品清单盖章时间
		baseDateService.save(baseDate);
		
		articlesMainDao.saveOrUpdate(docArticlesMain);
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocArticlesMain getById(String id) {
		return articlesMainDao.get(id);
	}

	/**
	 * 更新 立案审批表
	 * @param inspectionRecord
	 */
	public void update(DocArticlesMain docArticlesMain) {
		articlesMainDao.update(docArticlesMain);
	}
	
	/**
	 * 通过baseId 获取物品清单信息
	 * @param baseId
	 * @return
	 */
	public List<DocArticlesMain> getByBaseId(ArticlesMainModel articlesMainModel) {
		return articlesMainDao.getByBaseId(articlesMainModel);
		
	}
}