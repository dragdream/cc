<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>应用系统维护</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<script type="text/javascript">
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/ApplicationSystemMaintainController/getList.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		striped : true,
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
            {field:'sid',checkbox:true,width:100},
			{field:'id',title:'ID',width:100,formatter:function(value,rowData,rowIndex){
                return rowData.sid;
			}},
			{field:'systemName',title:'应用系统名称',width:200},
			{field:'url',title:'访问地址',width:200},
			{field:'userNames',title:'人员权限',width:200},
			{field:'deptNames',title:'部门权限',width:200},
			{field:'roleNames',title:'角色权限',width:200},
			{field:'opt_',title:'操作',width:200,formatter:function(value,rowData,rowIndex){
                var  opt= "<a href=\"#\" onclick=\"addOrUpdate("+rowData.sid+")\">编辑</a>&nbsp;&nbsp;" 
                        + "<a href=\"#\" onclick=\"querySysMenu("+rowData.sid+")\">菜单查询</a>";
                return  opt;
			}},
			
		]]
	})

}



//新建
function addOrUpdate(sid){
    var title="";
    var mess="";
    if(sid>0){
    	title="编辑应用系统信息";
    	mess="编辑成功！";
    }else{
    	title="新增应用系统信息";
    	mess="新增成功！";
    }
	var url=contextPath+"/system/core/system/applicationsystem/addOrUpdate.jsp?sid="+sid;
	bsWindow(url ,title,{width:"600",height:"320",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSave();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto(mess,function(){
		    		datagrid.datagrid('reload');
		    	});
		    	return true;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}


//删除   批量删除
function delByIds(){
	var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	
	if(ids.length==0){
		$.MsgBox.Alert_auto("未选中任何记录！");
		return ;
	}else{
		$.MsgBox.Confirm ("提示", "是否确认删除选中的系统信息？", function(){
			var url=contextPath+"/ApplicationSystemMaintainController/delByIds.action";
			var para = {ids:ids};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！",function(){
					datagrid.datagrid('reload');
				});
				
			}	
		});
	}
	
}

function querySysMenu(id){
	 window.location.href = "<%=contextPath %>/system/core/system/applicationsystem/querySysMenu.jsp?sysId=" + id;
}

</script>
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="doInit();">
   <div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/system/applicationsystem/image/icon_yyxtwh.png">
			<span class="title">应用系统维护</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white" onclick="addOrUpdate();" value="新建"/>
			<input type="button" class="btn-del-red fl" onclick="delByIds()" value="删除"/>
	    </div>
   </div>
   <table id="datagrid" fit="true"></table> 
</body>
</html>