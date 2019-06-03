package com.beidasoft.xzzf.evi.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.evi.bean.ResEvidence;
import com.beidasoft.xzzf.evi.model.ResModel;
import com.beidasoft.xzzf.evi.service.ResService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("/ResController")
public class ResController {
	@Autowired
	private ResService res;
	/**
	 * 保存
	 * @param checkModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(ResModel resModel) {
		TeeJson json = new TeeJson();
		ResEvidence resInfo = new ResEvidence();        // 创建实例化实体类对象
		BeanUtils.copyProperties(resModel, resInfo);    // 复制model中的字段到 实体类
		resInfo.setGet_time(TeeDateUtil.format(resModel.getGet_time_str(),"yyyy-MM-dd"));
		resInfo.setCreate_time(Calendar.getInstance());
		resInfo.setId(UUID.randomUUID().toString());
		res.save(resInfo);
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
	public TeeJson update(ResModel resModel) {
		TeeJson json = new TeeJson();
		ResEvidence resInfo = res.getById(resModel.getId());
		BeanUtils.copyProperties(resModel, resInfo);
		resInfo.setGet_time(TeeDateUtil.format(resModel.getGet_time_str(),"yyyy-MM-dd"));
		res.update(resInfo);
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
	public TeeJson delete(String id) {
		TeeJson json = new TeeJson();
		res.deleteById(id);
		json.setRtState(true);

		return json;
	}
	/**
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/get")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		ResEvidence resInfo = res.getById(id);
		ResModel resModel = new ResModel();
		BeanUtils.copyProperties(resInfo, resModel);
		resModel.setGet_time_str(TeeDateUtil.format(resInfo.getGet_time(),"yyyy-MM-dd"));
		json.setRtData(resModel);
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
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,
			ResModel queryModel) {
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();
		// 通过分页获取用户信息数据的list集合
		long total = res.getTotal(queryModel);
		List<ResEvidence> resInfos = res.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),queryModel);
		List<ResModel> modelList=new ArrayList<ResModel>();
		for(ResEvidence resInfo:resInfos){
			ResModel resModel=new ResModel();
			BeanUtils.copyProperties(resInfo, resModel);
			resModel.setGet_time_str(TeeDateUtil.format(resInfo.getGet_time(),"yyyy-MM-dd"));
			modelList.add(resModel);
		}
		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);
		return dataGridJson;
	}
}
