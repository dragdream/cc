package com.tianee.oa.subsys.informationReport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.subsys.informationReport.bean.TeeTaskTemplateType;
import com.tianee.oa.subsys.informationReport.dao.TeeTaskTemplateTypeDao;
import com.tianee.oa.subsys.informationReport.model.TeeTaskTemplateTypeModel;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeTaskTemplateTypeService extends TeeBaseService{
	@Autowired
	private TeeTaskTemplateTypeDao typeDao;
	
	//添加
	public void save(TeeTaskTemplateType type){
		
		typeDao.save(type);
	}
	//更改
	public void update(TeeTaskTemplateType type){
		
		typeDao.update(type);
	}
	
	//删除
	public void delete(int sid){
		
		typeDao.delete(sid);
	}
	
	//通过sid查
		public TeeTaskTemplateType get(int sid){
			
			return typeDao.get(sid);
		}
		
	//查询全部
	public List<TeeTaskTemplateType> taskTemplateTypeList(){
				
		return typeDao.taskTemplateTypeList();
	}
	//分页检索查询
	public List<TeeTaskTemplateType> list(int firstResult,int rows,TeeTaskTemplateTypeModel querymodel){
		
		return typeDao.list(firstResult, rows, querymodel);
	}
	
	
	public long  getTotal(TeeTaskTemplateTypeModel querymodel){
		
		return typeDao.getTotal(querymodel);
	
	}
}
