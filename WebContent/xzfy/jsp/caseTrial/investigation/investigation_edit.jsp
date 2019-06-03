<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
	String id = request.getParameter("id");
	String caseId = request.getParameter("caseId");
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>
<script type="text/javascript" src="<%=contextPath%>/beidasoft/caseReport/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<title>案件填报</title>
</head>

<body style="margin-bottom: 10px;" >
	<br>
	<form action="<%=contextPath%>/caseInvestigationController/update.action"  method="post" name="form1" id="form1" >
	<div>
		<input type="hidden" name="id" id="id" value="<%=id %>"/>
		<input type="hidden" name="caseId" id="caseId" value="<%=caseId %>"/>
	</div>

	<!-- 调查管理信息 -->	
	<div id="res">
		<div id="resDiv">
			<table align="center" width="90%" class="TableBlock">
				<tr>
					<td nowrap class="TableData"  width="15%;" >调查人姓名：<font style='color:red'>*</font></td>
					<td class="TableData" width="35%;" >
						<input value="" id="investName" name="investName" class="BigInput  easyui-validatebox" />
					</td>
					<td nowrap class="TableData"  width="15%;" >调查时间：<font style='color:red'>*</font></td>
					<td class="TableData" width="35%;" >
						<input value="" id="startTime" name="startTime" type="hidden"/>
					</td>
					<td nowrap class="TableData"  width="15%;" >调查方式：<font style='color:red'>*</font></td>
					<td class="TableData" width="35%;" >
						<input value="" id="investType" name="investType" type="hidden"/>
					</td>
				</tr>
				<tr>
				<td nowrap class="TableData"  width="15%;" >
					被调查人姓名：
				</td> 
				<td class="TableData" width="35%;" >
					<input style="width: 174px" type="text" name="respondent" id="respondent" size="" class="BigInput  easyui-validatebox" value="">
				</td>
				<td nowrap class="TableData"  width="15%;" >
					被调查人电话：
				</td> 
				<td class="TableData" width="35%;" >
					<input style="width: 174px" type="text" name="respondentPhoneNum" id="respondentPhoneNum" size="" class="BigInput  easyui-validatebox" value="">
				</td>
				<td nowrap class="TableData"  width="15%;" >
					被调查人性别：
				</td> 
				<td class="TableData" width="35%;" >
					<input style="width: 174px" type="text" name="respondentSex" id="respondentSex" size="" class="BigInput  easyui-validatebox" value="">
				</td>
			</tr>
			</table>
		</div>	
	</div>
	<br>
	<h4>&nbsp;&nbsp;&nbsp;调查相关材料</h4></td>
	<table id="upload1" align="center" width="90%" class="TableBlock">
		<tr id="sqrcl">
			<td align='center' class="TableData">文件类型</td>
			<td align='center' class="TableData">文件名称</td>
			<td align='center' class="TableData"><a class="glyphicon glyphicon-plus" style="color:green;" href="javascript:void(0);" onclick="add_page()"></a></td>
		</tr>
	</table>
	<br>
</form>
<script type="text/javascript">
var id = "<%=request.getParameter("id")%>";
//回显
$(function(){
	if(id == "null") return;
	var para = {'id':id};
	var url = "<%=contextPath%>/caseInvestigationController/getById.action";
	jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		bindJsonObj2Cntrl(data);
	}else{
		alert("请求失败");
	}
});
// 保存
function save(callback){
	if(!checkForm()){
		callback(false);
	}
	$(form1).ajaxSubmit({
        type: 'post', // 提交方式 get/post
        url: '<%=contextPath%>/caseInvestigationController/update.action', // 需要提交的 url
        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
            // 此处可对 data 作相关处理
        	callback(true);
        }
    });
	return false;
}

/*
 * 表单必填验证
 */
function checkForm(){
	/* if(!checkVal("applName","请输入姓名"))return false;
	if(!checkSelect("applGenderSelect","请选择性别"))return false; */
}

// 验证input的val是否为空
function checkVal(obj,tip){
	if($("#"+obj).val()==""){
		$('#'+obj).tips({
			side : 3,
			msg : tip,
			bg : '#AE81FF',
			time : 2
		});
		return false;
	}
	return true;
}
// 验证select是否选中
function checkSelect(obj,tip){
	var selectObj = document.getElementById(obj);
	var activeIndex = selectObj.options[selectObj.selectedIndex].value;
	if (activeIndex == ""){
		$('#'+obj).tips({
			side : 3,
			msg : tip,
			bg : '#AE81FF',
			time : 2
		});
		return false;
	}
	return true;
}
// 验证radio是否选中
function checkRadio(obj,tip){
	var val = $('#'+obj).val();
	if(val ==""){
		$('#'+obj).tips({
			side : 3,
			msg : tip,
			bg : '#AE81FF',
			time : 2
		});
		return false;
	}
	return true;
}

//删除文件
function deltrfile(obj) {
	var msg = "您真的确定要删除这个文件吗？\n\n请确认！";
	if (!confirm(msg)){
		return false;
	}
	var table = obj.parentNode.parentNode.parentNode;
	var file_id = obj.parentNode.parentNode
			.getElementsByTagName("INPUT")[0].value;
	var file_url = obj.parentNode.parentNode
			.getElementsByTagName("INPUT")[1].value;
	var para = {'fileId':file_id};
	var url = "<%=contextPath%>/wjxxController/deleteById.action";
	tools.requestJsonRs(url,para);
	table.deleteRow(obj.parentNode.parentNode.rowIndex);
}

$(function(){
	// 回显文件信息
	var para = {'caseId':id};
	var url = "<%=contextPath%>/register/getFileInfo.action";
	json = tools.requestJsonRs(url,para);
	console.log(json);
	
	var length = json.rows.length;
	for(var i = 0; i < length; i++){
		var data = json.rows[i];
		var $tr = $("<tr></tr>");
		var $td3 = $("<td class='TableData' align='center'>"
				+ data.fileType + "<input type='hidden' name='uuid' value="+data.uuid+"><input type='hidden' name='fileUrl' value="+data.fileUrl+"</td>");
		$tr.append($td3);
		var $td3 = $("<td class='TableData' align='center'><a href='/wjxxController/downErrFile.action?fileurl="+data.fileUrl+"&&fileName="+data.fileName+"' target='_blank' title='点击下载文件' >"
				+ data.fileName + "</a></td>");
		$tr.append($td3);
		var $td4 = $("<td class='TableData' style='text-align: center;'><a href='javascript:void(0);' onclick='deltrfile(this);'  title='删除'> <span><i style='color:red;' class='glyphicon glyphicon-minus'></i></span>");
		$tr.append($td4); 
		var fileTypeCode = data.fileTypeCode;
		// 判断是否复议案件申请材料，是的话回显
		if(fileTypeCode == "01"){
			$("#sqrcl").after($tr);
		}
	}
});

//文件上传，点击加号弹出浮层
function add_page() {
	 var title = "文件上传";
	 var url = "/beidasoft/caseReport/uploadFile.jsp?fileTypeCode=01";
	bsWindow(url ,title,{width:"800",height:"300",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-mini btn-danger"}
		]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var data = cw.fileUpload();
			//json字符串转成json对象
			data = eval('(' + data + ')');
			if (data.rtState) {
				//动态添加一个tr行
				//创建tr组件
				var $tr = $("<tr></tr>");
				var $td3 = $("<td class='TableData' align='center'>"
						+ data.rtData.fileType + "<input type='hidden' name='uuid' value="+data.rtData.uuid+"><input type='hidden' name='fileUrl' value="+data.rtData.fileUrl+"</td>");
				$tr.append($td3);
				var $td3 = $("<td class='TableData' align='center'>"
						+ data.rtData.fileName + "</td>");
				$tr.append($td3);
				var $td4 = $("<td class='TableData' style='text-align: center;'><a href='javascript:void(0);' onclick='deltrfile(this);'  title='删除'> <span><i style='color:red;' class='glyphicon glyphicon-minus'></i></span>");
				$tr.append($td4);
				$("#sqrcl").after($tr);
			} else {
				alert("上传失败");
			}
			return true;
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

</script>	

</body>
  
</html>