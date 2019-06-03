package com.beidasoft.zfjd.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.common.model.OrgCtrlInfoModel;
import com.beidasoft.zfjd.system.bean.AdminDivisionDivided;
import com.beidasoft.zfjd.system.model.AdminDivisionDividedModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 行政区划管理信息DAO类
 */
@Repository
public class AdminDivisionDividedDao extends TeeBaseDao<AdminDivisionDivided> {

    /**
     * 
     * @Description: 分页查询强制案件符合条件总数（对填报）
     *
     * @param: start
     * @param: length
     *  @param: adminDivisionDividedModel
     * @return：符合查询条件的案件数量
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public List<AdminDivisionDivided> selectBaseListByPage(int start, int length,
            AdminDivisionDividedModel adminDivisionDividedModel) {
        String hql = "from AdminDivisionDivided where ";
        
        boolean flag = false;
        if(adminDivisionDividedModel.getDistrictCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getDistrictCode())){
        	flag = true;
        	hql = hql + " districtCode = '" + adminDivisionDividedModel.getDistrictCode() + "' ";
        }
        if(adminDivisionDividedModel.getCityCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getCityCode())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " cityCode = '" + adminDivisionDividedModel.getCityCode() + "' ";
        }
        if(adminDivisionDividedModel.getProvincialCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getProvincialCode())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " provincialCode = '" + adminDivisionDividedModel.getProvincialCode() + "' ";
        }
        if(adminDivisionDividedModel.getAdminDivisionName() != null 
        		&& !"".equals(adminDivisionDividedModel.getAdminDivisionName())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " adminDivisionName like '%" + adminDivisionDividedModel.getAdminDivisionName() + "%' ";
        }
        if(adminDivisionDividedModel.getAdminDivisionCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getAdminDivisionCode())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " adminDivisionCode like '%" + adminDivisionDividedModel.getAdminDivisionCode() + "%' ";
        }
        String baseLevelCode = adminDivisionDividedModel.getBaseLevelCode();
        String baseAdminDivisionCode = adminDivisionDividedModel.getBaseAdminDivisionCode();
        if (baseLevelCode != null && !"".equals(baseLevelCode)) {
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
            switch (baseLevelCode) {
                case "0100": {
                    break;
                }
                case "0200":{
                    hql = hql + " provincialCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0300":{
                    hql = hql + " cityCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0400":{
                    hql = hql + " districtCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0500":{
                    hql = hql + " streetCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                default: {
                    return null;
                }
            }
        } else {
            return null;
        }
        hql = hql+ " order by adminDivisionCode ";
        return pageFind(hql, start, length, null);
    }

    /**
     * 
     * @Description: 分页查询强制案件符合条件总数（对填报）
     *
     * @param: adminDivisionDividedModel
     * @return：符合查询条件的行政区划数量
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public long selectBaseListCount(AdminDivisionDividedModel adminDivisionDividedModel) {
        String hql = "select count(*) from AdminDivisionDivided where ";
        boolean flag = false;
        if(adminDivisionDividedModel.getDistrictCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getDistrictCode())){
        	flag = true;
        	hql = hql + " districtCode = '" + adminDivisionDividedModel.getDistrictCode() + "' ";
        }
        if(adminDivisionDividedModel.getCityCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getCityCode())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " cityCode = '" + adminDivisionDividedModel.getCityCode() + "' ";
        }
        if(adminDivisionDividedModel.getProvincialCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getProvincialCode())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " provincialCode = '" + adminDivisionDividedModel.getProvincialCode() + "' ";
        }
        if(adminDivisionDividedModel.getAdminDivisionName() != null 
        		&& !"".equals(adminDivisionDividedModel.getAdminDivisionName())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " adminDivisionName like '%" + adminDivisionDividedModel.getAdminDivisionName() + "%' ";
        }
        if(adminDivisionDividedModel.getAdminDivisionCode() != null 
        		&& !"".equals(adminDivisionDividedModel.getAdminDivisionCode())){
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
        	hql = hql + " adminDivisionCode like '%" + adminDivisionDividedModel.getAdminDivisionCode() + "%' ";
        }
        String baseLevelCode = adminDivisionDividedModel.getBaseLevelCode();
        String baseAdminDivisionCode = adminDivisionDividedModel.getBaseAdminDivisionCode();
        if (baseLevelCode != null && !"".equals(baseLevelCode)) {
        	if(flag){
        		hql = hql + " and ";
        	}
        	flag = true;
            switch (baseLevelCode) {
                case "0100": {
                    break;
                }
                case "0200":{
                    hql = hql + " provincialCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0300":{
                    hql = hql + " cityCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0400":{
                    hql = hql + " districtCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0500":{
                    hql = hql + " streetCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                default: {
                    return 0L;
                }
            }
        } else {
            return 0L;
        }
        return count(hql, null);
    }
    
    public AdminDivisionDivided getByAdminCode(String adminDivisionCode){
        String hql = "from AdminDivisionDivided where isDelete = 0 ";
        hql = hql + " and adminDivisionCode = '"+ adminDivisionCode +"' ";
        List<AdminDivisionDivided> result = find(hql, null);
        if(result !=null && result.size() > 0){
            return result.get(0);
        }else{
            return null;
        }
        
    }
    /**
     * 
     * @Description: 分页查询强制案件符合条件总数（对填报）
     *
     * @param: start
     * @param: length
     *  @param: adminDivisionDividedModel
     * @return：符合查询条件的案件数量
     * @throws：异常描述
     *
     * @author: hoax
     * @date: 2019年1月3日 下午3:58:29
     *
     */
    public List<AdminDivisionDivided> findChildAdminDivisionListByCode(AdminDivisionDividedModel adminDivisionDividedModel) {
        String hql = "from AdminDivisionDivided where ";
        String baseLevelCode = adminDivisionDividedModel.getBaseLevelCode();
        String baseAdminDivisionCode = adminDivisionDividedModel.getBaseAdminDivisionCode();
        if (baseLevelCode != null && !"".equals(baseLevelCode)) {
            switch (baseLevelCode) {
                case "0100": {
                    hql = hql + " levelCode = '0200' ";
                    break;
                }
                case "0200":{
                    hql = hql + " levelCode = '0300' ";
                    hql = hql + " and provincialCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0300":{
                    hql = hql + " levelCode = '0400' ";
                    hql = hql + "and cityCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0400":{
                    hql = hql + " levelCode = '0500' ";
                    hql = hql + "and districtCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                case "0500":{
                    hql = hql + " levelCode = '0600' ";
                    hql = hql + "and streetCode = '" + baseAdminDivisionCode + "' ";
                    break;
                }
                default: {
                    return null;
                }
            }
        } else {
            return null;
        }

        return find(hql,null);
    }
    
    public Map<String, Object> getNextSysDeptUuid(){
        String sql = "select DEPARTMENT_SEQ.nextval  id from dual";
        return   super.executeNativeUnique(sql, null);
    }
    
    /**
     * 执法机关综合查询 获取部门地区
     * @param firstResult
     * @param rows
     * @param orgCtrl
     * @return
     * @date:2019年3月27日下午5:47:50
     * @author:yxy
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> listAreaSearch(OrgCtrlInfoModel orgCtrl) {
    	String sql = "";
    	if(!TeeUtility.isNullorEmpty(orgCtrl.getLevelCode())){
    		if("0200".equals(orgCtrl.getLevelCode())){
    			sql += "  select admin_division_code id, full_name name from tbl_admin_division_divided where provincial_code  = '"+orgCtrl.getAdminDivisionCode()+"'";
    		}
    		if("0300".equals(orgCtrl.getLevelCode())){
    			sql += "  select admin_division_code id, full_name name from tbl_admin_division_divided where city_code  = '"+orgCtrl.getAdminDivisionCode()+"'";
    		}
    		if("0400".equals(orgCtrl.getLevelCode())){
    			sql += "  select admin_division_code id, full_name name from tbl_admin_division_divided where district_code  = '"+orgCtrl.getAdminDivisionCode()+"'";
    		}
    		if("0500".equals(orgCtrl.getLevelCode())){
    			sql += "  select admin_division_code id, full_name name from tbl_admin_division_divided where street_code  = '"+orgCtrl.getAdminDivisionCode()+"'";
    		}
    	}
    	List<Map> list = (List<Map>) super.executeNativeQuery(sql, null, 0, 10000);
        return list;
    }
    
    /**
     * 根据部门地区获取部门层级
     * @param orgCtrl
     * @return
     * @date:2019年3月28日上午11:02:53
     * @author:yxy
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> AreaToLevel(String  id) {
    	String sql = "";
    	String ids = id.replace(",", "','");
    	if(!TeeUtility.isNullorEmpty(id)){
    		sql += " select level_code id from tbl_admin_division_divided where admin_division_code in ('"+ids+"') ";
    	}
    	List<Map> list = (List<Map>) super.executeNativeQuery(sql, null, 0, 10000);
        return list;
    }
}
