package com.tianee.oa.core.base.imgbase.dao;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.tianee.oa.core.base.imgbase.bean.TeeImgBase;
import com.tianee.oa.core.base.imgbase.model.TeeImgBaseModel;
import com.tianee.oa.core.base.imgbase.utility.Base64Private;
import com.tianee.oa.core.base.imgbase.utility.ImageScale;
import com.tianee.oa.core.base.imgbase.utility.ImageUtil;
import com.tianee.oa.core.base.news.bean.TeeNews;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.bean.TeeUserRole;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("TeeImgBaseDao")
public class TeeImgBaseDao extends TeeBaseDao<TeeImgBase>{
	/*
	 * 保存
	 */
	public void addImgBase(TeeImgBase imgBase){
		save(imgBase);
	}
	
	public void updateImgBase(TeeImgBase imgBase) {
		update(imgBase);	
	}
	
	
	public TeeEasyuiDataGridJson getImgBaseList(TeeDataGridModel dm,Map requestDatas){
		TeePerson loginUser = (TeePerson) requestDatas.get(TeeConst.LOGIN_USER);
		TeeEasyuiDataGridJson datagird = new TeeEasyuiDataGridJson();
		List param = new ArrayList();
		String hql = "from TeeImgBase img where 1=1 ";
		
		if(dm.getSort()!=null){
			hql+=" order by "+dm.getSort()+" "+dm.getOrder();
		}else{
			hql+=" order by img.sid desc";
		}
		long total = countByList("select count(*) "+hql, param);
		List rows = new ArrayList();
		List<TeeImgBase> list =pageFindByList(hql, dm.getRows()*(dm.getPage()-1), dm.getRows(), param);
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		for(TeeImgBase imgBase:list){
			TeeImgBaseModel model = new TeeImgBaseModel();
			BeanUtils.copyProperties(imgBase, model);
			if(!TeeUtility.isNullorEmpty(imgBase.getCreateTime())){
				model.setCreateTimeDesc(sf.format(imgBase.getCreateTime().getTime()));
			}
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
			rows.add(model);
		}
		
		datagird.setRows(rows);
		datagird.setTotal(total);
		
		return datagird;
	}
	
	/**
	 * 获取当前登录人有权限看到的图片库
	 */
	public List<TeeImgBase> getImgBaseForPerson(TeePerson loginPerson){
		int uId = loginPerson.getUuid();
		int deptId = 0;
		int userRoleId = 0;
		if(loginPerson.getDept() != null){
			deptId= loginPerson.getDept().getUuid();
		}
		if(loginPerson.getUserRole() != null){
			userRoleId= loginPerson.getUserRole().getUuid();
		}
		//权限
		String hql = "select img  from TeeImgBase img where 1=1 ";
		hql = hql + " and (exists (select 1 from img.postDept dept where dept.uuid = "+deptId+")"
				+ " or exists (select 1 from img.postUser user where user.uuid = " + uId + ")"
				+ " or exists (select 1 from img.postUserRole role where role.uuid = " + userRoleId + ") or img.allPriv=1)";
		hql += " order by img.createTime desc" ;
		
		List<TeeImgBase> imgBaseList = find(hql, null);
		
		return imgBaseList;
	}

	/**
	 * 获取所有的文件夹
	 * @param imgDir
	 * @return
	 */
	public List<Map> getFileFolder(String imgDir) {
		
		List<Map> folderList = new ArrayList<Map>();
		File file = new File(new String(Base64Private.decode(imgDir.getBytes())));
		File[] fileList= file.listFiles();
		if(null!=fileList && fileList.length>0){
			for(File f : fileList){
				if(f.isDirectory() && !f.getName().equals("thumb_cache")){
					Map map = new HashMap();
					map.put("folderName",f.getName());
					String filePath = f.getAbsolutePath();
					//filePath = filePath.replace("\\", "/");
					map.put("folderPath", new String(Base64Private.encode(filePath.getBytes())));
					folderList.add(map);
				}
			}
		}
		return folderList;
	}
	
	
	/**
	 * 获取目录下的文件
	 * @param imgDir
	 * @return
	 */
	public List<Map<String,String>> getFileList(String imgDir,String sortType,int pageSize,int curPage) {
		List<Map<String,String>> folderList = new ArrayList<Map<String,String>>();
		List<Map<String,String>> returnList = new ArrayList<Map<String,String>>();
		File file = new File(new String(Base64Private.decode(imgDir.getBytes())));
		File[] fileList= file.listFiles();
		if(null!=fileList && fileList.length>0){
			for(File f : fileList){
				if(f.isFile()){
					String ext = f.getName().substring(f.getName().lastIndexOf("."),f.getName().length());
					if(isPic(ext)){
						Map map = new HashMap();
						map.put("fileName", f.getName());
						String filePath = f.getAbsolutePath();
						map.put("filePath", new String(Base64Private.encode(filePath.getBytes())));
						map.put("lastModifiedTime", String.valueOf(f.lastModified()));
						try {
							FileInputStream fis = new FileInputStream(f);
							long s = fis.available();
							map.put("fileSize", String.valueOf(s));
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						folderList.add(map);
					}
				}
			}
		    /**
		     * 排序
		     */
			if(!TeeUtility.isNullorEmpty(sortType)){
				Collections.sort(folderList, new MapComparator(sortType));
			}
		}
		if(curPage>0){
			for(int i=(curPage-1)*pageSize;i<curPage*pageSize && i<folderList.size();i++){
				File f = new File(new String(Base64Private.decode(folderList.get(i).get("filePath").getBytes())));
				String destPath = f.getParent()+File.separator+"thumb_cache"+File.separator+"thumb_"+f.getName();
				File destFile = new File(destPath);
				try {
					if(!destFile.exists()){
						ImageScale.resizeFix(new File(f.getAbsolutePath()), destFile, 400, 400);
					}
					folderList.get(i).put("thumbFileName",destFile.getName());
					folderList.get(i).put("thumbFilePath",new String(Base64Private.encode(destPath.getBytes())));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				returnList.add(folderList.get(i));
			}
			return returnList;
		}else{
			for(int i=0;i<folderList.size();i++){
				File f = new File(new String(Base64Private.decode(folderList.get(i).get("filePath").getBytes())));
				String destPath = f.getParent()+File.separator+"thumb_cache"+File.separator+"thumb_"+f.getName();
				File destFile = new File(destPath);
				try {
					if(!destFile.exists()){
						ImageScale.resizeFix(new File(f.getAbsolutePath()), destFile, 300, 300);
					}
					folderList.get(i).put("thumbFileName",destFile.getName());
					folderList.get(i).put("thumbFilePath",new String(Base64Private.encode(destPath.getBytes())));
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return folderList;
		}
	}
	
	/**
	 * 判断是否是图片
	 * @param ext
	 * @return
	 */
	public boolean isPic(String ext){
		ext = ext.toLowerCase();
		String[] exts = {".jpg",".bmp",".gif",".png",".jpeg"};
		for(String e:exts){
			if(ext.equals(e)){
				return true;
			}
		}
		return false;
	}
	
	 public static class MapComparator implements Comparator<Map<String, String>> {
		 
		 private String sortType;
		 
		 public MapComparator(String sortType){
			 this.sortType = sortType;
		 }
		  @Override
		  public int compare(Map<String, String> o1, Map<String, String> o2) {
		   String b1 = o1.get(sortType);
		   String b2 = o2.get(sortType);
		   if (b2 != null) {
		    return b1.compareTo(b2);
		   }
		   return 0;
		  }
		 }
	
	
	
	
}