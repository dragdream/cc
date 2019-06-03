package com.tianee.oa.core.base.hr.code.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.hr.code.bean.TeeHrCode;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;
@Repository("hrCodeDao")
public class TeeHrCodeDao extends TeeBaseDao<TeeHrCode> {

	/**
	 * 获取主编码
	 * @author syl
	 * @date 2014-3-1
	 * @return
	 */
	public List<TeeHrCode> getSysPara() {
	    String hql = " from TeeHrCode where parentId <=0 order by codeOrder , codeNo";
		List<TeeHrCode> list = (List<TeeHrCode>) executeQuery(hql,null);
		return list;
	}
	
	/**
	 * 根据主编码编号 获取子编码列表
	 * @author syl
	 * @date 2014-3-1
	 * @param paraValue
	 * @return
	 */
	public List<TeeHrCode> getSysParaByParent(TeeHrCode code) {
		Object[] values = {code.getParentId()};//'%'||:name||'%' /% escape '/' 
		String hql = " from TeeHrCode t where t.parentId = ? order by t.codeOrder ";
		List<TeeHrCode> list = (List<TeeHrCode>) executeQuery(hql,values);
		return list;
	}
	

	
	/**
     * 判断是否存在编号的编码   主编码 
     * @param status  true-修改 ；false-新增
     * @return
     */
 	public boolean isExist(boolean status , TeeHrCode sysPara) {
 		String hql = " from TeeHrCode t where t.codeNo = ?";
 		Object[] values = {sysPara.getCodeNo()};
 		List<TeeHrCode> list = new ArrayList<TeeHrCode>();
 		if(status){
 			hql = " from TeeHrCode t where t.codeNo = ? and t.sid != ? ";
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
 	public boolean isExistChild(boolean status , TeeHrCode sysPara) {
 		
 		String hql = " from TeeHrCode t where t.codeNo = ? and t.parentId = ?";
 		Object[] values = {sysPara.getCodeNo() , sysPara.getParentId()};
 		List<TeeHrCode> list = new ArrayList<TeeHrCode>();
 		if(status){
 			hql = " from TeeHrCode t where t.codeNo = ?  and t.parentId = ?  and t.sid != ? ";
 			Object[] values2 =  {sysPara.getCodeNo() , sysPara.getParentId() , sysPara.getSid() };
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
	 * 查询 byId
	 * @param TeeSysPara
	 */
	public TeeHrCode selectById(int id) {
		TeeHrCode para = get(id);
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
		String hql = "delete from TeeHrCode where parentId = ? or sid = ?";
		return deleteOrUpdateByQuery(hql, values);
	}
	
	/**
	 * 查询 条件查询 
	 * @param TeeSysPara
	 */
	public List<TeeHrCode> selectPara(String hql ,Object[] values) {
	    List<TeeHrCode> list = (List<TeeHrCode>) executeQuery(hql, values);
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
	public void updateSysCode(TeeHrCode code){
		update(code);
	}

	
	
	/**
	 * 查询  byIds 
	 * @author syl
	 * @date 2014-3-1
	 * @param paraValue
	 * @return
	 */
	public List<TeeHrCode> getSysParaByIds(String ids) {
		if(TeeUtility.isNullorEmpty(ids)){
			return null;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		Object[] values = {};//'%'||:name||'%' /% escape '/' 
		String hql = " from TeeHrCode t where t.sid in (" + ids + ")  order by t.codeOrder ";
		List<TeeHrCode> list = (List<TeeHrCode>) executeQuery(hql,values);
		return list;
	}
}
