package com.beidasoft.xzzf.informationEntry.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.informationEntry.bean.InforEntryStaff;
import com.beidasoft.xzzf.informationEntry.dao.InforEntryStaffDao;
import com.beidasoft.xzzf.informationEntry.model.InforEntryStaffModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 子表执法人员SERVICE类
 */
@Service
public class InforEntryStaffService extends TeeBaseService {

    @Autowired
    private InforEntryStaffDao inforEntryStaffDao;
    
    @Autowired
    private TeePersonDao personDao;

    /**
     * 保存子表执法人员数据
     *
     * @param beanInfo
     */
    public void save(InforEntryStaff beanInfo) {
        inforEntryStaffDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取子表执法人员数据
     *
     * @param id
     * @return     */
    public InforEntryStaff getById(String id) {

        return inforEntryStaffDao.get(id);
    }
    
    /**
     * 获取子表执法人员数据
     *
     * @param id
     * @param request
     * @return 
     */
    public TeeEasyuiDataGridJson getStaffList(HttpServletRequest request,TeeDataGridModel dataGridModel) {
    	TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
    	String ids = request.getParameter("ids");
        if ("empty".equals(ids)) {
        	List<InforEntryStaffModel> staffModels = new ArrayList<InforEntryStaffModel>();
        	json.setRows(staffModels);
	        json.setTotal((long)0);
		} else {
			//查询执法人员
	        String lstHql = "from TeePerson where dept.uuid in (10,11,12,13,14) ";
	        if (!TeeUtility.isNullorEmpty(ids)) {
	        	lstHql = "from TeePerson where uuid in ("+ids+") ";
			}
	        List<TeePerson> personList = personDao.pageFind(lstHql, dataGridModel.getFirstResult(), dataGridModel.getRows(), null);
	        //记录数
	        String totalHql = "select count(*) from TeePerson where dept.uuid in (10,11,12,13,14) ";
	        if (!TeeUtility.isNullorEmpty(ids)) {
	        	totalHql = "select count(*) from TeePerson where uuid in ("+ids+") ";
			}
	        Long count = personDao.count(totalHql, null);
	        List<InforEntryStaffModel> staffModels = new ArrayList<InforEntryStaffModel>();
	        for (TeePerson person : personList) {
	        	InforEntryStaffModel staffModel = new InforEntryStaffModel();
	        	staffModel.setIdentistyId(TeeStringUtil.getString(person.getUuid()));
	        	staffModel.setOfficeName(TeeStringUtil.getString(person.getUserName()));
	        	staffModel.setCardCode(person.getPerformCode());
	        	staffModel.setSubjectId(TeeStringUtil.getString(person.getDept().getUuid()));
	        	staffModels.add(staffModel);
			}
	        json.setRows(staffModels);
	        json.setTotal(count);
		}
        return json;
    }
    
    /**
	 * 根据caseId查询
	 * @param caseId
	 * @return
	 */
	public List<InforEntryStaff> getbyCaseId(String caseId){
		return inforEntryStaffDao.getbyCaseId(caseId);
	}
	
	/**
	 * 根据主表id删除子表信息
	 * @param caseId
	 * @return
	 */
	public TeeJson deleteByCaseId(String caseId){
		TeeJson json = new TeeJson();
		String hql = "delete from InforEntryStaff where caseId = '"+caseId+"'";
		inforEntryStaffDao.executeUpdate(hql, null);
		json.setRtState(true);
		return json;
	}
	
}
