<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>


<script>


//验证
function check(){
	if($("#storeCode").val()==""||$("#storeCode").val()==null){
		   $.MsgBox.Alert_auto("题库编号不能为空！");
		   return false;
	   }	
	   if($("#storeName").val()==""||$("#storeName").val()==null){
		   $.MsgBox.Alert_auto("题库名称不能为空！");
		   return false;
	   }
	   return true;
}


function commit(){
	if(check()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeExamStoreController/addExamStore.action";
		var json = tools.requestJsonRs(url,param);
		return json;
	}
}

</script>

</head>
<body onload="" style="background-color: #f2f2f2">
<form id="form1" name="form1">
	<table class="TableBlock" style="width:100%;font-size:12px;margin:0 auto;" >
		<tr>
			<td class="TableData" style="text-indent: 10px">
				题库编号：
				</td>
			<td colspan="3" class="TableData">
				<input type='text' class="BigInput" style="width: 250px;height: 23px;"  id="storeCode" name="storeCode" required="true"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				题库名称：
				</td>
			<td colspan="3" class="TableData">
				<input type='text' class="BigInput" id="storeName" name="storeName" style="width:250px;height: 23px" required="true" />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				题库说明：
				</td>
			<td colspan="3" class="TableData">
				<textarea class="BigTextarea"  id="storeDesc" name="storeDesc" style='width:350px;height:80px;'></textarea>
			</td>
		</tr>
	</table>
</form>
</body>
</html>