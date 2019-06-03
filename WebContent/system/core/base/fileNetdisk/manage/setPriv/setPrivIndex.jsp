<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color:#f2f2f2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置权限</title>
<%@ include file="/header/header2.0.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/core/base/fileNetdisk/dialog/css/dialog.css"/>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/dialog/js/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileUserPriv.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/deptPriv.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileRolePriv.js"></script>

<script type="text/javascript">
var sid = "<%=request.getParameter("sid")%>";



function doInit(){
 $("#fileFolderSid").val(sid);
  /* 显示权限div */
  //ShowDialog('detail');
  /* 人员权限初始化 */
  doInitUserPrivFunc(sid);
  /* 部门权限初始化 */
  doInitDeptPrivFunc(sid);
  /* 角色权限初始化 */
  doInitRolePrivFunc(sid);
}

</script>
</head>
<style>
	#tab{
		padding:0px 10px 0;
	}
	#tab li{
		margin-right:10px;
		color:#000;
		line-height: 25px;
		margin-right: 20px;
		float: left;
		cursor: pointer;
	}
	.tab_active{
		color:#379ff7!important;
		border-bottom:2px solid #379ff7;
		
	}
	.tabContent li{
		display:none;
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
</style>
<body  onload="doInit()"  style="background-color:#f2f2f2;padding-left: 10px">
    <div class="topbar clearfix" style="margin-right: 10px" >
       <div class="fl left ">
		   <ul id = 'tab' class = 'tab clearfix'>
	         <li class='tab_active'>用户权限</li>
	         <li>部门权限</li>
	         <li>角色权限</li>	
		   </ul>
		   <span class="basic_border_grey fl"></span>
		</div>
		<div  class="fr">
		    <ul class='tab-buttons'>
		       <li style="display:block;">
		           <span id="userPriv" >
                   <input type="hidden" class="btn-win-white" type="button" value="刷新">
                   <input class="btn-win-white" type="button" value="添加用户" onclick="showUserPriv();">
                   &nbsp;
                   <input class="btn-del-red" type="button" onclick="submitPersonPriv();" value="提交">
                   &nbsp;&nbsp;
                   <input id="userPrivMulti" type="checkbox" >&nbsp;<label>将权限继承到子文件夹中</label>
                   </span>
		       </li>
		       <li style="display:none;">
		          <span id="deptPriv" >
                  <input type="hidden" class="btn-win-white" type="button" value="刷新">
                  <input class="btn-win-white" type="button" value="添加部门" onclick="showDeptPriv()">
                  &nbsp;
                  <input class="btn-del-red" type="button" onclick="submitDeptPriv();" value="提交">
                  &nbsp;&nbsp;
                  <input id="deptPrivMulti" type="checkbox" >&nbsp;<label>将权限继承到子文件夹中</label>
                  </span>
		       </li>
		       <li style="display:none;">
		          <span id="rolePriv" >
                  <input type="hidden" class="btn-win-white" type="button" value="刷新">
                  <input class="btn-win-white" type="button" value="添加角色" onclick="showRolePriv()">
                  &nbsp;
                  <input class="btn-del-red" type="button" onclick="submitRolePriv();" value="提交">
                  &nbsp;&nbsp;
                  <input id="rolePrivMulti" type="checkbox" >&nbsp;<label>将权限继承到子文件夹中</label>
                  </span>
		       </li>    
		    </ul>     
        </div>
	</div>
	
	<div id="tab-content" style="margin-right: 10px" >
	<input type="hidden" value="" id="fileFolderSid" name="fileFolderSid" title="目录sid" >
	    <ul class = 'tabContent'>
	       <li style='display:block;'>
	          <!-- 用户权限设置 -->
              <div id="con_one_1" class="hover">
              <p>
              <div id="bodyDiv"></div>
      
              </div>
	       </li>
	       <li style="display:none">
	           <!-- 部门权限设置 -->
	          <div id="con_one_2" >
              <p>
              <div id="deptBodyDiv"></div>
              </div>
	       </li>
	       <li style="display:none">
	          <!-- 角色权限设置 -->
              <div id="con_one_3" >
              <p>
              <div id="roleBodyDiv"></div>
              </div>
	       </li>	
		</ul>
	</div>
	<script>
		$("#tab li").on("click",function(){
			var index = $(this).index();
			$(this).addClass("tab_active").siblings().removeClass('tab_active');
			$(".tabContent li").eq(index).show().siblings().hide();
			
			$(".tab-buttons li").eq(index).show().siblings().hide();
		});
	</script>
</body>
</html>