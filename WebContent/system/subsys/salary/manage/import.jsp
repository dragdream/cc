<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	int accountId = TeeStringUtil.getInteger(request.getParameter("accountId"), 0);//账套
	int salYear =  TeeStringUtil.getInteger(request.getParameter("salYear"), year);//年份
	int salMonth = TeeStringUtil.getInteger(request.getParameter("salMonth"),month);//月份
	int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);//工资流程

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>工资项管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/salary/js/account.js"></script>


<style type="text/css">
</style>

<script type="text/javascript">
var accountId =  <%=accountId%>;
function exportSalModule(){
	window.location.href = "<%=contextPath%>/teeSalImportExportController/exportSalModule.action?accountId=" + $("#accountId").val()+"&deptIds="+$("#deptIds").val();

}
function checkForm(){
	return checkSuppotExcleFile('importFile');
}
function doImport(){
	if(checkForm()){
		$("#form1").submit();
// 		 var para =  tools.formToJson($("#form1")) ;
// 		 $("#form1").ajaxSubmit({
//			  url :"<%=contextPath %>/teeSalImportExportController/importSal.action",
// 			  iframe: true,
// 	          data: para,
// 	          success: function(res) {
// 	        	  if(res.rtState){
// 	        		  var data = res.rtData;
// 	        		  var dataJson = data;//eval('(' + data + ')');
// 	        		  importInfo( dataJson ,res.rtMsg );
// 	        	  }else{
// 	        		  alert(res.rtMsg);
// 	        	  }
// 	            },
// 	          error: function(arg1, arg2, ex) {
// 	                // ... my error function (never getting here in IE)
// 	                alert("保存错误");
// 	          },
// 	          dataType: 'json'});
	}
}
/**
 * 导入返回信息
 */
function importInfo( data , importDeptCount){
	if(data.length >0){
		var tableStr = "<table class='TableList' width='95%' align='center' >"
	      + " <tbody id='tbody'>"
	      + "<tr  class='TableHeader'>"
	 /*      + "<td >部门名称 </td>" */
	      + "<td >姓名</td>"
	      + "<td > 导入信息</td>"
	      +"</tr>";
		for(var i = 0;i<data.length ; i++){
			var obj = data[i];
			var color = obj.color;
			tableStr = tableStr + "<tr  align='center'>"
			//+"<td width='80px;'><font color='" + color + "'>" + obj.deptName + "</font></td>"
			+"<td width='120px;'><font color='" + color + "'>" + obj.userName+ "</font></td>"
			+"<td width='' ><font color='" + color + "'>" + obj.info + "</font></td>"
			+ "</tr>";		
		}
	    tableStr = tableStr + " </tbody></table>";
	    $("#importFileDiv").hide();

	    
		$("#importInfo").append(tableStr + "<br>");
		messageMsg("总共导入 " +  importDeptCount + " 条数据", "importInfo2" ,'' ,280);
		
		$("#importInfo3").append( "<input type='button' value='返回' class='btn primary-btn' onclick='window.location.reload();'><br>");
	}
	
}

function doInit(){
	//getAllAccount('accountId');
	
	var url = contextPath + "/teeSalAccountController/getById.action";
	var para = {sid:accountId} ;
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		$("#accountName").append(jsonObj.rtData.accountName);
	} else {
		alert(jsonObj.rtMsg);
	}
	$("#accountId").val(accountId); 
}
</script>

</head>
<body onload="doInit()">

<div class="Big3" style="padding:5px 0px 10px 5px;">
	工资数据导入
</div>

<div id="importFileDiv">

<form enctype="multipart/form-data" action="<%=contextPath %>/teeSalImportExportController/importSal.action"  method="post" name="form1" id="form1" target="iframe0">
  <table class="TableBlock" width="70%" align="center">
    <tr>
      <td nowrap class="TableData" width="180px;">下载导入工资模板：</td>
      <td class="TableData">
       	   指定部门工资模版（为空则是空模版）：
       	  <textarea name="deptNames" id="deptNames"  readonly class="BigTextarea readonly" style='margin-bottom:10px;width:200px;height:50px;'></textarea>
       	  <input type="hidden" name="deptIds" id="deptIds" class="BigInput"/>
       	  <a href="javascript:void(0)" onclick="selectDept(['deptIds','deptNames'])">选择</a>
       	  <a href="javascript:void(0)" onclick="clearData('deptIds','deptNames')">清空</a>
       	  <br/>
       	  <input type="button" class="btn btn-warning" value="下载导入工资模板" onclick="exportSalModule();"/>
      </td>
    </tr>
    
     <tr>
      <td nowrap class="TableData" width="180px;">工资账套：</td>
      <td class="TableData" id="accountName">
       	
       	  
      </td>
    </tr>
     <tr>
      <td nowrap class="TableData" width="">工资年份：</td>
      <td class="TableData">
      	  <%= salYear%>年<%=salMonth %>月
       	 
      </td>
    </tr>
    <tr id="avatarUpload" >
      <td nowrap class="TableData"> 导入工资数据文件：</td>
      <td class="TableData" id="">
      	
          <input type="file" name="importFile" id="importFile" size="30" class="" title="选择Excle文件" value="">
          </input>
     </td>
     </tr>
     <tr id="avatarUpload" >
	      <td nowrap class="TableData" > 说明：</td>
	      <td class="TableData" id="">
	        1、请导入EXCLE文件。<br> 
			2、使用工资报表模板导入数据，先填内容再导入。 <br>
			3、工资报表模板中，姓名不能为空，为空则不能导入。
	      </td>
      </tr>
   
     <tr>
        <td nowrap class="TableData" colspan="4" align="center">
        	<input type="hidden" name="flowId" value="<%=flowId%>"></input>
            <input type="hidden" name="accountId" id="accountId" />
            <input type="hidden" name="salYear" value="<%=salYear %>"></input> 
       	    <input type="hidden" name="salMonth" value="<%=salMonth %>"></input> 
            <input id="button" type="button" value="确定导入数据" onclick="doImport();" class="btn btn-success"/>&nbsp;
        	<%
        	if(accountId > 0){	
        	%>
          &nbsp;&nbsp; <input id="button" type="button" value="返回" onclick="window.location.href='<%=contextPath %>/system/subsys/salary/salflow/index.jsp'" class="btn btn-success"/>&nbsp;
        
                	<% 	}%>
         </td>
     </tr>
    </table>
<br>
</div>
<div id="importInfo">

</div>
<div id="importInfo2">

</div>
<div id="importInfo3" style="" align="center">

</div>
<iframe id="iframe0" name="iframe0" style="display:none">
</iframe>
</form>
</body>
</html>
