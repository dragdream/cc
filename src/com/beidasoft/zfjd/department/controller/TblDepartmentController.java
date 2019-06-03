package com.beidasoft.zfjd.department.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("departmentInfoController")
public class TblDepartmentController {

    @Autowired
    private TblDepartmentDao departmentDao;
    @Autowired
    private TblDepartmentService departmentService;
    @Autowired
    private TeeSysCodeService sysCodeService;
    @Autowired
    private CommonService commonService;
    
    /**
     * 保存
     * @param departmentModel
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public TeeJson save(TblDepartmentModel departmentModel){
        TeeJson json = new TeeJson();
        try {
            json = departmentService.save(departmentModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.save"+e.getMessage());
        }
        return json;
    }
    
    @ResponseBody
    @RequestMapping("update")
    public TeeJson update(TblDepartmentModel departmentModel){
        TeeJson json = new TeeJson();
        try {
            json = departmentService.updateUser(departmentModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.update"+e.getMessage());
        }
        return json;
    }

    /**
     * 审核
     * @param lawModel
     * @return
     */
    @ResponseBody
    @RequestMapping("/examine")
    public TeeJson examine(String id,HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            departmentService.addDepartment(id,orgCtrl);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.examine"+e.getMessage());
        }
        return json;
    }
    
    @ResponseBody
    @RequestMapping("delete")
    public TeeJson delete(String id){       
        TeeJson json = new TeeJson();
        try {
            //创建实例化实体类对象
            TblDepartmentInfo department = new TblDepartmentInfo();
            department = departmentService.getById(id);
            
            department.setIsDelete(1);
            department.setDeleteTime(new Date());
            departmentService.update(department);   
            
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.delete"+e.getMessage());
        }
        return json;
    }
    
    @SuppressWarnings("unused")
    @ResponseBody
    @RequestMapping("get")
    public TeeJson get(String id) throws Exception{
        TeeJson json = new TeeJson();
        try {
            TblDepartmentInfo userInfo = departmentService.getById(id);
            
            TblDepartmentModel infoModel = new TblDepartmentModel();
            BeanUtils.copyProperties(userInfo, infoModel);
            if (infoModel != null) {            
                json.setRtData(infoModel);
                json.setRtState(true);
            }else{
                json.setRtState(false);
            }
        } catch (Exception e) {
            json.setRtState(false);
        }
        return json;
    }
    
    /**
     * 获取归属人民政府
     * @return
     */
    @SuppressWarnings("rawtypes")
    @ResponseBody
    @RequestMapping("getParentName")
    public String getParentName(String parentId,HttpServletRequest request){
            //获取归属人民政府
            List<Map> parentName = departmentService.listByPI(0, 10, parentId);
            String a = (String) parentName.get(0).get("NAME");
            return a;
                
    }
    
    
    @ResponseBody
    @RequestMapping("findAllUsers")
    public TeeJson findAllUsers(){
        TeeJson json = new TeeJson();
        return json;
    }
    
    @ResponseBody
    @RequestMapping("deletes")
    public TeeJson deletes(HttpServletRequest request){

        String id = request.getParameter("ids");
        TeeJson json = new TeeJson();
        departmentService.deletes(id);
        json.setRtState(true);
        return null;
    }
    
    /**
     * 查询部门名称 权限控制
     * @param q
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getSysCodeTemp.action")
    public List<TblDepartmentInfo> getSysCodeTemp(String q,HttpServletRequest request){
        TblDepartmentModel departmentModel = new TblDepartmentModel();
        List<TblDepartmentInfo> codeList = null;
        try {
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            departmentModel.setName(q);
            codeList = departmentService.listByDeQ(0,10,departmentModel,orgCtrl);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("TblDepartmentController.getSysCodeTemp"+e.getMessage());
        }
        
        return codeList;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,TblDepartmentModel queryModel){
        TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
        long total = departmentService.getTotal(queryModel);
        
        //部门性质 nature
        List<Map<String, Object>> deptNature = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("DEPT_NATURE");

        List<TblDepartmentModel> modelList = new ArrayList();
        List<TblDepartmentInfo> departmentInfos = departmentService.listByPage(
                dataGridModel.getFirstResult(), dataGridModel.getRows(),
                queryModel);
        for (TblDepartmentInfo departmentInfo : departmentInfos) {
            TblDepartmentModel infoModel = new TblDepartmentModel();
            BeanUtils.copyProperties(departmentInfo,infoModel);
            
            if(!TeeUtility.isNullorEmpty(infoModel.getNature())){
                for(Map<String, Object> code : deptNature) {
                    if(infoModel.getNature().equals(code.get("codeNo").toString())) {
                        infoModel.setNature(code.get("codeName").toString());
                        break;
                    }
                }
            }

            modelList.add(infoModel);
        }
        dataGridJson.setTotal(total);
        dataGridJson.setRows(modelList);
        return dataGridJson;

    }
    /**
     * 查看界面进行权限控制
     */
    @ResponseBody
    @RequestMapping("findSearchListBypage")
    public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, TblDepartmentModel cbModel, HttpServletRequest request) {
            //返回前台json 
            TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
            // 接收查询集合 cList
            List<TblDepartmentInfo> cList = null; 
            //bean对应的model cModels
            List<TblDepartmentModel> cModels = new ArrayList<TblDepartmentModel>(); 
            //查询集合总数
            Long count = null;
            try {
                OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
                cList = departmentService.findListByPageSearch(tModel, cbModel, orgCtrl);
                count = departmentService.listSearchCount(cbModel, orgCtrl);
                //定义model
                TblDepartmentModel cModel = null;
                if (cList != null && cList.size() > 0) {
                    for (int i = 0; i < cList.size(); i++) {
                        cModel = new TblDepartmentModel();
                        TblDepartmentInfo tempBasic = cList.get(i);
                        //将tempBasic赋值给cModel
                        BeanUtils.copyProperties(tempBasic, cModel);
                        //获取案件当前状态代码值
                        cModel.setNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_NATURE", cModel.getNature()));
                        cModels.add(cModel);
                    }
                }
                
                json.setRows(cModels);
                json.setTotal(count);
            } catch (Exception e) {
                System.out.println("TblDepartmentController.findSearchListBypage() " + e.getMessage());
            }
            return json;
        }
    
    /**
     * 获取归属政府及部门地区和层级，权限控制
     * @param id
     * @return
     * @date:2019年3月14日下午2:28:18
     * @author:yxy
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ResponseBody
    @RequestMapping("parentIdToLevel")
    public TeeJson parentIdToLevel(HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            List<Map> level = departmentService.parentIdToLevel(0, 10, orgCtrl);
            String idString = (String) level.get(0).get("ID");
            String nameString = (String) level.get(0).get("NAME");
            String a = (String) level.get(0).get("ADMINISTRATIVE_DIVISION");
            String b = (String) level.get(0).get("DEPT_LEVEL");

            Map codeMap = new HashMap();
            codeMap.put("id", idString);
            codeMap.put("name", nameString);
            codeMap.put("areaId", a);
            codeMap.put("areaName", TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", a));
            codeMap.put("levelId", b);
            codeMap.put("levelName", TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", b));
            
            json.setRtData(codeMap);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("departmentController.parentIdToLevel() "+e.getMessage());
        }
        return json;

    }
    
    /**
     * build执法机关树（by department_id）
     * @param id
     * @return
     * @date: 2019年3月8日下午4:27:16
     * @author: hoax
     */
/*    @SuppressWarnings("unchecked")*/
    @ResponseBody
    @RequestMapping("buildDepartmentTree")
    public JSONArray buildDepartmentTree(String id){
        JSONArray json = new JSONArray();
        try {
            
            if(id == null || "".equals(id)){
                TblDepartmentModel queryModel = new TblDepartmentModel();
                //build base node
                //获取中央人民政府组成顶级节点
                queryModel.setIsManubrium(0);
                queryModel.setIsGovernment(1);
                queryModel.setDeptLevel("0100");
                queryModel.setAdministrativeDivision("00000000");
                List<TblDepartmentInfo> countryGovs = departmentService.findEnforceDeptsSameLevel(queryModel);
                for(TblDepartmentInfo  dept : countryGovs){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", dept.getId());
                    jsonObject.put("text",dept.getName());
                    jsonObject.put("state", "closed");
                    json.add(jsonObject);
                }
                //获取国家部委级垂管单位组成顶级节点
                queryModel.setIsManubrium(1);
                queryModel.setIsGovernment(0);
                List<TblDepartmentInfo> manubrimDepts = departmentService.findEnforceDeptsSameLevel(queryModel);
                for(TblDepartmentInfo  dept : manubrimDepts){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", dept.getId());
                    jsonObject.put("text",dept.getName());
                    jsonObject.put("state", "closed");
                    json.add(jsonObject);
                }
            }
            // 参数校验，id不为空时，获取下级列表
            if(id != null){
                //通过id获取部门信息
                TblDepartmentInfo departmentInfo = departmentService.getById(id);
                if(departmentInfo != null){
                    TblDepartmentModel queryModel = new TblDepartmentModel();
                    if(departmentInfo.getIsGovernment() != null && departmentInfo.getIsGovernment().equals(1)){
                        //部门信息存在，且该部门为人民政府
                        //获取下级人民政府节点
                        queryModel.setId(id);
                        queryModel.setIsGovernment(1);
                        queryModel.setIsManubrium(0);
                        queryModel.setAdministrativeDivision(departmentInfo.getAdministrativeDivision());
                        queryModel.setDeptLevel(departmentInfo.getDeptLevel());
                        List<TblDepartmentInfo> govDepts = departmentService.findGovDeptsNextLevel(queryModel);
                        for(TblDepartmentInfo  dept : govDepts){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", dept.getId());
                            jsonObject.put("text",dept.getName());
                            jsonObject.put("state", "closed");
                            json.add(jsonObject);
                        }
                        //获取本地区本级执法部门
                        queryModel.setId(id);
                        queryModel.setIsGovernment(0);
                        queryModel.setIsManubrium(0);
                        queryModel.setAdministrativeDivision(departmentInfo.getAdministrativeDivision());
                        queryModel.setDeptLevel(departmentInfo.getDeptLevel());
                        List<TblDepartmentInfo> enforceDepts = departmentService.findEnforceDeptsSameLevel(queryModel);
                        for(TblDepartmentInfo  dept : enforceDepts){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", dept.getId());
                            jsonObject.put("text",dept.getName());
                            jsonObject.put("state", "closed");
                            json.add(jsonObject);
                        }
                    }else if(departmentInfo.getIsManubrium() != null && departmentInfo.getIsManubrium().equals(1)){
                      //部门信息存在，且该部门为垂管部门
                      //通过parentId获取下级垂管单位
                        queryModel.setDroopId(departmentInfo.getId());
                        queryModel.setIsManubrium(1);
                        List<TblDepartmentInfo> enforceDepts = departmentService.findManuDeptsByParentIds(queryModel);
                        for(TblDepartmentInfo  dept : enforceDepts){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("id", dept.getId());
                            jsonObject.put("text",dept.getName());
                            jsonObject.put("state", "closed");
                            json.add(jsonObject);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("departmentController.parentIdToLevel() "+e.getMessage());
        }
        return json;

    }
    
    /**
     * 账号分配
     * @param officialsModel
     * @param request
     * @return
     * @date:2019年3月18日下午5:06:51
     * @author:yxy
     */
    @ResponseBody
    @RequestMapping("saveUser")
    public TeeJson saveUser(TblDepartmentModel departmentModel,HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            json = departmentService.saveUser(departmentModel,orgCtrl);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.saveUser"+e.getMessage());
        }
        return json;
    }
    
    /**
     * 密码重置
     * @param id
     * @param request
     * @return
     * @date:2019年3月18日下午5:07:06
     * @author:yxy
     */
    @ResponseBody
    @RequestMapping("resetPassword")
    public TeeJson resetPassword(String id,HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            json = departmentService.resetPassword(id);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.resetPassword"+e.getMessage());
        }
        return json;
    }
    
    /**
     * 回收账号
     * @param id
     * @param request
     * @return
     * @date:2019年3月18日下午5:07:14
     * @author:yxy
     */
    @ResponseBody
    @RequestMapping("resetUser")
    public TeeJson resetUser(String id,HttpServletRequest request){
        TeeJson json = new TeeJson();
        try {
            json = departmentService.resetUser(id);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            System.out.println("TblDepartmentController.resetUser"+e.getMessage());
        }
        return json;
    }
}
