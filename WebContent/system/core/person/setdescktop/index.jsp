

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<style>
html{
   overflow-x:hidden;
}
a {
  display:block;
  line-height:22px;
  padding-top:5px;
  font-size:12px;

}

a:link, a:visited, a:hover, a:active {
  display:block;
  text-decoration: none;
}

.panel-body a{
	<%-- background: url("<%=imgPath%>/dot.png") no-repeat scroll left center transparent; --%>
	background: url("<%=imgPath%>/bullet.png") no-repeat scroll left center transparent;
	color: #393939;
	padding-left: 20px;
	  margin-left:15px;
	  font-size:12px;
}
.panel-heading{
padding:5px;
}

</style>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>

function doInit(){
	$("#layout").layout({auto:true});
	$("#group").group();
	//changePage(1);
	$("#frame0").attr("src","<%=contextPath %>/system/core/person/setdescktop/mypriv.jsp");//
}



function changePage(url){
	$("#frame0").attr("src",url);
}



</script>
</head>
<body onload="doInit()" style="overflow:hidden;">
<div class="base_layout_left" style="text-align:center;width:200px;background:none;border:0px" >
	<center>
	<table class="TableBlock" style="width:180px;margin-top:5px">
		<%-- <tr>
			<td class="TableData TableBG" style="font-weight:bold;font-size:14px;text-indent:40px">界面设置</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/desktop.jsp')" ><span>界面主题</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/fav/manage.jsp')"<span>收藏夹</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				 <a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/desktopSetting.jsp')"><span>桌面空间</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData TableBG" style="font-weight:bold;font-size:14px;text-indent:40px">个人信息</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/info.jsp')" ><span>个人资料</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/avatar.jsp')" ><span>昵称与头像</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/dept/usergroup/personalgroup.jsp')"><span>自定义用户组</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/updateSealPwd.jsp')" ><span>电子签章管理</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/updatePicSealPwd.jsp')" ><span>图片签章管理</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/commonword/list.jsp')" ><span>常用语维护</span></a>
			</td>
		</tr> --%>
		<tr>
			<td class="TableData TableBG" style="font-weight:bold;font-size:14px;text-indent:40px">账号安全</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/mypriv.jsp')" ><span>我的帐户</span></a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/password.jsp')" ><span>修改密码</span></a>
			</td>
		</tr>
		
		<%
			String GAO_SU_BO_VERSION = TeeStringUtil.getString(TeeSysProps.getString("GAO_SU_BO_VERSION"));
			if(GAO_SU_BO_VERSION.equals("1")){
		%>
   		 <tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/accountAndPassword.jsp')" ><span>云平台账号/密码设置</span></a>
			</td>
		</tr>
		
		<%} %>
		<tr>
			<td class="TableData" style="text-indent:50px">
				<a href="javascript:void(0)" onclick="changePage('<%=contextPath %>/system/core/person/setdescktop/securitylog.jsp')" ><span>安全日志</span></a> 
			</td>
		</tr>
	</table>
	</center>
</div>
<div class="base_layout_right" style="left:201px;overflow:hidden;">
	<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
</div>

</body>
</html>

