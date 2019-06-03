package com.beidasoft.xzzf.inspection.inspectors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.beidasoft.xzzf.inspection.inspectors.dao.InspectorsDao;
import com.beidasoft.xzzf.inspection.inspectors.model.InspectorsModel;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class InspectorsService extends TeeBaseService {

	@Autowired
	private InspectorsDao inspectorsDao;
	
	/**
	 * 保存检查人员
	 * @param inspectors
	 */
	public TeeJson saveInspectors(List<Inspectors> inspectorsList) {
		TeeJson json = new TeeJson();
		//提示信息
		String msg = "";
		String msgSuccess = "";
		//循环保存
		for (Inspectors inspectors : inspectorsList) {
			//保存前查询是否已存在
			String hql = " from Inspectors Where staffId = '" + inspectors.getStaffId() +"' ";
			Inspectors checkObj = inspectorsDao.getObjById(hql);
			//如果不存在保存，并存取保存成功人员列表
			if(TeeUtility.isNullorEmpty(checkObj)) {
				inspectorsDao.save(inspectors);
				msgSuccess += inspectors.getStaffName() + ",";
			}else {
				msg += checkObj.getStaffName() + ",";
			}
		}
		//添加失败人员不为空
		if (!TeeUtility.isNullorEmpty(msg)) {
			msg += "已存在,请删除后添加！ ";
			if (!TeeUtility.isNullorEmpty(msgSuccess)) {
				msg += msgSuccess + "添加成功。";
			}
		} else {
			msg += "添加成功！";
		}
		json.setRtMsg(msg);
		json.setRtState(true);
		return json;
	}
	
	/**
	 * 更新检查人员
	 * @param inspectors
	 */
	public void update(Inspectors inspectors) {
		inspectorsDao.update(inspectors);
	}
	
	/**
	 * 删除检查人员（通过Id）
	 * @param id
	 */
	public void deleteById(int id) {
		inspectorsDao.delete(id);
	}
	
	/**
	 * 删除检查人员（通过对象）
	 * @param inspectors
	 */
	public void deleteByObjact(Inspectors inspectors){
		inspectorsDao.deleteByObj(inspectors);
	}
	
	/**
	 * 条件分页查询检查人员
	 * @param inspectorsModel
	 * @param dataGridModel
	 * @return
	 */
	public List<Inspectors> getInspectors(InspectorsModel inspectorsModel,TeeDataGridModel dataGridModel) {
		return inspectorsDao.getInspectors(inspectorsModel, dataGridModel);
	}
	
	/**
	 * 条件查询检查人员
	 * @param inspectorsModel
	 * @return
	 */
	public List<Inspectors> getInspectors(InspectorsModel inspectorsModel) {
		return inspectorsDao.getInspectors(inspectorsModel);
	}
	
	
	/**
	 * 根据检查人员id查询
	 * @param id
	 * @return
	 */
	public Inspectors getById(int staffId) {
		return inspectorsDao.get(staffId);
	}
	
	/**
	 * 根据检查人员姓名查询
	 * @param staffName
	 * @return
	 */
	public Inspectors getByName(String staffName) {
		return inspectorsDao.getByName(staffName);
	}
	
	/**
	 * 根据部门Id查询
	 * @param departmentId
	 * @return
	 */
	public List<Inspectors> getByDeptId(int departmentId) {
		return inspectorsDao.getByDeptId(departmentId);
	}
	
	/**
	 * 获得总记录数
	 * @return
	 */
	public long getTotal() {
		return inspectorsDao.getTotal();
	}
	
	/**
	 * 获得符合条件的总记录数
	 * @param inspectorsModel
	 * @return
	 */
	public long getTotal(InspectorsModel inspectorsModel) {
		return inspectorsDao.getTotal(inspectorsModel);
	}
}
