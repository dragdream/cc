package com.beidasoft.zfjd.subjectPerson.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.subjectPerson.bean.TblSubjectPerson;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 主体人员关联表DAO类
 */
@Repository
public class TblSubjectPersonDao extends TeeBaseDao<TblSubjectPerson> {
	/**
	 * 根据人员id查询
	 * @param personId
	 * @return
	 */
	public TblSubjectPerson getByPersonId (String personId){
		String hql = " from TblSubjectPerson where personId = '"+personId+"' ";
		List<TblSubjectPerson> list = super.find(hql, null);
		if (!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	 public List<TblSubjectPerson> listByPage(int firstResult,int rows,TblSubjectPersonModel queryModel){
		  String hql = " from TblSubjectPerson where 1=1 ";
		  if (!TeeUtility.isNullorEmpty(queryModel.getSubjectId())) {
			  hql += " and subjectId = '"+queryModel.getSubjectId()+"'";
		  }
		return super.pageFind(hql, firstResult, rows, null);
		  
	  }		  
public long getTotal(){
		  return super.count("select count(id) from TblSubjectPerson where 1=1 and isDelete = 0 ",null);
	  }
	  
public long getTotal(TblSubjectPersonModel queryModel){
		  String hql = "select count(id) from TblSubjectPerson where 1=1 ";
		  if (!TeeUtility.isNullorEmpty(queryModel.getSubjectId())) {
			  hql += " and subjectId = '"+queryModel.getSubjectId()+"'";
		  }
		   return super.count(hql,null);
	  }
    /**
     * 
    * @Function: listSubjectPerson()
    * @Description: 该函数的功能描述
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月17日 下午7:02:19 
    *
     */
    public List<TblSubjectPerson> listSubjectPerson(TblSubjectPersonModel queryModel) {
        StringBuffer hql = new StringBuffer();
        hql.append(" from TblSubjectPerson where 1=1 ");
        if (queryModel.getSubjectId() != null && !"".equals(queryModel.getSubjectId())) {
            hql.append(" and subjectId = '" + queryModel.getSubjectId() + "'");
        }
        return super.find(hql.toString(), null);
    }

}
