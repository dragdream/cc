package com.tianee.oa.core.base.fileNetdisk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileNetdiskDao;
import com.tianee.oa.core.base.fileNetdisk.dao.TeeFileUserPrivDao;
import com.tianee.oa.core.base.fileNetdisk.model.TeeFileUserPrivModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeFileUserPrivService extends TeeBaseService {
    @Autowired
    @Qualifier("fileUserPrivDao")
    private TeeFileUserPrivDao fileUserPrivDao;
    @Autowired
    @Qualifier("fileNetdiskDao")
    private TeeFileNetdiskDao fileNetdiskDao;
    @Autowired
    @Qualifier("personDao")
    private TeePersonDao personDao;

    public TeeJson addFileUserPriv(TeePerson person, List<Map<String, String>> list, String fileId,String extend) {
        
        /* 1.根据页面提交的文件夹id查询权限值返回权限id列表
         * 2.根据页面提交的权限id与第1步的权限id比较，
         *   (1).如页面权限id无值则新建，有值则须
         *   (1.1). 如列表存在id则编辑
         * 
         * */
        
        
        TeeJson json = new TeeJson();
        List<TeeFileNetdisk> diskList = new ArrayList();//待操作的网盘
    	TeeFileNetdisk curFileNetdisk = fileNetdiskDao.get(TeeStringUtil.getInteger(fileId, 0));
    	if(extend!=null){//获取当前文件夹及所有子类
    		diskList = fileNetdiskDao.getChildFoldersByPath(TeeStringUtil.getInteger(fileId, 0));
    		diskList.add(curFileNetdisk);
    	}else{//获取当前文件夹
    		diskList.add(curFileNetdisk);
    	}
    	
    	String userId = null;
    	String userPriv = null;
    	for(TeeFileNetdisk fileNetdisk:diskList){
    		 if (fileNetdisk != null) {
    			 //删除之前的权限
    			 fileUserPrivDao.deleteFileUserPrivByFolder(fileNetdisk.getSid());
    			 //添加新权限
                 for (Map<String, String> map : list) {
                     userId = map.get("userId");
                     userPriv = map.get("userPriv");
                     
                     TeePerson user = personDao.get(TeeStringUtil.getInteger(userId, 0));
                     TeeFileUserPriv fileUserPriv = new TeeFileUserPriv();
                     fileUserPriv.setFileNetdisk(fileNetdisk);
                     fileUserPriv.setPrivValue(TeeStringUtil.getInteger(userPriv, 0));
                     fileUserPriv.setUser(user);
                     fileUserPrivDao.save(fileUserPriv);
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
        String hql = " from TeeFileUserPriv where fileNetdisk.fileNetdiskType=0 and fileNetdisk.filetype=0 and fileNetdisk.sid = " + fileId;
        List<TeeFileUserPriv> fileUserPrivs = (List<TeeFileUserPriv>) fileUserPrivDao.executeQuery(hql, null);

        List<TeeFileUserPrivModel> models = new ArrayList<TeeFileUserPrivModel>();
        if (fileUserPrivs != null && fileUserPrivs.size() > 0) {
            for (TeeFileUserPriv fileUserPriv : fileUserPrivs) {
                TeeFileUserPrivModel model = new TeeFileUserPrivModel();
                model.setSid(fileUserPriv.getSid());
                model.setPrivValue(this.getPrivValue(fileUserPriv.getPrivValue()));
                models.add(model);
                TeePerson person = fileUserPriv.getUser();
                if (person != null) {
                    model.setUserId(person.getUuid());
                    model.setUserName(person.getUserName());
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
     * 获取网盘权限信息(个人文件柜)
     * 
     * @date 2014-1-15
     * @author
     * @param fileId
     * @return
     */
    public TeeJson getFileNetdiskPersonPrivService(int fileId,TeePerson person) {
        TeeJson json = new TeeJson();
        String hql = " from TeeFileUserPriv where fileNetdisk.fileNetdiskType=1 and fileNetdisk.filetype=0 and fileNetdisk.creater.uuid=" + person.getUuid() + " and fileNetdisk.sid = " + fileId   ;
        List<TeeFileUserPriv> fileUserPrivs = (List<TeeFileUserPriv>) fileUserPrivDao.executeQuery(hql, null);

        List<TeeFileUserPrivModel> models = new ArrayList<TeeFileUserPrivModel>();
        if (fileUserPrivs != null && fileUserPrivs.size() > 0) {
            for (TeeFileUserPriv fileUserPriv : fileUserPrivs) {
                TeeFileUserPrivModel model = new TeeFileUserPrivModel();
                model.setSid(fileUserPriv.getSid());
                model.setPrivValue(this.getPrivValue(fileUserPriv.getPrivValue()));
                models.add(model);
                TeePerson dbPerson = fileUserPriv.getUser();
                if (person != null) {
                    model.setUserId(dbPerson.getUuid());
                    model.setUserName(dbPerson.getUserName());
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
     * 删除网盘人员权限信息
     * @date 2014-2-15
     * @author
     * @param sids
     * @return
     */
    public TeeJson deleteFileUserPriv(int sid) {
        fileUserPrivDao.deleteFileUserPrivDao(sid);
        TeeJson json = new TeeJson();
        json.setRtState(true);
        json.setRtMsg("人员权限删除成功!");
        return json;
    }
    
    
    
    
    public static void main(String[] args) {
        int dbValue = 63;
        StringBuffer buffer = new StringBuffer();
        for (int i = 1; i <= dbValue; i *= 2) {
            if (!TeeUtility.isNullorEmpty(buffer.toString().trim()) && ((i & dbValue) != 0)) {
                buffer.append(",");
            }
            if ((i & dbValue) != 0) {
                buffer.append(i & dbValue);
            }

        }
        String ddd = buffer.toString();
        System.out.println(ddd);
        
      /*Set<Integer> set = new HashSet<Integer>();
      set.add(3);
      set.add(3);
      set.add(1);
      set.add(4);
      Iterator<Integer> iterator = set.iterator();
      while(iterator.hasNext()){
          System.out.println(iterator.next());
      }*/
        
    }

}
