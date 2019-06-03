package com.beidasoft.xzzf.punish.manage.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishCalendar;
import com.beidasoft.xzzf.punish.common.service.PunishCalendarService;
import com.beidasoft.xzzf.punish.document.bean.DocArticlesMain;
import com.beidasoft.xzzf.punish.document.bean.DocSealseizure;
import com.beidasoft.xzzf.punish.document.service.ArticlesMainService;
import com.beidasoft.xzzf.punish.document.service.SealseizureService;
import com.beidasoft.xzzf.punish.manage.bean.PunishSealseizure;
import com.beidasoft.xzzf.punish.manage.dao.PunishSealseizureDao;
import com.beidasoft.xzzf.punish.manage.model.PunishSealseizureModel;
import com.tianee.oa.core.base.dam.service.TeeDamFilesService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.workflow.TeeWorkFlowServiceContextInterface;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.service.TeeFlowTypeServiceInterface;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.flowrun.service.TeeFlowRunPrcsServiceInterface;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class PunishSealseizureService extends TeeBaseService {
	@Autowired
	private PunishSealseizureDao punishSealseizureDao;
	@Autowired
	private ArticlesMainService articlesMainService;
	@Autowired
	private SealseizureService sealseizureService;
	@Autowired
	private PunishCalendarService punishCalendarService;
	@Autowired
	private TeeWorkFlowServiceContextInterface flowServiceContext;
	@Autowired
	private TeeFlowRunPrcsServiceInterface flowRunPrcsService;
	@Autowired
	private TeeFlowTypeServiceInterface flowTypeService;
	@Autowired
	private TeeDamFilesService damFilesService;
	
	//查封扣押办结流程
	public TeeJson punishTurnEnd(PunishSealseizureModel model, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"), 0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
		flowServiceContext.getFlowRunPrcsService().turnEnd(frpSid);
		
		TeeFlowRunPrcs currentFrp=flowRunPrcsService.get(frpSid);
		TeeFlowType flowType=flowTypeService.get(flowId);
		
		//处理自动归档
		int autoArchive=flowType.getAutoArchive();
		int autoArchiveValue=flowType.getAutoArchiveValue();
		Map reqData=new HashMap();
		reqData.put("loginUser", currentFrp.getPrcsUser());
		reqData.put("runId", currentFrp.getFlowRun().getRunId());
		if(autoArchive==1){//自动归档
			if((autoArchiveValue&1)==1){//表单
				 reqData.put("hasBd", 1);
			}else{
				reqData.put("hasBd", 0);
			}
			if((autoArchiveValue&2)==2){//签批单
				 reqData.put("hasQpd", 1);
			}else{
				reqData.put("hasQpd", 0);
			}
			if((autoArchiveValue&4)==4){//正文
				 reqData.put("hasZw", 1);
			}else{
				reqData.put("hasZw", 0);
			}
			if((autoArchiveValue&8)==8){//版式正文
				 reqData.put("hasBszw", 1);
			}else{
				reqData.put("hasBszw", 0);
			}
			if((autoArchiveValue&16)==16){//附件
				 reqData.put("hasFj", 1);
			}else{
				reqData.put("hasFj", 0);
			}
			
			damFilesService.workFlowArchive(reqData);
		}
		
		json = savePunishSealseizure(model, request);
		return json;
	}
	
	/**
	 * 保存
	 * @param model
	 * @param request
	 * @return
	 */
	public TeeJson savePunishSealseizure(PunishSealseizureModel model, HttpServletRequest request) {
		TeeJson json = new TeeJson();
		PunishSealseizure punishSealseizure = new PunishSealseizure();
		
		//查封扣押决定书id
		punishSealseizure.setDocSealseizureId(model.getDocSealseizureId());
		//物品清单主表id
		punishSealseizure.setArticlesMainId(model.getArticlesMainId());
		//uuid
		punishSealseizure.setId(UUID.randomUUID().toString());
		//查封扣押决定书
		DocSealseizure docSealseizure = new DocSealseizure();
		if(!TeeUtility.isNullorEmpty(model.getDocSealseizureId())){
			docSealseizure = sealseizureService.getById(model.getDocSealseizureId());
		}
		if(!TeeUtility.isNullorEmpty(docSealseizure)){
			//案件唯一标识
			punishSealseizure.setBaseId(docSealseizure.getBaseId());
			//查封扣押决定书文号   格式：（京）文（封）字〔2018〕第100号
			StringBuffer docSealseizureCode = new StringBuffer("");
			docSealseizureCode.append("（");
			docSealseizureCode.append(docSealseizure.getDocArea());
			docSealseizureCode.append("）文（");
			docSealseizureCode.append((Integer.parseInt(docSealseizure.getDocNumType())==0?"封":"扣"));
			docSealseizureCode.append("）字〔");
			docSealseizureCode.append(docSealseizure.getDocYear());
			docSealseizureCode.append("〕第");
			docSealseizureCode.append(docSealseizure.getDocNum());
			docSealseizureCode.append("号");
			punishSealseizure.setDocSealseizureCode(docSealseizureCode.toString());
			//查封期限起
			punishSealseizure.setProcessDecisionDateStart(docSealseizure.getProcessDecisionDateStart());
			//查封期限末
			punishSealseizure.setProcessDecisionDateEnd(docSealseizure.getProcessDecisionDateEnd());
		}
		
		//是否送鉴定，默认0
		punishSealseizure.setIsAppraisal(0);
		
		//物品清单主表
		if (!"".equals(model.getArticlesMainId())) {
			String[] articlesStr = model.getArticlesMainId().split(",");
			StringBuffer articlesMainCode = new StringBuffer("");
			for(int i = 0; i < articlesStr.length; i++) {
				DocArticlesMain docArticlesMain = articlesMainService.getById(articlesStr[i]);
				if(!TeeUtility.isNullorEmpty(docArticlesMain)){
					//物品清单文号   格式：（昌）文〔2018〕第12号
					articlesMainCode.append("（");
					articlesMainCode.append(docArticlesMain.getDocArea());
					articlesMainCode.append("）文〔");
					articlesMainCode.append(docArticlesMain.getDocYear());
					articlesMainCode.append("〕第");
					articlesMainCode.append(docArticlesMain.getDocNum());
					articlesMainCode.append("号");
					
				}
			}
			punishSealseizure.setArticlesMainCode(articlesMainCode.toString());
		}
		
//		DocArticlesMain docArticlesMain = null;
//		if(!TeeUtility.isNullorEmpty(model.getArticlesMainId())){
//			docArticlesMain = articlesMainService.getById(model.getArticlesMainId());
//		}
//		if(!TeeUtility.isNullorEmpty(docArticlesMain)){
//			//物品清单文号   格式：（昌）文〔2018〕第12号
//			StringBuffer articlesMainCode = new StringBuffer("");
//			articlesMainCode.append("（");
//			articlesMainCode.append(docArticlesMain.getDocArea());
//			articlesMainCode.append("）文〔");
//			articlesMainCode.append(docArticlesMain.getDocYear());
//			articlesMainCode.append("〕第");
//			articlesMainCode.append(docArticlesMain.getDocNum());
//			articlesMainCode.append("号");
//			punishSealseizure.setArticlesMainCode(articlesMainCode.toString());
//		}
		
		//当前主办人
		TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		punishSealseizure.setUserId(user.getUuid());
		punishSealseizure.setUserName(user.getUserName());
		
		//保存
		save(punishSealseizure);
		
		//创建计时器
		PunishCalendar pc = new PunishCalendar();
		pc.setBaseId(punishSealseizure.getBaseId());
		pc.setRemindUrl("/platform/punish/transact/articlesDetail.jsp?pid="+punishSealseizure.getId());
		pc.setStartTime(punishSealseizure.getProcessDecisionDateStart().getTime());
		pc.setClosedTime(punishSealseizure.getProcessDecisionDateEnd().getTime());
		pc.setRemindDaily(3);
		pc.setRemindType("100");
		pc.setRemindTemplet("您的查封扣押管理");
		pc.setRemindTitle("查封扣押管理");
		pc.setStatus("100");
		pc.setUser(user);
		pc.setPrimaryId(punishSealseizure.getId());
		punishCalendarService.addPCalendar(pc);
		json.setRtState(true);
		json.setRtMsg("流程已结束");
		return json;
	}
	
	/**
	 * 修改
	 * @param model
	 * @return
	 */
	public TeeJson updatePunishSealseizure(PunishSealseizureModel model){
		TeeJson json = new TeeJson();
		PunishSealseizure punishSealseizure = loadById(model.getId());
		//是否送鉴定
		punishSealseizure.setIsAppraisal(model.getIsAppraisal());
		//是否延期
		punishSealseizure.setIsDelay(model.getIsDelay());
		//送检日期
		if(!TeeUtility.isNullorEmpty(model.getCheckupDateStartStr())){
			punishSealseizure.setCheckupDateStart(TeeDateUtil.format(model.getCheckupDateStartStr(), "yyyy-MM-dd"));
		}
		//送检结束日期
		if(!TeeUtility.isNullorEmpty(model.getCheckupDateEndStr())){
			punishSealseizure.setCheckupDateEnd(TeeDateUtil.format(model.getCheckupDateEndStr(), "yyyy-MM-dd"));
		}
		//保存附件id
		if(!TeeUtility.isNullorEmpty(model.getAttachId())){
			punishSealseizure.setAttachId(model.getAttachId());
		}
		//延期日期
		if(!TeeUtility.isNullorEmpty(model.getDelayDateStr())){
			punishSealseizure.setDelayDate(TeeDateUtil.format(model.getDelayDateStr(), "yyyy-MM-dd"));
		}
		//修改查封扣押管理表，增加以上数据
		update(punishSealseizure);
		if ("1".equals(punishSealseizure.getIsAppraisal().toString())) {
			//暂停或开始计时器
			PunishCalendar pc = new PunishCalendar();
			pc.setPrimaryId(punishSealseizure.getId());
			long startTimes = 0;
			if (!TeeUtility.isNullorEmpty(punishSealseizure.getCheckupDateStart())) {
				startTimes = punishSealseizure.getCheckupDateStart().getTime();
			}
			long endTimes = 0;
			if (!TeeUtility.isNullorEmpty(punishSealseizure.getCheckupDateEnd())) {
				endTimes = punishSealseizure.getCheckupDateEnd().getTime();
			}
			punishCalendarService.changePCalendar(pc, startTimes, endTimes);
		}
		if ("1".equals(punishSealseizure.getIsDelay().toString())) {
			PunishCalendar pc = new PunishCalendar();
			pc.setPrimaryId(punishSealseizure.getId());
			long delayTimes = 0;
			if (!TeeUtility.isNullorEmpty(punishSealseizure.getDelayDate())) {
				delayTimes = punishSealseizure.getDelayDate().getTime();
			}
			punishCalendarService.changePunishCalendar(pc, delayTimes);
		}
		
		return json;
	}
	
	
	
	/**
	 * 分页查询
	 * @param dm
	 * @param request
	 * @param baseId
	 * @return
	 */
	public TeeEasyuiDataGridJson getSealseizureOfPage(TeeDataGridModel dm, HttpServletRequest request, String baseId) {
		TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
		//查询条件list
		List param = new ArrayList();
		//基础hql
		String hql = " from PunishSealseizure  where 1=1 ";
		String baseIdFmt = TeeDbUtility.formatString(baseId);
		hql = hql + "and baseId='" + baseIdFmt + "' ";
		long total = simpleDaoSupport.count("select count(id) " + hql, param.toArray());
		datagrid.setTotal(total);
		List<PunishSealseizure> list = simpleDaoSupport.pageFind(hql + " order by docSealseizureCode ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param.toArray());
		
		List<PunishSealseizureModel> models = new ArrayList<PunishSealseizureModel>();
		PunishSealseizureModel model = null;
		for(PunishSealseizure row : list) {
			model = new PunishSealseizureModel();
			model = transferModel(row);
			models.add(model);
		}
		datagrid.setRows(models);
		
		return datagrid;
	}
	
	/**
	 * 将bean转换为model
	 * @param PunishSealseizureBean
	 * @return
	 */
	private PunishSealseizureModel transferModel(PunishSealseizure punishSealseizure) {
		PunishSealseizureModel punishSealseizureModel = new PunishSealseizureModel();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		BeanUtils.copyProperties(punishSealseizure, punishSealseizureModel);
		
		//转换查封期限起
		if(punishSealseizure.getProcessDecisionDateStart() != null) {
			punishSealseizureModel.setProcessDecisionDateStartStr(dateFormat.format(punishSealseizure.getProcessDecisionDateStart()));
		}

		//转换查封期限末
		if(punishSealseizure.getProcessDecisionDateEnd() != null) {
			punishSealseizureModel.setProcessDecisionDateEndStr(dateFormat.format(punishSealseizure.getProcessDecisionDateEnd()));
		}

		//转换鉴定开始时间
		if(punishSealseizure.getCheckupDateStart() != null) {
			punishSealseizureModel.setCheckupDateStartStr(dateFormat.format(punishSealseizure.getCheckupDateStart()));
		}

		//转换鉴定结束时间
		if(punishSealseizure.getCheckupDateEnd() != null) {
			punishSealseizureModel.setCheckupDateEndStr(dateFormat.format(punishSealseizure.getCheckupDateEnd()));
		}
		
		return punishSealseizureModel;
	}
	
	/**
	 * 保存
	 */
	public void save(PunishSealseizure punishSealseizure) {
		punishSealseizureDao.save(punishSealseizure);
	}
	
	/**
	 * 修改
	 */
	public void update(PunishSealseizure punishSealseizure) {
		punishSealseizureDao.update(punishSealseizure);
	}
	
	/**
	 * 根据id查询
	 */
	public PunishSealseizure loadById(String id) {
		return punishSealseizureDao.loadById(id);
	}
	
}
