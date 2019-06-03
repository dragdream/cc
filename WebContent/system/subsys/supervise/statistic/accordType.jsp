<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.tree.js'></script>
<script type="text/javascript" src = '<%=contextPath %>/common/zt_webframe/js/jquery.treegrid.js'></script>
<title>按部门</title>
</head>
<script>
var datagrid;
//初始化
function doInit(){
	query();
	
	
}

//查询
function query(){
	var param=tools.formToJson("#form1");
	$("#treeGrid").treegrid({
		url: contextPath + "/supervisionController/getSupCountByType.action",
		method: 'get',
		queryParams:param,
        idField: 'sid',
        toolbar:"#toolbar",
        treeField: 'typeName',
        pagination:false,
        border:false,
		columns:[[
			{field:'typeName',title:'分类名称',width:300},
			{field:'sumCount',title:'任务总数',width:100},
			{field:'count1',title:'待发布',width:100},
			{field:'count2',title:'待签收',width:100},
			{field:'count3',title:'正常办理中',width:100},
			{field:'count4',title:'逾期办理中',width:100},
			{field:'count5',title:'已暂停',width:100},
			{field:'count6',title:'正常已办结',width:100}, 
			{field:'count7',title:'逾期已办结',width:100},
			
		]]
	});
	
	
	
}






</script>
<body onload="doInit()">
<div id="toolbar" class = "clearfix">
  <form  method="post" name="form1" id="form1" >
     <div class="left fl setHeight">
         
	         主办部门：<input name="deptId" id="deptId" type="text" style="display:none;"/>
			  <input type="text" id="deptName" name="deptName" style="height:23px;width: 200px"  readonly/>
			  <span class='addSpan'>
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_select.png" onclick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
					   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/system/subsys/supervise/imgs/icon_cancel.png" onclick="clearData('deptId','deptName')" value="清空"/>
			  </span>
              
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" id='beginTimeStr1' name='beginTimeStr1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginTimeStr2\')}'})" class="Wdate BigInput" style="width: 170px;height: 23px" />
              ~&nbsp;&nbsp;&nbsp;<input type="text" id='beginTimeStr2' name='beginTimeStr2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTimeStr1\')}'})" class="Wdate BigInput" style="width: 170px;height: 23px" />
		  
		  &nbsp;&nbsp;<input type="button" value="查询" class="btn-win-white" title="查询" onclick="query()" >&nbsp;&nbsp;
		  <input type="reset" value="重置" class="btn-win-white" title="重置" >
	  
     </div>
  </form> 
</div>
<table id="treeGrid" class="easyui-treegrid" fit="true" style="width: 100%"></table>
</body>
</html>