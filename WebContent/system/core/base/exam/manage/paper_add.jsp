<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
function doInit(){
	getExamStore();
}
function commit(){
	if($("#form1").valid()&&checkForm()){
		var param = tools.formToJson($("#form1"));
		var url = contextPath+"/TeeExamPaperController/addExamPaper.action";
		var json = tools.requestJsonRs(url,param);
		/* if(json.rtState){
			$.MsgBox.Alert_auto(json.rtMsg);
			location.href=contextPath+"/system/core/base/exam/manage/paperList.jsp";
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		} */
		return json;
	}
}

function getExamStore(){
	var url =contextPath+"/TeeExamStoreController/datagrid.action"
	var json = tools.requestJsonRs(url);
	var examStores = json.rows;
	var html = "<option value=\"0\"></option>";
	for(var i=0;i<examStores.length;i++){
		html+="<option value=\""+examStores[i].sid+"\">"+examStores[i].storeName+"</option>";
	}
	$("#storeId").html(html);
}

function checkForm(){
	if($("#storeId").val()=="" || $("#storeId").val()==null || $("#storeId").val()==0){
		$.MsgBox.Alert_auto("题库不能为空,请选择所属题库!");
		return false;
	}
	return true;
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<form method="post" name="form1" id="form1" >
	<table class="TableBlock" style="width:100%;font-size:12px;margin:0 auto;" >
		<tr>
			<td class="TableData" style="text-indent: 10px">
				试卷标题：
				</td>
			<td  class="TableData">
				<input type='text' class="easyui-validatebox BigInput" id="title" name="title" style="width:300px;height: 23px;" required="true"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				分数类型：
				</td>
			<td  class="TableData" >
				<select class="BigSelect"  id="scoreType" name="scoreType" style="width: 300px;height: 23px" >
					<option value="1">根据试题分值以百分比计算</option>
					<option value="2">按试题分数计分</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				试卷总分：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="scoreAll" name="scoreAll" style="width:300px;height: 23px"  positive_integer="true"/>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				试题数量：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="qCount" name="qCount" style="width:300px;height: 23px"  positive_integer="true"/>
			</td>
		</tr>
		 <tr>
			<td class="TableData" style="text-indent: 10px">
				考试时长：
				</td>
			<td  class="TableData">
				<input type='text' class="BigInput" id="examTimes" name="examTimes" style="width:300px;height: 23px"  positive_integer="true"/><span>（单位为分钟）</span>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				所属题库：
				</td>
			<td colspan="3" class="TableData">
				<select class=" BigSelect"  id="storeId" name="storeId" required style="height: 23px;width: 300px">
				
				</select>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent: 10px">
				试题说明：
				</td>
			<td  class="TableData">
				<textarea class="BigTextarea"  id="paperDesc" name="paperDesc" style='width:400px;height:60px;'></textarea>
			</td>
		</tr>
		
	</table>
</form>
</body>
<script>
$("#form1").validate();
</script>
</html>