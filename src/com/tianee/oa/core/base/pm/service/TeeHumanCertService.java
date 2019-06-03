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

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.pm.bean.TeeHumanCert;
import com.tianee.oa.core.base.pm.bean.TeeHumanDoc;
import com.tianee.oa.core.base.pm.dao.TeeHumanCertDao;
import com.tianee.oa.core.base.pm.dao.TeeHumanDocDao;
import com.tianee.oa.core.base.pm.model.TeeHumanCertModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeHumanCertService extends TeeBaseService{
	
	@Autowired
	TeeHumanCertDao cerDao;
	
	@Autowired
	TeeHumanDocDao humanDocDao;
	
	public TeeHumanCert addHumanCert(TeeHumanCertModel humanCertModel) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanCert humanCert =new TeeHumanCert();
		String validTimeDesc = humanCertModel.getValidTimeDesc();
		String getTimeDesc = humanCertModel.getGetTimeDesc();
		String endTimeDesc = humanCertModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(validTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(validTimeDesc));
			humanCert.setValidTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(getTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(getTimeDesc));
			humanCert.setGetTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanCert.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanCertModel.getHumanDocSid());
		BeanUtils.copyProperties(humanCertModel, humanCert);	
		humanCert.setHumanDoc(humanDoc);
		cerDao.save(humanCert);
		return humanCert;
	}
	
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		String sid = (String)requestDatas.get("humanDocSid");
		String certCode = (String)requestDatas.get("certCode");
		String certName = (String)requestDatas.get("certName");
		String certType = (String)requestDatas.get("certType");
		String certAttr = (String)requestDatas.get("certAttr");
		List param = new ArrayList();
		String hql = "from TeeHumanCert Cert where 1=1 and Cert.humanDoc.sid="+sid;
		if(!TeeUtility.isNullorEmpty(certCode)){
			hql+=" and Cert.certCode like ?";
			param.add("%"+certCode+"%");
		}
		if(!TeeUtility.isNullorEmpty(certName)){
			hql+=" and Cert.certName like ?";
			param.add("%"+certName+"%");
		}
		if(!TeeUtility.isNullorEmpty(certType) && !"全部".equals(certType)){
			hql+=" and Cert.certType='"+certType+"'";
		}
		if(!TeeUtility.isNullorEmpty(certAttr) && !"全部".equals(certAttr)){
			hql+=" and Cert.certAttr='"+certAttr+"'";
		}
		List<TeeHumanCert> humanCerts = cerDao.pageFindByList(hql, (dm.getPage()-1)*dm.getRows(), dm.getRows(), param);
		long total = cerDao.countByList("select count(*) "+hql, param);
		List<TeeHumanCertModel> models = new ArrayList<TeeHumanCertModel>();
		for(TeeHumanCert humanCert:humanCerts){
			TeeHumanCertModel m = new TeeHumanCertModel();
			BeanUtils.copyProperties(humanCert, m);	
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			if(humanCert.getValidTime()!=null){
				m.setValidTimeDesc(formatter.format(humanCert.getValidTime().getTime()));
			}
			if(humanCert.getEndTime()!=null){
				m.setEndTimeDesc(formatter.format(humanCert.getEndTime().getTime()));
			}
			if(humanCert.getGetTime()!=null){
				m.setGetTimeDesc(formatter.format(humanCert.getGetTime().getTime()));
			}
			m.setHumanDocSid(Integer.parseInt(sid));
			String certTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CERT_TYPE", m.getCertType());
			m.setCertTypeDesc(certTypeDesc);
			String certAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CERT_ATTR", m.getCertAttr());
			m.setCertAttrDesc(certAttrDesc);
			models.add(m);
		}
		dataGridJson.setRows(models);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}
	
	public TeeHumanCertModel getModelById(int sid){
		TeeHumanCert humanCert = (TeeHumanCert)cerDao.get(sid);
		TeeHumanCertModel model = new TeeHumanCertModel();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if(humanCert.getValidTime()!=null){
			model.setValidTimeDesc(formatter.format(humanCert.getValidTime().getTime()));
		}else{
			model.setValidTimeDesc("");
		}
		if(humanCert.getEndTime()!=null){
			model.setEndTimeDesc(formatter.format(humanCert.getEndTime().getTime()));
		}else{
			model.setEndTimeDesc("");
		}
		if(humanCert.getGetTime()!=null){
			model.setGetTimeDesc(formatter.format(humanCert.getGetTime().getTime()));
		}else{
			model.setGetTimeDesc("");
		}
		model.setHumanDocSid(humanCert.getHumanDoc().getSid());
		BeanUtils.copyProperties(humanCert,model);
		String certTypeDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CERT_TYPE", model.getCertType());
		model.setCertTypeDesc(certTypeDesc);
		String certAttrDesc = TeeHrCodeManager.getChildSysCodeNameCodeNo("PM_CERT_ATTR", model.getCertAttr());
		model.setCertAttrDesc(certAttrDesc);
		return model;
	}
	
	
	public void updateHumanCert(TeeHumanCertModel humanCertModel) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		TeeHumanCert humanCert = new TeeHumanCert();
		String validTimeDesc = humanCertModel.getValidTimeDesc();
		String getTimeDesc = humanCertModel.getGetTimeDesc();
		String endTimeDesc = humanCertModel.getEndTimeDesc();
		if(!TeeUtility.isNullorEmpty(validTimeDesc)){
			Calendar cl2 = Calendar.getInstance();
			cl2.setTime(formatter.parse(validTimeDesc));
			humanCert.setValidTime(cl2);
		}
		if(!TeeUtility.isNullorEmpty(getTimeDesc)){
			Calendar cl = Calendar.getInstance();
			cl.setTime(formatter.parse(getTimeDesc));
			humanCert.setGetTime(cl);
		}
		if(!TeeUtility.isNullorEmpty(endTimeDesc)){
			Calendar cl3 = Calendar.getInstance();
			cl3.setTime(formatter.parse(endTimeDesc));
			humanCert.setEndTime(cl3);
		}
		TeeHumanDoc humanDoc = (TeeHumanDoc)humanDocDao.get(humanCertModel.getHumanDocSid());
		BeanUtils.copyProperties(humanCertModel, humanCert);	
		humanCert.setHumanDoc(humanDoc);
		cerDao.update(humanCert);
	}
	
	public void delHumanCert(int sid){
		cerDao.delete(sid);
	}
}
