package com.beidasoft.zfjd.inspection.inspModule.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.zfjd.department.bean.TblDepartmentInfo;
import com.beidasoft.zfjd.department.dao.TblDepartmentDao;
import com.beidasoft.zfjd.department.model.TblDepartmentModel;
import com.beidasoft.zfjd.inspection.inspItem.dao.InspectItemDao;
import com.beidasoft.zfjd.inspection.inspList.dao.InspListBaseDao;
import com.beidasoft.zfjd.inspection.inspList.dao.InspListModuleDao;
import com.beidasoft.zfjd.inspection.inspModule.bean.InspModule;
import com.beidasoft.zfjd.inspection.inspModule.dao.InspModuleDao;
import com.beidasoft.zfjd.inspection.inspModule.model.InspModuleModel;
import com.beidasoft.zfjd.system.bean.SysBusinessRelation;
import com.tianee.webframe.service.TeeBaseService;

/**
 * 职权基础信息表SERVICE类
 */
@Service
public class InspModuleService extends TeeBaseService {

    @Autowired
    private InspModuleDao inspectionDao;
    
    @Autowired
	private TblDepartmentDao departmentDao;
    @Autowired
    private InspectItemDao inspectitemDao;
    
    @Autowired
    private InspListModuleDao inspListModuleDao;
    
    @Autowired
    private InspListBaseDao inspListBaseDao;

    /**
     * 保存职权基础信息表数据
     *
     * @param beanInfo
     */
    public void save(InspModule beanInfo) {

        inspectionDao.saveOrUpdate(beanInfo);
    }

    /**
     * 获取职权基础信息表数据
     *
     * @param id
     * @return     */
    public InspModule getById(String id) {

        return inspectionDao.get(id);
    }

	public List<InspModule> listByPage(int firstResult, int rows, InspModuleModel queryModel) {
		return inspectionDao.listByPage(firstResult, rows, queryModel);
	}
    
	public long getTotal(InspModuleModel queryModel) {
		// TODO Auto-generated method stub
		return inspectionDao.getTotal(queryModel);
	}
	
	/*
	 * 通过主键假删除用户信息
	 */
	/*public void deleteById(String id) {

		Inspection inspection = new Inspection();
		inspection= inspectionDao.get(id);
		inspection.setIsDelete(1);
		this.update(inspection);
	}*/
	
	public void update(InspModule beanInfo) {
	    //修改检查模块
        inspectionDao.update(beanInfo);
        //关联
        inspectitemDao.updateItem(beanInfo);
        
    }
	/**
	 * @author lrn
	 * @description 删除检查模块
	 */
	public void InspModeldel(InspModule beanInfo){
	    //修改检查模块
	    inspectionDao.update(beanInfo);
	    //关联删除检查项
	    inspectitemDao.deteleItem(beanInfo.getId());
	    //对检查多单模版关联删除
	    //获取关联的检查单模版id
	    List<String> ids = inspListModuleDao.getByModelId(beanInfo.getId());
	    if(ids.size()>0){
	        String[] str = new String[ids.size()];
	        //对相关联检查单模版实施停用
	        Integer currentState = 2;
	        inspListBaseDao.updateListState(ids.toArray(str),currentState);
	    }
	}
	/**
	 * @author lrn
	 * @description 删除检查模块
	 * @param id
	 */
	public void deletes(String id ) {

		String ids [] = id.split(",");
		
		inspectionDao.deletes(ids);
		//关联删除检查项
		inspectitemDao.deteleItem(id);
		//对检查单模版关联删除
		List<String> listIds = inspListModuleDao.getByModelId(id);
		if(listIds.size()>0){
		    String[] str = new String[listIds.size()];
	        //对相关联检查单模版实施停用
	        Integer currentState = 2;
	        inspListBaseDao.updateListState(listIds.toArray(str),currentState);
		}
        
		
    }

	public List<TblDepartmentInfo> listByDe(int firstResult, int rows, TblDepartmentModel queryModel) {
		return departmentDao.listByDe(firstResult, rows, queryModel);

	}

	 /**
     * 根据指定部门ID，查询SysBusinessRelation关系对象
     *
     * @param deptId
     */
    public SysBusinessRelation getSysBusinessRelationByDeptId(int deptId) {
        return (SysBusinessRelation)simpleDaoSupport.unique("from SysBusinessRelation where deptRelation.uuid="+deptId, null);
    }

    /**
    * 根据指定执法系统，获取检查模块数据
    *
    * @param deptId
    */
    public List<InspModule> listByOrgSysCode(InspModuleModel model) {
        return inspectionDao.listByOrgSysCode(model);
    }
}
