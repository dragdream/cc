<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<%
   String projectId=TeeStringUtil.getString(request.getParameter("projectId"),"");//项目主键
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);//任务主键
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" style="background-color: #f2f2f2">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
<title>新增/编辑任务</title>
<style>
input,select{
  height:23px
}
</style>
<script >
var projectId="<%=projectId%>";
var sid=<%=sid%>;
function doInit(){
	initManager(projectId);
	initOtherTasks();
	if(sid>0){//初始化 数据
		getInfoBySid();
		
	}
	
}

//初始化数据
function getInfoBySid(){
	var url=contextPath+"/taskController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		bindJsonObj2Cntrl(json.rtData);
	}
}

	//初始化任务负责人
	function initManager(projectId) {
		var url = contextPath + "/projectController/getManagerOrMember.action";
		var json = tools.requestJsonRs(url, {
			uuid : projectId
		});
		if (json.rtState) {
			var data = json.rtData;
			for (var i = 0; i < data.length; i++) {
				$("#managerId").append(
						"<option value="+data[i].uuid+">" + data[i].userName
								+ "</option>");
			}
		} else {
			$.MsgBox.Alert_auto("数据获取失败！");
		}

	}
	//选择流程
	function selectFlowType() {
		var url = contextPath
				+ "/system/subsys/project/projectdetail/flowTree.jsp";
		parent.bsWindow(url, "选择相关流程", {
			width : "600",
			height : "220",
			buttons : [ {
				name : "确定",
				classStyle : "btn-alert-blue"
			}, {
				name : "关闭",
				classStyle : "btn-alert-gray"
			} ],
			submit : function(v, h) {
				var cw = h[0].contentWindow;
				if (v == "确定") {
					var rt = cw.commit();
					document.getElementById('flowTypeNames').value = rt.names;
					document.getElementById('flowTypeIds').value = rt.ids;
					return true;
				} else if (v == "关闭") {
					return true;
				}
			}
		});
	}

	//初始化  上级任务  前置任务的下拉列表
	function initOtherTasks() {
		var url = contextPath
				+ "/taskController/getOtherTasksByProjectId.action";
		var json = tools.requestJsonRs(url, {
			projectId : projectId,
			sid : sid
		});
		if (json.rtState) {
			data = json.rtData;
			for (var i = 0; i < data.length; i++) {
				$("#higherTaskId").append(
						"<option value="+data[i].sid+">" + data[i].taskName
								+ "</option>");
				$("#preTaskId").append(
						"<option value="+data[i].sid+">" + data[i].taskName
								+ "</option>");
			}
		}
	}

	//保存
	function commit() {
		if (check()) {
			var url = contextPath + "/taskController/addOrUpdate.action";
			var param = tools.formToJson("#form1");
			var json = tools.requestJsonRs(url, param);
			return json.rtState;

		}

	}

	//获取天数
	function getDays() {
		var startTime = document.getElementById("beginTimeStr").value;
		var endTime = document.getElementById("endTimeStr").value;
		if (startTime != null && endTime != null) {
			var days = DateDiff(startTime, endTime);
			$("#days").val(days + 1);
		}
	}

	function DateDiff(sDate1, sDate2) {
		var iDays;
		sDate1 = sDate1.replace(/-/g, "/");
		var aDate = new Date(sDate1);
		sDate2 = sDate2.replace(/-/g, "/");
		var oDate = new Date(sDate2);
		iDays = parseInt(Math.abs(oDate - aDate) / 1000 / 60 / 60 / 24); //把相差的毫秒數轉抽象為天數
		return iDays;
	}

	//验证
	function check() {
		var taskNo = $("#taskNo").val();
		var taskName = $("#taskName").val();
		var managerId = $("#managerId").val();
		var beginTimeStr = $("#beginTimeStr").val();
		var endTimeStr = $("#endTimeStr").val();
		if (taskNo == null || taskNo == "") {
			$.MsgBox.Alert_auto("请填写任务序号！");
			return false;
		}
		if (taskName == null || taskName == "") {
			$.MsgBox.Alert_auto("请填写任务名称！");
			return false;
		}
		if (managerId == null || managerId == "" || managerId == 0) {
			$.MsgBox.Alert_auto("请选择任务负责人！");
			return false;
		}
		if (beginTimeStr == null || beginTimeStr == "") {
			$.MsgBox.Alert_auto("请选择开始时间！");
			return false;
		}
		if (endTimeStr == null || endTimeStr == "") {
			$.MsgBox.Alert_auto("请选择结束时间！");
			return false;
		}
		return true;
	}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_addfield.jpg">
		<span class="title">新增/编辑任务</span>
	</div>
  <!--  <div class="fr right">
      <input type="button" value="保存" class="btn-win-white" onclick="save(1);"/>
      <input type="button" value="返回" class="btn-win-white" onclick="back();"/>
   </div> -->
</div>

<form  method="post" name="form1" id="form1" >
<table class="TableBlock" width="100%" align="center">
	<input type="hidden" name="sid" id="sid" value="<%=sid %>" />
	<input type="hidden" name="projectId" id="projectId" value="<%=projectId %>" />
	<tr>
    <td  class="TableData" width="150" style="text-indent:15px">任务序号：</td>
    <td  class="TableData" align="left" id="groupSelect">
       <input type="text" name="taskNo" id="taskNo" style="height: 23px;width: 350px" />
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">任务名称：</td>
    <td  class="TableData" align="left">
        <input type="text" name="taskName" id="taskName" style="height: 23px;width: 350px" />
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">任务级别：</td>
    <td  class="TableData" align="left">
        <select id="taskLevel" name="taskLevel">
            <option value="次要">次要</option>
            <option value="一般">一般</option>
            <option value="重要">重要</option>
            <option value="非常重要">非常重要</option>
        </select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">负责人：</td>
    <td  class="TableData" align="left">
        <select id="managerId" name="managerId">
            <option value="0">请选择负责人</option>
        </select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">上级任务：</td>
    <td  class="TableData" align="left">
        <select id="higherTaskId" name="higherTaskId">
            <option value="0">请选择上级任务</option>
        </select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">前置任务：</td>
    <td  class="TableData" align="left">
        <select id="preTaskId" name="preTaskId">
            <option value="0">请选择前置任务</option>
        </select>
    </td>
   </tr>
   <tr>
    <td  class="TableData" width="150" style="text-indent:15px">计划周期：</td>
    <td  class="TableData" align="left">
       <input type="text" id='beginTimeStr' name='beginTimeStr' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'beginTimeStr\')}'})" class="Wdate BigInput" style="width: 160px" />
				&nbsp;至&nbsp;
	   <input type="text" id='endTimeStr' name='endTimeStr' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'beginTimeStr\')}'})" class="Wdate BigInput" style="width: 160px" onblur="getDays()"/>
               &nbsp;&nbsp;&nbsp; &nbsp;共&nbsp;
        <input type="text" id='days' name='days'  class="BigInput" style="width: 100px" />&nbsp;天&nbsp;
    </td>
   </tr>
    <tr>
		<td  class="TableData" width="150" style="text-indent:15px">相关流程：</td>
		<td  class="TableData">
		   <input type="hidden" name="flowTypeIds" id="flowTypeIds"/>
		   <textarea rows="4" cols="" style="width: 350px" id="flowTypeNames"  name="flowTypeNames" class="BigTextarea" readonly="readonly"></textarea>
		   &nbsp;&nbsp;<a href="#" onclick="selectFlowType()">选择流程</a>
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">任务描述：</td>
		<td  class="TableData">
		   <textarea rows="6" style="width: 550px" name="description" id="description"></textarea>
		</td>
	</tr>
	<tr>
		<td  class="TableData" width="150" style="text-indent:15px">备注：</td>
		<td  class="TableData">
		   <textarea rows="6" style="width: 550px" name="remark" id="remark"></textarea>
		</td>
	</tr>
</table>

  </form>
</body>
</html>