package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocEvidencedescription;
import com.beidasoft.xzzf.punish.document.dao.EvidencedescriptionDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class EvidencedescriptionService extends TeeBaseService{

	@Autowired
	private EvidencedescriptionDao ecidencedescriptionDao;
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
	 * 保存取证情况及证据说明
	 * 
	 * @param docEvidencedescription
	 * @throws Exception 
	 */
	public void save(DocEvidencedescription docEvidencedescription , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
		// 获取文号
		if ("99".equals(prcsId)) {
			PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
			//获取登录人的信息
			TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
			TeeDepartment department = user.getDept();
			String dept = department.getSubordinateUnits();
			
			flowInfo.setContentsCode("");
			flowInfo.setContentsDate(TeeDateUtil.format(docEvidencedescription.getClientStampData(),"yyyyMMdd"));
			flowInfo.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(docEvidencedescription.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfo.setContentsPages(page);
			flowInfo.setContentsFilepath(path);
			flowInfo.setContentsRemark(request.getParameter(""));
			flowInfo.setContentsResponer(dept);
			flowInfo.setPunishDocId(docEvidencedescription.getId());
			flowService.update(flowInfo);
			
			PunishBase base = baseService.getbyid(docEvidencedescription.getBaseId()); //baseId 取得base实体类
			// 更新base表当事人信息
			//个人信息
			base.setPsnName(docEvidencedescription.getClientName());
			//单位信息
			base.setOrganName(docEvidencedescription.getClientName());
			//更新base对象
			baseService.update(base);                         
		    //把base表的数据添加base表子表
			PunishBaseDetail baseDetail = new PunishBaseDetail();
			BeanUtils.copyProperties(base, baseDetail);
			baseDetail.setCreateDate(Calendar.getInstance().getTime());
			baseDetail.setId(UUID.randomUUID().toString());
			BaseDetailService.save(baseDetail);
		}
		
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docEvidencedescription.getBaseId());
		baseDate.setQzqkjzjsmStartDate(docEvidencedescription.getEvidenceStartTime());//取证情况及证据说明取证开始时间
		baseDate.setQzqkjzjsmEndDate(docEvidencedescription.getEvidenceEndTime());//取证情况及证据说明取证结束时间
		baseDate.setQzqkjzjsmSignDate(docEvidencedescription.getClientStampData());//取证情况及证据说明取证当事人签字时间
		baseDateService.save(baseDate);
		
		ecidencedescriptionDao.saveOrUpdate(docEvidencedescription);

	}
	
	/**
	 * 获取文书信息（根据ID）
	 * 
	 * @param id
	 * @return
	 */
	public DocEvidencedescription getById(String id) {
		return ecidencedescriptionDao.get(id);
	}
	
	/**
	 * 更新取证情况及证据说明
	 * 
	 * @param inspectionRecord
	 */
	public void update(DocEvidencedescription docEvidencedescription) {
		ecidencedescriptionDao.update(docEvidencedescription);
	}
}
