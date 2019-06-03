package com.tianee.oa.core.base.hr.code.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.hr.TeeHrCodeManager;
import com.tianee.oa.core.base.hr.code.bean.TeeHrCode;
import com.tianee.oa.core.base.hr.code.dao.TeeHrCodeDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
@Service
public class TeeHrCodeService extends TeeBaseService{
    @Autowired
    TeeHrCodeDao sysCodeDao;
    
    

   TeeHrCodeManager codeManager = new TeeHrCodeManager() ;
    /**
     * 新增或者更新
     * @param para
     * @return
     */
    public TeeJson addUpdatePara(TeeHrCode para , HttpServletRequest request){
    	String oldCodeNo = "";
    	int oldParentId = 0;
    	TeeJson json = new TeeJson();
    	if(para.getSid() > 0){//更新
    		TeeHrCode code = sysCodeDao.selectById(para.getSid());
    		if(code != null){
    			oldCodeNo = code.getCodeNo();
    			oldParentId = code.getParentId();
    			//主编码校验
            	boolean isExist  = false;
            	
            	if(para.getParentId() > 0){
            		sysCodeDao.isExistChild(true,para);
            	}else{
            		sysCodeDao.isExist(true,para);
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
        		sysCodeDao.isExistChild(true,para);
        	}else{
        		sysCodeDao.isExist(true,para);
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
    		List<TeeHrCode>  list = sysCodeDao.getSysParaByIds(ids);//查询主类编码
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
    public TeeHrCode getById(int id){
    	TeeHrCode code = sysCodeDao.selectById(id);
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
    	TeeHrCode code = sysCodeDao.selectById(id);
    	sysCodeDao.delById(id);
    	if(code != null){
    		int parentId = code.getParentId();
    		TeeHrCode parentCode = sysCodeDao.selectById(parentId);
    		if(parentCode != null){
    			String parentCodeNo = parentCode.getCodeNo();
    			String codeNo = code.getCodeNo();
    			//codeManager.deleteChildSysCodeElement(parentCodeNo, codeNo);
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
    	
		TeeHrCode parentCode = sysCodeDao.selectById(id);
    	sysCodeDao.delByParentId(id);
    	//codeManager.removeSysCodeElement(parentCode.getCodeNo());
    }
    
    /**
     * 获取主编码列表
     * @param paraName
     * @return
     */
    public List<TeeHrCode> getSysPara(){
    	List<TeeHrCode> list = sysCodeDao.getSysPara();
    	return list;
    }
    
    /**
     * 根据主编码编号 获取子编码列表
     * @author syl
     * @date 2014-3-1
     * @param code
     * @return
     */
    public List<TeeHrCode> getSysParaByParent(TeeHrCode code){
    	List<TeeHrCode> list = sysCodeDao.getSysParaByParent(code);
    	return list;
    }
  
    
}
