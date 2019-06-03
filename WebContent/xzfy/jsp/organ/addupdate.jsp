<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String puuid = request.getParameter("puuid") == null ? "" : request.getParameter("puuid");
	String uuid = request.getParameter("uuid") == null ? "" : request.getParameter("uuid");
	String type = request.getParameter("type") == null ? "" : request.getParameter("type");
	String deptParent = request.getParameter("deptParent") == null? "" : request.getParameter("deptParent");
%>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>部门管理</title>
	<link href="<%=cssPath%>/style.css" rel="stylesheet" type="text/css" />
	<link href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
</head>

<body onload="doInit()" style="font-family: MicroSoft YaHei;font-size: 12px;">
	<form method="post" name="form1" id="form1" class="tableForm" style="padding-bottom:5px;">
		<table class="TableBlock_page" width="100%" align="center">
            <tr>
               <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png" align="absMiddle">&nbsp;&nbsp; 
				   <span id="title"></span> 
               </td>
            </tr>
           
			<tr>
			    <td nowrap class="TableData" style="text-indent: 10px">机关名称：</td>
				<td nowrap class="TableData">
					<input id="deptId" name="deptId"  type="hidden"/>
					<input id="orgId" name="orgId"  type="hidden"/>
					<input type="text" id="orgName" name="orgName" style="font-family:MicroSoft YaHei;" 
					    class="easyui-validatebox BigInput" 
					    required="true" readonly="readonly"/>
			 		
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">机关层级：</td>
				<td nowrap class="TableData">
					<select class="easyui-validatebox BigSelect" name="orgLevelCode" id="orgLevelCode" 
					    required="true" >
						<option value="">--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
                <td nowrap class="TableData" style="text-indent: 10px">机关编码：</td>
                <td nowrap class="TableData">
                	<input type="text" name="orgCode" id="orgCode" 
                	    class="easyui-validatebox BigInput" 
					    required="true" validType="orgCodeCheck" />
                </td>
            </tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">法人：</td>
				<td nowrap class="TableData">
					<input type="text" name="legalRepresentative" id="legalRepresentative" 
					    class="easyui-validatebox BigInput" size="25" maxlength="25" 
						required="true" validType="legalRepresentativeCheck"/>&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">编制人数：</td>
				<td nowrap class="TableData">
				    <input type="text" name="compilersNum" id="compilersNum" 
				    class="easyui-validatebox BigInput" size="25" maxlength="25" 
					required="true" validType="compilersNumCheck" />&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">联系人：</td>
				<td nowrap class="TableData">
				    <input type="text" id="contacts" name="contacts" 
				    class="easyui-validatebox BigInput" size="40" maxlength="40" 
					required="true" validType="contactsCheck" />&nbsp;
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">联系人电话：</td>
				<td class="TableData">
				<input type="text" name="contactsPhone" id="contactsPhone" 
				    class="easyui-validatebox BigInput" maxlength="11"
				    required="true" validType="contactsPhoneCheck" />&nbsp;
               </td>
			</tr>
            <tr>
				<td nowrap class="TableData" style="text-indent: 10px">传真：</td>
				<td class="TableData">
				<input type="text" name="fax" id="fax" 
				    class="easyui-validatebox BigInput" />&nbsp;
               </td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">邮政编码：</td>
				<td class="TableData">
				<input type="text" name="areaCode" id="areaCode" 
				    class="easyui-validatebox BigInput" 
				    />&nbsp;
               </td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">备注：</td>
				<td class="TableData">
				<textarea cols="45" name="remark" id="remark" rows="4" style="overflow-y: auto;" 
					class="easyui-validatebox SmallStatic BigTextarea" wrap="yes" 
					>
				</textarea>
				
               </td>
			</tr>
			
			<tr>
				<td colspan="2">
				 <div align="center">
					<input id="saveBtn"  type="button" value="保存" 
					    class="btn-win-white" onclick="doSave();" />&nbsp;&nbsp;  
					<input type="text" id="uuid" name="uuid" value="0" style="display: none;" />
					
				  </div>
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript" src="/xzfy/js/organ/addupdate.js"></script>
	<script type="text/javascript" src="/xzfy/js/organ/check.js"></script>
</body>

</html>