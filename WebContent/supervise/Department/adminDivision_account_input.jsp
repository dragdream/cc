<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
    import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>

<script type="text/javascript" src="<%=contextPath%>/supervise/Department/js/adminDivision_account_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>

<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/power/css/power.css" />
<link rel="stylesheet" type="text/css" href="/supervise/Department/css/adminDivisionManage.css" />

<title>填报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body onload="doInit();" style="padding: 5px; background-color: #fff;">
        <div title="分配账号">
            <form role="form" id= "form1" name = "form1"  enctype="multipart/form-data" method="post" class="easyui-form" data-options="novalidate:true">
                <input type="hidden" id="id" name = "id" value="<c:out value = '${baseInfo.id}'/>"/>
                <input type="hidden" id="adminDivisionCode" name = "adminDivisionCode" value="<c:out value = '${baseInfo.adminDivisionCode}'/>"/>
                <table class="TableBlock" style="width: 100%; background: #fff;">
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style="width: 300px;">
                            <input name="userAccount" id="userAccount" class="easyui-textbox" data-options="validType:'length[1,50]',required:true, missingMessage:'请输入帐号'" style="width: 89%;"  />
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td class="power-table-label" style="text-align:right;">用&nbsp;&nbsp;户&nbsp;&nbsp;名<span style="color:red;font-weight:bold;">*</span>:&nbsp;&nbsp;</td>
                        <td style="width: 300px;">
                            <input name="userName" id="userName" class="easyui-textbox" data-options="validType:'length[1,50]',required:true, missingMessage:'请输入用户名'" style="width: 89%;"/>
                        </td>
                    </tr>
                    <tr style="border:none!important;">
                        <td  colspan="2" class="power-table-label" style="text-align:left;">
                            <span style="text-align:left!important;"><i style="color:red;" class="fa fa-exclamation-circle"></i>默认登录密码为<span style="color:red;">zfjd213456</span>。请提醒帐号持有人及时修改密码。</span>
                        </td>
              
                    </tr>
                 </table>
            </form>
        </div>
</body>
</html>