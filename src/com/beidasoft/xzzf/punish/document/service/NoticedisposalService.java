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
import com.beidasoft.xzzf.punish.document.bean.DocNoticedisposal;
import com.beidasoft.xzzf.punish.document.dao.NoticedisposalDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class NoticedisposalService extends TeeBaseService{

	@Autowired
	private NoticedisposalDao noticedisposalDao;
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
	 * 保存抽样取证物品处理通知书
	 * @param docNoticedisposal
	 * @throws Exception 
	 */
	public void save(DocNoticedisposal docNoticedisposal,HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		String docNum = docNoticedisposal.getSampleSquence();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docNoticedisposal.setSampleAddr(map.get("DocArea"));
				docNoticedisposal.setSampleYear(map.get("DocYear"));
				docNoticedisposal.setSampleSquence(map.get("DocNum"));
				PunishFLow flowInfo = punishFlowDao.getByRunId(runId);
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docNoticedisposal.getSampleAddr());
				content.put("文号年", docNoticedisposal.getSampleYear());
				content.put("文号", docNoticedisposal.getSampleSquence());
				//获取登录人的信息
				TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
				TeeDepartment department = user.getDept();
				String dept = department.getSubordinateUnits();
				
				String numb = "（"+docNoticedisposal.getSampleAddr() + "）文改字〔" +docNoticedisposal.getSampleYear()+"〕第" + docNoticedisposal.getSampleSquence()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docNoticedisposal.getStampDate(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docNoticedisposal.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docNoticedisposal.getId());
				flowService.update(flowInfo);
				
				PunishBase base = baseService.getbyid(docNoticedisposal.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				//个人信息
				base.setPsnName(docNoticedisposal.getPartName());
				//单位信息
				base.setOrganName(docNoticedisposal.getPartName());
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
		baseDate.setBaseId(docNoticedisposal.getBaseId());
		baseDate.setCyqzwqcltzsSatrtDate(docNoticedisposal.getStartData());
		baseDate.setCyqzwqcltzsEndDate(docNoticedisposal.getEndData());
		baseDate.setCyqzwqcltzsSealDate(docNoticedisposal.getStampDate());
		baseDate.setCyqzwqcltzsArriveDate(docNoticedisposal.getSendDate());
		baseDate.setCyqzwqcltzsReceiveDate(docNoticedisposal.getReceiptDate());
		baseDateService.save(baseDate);
		
		noticedisposalDao.saveOrUpdate(docNoticedisposal);
	
	}
	
	/**
	 * 获取抽样取证物品处理通知书
	 * @param id
	 * @return
	 */
	public DocNoticedisposal getById(String id) {
		return noticedisposalDao.get(id);
	}
	
	/**
	 * 修改抽样取证物品处理通知书
	 * @param docNoticedisposal
	 */
	public void update(DocNoticedisposal docNoticedisposal) {
		noticedisposalDao.update(docNoticedisposal);
	}
}
