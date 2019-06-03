package com.tianee.oa.util.workflow.ctrl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUnicodeUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTextareaTag;
import com.tianee.webframe.util.thread.TeeRequestInfoContext;

public class TeeXFeedbackCtrl extends TeeCtrl{
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
		TeeHTMLTextareaTag tag = new TeeHTMLTextareaTag();
		tag.analyse(formItem.getContent());
		
		StringBuffer sb = new StringBuffer();
		String name = tag.getAttributes().get("name");
		String template = tag.getAttributes().get("template");
		
		sb.append("<textarea id=\""+name+"\" onkeyup=\"$('#FEEDBACK_CTRL_DEMO_CONTENT_"+name+"').html(this.value==''?'{会签内容}':this.value);\" xtype=\"xfeedback\"  required=\""+tag.getAttributes().get("required")+ "\" title=\""+tag.getAttributes().get("title")+ "\" style=\""+tag.getAttributes().get("style")+"\" template=\""+template+"\">");
		sb.append("{会签控件}");
		sb.append("</textarea><br/>");
		sb.append("<button onclick=\"addSeal('"+name+"')\">盖章测试</button>");
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		String dateFormat=TeeStringUtil.getString(attrs.get("dateformat"));
		if(("").equals(dateFormat)){
			dateFormat="yyyy年MM月dd日  HH时mm分";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		StringBuffer sb = new StringBuffer();
		//获取该流程该控件的会签意见
		int sortField=TeeStringUtil.getInteger(attrs.get("sortfield"),1);//排序字段
		String order=attrs.get("order");//排序方式
		int runId = flowRun.getRunId();
		int itemId = formItem.getItemId();
		List<Map> list = getContext().getWorkflowService().getCtrlFeedbacks(runId, itemId,sortField,order);
		String template = tag.getAttributes().get("template");
		template = TeeUnicodeUtil.unicode2String(template);
		String crTime="";
		String h5float = attrs.get("h5float");
		String h5w = attrs.get("h5w");
		String h5h = attrs.get("h5h");
		
		String mobifloat = attrs.get("mobifloat");
		String mobiw = attrs.get("mobiw");
		String mobih = attrs.get("mobih");
		
		
		
		for(Map fb:list){
			if(fb.get("createTime")!=null){
				crTime=sdf.format(fb.get("createTime"));
			}else{
				crTime="";
			}
			String tmp = template
			.replace("{T}", crTime)
			.replace("{O}", "")
			.replace("{D}", fb.get("deptName")+"")
			.replace("{DD}", fb.get("deptNamePath")+"")
			.replace("{R}", fb.get("roleName")+"")
			.replace("{C}", TeeStringUtil.getString(fb.get("content")).replaceAll("\n", "<br/>"))
			.replace("{IMAGE}", "<img src='"+TeeStringUtil.getString(fb.get("signature"))+"'/>");
			String sealData = fb.get("sealData")+"";
			String hwData = fb.get("hwData")+"";
			String picData = fb.get("picData")+"";
			String h5Data = fb.get("h5Data")+"";
			String mobiData = fb.get("mobiData")+"";
			
			
			String spanOut = "";
			String span = "<span id=\""+"SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand")+"\" style='position:absolute'>";
			
			if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
				//如果签章数据不是空，则不显示人名
				tmp=tmp.replace("{U}","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				
				String sp[] =  sealData.split("\\|");
				String pos[] = sp[1].split(",");
				String size[] = sp[2].split(",");
				String left = pos[0];
				String top = pos[1];
//				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_PNG_SEAL_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\"data:image/gif;base64,"+sp[0]+"\" top=\""+top+"\" left=\""+left+"\"/>");
//				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
//				sb.append("<script>ctrlRandPngArray.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//				sb.append("<script>ctrlRandPngPos.push(\""+left+","+top+"\");</script>");
				
				span+="<img src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+";top:-20px;width:"+size[0]+";height:"+size[1]+"\"  onerror=\"this.style.display = 'none';\"/>";
				
			}else{
				//如果签章数据是空则显示人名
				tmp=tmp.replace("{U}", fb.get("userName")+"");
			}
			
//			if(hwData!=null && !"".equals(hwData) && !"null".equals(hwData)){
//				String sp[] =  hwData.split(";");
//				String pos[] = sp[1].split(",");
//				String left = pos[0];
//				String top = pos[1];
////				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_PNG_HW_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\"data:image/gif;base64,"+sp[0]+"\" top=\""+top+"\" left=\""+left+"\"/>");
////				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
////				sb.append("<script>ctrlRandPngArray.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
////				sb.append("<script>ctrlRandPngPos.push(\""+left+","+top+"\");</script>");
//				
//				span+="<img src=\"data:image/gif;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px\"  onerror=\"this.style.display = 'none';\"/>";
//			}
			
			if(picData!=null && !"".equals(picData) && !"null".equals(picData)){
				String sp[] = picData.replace("\n", "").split(",");
				String top = sp[4];
				String left = sp[3];
//				sb.append("<script>ctrlRandArray4Pic.push(\""+formItem.getName()+"_"+fb.get("rand")+"\");</script>");
//				sb.append("<script>ctrlRandPngArray4Pic.push(\""+picData.replaceAll("\n", "")+"\");</script>");
				span+="<img src=\""+sp[0]+","+sp[1]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px\"  onerror=\"this.style.display = 'none';\"/>";
			}
			
			if(h5Data!=null && !"".equals(h5Data) && !"null".equals(h5Data)){
				String sp[] = h5Data.replace("\n", "").split(",");
				String top = sp[4];
				String left = sp[3];
//				sb.append("<script>ctrlRandArray4Pic.push(\""+formItem.getName()+"_"+fb.get("rand")+"\");</script>");
//				sb.append("<script>ctrlRandPngArray4Pic.push(\""+picData.replaceAll("\n", "")+"\");</script>");
				if("0".equals(h5float)){
					spanOut+="<img src=\""+sp[0]+","+sp[1]+"\" style=\"height:"+h5h+"px;width:"+h5w+"px;max-width:100%\"  onerror=\"this.style.display = 'none';\"/>";
				}else{
					span+="<img src=\""+sp[0]+","+sp[1]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px;height:"+h5h+"px;width:"+h5w+"px\"  onerror=\"this.style.display = 'none';\"/>";
				}
			}
			
			if(mobiData!=null && !"".equals(mobiData) && !"null".equals(mobiData)){
//				sb.append("<script>ctrlRandArray4Pic.push(\""+formItem.getName()+"_"+fb.get("rand")+"\");</script>");
//				sb.append("<script>ctrlRandPngArray4Pic.push(\""+picData.replaceAll("\n", "")+"\");</script>");
				if("0".equals(mobifloat)){
					spanOut+="<img src=\""+mobiData+"\" style=\"height:"+mobih+"px;width:"+mobiw+"px;max-width:100%\"  onerror=\"this.style.display = 'none';\"/>";
				}else{
					span+="<img src=\""+mobiData+"\" style=\"position:absolute;height:"+mobih+"px;width:"+mobiw+"px\"  onerror=\"this.style.display = 'none';\"/>";
				}
			}
			
			span+="</span>";
			
			tmp = tmp.replace("{P}", spanOut+span);
			sb.append(tmp);
			
		}
		
		
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		int userSid = TeeRequestInfoContext.getRequestInfo().getUserSid();//当前登录人主键
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String seal = attrs.get("seal");
		String hand = attrs.get("hand");
		String picseal = attrs.get("picseal");
		String h5hand = attrs.get("h5hand");
		String mobihand = attrs.get("mobihand");
		int sortField=TeeStringUtil.getInteger(attrs.get("sortfield"),1);//排序字段
		String order=attrs.get("order");//排序方式
		
		String dateFormat=TeeStringUtil.getString(attrs.get("dateformat"));
		if(("").equals(dateFormat)){
			dateFormat="yyyy年MM月dd日  HH时mm分";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		
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
		
		//封装是否必填
		tag.getAttributes().put("required", required+"");
		//封装标题到tag里
		tag.getAttributes().put("title", title);
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
//		//附加条件，如果当前为经办人的话，则没有权限动表单
//		if(flowRunPrcs.getTopFlag()==0){
//			writable = false;
//			required = false;
//			auto = false;
//		}
		
		if(hidden){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		//获取该流程该控件的会签意见
		int runId = flowRunPrcs.getFlowRun().getRunId();
		int itemId = formItem.getItemId();
		List<Map> list = getContext().getWorkflowService().getCtrlFeedbacks(runId, itemId,sortField,order);
		String template = tag.getAttributes().get("template");
		String oTemplate = template;
		template = TeeUnicodeUtil.unicode2String(template);
		String crTime="";
		String strText="";
		String imgStr="";
		for(Map fb:list){
			int prcsId=TeeStringUtil.getInteger(fb.get("prcsId"), 0);
			//签章数据
			String sealData = fb.get("sealData")+"";
			
			if(fb.get("createTime")!=null){
				crTime=sdf.format(fb.get("createTime"));
			}else{
				crTime="";
			}
			if(prcsId!=flowRunPrcs.getPrcsId() || !fb.get("userSid").toString().equals(String.valueOf(userSid))){
				String tmp = template
						.replace("{T}",crTime)
						/*.replace("{P}", "<span id=\""+"SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand")+"\"></span>")*/
						.replace("{D}", fb.get("deptName")+"")
						.replace("{DD}", fb.get("deptNamePath")+"")
						.replace("{R}", fb.get("roleName")+"")
						.replace("{C}", TeeStringUtil.getString(fb.get("content")).replaceAll("\n", "<br/>"))
						.replace("{IMAGE}", "<img src='"+TeeStringUtil.getString(fb.get("signature"))+"'/>");
				if(fb.get("userSid").toString().equals(String.valueOf(userSid))){
					tmp = tmp.replace("{O}", "<a href='javascript:void(0)' onclick='delCtrlFeedbackData("+fb.get("sid")+")'>删除</a>");
				}else{
					tmp = tmp.replace("{O}", "");
				}
				
				//如果签章不是空的话  则不显示人名
				/*if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
				tmp=tmp.replace("{U}", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}else{
				tmp=tmp.replace("{U}", fb.get("userName")+"");
			}*/
				
				String span = "<span id=\""+"SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand")+"\" style='position:absolute'>";
				if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
					//如果签章数据不是空，则不显示人名
					tmp=tmp.replace("{U}","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					
					String sp[] =  sealData.split("\\|");
					String pos[] = sp[1].split(",");
					String size[] = sp[2].split(",");
					String left = pos[0];
					String top = pos[1];
//				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_PNG_SEAL_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\"data:image/gif;base64,"+sp[0]+"\" top=\""+top+"\" left=\""+left+"\"/>");
//				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
//				sb.append("<script>ctrlRandPngArray.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//				sb.append("<script>ctrlRandPngPos.push(\""+left+","+top+"\");</script>");
					//imgStr=sp[0];
					span+="<img src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:0px;top:-20px;width:"+size[0]+";height:"+size[1]+"\"  onerror=\"this.style.display = 'none';\"/>";
				}else{
					//如果签章数据是空则显示人名
					tmp=tmp.replace("{U}", fb.get("userName")+"");
				}
				
				span+="</span>";
				
				tmp = tmp.replace("{P}",span);
				//System.out.print(span);
				sb.append(tmp);
				
				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("signData"))+"\"/>");
				sb.append("<input type=\"hidden\" id=\"CTRL_CONTENT_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("content"))+"\"/>");
				sb.append("<input type=\"hidden\" id=\"CTRL_PIC_SIGN_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("picData"))+"\"/>");
				sb.append("<input type=\"hidden\" id=\"CTRL_H5HAND_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("h5Data"))+"\"/>");
				sb.append("<input type=\"hidden\" id=\"CTRL_MOBIHAND_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("mobiData"))+"\"/>");
				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
			}else{
				if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
					String sp[] =  sealData.split("\\|");
					imgStr=sp[0];
				}
				strText=TeeStringUtil.getString(fb.get("content"));
			}
		}
		
		StringBuffer ss = new StringBuffer();
		//ss.append("<textarea id=\""+name+"\" onkeyup=\"$('#FEEDBACK_CTRL_DEMO_CONTENT_"+name+"').html(this.value);\" onfocus=\"$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\"  xtype=\"xfeedback\" "+(writable?"writable":"disabled")+" required=\""+tag.getAttributes().get("required")+ "\" title=\""+tag.getAttributes().get("title")+ "\" style=\""+tag.getAttributes().get("style")+"\" template=\""+oTemplate+"\" h5w=\""+tag.getAttributes().get("h5w")+"\" h5h=\""+tag.getAttributes().get("h5h")+"\" h5float=\""+tag.getAttributes().get("h5float")+"\" mobiw=\""+tag.getAttributes().get("mobiw")+"\" mobih=\""+tag.getAttributes().get("mobih")+"\" mobifloat=\""+tag.getAttributes().get("mobifloat")+"\">");
		ss.append("<textarea ondblclick=\"dbClickFbCtrl("+itemId+");\" id=\""+name+"\"   xtype=\"xfeedback\" "+(writable?"writable":"disabled")+" required=\""+tag.getAttributes().get("required")+ "\" title=\""+tag.getAttributes().get("title")+ "\" style=\""+tag.getAttributes().get("style")+"\" template=\""+oTemplate+"\" h5w=\""+tag.getAttributes().get("h5w")+"\" h5h=\""+tag.getAttributes().get("h5h")+"\" h5float=\""+tag.getAttributes().get("h5float")+"\" mobiw=\""+tag.getAttributes().get("mobiw")+"\" mobih=\""+tag.getAttributes().get("mobih")+"\" mobifloat=\""+tag.getAttributes().get("mobifloat")+"\">");
		ss.append(strText+"</textarea><br/>");
		
		sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_DATA_INPUT_O_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_DATA_INPUT_I_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_DATA_INPUT_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_H5_DATA_INPUT_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_MOBI_DATA_INPUT_"+name+"\" />");
		
		if("1".equals(seal)){
			if(!TeeUtility.isNullorEmpty(imgStr)){
				ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"alert('您已经上传签章！');\" class=\"btn btn-default\" clazz='PC' value=\"电子签章\">");
			}else{
				ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"$('#FEEDBACK_CTRL_DEMO_"+name+"').show();addCtrlSeal('"+name+"');\" class=\"btn btn-default\" clazz='PC' value=\"电子签章\">");
			}
			ss.append("&nbsp;");
		}
		
		if("1".equals(hand)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handCtrlWrite('"+name+"','0x0000FF');$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\" class=\"btn btn-default XFeedBackSealHandWrite\" clazz='PC' value=\"电子签名\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(picseal)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"picCtrlSeal('"+name+"');$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\" class=\"btn btn-default\" value=\"图章\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(h5hand)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"h5CtrlSeal('"+name+"');$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\" class=\"btn btn-default\" value=\"H5手写\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(mobihand)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"mobiCtrlSeal('"+name+"')\" class=\"btn btn-default\"  clazz='MOBILE' value=\"移动签批\">");
			ss.append("&nbsp;");
		}
		
		sb.append(wrap(writable,ss.toString(),"",attrs.get("style"),"",formItem.getItemId(),title));
		
		sb.append("<script>ctrlSignArray.push('"+name+"');</script>");
		
		return sb.toString();
	}

	@Override
	public void initFieldData(TeeFormItem formItem, Map<String, String> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCtrlHtml4Edit(TeeFlowRun flowRun, TeeFormItem formItem,
			TeeFlowFormData flowFormData, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		boolean writable = true;//可写
		boolean required = false;//必填
		boolean hidden = false;//可见
		boolean auto = false;//自动赋值
		
		
		int sortField=TeeStringUtil.getInteger(attrs.get("sortfield"),1);//排序字段
		String order=attrs.get("order");//排序方式
		//将是否必填写到tag
		tag.getAttributes().put("required", required+"");
		
		//将标题写到tag
		tag.getAttributes().put("title", title);
		
		StringBuffer sb = new StringBuffer();
		//获取该流程该控件的会签意见
		int runId = flowRun.getRunId();
		int itemId = formItem.getItemId();
		List<Map> list = getContext().getWorkflowService().getCtrlFeedbacks(runId, itemId,sortField,order);
		String template = tag.getAttributes().get("template");
		template = TeeUnicodeUtil.unicode2String(template);
		for(Map fb:list){
			String tmp = template.replace("{U}", fb.get("userName")+"")
			.replace("{T}", fb.get("createTime")+"")
			.replace("{P}", "SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand"))
			.replace("{C}", fb.get("content")+"");
			
			
			sb.append(tmp);
			sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+fb.get("signData")+"\"/>");
			sb.append("<input type=\"hidden\" id=\"CTRL_CONTENT_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+fb.get("content")+"\"/>");
			sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
		}
		
		sb.append("<textarea id=\""+name+"\"  xtype=\"xfeedback\" "+(writable?"writable":"disabled")+" "+" required=\""+tag.getAttributes().get("required")+ "\" title=\""+tag.getAttributes().get("title")+ "\"   style=\""+tag.getAttributes().get("style")+"\" >");
		sb.append("</textarea>");
		sb.append("<br/><input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"addCtrlSeal('"+name+"')\" class=\"btn btn-default\" value=\"盖章\">");
		sb.append("&nbsp;<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handCtrlWrite('"+name+"','0x0000FF')\" class=\"btn btn-default\" value=\"手写\">");
		sb.append("<script>ctrlSignArray.push('"+name+"');</script>");
		
		return sb.toString();
	
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		int userSid = TeeRequestInfoContext.getRequestInfo().getUserSid();//当前登录人主键
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		String seal = attrs.get("seal");
		String hand = attrs.get("hand");
		String picseal = attrs.get("picseal");
		String h5hand = attrs.get("h5hand");
		String mobihand = attrs.get("mobihand");
		int sortField=TeeStringUtil.getInteger(attrs.get("sortfield"),1);//排序字段
		String order=attrs.get("order");//排序方式
		
		String dateFormat=TeeStringUtil.getString(attrs.get("dateformat"));
		if(("").equals(dateFormat)){
			dateFormat="yyyy年MM月dd日  HH时mm分";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		
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
		
		//封装是否必填
		tag.getAttributes().put("required", required+"");
		//封装标题到tag里
		tag.getAttributes().put("title", title);
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && flowRunPrcs.getPrcsId()==1){
			writable = true;
		}
		
//		//附加条件，如果当前为经办人的话，则没有权限动表单
//		if(flowRunPrcs.getTopFlag()==0){
//			writable = false;
//			required = false;
//			auto = false;
//		}
		
		if(hidden){
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		//获取该流程该控件的会签意见
		int runId = flowRunPrcs.getFlowRun().getRunId();
		int itemId = formItem.getItemId();
		List<Map> list = getContext().getWorkflowService().getCtrlFeedbacks(runId, itemId,sortField,order);
		String template = tag.getAttributes().get("template");
		String oTemplate = template;
		template = TeeUnicodeUtil.unicode2String(template);
		String crTime="";
		for(Map fb:list){
			//签章数据
			String sealData = fb.get("sealData")+"";
			
			if(fb.get("createTime")!=null){
				crTime=sdf.format(fb.get("createTime"));
			}else{
				crTime="";
			}
			
			String tmp = template
			.replace("{T}",crTime)
			/*.replace("{P}", "<span id=\""+"SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand")+"\"></span>")*/
			.replace("{D}", fb.get("deptName")+"")
			.replace("{DD}", fb.get("deptNamePath")+"")
			.replace("{R}", fb.get("roleName")+"")
			.replace("{C}", TeeStringUtil.getString(fb.get("content")).replaceAll("\n", "<br/>"))
			.replace("{IMAGE}", "<img src='"+TeeStringUtil.getString(fb.get("signature"))+"'/>");
			if(fb.get("userSid").toString().equals(String.valueOf(userSid))){
				tmp = tmp.replace("{O}", "<a href='javascript:void(0)' onclick='delCtrlFeedbackData("+fb.get("sid")+")'>删除</a>");
			}else{
				tmp = tmp.replace("{O}", "");
			}
			
			//如果签章不是空的话  则不显示人名
			/*if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
				tmp=tmp.replace("{U}", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}else{
				tmp=tmp.replace("{U}", fb.get("userName")+"");
			}*/
			
			String span = "<span id=\""+"SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand")+"\" style='position:absolute'>";
			if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
				//如果签章数据不是空，则不显示人名
				tmp=tmp.replace("{U}","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				
				String sp[] =  sealData.split("\\|");
				String pos[] = sp[1].split(",");
				String size[] = sp[2].split(",");
				String left = pos[0];
				String top = pos[1];
//				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_PNG_SEAL_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\"data:image/gif;base64,"+sp[0]+"\" top=\""+top+"\" left=\""+left+"\"/>");
//				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
//				sb.append("<script>ctrlRandPngArray.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//				sb.append("<script>ctrlRandPngPos.push(\""+left+","+top+"\");</script>");
				
				span+="<img src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:0px;top:0px;width:"+size[0]+";height:"+size[1]+"\"  onerror=\"this.style.display = 'none';\"/>";
				
			}else{
				//如果签章数据是空则显示人名
				tmp=tmp.replace("{U}", fb.get("userName")+"");
			}
			
            span+="</span>";
			
			tmp = tmp.replace("{P}",span);
			//System.out.print(span);
			sb.append(tmp);
			sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("signData"))+"\"/>");
			sb.append("<input type=\"hidden\" id=\"CTRL_CONTENT_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("content"))+"\"/>");
			sb.append("<input type=\"hidden\" id=\"CTRL_PIC_SIGN_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("picData"))+"\"/>");
			sb.append("<input type=\"hidden\" id=\"CTRL_H5HAND_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("h5Data"))+"\"/>");
			sb.append("<input type=\"hidden\" id=\"CTRL_MOBIHAND_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\""+TeeStringUtil.getString(fb.get("mobiData"))+"\"/>");
			sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
		}
		StringBuffer ss = new StringBuffer();
		ss.append("<textarea class=\"text-area\" id=\""+name+"\" onkeyup=\"$('#FEEDBACK_CTRL_DEMO_CONTENT_"+name+"').html(this.value);\" onfocus=\"$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\"  xtype=\"xfeedback\" "+(writable?"writable":"disabled")+" required=\""+tag.getAttributes().get("required")+ "\" title=\""+tag.getAttributes().get("title")+ "\" style=\"\" template=\""+oTemplate+"\" h5w=\""+tag.getAttributes().get("h5w")+"\" h5h=\""+tag.getAttributes().get("h5h")+"\" h5float=\""+tag.getAttributes().get("h5float")+"\" mobiw=\""+tag.getAttributes().get("mobiw")+"\" mobih=\""+tag.getAttributes().get("mobih")+"\" mobifloat=\""+tag.getAttributes().get("mobifloat")+"\">");
		ss.append("</textarea><br/>");
		sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_DATA_INPUT_O_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_DATA_INPUT_I_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_DATA_INPUT_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_H5_DATA_INPUT_"+name+"\" />");
		sb.append("<input type=\"hidden\" id=\"CTRL_MOBI_DATA_INPUT_"+name+"\" />");
		
		if("1".equals(seal)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"$('#FEEDBACK_CTRL_DEMO_"+name+"').show();addCtrlSeal('"+name+"');\" class=\"btn btn-default\" clazz='PC' value=\"电子签章\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(hand)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"handCtrlWrite('"+name+"','0x0000FF');$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\" class=\"btn btn-default XFeedBackSealHandWrite\" clazz='PC' value=\"电子签名\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(picseal)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"picCtrlSeal('"+name+"');$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\" class=\"btn btn-default\" value=\"图章\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(h5hand)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"h5CtrlSeal('"+name+"');$('#FEEDBACK_CTRL_DEMO_"+name+"').show();\" class=\"btn btn-default\" value=\"H5手写\">");
			ss.append("&nbsp;");
		}
		
		if("1".equals(mobihand)){
			ss.append("<input type=\"button\" "+(writable?"":"style=\"display:none\"")+" onclick=\"mobiCtrlSeal('"+name+"')\" class=\"btn btn-default\"  clazz='MOBILE' value=\"移动签批\">");
			ss.append("&nbsp;");
		}
		
		
		
		sb.append(wrap(writable,ss.toString(),"","","",formItem.getItemId(),title));
		
		sb.append("<script>ctrlSignArray.push('"+name+"');</script>");
	    String sbToString=sb.toString().replaceAll("font-size", "x");
		return sbToString;
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		String dateFormat=TeeStringUtil.getString(attrs.get("dateformat"));
		if(("").equals(dateFormat)){
			dateFormat="yyyy年MM月dd日  HH时mm分";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		
		
		TeeJsonUtil jsonUtil = new TeeJsonUtil();
		
		String name = formItem.getName();
		String title = formItem.getTitle();
		String data = flowFormData.getData()==null?"":flowFormData.getData();
		
		StringBuffer sb = new StringBuffer();
		//获取该流程该控件的会签意见
		int sortField=TeeStringUtil.getInteger(attrs.get("sortfield"),1);//排序字段
		String order=attrs.get("order");//排序方式
		int runId = flowRun.getRunId();
		int itemId = formItem.getItemId();
		List<Map> list = getContext().getWorkflowService().getCtrlFeedbacks(runId, itemId,sortField,order);
		String template = tag.getAttributes().get("template");
		template = TeeUnicodeUtil.unicode2String(template);
		String crTime="";
		String h5float = attrs.get("h5float");
		String h5w = attrs.get("h5w");
		String h5h = attrs.get("h5h");
		
		String mobifloat = attrs.get("mobifloat");
		String mobiw = attrs.get("mobiw");
		String mobih = attrs.get("mobih");
		
		
		
		for(Map fb:list){
			if(fb.get("createTime")!=null){
				crTime=sdf.format(fb.get("createTime"));
			}else{
				crTime="";
			}
			String tmp = template
			.replace("{T}", crTime)
			.replace("{O}", "")
			.replace("{D}", fb.get("deptName")+"")
			.replace("{DD}", fb.get("deptNamePath")+"")
			.replace("{R}", fb.get("roleName")+"")
			.replace("{C}", TeeStringUtil.getString(fb.get("content")).replaceAll("\n", "<br/>"))
			.replace("{IMAGE}", "<img src='"+TeeStringUtil.getString(fb.get("signature"))+"'/>");
			String sealData = fb.get("sealData")+"";
			String hwData = fb.get("hwData")+"";
			String picData = fb.get("picData")+"";
			String h5Data = fb.get("h5Data")+"";
			String mobiData = fb.get("mobiData")+"";
			
			
			String spanOut = "";
			String span = "<span id=\""+"SIGN_POS_CTRL_"+formItem.getName()+"_"+fb.get("rand")+"\" style='position:absolute'>";
			
			if(sealData!=null && !"".equals(sealData) && !"null".equals(sealData)){
				//如果签章数据不是空，则不显示人名
				tmp=tmp.replace("{U}","&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				
				String sp[] =  sealData.split("\\|");
				String pos[] = sp[1].split(",");
				String size[] = sp[2].split(",");
				String left = pos[0];
				String top = pos[1];
//				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_PNG_SEAL_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\"data:image/gif;base64,"+sp[0]+"\" top=\""+top+"\" left=\""+left+"\"/>");
//				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
//				sb.append("<script>ctrlRandPngArray.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
//				sb.append("<script>ctrlRandPngPos.push(\""+left+","+top+"\");</script>");
				
				span+="<img src=\"data:image/png;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+";top:"+top+";width:"+size[0]+";height:"+size[1]+"\"  onerror=\"this.style.display = 'none';\"/>";
				
			}else{
				//如果签章数据是空则显示人名
				tmp=tmp.replace("{U}", fb.get("userName")+"");
			}
			
//			if(hwData!=null && !"".equals(hwData) && !"null".equals(hwData)){
//				String sp[] =  hwData.split(";");
//				String pos[] = sp[1].split(",");
//				String left = pos[0];
//				String top = pos[1];
////				sb.append("<input type=\"hidden\" id=\"CTRL_SIGN_PNG_HW_"+formItem.getName()+"_"+fb.get("rand")+"\" value=\"data:image/gif;base64,"+sp[0]+"\" top=\""+top+"\" left=\""+left+"\"/>");
////				sb.append("<script>ctrlRandArray.push('"+formItem.getName()+"_"+fb.get("rand")+"');</script>");
////				sb.append("<script>ctrlRandPngArray.push(\"data:image/gif;base64,"+sp[0]+"\");</script>");
////				sb.append("<script>ctrlRandPngPos.push(\""+left+","+top+"\");</script>");
//				
//				span+="<img src=\"data:image/gif;base64,"+sp[0]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px\"  onerror=\"this.style.display = 'none';\"/>";
//			}
			
			if(picData!=null && !"".equals(picData) && !"null".equals(picData)){
				String sp[] = picData.replace("\n", "").split(",");
				String top = sp[4];
				String left = sp[3];
//				sb.append("<script>ctrlRandArray4Pic.push(\""+formItem.getName()+"_"+fb.get("rand")+"\");</script>");
//				sb.append("<script>ctrlRandPngArray4Pic.push(\""+picData.replaceAll("\n", "")+"\");</script>");
				span+="<img src=\""+sp[0]+","+sp[1]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px\"  onerror=\"this.style.display = 'none';\"/>";
			}
			
			if(h5Data!=null && !"".equals(h5Data) && !"null".equals(h5Data)){
				String sp[] = h5Data.replace("\n", "").split(",");
				String top = sp[4];
				String left = sp[3];
//				sb.append("<script>ctrlRandArray4Pic.push(\""+formItem.getName()+"_"+fb.get("rand")+"\");</script>");
//				sb.append("<script>ctrlRandPngArray4Pic.push(\""+picData.replaceAll("\n", "")+"\");</script>");
				if("0".equals(h5float)){
					spanOut+="<img src=\""+sp[0]+","+sp[1]+"\" style=\"height:"+h5h+"px;width:"+h5w+"px;max-width:100%\"  onerror=\"this.style.display = 'none';\"/>";
				}else{
					span+="<img src=\""+sp[0]+","+sp[1]+"\" style=\"position:absolute;left:"+left+"px;top:"+top+"px;height:"+h5h+"px;width:"+h5w+"px\"  onerror=\"this.style.display = 'none';\"/>";
				}
			}
			
			if(mobiData!=null && !"".equals(mobiData) && !"null".equals(mobiData)){
//				sb.append("<script>ctrlRandArray4Pic.push(\""+formItem.getName()+"_"+fb.get("rand")+"\");</script>");
//				sb.append("<script>ctrlRandPngArray4Pic.push(\""+picData.replaceAll("\n", "")+"\");</script>");
				if("0".equals(mobifloat)){
					spanOut+="<img src=\""+mobiData+"\" style=\"height:"+mobih+"px;width:"+mobiw+"px;max-width:100%\"  onerror=\"this.style.display = 'none';\"/>";
				}else{
					span+="<img src=\""+mobiData+"\" style=\"position:absolute;height:"+mobih+"px;width:"+mobiw+"px\"  onerror=\"this.style.display = 'none';\"/>";
				}
			}
			
			span+="</span>";
			
			tmp = tmp.replace("{P}", spanOut+span);
			tmp=tmp.replaceAll("font-size", "x");
			sb.append(tmp);
			
		}
		
		
		
		return sb.toString();
	}

}
