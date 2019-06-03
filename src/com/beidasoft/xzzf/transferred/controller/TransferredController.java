package com.beidasoft.xzzf.transferred.controller;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.lawCheck.bean.BaseLawCheck;
import com.beidasoft.xzzf.lawCheck.bean.LawCheckItem;
import com.beidasoft.xzzf.lawCheck.model.LawCheckModel;
import com.beidasoft.xzzf.lawCheck.service.LawCheckService;
import com.beidasoft.xzzf.transferred.bean.DocTransferred;
import com.beidasoft.xzzf.transferred.model.TransferredModel;
import com.beidasoft.xzzf.transferred.service.TransferredService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/transferredController")
public class TransferredController {
	@Autowired
	private TransferredService transferredService;
	

	/**
	 * 保存
	 * @param transferredModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
 public TeeJson save (TransferredModel transferredModel){
		TeeJson json=new TeeJson();
		//创建实例化实体类对象
		DocTransferred docTransferred = new DocTransferred();
		//复制model中的字段到   实体类
		BeanUtils.copyProperties(transferredModel, docTransferred);
		// 单独处理时间类型转换
		if (StringUtils.isNotBlank(transferredModel.getTransferredTransTimeStr())) {
			docTransferred.setTransferredTransTime(TeeDateUtil.format(
					transferredModel.getTransferredTransTimeStr(), "yyyy年MM月dd日"));
		}
		//自动生成主键
		docTransferred.setId(UUID.randomUUID().toString());
		transferredService.save(docTransferred);
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
 public TeeJson update (TransferredModel transferredModel){
		
        TeeJson json=new TeeJson();
        
        DocTransferred docTransferred = transferredService.getById(transferredModel.getId());
        BeanUtils.copyProperties(transferredModel, docTransferred);
        
        transferredService.update(docTransferred);
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
 public TeeJson delete (String id){
		TeeJson json=new TeeJson();
		transferredService.deleteById(id);
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
		DocTransferred docTransferred = transferredService.getById(id);
		TransferredModel transferredModel = new TransferredModel();
		BeanUtils.copyProperties(docTransferred, transferredModel);
		if (docTransferred.getTransferredTransTime() != null) {
			transferredModel.setTransferredTransTimeStr(TeeDateUtil.format(
					docTransferred.getTransferredTransTime(), "yyyy年MM月dd日"));
		}
		json.setRtData(transferredModel);
	    return json;
 }
 
	/**
	 * 获取gistDataGrid List
	 * @param id
	 * @param dataGridModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getItemDataGrid")
	public TeeEasyuiDataGridJson getItemDataGrid(
			TeeDataGridModel dataGridModel, TransferredModel transferredModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		List<LawCheckItem> itemList = transferredService.getBylawListById(transferredModel.getId(), dataGridModel);
		long total = transferredService.getTotal(transferredModel.getId()); // 根据传来的条件
		dataGridJson.setRows(itemList);
		dataGridJson.setTotal(total);
		return dataGridJson;
	}

	/**
	 * 分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listByPage")                                             
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,TransferredModel transferredModel){
		TeeEasyuiDataGridJson dataGridJson=new TeeEasyuiDataGridJson();
		//通过分页获取用户信息数据的list集合
//		long total=transferredService.getTotal(transferredModel);
		List<DocTransferred> docTransferreds = transferredService.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),transferredModel);
//		dataGridJson.setTotal(total);
		dataGridJson.setRows(docTransferreds);
		return dataGridJson;
	}
 }
