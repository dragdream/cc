package com.tianee.oa.util.workflow.ctrl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mvel2.MVEL;

import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowProcess;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeFlowType;
import com.tianee.oa.core.workflow.flowmanage.bean.TeeListCtrlExtend;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRun;
import com.tianee.oa.core.workflow.flowrun.bean.TeeFlowRunPrcs;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFlowFormData;
import com.tianee.oa.core.workflow.formmanage.bean.TeeForm;
import com.tianee.oa.core.workflow.formmanage.bean.TeeFormItem;
import com.tianee.oa.util.workflow.TeeColumnType;
import com.tianee.webframe.util.global.TeeSysProps;
import com.tianee.webframe.util.str.TeeJsonUtil;
import com.tianee.webframe.util.str.TeeStringUtil;
import com.tianee.webframe.util.str.TeeUtility;
import com.tianee.webframe.util.str.expReplace.TeeHTMLInputTag;
import com.tianee.webframe.util.str.expReplace.TeeHTMLTag;

public class TeeXListCtrl extends TeeCtrl{
	
	@Override
	public int getCtrlColumnType(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		
		return formItem.getColumnType();
	}

	@Override
	public String getCtrlColumnTypeName(TeeFormItem formItem) {
		// TODO Auto-generated method stub
		return TeeColumnType.getColumnType(getCtrlColumnType(formItem));
	}

	@Override
	public String getCtrlHtml4Design(TeeFormItem formItem) {
		TeeJsonUtil json = new TeeJsonUtil();
		StringBuffer sb = new StringBuffer();
		boolean addable = false;//添加模式
		boolean deletable = false;//删除模式
		boolean editable = false;//修改模式
		
		String model = formItem.getModel();
		
		//获取列表扩展
		String listCtlExtModel = "[]";
		
		addable = true;
		deletable = true;
		editable = true;
		
		boolean oper = false;
		
		//列表控件模型
		List<Map<String,String>> modelList = json.JsonStr2MapList(model);
		
		//模拟列表属性
		List<Map<String,String>> ctrlModelExt = new ArrayList();
		for(Map<String,String> m:modelList){
			m.put("writable", "1");
			ctrlModelExt.add(m);
		}
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		//渲染表头
		sb.append("<table "+(addable?" addable":" ")+(deletable?" deletable":" ")+(editable?" editable":"")+" xtype=\"xlist\" class=\"xlist_tb\" id=\"XLIST_"+formItem.getItemId()+"\" dataId=\"DATA_"+formItem.getItemId()+"\" model=\""+model.replace("\"", "\\\"")+"\"  dataStyle=\""+attrs.get("data")+"\" ctrlModel=\""+listCtlExtModel.replace("\"", "'")+"\" style=\"width:"+attrs.get("rows")+";\">");
		sb.append("<thead class=\"xlist_thead\">");
		sb.append("<tr>");
		for(Map<String,String> item:modelList){
			renderColumnTitle(sb,item.get("title"),ctrlModelExt,addable,editable,deletable,attrs);
		}
		if(addable || deletable || editable){
			oper = true;
			sb.append("<td style=\""+attrs.get("header")+"\">操作</td>");
		}
		sb.append("</tr>");
		sb.append("</thead>");
		
		//渲染数据
		sb.append("<tbody "+(oper?"oper":"")+" class=\"xlist_tbody\" id=\"xlist_tbody_"+formItem.getItemId()+"\" dataStyle=\""+attrs.get("data")+"\">");
		String data = "[{},{},{}]";
		int col = 1;//列递增号
		int row = 1;//行递增号
		
		
		if(data!=null && !"".equals(data)){
			data = data.replace("\n", "\\n");
			//解析数据格式
			List<Map<String,String>> dataList = json.JsonStr2MapList(data);
			String tmp = null;
			for(Map<String,String> dataItem:dataList){
				sb.append("<tr index=\""+(row++)+"\" bisKey=\""+TeeStringUtil.getString(dataItem.get("bisKey"))+"\">");
				for(Map<String,String> item:modelList){
					tmp = dataItem.get(item.get("title"));
					tmp = tmp==null?"":tmp;
					renderColumn(sb,item,tmp,ctrlModelExt,col++,addable,editable,deletable,attrs,formItem.getItemId());
				}
				//渲染列操作按钮
				if(deletable){
					sb.append("<td oper=\"oper\" style=\""+attrs.get("data")+"\"><input type=\"button\" value=\"删除\" onclick=\"deleteRow(this)\" /></td>");
				}else if(addable || deletable || editable){
					sb.append("<td oper=\"oper\" style=\""+attrs.get("data")+"\"></td>");
				}
				sb.append("</tr>");
				col=1;
			}
		}
		
		//渲染操作按钮
		sb.append("</tbody>");
		sb.append("<tfoot id=\"xlist_tfoot_"+formItem.getItemId()+"\">");
		sb.append("<tr index=\""+1+"\">");
		int cols = oper?modelList.size()+1:modelList.size();
		for(int i=0;i<cols;i++){
			if(addable && i==cols-1){
				sb.append("<td style=\""+attrs.get("data")+"\">");
				if((TeeStringUtil.getInteger(attrs.get("type"),0))==1&&!"".equals(attrs.get("dfid")) && attrs.get("dfid")!=null){//可以进行选择视图标识
					sb.append(""
							+ "<span class='mobilexlistoper'><button type=\"button\" style=\"height:20px;line-height:5px;margin-top:5px\" class=\"\" onclick=\"selectListDataSource('"+attrs.get("dfid")+"','"+formItem.getItemId()+"','"+formItem.getTitle()+"')\">选择</button></span>"
							);
				}else if((TeeStringUtil.getInteger(attrs.get("type"),0))==2 && TeeStringUtil.getInteger(attrs.get("flowid"),0)!=0 && !TeeUtility.isNullorEmpty(attrs.get("mappingstr")) ){//流程数据选择
					sb.append(""
							+ "<span class='mobilexlistoper'><button  flowid='"+attrs.get("flowid")+"'  mappingstr="+attrs.get("mappingstr")+"   itemid='"+formItem.getItemId()+"'    type=\"button\" style=\"height:20px;line-height:5px;margin-top:5px\" class=\"\" onclick=\"selectFlowDataSourceXList(this)\">选择</button></span>"
							);
				}else{
					sb.append("<span class='mobilexlistoper'><button type=\"button\" style=\"height:20px;line-height:5px\" class=\"\" onclick=\"addRow("+formItem.getItemId()+")\">添加</button>"
							+ "");
					sb.append("<form enctype='multipart/form-data' method='post' action='"+TeeSysProps.getString("contextPath")+"/flowRun/uploadXlistDatas.action' target='xListDataUploadIframe' style='position:relative'>");
					sb.append("<input type='file' name='file' style='opacity:0;filter:Alpha(opacity=0);position:absolute;left:0px;top:10px;width:100px' onchange=\"doUploadXlistData(this)\"/>");
					sb.append("<input type='hidden' name='itemId' value='"+formItem.getItemId()+"'/>");
					sb.append("<br/><a style='font-size:12px'>上传Excel</a>");
					sb.append("</form></span>");
				}
				sb.append("</td>");
			}else{
				sb.append("<td style=\""+attrs.get("data")+"\" index=\""+(i+1)+"\"></td>");
			}
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tfoot>");
		
		sb.append("</table><input  title=\""+formItem.getTitle()+"\" type=\"hidden\" name=\"DATA_"+formItem.getItemId()+"\" id=\"DATA_"+formItem.getItemId()+"\" "+((addable || deletable || editable)?"writable":"disabled")+">");
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4Print(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,TeeFlowRun flowRun,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		StringBuffer sb = new StringBuffer();

		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		String width = attrs.get("rows");
		String header = attrs.get("header");
		String dataStyle = attrs.get("data");
		
		//列表控件模型
		List<Map<String,String>> modelList = json.JsonStr2MapList(model);
		
		//渲染表头
		sb.append("<table width=\""+width+"\" header=\""+header+"\" dataStyle=\""+dataStyle+"\" xtyle=\"xlist\" class=\"xlist_tb\" id=\"XLIST_"+flowFormData.getItemId()+"\" dataId=\"DATA_"+flowFormData.getItemId()+"\" model=\""+model+"\" style=\"width:"+width+"\">");
		sb.append("<thead class=\"xlist_thead\">");
		sb.append("<tr>");
		boolean hasColSum = false;
		
		for(Map<String,String> item:modelList){
			sb.append("<td style=\""+header+"\">"+item.get("title")+"</td>");
			if("1".equals(item.get("sum"))){
				hasColSum = true;
			}
		}
		sb.append("</tr>");
		sb.append("</thead>");
		
		//渲染数据
		sb.append("<tbody  class=\"xlist_tbody\" id=\"xlist_tbody_"+flowFormData.getItemId()+"\">");
		String data = flowFormData.getData();
		int col = 1;//列递增号
		int row = 1;//行递增号
		String dataStyleSingle = null;//数据单元格样式（每列指定）
		
		List<Map<String,String>> dataList = new ArrayList();
		if(data!=null && !"".equals(data)){
			data = data.replace("\\n", "<br/>");
			//解析数据格式
			dataList = json.JsonStr2MapList(data);
			String tmp = null;
			for(Map<String,String> dataItem:dataList){
				sb.append("<tr index=\""+(row++)+"\">");
				for(Map<String,String> item:modelList){
					tmp = dataItem.get(item.get("title"));
					tmp = tmp==null?"":tmp;
					dataStyleSingle = item.get("style");
					if(!"".equals(dataStyleSingle)){
						sb.append("<td style=\""+dataStyleSingle+"\">"+tmp+"</td>");
					}else{
						sb.append("<td style=\""+dataStyle+"\">"+tmp+"</td>");
					}
				}
				sb.append("</tr>");
				col=1;
			}
		}
		
		//渲染操作按钮
		sb.append("</tbody>");
		
		if(hasColSum){//如果有合计列，则渲染tfoot
			sb.append("<tfoot>");
			sb.append("<tr>");
			for(Map<String,String> item:modelList){
				int maxBit=0;
				String plusExp = "0";
				dataStyleSingle = item.get("style");
				if(!"".equals(dataStyleSingle)){
					sb.append("<td style=\""+dataStyleSingle+"\">");
				}else{
					sb.append("<td style=\""+dataStyle+"\">");
				}
				boolean sumCol = false;
				for(Map<String,String> dataItem:dataList){
					Set<String> keys = dataItem.keySet();
					for(String key:keys){
						if(key.equals(item.get("title")) && "1".equals(item.get("sum"))){
							try{
								//System.out.println(TeeStringUtil.getDouble(dataItem.get(key), 0)+"");
								//Integer.parseInt(dataItem.get(key));
								String []strArray=(TeeStringUtil.getDouble(dataItem.get(key), 0)+"").split("\\.");
								if(strArray.length==2 && maxBit<strArray[1].length()){
									maxBit = strArray[1].length();
								}
								plusExp += "+"+TeeStringUtil.getDouble(dataItem.get(key), 0);
								
								
								//System.out.println(plusExp);
							}catch(Exception ex){
								plusExp += "+"+0;
							}
							sumCol = true;
						}
					}
				}
				if(sumCol){
					//将合计四舍五
					double sum=(Double) MVEL.eval(plusExp); 
					BigDecimal  b =new   BigDecimal(sum);  
					String sum1 =b.setScale(maxBit, BigDecimal.ROUND_HALF_UP).toString();  
					
					sb.append("合计："+sum1);
				}
				
				sb.append("</td>");
			}
			sb.append("</tr>");
			sb.append("</tfoot>");
		}
		
		sb.append("</table>");
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4Process(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs,Map<String,String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes(); 
				
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		
		StringBuffer sb = new StringBuffer();
		
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		
		//渲染列表
		renderList(sb,model,flowFormData,ctrl,flowType,flowRunPrcs,formItem,attrs.get("data"));
		
		return sb.toString();
	}
	
	/**
	 * 渲染列表控件
	 * @param sb
	 * @param model
	 * @param frd
	 */
	private void renderList(StringBuffer sb,String model,TeeFlowFormData frd,Map<String,String> ctrl,TeeFlowType flowType,TeeFlowRunPrcs frp,TeeFormItem formItem,String dataStyle){
		TeeJsonUtil json = new TeeJsonUtil();
		
		boolean addable = false;//添加模式
		boolean deletable = false;//删除模式
		boolean editable = false;//修改模式
		
		TeeFlowProcess fp = null;
		fp = frp.getFlowPrcs();
		
		//获取列表扩展
		TeeListCtrlExtend listCtlExt = super.getContext().getFlowFormService().getListCtrlExtend(fp==null?0:fp.getSid(), frd.getItemId());
		List<Map<String,String>> ctrlModelExt = null;
		String listCtlExtModel = "[]";
		if(listCtlExt!=null){
			ctrlModelExt = json.JsonStr2MapList(listCtlExt.getColumnCtrlModel());
			listCtlExtModel = listCtlExt.getColumnCtrlModel();
		}
		if(ctrlModelExt==null){
			ctrlModelExt = new ArrayList<Map<String,String>>();
		}
		
		if(ctrl!=null){
			if("1".equals(ctrl.get("addable"))){
				addable = true;
			}
			if("1".equals(ctrl.get("deletable"))){
				deletable = true;		
			}
			if("1".equals(ctrl.get("editable"))){
				editable = true;
			}
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(flowType.getType()==2 && frp.getPrcsId()==1){
			addable = true;
			deletable = true;
			editable = true;
		}
		
		//如果为自由流程并且是第一步发起时，将所有字段设置为可写
		if(frp.getTopFlag()==0){
			addable = false;
			deletable = false;
			editable = false;
		}
		
		boolean oper = false;
		
		//列表控件模型
		List<Map<String,String>> modelList = json.JsonStr2MapList(model);
		
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		//渲染表头
		sb.append("<table "+(addable?" addable":" ")+(deletable?" deletable":" ")+(editable?" editable":"")+" xtype=\"xlist\" class=\"xlist_tb\" id=\"XLIST_"+frd.getItemId()+"\" dataId=\"DATA_"+frd.getItemId()+"\" model=\""+model.replace("\"", "\\\"")+"\" ctrlModel=\""+listCtlExtModel.replace("\"", "'")+"\" style=\"width:"+attrs.get("rows")+"\">");
		sb.append("<thead class=\"xlist_thead\">");
		sb.append("<tr>");
		for(Map<String,String> item:modelList){
			renderColumnTitle(sb,item.get("title"),ctrlModelExt,addable,editable,deletable,attrs);
		}
		if(addable || deletable || editable){
			oper = true;
			sb.append("<td style=\""+attrs.get("header")+"\">操作</td>");
		}
		sb.append("</tr>");
		sb.append("</thead>");
		
		//渲染数据
		sb.append("<tbody "+(oper?"oper":"")+" dataStyle=\""+dataStyle+"\" class=\"xlist_tbody\" id=\"xlist_tbody_"+frd.getItemId()+"\">");
		String data = frd.getData();
		int col = 1;//列递增号
		int row = 1;//行递增号
		
		
		if(data!=null && !"".equals(data)){
			data = data.replace("\n", "\\n");
			//解析数据格式
			List<Map<String,String>> dataList = json.JsonStr2MapList(data);
			String tmp = null;
			for(Map<String,String> dataItem:dataList){
				sb.append("<tr index=\""+(row++)+"\" bisKey=\""+TeeStringUtil.getString(dataItem.get("bisKey"))+"\">");
				for(Map<String,String> item:modelList){
					tmp = dataItem.get(item.get("title"));
					tmp = tmp==null?"":tmp;
					renderColumn(sb,item,tmp,ctrlModelExt,col++,addable,editable,deletable,attrs,formItem.getItemId());
				}
				//渲染列操作按钮
				if(deletable){
					sb.append("<td oper=\"oper\" style=\""+attrs.get("data")+"\"><input type=\"button\" value=\"删除\" onclick=\"deleteRow(this)\" /></td>");
				}else if(addable || deletable || editable){
					sb.append("<td oper=\"oper\" style=\""+attrs.get("data")+"\"></td>");
				}
				sb.append("</tr>");
				col=1;
			}
		}
		
		//渲染操作按钮
		sb.append("</tbody>");
		sb.append("<tfoot id=\"xlist_tfoot_"+frd.getItemId()+"\">");
		sb.append("<tr index=\""+1+"\">");
		int cols = oper?modelList.size()+1:modelList.size();
		for(int i=0;i<cols;i++){
			if(addable && i==cols-1){
				sb.append("<td style=\""+attrs.get("data")+"\">");
				if((TeeStringUtil.getInteger(attrs.get("type"),0))==1&&!"".equals(attrs.get("dfid")) && attrs.get("dfid")!=null){//可以进行选择视图标识
					sb.append(""
							+ "<span class='mobilexlistoper'><button type=\"button\" style=\"height:20px;line-height:5px;margin-top:5px\" class=\"\" onclick=\"selectListDataSource('"+attrs.get("dfid")+"','"+formItem.getItemId()+"','"+formItem.getTitle()+"')\">选择</button></span>"
							);
				}else if((TeeStringUtil.getInteger(attrs.get("type"),0))==2 && TeeStringUtil.getInteger(attrs.get("flowid"),0)!=0 && !TeeUtility.isNullorEmpty(attrs.get("mappingstr")) ){//流程数据选择
					sb.append(""
							+ "<span class='mobilexlistoper'><button  flowid='"+attrs.get("flowid")+"'  mappingstr="+attrs.get("mappingstr")+"   itemid='"+formItem.getItemId()+"'    type=\"button\" style=\"height:20px;line-height:5px;margin-top:5px\" class=\"\" onclick=\"selectFlowDataSourceXList(this)\">选择</button></span>"
							);
				}else{
					sb.append("<br/><button type=\"button\" style=\"height:20px;line-height:5px\" class=\"\" onclick=\"addRow("+frd.getItemId()+")\">添加</button>"
							+ "");

					sb.append("<form enctype='multipart/form-data' method='post' action='"+TeeSysProps.getString("contextPath")+"/flowRun/uploadXlistDatas.action' target='xListDataUploadIframe' style='position:relative'>");
					//sb.append("");

					sb.append("<input type='hidden' name='itemId' value='"+frd.getItemId()+"'/>");
					sb.append("<br/><span class='mobilexlistoper'><a style='font-size:12px'><input type='file' name='file' style='opacity:0;filter:Alpha(opacity=0);position:absolute;left:0px;width:80px;cursor:pointer;' onchange=\"doUploadXlistData(this)\"/>上传Excel</a><br/><a style='font-size:12px;cursor:pointer' onclick=\"downloadExcelTemplate("+formItem.getSid()+")\">下载模版</a></span>");
					sb.append("</form>");
				}
				sb.append("</td>");
			}else{
				sb.append("<td style=\""+attrs.get("data")+"\" index=\""+(i+1)+"\"></td>");
			}
		}
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</tfoot>");
		
		sb.append("</table><input "+(frp.getTopFlag()==0?"disabled":"")+" title=\""+formItem.getTitle()+"\" type=\"hidden\" name=\"DATA_"+frd.getItemId()+"\" id=\"DATA_"+frd.getItemId()+"\" "+((addable || deletable || editable)?"writable":"disabled")+">");
	}
	
	/**
	 * 渲染表头列
	 * @param sb
	 */
	private void renderColumnTitle(StringBuffer sb,String title,List<Map<String,String>> listCtrlExtModel,boolean addable,boolean editable,boolean deletable,Map<String,String> attrs){
		String rendered = "<td class=\"xlist_thead\" style=\""+attrs.get("header")+"\">"+title+"</td>";
		if(listCtrlExtModel!=null){
			for(Map<String,String> model:listCtrlExtModel){
				if(model.get("title").equals(title)){
					if("1".equals(model.get("hidden"))){
						rendered="<td style=\"display:none\">"+title+"</td>";
					}else{
						rendered="<td class=\"xlist_thead\" style=\""+attrs.get("header")+"\">"+title+"</td>";
					}
					break;
				}
			}
		}
		sb.append(rendered);
	}
	
	/**
	 * 渲染数据列
	 * @param sb
	 * @param title
	 * @param data
	 * @param listCtrlExtModel
	 * @param addable
	 * @param editable
	 * @param deletable
	 */
	private void renderColumn(StringBuffer sb,Map<String,String> _model,String data,List<Map<String,String>> listCtrlExtModel,int col,boolean addable,boolean editable,boolean deletable,Map<String,String> attrs,int itemId){
		
		boolean writable = false;//可写
		boolean required = false;//必填
		boolean hidden = false;//是否隐藏
		
		String rendered = "<td index=\""+(col)+"\" style=\""+attrs.get("data")+"\">"+(renderColumnByCtrlType(_model,data,null,col,writable,required,hidden,editable,itemId))+"</td>";
		
		if(listCtrlExtModel!=null){
			for(Map<String,String> model:listCtrlExtModel){
				if(model.get("title").equals(_model.get("title"))){
					if("1".equals(model.get("required"))){//是否必填
						required = true;
					}
					if("1".equals(model.get("writable"))){//是否可写
						writable = true;
					}
					if("1".equals(model.get("hidden"))){//是否隐藏
						hidden = true;
					}
					if(!TeeUtility.isNullorEmpty(_model.get("style"))){
						rendered="<td "+(required?"required":"")+" "+(hidden?"style=\"display:none\" ":"")+" style=\""+_model.get("style")+"\" index=\""+(col)+"\">";
					}else{
						rendered="<td "+(required?"required":"")+" "+(hidden?"style=\"display:none\" ":"")+" style=\""+attrs.get("data")+"\"  index=\""+(col)+"\">";
					}
					
					rendered+=renderColumnByCtrlType(_model,data,model,col,writable,required,hidden,editable,itemId);
					rendered+="</td>";
					break;
				}
			}
		}
		sb.append(rendered);
	}
	
	private String renderColumnByCtrlType(Map<String,String> model,String data,Map<String,String> ctrlModel,int col,boolean writable,boolean required,boolean hidden,boolean editable,int itemId){
		StringBuffer sb = new StringBuffer();
		
		String type = model.get("type");
		String valueModel = model.get("model");
		long rand = System.nanoTime();
		
		String eventStr = "";
		String eventScript = "";
		String eventType = "";
		
		if(ctrlModel!=null){
			eventScript = ctrlModel.get("eventScript");
			eventType = ctrlModel.get("eventType");
		}
		
		if("1".equals(eventType)){
			eventStr = "onClick=\""+eventScript+"\"";
		}else if("2".equals(eventType)){
			eventStr = "onFocus=\""+eventScript+"\"";
		}else if("3".equals(eventType)){
			eventStr = "onBlur=\""+eventScript+"\"";
		}else if("4".equals(eventType)){
			eventStr = "onChange=\""+eventScript+"\"";
		}else if("5".equals(eventType)){
			eventStr = "onKeyDown=\""+eventScript+"\"";
		}else if("6".equals(eventType)){
			eventStr = "onKeyUp=\""+eventScript+"\"";
		}else if("7".equals(eventType)){
			eventStr = "onKeyPress=\""+eventScript+"\"";
		}else if("8".equals(eventType)){
			eventStr = "onMouseDown=\""+eventScript+"\"";
		}else if("9".equals(eventType)){
			eventStr = "onMouseUp=\""+eventScript+"\"";
		}
		
		
		if("1".equals(type)){//单行输入框
			sb.append("<input type=\"text\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}
			
			sb.append("/>");
		}else if("2".equals(type)){//多行文本框
			sb.append("<textarea "+eventStr+"  style=\"width:"+model.get("width")+"\" ");
			if(!editable){
				sb.append(" readonly class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}
			sb.append(">"+data+"</textarea>");
		}else if("3".equals(type)){//下拉框
			sb.append("<select "+eventStr+"  style=\"width:"+model.get("width")+"\" ");
			if(!editable){
				sb.append(" disabled ");
			}
			if(!writable){
				sb.append(" disabled ");
			}
			
			sb.append(">");
			String sp[] = valueModel.split(",");
			for(String v:sp){
				sb.append("<option value=\""+v+"\" "+(v.equals(data)?"selected":"")+">"+v+"</option>");
			}
			sb.append("</select>");
		}else if("4".equals(type)){//单选框
			
			String sp[] = valueModel.split(",");
			for(String v:sp){
				sb.append("<input "+eventStr+"  "+(editable?"":"disabled")+" "+(writable?"":"disabled")+" type=\"radio\" name=\"rdo"+rand+"\" value=\""+v+"\" "+(v.equals(data)?"checked":"")+"/>"+v);
			}
		}else if("5".equals(type)){//多选框
			if(ctrlModel!=null){
				sb.append("");
			}
			String sp[] = valueModel.split(",");
			boolean exist = false;
			for(String v:sp){
				exist = false;
				String dd[] = data.split(",");
				for(String d:dd){
					if(d.equals(v)){
						exist = true;
						break;
					}
				}
				sb.append("<input "+eventStr+"  "+(editable?"":"disabled")+" "+(writable?"":"disabled")+" type=\"checkbox\" name=\"cbx"+rand+"\" value=\""+v+"\" "+(exist?"checked":"")+"/>"+v);
			}
		}else if("6".equals(type)){//序号列
			if(ctrlModel!=null){
				sb.append("");
			}
			sb.append(data);
		}else if("7".equals(type)){//日期控件
			sb.append("<input type=\"text\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly Wdate\" ");
			}else{
				sb.append(" class=\"Wdate\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly Wdate\" ");
			}else{
				sb.append("  class=\"Wdate\" ");
			}
			
			if(!"".equals(valueModel)){
				sb.append("onfocus=\"WdatePicker({isShowClear:false,readOnly:true,dateFmt:'"+valueModel+"'})\"");
			}else{
				sb.append("onfocus=\"WdatePicker({isShowClear:false,readOnly:true})\"");
			}
			
			sb.append("/>");
		}else if("8".equals(type)){//数据选择
			sb.append("<input type=\"text\" value=\""+data+"\"   valueModel='"+valueModel+"' "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" onclick=\"xListItemSelectData(this,"+itemId+")\" ");
			}
			
			sb.append("/>");
		}else if("9".equals(type)){//人员单选
			String xListItemId = "XLIST_ITEM_ID_"+System.nanoTime();
			sb.append("<input type=\"text\" id=\""+xListItemId+"\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" readonly onclick=\"selectSingleUser(['"+xListItemId+"','"+xListItemId+"'])\" ");
			}
			sb.append("/>");
		}else if("10".equals(type)){//人员多选
			String xListItemId = "XLIST_ITEM_ID_"+System.nanoTime();
			sb.append("<input type=\"text\" id=\""+xListItemId+"\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" readonly onclick=\"selectUser(['"+xListItemId+"','"+xListItemId+"'])\" ");
			}
			sb.append("/>");	 
		}else if("11".equals(type)){//部门单选
			String xListItemId = "XLIST_ITEM_ID_"+System.nanoTime();
			sb.append("<input type=\"text\" id=\""+xListItemId+"\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" readonly onclick=\"selectSingleDept(['"+xListItemId+"','"+xListItemId+"'])\" ");
			}
			sb.append("/>");	
		}else if("12".equals(type)){//部门多选
			String xListItemId = "XLIST_ITEM_ID_"+System.nanoTime();
			sb.append("<input type=\"text\" id=\""+xListItemId+"\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" readonly onclick=\"selectDept(['"+xListItemId+"','"+xListItemId+"'])\" ");
			}
			sb.append("/>");
		}else if("13".equals(type)){//角色单选
			String xListItemId = "XLIST_ITEM_ID_"+System.nanoTime();
			sb.append("<input type=\"text\" id=\""+xListItemId+"\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" readonly onclick=\"selectSingleRole(['"+xListItemId+"','"+xListItemId+"'])\" ");
			}
			sb.append("/>");
		}else if("14".equals(type)){//角色多选
			String xListItemId = "XLIST_ITEM_ID_"+System.nanoTime();
			sb.append("<input type=\"text\" id=\""+xListItemId+"\" value=\""+data+"\" "+eventStr+" style=\"width:"+model.get("width")+"\"");
			if(!editable || !"".equals(model.get("formula"))){
				sb.append(" readonly  class=\"readonly\" ");
			}
			if(!writable){
				sb.append(" readonly  class=\"readonly\" ");
			}else{
				sb.append(" readonly onclick=\"selectRole(['"+xListItemId+"','"+xListItemId+"'])\" ");
			}
			sb.append("/>");
		}
		
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
		return null;
	}

	@Override
	public String getCtrlHtml4MobileProcess(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRunPrcs flowRunPrcs, Map<String, String> datas) {
		// TODO Auto-generated method stub
		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes(); 
		attrs.put("style", "");
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		
		StringBuffer sb = new StringBuffer();
		
		Map<String,String> ctrl = getCtrlModel(flowType, flowRunPrcs.getFlowRun(),flowRunPrcs, formItem);
		
		//渲染列表
		renderList(sb,model,flowFormData,ctrl,flowType,flowRunPrcs,formItem,attrs.get("data"));
		
		return sb.toString();
	}

	@Override
	public String getCtrlHtml4MobilePrint(TeeFormItem formItem,
			TeeFlowFormData flowFormData, TeeFlowType flowType, TeeForm form,
			TeeFlowRun flowRun, TeeFlowRunPrcs flowRunPrcs,
			Map<String, String> datas) {
		
		String model = formItem.getModel();
		TeeJsonUtil json = new TeeJsonUtil();
		StringBuffer sb = new StringBuffer();

		//表单控件分析
		TeeHTMLTag tag = new TeeHTMLInputTag();
		tag.getAttributes().put("style", "");
		tag.analyse(formItem.getContent());
		//获取控件属性
		Map<String,String> attrs = tag.getAttributes();
		attrs.put("style", "");
		String width = attrs.get("rows");
		String header = attrs.get("header");
		String dataStyle = attrs.get("data");
		
		//列表控件模型
		List<Map<String,String>> modelList = json.JsonStr2MapList(model);
		
		//渲染表头
		sb.append("<table width=\""+width+"\" header=\""+header+"\" dataStyle=\""+dataStyle+"\" xtyle=\"xlist\" class=\"xlist_tb\" id=\"XLIST_"+flowFormData.getItemId()+"\" dataId=\"DATA_"+flowFormData.getItemId()+"\" model=\""+model+"\" style=\"width:"+width+"\">");
		sb.append("<thead class=\"xlist_thead\">");
		sb.append("<tr>");
		boolean hasColSum = false;
		
		for(Map<String,String> item:modelList){
			sb.append("<td style=\""+header+"\">"+item.get("title")+"</td>");
			if("1".equals(item.get("sum"))){
				hasColSum = true;
			}
		}
		sb.append("</tr>");
		sb.append("</thead>");
		
		//渲染数据
		sb.append("<tbody  class=\"xlist_tbody\" id=\"xlist_tbody_"+flowFormData.getItemId()+"\">");
		String data = flowFormData.getData();
		int col = 1;//列递增号
		int row = 1;//行递增号
		String dataStyleSingle = null;//数据单元格样式（每列指定）
		
		List<Map<String,String>> dataList = new ArrayList();
		if(data!=null && !"".equals(data)){
			data = data.replace("\\n", "<br/>");
			//解析数据格式
			dataList = json.JsonStr2MapList(data);
			String tmp = null;
			for(Map<String,String> dataItem:dataList){
				sb.append("<tr index=\""+(row++)+"\">");
				for(Map<String,String> item:modelList){
					tmp = dataItem.get(item.get("title"));
					tmp = tmp==null?"":tmp;
					dataStyleSingle = item.get("style");
					if(!"".equals(dataStyleSingle)){
						sb.append("<td style=\""+dataStyleSingle+"\">"+tmp+"</td>");
					}else{
						sb.append("<td style=\""+dataStyle+"\">"+tmp+"</td>");
					}
				}
				sb.append("</tr>");
				col=1;
			}
		}
		
		//渲染操作按钮
		sb.append("</tbody>");
		
		if(hasColSum){//如果有合计列，则渲染tfoot
			sb.append("<tfoot>");
			sb.append("<tr>");
			for(Map<String,String> item:modelList){
				int maxBit=0;
				String plusExp = "0";
				dataStyleSingle = item.get("style");
				if(!"".equals(dataStyleSingle)){
					sb.append("<td style=\""+dataStyleSingle+"\">");
				}else{
					sb.append("<td style=\""+dataStyle+"\">");
				}
				boolean sumCol = false;
				for(Map<String,String> dataItem:dataList){
					Set<String> keys = dataItem.keySet();
					for(String key:keys){
						if(key.equals(item.get("title")) && "1".equals(item.get("sum"))){
							try{
								//System.out.println(TeeStringUtil.getDouble(dataItem.get(key), 0)+"");
								//Integer.parseInt(dataItem.get(key));
								String []strArray=(TeeStringUtil.getDouble(dataItem.get(key), 0)+"").split("\\.");
								if(strArray.length==2 && maxBit<strArray[1].length()){
									maxBit = strArray[1].length();
								}
								plusExp += "+"+TeeStringUtil.getDouble(dataItem.get(key), 0);
								
								
								//System.out.println(plusExp);
							}catch(Exception ex){
								plusExp += "+"+0;
							}
							sumCol = true;
						}
					}
				}
				if(sumCol){
					//将合计四舍五
					double sum=(Double) MVEL.eval(plusExp); 
					BigDecimal  b =new   BigDecimal(sum);  
					String sum1 =b.setScale(maxBit, BigDecimal.ROUND_HALF_UP).toString();  
					
					sb.append("合计："+sum1);
				}
				
				sb.append("</td>");
			}
			sb.append("</tr>");
			sb.append("</tfoot>");
		}
		
		sb.append("</table>");
		
		return sb.toString();
	}
	
}
