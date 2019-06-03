<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>考勤统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%-- <link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/> --%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>  --%>
<script>
/**
 * 初始化
 */
function doInit(){
	getDeptParent();
}

/**
 * 确定
 */
function doSubmit(){
	if($("#form1").valid() && checkForm()){
		var deptIds =$("#deptIds").val();
		//获取统计月份
		var countMonth = $("#countMonth").val();
		//获取这个月的天数
		var sumDays=getDays(countMonth.split('-')[0],countMonth.split('-')[1]);

		//开始统计时间
		var startDateDesc = countMonth+"-01";
		//结束统计时间 
		var endDateDesc = countMonth+"-"+sumDays;

		location.href=contextPath+"/system/core/base/attend/query/query.jsp?deptIds="+deptIds+"&startDateDesc="+startDateDesc+"&endDateDesc="+endDateDesc+"&countMonth="+countMonth;
	}
}


//计算指定年月的天数
var getDays = function(year, month) {
    // month 取自然值，从 1-12 而不是从 0 开始
    return new Date(year, month, 0).getDate()

    // 如果 month 按 javascript 的定义从 0 开始的话就是
    // return new Date(year, month + 1, 0).getDate()
}



/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false,
				onAsyncSuccess:setDeptParentSelct
			};
		zTreeObj = ZTreeTool.config(config);
		//setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}

function checkForm(){
	if($("#deptNames").val()==null || $("#deptNames").val()=="" ||$("#deptNames").val()=="null" ){
		  $.MsgBox.Alert_auto("请至少选择一个部门！");
		  return false;
	  } 
	
	if($("#countMonth").val()==null || $("#countMonth").val()=="" ||$("#countMonth").val()=="null" ){
		  $.MsgBox.Alert_auto("请选择统计月份！");
		  return false;
	  } 
	
	return true;
}
</script>
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
.combo-arrow{
    vertical-align: middle;
}
</style>
</head>
<body onload="doInit()"  style="overflow-y:auto;overflow-x:hidden;padding-top:25px;">
	<center>
		<div id="time_info" >
				<form method="post" name="form1" id="form1">
					<table class="none-table" style="width:60%;font-size:12px;margin:0 auto;">
							<tr class="TableHeader" style="font-size:16px;border-bottom: 2px solid #b0deff;"><td colspan="2">考勤统计</td></tr>
							<tr class="TableData">
							<td width="30%" align="left">部门：</td>
							<%-- <td align="left">
								   <input type="hidden" name="deptId" id="deptId" value=""/>
							       <input type="text"  name="deptName" id="deptName" class="BigInput" style="height: 23px;width: 200px"/>
							       <span class='addSpan'>
									<img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
									 &nbsp;&nbsp;
									 <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('deptId','deptName')" value="清空"/>
								</span>
							</td> --%>
							<td align="left">
								   <div style="padding-top: 5px">
								      <input type="hidden" name="deptIds" id="deptIds" value=""/>
								      <textarea rows="6" cols="40" name="deptNames" id="deptNames" readonly="readonly"></textarea>
							          <span class='addSpan'>
									   <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectDept(['deptIds','deptNames'],'14')" value="选择"/>
									    &nbsp;&nbsp;
									   <img src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('deptIds','deptNames')" value="清空"/>
								      </span>
								     </div>
								
							</td>
							</tr>
							
							
							<tr class="TableData">
							<td width="30%" align="left">统计月份：</td>
							<td align="left">
								<input type="text" id='countMonth' onfocus="WdatePicker({dateFmt:'yyyy-MM'})" name='countMonth' class="Wdate BigInput"   style="height: 23px;width: 200px"/>
							</td></tr>
							
							<tr class="TableData" style="border-bottom:none;">
							<td colspan="2" align="center">
								<input onclick="doSubmit();" type="button" value="统计" class="btn-win-white fr"/>
							</td></tr>
					</table>
				</form>
		</div>
</center>
</body>
</html>

