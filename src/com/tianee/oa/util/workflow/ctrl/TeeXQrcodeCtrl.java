package com.tianee.oa.util.workflow.ctrl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;

import sun.misc.BASE64Encoder;

import com.runqianapp.dams.report.ResParam;
import com.swetake.util.Qrcode;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLImgTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXQrcodeCtrl extends TeeCtrl{
	
	private static int byteArray[] = null;
	static{
		byteArray = new int[40];
		byteArray[0]=27;
		byteArray[1]=50;
		byteArray[2]=77;
		byteArray[3]=108;
		byteArray[4]=143;
		byteArray[5]=182;
		byteArray[6]=225;
		byteArray[7]=272;
		byteArray[8]=323;
		byteArray[9]=378;
		byteArray[10]=437;
		byteArray[11]=500;
		byteArray[12]=567;
		byteArray[13]=638;
		byteArray[14]=713;
		byteArray[15]=792;
		byteArray[16]=875;
		byteArray[17]=962;
		byteArray[18]=1053;
		byteArray[19]=1148;
		byteArray[20]=1247;
		byteArray[21]=1350;
		byteArray[22]=1457;
		byteArray[23]=1568;
		byteArray[24]=1683;
		byteArray[25]=1802;
		byteArray[26]=1925;
		byteArray[27]=2052;
		byteArray[28]=2183;
		byteArray[29]=2318;
		byteArray[30]=2457;
		byteArray[31]=2600;
		byteArray[32]=2747;
		byteArray[33]=2898;
		byteArray[34]=3053;
		byteArray[35]=3212;
		byteArray[36]=3375;
		byteArray[37]=3542;
		byteArray[38]=3713;
		byteArray[39]=3888;
	}
	
	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		String codeWidth=attrs.get("codewidth");
		String codeHeight=attrs.get("codeheight");
		
		String style="";
		if(!TeeUtility.isNullorEmpty(codeWidth)){
			style+="width:"+codeWidth+"px;";
		}
		if(!TeeUtility.isNullorEmpty(codeHeight)){
			style+="height:"+codeHeight+"px;";
		}
		if(!TeeUtility.isNullorEmpty(style)){
			return "<img src=\"/common/ckeditor/plugins/xqrcode/imgs/code.png\"  style=\""+style+"\"   />";
		}else{
			return "<img src=\"/common/ckeditor/plugins/xqrcode/imgs/code.png\"/>";
		}
		
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRun,flowRunPrcs, formItem);
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		String codeWidth=attrs.get("codewidth");
		String codeHeight=attrs.get("codeheight");
		String saveField=attrs.get("savefield");
		List<TeeFormItem> formItems=form.getFormItems();
		String html="流水号："+flowRun.getRunId();
		if(!TeeUtility.isNullorEmpty(saveField)){
			String[]fields=saveField.split(",");
			if(fields.length>0){
				for(int i=0;i<fields.length;i++){
					for(int j=0;j<formItems.size();j++){
						if((formItems.get(j).getTitle()).equals(fields[i])){
					        html+="\n"+formItems.get(j).getTitle()+":"+datas.get(formItems.get(j).getName());
						    break;
						}
					}
				}
			}
		}
		String baseStr="";
		try {
			baseStr = genarateCode(html);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String style="";
		if(!TeeUtility.isNullorEmpty(codeWidth)){
			style+="width:"+codeWidth+"px;";
		}
		if(!TeeUtility.isNullorEmpty(codeHeight)){
			style+="height:"+codeHeight+"px;";
		}
		
		if(!TeeUtility.isNullorEmpty(style)){
			return "<img src=\"data:image/jpeg;base64,"+baseStr+" \"   style=\""+style+"\"    />";
		}else{
			return "<img src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
		}
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
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
			if("1".equals(ctrl.get("auto"))){
				auto = true;
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
			auto = false;
		}
		
		if(hidden){
			return "";
		}
		
		
		//StringBuffer html = new StringBuffer();
		String codeWidth=attrs.get("codewidth");
		String codeHeight=attrs.get("codeheight");
		String saveField=attrs.get("savefield");
		List<TeeFormItem> formItems=form.getFormItems();
		String html="流水号："+flowRunPrcs.getFlowRun().getRunId();
		if(!TeeUtility.isNullorEmpty(saveField)){
			String[]fields=saveField.split(",");
			if(fields.length>0){
				for(int i=0;i<fields.length;i++){
					for(int j=0;j<formItems.size();j++){
						if((formItems.get(j).getTitle()).equals(fields[i])){
					        html+="\n"+formItems.get(j).getTitle()+":"+datas.get(formItems.get(j).getName());
						    break;
						}
					}
				}
			}
		}
		String baseStr="";
		try {
			baseStr = genarateCode(html);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String style="";
		if(!TeeUtility.isNullorEmpty(codeWidth)){
			style+="width:"+codeWidth+"px;";
		}
		if(!TeeUtility.isNullorEmpty(codeHeight)){
			style+="height:"+codeHeight+"px;";
		}
		
		if(!TeeUtility.isNullorEmpty(style)){
			return "<img src=\"data:image/jpeg;base64,"+baseStr+" \"   style=\""+style+"\"    />";
		}else{
			return "<img src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
		}
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	
	public String  genarateCode(String info) throws Exception{
		Qrcode rcode = new Qrcode();
		
		int dataLength=info.getBytes("UTF-8").length;
		int v = 0;
		for(int i=0;i<byteArray.length;i++){
			if(byteArray[i]>=dataLength){
				v=i+1;
				break;
			}
		}
		
        int width = 67 + 12 * (v - 1);
        int height = 67 + 12 * (v - 1);
		rcode.setQrcodeVersion(v);
		
		byte[] content = null;
		content = info.getBytes("UTF-8");

		// 输出内容> 二维码
		boolean[][] codeOut = null;
		try{
			codeOut = rcode.calQrcode(content);
		}catch(Exception ex){
			for(int i=v+1;i<byteArray.length;i++){
				try{
					v=i;
					rcode.setQrcodeVersion(v);
					codeOut = rcode.calQrcode(content);
					height = 67 + 12 * (v - 1);
					width = 67 + 12 * (v - 1);
					break;
				}catch(Exception ex1){}
			}
		}
		
		BufferedImage bufImg = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB); // 图片的大小
		Graphics2D gs = bufImg.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, width, height);
		gs.setColor(Color.BLACK);
		for (int i = 0; i < codeOut.length; i++) {
			for (int j = 0; j < codeOut.length; j++) {
				if (codeOut[j][i]) {
					gs.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
				}
			}
		}
		gs.dispose();
		bufImg.flush();
		    
		
        // 将图像输出到Servlet输出流中。         
        ByteArrayOutputStream out = new ByteArrayOutputStream();      
        ImageIO.write(bufImg, "jpeg", out);  
        byte b[] = out.toByteArray();
        out.close();
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b);
                
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		//表单控件分析
				TeeHTMLTag tag = new TeeHTMLImgTag();
				tag.getAttributes().put("style", "");
				tag.analyse(formItem.getContent());
				//获取控件属性
				Map<String,String> attrs = tag.getAttributes();
				attrs.put("style", "");
				TeeJsonUtil jsonUtil = new TeeJsonUtil();
				
				String name = formItem.getName();
				String title = formItem.getTitle();
				String data = flowFormData.getData()==null?"":flowFormData.getData();
				String extraData=datas.get("EXTRA_"+formItem.getItemId());
				
				boolean writable = false;//可写
				boolean required = false;//必填
				boolean hidden = false;//可见
				boolean auto = false;//自动赋值
				
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
					if("1".equals(ctrl.get("auto"))){
						auto = true;
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
					auto = false;
				}
				
				if(hidden){
					return "";
				}
				
				
				//StringBuffer html = new StringBuffer();
				String codeWidth=attrs.get("codewidth");
				String codeHeight=attrs.get("codeheight");
				String saveField=attrs.get("savefield");
				List<TeeFormItem> formItems=form.getFormItems();
				String html="流水号："+flowRunPrcs.getFlowRun().getRunId();
				if(!TeeUtility.isNullorEmpty(saveField)){
					String[]fields=saveField.split(",");
					if(fields.length>0){
						for(int i=0;i<fields.length;i++){
							for(int j=0;j<formItems.size();j++){
								if((formItems.get(j).getTitle()).equals(fields[i])){
							        html+="\n"+formItems.get(j).getTitle()+":"+datas.get(formItems.get(j).getName());
								    break;
								}
							}
						}
					}
				}
				String baseStr="";
				try {
					baseStr = genarateCode(html);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String style="";
				if(!TeeUtility.isNullorEmpty(codeWidth)){
					style+="width:"+codeWidth+"px;";
				}
				if(!TeeUtility.isNullorEmpty(codeHeight)){
					style+="height:"+codeHeight+"px;";
				}
				
				if(!TeeUtility.isNullorEmpty(style)){
					return "<img src=\"data:image/jpeg;base64,"+baseStr+" \"   style=\"\"    />";
				}else{
					return "<img src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
				}
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLImgTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		//获取控制模型
		Map<String,String> ctrl = getCtrlModel(flowType, flowRun,flowRunPrcs, formItem);
		
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String extraData=datas.get("EXTRA_"+formItem.getItemId());
		boolean hidden = false;//可见
		if(ctrl!=null){
			if("1".equals(ctrl.get("hidden"))){
				hidden = true;
			}
		}
		
		if(hidden){
			return "";
		}
		
		String codeWidth=attrs.get("codewidth");
		String codeHeight=attrs.get("codeheight");
		String saveField=attrs.get("savefield");
		List<TeeFormItem> formItems=form.getFormItems();
		String html="流水号："+flowRun.getRunId();
		if(!TeeUtility.isNullorEmpty(saveField)){
			String[]fields=saveField.split(",");
			if(fields.length>0){
				for(int i=0;i<fields.length;i++){
					for(int j=0;j<formItems.size();j++){
						if((formItems.get(j).getTitle()).equals(fields[i])){
					        html+="\n"+formItems.get(j).getTitle()+":"+datas.get(formItems.get(j).getName());
						    break;
						}
					}
				}
			}
		}
		String baseStr="";
		try {
			baseStr = genarateCode(html);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String style="";
		if(!TeeUtility.isNullorEmpty(codeWidth)){
			style+="width:"+codeWidth+"px;";
		}
		if(!TeeUtility.isNullorEmpty(codeHeight)){
			style+="height:"+codeHeight+"px;";
		}
		
		if(!TeeUtility.isNullorEmpty(style)){
			return "<img src=\"data:image/jpeg;base64,"+baseStr+" \"   style=\""+style+"\"    />";
		}else{
			return "<img src=\"data:image/jpeg;base64,"+baseStr+"   \"/>";
		}
	}
}
