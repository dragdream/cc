package com.tianee.oa.core.base.onduty.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.onduty.bean.TeePbDutyType;
import com.tianee.oa.core.base.onduty.bean.TeePbTypeChild;
import com.tianee.oa.core.base.onduty.dao.TeePbDutyTypeDao;
import com.tianee.oa.core.base.onduty.dao.TeePbTypeChildDao;
import com.tianee.oa.core.base.onduty.model.TeePbDutyTypeModel;
import com.tianee.oa.core.base.onduty.model.TeePbTypeChildModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeePbDutyTypeService extends TeeBaseService{

	@Autowired
	private TeePbDutyTypeDao teePbDutyTypeDao;
	
	@Autowired
	private TeePbTypeChildDao teePbTypeChildDao;

	/**
	 * 获取值班类型列表
	 * */
	public TeeEasyuiDataGridJson findDutyTypeList(HttpServletRequest request,
			TeeDataGridModel m) {
		TeeEasyuiDataGridJson easyJson=new TeeEasyuiDataGridJson();
		List<TeePbDutyTypeModel> listModel=new ArrayList<TeePbDutyTypeModel>();
		String hql="from TeePbDutyType";
		List<TeePbDutyType> pageFind = teePbDutyTypeDao.pageFind(hql, m.getFirstResult(), m.getRows(), null);
		Long count2 = teePbDutyTypeDao.count("select count(*) "+hql, null);
		if(pageFind!=null && pageFind.size()>0){
			for(TeePbDutyType pt:pageFind){
				TeePbDutyTypeModel model=new TeePbDutyTypeModel();
				model.setSid(pt.getSid());
				model.setTypeName(pt.getTypeName());
				model.setSease(pt.getSease());
				Long count = teePbTypeChildDao.count("select count(*) from TeePbTypeChild where typeId=?", new Object[]{pt.getSid()});
				int integer = TeeStringUtil.getInteger(count, 0);
				model.setNumber(integer);
				listModel.add(model);
			}
		}
		easyJson.setRows(listModel);
		easyJson.setTotal(count2);
		return easyJson;
	}

	/**
	 * 添加或修改值班类型
	 * */
	public TeeJson addOrUpdateDutyType(HttpServletRequest request,TeePbDutyTypeModel model) {
		TeeJson json=new TeeJson();
		int typeId=0;
	    //类型操作
		if(model.getSid()>0){//修改
			 TeePbDutyType type = teePbDutyTypeDao.get(model.getSid());
			 type.setSease(model.getSease());
			 type.setTypeName(model.getTypeName());
			 teePbDutyTypeDao.update(type);
			 typeId=model.getSid();
		}else{//添加
			TeePbDutyType pt=new TeePbDutyType();
			pt.setSease(model.getSease());
			pt.setTypeName(model.getTypeName());
			Serializable save = teePbDutyTypeDao.save(pt);
			typeId=TeeStringUtil.getInteger(save, 0);
			
		}
		//字段操作
		List<TeePbTypeChild> find = teePbTypeChildDao.find("from TeePbTypeChild where typeId=?", new Object[]{typeId});
		if(find!=null && find.size()>0){
			for(TeePbTypeChild pb:find){
				teePbTypeChildDao.delete(pb.getSid());
			}
		}
		String nameStr = request.getParameter("nameStr");
		if(nameStr!=null && !"".equals(nameStr)){
			String[] split = nameStr.split(",");
			for(int i=0;i<split.length;i++){
				String [] str = split[i].split(";");
				TeePbTypeChild pc=new TeePbTypeChild();
				pc.setName(str[0]);
				pc.setSease(str[1]);
				pc.setTypeId(typeId);
				teePbTypeChildDao.save(pc);
			}
			
		}
		json.setRtState(true);
		return json;
	}

	/**
	 * 删除值班类型
	 * */
	public TeeJson deleteDutyType(int sid) {
		TeeJson json=new TeeJson();
		if(sid>0){
			teePbDutyTypeDao.delete(sid);
			teePbTypeChildDao.deleteOrUpdateByQuery("delete from TeePbTypeChild where typeId=?", new Object[]{sid});
			json.setRtState(true);
			json.setRtMsg("删除成功");
		}else{
			json.setRtState(false);
			json.setRtMsg("删除失败");
		}
		return json;
	}

	/**
	 * 获取类型的详细信息
	 * */
	public TeeJson findDutyTypeById(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
	    TeePbDutyType dutyType = teePbDutyTypeDao.get(sid);
	    TeePbDutyTypeModel model=new TeePbDutyTypeModel();
	    if(dutyType!=null){
	    	model.setSid(dutyType.getSid());
	    	model.setTypeName(dutyType.getTypeName());
	    	model.setSease(dutyType.getSease());
	    	List<TeePbTypeChild> find = teePbTypeChildDao.find("from TeePbTypeChild where typeId=?", new Object[]{sid});
	    	List<TeePbTypeChildModel> childModel = model.getChildModel();
	    	if(find!=null && find.size()>0){
	    		for(TeePbTypeChild pc:find){
	    			TeePbTypeChildModel m=new TeePbTypeChildModel();
	    			m.setName(pc.getName());
	    			m.setSease(pc.getSease());
	    			m.setSid(pc.getSid());
	    			m.setTypeId(sid);
	    			childModel.add(m);
	    		}
	    	}
	    	model.setChildModel(childModel);
	    	json.setRtData(model);
	    	json.setRtState(true);
	    }else{
	    	json.setRtData(model);
	    	json.setRtState(false);
	    }
		return json;
	}

	/**
	 * 获取所有值班信息
	 * */
	public TeeJson findDutyTypeAll() {
		TeeJson json=new TeeJson();
		List<TeePbDutyTypeModel> modelList=new ArrayList<TeePbDutyTypeModel>();
		List<TeePbDutyType> find = teePbDutyTypeDao.find("from TeePbDutyType", null);
		if(find!=null && find.size()>0){
			for(TeePbDutyType pb:find){
				TeePbDutyTypeModel m=new TeePbDutyTypeModel();
				m.setSid(pb.getSid());
				m.setTypeName(pb.getTypeName());
				m.setSease(pb.getSease());
				modelList.add(m);
			}
		}
		json.setRtData(modelList);
		return json;
	}
	
	
}
