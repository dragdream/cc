<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   String uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>Insert title here</title>
<script type="text/javascript">
var uuid="<%=uuid%>";
function doInit(){
	 if(uuid!=""&&uuid!=null){
		$("#tishi").show();
		var url=contextPath+"/mobileSeal/getByUuid.action";
		var json=tools.requestJsonRs(url,{uuid:uuid});
		if(json.rtState){
			bindJsonObj2Cntrl(json.rtData);
		}
	}else{
		$("#tishi").hide();
	}	
}
function addOrUpdate(){
	if(checkForm()){
		$("#form1").doUpload({
			url:contextPath+"/mobileSeal/addOrUpdate.action",
			success:function(json){
				parent.$("#datagrid").datagrid("reload");
				parent.$("#win_ico").click();
			},
			post_params:tools.formToJson($("#form1"))
		}); 
	}

}


function checkForm(){
	if($("#sealName").val()==""){
		$.MsgBox.Alert_auto("请输入印章名称");
		return false;
	}
	
	if($("#userId").val()==""){
		$.MsgBox.Alert_auto("请选择签章使用人");
		return false;
	}
	
	if($("#file").val()!=""){
		var ext=$("#file").val().split(".")[1];
		if(ext!="jpg"&&ext!="gif"&&ext!="png"){
			$.MsgBox.Alert_auto("请上传jpg,gif,png格式文件");
			return false;
		}
	}
	
	return true;
}


</script>
</head>
<body onload="doInit()" style="background-color: #f2f2f2">
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
   <table class="TableBlock" width="100%">
		<tr>
			<td class="TableData" >签章名称：</td>
			<td class="TableData">
				<input type="text" name="sealName" id="sealName" class="BigInput" style="height: 23px;width: 200px">
			</td>
		</tr>
		<tr>
			<td class="TableData" >签章使用人：</td>
			<td class="TableData">
				<input type=hidden name="userId" id="userId" value="">
				<input type="text" name="userName" id="userName" class="BigInput readonly" readonly style="height: 23px;width: 200px">
				<span>
					<a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['userId', 'userName']);">添加</a>
					<a href="javascript:void(0);" class="orgClear" onClick="$('#userId').val('');$('#userName').val('');">清空</a>
				</span>
			</td>
		</tr>
		<tr>
			<td class="TableData">设备号：</td>
			<td class="TableData"  >
				<input type='text' class="BigInput readonly" readonly id='deviceNo' name='deviceNo' style="height: 23px;width: 200px" />
			</td>
		</tr>
		<tr>
			<td class="TableData">签章密码：</td>
			<td class="TableData"  >
				<input type='password' class="BigInput" id='pwd' name='pwd' style="height: 23px;width: 200px" />
			</td>
		</tr>
		<tr>
			<td class="TableData">签章图片：<br/>支持JPG,GIF,PNG格式</td>
			<td class="TableData">
				<input type='file' class="" id='file' name='file' style="width:180px;" /><br/>
	                                                           <div id="tishi" style="display:none;"><font color="red">如果印章没有变化，无需重新上传。</font></div>
			</td>
		</tr>
		<input type="hidden" value="<%=uuid %>" name="uuid"/>
	</table>
</form>
</body>
</html>