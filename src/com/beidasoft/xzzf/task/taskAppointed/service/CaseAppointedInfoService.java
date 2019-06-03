package com.beidasoft.xzzf.task.taskAppointed.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishTache;
import com.beidasoft.xzzf.punish.common.bean.UndertakerU;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.dao.UndertakerUDao;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.punish.common.service.PunishTacheService;
import com.beidasoft.xzzf.punish.document.bean.DocInspectionRecord;
import com.beidasoft.xzzf.punish.document.service.InspectionRecordService;
import com.beidasoft.xzzf.task.taskAppointed.bean.CaseAppointedInfo;
import com.beidasoft.xzzf.task.taskAppointed.dao.CaseAppointedInfoDao;
import com.beidasoft.xzzf.task.taskAppointed.model.CaseAppointedInfoModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class CaseAppointedInfoService extends TeeBaseService {
	@Autowired
	private CaseAppointedInfoDao caseAppiontedInfoDao;
	
	@Autowired
	private PunishFlowService flowService;

	@Autowired
	private TeePersonService teePersonService;
	
	@Autowired
	private PunishBaseDao baseDao;
	
	@Autowired
	private PunishTacheService punishTacheService;
	
	@Autowired
	private UndertakerUDao undertakerUDao;
	
	@Autowired
	private InspectionRecordService inspectionRecordService; 
	
	//保存
	public void save(CaseAppointedInfo o) {
		caseAppiontedInfoDao.saveOrUpdate(o);
	}
	
	/**
	 * 根据分页查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<CaseAppointedInfo> listByPage(int firstResult,int rows,CaseAppointedInfoModel queryModel, TeePerson loginUser){
		return caseAppiontedInfoDao.listByPage(firstResult, rows, queryModel,  loginUser);//最后一个参数是条件查询的条件
		
	}
	/**
	 * 返回总记录结果
	 * @return
	 */
	public long getTotal(CaseAppointedInfoModel queryModel, TeePerson loginUser){
		return caseAppiontedInfoDao.getTotal(queryModel, loginUser);
		
	}
	
	public List<CaseAppointedInfo> getCaseAppointList(int taskRec, int dealType, TeePerson person) {
		List<CaseAppointedInfo> caseList = caseAppiontedInfoDao.getCaseAppointList(taskRec, dealType, person);
		return caseList;
	}
	/**
	 * 通过ID查找案件详细信息
	 * @param id
	 * @return
	 */
	public CaseAppointedInfo getByTaskRecId(String TaskRecId){
		return caseAppiontedInfoDao.getByTaskRecId(TaskRecId);
		
	}
	
	
	/**
	 * 案件办结（将案件表dealType属性改为10，不能再次进行案件指派功能）
	 * @param caseAppointedInfoModel
	 * @return
	 */
	public TeeJson appointBreak(String taskRecId) {
		TeeJson json = new TeeJson();
		
		//一：根据TaskRecId获取案件信息
		CaseAppointedInfo caseAppointedInfo = caseAppiontedInfoDao.getByTaskRecId(taskRecId);
		caseAppointedInfo.setDisposeTime(Calendar.getInstance());
		caseAppointedInfo.setDealType(10);
		this.update(caseAppointedInfo);
		json.setRtMsg("案件处理完毕");
		json.setRtState(true);
		return json;
	}
	
	/**
	 *  案件指派（添加案件执法人员、案件基础信息）
	 * @param caseAppointedInfoModel
	 * @return
	 */
	public TeeJson saveAppointed(CaseAppointedInfoModel caseAppointedInfoModel, HttpServletRequest request) {
		
		
		//登录用户信息
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		String deptName = loginUser.getDept().getDeptName();
		TeeJson json = new TeeJson();
		//一：根据TaskRecId获取案件信息
		CaseAppointedInfo caseAppointedInfo = caseAppiontedInfoDao.getByTaskRecId(caseAppointedInfoModel.getTaskRecId());
		
		//获取案件来源Id,（如果10为线索任务，20检查计划，30现场检查）
		String taskRecId =  caseAppointedInfo.getTaskRecId();
		int taskType = caseAppointedInfo.getTaskType();
		//为线索任务
		if(10 == taskType) {
			caseAppointedInfo.setIsDelete(0);
			caseAppointedInfo.setDisposeTime(Calendar.getInstance());
			//  1. 指派立案-修改案件信息（如果saveType的值是9,表示案件已经指派但是还可继续指派；如果saveType的值是10表示案件已经指派完毕）
			caseAppointedInfo.setDealType(9);
			this.update(caseAppointedInfo);
			
			// 2.添加案件基础信息并查询案件基础信息的唯一编号（caseId）
			PunishBase punishBase = new PunishBase();
			
			String uuid = UUID.randomUUID().toString();
			punishBase.setAppointTime(Calendar.getInstance());
			punishBase.setBaseId(uuid);
			punishBase.setSourceId(caseAppointedInfo.getTaskRecId()+"");
			punishBase.setSourceType("10");
				// 当事人类型
			punishBase.setLitigantType(caseAppointedInfo.getLitigantType());
			if ("1".equals(caseAppointedInfo.getLitigantType())) {
				//(当事人类型)个人
				punishBase.setPsnName(caseAppointedInfo.getPsnName());
				punishBase.setPsnAddress(caseAppointedInfo.getPsnAddress());
				punishBase.setPsnTel(caseAppointedInfo.getPsnTel());
				// 个人字号名称
				punishBase.setPsnShopName("");
					// 个人当事人性别
				punishBase.setPsnSex("");
				// 个人当事人证件类型（代码表）
				punishBase.setPsnType("");
					// 个人当事人证件号码
				punishBase.setPsnIdNo("");
			}
			if ("2".equals(caseAppointedInfo.getLitigantType())) {
				//(当事人类型)单位
				// 单位当事人类型（代码表）
				punishBase.setOrganType("1");
				punishBase.setOrganName(caseAppointedInfo.getOrganName());
				punishBase.setOrganAddress(caseAppointedInfo.getOrganAddress());
				punishBase.setOrganLeadingName(caseAppointedInfo.getOrganLeadingName());
				punishBase.setOrganLeadingTel(caseAppointedInfo.getOrganLeadingTel());
				punishBase.setOrganCodeType(caseAppointedInfo.getOrganCodeType());
				punishBase.setOrganCode(caseAppointedInfo.getOrganCode());
			}
			
			punishBase.setMajorPersonId(caseAppointedInfoModel.getMajorPersonId());
			punishBase.setMajorPersonName(caseAppointedInfoModel.getMajorPersonName());
			punishBase.setMinorPersonId(caseAppointedInfoModel.getMinorPersonId());
			punishBase.setMinorPersonName(caseAppointedInfoModel.getMinorPersonName());
			punishBase.setTaskId(caseAppointedInfo.getId());
			punishBase.setBaseCode(TeeDateUtil.format(new Date(), "yyyyMMddHHmmsssss"));
			punishBase.setDepartmentId(caseAppointedInfo.getOrganizationId());
			punishBase.setDepartmentName(deptName);
				// 状态
			punishBase.setStatus("10");
			
			//添加案件基础信息
			baseDao.save(punishBase);
			
			//添加变更表
			UndertakerU uInfo = new UndertakerU();
			
			uInfo.setChangeId(UUID.randomUUID().toString());
			uInfo.setBaseId(punishBase.getBaseId());
			uInfo.setOldMajorId(TeeStringUtil.getString(punishBase.getMajorPersonId()));
			uInfo.setOldMajorName(punishBase.getMajorPersonName());
			uInfo.setNewMajorId(TeeStringUtil.getString(punishBase.getMajorPersonId()));
			uInfo.setNewMajorName(punishBase.getMajorPersonName());
			uInfo.setOldMinorId(TeeStringUtil.getString(punishBase.getMinorPersonId()));
			uInfo.setOldMinorName(punishBase.getMinorPersonName());
			uInfo.setNewMinorId(TeeStringUtil.getString(punishBase.getMinorPersonId()));
			uInfo.setNewMinorName(punishBase.getMinorPersonName());
			Date date = new Date();
			uInfo.setChangeDate(date);
			
			//添加
			undertakerUDao.save(uInfo);
			
			//3. 添加环节信息
			PunishTache punishTache = new PunishTache();
			String tuuid = UUID.randomUUID().toString();
			punishTache.setId(tuuid);
			punishTache.setBaseId(uuid);
			punishTache.setConfTacheCode("");
			punishTache.setConfTacheIndex(1); //环节表的序列
			
				//保存方法
			punishTacheService.save(punishTache);
			
			json.setRtState(true);
			json.setRtData(punishBase);
			return json;
		} else {
			//  1. 修改案件信息（指派立案） 来自于检查的只能指派一次  指派之后即办结
					caseAppointedInfo.setDealType(10);
					caseAppointedInfo.setDisposeTime(Calendar.getInstance());
					this.update(caseAppointedInfo);
					
			// 2.获取案件信息并更新相应字段 
			PunishBase punishBase = baseDao.get(taskRecId);
			
			if (20 == taskType) {//双随机
				punishBase.setSourceType("20");
			} else if (30 == taskType) {//现场检查
				punishBase.setSourceType("30");
			} else {
				json.setRtState(false);
				json.setRtData(null);
			}
			punishBase.setSourceId(caseAppointedInfo.getTaskRecId()+"");
			
			//设置案件的主办人协办人
			punishBase.setMajorPersonId(caseAppointedInfoModel.getMajorPersonId());
			punishBase.setMajorPersonName(caseAppointedInfoModel.getMajorPersonName());
			punishBase.setMinorPersonId(caseAppointedInfoModel.getMinorPersonId());
			punishBase.setMinorPersonName(caseAppointedInfoModel.getMinorPersonName());
			punishBase.setTaskId(caseAppointedInfo.getId());
			punishBase.setBaseCode(TeeDateUtil.format(new Date(), "yyyyMMddHHmmsssss"));
			punishBase.setDepartmentId(caseAppointedInfo.getOrganizationId());
			punishBase.setDepartmentName(deptName);
			punishBase.setAppointTime(Calendar.getInstance());
			
			// 设置当前环节为立案
			punishBase.setStatus("10");
			// 设置 字段 isApply(是否立案) 为已立案
			punishBase.setIsApply(0);

			// 更新案件基础信息
			baseDao.update(punishBase);
			
			json.setRtState(true);
			return json;
		}
	}
	
	/**
	 * 修改案件信息appointed表（不予立案）
	 * @param userInfo
	 */
	public TeeJson update(CaseAppointedInfo userInfo) {
		TeeJson json = new TeeJson();
		caseAppiontedInfoDao.update(userInfo);
		json.setRtState(true);
		return json;
	}

	/**
	 * 申请立案
	 * 
	 * @param baseId
	 * @return
	 */
	public TeeJson applyRegister(String baseId) {
		TeeJson json = new TeeJson();
		PunishBase base = baseDao.get(baseId);
		
		List<DocInspectionRecord> docInspectionRecords = inspectionRecordService.getByBaseId(baseId);
		if (docInspectionRecords.size() > 0) {
			Date date = docInspectionRecords.get(0).getInspectionTimeEnd();
			//取最终检查结束时间
			for (int i = 1; i < docInspectionRecords.size(); i++) {
				if (date.before(docInspectionRecords.get(i).getInspectionTimeEnd())){ 
					date = docInspectionRecords.get(i).getInspectionTimeEnd();
				}
			}
			//立案超时
			if ((int)(Calendar.getInstance().getTime().getTime()-date.getTime())/(1000 * 60 * 60) > 48) {
				base.setIsFilingDelay(1);
			}
			if (0 == base.getIsFilingDelay()) {
				
				CaseAppointedInfo appointedInfo = new CaseAppointedInfo();
				
				CaseAppointedInfo caseAppointedInfo = getByTaskRecId(baseId);
				// 判断 taskcase 表里是否有此条数据
				if (caseAppointedInfo == null) {
					//所属部门ID
					appointedInfo.setOrganizationId(base.getChargeDeptId());
					
					//送审日期
					appointedInfo.setTaskSendTime(Calendar.getInstance().getTime());
					
					//date 转  calendar 创建日期
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(base.getInspectionDate());
					appointedInfo.setCreateTime(calendar);		
					
					// punishBase 表字段赋值 给  taskAppointed
					if (base.getSourceType().equals("20")) {
						appointedInfo.setTaskType(20);
						appointedInfo.setTaskRec(20);
						appointedInfo.setTaskRecName("双随机检查");
					} else if (base.getSourceType().equals("30")){ 
						appointedInfo.setTaskType(30);
						appointedInfo.setTaskRec(30);
						appointedInfo.setTaskRecName("现场检查");
					} else {
						appointedInfo.setTaskType(20);
						appointedInfo.setTaskRec(20);
						appointedInfo.setTaskRecName("双随机检查");
					}
					appointedInfo.setTaskRecId(base.getBaseId());
					
					//自动生成主键
					appointedInfo.setId(UUID.randomUUID().toString());
					
					String taskName = "";
					if (base.getSourceType().equals("20")) {
						taskName +="双随机检查-";
					} else if (base.getSourceType().equals("30")){
						taskName +="现场检查-";
					}
					//判断当事人类型
					String litigantType = base.getLitigantType();

					if (litigantType.equals("1")) { // 如果当事人类型是个人
						taskName += "公民-"+base.getPsnName();
						appointedInfo.setPsnName(base.getPsnName());
						appointedInfo.setPsnAddress(base.getPsnAddress());
						appointedInfo.setPsnTel(base.getPsnTel());

					} else if (litigantType.equals("2")) { // 如果当事人类型是单位
						taskName += "组织-"+base.getOrganName();
						appointedInfo.setOrganName(base.getOrganName());
						appointedInfo.setOrganType(base.getOrganType());
						appointedInfo.setOrganAddress(base.getOrganAddress());
						appointedInfo.setOrganLeadingName(base.getOrganLeadingName());
						appointedInfo.setOrganLeadingTel(base.getOrganLeadingTel());

					} else if (litigantType.equals("3")) { // 如果当事人类型是个人并且是个体商户
						taskName += "个体工商户-"+base.getPsnName();
						appointedInfo.setPsnName(base.getPsnName());
						appointedInfo.setPsnAddress(base.getPsnAddress());
						appointedInfo.setPsnTel(base.getPsnTel());
						appointedInfo.setPsnShopName(base.getPsnShopName());
					}
					//设置案件详情
					appointedInfo.setTaskName(taskName);
					
					//保存任务来源人信息
					appointedInfo.setTaskSendPersonId(base.getChargePsnId());
					appointedInfo.setTaskSendPersonName(base.getChargePsnName());
					
					//保存案件指派表
					caseAppiontedInfoDao.save(appointedInfo);
					
					//设置 punishBase  字段  是否申请立案  为  已申请立案
					base.setIsRegister(0);
					//保存案件表
					baseDao.update(base);
					
					json.setRtState(true);
					json.setRtData(appointedInfo);
					
				} else {
					json.setRtState(false);
					json.setRtData(null);
					json.setRtMsg("已申请立案，不可重复申请！");
				}
			} else {
				json.setRtState(false);
				json.setRtData(null);
				json.setRtMsg("立案超48小时，请审批");
			}
		}else {
			json.setRtState(false);
			json.setRtData(null);
			json.setRtMsg("请保存现场勘验笔录！");
		}
		
		return json;
	}

	
	/**
	 * 检查是否合格
	 * 
	 * @param baseId
	 * @return
	 */
	public TeeJson checkUnError(String baseId, int staus) {
		TeeJson json = new TeeJson();
		
		PunishBase base = baseDao.get(baseId);
		base.setIsUnerror(staus); //检查合格
		base.setIsClosed(1); //办结
		// 归档标识
		base.setPunishFlg("0");
		// 是否借阅
		base.setBorrowingFlg("0");
		baseDao.update(base);
		
		json.setRtData(base);
		json.setRtState(true);
		return json;
	}
	
}
