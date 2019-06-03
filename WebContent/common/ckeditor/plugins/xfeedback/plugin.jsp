<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<!-- jQuery 布局器 -->
	<script src="<%=contextPath%>/common/ueditor/ueditor.config.js"></script>
	<script src="<%=contextPath%>/common/ueditor/ueditor.all.min.js"></script>
	<title>会签控件</title>
	<script>
	var editor;
	function doInit(){
		editor = UE.getEditor('template',{
			toolbars:[['FullScreen', 'Source','bold','italic','underline','justifyleft', 'justifycenter', 'justifyright', 'justifyjustify','table','fontfamily', 'fontsize','inserttable']]
		});//获取编辑器对象
		editor.ready(function(){//初始化方法
			editor.setHeight(100);
		});
	}
	</script>
	<style>
	td{
		padding:5px;
	}
	</style>
</head>
<body onload="doInit()">
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px;width:100%">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input id="title" class="BigInput" style="width:90%"/>
			</td>
		</tr>
		<tr>
			<td>
				<b>签章选项：</b>
				<br/>
				<input id="seal" type="checkbox"/>电子签章
				&nbsp;&nbsp;
				<input id="hand" type="checkbox"/>手写签名
				&nbsp;&nbsp;
				<input id="picseal" type="checkbox"/>图章
				&nbsp;&nbsp;
				<input id="h5hand" type="checkbox"/>H5手写
				&nbsp;&nbsp;
				<input id="mobihand" type="checkbox"/>移动签批
			</td>
		</tr>
		<tr>
			<td>
				<b>H5手写设置：</b>
				<br/>
				宽：<input id="h5w" type="text" style="width:30px" />px&nbsp;&nbsp;
				高：<input id="h5h" type="text" style="width:30px" />px&nbsp;&nbsp;
				悬浮显示：<select id="h5float" ><option value="1">是</option><option value="0">否</option></select>
			</td>
		</tr>
		<tr>
			<td>
				<b>移动签批设置：</b>
				<br/>
				宽：<input id="mobiw" type="text" style="width:30px" />px&nbsp;&nbsp;
				高：<input id="mobih" type="text" style="width:30px" />px&nbsp;&nbsp;
				悬浮显示：<select id="mobifloat" ><option value="1">是</option><option value="0">否</option></select>
			</td>
		</tr>
		<tr>
			<td>
				<b>会签模板：</b>
				<br/>
				<textarea id="template" style="height:80px" class="BigTextarea"></textarea>
				<br/>
				<span style="color:red">
					会签控件数据显示格式，{C}=会签内容，{U}=会签用户名，{D}=用户所属部门，{R}=用户所属角色，{T}=会签时间，{P}=签章位置，{O}=操作位置
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<b>日期格式：</b>
				<br/>
				<input type="text" name="dateformat" id="dateformat" value="yyyy年MM月dd日  HH时mm分"  style="height: 23px;width: 300px">
			</td>
		</tr>
		<tr>
			<td>
				<b>控件样式：</b>
				<br/>
				<textarea id="style" style="width:320px;height:30px" class="BigTextarea"></textarea>
			</td>
		</tr>
		<tr>
			<td>
				<b>排序字段：</b>
				<br/>
				<select id="sortfield" name="sortfield">
				   <option value="1">会签时间</option>
				   <option value="2">用户排序号</option>
				   <option value="3">角色排序号</option>
				   <option value="4">部门排序号</option>  
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<b>排序方式：</b>
				<br/>
				<select id="order" name="order" >
				   <option value="asc">正序</option>
				   <option value="desc">倒序</option>
				</select>
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
		window.template = editor.getContent();
		window.style = $("#style").val();
        window.sortfield=$("#sortfield").val();
        window.order=$("#order").val();
        
		window.dateformat=$("#dateformat").val();
		var html = "<textarea  xtype=\"xfeedback\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\" id=\"DATA_"+parent.maxItem+"\" ";
		}
		
		if(title){
			html+=" title=\""+title+"\"";
		}
		
		if(sortfield){
			html+=" sortfield=\""+sortfield+"\"";
		}
		if(order){
			html+=" order=\""+order+"\"";
		}
		
		if($("#seal").attr("checked")){
			html+=" seal=\"1\"";
		}
		
		if($("#hand").attr("checked")){
			html+=" hand=\"1\"";
		}
		
		if($("#picseal").attr("checked")){
			html+=" picseal=\"1\"";
		}
		
		if($("#h5hand").attr("checked")){
			html+=" h5hand=\"1\"";
		}
		
		if($("#mobihand").attr("checked")){
			html+=" mobihand=\"1\"";
		}
		
		html+=" h5w=\""+$("#h5w").val()+"\" h5h=\""+$("#h5h").val()+"\"  h5float=\""+$("#h5float").val()+"\"  ";
		html+=" mobiw=\""+$("#mobiw").val()+"\" mobih=\""+$("#mobih").val()+"\"  mobifloat=\""+$("#mobifloat").val()+"\"  ";
		
		html+=" template=\""+tools.string2Unicode(template)+"\" style=\""+style+"\"   dateformat=\""+dateformat+"\"   />";
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	if(element!=null && element.getAttribute('xtype')=="xfeedback"){
		window.nameCode = element.getAttribute('name');
		var sortfield=$("#sortfield");
		var order=$("#order");	
		var title = $("#title");
		var seal = $("#seal");
		var hand = $("#hand");
		var picseal = $("#picseal");
		var h5hand = $("#h5hand");
		var mobihand = $("#mobihand");
		
		var h5w = $("#h5w");
		var h5h = $("#h5h");
		var h5float = $("#h5float");
		
		var mobiw = $("#mobiw");
		var mobih = $("#mobih");
		var mobifloat = $("#mobifloat");
		
		window.template = $("#template");
		
		window.style = $("#style");
		
		window.dateformat=$("#dateformat");
		
		
		if(element.getAttribute('title')){
			title.attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('sortfield')){
			sortfield.attr("value",element.getAttribute('sortfield'));
		}
		
		
		if(element.getAttribute('order')){
			order.attr("value",element.getAttribute('order'));
		}
		
		if(element.getAttribute('seal')){
			if(element.getAttribute('seal')=="1"){
				seal.attr("checked","");
			}
		}
		if(element.getAttribute('hand')){
			if(element.getAttribute('hand')=="1"){
				hand.attr("checked","");
			}
		}
		if(element.getAttribute('picseal')){
			if(element.getAttribute('picseal')=="1"){
				picseal.attr("checked","");
			}
		}
		if(element.getAttribute('h5hand')){
			if(element.getAttribute('h5hand')=="1"){
				h5hand.attr("checked","");
			}
		}
		if(element.getAttribute('mobihand')){
			if(element.getAttribute('mobihand')=="1"){
				mobihand.attr("checked","");
			}
		}
		
		if(element.getAttribute('h5w')){
			h5w.val(element.getAttribute('h5w'));
		}
		if(element.getAttribute('h5h')){
			h5h.val(element.getAttribute('h5h'));
		}
		if(element.getAttribute('h5float')){
			h5float.val(element.getAttribute('h5float'));
		}
		if(element.getAttribute('mobiw')){
			mobiw.val(element.getAttribute('mobiw'));
		}
		if(element.getAttribute('mobih')){
			mobih.val(element.getAttribute('mobih'));
		}
		if(element.getAttribute('mobifloat')){
			mobifloat.val(element.getAttribute('mobifloat'));
		}

		if(element.getAttribute('template')){
			template.attr("value",tools.unicode2String(element.getAttribute('template')));
		}

		if(element.getAttribute('style')){
			style.attr("value",element.getAttribute('style'));
		}
		
		if(element.getAttribute('dateformat')){
			dateformat.attr("value",element.getAttribute('dateformat'));
		}
	}else{
		window.template = $("#template");
		window.template.attr("value","<div style='MARGIN-BOTTOM: 20px; FONT-SIZE: 12px'><p style='TEXT-ALIGN: left'>{C}</p><p style='TEXT-ALIGN: left'><span style='FONT-SIZE: 12px'>{P}</span></p><div style='TEXT-ALIGN: center'>{U}&nbsp;&nbsp;{T}&nbsp;&nbsp;{O}</div></div>");
		window.nameCode = null;
	}

</script>
</body>

</html>