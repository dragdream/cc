package com.tianee.oa.core.org.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.bean.TeeUserRoleType;
import com.tianee.oa.core.org.dao.TeeUserRoleTypeDao;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;
@Service
public class TeeUserRoleTypeService  extends TeeBaseService {
	@Autowired
	private TeeUserRoleTypeDao roleTypeDao;
	
	/**
	 * 新建或者更新角色
	 * @param requestData
	 * @return
	 */
	@TeeLoggingAnt(template="{logModel.optType},名称为：{logModel.typeName}",type="004A")
	public TeeJson addOrUpdateService(Map requestData){
		TeeJson json = new TeeJson();
		TeeUserRoleType type = (TeeUserRoleType)requestData.get("userRoleType");
		String optType = "新增角色类型";
		if(type.getSid() > 0){
			optType = "修改角色类型";
			TeeUserRoleType old = roleTypeDao.get(type.getSid());
			BeanUtils.copyProperties(type, old,new String[]{"sid"});
			roleTypeDao.update(old);
		}else{
			roleTypeDao.save(type);
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("optType",  optType);//添加其他参数
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("typeName",  type.getTypeName());//添加其他参数
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取所有类型
	 * @param requestData
	 * @return
	 */
	

	public TeeEasyuiDataGridJson datagrid(Map requestData){
		TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
		List<TeeUserRoleType> list = roleTypeDao.getAllRoleType();
		json.setTotal(Long.parseLong(String.valueOf(list.size())));
		json.setRows(list);
		return json;
	}
	
	/**
	 * 获取所有类型
	 * @param requestData
	 * @return
	 */
	

	public TeeJson getAllType(Map requestData){
		TeeJson json = new TeeJson();
		List<TeeUserRoleType> list = roleTypeDao.getAllRoleType();
		json.setRtData(list);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 删除ById
	 * @param requestData
	 * @return
	 */
	@TeeLoggingAnt(template="删除角色类型,名称为：{logModel.typeName}",type="004C")
	public TeeJson deleteByIdService(Map requestData){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(requestData.get("sid"), 0);
		 String hql="from TeeUserRole tur where tur.userRoleType.sid="+sid;
		 	List<TeeUserRole> list =simpleDaoSupport.executeQuery(hql,null);
		 	if(list.size()==0){
		TeeUserRoleType old = roleTypeDao.get(sid);
		if(old != null){
			TeeRequestInfoContext.getRequestInfo().getLogModel().put("typeName",  old.getTypeName());//添加其他参数
			roleTypeDao.deleteByObj(old);
		}
		json.setRtState(true);
		 	}else{
		 		//json.setRtMsg("");
		 		json.setRtState(false);
		 	}
		return json;
	}
	
	

	
}