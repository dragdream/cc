package com.beidasoft.xzzf.inspection.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.beidasoft.xzzf.inspection.inspectors.bean.Inspectors;
import com.beidasoft.xzzf.inspection.inspectors.service.InspectorsService;
import com.beidasoft.xzzf.inspection.plan.bean.InspectionStaff;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionStaffDao;
import com.beidasoft.xzzf.inspection.plan.dao.InspectionTaskDao;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.service.TeePersonService;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.webframe.data.TeeDataRecord;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.file.TeeCSVUtil;
import com.tianee.webframe.util.secure.TeePassEncryptMD5;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Service
public class InspectionStaffService extends TeeBaseService{
	@Autowired
	private InspectionStaffDao inspectionStaffDao;
	@Autowired
	private TeePersonService personService;
	@Autowired
	private InspectorsService inspectorsService;
	@Autowired
	private InspectionTaskDao inspectionTaskDao;
	@Autowired
	private TeeDeptDao deptDao;
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public TeeJson deleteStaffs(String ids){
		TeeJson json = new TeeJson();
		//判断参数是否为空
		if (!TeeUtility.isNullorEmpty(ids)) {
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				InspectionStaff staff = inspectionStaffDao.get(id);
				//判断对象是否存在
				if (!TeeUtility.isNullorEmpty(staff)) {
					//存在则删除
					inspectionStaffDao.delete(id);
					json.setRtState(true);
					inspectionTaskDao.delListByInsId(staff.getInspectionId());//人员表变动时删除后续计划
				}
			}
		}
		return json;
	}
	
	/**
	 * 新增参与执法人员
	 * @param ids
	 * @param inspectionId
	 * @return
	 */
	public TeeJson saveStaff(String ids,String inspectionId){
		TeeJson json = new TeeJson();
		if (!TeeUtility.isNullorEmpty(ids)&&!TeeUtility.isNullorEmpty(inspectionId)) {
			String[] idArr = ids.split(",");
			//循环添加选中人员
			for (String id : idArr) {
				Inspectors inspectors = inspectorsService.getById(TeeStringUtil.getInteger(id, 0));
				//判断人员表中是否存在该人员
				if (!TeeUtility.isNullorEmpty(inspectors)) {
					//存在 测查询该计划下 是否重复
					InspectionStaff insStaff = inspectionStaffDao.getObjByinsIdAndUserId(inspectionId, inspectors.getStaffId());
					if (TeeUtility.isNullorEmpty(insStaff)) {
						//该计划下没有该人员 则添加
						InspectionStaff staff = new InspectionStaff();
						staff.setId(TeeStringUtil.getString(UUID.randomUUID()));
						staff.setInspectionId(inspectionId);
						staff.setStaffId(inspectors.getStaffId());
						staff.setStaffName(inspectors.getStaffName());
						staff.setStaffCode(personService.get(inspectors.getStaffId()).getPerformCode());//执法证号
						staff.setDeptId(inspectors.getDepartmentId());
						staff.setDeptName(inspectors.getDepartmentName());
						inspectionStaffDao.save(staff);
						json.setRtState(true);
						inspectionTaskDao.delListByInsId(inspectionId);//人员表变动时删除后续计划
					} else {
						//该计划下有该人员 无需添加
						json.setRtMsg("该人员已存在");
						json.setRtState(false);
					}
				} else {
					//不存在 人员ID有误
					json.setRtMsg("该人员不存在");
					json.setRtState(false);
				}
			}
		}
		return json;
	}
	
	
	
	/**
	 * 分页查询
	 * @param firstResult
	 * @param rows
	 * @return
	 */
	public List<InspectionStaff> listByPage(int firstResult,int rows,String inspectionId){
		List<InspectionStaff> list = inspectionStaffDao.listByPage(firstResult, rows ,inspectionId);
		return list;
	}
	/**
	 * 查询总记录数
	 * @return
	 */
	public Long getTotal(String inspectionId){
		return inspectionStaffDao.getTotal(inspectionId);
	}
	
	/**
	 * 根据计划主表id查询
	 * @param inspectionId
	 * @return
	 */
	public List<InspectionStaff> getListByInsId(String inspectionId){
		return inspectionStaffDao.getListByInsId(inspectionId);
	}
	
	/**
	 * 导出执法人员CSV
	 * @return
	 */
	public ArrayList<TeeDataRecord> exportStaffInfo(String inspectionId){
		ArrayList<TeeDataRecord> list = new ArrayList<TeeDataRecord>();
		List<InspectionStaff> staffList = getListByInsId(inspectionId);
	    for (int i = 0; i < staffList.size(); i++) {
	    	InspectionStaff staff = staffList.get(i);
	        TeeDataRecord dbrec = new TeeDataRecord();
	        TeePerson person = personService.get(staff.getStaffId());
	        dbrec.addField("执法人员用户名", TeeStringUtil.getString(person.getUserId()));
	        dbrec.addField("执法人员姓名", TeeStringUtil.getString(staff.getStaffName()));
	        dbrec.addField("所在部门", TeeStringUtil.getString(staff.getDeptName()));
	        list.add(dbrec);
	      }
		return list;
	}
    
	
	/**
	 * 导入检察人员
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public TeeJson importStaff(HttpServletRequest request,String inspectionId) throws Exception{
		TeeJson json = new TeeJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		StringBuffer sb = new StringBuffer("[");
		String infoStr= "";
		int importSuccessCount = 0;
		try {
			MultipartFile  file = multipartRequest.getFile("importStaffFile");
			if(!file.isEmpty() ){
				//获取真实文件名称
				String realName = file.getOriginalFilename();
				ArrayList<TeeDataRecord> dbL = TeeCSVUtil.CVSReader(file.getInputStream(), "GBK");
				List<InspectionStaff> addStaffList = new ArrayList<InspectionStaff>();
				List<TeeDepartment> addDeptList = new ArrayList<TeeDepartment>();
				for (int i = 0; i < dbL.size(); i++) {
					TeeDataRecord dr = dbL.get(i);
					String userId = TeeStringUtil.getString(dr.getValueByName("执法人员用户名"));
					String staffName = TeeStringUtil.getString(dr.getValueByName("执法人员姓名"));
					String deptName = TeeStringUtil.getString(dr.getValueByName("所在部门"));
					TeePerson person = personService.getPersonByUserId(userId);
					if (TeeUtility.isNullorEmpty(person)) {
						infoStr = "导入失败,该用户 " + userId + " 不存在！";
						sb.append("{");
						sb.append("userId:\"" + userId + "\"");
				        sb.append("staffName:\"" + staffName + "\"");
				        sb.append(",deptName:\"" + deptName + "\"");
				        sb.append("},");
				        continue;
					}
					InspectionStaff staff = inspectionStaffDao.getObjByinsIdAndUserId(inspectionId, person.getUuid());
					if (!TeeUtility.isNullorEmpty(staff)) {
						infoStr = "导入失败,执法人员 " + staffName + " 已经存在！";
						sb.append("{");
						sb.append("userId:\"" + userId + "\"");
				        sb.append("staffName:\"" + staffName + "\"");
				        sb.append(",deptName:\"" + deptName + "\"");
				        sb.append("},");
				        continue;
					}
					//personService.getPersonByUserId(userId)
//					inspectorsService
					//isCount++;
			        infoStr = "导入成功";
			        InspectionStaff newStaff = new InspectionStaff();
					TeeDepartment dept = new TeeDepartment();
					newStaff.setId(TeeStringUtil.getString(UUID.randomUUID()));
					newStaff.setInspectionId(inspectionId);
					newStaff.setStaffId(person.getUuid());
					newStaff.setStaffName(staffName);
					newStaff.setStaffCode(TeeStringUtil.getString(person.getUuid()));
					newStaff.setDeptId(person.getDept().getUuid());
					newStaff.setDeptName(person.getDept().getDeptName());
					inspectionStaffDao.save(newStaff);
					inspectionTaskDao.delListByInsId(inspectionId);//人员表变动时删除后续计划
			        sb.append("{");
			        sb.append("userId:\"" + userId + "\"");
			        sb.append("staffName:\"" + staffName + "\"");
			        sb.append(",deptName:\"" + deptName + "\"");
			        sb.append("},");
			        importSuccessCount++;
				}
			}else{
				json.setRtState(false);
				json.setRtMsg("文件为空！");
				return json;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		if(!sb.equals("[")){
			sb.deleteCharAt(sb.length() -1);
		}
		sb.append("]");
		json.setRtData(sb);
		json.setRtState(true);
		json.setRtMsg(importSuccessCount + "");
		return json;
	}
	/**
	 * 根据部门id查询
	 * @param inspectionId
	 * @param deptId
	 * @return
	 */
	public List<InspectionStaff> getListByDeptId(String inspectionId,int deptId){
		return inspectionStaffDao.getListByDeptId(inspectionId, deptId);
	}
}
