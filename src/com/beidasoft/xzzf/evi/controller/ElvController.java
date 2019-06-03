package com.beidasoft.xzzf.evi.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.xzzf.evi.bean.EleEvidenceBase;
import com.beidasoft.xzzf.evi.model.ElvModel;
import com.beidasoft.xzzf.evi.service.ElvService;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;

@Controller
@RequestMapping("elvController")
public class ElvController {
	@Autowired
	private ElvService elvs;
	@Autowired
	private  TeeAttachmentService attachmentService;//注入附件
	/**
	 * 增加一条数据
	 * 保存的是ElvModel类型的数据，主键自动生成，attaches为附件
	 * @param elvModel
	 * @param attaches
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/save")
	public TeeJson save(ElvModel elvModel,String attaches){
		TeeJson json = elvs.save(elvModel, attaches);
		return json;
	}
	
	/**
	 * 更新数据，参数为ElvModel
	 * @param elvmodel
	 * @param attaches
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public TeeJson update(ElvModel elvmodel,String attaches) {
		TeeJson json = elvs.update(elvmodel, attaches);
		return json;
	}
	
	/**
	 * 获取
	 * 修改一条数据
	 * ElvModel，参数类型为模型id
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getbyid")
	public TeeJson get(String id) {
		TeeJson json = new TeeJson();
		EleEvidenceBase eleinfo=elvs.getbyId(id);
		ElvModel elvmodel=new ElvModel();
		BeanUtils.copyProperties(eleinfo, elvmodel);	
		elvmodel.setGet_time_str(TeeDateUtil.format(eleinfo.getGet_time(),"yyyy-MM-dd"));
		//获取用户的附件信息
		List<TeeAttachmentModel> attachments = attachmentService.getAttacheModels("ddd", id+"");
		elvmodel.setAttachments(attachments);
		json.setRtData(elvmodel);
		return json;
	}
	
	/**
	 * 显示列表数据  以及检索
	 * TeeDataGridModel为DataGrid参数专用模型，searchModel为搜索模型
	 * @param dataGridModel
	 * @param searchModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listbypagegrid")
	public TeeEasyuiDataGridJson listByPage(TeeDataGridModel  dataGridModel,ElvModel searchModel) {//注意这里是easy 专用格式 TeeDataGridModelk 空间传递的参数		
		TeeEasyuiDataGridJson DataGridJson =new TeeEasyuiDataGridJson();//专用返回格式
		List<ElvModel> infoModel=new ArrayList<ElvModel>();
		long total=elvs.gettotal(searchModel);
		List<EleEvidenceBase> userInfos=elvs.listByPage(dataGridModel.getFirstResult(), dataGridModel.getRows(),searchModel);
		for (EleEvidenceBase eleEvidenceBase : userInfos) {
			ElvModel elvmodel=new ElvModel();
			BeanUtils.copyProperties(eleEvidenceBase, elvmodel);
			elvmodel.setGet_time_str(TeeDateUtil.format(eleEvidenceBase.getGet_time(),"yyyy-MM-dd"));
			infoModel.add(elvmodel);
		}
		DataGridJson.setTotal(total);
		DataGridJson.setRows(infoModel);
		return DataGridJson;
	}
	/**
	 * 逻辑删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateDelete")
	public TeeJson updateDelete(String id) {
		TeeJson json = elvs.updateDelete(id);
		return json;
	}
	
	/**
	 * 证据固化
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/fileCuring")
	public TeeJson fileCuring(String id) throws IOException {
		TeeJson json = elvs.fileCuring(id);
		return json;
	}
}
