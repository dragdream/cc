<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.tianee.oa.core.partthree.util.TeePartThreeUtil" %>
<%
   TeePerson  loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
   boolean hasPriv=TeePartThreeUtil.checkHasPriv(loginUser, "SYS_USER_MANAGE");
   if(!hasPriv){//没有权限
	   response.sendRedirect("/system/core/system/partthree/error.jsp");
   }
   
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>用户导入</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/jquery.form.min.js"></script>
<script type="text/javascript">

function doInit(){
	messageMsg("导入的用户名均不能重复,用户名、部门、角色不能为空！ ", "info" ,'info');
}

function check() {
	return checkSuppotCsvFile('importUserFile');
}

/**
 * 保存
 */
function submitForm(){
	if(check()){
		 var para =  tools.formToJson($("#form1")) ;
		 $("#form1").ajaxSubmit({
			  url :"<%=contextPath %>/orgImportExport/importUser.action",
	          iframe: true,
	          data: para,
	          success: function(res) {
	        	  if(res.rtState){
	        		  var data = res.rtData;
	        		  var dataJson = data;//eval('(' + data + ')');
	        		  importInfo( dataJson ,res.rtMsg );
	        	  }else{
	        		  $.MsgBox.Alert_auto(res.rtMsg);
	        	  }
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	                $.MsgBox.Alert_auto("保存错误");
	          },
	          dataType: 'json'});
	}	
}

/**
 * 导入返回信息
 */
function importInfo( data , importDeptCount){
	if(data.length >0){
		var tableStr = "<table class='TableBlock_page' width='90%' align='center' >"
	      + " <tbody id='tbody'>"
	      + "<tr  class='TableData' style='line-height:35px;border-bottom:1px solid #f2f2f2;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8;'>"
	      + "<td width='40'>部门名称 </td>"
	      + "<td >用户名</td>"
	      + "<td >姓名</td>"
	      + "<td >角色</td>"
	      + "<td >排序号</td>"
	      + "<td >管理范围</td>"
	      + "<td > 导入信息</td>"
	      +"</tr>";
		for(var i = 0;i<data.length ; i++){
			var obj = data[i];
			var color = obj.color;
			tableStr = tableStr + "<tr class='TableData' style='line-height:25px;'>"
			+"<td width='80px;'><font color='" + color + "'>" + obj.deptName + "</font></td>"
			+"<td width='100px;'><font color='" + color + "'>" + obj.userId+ "</font></td>"
			+"<td width='120px;'><font color='" + color + "'>" + obj.userName+ "</font></td>"
			+"<td width='100px;'><font color='" + color + "'>" + obj.roleName+ "</font></td>"
			+"<td width='50px;'><font color='" + color + "'>" + obj.userNo+ "</font></td>"
			+"<td width='80px;'><font color='" + color + "'>" + obj.postPriv + "</font></td>"
			+"<td width='200px;' ><font color='" + color + "'>" + obj.info + "</font></td>"
			+ "</tr>";		
		}
	    tableStr = tableStr + " </tbody></table>";
	    $("#importFile").hide();
	    $("#importDiv").show();
	    
		$("#importInfo").append(tableStr + "<br>");
		messageMsg("总共导入 " +  importDeptCount + " 条数据", "importSuccessInfo" ,'info');
		
	}
}
</script>

</head>
<body onload="doInit();" style="padding: 10px;margin-left: 5px;margin-right: 5px;">
<div id="importFile">
<form enctype="multipart/form-data" action=""  method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="90%" align="center">
  <tr>
    <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; <span  class="imgMiddleSpan">用户导入</span></td>
   </tr>
    <tr id="avatarUpload" >
      <td nowrap class="TableData" width='200px' style="font-family: MicroSoft YaHei;"> 请指定用于导入的CSV文件： </td>
      <td class="TableData" id="">
          <input type="file" name="importUserFile" id="importUserFile" size="40" title="选择附件文件" value="">
         &nbsp;&nbsp; <input type="button" value="导入" class="btn-win-white" onclick="submitForm()">&nbsp;&nbsp;
      <br>
      </td>
    </tr>
   
  </table>
</form>

	<div id="info" style="padding-top: 10px;"></div>
  </div>
  <br>

<!--   信息导入返回信息 -->
  <div id="importDiv" style="display:none;"  align="center">
  	   <div id="importInfo" style="padding-top: 10px;padding-bottom: 10px;"></div>   
  	   <div id="importSuccessInfo" style="padding-top: 10px;padding-bottom: 10px;"></div>

	   <input type='button' class="btn-win-white" value="返回" onclick='window.location.reload();'/>
</div>
</body>
</html>