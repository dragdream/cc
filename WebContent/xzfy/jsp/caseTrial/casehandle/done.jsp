<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="/xzfy/css/common/supervise.css" />
<!-- 中腾按钮框架 -->
<link rel="stylesheet" type="text/css" href="/common/zt_webframe/css/package1.css" />
<title>审理待办</title>
<style> 
	tr:nth-child(even){
		 background-color:#F0F0F0;
	}
</style>

</head>
<body style="height:100%" onload="doInit();">
<!-- 菜单栏 -->

<div class="base_layout_top" style="position:static">
    <img class = 'fl' style="margin-right: 10px; margin-top: 3px" src="<%=contextPath %>/common/zt_webframe/imgs/hygl/wdhy/我的会议.png">
	<span class="title" style="padding-top: 4px;">已办</span>
</div>
<!-- 查询项目 -->
<div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
	            <table class="none_table" width="100%">
	 				<tr>
	 					<td class="TableData TableBG">案件编号：
	 					    <input class="BigInput" type="text" name="caseNum" id="caseNum"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
	 					<td class="TableData TableBG" >申请方式：
	 						<select class="BigSelect" id="postType" name="postType" style="width: 172px;">
							    <option>---请选择---</option>
							</select>
	 					</td>
	 					
	 					<td class="TableData TableBG">申请人：
	 					    <input class="BigInput" type="text" name="name" id="name"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
	 					<td class="TableData TableBG" >案件状态：
	 						<select class="BigSelect" id="caseStatus" name="caseStatus">
							    <option>---请选择---</option>
							</select>
	 					</td>
	 				</tr>	
	 				<tr>	
	 					<td class="TableData TableBG">开始时间：
	 					 	<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput"/>
						</td>
						
						<td class="TableData TableBG">结束时间：
	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:170px"/>
	 					</td>
	 					
	 					<td class="TableData TableBG"></td>
	 					
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="search()">查询</button></td>
	 				</tr>
				</table>
			</form>
		</div>
   	</div>
<!-- 这是列表页 -->	
<div class="easyui-panel case-common-panel-body1" title="已办" style="width: 100%; margin-bottom: 10px; height: 390px!important;" id="common_case_add_person_div"
         align="center" data-options="tools:'#common_case_add_person'">
	<table class="TableBlock" style="height:100%" id="datagrid"></table>
</div>

<script type="text/javascript" src="<%=contextPath %>/system/core/base/meeting/js/meeting.js"></script>
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath %>/xzfy/js/caseTrial/common.js/casetriallist_done.js"></script>
<script type="text/javascript">

</script>
</body>
</html>