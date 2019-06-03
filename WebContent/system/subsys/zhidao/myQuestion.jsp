<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的问题</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var datagrid ;
//初始化
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/zhiDaoQuestionController/getMyQuestion.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'title',title:'问题',width:150,formatter:function(value,rowData,rowIndex){
			  var title=rowData.title;
			  return "<a href=\"#\" onclick=\"detail("+rowData.sid+")\" style='display:block;max-width:550px;overflow:hidden;white-space:nowrap;text-overflow:ellipsis'>"+title+"</a>";
			}
			},
			{field:'catName',title:'所属分类',width:50,formatter:function(value,rowData,rowIndex){
				  var catName=rowData.catName;
				  return "<span style='color:green'>【"+catName+"】</span>";
				}
			},
			{field:'createTimeStr',title:'提问时间',width:50},
			{field:'status_',title:'状态',width:20,formatter:function(value,rowData,rowIndex){
				  var status=rowData.status;
				  if(status==0){//未解决
					 return "<span><img src=\"img/icon_no.png\"></span>"; 
				  }else{//已解决
					  return "<span><img src=\"img/icon_yes.png\"></sapn>";
				  }
			 }
			},
			{field:'opt_',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
				  var status=rowData.status;
				  var opt="";
				  if(status==0){//未解决
					 opt+="<a href=\"#\" onclick=\"edit("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;";
					 opt+="<a href=\"#\" onclick=\"delBySid("+rowData.sid+");\">删除</a>";
				  }
				  return opt;
			 }
			}
		]]
	});
	
}

//问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/zhidao/detail.jsp?sid="+sid;
	openFullWindow(url);
}

//编辑
function edit(sid){
	var url=contextPath+"/system/subsys/zhidao/addQuestion.jsp?sid="+sid;
	window.location.href=url;
}

//删除
function delBySid(sid){
	$.MsgBox.Confirm ("提示", "删除后不可恢复，是否确认删除该问题？", function(){
		var url=contextPath+"/zhiDaoQuestionController/delBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！",function(){
				datagrid.datagrid("reload");
				window.opener.location.reload();
			});
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}   
	 });
}

//查询
function query(){
	var title=$("#title").val();
	datagrid.datagrid("reload",{title:title});
}
</script>
</head>

<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/zhidao/img/icon_my.png">
		<span class="title">我的问题</span>
	</div>
	<div class = "right fr clearfix">
		<input id="title" name="title" style="width:250px;border:1px solid #b8b8b8;height:24px;margin:0px;padding:0px;" /><button style="border:1px solid #3388ff;background:#3388ff;padding:5px;width:80px;font-size:12px;color:#fff;font-weight:bold" onclick="query();">查&nbsp;&nbsp;询</button>
    </div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>