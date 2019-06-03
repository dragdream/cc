package com.beidasoft.xzzf.discretionaryPower.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.discretionaryPower.bean.discretionaryDetail;
import com.beidasoft.xzzf.discretionaryPower.bean.discretionaryPower;
import com.beidasoft.xzzf.discretionaryPower.model.discretionaryDetailModel;
import com.beidasoft.xzzf.discretionaryPower.service.discretionaryDetailService;
import com.beidasoft.xzzf.discretionaryPower.service.discretionaryService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("discretionaryController")
public class discretionaryController {

	@Autowired
	private discretionaryService services;
	@Autowired
	private discretionaryDetailService detailservices;
	//自由裁量权信息
	@ResponseBody
	@RequestMapping("listBypage")
	public TeeEasyuiDataGridJson listBypage(TeeDataGridModel  dataGridModel,discretionaryPower power){
		System.out.println("ok");
		TeeEasyuiDataGridJson   dataGridJson = new TeeEasyuiDataGridJson();
		long total = services.getTotal(power); //根据传来的条件 （有或无） 查询总数量
		//根据传来的条件 ，查询符合条件总页数   条件为空 查询总页数
		List<discretionaryPower> userInfos = services.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),power);
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(userInfos);

		return dataGridJson;
	
	}
	
	
	//修改默认根据id获取该用户的信息
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(int id){
		TeeJson json = new TeeJson();
		discretionaryPower userInfo = services.getById(id);
		json.setRtData(userInfo);
		return json;
	}
	
	/**
	 * 存自由裁量权详细信息
	 * @param userInfo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("savedetail")
	@Transactional
	public TeeJson save(discretionaryDetail userInfo,@RequestParam("timeStr") String timeStr){
		TeeJson json = new TeeJson();
		userInfo.setTime(TeeDateUtil.format(timeStr, "yyyy-MM-dd"));
		detailservices.save(userInfo);		
		json.setRtState(true);
		return json;
	}
	
	
	
	
	
	/**
	 * 自由裁量权详细信息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getdetail")
	public TeeJson getdetail(int id){
		TeeJson json = new TeeJson();
		discretionaryDetail userInfo = detailservices.getById(id);
		discretionaryDetailModel model = new discretionaryDetailModel();
		BeanUtils.copyProperties(userInfo, model);
		model.setTimeStr(TeeDateUtil.format(userInfo.getTime(), "yyyy-MM-dd"));
		json.setRtData(model);
		return json;
	}
	/**
	 * 修该自由裁量权信息
	 * @param userInfoModel
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("update")
	@Transactional
	public TeeJson update(discretionaryPower power){
		TeeJson json = new TeeJson();
		discretionaryPower userInfo = services.getById(power.getId());
		BeanUtils.copyProperties(power, userInfo);
		services.update(userInfo);
		json.setRtState(true);
		return json;
		}
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(int id){
		TeeJson json = new TeeJson();
		services.deleteById(id);
		json.setRtState(true);
		return json;
	}
	
	
	
	//自由裁量权详细信息 操作   start
	
	//自由裁量权详细信息
	@ResponseBody
	@RequestMapping("listBypagedetail")
	public TeeEasyuiDataGridJson listBypagedetaildetail(TeeDataGridModel  dataGridModel,discretionaryDetail detail){
		TeeEasyuiDataGridJson   dataGridJson = new TeeEasyuiDataGridJson();
		long total = detailservices.getTotal(detail); //根据传来的条件 （有或无） 查询总数量
		//根据传来的条件 ，查询符合条件总页数   条件为空 查询总页数
		List<discretionaryDetail> userInfos = detailservices.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),detail);
		List<discretionaryDetailModel> modelList = new ArrayList<discretionaryDetailModel>();
		for(discretionaryDetail userInfo:userInfos){
			discretionaryDetailModel model = new discretionaryDetailModel();
			BeanUtils.copyProperties(userInfo, model);
			model.setTimeStr(TeeDateUtil.format(userInfo.getTime(), "yyyy-MM-dd"));
			modelList.add(model);
		}
		
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;
	
	}
	
	/**
	 * 修该自由裁量权详细信息
	 * @param userInfoModel
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("updatedetail")
	@Transactional
	public TeeJson updatedetail(discretionaryDetailModel detail){
		TeeJson json = new TeeJson();
		discretionaryDetail userInfo = detailservices.getById(detail.getId());
		BeanUtils.copyProperties(detail, userInfo);
		userInfo.setTime(TeeDateUtil.format(detail.getTimeStr(), "yyyy-MM-dd"));
		detailservices.update(userInfo);
		json.setRtState(true);
		return json;
		}
	
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deletedetail")
	public TeeJson deletedetail(int id){
		TeeJson json = new TeeJson();
		detailservices.deleteById(id);
		json.setRtState(true);
		return json;
	}
	
	
}
