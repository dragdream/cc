package com.tianee.oa.core.general.service;

import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeePersonalitySettings;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeePersonalitySettingsService extends TeeBaseService{
	
	/**
	 * 更新工作面板位置模型
	 * @param wfPanelPosModel
	 * @param loginPerson
	 */
	public void updateWfPanelPosModel(String wfPanelPosModel,TeePerson loginPerson){
		TeePersonalitySettings settings = getSettings(loginPerson.getUuid());
		settings.setWfPanelPosModel(wfPanelPosModel);
		simpleDaoSupport.update(settings);
	}
	
	/**
	 * 更新标星模型
	 * @param wfStarsModel
	 * @param loginPerson
	 */
	public void updateWfStarsModel(String wfStarsModel,TeePerson loginPerson){
		TeePersonalitySettings settings = getSettings(loginPerson.getUuid());
		settings.setWfStarsModel(wfStarsModel);
		simpleDaoSupport.update(settings);
	}
	
	/**
	 * 根据登陆人id获取人员个性化设置对象
	 * @param loginPerson
	 * @return
	 */
	public TeePersonalitySettings getSettings(int uuid){
		TeePersonalitySettings settings = 
			(TeePersonalitySettings) simpleDaoSupport.unique("from TeePersonalitySettings where person.uuid="+uuid, null);
		
		if(settings==null){
			settings = new TeePersonalitySettings();
			settings.setPerson((TeePerson)simpleDaoSupport.get(TeePerson.class, uuid));
			simpleDaoSupport.save(settings);
		}
		
		return settings;
	}
}
