<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");//父文件夹的主键
	if(sid == null){
    	sid = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>报表管理</title>
<style type="text/css">
.filePathClass{
	padding-left: 10px;padding-right: 5px;
}
</style>
<script type="text/javascript" charset="UTF-8">
var sid = '<%=sid%>';
var datagrid;
var priv;//管理权限   查看权限
//初始化
function doInit(){
	//判断查看权限 还是  管理权限
	getPriv();
	if(priv==1){//管理权限
		$("#manageButton").show();	
	}
	//获取列表
	getFileData(sid);
}

//获取当前登录人    对当前文件夹是管理权限还是查看权限
function  getPriv(){
	var url =  contextPath+"/quieeReportController/getPriv.action?folderSid="+sid;
	var jsonObj = tools.requestJsonRs(url, {});
	//给权限赋值
	priv=jsonObj.rtData;
}

//获取文件列表
function getFileData(sid){
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/quieeReportController/getReportPage.action?folderSid=' + sid,
		toolbar : '#toolbar',
		//title : title,
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		singleSelect:false,

		frozenColumns : [ [
		    {field:'sid',checkbox:true},{
			field : 'reportName',
			title : '名称',
			width : 230,
			sortable : true,
			formatter : function(value, rowData, rowIndex) {
				var reportType = rowData.reportType;
				var fileTypeImg="";
				var fileNameStr = "" ;
				if(reportType =="2"){//文件
					//fileTypeImg = "<img src='"+systemImagePath+"/filetype/folder.gif'>&nbsp;" + value ;
				  	fileNameStr = "<a href='#' onclick='openFileFunc("+ rowData.sid + ");'>" + value + "</a>";
				  	
				}else{//文件夹
					fileTypeImg = "<img src='"+systemImagePath+"/filetype/folder.gif'>&nbsp;" + value ;
					fileNameStr = "<a href='#' onclick='openFolderFunc("+ rowData.sid + ");'>" + fileTypeImg + "</a>" ;
				}
				return "<span title='" + value + "'>" + fileNameStr  +"</span>";
			}
		} ] ],
		columns : [ [  {
			field : 'crTimeStr',
			title : '创建日期',
			width : 130,
			sortable : true			
		 } ,  {
			field : '_optmanage',
			title : '操作',
			width : 200,
			formatter : function(value, rowData, rowIndex) {
		
				var optStr = "";
				var reportType = rowData.reportType;
				
				if(priv==1){//管理权限
					if(reportType=='1'){//文件夹
						optStr += "<a href='javascript:void(0);' onclick='editReportFolder(\"" +rowData.sid + "\");'>编辑</a>&nbsp;";	
					}
					if(reportType == '2' ){//文件						
					optStr += "<a href='javascript:void(0);' onclick='editReport(\"" +rowData.sid + "\");'>编辑</a>&nbsp;";								
					}
					
				}
				
				
				
				return "<div>" + optStr + "</span>";
			} 
	 } ] ],onLoadSuccess:function(rowIndex, rowData){}
	 ,
     onClickRow: function(rowIndex, rowData){
         $("input[type='checkbox']").each(function(index, el){
             if (el.disabled == true) {
             	datagrid.datagrid('unselectRow', index - 1);
             }
         });
     },
     onSelectAll:function(rowIndex, rowData){
     	  $("input[type='checkbox']").each(function(index, el){
               if (el.disabled == true) {
               	datagrid.datagrid('unselectRow', index - 1);
               	el.checked=false;
               }
           });
     }
	});
	
  
}


/**
 * 编辑文件夹
 */
 function editReportFolder(sid){
	 var url = contextPath + "/system/subsys/report/quiee/addOrUpdateFolder.jsp?currentFolderSid=" + sid;
		bsWindow(url ,"编辑文件夹",{width:"650",height:"440",buttons:
			[
			 {name:"保存",classStyle:"btn btn-primary"},
		 	 {name:"关闭",classStyle:"btn btn-primary"}
			 ]
			,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				//cw.commit();
				var isStatus = cw.doSaveOrUpdate();
				if(isStatus){
					window.location.reload();
					//刷新树页面
					parent.$("#file_tree").attr("src","left.jsp");
					return true;
				}
			}else if(v=="关闭"){
				return true;
			}
		}});	
}




/**
 * 打开文件夹
 */
 function openFolderFunc(sid){
   location.href = contextPath + "/system/subsys/report/quiee/reportManage.jsp?sid=" + sid;
 }
 
 function openFileFunc(sid){
	 openFullWindow(contextPath+"/report/reportJsp/showZTReport.jsp?reportId="+sid);
 }
	
/**
 * 批量删除文件
 */
function batchDelete(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		alert("至少选择一项");
		return ;
	}
	if(confirm("确定要删除所选文件或文件夹？")){
		var ids = "";
		for(var i=0;i<selections.length;i++){
			ids+=selections[i].sid;
			if(i!=selections.length-1){
				ids+=",";
			}
		}
		deleteReportsFunc(ids);
	}
}

/**
 * 批量删除文件
 */
function deleteReportsFunc(ids){
  var url = contextPath +  "/quieeReportController/deleteReportsBySid.action?sids=" + ids;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		//alert("删除成功!");
		$("#datagrid").datagrid("reload");
		//刷新树页面
		parent.$("#file_tree").attr("src","left.jsp"); 
	}else{
		alert(jsonRs.rtMsg);
	}
}



//新建文件夹
function newFolder(){	
	var url = contextPath + "/system/subsys/report/quiee/addOrUpdateFolder.jsp?parentId=" + sid;
	bsWindow(url ,"新建文件夹",{width:"650",height:"440",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			var isStatus = cw.doSaveOrUpdate();
			if(isStatus){
				window.location.reload();
				//刷新树页面
				parent.$("#file_tree").attr("src","left.jsp");
				return true;
			}
		}else if(v=="关闭"){
			return true;
		}
	}});	
}


/**
 * 上传文件
 */
function uploadReportFunc(){
	var url = contextPath + "/system/subsys/report/quiee/addOrUpdateReport.jsp?parentId=" + sid;
	bsWindow(url ,"上传报表",{width:"650",height:"200",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			cw.doSaveOrUpdate(function(json){
				window.location.reload();
				window.BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 编辑文件
 */
function editReport(sid){
	var url = contextPath + "/system/subsys/report/quiee/addOrUpdateReport.jsp?reportId=" + sid;
	bsWindow(url ,"编辑报表",{width:"650",height:"200",buttons:
		[
		 {name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			//cw.commit();
			cw.doSaveOrUpdate(function(json){
				window.location.reload();
				window.BSWINDOW.modal("hide");
			});
		}else if(v=="关闭"){
			return true;
		}
	}});
}
</script>
</head>
<body onload="doInit();" id="body">
<table id="datagrid"></table>
<div id="toolbar">
	<div class="base_layout_top" style="position:static">
		<span id="previousSpan"></span>
		<span id="folderPathSpan"></span>
	</div>
	<div style='padding:5px;display: none;' class="Big3" id="manageButton" >
 		<button type='button' id="uploadReportsButton"  class='btn btn-default' value='新建报表' onclick="uploadReportFunc();"><i class="glyphicon glyphicon-cloud-upload"></i>&nbsp;新建报表</button>
 		<button type='button' id="batchDelete"  class='btn btn-default' value='删除' onclick="batchDelete();"><i class="glyphicon glyphicon-remove-circle"></i>&nbsp;删除</button>
 		<button type='button'  class='btn btn-default'  onclick="newFolder();"><i class="glyphicon glyphicon-folder-close"></i>&nbsp;新建文件夹</button>
 		
  	</div>
	<div>
		<div region="center" border="false" style="overflow: hidden;" id="easyuiDiv"></div>
	</div>
</div>

</body>
</html>