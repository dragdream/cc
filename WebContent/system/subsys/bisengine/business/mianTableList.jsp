<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%
	int fieldId = TeeStringUtil.getInteger(request.getParameter("fieldId"), 0);
%>
<title>业务模型显示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var fieldId=<%=fieldId%>;
var datagrid;
//初始化
function doInit(){
	//根据字段id  获取字段对象
	var url=contextPath+"/businessModelController/getFieldById.action";
	var json=tools.requestJsonRs(url,{fieldId:fieldId});
	var sqlFilter=json.rtData.sqlFilter;
    var controlModel=json.rtData.fieldControlModel;
    var modelJson=tools.strToJson(controlModel);
    //获取主表的id
    var  tableId=modelJson.mainTable;
    //获取主表主键的id
    var  pkId=modelJson.key;
    //获取主表和子表之间的映射关系
    var  mappingArray=modelJson.mapping;

    //主表信息列表
    var opts=[{field:"___",checkbox:true,width:1}];
    for(var i=0;i<mappingArray.length;i++){
    	var mappingJson=mappingArray[i];
    	var fieldName=mappingJson.main;
    	var fieldId1=mappingJson.mainId;
    	//根据字段id  获取字段对象
    	var url1=contextPath+"/businessModelController/getFieldById.action";
    	var json1=tools.requestJsonRs(url1,{fieldId:fieldId1});
    	//字段描述
    	var fieldDesc=json1.rtData.fieldDesc;
    	opts.push({field:fieldName,title:fieldDesc,width:1});
    }

	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+'/businessModelController/getMainTableRecordsList.action?mianTableId='+tableId+'&sqlFilter='+sqlFilter,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		pageSize : 30,
		pageList: [30,60,70,80,90,100],
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		remoteSort:true,
		columns:[opts]
	}); 
    
	
}

function doSelect(){
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
		alert("请至少选择一项数据");
		return;
	}
	//选中的记录
	var data = selections[0];
	//alert(tools.jsonObj2String(data));
	
	
	//根据字段id  获取字段对象
	var url=contextPath+"/businessModelController/getFieldById.action";
	var json=tools.requestJsonRs(url,{fieldId:fieldId});
    var controlModel=json.rtData.fieldControlModel;
    var fieldName=json.rtData.fieldName;
    var modelJson=tools.strToJson(controlModel);
    //获取主表的id
    var  tableId=modelJson.mainTable;
    //获取主表主键的id
    var  pkId=modelJson.key;
    //获取主表和子表之间的映射关系
    var  mappingArray=modelJson.mapping;

    for(var i=0;i<mappingArray.length;i++){
    	var mappingJson=mappingArray[i];
    	var mainfieldName=mappingJson.main;
    	var childfieldName=mappingJson.current;
    	xparent.$("#"+childfieldName).val(data[mainfieldName]);
    }
	
    xparent.$("#"+fieldName).val(pkId);
	window.close();
}

</script>


</head>
<body onload="doInit();">
<table id="datagrid" fit="true"></table>
<div id="toolbar">
		<div class="base_layout_top" style="position: static">
            <span class="easyui_h1" id="title1">主表信息</span>
            <button onclick="doSelect()" class="btn btn-primary" style="float: right;margin-right: 10px;margin-top: 3px;">确定</button>			
		</div>
		</br>
</div>
<div>

</div>
</body>
</html>