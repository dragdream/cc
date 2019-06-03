<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id")==null?"0":request.getParameter("id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<script type="text/javascript" src="<%=contextPath%>/supervise/subject/js/subject_login.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/md5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sysUtil.js"></script>
<script type="text/javascript">
var id = '<%=id%>';
</script>
</head>
<body onload="doInit();" style="padding: 10px;background-color: #fff;">
        <div >
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value=""/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">账&nbsp;&nbsp;&nbsp;号<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style="width: 300px;">
                            <input name="userName" id="userName" class="easyui-textbox" data-options="validType:'length[1,30]',required:true, missingMessage:'请输入账号'" style="width: 89%;"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                    	<td class="power-table-label" style="text-align:right;">用户名<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style="width: 300px;">
                            <input name="subName" id="subName" class="easyui-textbox" data-options="validType:'length[1,200]',required:true, missingMessage:'请输入用户姓名'" style="width: 89%;"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td  colspan="2" class="power-table-label" style="text-align:right;">
                            <span style="text-align:right!important;"><i style="color:red;" class="fa fa-exclamation-circle"></i>默认登录密码为<span style="color:red;">zfjd123456</span>，请提醒账号持有人及时修改密码。</span>
                        </td>
                    </tr>
                    <!-- <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">密&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp码<span style="color:red;font-weight:bold;">*</span>:&nbsp&nbsp</td>
                        <td style=" width: 300px;">
                            <input name="password" id="password" class="easyui-textbox" style="width: 89%;" data-options="novalidate:true,required:true, missingMessage:'请选择身份证号',panelHeight:'auto'" />
                        </td>
                    </tr> -->
                 </table>
            </form>
        </div>
</body>
</html>