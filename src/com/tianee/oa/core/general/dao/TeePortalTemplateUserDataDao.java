package com.tianee.oa.core.general.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeePortalTemplateUserData;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;

@Repository("TeePortalTemplateUserDataDao")
public class TeePortalTemplateUserDataDao extends TeeBaseDao<TeePortalTemplateUserData>{
	/**
	 * 获取当前登录人的桌面模板信息
	 * @param person
	 * @return
	 */
	public TeePortalTemplateUserData getTemplateUserData(TeePerson person) {
		String hql=" from TeePortalTemplateUserData d where d.user.uuid="+person.getUuid()+" and d.portalTemplate.sid="+person.getDesktop();
		List<TeePortalTemplateUserData> userData = find(hql, null);
		if(null!= userData && userData.size()>0){
			return userData.get(0);
		}
		return null;
	}

	public void reset(TeePerson person, int templateId) {
		String hql=" delete TeePortalTemplateUserData d where d.user.uuid="+person.getUuid()+" and d.portalTemplate.sid="+templateId;
		executeUpdate(hql, null);
	}
	
}