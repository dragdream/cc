<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>计算控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>名称</b>
				<br/>
				<input id="title" class="BigInput" />
			</td>
			<td>
				<b>值类型</b>
				<br/>
				<select id="valuetype" class="BigSelect">
					<option value="0">字符型</option>
					<option value="1">整数[整型]</option>
					<option value="2">小数[浮点型]</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<b>小数保留位</b>
				<br/>
				<input id="precision" class="BigInput" value="0"/>
			</td>
			<td>
				<b>数据格式</b>
				<br/>
				<select id="format" class="BigSelect" >
					<option value="0">无格式</option>
					<option value="1">四舍五入</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<b>计算公式</b>
				<br/>
				<textarea id="formula" style="width:338px;height:80px" class="BigTextarea"></textarea>
				<br/>
				<span style="color:blue">
				表单控件以 {控件名称} 表示，例：{数量}*{价格}<br/>
				相关函数：<br/>
				1、xFormatMoney({pattern}) 格式化为人民币大写，pattern为表单控件<br/>
				2、xDeltaDays(`{pattern}`,`{pattern}`) 计算时间的差值 天 ，pattern为表单控件<br/>
				3、xDeltaDays(`{pattern}`,`{pattern}`,workhour) 计算时间的差值 天，支持半天类型 ，pattern为表单控件，workhour为工作小时<br/>
				4、xDeltaHours(`{pattern}`,`{pattern}`) 计算时间的差值 小时，pattern为表单控件<br/>
				5、xDeltaMinutes(`{pattern}`,`{pattern}`) 计算时间的差值 分钟，pattern为表单控件<br/>
				6、xDeltaDate(`{pattern}`,`{pattern}`) 计算时间的差，pattern为表单控件，输出为字符串类型，形如xx天xx小时xx分xx秒<br/>
				7、xListColSum(`列表控件名称`,`列表列名称`) 计算指定列表中指定列的合计<br/>
				8、xWorkDays(`{pattern}`,`{pattern}`) 计算开始时间到结束时间的工作天数，支持半天<br/>
				</span>
				<br/>
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
		window.precision = $("#precision").val();
		window.valuetype = $("#valuetype").val();
		window.format0 = $("#format").val();
		window.formula = $("#formula").val();
		window.style = $("#style").val();
		
		var html = "<input xtype=\"xcalculate\" type=\"text\" ";
		if(title){
			html+=" title=\""+title+"\"";
		}
		if(style){
			html+=" style=\""+style+"\"";
		}
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		var json = "{";
		if(precision){
			json+="'precision':"+precision+",";
		}
		if(valuetype){
			json+="'valuetype':"+valuetype+",";
		}
		if(format0){
			json+="'format':"+format0+",";
		}
		if(formula){
			json+="'formula':'"+formula+"',";
		}
		if(json.length!=1){
			json = json.substring(0,json.length-1);
		}
		json+="}";

		html+=" model=\""+json+"\"/>";
		
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();

	if(element!=null && element.getAttribute("xtype")=="xcalculate"){
		nameCode = element.getAttribute('name');
		window.title = $("#title");
		window.precision =  $("#precision");
		window.valuetype =  $("#valuetype");
		window.format0 =  $("#format");
		window.formula =  $("#formula");
		window.style = $("#style");
		
		if(element.getAttribute("title")){
			title.attr("value",element.getAttribute("title"));
		}
		if(element.getAttribute("style")){
			style.attr("value",element.getAttribute("style"));
		}
		var model = element.getAttribute("model");
		model = eval("("+model+")");
		if(model){
			if(model.precision){
				precision[0].value = model.precision;
			}
			if(model.valuetype){
				valuetype[0].value = model.valuetype;
			}
			if(model.format){
				format0[0].value = model.format;
			}
			if(model.formula){
				formula[0].value = model.formula;
			}
		}
	}else{
		nameCode = null;
	}

</script>
</body>

</html>