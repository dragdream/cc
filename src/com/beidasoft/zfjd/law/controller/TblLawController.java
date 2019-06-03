package com.beidasoft.zfjd.law.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beidasoft.zfjd.law.bean.TblLawInfo;
import com.beidasoft.zfjd.law.model.TblLawModel;
import com.beidasoft.zfjd.law.service.TblLawService;
import com.beidasoft.zfjd.lawManage.controller.LawAdjustReportController;
import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.general.TeeSysCodeManager;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;
import com.tianee.webframe.util.date.TeeDateUtil;
import com.tianee.webframe.util.str.TeeUtility;

@Controller
@RequestMapping("lawInfoController")
public class TblLawController {

	
	@Autowired
	private TblLawService lawService;
	@Autowired
	private TeeAttachmentService attachmentService;
	@Autowired
	private LawAdjustReportController adjustReportController;
	
	/**
	 * 保存
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(TblLawModel lawModel,String attaches){
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		TblLawInfo department = new TblLawInfo();
		BeanUtils.copyProperties(lawModel, department);
		department.setId(UUID.randomUUID().toString());
		//单独处理日期
		department.setPromulgation(TeeDateUtil.format(lawModel.getPromulgationStr(), "yyyy-MM-dd"));
		department.setImplementation(TeeDateUtil.format(lawModel.getImplementationStr(), "yyyy-MM-dd"));

		department.setCreateDate(new Date());
		lawService.save(department);
		
		if(!TeeUtility.isNullorEmpty(attaches)){
			//处理随带的附件
			String sp[] = attaches.split(",");
			for(String attachId:sp){
				TeeAttachment attachment = attachmentService.getById(Integer.parseInt(attachId));
				attachment.setModelId(department.getId()+"");
				attachmentService.updateAttachment(attachment);
			}
		}
		
		
		json.setRtState(true);
		
		return json;
	}

	
	/**
	 * 更新
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(TblLawModel lawModel){
		TeeJson json = new TeeJson();
		
		TblLawInfo userInfo = lawService.getById(lawModel.getId());
		
		//处理创建日期审核日期删除日期
		if(!TeeUtility.isNullorEmpty(userInfo.getCreateDate())){
			lawModel.setCreateDate(userInfo.getCreateDate());
		}
		BeanUtils.copyProperties(lawModel, userInfo);

		userInfo.setCreateDate(lawModel.getCreateDate());
		lawService.update(userInfo);
		json.setRtState(true);

		return json;
	}
	
	/**
	 * 审核
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("examine")
	public TeeJson examine(String id){
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		TblLawInfo department = new TblLawInfo();
		department = lawService.getById(id);
		
		if(!TeeUtility.isNullorEmpty(department.getExamine())){
			if(department.getExamine()==1){
				department.setExamine(0);
			}else{
				department.setExamine(1);
			}
		}
		
		
		lawService.update(department);	
		
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 废止
	 * @param lawModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("abolish")
	public TeeJson abolish(String id){
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		TblLawInfo department = new TblLawInfo();
		department = lawService.getById(id);
		
		department.setTimeliness("02");
		lawService.update(department);	
		
		json.setRtState(true);
		
		return json;
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson dalete(String id){
		TeeJson json = new TeeJson();
		//创建实例化实体类对象
		TblLawInfo department = new TblLawInfo();
		department = lawService.getById(id);
		
		department.setIsDelete(1);
		lawService.update(department);	
		
		json.setRtState(true);
		
		return json;
	}
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(String id){
			TeeJson json = new TeeJson();
			TblLawModel lawModel = new TblLawModel();
			try {
				TblLawInfo lawInfo = lawService.getById(id);
				BeanUtils.copyProperties(lawInfo, lawModel);
				
				lawModel.setPromulgationStr(TeeDateUtil.format(lawInfo.getPromulgation(), "yyyy-MM-dd"));
				lawModel.setImplementationStr(TeeDateUtil.format(lawInfo.getImplementation(), "yyyy-MM-dd"));
				//获取该用户附件信息
				List<TeeAttachmentModel> attachModels = attachmentService.getAttacheModels("lawInfo", id+"");
				lawModel.setAttachModels(attachModels);
				//法律类别 时效 
				lawModel.setSubmitlawLevel(TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_TYPE", lawInfo.getSubmitlawLevel()));
				lawModel.setTimeliness(TeeSysCodeManager.getChildSysCodeNameCodeNo("LAW_TIMELINESS", lawInfo.getTimeliness()));
				
				json.setRtData(lawModel);
				json.setRtState(true);
			} catch (Exception e) {
				// TODO: handle exception
				json.setRtState(false);
				System.out.println("TblLawController.get"+e.getMessage());
			}
			
			
			return json;
		}
	
	@ResponseBody
	@RequestMapping("findAllUsers")
	public TeeJson findAllUsers(){
		return null;
	}
	/**
	 * 分页
	 * @param dataGridModel
	 * @param queryModel
	 * @return
	 */
	@ResponseBody
    @RequestMapping("listByPage")
    public TeeEasyuiDataGridJson listByPage(TeeDataGridModel dataGridModel,TblLawModel queryModel){
		TeeEasyuiDataGridJson dataGridJson = new TeeEasyuiDataGridJson();

		long total = lawService.getTotal(queryModel);
		List<TblLawModel> modelList = new ArrayList<TblLawModel>();
		List<TblLawInfo> lawInfos = lawService.listByPage(
				dataGridModel.getFirstResult(), dataGridModel.getRows(),
				queryModel);
		for (TblLawInfo lawInfo : lawInfos) {
			TblLawModel infoModel = new TblLawModel();
			BeanUtils.copyProperties(lawInfo,infoModel);
			if (lawInfo.getCreateDate() != null) {
				infoModel.setCreateDateStr(TeeDateUtil.format(lawInfo.getCreateDate(), "yyyy-MM-dd"));
            } else {
            	infoModel.setCreateDateStr("");
            }
			//infoModel.setExamineStr(Integer.toString(userInfo.getExamine()));
			
			modelList.add(infoModel);

		}

		dataGridJson.setTotal(total);
		dataGridJson.setRows(modelList);

		return dataGridJson;

	}

	
}
