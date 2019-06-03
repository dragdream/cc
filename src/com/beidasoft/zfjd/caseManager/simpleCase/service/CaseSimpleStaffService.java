package com.beidasoft.zfjd.caseManager.simpleCase.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.caseManager.simpleCase.bean.CaseSimpleStaff;
import com.beidasoft.zfjd.caseManager.simpleCase.dao.CaseSimpleStaffDao;
import com.beidasoft.zfjd.caseManager.simpleCase.model.CaseSimpleStaffModel;
import com.beidasoft.zfjd.officials.bean.TblOfficials;
import com.beidasoft.zfjd.officials.model.TblOfficialsModel;
import com.beidasoft.zfjd.officials.service.TblOfficialsService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.beidasoft.zfjd.subjectPerson.bean.TblSubjectPerson;
import com.beidasoft.zfjd.subjectPerson.model.TblSubjectPersonModel;
import com.beidasoft.zfjd.subjectPerson.service.TblSubjectPersonService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class CaseSimpleStaffService extends TeeBaseService{
    
    @Autowired
    private CaseSimpleStaffDao caseSimpleStaffDao;
    @Autowired
    private TblSubjectPersonService personService;
    @Autowired
    private TblOfficialsService officialsService;
    @Autowired
    private SubjectService subjectService;
    
    /**
     ** 根据执法人员id查询
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel,TblOfficialsModel cbModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        // 接收查询集合 cList
        List<TblOfficials> cList = null;
        // bean对应的model cModels
        List<TblOfficialsModel> cModels = new ArrayList<TblOfficialsModel>();
        // 查询集合总数
        Long count = null;
        cList = officialsService.listByPage(tModel.getFirstResult(),tModel.getRows(), cbModel);
        count = officialsService.getTotal(cbModel);
        // 定义model
        TblOfficialsModel cModel = null;
        if (cList != null && cList.size() > 0) {
            for (int i = 0; i < cList.size(); i++) {
                cModel = new TblOfficialsModel();
                TblOfficials tempBasic = cList.get(i);
                // 将tempBasic赋值给cModel
                BeanUtils.copyProperties(tempBasic, cModel);
                //主体名称
                TblSubjectPerson tblSubjectPerson = personService.getByPersonId(tempBasic.getId());
                //查询主体
                Subject subject = subjectService.getById(tblSubjectPerson.getSubjectId());
                cModel.setSubjectName(subject.getSubName());
                //执法证号
                if (!TeeUtility.isNullorEmpty(tempBasic.getCityCode())) {
                    cModel.setEnforcerCode(tempBasic.getCityCode());
                } else {
                    cModel.setEnforcerCode(tempBasic.getDepartmentCode());
                }
                cModels.add(cModel);
            }
        }
        json.setRows(cModels);
        json.setTotal(count);
        return json;
    }
    
    /**
     ** 根据主体查询执法人员
     * @param tModel
     * @param cbModel
     * @param request
     * @return
     */
    public TeeEasyuiDataGridJson findListByZhuTi(TeeDataGridModel tModel, TblSubjectPersonModel subPersonModel, HttpServletRequest request){
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        //根据主体id查询执法人员id
        List<TblSubjectPerson> subPersonList = personService.listByPage(tModel.getFirstResult(), tModel.getRows(), subPersonModel);
        long total = personService.getTotal(subPersonModel);
        //查询主体
        Subject subject = subjectService.getById(subPersonModel.getSubjectId());
        //循环
        List<TblOfficialsModel> officialsList = new ArrayList<TblOfficialsModel>();
        if (!subPersonList.isEmpty()) {
            for (TblSubjectPerson tblSubjectPerson : subPersonList) {
                TblOfficialsModel model = new TblOfficialsModel();
                TblOfficials officials = officialsService.getById(tblSubjectPerson.getPersonId());
                if (officials.getIsDelete()==1) {
                    continue;
                }
                BeanUtils.copyProperties(officials, model);
                //主体名称
                model.setSubjectName(subject.getSubName());
                //执法证号
                if (!TeeUtility.isNullorEmpty(officials.getCityCode())) {
                    model.setEnforcerCode(officials.getCityCode());
                } else {
                    model.setEnforcerCode(officials.getDepartmentCode());
                }
                officialsList.add(model);
            }
        }
        json.setRows(officialsList);
        json.setTotal(total);
        return json;
    }
    
    /**
     ** 保存
     * @return
     */
    public TeeJson saveSimpleStaff(CaseSimpleStaffModel staffModel){
        TeeJson json = new TeeJson();
        String[] ids = staffModel.getIds().split(",");
        for (String id : ids) {
            //执法人员
            TblOfficials officials = officialsService.getById(id);
            //人员主体关联表
            TblSubjectPerson subjectPerson = personService.getByPersonId(id);
            //主体表
            Subject subject = subjectService.getById(subjectPerson.getSubjectId());
            CaseSimpleStaff staff = new CaseSimpleStaff();
            staff.setId(UUID.randomUUID().toString());
            staff.setCaseId(staffModel.getCaseId());
            staff.setIdentityId(officials.getId());
            staff.setOfficeName(officials.getName());
            if (!TeeUtility.isNullorEmpty(officials.getCityCode())) {
                staff.setCardCode(officials.getCityCode());
            } else {
                staff.setCardCode(officials.getDepartmentCode());
            }
            staff.setSubjectId(subject.getSubName());
            staff.setCreateDate(new Date());
            caseSimpleStaffDao.save(staff);
        }
        json.setRtState(true);
        return json;
    }
    
    /**
     ** 根据主表id查询
     * @param caseId
     * @return
     */
    public List<CaseSimpleStaff> getListByCaseId(String caseId){
        List<CaseSimpleStaff> list = caseSimpleStaffDao.getListByCaseId(caseId);
        return list;
    }
    
    /**
     ** 根据主表id删除
     * @param caseId
     */
    public void deleteByCaseId(String caseId){
        caseSimpleStaffDao.deleteByCaseId(caseId);
    }
}
