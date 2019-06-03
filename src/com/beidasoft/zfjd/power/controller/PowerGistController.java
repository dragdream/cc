package com.beidasoft.zfjd.power.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.binding.corba.wsdl.Array;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.power.bean.PowerGist;
import com.beidasoft.zfjd.power.model.PowerGistModel;
import com.beidasoft.zfjd.power.service.PowerGistService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeStringUtil;

@Controller
@RequestMapping("powerGistCtrl")
public class PowerGistController {
    
    @Autowired
    private PowerGistService gistService;
    
    @Autowired
    private TeeDeptService deptService;
    
    @ResponseBody
    @RequestMapping("/listByPage")
    public TeeEasyuiDataGridJson listByPage(HttpServletRequest request, TeeDataGridModel dm, PowerGistModel powerGistModel) {
        TeeEasyuiDataGridJson datagrid = new TeeEasyuiDataGridJson();
        
        TeePerson person = (TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
        TeeDepartment department = deptService.selectDeptByUuid(TeeStringUtil.getString(person.getDept().getUuid(), ""));
        String subjectIds = "";
        if(!"admin".equals(person.getUserId())) {
            if(department.getRelations() != null && department.getRelations().size() != 0) {
                if(department.getRelations().size() == 0) {
                    subjectIds = department.getRelations().get(0).getBusinessSubjectId();
                    powerGistModel.setSubjectId(subjectIds);
                } else {
                    for(int i = 0; i < department.getRelations().size(); i++) {
                        subjectIds = subjectIds + department.getRelations().get(i).getBusinessSubjectId() + ",";
                    }
                    subjectIds = subjectIds.substring(0, subjectIds.length() - 1);
                    powerGistModel.setSubjectIds(subjectIds);
                }
            }
        }
        
        List<PowerGist> gists = gistService.listByPage(dm, powerGistModel);
        Long total = gistService.listCount(powerGistModel);
        
        List<PowerGistModel> gistModels = new ArrayList<PowerGistModel>();
        PowerGistModel gistModel = null;

        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("POWER_TYPE");
        List<Map<String, Object>> typeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("GIST_TYPE");
        
        for(int i = 0; i < gists.size(); i++) {
            gistModel = new PowerGistModel();
            
            BeanUtils.copyProperties(gists.get(i), gistModel);
            
            gistModel.setCreateTimeStr(TeeDateUtil.format(gists.get(i).getCreateTime(), "yyyy-MM-dd"));
            gistModel.setPowerName(gists.get(i).getPowerGist().getName());
            for(int j = 0; j < codeList.size(); j++) {
                if(gists.get(i).getPowerGist().getPowerType().equals(codeList.get(j).get("codeNo"))) {
                    gistModel.setPowerType(codeList.get(j).get("codeName").toString());
                    break;
                }
            }
            for(int j = 0; j < typeList.size(); j++) {
                if(gists.get(i).getGistType().equals(typeList.get(j).get("codeNo"))) {
                    gistModel.setGistType(typeList.get(j).get("codeName").toString());
                    break;
                }
            }
            
            gistModels.add(gistModel);
        }
        
        datagrid.setRows(gistModels);
        datagrid.setTotal(total);
        
        return datagrid;
    }
}
