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
<title>相关客户</title>
<script type="text/javascript">
var runId=<%=runId %>;
var datagrid ;
//初始化
function doInit(){
	initAddressCtr();
	
	doSearch();
}

//初始化级联地址控件
function initAddressCtr(){
	addressInit("province","city","district");
}

function doSearch(){
	var param=tools.formToJson($("#form1"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeFlowRelatedResourceController/relatedCustomerList.action",
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
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'customerName',title:'客户名称',width:200},
			{field:'customerNum',title:'客户编码',width:120},
			{field:'typeDesc',title:'客户性质',width:100},
			{field:'province',title:'所属省',width:100},
			{field:'city',title:'所属市',width:100},
			{field:'district',title:'所属区',width:100},
			{field:'managerPerson',title:'负责人',width:100}
		]]
	});
}



//点击确定
function doSave(){
	var params = {};
	var selections = $("#datagrid").datagrid("getSelections");
	if(selections.length==0){
        $.MsgBox.Alert_auto("请勾选相关客户！");		
	}else{
		var sids = [];
		for(var i=0;i<selections.length;i++){
			sids.push(selections[i].sid);
		}
		params["relatedIds"]=sids.join(",");
		params["type"]=3;
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
      	 <td nowrap>客户名称：</td>
         <td>
         	<input type="text" style="height:23px;width:100px" name="customerName" id="customerName" />
         </td>
         <td nowrap>客户编码：</td>
         <td>
         	<input type="text" style="height:23px;width:100px" name="customerNum" id="customerNum" />
         </td>
         <td nowrap>客户性质：</td>
         <td>
         	<select id="type" name="type">
         	    <option value="0">请选择</option>
         	    <option value="1">客户</option>
         	    <option value="2">供应商</option>
         	</select>
         </td>
         <td nowrap>负责人：</td>
         <td> 
            <input name="managerPersonId" id="managerPersonId" type="hidden"/>
			 <input name="managerPersonName" id="managerPersonName" type="text" style="height:23px;width:100px"/>
			<span class='addSpan'>
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_select.png" onclick="selectSingleUser(['managerPersonId','managerPersonName'],'14')" value="选择"/>
			   &nbsp;
			   <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/gryj/icon_cancel.png" onclick="clearData('managerPersonId','managerPersonName')" value="清空"/>
			</span>
         </td>
      </tr>
      <tr>
         <td>省：</td>
         <td>
			<select id="province" name="province" style="height: 23px;width: 102px">
			</select>
         </td>
         <td>市：</td>
         <td>
             <select id="city" name="city" style="height: 23px;width: 102px">
             </select>
         </td>
         <td>区/县：</td>
         <td>
         	 <select id="district" name="district" style="height: 23px;width: 102px">
         	 </select>
         </td>
         <td><input type="button" value="查询" class="btn-win-white" onclick="doSearch();"/></td>
      </tr>
   </table>
  </form>
</div>
<table id="datagrid" fit="true"></table>

</body>
</html>
</html>