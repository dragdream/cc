package com.tianee.oa.subsys.crm.setting.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.subsys.crm.setting.bean.TeeCrmCode;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("crmCodeDao")
public class TeeCrmCodeDao extends TeeBaseDao<TeeCrmCode> {

	/**
	 * 获取主编码
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeCrmCode> getSysPara() {
	    String hql = " from TeeCrmCode where parentId <=0 order by codeNo";
		List<TeeCrmCode> list = (List<TeeCrmCode>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 根据主编码编号 获取子编码列表
	 * @author syl
	 * @date 2014-3-1
	 * @param paraValue
	 * @return
	 */
	public List<TeeCrmCode> getSysParaByParent(TeeCrmCode code) {
		Object[] values = {code.getParentId()};//'%'||:name||'%' /% escape '/' 
		String hql = " from TeeCrmCode t where t.parentId = ? order by t.codeNo ";
		List<TeeCrmCode> list = (List<TeeCrmCode>) executeQuery(hql,values);
		return list;
	}
	

	
	/**
     * 判断是否存在编号的编码   主编码 
     * @param status  true-修改 ；false-新增
     * @return
     */
 	public boolean isExist(boolean status , TeeCrmCode sysPara) {
 		String hql = " from TeeCrmCode t where t.codeNo = ?";
 		Object[] values = {sysPara.getCodeNo()};
 		List<TeeCrmCode> list = new ArrayList<TeeCrmCode>();
 		if(status){
 			hql = " from TeeCrmCode t where t.codeNo = ? and t.sid != ? ";
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
 	public boolean isExistChild(boolean status , TeeCrmCode sysPara) {
 		
 		String hql = "select count(*) from TeeCrmCode t where t.codeNo = ? and t.parentId = ?";
 		Object[] values = {sysPara.getCodeNo() , sysPara.getParentId()};
 		long count = 0;
 		if(status){
 			hql = "select count(*) from TeeCrmCode t where t.codeNo = ?  and t.parentId = ?  and t.sid != ? ";
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
	public TeeCrmCode selectById(int id) {
		TeeCrmCode para = get(id);
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
		String hql = "delete from TeeCrmCode where parentId = ? or sid = ?";
		return deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 查询 条件查询 
	 * @param TeeSysPara
	 */
	public List<TeeCrmCode> selectPara(String hql ,Object[] values) {
	    List<TeeCrmCode> list = (List<TeeCrmCode>) executeQuery(hql, values);
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
	public void updateSysCode(TeeCrmCode code){
		update(code);
	}

	
	
	/**
	 * 查询  byIds 
	 * @author syl
	 * @date 2014-3-1
	 * @param paraValue
	 * @return
	 */
	public List<TeeCrmCode> getSysParaByIds(String ids) {
		if(TeeUtility.isNullorEmpty(ids)){
			return null;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		Object[] values = {};//'%'||:name||'%' /% escape '/' 
		String hql = " from TeeCrmCode t where t.sid in (" + ids + ")  order by t.codeNo ";
		List<TeeCrmCode> list = (List<TeeCrmCode>) executeQuery(hql,values);
		return list;
	}
}
