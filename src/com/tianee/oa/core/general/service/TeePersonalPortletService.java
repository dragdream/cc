package com.tianee.oa.core.general.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianee.oa.core.general.bean.TeePortlet;
import com.tianee.oa.core.general.bean.TeePortletPersonal;
import com.tianee.oa.core.general.bean.TeeSms;
import com.tianee.oa.core.general.bean.TeeSmsBody;
import com.tianee.oa.core.general.dao.TeePersonalPortletDao;
import com.tianee.oa.core.general.dao.TeePortletDao;
import com.tianee.oa.core.general.dao.TeeSmsBodyDao;
import com.tianee.oa.core.general.dao.TeeSmsDao;
import com.tianee.oa.core.general.model.TeeSmsModel;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.page.PageUtil;

@Service
public class TeePersonalPortletService extends TeeBaseService{
	
	@Autowired
	private TeePersonalPortletDao personalPortletDao;
	
	@Autowired
	private TeePortletDao portletDao;
	
    /**
     * 获取登录人portletList
     * @param portletPersonal
     * @return
     */
    public List<TeePortletPersonal> getPortletList(int uuid){
    	
    	List<TeePortletPersonal> list = new ArrayList<TeePortletPersonal>();
		try {
			list = personalPortletDao.getPortletList(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return list;
    }

    /**
     * 根据portletId获取登录人portletList
     * @param portletPersonal
     * @return
     */
    public List<TeePortletPersonal> getPortletByPortletId(int id){
    	
    	List<TeePortletPersonal> list = new ArrayList<TeePortletPersonal>();
		try {
			list = personalPortletDao.getPortletByPortletId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return list;
    }
    
    public void saveAll(int id,int userId){
    	TeePortletPersonal p = new TeePortletPersonal();
    	if(!personalPortletDao.checkRepeat(id, userId)){
        	p.setSortNo(1);
        	p.setUserId(userId);
        	p.setPortletId(portletDao.get(id));
        	p.setPortCol(1);
        	p.setPortHeight(150);
        	personalPortletDao.save(p);
    	}
    }
    
    public void saveTotal(int userId){
    	List<TeePortlet> list = portletDao.getPortletList();
    	for(TeePortlet portlet : list){
        	TeePortletPersonal p = new TeePortletPersonal();
        	if(!personalPortletDao.checkRepeat(portlet.getSid(), userId)){
	        	p.setSortNo(1);
	        	p.setUserId(userId);
	        	p.setPortletId(portlet);
	        	p.setPortCol(1);
	        	p.setPortHeight(150);
	        	personalPortletDao.save(p);
        	}
    	}
    }
    
    public void save(TeePortletPersonal p){

    	personalPortletDao.save(p);
    	
    }
    
    public TeePortletPersonal getOne(int id){
    	
    	return personalPortletDao.get(id);
    	
    }
	
    public void update(TeePortletPersonal p){

    	personalPortletDao.update(p);
    	
    }
    
    public void updateMaxCol(int col,int userId){
    	
    	String hql = "update TeePortletPersonal p set p.portCol = "+col+" where p.userId="+userId+" and p.portCol > "+col;
    	
    	personalPortletDao.deleteOrUpdateByQuery(hql, null);
    }
    
    public void updatePortletAddress(String idAddressStr){
    	String hql = " update TeePortletPersonal p set p.portCol = case p.sid ";
    	String hql1 = "";
    	String hql2 = "";
    	idAddressStr = idAddressStr.replaceAll(" ", "");
    	//System.out.println(idAddressStr);
    	String[] address = idAddressStr.split("`~");
    	for(String a : address){
    		String[] idStr = a.split("[*]");
    		hql1 += " when "+Integer.parseInt(idStr[0])+" then "+Integer.parseInt(idStr[1]);
    		hql2 += " when "+Integer.parseInt(idStr[0])+" then "+Integer.parseInt(idStr[2]);
    	}
    	hql = hql + hql1 + " end, p.sortNo = case p.sid " + hql2 + " end";
    	//System.out.println(hql);
    	personalPortletDao.deleteOrUpdateByQuery(hql, null);
    }
    
    public void removePortlet(String id){
    	
    	
    	personalPortletDao.delete(Integer.parseInt(id));
    }

}


