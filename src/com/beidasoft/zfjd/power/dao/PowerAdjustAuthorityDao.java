package com.beidasoft.zfjd.power.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.power.bean.PowerAdjustAuthority;
import com.tianee.webframe.dao.TeeBaseDao;

import net.sf.json.JSONObject;

@Repository
public class PowerAdjustAuthorityDao extends TeeBaseDao<PowerAdjustAuthority>{
    
    public void batchSave(List<PowerAdjustAuthority> adjustAuthorities) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(int i = 0; i < adjustAuthorities.size(); i++) {
            session.save(adjustAuthorities.get(i));
        }
        tx.commit();
        session.close();
    }
    
    public List<PowerAdjustAuthority> listByPage(int firstResult, int rows, String id) {
        String hql = "from PowerAdjustAuthority where powerAdjust.id = '" + id + "'" ;
        return super.pageFind(hql, firstResult, rows, null);
    }
    
    public Long listCount(String id) {
        String hql = "select count(*) from PowerAdjustAuthority where powerAdjust.id = '" + id + "'" ;
        return super.count(hql, null);
    }
    
    public void examinePower(String id, String examineState) {
        String hql = " update PowerAdjustAuthority set examineState = '" + examineState + "'";
        hql = hql + " where id = '" + id + "'";
        deleteOrUpdateByQuery(hql, null);
    }
    
    public List<PowerAdjustAuthority> getAuthorityByAdjustId(String adjustId) {
        String hql = " from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "'";
        return super.find(hql, null);
    }
    
    public List<PowerAdjustAuthority> listByAdjustId(String adjustId) {
        String hql = " from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "'"; 
        return super.find(hql, null);
    }
    
    public JSONObject getExamineInfoById(String adjustId) {
        JSONObject resultInfo = new JSONObject();
        
        Long examinePass = super.count("select count(*) from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "' and examineState = 20", null); 
        Long examineFail = super.count("select count(*) from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "' and examineState = 90", null); 
        Long examineEdit = super.count("select count(*) from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "' and examineState = 30", null);
        Long examineNone = super.count("select count(*) from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "' and examineState = 10", null);

        Long sum = super.count("select count(*) from PowerAdjustAuthority where powerAdjust.id = '" + adjustId + "'", null);
        
        resultInfo.put("pass", examinePass);
        resultInfo.put("fail", examineFail);
        resultInfo.put("edit", examineEdit);
        resultInfo.put("none", examineNone);
        resultInfo.put("sum", sum);
        
        return resultInfo;
    }
}
