package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.service.PowerSelectService;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;
import com.beidasoft.xzzf.punish.document.bean.DocSealseizure;
import com.beidasoft.xzzf.punish.document.dao.SealseizureDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.dao.TeeFlowRunDao;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class SealseizureService extends TeeBaseService{

	@Autowired
	private SealseizureDao sealseizureDao;
	@Autowired
	private TeeFlowRunDao flowRunDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private ArticlesMainService articlesMainService;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	@Autowired
	private PowerSelectService powerSelectService;
	/**
	 * 保存查封扣押决定书
	 * @param sealseizure
	 * @throws Exception 
	 */
	public void save(DocSealseizure sealseizure,HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = sealseizure.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				sealseizure.setDocArea(map.get("DocArea"));
				sealseizure.setDocYear(map.get("DocYear"));
				sealseizure.setDocNum(map.get("DocNum"));
				
				Map<String, String> tmpInfo = sealseizureDao.executeNativeUnique("select DOC_ARTICLES_ID from ZF_DOC_SEALSEIZURE where ID='"+sealseizure.getId()+"'", null);
				String articlesIdStr = tmpInfo.get("DOC_ARTICLES_ID");
				if (!"".equals(articlesIdStr)) {
					String[] articlesIdStrs = articlesIdStr.split(",");
					for (int i = 0; i < articlesIdStrs.length; i++) {
						DocArticlesMain docArticlesMain = articlesMainService.getById(articlesIdStrs[i]);
						if (docArticlesMain != null) {
							docArticlesMain.setBind("0");
							docArticlesMain.setSealseizureId("");
							articlesMainService.update(docArticlesMain);
						}
					}
				}
				//保存绑定过的物品清单
				if (!"".equals(sealseizure.getDocArticlesId())) {
					String[] articlesStr = sealseizure.getDocArticlesId().split(",");
					for(int i = 0; i < articlesStr.length; i++) {
						DocArticlesMain docArticlesMain = articlesMainService.getById(articlesStr[i]);
						if (docArticlesMain!=null) {
							docArticlesMain.setBind("1");
							docArticlesMain.setSealseizureId(sealseizure.getId());
							articlesMainService.update(docArticlesMain);
						}
					}
				}
				
				
				//目录信息
				String NumType = "";
				if ("1".equals(sealseizure.getDocNumType())) {
					NumType = "封";
				} else {
					NumType = "扣";
				}
				PunishFLow flowInfos = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", sealseizure.getDocArea());
				content.put("文号年", sealseizure.getDocYear());
				content.put("文号", sealseizure.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+sealseizure.getDocArea() + "）文"+NumType+"字〔" +sealseizure.getDocYear()+"〕第" + sealseizure.getDocNum()+"号";
				flowInfos.setContentsCode("");
				flowInfos.setContentsDate(TeeDateUtil.format(sealseizure.getAdministrationDate(),"yyyyMMdd"));
				flowInfos.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(sealseizure.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfos.setContentsPages(page);
				flowInfos.setContentsFilepath(path);
				flowInfos.setContentsRemark(request.getParameter(""));
				flowInfos.setContentsResponer(dept);
				flowInfos.setPunishDocId(sealseizure.getId());

				flowService.update(flowInfos);
				
				PunishBase base = baseService.getbyid(sealseizure.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(sealseizure.getParty());
				base.setPsnAddress(sealseizure.getPartyAddress());
				//单位信息
				base.setOrganName(sealseizure.getParty());
				base.setOrganType(sealseizure.getPartyType());
				base.setOrganLeadingName(sealseizure.getLeadingName());
				base.setOrganAddress(sealseizure.getPartyAddress());
				//更新base对象
				baseService.update(base);                         
			    //把base表的数据添加base表子表
				PunishBaseDetail baseDetail = new PunishBaseDetail();
				BeanUtils.copyProperties(base, baseDetail);
				baseDetail.setCreateDate(Calendar.getInstance().getTime());
				baseDetail.setId(UUID.randomUUID().toString());
				BaseDetailService.save(baseDetail);
			}
		}
		
		//更新查封扣押文书名
		String confFlowName = "";
		if (sealseizure.getDocNameType().equals("1")) {
			confFlowName += "查封决定书";
		} else if (sealseizure.getDocNameType().equals("2")) {
			confFlowName += "扣押决定书";
		} else {
			confFlowName += "";
		}
		if (StringUtils.isBlank(runId)) {
			runId = "0";
		}
		//更新文书流程的FLOW名称
		PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
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
		
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(sealseizure.getBaseId());
		//查封决定书
		if ("1".equals(sealseizure.getDocNameType())) {
			baseDate.setCfjdsStartDate(sealseizure.getProcessDecisionDateStart());
			baseDate.setCfjdsEndDate(sealseizure.getProcessDecisionDateEnd());
			baseDate.setCfjdsSealDate(sealseizure.getAdministrationDate());
			baseDate.setCfjdsArriveDate(sealseizure.getDeliveryTime());
			baseDate.setCfjdsReceiveDate(sealseizure.getReceiverDate());
		}
		//扣押决定书
		if ("2".equals(sealseizure.getDocNameType())) {
			baseDate.setKyjdsStartDate(sealseizure.getProcessDecisionDateStart());
			baseDate.setKyjdsEndDate(sealseizure.getProcessDecisionDateEnd());
			baseDate.setKyjdsSealDate(sealseizure.getAdministrationDate());
			baseDate.setKyjdsArriveDate(sealseizure.getDeliveryTime());
			baseDate.setKyjdsReceiveDate(sealseizure.getReceiverDate());
		}
		
		BasePower basePower = powerSelectService.getById(sealseizure.getCauseAction());
	 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
	 	powerSelectService.save(basePower);
		
		baseDateService.save(baseDate);
		sealseizureDao.saveOrUpdate(sealseizure);
		
	}
	
	
	/**
	 * 获取查封扣押决定书
	 * @param id
	 * @return
	 */
	public DocSealseizure getById(String id) {
		return sealseizureDao.loadById(id);
	}


}
