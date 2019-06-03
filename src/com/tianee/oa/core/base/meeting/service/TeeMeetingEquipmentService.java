package com.tianee.oa.core.base.meeting.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tianee.oa.core.base.meeting.bean.TeeMeetingEquipment;
import com.tianee.oa.core.base.meeting.dao.TeeMeetingEquipmentDao;
import com.tianee.oa.core.base.meeting.model.TeeMeetingEquipmentModel;
import com.tianee.oa.core.general.TeeSmsManager;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.service.TeeBaseService;

@Service
public class TeeMeetingEquipmentService  extends TeeBaseService{
	@Autowired
	private TeeMeetingEquipmentDao meetingDao;
	

	
	@Autowired
	private TeeSmsManager smsManager;
	/**
	 * @author syl
	 * 新增 或者 更新
	 * @param message
	 * @param person 系统当前登录人
	 * @return
	 */
	public TeeJson addOrUpdate(HttpServletRequest request, TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		TeeMeetingEquipment equipment = new TeeMeetingEquipment();
		if(model.getSid() > 0){
			TeeMeetingEquipment eq  = meetingDao.getById(model.getSid());
			if(eq != null){
				BeanUtils.copyProperties(model, eq);
				meetingDao.updateEquipment(eq);
			}else{
				json.setRtState(false);
				json.setRtMsg("未查到到相关外出信息！");
				return json;
			}
		}else{
			BeanUtils.copyProperties(model, equipment);
			meetingDao.addEquipment(equipment);
		}
		json.setRtState(true);
		json.setRtData(model);
		json.setRtMsg("保存成功！");
		return json;
	}
	
	/**
	 * 
	 * @author syl
	 * @date 2014-1-29
	 * @param request
	 * @param model
	 * @return
	 */
	public TeeJson getAllEquipment(HttpServletRequest request, TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		List<TeeMeetingEquipment> list = meetingDao.getAllEquipment();
		List<TeeMeetingEquipmentModel> listModel = new ArrayList<TeeMeetingEquipmentModel> ();
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
	public TeeMeetingEquipmentModel parseModel(TeeMeetingEquipment equipment){
		TeeMeetingEquipmentModel model = new TeeMeetingEquipmentModel();
		if(equipment == null){
			return null;
		}
		BeanUtils.copyProperties(equipment, model);
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
	public TeeJson deleteById(HttpServletRequest request, String ids) {
		TeeJson json = new TeeJson();
		meetingDao.delByIds(ids);
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
	public TeeJson getById(HttpServletRequest request, TeeMeetingEquipmentModel model) {
		TeeJson json = new TeeJson();
		if(model.getSid() > 0){
			TeeMeetingEquipment out = meetingDao.getById(model.getSid());
			if(out !=null){
				model = parseModel(out);
				json.setRtData(model);
				json.setRtState(true);
				json.setRtMsg("查询成功!");
				return json;
			}
		}
		json.setRtState(false);
		json.setRtMsg("未找到相关设备记录！");
		return json;
	}
	
	public TeeEasyuiDataGridJson datagird(TeeDataGridModel dm, Map requestDatas) {
		return meetingDao.datagird(dm, requestDatas);
	}

	public TeeJson getMeetingEquipment(Map requestrMap) {
		TeeJson json = new TeeJson();
		List<TeeMeetingEquipment> list = meetingDao.getAllEquipment();
		List<TeeMeetingEquipmentModel> listModel = new ArrayList<TeeMeetingEquipmentModel> ();
		for (int i = 0; i < list.size(); i++) {
			listModel.add(parseModel(list.get(i)));
		}
		json.setRtData(listModel);
		json.setRtState(true);
		return json;
	}
}