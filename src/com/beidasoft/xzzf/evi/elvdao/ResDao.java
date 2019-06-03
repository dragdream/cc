package com.beidasoft.xzzf.evi.elvdao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.evi.bean.ResEvidence;
import com.beidasoft.xzzf.evi.model.ResModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class ResDao extends TeeBaseDao<ResEvidence>{
	/**
	 * 根据分页查找用户信息
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<ResEvidence> listByPage(int firstResult,int rows,ResModel queryModel){
		String hql = "from ResEvidence where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getBaseId())){
			hql+="and baseId ='"+queryModel.getBaseId()+"'";
		}
		return super.pageFind(hql, firstResult, rows, null);
	}
	/**
	 * 返回总记录数
	 * @return
	 */
	public long getTotal(){
		return super.count( "select count(baseId) from ResEvidence", null);
	}
	/**
	 * 重载
	 * @return
	 */
	public long getTotal(ResModel queryModel){
		String hql ="select count(baseId) from ResEvidence where 1=1";
		if(!TeeUtility.isNullorEmpty(queryModel.getBaseId())){
			hql+="and baseId ='"+queryModel.getBaseId()+"'";
		}if(!TeeUtility.isNullorEmpty(queryModel.getName())){
			hql+=" and name like '%"+queryModel.getName()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getSrc_type())){
			hql+=" and src_type like '%"+queryModel.getSrc_type()+"%'";
		}if(!TeeUtility.isNullorEmpty(queryModel.getFile_type())){
			hql+=" and file_type like '%"+queryModel.getFile_type()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getGet_time_str())){
			hql+=" and get_time_str like '%"+queryModel.getGet_time_str()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getGet_person())){
			hql+=" and get_person like '%"+queryModel.getGet_person()+"%'";
		}
		if(!TeeUtility.isNullorEmpty(queryModel.getIs_valid())){
			hql+=" and is_valid like '%"+queryModel.getIs_valid()+"%'";
		}
		
		return super.count(hql, null);
	}
}
