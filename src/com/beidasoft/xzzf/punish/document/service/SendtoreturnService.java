package com.beidasoft.xzzf.punish.document.service;

import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.ConfTache;
import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDate;
import com.beidasoft.xzzf.punish.common.bean.PunishBaseDetail;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.ConfTacheService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDetailService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocSendtoreturn;
import com.beidasoft.xzzf.punish.document.dao.SendtoreturnDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class SendtoreturnService extends TeeBaseService {
	@Autowired
	private SendtoreturnDao sendtoreturnDao;
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
	@Autowired
	private ConfTacheService confTacheService;

	/**
	 * 保存送达回证信息
	 * @param docSendtoreturn
	 * @param request 
	 * @throws Exception 
	 */
	public void saveDocInfo(DocSendtoreturn docSendtoreturn, HttpServletRequest request) throws Exception {
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
			flowInfo.setContentsDate(TeeDateUtil.format(docSendtoreturn.getArrvelTimeOne(),"yyyyMMdd"));
			flowInfo.setContentsNumber("");
			//调用方法获取文书页数（int-->String）
			String nums = wenshuService.getPages(docSendtoreturn.getId(), runId, request);
			String numsArr[] = nums.split(",");
			String page = numsArr[0];
			String path = numsArr[1];
			flowInfo.setContentsPages(page);
			flowInfo.setContentsFilepath(path);
			flowInfo.setContentsRemark(request.getParameter(""));
			flowInfo.setContentsResponer(dept);
			flowInfo.setPunishDocId(docSendtoreturn.getId());
			
			//保存案件时间表内对应的时间
			PunishBaseDate baseDate = new PunishBaseDate();
			baseDate.setBaseId(docSendtoreturn.getBaseId());
			
			ConfTache confTache = confTacheService.getById(docSendtoreturn.getLawLinkId());
			if ("调查取证".equals(confTache.getConfTacheName())) {
				baseDate.setSdhzDcqzArriveDate(docSendtoreturn.getArrvelTime());
			} else if ("责改告知".equals(confTache.getConfTacheName())) {
				baseDate.setSdhzZggzArriveDate(docSendtoreturn.getArrvelTime());
			} else if ("案件呈批".equals(confTache.getConfTacheName())) {
				baseDate.setSdhzAjcpArriveDate(docSendtoreturn.getArrvelTime());
			}
			baseDateService.save(baseDate);
			
			sendtoreturnDao.saveOrUpdate(docSendtoreturn);
			
			String str = "";
			if (StringUtils.isNotBlank(docSendtoreturn.getSendDoc1Id())) {
				str += docSendtoreturn.getSendDoc1Id();
				if (StringUtils.isNotBlank(docSendtoreturn.getSendDoc2Id())) {
					str += "," + docSendtoreturn.getSendDoc2Id();
					if (StringUtils.isNotBlank(docSendtoreturn.getSendDoc3Id())) {
						str += "," + docSendtoreturn.getSendDoc3Id();
					}
				}
			}
			flowInfo.setSendDocsId(str);
			flowService.update(flowInfo);
			
			PunishBase base = baseService.getbyid(docSendtoreturn.getBaseId()); //baseId 取得base实体类
			// 更新base表当事人信息
			//个人信息
			base.setPsnName(docSendtoreturn.getArrvelPeople());
			//单位信息
			base.setOrganName(docSendtoreturn.getArrvelPeople());
			//更新base对象
			baseService.update(base);                         
		    //把base表的数据添加base表子表
			PunishBaseDetail baseDetail = new PunishBaseDetail();
			BeanUtils.copyProperties(base, baseDetail);
			baseDetail.setCreateDate(Calendar.getInstance().getTime());
			baseDetail.setId(UUID.randomUUID().toString());
			BaseDetailService.save(baseDetail);
		}else {
			//保存案件时间表内对应的时间
			PunishBaseDate baseDate = new PunishBaseDate();
			baseDate.setBaseId(docSendtoreturn.getBaseId());
			
			ConfTache confTache = confTacheService.getById(docSendtoreturn.getLawLinkId());
			if ("调查取证".equals(confTache.getConfTacheName())) {
				baseDate.setSdhzDcqzArriveDate(docSendtoreturn.getArrvelTime());
			} else if ("责改告知".equals(confTache.getConfTacheName())) {
				baseDate.setSdhzZggzArriveDate(docSendtoreturn.getArrvelTime());
			} else if ("案件呈批".equals(confTache.getConfTacheName())) {
				baseDate.setSdhzAjcpArriveDate(docSendtoreturn.getArrvelTime());
			}
			baseDateService.save(baseDate);
			sendtoreturnDao.saveOrUpdate(docSendtoreturn);
		}
		
	}

	/**
	 * 查询送达回证信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocSendtoreturn getDocInfo(String id) {
		return sendtoreturnDao.get(id);
	}

}
