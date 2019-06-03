<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/hr/training/js/training.js"></script>
<script type="text/javascript" src="<%=contextPath%>/beidasoft/caseReport/js/jquery.tips.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script src="<%=contextPath %>/common/jsc/orgselect.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery.form.min.js"></script>
<title>模板管理</title>
<style>
	.btn-win-white {
	   color: white;
	   height: 30px;
	   width: 80px;
	   background-color: #4a8bf5;
	   border: 0;
	   line-height: 30px;
	   border-radius: 5px;
	   }
</style>
</head>

<body style="margin-bottom: 10px;" >
	<br>
	<form action="/templateController/saveTemplate.action"  method="post" name="form1" id="form1">
	<table align="center" width="90%" class="TableBlock" id="applTable">
		<tr>
			<td nowrap class="TableData"  width="15%;" >模版类型：</td>
			<td class="TableData" width="35%;" >
				<select name="typesOfCode" id="typesOfCode" style="height: 27px" onchange="change(this)">
					<option value="01" selected="selected">共性模版文件</option>
					<option value="02">个性模版文件</option>
				</select>			
				<input type="hidden" name="typesOf" id="typesOf" value="共性模版文件" />
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData"  width="15%;" >文书类型：</td>
			<td class="TableData" width="35%;" >
				<select name="documentNo" id="documentNo" style="height: 27px" onchange="changeBook(this)">
				</select>			
				<input type="hidden" name="documentName" id="documentName"  value="申请书"/>
			</td>
		</tr>
		<tr>
			<td nowrap class="TableData"  width="15%;" >选择机构：</td>
			<td class="TableData" width="35%;" >
				<input id="fyOrganId" name="fyOrganId"  type="hidden" />
				<input readonly="readonly" disabled type="text" id="deptName" placeholder="请点击选择部门" name="deptName" style="font-family:MicroSoft YaHei;" onclick="selectSingleDept(['fyOrganId','deptName'],'1')"/>
			 	<%-- <span class='addSpan'>
			         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleDept(['fyOrganId','deptName'],'1')" value="选择"/>
				        &nbsp;&nbsp;
				        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('fyOrganId','deptName')" value="清空"/>
			 	</span> --%>
		 	</td>
		</tr>
		<tr>
			<td nowrap class="TableData"  width="15%;" >文件上传：</td>
			<td class="TableData" width="35%;" >
				<input type="file" name="file" id="file" accept=".ftl" />
				<input type="hidden" name="mblj" id="mblj"  />
			</td>
		</tr>
	</table>
	
		
	</form>
<script type="text/javascript">
var id = "<%=request.getParameter("id")%>";

//文书类型
$(function(){
	getSysCodeByParentCodeNo('FY_LEGALDOC_TYPE' , 'documentNo');
});

function doSave(){
	var temp = false;
	$("#form1").ajaxSubmit({
        type: 'post', // 提交方式 
        url:"/templateController/saveTemplate.action",//上传的url
        dataType:"json",//回调值的数据类型
        async:false,
        success:function(responseText){
			if(!responseText.rtState){
				alert(responseText.rtMsg);
				temp = false;
			}else{
				temp = true;
			}
		},
		error:function(responseText){
			alert("请联系管理员上传！");
			temp = false;
		}
    });
	return temp;
}

function change(nowEle) {
	var selectTag = $(nowEle);
	var hiddenTag = $(nowEle).next();
	var selectText = selectTag.find("option:selected").text();
	hiddenTag.val(selectText);
	selectTag.attr({ "title" : selectTag.find("option:selected").text(),});
	if(selectText=="个性模版文件"){
		$('#deptName').attr("disabled",false);
	}else{
		$('#deptName').attr("disabled",true);
	}
}
function changeBook(nowEle) {
	var selectTag = $(nowEle);
	var hiddenTag = $(nowEle).next();
	var selectText = selectTag.find("option:selected").text();
	hiddenTag.val(selectText);
	selectTag.attr({ "title" : selectTag.find("option:selected").text(),});
}


</script>	

</body>

</html>