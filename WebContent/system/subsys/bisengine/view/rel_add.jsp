<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp" %>
<title>关联映射</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function doInit(){
	tables = xparent.tables;
	for(var i=0;i<tables.length;i++){
		$("#table1").append("<option value=\""+tables[i].sid+","+tables[i].name+","+tables[i].index+","+tables[i].desc+"\">"+tables[i].desc+"("+tables[i].name+"_"+tables[i].index+")</option>");
		$("#table2").append("<option value=\""+tables[i].sid+","+tables[i].name+","+tables[i].index+","+tables[i].desc+"\">"+tables[i].desc+"("+tables[i].name+"_"+tables[i].index+")</option>");
	}
	
	selectChange(window.table1,window.field1);
	selectChange(window.table2,window.field2);
}


function selectChange(obj,field){
	$(field).html("");
	var tableSid = obj.value.split(",")[0];
	var tableName = obj.value.split(",")[1];
	var tableIndex = obj.value.split(",")[2];
	var tableDesc = obj.value.split(",")[3];
	
	var json = tools.requestJsonRs(contextPath+"/bisTableField/datagrid.action?bisTableId="+tableSid);
	var list = json.rows;
	for(var i=0;i<list.length;i++){
		$(field).append("<option value=\""+tableName+","+tableIndex+","+list[i].fieldName+","+tableDesc+","+list[i].fieldDesc+"\">"+list[i].fieldDesc+"</option>");
	}
}

/**
 * [{t1:表1|1,t2:表2|2,l1:表1字段,l2:表2字段,desc:xxxxxxxxx}]
 */
function ok(){
	var f1 = $("#field1").val();
	var name1 = f1.split(",")[0];
	var index1 = f1.split(",")[1];
	var fieldName1 = f1.split(",")[2];
	var tableDesc1 = f1.split(",")[3];
	var fieldDesc1 = f1.split(",")[4];
	
	var f2 = $("#field2").val();
	var name2 = f2.split(",")[0];
	var index2 = f2.split(",")[1];
	var fieldName2 = f2.split(",")[2];
	var tableDesc2 = f2.split(",")[3];
	var fieldDesc2 = f2.split(",")[4];
	
	xparent.appendFieldItem({t1:name1+"|"+index1,t2:name2+"|"+index2,l1:fieldName1,l2:fieldName2,desc:tableDesc1+"("+fieldDesc1+")&nbsp;=&nbsp;"+tableDesc2+"("+fieldDesc2+")"});
	CloseWindow();
}

</script>
<style>
td{
border:1px solid gray;
padding:5px;
}
</style>
</head>
<body onload="doInit()" style="margin:10px">
<table>
	<tr>
		<td>
			<select class="BigSelect" id="table1"  onchange="selectChange(this,window.field1)">
				
			</select>
		</td>
		<td width="50px"></td>
		<td>
			<select class="BigSelect" id="table2" onchange="selectChange(this,window.field2)">
				
			</select>
		</td>
	</tr>
	<tr>
		<td>
			<select class="BigSelect" id="field1">
				
			</select>
		</td>
		<td>
			<center>=</center>
		</td>
		<td>
			<select class="BigSelect" id="field2">
				
			</select>
		</td>
	</tr>
</table>
<br/>
<button type="button" class="btn btn-info" onclick="ok()">确定</button>
</body>
</html>
