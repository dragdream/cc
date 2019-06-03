package com.tianee.oa.subsys.project.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.project.bean.TeeProjectCostType;
import com.tianee.oa.subsys.project.bean.TeeProjectType;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeProjectCostTypeService extends TeeBaseService {

	/**
	 * 获取费用类型列表
	 * @param request
	 * @return
	 */
	public TeeJson getCostTypeList(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		List<TeeProjectCostType> list=simpleDaoSupport.executeQuery(" from TeeProjectCostType order by orderNum asc ", null);
		json.setRtState(true);
		json.setRtData(list);
		json.setRtMsg("数据获取成功！");	
		return json;
	}

	
	/**
	 * 根据主键删除费用类型
	 * @param request
	 * @return
	 */
	public TeeJson deleteBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCostType type=(TeeProjectCostType) simpleDaoSupport.get(TeeProjectCostType.class,sid);
		    simpleDaoSupport.deleteByObj(type);
		    json.setRtState(true);
		    json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("该费用类型不存在！");
		}
		return json;
	}

	
    /**
     * 根据主键获取费用类型详情
     * @param request
     * @return
     */
	public TeeJson getCostTypeBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectCostType type=(TeeProjectCostType) simpleDaoSupport.get(TeeProjectCostType.class,sid);
			json.setRtState(true);
			json.setRtData(type);
			json.setRtMsg("数据获取成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("获取数据失败！");
		}
		return json;
	}


	/**
	 * 新增/编辑费用类型
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
		int orderNum=TeeStringUtil.getInteger(request.getParameter("orderNum"), 0);
		if(sid>0){//编辑
			TeeProjectCostType type=(TeeProjectCostType) simpleDaoSupport.get(TeeProjectCostType.class,sid);
		    type.setOrderNum(orderNum);
		    type.setTypeName(typeName);
		    simpleDaoSupport.update(type);
		    json.setRtMsg("编辑成功！");
		}else{//新增
			TeeProjectCostType type=new TeeProjectCostType();
		    type.setOrderNum(orderNum);
		    type.setTypeName(typeName);
		    simpleDaoSupport.save(type);
		    json.setRtMsg("保存成功！");
		}
		json.setRtState(true);
		
		return json;
	}

}
