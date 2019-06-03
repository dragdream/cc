package com.tianee.oa.core.org.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.org.bean.TeeOrganization;
import com.tianee.oa.core.org.dao.TeeOrgDao;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.cache.TeeCacheManager;
import com.tianee.webframe.util.dynamic.TeeClassRunner;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeJsonUtil;
@Service
public class TeeOrgService  extends TeeBaseService {
 
	@Autowired
	@Qualifier("orgDao")
	
	private TeeOrgDao orgDao;
	
	/**
	 * 新增
	 * @param TeeOrganization
	 */
	public void add(TeeOrganization org){
		orgDao.addOrg(org);
	}
	
	/**
	 * 更新
	 * @param TeeOrganization
	 */
	public void update(TeeOrganization org){
		orgDao.updateOrg(org);
//		try {
//			TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "setUnitName", new Object[]{org.getUnitName()});
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try{
			 TeeClassRunner.exec("com.tianee.webframe.util.auth.TeeAuthUtil", "initAuthInfo", null);
		 }catch(ClassFormatError e){
			 e.printStackTrace();
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询 byUuid
	 * @param TeeOrganization
	 */
	public TeeOrganization selectOrgByUuid(String uuid) {
		
		TeeOrganization org = (TeeOrganization) orgDao.selectOrgById(uuid);
		return org;
	}
	
	
	/**
	 * 删除byUuid
	 * @param TeeOrganization
	 */
	public void delOrgByUuid(TeeOrganization org) {
		orgDao.delSysOrg(org);	
	}
	
	/**
	 * 查询 条件查询 
	 * @param TeeOrganization
	 */
	public List<TeeOrganization> selectOrg(String hql, Object[] values) {
		//System.out.println(hql);
		//System.out.println(values[0]);
		List<TeeOrganization> list = orgDao.selectOrg(hql, values);
		return list;
	}
	
	
	/**
	 * 检查部门JSON数据
	 * @return
	 */
	public boolean checkDeptJsonData(String md5){
		File file = new File(TeeSysProps.getRootPath()+"/../gen/dept.json");
		//如果不存在该文件，则返回false
		if(!file.exists()){
			return false;
		}
		
		return TeeFileUtility.computeFileMd5(TeeSysProps.getRootPath()+"/../gen/dept.json").equals(md5);
	}
	
	/**
	 * 生成部门JSON数据
	 * @return
	 */
	public void generateDeptJsonData(){
		TeeCacheManager.refreshRedisDept();
	}
	
	/**
	 * 检查人员JSON数据
	 * @return
	 */
	public boolean checkPersonJsonData(String md5){
		File file = new File(TeeSysProps.getRootPath()+"../gen/person.json");
		//如果不存在该文件，则返回false
		if(!file.exists()){
			return false;
		}
		return TeeFileUtility.computeFileMd5(TeeSysProps.getRootPath()+"/../gen/person.json").equals(md5);
	}
	
	/**
	 * 生成人员JSON数据
	 * @return
	 */
	public void generatePersonJsonData(){
		TeeCacheManager.refreshRedisPerson();
	}
	
	/**
	 * 检查角色JSON数据
	 * @return
	 */
	public boolean checkRoleJsonData(String md5){
		File file = new File(TeeSysProps.getRootPath()+"../gen/role.json");
		//如果不存在该文件，则返回false
		if(!file.exists()){
			return false;
		}
		return TeeFileUtility.computeFileMd5(TeeSysProps.getRootPath()+"/../gen/role.json").equals(md5);
	}
	
	/**
	 * 生成角色JSON数据
	 * @return
	 */
	public void generateRoleJsonData(){
		TeeCacheManager.refreshRedisRole();
	}
}
