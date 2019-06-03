<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
	String callback = request.getParameter("callback");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>
var frpSid = <%=frpSid%>;
var flowId = <%=flowId%>;

var parentWindowObj = window.dialogArguments;
var userRetNameArray = xparent["fieldCtrlArray"];
var backDomId = userRetNameArray[0];
var backDomAttachCtrlId = userRetNameArray[2];
var modelStr = xparent.document.getElementById(backDomId).value;
var attachCtrModelStr = xparent.document.getElementById(backDomAttachCtrlId).value;
//var parentWindowObj = window.dialogArguments;
//function click_seal(ID) {
//  if (callback) {
//    xparent.<%=callback%>('',ID);
//  }

function openListCtrlExtPage(flowPrcsId,formItemId){
	window.openWindow("listCtrlExtend.jsp?flowPrcsId="+flowPrcsId+"&formItemId="+formItemId,"列表详细设置",700,300);
}

function doInit(){
	//获取对应表单字段
	var url = contextPath+"/flowForm/getFormItemsByFlowType.action";
	var json = tools.requestJsonRs(url,{flowId:flowId});
	var items = json.rtData;
	for(var i=0;i<items.length;i++){
		var data = items[i];
		if(data.xtype=="xlist"){
			continue;
		}
		var html = "<tr clazz='dataLine' class='"+(i%2==0?"T_Line1":"T_Line2")+"' xtype='"+data.xtype+"' value='"+data.itemId+"'>";
		html += "<td style='text-align:center' class='TableData'>"+data.title+"</td>";
		html += "<td style='color:blue;text-align:center' class='TableData'>"+data.xtypeDesc+"<a href='#' ></a></td>";
		if(data.xtype!="xlist"){
			if(data.xtype=="xupload"){//附件上传控件
				html += "<td style='text-align:center' class='TableData'><input type='checkbox'  class='ck'  value='1'/></td>";
				html += "<td style='text-align:center' class='TableData'><input type='checkbox'  class='xz'  value='2'/></td>";
				html += "<td style='text-align:center' class='TableData'><input type='checkbox'  class='sc' value='4'/></td>";
				html += "<td style='text-align:center' class='TableData'><input type='checkbox'  class='bj' value='8'/></td>";
				html += "<td style='text-align:center' class='TableData'><input type='checkbox'  class='sc' value='16'/></td>";
			}else{
				html += "<td style='text-align:center'><input type='checkbox' clazz='writable' itemId='"+data.itemId+"' id='writable_"+data.itemId+"'/></td>";
				html += "<td style='text-align:center'><input type='checkbox' clazz='required' itemId='"+data.itemId+"' id='required_"+data.itemId+"' /></td>";
				html += "<td style='text-align:center'><input type='checkbox' clazz='hidden' itemId='"+data.itemId+"' id='hidden_"+data.itemId+"'/></td>";
				html += "<td style='text-align:center'><input type='checkbox' clazz='auto' itemId='"+data.itemId+"' id='auto_"+data.itemId+"'/></td>";
				html += "<td style='text-align:center'><input type='checkbox' clazz='readonly' itemId='"+data.itemId+"' id='readonly_"+data.itemId+"'/></td>";
			}
		}
		html +="</tr>";
		
		if(data.xtype=="xupload"){//附件上传控件
			$(html).appendTo($("#renderBody2"));
		}else{
			$(html).appendTo($("#renderBody"));
		}
		
	}

	//获取该步骤的字段控制模型
	var model;
	if(modelStr){
		model =  eval("("+modelStr+")")
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
				if(c_writable.length!=0 && c_required.length!=0 && c_hidden.length!=0 && c_auto.length!=0){
					if(writable=="1") c_writable.attr("checked","on");
					if(required=="1") c_required.attr("checked","on");
					if(hidden=="1") c_hidden.attr("checked","on");
					if(auto=="1") c_auto.attr("checked","on");
					if(readonly=="1") c_readonly.attr("checked","on");
				}
			}
		}
	}

	
	
	//附件控制模型
	var model1= eval("("+attachCtrModelStr+")");
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
						$(attachTr[m]).find("td input[type=checkbox][class=sc]").attr("checked","on");
					}
					break;
				}
				
			}
		}
		
	}
	
}

function getSelectDom(){
	
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
	
	var requestJsonStr = tools.jsonArray2String(model);
	xparent.document.getElementById(backDomId).value = requestJsonStr ;
	
	
	
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
	
	xparent.document.getElementById(backDomAttachCtrlId).value = attachCtrModel ;
	CloseWindow();
	//var json = tools.requestJsonRs(url,{jsonStr:requestJsonStr,prcsId:prcsId});
	//alert(json.rtMsg);
	//parent.top.opener.location.reload();
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
<body onload="doInit()" id="body" style="overflow:auto;margin:0px">
<table class="TableBlock" width="100%" align="center">
   <tr class="TableHeader"><td colspan="2"><input type="button" value="保存" class="btn btn-success" onclick="commit()"/></td></tr>
   <tr>
    <td class="TableData" colspan="2">
    	<table class="T_Table">
			<tbody id="renderBody">
				<tr class="TableHeader">
					<td>表单字段名</td>
					<td>控件类型</td>
					<td style="line-height:20px;">是否可写<i class="glyphicon glyphicon-question-sign" title="是否允许该控件在保存的时候将数据提交至数据库" style="color:#428bca"></i><br/><input type="checkbox" id="writableAll" prefix="writable" onclick="allSelect(this)"/></td>
					<td style="line-height:20px;">是否必填<i class="glyphicon glyphicon-question-sign" title="是否允许该字段为空值" style="color:#428bca"></i><br/><input type="checkbox" id="requiredAll" prefix="required" onclick="allSelect(this)" /></td>
					<td style="line-height:20px;">是否隐藏<i class="glyphicon glyphicon-question-sign" title="是否允许该字段隐藏" style="color:#428bca"></i><br/><input type="checkbox" id="hiddenAll" prefix="hidden" onclick="allSelect(this)" /></td>
					<td style="line-height:20px;">自动赋值<i class="glyphicon glyphicon-question-sign" title="如有控件默认值或特定宏控件时，自动赋值该控件" style="color:#428bca"></i><br/><input type="checkbox" id="autoAll" prefix="auto" onclick="allSelect(this)"/></td>
					<td style="line-height:20px;">是否只读<i class="glyphicon glyphicon-question-sign" title="是否控制该控件只读" style="color:#428bca"></i><br/><input type="checkbox" id="readonlyAll" prefix="readonly" onclick="allSelect(this)"/></td>
				</tr>
			</tbody>
		</table>
    </td>
   </tr>
</table>
<br>
<b style="margin-left: 10px">附件上传控件：</b>
<br/><br/>
<table class="TableBlock" width="98%"  align="center">
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