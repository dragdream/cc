<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   int runId=TeeStringUtil.getInteger(request.getParameter("runId"),0);
%>
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade_search.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>相关项目</title>
<script type="text/javascript">
var runId=<%=runId %>;
var datagrid ;
//初始化
function doInit(){
	initProjectType();
	doSearch();
}



//初始化项目类型
function initProjectType(){
	var url=contextPath+"/projectTypeController/getTypeList.action";
	var json=tools.requestJsonRs(url);
    if(json.rtState){
    	var data=json.rtData;
		for(var i=0;i<data.length;i++){
			$("#projectTypeId").append("<option value="+data[i].sid+" >"+data[i].typeName+"</option>");
		}
    }	

}

function doSearch(){
	var param=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFlowRelatedResourceController/relatedProjectList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		queryParams:param,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'uuid',checkbox:true,title:'ID',width:200},
			{field:'projectName',title:'项目名称',width:200},
			{field:'typeName',title:'项目类型',width:120},
			{field:'projectLevel',title:'项目级别',width:100},
			{field:'managerName',title:'负责人',width:100},
		]]
	});
}



//点击确定
function doSave(){
	var params = {};
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
        $.MsgBox.Alert_auto("请勾选相关项目！");		
	}else{
		var sids = [];
		for(var i=0;i<selections.length;i++){
			sids.push(selections[i].uuid);
		}
		
		params["relatedIds"]=sids.join(",");
		params["type"]=4;
		params["runId"]=runId;
		var url=contextPath+"/TeeFlowRelatedResourceController/add.action";
		var json=tools.requestJsonRs(url,params);
		return json; 
		
	}
	
}
</script>
</head>
<body onload="doInit();">
<div id="toolbar" class = "topbar clearfix">
  <form id="form1">
   <table style="width:100%">
      <tr>
      	 <td nowrap>项目名称：</td>
         <td>
         	<input type="text" style="height:23px;" name="projectName" id="projectName" />
         </td>
         <td nowrap>项目类型：</td>
         <td>
         	<select id="projectTypeId" name="projectTypeId" style="height: 23px;">
         	   <option value="0">请选择</option>
         	</select>
         </td>
         <td nowrap>项目级别：</td>
         <td>
         	<select name="projectLevel" id="projectLevel" style="height: 23px;">
         	   <option value="">请选择</option>
		       <option value="A">A</option>
		       <option value="B">B</option>
		       <option value="C">C</option>
		       <option value="D">D</option>
		    </select>
         </td>
     </tr>
     <tr>
        <td nowrap>负责人：</td>
        <td> 
           <input name="managerPersonId" id="managerPersonId" type="hidden"/>
		 <input name="managerPersonName" id="managerPersonName" type="text" style="height:23px;"/>
		<span class='addSpan'>
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['managerPersonId','managerPersonName'],'14')" value="选择"/>
		   &nbsp;
		   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerPersonId','managerPersonName')" value="清空"/>
		</span>
        </td>
        <td colspan="2">
           <input type="button" class="btn-win-white" value="查询" onclick="doSearch();"/>
        </td>
     </tr>
     
   </table>
  </form>
</div>
<table id="datagrid" fit="true"></table>

</body>
</html>
</html>