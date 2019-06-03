<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int prcsId = TeeStringUtil.getInteger(request.getParameter("prcsId"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var prcsId = <%=prcsId%>;
var flowId = <%=flowId%>;

function openListCtrlExtPage(flowPrcsId,formItemId){
	openWindow(contextPath+"/system/core/workflow/flowtype/flowdesign/setprops/listCtrlExtend.jsp?flowPrcsId="+flowPrcsId+"&formItemId="+formItemId,"列表详细设置",700,300);
}

function doInit(){
	//获取对应表单字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	for(var i=0;i<items.length;i++){
		var data = items[i];
		var html = "<tr clazz='dataLine' class='TableData' xtype='"+data.xtype+"' value='"+data.itemId+"'>";
		html += "<td width='130px' style='text-align:center'>"+data.title+"</td>";
		html += "<td style='color:blue' width='70px'>"+data.xtypeDesc+"<a href='#' onclick='openListCtrlExtPage("+prcsId+","+data.sid+")'>"+(data.xtype=='xlist'?"(详细设置)":"")+"</a></td>";
		/* if(data.xtype!="xlist"){
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='writable' itemId='"+data.itemId+"' id='writable_"+data.itemId+"'/></td>";
			html += "<td style='text-align:center' width='50px'><input "+("xcheckbox,xradio".indexOf(data.xtype)!=-1?"style='display:none'":"")+" type='checkbox' clazz='required' itemId='"+data.itemId+"' id='required_"+data.itemId+"' /></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='hidden' itemId='"+data.itemId+"' id='hidden_"+data.itemId+"'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='auto' "+("xmacro".indexOf(data.xtype)==-1 && "xautonumber".indexOf(data.xtype)==-1?"style='display:none'":"")+" itemId='"+data.itemId+"' id='auto_"+data.itemId+"'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='readonly' itemId='"+data.itemId+"' id='readonly_"+data.itemId+"'/></td>";
		}else{
			html += "<td><input type='checkbox' clazz='addable' itemId='"+data.itemId+"' id='addable_"+data.itemId+"'/>(增加模式)</td>";
			html += "<td><input type='checkbox' clazz='editable' itemId='"+data.itemId+"' id='editable_"+data.itemId+"' />(修改模式)</td>";
			html += "<td><input type='checkbox' clazz='deletable' itemId='"+data.itemId+"' id='deletable_"+data.itemId+"'/>(删除模式)</td>";
		} */
		if(data.xtype=="xlist"){//列表控件
			html += "<td><input type='checkbox' clazz='addable' itemId='"+data.itemId+"' id='addable_"+data.itemId+"'/>(增加模式)</td>";
			html += "<td><input type='checkbox' clazz='editable' itemId='"+data.itemId+"' id='editable_"+data.itemId+"' />(修改模式)</td>";
			html += "<td><input type='checkbox' clazz='deletable' itemId='"+data.itemId+"' id='deletable_"+data.itemId+"'/>(删除模式)</td>";
		}else if(data.xtype=="xupload"){//附件上传控件
			html += "<td style='text-align:center' width='50px'><input type='checkbox'  class='ck'  value='1'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox'  class='xz'  value='2'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox'  class='sc' value='4'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox'  class='bj' value='8'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox'  class='sc1' value='16'/></td>";
		}else{
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='writable' itemId='"+data.itemId+"' id='writable_"+data.itemId+"'/></td>";
			html += "<td style='text-align:center' width='50px'><input "+("xcheckbox,xradio".indexOf(data.xtype)!=-1?"style='display:none'":"")+" type='checkbox' clazz='required' itemId='"+data.itemId+"' id='required_"+data.itemId+"' /></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='hidden' itemId='"+data.itemId+"' id='hidden_"+data.itemId+"'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='auto' "+("xmacro".indexOf(data.xtype)==-1 && "xautonumber".indexOf(data.xtype)==-1?"style='display:none'":"")+" itemId='"+data.itemId+"' id='auto_"+data.itemId+"'/></td>";
			html += "<td style='text-align:center' width='50px'><input type='checkbox' clazz='readonly' itemId='"+data.itemId+"' id='readonly_"+data.itemId+"'/></td>";
		}
		html +="</tr>";
		/* if(data.xtype!="xlist"){
			$(html).appendTo($("#renderBody"));
		}else{
			$(html).appendTo($("#renderBody1"));
		} */
		
		if(data.xtype=="xlist"){
			$(html).appendTo($("#renderBody1"));
		}else if(data.xtype=="xupload"){
			$(html).appendTo($("#renderBody2"));
		}else{
			$(html).appendTo($("#renderBody"));
		}
	}
	

	//获取该步骤的字段控制模型
	var url = contextPath+"/flowProcess/getFieldCtrlModel.action";
	var json = tools.requestJsonRs(url,{prcsId:prcsId});
	var model;
	if(json.rtState){
		model = eval("("+json.rtData.fieldCtrlModel+")");
	}

	//如果存在model，则进行渲染
	if(model){
		for(var i=0;i<model.length;i++){
			var m = model[i];
			var itemId = m.itemId;

			if(m.xtype!="xlist"){
				var writable = m.writable;
				var required = m.required;
				var hidden = m.hidden;
				var auto = m.auto;
				var readonly = m.readonly;

				var c_writable = $("#writable_"+itemId);
				var c_required = $("#required_"+itemId);
				var c_hidden = $("#hidden_"+itemId);
				var c_auto = $("#auto_"+itemId);
				var c_readonly = $("#readonly_"+itemId);
				
				if(c_writable.length!=0 && c_required.length!=0 && c_hidden.length!=0 && c_auto.length!=0 && c_readonly.length!=0){
					if(writable=="1") c_writable.attr("checked","on");
					if(required=="1") c_required.attr("checked","on");
					if(hidden=="1") c_hidden.attr("checked","on");
					if(auto=="1") c_auto.attr("checked","on");
					if(readonly=="1") c_readonly.attr("checked","on");
				}
			}else{
				var addable = m.addable;
				var editable = m.editable;
				var deletable = m.deletable;

				var c_addable = $("#addable_"+itemId);
				var c_editable = $("#editable_"+itemId);
				var c_deletable = $("#deletable_"+itemId);
				if(c_addable.length!=0 && c_editable.length!=0 && c_deletable.length!=0){
					if(addable=="1") c_addable.attr("checked","on");
					if(editable=="1") c_editable.attr("checked","on");
					if(deletable=="1") c_deletable.attr("checked","on");
				}
			}
		}
	}

	$("[title]").tooltips();
	
	var headerTb = $("#headertb");
	$(window).scroll(function() {
		if($(this).scrollTop()>=41){
			headerTb.css("position","fixed");
		}else{
			headerTb.css("position","");
		}
	});
	
	
	
	//获取附件控制模型  初始化
	var attachCtrlModel=json.rtData.attachCtrlModel;
	var model1= eval("("+attachCtrlModel+")");
	if(model1){
		var attachTr=$("#renderBody2 tr[xtype=xupload]");
		for(var m=0;m<attachTr.length;m++){
			var value=$(attachTr[m]).attr("value");
			for(var n=0;n<model1.length;n++){
				var itemId=model1[n].itemId;
				var priv=model1[n].priv;
				if(value==itemId){
					if((priv&1)==1){//查看
						$(attachTr[m]).find("td input[type=checkbox][class=ck]").attr("checked","on");
					}
					if((priv&2)==2){//下载
						$(attachTr[m]).find("td input[type=checkbox][class=xz]").attr("checked","on");
					}
					if((priv&4)==4){//删除
						$(attachTr[m]).find("td input[type=checkbox][class=sc]").attr("checked","on");
					}
					if((priv&8)==8){//编辑
						$(attachTr[m]).find("td input[type=checkbox][class=bj]").attr("checked","on");
					}
					if((priv&16)==16){//上传
						$(attachTr[m]).find("td input[type=checkbox][class=sc1]").attr("checked","on");
					}
					break;
				}
				
			}
		}
		
	}
}

//提交
function commit(){
	var url = contextPath+"/flowProcess/updateFieldCtrlModel.action";
	var para = tools.formToJson($("#body"));

	var dataLines = $("#renderBody tr[clazz=dataLine]");
	var model = new Array();
	dataLines.each(function(i,obj){
		var cbx = $(obj).find("input[type='checkbox']");
		var data = {};
		cbx.each(function(i1,obj1){
			data["itemId"] = $(obj1).attr("itemId");
			data[$(obj1).attr("clazz")]=$(obj1).attr("checked")=="checked"?1:0;
			data["xtype"] = $(obj).attr("xtype");
		});
		model.push(data);
	});

	dataLines = $("#renderBody1 tr[clazz=dataLine]");
	dataLines.each(function(i,obj){
		var cbx = $(obj).find("input[type='checkbox']");
		var data = {};
		cbx.each(function(i1,obj1){
			data["itemId"] = $(obj1).attr("itemId");
			data[$(obj1).attr("clazz")]=$(obj1).attr("checked")=="checked"?1:0;
			data["xtype"] = $(obj).attr("xtype");
		});
		model.push(data);
	});
	var requestJsonStr = tools.jsonArray2String(model);
	
	
	//获取附件上传控件的控制模型
	var model1 = new Array();
	var attachTr=$("#renderBody2 tr[xtype=xupload]");
	attachTr.each(function(i,obj){
		var itemId=$(obj).attr("value");
		var cbx = $(obj).find("input[type='checkbox']");
		var data = {};
		var priv=0;
		cbx.each(function(i1,obj1){
			if($(obj1).attr("checked")=="checked"){
				priv=priv+parseInt($(obj1).attr("value"));
			}
		});
		data["itemId"] =itemId;
		data["priv"]=priv;
		model1.push(data);
	});
	var attachCtrModel=tools.jsonArray2String(model1);
	
	var json = tools.requestJsonRs(url,{jsonStr:requestJsonStr,prcsId:prcsId,attachCtrModel:attachCtrModel});
// 	top.$.jBox.tip(json.rtMsg,"info");
}

function allSelect(obj){
	var clazz = obj.getAttribute("prefix");
	if(obj.checked){
		$("input[clazz="+clazz+"]").attr("checked","");
	}else{
		$("input[clazz="+clazz+"]").removeAttr("checked");
	}
}


</script>
<style>

</style>
</head>
<body onload="doInit()" id="body">
<b>普通控件：</b>
<br/><br/>
<div id="headertb" style="width:650px;top:0px;left:0px;right:0px;margin-left:auto;margin-right:auto;">
	<table class="TableBlock" align="center" style="width:650px;">
		<thead>
			<tr class="TableHeader">
				<td width="127px">表单字段名</td>
				<td width="70px">控件类型</td>
				<td style="line-height:20px;width:50px">可写<i class="glyphicon glyphicon-question-sign" title="是否允许该控件在保存的时候将数据提交至数据库" style="color:#428bca"></i><br/><input type="checkbox" id="writableAll" prefix="writable" onclick="allSelect(this)"/></td>
				<td style="line-height:20px;width:50px">必填<i class="glyphicon glyphicon-question-sign" title="是否允许该字段为空值" style="color:#428bca"></i><br/><input type="checkbox" id="requiredAll" prefix="required" onclick="allSelect(this)" /></td>
				<td style="line-height:20px;width:50px">隐藏<i class="glyphicon glyphicon-question-sign" title="是否允许该字段隐藏" style="color:#428bca"></i><br/><input type="checkbox" id="hiddenAll" prefix="hidden" onclick="allSelect(this)" /></td>
				<td style="line-height:20px;width:50px">赋值<i class="glyphicon glyphicon-question-sign" title="如有控件默认值或特定宏控件时，自动赋值该控件" style="color:#428bca"></i><br/><input type="checkbox" id="autoAll" prefix="auto" onclick="allSelect(this)"/></td>
				<td style="line-height:20px;width:50px">只读<i class="glyphicon glyphicon-question-sign" title="是否控制该控件只读" style="color:#428bca"></i><br/><input type="checkbox" id="readonlyAll" prefix="readonly" onclick="allSelect(this)"/></td>
			</tr>
		</thead>
	</table>
</div>
<table class="TableBlock" width="650px" align="center">
   <tbody id="renderBody">
	</tbody>
</table>
<br/>
<b>明细表控件：</b>
<br/><br/>
<table class="TableBlock" width="650px"  align="center">
   <tbody id="renderBody1">
		<tr class="TableHeader">
			<td>表单字段名</td>
			<td>控件类型</td>
			<td>添加模式</td>
			<td>修改模式</td>
			<td>删除模式</td>
		</tr>
	</tbody>
</table>

<br>
<b>附件上传控件：</b>
<br/><br/>
<table class="TableBlock" width="650px"  align="center">
   <tbody id="renderBody2">
		<tr class="TableHeader">
			<td>表单字段名</td>
			<td>控件类型</td>
			<td>查看权限</td>
			<td>下载权限</td>
			<td>删除权限 </td>
			<td>编辑权限 </td>
			<td>上传权限 </td>
		</tr>
	</tbody>
</table>
</body>
</html>