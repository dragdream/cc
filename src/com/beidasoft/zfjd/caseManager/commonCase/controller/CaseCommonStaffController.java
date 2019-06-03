/**   
 * 功能描述：
 * @Package: com.beidasoft.zfjd.caseManager.commonCase.controller 
 * @author: songff   
 * @date: 2019年1月17日 下午3:36:26 
 */
package com.beidasoft.zfjd.caseManager.commonCase.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.caseManager.commonCase.service.CaseCommonBaseService;
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
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

/**   
* 2019 
* @ClassName: CaseCommonStaffController.java
* @Description: 该类的功能描述
*
* @author: songff
* @date: 2019年1月17日 下午3:36:26 
*
*/
@Controller
@RequestMapping("caseCommonStaffCtrl")
public class CaseCommonStaffController {
    //获取日志记录器Logger，名字为本类类名
    private static Logger logger = LogManager.getLogger(CaseCommonStaffController.class);
    
    @Autowired
    private CaseCommonBaseService caseCommonBaseService;
    @Autowired
    private TblSubjectPersonService personService;
    @Autowired
    private TblOfficialsService officialsService;
    @Autowired
    private SubjectService subjectService;
    
    /**
     * 
    * @Function: commonCaseAddStaff()
    * @Description: 跳转一般案件执法人员页面
    *
    * @param: request
    * @param: response
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月17日 上午11:50:23 
    *
     */
    @RequestMapping("commonCaseAddStaff")
    public void commonCaseAddStaff(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            if(request.getParameter("subjectId") != null && !"".equals(request.getParameter("subjectId"))) {
                String subjectId = TeeStringUtil.getString(request.getParameter("subjectId"),"");
                request.setAttribute("subjectId", subjectId);
            } 
            request.getRequestDispatcher("/supervise/caseManager/commonCase/common_case_addStaff.jsp").forward(request, response);    
        }catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    /**
     * 
    * @Function: findListBySubjectIdAndPerson();
    * @Description: 通过主体ID，人员条件查询人员
    *
    * @param: tModel 表格参数
    * @param: subPersonModel 主体ID参数
    * @param: oModel 人员条件参数
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月17日 下午3:41:31 
    *
     */
    @ResponseBody
    @RequestMapping("findListBySubjectIdAndPerson")
    public TeeEasyuiDataGridJson findListBySubjectIdAndPerson(TeeDataGridModel tModel, TblSubjectPersonModel subPersonModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson json = caseCommonBaseService.findListBySubjectIdAndPerson(tModel, subPersonModel);
        return json;
    }
    
    /**
     * 
    * @Function: findListByPersonIds()
    * @Description: 该函数的功能描述
    *
    * @param:描述1描述
    * @return：返回结果描述
    * @throws：异常描述
    *
    * @author: songff
    * @date: 2019年1月17日 下午4:19:50 
    *
     */
    @ResponseBody
    @RequestMapping("findListByPersonIds")
    public TeeEasyuiDataGridJson findListByPersonIds(TeeDataGridModel tModel,TblOfficialsModel cbModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        // 接收查询集合 cList
        List<TblOfficials> cList = null;
        // bean对应的model cModels
        List<TblOfficialsModel> cModels = new ArrayList<TblOfficialsModel>();
        // 查询集合总数
        Long count = null;
        //处理人员ID
        if(cbModel.getIds() != null && !"".equals(cbModel.getIds())) {
            cbModel.setIds("'" + cbModel.getIds().replace(",", "','") + "'");
        }
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
                //获取主体ID
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
    
}
