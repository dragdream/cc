package com.tianee.oa.core.base.vehicle.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.base.email.bean.TeeMail;
import com.tianee.oa.core.base.email.model.TeeEmailModel;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicle;
import com.tianee.oa.core.base.vehicle.bean.TeeVehicleUsage;
import com.tianee.oa.core.base.vehicle.dao.TeeVehicleDao;
import com.tianee.oa.core.base.vehicle.model.TeeVehicleModel;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.core.org.dao.TeeDeptDao;
import com.tianee.oa.core.org.dao.TeePersonDao;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.file.TeeFileUtility;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
@Service
public class TeeVehicleService  extends TeeBaseService{
	@Autowired
	private TeeVehicleDao vehicleDao;
	
	@Autowired
	private TeePersonDao personDao;

	@Autowired
	private TeeDeptDao deptDao;
	
	@Autowired
	private TeeBaseUpload upload;

	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeVehicleModel model) throws IOException, ParseException {
		/*附件处理*/		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<TeeAttachment> attachments = upload.manyAttachUpload(multipartRequest, TeeAttachmentModelKeys.VEHICLE);
		
		
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		
		TeeVehicle room = new TeeVehicle();
		BeanUtils.copyProperties(model, room);
		if(!TeeUtility.isNullorEmpty(model.getPostDeptIds())){//申请权限会议室  ---部门
			List<TeeDepartment> listDept = deptDao.getDeptListByUuids(model.getPostDeptIds());
			room.setPostDept(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getPostUserIds())){//申请权限会议室 -- 人员
			List<TeePerson> listDept = personDao.getPersonByUuids(model.getPostUserIds());
			room.setPostUser(listDept);
		}
		if(!TeeUtility.isNullorEmpty(model.getBuyDateStr())){
			Date date = TeeUtility.parseDate("yyyy-MM-dd", model.getBuyDateStr());
			room.setBuyDate(date);
		}
		if(attachments!=null&&attachments.size()>0){
			room.setAttache(attachments.get(0));
		}
		if(model.getSid() > 0){
			TeeVehicle meetRoom  = vehicleDao.getById(model.getSid());
			if(meetRoom != null){
				int sid = meetRoom.getSid();
				BeanUtils.copyProperties(room, meetRoom);
				vehicleDao.updateObj(meetRoom);
			}else{
				json.setRtState(false);
				json.setRtMsg("该车辆已被删除！");
				return json;
			}
		}else{
			vehicleDao.add(room);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		
		return json;
	}
	
	/**
	 * 获取所有车辆申请
	 * @date 2014-3-9
	 * @author 
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeEasyuiDataGridJson getAllVehicle(HttpServletRequest request, TeeVehicleModel model,TeeDataGridModel dm) {
		TeeEasyuiDataGridJson j = new TeeEasyuiDataGridJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		Map requestDatas = TeeServletUtility.getParamMap(request);
		String hql = "from TeeVehicle order by buyDate";
		List param = new ArrayList();
		
		// 设置总记录数
		j.setTotal(simpleDaoSupport.countByList("select count(*) " + hql, param));// 设置总记录数

		int firstIndex = 0;
		firstIndex = (dm.getPage() - 1) * dm.getRows();// 获取开始索引位置
		List<TeeVehicle> list = simpleDaoSupport.pageFindByList(hql, (dm.getPage() - 1) * dm.getRows(), dm.getRows(), param);// 查

		List<TeeVehicleModel> modelList = new ArrayList<TeeVehicleModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				modelList.add(parseModel(list.get(i)));
			}
		}
		j.setRows(modelList);// 设置返回的行
		return j;
	}
	
	
	
	/**
	 * 获取有权限的会议室
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson selectPostVehicle(HttpServletRequest request, TeeVehicleModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeVehicle> list = vehicleDao.selectPostVehicle(person, model);
		List<TeeVehicleModel> listModel = new ArrayList<TeeVehicleModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
	

	/**
	 * 对象转换
	 * @author syl
	 * @date 2014-1-29
	 * @param out
	 * @return
	 */
	public TeeVehicleModel parseModel(TeeVehicle room){
		TeeVehicleModel model = new TeeVehicleModel();
		if(room == null){
			return model;
		}
		BeanUtils.copyProperties(room, model);
		List<TeeDepartment> listDept = room.getPostDept();
		List<TeePerson> userList = room.getPostUser();
		
		String postDeptIds = "";
		String postDeptNames = "";
		String postUserIds = "";
		String postUserNames = "";
	    if(listDept != null){
	    	for (int i = 0; i < listDept.size(); i++) {
	    		postDeptIds = postDeptIds + listDept.get(i).getUuid() + ",";
	    		postDeptNames = postDeptNames + listDept.get(i).getDeptName() + ",";
			}
	    }
	    
	    if(userList != null){
	    	for (int i = 0; i < userList.size(); i++) {
	    		postUserIds = postUserIds + userList.get(i).getUuid() + ",";
	    		postUserNames = postUserNames + userList.get(i).getUserName() + ",";
			}
	    }
	    model.setPostDeptIds(postDeptIds);
	    model.setPostDeptNames(postDeptNames);
	    model.setPostUserIds(postUserIds);
	    model.setPostUserNames(postUserNames);
//	    model.setvNum(vNum);
	    SimpleDateFormat simpDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String buyDateStr = "";
	    if(room.getBuyDate() != null){
	    	buyDateStr = TeeUtility.getDateStrByFormat(room.getBuyDate() , simpDateFormat);
	    	model.setBuyDateStr(buyDateStr);
	    }
	    //处理附件
	    if(room.getAttache()!=null){
	    	TeeAttachmentModel attModel=new TeeAttachmentModel();
	    	BeanUtils.copyProperties(room.getAttache(), attModel);
	    	attModel.setCreateTimeDesc(TeeDateUtil.format(room.getAttache().getCreateTime()));
	    	attModel.setSizeDesc(TeeFileUtility.getFileSizeDesc(room.getAttache().getSize()));
			if(room.getAttache().getUser()!=null){
				attModel.setUserId(room.getAttache().getUser().getUuid()+"");
				attModel.setUserName(room.getAttache().getUser().getUserName());
			}else{
				attModel.setUserId("");
				attModel.setUserName("");
			}
			
			model.setAttacheModel(attModel);
	    }
	 
		return model;
	}
	
	
	/**
	 * 
	 * @author syl 删除ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteById(HttpServletRequest request, TeeVehicleModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			simpleDaoSupport.executeUpdate("delete from TeeVehicleMaintenance where vehicle.sid="+model.getSid(), null);
			simpleDaoSupport.executeUpdate("delete from TeeVehicleUsage where vehicle.sid="+model.getSid(), null);
			vehicleDao.delById(model.getSid());
		}
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	/**
	 * 
	 * @author syl 删除所有会议室
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson deleteAll(HttpServletRequest request, TeeVehicleModel model) {
		TeeJson json = new TeeJson();
		vehicleDao.delAll();
		json.setRtState(true);
		json.setRtMsg("删除成功!");
		return json;
	}
	
	
	/**
	 * 
	 * @author syl 
	 *  查询 ById
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getById(HttpServletRequest request, TeeVehicleModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeVehicle out = vehicleDao.getById(model.getSid());
			if(out !=null){
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("该会议室可能已被删除！");
		return json;
	}
	
	/**
	 * 校验名称的唯一性
	 * @date 2014年7月20日
	 * @author 
	 * @param request
	 * @return
	 */
	public TeeJson checkName(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		String nameStr = TeeUtility.null2Empty(request.getParameter("nameStr"));
		long count = vehicleDao.getVehicleCountByNameDao(sid, nameStr);
		int flag = 0;
		if(count>0){
			flag = 1;
		}
		Map map = new HashMap();
		map.put("flag", flag);
		json.setRtState(true);
		json.setRtData(map);
		return json;
	}
	
	/**
	 * 校验名称的唯一性
	 * @date 2014年7月20日
	 * @author 
	 * @param request
	 * @return
	 */
	public List<Map> getOperators(HttpServletRequest request){
		String diaoduyuan = TeeSysProps.getString("VEHICLE_MANAGER_TYPE");
		List<TeePerson> persons = personDao.getPersonByUuids(diaoduyuan);
		List<Map> list = new ArrayList();
		for(TeePerson p:persons){
			Map map = new HashMap();
			map.put("id", p.getUuid());
			map.put("name", p.getUserName());
			list.add(map);
		}
		return list;
	}
	
	
	/**
	 * 获取占用情况
	 * @param roomId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public List<double[]> getVehicleUseage(int vehicleId,String date) throws Exception {
		List<double[]> list = new ArrayList();
		
		Date start = TeeDateUtil.parseDate(date+" 00:00:00");
		Date end = TeeDateUtil.parseDate(date+" 23:59:59");
		
		//获取指定日期下的车辆占用情况
		List<TeeVehicleUsage> meetingInfo = simpleDaoSupport.find("from TeeVehicleUsage where ?<=vuStart and vuStart<=? and vehicle.sid="+vehicleId, new Object[]{start,end});
		for(TeeVehicleUsage usage:meetingInfo){
			Calendar s = Calendar.getInstance();
			s.setTimeInMillis(usage.getVuStart().getTime());
			s.set(Calendar.SECOND, 0);
			if(s.get(Calendar.MINUTE)<30){
				s.set(Calendar.MINUTE, 0);
			}else{
				s.set(Calendar.MINUTE, 30);
			}
			
			Calendar e = Calendar.getInstance();
			e.setTimeInMillis(usage.getVuEnd().getTime());
			e.set(Calendar.SECOND, 0);
			if(e.get(Calendar.MINUTE)<30){
				e.set(Calendar.MINUTE, 0);
			}else{
				e.set(Calendar.MINUTE, 30);
			}
			
			double arr[] = new double[2];
			int minute = s.get(Calendar.MINUTE);
			if(minute==30){
				arr[0] = s.get(Calendar.HOUR_OF_DAY)+0.5;
			}else{
				arr[0] = s.get(Calendar.HOUR_OF_DAY)+0.0;
			}
			
			minute = e.get(Calendar.MINUTE);
			if(minute==30){
				arr[1] = e.get(Calendar.HOUR_OF_DAY)+0.5;
			}else{
				arr[1] = e.get(Calendar.HOUR_OF_DAY)+0.0;
			}
			list.add(arr);
		}
		
		return list;
	}

	/**
	 * 获取所有的车辆
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getAllVehicles(HttpServletRequest request,
			TeeVehicleModel model) {
		TeeJson j = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeVehicle> list = vehicleDao.getAllVehicle(person, model);

		List<TeeVehicleModel> modelList = new ArrayList<TeeVehicleModel>();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				modelList.add(parseModel(list.get(i)));
			}
		}
		j.setRtState(true);
		j.setRtData(modelList);
		return j;
	}
}