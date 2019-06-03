<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background:#f0f0f0">
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>二维码控件</title>
</head>
<body style="background:#f0f0f0">
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input id="title" class="BigInput" style="width:250px"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>保存字段：</b>
				<br/>
				<textarea rows="6" cols="50" id="savefield"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<b>二维码宽度：</b>
				<br/>
				<input id="codewidth" class="BigInput" style="width:100px"/>px
			</td>
		</tr>
		<tr>
			<td>
				<b>二维码高度：</b>
				<br/>
				<input id="codeheight" class="BigInput" style="width:100px"/>px
			</td>
		</tr>
		<tr>
			<td>
				<span style="color:red">注：1.保存字段为其他非二维码控件名称，多个名称之间用逗号分隔<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.宽度高度不设置，则为二维码生成的默认大小<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3.宽高设置不当，可能导致二维码失真的问题，请谨慎填写</span>
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
		savefield = $("#savefield").val();
		codewidth=$("#codewidth").val();
		codeheight=$("#codeheight").val();
		
		
		var html = "<img  src=\"/common/ckeditor/plugins/xqrcode/imgs/code.png\"  style=\"width:20px;height:20px\"  xtype=\"xqrcode\"  ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(savefield){
			html+=" savefield=\""+savefield+"\"";
		}
        if(codewidth){
        	html+=" codewidth=\""+codewidth+"\"";
        }
		
        if(codeheight){
        	html+=" codeheight=\""+codeheight+"\"";
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
	
	if(element!=null && element.getAttribute('xtype')=='xqrcode'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var savefield = $("#savefield");
		var codewidth = $("#codewidth");
		var codeheight = $("#codeheight");
		if(element.getAttribute('title')){
			$("#title").attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('savefield')){
			$("#savefield").attr("value",element.getAttribute('savefield'));
		}
		if(element.getAttribute('codewidth')){
			$("#codewidth").attr("value",element.getAttribute('codewidth'));
		}
		if(element.getAttribute('codeheight')){
			$("#codeheight").attr("value",element.getAttribute('codeheight'));
		}
	}else{
		nameCode = null;
	}

</script>
</body>

</html>