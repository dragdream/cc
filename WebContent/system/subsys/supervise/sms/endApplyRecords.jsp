<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
//当前登陆人
  TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>办结申请记录</title>
</head>
<script>
var supId=<%=supId%>;//任务主键
var datagrid;
function doInit(){
	getList();
}

//判断当前登陆人  是不是任务的责任领导
function isLeader(){
	var url=contextPath+"/supervisionController/getStatusAndRole.action";
	var json=tools.requestJsonRs(url,{sid:supId});
	if(json.rtState){
		var data=json.rtData;
		var isLeader=data.isLeader;	
		
		return isLeader;
	}
}


//获取列表
function getList(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supervisionApplyController/getEndApplyListBySupId.action?supId="+supId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			
			{field:'createrName',title:'申请人',width:120},
			{field:'content',title:'申请内容',width:120},
			{field:'createTimeStr',title:'申请时间',width:120},
			{field:'status',title:'申请状态',width:120,formatter:function(value,rowData,rowIndex){
			   var status=rowData.status;
			   var desc="";
			   if(status==0){
				   desc="待审批";
			   }else if(status==1){
				   desc="已同意";
			   }else if(status==2){
				   desc="已拒绝";
			   }
			   return desc;
			}},{field:'opt',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
				var opt="";  
				var status=rowData.status;
				var isLead=isLeader();
				if(status==0){
					if(isLead==1){
						opt+="<a href=\"#\" onclick=\"approve(1,"+rowData.sid+")\">同意</a>&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"approve(2,"+rowData.sid+")\">拒绝</a>";   
					}	
				}
				
				return opt;
			}},
			
		]]
	});
}



//审批
function approve(status,sid){
	var mess1="";
	var mess2="";
	if(status==1){
		mess1="是否确认同意该申请？";
		mess2="已同意";
	}else if(status==2){
		mess1="是否确认拒绝该申请？";
		mess2="已拒绝";
	}
	$.MsgBox.Confirm ("提示",mess1, function(){
		  if(status==1){//同意
			  var url = contextPath + "/supervisionApplyController/approve.action";
			  var para = {sid:sid,status:status};
			  var json = tools.requestJsonRs(url,para);
			  if(json.rtState){					
					//刷新父页面的datagrid
					 parent.xparent.datagrid.datagrid("reload");
					  $.MsgBox.Alert_auto(mess2,function(){
						//刷新当前页面
						parent.window.location.reload();
					   });
					
				} 
		  }else{
			  var url1=contextPath+"/system/subsys/supervise/handle/reason.jsp?status="+status+"&&sid="+sid;
			  bsWindow(url1 ,"拒绝原因",{width:"600",height:"140",buttons:
					[
                   {name:"保存",classStyle:"btn-alert-blue"},
				 	 {name:"关闭",classStyle:"btn-alert-gray"}
					 ]
					,submit:function(v,h,f,d){
					var cw = h[0].contentWindow;
					if(v=="保存"){
					    var a=cw.save();
					    if(a){
					    	d.hide();
							 $.MsgBox.Alert_auto(mess2,function(){
								//刷新当前页面
								  datagrid.datagrid("reload");
								
							 });
							  
					    }
					}else if(v=="关闭"){
						return true;
					}
				}}); 
		  }
		       
	  });

	
}
</script>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
 <div id="toolbar" class="topbar clearfix">
      <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/supervise/imgs/icon_sqjl.png">
		<span class="title">办结申请记录 </span>
	</div>
   </div>
   <table id="datagrid" fit="true"></table>
</body>
</html>