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
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocPutonrecord;
import com.beidasoft.xzzf.punish.document.dao.PutonrecordDao;
import com.beidasoft.xzzf.queries.bean.PowerBase;
import com.beidasoft.xzzf.queries.service.PowerBaseService;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class PutonrecordService extends TeeBaseService{
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDao baseDao;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PutonrecordDao docPutonrecordDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private PowerBaseService powerService;
	@Autowired
	private PowerSelectService powerSelectService;
	@Autowired
	private PunishBaseDetailService BaseDetailService;
	@Autowired
	private PunishBaseDateService baseDateService;
	/**
	 * 保存 立案审批表
	 * @param inspectionRecord
	 * @throws Exception 
	 */
	public void save(DocPutonrecord docPutonrecord,HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docPutonrecord.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docPutonrecord.setDocArea(map.get("DocArea"));
				docPutonrecord.setDocYear(map.get("DocYear"));
				docPutonrecord.setDocNum(map.get("DocNum"));
				
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docPutonrecord.getDocArea());
				content.put("文号年", docPutonrecord.getDocYear());
				content.put("文号", docPutonrecord.getDocNum());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docPutonrecord.getDocArea() + "）文执字〔" +docPutonrecord.getDocYear()+"〕第" + docPutonrecord.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docPutonrecord.getUndertakeDepartmentTime(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docPutonrecord.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docPutonrecord.getId());
				flowService.update(flowInfo);
				
				
				
				//立案审批表最终保存时    需要保存 PunishBase 表中的  案件标题字段（baseTitle）
				String title = "";
				String powerId = docPutonrecord.getCauseAction(); //获取立案审批表中的 职权ID
				PowerBase power = powerService.getById(powerId); //通过职权ID 查职权实体类
				String powerName = power.getName(); //取得职权名称
				
				PunishBase base = baseService.getbyid(docPutonrecord.getBaseId()); //baseId 取得base实体类
				if ("2".equals(base.getLitigantType())) { //如果当事人是单位
					title += base.getOrganName() + powerName;
				} else { //如果当事人是个人
					title += base.getPsnName() + powerName;
				}
				//赋值
				base.setBaseTitle(title);
				// 设置立案日期
				base.setFilingDate(Calendar.getInstance().getTime());
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(docPutonrecord.getPartyName());
				base.setPsnType(docPutonrecord.getIdNameCode());
				base.setPsnIdNo(docPutonrecord.getIdName());
				base.setPsnTel(docPutonrecord.getPartyPhone()); 
				base.setPsnAddress(docPutonrecord.getPartyAddress());
				//单位信息
				base.setOrganName(docPutonrecord.getPartyName());
				base.setOrganCodeType(docPutonrecord.getIdNameCode());
				base.setOrganCode(docPutonrecord.getIdName());
				base.setOrganType(docPutonrecord.getPartyType());
				base.setOrganLeadingName(docPutonrecord.getPartyPersonName());
				base.setOrganLeadingTel(docPutonrecord.getPartyPhone());
				base.setOrganAddress(docPutonrecord.getPartyAddress());
				//更新base对象
				baseDao.update(base);                         
			    //把base表的数据添加base表子表
				PunishBaseDetail baseDetail = new PunishBaseDetail();
				BeanUtils.copyProperties(base, baseDetail);
				baseDetail.setCreateDate(Calendar.getInstance().getTime());
				baseDetail.setId(UUID.randomUUID().toString());
				BaseDetailService.save(baseDetail);
			}
		}
		
		BasePower basePower = powerSelectService.getById(docPutonrecord.getCauseAction());
	 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
	 	powerSelectService.save(basePower);
	 	
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docPutonrecord.getBaseId());
		baseDate.setLaspDate(docPutonrecord.getUndertakeDepartmentTime());
		baseDateService.save(baseDate);
		docPutonrecordDao.saveOrUpdate(docPutonrecord);
		
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocPutonrecord getById(String id) {
		return docPutonrecordDao.get(id);
	}

	/**
	 * 更新 立案审批表
	 * @param inspectionRecord
	 */
	public void update(DocPutonrecord docPutonrecord) {
		docPutonrecordDao.update(docPutonrecord);
	}
}