<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
    String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建拜访</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
html{
   background-color: #f2f2f2;
}
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
var customerName ="<%=customerName%>";
var customerId = "<%=customerId%>";
function doInit(){
	getCrmCodeByParentCodeNo("VISIT_TOPIC","visitTopic");//拜访主题
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);
	$("#managePersonId").val(loginPersonId);
	$("#managePersonName").val(userName); 
	
	if( sid > 0){
		var url = contextPath+"/TeeCrmVisitController/getInfoBySid.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}

}

function selectCustomer(){
	var url = contextPath+"/system/subsys/crm/core/customer/query.jsp";
	  dialogChangesize(url, 860, 500);
}


/**
 * 保存
 */
function save(callback){
	var url="";
	//编辑拜访
	if(sid>0){
		if(check()){
		    url=contextPath+"/TeeCrmVisitController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	callback(json.rtState);
		          }else{
		    	    	$.MsgBox.Alert(json.rtMsg);
		    	    }	
		}
	}else{//添加拜访
		if(check()){
			url=contextPath+"/TeeCrmVisitController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	callback(json.rtState);
	    	 }else{
	    	    	$.MsgBox.Alert(json.rtMsg);
	    	    }
		}
	    
	};
	
}

/**
 * 验证
 */
function check(){
	 var checks= $("#form1").valid(); 
	  if(!checks){
	    	return false;
	    }
	if($("#customerId").val()=="" || $("#customerId").val()=="null" || $("#customerId").val()==null){
		$.MsgBox.Alert_auto("请选择所属客户！");
		return false;
	}
	if($("#visitTimeDesc").val()=="" || $("#visitTimeDesc").val()=="null" || $("#visitTimeDesc").val()==null){
		$.MsgBox.Alert_auto("请选择计划日期！");
		return false;
	}
	if($("#managePersonId").val()=="" || $("#managePersonId").val()=="null" || $("#managePersonId").val()==null){
		$.MsgBox.Alert_auto("请选择负责人！");
		return false;
	}
	return true;
}


</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;background-color: #f2f2f2;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/project/img/icon_addfield.jpg">
		<span class="title">新增/编辑拜访</span>
	</div>
</div>

<form method="post" name="form1" id="form1" >
	<table class="TableBlock" width="100%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				所属客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" id='customerName' name='customerName' class="BigInput" type='text' readonly="readonly"/>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
			</td>
		</tr>
        <tr>
            <td class="TableData" width="150px;" style="text-indent: 15px;">拜访主题：</td>
			<td class="TableData">
				<select style="height: 25px;width: 200px;" id="visitTopic" name="visitTopic" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
			</td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">计划日期 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td>
				<input type="text" style="width: 200px;" requried id='visitTimeDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='visitTimeDesc' class="Wdate BigInput" />
			</td>
        </tr>
        <tr>
	        <td class="TableData" width="150" style="text-indent:15px">负责人 <span style="color:red;font-weight:bold;">*</span>：</td>
			<td class="TableData">
				<input type="hidden" name="managePersonId" id="managePersonId"> 
				<input name="managePersonName" id="managePersonName" style="height:23px;width:350px;border: 1px solid #dadada;"   class="BigInput" wrap="yes" readonly />
				   <span class='addSpan'>
			               <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_select.png" onClick="selectSingleUser(['managePersonId', 'managePersonName'],'14')" value="选择"/>
				           &nbsp;&nbsp;
				           <img src="<%=contextPath %>/common/zt_webframe/imgs/htgl/khgl/icon_cancel.png" onclick="clearData('managePersonId', 'managePersonName')" value="清空"/>
			       </span>
			</td>
	   </tr>
	 </table>
</form>
</body>
</html>