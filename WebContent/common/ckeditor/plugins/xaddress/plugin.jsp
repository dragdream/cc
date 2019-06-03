<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/header/header2.0.jsp" %>
	<script type="text/javascript" src="/common/js/address_cascade.js"></script>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>区域联动控件</title>
</head>
<body>
<div style="padding:10px;font-size:12px">
	<table style="font-size:12px">
		<tr>
			<td>
				<b>控件名称：</b>
				<br/>
				<input id="title" class="BigInput"/>
			</td> 
		</tr>
		<tr>
			<td>
				<b>控件类型：</b>
				<br/>
				<select id="atype" class="BigSelect" >
				   <option value="province">省</option>
				   <option value="city">市</option>
				   <option value="district">区</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<b>所属分组：</b>
				<br/>
				<select id="agroup" class="BigSelect">
				   <option value="A">A</option>
				   <option value="B">B</option>
				   <option value="C">C</option>
				   <option value="D">D</option>
				   <option value="E">E</option>
				   <option value="F">F</option>
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
		window.atype = $("#atype").val();
		window.agroup = $("#agroup").val();

		var html = "<select xtype=\"xaddress\" ";
		if(nameCode && nameCode!=null){
			html+=" name=\""+nameCode+"\" id=\""+nameCode+"\"";
		}else{
			parent.maxItem++;
			html+=" name=\"DATA_"+parent.maxItem+"\"   id=\"DATA_"+parent.maxItem+"\" ";
		}
		if(title){
			html+=" title=\""+title+"\" value=\"{区域联动控件}\" atype=\""+atype+"\"   agroup=\""+agroup+"\" ";
		}

		html+=" ></select>";
		return html;
	}

	//加载数据
	var selection = parent.editor.getSelection();
	var ranges = selection.getRanges();
	var element = selection.getSelectedElement();
	
	
	
	/* var json = tools.requestJsonRs(contextPath+"/cusNumberController/datagrid.action?page=1&rows=100000");
	for(var i=0;i<json.rows.length;i++){
		$("#numberStyle").append("<option value='"+json.rows[i].uuid+"'>"+json.rows[i].userSetStyle+"</option>");
	} */
	
	if(element!=null && element.getAttribute('xtype')=='xaddress'){
		nameCode = element.getAttribute('name');
		var title = $("#title");
		var atype = $("#atype");
		var agroup = $("#agroup");

		if(element.getAttribute('title')){
			$("#title").attr("value",element.getAttribute('title'));
		}
		
		if(element.getAttribute('atype')){
			$("#atype").attr("value",element.getAttribute('atype'));
		}
		
		if(element.getAttribute('agroup')){
			$("#agroup").attr("value",element.getAttribute('agroup'));
		}
	}else{
		nameCode = null;
	}
	
	
</script>
</body>

</html>