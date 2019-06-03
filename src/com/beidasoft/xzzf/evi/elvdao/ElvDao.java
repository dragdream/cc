package com.beidasoft.xzzf.evi.elvdao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.evi.bean.EleEvidenceBase;
import com.beidasoft.xzzf.evi.model.ElvModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class ElvDao extends TeeBaseDao<EleEvidenceBase>{
	public List<EleEvidenceBase> listByPage(int firstResult,int rows){
		return super.pageFind("from EleEvidenceBase", firstResult, rows, null);
	}
	public List<EleEvidenceBase> listByPage(int firstResult,int rows,ElvModel searchModel){
		searchModel.setIs_valid(-1);
		searchModel.setFile_type(-1);
		String hql = "from EleEvidenceBase where 1=1 and isDelete = 0 ";
		if(!TeeUtility.isNullorEmpty(searchModel.getBaseId())){
			hql+="and baseId ='"+searchModel.getBaseId()+"'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getName())){
			hql+=" and name like '%"+searchModel.getName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getSrc_type())){
			hql+=" and src_type like '%"+searchModel.getSrc_type()+"%'";
		}
		if(searchModel.getFile_type()!=-1){
			hql+=" and file_type like '%"+searchModel.getFile_type()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getGet_time_str())){
			hql+=" and get_time_str like '%"+searchModel.getGet_time_str()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getGet_person())){
			hql+=" and get_person like '%"+searchModel.getGet_person()+"%'";
		}
		if(searchModel.getIs_valid()!=-1){
			hql+=" and is_valid like '%"+searchModel.getIs_valid()+"%'";
		}
		return super.pageFind(hql, firstResult, rows, null);
	}
	/**
	 * 记录总数
	 * @return
	 */
	public long gettotal(){
		return super.count("select count(baseId) from EleEvidenceBase", null);	
	}
	public long gettotal(ElvModel searchModel){
		searchModel.setIs_valid(-1);
		searchModel.setFile_type(-1);
		String hql = "select count(baseId) from EleEvidenceBase where 1=1 and isDelete = 0";
		if(!TeeUtility.isNullorEmpty(searchModel.getBaseId())){
			hql+="and baseId ='"+searchModel.getBaseId()+"'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getName())){
			hql+=" and name like '%"+searchModel.getName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getSrc_type())){
			hql+=" and src_type like '%"+searchModel.getSrc_type()+"%'";
		}if(searchModel.getFile_type()!=-1){
			hql+=" and file_type like '%"+searchModel.getFile_type()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getGet_time_str())){
			hql+=" and get_time_str like '%"+searchModel.getGet_time_str()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(searchModel.getGet_person())){
			hql+=" and get_person like '%"+searchModel.getGet_person()+"%'";
		}
		if(searchModel.getIs_valid()!=-1){
			hql+=" and is_valid like '%"+searchModel.getIs_valid()+"%'";
		}
		return super.count(hql,null);
	}
}
