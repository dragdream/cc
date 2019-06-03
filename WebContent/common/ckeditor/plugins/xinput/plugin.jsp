<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background:#f0f0f0">
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>单行输入框</title>
</head>
<body style="background:#f0f0f0">
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>名称：</b>
				<br/>
				<input id="title" class="BigInput" />
			</td>
			<td>
				<b>初始值：</b>
				<br/>
				<input id="defaultValue" class="BigInput" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>字符长度：</b>
				<br/>
				<input id="maxLength" class="BigInput" />
			</td>
		</tr>
		<tr>
			<td>
				<b>数据类型：</b>
				<br/>
				<select id="datatype" class="BigSelect" onchange="selectChanged(this)">
					<option value="1">字符型</option>
					<option value="2">整数[整型]</option>
					<option value="3">小数[浮点型]</option>
				</select>
			</td>
			<td>
				<b>格式：</b>
				<br/>
				<select id="format" class="BigSelect" style="width:90%" >
					<option value="0">无</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>控件样式：</b>
				<br/>
				<textarea id="style" class="BigTextarea" style="width:100%;height:70px"></textarea>
			</td>
		</tr>
	</table>
</div>
<script>
	var contextPath = "<%=contextPath%>";

	var renderIntType = function(){
		var dom = "<option value='#'>#</option>";
		dom+="<option value='#,###'>#,###</option>";
		return dom;
	}
	var renderFloatType = function(){
		var dom = "<option value='#.#'>#.#</option>";
		dom+="<option value='#.##'>#.##</option>";
		dom+="<option value='#.###'>#.###</option>";
		dom+="<option value='#,###.#'>#,###.#</option>";
		dom+="<option value='#,###.##'>#,###.##</option>";
		dom+="<option value='#,###.###'>#,###.###</option>";
		dom+="<option value='#.0'>#.0</option>";
		dom+="<option value='#.00'>#.00</option>";
		dom+="<option value='#.000'>#.000</option>";
		dom+="<option value='#,###.0'>#,###.0</option>";
		dom+="<option value='#,###.00'>#,###.00</option>";
		dom+="<option value='#,###.000'>#,###.000</option>";
		return dom;
	}
	
	
	function selectChanged(obj){
		var dom="";
		if(obj.value=='1'){
			dom = "";
		}else if(obj.value=='2'){
			dom = renderIntType();
		}else if(obj.value=='3'){
			dom = renderFloatType();
		}
		$("#format").html(dom);
	}

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
		style = $("#style").val();
		defaultValue = $("#defaultValue").val();
		maxLength = $("#maxLength").val();
		datatype = $("#datatype").val();
		format = $("#format").val();
		var html = "<input type=\"text\" xtype=\"xinput\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(title){
			html+=" title=\""+title+"\"";
		}
		if(defaultValue){
			html+=" value=\""+defaultValue+"\" defaultvalue=\""+defaultValue+"\" ";
		}
		if(maxLength){
			html+=" maxlength=\""+maxLength+"\"";
		}
		html+=" style=\""+style+"\" ";

		var json = "{";
		if(datatype){
			json+="'datatype':'"+datatype+"'";
		}
		if(format){
			json+=",'format':'"+format+"'";
		}
		json+="}";
		
		html+=" model=\""+json+"\"/>"
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=='xinput'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var style = $("#style");
		var defaultValue = $("#defaultValue");
		var maxLength = $("#maxLength");
		var datatype = $("#datatype");
		var format = $("#format");

		if(element.getAttribute('title')){
			$("#title").attr("value",element.getAttribute('title'));
		}
		if(element.getAttribute('style')){
			$("#style").attr("value",element.getAttribute('style'));
		}
		if(element.getAttribute('defaultvalue')){
			$("#defaultValue").attr("value",element.getAttribute('defaultvalue'));
		}
		if(element.getAttribute('maxlength')){
			$("#maxLength").attr("value",element.getAttribute('maxlength'));
		}
		if(element.getAttribute('model')){
			model = element.getAttribute('model');
			model = eval('('+model+')');
			if(model.datatype){
				var dataType = model.datatype;
				datatype[0].value = dataType;
				
				format.html("");
				
				if(dataType=='1'){
					
				}else if(dataType=='2'){
					format.html(renderIntType());
				}else if(dataType=='3'){
					format.html(renderFloatType());
				}
				format[0].value = model.format;
			}
		}
	}else{
		nameCode = null;
	}

</script>
</body>

</html>