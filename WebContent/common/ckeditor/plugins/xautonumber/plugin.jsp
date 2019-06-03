<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>自动编号控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input id="title" class="BigInput" />
			</td>
		</tr>
		<tr>
			<td>
				<b>选择编号样式：</b>
				<br/>
				<select id="numberStyle" class="BigInput">
				</select>
			</td>
		</tr>
	</table>
</div>
<script>
	var contextPath = "<%=contextPath%>";

	//校验，required
	function validate(){
		//判断是否填写标题了
		if($("#title").val()==""){
			alert("请填写控件名称！");
			$("#title").focus();
			return false;
		}
		//判断当前title是否在整个dom文档中
		var findit = $(parent.editor.getData()).find("[title="+$("#title").val()+"]").length!=0?true:false;
		if(findit){
			if(element && $("#title").val()!=element.getAttribute('title')){
				alert("已存在该名称的控件，控件名称禁止重复。");
				return false;
			}else if(element && $("#title").val()==element.getAttribute('title')){
				return true;
			}else{
				alert("已存在该名称的控件，控件名称禁止重复。");
				return false;
			}
		}
		return true;
	}

	//转换dom节点，required
	function toDomStr(){
		title = $("#title").val();
		numberStyle = $("#numberStyle").val();

		var html = "<input xtype=\"xautonumber\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(title){
			html+=" title=\""+title+"\" value=\"{自动编号控件}\" numberId=\""+numberStyle+"\" ";
		}

		html+=" />";
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	var json = tools.requestJsonRs(contextPath+"/cusNumberController/datagrid.action?page=1&rows=100000");
	for(var i=0;i<json.rows.length;i++){
		$("#numberStyle").append("<option value='"+json.rows[i].uuid+"'>"+json.rows[i].codeName+"【"+json.rows[i].userSetStyle+"】"+"</option>");
	}
	
	if(element!=null && element.getAttribute('xtype')=='xautonumber'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var numberStyle = $("#numberStyle");

		if(element.getAttribute('title')){
			$("#title").attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('numberId')){
			$("#numberStyle").attr("value",element.getAttribute('numberId'));
		}
	}else{
		nameCode = null;
	}
	
	
</script>
</body>

</html>