

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>控制面板</title>
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
<script type="text/javascript">
</script>


</head>

<body style="margin:5px;font-size:12px">


<div class="panel-group" id="accordion" style="width:180px;padding:20px 3px 3px 3px;">
  <div class="panel panel-default">
    <div class="panel-heading" align="">
      <h5 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#">
          界面设置
        </a>
      </h5>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      <div class="panel-body">
        <a href="<%=contextPath %>/system/core/person/setdescktop/desktop.jsp" target="c_main"><span>界面主题</span></a>
        <a href="<%=contextPath %>/system/core/person/setdescktop/shortcutMenu.jsp" target="c_main"><span>菜单常用组</span></a> 
   	    <a href="<%=contextPath %>/system/core/fav/manage.jsp" target="c_main"><span>收藏夹</span></a>
   	    <a href="<%=contextPath %>/system/core/person/setdescktop/desktopSetting.jsp" target="c_main"><span>桌面模块</span></a>
   	   </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#">
          个人信息
        </a>
      </h5>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse in">
      <div class="panel-body">
         <a href="<%=contextPath %>/system/core/person/setdescktop/info.jsp" target="c_main"><span>个人资料</span></a>
    <a href="<%=contextPath %>/system/core/person/setdescktop/avatar.jsp" target="c_main"><span>昵称与头像</span></a>
    <a href="<%=contextPath %>/system/core/dept/usergroup/personalgroup.jsp" target="c_main"><span>自定义用户组</span></a>
    <a href="<%=contextPath %>/system/core/person/setdescktop/updateSealPwd.jsp" target="c_main"><span>印章密码修改</span></a>
         
          </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h5 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#">
          账号与安全
        </a>
      </h5>
    </div>
    <div id="collapseThree" class="panel-collapse collapse in">
      <div class="panel-body">
        <a href="<%=contextPath %>/system/core/person/setdescktop/mypriv.jsp" target="c_main"><span>我的帐户</span></a>
   	 	<a href="<%=contextPath %>/system/core/person/setdescktop/password.jsp" target="c_main"><span>修改密码</span></a>
    	<a href="<%=contextPath %>/system/core/person/setdescktop/securitylog.jsp" target="c_main"><span>安全日志</span></a>
       
       </div>
    </div>
  </div>
</div>

</body>
</html>