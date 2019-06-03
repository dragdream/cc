<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>数据选择控件</title>
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
				<b>数据模型标识：</b>
				<br/>
				<input id="dfid" class="BigInput" value="" ></input>
				<br/>
				<span style="color:red">
					&nbsp;与业务引擎有关，一般无需设置该项。
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<b>选择重置项：</b>
				<br/>
				<textarea id="resetitems" class="BigTextarea" style="width:90%;height:50px;" value="" ></textarea>
				<br/>
				<span style="color:red">
					&nbsp;此项主要是用于选择数据后，将其他指定控件值置空，每项以逗号分隔
				</span>
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
		window.resetitems = $("#resetitems").val();

		var html = "<input type=\"button\" xtype=\"xdatasel\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		if(title){
			html+=" title=\""+title+"\"";
		}
		
		if(resetitems){
			html+=" resetitems=\""+resetitems+"\"";
		}
		
		html+=" value=\""+title+"\" dfid=\""+$("#dfid").val()+"\" />"
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=="xdatasel"){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		if(element.getAttribute('title')){
			title.attr("value",element.getAttribute('title'));
		}
		$("#dfid").attr("value",element.getAttribute('dfid'));
		$("#resetitems").attr("value",element.getAttribute('resetitems'));
	}else{
		nameCode = null;
	}

</script>
</body>

</html>