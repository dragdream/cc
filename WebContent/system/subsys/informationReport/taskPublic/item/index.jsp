<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   int taskTemplateSid=TeeStringUtil.getInteger(request.getParameter("taskTemplateSid"), 0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置任务模板项</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
//初始化方法
var taskTemplateSid=<%=taskTemplateSid %>;
var datagrid ;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeTaskTemplateItemController/getItemListByTaskTemplateId.action?taskTemplateSid="+taskTemplateSid,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:200},
			{field:'fieldName',title:'字段名称',width:120},
			{field:'filedTypeDesc',title:'字段类型',width:120,formatter:function(value,rowData,rowIndex){
                var fieldType=rowData.fieldType;
                if(fieldType==1){
                	return "单行文本";
                }else if(fieldType==2){
                	return "多行文本";
                }else if(fieldType==3){
                	return "数字文本";
                }else if(fieldType==4){
                	return "日期时间";
                }else if(fieldType==5){
                	return "下拉列表";
                }
			}},
			{field:'showType',title:'显示方式',width:120},
			{field:'showAtListDesc',title:'表头列显示',width:120,formatter:function(value,rowData,rowIndex){
                var showAtList=rowData.showAtList;
                if(showAtList==1){
                	return "是";
                }else{
                	return "否";
                }
			}},
			{field:'cal',title:'统计',width:120},
			{field:'opt_',title:'操作',width:120,formatter:function(value,rowData,rowIndex){
				var opt="<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;&nbsp;";   
                opt+="<a href='#' onclick=\"delBySid("+rowData.sid+")\">删除</a>&nbsp;&nbsp;&nbsp;";
                return opt;
			}},
		]]
	});
	
	
}


//新建/编辑模板项目
function addOrUpdate(sid){
	var title="";
	if(sid==0){
		title="新建模板项";
	}else{
		title="编辑模板项";
	}
	var url=contextPath+"/system/subsys/informationReport/taskPublic/item/addOrUpdate.jsp?sid="+sid+"&&taskTemplateSid="+taskTemplateSid;
	bsWindow(url ,title,{width:"500",height:"200",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSave();
		    if(json.rtState){
		        $.MsgBox.Alert_auto("保存成功！",function(){
		        	datagrid.datagrid("reload");
		        });
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

//删除
function delBySid(sid){
	$.MsgBox.Confirm ("提示", "是否确认删除该模板项？", function(){
		var url=contextPath+"/TeeTaskTemplateItemController/delBySid.action";
		var json=tools.requestJsonRs(url,{sid:sid});
		if(json.rtState){
			$.MsgBox.Alert_auto("删除成功！",function(){
				datagrid.datagrid("reload");
			});
		}else{
			$.MsgBox.Alert_auto("删除失败！");
		}
	 });
}
</script>
</head>
<body  onload="doInit()" style="font-size:12px;padding-left: 10px;padding-right: 10px">
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_rwmbx.png">
		<span class="title">任务模板项</span>
	</div>
	<div class = "right fr clearfix">
		<input type="button" class="btn-win-white fl" onclick="addOrUpdate(0);" value="新建模板项"/>
    </div>
</div>
<table id="datagrid" fit="true"></table>
</body>
</html>