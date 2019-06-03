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
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocEvidenceNote;
import com.beidasoft.xzzf.punish.document.dao.EvidenceNoteDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class EvidenceNoteService extends TeeBaseService{

	@Autowired
	private EvidenceNoteDao  evidenceNoteDao;
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
	 * 保存证据先行登记保存通知书
	 * @param evidenceNoteModel
	 * @return 
	 * @return 
	 * @throws Exception 
	 */
	public void save(DocEvidenceNote evidenceNote,HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = evidenceNote.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				evidenceNote.setDocArea(map.get("DocArea"));
				evidenceNote.setDocYear(map.get("DocYear"));
				evidenceNote.setDocNum(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", evidenceNote.getDocArea());
				content.put("文号年", evidenceNote.getDocYear());
				content.put("文号", evidenceNote.getDocNum());
				
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+evidenceNote.getDocArea() + "）文保字〔" +evidenceNote.getDocYear()+"〕第" + evidenceNote.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(evidenceNote.getLawUnitDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(evidenceNote.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(evidenceNote.getId());
				flowService.update(flowInfo);
				
				BasePower basePower = powerSelectService.getById(evidenceNote.getCauseAction());
			 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
			 	powerSelectService.save(basePower);
			 	
				PunishBase base = baseService.getbyid(evidenceNote.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(evidenceNote.getPartyName());
				//单位信息
				base.setOrganName(evidenceNote.getPartyName());
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
		baseDate.setBaseId(evidenceNote.getBaseId());
		// 设置时间赋值
		baseDate.setZjxxdjbctzsSatrtDate(evidenceNote.getSaveDateStart());//证据先行登记保存期限开始
		baseDate.setZjxxdjbctzsEndDate(evidenceNote.getSaveDateEnd());//证据先行登记保存期限结束
		baseDate.setZjxxdjbctzsSealDate(evidenceNote.getLawUnitDate());//证据先行登记保存移送时间
		baseDate.setZjxxdjbctzsArriveDate(evidenceNote.getSendTime());//证据先行登记保存接收时间
		baseDate.setZjxxdjbctzsReceiveDate(evidenceNote.getReceiverSignatureDate());//证据先行登记保存签章时间
		baseDateService.save(baseDate);
		//保存证据先行登记保存通知书
		evidenceNoteDao.saveOrUpdate(evidenceNote);
		
	}

	/**
	 * 通过getById 获取证据先行登记保存通知书文书内容
	 * @param id
	 * @return
	 */
	 
	public DocEvidenceNote getById(String id){
		return evidenceNoteDao.get(id);
	}
	
	
	/**
	 * 
	 * @param evidenceNote
	 */
	public void update(DocEvidenceNote evidenceNote) {

		evidenceNoteDao.update( evidenceNote );
		
	}


}
