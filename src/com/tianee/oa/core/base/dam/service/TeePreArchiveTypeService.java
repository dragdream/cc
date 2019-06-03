package com.tianee.oa.core.base.dam.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.dam.bean.TeeFiles;
import com.tianee.oa.core.base.dam.bean.TeePreArchiveType;
import com.tianee.oa.core.base.dam.model.TeePreArchiveTypeModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeePreArchiveTypeService extends TeeBaseService{

	
	/**
	 * 新建/编辑分类
	 * @param request
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
		int sortNo=TeeStringUtil.getInteger(request.getParameter("sortNo"), 0);
		String typeName=TeeStringUtil.getString(request.getParameter("typeName"));
		int managerId=TeeStringUtil.getInteger(request.getParameter("managerId"), 0);
		TeePerson manager=null;
		if(managerId!=0){
			manager=(TeePerson) simpleDaoSupport.get(TeePerson.class,managerId);
		}
		if(sid==0){//新建
			TeePreArchiveType type=new TeePreArchiveType();
			type.setSortNo(sortNo);
			type.setTypeName(typeName);
			type.setManager(manager);
			simpleDaoSupport.save(type);
		}else{//编辑
			TeePreArchiveType type=(TeePreArchiveType) simpleDaoSupport.get(TeePreArchiveType.class,sid);
			type.setSortNo(sortNo);
			type.setTypeName(typeName);
			type.setManager(manager);
			simpleDaoSupport.update(type);
		}
		
		json.setRtState(true);
		return json;
	}

	
	
	/**
	 * 获取所有分类列表
	 * @param dm
	 * @param request
	 * @return
	 */
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,
			HttpServletRequest request) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		String hql = " from TeePreArchiveType ";
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, null));// 设置总记录数
		hql += " order by sortNo asc";

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeePreArchiveType> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), null);// 查
        List<TeePreArchiveTypeModel> modelList=new ArrayList<TeePreArchiveTypeModel>();
		if(list!=null&&list.size()>0){
			TeePreArchiveTypeModel model=null;
			for (TeePreArchiveType type : list) {
				model=parseToModel(type);
				modelList.add(model);
			}
		}
		
		j.setRows(modelList);// 设置返回的行
		return j;
	}



	/**
	 * 将实体类转换成model
	 * @param type
	 * @return
	 */
	private TeePreArchiveTypeModel parseToModel(TeePreArchiveType type) {
		TeePreArchiveTypeModel model=new TeePreArchiveTypeModel();
		BeanUtils.copyProperties(type, model);
		if(type.getManager()!=null){
			model.setManagerId(type.getManager().getUuid());
			model.setManagerName(type.getManager().getUserName());
		}
		return model;
	}



	/**
	 * 根据主键获取详情
	 * @param request
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeePreArchiveType type=(TeePreArchiveType) simpleDaoSupport.get(TeePreArchiveType.class,sid);
		    if(type!=null){
		    	TeePreArchiveTypeModel model=parseToModel(type);
		    	json.setRtData(model);
		    	json.setRtState(true);
		    }else{
		    	json.setRtState(false);
				json.setRtMsg("数据获取失败！");
		    }
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		
		return json;
	}



	/**
	 * 根据主键进行删除
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson  json=new TeeJson();
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeePreArchiveType type=(TeePreArchiveType) simpleDaoSupport.get(TeePreArchiveType.class,sid);
			if(type!=null){
				//判断该分类下  是否有档案存在
				List<TeeFiles> fileList=simpleDaoSupport.executeQuery(" from TeeFiles where type.sid=? ", new Object[]{type.getSid()});
				if(fileList!=null&&fileList.size()>0){//存在
					json.setRtState(false);
					json.setRtMsg("已有档案关联该分类，暂且不能删除！");
				}else{//不存在
					simpleDaoSupport.deleteByObj(type);
					json.setRtState(true);
					json.setRtMsg("删除成功！");
				}
				
			}else{
				json.setRtState(false);
				json.setRtMsg("数据获取失败！");
			}	
		}else{
			json.setRtState(false);
			json.setRtMsg("数据获取失败！");
		}
		return json;
	}



	/**
	 * 获取所有分类  不分页
	 * @param request
	 * @return
	 */
	public TeeJson getAllPreArchiveType(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		String hql = " from TeePreArchiveType order by sortNo asc";
		List<TeePreArchiveType> list = simpleDaoSupport.executeQuery(hql,null);// 查
        List<TeePreArchiveTypeModel> modelList=new ArrayList<TeePreArchiveTypeModel>();
		if(list!=null&&list.size()>0){
			TeePreArchiveTypeModel model=null;
			for (TeePreArchiveType type : list) {
				model=parseToModel(type);
				modelList.add(model);
			}
		}
		json.setRtState(true);
		json.setRtData(modelList);
		return json;
	}

}
