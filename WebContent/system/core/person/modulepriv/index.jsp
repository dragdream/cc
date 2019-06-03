<%@page import="com.sun.xml.internal.bind.v2.schemagen.xmlschema.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String personUuid = request.getParameter("personUuid") == null ? "" : request.getParameter("personUuid");//人员UUID
	String personName = request.getParameter("personName") == null ? "" : request.getParameter("personName");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var personUuid= "<%=personUuid%>";
var personName= "<%=personName%>";
var jsArray = [
             //["<%=TeeModelIdConst.SYS_ONLINE_USER%>",'在线人员' ,personName+'可以看到所选范围的所有在线人员，为空则不限制'],
             //["<%=TeeModelIdConst.SYS_ALL_USER%>" , '全部人员', personName+'可以看到所选范围的所有人员，为空则不限制'],
             //["<%=TeeModelIdConst.MONITOR_PRIV%>" , '下属监控范围', personName+'可监控的下属人员范围，为空则只能监控本部门且比自己低的角色'],
<%--              ["<%=TeeModelIdConst.CALENDAR_SEND_PRIV%>",'日程安排发送范围',personName+'可以安排所选范围人员的日程安排，为空则不限制'], --%>
<%--              ["<%=TeeModelIdConst.CALENDAR_POST_PRIV%>",'日程安排管理范围',personName+'可以安排管理所选范围人员的日程安排，为空则管理本部门且比自己低的角色的日程安排'], --%>
             
             ["<%=TeeModelIdConst.DIARY_SEND_PRIV%>",'日志共享范围',personName+'可以共享所选范围人员的工作日志，为空则不限制'],
<%--              ["<%=TeeModelIdConst.DIARY_POST_PRIV%>",'下属日志管理范围',personName+'可以挂历所选范围人员的工作日志,为空则管理本部门且比自己低的角色的工作日志'], --%>
             
<%--              ["<%=TeeModelIdConst.NOTIFY_SEND_PRIV%>",'公告通知发送范围',personName+'可以发送所选范围人员的公告通知，为空则不限制'], --%>
             ["<%=TeeModelIdConst.NOTIFY_POST_PRIV%>",'公告通知管理范围',personName+'可以管理所选范围人员的公告通知，为空则管理本部门且比自己低的角色的公告通知'],
             
<%--              ["<%=TeeModelIdConst.NEWS_SEND_PRIV%>",'新闻发送范围',personName+'可以发送所选范围人员的新闻，为空则不限制'], --%>
             ["<%=TeeModelIdConst.NEWS_POST_PRIV%>",'新闻管理范围',personName+'可以管理所选范围人员的新闻，为空则管理本部门且比自己低的角色的新闻'],
             
<%--              ["<%=TeeModelIdConst.VOTE_SEND_PRIV%>",'投票发送范围',personName+'可以发送所选范围人员的投票，为空则不限制'], --%>
             ["<%=TeeModelIdConst.VOTE_POST_PRIV%>",'投票管理范围',personName+'可以管理所选范围人员的投票，为空则管理本部门且比自己低的角色的投票'],
             
             ["<%=TeeModelIdConst.HR_PERSONAL_RECORDS_QUERY_PRIV%>",'人事档案查询范围',personName+'可以查询所选范围人员的信息，为空则不限制'],
             ["<%=TeeModelIdConst.HR_PERSONAL_RECORDS_POST_PRIV%>",'人事档案管理范围',personName+'可以管理所选范围人员的信息，为空则管理本部门且比自己低的角色的人事档案'],
             
<%--              ["<%=TeeModelIdConst.HR_PERSONAL_RECORDS_POST_PRIV%>",'邮件发送范围',personName+'可以发送所选范围人员，为空则不限制'], --%>
            
             ["<%=TeeModelIdConst.CONTRACT_MANAGER_POST_PRIV%>",'合同管理范围',personName+'可以管理所选范围人员的合同，为空则管理本部门且比自己低的角色的合同'],
             
<%--              ["<%=TeeModelIdConst.CRM_CUSTOMER_CONTRACT_MANAGER_POST_PRIV%>",'客户管理合同管理范围',personName+'可以管理所选范围人员的客户合同，为空则管理本部门且比自己低的角色的客户合同'], --%>
             ["<%=TeeModelIdConst.PAI_BAN_POST_PRIV%>",'排班值班管理范围',personName+'可以管理所选范围人员的排班情况，为空则不限制']
             ];
function init() {
	  var ul = document.getElementById("teemenulist");
	  var templiall = '';
	  for(var i = 0;i < jsArray.length;i++) {
		  
		  templiall = templiall +  "<div style='border-bottom:1px solid #ddd;'><span style='margin-right:20px;margin-top:10px;' class='fr caret-right'></span><a style='line-height:35px;text-indent:20px;color:#000;display:inline-block;' href='javascript:void(0)' class=\"list-group-item\" onclick=\"clickMenu("+jsArray[i][0] +",'"+jsArray[i][1]+"','"+jsArray[i][2]+"');\">"
		  	
		  	+"&nbsp;" + jsArray[i][1]
		    +"</a></div>";
	  }   
	  $("#group").append(templiall);
}

/***
*
*点击模块事件
*/
function clickMenu(i , modelName,modelDesc) {
	  var tempid = i ;
	  $("#frame0").attr("src","<%=contextPath%>/system/core/person/modulepriv/privset.jsp?personUuid=" + personUuid +"&personName=" + encodeURIComponent('<%=personName%>') + "&moduleId=" + tempid
			     + "&moduleName=" +  encodeURIComponent(modelName)
			     + "&modelDesc=" + encodeURIComponent(modelDesc));
 	  //window.parent.privset.location="
 	  $(document).scrollTop(0);
}
function doInit(){
	$("#layout").layout({auto:true});
	
	init();
	clickMenu(1,jsArray[0][1], jsArray[0][2] );
	$("#group").group();
}


</script>
<style>
		html{
			overflow: hidden;
		}
		body{
			background-color:#fff;
		}
		/* .panel-heading > span{
			position:absolute;
		}
		.panel-heading{
		padding: 10px 5px;
		font-size: 14px;
		text-align: left;
		text-indent:20px;
		box-sizing: border-box;
		}
		.panel-title {
			margin-left:15px;
			display:inline;
		}
		.panel-title a{
		color:#000;
		}
		.groupContent li{
		height: 30px;
		line-height: 30px;
		font-size: 12px;
		text-align: left;
		text-indent:60px;
		cursor:pointer;
		}
		.groupContent li:hover{
		background-color:#fff;
		color:#fff;
		}
		.groupContent li a{
		color:#000;
		}
		.groupContent li a:hover{
		color:#000;
		} */
		li.li_active{
			background-color:#fff;
		}
		#group{
			border:1px solid #ddd;
			height:400px;
			overflow: auto;
		}
</style>

</head>
<body onload="doInit()" style="overflow-y:auto;overflow-x:hidden;font-size:12px">
<div id="layout">
	<div layout="west" width="213">
		<div id="group" class="list-group">
		</div>
	</div>
	<div layout="center" style="">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
</body>
</html>