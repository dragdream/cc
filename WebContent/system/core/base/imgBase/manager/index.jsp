<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片库管理</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>
var datagrid;
function doInit(){
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/teeImgBaseController/datagrid.action",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false,
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:100},
			{field:'imgDirName',title:'目录名称',width:100},
			{field:'imgDir',title:'目录路径',width:200},
			{field:'storeDesc',title:'查看范围',width:400,
			formatter:function(value,rowData,rowIndex){
				var deptNames = rowData.postDeptNames;
				var personNames = rowData.postUserNames;
				var rolesNames = rowData.postUserRoleNames;
				var renderHtml = "";
				if(deptNames && deptNames != ""){
					renderHtml = renderHtml + "<div><B>部门：</B>&nbsp;&nbsp;"+deptNames+""+"</div>";
				}
				if(personNames && personNames != ""){
					renderHtml = renderHtml + "<div><B>人员：</B>&nbsp;&nbsp;"+personNames+""+"</div>";
				}
				if(rolesNames && rolesNames != ""){
					renderHtml = renderHtml + "<div><B>角色：</B>&nbsp;&nbsp;"+rolesNames+""+"</div>";
				}
				if(rowData.allPriv==1){
					renderHtml="<div>全体人员</div>";
				}
				
				return "<div>"+renderHtml+"</div>";
			}
			},
			{field:'2',title:'操作',width:100,formatter:function(e,rowData,index){
				return "<a href='#' onclick='eidtImgBase("+index+")'>修改</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void();' onclick='setPriv("+index+")'>权限设置</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void();' onclick='del("+index+")'>删除</a>";
			}}
		]]
	});
}

function del(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要删除的图片库");
		return;
	}
	var sid = selection.sid;
	
	  $.MsgBox.Confirm ("提示", "确认删除该图片库信息吗？", function(){
		  var url = contextPath+"/teeImgBaseController/delImgBase.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				$.MsgBox.Alert_auto(json.rtMsg);
				datagrid.datagrid("unselectAll");
				datagrid.datagrid("reload");
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}   
	  });

}


function setPriv(index){
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要设置权限的图片库！");
		return;
	}
	var sid = selection.sid;
	bsWindow(contextPath+"/system/core/base/imgBase/manager/setPriv.jsp?sid="+sid
			,"图片库权限设置"
			,{height:"300",width:"750",buttons:
				[
                 {name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,
		     submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						datagrid.datagrid("reload");
						return true;
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
				}else if(v=="关闭"){
					return  true;
					
				}	
			}});
	
}
function addImgBase(){
	bsWindow(contextPath+"/system/core/base/imgBase/manager/addImgBase.jsp?"
			,"新建图片库"
			,{height:"300",width:"650",buttons:
				[
                 {name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
					var cw = h[0].contentWindow;
					if(v=="保存"){
						var json = cw.commit();
						if(json.rtState){
							datagrid.datagrid("reload");
							return true;
						}else{
							$.MsgBox.Alert_auto(json.rtMsg);
						} 
					}else if(v=="关闭"){
						return true;
					}		
			}});
}

function eidtImgBase(index){
	datagrid.datagrid("unselectAll");
	if(index>=0){
		datagrid.datagrid("selectRow",index);
	}
	var selection = datagrid.datagrid("getSelected");
	if(selection==null || selection==undefined){
		$.MsgBox.Alert_auto("请选择需要修改的内容");
		return;
	}
	var sid = selection.sid;
	bsWindow(contextPath+"/system/core/base/imgBase/manager/addImgBase.jsp?sid="+sid
			,"编辑图片库"
			,{height:"350",width:"650",buttons:
				[
                 {name:"保存",classStyle:"btn-alert-blue"},
			 	 {name:"关闭",classStyle:"btn-alert-gray"}
				 ]
				,submit:function(v,h){
				var cw = h[0].contentWindow;
				if(v=="保存"){
					var json = cw.commit();
					if(json.rtState){
						datagrid.datagrid("reload");
						return true;
					}else{
						$.MsgBox.Alert_auto(json.rtMsg);
					}
				}else if(v=="关闭"){
					return true;
				}
				
			}});
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<table id="datagrid" fit="true"></table>
	<div id="toolbar" class="topbar clearfix">
	    <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/imgBase/img/tpksz.png">
		  <span class="title">图片库设置 </span>
	    </div>
	    <div class = "right fr clearfix">
		   <input type="button" class="btn-win-white fl" onclick="addImgBase()" value="新建图片库"/>
	    </div>
	
	</div>
</body>
</html>