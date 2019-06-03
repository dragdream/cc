<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//项目主键
  String  uuid=TeeStringUtil.getString(request.getParameter("uuid"));
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>项目详情</title>
</head>
<script>
var uuid="<%=uuid%>";
//初始化
function doInit(){
	//判断当前登陆的用户是不是项目负责人  或者  项目创建者
    var url=contextPath+"/projectController/isCreaterOrManager.action";
	var json=tools.requestJsonRs(url,{uuid:uuid});
	if(json.rtState){
		var data=json.rtData;
		if(data==1){//是项目负责人  或者  项目创建者
			$("#optButton").show();
		    var url1=contextPath+"/projectController/getInfoByUuid.action";
		    var json1=tools.requestJsonRs(url1,{uuid:uuid});
		    if(json1.rtState){
		    	var data1=json1.rtData;
		    	if(data1.status==3){//办理中：创建任务、结束项目、挂起项目、销毁项目、变更项目
		    		$("#createLi").show();
		    		$("#endLi").show();
		    		$("#hangLi").show();
		    		$("#destroyLi").show();
		    		$("#changeLi").show();
		    	}else if(data1.status==4){//挂起中：恢复挂起、销毁项目
		    		$("#recoverLi").show();
		    		$("#destroyLi").show();
		    	}else if(data1.status==5){//已结束：销毁项目
		    		$("#destroyLi").show();
		    	}
		    }
		}
	}
}

//创建任务
function  createTask(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/addOrUpdateTask.jsp?projectId="+uuid;
	bsWindow(url ,"创建任务",{width:"1000",height:"400",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		   var b=cw.commit();
		   if(b){
			 $.MsgBox.Alert_auto("任务创建成功！"); 
			 var url=contextPath+"/system/subsys/project/projectdetail/basic/index.jsp?uuid="+uuid+"&&option=任务列表";
			 $('#tab-content iframe').attr('src',url);
			 $($("#tab").find("li")[0]).addClass("select").siblings().removeClass("select");
			 return true;
		   }else{
			 //$.MsgBox.Alert_auto("任务创建失败！");  
			 return false;  
		   }
		   
		}else if(v=="关闭"){
			return true;
		}
	}});
}
//结束任务
function end(uuid){
	$.MsgBox.Confirm ("提示", "是否确认结束该项目？", function(){
		 var url=contextPath+"/projectController/finish.action";
			var param={uuid:uuid};
			var json=tools.requestJsonRs(url,param);
			
			if(json.rtState){
				$.MsgBox.Alert_auto("已完成！");
				opener.datagrid.datagrid('reload');
				window.location.reload();
			} 
	  });
}
//挂起任务
function  hang(uuid){
	$.MsgBox.Confirm ("提示", "是否确认挂起该项目？", function(){
		 var url=contextPath+"/projectController/hang.action";
			var param={uuid:uuid,status:4};
			var json=tools.requestJsonRs(url,param);
			
			if(json.rtState){
				$.MsgBox.Alert_auto("挂起成功！");
				opener.datagrid.datagrid('reload');
				window.location.reload();
			} 
	  });
}
//恢复项目
function recover(uuid){
	$.MsgBox.Confirm ("提示", "是否确认恢复该项目？", function(){
		 var url=contextPath+"/projectController/restoreHang.action";
			var param={uuid:uuid,status:3};
			var json=tools.requestJsonRs(url,param);		
			if(json.rtState){
				$.MsgBox.Alert_auto("还原成功！");
				opener.datagrid.datagrid('reload');
				window.location.reload();
			} 
	  });
}
//变更项目
function  change(uuid){
	var url=contextPath+"/system/subsys/project/projectdetail/change.jsp?uuid="+uuid;
	window.location.href=url;
}

//销毁项目
function destroy(uuid){
	$.MsgBox.Confirm ("提示", "是否确认销毁该项目？", function(){
		var url=contextPath+"/projectController/delByUUid.action";
		var json=tools.requestJsonRs(url,{uuid:uuid});
		if(json.rtState){
			$.MsgBox.Alert_auto("销毁成功！");
			opener.datagrid.datagrid('reload');
			window.close();
		}
	  });
} 
</script>

<body onload="doInit();" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/subsys/project/img/icon_xiangmuxiangqing.png">
		<p class="title">项目详情</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
			
		</ul>

		<div class="right fr clearfix">
		  <div class="btn-group fl" style="display: none" id="optButton">
		    <button type="button" class="btn-win-white btn-menu" >
		           相关操作<span class="caret-down"></span>
		    </button>
		    <ul class="btn-content" >
		      <li onclick="createTask('<%=uuid %>')" id="createLi" style="display: none">创建任务</li>
		      <li onclick="end('<%=uuid%>')" id="endLi" style="display: none">结束项目</li>
		      <li onclick="hang('<%=uuid %>')" id="hangLi" style="display: none">挂起项目</li>
		      <li onclick="recover('<%=uuid %>')" id="recoverLi" style="display: none">恢复挂起</li>
		      <li onclick="change('<%=uuid %>')" id="changeLi" style="display: none">变更项目</li>
		      <li onclick="destroy('<%=uuid %>')" id="destroyLi" style="display: none">销毁项目</li>
		    </ul>
		   </div>
		</div>
		<span class="basic_border_grey fl"></span>
	</div>
	  <div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	  
</body>
<script>
 $.addTab("tab","tab-content",[{title:"基本详情",url:contextPath+"/system/subsys/project/projectdetail/basic/index.jsp?uuid="+uuid},
                             /*  {title:"项目进度",url:contextPath+""}, */
                              {title:"时间管理",url:contextPath+"/system/subsys/project/projectdetail/timeManagement.jsp?uuid="+uuid},
                              {title:"预算及成本",url:contextPath+"/system/subsys/project/projectdetail/cost.jsp?uuid="+uuid},
                             /*  {title:"资源管理",url:contextPath+""}, */
                              {title:"汇总报表",url:contextPath+"/system/subsys/project/projectdetail/summary.jsp?uuid="+uuid},
                              {title:"项目甘特图",url:contextPath+"/system/subsys/project/projectdetail/ganttChart.jsp?uuid="+uuid}]); 

</script>
</html>