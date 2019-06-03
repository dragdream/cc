package com.tianee.oa.core.base.fileNetdisk.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileDeptPriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;

@Repository("fileDeptPrivDao")
public class TeeFileDeptPrivDao extends TeeBaseDao<TeeFileDeptPriv> {
    
    /**
     * 获取部门权限
     * @date 2014-2-16
     * @author 
     * @param person
     * @param disk
     * @return
     */
    public List<TeeFileDeptPriv> getDeptPrivDao(TeePerson  person ,TeeFileNetdisk disk ){
    	//获取当前登陆人  辅助部门的ids
    	String otherDeptIds="";
		List<TeeDepartment> otherDeptList=person.getDeptIdOther();
		if(otherDeptList!=null&&otherDeptList.size()>0){
			for (TeeDepartment teeDepartment : otherDeptList) {
				otherDeptIds+=teeDepartment.getUuid()+",";
			}
			if(otherDeptIds.endsWith(",")){
				otherDeptIds=otherDeptIds.substring(0,otherDeptIds.length()-1);
			}
		}else{
			otherDeptIds="0";
		}
        Object[] values = {person.getDept().getUuid() ,disk.getSid()};
         String hql = " from TeeFileDeptPriv where (dept.uuid = ? or "+TeeDbUtility.IN("dept.uuid", otherDeptIds)+") and fileNetdisk.sid = ? ";
        return executeQuery(hql, values);
    }
    
    /**
     * 删除部门权限
     * @date 2014-2-15
     * @author 
     * @param sidStr
     */
    public void deleteFileDeptPrivDao(int sid) {
        String hql = " delete from TeeFileDeptPriv where sid=" + sid;
        deleteOrUpdateByQuery(hql, null);
    }
    
    
    /**
     * 根据文件Id获取部门权限列表
     * @date 2014-2-16
     * @author 
     * @param person
     * @param disk
     * @return
     */
    public List<TeeFileDeptPriv> getDeptPrivListDao(TeePerson person,int fileSid) {
        Object[] values = {fileSid};
         String hql = " from TeeFileDeptPriv where fileNetdisk.sid = ? ";
        return executeQuery(hql, values);
    }
    
    public void deleteFileDeptPrivByFolder(int folderId){
    	String hql = " delete from TeeFileDeptPriv where fileNetdisk.sid=" + folderId;
		deleteOrUpdateByQuery(hql, null);
    }
    

}
