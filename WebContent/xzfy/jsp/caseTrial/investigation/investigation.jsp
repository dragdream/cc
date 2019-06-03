<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%
	String id = UUID.randomUUID().toString();
	id = id.replace("-", ""); 
	String caseId = request.getParameter("caseId");
%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/xzfy/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<link rel="stylesheet" type="text/css" href="/supervise/caseManager/commonCase/css/caseAdd.css" />
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />
<title>调查管理</title>
</head>

<body style="margin-bottom: 10px;" >
	<!-- 调查管理Id -->
	<!-- 这是登记页面 -->
	
	<input type="hidden" id="id" value="<%= id%>">
	<input type="hidden" id="caseId" value="<%= caseId%>">
	<br>
	<form action="<%=contextPath%>/caseInvestigationController/save.action"  method="post" name="form1" id="form1" >
	<!-- 调查管理信息 -->	
	<div id="res">
		<div id="resDiv">
			<table align="center" width="90%" class="TableBlock">
				<tr>
					<td nowrap class="case-common-filing-td-class1"  >调查人姓名：<font style='color:red'>*</font></td>
					<td class="TableData" width="35%;" >
						<input value="" id="investName" name="investName" class="BigInput  easyui-validatebox" title="调查人姓名"/>
					</td>
					<td nowrap class="TableData"  width="15%;" >调查时间：<font style='color:red'>*</font></td>
					<td class="TableData" width="35%;" >
						<input type="text" name="startTime" id="startTime" value="" class="BigInput"  style="height:25px;width: 174px;" maxlength="100" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" title="调查时间">
					</td>
					<td nowrap class="TableData"  width="15%;" >调查方式：<font style='color:red'>*</font></td>
					<td class="TableData" width="35%;" >
						<select style="width: 174px" name="investType" id="investType" class="BigSelect" title="调查方式">
							<option></option>
						</select>
					</td>
				</tr>
				<tr>
				<td nowrap class="case-common-filing-td-class1"  width="15%;" >
					被调查人姓名：
				</td> 
				<td class="TableData" width="35%;" >
					<input style="width: 174px" type="text" name="respondent" id="respondent" size="" class="BigInput  easyui-validatebox" value="" title="被调查人姓名">
				</td>
				<td nowrap class="TableData"  width="15%;" >
					被调查人电话：
				</td> 
				<td class="TableData" width="35%;" >
					<input style="width: 174px" type="text" name="respondentPhoneNum" id="respondentPhoneNum" size="" class="BigInput  easyui-validatebox" value="" title="被调查人电话">
				</td>
				<td nowrap class="TableData"  width="15%;" >
					被调查人性别：
				</td> 
				<!-- respondentSex -->
				<td class="TableData" width="35%;" >
					<input name="respondentSex" type="radio" value="1" />男
					<input name="respondentSex" type="radio" value="0" />女
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData"  width="15%;" >
					调查地址：
				</td> 
				<td class="TableData" colspan="5">
					<input style="width: 800px" type="text" name="investPlace" id="investPlace" size="" class="BigInput  easyui-validatebox" value="" title="被调查人电话">
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData"  width="15%;" >
					告知权利与义务：
				</td> 
				<td class="TableData" colspan="5">
					<input style="width: 800px" type="text" name="notice" id="notice"  class="BigInput  easyui-validatebox" value="" title="告知权力与义务">
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData"  width="15%;" >
					调 查 情 况：
				</td> 
				<td class="TableData" colspan="5">
					<input style="width: 800px" type="text" name="investDetail" id="investDetail"  class="BigInput  easyui-validatebox" value="" title="调查情况">
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData"  width="15%;" >
					审查意见：
				</td> 
				<td class="TableData" colspan="5">
					<input style="width: 800px" type="text" name="result" id="result"  class="BigInput  easyui-validatebox" value="" title="审查意见">
				</td>
			</tr>
			</table>
		</div>	
	</div>
	<br>
	<h4>&nbsp;&nbsp;&nbsp;上传材料</h4></td>
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
var id = "<%= id%>";
var caseId = "<%= caseId%>";
// 保存
function save(callback){
	if(!checkForm()){
		callback(false);
	}
	$(form1).ajaxSubmit({
        type: 'post', // 提交方式 get/post
        url: '<%=contextPath%>/caseInvestigationController/save.action', // 需要提交的 url
        success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
            // 此处可对 data 作相关处理
        	callback(true);
        }
    });
	return false;
}

//回显
<%-- $(function(){
	if(id == "null"){
		return;
	}
	var para = {'id':id};
	$("#id").val(id);
	$("#caseId").val(caseId);
	debugger
	var url = "<%=contextPath%>/caseInvestigationController/getById.action";
	json = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var data = jsonRs.rtData;
		bindJsonObj2Cntrl(data);
	}
}); --%>
// <!--回显结束-->

function selectData(obj,data){
	$("#"+obj+" option").each(function(){
	        //遍历所有option
	        var text = $(this).text();
	        if(text == data){
	            this.selected = 'selected';
	        }
	    });
	$("#"+obj).next().val($("#"+obj).find("option:selected").text());
}


/*
 * 表单必填验证
 */
function checkForm(){
	
	//if(!checkVal("applicationDate","请填写申请行政复议日期"))return false;
	//if(!checkSelect("applicationItemCode","请选择申请行政复议事项"))return false;
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

// 点击打开案由维护页面
function weihu(){
	var para = {'parentId':4298};
	var url = "<%=contextPath%>/sysCode/getSysCodeByParentCodeNo.action";
	json = tools.requestJsonRs(url,para);
	console.log(json);
	 var title = "案由维护";
	 var url = contextPath + "/beidasoft/caseReport/caseReason.jsp?sid="+'4298';
	bsWindow(url ,title,{width:"600",height:"300",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-mini btn-danger"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			var isOk = cw.save();
			return isOk;
		}else if(v=="关闭"){
			return true;
		}
	}}); 
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

<%-- $(function(){
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
}); --%>

//文件上传，点击加号弹出浮层  zck
function add_page() {
	 var title = "文件上传";
	 var fileTypeCode = "01";
	 var fileType = "调查管理材料";
	 var url = "../common/uploadFile.jsp?fileTypeCode="+fileTypeCode+"&&fileType="+fileType+"&&id="+id+"&&caseId="+caseId;
	 bsWindow(url ,title,{width:"500",height:"200",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-mini btn-danger"}
		]
		,submit:function(v,h){
		var childwinow = h[0].contentWindow;
		if(v=="保存"){
			//获取到json数组
			var data = childwinow.fileUpload();
			//json字符串转成json对象
			data = eval('(' + data + ')');
			if (data.rtState) {
				//json数组
				var jsonData = data.rtData;
				for(var i = 0;i<jsonData.length;i++){
					//动态添加一个tr行
					var $tr = $("<tr></tr>");
					var $td3 = $("<td class='TableData' align='center'>" + jsonData[i].fileType + "<input type='hidden' name='uuid' value="+jsonData[i].id+"><input type='hidden' name='StoragePath' value="+jsonData[i].storagePath+"</td>");
					$tr.append($td3);
					var $td3 = $("<td class='TableData' align='center'>" + jsonData[i].fileName + "</td>");
					$tr.append($td3);
					var $td4 = $("<td class='TableData' style='text-align: center;'><a href='javascript:void(0);' onclick='deltrfile(this);'  title='删除'> <span><i style='color:red;' class='glyphicon glyphicon-minus'></i></span>");
					$tr.append($td4);
					$("#sqrcl").after($tr);
				}
			} else {
				alert("上传失败,请联系管理员!");
				return false;
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