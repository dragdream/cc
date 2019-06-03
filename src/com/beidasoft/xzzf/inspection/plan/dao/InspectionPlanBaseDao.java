package com.beidasoft.xzzf.inspection.plan.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.beidasoft.xzzf.inspection.plan.bean.InspectionPlanBase;
import com.tianee.webframe.dao.TeeBaseDao;
import com.tianee.webframe.util.db.TeeDbUtility;
import com.tianee.webframe.util.str.TeeUtility;

@Repository("inspectionPlanBaseDao")
public class InspectionPlanBaseDao extends TeeBaseDao<InspectionPlanBase> {

	/**
	 * 保存
	 */
	public void saveInspectionPlanBase(InspectionPlanBase inspectionPlanBase) {
		super.save(inspectionPlanBase);
	}
	
	
	/**
	 * 修改
	 */
	public void update(InspectionPlanBase inspectionPlanBase) {
		super.saveOrUpdate(inspectionPlanBase);
	}
	
	/**
	 * 根据id查询
	 */
	public InspectionPlanBase loadById(String id) {
		InspectionPlanBase inspectionPlanBase = super.get(id);
		return inspectionPlanBase;
	}
	
	/**
	 * 批量，单个修改状态
	 * @param ids id或id串
	 * @param num 要改的数字
	 * @param param	要改的参数
	 * @return
	 */
	public int updateDelByUuids(String  ids, int num, String param) {
		String[] str = ids.split(",");
		StringBuffer strIds = new StringBuffer("");
		for (int i = 0; i < str.length; i++) {
			strIds.append("'");
			strIds.append(str[i]);
			strIds.append("'");
			if(i < str.length - 1){
				strIds.append(",");
			}
		}
		String sql = "";
		if("isDeleted".equals(param)){
			//只有未使用的可以删除
			sql = "update InspectionPlanBase set "+param+" = ? where id in(" + strIds + ") and status<1";
		}else{
			sql = "update InspectionPlanBase set "+param+" = ? where id in(" + strIds + ")";
		}
		
		Object[] values = {num};
		int count = deleteOrUpdateByQuery(sql, values);
		return count;
	}
	
	/**
	 * 根据标题模糊查询
	 * @param title
	 * @return List
	 */
	public List<InspectionPlanBase> listByTitle(String title){
		String hql = "from InspectionPlanBase where 1 = 1 ";
		if (!"".equals(title)&&!TeeUtility.isNullorEmpty(title)) {
			title = TeeDbUtility.formatString(title);
			hql += " and title like '%"+title+"%' ";
		}
		List<InspectionPlanBase> list = super.find(hql, null);
		return list;
	}
}
