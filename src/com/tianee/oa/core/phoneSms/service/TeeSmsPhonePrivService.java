package com.tianee.oa.core.phoneSms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.base.news.bean.TeeNewsInfo;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.phoneSms.bean.TeeSmsPhonePriv;
import com.tianee.oa.core.phoneSms.dao.TeeSmsPhonePrivDao;
import com.tianee.oa.core.phoneSms.model.TeeSmsPhonePrivModel;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.oaconst.TeeModuleConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.service.TeeSimpleService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeSmsPhonePrivService extends TeeBaseService{
	@Autowired
	private TeeSmsPhonePrivDao privDao;
	
	@Autowired
	private TeePersonDao personDao;
	
	@Autowired
	private TeeSimpleService simpleService;

	/**
	 * 保存或更新
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeSmsPhonePrivModel model) {
		TeeJson json = new TeeJson();
		TeeSmsPhonePriv privInfo = new TeeSmsPhonePriv();
		if(model.getSid() > 0){
		    privInfo  = privDao.getById(model.getSid());
			if(privInfo != null){
				BeanUtils.copyProperties(model, privInfo);
				privDao.updatePhonePrivInfo(privInfo);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, privInfo);
			privDao.addPhonePrivInfo(privInfo);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}

	/**
	 * 对象转换
	 * @author nieyi
	 * @param privInfo
	 * @return
	 */
	public TeeSmsPhonePrivModel parseModel(TeeSmsPhonePriv privInfo){
		TeeSmsPhonePrivModel model = new TeeSmsPhonePrivModel();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(privInfo == null){
			return null;
		}
		BeanUtils.copyProperties(privInfo, model);
		if(!TeeUtility.isNullorEmpty(privInfo.getOutPriv())){//外发权限
			String outPrivUserNames="";
			String[] ids = privInfo.getOutPriv().split(",");
			for(int i = 0 ;i<ids.length;i++){
				TeePerson person = personDao.get(Integer.parseInt(ids[i]));
				outPrivUserNames+=person.getUserName()+",";
			}
			if(outPrivUserNames.endsWith(",")){
				outPrivUserNames = outPrivUserNames.substring(0,outPrivUserNames.length()-1);
			}
			model.setOutPrivUserNames(outPrivUserNames);
			
		}
		if(!TeeUtility.isNullorEmpty(privInfo.getRemindPriv())){//被提醒权限
			String remindPrivUserNames="";
			String[] ids = privInfo.getRemindPriv().split(",");
			for(int i = 0 ;i<ids.length;i++){
				TeePerson person = personDao.get(Integer.parseInt(ids[i]));
				remindPrivUserNames+=person.getUserName()+",";
			}
			if(remindPrivUserNames.endsWith(",")){
				remindPrivUserNames=remindPrivUserNames.substring(0,remindPrivUserNames.length()-1);
			}
			model.setRemindPrivUserNames(remindPrivUserNames);
			
		}
		if(!TeeUtility.isNullorEmpty(privInfo.getSmsRemindPriv())){//提醒权限
			String smsRemindPrivUserNames="";
			String[] ids = privInfo.getSmsRemindPriv().split(",");
			for(int i = 0 ;i<ids.length;i++){
				TeePerson person = personDao.get(Integer.parseInt(ids[i]));
				smsRemindPrivUserNames+=person.getUserName()+",";
			}
			if(smsRemindPrivUserNames.endsWith(",")){
				smsRemindPrivUserNames=smsRemindPrivUserNames.substring(0,smsRemindPrivUserNames.length()-1);
			}
			model.setSmsRemindPrivUserNames(smsRemindPrivUserNames);
			
		}
		if(!TeeUtility.isNullorEmpty(privInfo.getTypePriv())){//模块权限
			String typePrivNames="";
			String[] ids = privInfo.getTypePriv().split(",");
			for(int i=0;i<ids.length;i++){
				typePrivNames+=TeeModuleConst.MODULE_SORT_TYPE.get(ids[i])+",";
			}
			if(typePrivNames.endsWith(",")){
				typePrivNames=typePrivNames.substring(0,typePrivNames.length()-1);
			}
			model.setTypePrivNames(typePrivNames);
		}
		return model;
	}
	
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteByIdService(HttpServletRequest request,String sids) {
		TeeJson json = new TeeJson();
		privDao.delByIds(sids);
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * @author nieyi
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeSmsPhonePrivModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeSmsPhonePriv privInfo = privDao.getById(model.getSid());
			if(privInfo !=null){
				model = parseModel(privInfo);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}

	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return privDao.datagird(dm, requestDatas);
	}

	public List<TeeSmsPhonePrivModel> getTotalByConditon(Map requestDatas) {
		return privDao.getTotalByConditon(requestDatas);
	}

	public TeeJson getPhonePriv() {
		TeeJson json = new TeeJson();
		TeeSmsPhonePrivModel model = new TeeSmsPhonePrivModel();
		TeeSmsPhonePriv privInfo = privDao.getPhonePriv();
		if(privInfo !=null){
			model = parseModel(privInfo);
			json.setRtData(model);
			json.setRtState(true);
			json.setRtMsg("查询成功!");
			return json;
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关记录！");
		return json;
	}
	
	/**
	 * 根据部门，角色，人员 取到userIds
	 * @param request
	 * @return
	 */
	public TeeJson getUserIds(HttpServletRequest request){
		String toDeptIds = TeeStringUtil.getString(request.getParameter("toDeptIds"),"");
		String toRolesIds = TeeStringUtil.getString(request.getParameter("toRolesIds"),"");
		String toUserIds = TeeStringUtil.getString(request.getParameter("toUserIds"),"");//  
		toDeptIds  = TeeUtility.formatIdsQuote(toDeptIds);
		toRolesIds = TeeUtility.formatIdsQuote(toRolesIds);
		toUserIds = TeeUtility.formatIdsQuote(toUserIds);
		List<TeePerson> toUsers = null;
		toUsers = new ArrayList<TeePerson>();
		List<TeePerson> toDeptpersons = null;
		if(!TeeUtility.isNullorEmpty(toDeptIds)){
			toDeptpersons = simpleService.getToDeptPersons(toDeptIds);
			if(toDeptpersons != null && toDeptpersons.size() > 0){
				toUsers.addAll(toDeptpersons);
			}
		}
		List<TeePerson> toRolepersons = null;
		if(!TeeUtility.isNullorEmpty(toRolesIds)){
			toRolepersons = simpleService.getToRolePersons(toRolesIds);
			if(toRolepersons != null && toRolepersons.size() > 0){
				toUsers.addAll(toRolepersons);
			}
		}
		List<TeePerson> topersons = null;
		if(!TeeUtility.isNullorEmpty(toUserIds)){
			topersons = simpleService.getToUserPersons(toUserIds);
			if(topersons != null && topersons.size() > 0){
				toUsers.addAll(topersons);
			}
		}
		//去重
		toUsers = simpleService.filterExistPerson(toUsers);
		StringBuffer ids = new StringBuffer();
		for(TeePerson person:toUsers){
			ids.append(person.getUuid()+",");
		}
		if(ids.toString().endsWith(",")){
			ids = ids.deleteCharAt(ids.length()-1);
		}
		TeeJson json = new TeeJson();
		try{
			json.setRtData(ids);
			json.setRtState(true);
		} catch (Exception e) {
			json.setRtState(false);
			json.setRtMsg(e.getMessage());
		}
		return json;
	}
	
}