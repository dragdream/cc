<%@page import="com.tianee.oa.subsys.crm.setting.TeeCrmCodeManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String cusName = TeeStringUtil.getString(request.getParameter("customerName"), null);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>新建销售线索</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crmCode.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/address_cascade.js"></script>
<style>
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
var cusName = "<%=cusName%>";
function doInit(){
	getCrmCodeByParentCodeNo("CLUE_SOURCE","clueSource"); //线索来源
	
	if(sid>0){//编辑线索
		getInfoBySid(sid);
	}else{
		
	}

}

//编辑的时候获取初始化数据
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmClueController/getInfoBySid.action";
	var param={sid:sid};
	var json=tools.requestJsonRs(url,param);
	if(json.rtState){
		var data=json.rtData;
		bindJsonObj2Cntrl(data);
		//alert(tools.jsonObj2String(data));
		
	}else{
		$.MsgBox.Alert_auto("数据获取失败！");
	}

}


/**
 * 保存
 */
function commit(){
	var url="";
	//编辑线索
	if(sid>0){
		if(check()){
		    url=contextPath+"/TeeCrmClueController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			     parent.$.MsgBox.Alert_auto(json.rtMsg);
			      window.location.href=contextPath+"/system/subsys/crm/core/clue/clueInfoDetial.jsp?sid="+sid+"&customerName="+cusName; 
		    	  opener.datagrid.datagrid("unselectAll");
				  opener.datagrid.datagrid('reload');
			          };	
		}
	}else{//添加线索
		if(check()){
			url=contextPath+"/TeeCrmClueController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	  parent.$.MsgBox.Alert_auto(json.rtMsg,function(){
				         window.location.href=contextPath+"/system/subsys/crm/core/clue/index.jsp";
			    	 });
		         };	
		}
	    
	};
	
}

/**
 * 验证
 */
function check(){
	var clueName=$("#name").val();
	var companyName = $("#companyName").val();
	var culeDetail = $("#culeDetail").val();
	var telephone = $("#telephone").val();
	if(clueName==""||clueName==null){
		$.MsgBox.Alert_auto("请填写姓名！");
		return false;	
	}
	if(companyName==""||companyName==null){
		$.MsgBox.Alert_auto("请填写公司！");
		return false;	
	}
	if(culeDetail==""||culeDetail==null){
		$.MsgBox.Alert_auto("请填写线索详情！");
		return false;	
	}
	if(telephone==""||telephone==null){
		$.MsgBox.Alert_auto("请填写联系电话！");
		return false;	
	}
	
	return true;
}

//返回
function goBack(){
	var url="";
	if(sid>0){
		history.go(-1);
	}else{
	    url= contextPath+"/system/subsys/crm/core/clue/index.jsp";
	}
	location.href=url;
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_xjxs.png">
		<span class="title">新增/编辑线索</span>
	</div>
   <div class="fr right">
      <input type="button" value="保存" class="btn-win-white" onclick="commit();"/>
      <input type="button" value="返回" class="btn-win-white" onclick="goBack();"/>
   </div>
</div>

<form method="post" name="form1" id="form1" >
	<table class="TableBlock_page" width="60%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				姓名 <span style="color:red;font-weight:bold;">&nbsp;&nbsp;*</span>：
			</td>
		    <td class="TableData">
                <input requried type='text' name="name" id="name" style="height: 23px;width: 350px"/>
            </td>
		</tr>
		<tr>
           	<td class="TableData" width="150" style="text-indent:15px">
				公司 <span style="color:red;font-weight:bold;">&nbsp;&nbsp;*</span>：
			</td>
		    <td class="TableData">
                <input requried type='text' name=companyName id="companyName" style="height: 23px;width: 350px"/>
            </td>
        </tr>
        <tr>
            <td class="TableData" width="150" style="text-indent:15px">
				销售线索详情 <span style="color:red;font-weight:bold;">&nbsp;&nbsp;*</span>：
			</td>
			<td class="tableData">
			   <textarea style="width: 350px;" requried rows="5" cols="8" id="culeDetail" name="culeDetail"></textarea>
			
			</td>
        </tr>
        <tr>
           <td class="TableData" width="150" style="text-indent:15px">
                                    来源 <span style="color:red;font-weight:bold;">&nbsp;&nbsp;*</span></td>
		   <td class="TableData">
				<select style="height: 25px;width: 200px;" id="clueSource"  name="clueSource" class="BigSelect" title="<%=TeeCrmCodeManager.codeSettingPath %>"></select>
		   </td>
        </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">部门：</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="department" id="department" style="height: 23px;width: 350px"/>
			</td>
	   </tr>
	      <tr>
	        <td class="TableData" width="150" style="text-indent:15px">职务：</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="duties" id="duties" style="height: 23px;width: 350px" />
			</td>
	   </tr>
	   <tr>
            <td class="TableData" width="150" style="text-indent:15px">
                                      电话  <span style="color:red;font-weight:bold;">&nbsp;&nbsp;*</span>：
            </td>
			<td class="TableData">
				<input requried type="text" class="BigInput" name="telephone" id="telephone" style="height: 23px;width: 350px" />
			</td>	   
	   </tr>
	      <tr>
            <td class="TableData" width="150" style="text-indent:15px">手机：</td>
			<td class="TableData">
				<input type="text" class="BigInput" name="mobilePhone" id="mobilePhone" style="height: 23px;width: 350px" />
			</td>	   
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">网址：</td>
			<td class="TableData">
				<input type="text" name="url" id="url" class="BigInput" style="height: 23px;width: 350px"/> 
			</td>
	   </tr>
	   <tr>
	        <td class="TableData" width="150" style="text-indent:15px">邮箱：</td>
			<td class="TableData">
				<input type="text" name="email" id="email" class="BigInput" style="height: 23px;width: 350px"/> 
			</td>
	   </tr>
	      <tr>
	        <td class="TableData" width="150" style="text-indent:15px">地址：</td>
			<td class="TableData">
				<input type="text" name="address" id="address" class="BigInput" style="height: 23px;width: 350px"/> 
			</td>
	   </tr>
	 </table>

</form>
</body>
</html>