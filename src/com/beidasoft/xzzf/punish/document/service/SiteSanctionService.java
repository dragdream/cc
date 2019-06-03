package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.beidasoft.xzzf.punish.document.bean.DocSiteSanction;
import com.beidasoft.xzzf.punish.document.dao.SiteSanctionDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class SiteSanctionService extends TeeBaseService {

	@Autowired
	private SiteSanctionDao siteSanctionDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 保存当场处罚信息
	 * @param docSiteSanction
	 * @throws Exception 
	 */
	public void saveDocInfo(DocSiteSanction docSiteSanction , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docSiteSanction.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docSiteSanction.setDocArea(map.get("DocArea"));
				docSiteSanction.setDocYear(map.get("DocYear"));
				docSiteSanction.setDocNum(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docSiteSanction.getDocArea());
				content.put("文号年", docSiteSanction.getDocYear());
				content.put("文号", docSiteSanction.getDocNum());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docSiteSanction.getDocArea() + "）文当罚字〔" +docSiteSanction.getDocYear()+"〕第" + docSiteSanction.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docSiteSanction.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docSiteSanction.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docSiteSanction.getId());
				flowService.update(flowInfo);
				
				PunishBase base = baseService.getbyid(docSiteSanction.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				// 设置处罚决定书日期日期
				base.setPunishmentDate(Calendar.getInstance().getTime());
				//个人信息
				base.setPsnName(docSiteSanction.getPenName());
				base.setPsnShopName(docSiteSanction.getPsnShopName());
				base.setPsnSex(docSiteSanction.getPsnSex());
				base.setPsnType(docSiteSanction.getPsnType());
				base.setPsnIdNo(docSiteSanction.getPsnIdNo());
				base.setPsnTel(docSiteSanction.getPsnTel()); 
				base.setPsnAddress(docSiteSanction.getPsnAddress());
				//单位信息
				base.setOrganName(docSiteSanction.getOrganName());
				base.setOrganCodeType(docSiteSanction.getOrganCodeType());
				base.setOrganCode(docSiteSanction.getOrganCode());
				base.setOrganType(docSiteSanction.getOrganType());
				base.setOrganLeadingName(docSiteSanction.getOrganLeadingName());
				base.setOrganLeadingTel(docSiteSanction.getOrganLeadingTel());
				base.setOrganAddress(docSiteSanction.getOrganAddress());
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
		baseDate.setBaseId(docSiteSanction.getBaseId());
		baseDate.setXzcfjdsDate(docSiteSanction.getLawUnitDate());
		baseDateService.save(baseDate);
		
		siteSanctionDao.saveOrUpdate(docSiteSanction);
	
	}

	/**
	 * 查询当场处罚信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocSiteSanction getDocInfo(String id) {
		return siteSanctionDao.get(id);
	}

}
