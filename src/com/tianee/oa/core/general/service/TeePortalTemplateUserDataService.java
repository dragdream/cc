package com.tianee.oa.core.general.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeePortalTemplate;
import com.tianee.oa.core.general.bean.TeePortalTemplateUserData;
import com.tianee.oa.core.general.dao.TeePortalTemplateDao;
import com.tianee.oa.core.general.dao.TeePortalTemplateUserDataDao;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeePortalTemplateUserDataService extends TeeBaseService{
	@Autowired
	TeePortalTemplateUserDataDao templateDao;
	
	@Autowired
	TeePortalTemplateDao portalDao;
	
	@Autowired
	TeeDeptDao deptDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeUserRoleDao userRoleDao;
	
	public TeePortalTemplateUserData addTemplate(TeePortalTemplateUserData template){
		templateDao.save(template);
		return template;
	}
	
	public void updateTemplate(TeePortalTemplateUserData template){
		//templateDao.update(template);
		personDao.update(template.getUser());
	}

	public TeePortalTemplateUserData getTemplateUserData(TeePerson person) {
		TeePortalTemplateUserData userData = new TeePortalTemplateUserData();
		person = personDao.get(person.getUuid());
		userData = templateDao.getTemplateUserData(person);
		if(null==userData){
			TeePortalTemplateUserData data = new TeePortalTemplateUserData();
			TeePortalTemplate template = portalDao.get(Integer.parseInt(person.getDesktop()));
			data.setUser(person);
			data.setPortalData(template.getPortalModel());
			data.setPortalTemplate(template);
			data = addTemplate(data);
			return data;
		}else{
			return userData;
		}
	}

	public void reset(TeePerson person, int templateId) {
		templateDao.reset(person,templateId);
	}

	public void setDefault(TeePerson person, int templateId) {
		templateDao.reset(person,templateId);
		person.setDesktop(String.valueOf(templateId));
		Map updateItem = new HashMap();
		updateItem.put("desktop", String.valueOf(templateId));
		personDao.update(updateItem, person.getUuid());
		
	}
	
}
