<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>已解决的问题</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var datagrid ;
//初始化
function doInit(){
	//渲染分类
	renderCat();
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/zhiDaoQuestionController/getHandledQuestion.action",
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
			{field:'createUserName',title:'提问者',width:50},
			{field:'createTimeStr',title:'提问时间',width:50},
			{field:'status_',title:'状态',width:20,formatter:function(value,rowData,rowIndex){
				  var status=rowData.status;
				  if(status==0){//未解决
					 return "<span><img src=\"img/icon_no.png\"></span>"; 
				  }else{//已解决
					  return "<span><img src=\"img/icon_yes.png\"></sapn>";
				  }
			 }
			}
		]]
	});
	
}


//查询
function query(){
	var title=$("#title").val();
	var catId=$("#catId").val();
	datagrid.datagrid("reload",{title:title,catId:catId});
}


//渲染分类
function renderCat(){
	var url=contextPath+"/zhiDaoCatController/getAllCat1.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			
			for(var i=0;i<data.length;i++){
				var parent=data[i].parent;
				var children=data[i].children;
				html.push("<option style=\"font-size:14px;font-weight:bold\" value="+parent.sid+">"+parent.catName+"</option>");
			    if(children!=null&&children.length>0){
			    	for(var j=0;j<children.length;j++){
			    		html.push("<option style=\"font-size:12px;\" value="+children[j].sid+">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+children[j].catName+"</option>");
			    	}
			    }
			}
		}
		$("#catId").append(html.join(""));
	}
}


//问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/zhidao/detail.jsp?sid="+sid;
	openFullWindow(url);
}
</script>
</head>

<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/zhidao/img/icon_handled.png">
		<span class="title">已解决的问题</span>
	</div>
	<div class = "right fr clearfix">
		<input id="title" name="title" style="width:250px;border:1px solid #b8b8b8;height:24px;margin:0px;padding:0px;" />
		<select id="catId" name="catId" class="BigSelect left  fl clearfix" style="margin-left:10px;height: 25px" >
		   <option value="0">请选择所属分类</option>
		</select>
		<button style="border:1px solid #3388ff;background:#3388ff;padding:5px;width:80px;font-size:12px;color:#fff;font-weight:bold" onclick="query();">查&nbsp;&nbsp;询</button>
    </div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>