package com.tianee.oa.core.base.pm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.pm.bean.TeeHumanContract;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.dao.TeeHumanContractDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.model.TeeHumanContractModel;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanContractService extends TeeBaseService{
	
	@Autowired
	TeeHumanContractDao contractDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	@Autowired
	private TeeAttachmentDao attachmentDao;

	public void setAttachmentDao(TeeAttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public TeeAttachmentDao getAttachmentDao() {
		return attachmentDao;
	}
	
	public TeeHumanContract addHumanContract(TeeHumanContractModel humanContractModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanContract humanContract =new TeeHumanContract();
		String invalidTimeDesc=humanContractModel.getInvalidTimeDesc();
		String validTimeDesc = humanContractModel.getValidTimeDesc();
		String endTimeDesc = humanContractModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(invalidTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(invalidTimeDesc));
			humanContract.setInvalidTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(validTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(validTimeDesc));
			humanContract.setValidTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanContract.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanContractModel.getHumanDocSid());
		BeanUtils.copyProperties(humanContractModel, humanContract);	
		humanContract.setHumanDoc(humanDoc);
		contractDao.save(humanContract);
		return humanContract;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		String hql = "from TeeHumanContract contract where 1=1 and contract.humanDoc.sid="+sid;
		String conTitle=(String)requestDatas.get("conTitle");
		String conCode=(String)requestDatas.get("conCode");
		String conType=(String)requestDatas.get("conType");
		String conStatus=(String)requestDatas.get("conStatus");
		List param = new ArrayList();
		if(!TeeUtility.isNullorEmpty(conTitle)){
			hql+=" and contract.conTitle like ?";
			param.add("%"+conTitle+"%");
		}
		if(!TeeUtility.isNullorEmpty(conCode)){
			hql+=" and contract.conCode like ?";
			param.add("%"+conCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(conType) && !"全部".equals(conType)){
			hql+=" and contract.conType='"+conType+"'";
		}
		if(!TeeUtility.isNullorEmpty(conStatus) && !"全部".equals(conStatus)){
			hql+=" and contract.conStatus='"+conStatus+"'";
		}
		List<TeeHumanContract> humanContracts = contractDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = contractDao.countByList("select count(*) "+hql, param);
		List<TeeHumanContractModel> models = new ArrayList<TeeHumanContractModel>();
		for(TeeHumanContract humanContract:humanContracts){
			TeeHumanContractModel m = new TeeHumanContractModel();
			BeanUtils.copyProperties(humanContract, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanContract.getValidTime()!=null){
				m.setValidTimeDesc(formatter.format(humanContract.getValidTime().getTime()));
			}
			if(humanContract.getInvalidTime()!=null){
				m.setInvalidTimeDesc(formatter.format(humanContract.getInvalidTime().getTime()));
			}
			if(humanContract.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanContract.getEndTime().getTime()));
			}
			if(humanContract.getRenewDate()!=null){
				m.setRenewDateDesc(formatter.format(humanContract.getRenewDate().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			String conTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_ATTR", m.getConType());
			String conAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_TYPE", m.getConAttr());
			String conStatusDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_STATUS", m.getConStatus());
			m.setConTypeDesc(conTypeDesc);
			m.setConAttrDesc(conAttrDesc);
			m.setConStatusDesc(conStatusDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 查询即将到期合同
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson queryDueToContract(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from TeeHumanContract contract where 1=1 ";
		String conTitle=(String)requestDatas.get("conTitle");
		String conCode=(String)requestDatas.get("conCode");
		String conType=(String)requestDatas.get("conType");
		String conStatus=(String)requestDatas.get("conStatus");
		List param = new ArrayList();
		
		Calendar cl = Calendar.getInstance();
		String dateStr = sdf.format(cl.getTime());
		try {
			cl.setTime(formatter.parse(dateStr+" 00:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hql+=" and contract.endTime>=?";
		param.add(cl);
		
		Calendar cl2 = Calendar.getInstance();
		cl2.add(Calendar.DAY_OF_YEAR, 10);
		hql+=" and contract.endTime<=?";
		param.add(cl2);
		if(!TeeUtility.isNullorEmpty(conTitle)){
			hql+=" and contract.conTitle like ?";
			param.add("%"+conTitle+"%");
		}
		if(!TeeUtility.isNullorEmpty(conCode)){
			hql+=" and contract.conCode like ?";
			param.add("%"+conCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(conType) && !"全部".equals(conType)){
			hql+=" and contract.conType='"+conType+"'";
		}
		if(!TeeUtility.isNullorEmpty(conStatus) && !"全部".equals(conStatus)){
			hql+=" and contract.conStatus='"+conStatus+"'";
		}
		List<TeeHumanContract> humanContracts = contractDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = contractDao.countByList("select count(*) "+hql, param);
		List<TeeHumanContractModel> models = new ArrayList<TeeHumanContractModel>();
		for(TeeHumanContract humanContract:humanContracts){
			TeeHumanContractModel m = new TeeHumanContractModel();
			BeanUtils.copyProperties(humanContract, m);	
			
			if(humanContract.getValidTime()!=null){
				m.setValidTimeDesc(formatter.format(humanContract.getValidTime().getTime()));
			}
			if(humanContract.getInvalidTime()!=null){
				m.setInvalidTimeDesc(formatter.format(humanContract.getInvalidTime().getTime()));
			}
			if(humanContract.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanContract.getEndTime().getTime()));
			}
			if(humanContract.getRenewDate()!=null){
				m.setRenewDateDesc(formatter.format(humanContract.getRenewDate().getTime()));
			}
			String conTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_ATTR", m.getConType());
			String conAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_TYPE", m.getConAttr());
			String conStatusDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_STATUS", m.getConStatus());
			m.setConTypeDesc(conTypeDesc);
			m.setConAttrDesc(conAttrDesc);
			m.setConStatusDesc(conStatusDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 查询已到期合同
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson queryExpiredContract(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from TeeHumanContract contract where 1=1 ";
		String conTitle=(String)requestDatas.get("conTitle");
		String conCode=(String)requestDatas.get("conCode");
		String conType=(String)requestDatas.get("conType");
		String conStatus=(String)requestDatas.get("conStatus");
		List param = new ArrayList();
		
		Calendar cl = Calendar.getInstance();
		String dateStr = sdf.format(cl.getTime());
		try {
			cl.setTime(formatter.parse(dateStr+" 00:00:00"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hql+=" and contract.endTime<?";
		param.add(cl);
		
		if(!TeeUtility.isNullorEmpty(conTitle)){
			hql+=" and contract.conTitle like ?";
			param.add("%"+conTitle+"%");
		}
		if(!TeeUtility.isNullorEmpty(conCode)){
			hql+=" and contract.conCode like ?";
			param.add("%"+conCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(conType) && !"全部".equals(conType)){
			hql+=" and contract.conType='"+conType+"'";
		}
		if(!TeeUtility.isNullorEmpty(conStatus) && !"全部".equals(conStatus)){
			hql+=" and contract.conStatus='"+conStatus+"'";
		}
		List<TeeHumanContract> humanContracts = contractDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = contractDao.countByList("select count(*) "+hql, param);
		List<TeeHumanContractModel> models = new ArrayList<TeeHumanContractModel>();
		for(TeeHumanContract humanContract:humanContracts){
			TeeHumanContractModel m = new TeeHumanContractModel();
			BeanUtils.copyProperties(humanContract, m);	
			
			if(humanContract.getValidTime()!=null){
				m.setValidTimeDesc(formatter.format(humanContract.getValidTime().getTime()));
			}
			if(humanContract.getInvalidTime()!=null){
				m.setInvalidTimeDesc(formatter.format(humanContract.getInvalidTime().getTime()));
			}
			if(humanContract.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanContract.getEndTime().getTime()));
			}
			if(humanContract.getRenewDate()!=null){
				m.setRenewDateDesc(formatter.format(humanContract.getRenewDate().getTime()));
			}
			String conTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_ATTR", m.getConType());
			String conAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_TYPE", m.getConAttr());
			String conStatusDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_STATUS", m.getConStatus());
			m.setConTypeDesc(conTypeDesc);
			m.setConAttrDesc(conAttrDesc);
			m.setConStatusDesc(conStatusDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	/**
	 * 查询全部合同
	 * @param dm
	 * @param requestDatas
	 * @return
	 */
	public TeeEasyuiDataGridJson queryAllContract(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String hql = "from TeeHumanContract contract where 1=1 ";
		String conTitle=(String)requestDatas.get("conTitle");
		String conCode=(String)requestDatas.get("conCode");
		String conType=(String)requestDatas.get("conType");
		String conStatus=(String)requestDatas.get("conStatus");
		
		List param = new ArrayList();
		
		if(!TeeUtility.isNullorEmpty(conTitle)){
			hql+=" and contract.conTitle like ?";
			param.add("%"+conTitle+"%");
		}
		if(!TeeUtility.isNullorEmpty(conCode)){
			hql+=" and contract.conCode like ?";
			param.add("%"+conCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(conType) && !"全部".equals(conType)){
			hql+=" and contract.conType='"+conType+"'";
		}
		if(!TeeUtility.isNullorEmpty(conStatus) && !"全部".equals(conStatus)){
			hql+=" and contract.conStatus='"+conStatus+"'";
		}
		
		List<TeeHumanContract> humanContracts = contractDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = contractDao.countByList("select count(*) "+hql, param);
		List<TeeHumanContractModel> models = new ArrayList<TeeHumanContractModel>();
		for(TeeHumanContract humanContract:humanContracts){
			TeeHumanContractModel m = new TeeHumanContractModel();
			BeanUtils.copyProperties(humanContract, m);	
			
			if(humanContract.getValidTime()!=null){
				m.setValidTimeDesc(formatter.format(humanContract.getValidTime().getTime()));
			}
			if(humanContract.getInvalidTime()!=null){
				m.setInvalidTimeDesc(formatter.format(humanContract.getInvalidTime().getTime()));
			}
			if(humanContract.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanContract.getEndTime().getTime()));
			}
			if(humanContract.getRenewDate()!=null){
				m.setRenewDateDesc(formatter.format(humanContract.getRenewDate().getTime()));
			}
			String conTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_ATTR", m.getConType());
			String conAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_TYPE", m.getConAttr());
			String conStatusDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_STATUS", m.getConStatus());
			m.setConTypeDesc(conTypeDesc);
			m.setConAttrDesc(conAttrDesc);
			m.setConStatusDesc(conStatusDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	
	public TeeHumanContractModel getModelById(int sid,String type){
		TeeHumanContract contract = (TeeHumanContract)contractDao.get(sid);
		TeeHumanContractModel model = new TeeHumanContractModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(contract.getValidTime()!=null){
			model.setValidTimeDesc(formatter.format(contract.getValidTime().getTime()));
		}else{
			model.setValidTimeDesc("");
		}
		if(contract.getInvalidTime()!=null){
			model.setInvalidTimeDesc(formatter.format(contract.getInvalidTime().getTime()));
		}else{
			model.setInvalidTimeDesc("");
		}
		if(contract.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(contract.getEndTime().getTime()));
		}else{
			model.setEndTimeDesc("");
		}
		if(contract.getRenewDate()!=null){
			model.setRenewDateDesc(formatter.format(contract.getRenewDate().getTime()));
		}else{
			model.setRenewDateDesc("");
		}
		if(contract.getLastRemindDate()!=null){
			model.setLastRemindDateDesc(formatter.format(contract.getLastRemindDate().getTime()));
		}else{
			model.setLastRemindDateDesc("");
		}
		model.setHumanDocSid(contract.getHumanDoc().getSid());
		BeanUtils.copyProperties(contract,model);
		String conTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_ATTR", model.getConType());
		String conAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_TYPE", model.getConAttr());
		String conStatusDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CONTRACT_STATUS", model.getConStatus());
		model.setConTypeDesc(conTypeDesc);
		model.setConAttrDesc(conAttrDesc);
		model.setConStatusDesc(conStatusDesc);
		
		List<TeeAttachmentModel> attachmodels = new ArrayList<TeeAttachmentModel>();
		List<TeeAttachment> attaches =this.getAttachmentDao().getAttaches(TeeAttachmentModelKeys.hrContract, String.valueOf(sid));
		for (TeeAttachment attach : attaches) {
			TeeAttachmentModel attachmentModel = new TeeAttachmentModel();
			BeanUtils.copyProperties(attach, attachmentModel);
			attachmentModel.setUserId(attach.getUser().getUuid() + "");
			attachmentModel.setUserName(attach.getUser().getUserName());
			if(type.equals("1")){
				attachmentModel.setPriv(1+2);// 一共五个权限好像
			}else{
				attachmentModel.setPriv(1 + 2+4+8+16+32);// 一共五个权限好像
			}
											// 1、2、4、8、16、32,具体权限值含义可以参考TeeAttachment
			attachmodels.add(attachmentModel);
		}
		model.setAttachMentModel(attachmodels);
		
		
		return model;
	}
	
	
	public void updateHumanContract(TeeHumanContractModel humanContractModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanContract contract = new TeeHumanContract();
		String invalidTimeDesc=humanContractModel.getInvalidTimeDesc();
		String validTimeDesc = humanContractModel.getValidTimeDesc();
		String endTimeDesc = humanContractModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(invalidTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(invalidTimeDesc));
			contract.setInvalidTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(validTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(validTimeDesc));
			contract.setValidTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			contract.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanContractModel.getHumanDocSid());
		BeanUtils.copyProperties(humanContractModel,contract);
		contract.setHumanDoc(humanDoc);
		contractDao.update(contract);
	}
	
	public void delHumanContract(int sid){
		contractDao.delete(sid);
	}
	
	public TeeHumanContract getContractById(int sid){
		return contractDao.get(sid);
	}
}


