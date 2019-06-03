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
import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.beidasoft.xzzf.punish.document.dao.InspectionRecordDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class InspectionRecordService extends TeeBaseService{

	@Autowired
	private InspectionRecordDao inspectionRecordDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 保存 现场检查（勘验）记录表
	 * @param inspectionRecord
	 * @throws Exception 
	 */
	public void save(DocInspectionRecord inspectionRecord, HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = inspectionRecord.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				inspectionRecord.setDocArea(map.get("DocArea"));
				inspectionRecord.setDocYear(map.get("DocYear"));
				inspectionRecord.setDocNum(map.get("DocNum"));
			
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", inspectionRecord.getDocArea());
				content.put("文号年", inspectionRecord.getDocYear());
				content.put("文号", inspectionRecord.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+inspectionRecord.getDocArea() + "）文检（勘）单字〔" +inspectionRecord.getDocYear()+"〕第" + inspectionRecord.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(inspectionRecord.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(inspectionRecord.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(inspectionRecord.getId());
				flowService.update(flowInfo);
				
				PunishBase base = baseService.getbyid(inspectionRecord.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				// 设置检查日期
				base.setInspectionDate(Calendar.getInstance().getTime());
				//个人信息
				base.setPsnName(inspectionRecord.getPartyName());
				base.setPsnShopName(inspectionRecord.getPsnShopName());
				base.setPsnSex(inspectionRecord.getPsnSex());
				base.setPsnType(inspectionRecord.getPartyType());
				base.setPsnIdNo(inspectionRecord.getPsnIdNo());
				base.setPsnTel(inspectionRecord.getPsnTel()); 
				base.setPsnAddress(inspectionRecord.getPsnAddress());
				//单位信息
				base.setOrganName(inspectionRecord.getPartyName());
				base.setOrganType(inspectionRecord.getOrganType());
				base.setOrganLeadingName(inspectionRecord.getOrganLeadingName());
				base.setOrganLeadingTel(inspectionRecord.getOrganLeadingTel());
				base.setOrganAddress(inspectionRecord.getOrganAddress());
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
		baseDate.setBaseId(inspectionRecord.getBaseId());
		baseDate.setXcjckyblStartDate(inspectionRecord.getInspectionTimeStart());
		baseDate.setXcjckyblEndDate(inspectionRecord.getInspectionTimeEnd());
		baseDate.setXcjckyblSignDate(inspectionRecord.getSiteLeaderDate());
		baseDate.setXcjckyblSealDate(inspectionRecord.getLawUnitDate());
		baseDateService.save(baseDate);
		inspectionRecordDao.saveOrUpdate(inspectionRecord);
	
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocInspectionRecord getById(String id) {
		return inspectionRecordDao.get(id);
	}

	/**
	 * 更新 现场检查（勘验）记录表
	 * @param inspectionRecord
	 */
	public void update(DocInspectionRecord inspectionRecord) {
		inspectionRecordDao.update(inspectionRecord);
	}
	
	/**
	 * 根据baseId获取
	 * @param baseId
	 * @return
	 */
	public List<DocInspectionRecord> getByBaseId(String baseId) {
		return inspectionRecordDao.getAllByBaseId(baseId);
	}
}
