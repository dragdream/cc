<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/My97DatePicker/WdatePicker.js"></script>
<title>考勤数据刷新</title>
<script type="text/javascript">
//刷新
function refreshData(){
	if(check()){
		var deptId=$("#deptId").val();
		var month=$("#month").val();
		bsWindow(contextPath+"/TeeAttendDutyController/refreshData.action?deptId="+deptId+"&month="+month ,"刷新进度",{width:"600",height:"320",buttons:
			[
		 	 {name:"关闭",classStyle:"btn-alert-gray"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
				if(v=="保存"){
				
				}else if(v=="关闭"){
					return true;
				}
			}});
	}
	
}


//验证
function  check(){
	var deptId=$("#deptId").val();
	var month=$("#month").val();
	
	if(deptId==""||deptId=="null"||deptId==null){
		$.MsgBox.Alert_auto("请选择部门！");
		return false;
	}
	
	if(month==""||month=="null"||month==null){
		$.MsgBox.Alert_auto("请选择月份！");
		return false;
	}
	
	return true;
}


</script>

</head>

<body  onload="" style="padding-left: 10px;padding-right: 10px;">


<form id="form1" name="form1">

<table class="TableBlock_page" style="width:100%;margin-top: 20px">
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			部门：
		</td>
		<td class="TableData">
			 <input type="hidden" name="deptId" id="deptId" value=""/>
	         <input type="text"  name="deptName" id="deptName" class="BigInput" style="height: 23px;width: 200px"/>
	         <span class='addSpan'>
			   <img src="/common/zt_webframe/imgs/xzbg/tzgg/icon_select.png" onClick="selectSingleDept(['deptId','deptName'],'14')" value="选择"/>
			    &nbsp;&nbsp;
			   <img src="/common/zt_webframe/imgs/xzbg/tzgg/icon_cancel.png" onClick="clearData('deptId','deptName')" value="清空"/>
		     </span>	
		</td>
	</tr>
	<tr>
		<td class="TableHeader" style="width:100px;text-indent: 15px;">
			月份：
		</td>
		<td class="TableData">
			<input style="width:200px;" type="text" name="month" id="month" size="20" readonly onfocus="WdatePicker({dateFmt:'yyyy-MM'})" class="Wdate BigInput">
		</td>
	</tr>
	<tr>
		<td colspan="2">
		   <div align="center">
		        <input type="button" class="btn-win-white" value="刷新" onclick="refreshData()"/>
		   </div>	 
		</td> 
	</tr>
</table>
</form>	
</div>
</body>

</html>