package com.beidasoft.xzzf.informationEntry.service;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryBase;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryPunish;
import com.beidasoft.xzzf.informationEntry.dao.InforEntryPunishDao;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.beidasoft.xzzf.informationEntry.model.InforEntryPunishModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 字表处罚决定SERVICE类
 */
@Service
public class InforEntryPunishService extends TeeBaseService {

    @Autowired
    private InforEntryPunishDao inforEntryPunishDao;
    
    @Autowired
    private InforEntryBaseService inforEntryBaseService;

    /**
     * 保存字表处罚决定数据
     *
     * @param beanInfo
     */
    public TeeJson save(InforEntryPunishModel model, HttpServletRequest request) {
    	
    	TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        InforEntryPunish beanInfo = new InforEntryPunish();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);

  		// 单独处理时间类型转换
  		if (!StringUtils.isBlank(model.getInspectDateStr())) {//检查日期
  			beanInfo.setInspectDate(TeeDateUtil.format(model.getInspectDateStr(),"yyyy年MM月dd日"));
  		}
  		if (!StringUtils.isBlank(model.getFilingDateStr())) {//立案日期
  			beanInfo.setFilingDate(TeeDateUtil.format(model.getFilingDateStr(),"yyyy年MM月dd日"));
  		}
  		
  		if (beanInfo.getId() == null || beanInfo.getId() == "") {
  			beanInfo.setId(UUID.randomUUID().toString());
 			beanInfo.setCreateName(user.getUserName());
 			beanInfo.setCreateId(TeeStringUtil.getString(user.getUuid()));
 			beanInfo.setCreateDate(new Date());
 			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
 			
 			inforEntryBaseService.updateIsNext(beanInfo.getCaseId(), 3, request);
 			
 		} else {
 			beanInfo.setUpdateName(user.getUserName());
 			beanInfo.setUpdateId(TeeStringUtil.getString(user.getUuid()));
 			beanInfo.setUpdateDate(new Date());
 			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
 		}

  		inforEntryPunishDao.saveOrUpdate(beanInfo);

        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    	
    }

    /**
     * 获取字表处罚决定数据
     *
     * @param id
     * @return     */
    public InforEntryPunish getById(String id) {

        return inforEntryPunishDao.get(id);
    }
    
    /**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public InforEntryPunish getbyCaseId(String caseId){
		 return inforEntryPunishDao.getbyCaseId(caseId);
	}
}
