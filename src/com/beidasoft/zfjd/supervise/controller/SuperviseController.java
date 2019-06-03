package com.beidasoft.zfjd.supervise.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.supervise.bean.Supervise;
import com.beidasoft.zfjd.supervise.model.SuperviseModel;
import com.beidasoft.zfjd.supervise.service.SuperviseService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.general.service.TeeSysCodeService;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

@Controller
@RequestMapping("SuperviseController")
public class SuperviseController {

	
	@Autowired
	private SuperviseService superviseService;
	@Autowired
	private TeeSysCodeService sysCodeService;
	@Autowired
    private CommonService commonservivse;
	
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(SuperviseModel superviseModel){
		TeeJson json = new TeeJson();
		try {
			//获取当前登录人
			TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
			//创建实例化实体类对象
			Supervise supervise = new Supervise();
			BeanUtils.copyProperties(superviseModel, supervise);
			
			supervise.setId(UUID.randomUUID().toString());
			supervise.setCreateTime(new Date());
			supervise.setCreatorId(user.getUserId());
			supervise.setCreatorName(user.getUserName());
			superviseService.save(supervise);

			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.save"+e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(SuperviseModel superviseModel){
		TeeJson json = new TeeJson();
		try {
			//获取当前登录人
			TeePerson user = (TeePerson) TeeRequestInfoContext.getRequestInfo().getSession().get(TeeConst.LOGIN_USER);
			
			Supervise supervise = superviseService.getById(superviseModel.getId());
			
			//处理创建日期审核日期删除日期
			if(!TeeUtility.isNullorEmpty(supervise.getCreateTime())){
				superviseModel.setCreateTime(supervise.getCreateTime());
			}
			if(!TeeUtility.isNullorEmpty(supervise.getExamineTime())){
				superviseModel.setExamineTime(supervise.getExamineTime());
			}
			if(!TeeUtility.isNullorEmpty(supervise.getDisExamineTime())){
				superviseModel.setDisExamineTime(supervise.getDisExamineTime());
			}
			if(!TeeUtility.isNullorEmpty(supervise.getDeleteTime())){
				superviseModel.setDeleteTime(supervise.getDeleteTime());
			}
			if(!TeeUtility.isNullorEmpty(supervise.getCreatorId())){
				superviseModel.setCreatorId(supervise.getCreatorId());
			}
			if(!TeeUtility.isNullorEmpty(supervise.getCreatorName())){
				superviseModel.setCreatorName(supervise.getCreatorName());
			}
			
			BeanUtils.copyProperties(superviseModel, supervise);

			supervise.setCreateTime(superviseModel.getCreateTime());
			supervise.setExamineTime(superviseModel.getExamineTime());
			supervise.setDisExamineTime(superviseModel.getDisExamineTime());
			supervise.setDeleteTime(superviseModel.getDeleteTime());
			supervise.setCreatorId(superviseModel.getCreatorId());
			supervise.setCreatorName(superviseModel.getCreatorName());
			supervise.setUpdateTime(new Date());
			supervise.setUpdatePersonId(user.getUserId());
			superviseService.update(supervise);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.update"+e.getMessage());
		}
		return json;
	}

	/**
	 * 审核
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("examine")
	public TeeJson examine(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			json = superviseService.examine(id,orgCtrl);	
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.examine"+e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(String id){		
		TeeJson json = new TeeJson();
		try {
			//创建实例化实体类对象
			Supervise supervise = new Supervise();
			supervise = superviseService.getById(id);
			
			supervise.setIsDelete(1);
			supervise.setDeleteTime(new Date());
			superviseService.update(supervise);	
			
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.delete"+e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
		TeeJson json = new TeeJson();
		try {
			Supervise supervise = superviseService.getById(id);
			
			SuperviseModel superviseModel = new SuperviseModel();
			BeanUtils.copyProperties(supervise, superviseModel);
			
			json.setRtData(superviseModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.get"+e.getMessage());
		}
		return json;
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
		TeeJson json = new TeeJson();
		try {
			String id = request.getParameter("ids");
//	    	String id = "2bcc6c85-ddd2-445c-b3bc-b7b9cad95eea,707ef003-8f68-43dd-9a93-eb13c8313c24";
	    	json = new TeeJson();
	    	superviseService.deletes(id);
	    	json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.deletes"+e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,SuperviseModel superviseModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		long total = superviseService.getTotal(superviseModel);
		
		//部门地区
        List<Map<String, Object>> administrativeDivision = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("ADMINISTRAIVE_DIVISION");
		
		List<SuperviseModel> modelList = new ArrayList();
		List<Supervise> supervises = superviseService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				superviseModel);
		for (Supervise supervise : supervises) {
			SuperviseModel infoModel = new SuperviseModel();
			BeanUtils.copyProperties(supervise,infoModel);
			
			if(!TeeUtility.isNullorEmpty(infoModel.getAdministrativeDivision())){
            	for(Map<String, Object> code : administrativeDivision) {
                    if(infoModel.getAdministrativeDivision().equals(code.get("codeNo").toString())) {
                    	infoModel.setAdministrativeDivision(code.get("codeName").toString());
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
	 * 根据ID查询监督部门名称 权限控制
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeTempById.action")
	public List<Supervise> getSysCodeTempById(String q,String id,HttpServletRequest request){
		SuperviseModel superviseModel = new SuperviseModel();
		List<Supervise> codeList = null;
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			superviseModel.setName(q);
			superviseModel.setParentId(id);
			codeList = superviseService.listByDe(0,10,superviseModel,orgCtrl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SuperviseController.getSysCodeTempById"+e.getMessage());
		}
		return codeList;
	}
	
	/**
	 * 根据ID查询监督部门名称 全部
	 * @param q
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月14日下午3:43:52
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeTempByIdAll.action")
	public List<Supervise> getSysCodeTempByIdAll(String q,String id,HttpServletRequest request){
		SuperviseModel superviseModel = new SuperviseModel();
		List<Supervise> codeList = null;
		try {
			superviseModel.setName(q);
			superviseModel.setParentId(id);
			codeList = superviseService.listByDeAll(0,10,superviseModel);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SuperviseController.getSysCodeTempByIdAll"+e.getMessage());
		}
		return codeList;
	}
	/**
	 * 监督部门查询 界面获取数据
	 */
	@ResponseBody
	@RequestMapping("getSupDeptById")
	public TeeJson getSupDeptById(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
        try {
            SuperviseModel superviseModel = superviseService.getSupDeptById(id);
            json.setRtData(superviseModel);
            json.setRtState(true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            json.setRtState(false);
            e.printStackTrace();
        }
        return json;
	}
	/**
	 * 监督部门查询 进行权限控制
	 */
	@ResponseBody
    @RequestMapping("findSearchListBypage")
	public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, SuperviseModel cbModel, HttpServletRequest request) {
	      //返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<Supervise> cList = null; 
	        //bean对应的model cModels
	        List<SuperviseModel> cModels = new ArrayList<SuperviseModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = superviseService.findListByPageSearch(tModel, cbModel, orgCtrl);
	            count = superviseService.listSearchCount(cbModel, orgCtrl);
	            //定义model
	            SuperviseModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SuperviseModel();
	                    Supervise tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //部门层级
	                    cModel.setDeptLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", cModel.getDeptLevel()));
	                    //获取部门地区代码值
	                    cModel.setAdministrativeDivision(TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", cModel.getAdministrativeDivision()));
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("SuperviseController.findSearchListBypage() " + e.getMessage());
	        }
	        return json;
	    }
	/**
	 * 监督部门管理，进行权限控制
	 */
	@ResponseBody
    @RequestMapping("findListBypage")
	public TeeEasyuiDataGridJson findListBypage(TeeDataGridModel tModel, SuperviseModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<Supervise> cList = null; 
	        //bean对应的model cModels
	        List<SuperviseModel> cModels = new ArrayList<SuperviseModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = superviseService.findListByPage(tModel, cbModel, orgCtrl);
	            count = superviseService.listCount(cbModel, orgCtrl);
	            //定义model
	            SuperviseModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SuperviseModel();
	                    Supervise tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //部门层级
	                    cModel.setDeptLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", cModel.getDeptLevel()));
	                    //获取部门地区代码值
	                    cModel.setAdministrativeDivision(TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", cModel.getAdministrativeDivision()));
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("SuperviseController.findSearchListBypage() " + e.getMessage());
	        }
	        return json;
	    }
	
	/**
	 * 账号分配
	 * @param superviseModel
	 * @param request
	 * @return
	 * @date:2019年3月19日下午2:30:42
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("saveUser")
	public TeeJson saveUser(SuperviseModel superviseModel,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
	        OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			json = superviseService.saveUser(superviseModel,orgCtrl);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.saveUser"+e.getMessage());
		}
		return json;
	}
	/**
	 * 判断person用户名是否重复
	 * @param name
	 * @param request
	 * @return
	 * @date:2019年3月21日上午10:56:16
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("doubleUser")
	public TeeJson doubleUser(String name,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = superviseService.doubleUser(name);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.doubleUser"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 密码重置
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月19日下午2:30:18
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetPassword")
	public TeeJson resetPassword(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = superviseService.resetPassword(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.resetPassword"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 回收账号
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月19日下午2:30:02
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetUser")
	public TeeJson resetUser(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = superviseService.resetUser(id);
			json.setRtData(json);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SuperviseController.resetUser"+e.getMessage());
		}
		return json;
	}
}
