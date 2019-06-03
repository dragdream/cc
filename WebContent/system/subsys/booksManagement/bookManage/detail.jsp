<%@page import="com.tianee.webframe.util.str.TeeUtility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<title>图书详细资料 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">


</script>
</head>

<body class="bodycolor" topmargin="5">
<table border="0" width="450px" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><span class="big3"> 图书详细信息</span>
    </td>
  </tr>
</table>
  <form name="form1">
<table class="TableBlock"  width="450px" align="center">

   <tr>
    <td nowrap class="TableData" width="70">部门：</td>
    <td nowrap class="TableData"  width="280">
      ${bookModel.createDeptName }&nbsp;
    </td>
    <td class="TableData" rowspan="6" width="100">
    <c:choose>
        <c:when test="${bookModel.avatarId > 0}">
            
            <img id="avatarImg" src="<%=contextPath%>/attachmentController/downFile.action?id=${bookModel.avatarId}&model=book"></img>
        </c:when>
       <c:otherwise>
            <center>暂无封面</center>
       </c:otherwise>
    </c:choose>  
   </td>
   </tr>
   <tr>
    <td nowrap class="TableData">书名：</td>
    <td nowrap class="TableData">${bookModel.bookName }&nbsp;</td>
   </tr>
   <tr>
    <td nowrap class="TableData">编号：</td>
    <td nowrap class="TableData">
    ${bookModel.bookNo }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">图书类别：</td>
    <td nowrap class="TableData">
    ${bookModel.bookTypeName }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">作者：</td>
    <td nowrap class="TableData">
    ${bookModel.author }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">ISBN号：</td>
    <td nowrap class="TableData">
    ${bookModel.ISBN }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">出版社：</td>
    <td nowrap class="TableData" colspan="2">
    ${bookModel.pubHouse }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">出版日期：</td>
    <td nowrap class="TableData" colspan="2">
    ${bookModel.pubDateStr }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">存放地点：</td>
    <td nowrap class="TableData" colspan="2">
    ${bookModel.area }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">数量：</td>
    <td nowrap class="TableData" colspan="2">
    ${bookModel.amt }&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">借书待批：</td>
    <td class="TableData" colspan="2">

<table class="TableBlock"  width="100%"  align="center" >
   <tr align="center">
     <td nowrap class="TableData">借书人</td>
     <td nowrap class="TableData">借书日期</td>
     <td nowrap class="TableData">归还日期</td>
     <td nowrap class="TableData">数量</td>
   </tr>
<c:forEach items="${list1}" var="list1Sort" varStatus="list1Status">
   <tr align="center">
     <td nowrap class="TableData">${list1[list1Status.index].buserName}</td>
     <td nowrap class="TableData">${list1[list1Status.index].borrowDateStr}</td>
     <td nowrap class="TableData">${list1[list1Status.index].returnDateStr}</td>
     <td nowrap class="TableData">1</td>
   </tr>
</c:forEach>
</table>
</td>
   <tr>
    <td nowrap class="TableData">未还记录：</td>
    <td class="TableData" colspan="2">

<table class="TableBlock" width="100%" align="center" >
   <tr align="center">
     <td nowrap class="TableData">借书人</td>
     <td nowrap class="TableData">借书日期</td>
     <td nowrap class="TableData">归还日期</td>
     <td nowrap class="TableData">数量</td>
   </tr>

<c:forEach items="${list2}" var="list2Sort" varStatus="list2Status">
   <tr align="center">
     <td nowrap class="TableData">${list2[list2Status.index].buserName}</td>
     <td nowrap class="TableData">${list2[list2Status.index].borrowDateStr}</td>
     <td nowrap class="TableData">${list2[list2Status.index].returnDateStr}</td>
     <td nowrap class="TableData">1</td>
   </tr>
</c:forEach>
   </table>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">价格：</td>
    <td nowrap class="TableData" colspan="2">
    ${bookModel.price }&nbsp;元
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">内容简介：</td>
    <td class="TableData" colspan="2">
    ${bookModel.brief }
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">借阅范围：</td>
    <td nowrap class="TableData" colspan="2" width="300">${bookModel.postDeptNames }${bookModel.postUserNames }${bookModel.postUserRoleNames }
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">借阅状态：</td>
    <td nowrap class="TableData" colspan="2">
	${lendDesc }
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">新建人：</td>
    <td nowrap class="TableData" colspan="2">
   ${bookModel.createUserName } &nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">备注：</td>
    <td nowrap class="TableData" colspan="2">
     ${bookModel.memo }&nbsp;
    </td>
   </tr>
   <tr align="center" class="TableData">
     <td colspan="3">
       <input type="button" value="关闭" class="btn btn-primary" onClick="javascript:CloseWindow();">
     </td>
   </tr>
 
</table>
 </form>
<br>
</body>
</html>