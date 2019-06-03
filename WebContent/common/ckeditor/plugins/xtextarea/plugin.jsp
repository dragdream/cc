<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>多行文本框</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px" style="width:100%">
		<tr>
			<td>
				<b>名称：</b>
				<br/>
				<input id="title" class="BigInput" style="width:100%;"/>
				<input id="rich" type="checkbox" />&nbsp;&nbsp;开启文本编辑器模式
			</td>
		</tr>
		<tr>
			<td>
				<b>初始值：</b>
				<textarea id="defaultValue" class="BigTextarea" style="width:100%;height:78px"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<b>控件样式：</b>
				<br/>
				<textarea id="style" class="BigTextarea" style="width:100%;height:70px"></textarea>
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
		window.title = $("#title").val();
		window.style = $("#style").val();
		window.defaultValue = $("#defaultValue").val();
		window.rich = $("#rich");
		
		var html = "<textarea xtype=\"xtextarea\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(title){
			html+=" title=\""+title+"\"";
		}

		html+=" style=\""+style+"\"";
		
		if(defaultValue){
			html+=" defaultvalue=\""+tools.string2Unicode(defaultValue)+"\"";
		}
		
		if(rich[0].checked){
			html+=" rich=\"1\"";
		}else{
			html+=" rich=\"0\"";
		}
		
		html+=">";

// 		if(defaultValue){
// 			html+=defaultValue;
// 		}
		

		html+="</textarea>";
		
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=='xtextarea'){
		window.nameCode = element.getAttribute('name');
		window.title = $("#title");
		window.style = $("#style");
		window.defaultValue = $("#defaultValue");
		window.rich = $("#rich");
		
		if(element.getAttribute('title')){
			title.attr("value",element.getAttribute('title'));
		}
		if(element.getAttribute("style")){
			style.attr("value",element.getAttribute("style"));
		}
		if(element.getAttribute("rich")){
			if(element.getAttribute("rich")=="1"){
				rich.attr("checked","checked");
			}
		}
		defaultValue.attr("value",tools.unicode2String(element.getAttribute("defaultvalue")));
	}else{
		window.nameCode = null;
	}

</script>
</body>

</html>