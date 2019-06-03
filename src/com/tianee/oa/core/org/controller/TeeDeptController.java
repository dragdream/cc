package com.tianee.oa.core.org.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.model.TeeDepartmentModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeeOrgService;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/deptManager")
public class TeeDeptController {

	@Autowired
	private TeeDeptService deptService;
	@Autowired
	private TeeOrgService orgService;
	

	
	@RequestMapping("/addDept.action")
	public String addDept(HttpServletRequest request, TeeDepartmentModel model)
			throws Exception {
		String sb = "";
		try {
			deptService.addDeptService(model,request);
			sb = "{uuid:'" + model.getUuid() + "'}";
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, sb.toString());
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}
	
	/**
	 * 新增或添加
	 * @param request
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateDept.action")
	@ResponseBody
	public TeeJson addOrUpdateDept(HttpServletRequest request, 
		     HttpServletResponse response,TeeDepartmentModel model)
			throws Exception {
		TeeJson  json = null;
		int uuid = model.getUuid() ;
	
		model.setDeptParentId(TeeStringUtil.getInteger(request.getParameter("deptParent"),0));
		if(uuid==0){
			json = deptService.addDeptService(model,request);
		}else{
			json = deptService.updateDeptService(model,request);
		}
		
		//生成部门json数据
		orgService.generateDeptJsonData();
		return json;
	}
	
	/**
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeptById.action")
	public String getDeptById(HttpServletRequest request) throws Exception {
		String data = "";
		try {
			data = deptService.getDeptByUuid(request);
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, data);
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
	}

	
	/**
	 * 获取 组织部门树 ---无权限 syl
	 * 异步加载
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAsynOrgDeptTree")
	@ResponseBody
	public TeeJson getAsynOrgDeptTree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> orgDeptList = new ArrayList<TeeZTreeModel>(); 
             try {
				if (!TeeUtility.isNullorEmpty(id)) {
                   String[] idArray = id.split(";");
                   if(idArray.length >=2){
                	   Object[] values = {Integer.parseInt(idArray[0])};
                	   List<TeeDepartment> list = deptService.selectDept("from TeeDepartment  where deptParent.uuid = ? order by deptNo  ", values);
                	   for (int i = 0; i < list.size(); i++) {
                		   TeeDepartment dept = list.get(i);
                		   TeeZTreeModel ztree = new TeeZTreeModel();
   						   ztree.setId(dept.getUuid() + ";dept");
   						   ztree.setName(dept.getDeptName());
   						   ztree.setParent(false);
   						   ztree.setParent(false);
  						   if(deptService.checkExistsChild(dept)){
  							   ztree.setParent(true);
  						   }
   						   ztree.setpId(id);
   						   ztree.setIconSkin("deptNode");
   						   orgDeptList.add(ztree);
					   }
                   }else{//上级是单位获取所有是第一级的部门
                	   List<TeeDepartment> list = deptService.selectDept("from TeeDepartment  where deptParent is null  order by deptNo", null);
                	   for (int i = 0; i < list.size(); i++) {
                		   TeeDepartment dept = list.get(i);
                		   TeeZTreeModel ztree = new TeeZTreeModel();
   						   ztree.setId(dept.getUuid()+ ";dept");
   						   ztree.setName(dept.getDeptName());
   						   ztree.setParent(false);
  						   if(deptService.checkExistsChild(dept)){
  							   ztree.setParent(true);
  						   }
						   ztree.setpId(id);
   						   ztree.setIconSkin("deptNode");
   						   orgDeptList.add(ztree);
					   }
                   }
				} else {//获取单位
					List<TeeOrganization> list = orgService.selectOrg("from TeeOrganization org ", null);
					if (list.size() > 0) {
						TeeOrganization org = list.get(0);
						TeeZTreeModel ztree = new TeeZTreeModel();
						ztree.setId(org.getSid() + "");
						ztree.setName(org.getUnitName());
						ztree.setParent(true);
						ztree.setpId("0");
						ztree.setOpen(true);
						ztree.setIconSkin("pIconHome");//button pIcon01_ico_open
						orgDeptList.add(ztree);
					}
				}
				json.setRtMsg("获取成功");
				json.setRtData(orgDeptList);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
				json.setRtMsg(e.getMessage());
				json.setRtState(false);
			}

		return json;
	}
	/**
	 * 获取 组织部门树 ---无权限 
	 * 同步加载
	 * @author syl
	 * @date 2013-11-21
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrgDeptTree")
	@ResponseBody
	public TeeJson getOrgDeptTree(HttpServletRequest request) throws Exception {
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		TeeJson json = new TeeJson();
        try {
           	 json = deptService.getOrgDeptTree(request);
           	 json.setRtState(true);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			json.setRtMsg(e.getMessage());
			json.setRtState(false);
		}
		return json;
	}
	
	
	
	
	/**
	 * 获取部门 与 控件绑定  ---- 作为上级部门，并去除本部门及所有下级部门  
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeptTree.action")
	@ResponseBody
	public TeeJson getDeptTree(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		String uuid = request.getParameter("uuid");
		TeeOrgController zTree = new TeeOrgController();
		try {
			Object[] values = {uuid};
			String hql = "from TeeDepartment dept where uuid != " + uuid + "  order by dept.deptNo asc";	
			if(TeeUtility.isNullorEmpty(uuid)){//如果为空获取所有部门
				 hql = "from TeeDepartment dept  order by dept.deptNo asc";	
			}
			List<TeeDepartment>  listDept = deptService.selectDept(hql,null);//获取所有的部门
			
			List<TeeDepartment> listDept2 = new  ArrayList<TeeDepartment>();
			if(!TeeUtility.isNullorEmpty(uuid)){//获取所有下级节点和当前部门，
				
			   	listDept2 =deptService.getAllChildDeptByLevl(uuid);//获取下级所有部门，不包括本身
			    listDept.removeAll(listDept2);
				//listDeptUuid = deptService.getAllChildDeptUuid(uuid);
			}
		    List<TeeZTreeModel> deptTree = new  ArrayList<TeeZTreeModel>();
		 
		    for(TeeDepartment dept : listDept){
		    /*	if(listDeptUuid.contains(dept.getUuid())){
		    		continue;
		    	}*/
		    	String pid = "0";
		    	if(dept.getDeptParent()!=null){
		    		pid= dept.getDeptParent().getUuid() + "";
		    	}
		    	TeeZTreeModel ztree = new TeeZTreeModel();
		    	ztree.setId(dept.getUuid() + "");
		    	ztree.setName(dept.getDeptName());
		    	ztree.setOpen(true);
		    	ztree.setpId(pid);
				//ztree.setExtend1(dept.getDeptParentLevel());
		    	ztree.setParent(false);
		    	
		    	ztree.setIconSkin("deptNode");
			    deptTree.add(ztree);
		    }
		    json.setRtData(deptTree);
		 
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
			return json;
	}
	
	/**
	 * 获取部门 与 控件绑定  ---- 获取所有的
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeptTreeAll.action")
	@ResponseBody
	public TeeJson getDeptTreeAll(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		
		try {
		
			json = deptService.getDeptTreeAll();
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		return json;
	}
	
	
	/**
	 * 删除当前部门已经下级部门
	 * @author syl
	 * @date 2013-11-17
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteDeptAndSubDept")
	@ResponseBody
	public TeeJson deleteDeptAndSubDept(HttpServletRequest request)
			throws Exception {
		TeeJson json = new TeeJson();
		int id = TeeStringUtil.getInteger(request.getParameter("id"), 0);
		try {
			if(id > 0){
				json = deptService.deleteDeptAndSubDept(id,request);
			}
			
		} catch (Exception ex) {
			json.setRtState(false);
			json.setRtMsg(ex.getMessage());
			throw ex;
		}
		json.setRtState(true);
		
		//生成部门json数据
		orgService.generateDeptJsonData();
		return json;
	}
	
	
	
	/**
	 * 根据部门主键  获取部门对象
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeptByUuid.action")
	@ResponseBody
	public TeeJson getDeptByUuid(HttpServletRequest request) throws Exception {
		
		return  deptService.getDeptByUid(request);
	}

	
	
	public void setDeptService(TeeDeptService deptService) {
		this.deptService = deptService;
	}

	public void setOrgService(TeeOrgService orgService) {
		this.orgService = orgService;
	}

	
}
