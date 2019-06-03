package com.tianee.oa.core.general.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.oa.core.general.dao.TeeSysCodeDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
@Service
public class TeeSysCodeService extends TeeBaseService{
    @Autowired
    TeeSysCodeDao sysCodeDao;
    
    

    TeeSysCodeManager codeManager = new TeeSysCodeManager() ;
    /**
     * 新增或者更新
     * @param para
     * @return
     */
    public TeeJson addUpdatePara(TeeSysCode para , HttpServletRequest request){
    	String oldCodeNo = "";
    	int oldParentId = 0;
    	TeeJson json = new TeeJson();
    	if(para.getSid() > 0){//更新
    		TeeSysCode code = sysCodeDao.selectById(para.getSid());
    		if(code != null){
    			oldCodeNo = code.getCodeNo();
    			oldParentId = code.getParentId();
    			//主编码校验
            	boolean isExist  = false;
            	
            	if(para.getParentId() > 0){
            		isExist = sysCodeDao.isExistChild(true,para);
            	}else{
            		isExist = sysCodeDao.isExist(true,para);
            	}
            	if(isExist){//已存在
            		json.setRtState(false);
            		json.setRtMsg("已存在主编码编号！");
            		return json;
            	}else{
            		BeanUtils.copyProperties(para, code);
            		sysCodeDao.updateSysCode(code);
            	}
            	
    		}else{
    			json.setRtState(false);
        		json.setRtMsg("此数据已被删除！");
        		return json;
    		}
    	}else{
    		//主编码校验
        	boolean isExist  = false;
        	if(para.getParentId() > 0){
        		isExist= sysCodeDao.isExistChild(false,para);
        	}else{
        		isExist = sysCodeDao.isExist(false,para);
        	}
        	if(isExist){//已存在
        		json.setRtState(false);
        		json.setRtMsg("已存在主编码编号！");
        		return json;
        	}else{
        		sysCodeDao.save(para);
 
        	}
    	}
    	if(para.getParentId() > 0){
    		String ids = para.getParentId() + "";
    		if(oldParentId > 0 && oldParentId != para.getParentId() ){
    			ids = ids + "," + oldParentId;
    		}
    		List<TeeSysCode>  list = sysCodeDao.getSysParaByIds(ids);//查询主类编码
    		String parentCodeNo = "";
    		String oldParentCodeNo = "";
    		for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSid() == para.getParentId()){
					parentCodeNo = list.get(i).getCodeNo();
				}
				if(list.get(i).getSid() == oldParentId){
					oldParentCodeNo = list.get(i).getCodeNo();
				}
			}
    		codeManager.updateChildSysCodeElement(oldParentCodeNo, parentCodeNo, oldCodeNo,  para);
    	}else{
    		codeManager.updateSysCodeElement(oldCodeNo, para.getCodeNo());
    	}
    	json.setRtMsg("保存成功");
    	json.setRtState(true);
    	return json;
    }
    
    
    /**
     * 获取对象  byId
     * @author syl
     * @date 2014-3-1
     * @param id
     * @return
     */
    public TeeSysCode getById(int id){
    	TeeSysCode code = sysCodeDao.selectById(id);
    	return code;
    }
    
    /**
     * 删除  byId
     * @author syl
     * @date 2014-3-1
     * @param id
     * @return
     */
    public void deleteById(int id){
    	TeeSysCode code = sysCodeDao.selectById(id);
    	sysCodeDao.delById(id);
    	if(code != null){
    		int parentId = code.getParentId();
    		TeeSysCode parentCode = sysCodeDao.selectById(parentId);
    		if(parentCode != null){
    			String parentCodeNo = parentCode.getCodeNo();
    			String codeNo = code.getCodeNo();
    			codeManager.deleteChildSysCodeElement(parentCodeNo, codeNo);
    		}
    		
    	}
    
    }
    
    /**
     * 删除主编码  同时也删除下级所有编码
     * @author syl
     * @date 2014-3-1
     * @param id
     * @return
     */
    public void delByParentId(int id){
    	
		TeeSysCode parentCode = sysCodeDao.selectById(id);
    	sysCodeDao.delByParentId(id);
    	codeManager.removeSysCodeElement(parentCode.getCodeNo());
    }
    
    /**
     * 获取主编码列表
     * @param paraName
     * @return
     */
    public List<TeeSysCode> getSysPara(){
    	List<TeeSysCode> list = sysCodeDao.getSysPara();
    	return list;
    }
    
    /**
     * 根据主编码编号 获取子编码列表
     * @author syl
     * @date 2014-3-1
     * @param code
     * @return
     */
    public List<TeeSysCode> getSysParaByParent(TeeSysCode code){
    	List<TeeSysCode> list = sysCodeDao.getSysParaByParent(code);
    	return list;
    }
  
    public List<TeeSysCode> getSysParaByParentCode(String parentCodeNo, String codeNo) {
    	List<TeeSysCode> list = sysCodeDao.getSysParaByParentCode(parentCodeNo, codeNo);
        return list;
    }
}
