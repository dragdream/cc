package com.beidasoft.xzzf.transferred.service;

import java.util.ArrayList;
import java.util.List;








import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.PunishFLow;
import com.beidasoft.xzzf.punish.common.dao.PunishBaseDao;
import com.beidasoft.xzzf.punish.common.dao.PunishFlowDao;
import com.beidasoft.xzzf.punish.common.service.PunishBaseService;
import com.beidasoft.xzzf.punish.common.service.PunishFlowService;
import com.beidasoft.xzzf.transferred.bean.DocManagements;
import com.beidasoft.xzzf.transferred.dao.ManagementsDao;
import com.beidasoft.xzzf.transferred.model.ManagementsModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class ManagementsService extends TeeBaseService{
	
	@Autowired
	private ManagementsDao managementDao;
	@Autowired
	private PunishBaseService baseService;
	@Autowired
	private PunishFlowService flowService;
	@Autowired
	private PunishFlowDao punishFlowDao;
	@Autowired
	private PunishBaseDao punishBaseDao;
	/**
	 * 保存
	 * @param docManagements
	 */
	public void saveOrUpdate(DocManagements docManagement) {
		
		managementDao.saveOrUpdate(docManagement);
	}

	/**
	 * 获取文书信息（根据ID）
	 * @param id
	 * @return
	 */
	public DocManagements getById(String id) {
		return managementDao.get(id);
	}

	
	/**
	 * 通过baseId和lawLinkId 
	 * @param baseId
	 * @return
	 */
	public List<DocManagements> getByBaseId(String baseId) {
		return managementDao.getByBaseId(baseId);
		
	}
	
	public List<DocManagements> listByPage(int firstResult, int rows,
			ManagementsModel managementsModel) {
		return managementDao.listByPage(firstResult, rows, managementsModel);
	}
	
	/**
	 * 获取案卷目录责任人
	 */
	public String initCaseRespon (String baseId) {
		String partyName = "";
		PunishBase base = baseService.getbyid(baseId);
		if (base.getLitigantType().equals("2")) { //如果当事人类型是  单位   则返回单位名
			partyName += base.getOrganName();
		} else { //如果当事人类型是 个人
			partyName += base.getPsnName();
		}
		
		return partyName;
	}
	/**
	 * 获取案件名
	 * @return
	 */
	public String getCaseName(String baseId) {
		String partyCaseName = "";
		PunishBase base = baseService.getbyid(baseId);
		partyCaseName = base.getBaseTitle();
		return  partyCaseName;
	}
	/**
	 * 获取文号信息
	 * @param baseId
	 * @return
	 */
	public String initCaseMess(String baseId) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 初始化案卷目录
	 * person存办结人员信息
	 * @param punishBase
	 * @param person
	 */
	public List<DocManagements> initCaseMenu (String baseId, TeePerson person) {
		// 从punishFlow表中取文书信息
		DocManagements docManagements = new DocManagements();
		List<DocManagements> manageList = new ArrayList<DocManagements>();
 		List<PunishFLow> flowInfoList = punishFlowDao.getManageCase(baseId);
 		List<DocManagements> tmpList = new ArrayList<DocManagements>();
 		
 		String prcsNameString = null;
		for (PunishFLow flowInfo : flowInfoList) {
			docManagements = new DocManagements();
			// 题名
			prcsNameString = TeeStringUtil.getString(flowInfo.getPunishPrcsName(), "");
			docManagements.setManageTitle(prcsNameString);
			// 责任者
			docManagements.setResponsible(TeeStringUtil.getString(flowInfo.getContentsResponer(),""));
			//办结日期(行政处罚日期)
//			if (StringUtils.isNotBlank(flowInfo.getContentsDate())) {
//				docManagements.setTransferredTime(TeeStringUtil.getString(flowInfo.getContentsDate()));
//			} else {
//				docManagements.setTransferredTime("");
//			}
			docManagements.setTransferredTime(TeeStringUtil.getString(flowInfo.getContentsDate(),""));
			//文号
			docManagements.setTitanicNumber(TeeStringUtil.getString(flowInfo.getContentsNumber(), ""));
			//页次
			docManagements.setPageNumber(TeeStringUtil.getString(flowInfo.getContentsPages(),""));
			//备注
			docManagements.setManageNote(TeeStringUtil.getString(flowInfo.getContentsRemark(),""));
			//序号
			docManagements.setManageId(TeeStringUtil.getString(flowInfo.getContentsCode(),""));
//			//文书预览ID
			docManagements.setManaFilePath(TeeStringUtil.getString(flowInfo.getContentsFilepath(), ""));
			
			if ("行政处罚决定书".equals(prcsNameString)) {
				tmpList.add(docManagements);
				continue;
			}
			
			manageList.add(docManagements);
		}
		manageList.addAll(0, tmpList);
		
		return manageList;
	}
	

	
	/**
	 * 预览文书
	 * person存办结人员信息
	 * @param punishBase
	 * @param person
	 */
	public List<DocManagements> lookCasePDF (String baseId, TeePerson person) {
		// 从punishFlow表中取文书信息
		DocManagements docManagements = new DocManagements();
		List<DocManagements> manageList = new ArrayList<DocManagements>();
 		List<PunishFLow> flowInfoList = punishFlowDao.getManageCase(baseId);
// 		List<DocManagements> tmpList = new ArrayList<DocManagements>();
		for (PunishFLow flowInfo : flowInfoList) {
			docManagements = new DocManagements();
			//文书预览ID
			docManagements.setManaFilePath(TeeStringUtil.getString(flowInfo.getContentsFilepath(), ""));
			// 题名
			docManagements.setManageTitle(TeeStringUtil.getString(flowInfo.getConfFlowName(),""));
//			String prcsNameString = TeeStringUtil.getString(flowInfo.getPunishPrcsName(), "");
//			docManagements.setManageTitle(prcsNameString);
			manageList.add(docManagements);
		}
		
//		manageList.addAll(0, tmpList);
		
		return manageList;
	}

	public void delById(String baseId) {
		managementDao.delById(baseId);
		
	}

	
}
