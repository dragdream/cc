package com.tianee.mem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.mem.bean.Membean;
import com.tianee.mem.model.Memmodel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository
public class Memdao extends TeeBaseDao<Membean>{
	public long gettotal(){
		return super.count("select count(id) from Membean", null);	
	}
	public long gettotal(Memmodel searchModel){
		String hql = "select count(id) from Membean where 1=1";
		if(!TeeUtility.isNullorEmpty(searchModel.getEvidence_name())){
			hql+="and evidence_name like '%"+searchModel.getEvidence_name()+"%'";
		}
		return super.count(hql,null);
	}

	
	public List<Membean> listByPage(int firstResult,int rows,Memmodel searchModel){
		String hql = "from Membean where 1=1";
		if(!TeeUtility.isNullorEmpty(searchModel.getEvidence_name())){
			hql+="and evidence_name like '%"+searchModel.getEvidence_name()+"%'";
		}
		/*if(searchModel.getFile_type()!=-1){
			hql+="and file_type ="+searchModel.getFile_type();	
		}*/
		return super.pageFind(hql, firstResult, rows, null);
	}
}
