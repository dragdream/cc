package com.beidasoft.xzzf.lawCheck.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.lawCheck.model.CheckItemModel;
import com.beidasoft.xzzf.lawCheck.service.CheckItemService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("/itemController")
public class CheckItemCollroller {
	@Autowired
	private CheckItemService itemService;
	/**
	 * 保存
	 * @param checkModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
 public TeeJson save (CheckItemModel itemModel){
		TeeJson json=new TeeJson();
		LawCheckItem item=new LawCheckItem();
		BeanUtils.copyProperties(itemModel, item);
		item.setId(UUID.randomUUID().toString());
		itemService.save(item);
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
 public TeeJson update (CheckItemModel itemModel){
		
        TeeJson json=new TeeJson();
        LawCheckItem item= new LawCheckItem();
//        		itemService.getById(checkModel.getId());
        BeanUtils.copyProperties(itemModel, item);
        itemService.update(item);
		json.setRtState(true);
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
		LawCheckItem item=itemService.getById(id);
		CheckItemModel itemModel= new CheckItemModel();
		BeanUtils.copyProperties(item, itemModel);
		json.setRtData(itemModel);
	    return json;
 }
	/**
	 * 分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")                                             
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,CheckItemModel queryModel){
		TeeEasyuiDataGridJson dataGridJson=new TeeEasyuiDataGridJson();
		//通过分页获取用户信息数据的list集合
		List<CheckItemModel> itemModel=new ArrayList();
		long total=itemService.getTotal(queryModel);
		List<LawCheckItem> items=itemService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		for (LawCheckItem lawCheckItem : items) {
			CheckItemModel checkItemModel=new CheckItemModel();
			BeanUtils.copyProperties(lawCheckItem, checkItemModel);
			itemModel.add(checkItemModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(itemModel);
		return dataGridJson;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public TeeJson delete(String id) {
		TeeJson json = new TeeJson();
		itemService.del(id);
		json.setRtState(true);
		return json;
	}
	
}
