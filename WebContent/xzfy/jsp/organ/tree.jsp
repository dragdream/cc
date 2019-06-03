<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
  int isAdmin = 0;//是否是超级管理员
  if(TeePersonService.checkIsAdminPriv(loginUser)){
	  isAdmin = 1;
  }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
	<%@ include file="/header/header2.0.jsp" %>
	<%@ include file="/header/ztree.jsp" %>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<!-- jQuery 布局器 -->
	<link rel="stylesheet" type="text/css" href="/xzfy/css/organ/organ.css" />
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">

	<div layout="west" width="280px" style="overflow-y:auto;overflow-x:hidden;position:absolute;left:0px;top:0px;bottom:0px;width:280px">
		<br />
		<div id="group" class="list-group">
		    
		 	<div class="panel-group" class="list-group">
			    <div class="panel panel-default">
				    <div class="panel-heading menuList">
				        <span class='caret-down' ></span>
				        <h4 class="panel-title">
				            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
				          	部门列表
				            </a>
				        </h4>
				    </div>
				    <div id="collapseOne" class="panel-collapse collapse in">
				      	<div class="panel-body" style="padding:0px;">
				       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:100%;min-height:330px; padding:2px;"></ul>
				   	    </div>
				    </div>
				</div>
				<div class="panel panel-default">
				    <div class="panel-heading menuList dom">
				        <h5 class="panel-title">
				            <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="userGroup();">
				         	公共自定义组
				            </a>
				        </h5>
				    </div>
			    </div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="/xzfy/js/organ/tree.js" ></script>
</body>
</html>