package com.tianee.oa.subsys.project.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.service.TeeFileNetdiskService;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.subsys.project.bean.TeeProject;
import com.tianee.oa.subsys.project.bean.TeeProjectFile;
import com.tianee.oa.subsys.project.model.TeeProjectFileModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeStringUtil;

@Service
public class TeeProjectFileService extends TeeBaseService{

	@Autowired
	private TeeFileNetdiskService  fileNetDiskService;
	
	@Autowired
	private TeeSmsManager smsManager;
	
	
	
	/**
	 * 根据项目主键 获取文档数据
	 * @param request
	 * @return
	 */
	public TeeJson getFileData(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson  json=new TeeJson();
		//获取页面上传来的项目的主键
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		
		int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
		//获取文档目录
	    Map  m=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{projectId});
		String diskIds=TeeStringUtil.getString(m.get("PROJECT_FILE_NETDISK_IDS"));
		List data=new ArrayList();
		if(!("").equals(diskIds)){
			String[] diskIdArray=diskIds.split(",");
			Map map=null;
			TeeFileNetdisk disk=null;
			List<TeeProjectFile> fileList=null;
			List<TeeProjectFileModel> modelList=null;
			TeeProjectFileModel model=null;
		    for (String str : diskIdArray) {
				map=new HashMap();
				modelList=new ArrayList();
				disk=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,TeeStringUtil.getInteger(str, 0));
			    if(disk!=null){
			    	map.put("diskName",disk.getFileName());
			    	map.put("diskId", disk.getSid());
			    	//获取文件夹下的文件列表
			    	//fileList=simpleDaoSupport.executeQuery(" from TeeProjectFile f where f.file.fileParent.sid=? and f.projectId=?", new Object[]{disk.getSid(),projectId});
			    	fileList=simpleDaoSupport.executeQuery(" from TeeProjectFile f where f.taskId=?", new Object[]{taskId});
			    	for (TeeProjectFile file : fileList) {
						model=parseToModel(file);
						modelList.add(model);
					}
			    	
			    	map.put("fileList", modelList);
			    	//判断当前登陆人  是不是项目创建人  负责人  或者项目成员
			    	List list=simpleDaoSupport.executeNativeQuery(" select * from project p where p.uuid=?  and ((p.project_creater_id=?) or (p.project_manager_id=?) or (exists (select *  from project_member pm where pm.project_id=p.uuid and pm.member_id=? ))) ", new Object[]{projectId,loginUser.getUuid(),loginUser.getUuid(),loginUser.getUuid()},0,Integer.MAX_VALUE);
					
					if(list!=null&&list.size()>0){
						map.put("isCreaterOrManagerOrMember", 1);
					}else{
						map.put("isCreaterOrManagerOrMember", 0);
					}
			        
			    }
				data.add(map);
		    }
		}
		json.setRtData(data);
		json.setRtState(true);
		json.setRtMsg("数据获取成功！");
		return json;
	}

	
	/**
	 * 将实体类转换成model
	 * @param file
	 * @return
	 */
	public TeeProjectFileModel parseToModel(TeeProjectFile file) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		TeeProjectFileModel model=new TeeProjectFileModel();
		BeanUtils.copyProperties(file, model);
		if(file.getCreater()!=null){
			model.setCreaterId(file.getCreater().getUuid());
			model.setCreaterName(file.getCreater().getUserName());
		}
		if(file.getCreateTime()!=null){
			model.setCreateTimeStr(sdf.format(file.getCreateTime()));
		}
	    if(file.getFile()!=null){
	    	model.setFileId(file.getFile().getSid());
	    	model.setFileName(file.getFile().getFileName());
	    	model.setFileExt(file.getFile().getAttachemnt().getExt());
	    	model.setAttchId(file.getFile().getAttachemnt().getSid());
	    	model.setAttchName(file.getFile().getAttachemnt().getAttachmentName());
	    }
		return model;
	}


	/**
	 * 上传文件
	 * @param request
	 * @return
	 */
	public TeeJson upload(HttpServletRequest request) {
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    TeeJson json=new TeeJson();
	    //获取页面上传来的文件的主键
	    int fileId=TeeStringUtil.getInteger(request.getParameter("fileId"), 0);
	    //获取页面上传来的项目主键
	    String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
	    int taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
	    TeeFileNetdisk file=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,fileId);
	    TeeProjectFile projectFile=new TeeProjectFile();
	    projectFile.setCreater(loginUser);
	    projectFile.setCreateTime(new Date());
	    projectFile.setFile(file);
	    projectFile.setProjectId(projectId);
	    projectFile.setTaskId(taskId);
	    simpleDaoSupport.save(projectFile);
	    
	   json.setRtState(true);
	   json.setRtMsg("上传成功！");   
	   return json;
}


	/**
	 * 根据主键删除文档
	 * @param request
	 * @return
	 */
	public TeeJson delBySid(HttpServletRequest request) {
		TeeJson json=new TeeJson();
		//获取主键
		int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		if(sid>0){
			TeeProjectFile file=(TeeProjectFile) simpleDaoSupport.get(TeeProjectFile.class,sid);
			simpleDaoSupport.deleteByObj(file);
			json.setRtState(true);
			json.setRtMsg("删除成功！");
		}else{
			json.setRtState(false);
			json.setRtMsg("文档不存在！");
		}
		
		return json;
	}


	/**
	 * 根据diskId获取文档数据
	 * @param request
	 * @param dm
	 * @return
	 */
	public TeeEasyuiDataGridJson getFileListByDiskId(
			HttpServletRequest request, TeeDataGridModel dm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		TeeEasyuiDataGridJson json=new TeeEasyuiDataGridJson();
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		//获取页面上传来的diskId
		int diskId=TeeStringUtil.getInteger(request.getParameter("diskId"),0);
		String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
		
		long total=simpleDaoSupport.count(" select count(*) from TeeProjectFile f where f.projectId=?", new Object[]{projectId});
		json.setTotal(total);
		
		List<TeeProjectFile>result=simpleDaoSupport.pageFind("  from TeeProjectFile f where f.projectId=? ", (dm.getPage() - 1) * dm.getRows(), dm.getRows(),  new Object[]{projectId});
		
		List<TeeProjectFileModel> modelList=new ArrayList<TeeProjectFileModel>();
		TeeProjectFileModel model=null;
		for (TeeProjectFile file : result) {
			model=parseToModel(file);
			modelList.add(model);
		}
		
		json.setRows(modelList);
		return json;
	}


	
	/**
	 * 文档上传成功   给项目负责人  创建人    成员  观察者发送消息
	 * @param request
	 * @return
	 */
	public TeeJson sendMessage(HttpServletRequest request) {
	    TeeJson json=new TeeJson();
	    TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
	    
	    //获取页面上传来的项目主键
	    String projectId=TeeStringUtil.getString(request.getParameter("projectId"));
	    //获取文件夹的主键
	    int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
	    TeeFileNetdisk disk=(TeeFileNetdisk) simpleDaoSupport.get(TeeFileNetdisk.class,sid);
	    String diskName="";
	    if(disk!=null){
	    	diskName=disk.getFileName();
	    }	    
	  //获取项目的基本信息
		  Map map=simpleDaoSupport.executeNativeUnique(" select * from project where uuid=? ", new Object[]{projectId});
		  String projectName=TeeStringUtil.getString(map.get("PROJECT_NAME"));
		  int managerId=TeeStringUtil.getInteger(map.get("PROJECT_MANAGER_ID"), 0);
		  int createrId=TeeStringUtil.getInteger(map.get("PROJECT_CREATER_ID"), 0);
		  				
		  Set set=new HashSet();
		  set.add(createrId);
		  set.add(managerId);
		  //获取项成员
		  List<Map> memberList=simpleDaoSupport.executeNativeQuery(" select * from project_member where project_id=?", new Object[]{projectId}, 0, Integer.MAX_VALUE);
		  for (Map m : memberList){
		  	    set.add(TeeStringUtil.getInteger(m.get("member_id"), 0));
		  }
		  //获取项目观察者
		  List<Map> viewList=simpleDaoSupport.executeNativeQuery(" select * from project_view where project_id=?", new Object[]{projectId}, 0, Integer.MAX_VALUE);
		  for (Map m : viewList){
		  		set.add(TeeStringUtil.getInteger(m.get("view_id"), 0));
		  		     		  
		  }
		  String userListIds="";//项目创建人  项目成员  项目观察者的id字符串
		  for (Object obj : set) {
		  		int id=TeeStringUtil.getInteger(obj, 0);
		  		if(id!=0){
		  			userListIds+=id+",";
		  		}
		  }
		  if(userListIds.endsWith(",")){
		  		userListIds=userListIds.substring(0,userListIds.length()-1);
		  }
		    
		 // 发送消息
		Map requestData1 = new HashMap();
		requestData1.put("content", "项目“"+projectName+"”有新的项目文档已上传，请注意查收。");
		requestData1.put("userListIds", userListIds);
		requestData1.put("moduleNo", "060");
		requestData1.put("remindUrl","/system/subsys/project/projectfile/list.jsp?diskId="+sid+"&&diskName="+diskName+"&&projectId="+projectId);
		smsManager.sendSms(requestData1, loginUser);
	    
	    
	    json.setRtState(true);
		return json;
	}
	

}
