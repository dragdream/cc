package com.beidasoft.zfjd.law.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.law.bean.TblLawDetail;
import com.beidasoft.zfjd.law.bean.TblLawInfo;
import com.beidasoft.zfjd.law.model.TblLawModel;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

@Repository
public class TblLawDao extends TeeBaseDao<TblLawInfo>{

	@Autowired
	private TeeDeptDao dao;
		
	 public List<TblLawInfo>  findUsers(){
		  return super.find("from TblLawInfo", null);	  
		  }
	 
	 /**
	  * 分页
	  * @param firstResult
	  * @param rows
	  * @param queryModel
	  * @return
	  */
	  public List<TblLawInfo> listByPage(int firstResult,int rows,TblLawModel queryModel){
		  String hql = " from TblLawInfo where 1=1 and isDelete = 0";
		  if(!TeeUtility.isNullorEmpty(queryModel.getName()))
		  {
			  hql +="and name like '%"+queryModel.getName()+"%'";  
		  }
		
		  if(!TeeUtility.isNullorEmpty(queryModel.getTimeliness())){
			  hql+=" and timeliness = "+queryModel.getTimeliness();
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())){
			  hql+=" and SUBMITLAW_LEVEL ="+queryModel.getSubmitlawLevel();
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getExamine())){
			  hql+=" and EXAMINE ="+queryModel.getExamine();
		  }
		  hql += " order by createDate desc ";
		return super.pageFind(hql, firstResult, rows, null);
		  
	  }
		  
	  public long getTotal(){
		  return super.count("select count(id) from TblLawInfo",null);
	  }
	  /**
	   * 导航
	   * @param queryModel
	   * @return
	   */
	  public long getTotal(TblLawModel queryModel){
		  String hql = "select count(id) from TblLawInfo where 1=1 and isDelete = 0";
		  
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getName()))
		  {
			  hql +="and name like '%"+queryModel.getName()+"%' ";  
		  }
		 
		  if(!TeeUtility.isNullorEmpty(queryModel.getTimeliness())){
			  hql+=" and timeliness = "+queryModel.getTimeliness();
		  }
		  
		  if(!TeeUtility.isNullorEmpty(queryModel.getSubmitlawLevel())){
			  hql+=" and SUBMITLAW_LEVEL ="+queryModel.getSubmitlawLevel();
		  }
		   
		  if(!TeeUtility.isNullorEmpty(queryModel.getExamine())){
			  hql+=" and EXAMINE ="+queryModel.getExamine();
		  }
		   return super.count(hql,null);
	  }

}
