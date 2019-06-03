<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/easyui2.0.jsp" %>
<title>易报表管理</title>
<script type="text/javascript">
//初始化方法
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/teeEreportController/getEreportList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			/* {field:'sid',checkbox:true,title:'ID',width:200}, */
			{field:'title',title:'报表名称',width:120},
			{field:'catName',title:'所属分类',width:120},
			{field:'chartTypeDesc',title:'图表类型',width:120},
			{field:'crUserName',title:'创建人',width:120},
			{field:'ope_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
		       var opt="";
		       opt+="<a href=\"#\" onclick=\"design("+rowData.sid+")\" >设计</a>&nbsp;&nbsp;&nbsp;";
		       opt+="<a href=\"#\" onclick=\"del("+rowData.sid+")\" >删除</a>&nbsp;&nbsp;&nbsp;";
		       opt+="<a href=\"#\" onclick=\"exportXml("+rowData.sid+")\" >导出</a>";
			    return opt;
			}},
			
		]]
	});
}

//删除
function del(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该报表？", function(){
		  var url = contextPath + "/teeEreportController/delBySid.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
				datagrid.datagrid('reload');
			}   
	  });
}

 function exportXml(sid){
	 $("#frame0").attr("src",contextPath+"/teeEreportController/export.action?sid="+sid);
	//tools.requestJsonRs(contextPath+"/teeEreportController/export.action",{sid:sid});
} 

//设计
function design(sid){
	window.location.href=contextPath+"/system/subsys/ereport/manage.jsp?sid="+sid;
}

//创建
function create(){
	var url=contextPath+"/system/subsys/ereport/create.jsp";
	bsWindow(url ,"创建报表",{width:"400",height:"100",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.save();
		    if(json.rtState){
		       datagrid.datagrid("reload");
		       window.location.href=contextPath+"/system/subsys/ereport/manage.jsp?sid="+json.rtData;
		       return true;
		    }else{
		    	 $.MsgBox.Alert_auto("保存失败！");
		    	 return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}
//导入
function upload(){
	var url=contextPath+"/system/subsys/ereport/uploadView.jsp";
	bsWindow(url ,"报表导入",{width:"600",height:"140",buttons:
		[
         {name:"上传",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="上传"){
		    if(cw.upload()){
		       uploadSuccess();
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}

function doImport(obj){
	if(document.getElementById("file").value.indexOf(".xml")==-1){
		$.MsgBox.Alert_auto("仅能上传xml后缀名模板文件！");
		return false;
	}
	$("#uploadBtn").attr("value","上传中").attr("disabled","");
	return true;
}

function uploadSuccess(){
	$.MsgBox.Alert_auto("导入成功！",function(){
		window.location.reload();
	});
	
}
</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
    <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="imgs/icon_bbsj.png">
		<span class="title">易报表管理</span>
	</div>
    <div class="fr right">
        <input type="button" class="btn-win-white" value="创建"  onclick="create();" />
        <input id="importBtn" type="button" class="btn-win-white" value="导入" onclick="upload()"/>
    </div>
</div>

<table id="datagrid" fit="true"></table>
<iframe id="frame0" style="display:none"></iframe>
</body>
</html>