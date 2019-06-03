package com.tianee.oa.core.base.fileNetdisk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileRolePriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileRolePrivDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileRolePrivModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFileRolePrivService extends TeeBaseService {
    @Autowired
    @Qualifier("fileRolePrivDao")
    private TeeFileRolePrivDao fileRolePrivDao;
    @Autowired
    @Qualifier("fileNetdiskDao")
    private TeeFileNetdiskDao fileNetdiskDao;
    @Autowired
    @Qualifier("userRolelDao")
    private TeeUserRoleDao userRolelDao;

    /**
     * 新建或编辑角色权限
     * @date 2014-2-13
     * @author 
     * @param person
     * @param list
     * @param fileId
     * @return
     */
    public TeeJson addOrUpdateFileRolePriv(TeePerson person, List<Map<String, String>> list, String fileId,String extend) {
        TeeJson json = new TeeJson();
        List<TeeFileNetdisk> diskList = new ArrayList();//待操作的网盘
    	TeeFileNetdisk curFileNetdisk = fileNetdiskDao.get(TeeStringUtil.getInteger(fileId, 0));
    	if(extend!=null){//获取当前文件夹及所有子类
    		diskList = fileNetdiskDao.getChildFoldersByPath(TeeStringUtil.getInteger(fileId, 0));
    		diskList.add(curFileNetdisk);
    	}else{//获取当前文件夹
    		diskList.add(curFileNetdisk);
    	}
    	
    	String roleId = null;
    	String rolePriv = null;
    	for(TeeFileNetdisk fileNetdisk:diskList){
    		 if (fileNetdisk != null) {
    			 //删除之前的权限
    			 fileRolePrivDao.deleteFileRolePrivByFolder(fileNetdisk.getSid());
    			 //添加新权限
                 for (Map<String, String> map : list) {
                	 roleId = map.get("roleId");
                	 rolePriv = map.get("rolePriv");
                     
                	 TeeUserRole userRole = userRolelDao.get(TeeStringUtil.getInteger(roleId, 0));
                     TeeFileRolePriv fileRolePriv = new TeeFileRolePriv();
                     fileRolePriv.setFileNetdisk(fileNetdisk);
                     fileRolePriv.setPrivValue(TeeStringUtil.getInteger(rolePriv, 0));
                     fileRolePriv.setUserRole(userRole);
                     fileRolePrivDao.save(fileRolePriv);
                 }
             }
    	}
    	
        json.setRtState(true);
        json.setRtMsg("操作成功!");
        return json;
    }

    /**
     * 获取网盘权限信息
     * 
     * @date 2014-1-15
     * @author
     * @param fileId
     * @return
     */
    public TeeJson getFileUserPrivService(int fileId) {
        TeeJson json = new TeeJson();
        String hql = " from TeeFileRolePriv where fileNetdisk.sid = " + fileId;
        List<TeeFileRolePriv> fileRolePrivs = (List<TeeFileRolePriv>) fileRolePrivDao.executeQuery(hql, null);

        List<TeeFileRolePrivModel> models = new ArrayList<TeeFileRolePrivModel>();
        if (fileRolePrivs != null && fileRolePrivs.size() > 0) {
            for (TeeFileRolePriv fileRolePriv : fileRolePrivs) {
                TeeFileRolePrivModel model = new TeeFileRolePrivModel();
                model.setSid(fileRolePriv.getSid());
                model.setPrivValue(this.getPrivValue(fileRolePriv.getPrivValue()));
                models.add(model);
                TeeUserRole userRole = fileRolePriv.getUserRole();
                if (userRole != null) {
                    model.setRoleId(userRole.getUuid());
                    model.setRoleName(userRole.getRoleName());
                }
            }

        }
        String data = TeeJsonUtil.toJson(models);
        json.setRtState(true);
        json.setRtMsg("操作成功!");
        json.setRtData(data);
        return json;
    }

    /**
     * 获取权限原始值
     * 
     * @date 2014-1-15
     * @author
     * @param dbValue
     * @return
     */
    public String getPrivValue(int dbValue) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i <= 64; i *= 2) {
        	if(i==16){
        		continue;
        	}
            if (!TeeUtility.isNullorEmpty(buffer.toString().trim())) {
                buffer.append(",");
            }
            buffer.append(i & dbValue);
        }
        return buffer.toString();
    }
    
    /**
     * 删除网盘角色权限信息
     * @date 2014-2-15
     * @author
     * @param sids
     * @return
     */
    public TeeJson deleteFileRolePriv(int sid) {
        fileRolePrivDao.deleteFileRolePrivDao(sid);
        TeeJson json = new TeeJson();
        json.setRtState(true);
        json.setRtMsg("角色权限删除成功!");
        return json;
    }

}
