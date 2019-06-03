package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.power.bean.BasePower;
import com.beidasoft.xzzf.power.service.PowerSelectService;
import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocCasemovelist;
import com.beidasoft.xzzf.punish.document.dao.CasemovelistDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;


@Service
public class CasemovelistService extends TeeBaseService {
	@Autowired
	private CasemovelistDao casemovelistDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PowerSelectService powerSelectService;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	@Autowired
	private ConfTacheService confTacheService;
	/**
	 * 保存案件移送单信息
	 * @param docCasemovelist
	 * @throws Exception 
	 */
	public void saveDocInfo(DocCasemovelist docCasemovelist , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docCasemovelist.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docCasemovelist.setDocArea(map.get("DocArea"));
				docCasemovelist.setDocYear(map.get("DocYear"));
				docCasemovelist.setDocNum(map.get("DocNum"));
				
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docCasemovelist.getDocArea());
				content.put("文号字", docCasemovelist.getDocYear());
				content.put("文号", docCasemovelist.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docCasemovelist.getDocArea() + "）文移字〔" +docCasemovelist.getDocYear()+"〕第" + docCasemovelist.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docCasemovelist.getReciveLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docCasemovelist.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docCasemovelist.getId());

				flowService.update(flowInfo);
				
				BasePower basePower = powerSelectService.getById(docCasemovelist.getCauseAction());
			 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
			 	powerSelectService.save(basePower);
			 	
			 	PunishBase base = baseService.getbyid(docCasemovelist.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(docCasemovelist.getPartyName());
				base.setPsnSex(docCasemovelist.getPartyAddress());
				base.setPsnType(docCasemovelist.getPartyPhone());
				//单位信息
				base.setOrganName(docCasemovelist.getPartyName());
				base.setOrganAddress(docCasemovelist.getPartyAddress());
				base.setOrganCode(docCasemovelist.getPartyPhone());
				base.setOrganType(docCasemovelist.getPartyType());
				base.setOrganLeadingName(docCasemovelist.getPartyTypeName());
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
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docCasemovelist.getBaseId());
		//根据LinkId查询环节名
		ConfTache confTache = confTacheService.getById(docCasemovelist.getLawLinkId());
		if ("调查取证".equals(confTache.getConfTacheName())) {
			baseDate.setAjysdDcqzSendDate(docCasemovelist.getMoveLawUnitDate());//移送时间
		} else if ("责改告知".equals(confTache.getConfTacheName())) {
			baseDate.setAjysdZggzSendDate(docCasemovelist.getMoveLawUnitDate());//移送时间
		}
		baseDateService.save(baseDate);
		casemovelistDao.saveOrUpdate(docCasemovelist);
	}

	/**
	 * 查询案件移送单信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocCasemovelist getDocInfo(String id) {
		return casemovelistDao.get(id);
	}
}
