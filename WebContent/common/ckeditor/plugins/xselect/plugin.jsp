<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>下拉列表控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<b>名称</b>
	<br/>
	<input id="title" name="title" class="BigInput" style="width:90%"/>
	<br/>
	<b>关联上级下拉列表控件名称</b>
	<br/>
	<span style='color:red'>指定上级下拉列表控件，则上级控件在选择后，根据选择的数据值，级联影响当前控件的选择项。此时，需要在下列单选项中加入固定单选项格式“选项(1-n)|上级控件值”，例如：“香蕉|水果”或“刀叉|餐具”</span>
	<br/>
	<input id="rel" name="rel" class="BigInput" style="width:90%"/>
	<br/>
	<br/><div id='xselect_content'></div><br/><a href='javascript:void(0)' onclick='xselect_addDataItem()'>添加数据项</a>
</div>
<script>
	var contextPath = "<%=contextPath%>";
	var headerstyle = "style='text-align:center;font-weight:bold;'";
	var bodystyle = "style='text-align:center;'";
	
	function xselect_addDataItem(){
		var xselect_tbody = $("#xselect_tbody");
		var html = "";

		html+="<tr>";
		html+="<td "+headerstyle+"><input type='text' class='cke_dialog_ui_input_text'/></td>";
		html+="<td "+headerstyle+"><input type='radio' name='default'/></td>";
		html+="<td "+headerstyle+"><a href='javascript:void(0)' onclick='xselect_deleteDataItem(this)'>删除</a></td>";
		html+="</tr>";

		var child = $(html);
		
		xselect_tbody.append(child);
	}

	function xselect_deleteDataItem(obj){
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
		var dataList = $("#xselect_tbody tr");
		var defaultValue="";
		dataList.each(function(i,obj){
			var data = $(obj).find("td");
			var value = $(data[0]).find("input:first").val();
			var checked = $(data[1]).find("input:first")[0].checked;
			if(checked){
				defaultValue = value;
			}
		});
		
		var title = $("#title").val();
		var rel = $("#rel").val();
		var html = "<select  xtype=\"xselect\" title=\""+title+"\" rel=\""+rel+"\" defaultvalue=\""+defaultValue+"\"";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\" ";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		html+=">";
		
		dataList.each(function(i,obj){
			var data = $(obj).find("td");
			var value = $(data[0]).find("input:first").val();
			var checked = $(data[1]).find("input:first")[0].checked;
			if(value!=""){
				html+="<option value=\""+value+"\" "+(checked?"selected":"")+">"+value+"</option>";
			}
		});

		html+="</select>";
		
		return html;
	}

	//初始化控件数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	var xselect_content = $("#xselect_content");
	
	//设置表头
	var html = "<table style='width:100%;font-size:12px'>";
	html+="<thead>";
	html+="<tr>";
	html+="<td "+headerstyle+">单选项</td><td "+headerstyle+">默认选中</td><td "+headerstyle+">操作</td>";
	html+="</tr>";
	html+="</thead>";

	html+="<tbody id='xselect_tbody'>";
	
	if(element!=null && element.getAttribute('xtype')=='xselect'){
		window.nameCode = element.getAttribute('name');
		var title = $("#title");
		if(element.getAttribute("title")){
			title.attr("value",element.getAttribute("title"));
		}
		
		var rel = $("#rel");
		if(element.getAttribute("rel")){
			rel.attr("value",element.getAttribute("rel"));
		}
		
		var opts = $(element.getHtml());
		opts.each(function(i,obj){
			html+="<tr>";
			html+="<td "+bodystyle+"><input class='cke_dialog_ui_input_text' type='text' value='"+obj.value+"'></td><td "+bodystyle+"><input type='radio' name='default' "+(obj.selected?"checked":"")+"></td><td "+bodystyle+"><a href='javascript:void()' onclick='xselect_deleteDataItem(this)'>删除</a></td>";
			html+="</tr>";
		});
	}else{
		window.nameCode = null;
	}
	html+="</tbody>";
	html+="</table>";
	xselect_content.html(html);
	
</script>
</body>

</html>