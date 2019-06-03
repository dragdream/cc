<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>单项选择控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<b>名称</b>
	<br/>
	<input id="title" name="title" class="BigInput" style="width:90%"/>
	<br/>
	<br/>
	<div id="xradio_content"></div><br/><a href="javascript:void(0)" onclick="xradio_addDataItem()">添加数据项</a>
</div>
<script>
	var contextPath = "<%=contextPath%>";
	var headerstyle = "style='text-align:center;font-weight:bold;'";
	var bodystyle = "style='text-align:center;'";
	
	function xradio_addDataItem(){
		var xradio_tbody = $("#xradio_tbody");
		var html = "";

		html+="<tr>";
		html+="<td "+headerstyle+"><input type='text' class='cke_dialog_ui_input_text'/></td>";
		html+="<td "+headerstyle+"><input type='radio' name='default'/></td>";
		html+="<td "+headerstyle+"><a href='javascript:void()' onclick='xradio_deleteDataItem(this)'>删除</a></td>";
		html+="</tr>";
		
		xradio_tbody.append(html);
	}

	function xradio_deleteDataItem(obj){
		$(obj).parent().parent().remove();
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
		var dataList = $("#xradio_tbody tr");
		var defaultValue = "";
		dataList.each(function(i,obj){
			var data = $(obj).find("td");
			if($(data[0]).find("input:first").val()!=""){
				if($(data[1]).find("input:first")[0].checked){
					defaultValue = $(data[0]).find("input:first").val();
				}
			}
		});
		
		var html = "<input type=\"radio\" xtype=\"xradio\" title=\""+title+"\" defaultvalue=\""+defaultValue+"\"";
		
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		
		var json = "[";
		dataList.each(function(i,obj){
			var data = $(obj).find("td");
			if($(data[0]).find("input:first").val()!=""){
				json+="{'data':'"+$(data[0]).find("input:first").val()+"','default':"+($(data[1]).find("input:first")[0].checked?"true":"false")+"}";
				if(i!=dataList.length-1){
					json+=",";
				}
			}
		});
		
		json+="]";
		
		html+="model=\""+json+"\"";
		html+="/>";
		return html;
	}

	//初始化控件数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	var xradio_content = $("#xradio_content");

	//设置表头
	var html = "<table style='width:100%;;font-size:12px'>";
	html+="<thead>";
	html+="<tr>";
	html+="<td "+headerstyle+">单选项</td><td "+headerstyle+">默认选中</td><td "+headerstyle+">操作</td>";
	html+="</tr>";
	html+="</thead>";

	html+="<tbody id='xradio_tbody'>";

	if(element!=null && element.getAttribute('xtype')=='xradio'){
		nameCode = element.getAttribute('name');
		var title = $("#title").val();
		if(element.getAttribute("title")){
			$("#title").attr("value",element.getAttribute("title"));
		}

		var model = element.getAttribute("model");//获取元素model属性
		if(model && model!=""){
			var list = eval("("+model+")");
			for(var i=0;i<list.length;i++){
				html+="<tr>";
				html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' value='"+list[i].data+"'></td><td "+bodystyle+"><input type='radio' name='default' "+(list[i]['default']?"checked":"")+"></td><td "+bodystyle+"><a href='javascript:void()' onclick='xradio_deleteDataItem(this)'>删除</a></td>";
				html+="</tr>";
			}
		}
		
	}else{
		nameCode = null;
	}
	html+="</tbody>";
	html+="</table>";
	xradio_content.html(html);
	
</script>
</body>

</html>