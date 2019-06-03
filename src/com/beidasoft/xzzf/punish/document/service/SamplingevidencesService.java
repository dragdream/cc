package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.beidasoft.xzzf.punish.document.bean.DocSamplingevidences;
import com.beidasoft.xzzf.punish.document.dao.SamplingevidencesDao;
import com.beidasoft.xzzf.punish.document.model.SamplingevidencesModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class SamplingevidencesService extends TeeBaseService{
	
	@Autowired
	private  SamplingevidencesDao samplingevidencesDao;
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
	/**
	 * 保存 抽样取证凭证
	 * @param samplingevidences
	 * @throws Exception 
	 */
	public void save(DocSamplingevidences docSamplingevidences,HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docSamplingevidences.getSampleSquence();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docSamplingevidences.setSampleAddr(map.get("DocArea"));
				docSamplingevidences.setSampleYear(map.get("DocYear"));
				docSamplingevidences.setSampleSquence(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docSamplingevidences.getSampleAddr());
				content.put("文号年", docSamplingevidences.getSampleYear());
				content.put("文号", docSamplingevidences.getSampleSquence());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docSamplingevidences.getSampleAddr() + "）文抽字〔" +docSamplingevidences.getSampleYear()+"〕第" + docSamplingevidences.getSampleSquence()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docSamplingevidences.getStampDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docSamplingevidences.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docSamplingevidences.getId());
				flowService.update(flowInfo);
			 	BasePower basePower = powerSelectService.getById(docSamplingevidences.getCauseAction());
			 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
			 	powerSelectService.save(basePower);
			 	
				PunishBase base = baseService.getbyid(docSamplingevidences.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(docSamplingevidences.getClientName());
				//单位信息
				base.setOrganName(docSamplingevidences.getClientName());
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
		baseDate.setBaseId(docSamplingevidences.getBaseId());
		
		baseDate.setCyqzpzSealDate(docSamplingevidences.getStampDate());
		baseDate.setCyqzpzArriveDate(docSamplingevidences.getSendDate());
		baseDate.setCyqzpzReceiveDate(docSamplingevidences.getReceiptDate());
		baseDateService.save(baseDate);
		
		samplingevidencesDao.saveOrUpdate(docSamplingevidences);
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocSamplingevidences getById(String id) {
		return samplingevidencesDao.get(id);
	}

	/**
	 * 更新 抽样取证凭证
	 * @param samplingevidences
	 */
	public void update(DocSamplingevidences samplingevidences) {
		samplingevidencesDao.update(samplingevidences);
	}
	
	/**
	 * 通过baseId和lawLinkId 获取物品清单信息
	 * @param baseId
	 * @return
	 */
	public List<DocSamplingevidences> getByBaseId(SamplingevidencesModel samplingevidencesModel) {
		return samplingevidencesDao.getByBaseId(samplingevidencesModel);
		
	}
}
