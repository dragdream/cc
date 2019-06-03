package com.tianee.oa.subsys.crm.core.product.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.bean.TeeSysLog;
import com.tianee.oa.subsys.crm.core.product.bean.TeeCrmProductsType;
import com.tianee.oa.subsys.crm.core.product.dao.TeeCrmProductsTypeDao;
import com.tianee.oa.subsys.crm.core.product.model.TeeCrmProductsTypeModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.annotation.TeeLoggingAnt;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.interceptor.TeeServiceLoggingInterceptor;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Service
public class TeeCrmProductsTypeService extends TeeBaseService {
	@Autowired
    TeeCrmProductsTypeDao crmProductsTypeDao;
	
	/**
	 * 新建或者更新
	 * @param request 
	 * @param object 模型
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeCrmProductsTypeModel object){
		StringBuffer logMsg = new StringBuffer("新建产品类型,");//
		String logType = "047A";
		TeeJson json = new TeeJson();
		TeeCrmProductsType type = new TeeCrmProductsType();
		TeeCrmProductsType parentType = null;//上级产品类型
		String parentPath = "";//上级产品类型路径
		if(object.getParentId() > 0){
			 parentType = crmProductsTypeDao.get(object.getParentId());
		}
		if(parentType != null){
			parentPath = parentType.getParentPath() + parentType.getSid() + "/";
		}
		type.setParentType(parentType);
		type.setParentPath(parentPath);
		if(object.getSid() > 0){
			TeeCrmProductsType typeOld = crmProductsTypeDao.get(object.getSid());
			
			//更新下级上级
			String oldMenuId = TeeStringUtil.getString(typeOld.getParentPath());
			crmProductsTypeDao.updateProductsTypeParentPath(oldMenuId, parentPath, typeOld.getSid());
			typeOld.setTypeName(object.getTypeName());
			typeOld.setTypeOrder(object.getTypeOrder());
			typeOld.setParentPath(parentPath);
			typeOld.setParentType(parentType);
			crmProductsTypeDao.update(typeOld);
			type = typeOld;
			logMsg.delete(0, logMsg.length());
			logMsg.append("编辑产品类型,");
			logType = "047B";
		}else{
			BeanUtils.copyProperties(object, type);
			crmProductsTypeDao.save(type);
			
		}
		json.setRtState(true);
		
		//记录日志
		logMsg.append("{名称：" + type.getTypeName() + ",排序号:" + type.getTypeOrder() + "}");
		TeeSysLog sysLog = TeeSysLog.newInstance();
		sysLog.setType(logType);
        sysLog.setRemark(logMsg.toString());
        TeeServiceLoggingInterceptor.sysLogsBufferdPool.add(sysLog);
		return json;
	}
	
	/**
	 * 获取产品类型数
	 * @param request
	 * @return
	 */
	public TeeJson getProductTypeTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeCrmProductsType> list = crmProductsTypeDao.getCrmProductsType();
		List<TeeZTreeModel> ztreeList = new ArrayList<TeeZTreeModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmProductsType type = list.get(i);
			String pId = "0";
			TeeCrmProductsType parentType = type.getParentType();
			if(parentType != null){
				pId = parentType.getSid() + "";
			}
			TeeZTreeModel ztreeModel = new TeeZTreeModel(type.getSid() + "" , type.getTypeName() , true , pId  , true , "" , "" , true);
			ztreeModel.setParent(false);
			ztreeList.add(ztreeModel);
		}
		json.setRtState(true);
		json.setRtData(ztreeList);
		return json;
	}
	
	/**
	 * 获取上级产品类型树， 
	 * @param request
	 * @return
	 */
	public TeeJson getParentProductTypeTree(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String typeStr = TeeStringUtil.getString(request.getParameter("type") , "0");//0-新建  1- 编辑
		TeeCrmProductsType productType = null;
		if(sid > 0){
			productType = crmProductsTypeDao.get(sid);
		}
		List<TeeCrmProductsType> list = new ArrayList<TeeCrmProductsType>();
		
		if(productType == null || typeStr.equals("0")){//新建
			list = crmProductsTypeDao.getCrmProductsType();
		}else{
			String parentPath = productType.getParentPath() + productType.getSid() + "/" ;
			list = crmProductsTypeDao.getCrmProductsTypeNoChildAndSelf(sid, parentPath);
		}
		
		List<TeeZTreeModel> ztreeList = new ArrayList<TeeZTreeModel>();
		for (int i = 0; i < list.size(); i++) {
			TeeCrmProductsType type = list.get(i);
			String pId = "0";
			TeeCrmProductsType parentType = type.getParentType();
			if(parentType != null){
				pId = parentType.getSid() + "";
			}
			TeeZTreeModel ztreeModel = new TeeZTreeModel(type.getSid() + "" , type.getTypeName() , true , pId  , true , "" , "" , true);
			ztreeList.add(ztreeModel);
		}
		json.setRtState(true);
		json.setRtData(ztreeList);
		return json;
	}
	
	
	/**
	 * 根据Id 删除 --- 级联删除下级产品类型
	 * @param request
	 * @return
	 */
	@TeeLoggingAnt(template="删除客户产品类型 ， {logModel.typeNames}",type="047C")
	public TeeJson deleteByIdService(HttpServletRequest request){
		TeeJson json = new TeeJson();
		String typeNames = "";
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0 );
		TeeCrmProductsType parentType = null;
		if(sid > 0){
			 parentType = crmProductsTypeDao.get(sid);
		}
		String parentPath = "";
		if(parentType != null){
			typeNames = "{名称：" +  parentType.getTypeName() + ",排序号:"+ parentType.getTypeOrder()+"}";
			parentPath = parentType.getParentPath() + parentType.getSid() + "/" ;
			int count = crmProductsTypeDao.deleteType(sid, parentPath);
		}
		TeeRequestInfoContext.getRequestInfo().getLogModel().put("typeNames", typeNames);//添加其他参数
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 获取 BY Id 
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0 );
		TeeCrmProductsType Type = null;
		TeeCrmProductsTypeModel model = null;
		if(sid > 0){
			Type = crmProductsTypeDao.get(sid);
		}
		if(Type == null){
			json.setRtState(false);
			json.setRtMsg("数据未找到，可能已被删除");
			return json;
		}
		model = parseModel(Type);
		json.setRtState(true);
		json.setRtData(model);
		return json;
	}
	
	/**
	 * 转换产品类型 模型
	 * @param type
	 * @return
	 */
	public  TeeCrmProductsTypeModel parseModel(TeeCrmProductsType  type){
		TeeCrmProductsTypeModel model = new TeeCrmProductsTypeModel();
		if(type != null){
			TeeCrmProductsType parentType = type.getParentType();
			int parentTypeId = 0;
			String parentTypeName = "";
			if(parentType != null){
				parentTypeId= parentType.getSid();
				parentTypeName = parentType.getTypeName();
			}
			BeanUtils.copyProperties(type, model);
			model.setParentId(parentTypeId);
			model.setParentName(parentTypeName);
		}
		return model;
	}
}

    