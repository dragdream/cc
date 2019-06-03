<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
    int id = TeeStringUtil.getInteger(request.getParameter("id") , 0 ) ;
	/* String id = request.getParameter("id") == null ? "0" : request.getParameter("id") ; */
	String to = request.getParameter("to") == null ? "" : request.getParameter("to") ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>编辑群组</title>
<style>
	table{
		width:100%;
		border-collapse:collapse;
	}
	table tr {
		line-height:35px;
		width:100%;
		height:auto;
		border-bottom:1px solid #f2f2f2;
		padding:5px 0;
	}
	table tr input{
		line-height:16px;
	}
	table tr td:first-child{
		width:200px;
		text-align:left;
		text-indent:8px;
	}
	.btns{
		text-align:center!important;
	}
	textarea{
		margin:10px 0!important;
		margin-bottom:0!important;
	}
</style>

<script type="text/javascript">

var id = '<%=id%>';
var to = '<%=to%>';
function doInit(){
	if(id > 0){
		var url = "<%=contextPath%>/messageGroupManage/selectById.action";
		var jsonRs = tools.requestJsonRs(url,{id:id});
		if(jsonRs.rtState){
			var data = jsonRs.rtData;
			if(data){
				bindJsonObj2Cntrl(data);
			}
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
	
	if(to!=''){
		var json = tools.requestJsonRs(contextPath+"/personManager/getPersonListByUuids.action",{uuid:to});
		var list = json.rtData;
		var ids = [];
		var names = [];
		for(var i=0;i<list.length;i++){
			ids.push(list[i].uuid);
			names.push(list[i].userName);
		}
		
		$("#toId").attr("value",ids.join(","));
		$("#toName").attr("value",names.join(","));

	}
	
}
/**
 * 保存
 */
function doSave(){
	if (checkForm()){
		var url = "<%=contextPath%>/messageGroupManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("保存成功！",function(){
			window.location.href ="<%=contextPath%>/system/core/base/message/group/index.jsp";
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}


</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
<div style="padding:5px 0;border-bottom:2px solid #b0deff" id="toolbar" class = "toolbar clearfix">
   <div  class="fl setHeight">
    <h4 style="font-size: 16px;font-family: MicroSoft YaHei;">
				<%if(id > 0){ %>
		        	       编辑群组
		        <%}else{ %>
		        	      新增群组
		        <%} %>
				</h4>
   
   </div> 
</div>
	<form  method="post" name="form1" id="form1" >
<table style="font-size:12px;" class='TableBlock_page' align="center">

   <tr>
    <td nowrap class="" >排序号：</td>
    <td nowrap class="" align="left" >
        <input type='text' name="orderNo" class=" " size='2' required positive_integer='true' style="width:100px;"/>
    </td>
   </tr>
   <tr>
    <td nowrap class="" >群组名称：</td>
    <td nowrap class="" align="left">
        <input type='text' name="groupName" class=" " size='45'  newMaxLength="100" required/>
    </td>
   </tr>
   
    <tr>
   		<td nowrap class="" >群组主题：</td>
   	 	<td nowrap class="" align="left">
       		 <input type='text' name="groupSubject" class=" " size='45'  newMaxLength="100" required />
    	</td>
    </tr>
   
    <tr>
   		 <td nowrap class="">群组简介：<span style=""></span></td>
   		 <td nowrap class="" align="left">
       		 <textarea name="groupIntroduction" id = "groupIntroduction" class="" rows="5" cols="50"></textarea>
    </td>
   	</tr>
   	<tr>
   		 <td nowrap class="" >群组公告：<span style=""></span></td>
   		 <td nowrap class="" align="left">
       		 <textarea name="groupNotify" id = "groupNotify" class="" rows="5" cols="50"></textarea>
    </td>
   	</tr>
	<tr>
		<td nowrap class="">群组人员：</td>
		<td  class=""  align="left">
			<input type="hidden" name="toId" id="toId" value="">
			 <textarea cols="50" name="toName" id="toName" rows="5" style="overflow-y: auto;"  class=" " wrap="yes" readonly></textarea>
			 <span name="addSpan" class='addSpan'>
			    <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/smsManager/add.png" onClick="selectUser(['toId', 'toName'])"></img>
			    <img src="<%=contextPath %>/common/zt_webframe/imgs/grbg/smsManager/clear.png" onClick="clearData('toId', 'toName')"></img>
		     </span>
		</td>
   </tr>
  
   <tr style="display:none">
  	  <td nowrap class="">提醒：</td>
   	  <td nowrap class="" align="left">
  		  <input type="checkBox" name="smsRemind" id="smsRemind"  value='1' >
  		  <label for="smsRemind">使用微讯提醒</label>&nbsp;&nbsp;
			<input type="checkBox" name="sms2Remind" id="sms2Remind" value='1' >
  		  <label for="sms2Remind">使用手机短信提醒 </label>&nbsp;&nbsp;
      </td>
   </tr>
   <tr>
	    <td nowrap  class="btns" colspan="2" align="center">
	        <input type="button" value="保存" class="btn-win-white" title="保存" onclick="doSave()" >&nbsp;&nbsp;
	        <input type="button" value="返回" class="btn-win-white" title="返回" onClick="window.location='index.jsp';">
	        <input type='hidden' value='<%=id %>' name='sid'/>
	    </td>
   </tr>
   
</table>
  </form>

</body>
<script>
	$("#form1").validate();
</script>
</html>
 