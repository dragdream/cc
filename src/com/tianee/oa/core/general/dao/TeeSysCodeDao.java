package com.tianee.oa.core.general.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("sysCodeDao")
public class TeeSysCodeDao extends TeeBaseDao<TeeSysCode> {

	/**
	 * 获取主编码
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeSysCode> getSysPara() {
	    String hql = " from TeeSysCode where parentId <=0 order by codeNo";
		List<TeeSysCode> list = (List<TeeSysCode>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 根据主编码编号 获取子编码列表
	 * @author syl
	 * @date 2014-3-1
	 * @param paraValue
	 * @return
	 */
	public List<TeeSysCode> getSysParaByParent(TeeSysCode code) {
		Object[] values = {code.getParentId()};//'%'||:name||'%' /% escape '/' 
		String hql = " from TeeSysCode t where t.parentId = ? order by t.codeNo ";
		List<TeeSysCode> list = (List<TeeSysCode>) executeQuery(hql,values);
		return list;
	}
	

	
	/**
     * 判断是否存在编号的编码   主编码 
     * @param status  true-修改 ；false-新增
     * @return
     */
 	public boolean isExist(boolean status , TeeSysCode sysPara) {
 		String hql = " from TeeSysCode t where t.codeNo = ?";
 		Object[] values = {sysPara.getCodeNo()};
 		List<TeeSysCode> list = new ArrayList<TeeSysCode>();
 		if(status){
 			hql = " from TeeSysCode t where t.codeNo = ? and t.sid != ? ";
 			Object[] values2 =  {sysPara.getCodeNo(), sysPara.getSid() };
 		   list = executeQuery(hql, values2);
 		}else{
 		   list = executeQuery(hql, values);
 		}
 		if(list.size() > 0){//存在
 			return true;
 		}
 		return false;
 	}
 	
 	/**
     * 判断是否存在编号的编码   子编码 
     * @param status  true-修改 ；false-新增
     * @return
     */
 	public boolean isExistChild(boolean status , TeeSysCode sysPara) {
 		
 		String hql = "select count(*) from TeeSysCode t where t.codeNo = ? and t.parentId = ?";
 		Object[] values = {sysPara.getCodeNo() , sysPara.getParentId()};
 		long count = 0;
 		if(status){
 			hql = "select count(*) from TeeSysCode t where t.codeNo = ?  and t.parentId = ?  and t.sid != ? ";
 			Object[] values2 =  {sysPara.getCodeNo() , sysPara.getParentId() , sysPara.getSid() };
 			count = count(hql, values2);
 		}else{
 			count = count(hql, values);
 		}
 		if(count > 0){//存在
 			return true;
 		}
 		return false;
 	}
 	
	/**
	 * 查询 byId
	 * @param TeeSysPara
	 */
	public TeeSysCode selectById(int id) {
		TeeSysCode para = get(id);
		return para;
	}
	
	
	/**
	 * 删除 by Id
	 * @param TeeSysPara
	 */
	public void delById(int id) {
		delete(id);
	}
	
	
	/**
	 * 删除 by parentId  以及本身
	 * @param TeeSysPara
	 */
	public long delByParentId(int parentId) {
		Object[] values = {parentId , parentId};
		String hql = "delete from TeeSysCode where parentId = ? or sid = ?";
		return deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 查询 条件查询 
	 * @param TeeSysPara
	 */
	public List<TeeSysCode> selectPara(String hql ,Object[] values) {
	    List<TeeSysCode> list = (List<TeeSysCode>) executeQuery(hql, values);
		return list;
	}
	
	/**
	 * 删除
	 * @param TeeSysPara
	 */
	public void delSysPara(String hql ,Object[] values) {
		deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 更新对象
	 * @author syl
	 * @date 2014-3-1
	 * @param code
	 */
	public void updateSysCode(TeeSysCode code){
		update(code);
	}

	
	
	/**
	 * 查询  byIds 
	 * @author syl
	 * @date 2014-3-1
	 * @param paraValue
	 * @return
	 */
	public List<TeeSysCode> getSysParaByIds(String ids) {
		if(TeeUtility.isNullorEmpty(ids)){
			return null;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		Object[] values = {};//'%'||:name||'%' /% escape '/' 
		String hql = " from TeeSysCode t where t.sid in (" + ids + ")  order by t.codeNo ";
		List<TeeSysCode> list = (List<TeeSysCode>) executeQuery(hql,values);
		return list;
	}
	
	/**
	    * 
	   * @Function: TeeSysCodeDao.java
	   * @Description: 通过主编码ID和codeNo查询业务子编码
	   *
	   * @param:描述1描述
	   * @return：返回结果描述
	   * @throws：异常描述
	   *
	   * @author: chenq
	   * @date: 2018年12月25日 上午10:26:13 
	   *
	    */
	    public List<TeeSysCode> getSysParaByParentCode(String parentCodeNo, String codeNo) {
	        String hql = " from TeeSysCode t where t.parentId=(select sid from TeeSysCode where codeNo = '" + parentCodeNo + "')"
	        		+ " and t.codeNo like '" + codeNo + "%' order by t.codeNo ";
	        List<TeeSysCode> list = (List<TeeSysCode>) executeQuery(hql, null);
	        return list;
	    }
}
