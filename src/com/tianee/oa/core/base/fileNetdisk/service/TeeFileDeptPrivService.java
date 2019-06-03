package com.tianee.oa.core.base.fileNetdisk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileDeptPriv;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileDeptPrivDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileDeptPrivModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFileDeptPrivService extends TeeBaseService {
    @Autowired
    @Qualifier("fileDeptPrivDao")
    private TeeFileDeptPrivDao fileDeptPrivDao;
    @Autowired
    @Qualifier("fileNetdiskDao")
    private TeeFileNetdiskDao fileNetdiskDao;
    @Autowired
    @Qualifier("deptDao")
    private TeeDeptDao deptDao;

    /**
     * 新建或编辑部门权限
     * @date 2014-2-13
     * @author 
     * @param person
     * @param list
     * @param fileId
     * @return
     */
    public TeeJson addOrUpdateFileDeptPriv(TeePerson person, List<Map<String, String>> list, String fileId,String extend) {
        TeeJson json = new TeeJson();
        
        List<TeeFileNetdisk> diskList = new ArrayList();//待操作的网盘
    	TeeFileNetdisk curFileNetdisk = fileNetdiskDao.get(TeeStringUtil.getInteger(fileId, 0));
    	if(extend!=null){//获取当前文件夹及所有子类
    		diskList = fileNetdiskDao.getChildFoldersByPath(TeeStringUtil.getInteger(fileId, 0));
    		diskList.add(curFileNetdisk);
    	}else{//获取当前文件夹
    		diskList.add(curFileNetdisk);
    	}
    	
    	String deptId = null;
    	String deptPriv = null;
    	for(TeeFileNetdisk fileNetdisk:diskList){
    		 if (fileNetdisk != null) {
    			 //删除之前的权限
    			 fileDeptPrivDao.deleteFileDeptPrivByFolder(fileNetdisk.getSid());
    			 //添加新权限
                 for (Map<String, String> map : list) {
                	 deptId = map.get("deptId");
                	 deptPriv = map.get("deptPriv");
                     
                     TeeDepartment dept = deptDao.get(TeeStringUtil.getInteger(deptId, 0));
                     TeeFileDeptPriv fileDeptPriv = new TeeFileDeptPriv();
                     fileDeptPriv.setFileNetdisk(fileNetdisk);
                     fileDeptPriv.setPrivValue(TeeStringUtil.getInteger(deptPriv, 0));
                     fileDeptPriv.setDept(dept);
                     fileDeptPrivDao.save(fileDeptPriv);
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
        String hql = " from TeeFileDeptPriv where fileNetdisk.sid = " + fileId;
        List<TeeFileDeptPriv> fileDeptPrivs = (List<TeeFileDeptPriv>) fileDeptPrivDao.executeQuery(hql, null);

        List<TeeFileDeptPrivModel> models = new ArrayList<TeeFileDeptPrivModel>();
        if (fileDeptPrivs != null && fileDeptPrivs.size() > 0) {
            for (TeeFileDeptPriv fileDeptPriv : fileDeptPrivs) {
                TeeFileDeptPrivModel model = new TeeFileDeptPrivModel();
                model.setSid(fileDeptPriv.getSid());
                model.setPrivValue(this.getPrivValue(fileDeptPriv.getPrivValue()));
                models.add(model);
                TeeDepartment department = fileDeptPriv.getDept();
                if (department != null) {
                    model.setDeptId(department.getUuid());
                    model.setDeptName(department.getDeptName());
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
        	if(i ==16){
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
    public TeeJson deleteFileDeptPriv(int sid) {
        fileDeptPrivDao.deleteFileDeptPrivDao(sid);
        TeeJson json = new TeeJson();
        json.setRtState(true);
        json.setRtMsg("角色权限删除成功!");
        return json;
    }


}
