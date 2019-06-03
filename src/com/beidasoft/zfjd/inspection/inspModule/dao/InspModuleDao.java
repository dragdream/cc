package com.beidasoft.zfjd.inspection.inspModule.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.beidasoft.zfjd.inspection.inspModule.model.InspModuleModel;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.str.TeeUtility;

/**
 * 职权基础信息表DAO类
 */
@Repository
public class InspModuleDao extends TeeBaseDao<InspModule> {

//    @Autowired
//    private TeeDeptDao dao;

    public List<InspModule> listByPage(int firstResult, int rows, InspModuleModel queryModel) {
    	//权限控制，只有主管理员可以获取数据
    	if(queryModel.getCtrlType() == null || !"10".equals(queryModel.getCtrlType())){
    		return null;
    	}
        String hql = " from InspModule i where isDelete = 0 ";
        // String hql = " select t.*, b.name, s.* from tbl_insp_module t left
        // join TBL_BASE_ORGANIZATION b on t.organization_id = b.id left join
        // tbl_sys_bussiness_relation s on t.subject_id = s.id where t.is_delete
        // = 0 ";
        // System.out.println(hql);
        if (!TeeUtility.isNullorEmpty(queryModel.getModuleName())) {
            hql += " and moduleName like '%" + queryModel.getModuleName() + "%'";
        }
        /*
         * if(!TeeUtility.isNullorEmpty(queryModel.getModuleName())){ hql+=
         * " and moduleName like '%"+queryModel.getModuleName()+"%'"; }
         */

        // if (!TeeUtility.isNullorEmpty(queryModel.getOrganizationId()))
        // {
        // hql +=" and organization.sysOrgName like
        // '%"+queryModel.getOrganizationId()+"%'";
        // }

        // if(!TeeUtility.isNullorEmpty(queryModel.getAdministrativedivision())){
        // hql+=" and administrativeDivision like
        // '%"+queryModel.getAdministrativedivision()+"%'";
        // }
     // 确认执法系统
        if (queryModel.getOrgSys() != null) {
            String[] orgSysBuff = queryModel.getOrgSys().split(",");
            if (orgSysBuff.length > 0) {
                hql = hql + "and orgSys in ('empty'";
                for (String orgSys : orgSysBuff) {
                    hql = hql + ", '" + orgSys + "' ";
                }
                hql = hql + ") ";
            } else {
                return null;
            }
        }
        return super.pageFind(hql, firstResult, rows, null);
    }
    /**
     * 权限控制
     * @param queryModel
     * @return
     */
    public long getTotal(InspModuleModel queryModel) {
    	//权限控制，只有主管理员可以获取数据
    	if(queryModel.getCtrlType() == null || !"10".equals(queryModel.getCtrlType())){
    		return 0L;
    	}
        String hql = " select count(id) from InspModule where isDelete = 0 ";

        if (!TeeUtility.isNullorEmpty(queryModel.getModuleName())) {
            hql += " and moduleName  like '%" + queryModel.getModuleName() + "%'";
        }
        // 确认执法系统
        if (queryModel.getOrgSys() != null) {
            String[] orgSysBuff = queryModel.getOrgSys().split(",");
            if (orgSysBuff.length > 0) {
                hql = hql + "and orgSys in ('empty'";
                for (String orgSys : orgSysBuff) {
                    hql = hql + ", '" + orgSys + "' ";
                }
                hql = hql + ") ";
            } else {
                return 0L;
            }
        }
        return super.count(hql, null);
    }

    // 通过id假删除 0为存在 1删除
    public long deleteById(String id) {
        String hql = " update InspModule set isDelete = '1' where id = '" + id + "'";
        return this.executeUpdate(hql, null);
    }

    public long deletes(String[] ids) {
        // TODO Auto-generated method stub
        String hql = "";
        for (int i = 0; i < ids.length; i++) {
            hql = ("update InspModule set isDelete = '1' where id = '" + ids[i] + "'");
            this.executeUpdate(hql, null);
        }
        return this.executeUpdate(hql, null);
    }
    /**
     * 
     * @param queryModel
     * @return
     */
    public List<InspModule> listByOrgSysCode(InspModuleModel queryModel) {
        String hql = " from InspModule i where isDelete = 0 ";
        if (!TeeUtility.isNullorEmpty(queryModel.getOrgSys())) {
            hql += " and orgSys = '" + queryModel.getOrgSys() + "' ";
        }else{
            return null;
        }
        return super.find(hql, null);
    }
    /**
     * 通过多个id获取模版名称
     * @param moduleIds
     * @return
     */
    public String getByIds(List<String> moduleIds) {
        // TODO Auto-generated method stub
        return null;
    }
}
