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
				<td nowrap class="TableData" style="text-indent: 10px">组织机构：</td>
				<td nowrap class="TableData">
				    <input type="hidden" id="personId" name="personId" />
				    <input type="hidden" id="orgId" name="orgId" />
				    <input type="text" name="orgName" id="orgName" maxlength="3"
				        class="easyui-validatebox BigInput" size="10"
					    required="true" readonly="readonly" />
				</td>
			</tr>
			<tr>
                <td nowrap class="TableData" style="text-indent: 10px">姓名：</td>
                <td nowrap class="TableData">
                	<input type="text" name="personName" id="personName" 
                	    class="easyui-validatebox BigInput" 
                	    required="true" validType="personNameCheck"/>
                </td>
            </tr>
            
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">性别：</td>
				<td nowrap class="TableData">
					<select class="easyui-validatebox BigSelect" name="sex" id="sex" required="true">
						<option value="">--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">身份证：</td>
				<td nowrap class="TableData">
					<input type="text" name="idCard" id="idCard" size="25" maxlength="18" 
					    class="easyui-validatebox BigInput" 
					    required="true" validType="idCardCheck" />&nbsp;
				</td>
			</tr>
			
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">人员编制：</td>
				<td nowrap class="TableData">
                    <input id="staffingName" name="staffingName"  type="hidden"/>				
					<select class="easyui-validatebox BigSelect" name="staffing" id="staffing" required="true">
						<option value="">--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">职级：</td>
				<td nowrap class="TableData">
                    <input id="levelName" name="levelName"  type="hidden"/>				
					<select class="easyui-validatebox BigSelect" name="levelCode" id="levelCode" required="true">
						<option value="">--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">学历：</td>
				<td nowrap class="TableData">
                    <input id="easyui-validatebox educationName" name="educationName"  type="hidden"/>				
					<select class="easyui-validatebox BigSelect" name="educationCode" id="educationCode" required="true">
						<option value="">--请选择--</option>
					</select>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">是否获取法律证书：</td>
				<td nowrap class="TableData">
					<label class="btn btn-default" >
				    	<input type="radio" name="isLaw" id="option1" value="1" checked="checked"/>是
				  	</label>
				 	<label class="btn btn-default" >
				    	<input type="radio" name="isLaw" id="option2" value="0" />否
				  	</label>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">是否党员：</td>
				<td nowrap class="TableData">
					<label class="btn btn-default" >
				    	<input type="radio" name="isParty" id="option1" value="1" checked="checked"/>是
				  	</label>
				 	<label class="btn btn-default" >
				    	<input type="radio" name="isParty" id="option2" value="0" />否
				  	</label>
				</td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">电话：</td>
				<td class="TableData">
				<input type="text" name="phone" id="phone" 
				    class="easyui-validatebox BigInput"
				    required="true" validType="phoneCheck" />&nbsp;
               </td>
			</tr>
			<tr>
				<td nowrap class="TableData" style="text-indent: 10px">邮箱：</td>
				<td class="TableData">
				<input type="text" name="email" id="email" class="BigInput" />&nbsp;
               </td>
			</tr>
			
			<tr>
				<td colspan="2">
				 <div  align="center">
					<input id="saveBtn"  type="button" value="保存" 
					    class="btn-win-white" onclick="doSave();" />&nbsp;&nbsp;  
					<input type="text" id="uuid" name="uuid" value="0" style="display: none;" />
					<!--  
					<span id="deleteDeptSpan" style="display:none;"> 
					    <input type="button" value="删除当前部门/下级部门" 
					        class="btn-win-white" onclick="deleteDeptAndSubDept();"/>&nbsp;&nbsp;  
					</span>-->
				  </div>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript" src="/xzfy/js/organperson/addupdate.js"></script>
	<script type="text/javascript" src="/xzfy/js/organperson/check.js"></script>
</body>

</html>