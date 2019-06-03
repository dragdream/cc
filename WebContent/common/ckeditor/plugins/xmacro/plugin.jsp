<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>宏控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<b>控件名称：</b>
	<br/>
	<input id="title" name="title" class="BigInput" style="width:90%"/>
	<br/>
	<b>控件类型：</b>
	<br/>
	<select id="type" class="BigSelect" onchange="render(this.value)">
		<option value="1">当前年份</option>
		<option value="2">当前日期</option>
		<option value="3">当前时间</option>
<!--		<option value="4">当前季度</option>-->
		<option value="20">当前星期</option>
		<option value="5">流程名称</option>
		<option value="6">流程编号</option>
		<option value="7">流程发起人帐号</option>
		<option value="8">流程发起人姓名</option>
		<option value="9">流程发起人部门</option>
		<option value="10">流程发起人角色</option>
		<option value="11">当前用户帐号</option>
		<option value="12">当前用户姓名</option>
		<option value="13">当前用户部门</option>
		<option value="14">当前用户角色</option>
		<option value="15">当前用户IP</option>
		<option value="16">流程发起人辅助部门选择</option>
		<option value="17">流程发起人辅助角色选择</option>
		<option value="18">当前用户辅助部门选择</option>
		<option value="19">当前用户辅助角色选择</option>
		<option value="22">当前用户部门（层级）</option>
		<option value="23">当前用户辅助部门（层级）</option>
		<option value="24">流程发起人部门（层级）</option>
		<option value="25">流程发起人辅助部门（层级）</option>
		<option value="21">当前工作名称</option>
		<option value="26">当前用户姓名+当前日期</option>
	</select>
	<br/>
	<b>显示格式：</b>
	<br/>
	<div id="formatDiv">
		<select id="format" class="BigSelect">
		</select>
	</div>
	<b>控件样式：</b>
	<br/>
	<textarea id="style" class="BigTextarea" style="width:100%;height:70px"></textarea>
</div>
<script>
	function render(type){
		var html = "";
		if(type=="1"){
			html+="<option value=\"yyyy年\">yyyy年</option>";
			html+="<option value=\"yyyy\">yyyy</option>";
			html+="<option value=\"yy年\">yy年</option>";
			html+="<option value=\"yy\">yy</option>";
		}else if(type=="2"){
			html+="<option value=\"yyyy-MM-dd\">yyyy-MM-dd</option>";
			html+="<option value=\"yyyy-M-d\">yyyy-M-d</option>";
			html+="<option value=\"yyyy年MM月dd日\">yyyy年MM月dd日</option>";
			html+="<option value=\"yyyy年M月d日\">yyyy年M月d日</option>";
			html+="<option value=\"yyyyMMdd\">yyyyMMdd</option>";
			html+="<option value=\"yyyy/MM/dd\">yyyy/MM/dd</option>";
			html+="<option value=\"yyyy/M/d\">yyyy/M/d</option>";
			html+="<option value=\"yyyy.MM.dd\">yyyy.MM.dd</option>";
			html+="<option value=\"yyyy.M.d\">yyyy.M.d</option>";
			html+="<option value=\"yyyy年MM月\">yyyy年MM月</option>";
			html+="<option value=\"yyyy年M月\">yyyy年M月</option>";
			html+="<option value=\"MM月\">MM月</option>";
			html+="<option value=\"M月\">M月</option>";
			html+="<option value=\"dd日\">dd日</option>";
			html+="<option value=\"d日\">d日</option>";
			html+="<option value=\"MM-dd\">MM-dd</option>";
			html+="<option value=\"M-d\">M-d</option>";
			html+="<option value=\"MM月dd日\">MM月dd日</option>";
			html+="<option value=\"M月d日\">M月d日</option>";
		}else if(type=="3"){
			html+="<option value=\"yyyy-MM-dd HH:mm\">yyyy-MM-dd HH:mm</option>";
			html+="<option value=\"yyyy-MM-dd HH:mm:ss\">yyyy-MM-dd HH:mm:ss</option>";
			html+="<option value=\"yyyy年MM月dd日 HH时mm分\">yyyy年MM月dd日 HH时mm分</option>";
			html+="<option value=\"yyyy年MM月dd日 HH时mm分ss秒\">yyyy年MM月dd日 HH时mm分ss秒</option>";
			html+="<option value=\"HH\">HH</option>";
			html+="<option value=\"HH时\">HH时</option>";
			html+="<option value=\"HH:mm\">HH:mm</option>";
			html+="<option value=\"HH时mm分\">HH时mm分</option>";
			html+="<option value=\"HH:mm:ss\">HH:mm:ss</option>";
			html+="<option value=\"HH时mm分ss秒\">HH时mm分ss秒</option>";
		}

		$("#format").html(html);
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
		var style = $("#style").val();
		var html = "<input type=\"text\" value=\"{宏控件}\" xtype=\"xmacro\" title=\""+title+"\" ";
		
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		
		var json = "{";
		json+="type:"+type+"";
		if(format!=""){
			json+=",format:'"+format+"'";
		}
		json+="}";
		
		html+=" model=\""+json+"\"";

		html+=" style=\""+style+"\"";
		html+="/>";
		return html;
	}

	//初始化控件数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();

	if(element!=null && element.getAttribute('xtype')=='xmacro'){
		nameCode = element.getAttribute('name');
		var title = $("#title").val();
		var style = $("#style").val();
		if(element.getAttribute("title")){
			$("#title").attr("value",element.getAttribute("title"));
		}
		if(element.getAttribute("style")){
			$("#style").attr("value",element.getAttribute("style"));
		}

		var model = element.getAttribute("model");//获取元素model属性
		model = eval("("+model+")");
		
		var _type = model.type;
		var _format = model.format;
		$("#type")[0].value = _type;
		render(""+_type);
		$("#format")[0].value = _format;
		
	}else{
		nameCode = null;
		render("1");
	}
	
</script>
</body>

</html>