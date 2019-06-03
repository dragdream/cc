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
import com.beidasoft.xzzf.punish.document.bean.DocNotificationcorrection;
import com.beidasoft.xzzf.punish.document.dao.NotificationcorrectionDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class NotificationcorrectionService extends TeeBaseService {

	@Autowired
	private NotificationcorrectionDao notificationcorrectionDao;
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
	 * 责令改正通知书的保存
	 * 
	 * @param sealseizure
	 * @throws Exception 
	 */
	public void save(DocNotificationcorrection notificationcorrection , HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = notificationcorrection.getDocNumber();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				notificationcorrection.setDocWorld(map.get("DocArea"));
				notificationcorrection.setDocNo(map.get("DocYear"));
				notificationcorrection.setDocNumber(map.get("DocNum"));
				
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", notificationcorrection.getDocWorld());
				content.put("文号年", notificationcorrection.getDocNo());
				content.put("文号", notificationcorrection.getDocNumber());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+notificationcorrection.getDocWorld() + "）文改字〔" +notificationcorrection.getDocNo()+"〕第" + notificationcorrection.getDocNumber()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(notificationcorrection.getSealTime(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(notificationcorrection.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(notificationcorrection.getId());

				flowService.update(flowInfo);
				
			 	BasePower basePower = powerSelectService.getById(notificationcorrection.getCauseAction());
			 	basePower.setCommonlyUsed(basePower.getCommonlyUsed()+1);
			 	powerSelectService.save(basePower);
			 	
			 	PunishBase base = baseService.getbyid(notificationcorrection.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(notificationcorrection.getPartyName());
				//单位信息
				base.setOrganName(notificationcorrection.getPartyName());
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
		baseDate.setBaseId(notificationcorrection.getBaseId());
		baseDate.setZlgztzsModifyDate(notificationcorrection.getBefore());
		baseDate.setZlgztzsArriveDate(notificationcorrection.getDeliverDate());
		baseDate.setZlgztzsReceiveDate(notificationcorrection.getSignTime());
		baseDate.setZlgztzsSealDate(notificationcorrection.getSealTime());
		baseDateService.save(baseDate);
		
		notificationcorrectionDao.saveOrUpdate(notificationcorrection);
	}

	public DocNotificationcorrection getById(String id) {
		return notificationcorrectionDao.get(id);
	}
}
