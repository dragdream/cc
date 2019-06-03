package com.beidasoft.xzzf.informationEntry.service;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryReport;
import com.beidasoft.xzzf.informationEntry.dao.InforEntryReportDao;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.beidasoft.xzzf.informationEntry.model.InforEntryReportModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

/**
 * 子表结案报告书SERVICE类
 */
@Service
public class InforEntryReportService extends TeeBaseService {

    @Autowired
    private InforEntryReportDao inforEntryReportDao;
    
    @Autowired
    private InforEntryBaseService inforEntryBaseService;

    /**
     * 保存子表结案报告书数据
     *
     * @param beanInfo
     */
    public TeeJson save(InforEntryReportModel model, HttpServletRequest request) {
    	
    	TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        InforEntryReport beanInfo = new InforEntryReport();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);

        // 单独处理时间类型转换
  		if (!StringUtils.isBlank(model.getPunishDateStr())) {//检查日期
  			beanInfo.setPunishDate(TeeDateUtil.format(model.getPunishDateStr(),"yyyy年MM月dd日"));
  		}

  		if (beanInfo.getId() == null || beanInfo.getId() == "") {
  			beanInfo.setId(UUID.randomUUID().toString());
 			beanInfo.setCreateName(user.getUserName());
 			beanInfo.setCreateId(TeeStringUtil.getString(user.getUuid()));
 			beanInfo.setCreateDate(new Date());
 			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
 			
 			
 		} else {
 			beanInfo.setUpdateName(user.getUserName());
 			beanInfo.setUpdateId(TeeStringUtil.getString(user.getUuid()));
 			beanInfo.setUpdateDate(new Date());
 			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
 		}

  		inforEntryBaseService.updateIsNext(beanInfo.getCaseId(), 4, request);
  		
  		inforEntryReportDao.saveOrUpdate(beanInfo);

        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
        
    }

    /**
     * 获取子表结案报告书数据
     *
     * @param id
     * @return     */
    public InforEntryReport getById(String id) {

        return inforEntryReportDao.get(id);
    }
    
    /**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public InforEntryReport getbyCaseId(String caseId){
		
		return inforEntryReportDao.getbyCaseId(caseId);
	}
}
