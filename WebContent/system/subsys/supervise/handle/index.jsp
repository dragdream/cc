<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
//当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>督办任务管理</title>
</head>
<script>
var sid=<%=sid%>;
var loginUserUuid=<%=loginUser.getUuid()%>;

//初始化方法
function doInit(){
	var url=contextPath+"/supervisionController/getStatusAndRole.action";	
	var json=tools.requestJsonRs(url,{sid:sid});
    if(json.rtState){
    	var data=json.rtData;
    	var status=data.status;
    	var isCreater=data.isCreater;
    	var isLeader=data.isLeader;
    	var isManager=data.isManager;
    	var isAssist=data.isAssist;
    	
    	if(status==0){//未发布
    		if(isCreater==1){
    			$("#optButton").show();
    			$("#publish").show();
    		}
    	}else if(status==1){//办理中
    		if(isCreater==1){
    			$("#optButton").show();
    			$("#create").show();
    			$("#urge").show();
    		}
    		if(isLeader==1){
    			$("#optButton").show();
    			$("#urge").show();
    		}
    		if(isManager==1){
    			$("#optButton").show();
    			$("#feedback").show();
    			$("#applyPause").show();
    			$("#applyFinish").show();
    		}
    		if(isAssist==1){
    			$("#optButton").show();
    			$("#feedback").show();
    		}
    	}else if(status==2||status==4||status==5||status==6){//暂停申请中      恢复申请中    办结申请中 已办结
    		
    	}else if(status==3){//暂停中
    		if(isManager==1){
    			$("#optButton").show();
    			$("#applyRecover").show();	
    		}
    	}else if(status==7){//带接收
    		if(isManager==1){
    			$("#optButton").show();
    			$("#receive").show();
    		}
    	    if(isLeader){
    	    	$("#optButton").show();
    			$("#create").show();
    			$("#urge").show();
    	    }
    	}	
    }
}


//发布任务
function publish(){
	$.MsgBox.Confirm ("提示", "是否确认发布该督办任务？", function(){
		var url=contextPath+"/supervisionController/publish.action";
		   var json=tools.requestJsonRs(url,{sid:sid});
		   if(json.rtState){
			   //刷新父页面的datagrid
			   xparent.datagrid.datagrid("reload");
			   
			   $.MsgBox.Alert_auto("发布成功！",function(){
				 //刷新当前页面
				   window.location.reload();
			   });
		   } 
	  });
}

//创建子任务
function create(){
	var url=contextPath+"/system/subsys/supervise/supervisionManage/addOrUpdate.jsp?parentId="+sid;
	top.bsWindow(url ,"新建督办任务",{width:"800",height:"380",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
		 {name:"发布",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    cw.save(0,function(flag){
		    	if(flag){
		    		d.hide();
		    		$.MsgBox.Alert_auto("新建成功");
		    		//刷新父页面的datagrid
					xparent.datagrid.datagrid("reload");
		    	}else{
		    		$.MsgBox.Alert_auto("新建失败！");
		    	}
		    	
		    });
		}else if(v=="发布"){
			cw.save(7,function(flag){
				if(flag){
		    		d.hide();
		    		$.MsgBox.Alert_auto("发布成功");
		    		//刷新父页面的datagrid
					xparent.datagrid.datagrid("reload");
		    	}else{
		    		$.MsgBox.Alert_auto("发布失败！");
		    	}
		    });
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//任务催办
function urge(){
	var url=contextPath+"/system/subsys/supervise/handle/doUrge.jsp?supId="+sid;
	bsWindow(url ,"任务催办",{width:"600",height:"160",buttons:
		[
         {name:"提交",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="提交"){
		    var a=cw.save();
		    if(a){
		    	$.MsgBox.Alert_auto("催办成功！",function(){
		    		window.location.reload();
		    	});
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}

//发表反馈
function feedback(){
	var url=contextPath+"/system/subsys/supervise/handle/doFeedBack.jsp?supId="+sid;
	bsWindow(url ,"发表反馈",{width:"550",height:"180",buttons:
		[
         {name:"提交",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="提交"){
		    var a=cw.save();
		    if(a){
		    	$.MsgBox.Alert_auto("反馈成功！",function(){
		    		window.location.reload();
		    	});
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
	
}
//申请暂停   //申请恢复    //申请办结
function apply(type){
	var title="";
	if(type==1){
		title="暂停申请";
	}else if(type==2){
		title="恢复申请";
	}else if(type==3){
		title="办结申请";
	}
	var url=contextPath+"/system/subsys/supervise/handle/apply.jsp?supId="+sid+"&&type="+type;
	bsWindow(url ,title,{width:"550",height:"140",buttons:
		[
         {name:"提交",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="提交"){
		    var a=cw.save();
		    if(a){
		    	$.MsgBox.Alert_auto("申请成功！",function(){
	    		//刷新父页面的datagrid
				xparent.datagrid.datagrid("reload");
		    	window.location.reload();
		    	});
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}});
}


//接收任务
function receive(){
	$.MsgBox.Confirm ("提示", "是否确认签收该任务？", function(){
		  var url = contextPath + "/supervisionController/receive.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				//刷新父页面
				$.MsgBox.Alert_auto("签收成功！");
				xparent.datagrid.datagrid("reload");
				window.location.reload();
			}   
	  });
}
</script>

<body onload="doInit();" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/subsys/supervise/imgs/icon_dbgzrw.png">
		<p class="title">督办任务管理</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
			
		</ul>

		<div class="right fr clearfix">
		  <div class="btn-group fl" style="display: none" id="optButton">
		    <button type="button" class="btn-win-white btn-menu" >
		           相关操作<span class="caret-down"></span>
		    </button>
		    <ul class="btn-content" >
		      <li onclick="receive()" id="receive" style="display: none">签收任务</li>
		      <li onclick="publish()" id="publish" style="display: none">发布任务</li>
		      <li onclick="create()" id="create" style="display: none">创建子任务</li>
		      <li onclick="urge()" id="urge" style="display: none">任务催办</li>
		      <li onclick="feedback()" id="feedback" style="display: none">发表反馈</li>
		      <li onclick="apply(1)" id="applyPause" style="display: none">申请暂停</li>
		      <li onclick="apply(2)" id="applyRecover" style="display: none">申请恢复</li>
		      <li onclick="apply(3)" id="applyFinish" style="display: none">申请办结</li>
		    </ul>
		   </div>
		</div>
		<span class="basic_border_grey fl"></span>
	</div>
	  <div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	  
</body>
<script>
 $.addTab("tab","tab-content",[{title:"基本详情",url:contextPath+"/system/subsys/supervise/handle/basicinfo.jsp?sid="+sid},
                              {title:"办理情况",url:contextPath+"/system/subsys/supervise/handle/feedBackRecords.jsp?supId="+sid},
                              {title:"催办记录",url:contextPath+"/system/subsys/supervise/handle/urgeRecords.jsp?supId="+sid},
                              {title:"暂停恢复申请记录",url:contextPath+"/system/subsys/supervise/handle/pauseRecoverApplyRecords.jsp?supId="+sid},
                              {title:"办结申请记录",url:contextPath+"/system/subsys/supervise/handle/endApplyRecords.jsp?supId="+sid}]); 

</script>
</html>