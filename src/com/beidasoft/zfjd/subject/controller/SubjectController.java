package com.beidasoft.zfjd.subject.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.common.service.CommonService;
import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.department.service.TblDepartmentService;
import com.beidasoft.zfjd.subject.bean.Subject;
import com.beidasoft.zfjd.subject.dao.SubjectDao;
import com.beidasoft.zfjd.subject.model.SubjectModel;
import com.beidasoft.zfjd.subject.service.SubjectService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("subjectCtrl")
public class SubjectController {
    
    @Autowired
    private SubjectService subjectService;
    /*@Autowired
    private SuperviseService superviseService;*/
    @Autowired
    private TblDepartmentService departmentService;
    @Autowired
    private CommonService commonservivse;
    @Autowired
    private SubjectDao subjectDao;
    
    //主体分页
    @ResponseBody
    @RequestMapping("/listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dm, SubjectModel subjectModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridjson = new TeeEasyuiDataGridJson();
        
        if(subjectModel.getIds() != null && !"".equals(subjectModel.getIds())) {
            subjectModel.setIds("'" + subjectModel.getIds().replace(",", "','") + "'" );
        }
        
        List<Subject> subjects = subjectService.listByPage(dm, subjectModel);;
        long total = subjectService.listCount(subjectModel);
        
        //执法系统
        List<Map<String, Object>> codeList = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("LAW_ENFORCEMENT_FIELD");
        //主体性质 nature
        List<Map<String, Object>> deptNature = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("SYSTEM_SUBJECT_NATURE");
        //主体层级 subLevel
        List<Map<String, Object>> deptLevel = TeeSysCodeManager.getChildSysCodeListByParentCodeNo("DEPT_LEVEL");

        List<SubjectModel> subjectModels = new ArrayList<SubjectModel>();
        for (Subject subject : subjects) {
            SubjectModel infoModel = new SubjectModel();
            BeanUtils.copyProperties(subject,infoModel);
            
            if(!TeeUtility.isNullorEmpty(infoModel.getOrgSys())){
            	for(Map<String, Object> code : codeList) {
                    if(infoModel.getOrgSys().equals(code.get("codeNo").toString())) {
                    	infoModel.setOrgSys(code.get("codeName").toString());
                        break;
                    }
                }
            }
            if(!TeeUtility.isNullorEmpty(infoModel.getNature())){
            	for(Map<String, Object> code : deptNature) {
                    if(infoModel.getNature().equals(code.get("codeNo").toString())) {
                    	infoModel.setNature(code.get("codeName").toString());
                        break;
                    }
                }
            }
            if(!TeeUtility.isNullorEmpty(infoModel.getSubLevel())){
            	for(Map<String, Object> code : deptLevel) {
                    if(infoModel.getSubLevel().equals(code.get("codeNo").toString())) {
                    	infoModel.setSubLevel(code.get("codeName").toString());
                        break;
                    }
                }
            }
            subjectModels.add(infoModel);
        }

        gridjson.setRows(subjectModels);
        gridjson.setTotal(total);
        
        return gridjson;
    }
    
  //组织分页
    @ResponseBody
    @RequestMapping("/listByPageOrg")
    public TeeEasyuiDataGridJson listByPageOrg(TeeDataGridModel dm, SubjectModel subjectModel, HttpServletRequest request) {
        TeeEasyuiDataGridJson gridjson = new TeeEasyuiDataGridJson();
        
        if(subjectModel.getIds() != null && !"".equals(subjectModel.getIds())) {
            subjectModel.setIds("'" + subjectModel.getIds().replace(",", "','") + "'" );
        }
        List<Subject> subjects = subjectService.listByPageOrg(dm, subjectModel);
        long total = subjectService.listCountOrg(subjectModel);
        
        List<SubjectModel> subjectModels = new ArrayList<SubjectModel>();
        for (Subject subject : subjects) {
			SubjectModel infoModel = new SubjectModel();
			BeanUtils.copyProperties(subject,infoModel);
			//infoModel.setParentName(subjectService.getNameById(subject.getParentId()));
			
			Subject sub = subjectService.getNameById(subject.getParentId());
			if(sub == null){
				infoModel.setParentName("");
			}else{
				infoModel.setParentName(sub.getSubName());
			}
			subjectModels.add(infoModel);
		}
        gridjson.setRows(subjectModels);
        gridjson.setTotal(total);
        
        return gridjson;
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
			subjectService.examine(id,orgCtrl);	
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.examine"+e.getMessage());
		}
		return json;
	}
    
    /**
	 * 保存
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(SubjectModel subjectModel){
		TeeJson json = new TeeJson();
		try {
			json = subjectService.save(subjectModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.save"+e.getMessage());
		}
		return json;
	}
	/**
	 * 根据委托主体查询执法系统
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/subOrgSys.action")
	public TeeJson subOrgSys(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			List<Subject> orgSys = subjectService.listByDeSys(0,10,id);
			String a = orgSys.toString();
			a = a.substring(1,a.length()-1);
			json.setRtData(a);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.subOrgSys"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 更新
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(SubjectModel subjectModel){
		TeeJson json = new TeeJson();
		try {
			json = subjectService.updateUser(subjectModel);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.update"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson dalete(String id){
		TeeJson json = new TeeJson();
		try {
			Subject subject = new Subject();
			subject = subjectService.getById(id);
			
			subject.setIsDelete(1);
			subject.setDeleteTime(new Date());
			subjectService.update(subject);	
			
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.delete"+e.getMessage());
		}
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
	    	subjectService.deletes(id);
	    	json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.deletes"+e.getMessage());
		}
		return null;
	}

	/**
	 * 根据id查询执法部门 权限控制
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeTempById.action")
	public List<TblDepartmentInfo> getSysCodeTempById(String q,String id,HttpServletRequest request){
		TblDepartmentModel departmentModel = new TblDepartmentModel();
		List<TblDepartmentInfo> codeList = null;
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			departmentModel.setName(q);
			departmentModel.setParentId(id);
			codeList = departmentService.listByDeQ(0,10,departmentModel,orgCtrl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeTempById"+e.getMessage());
		}
		return codeList;
	}
	
	/**
	 * 根据id查询执法部门 全部
	 * @param q
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月14日下午3:38:26
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeTempByIdAll.action")
	public List<TblDepartmentInfo> getSysCodeTempByIdAll(String q,String id,HttpServletRequest request){
		TblDepartmentModel departmentModel = new TblDepartmentModel();
		List<TblDepartmentInfo> codeList = null;
		try {
			departmentModel.setName(q);
			departmentModel.setParentId(id);
			codeList = departmentService.listByDe(0,10,departmentModel);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeTempByIdAll"+e.getMessage());
		}
		return codeList;
	}
	/**
	 * 根据id查询归属人民政府 权限控制
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeGovById.action")
	public List<TblDepartmentInfo> getSysCodeGovById(String q,String id,HttpServletRequest request){
		TblDepartmentModel departmentModel = new TblDepartmentModel();
		List<TblDepartmentInfo> codeList = null;
		try {
			OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			departmentModel.setName(q);
			departmentModel.setParentId(id);
			codeList = departmentService.listByGov(0,10,departmentModel,orgCtrl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeGovById"+e.getMessage());
		}
		return codeList;
	}
	
	/**
	 * 根据查询部门下的所属主体
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysOrgSub.action")
	public List<Subject> getSysOrgSub(String q,String id,HttpServletRequest request){
		SubjectModel subjectModel = new SubjectModel();
		List<Subject> codeList = null;
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			subjectModel.setSubName(q);
			subjectModel.setDepartmentCode(id);
			codeList = subjectService.listByOrgSub(0,10,subjectModel,orgCtrl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysOrgSub"+e.getMessage());
		}
		return codeList;
	}
	/**
	 * 查询行政区划
	 * @param q
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/getSysCode.action")
	public List<Map> getSysCode(String q,HttpServletRequest request){
		List<Map> orgSys = null;
		try {
			orgSys = departmentService.listBySysCode(0, 10,q);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCode"+e.getMessage());
		}
		return orgSys;
	}
	/**
	 * 根据ID查询行政区划 部门
	 * @param q
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping("/getSysCodeById.action")
	public List<Map> getSysCodeById(String q,String id,HttpServletRequest request){
		List<Map> orgSys = null;
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			orgSys = departmentService.listBySysCode2(0,10,q,id,orgCtrl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeById"+e.getMessage());
		}
		return orgSys;
	}
	
	/**
	 * 获取登录人部门名称
	 * @param request
	 * @return
	 * @date:2019年3月25日上午9:14:20
	 * @author:yxy
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping("getDeptName.action")
	public TeeJson getDeptName(HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
            TblDepartmentInfo departmentInfo = departmentService.getById(orgCtrl.getDepartId());
			Map codeMap = new HashMap();
			codeMap.put("id",orgCtrl.getDepartId());
			codeMap.put("name", departmentInfo.getName());
			codeMap.put("code", departmentInfo.getDepartmentCode());
			json.setRtData(codeMap);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.getDeptName() "+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 获取监督系统机构权限控制信息
	 * @param request
	 * @return
	 * @date:2019年3月13日下午4:29:40
	 * @author:yxy
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping("getOrgCtrl.action")
	public TeeJson getOrgCtrl(HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			Map codeMap = new HashMap();
			codeMap.put("id",orgCtrl.getAdminDivisionCode());
			codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("ADMINISTRAIVE_DIVISION", orgCtrl.getAdminDivisionCode()));
			codeMap.put("isAdmin", orgCtrl.getGradeAdministrator());
			json.setRtData(codeMap);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.getOrgCtrl() "+e.getMessage());
		}
		return json;
	}
	/**
	 * 关联查询执法系统
	 * @param q
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping("/getSysCodeOrg.action")
	public List<Map> getSysCodeOrg(String id,HttpServletRequest request){
				
				//获取执法系统code，逗号分隔形式01,02,03
				List<Map> orgSys = departmentService.listByOrg(0, 10, id);
				String a = (String) orgSys.get(0).get("ORG_SYS");
				List<Map> sysCodes = new ArrayList();
				if(!TeeUtility.isNullorEmpty(a)){
					String orgSysSplited [] = a.split(",");
					for(String code:orgSysSplited){
						Map codeMap = new HashMap();
						codeMap.put("id", code);
						codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", code));
						sysCodes.add(codeMap);
					}
				}
				return sysCodes;
		   		
	}
	
	/**
	 * 回显执法系统
	 * @param q
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping("/getSysCodeOrgById.action")
	public List<Map> getSysCodeOrgById(String id,String deptId,HttpServletRequest request){
		List<Map> sysCodes = new ArrayList();
		Map codeMap = new HashMap();
			if(!TeeUtility.isNullorEmpty(deptId)){
				TblDepartmentInfo departmentInfo = departmentService.getById(deptId);
				String syscodeString = departmentInfo.getOrgSys();
				String orgSysSplited [] = syscodeString.split(",");
					for(String code:orgSysSplited){
						codeMap.put("id", code);
						codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", code));
						sysCodes.add(codeMap);
						return sysCodes;
					}
			}else {
				codeMap.put("id", id);
				codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", id));
				sysCodes.add(codeMap);
				return sysCodes;
			}
			return sysCodes;
	}
	
	/**
	 * 执法人员查询委托主体名称
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeSub.action")
	public List<Subject> getSysCodeSub(String q,String subId,String id,HttpServletRequest request){
		SubjectModel subjectModel = new SubjectModel();
		List<Subject> codeList = null;
		try {
			subjectModel.setParentId(subId);
			subjectModel.setSubName(q);
			subjectModel.setId(id);
			codeList = subjectService.listByDe(0,10,subjectModel);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeSub"+e.getMessage());
		}
		return codeList;
	}
	/**
	 * 委托组织根据ID查询委托主体名称
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeSubById.action")
	public List<Subject> getSysCodeSubById(String q,String id,HttpServletRequest request){
		SubjectModel subjectModel = new SubjectModel();
		List<Subject> codeList = null;
		try {
            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
            	subjectModel.setParentId(id);
    			subjectModel.setSubName(q);
    			codeList = subjectService.listByDe2(0,10,subjectModel,orgCtrl);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeSubById"+e.getMessage());
		}
		
		return codeList;
	}
	
	/**
	 * 查询执法主体名称
	 * @param q
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSysCodeSubject.action")
	public List<Subject> getSysCodeSubject(String q,HttpServletRequest request){
		SubjectModel subjectModel = new SubjectModel();
		List<Subject> codeList = null;
		try {
			subjectModel.setSubName(q);
			codeList = 
					subjectService.listBySubject(0,10,subjectModel);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SubjectController.getSysCodeSubject"+e.getMessage());
		}
		return codeList;
	}
	
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
			TeeJson json = new TeeJson();
			try {
				Subject subject = subjectService.getById(id);
				
				SubjectModel subjectModel = new SubjectModel();
				BeanUtils.copyProperties(subject, subjectModel);
				
				json.setRtData(subjectModel);
				
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				json.setRtState(false);
				System.out.println("SubjectController.get() "+e.getMessage());
			}
			return json;
		}
	
		/**
		 * 根据地区转化
		 * @param id
		 * @return
		 * @date:2019年3月8日上午11:12:29
		 * @author:yxy
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@ResponseBody
		@RequestMapping("areaToLevel")
		public TeeJson areaToLevel(String id){
			TeeJson json = new TeeJson();
			try {
				List<Map> level = subjectService.areaToLevel(0, 10, id);
				String a = (String) level.get(0).get("LEVEL_CODE");
//				List<Map> sysCodes = new ArrayList();
				Map codeMap = new HashMap();
				codeMap.put("id", a);
				codeMap.put("name", TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", a));
//				sysCodes.add(codeMap);
				
				json.setRtData(codeMap);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				json.setRtState(false);
				System.out.println("SubjectController.areaToLevel() "+e.getMessage());
			}
			return json;

		}
		
	/**
	 * 执法主体管理分页 权限控制
	 * @param tModel
	 * @param cbModel
	 * @param request
	 * @return
	 * @date:2019年3月6日上午11:26:47
	 * @author:yxy
	 */
	@ResponseBody
    @RequestMapping("findSearchListBypage")
	public TeeEasyuiDataGridJson findSearchListBypage(TeeDataGridModel tModel, SubjectModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        // 接收查询集合 cList
	        List<Subject> cList = null; 
	        //bean对应的model cModels
	        List<SubjectModel> cModels = new ArrayList<SubjectModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = subjectService.findListByPageSearch(tModel, cbModel, orgCtrl);
	            count = subjectService.listSearchCount(cbModel, orgCtrl);
	            //定义model
	            SubjectModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SubjectModel();
	                    Subject tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //执法系统
	                    cModel.setOrgSys(TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_ENFORCEMENT_FIELD", cModel.getOrgSys()));
	                    //主体性质
	                    cModel.setNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_SUBJECT_NATURE", cModel.getNature()));
	                    //主体层级
	                    cModel.setSubLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("DEPT_LEVEL", cModel.getSubLevel()));
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("SubjectController.findSearchListBypage() " + e.getMessage());
	        }
	        return json;
	    }
	
	/**
	 * 委托组织管理分页  权限控制
	 * @param tModel
	 * @param cbModel
	 * @param request
	 * @return
	 * @date:2019年3月6日下午3:08:38
	 * @author:yxy
	 */
	@ResponseBody
    @RequestMapping("findListBypageOrg")
	public TeeEasyuiDataGridJson findListBypageOrg(TeeDataGridModel tModel, SubjectModel cbModel, HttpServletRequest request) {
			//返回前台json 
	        TeeEasyuiDataGridJson json = new TeeEasyuiDataGridJson(); 
	        //委托主体
	        if(cbModel.getIds() != null && !"".equals(cbModel.getIds())) {
	        	cbModel.setIds("'" + cbModel.getIds().replace(",", "','") + "'" );
	        }
	        // 接收查询集合 cList
	        List<Subject> cList = null; 
	        //bean对应的model cModels
	        List<SubjectModel> cModels = new ArrayList<SubjectModel>(); 
	        //查询集合总数
	        Long count = null;
	        try {
	            OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
	            cList = subjectService.findListByPageManOrg(tModel, cbModel, orgCtrl);
	            count = subjectService.listManOrgCount(cbModel, orgCtrl);
	            //定义model
	            SubjectModel cModel = null;
	            if (cList != null && cList.size() > 0) {
	                for (int i = 0; i < cList.size(); i++) {
	                    cModel = new SubjectModel();
	                    Subject tempBasic = cList.get(i);
	                    //将tempBasic赋值给cModel
	                    BeanUtils.copyProperties(tempBasic, cModel);
	                    //性质
	                    cModel.setEntrustNature(TeeSysCodeManager.getChildSysCodeNameCodeNo("SYSTEM_SUBJECT_NATURE", cModel.getEntrustNature()));
	                    //委托主体
	                    Subject sub = subjectService.getNameById(tempBasic.getParentId());
	        			if(sub == null){
	        				cModel.setParentName("");
	        			}else{
	        				cModel.setParentName(sub.getSubName());
	        			}
	        			//创建人
	        			if(!TeeUtility.isNullorEmpty(tempBasic.getCreateId())){
	        				Map<String, Object> nameMap = subjectDao.getuserName(tempBasic.getCreateId());
		        			String a = nameMap.toString();
		        			cModel.setCreateId(a.substring(6, a.length()-1));
	        			}
	        			//创建时间
	        			if(!TeeUtility.isNullorEmpty(tempBasic.getCreateTime())){
	        				String creString = tempBasic.getCreateTime().toString();
		        			String timeString = creString.substring(0, 10);
		        			cModel.setCreateTimeStr(timeString);
	        			}
	                    cModels.add(cModel);
	                }
	            }
	            
	            json.setRows(cModels);
	            json.setTotal(count);
	        } catch (Exception e) {
	            System.out.println("SubjectController.findListBypageOrg() " + e.getMessage());
	        }
	        return json;
	    }
	
	/**
	 * 账号分配
	 * @param subjectModel
	 * @param request
	 * @return
	 * @date:2019年3月19日上午8:58:59
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("saveUser")
	public TeeJson saveUser(SubjectModel subjectModel,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
	        OrgCtrlInfoModel orgCtrl = commonservivse.getOrgCtrlInfo(request);
			json = subjectService.saveUser(subjectModel,orgCtrl);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.saveUser"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 密码重置
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月19日上午8:57:00
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetPassword")
	public TeeJson resetPassword(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = subjectService.resetPassword(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.resetPassword"+e.getMessage());
		}
		return json;
	}
	
	/**
	 * 回收账号
	 * @param id
	 * @param request
	 * @return
	 * @date:2019年3月19日上午8:56:55
	 * @author:yxy
	 */
	@ResponseBody
	@RequestMapping("resetUser")
	public TeeJson resetUser(String id,HttpServletRequest request){
		TeeJson json = new TeeJson();
		try {
			json = subjectService.resetUser(id);
			json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			json.setRtState(false);
			System.out.println("SubjectController.resetUser"+e.getMessage());
		}
		return json;
	}
}
