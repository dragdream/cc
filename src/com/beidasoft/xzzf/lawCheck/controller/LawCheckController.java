package com.beidasoft.xzzf.lawCheck.controller;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.lawCheck.bean.BaseLawCheck;
import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.lawCheck.model.LawCheckModel;
import com.beidasoft.xzzf.lawCheck.service.CheckItemService;
import com.beidasoft.xzzf.lawCheck.service.LawCheckService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/LawCheckController")
public class LawCheckController {
	@Autowired
	private LawCheckService  checkService;
	@Autowired
	private CheckItemService  itemService;
	/**
	 * 保存
	 * @param checkModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
 public TeeJson save (LawCheckModel checkModel){
		TeeJson json=new TeeJson();
		//创建实例化实体类对象
		BaseLawCheck check=new BaseLawCheck();
		//复制model中的字段到   实体类
		BeanUtils.copyProperties(checkModel, check);
		//自动生成主键
		check.setId(UUID.randomUUID().toString());
		checkService.save(check);
		json.setRtState(true);
		return json;
 }
	
	/**
	 * 更新
	 * @param checkModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
 public TeeJson update (LawCheckModel checkModel){
		
        TeeJson json=new TeeJson();
        
        BaseLawCheck check=checkService.getById(checkModel.getId());
        BeanUtils.copyProperties(checkModel, check);
        
        checkService.update(check);
		json.setRtState(true);
		return json;
 }
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
 public TeeJson delete (String sid){
		TeeJson json=new TeeJson();
		//List<LawCheckItem> modelList = itemService.getListById(sid);
		//if(modelList.size()!=0){
		//	json.setRtMsg("请先删除检查项");
		//	json.setRtState(false);
		//}else{
			checkService.deleteById(sid);
			json.setRtState(true);
		//}
		return json;
 }
	
	@ResponseBody
	@RequestMapping("/getList")
 public TeeJson getList (String sid){
		TeeJson json=new TeeJson();
		List<LawCheckItem> modelList = itemService.getListById(sid);
		if(modelList.size()!=0){
			json.setRtState(false);
		}else{
			json.setRtState(true);
		}
		return json;
 }
	/**
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
 public TeeJson get(String id){
		TeeJson json=new TeeJson();
		BaseLawCheck check=checkService.getById(id);
		LawCheckModel checkModel= new LawCheckModel();
		BeanUtils.copyProperties(check, checkModel);
		json.setRtData(checkModel);
	    return json;
 }
 
//	/**
//	 * 获取gistDataGrid List
//	 * @param id
//	 * @param dataGridModel
//	 * @return
//	 */
////	@ResponseBody
////	@RequestMapping("/getItemDataGrid")
////	public TeeEasyuiDataGridJson getItemDataGrid(
////			TeeDataGridModel dataGridModel,  LawCheckModel checkModel) {
////		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
////		List<LawCheckItem> itemList = checkService.getBylawListById(checkModel.getId(), dataGridModel);
////		long total = checkService.getTotal(checkModel.getId()); // 根据传来的条件
////		dataGridJson.setRows(itemList);
////		dataGridJson.setTotal(total);
////		return dataGridJson;
////	}

	/**
	 * 分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")                                             
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,LawCheckModel queryModel){
		TeeEasyuiDataGridJson dataGridJson=new TeeEasyuiDataGridJson();
		//通过分页获取用户信息数据的list集合
		long total=checkService.getTotal(queryModel);
		List<BaseLawCheck> checks=checkService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		dataGridJson.setTotal(total);
		dataGridJson.setRows(checks);
		return dataGridJson;
	}
 }
