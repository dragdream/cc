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
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.CommonService;
import com.beidasoft.xzzf.punish.common.service.DocWenhaoService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseDateService;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.document.bean.DocCasedeal;
import com.beidasoft.xzzf.punish.document.bean.DocSecurityAdmin;
import com.beidasoft.xzzf.punish.document.dao.CasedealDao;
import com.beidasoft.xzzf.punish.document.dao.SecurityAdminDao;
import com.beidasoft.xzzf.punish.document.model.CasedealModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.thirdparty.wenshu.service.TeeWenShuService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class CasedealService extends TeeBaseService{
	@Autowired
	private CasedealDao casedealDao;
	@Autowired
	private DocWenhaoService wenhaoService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private TeeWenShuService wenshuService;
	@Autowired
	private SecurityAdminDao securityAdminDao;
	@Autowired
	private CommonService commonService;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishBaseDateService baseDateService;
	//案件处理呈批表

	
	/**
	 * 保存案件处理呈批表
	 * @param casedealModel
	 * @param request
	 * @return
	 * @author 李敬
	 * @throws Exception 
	 */
	public TeeJson save(CasedealModel casedealModel, HttpServletRequest request) throws Exception {
		String runId = request.getParameter("runId");
		TeeJson json = new TeeJson();

		//获取登录人的信息
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeDepartment department = user.getDept();
		String dept = department.getSubordinateUnits();
		
		// 实例化实体类对象
		DocCasedeal docCasedeal = new DocCasedeal();
		DocSecurityAdmin docSecurityAdmin = new DocSecurityAdmin();
		
		CasedealModel casedealModelCopy = new CasedealModel();
		BeanUtils.copyProperties(casedealModel, casedealModelCopy);
		
		// 属性值传递
		BeanUtils.copyProperties(casedealModel, docCasedeal);
		BeanUtils.copyProperties(casedealModelCopy, docSecurityAdmin);
		
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(casedealModel.getContentTimeStr())) {
			docCasedeal.setContentTime(TeeDateUtil.format(casedealModel.getContentTimeStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(casedealModel.getRegisterTimeStr())) {
			docCasedeal.setRegisterTime(TeeDateUtil.format(casedealModel.getRegisterTimeStr(), "yyyy年MM月dd日 HH时mm分"));
		}
		if (StringUtils.isNotBlank(casedealModel.getSealtimeStr())) {
			docCasedeal.setSealtime(TeeDateUtil.format(casedealModel.getSealtimeStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(casedealModel.getLawTimeStr())) {
			docCasedeal.setLawTime(TeeDateUtil.format(casedealModel.getLawTimeStr(), "yyyy年MM月dd日"));
		}
		if (StringUtils.isNotBlank(casedealModel.getLeaderTimeStr())) {
			docCasedeal.setLeaderTime(TeeDateUtil.format(casedealModel.getLeaderTimeStr(), "yyyy年MM月dd日"));
		}
		
		if (StringUtils.isNotBlank(casedealModel.getpSendDatePenalityStr())) {
			docSecurityAdmin.setpSendDatePenality(TeeDateUtil.format(casedealModel.getpSendDatePenalityStr(), "yyyy年MM月dd日"));
		}
		// 设置创建人相关信息
		if (StringUtils.isBlank(casedealModel.getId())) {
			docCasedeal.setId(UUID.randomUUID().toString());
			docCasedeal.setCreateUserId(user.getUserId());
			docCasedeal.setCreateUserName(user.getUserName());
			docCasedeal.setCreateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "新建案件处理呈批表");
		} else {
			//设置创建人相关信息
			docCasedeal.setUpdateUserId(user.getUserId());
			docCasedeal.setUpdateUserName(user.getUserName());
			docCasedeal.setUpdateTime(Calendar.getInstance().getTime());
			//添加文书操作日志
			commonService.writeLog(request, "修改案件处理呈批表");
		}
		
		PunishFLow flowInfo = punishFlowDao.getByRunIdOrconfFlowName(runId, "案件处理呈批表");
		//案件处理呈批表的文号目录信息
		String docNum = docCasedeal.getDocNum();
		if (StringUtils.isBlank(docNum) || "0".equals(docNum)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docCasedeal.setDocArea(map.get("DocArea"));
				docCasedeal.setDocYear(map.get("DocYear"));
				docCasedeal.setDocNum(map.get("DocNum"));
				//界面回显用
				casedealModel.setDocArea(map.get("DocArea"));
				casedealModel.setDocYear(map.get("DocYear"));
				casedealModel.setDocNum(map.get("DocNum"));
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docCasedeal.getDocArea());
				content.put("文号字", docCasedeal.getDocYear());
				content.put("文号", docCasedeal.getDocNum());
				
				String numb = "（"+docCasedeal.getDocArea() + "）文加罚字〔" +docCasedeal.getDocYear()+"〕第" + docCasedeal.getDocNum()+"号";
				flowInfo.setContentsCode("");
				flowInfo.setContentsDate(TeeDateUtil.format(docCasedeal.getLawTime(),"yyyyMMdd"));
				flowInfo.setContentsNumber(numb);
				//调用方法获取文书页数（int-->String）
				String nums = wenshuService.getPages(docCasedeal.getId(), runId, content, request);
				String numsArr[] = nums.split(",");
				String page = numsArr[0];
				String path = numsArr[1];
				flowInfo.setContentsPages(page);
				flowInfo.setContentsFilepath(path);
				flowInfo.setContentsRemark(request.getParameter(""));
				flowInfo.setContentsResponer(dept);
				flowInfo.setPunishDocId(docCasedeal.getId());
				
				flowService.update(flowInfo);
			}
		}
		
		docCasedeal.setDelFlg("0");
		casedealDao.saveOrUpdate(docCasedeal);
		
		
		// 设置创建人相关信息
		if (StringUtils.isBlank(casedealModel.getpId())) {
			docSecurityAdmin.setpId(docCasedeal.getId());
			docSecurityAdmin.setCreateUserId(user.getUserId());
			docSecurityAdmin.setCreateUserName(user.getUserName());
			docSecurityAdmin.setCreateTime(Calendar.getInstance().getTime());
		} else {
			//设置创建人相关信息
			docSecurityAdmin.setUpdateUserId(user.getUserId());
			docSecurityAdmin.setUpdateUserName(user.getUserName());
			docSecurityAdmin.setUpdateTime(Calendar.getInstance().getTime());
		}
		

		
		PunishFLow flowInfos = punishFlowDao.getByRunIdOrconfFlowName(runId, "行政处罚决定书");
		String docNums = docSecurityAdmin.getpDocNum();
		if (StringUtils.isBlank(docNums) || "0".equals(docNums)) {
			String prcsId = TeeStringUtil.getString(request.getParameter("prcsId"), "");
			// 获取文号
			if ("99".equals(prcsId)) {
				Map<String, String> map = wenhaoService.getMaxNum(request);
				docSecurityAdmin.setpDocArea(map.get("DocArea"));
				docSecurityAdmin.setpDocYear(map.get("DocYear"));
				docSecurityAdmin.setpDocNum(map.get("DocNum"));
				//界面回显用
				casedealModel.setpDocArea(map.get("DocArea"));
				casedealModel.setpDocYear(map.get("DocYear"));
				casedealModel.setpDocNum(map.get("DocNum"));
				
				String jsonStr = TeeStringUtil.getString(request.getParameter("jsonObj"), "");
				Map<String, String> content = TeeJsonUtil.JsonStr2Map(jsonStr);
				content.put("文号文", docSecurityAdmin.getpDocArea());
				content.put("文号字", docSecurityAdmin.getpDocYear());
				content.put("文号", docSecurityAdmin.getpDocNum());
				
				String numbs = "（"+docSecurityAdmin.getpDocArea() + "）文加罚字〔" +docSecurityAdmin.getpDocYear()+"〕第" + docSecurityAdmin.getpDocNum()+"号";
				flowInfos.setContentsCode("");
				flowInfos.setContentsDate(TeeDateUtil.format(docSecurityAdmin.getpSendDatePenality(),"yyyyMMdd"));
				flowInfos.setContentsNumber(numbs);
				//调用方法获取文书页数（int-->String）
				String numss = wenshuService.getPages(docSecurityAdmin.getpId(), runId, content, request);
				String numsArrs[] = numss.split(",");
				String pages = numsArrs[0];
				String paths = numsArrs[1];
				flowInfos.setContentsPages(pages);
				flowInfos.setContentsFilepath(paths);
				flowInfos.setContentsRemark(request.getParameter(""));
				flowInfos.setContentsResponer(dept);
				flowInfos.setPunishDocId(docSecurityAdmin.getpId());
				flowService.update(flowInfos);
				
				PunishBase base = baseService.getbyid(docCasedeal.getBaseId()); //baseId 取得base实体类
				// 更新base表当事人信息
				// 设置处罚决定书日期日期
				base.setPunishmentDate(Calendar.getInstance().getTime());
				//更新base对象
				baseService.update(base);  
			}
		}
		docSecurityAdmin.setDelFlg("0");
		
		//保存案件时间表内对应的时间
		PunishBaseDate baseDate = new PunishBaseDate();
		baseDate.setBaseId(docSecurityAdmin.getBaseId());
		baseDate.setXzcfjdsDate(docSecurityAdmin.getpSendDatePenality());//行政处罚时间
		baseDate.setAjclcpbSealDate(docCasedeal.getLeaderTime());//呈批表领导签字时间
		baseDateService.save(baseDate);
		
		securityAdminDao.saveOrUpdate(docSecurityAdmin);
		
		
		
		casedealModel.setId(docCasedeal.getId());
		casedealModel.setpId(docSecurityAdmin.getpId());
		
		json.setRtData(casedealModel);
		json.setRtState(true);

		return json;
		
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocCasedeal getById(String id) {
		return casedealDao.get(id);
	}
	
	public TeeJson getById(String id, HttpServletRequest request) {
		
		TeeJson json = new TeeJson();
		
		DocCasedeal docCasedeal = casedealDao.get(id);
		DocSecurityAdmin docSecurityAdmin = securityAdminDao.get(id);
		
		CasedealModel casedealModel = new CasedealModel();
		
		BeanUtils.copyProperties(docCasedeal, casedealModel);
		BeanUtils.copyProperties(docSecurityAdmin, casedealModel);

		// 单独处理时间类型转换
		if (docCasedeal.getContentTime() != null) {
			casedealModel.setContentTimeStr(TeeDateUtil.format(docCasedeal.getContentTime() , 
					"yyyy年MM月dd日"));
		}
		
		if (docCasedeal.getRegisterTime() != null) {
			casedealModel.setRegisterTimeStr(TeeDateUtil.format(docCasedeal.getRegisterTime(), 
					"yyyy年MM月dd日 HH时mm分"));
		}
		if (docCasedeal.getSealtime() != null) {
			casedealModel.setSealtimeStr(TeeDateUtil.format(docCasedeal.getSealtime(), 
					"yyyy年MM月dd日"));
		}
		if (docCasedeal.getLawTime() != null) {
			casedealModel.setLawTimeStr(TeeDateUtil.format(docCasedeal.getLawTime(), 
					"yyyy年MM月dd日"));
		}
		if (docCasedeal.getLeaderTime() != null) {
			casedealModel.setLeaderTimeStr(TeeDateUtil.format(docCasedeal.getLeaderTime(), 
					"yyyy年MM月dd日"));
		}
		
		if (docSecurityAdmin.getpSendDatePenality() != null) {
			casedealModel.setpSendDatePenalityStr(TeeDateUtil.format(docSecurityAdmin.getpSendDatePenality(), 
					"yyyy年MM月dd日"));
		}

		// 返回 现场检查（勘验）记录表 json 对象
		json.setRtData(casedealModel);
		json.setRtState(true);

		return json;
	}

	/**
	 * 更新 立案审批表
	 * @param inspectionRecord
	 */
	public void update(DocCasedeal docCasedeal) {
		casedealDao.update(docCasedeal);
	}
}
