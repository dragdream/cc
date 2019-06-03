package com.beidasoft.zfjd.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.beidasoft.zfjd.system.model.AdminDivisionDividedModel;
import com.beidasoft.zfjd.system.service.AdminDivisionDividedService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.bean.TeeSysCode;
import com.tianee.oa.core.general.controller.TeeSysCodeController;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("adminDivisionManageCtrl")
public class AdminIDivisionManageController {

    @Autowired
    private AdminDivisionDividedService adminDivisionService;

    @Autowired
    private CommonService commonService;
    
    @Autowired
    private TeeSysCodeController sysCodeManager;
    
    @Autowired
    TeeSysCodeService sysCodeServ;

    /**
     * 条件查询-列表分页获取行本层级向下的政区划代码
     * 
     * @param TeeDataGridModel
     * @param AdminDivisionDividedModel
     * @param HttpServletRequest
     * @return TeeEasyuiDataGridJson
     * @date: 2019年3月12日下午4:27:16
     * @author: hoax
     */
    @ResponseBody
    @RequestMapping("/findListBypage.action")
    public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel,
            AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request) {
        // 返回前台json
        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson();
        // 接收查询集合 cList
        List<AdminDivisionDivided> cList = null;
        // bean对应的model cModels
        List<AdminDivisionDividedModel> cModels = new ArrayList<AdminDivisionDividedModel>();
        // 查询集合总数
        Long count = null;
        try {
            // 获取登录用户数据权限控制参数
            OrgCtrlInfoModel ctrlInfo = commonService.getOrgCtrlInfo(request);
            // 权限控制校验
            String baseLevelCode = ctrlInfo.getLevelCode();
            String baseAdminDivisionCode = ctrlInfo.getAdminDivisionCode();
            if (!ctrlInfo.getGradeAdministrator()) {
                // 不具备分级管理员权限，直接返回空结果
                json.setRows(null);
                json.setTotal(0L);
                return json;
            }
            // 设置数据权限控制参数
            adminDivisionDividedModel.setBaseAdminDivisionCode(baseAdminDivisionCode);
            adminDivisionDividedModel.setBaseLevelCode(baseLevelCode);
            cList = adminDivisionService.findBaseListByPage(tModel, adminDivisionDividedModel);
            count = adminDivisionService.findBaseListCount(adminDivisionDividedModel);
            if (cList != null && cList.size() > 0) {
                //获取机构层级字典表数据集
                List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("DEPT_LEVEL");
                //拼装modelList，用于返回列表展示
                for (AdminDivisionDivided adminDivisionDivided : cList) {
                    AdminDivisionDividedModel nodeDataModel = new AdminDivisionDividedModel();
                    BeanUtils.copyProperties(adminDivisionDivided, nodeDataModel);
                    
                    // 校验并设置字典表对应文本描述
                    for (Map<String, Object> code : codeList) {
                        if (nodeDataModel.getLevelCode().equals(code.get("codeNo").toString())) {
                            nodeDataModel.setLevelCodeStr(code.get("codeName").toString());
                            break;
                        }
                    }
                    cModels.add(nodeDataModel);
                }
            }
            json.setRows(cModels);
            json.setTotal(count);
        } catch (Exception e) {
            System.out.println("CaseCommonBaseController.findListBypage() " + e.getMessage());
        }
        return json;
    }

    /**
     * 打开行政区划信息编辑页面
     * 
     * @param AdminDivisionDividedModel
     * @param request
     * @param response
     * @return
     * @date: 2019年3月12日下午4:27:16
     * @author: hoax
     */
    @RequestMapping("/adminDivisionEditInput")
    public void adminDivisionEditInput(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            // 获取登录用户数据权限控制参数
            OrgCtrlInfoModel ctrlInfo = commonService.getOrgCtrlInfo(request);
            AdminDivisionDividedModel baseInfo = new AdminDivisionDividedModel();
            adminDivisionDividedModel.setBaseLevelCode(ctrlInfo.getLevelCode());
            adminDivisionDividedModel.setBaseAdminDivisionCode(ctrlInfo.getAdminDivisionCode());
            // 根据id查询强制措施信息
            if (adminDivisionDividedModel.getAdminDivisionCode() != null) {
                AdminDivisionDivided adminDivisionInfo = adminDivisionService
                        .getByAdminCode(adminDivisionDividedModel.getAdminDivisionCode());
                BeanUtils.copyProperties(adminDivisionInfo, baseInfo);
            }
            // 设置基础数据
            baseInfo.setBaseLevelCode(ctrlInfo.getLevelCode());
            baseInfo.setBaseAdminDivisionCode(ctrlInfo.getAdminDivisionCode());
            request.setAttribute("baseInfo", baseInfo);
            request.getRequestDispatcher("/supervise/Department/adminDivision_manage_input.jsp").forward(request,
                    response);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 通过code获取行政区划数据
     *
     * @param id
     * @param request
     * @return
     */

    @ResponseBody
    @RequestMapping("/getAdminDivisionByCode.action")
    public TeeJson getAdminDivisionByCode(String code, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        AdminDivisionDividedModel model = new AdminDivisionDividedModel();
        if (code != null && !"".equals(code)) {
            AdminDivisionDivided beanInfo = adminDivisionService.getByAdminCode(code);
            // 实体类-->model传值
            BeanUtils.copyProperties(beanInfo, model);
        }
        json.setRtData(model);
        json.setRtState(true);
        return json;
    }

    /**
     * 通过code获取下级行政区划数据
     *
     * @param code
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAdminDivisionListByCode.action")
    public JSONArray getAdminDivisionListByCode(AdminDivisionDividedModel adminDivisionDividedModel) {
        JSONArray json = new JSONArray();
        if (adminDivisionDividedModel.getBaseAdminDivisionCode() != null
                && !"".equals(adminDivisionDividedModel.getBaseAdminDivisionCode())) {
            List<AdminDivisionDivided> adminDivisions = adminDivisionService
                    .findChildAdminDivisionListByCode(adminDivisionDividedModel);
            // 判断结果是否为空
            if (adminDivisions != null) {
                // 循环生成jsonArrray
                for (AdminDivisionDivided adminDivision : adminDivisions) {
                    JSONObject object = new JSONObject();
                    object.put("code", adminDivision.getAdminDivisionCode());
                    object.put("name", adminDivision.getAdminDivisionName());
                    json.add(object);
                }
            }
        }
        return json;
    }

    /**
     * 保存法律法规调整信息
     * 
     * @param lawModel
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public TeeJson save(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        // 创建实例化实体类对象
        AdminDivisionDivided baseInfo = new AdminDivisionDivided();
        BeanUtils.copyProperties(adminDivisionDividedModel, baseInfo);

        if (adminDivisionDividedModel.getId() != null && !"".equals(adminDivisionDividedModel.getId())) {
            // 暂不考虑更新
        } else {
            // 数据处理
            // 生成区划全称
            baseInfo.setId(UUID.randomUUID().toString());
            baseInfo.setIsDelete(0);
            String fullName = "";
            if (baseInfo.getProvincialName() != null) {
                fullName = fullName + baseInfo.getProvincialName();
            }
            if (baseInfo.getCityName() != null) {
                fullName = fullName + baseInfo.getCityName();
            }
            if (baseInfo.getDistrictName() != null) {
                fullName = fullName + baseInfo.getDistrictName();
            }
            if (baseInfo.getStreetName() != null) {
                fullName = fullName + baseInfo.getStreetName();
            }
            baseInfo.setFullName(fullName);
            //
            Integer flag = adminDivisionService.save(baseInfo);
            //
            if(flag == 0){
                TeeSysCode newSysCode = adminDivisionService.buildAdminDivisionCode(baseInfo);
                sysCodeServ.addUpdatePara(newSysCode, request);
            }
            // 插入人民政府信息
            adminDivisionService.saveNewGovInfo(baseInfo);
        }
        json.setRtState(true);
        return json;
    }

    /**
     * 
     * 
     * @param AdminDivisionDividedModel
     * @param request
     * @param response
     * @return
     * @date: 2019年3月12日下午4:27:16
     * @author: hoax
     */
    @RequestMapping("/openAccountInput")
    public void openAccountInput(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {

            if (adminDivisionDividedModel.getAdminDivisionCode() != null) {
                //确认传递了行政区划代码参数
                AdminDivisionDivided result = adminDivisionService.getByAdminCode(adminDivisionDividedModel.getAdminDivisionCode());
                if (result != null) {
                    //确认该行政代码存在
                    request.setAttribute("baseInfo", adminDivisionDividedModel);
                    request.getRequestDispatcher("/supervise/Department/adminDivision_account_input.jsp").forward(request, response);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    /**
     * 分配账号
     * 
     * @param adminDivisionDividedModel
     * @return
     * @date:2019年3月9日下午4:25:38
     * @author:yxy
     */
    @ResponseBody
    @RequestMapping("saveAccount")
    public TeeJson saveAccount(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            // tblDepartmentDao.findOrderLvGovByAdminDivisionCode(queryModel);
            Integer flag = adminDivisionService.saveUserAcount(adminDivisionDividedModel);
            json.setRtState(true);
            json.setRtData(flag);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            json.setRtData(-9);
            System.out.println("tblOfficialsController.saveUser" + e.getMessage());
        }
        return json;
    }
    
    /**
     * 重置帐号密码
     * 
     * @param adminDivisionDividedModel
     * @return
     * @date:2019年3月9日下午4:25:38
     * @author:yxy
     */
    @ResponseBody
    @RequestMapping("reSetPassword")
    public TeeJson reSetPassword(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            // tblDepartmentDao.findOrderLvGovByAdminDivisionCode(queryModel);
            Integer flag = adminDivisionService.reSetUserPassword(adminDivisionDividedModel);
            json.setRtState(true);
            json.setRtData(flag);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            json.setRtData(-9);
            System.out.println("tblOfficialsController.saveUser" + e.getMessage());
        }
        return json;
    }
    
    /**
     * 收回帐号
     * 
     * @param adminDivisionDividedModel
     * @return
     * @date:2019年3月9日下午4:25:38
     * @author:yxy
     */
    @ResponseBody
    @RequestMapping("releaseAccount")
    public TeeJson releaseAccount(AdminDivisionDividedModel adminDivisionDividedModel, HttpServletRequest request) {
        TeeJson json = new TeeJson();
        try {
            // tblDepartmentDao.findOrderLvGovByAdminDivisionCode(queryModel);
            Integer flag = adminDivisionService.releaseAccount(adminDivisionDividedModel, request);
            json.setRtState(true);
            json.setRtData(flag);
        } catch (Exception e) {
            // TODO: handle exception
            json.setRtState(false);
            json.setRtData(-9);
            System.out.println("tblOfficialsController.saveUser" + e.getMessage());
        }
        return json;
    }
    
    /**
     * 执法机关综合查询  查询部门地区
     * @param request
     * @return
     * @date:2019年3月27日下午5:37:00
     * @author:yxy
     */
    @SuppressWarnings("rawtypes")
	@ResponseBody
    @RequestMapping("/getAreaSearch.action")
    public List<Map> getAreaSearch(HttpServletRequest request){
        List<Map> codeList = null;
        try {
            OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
            codeList = adminDivisionService.listAreaSearch(orgCtrl);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("AdminDivisionManageColltroller.getAreaSearch"+e.getMessage());
        }
        
        return codeList;
    }
    
    /**
     * 执法机关综合查询 部门层级根据部门地区联动
     * @param request
     * @return
     * @date:2019年3月28日上午9:58:04
     * @author:yxy
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping("/getAreaSearchLevel.action")
    public TeeJson getAreaSearchLevel(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<Map> maps = null;
        OrgCtrlInfoModel orgCtrl = commonService.getOrgCtrlInfo(request);
		try {
			Map codeMap = new HashMap();
			//通过用户选择的地区代码 获取部门层级
//			if(!TeeUtility.isNullorEmpty(id)){
//				//获取最大级部门层级
//				//Integer minLevel = Integer.parseInt(orgCtrl.getLevelCode());
//				Integer minLevel = 0500;
//				maps = adminDivisionService.AreaToLevel(id);
//				for (Map map : maps) {
//					Integer level = Integer.parseInt(map.toString().substring(4, map.toString().length()-1));
//					if(minLevel>level){
//						minLevel = level;
//					}
//				}
//			}else{
				String level = orgCtrl.getLevelCode();
					codeMap.put("id",level);
					codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", level));
//			}
			
			json.setRtData(codeMap);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("AdminIDivisionManageController.getAreaSearchLevel "+e.getMessage());
		}
		return json;
	}
}
