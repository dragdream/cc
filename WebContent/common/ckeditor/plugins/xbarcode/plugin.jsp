<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background:#f0f0f0">
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>条形码控件</title>
</head>
<body style="background:#f0f0f0">
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input type="text" id="title" class="BigInput" style="width:250px;height:20px"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>保存字段：</b>
				<br/>
				<input type="text" name="savefield"  id="savefield" style="width:250px;height:20px"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>条形码格式：</b>
				<br/>
				<select id="format" name="format">
				   <option value="code128">code128</option>
				   <option value="code93">code93</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<b>条形码高度：</b>
				<br/>
				<input type="text" name="barheight" id="barheight" style="height: 20px"/>(cm)
			</td>
		</tr>
		<tr>
			<td>
				<b>条形码密集度：</b>
				<br/>
				<input type="text" name="dimension" id="dimension" style="height: 20px"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>是否显示文本：</b>
				<br/>
				<select id="isshowtext" name="isshowtext">
				   <option value="1">是</option>
				   <option value="0">否</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<span style="color:red">注：1.保存字段为表单上其他非条形码控件名称，仅支持一个保存字段<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.密集度值范围是0~1,密集度设置不当，可能导致条形码失真的问题，请谨慎填写</span>
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
		if($("#barheight").val()==""){
			alert("请填写条形码高度！");
			$("#barheight").focus();
			return false;
		}
		if($("#dimension").val()==""){
			alert("请填写条形码密集度！");
			$("#dimension").focus();
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
		savefield = $("#savefield").val();
		format=$("#format").val();
		barheight=$("#barheight").val();
		dimension=$("#dimension").val();
		isshowtext=$("#isshowtext").val();
		
		
		var html = "<img  src=\"/common/ckeditor/plugins/xbarcode/imgs/code.png\"  style=\"vertical-align:middle\"  xtype=\"xbarcode\"  ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(savefield){
			html+=" savefield=\""+savefield+"\"";
		}
        if(format){
        	html+=" format=\""+format+"\"";
        }
		
        if(barheight){
        	html+=" barheight=\""+barheight+"\"";
        }
        
        if(isshowtext){
        	html+=" isshowtext=\""+isshowtext+"\"";
        }
        
        if(dimension){
        	html+=" dimension=\""+dimension+"\"";
        }
        
		if(title){
			html+=" title=\""+title+"\"";
		}
		
		html+="/>";
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=='xbarcode'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var savefield = $("#savefield");
		var format = $("#format");
		var barheight = $("#barheight");
		var dimension = $("#dimension");
		var isshowtext = $("#isshowtext");
		if(element.getAttribute('title')){
			$("#title").attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('savefield')){
			$("#savefield").attr("value",element.getAttribute('savefield'));
		}
		if(element.getAttribute('format')){
			$("#format").attr("value",element.getAttribute('format'));
		}
		if(element.getAttribute('barheight')){
			$("#barheight").attr("value",element.getAttribute('barheight'));
		}
		if(element.getAttribute('dimension')){
			$("#dimension").attr("value",element.getAttribute('dimension'));
		}
		if(element.getAttribute('isshowtext')){
			$("#isshowtext").attr("value",element.getAttribute('isshowtext'));
		}
	}else{
		nameCode = null;
	}

</script>
</body>

</html>