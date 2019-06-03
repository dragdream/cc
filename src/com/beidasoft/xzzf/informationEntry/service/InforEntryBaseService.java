package com.beidasoft.xzzf.informationEntry.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryBase;
import com.beidasoft.xzzf.informationEntry.bean.InforEntryStaff;
import com.beidasoft.xzzf.informationEntry.dao.InforEntryBaseDao;
import com.beidasoft.xzzf.informationEntry.model.InforEntryBaseModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 历史信息录入主表SERVICE类
 */
@Service
public class InforEntryBaseService extends TeeBaseService {

    @Autowired
    private InforEntryBaseDao inforEntryBaseDao;
    @Autowired
    private InforEntryStaffService inforEntryStaffService;
    @Autowired
    private TeePersonService personService;

    /**
     * 保存历史信息录入主表数据
     *
     * @param beanInfo
     */
    public TeeJson save(InforEntryBaseModel model, HttpServletRequest request) {

        TeeJson json = new TeeJson();
        TeePerson user = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        InforEntryBase beanInfo = new InforEntryBase();

        //model-->实体类传值
        BeanUtils.copyProperties(model, beanInfo);
        
        //处理时间类型
        if (!TeeUtility.isNullorEmpty(model.getPermitDecisionDateStr())) {
        	beanInfo.setPermitDecisionDate(TeeDateUtil.format(model.getPermitDecisionDateStr(),"yyyy年MM月dd日"));
		}
        if (!TeeUtility.isNullorEmpty(model.getPermitClosingDateStr())) {
        	beanInfo.setPermitClosingDate(TeeDateUtil.format(model.getPermitClosingDateStr(),"yyyy年MM月dd日"));
		}
        
        if ("1".equals(model.getEditFlag())) {
        	//新增时
			beanInfo.setCreateName(user.getUserName());
			beanInfo.setCreateId(TeeStringUtil.getString(user.getUuid()));
			beanInfo.setCreateDate(new Date());
			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
		} else if ("2".equals(model.getEditFlag())) {
			//修改时
			beanInfo.setUpdateName(user.getUserName());
			beanInfo.setUpdateId(TeeStringUtil.getString(user.getUuid()));
			beanInfo.setUpdateDate(new Date());
			beanInfo.setCreateDept(TeeStringUtil.getString(user.getDept().getUuid()));
		}
        //保存执法人员子表
        if (!TeeUtility.isNullorEmpty(model.getPersonJsonStr())) {
        	//保存之前先删除子表中的数据
        	inforEntryStaffService.deleteByCaseId(model.getId());
        	List<Map<String, String>> staffList = TeeJsonUtil.JsonStr2MapList(model.getPersonJsonStr());
        	for (Map<String, String> map : staffList) {
        		TeePerson person = personService.get(TeeStringUtil.getInteger(map.get("identistyId"), 0));
        		InforEntryStaff staff = new InforEntryStaff();
            	staff.setId(UUID.randomUUID().toString());
            	staff.setCaseId(map.get("caseId"));
            	staff.setIdentistyId(TeeStringUtil.getString(person.getUuid()));
            	staff.setOfficeName(person.getUserName());
            	staff.setCardCode(person.getPerformCode());
            	staff.setSubjectId(TeeStringUtil.getString(person.getDept().getUuid()));
            	staff.setCreateDate(new Date());
            	inforEntryStaffService.save(staff);
			}
		}
        beanInfo.setIsDelete(0);
        inforEntryBaseDao.saveOrUpdate(beanInfo);
        json.setRtData(beanInfo);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 修改当前状态
     * @param model
     * @param request
     * @return
     */
    public TeeJson updateIsNext(String caseId , int isNext, HttpServletRequest request) {
    	TeeJson json = new TeeJson();
        InforEntryBase base = getById(caseId);
        base.setIsNext(isNext);
        inforEntryBaseDao.update(base);
        json.setRtData(base);
        json.setRtState(true);
        return json;
    }

    /**
     * 获取历史信息录入主表数据
     *
     * @param id
     * @return     */
    public InforEntryBase getById(String id) {
        return inforEntryBaseDao.get(id);
    }
    
    /**
     * 分页查询
     * @param dataGridModel
     * @param entryBase
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,InforEntryBaseModel baseModel,HttpServletRequest request){
    	TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
    	//通过分页获取检查对象信息数据的List集合
		long total = inforEntryBaseDao.getTotal(baseModel,request);
		List<InforEntryBase> baseList = inforEntryBaseDao.listByPage(baseModel, dataGridModel,request);
		List<InforEntryBaseModel> modelList = new ArrayList<InforEntryBaseModel>();
		for (InforEntryBase entryBase : baseList) {
			InforEntryBaseModel model = new InforEntryBaseModel();
			BeanUtils.copyProperties(entryBase, model);
			//处理时间类型
			//许可决定日期
			if (!TeeUtility.isNullorEmpty(entryBase.getPermitDecisionDate())) {
				model.setPermitDecisionDateStr(TeeDateUtil.format(entryBase.getPermitDecisionDate(),"yyyy年MM月dd日"));
			}
			//许可截至日期
			if (!TeeUtility.isNullorEmpty(entryBase.getPermitClosingDate())) {
				model.setPermitClosingDateStr(TeeDateUtil.format(entryBase.getPermitClosingDate(),"yyyy年MM月dd日"));
			}
			modelList.add(model);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
    }
    
    /**
     * 获取历史信息录入主表数据
     *
     * @param id
     * @param request
     * @return 
     */
    public TeeJson getDocInfo(String id, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        InforEntryBaseModel model = new InforEntryBaseModel();
        InforEntryBase beanInfo = getById(id);
        if (TeeUtility.isNullorEmpty(beanInfo)) {
			json.setRtState(false);
			return json;
		}
        //实体类-->model传值
        BeanUtils.copyProperties(beanInfo, model);
        //处理时间类型
        if (!TeeUtility.isNullorEmpty(beanInfo.getPermitDecisionDate())) {
        	model.setPermitDecisionDateStr(TeeDateUtil.format(beanInfo.getPermitDecisionDate(),"yyyy年MM月dd日"));
		}
        if (!TeeUtility.isNullorEmpty(beanInfo.getPermitClosingDate())) {
        	model.setPermitClosingDateStr(TeeDateUtil.format(beanInfo.getPermitClosingDate(),"yyyy年MM月dd日"));
		}
        //查询相关执法人员
        List<InforEntryStaff> staffs = inforEntryStaffService.getbyCaseId(beanInfo.getId());
        StringBuffer personJsonStr = new StringBuffer();
        if (!staffs.isEmpty()) {
             for (InforEntryStaff inforEntryStaff : staffs) {
             	personJsonStr.append(inforEntryStaff.getIdentistyId()+",");
     		}
		}
        
        personJsonStr.deleteCharAt(personJsonStr.length() - 1);
        model.setPersonJsonStr(personJsonStr.toString());
        json.setRtData(model);
        json.setRtState(true);
        return json;
    }
    
    /**
     * 逻辑删除
     * @param dataGridModel
     * @param entryBase
     * @return
     */
    public TeeJson updateDelete(String caseId,HttpServletRequest request){
    	TeeJson json = new TeeJson();
        InforEntryBase base = getById(caseId);
        base.setIsDelete(1);
        inforEntryBaseDao.update(base);
        json.setRtData(base);
        json.setRtState(true);
        return json;
    }
}
