package com.tianee.test.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.attachment.service.TeeBaseUpload;
import com.tianee.oa.core.org.bean.TeeDepartment;
import com.tianee.oa.webframe.httpModel.TeeDataGridModel;
import com.tianee.test.demo.bean.TeeDemo;
import com.tianee.test.demo.model.TeeDemoModel;
import com.tianee.test.demo.service.TeeDemoService;
import com.tianee.webframe.httpmodel.TeeEasyuiDataGridJson;
import com.tianee.webframe.httpmodel.TeeJson;

@Controller
@RequestMapping("demo")
public class TeeDemoController {
	
	@Autowired
	private TeeDemoService demoService;//demo操作的service
	
	@Autowired
	private TeeAttachmentService attachmentService;//附件操作的service
	
	@Autowired
	private TeeBaseUpload baseUpload;//上传相关的service
	
	/**
	 * 保存（带附件）
	 * @param demoModel
	 * @return
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping("save")
	public TeeJson save(TeeDemoModel demoModel,HttpServletRequest request) throws Exception{
		TeeJson json = new TeeJson();
		
		//以表单形式提交，将request强制转换成MultipartHttpServletRequest
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		
		//实例化demo实体
		TeeDemo demo = new TeeDemo();
		//将dto模型的基础字段set到实体中
		BeanUtils.copyProperties(demoModel, demo);
		//特殊处理关联字段department
		TeeDepartment department = new TeeDepartment();
		department.setUuid(demoModel.getDeptId());
		//将关联对象set到实体上
		demo.setDept(department);
		
		//保存实体对象
		demoService.save(demo);
		
		//获取请求中的附件并返回附件对象
		TeeAttachment attachment = baseUpload.singleAttachUpload(multipartHttpServletRequest, "demo");
		if(attachment!=null){//如果存在附件，则将附件的model_id设置为demo实体的主键
			attachment.setModelId(String.valueOf(demo.getSid()));
			//更新附件信息
			attachmentService.updateAttachment(attachment);
		}
		
		
		json.setRtMsg("保存成功");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 更新
	 * @param demoModel
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("update")
	public TeeJson update(TeeDemoModel demoModel,HttpServletRequest request) throws IOException{
		TeeJson json = new TeeJson();
		
		//以表单形式提交，将request强制转换成MultipartHttpServletRequest
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
		
		//实例化demo实体
		TeeDemo demo = new TeeDemo();
		//将dto模型的基础字段set到实体中
		BeanUtils.copyProperties(demoModel, demo);
		//特殊处理关联字段department
		TeeDepartment department = new TeeDepartment();
		department.setUuid(demoModel.getDeptId());
		//将关联对象set到实体上
		demo.setDept(department);
		
		//更新实体对象
		demoService.update(demo);
		
		//判断当前请求中有无附件提交，如果有新的附件，则把之前的附件删除掉
		if(multipartHttpServletRequest.getFileMap().size()!=0){
			//取出之前的旧附件，然后删掉
			List<TeeAttachment> attachList = attachmentService.getAttaches("demo", String.valueOf(demoModel.getSid()));
			for(TeeAttachment attach:attachList){
				attachmentService.deleteAttach(attach);
			}
			
			//上传并保存新的附件
			TeeAttachment attach = baseUpload.singleAttachUpload(multipartHttpServletRequest, "demo");
			attach.setModelId(String.valueOf(demoModel.getSid()));
			attachmentService.updateAttachment(attach);
		}
		
		
		json.setRtMsg("更新成功");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 删除
	 * @param demoModel
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delete")
	public TeeJson delete(TeeDemoModel demoModel){
		TeeJson json = new TeeJson();
		
		//实例化demo实体
		TeeDemo demo = new TeeDemo();
		//将dto模型的基础字段set到实体中
		BeanUtils.copyProperties(demoModel, demo);
		//更新实体对象
		demoService.deleteByObj(demo);
		
		json.setRtMsg("删除成功");
		json.setRtState(true);
		
		return json;
	}
	
	/**
	 * 按条件和分页查询
	 * @param demoModel
	 * @param dm
	 * @return
	 */
	@ResponseBody
	@RequestMapping("query")
	public TeeEasyuiDataGridJson query(TeeDemoModel demoModel,TeeDataGridModel dm){
		return demoService.query(demoModel, dm);
	}
	
	/**
	 * 获取SID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("get")
	public TeeJson get(int sid){
		TeeJson json = new TeeJson();
		//获取实体类
		TeeDemo demo = demoService.getById(sid);
		//将实体类转换成dto模型
		TeeDemoModel demoModel = new TeeDemoModel();
		BeanUtils.copyProperties(demo, demoModel);
		
		demoModel.setDeptId(demo.getDept().getUuid());
		demoModel.setDeptName(demo.getDept().getDeptName());
		
		//获取附件
		List<TeeAttachmentModel> attaches = attachmentService.getAttacheModels("demo", String.valueOf(sid));
		//将附件集合存入model中
		demoModel.setAttachs(attaches);
		
		json.setRtData(demoModel);
		
		return json;
	}
}
