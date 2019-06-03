<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>选择器控件</title>
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
		</tr>
		<tr>
			<td>
				<b>控件类型</b>
				<br/>
				<select id="type" class="BigSelect" onchange="onSelectChanged(this)">
					<option value="1">日期选择器</option>
					<option value="2">时间选择器</option>
					<option value="3">部门选择器</option>
					<option value="4">部门选择器[多部门]</option>
					<option value="5">角色选择器</option>
					<option value="6">角色选择器[多角色]</option>
					<option value="7">人员选择器</option>
					<option value="8">人员选择器[多人员]</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<b>显示格式</b>
				<br/>
				<select id="format" class="BigSelect">
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<b>默认值</b>
				<br/>
				<select id="def" class="BigSelect">
					<option value="1">默认值</option>
					<option value="2">置空</option>
				</select>
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

	function onSelectChanged(obj){
		var format0 = $("#format");
		if(obj.value=="1"){
			format0.removeAttr("disabled");
			format0.html(renderDatetime());
		}else if(obj.value=="2"){
			format0.removeAttr("disabled");
			format0.html(renderTime());
		}else{
			format0.html("");
			format0.attr("disabled","disabled");
		}
	}

	var renderDatetime = function(){
		var dom = "";
		dom+="<option value='yyyy-MM-dd'>yyyy-MM-dd</option>";
		dom+="<option value='yyyy-M-d'>yyyy-M-d</option>";
		dom+="<option value='yyyy-MM-dd HH:mm'>yyyy-MM-dd HH:mm</option>";
		dom+="<option value='yyyy-MM'>yyyy-MM</option>";
		dom+="<option value='yyyy-M'>yyyy-M</option>";
		dom+="<option value='yy-MM-dd'>yy-MM-dd</option>";
		dom+="<option value='yy-M-d'>yy-M-d</option>";
		dom+="<option value='yyyyMMdd'>yyyyMMdd</option>";
		dom+="<option value='MM-dd yyyy'>MM-dd yyyy</option>";
		dom+="<option value='yyyy年MM月'>yyyy年MM月</option>";
		dom+="<option value='yyyy年M月'>yyyy年M月</option>";
		dom+="<option value='yyyy年MM月dd日'>yyyy年MM月dd日</option>";
		dom+="<option value='yyyy年M月d日'>yyyy年M月d日</option>";
		dom+="<option value='yyyy年MM月dd日 HH:mm'>yyyy年MM月dd日 HH:mm</option>";
		dom+="<option value='yyyy年M月d日 HH:mm'>yyyy年M月d日 HH:mm</option>";
		dom+="<option value='MM月dd日'>MM月dd日</option>";
		dom+="<option value='M月d日'>M月d日</option>";
		dom+="<option value='yyyy.MM'>yyyy.MM</option>";
		dom+="<option value='yyyy.MM.dd'>yyyy.MM.dd</option>";
		dom+="<option value='MM.dd'>MM.dd</option>";
		dom+="<option value='yyyy-MM-dd HH:mm:ss'>yyyy-MM-dd HH:mm:ss</option>";
		dom+="<option value='yyyy年MM月dd日 HH:mm:ss'>yyyy年MM月dd日 HH:mm:ss</option>";
		dom+="<option value='yyyy.MM.dd HH:mm:ss'>yyyy.MM.dd HH:mm:ss</option>";
		return dom;
	}
	var renderTime = function(){
		var dom = "";
		dom+="<option value='HH时'>HH时</option>";
		dom+="<option value='HH时mm分'>HH时mm分</option>";
		dom+="<option value='HH时mm分ss秒'>HH时mm分ss秒</option>";
		dom+="<option value='HH:mm'>HH:mm</option>";
		dom+="<option value='HH:mm:ss'>HH:mm:ss</option>";
		return dom;
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
		var title = $("#title").val();
		var type = $("#type").val();
		var format = $("#format").val();
		var def = $("#def").val();
		var style = $("#style").val();
		
		var typeName = "";
		if(type=="1" || type=="2"){//时间
			typeName = "时间{选择控件}";
		}else if(type=="3" || type=="4"){//部门
			typeName = "部门{选择控件}";
		}else if(type=="5" || type=="6"){//角色
			typeName = "角色{选择控件}";
		}else if(type=="7" || type=="8"){//人员
			typeName = "人员{选择控件}";
		}
		
		var html = "<input title=\""+title+"\" xtype=\"xfetch\" value=\""+typeName+"\"";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}

		html +=" style=\""+style+"\" ";
		
		var json = "{";
		if(type){
			json+="'type':"+type+",";
		}
		if(format){
			json+="'format':'"+format+"',";
		}
		if(def){
			json+="'def':"+def+",";
		}
		if(json.length!=1){
			json = json.substring(0,json.length-1);
		}
		json+="}";

		html+="model=\""+json+"\" />";
		
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	

	var title = $("#title");
	var type = $("#type");
	var format = $("#format");
	var def = $("#def");
	var style = $("#style");

	if(element!=null && element.getAttribute("xtype")=="xfetch"){
		nameCode = element.getAttribute('name');
		if(element.getAttribute('title')){
			title[0].value = element.getAttribute('title');
		}
		if(element.getAttribute('style')){
			style[0].value = element.getAttribute('style');
		}

		var model = element.getAttribute('model');
		model = eval("("+model+")");
		if(model){
			if(model.type){
				if(model.type==1){
					type[0].value = "1";
					format.html(renderDatetime());
				}else if(model.type==2){
					type[0].value = "2";
					format.html(renderTime());
				}else{
					type[0].value = model.type;
				}
			}
			if(model.format){
				format[0].value = model.format;
			}
			if(model.def){
				def[0].value = model.def;
			}
		}
	}else{
		var _format = $("#format");
		_format.html(renderDatetime());
		nameCode = null;
	}

</script>
</body>

</html>