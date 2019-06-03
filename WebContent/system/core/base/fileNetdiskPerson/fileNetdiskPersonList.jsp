<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	if(sid == null){
    	sid = "0";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人网盘</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdiskPerson/js/fileNetdiskPerson.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/TeeMenu.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">

var sid = '<%=sid%>';
var datagrid;
var title ="";

function doInit(){
  setOptPriv(sid);
  getFileData(sid);
  UPLOAD_ATTACH_LIMIT_GLOBAL = getAvailableSize() + "B";
  
  /* 
  if(sid !=0){
  }else{
    $("#easyuiDiv").hide();
    messageMsg("没有相关文件", "listDiv" ,'' ,380);
  }
   */
  <%-- $("#uploadFileButton").bingUpload({uploadSuccess:function(fileInfo){
	  var url = "<%=contextPath%>/fileNetdiskPerson/uploadNetdiskFile.action";
	  var para = {sid:sid,valuesHolder:fileInfo.sid};
	  var jsonRs = tools.requestJsonRs(url,para);
	},queueComplele:function(){
		window.location.reload();
	},model:"fileNetdiskPerson"}); --%>
}

function setOptPriv(sid){
	if(sid !=0){
		$("#deleteFileButton").show();
		$("#copyFilesButton").show();
		$("#cutFilesButton").show();
		$("#downLoadFilesButton").show();
		$("#uploadFileButton").show();
		$("#shareFolderButton").show();
	}
}

/**
 * 获取文件剩余大小
 */
function getAvailableSize(){
	var fileSize = 0;
	var url = contextPath + "/attachmentController/getLimitedSpaceInfo.action?model=<%=TeeAttachmentModelKeys.FILE_NET_DISK_PERSON%>";
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		fileSize = json.rtData.remain;
	}
	return fileSize;
}




function getFileData(sid){
  getFolderPathFunc(sid);
  datagrid = $('#datagrid').datagrid({
    url : contextPath + '/fileNetdiskPerson/getPersonFilePage.action?folderSid=' + sid,
    view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
    toolbar : '#toolbar',
    title : title,
    iconCls:'',
    pagination : true,
    pageSize : 10,
    /* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
    fit : true,
    fitColumns : true,
    nowrap : true,
    border : false,
    idField : 'sid',
    singleSelect:false,   
	frozenColumns : [[{
						field:'sid',checkbox:true
					},{
						field : 'fileName',
						title : '名称',
						width : 350,
						sortable : true,
						formatter : function(value, rowData, rowIndex) {
							var fileExt = rowData.fileTypeExt;
							var fileTypeImg="";
							var fileNameStr = "" ;
							if(fileExt !="0"){
								fileNameStr = "<div class='fileItem' priv='" + 63 + "' sid='" + rowData.attachSid +"' fileName='"+value+"' fileExt='"+fileExt+"'></div>";
							}else{
								fileTypeImg = "<img src='"+contextPath+"/common/zt_webframe/imgs/grbg/grwp/icon_wjj1.png' style='display:inline-block;width:17px;height:17px;vertical-align: text-bottom;' />&nbsp;" +value;
								fileNameStr = "<a href='#' style='' onclick='openFolderFunc("+ rowData.sid + ");'>" + fileTypeImg + "</a>" ;
							}
							return "<span title='" + value + "'>" + fileNameStr  +"</span>";
						}
				}]],
	columns : [[{
					field : 'fileSize',
					title : '大小',
					width : 100,
					sortable : false,
					formatter : function(value, rowData, rowIndex) {
						if(value){
							return value;
						}else{
							return "-";
						}
					}
				},{
					field : 'createTimeStr',
					title : '修改日期',
					width : 130,
					sortable : true,
					formatter : function(value, rowData, rowIndex) {
						return value;
					}
				},{
					field : '_optmanage',
					title : '操作',
					width : 200,
					formatter : function(value, rowData, rowIndex) {
						var fileExt = rowData.fileTypeExt;
						var reNameStr = "<a href='javascript:void(0);'  onclick='reNameFunc(\"" +rowData.sid + "\",\"" + fileExt + "\");'> 重命名 </a>&nbsp;&nbsp;";
						var optStr = "<a href='javascript:void(0);' onclick='deleteSingleFileFunc(" +rowData.sid + ");'> 删除</a>&nbsp;&nbsp;";
						return "<div>" + reNameStr + optStr + "</span>";
					}
				}
			
			]],onLoadSuccess:onLoadSuccessFunc
	
  });
  
}

/**
 * 获取最大记录数
 */
function onLoadSuccessFunc(){
// 	var data=$('#datagrid').datagrid('getData');
// 	$("#totalPerson").empty();
// 	$(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'});
// 	$(".datagrid-toolbar").css({"padding":"0px"});
	
	$(".fileItem").each(function(i,obj){
		var priv = parseInt($(obj).attr("priv"));
		if(priv&4==4){//去掉删除浮动菜单
			priv -= 4;
		}
		var attachModel = {fileName:$(obj).attr("fileName"),
		priv:priv,ext:$(obj).attr("fileExt"),sid:$(obj).attr("sid")};
		var fileItem = tools.getAttachElement(attachModel,{});
		$(obj).append(fileItem);
	});
}

/**
 * 上传文件
 */
function uploadFileFunc(folderSid){
	var url = contextPath+"/system/core/base/fileNetdiskPerson/uploadFileNetdiskPerson.jsp?sid="+folderSid;
	bsWindow(url,"上传文件",{width:"650",height:"250",
		buttons:[],
		submit:function(v,h){
		  /*  var cw = h[0].contentWindow;
		   cw.doSave(); */
		}
	});
	
}




/**
 * 批量删除文件
 */
function deleteFileFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项！");
		return ;
	}
	deleteFileNetdiskFunc(ids);
}
/**
 * 单个删除文件
 */
function deleteSingleFileFunc(sid){
	deleteFileNetdiskFunc(sid);
}
/**
 * 批量删除文件
 */
function deleteFileNetdiskFunc(ids){
	  $.MsgBox.Confirm ("提示", "确定要删除所选中文件？", function(){
		  var url = contextPath + "/fileNetdiskPerson/deleteFileBySid.action?sids=" + ids;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				$.MsgBox.Alert_auto("删除成功！");
			    $("#datagrid").datagrid("unselectAll");
			    $("#datagrid").datagrid("reload");
				parent.frames["file_tree"].deledteZTreeNode(ids);  
			}   
	  });
}


function getSelectItem(){
  var selections = $('#datagrid').datagrid('getSelections');
	var ids = "";
	for(var i=0;i<selections.length;i++){
		ids+=selections[i].sid;
		if(i!=selections.length-1){
			ids+=",";
		}
	}
	return ids;
}

</script>
<style>
	.t_btns>button{
		padding:5px 8px;
		padding-left:22px;
		text-align:right;
		background-repeat:no-repeat;
		background-position:6px center;
		background-size:17px 17px;
		border-radius:5px;
		background-color:#e6f3fc;
		border:none;
		color:#000;
		outline:none;
		font-size: 12px;
		border: #abd6ea solid 1px ;
	}
</style>
</head>
<body onload="doInit();" style="margin:0px;padding-right:10px;">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix" style='border-bottom:none!important;'>
    <div style="font-weight:bold;border-bottom:2px solid #74c5ff;">
       <span id="previousSpan" style="display:none"></span>
	   <span id="folderPathSpan"></span>
    </div>
	<div class="fl clearfix t_btns" style="margin-top: 5px;">
	    <button type='button' id="downLoadFilesButton" style="display: ;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_xiazai.png)"  class='' value='文件' onclick='downLoadFilesFunc("<%=sid %>");' >&nbsp;下载</button>
		<button type='button' id="uploadFileButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_shangchuan.png)" class='' value='上传' onclick='uploadFileFunc("<%=sid %>")' >&nbsp;上传</button>
		<button type='button' id="newFolderButton" style="display: ;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_xinjianwenjianjia.png)"  class='' value='新建文件夹' onclick='newFolderFunc("<%=sid %>");' >&nbsp;新建文件夹</button>
		<button type='button' id="deleteFileButton" style="display: ;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_shanchu.png)" class="" onclick="deleteFileFunc()">&nbsp;删除</button>
		<button type='button' id="copyFilesButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_fuzhi.png)" class="" onclick="copyFunc('<%=sid%>')">&nbsp;复制</button>
		<button type='button' id="cutFilesButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_fuzhi.png)" class="" onclick="batchCutFunc('<%=sid%>')">&nbsp;移动</button>
		<button type='button' id="shareFolderButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_gongxiang.png)" class="" onclick="shareFolderFunc('<%=sid%>')">&nbsp;共享此文件夹</button>
		<button type='button' style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_shuaxin.png)" class=''  onclick="$('#datagrid').datagrid('unselectAll');$('#datagrid').datagrid('reload');">&nbsp;刷新</button>	    
	</div>
	<div class="fr clearfix" style="margin-top:15px;">
	    <img src="<%=systemImagePath %>/favorite_click.png" class="favStyle" title="添加收藏夹" id="addFav"/>
	</div>
</div>
</body>
</html>