package com.tianee.oa.util.workflow.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Encoder;

import com.tianee.oa.core.attachment.bean.TeeAttachment;
import com.tianee.oa.core.attachment.model.TeeAttachmentModel;
import com.tianee.oa.core.attachment.service.TeeAttachmentService;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.oaconst.TeeAttachmentModelKeys;
import com.tianee.webframe.util.global.TeeBeanFactory;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXImgCtrl extends TeeCtrl{

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		String data = flowFormData.getData()==null?"":flowFormData.getData();

		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		StringBuffer attaches = new StringBuffer();
		if("".equals(data)){
			attaches.append("<img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"/common/images/workflow/imgupload.png\" />");
		}else{
			TeeAttachmentService attachService=(TeeAttachmentService) TeeBeanFactory.getBean("teeAttachmentService");
			TeeAttachment att=attachService.getById(TeeStringUtil.getInteger(data, 0));
			String ext=att.getExt().toLowerCase();
			String format="jpeg";
			if("jpg".equals(ext)||"jpeg".equals(ext)){
				format="jpeg";
			}else if("png".equals(ext)){
				format="png";
			}else if("gif".equals(ext)){
				format="gif";
			}else if("bmp".equals(ext)){
				format="bmp";
			}
			String filePath=att.getFilePath();
			byte[] b=null;
			FileInputStream in=null;
			try {
				in = new FileInputStream(new File(filePath));  
				b = new byte[in.available()]; 
				in.read(b);           
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			BASE64Encoder encoder = new BASE64Encoder();
			String base64Str=encoder.encode(b);
			attaches.append("<img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"data:image/"+format+";base64,"+base64Str+" \" />");
		}
		
		return attaches.toString();
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		if(ctrl!=null){
			if("1".equals(ctrl.get("writable"))){
				writable = true;
			}
			if("1".equals(ctrl.get("required"))){
				required = true;		
			}
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		
		StringBuffer attaches = new StringBuffer("<input type=\"hidden\" id=\""+name+"\" name=\""+name+"\" xtype=\"ximg\" "+(writable?"writable='writable'":"")+" value=\""+data+"\"/>");
		if("".equals(data)){
			attaches.append("<span style='display:none' id=\"xuploadDivTmp\"></span><div style='position:relative;display:inline-block' id=\"ximguploader"+formItem.getItemId()+"\"><img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"/common/images/workflow/imgupload.png\" /></div>");
		}else{
			attaches.append("<span style='display:none' id=\"xuploadDivTmp\"></span><div style='position:relative;display:inline-block' id=\"ximguploader"+formItem.getItemId()+"\"><img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"/attachmentController/downFile.action?id="+data+"\" /></div>");
		}
		
		return attaches.toString();
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
	// TODO Auto-generated method stub
		
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		attrs.put("style", "");
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		if(ctrl!=null){
			if("1".equals(ctrl.get("writable"))){
				writable = true;
			}
			if("1".equals(ctrl.get("required"))){
				required = true;		
			}
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
		//附加条件，如果当前为经办人的话，则没有权限动表单
		if(flowRunPrcs.getTopFlag()==0){
			writable = false;
			required = false;
		}
		
		if(hidden){
			return "";
		}
		
		
		StringBuffer attaches = new StringBuffer("<input type=\"hidden\" id=\""+name+"\" name=\""+name+"\" xtype=\"ximg\" "+(writable?"writable='writable'":"")+" value=\""+data+"\"/>");
		if("".equals(data)){
			attaches.append("<span style='display:none' id=\"xuploadDivTmp\"></span><div style='position:relative;display:inline-block' id=\"ximguploader"+formItem.getItemId()+"\"><img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"/common/images/workflow/imgupload.png\" /></div>");
		}else{
			attaches.append("<span style='display:none' id=\"xuploadDivTmp\"></span><div style='position:relative;display:inline-block' id=\"ximguploader"+formItem.getItemId()+"\"><img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"/attachmentController/downFile.action?id="+data+"\" /></div>");
		}
		
		return attaches.toString();
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		String data = flowFormData.getData()==null?"":flowFormData.getData();

		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		StringBuffer attaches = new StringBuffer();
		if("".equals(data)){
			attaches.append("<img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"/common/images/workflow/imgupload.png\" />");
		}else{
			TeeAttachmentService attachService=(TeeAttachmentService) TeeBeanFactory.getBean("teeAttachmentService");
			TeeAttachment att=attachService.getById(TeeStringUtil.getInteger(data, 0));
			String ext=att.getExt().toLowerCase();
			String format="jpeg";
			if("jpg".equals(ext)||"jpeg".equals(ext)){
				format="jpeg";
			}else if("png".equals(ext)){
				format="png";
			}else if("gif".equals(ext)){
				format="gif";
			}else if("bmp".equals(ext)){
				format="bmp";
			}
			String filePath=att.getFilePath();
			byte[] b=null;
			FileInputStream in=null;
			try {
				in = new FileInputStream(new File(filePath));  
				b = new byte[in.available()]; 
				in.read(b);           
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			BASE64Encoder encoder = new BASE64Encoder();
			String base64Str=encoder.encode(b);
			attaches.append("<img id=\"img"+formItem.getItemId()+"\" "+(attrs.get("height")==null?"":"height='"+attrs.get("height")+"'")+" "+(attrs.get("width")==null?"":"width='"+attrs.get("width")+"'")+" src=\"data:image/"+format+";base64,"+base64Str+" \" />");
		}
		
		return attaches.toString();
	}

}
