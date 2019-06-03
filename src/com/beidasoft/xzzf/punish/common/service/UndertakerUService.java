package com.beidasoft.xzzf.punish.common.service;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.punish.common.bean.PunishBase;
import com.beidasoft.xzzf.punish.common.bean.UndertakerU;
import com.beidasoft.xzzf.punish.common.dao.UndertakerUDao;
import com.beidasoft.xzzf.punish.common.model.UndertakerUModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class UndertakerUService {
	
	@Autowired
	private UndertakerUDao undertakerUDao;
	
	@Autowired
	private PunishBaseService punishBaseService;
	
	@Autowired
	private TeePersonService personService;
	
	
	public TeeJson save(HttpServletRequest request,UndertakerUModel model){
		TeeJson json = new TeeJson();
		
		UndertakerU info = new UndertakerU();
		
		//拿到baseId查询某个案件
		String baseId = request.getParameter("baseId");
		PunishBase baseInfo = punishBaseService.getbyid(baseId);
		
		//拿到原主办的信息
		Integer majorPersonId = baseInfo.getMajorPersonId();
		String majorPersonName = baseInfo.getMajorPersonName();
		//拿到原协办的信息
		Integer minorPersonId = baseInfo.getMinorPersonId();
		String minorPersonName = baseInfo.getMinorPersonName();
		
		//修改punishBase的值
		baseInfo.setMajorPersonId((TeeStringUtil.getInteger(model.getNewMajorId(), 0)));//主办id
		//获取到主办执法账号
		TeePerson teePersonMajor = personService.get(TeeStringUtil.getInteger(model.getNewMajorId(), 0));
		baseInfo.setMajorPersonCode(teePersonMajor.getPerformCode());//主办执法账号
		baseInfo.setMajorPersonName(model.getNewMajorName());//主办姓名
		
		baseInfo.setMinorPersonId((TeeStringUtil.getInteger(model.getNewMinorId(), 0)));
		//获取到协办执法账号
		TeePerson teePersonMinor = personService.get(TeeStringUtil.getInteger(model.getNewMinorId(), 0));
		baseInfo.setMinorPersonCode(teePersonMinor.getPerformCode());
		baseInfo.setMinorPersonName(model.getNewMinorName());
		
		punishBaseService.update(baseInfo);
		
		//增加承办人变更表
		model.setOldMajorId(majorPersonId.toString());//原主办人id
		model.setOldMajorName(majorPersonName);//原主办姓名
		model.setOldMinorId(minorPersonId.toString());//原协办id
		model.setOldMinorName(minorPersonName);//原协办姓名
		
		//model转info
		BeanUtils.copyProperties(model, info);
		
		//设置修改时间
		Date date = new Date();
		info.setChangeDate(date);
		
		//赋值主键id
		info.setChangeId(UUID.randomUUID().toString());
		
		//保存
		Serializable save = undertakerUDao.save(info);
		
		json.setRtState(true);
		return json;
	}

}
