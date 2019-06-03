package com.tianee.oa.core.org.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeeOrgTreeNote;
import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.model.TeeOrgTreeModel;
import com.tianee.oa.core.org.service.TeeDeptService;
import com.tianee.oa.core.org.service.TeeOrgCacheService;
import com.tianee.oa.core.org.service.TeeOrgService;
import com.tianee.oa.oaconst.TeeActionKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("/orgManager")
public class TeeOrgController {
	
	private static String treeData ;
	private static int loopNum = 1;
	@Autowired
	private TeeOrgService orgService;
	@Autowired
	private TeeDeptService deptService;
	@Autowired
	private TeeOrgCacheService orgCacheService;

	@RequestMapping(params = "/orgIndex.action")
	public String toIndex() {
		return "/system/common/organization/add.jsp";
	}
	@RequestMapping("/addOrg.action")
	public String addOrg(HttpServletRequest request, TeeOrganization org)
			throws Exception {
		String sb = "";
		try {
			orgService.add(org);
			sb = "{uuid:'" + org.getSid() + "'}";
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
	 * 新增或更新
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addOrUpdateOrg.action")
	public String addOrUpdateOrg(HttpServletRequest request, TeeOrganization org)
			throws Exception {
		String sb = "";
		try {
			if(org.getSid()<1){
				
				orgService.add(org);	
				
			}else{
				
				orgService.update(org);

			}
			sb = "{uuid:'" + org.getSid() + "'}";
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
	 * 判断是否已录入组织
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkOrg.action")
	public String checkOrg(HttpServletRequest request)
			throws Exception {
		String sb = "";
		boolean existFlag = false;
		
		TeeOrganization org = null;
		int[] obj = {0};
		try {
			List<TeeOrganization> list = orgService.selectOrg("from TeeOrganization org",null);
			if(list.size()>0){
				org = list.get(0);
				existFlag = true;
				//sb = "{uuid:'" + org.getUuid() + "',existFlag:" + existFlag + "}";
				TeeJsonUtil ju = new TeeJsonUtil();
				sb = ju.toJson(org);
			}else{
				sb = "{uuid:'',existFlag:" + existFlag + "}";
			}			
			//System.out.println(sb.toString());
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
	 * 查询 byId
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrgById.action")
	public String getOrgById(HttpServletRequest request) throws Exception {
		String uuid = request.getParameter("uuid");
		String data = "";
		try {
			if (!TeeUtility.isNullorEmpty(uuid)) {
				TeeOrganization org = orgService.selectOrgByUuid(uuid);
				TeeJsonUtil jsonUtil = new TeeJsonUtil();
				data = jsonUtil.obj2Json(org).toString();
			}

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
	 * 获取单位
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrg.action")
	public String getOrg(HttpServletRequest request) throws Exception {

		String data = "{}";
		try {
			List<TeeOrganization> list = orgService.selectOrg("from TeeOrganization org ",null);
			if(list.size() > 0){
				TeeOrganization org = list.get(0);
				TeeJsonUtil jsonUtil = new TeeJsonUtil();
				data = jsonUtil.obj2Json(org).toString();	
			}
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
	 * 获取组织树
	 * @param request
	 * @param org
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOrgTree.action")
	public String getOrgTree(HttpServletRequest request)
			throws Exception {
		String sb = "";
		//String data = "";
		String tree="";
		treeData = "";
		TeeOrganization org = null;
		String[] obj = {""};
		TeeJson json = new TeeJson();
		TeeOrgController zTree = new TeeOrgController();
		try {
			String hql = "from TeeDepartment dept order by dept.deptNo asc";			
		    List<TeeDepartment> listDept = deptService.selectDept(hql,null);
		    List<TeeOrgTreeModel> orgTree = new  ArrayList<TeeOrgTreeModel>();
		    for(TeeDepartment dept : listDept){
		    	String pid = "";
		    	if(dept.getDeptParent()!=null){
		    		pid= dept.getDeptParent().getUuid() + "";
		    	}
		    	TeeOrgTreeModel model = new TeeOrgTreeModel(dept.getUuid() + "",dept.getDeptName(),pid,"",false);
		    	orgTree.add(model);
		    }
			
			List<TeeOrganization> list = orgService.selectOrg("from TeeOrganization org where uuid != ?",obj);
			if(list.size()>0){
				org = list.get(0);
				TeeOrgTreeNote dto=zTree.getTreeNodeDto(org,orgTree);
				//System.out.println("+" + dto.getLevel() + "+"+dto.getName());
				treeData += "[{id:1,pId:0,name:'"+org.getUnitName()+"',uuid:'"+org.getSid()+"',puuid:'0',type:'org',open:true,isParent:true";
				treeData += "}";
				if(orgTree.size()>0){
					treeData += ",";
					zTree.printTreeNodes(dto,1);
				}  
			}
			treeData += "]";
			treeData = treeData.replaceAll("},]", "}]");
			//System.out.println(treeData);
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_OK);
			request.setAttribute(TeeActionKeys.RET_MSRG, "成功返回结果！");
			request.setAttribute(TeeActionKeys.RET_DATA, treeData);
		} catch (Exception ex) {
			request.setAttribute(TeeActionKeys.RET_STATE, TeeConst.RETURN_ERROR);
			request.setAttribute(TeeActionKeys.RET_MSRG, ex.getMessage());
			throw ex;
		}
		return "/inc/rtjson.jsp";
			
	}

	/**
	 * 递归输出子节点
	 */
	public void printTreeNodes(TeeOrgTreeNote rootNode,int pid){
		
		for(int i = 0;i< rootNode.getChilds().size();i++){
			loopNum++;
			int num = i + 1;
			TeeOrgTreeNote childNode = rootNode.getChilds().get(i);
			StringBuilder nodePrefix = new StringBuilder();
			for(int j=0; j<childNode.getLevel(); j++){
				nodePrefix.append("  ");
			}
			String puuid = "";
			if(childNode.getParent()!=null){
				puuid=childNode.getParent().getId();
			}
			int id = loopNum;
			treeData +="{id:"+id+",pId:"+pid+",name:'"+childNode.getName()+"',uuid:'"+childNode.getId()+"',puuid:'"+puuid+"',type:'dept',open:true,";
			if(childNode.isLeaf()==true){
				nodePrefix.append("-" + childNode.getLevel() + "-");
				treeData+="isParent:false},";
				
			} else {
				nodePrefix.append("+" + childNode.getLevel() + "+");
				treeData+="isParent:true},";
			}

			//System.out.println(nodePrefix + childNode.getName());
			printTreeNodes(childNode,id);
		}
		
	}
	
	public TeeOrgTreeNote getTreeNodeDto(TeeOrganization org, List<TeeOrgTreeModel> orgTree){

		TeeOrgTreeNote rootNode=new TeeOrgTreeNote();
		rootNode.setId(org.getSid() + "");
		rootNode.setLeaf(false);
		rootNode.setName(org.getUnitName());
		rootNode.setLevel(0);
	
		if(orgTree.size()>0){
			rootNode=constructTree(rootNode, orgTree, 0);
		}
		return rootNode;
	}
	
	public TeeOrgTreeNote constructTree(TeeOrgTreeNote rootNode, List<TeeOrgTreeModel> orgList, int rootLevel){
		List<TeeOrgTreeNote> childNodeList = new ArrayList<TeeOrgTreeNote>();
		//构造根节点
		String pid = "";
		if(rootLevel!=0){
			pid = rootNode.getId();
		}
		for(int i=0; i<orgList.size(); i++){
			TeeOrgTreeModel org = orgList.get(i);
			if(org.getPid().equals(pid)){
				TeeOrgTreeNote childNode = new TeeOrgTreeNote();
	
				childNode.setId(org.getUuid());
				childNode.setName(org.getDeptName());
				childNode.setParent(rootNode);
				//设置深度
				childNode.setLevel(rootLevel+1);
				childNodeList.add(childNode);
			}
		}
		//设置子节点
		rootNode.setChilds(childNodeList);
		//设置是否叶子节点
		if(childNodeList.size()==0){
			rootNode.setLeaf(true);
		} else {
			rootNode.setLeaf(false);
		}
		//递归构造子节点
		for(int j=0; j<childNodeList.size();j++){
			//进入子节点构造时深度+1
			constructTree(childNodeList.get(j), orgList, ++rootLevel);
			//递归调用返回时，构造子节点的兄弟节点，深度要和该子节点深度一样，因为之前加1，所以要减1
			--rootLevel;
		}
		return rootNode;
	}
	
	/**
	 * 检测生成的jsonData Md5有效性
	 * @param httpServletRequest
	 * @return
	 */
	/**
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkGenJsonDataMd5")
	public TeeJson checkGenJsonDataMd5(HttpServletRequest request){
		TeeJson json = new TeeJson();
		Map data = new HashMap();
		data.put("compress", "1");
//		data.put("dept", TeeFileUtility.computeFileMd5(TeeSysProps.getRootPath()+"/../gen/dept.json"));
//		data.put("person", TeeFileUtility.computeFileMd5(TeeSysProps.getRootPath()+"/../gen/person.json"));
//		data.put("role", TeeFileUtility.computeFileMd5(TeeSysProps.getRootPath()+"/../gen/role.json"));
		Connection dbConn = null;
		List<Map> datas;
		try {
			dbConn = TeeDbUtility.getConnection();
			DbUtils dbUtils = new DbUtils(dbConn);
			List<Map<String,Object>> list = dbUtils.queryToMapList("select version_,key_ from org_cache");
			for(Map<String,Object> dMap:list){
				data.put(dMap.get("key_"), dMap.get("version_")+"");
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			TeeDbUtility.closeConn(dbConn);
		}
		json.setRtData(data);
		return json;
	}
	
	/**
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/downloadJsonData")
	public void downloadJsonData(HttpServletRequest request,HttpServletResponse response){
		String type = request.getParameter("type");
		response.setContentType("application/octet-stream");
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Cache-Control", "maxage=3600");
		response.setHeader("Pragma", "public");
		
		try {
			byte b[] = orgCacheService.getBytes(type);
			OutputStream output = null;
			output = response.getOutputStream();
			
			response.setHeader("Accept-Length", b.length+"");
			response.setHeader("Content-Length", b.length+"");
			response.setHeader("Content-disposition", "attachment; filename=\""+type+".zip\"");
			output.write(b);
			output.flush();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
		}
		
	}
	
}
