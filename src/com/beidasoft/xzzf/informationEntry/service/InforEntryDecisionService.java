package com.beidasoft.xzzf.informationEntry.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryDecision;
import com.beidasoft.xzzf.informationEntry.dao.InforEntryDecisionDao;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.beidasoft.xzzf.informationEntry.model.InforEntryDecisionModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 子表查封扣押决定书SERVICE类
 */
@Service
public class InforEntryDecisionService extends TeeBaseService {

    @Autowired
    private InforEntryDecisionDao inforEntryDecisionDao;
    @Autowired
    private InforEntryItemService inforEntryItemService;
    @Autowired
    private InforEntryBaseService inforEntryBaseService;

    /**
     * 保存子表查封扣押决定书数据
     *
     * @param beanInfo
     */
    public TeeJson save(InforEntryDecisionModel model, HttpServletRequest request) {
    	TeeJson json = new TeeJson();
    	InforEntryDecision beanInfo = getById(model.getCaseId());
    	
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        //如果不存在 new一个对象新增
        if (TeeUtility.isNullorEmpty(beanInfo)) {
        	beanInfo = new InforEntryDecision();
        	model.setEditFlag("1");
		}
        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);

        //处理时间类型
        if (!TeeUtility.isNullorEmpty(model.getStartDateStr())) {
        	beanInfo.setStartDate(TeeDateUtil.format(model.getStartDateStr(),"yyyy年MM月dd日"));
		}
        if (!TeeUtility.isNullorEmpty(model.getEndDateStr())) {
        	beanInfo.setEndDate(TeeDateUtil.format(model.getEndDateStr(),"yyyy年MM月dd日"));
		}
        if (!TeeUtility.isNullorEmpty(model.getArrvelDateStr())) {
        	beanInfo.setArrvelDate(TeeDateUtil.format(model.getArrvelDateStr(),"yyyy年MM月dd日"));
        }
        if (!TeeUtility.isNullorEmpty(model.getReceiptDateStr())) {
        	beanInfo.setReceiptDate(TeeDateUtil.format(model.getReceiptDateStr(),"yyyy年MM月dd日"));
        }
        if ("1".equals(model.getEditFlag())) {
        	beanInfo.setId(UUID.randomUUID().toString());
    		beanInfo.setCreateName(user.getUserName());
    		beanInfo.setCreateId(TeeStringUtil.getString(user.getUuid()));
    		beanInfo.setCreateDate(new Date());
    		beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
    		//修改步骤编号
    		inforEntryBaseService.updateIsNext(model.getCaseId(),2, request);
		}else {
			beanInfo.setUpdateName(user.getUserName());
			beanInfo.setUpdateId(TeeStringUtil.getString(user.getUuid()));
			beanInfo.setUpdateDate(new Date());
			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
		}
        //修改子表中的值
        inforEntryItemService.updateByCaseId(model);
        inforEntryDecisionDao.saveOrUpdate(beanInfo);
        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }

    /**
     * 获取子表查封扣押决定书数据
     *
     * @param id
     * @return     */
    public InforEntryDecision getById(String id) {
        return inforEntryDecisionDao.get(id);
    }
    
    /**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public InforEntryDecision getbyCaseId(String caseId){
		InforEntryDecision decision = inforEntryDecisionDao.getbyCaseId(caseId);
		return decision;
	}
}
