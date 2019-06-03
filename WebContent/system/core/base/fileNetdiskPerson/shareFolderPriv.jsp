<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	if(sid == null){
		sid = "0";
	}

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>共享权限设置 </title>
<script type="text/javascript" src="<%=contextPath%>/system/core/base/fileNetdiskPerson/js/shareFolderPriv.js"></script>
<style>
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	border-bottom:1px solid #f2f2f2;
	font-weight:bold;
	border-left:3px solid #6ba6fe;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
    border: 1px solid #d6d6d6!important;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #d6d6d6!important;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #d6d6d6!important;
	background-color: #e8ecf9; 
}

/*input和label对齐*/
.TableHeader > td > input[type=checkbox]{
	vertical-align:middle;
}


</style>
<script type="text/javascript">
var sid = "<%=sid%>";
function doInit(){
  doInitUserPrivFunc(sid);
}
</script>

</head>
<body onload="doInit();" style="background-color: #f2f2f2">
<div id="toolbar" class = "topbar clearfix" style="margin-right: 17px">
	<div class="fl" style="position:static;">
		<span style="font-size: 14px;font-weight: bold;">用户权限：</span>
	</div>
	<div class = "right fr clearfix">
		  <input type="hidden" class="btn-win-white" type="button" value="刷新">
          <input class="btn-win-white" type="button" onclick="showUserPriv();" value="添加用户">
          <input class="btn-del-red" type="button" onclick="submitPersonPriv();" value="提交">	
    </div>
</div>
 <div id="itemsContainer" align="left" style="max-height:280px;height:280px;overflow-y:scroll;">
     <input type="hidden"  id="fileFolderSid" name="fileFolderSid" value="<%=sid %>" title="目录sid" >
    <!-- 用户权限设置 -->
      <div id="con_one_1" class="hover">
        <p>
        <div id="bodyDiv"></div>
      </div>
</div>
</body>
</html>