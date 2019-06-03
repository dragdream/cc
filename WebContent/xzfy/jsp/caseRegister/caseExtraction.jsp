<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>案件提取</title>
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">

   <div id="toolbar" class = " clearfix" style="margin-top: 5px">
      
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
	            <table class="none_table" width="100%">
	 				<tr>
	 					<td class="TableData TableBG">案件编号：
	 					    <input class="BigInput" type="text" name="caseNum" id="caseNum"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
	 					<td class="TableData TableBG">申请人：
	 					    <input class="BigInput" type="text" name="name" id="name"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
	 					<td class="TableData TableBG">被申请人：
	 					    <input class="BigInput" type="text" name="respondentName" id="respondentName"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
	 					<td class="TableData TableBG">接待人：
	 					    <input class="BigInput" type="text" name="dealMan1Id" id="dealMan1Id"
	 					        onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5a-zA-Z0-9\w]/g,'')" />
	 					</td>
	 					
	 				<tr>	
	 					<td class="TableData TableBG">开始时间：
	 					
	 					 	<input type="text" name="startTime" id="startTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput" style="width:172px;height: 20px"/>
						</td>
						
						<td class="TableData TableBG">结束时间：

	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:172px;height: 20px"/>
	 					</td>
	 					
 						<td class="TableData">
 						    <button class="btn-win-white" type="button" onclick="search()">查询</button>&nbsp;
 						    <button class="btn-win-white" type="button" onclick="formReset()">重置</button>&nbsp;
 						    <!-- <input type="button" class="btn-del-red" onclick="setMergeCase()" value="并案"/> -->
 						</td>
	 				</tr>
				</table>
			</form>
		</div>
   	</div>
    <table id="datagrid" fit="true"></table> 
    <iframe id="exportIframe" style="display:none"></iframe>
    <script type="text/javascript" src="/xzfy/js/caseRegister/caseExtraction.js"></script>

</body>
</html>