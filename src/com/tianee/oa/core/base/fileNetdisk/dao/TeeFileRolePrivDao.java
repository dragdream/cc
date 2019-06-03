package com.tianee.oa.core.base.fileNetdisk.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileRolePriv;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;

@Repository("fileRolePrivDao")
public class TeeFileRolePrivDao extends TeeBaseDao<TeeFileRolePriv> {

    /**
     * 获取角色权限
     * 
     * @date 2014-2-16
     * @author
     * @param person
     * @param disk
     * @return
     */
    public List<TeeFileRolePriv> getRolePrivDao(TeePerson person, TeeFileNetdisk disk) {
    	//获取当前登陆人  辅助角色的ids
		String otherRoleIds="";
		List<TeeUserRole> otherRoleList=person.getUserRoleOther();
		if(otherRoleList!=null&&otherRoleList.size()>0){
			for (TeeUserRole role: otherRoleList) {
				otherRoleIds+=role.getUuid()+",";
			}
			if(otherRoleIds.endsWith(",")){
				otherRoleIds=otherRoleIds.substring(0,otherRoleIds.length()-1);
			}
		}else{
			otherRoleIds="0";
		}
    	Object[] values = { person.getUserRole().getUuid(), disk.getSid() };
        String hql = " from TeeFileRolePriv where (userRole.uuid = ? or "+TeeDbUtility.IN("userRole.uuid", otherRoleIds)+") and fileNetdisk.sid = ? ";
        return executeQuery(hql, values);

    }
    
    /**
     * 删除角色权限
     * @date 2014-2-15
     * @author 
     * @param sidStr
     */
    public void deleteFileRolePrivDao(int sid) {
        String hql = " delete from TeeFileRolePriv where sid=" + sid;
        deleteOrUpdateByQuery(hql, null);
    }
    
    /**
     * 根据文件Id获取角色权限列表
     * @date 2014-2-16
     * @author 
     * @param person
     * @param disk
     * @return
     */
    public List<TeeFileRolePriv> getRolePrivListDao(TeePerson person,int fileSid) {
        Object[] values = {fileSid};
         String hql = " from TeeFileRolePriv where fileNetdisk.sid = ? ";
        return executeQuery(hql, values);
    }
    
    public void deleteFileRolePrivByFolder(int folderId){
    	String hql = " delete from TeeFileRolePriv where fileNetdisk.sid=" + folderId;
		deleteOrUpdateByQuery(hql, null);
    }

}
