package com.tianee.oa.core.base.dam.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tianee.oa.core.base.dam.bean.TeeStoreHouse;
import com.tianee.oa.core.base.dam.model.TeeStoreHouseModel;
import com.tianee.oa.core.base.dam.service.TeeStoreHouseService;
import com.tianee.oa.core.base.exam.model.TeeExamQuestionModel;
import com.tianee.oa.core.org.bean.TeePerson;
import com.tianee.oa.oaconst.TeeConst;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.servlet.TeeServletUtility;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("TeeStoreHouseController")
public class TeeStoreHouseController {
	@Autowired
	TeeStoreHouseService roomService;
	
	@RequestMapping("/addRoom")
	@ResponseBody
	public TeeJson addRoom(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeStoreHouseModel roomModel = new TeeStoreHouseModel();
		TeeServletUtility.requestParamsCopyToObject(request, roomModel);
		roomModel.setCreateUserId(loginUser.getUuid());
		roomModel.setCreateUserName(loginUser.getUserName());
		//roomModel.setBorrowRandIds(borrowRandIds);
		roomService.addRoomModel(roomModel);
		json.setRtState(true);
		json.setRtMsg("添加成功");
		return json;
	}
	
	@RequestMapping("/editRoom")
	@ResponseBody
	public TeeJson editRoom(HttpServletRequest request){
		TeeJson json = new TeeJson();
		TeeStoreHouseModel roomModel = new TeeStoreHouseModel();
		TeeServletUtility.requestParamsCopyToObject(request, roomModel);
		roomService.updateRoomModel(roomModel);
		json.setRtMsg("更新成功");
		json.setRtState(true);
		return json;		
	}
	
	@RequestMapping("/delRoom")
	@ResponseBody
	public TeeJson delRoom(HttpServletRequest request){
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		return roomService.deleteRoom(sid);		
	}
	
	@RequestMapping("/getRoom")
	@ResponseBody
	public TeeJson getRoom(HttpServletRequest request){
		TeeJson json = new TeeJson();
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
		json.setRtData(roomService.getModelById(sid));
		json.setRtState(true);
		return json;
	}
	
	@RequestMapping("/datagrid")
	@ResponseBody
	public TeeEasyuiDataGridJson datagrid(TeeDataGridModel dm,HttpServletRequest request) {
		Map requestDatas = TeeServletUtility.getParamMap(request);
		requestDatas.put(TeeConst.LOGIN_USER, request.getSession().getAttribute(TeeConst.LOGIN_USER));
		return roomService.datagrid(dm, requestDatas);
	}
	
	@RequestMapping("/getAllRoom")
	@ResponseBody
	public TeeJson getAllRoom(HttpServletRequest request){
		TeeJson json = new TeeJson();
		List<TeeStoreHouse> list = roomService.getAllRoom();
		List<TeeStoreHouseModel> models = new ArrayList<TeeStoreHouseModel>();
		for(TeeStoreHouse room:list){
			TeeStoreHouseModel model = new TeeStoreHouseModel();
			BeanUtils.copyProperties(room, model);
			models.add(model);
		}
		json.setRtData(models);
		json.setRtMsg("删除成功");
		json.setRtState(true);
		return json;		
	}
	
	
	//*******************************************************************//
	/**
	 * 获取卷库目录树结构
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/treeNode")
	public TeeJson treeNode(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//获取父目录主键
		json.setRtData(roomService.treeNode(sid,loginUser));
		return json;
	}
	
	
	
	/**
	 * 部门归档树结构
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deptArchiveTreeNode")
	public TeeJson deptArchiveTreeNode(HttpServletRequest request){
		TeePerson loginUser=(TeePerson) request.getSession().getAttribute(TeeConst.LOGIN_USER);
		TeeJson json = new TeeJson();
		json.setRtState(true);
		int sid = TeeStringUtil.getInteger(request.getParameter("id"), 0);//获取父目录主键
		json.setRtData(roomService.deptArchiveTreeNode(sid,loginUser));
		return json;
	}
	
	
	/**
	 * 判断所选择的所属卷库是不是当前编辑卷库的子库
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkIsChildren")
	public TeeJson checkIsChildren(HttpServletRequest request){
		return roomService.checkIsChildren(request);
	}
	
	
	
	/**
	 * 获取当前登陆人又权限的  卷库  以及  卷库下已经归档的卷盒  树结构
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHouseAndBoxTree")
	public TeeJson getHouseAndBoxTree(HttpServletRequest request){
		return roomService.getHouseAndBoxTree(request);
	}
}
