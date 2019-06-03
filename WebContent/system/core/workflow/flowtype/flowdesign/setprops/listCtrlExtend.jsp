<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.tianee.webframe.util.*"%>
<%@ include file="/header/header.jsp" %>
<%
	int flowPrcsId = TeeStringUtil.getInteger(request.getParameter("flowPrcsId"),0);
	int formItemId = TeeStringUtil.getInteger(request.getParameter("formItemId"),0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>
var flowPrcsId = <%=flowPrcsId%>;
var formItemId = <%=formItemId%>;

function doInit(){
	//获取对应列表控件
	var url = contextPath+"/listCtrlExtend/getListFieldInfo.action";
	var json = tools.requestJsonRs(url,{formItemId:formItemId});
	var field = json.rtData;
	//获取控件模型
	var model = eval("("+field.model+")");
	//获取控件扩展模型
	url = contextPath+"/listCtrlExtend/getListCtrlExtend.action";
	json = tools.requestJsonRs(url,{formItemId:formItemId,flowPrcsId:flowPrcsId});
	var ext = json.rtData;
	
	//渲染展示
	for(var i=0;i<model.length;i++){
		var data = model[i];
		var title = data.title;
		var html = "<tr clazz='dataLine' class='"+(i%2==0?"T_Line1":"T_Line2")+"'>";
		html += "<td>"+title+"</td>";
		html += "<td><input type='checkbox' clazz='writable' itemId='"+title+"' id='writable_"+title+"'/></td>";
		html += "<td><input type='checkbox' clazz='required' itemId='"+title+"' id='required_"+title+"' /></td>";
		html += "<td><input type='checkbox' clazz='hidden' itemId='"+title+"' id='hidden_"+title+"'/></td>";
		html += "<td><select id='eventType_"+title+"' clazz='eventType'>"+renderOpts()+"</select></td>";
		html += "<td><input type='text' id='eventScript_"+title+"' clazz='eventScript'/></td>";
		html +="</tr>";
		$(html).appendTo($("#renderBody"));
	}

	//根据ext扩展模型进行数据渲染
	if(ext!=null && ext){
		var columnCtrlModel = ext.columnCtrlModel;
		columnCtrlModel = eval("("+columnCtrlModel+")");
		for(var i=0;i<columnCtrlModel.length;i++){
			var m = columnCtrlModel[i];
			var title = m.title;
			var writable = m.writable;
			var required = m.required;
			var hidden = m.hidden;
			var eventType = m.eventType;
			var eventScript = m.eventScript;
			title = title.replace("/","\\/");
			var c_writable = $("#writable_"+title);
			var c_required = $("#required_"+title);
			var c_hidden = $("#hidden_"+title);
			var c_eventType = $("#eventType_"+title);
			var c_eventScript = $("#eventScript_"+title);
			
			if(c_writable.length!=0 && c_required.length!=0 && c_hidden.length!=0){
				if(writable=="1") c_writable.attr("checked","on");
				if(required=="1") c_required.attr("checked","on");
				if(hidden=="1") c_hidden.attr("checked","on");
				c_eventType.attr("value",eventType);
				c_eventScript.attr("value",eventScript);
			}
		}
	}
}

function renderOpts(){
	var html = "";
	html+="<option value='0'>无</option>";
	html+="<option value='1'>单击时(onClick)</option>";
	html+="<option value='2'>获得焦点时(onFocus)</option>";
	html+="<option value='3'>失去焦点时(onBlur)</option>";
	html+="<option value='4'>内容改变时(onChange)</option>";
	html+="<option value='5'>键盘按下时(onKeyDown)</option>";
	html+="<option value='6'>键盘放开时(onKeyUp)</option>";
	html+="<option value='7'>键盘按住时(onKeyPress)</option>";
	html+="<option value='8'>鼠标按下时(onMouseDown)</option>";
	html+="<option value='9'>鼠标松开时(onMouseUp)</option>";
	return html;
}

//提交
function commit(){
	var url = contextPath+"/listCtrlExtend/save.action";
	var para = tools.formToJson($("#body"));

	var dataLines = $("#renderBody tr[clazz=dataLine]");
	var model = new Array();
	dataLines.each(function(i,obj){
		var cbx = $(obj).find("input[type='checkbox']");
		var data = {};
		cbx.each(function(i1,obj1){
			data["title"] = $(obj1).attr("itemId");
			data[$(obj1).attr("clazz")]=$(obj1).attr("checked")=="checked"?1:0;
		});
		var select = $(obj).find("select");
		select.each(function(i1,obj1){
			data[$(obj1).attr("clazz")]=$(obj1).attr("value");
		});
		var text = $(obj).find("input[type='text']");
		text.each(function(i1,obj1){
			data[$(obj1).attr("clazz")]=$(obj1).attr("value");
		});
		model.push(data);
	});
	var requestJsonStr = tools.jsonArray2String(model);

	var json = tools.requestJsonRs(url,{columnCtrlModel:requestJsonStr,flowPrcsId:flowPrcsId,formItemId:formItemId});
// 	alert(json.rtMsg);
}

</script>
<style>

</style>
</head>
<body onload="doInit()" id="body">
<table class="formFrame" style="height:100px;width:100%">
	<tr><td class="formFrame_left_top"></td>
		<td class="formFrame_top"></td>
		<td class="formFrame_right_top"></td>
		</tr>
		<tr>
			<td class="formFrame_left"></td>
			<td class="formFrame_center">
				<input type="button" value="保存" class="SmallButtonA" onclick="commit()"/>
				<br/>
				<table class="T_Table">
					<tbody id="renderBody">
						<tr class="T_Header">
							<td>列表字段</td>
							<td>是否可写</td>
							<td>是否必填</td>
							<td>是否隐藏</td>
							<td>事件类型</td>
							<td>脚本方法</td>
						</tr>
					</tbody>
				</table>
			</td>
		<td class="formFrame_right"></td>
	</tr>
	<tr>
		<td class="formFrame_left_bottom"></td><td class="formFrame_bottom"></td><td class="formFrame_right_bottom"></td></tr>
</table>
</body>
</html>