<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background:#f0f0f0">
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>定位控件</title>
</head>
<body style="background:#f0f0f0" onload="doInit();">
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>名称：</b>
				<br/>
				<input type="text" id="title" class="BigInput" />
			</td>
		</tr>
		<tr>
			<td>
				<b>定位类型：</b>
				<br/>
				<select id="positiontype" class="positiontype" onchange="changePositionType();" >
					<option value="1">当前位置</option>
					<option value="2">自由定位</option
				</select>
			</td>
		</tr>
		<tr id="radiusTr" style="display: none">
			<td>
				<b>定位半径：</b>
				<br/>
				<input class="BigInput" type="text" name="radius" id="radius" value="0"/>米
			</td>
		</tr>
	</table>
</div>
<script>
	var contextPath = "<%=contextPath%>";

	function changePositionType(){
		var positiontype=$("#positiontype").val();
		if(positiontype==1){//当前位置
			$("#radiusTr").show();
		}else{//自由定位
			$("#radiusTr").hide();
		}
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
		positiontype = $("#positiontype").val();
		radius=$("#radius").val();
		
		var html = "<img  src=\"/common/ckeditor/plugins/xposition/imgs/position.png\"  style=\"width:20px;height:20px\"  xtype=\"xposition\"  ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(positiontype){
			html+=" positiontype=\""+positiontype+"\"";
		}
		if(title){
			html+=" title=\""+title+"\"";
		}
		if(radius){
			html+=" radius=\""+radius+"\"";
		}
		
		html+="/>";
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=='xposition'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var positiontype = $("#positiontype");
        var radius=$("#radius");
		
		if(element.getAttribute('title')){
			$("#title").attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('positiontype')){
			$("#positiontype").attr("value",element.getAttribute('positiontype'));
		    if(element.getAttribute('positiontype')==1||element.getAttribute('positiontype')=="1"){
		    	$("#radiusTr").show();
		    }else{
		    	$("#radiusTr").hide();
		    }
		}
		if(element.getAttribute('radius')){
			$("#radius").attr("value",element.getAttribute('radius'));
		}
		
	}else{
		nameCode = null;
		$("#radiusTr").show();
	}

</script>
</body>

</html>