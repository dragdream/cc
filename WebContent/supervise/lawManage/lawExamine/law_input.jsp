<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp"%>
<%@ include file="/header/upload.jsp"%>

<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link  rel="stylesheet" type="text/css" href="<%=cssPath %>/stylebootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="/supervise/common/css/supervise.css" />

</head>
<body onload="">

<div id="importFile">
<input type='hidden' id='dataId' value='<%=id%>'/>
<form enctype="multipart/form-data" action=""  method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="100%" align="center">
    <tr>
        <td colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;line-height: 30px;text-indent: 3px'></td>
    </tr>
    <tr id="avatarUpload" >
      <td nowrap class="TableData" valign="top" width='200px' style="text-indent: 15px"> 请指定用于导入的xls文件： </td>
      <td class="TableData" id="">
          <input type="file" name="importDeptFile" id="importDeptFile" size="40" accept="application/vnd.ms-excel" class="BigInput" title="选择附件文件" value=""/>
      </td>
    </tr>
   
    <tr align="center">
      <td colspan="2" nowrap>
       <div align="center">
          <!--  <input type="button" name ="uploadload" onclick="upload()" value="导入" class="btn-win-white"/> -->
 	  </div>
      </td>
    </tr>
  </table>
</form>
  </div>
    <script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
    <script type="text/javascript" src="<%=contextPath%>/supervise/lawManage/lawExamine/js/law_input.js"></script>
</body>
</html>