<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>复选框控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>名称</b>
				<br/>
				<input id="title" class="BigInput" style="width:90%"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>默认值</b>
				<br/>
				<select id="def" class="BigSelect">
					<option value="checked">选中</option>
					<option value="">不选中</option>
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
		def = $("#def").val();

		var html = "<input type=\"checkbox\" xtype=\"xcheckbox\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		if(title){
			html+=" title=\""+title+"\"";
		}
		if(def){
			html+=def;
			html+=" defaultvalue=\"1\"";
		}else{
			html+=" defaultvalue=\"0\"";
		}
		
		html+="/>"
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=="xcheckbox"){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var def = $("#def");
		if(element.getAttribute('title')){
			title.attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('checked')){
			def[0].value = "checked";
		}else{
			def[0].value = "";
		}
	}else{
		nameCode = null;
	}

</script>
</body>

</html>