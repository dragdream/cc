package com.tianee.oa.core.base.imgbase.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.dao.TeeAttachmentDao;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileNetdisk;
import com.tianee.oa.core.base.fileNetdisk.bean.TeeFileUserPriv;
import com.tianee.oa.core.base.imgbase.bean.TeeImgBase;
import com.tianee.oa.core.base.imgbase.dao.TeeImgBaseDao;
import com.tianee.oa.core.base.imgbase.model.TeeImgBaseModel;
import com.tianee.oa.core.base.imgbase.utility.Base64Private;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.core.org.dao.TeeUserRoleDao;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.oa.webframe.httpModel.TeeZTreeModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class TeeImgBaseService extends TeeBaseService{
	@Autowired
	TeeImgBaseDao imgBaseDao;
	@Autowired
	TeeDeptDao deptDao;
	@Autowired
	TeePersonDao personDao;
	@Autowired
	TeeUserRoleDao userRoleDao;
	
	@Autowired
	TeeAttachmentDao attachDao;
	
	public TeeImgBase addFile(TeeImgBase imgBase){
		imgBaseDao.save(imgBase);
		return imgBase;
	}
	
	public TeeImgBase addImgBase(TeeImgBaseModel imgBaseModel) throws Exception{
		TeeImgBase imgBase = new TeeImgBase();
		BeanUtils.copyProperties(imgBaseModel, imgBase);
		/*获取查看范围人员*/
		if(TeeUtility.isNullorEmpty(imgBaseModel.getPostDeptIds()) && TeeUtility.isNullorEmpty(imgBaseModel.getPostUserRoleIds()) && TeeUtility.isNullorEmpty(imgBaseModel.getPostUserIds())){
			imgBase.setAllPriv(1);
		}else{
			if(!TeeUtility.isNullorEmpty(imgBaseModel.getPostDeptIds())){//发布权限  ---部门
				List<TeeDepartment> listDept = deptDao.getDeptListByUuids(imgBaseModel.getPostDeptIds());
				imgBase.setPostDept(listDept);
			}
			if(!TeeUtility.isNullorEmpty(imgBaseModel.getPostUserIds())){//申发布权限- 人员
				List<TeePerson> listDept = personDao.getPersonByUuids(imgBaseModel.getPostUserIds());
				imgBase.setPostUser(listDept);
			}
			
			if(!TeeUtility.isNullorEmpty(imgBaseModel.getPostUserRoleIds())){//fa发布权限 -- 角色
				List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(imgBaseModel.getPostUserRoleIds());
				imgBase.setPostUserRole(listRole);
			}
		}
		Calendar cl = Calendar.getInstance();
		imgBase.setCreateTime(cl);
		addFile(imgBase);
		return imgBase;
	}
	
	public void delImgBase(TeeImgBaseModel model){
		TeeImgBase imgBase = new TeeImgBase();
		imgBase.setSid(model.getSid());
		imgBaseDao.deleteByObj(imgBase);
	}
	
	public void updateFile(TeeImgBase imgBase){
		imgBaseDao.update(imgBase);
	}
	
	public void editImgBase(TeeImgBaseModel imgBaseModel) throws Exception{
		TeeImgBase imgBase = (TeeImgBase) imgBaseDao.get(imgBaseModel.getSid());
		BeanUtils.copyProperties(imgBaseModel, imgBase);
		/*获取查看范围人员*/
		if(TeeUtility.isNullorEmpty(imgBaseModel.getPostDeptIds()) && TeeUtility.isNullorEmpty(imgBaseModel.getPostUserRoleIds()) && TeeUtility.isNullorEmpty(imgBaseModel.getPostUserIds())){
			imgBase.setAllPriv(1);
			imgBase.setPostDept(null);
			imgBase.setPostUser(null);
			imgBase.setPostUserRole(null);
		}else{
			if(!TeeUtility.isNullorEmpty(imgBaseModel.getPostDeptIds())){//发布权限  ---部门
				List<TeeDepartment> listDept = deptDao.getDeptListByUuids(imgBaseModel.getPostDeptIds());
				imgBase.setPostDept(listDept);
			}else{
				imgBase.setPostDept(null);
			}
			if(!TeeUtility.isNullorEmpty(imgBaseModel.getPostUserIds())){//申发布权限- 人员
				List<TeePerson> listDept = personDao.getPersonByUuids(imgBaseModel.getPostUserIds());
				imgBase.setPostUser(listDept);
			}else{
				imgBase.setPostUser(null);
			}
			
			if(!TeeUtility.isNullorEmpty(imgBaseModel.getPostUserRoleIds())){//fa发布权限 -- 角色
				List<TeeUserRole> listRole = userRoleDao.getPrivListByUuids(imgBaseModel.getPostUserRoleIds());
				imgBase.setPostUserRole(listRole);
			}else{
				imgBase.setPostUserRole(null);
			}
		}
		updateFile(imgBase);
	}
	
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,Map requestDatas){
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		datagird = imgBaseDao.getImgBaseList(dm, requestDatas);
		return datagird;
	}
	
	public TeeImgBaseModel getById(int sid){
		TeeImgBaseModel model = new TeeImgBaseModel();
		TeeImgBase imgBase = (TeeImgBase) imgBaseDao.get(sid);
		BeanUtils.copyProperties(imgBase, model);
		 String toRolesIds = "";//发布部门的id串
		 String toRolesNames = "";//发布部门的名字
		 String toDeptIds = "";//发布部门的id串
		 String toDeptNames = "";//发布部门的id串
		 String toUserNames = "";//发布人的名字
		 String toUserIds = "";//发布人的id串
		 List<TeePerson> pList = imgBase.getPostUser();
		 List<TeeDepartment> dList = imgBase.getPostDept();
		 List<TeeUserRole> rList = imgBase.getPostUserRole();
		 for (int i = 0; i < pList.size(); i++) {
			toUserIds = toUserIds +  (pList.get(i).getUuid() + ",");
			toUserNames = toUserNames + pList.get(i).getUserName() + ",";
		  }
		
		  for (int i = 0; i < dList.size(); i++) {
			toDeptIds = toDeptIds + dList.get(i).getUuid() + ",";
			toDeptNames=  toDeptNames + dList.get(i).getDeptName() + ",";
		  }
		
		 for (int i = 0; i < rList.size(); i++) {
			toRolesIds = toRolesIds + rList.get(i).getUuid() + ",";
			toRolesNames=  toRolesNames + rList.get(i).getRoleName() + ",";
		 }
		 model.setPostDeptIds(toDeptIds);
		 model.setPostDeptNames(toDeptNames);
		 model.setPostUserIds(toUserIds);
		 model.setPostUserNames(toUserNames);
		 model.setPostUserRoleIds(toRolesIds);
		 model.setPostUserRoleNames(toRolesNames);
		return model;
	}

	/**
	 * 获取图片库树
	 * @param person
	 * @param id
	 * @return
	 */
	public TeeJson getImgBaseTree(TeePerson person,String id,String isManager) {
		TeeJson json = new TeeJson();
		List<TeeZTreeModel> fileFolderTree = new ArrayList<TeeZTreeModel>();
		String iconSkin = TeeZTreeModel.FILE_FOLDER;
		if("".equals(id)){
			List<TeeImgBase> imgBaseList = imgBaseDao.getImgBaseForPerson(person);
			if (imgBaseList != null && imgBaseList.size() > 0) {
				for (TeeImgBase img : imgBaseList) {
					if(isManager.equals("1")){
						if(hasPriv(String.valueOf(person.getUuid()), img.getManagerUserIds()) || hasPriv(String.valueOf(person.getDept().getUuid()), img.getManagerDeptIds()) || hasPriv(String.valueOf(person.getUserRole().getUuid()), img.getManagerRoleIds())){
							String parentId = "0";
							TeeZTreeModel rooTztree = new TeeZTreeModel();
							rooTztree.setId(String.valueOf(img.getSid()));
							rooTztree.setName(String.valueOf(img.getImgDirName()));
							rooTztree.setOpen(true);
							rooTztree.setpId(parentId);
							rooTztree.setIconSkin(iconSkin);
							List<Map> fileNetdisks = imgBaseDao.getFileFolder(new String(Base64Private.encode(img.getImgDir().getBytes())));
							if(null!=fileNetdisks && fileNetdisks.size()>0){
								rooTztree.setParent(true);
							}
							fileFolderTree.add(rooTztree);
						}
					}else{
						String parentId = "0";
						TeeZTreeModel rooTztree = new TeeZTreeModel();
						rooTztree.setId(String.valueOf(img.getSid()));
						rooTztree.setName(String.valueOf(img.getImgDirName()));
						rooTztree.setOpen(true);
						rooTztree.setpId(parentId);
						rooTztree.setIconSkin(iconSkin);
						List<Map> fileNetdisks = imgBaseDao.getFileFolder(new String(Base64Private.encode(img.getImgDir().getBytes())));
						if(null!=fileNetdisks && fileNetdisks.size()>0){
							rooTztree.setParent(true);
						}
						fileFolderTree.add(rooTztree);
					}
				}
			}
		}else{
			//id = id.replaceAll("\\/","\\\\");
			if(!TeeUtility.isNullorEmpty(id) && !isNumeric(id)){
				List<Map> fileNetdisks = imgBaseDao.getFileFolder(id);
				if (fileNetdisks != null && fileNetdisks.size() > 0) {
					for (Map fileNetdisk : fileNetdisks) {
						TeeZTreeModel ztree = new TeeZTreeModel();
						if(isChildFolder(String.valueOf(fileNetdisk.get("folderPath")))){
							ztree.setParent(true);
						}
						ztree.setId(String.valueOf(fileNetdisk.get("folderPath")));
						ztree.setName(String.valueOf(fileNetdisk.get("folderName")));
						ztree.setOpen(true);
						ztree.setpId(id);
						ztree.setIconSkin(iconSkin);
						fileFolderTree.add(ztree);
					}
				}
				
			}else{
				TeeImgBase img = imgBaseDao.get(Integer.parseInt(id));
				List<Map> fileNetdisks = imgBaseDao.getFileFolder(new String(Base64Private.encode(img.getImgDir().getBytes())));
				if (fileNetdisks != null && fileNetdisks.size() > 0) {
					for (Map fileNetdisk : fileNetdisks) {
						TeeZTreeModel ztree = new TeeZTreeModel();
						if(isChildFolder(String.valueOf(fileNetdisk.get("folderPath")))){
							ztree.setParent(true);
						}
						ztree.setId(String.valueOf(fileNetdisk.get("folderPath")));
						ztree.setName(String.valueOf(fileNetdisk.get("folderName")));
						ztree.setOpen(true);
						ztree.setpId(String.valueOf(img.getSid()));
						ztree.setIconSkin(iconSkin);
						fileFolderTree.add(ztree);
					}
				}
			}
		}
		json.setRtState(true);
		json.setRtData(fileFolderTree);
		return json;
	}
	
	/**
	 * 判断是否有子文件夹
	 * @param path
	 * @return
	 */
	public boolean isChildFolder(String path){
		File file = new File(new String(Base64Private.decode(path.getBytes())));
		File[] fileList = file.listFiles();
		for(File f:fileList){
			if(f.isDirectory() && !f.getName().equals("thumb_cache")){
				return true;
			}
		}
		return false;
	}

	public TeeJson getPictureList(TeePerson person, String id,String sortType,int pageSize,int curPage) {
		TeeJson json = new TeeJson();
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if(!TeeUtility.isNullorEmpty(id) && !isNumeric(id)){
			list = imgBaseDao.getFileList(id,sortType,pageSize,curPage);
		}else{
			TeeImgBase img = imgBaseDao.get(Integer.parseInt(id));
			list = imgBaseDao.getFileList(new String(Base64Private.encode(img.getImgDir().getBytes())),sortType,pageSize,curPage);
		}
		json.setRtData(list);
		json.setRtState(true);
		json.setRtMsg("文件获取成功");
		return json;
	}
	
	public boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }

	
	public TeeJson dealAttach(String attachIds,String sid) {
		TeeJson json = new TeeJson();
		String path="";
		if(!TeeUtility.isNullorEmpty(sid) && !isNumeric(sid)){
			path = new String(Base64Private.decode(sid.getBytes()));
		}else{
			TeeImgBase img = imgBaseDao.get(Integer.parseInt(sid));
			path = img.getImgDir();
		}
		List<TeeAttachment> attachs = attachDao.getAttachmentsByIds(attachIds);
		FileInputStream input = null;
		FileOutputStream output = null;
		byte b[] = new byte[1024];
		int i = 0;
		try{
			for(TeeAttachment attach:attachs){
				String srcPath = attach.getAttachSpace().getSpacePath()+"/"+attach.getModel()+"/"+attach.getAttachmentPath()+"/"+attach.getAttachmentName();
				String destPath=path+"/"+attach.getFileName();
				input = new FileInputStream(srcPath);
				output = new FileOutputStream(destPath);
				while ((i = input.read(b)) != -1) {
					output.write(b, 0, i);
				}
				File file = new File(srcPath);
				if(file.exists()){
					file.delete();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				input.close();
				attachDao.deleteAttachs(attachIds);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		json.setRtState(true);
		json.setRtMsg("附件上传成功");
		return json;
	}

	/**
	 * 删除选中的文件
	 * @param paths
	 * @return
	 */
	public TeeJson deleteAll(String paths) {
		TeeJson json = new TeeJson();
		if(!TeeUtility.isNullorEmpty(paths)){
    		if(paths.endsWith(",")){
    			paths = paths.substring(0,paths.length()-1);
    		}
    		String[] pathList=paths.split(",");
    		for(String path:pathList){
    			File file = new File(new String(Base64Private.decode(path.getBytes())));
    			if(file.exists()){
    				file.delete();
    			}
    			String thumbPath=file.getParent()+"/thumb_cache/thumb_"+file.getName();
    			File thumbFile = new File(thumbPath);
    			if(thumbFile.exists()){
    				thumbFile.delete();
    			}
    			
    		}
    		json.setRtState(true);
    		json.setRtMsg("图片删除成功");
		}else{
			json.setRtState(false);
    		json.setRtMsg("图片删除失败");
		}
		return json;
	}
	
	/**
	 * 判断是否存在
	 * @param id
	 * @param ids
	 * @return
	 */
	public boolean hasPriv(String id,String ids){
		if(TeeUtility.isNullorEmpty(ids)){
			return false;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		String[] idList = ids.split(",");
		for(String t:idList){
			if(id.equals(t)){
				return true;
			}
		}
		return false;
		
	}

	/**
	 * 复制/剪切文件
	 * @param filePath
	 * @param paths
	 * @param type
	 */
	public void copyOrCutFiles(String filePath, String paths,String type) {
		if(paths.endsWith(",")){
			paths = paths.substring(0,paths.length());
		}
		String[] pathList = paths.split(",");
		FileInputStream input = null;
		FileOutputStream output = null;
		byte b[] = new byte[1024];
		int i = 0;
		try{
			for(String path:pathList){
				File srcFile = new File(new String(Base64Private.decode(path.getBytes())));
				String destPath=filePath+"/"+srcFile.getName();
				input = new FileInputStream(srcFile);
				output = new FileOutputStream(destPath);
				while ((i = input.read(b)) != -1) {
					output.write(b, 0, i);
				
				}
				output.flush();
				output.close();
				input.close();
			}
			/**
			 * 删除源文件
			 */
			
			if(type.equals("cut")){
				this.deleteAll(paths);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}
}
