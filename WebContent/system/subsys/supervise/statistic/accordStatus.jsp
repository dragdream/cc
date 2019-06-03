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
<title>按状态</title>
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
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supervisionController/getSupCountByStatus.action",
		queryParams:param,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'status',title:'状态',width:120},
			{field:'count',title:'数量（项）',width:120},
			
		]]
	});

} 


//选择分类  
function selectType(){
	var url=contextPath+"/system/subsys/supervise/statistic/selectSupType.jsp";
	top.bsWindow(url ,"选择分类",{width:"400",height:"200",buttons:
		[
		 {name:"选择",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="选择"){		
			var selections =cw.$('#treeGrid').treegrid('getSelections');
			$("#typeId").val(selections[0].sid);
			$("#typeName").val(selections[0].typeName);
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


//清空分类
function clearType(){
	$("#typeId").val("");
	$("#typeName").val("");
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
			  </span> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
	      任务分类：<input type="text" name="typeId" id="typeId" style="display: none"/>
            <input type="text" style="width: 200px;height: 23px" name="typeName" id="typeName" readonly="readonly"/>
            <a href="#" onclick="selectType()">选择</a>&nbsp;&nbsp;<a href="#" onclick="clearType()">清空</a>
              
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;开始时间：<input type="text" id='beginTimeStr1' name='beginTimeStr1' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginTimeStr2\')}'})" class="Wdate BigInput" style="width: 90px;height: 23px" />
              ~&nbsp;&nbsp;&nbsp;<input type="text" id='beginTimeStr2' name='beginTimeStr2' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTimeStr1\')}'})" class="Wdate BigInput" style="width: 90px;height: 23px" />
		  
		 &nbsp;<input type="button" value="查询" class="btn-win-white" title="查询" onclick="query()" >
		  <input type="reset" value="重置" class="btn-win-white" title="重置" >
	  
     </div>
  </form> 
</div>
  <table id="datagrid" fit="true"></table> 
</body>
</html>