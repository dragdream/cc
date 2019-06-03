<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
//获取分类主键
int typeId=TeeStringUtil.getInteger(request.getParameter("typeId"), 0);
//当前登陆人
TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>列表页面</title>
</head>
<script>
var datagrid;
var typeId=<%=typeId%>;
var loginUserUuid=<%=loginUser.getUuid()%>;
function doInit(){
	getData();
}


//获取列表数据
function getData(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/supervisionController/getSupervisionListByTypeId.action?typeId="+typeId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:100}, */
			{field:'supName',title:'工作事项',width:120},
			{field:'createrName',title:'创建人',width:100},
			{field:'leaderName',title:'责任领导',width:100},
			{field:'managerName',title:'主办人',width:100},
			{field:'assistNames',title:'协办人',width:200},
			{field:'status',title:'工作状态',width:100,formatter:function(value,rowData,rowIndex){
				var status=rowData.status;
				var desc="";
				if(status==0){
					desc="未发布";
				}else if(status==1){
					desc="办理中";
				}else if(status==2){
					desc="暂停申请中";
				}else if(status==3){
					desc="暂停中";
				}else if(status==4){
					desc="恢复申请中";
				}else if(status==5){
					desc="办结申请中";
				}else if(status==6){
					desc="已办结";
				}else if(status==7){
					desc="待接收";
				}
				return desc;
			}},
			{field:'endTimeStr',title:'截止时间',width:100},
			{field:'opt_',title:'操作',width:150,formatter:function(value,rowData,rowIndex){
				var status=rowData.status;
				var createrId=rowData.createrId;
				var opt="";
				if(createrId==loginUserUuid){//当前登陆人 是创建人 
					if(status==0){
						opt="<a href=\"#\" onclick=\"handle("+rowData.sid+")\">办理</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"edit("+rowData.sid+")\">修改</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"publish("+rowData.sid+")\">发布</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>";
					}else if(status==1||status==2||status==3||status==4||status==5||status==7){
						opt="<a href=\"#\" onclick=\"handle("+rowData.sid+")\" >办理</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>";
					}else if(status==6){
						opt="<a href=\"#\" onclick=\"handle("+rowData.sid+")\">查看</a>&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+rowData.sid+")\">删除</a>";
					}
				}else{
					opt="<a href=\"#\" onclick=\"handle("+rowData.sid+")\">查看</a>";
				}
				
				return opt;
			}},
			
		]]
	});
}


//办理
function handle(sid){
	var url=contextPath+"/system/subsys/supervise/handle/index.jsp?sid="+sid;
	openFullWindow(url);
}

//删除
function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该督办任务？", function(){
		  var url=contextPath+"/supervisionController/delBySid.action";
		  var json=tools.requestJsonRs(url,{sid:sid});
		  if(json.rtState){
			  $.MsgBox.Alert_auto("删除成功！");
			  datagrid.datagrid("reload");
		  }
	  });

}

//编辑
function edit(sid){
	var url=contextPath+"/system/subsys/supervise/supervisionManage/addOrUpdate.jsp?sid="+sid;
	top.bsWindow(url ,"编辑督办任务",{width:"800",height:"380",buttons:
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
		    		$.MsgBox.Alert_auto("编辑成功");
		    		datagrid.datagrid("reload");
		    	}else{
		    		$.MsgBox.Alert_auto("编辑失败！");
		    	}
		    	
		    });
		}else if(v=="发布"){
			cw.save(1,function(flag){
				if(flag){
		    		d.hide();
		    		$.MsgBox.Alert_auto("发布成功");
		    		datagrid.datagrid("reload");
		    	}else{
		    		$.MsgBox.Alert_auto("发布失败！");
		    	}
		    });
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

//发布
function publish(sid){
	$.MsgBox.Confirm ("提示", "是否确认发布该督办任务？", function(){
		  var url=contextPath+"/supervisionController/publish.action";
		  var json=tools.requestJsonRs(url,{sid:sid});
		  if(json.rtState){
			  $.MsgBox.Alert_auto("发布成功！");
			  datagrid.datagrid("reload");
		  }
	  });
}
</script>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;padding-top: 5px">
   <table id="datagrid" fit="true"></table>
</body>
</html>