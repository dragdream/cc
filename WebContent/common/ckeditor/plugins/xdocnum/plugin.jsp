<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>文号控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input id="title" class="BigInput" style="width:90%"/>
			</td>
		</tr>
		<tr>
			<td>
				注：每个表单中有且仅有一个文号控件，该文号控件用于生成文号，具体文号生成策略及权限请打开首页菜单【工作流程】-》【流程设置】-》【文号管理】
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
		def = $("#def").val();

		var html = "<input type=\"text\" xtype=\"xdocnum\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		if(title){
			html+=" title=\""+title+"\"";
		}
		
		html+=" value=\"{文号控件}\" />"
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=="xdocnum"){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		if(element.getAttribute('title')){
			title.attr("value",element.getAttribute('title'));
		}

	}else{
		nameCode = null;
	}

</script>
</body>

</html>