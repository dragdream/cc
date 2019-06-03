<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String sid = request.getParameter("sid");
	if(sid == null){
    	sid = "0";
	}
	String shareFolderSid = request.getParameter("shareFolderSid");
	if(shareFolderSid == null){
	    shareFolderSid = "0";
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
<title>共享文件柜</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdiskPerson/js/fileNetdiskPerson.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/TeeMenu.js"></script>
<style type="text/css">

</style>
<script type="text/javascript">

var sid = '<%=sid%>';
var shareFolderSid = '<%=shareFolderSid%>';
var datagrid;
var title ="";
var rootFolderPriv = 0;
function doInit(){
  if(sid !=0){
    setOptPriv(shareFolderSid);
    getFileData(sid);
  }else{
    $("#easyuiDiv").hide();
    messageMsg("没有相关文件", "listDiv" ,'' ,380);
  }
}

function setOptPriv(sid){
  	rootFolderPriv = getFilePrivValueBySid(sid);
	//alert(shareFolderSid + ">>" + rootFolderPriv);
	if((rootFolderPriv & 2)==2){
		//$("#deleteFileButton").show();
		//$("#copyFilesButton").show();
		//$("#cutFilesButton").show();
	}
	if((rootFolderPriv & 4)==4){
		$("#downLoadFilesButton").show();
	}
}


function getFileData(sid){
  getFolderSharePathFunc(sid);
  datagrid = $('#datagrid').datagrid({
    url : contextPath + '/fileNetdiskPerson/getPersonFileSharePage.action?folderSid=' + sid,
    view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
    toolbar : '#toolbar',
    title : title,
    iconCls:'',
    pagination : true,
    pageSize : 10,
  /*   pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
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
								fileNameStr = "<div class='fileItem' priv='" + rootFolderPriv + "' sid='" + rowData.attachSid +"' fileName='"+value+"' fileExt='"+fileExt+"'></div>";
							}else{
								fileTypeImg = "<img src='"+contextPath+"/common/zt_webframe/imgs/grbg/grwp/icon_wjj1.png' style='display:inline-block;width:17px;height:17px;vertical-align: text-bottom;'>&nbsp;" + value ;
								fileNameStr = "<a href='#' onclick='openFolderShareFunc("+ rowData.sid + ");'>" + fileTypeImg + "</a>" ;
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
					sortable : false,
					formatter : function(value, rowData, rowIndex) {
						return value;
					}
				}
				/* ,{
					field : '_optmanage',
					title : '操作',
					width : 200,
					formatter : function(value, rowData, rowIndex) {
						var fileExt = rowData.fileTypeExt;
						var reNameStr = "<a href='javascript:void(0);'  onclick='reNameFunc(\"" +rowData.sid + "\",\"" + fileExt + "\");'> 重命名 </a>&nbsp;&nbsp;";
						var optStr = "<a href='javascript:void(0);' onclick='deleteSingleFileFunc(" +rowData.sid + ");'> 删除</a>&nbsp;&nbsp;";
						return "<div align='center'>" + reNameStr + optStr + "</span>";
					}
				} */
			
			]],onLoadSuccess:onLoadSuccessFunc
	
  });
  
}


/**
 * 获取文件夹目录级别完整路径
 * @param sid
 */
function getFolderSharePathFunc(sid){
  var url = contextPath + "/fileNetdiskPerson/getShareFolderPathBySid.action?sid=" + sid;
  var jsonRs = tools.requestJsonRs(url);
  if(jsonRs.rtState){
    var prcs = jsonRs.rtData;
    var previous = prcs.previous;
    //var rootFolder = prcs.rootFolder;
    var folderPathList = prcs.folderPath;
    if(previous !='0'){
      //$("#previousSpan").show();
      //$("#previousSpan").append("<a href='javascript:void(0)' class='filePathClass' onclick='openFolderShareFunc(" + previous + ")'>返回上一级</a>|");
    }
    var folderPathLength = folderPathList.length-1;
    if(folderPathList.length>0){
      //$("#folderPathSpan").append("<a href='javascript:void(0)' class='filePathClass' onclick='openFolderShareFunc(0)'>全部文件</a>&gt;");
      $.each(folderPathList,function(i,prc){
        //alert("folderPathLength>>" + folderPathLength + "  i>>" +i + " prc.sid>>" + prc.sid + "  prc.folderName>" + prc.folderName);
        if(i !=folderPathLength){
          //alert("id>>" + prc.sid + "  >>" + prc.folderName);
          var folderPathSan = "";
          if(prc.folderName != "根目录"){
            //folderPathSan = "<font  class='filePathClass' color='#000000;' >" + prc.folderName + "</font>";
            folderPathSan = "<a href='#' class='filePathClass' onclick='openFolderShareFunc(" + prc.sid + ")'>" + prc.folderName + "</a>&gt;";
            $("#folderPathSpan").append(folderPathSan);
          }
        }else{
          $("#folderPathSpan").append("<font  class='filePathClass' color='#000000;' >" + prc.folderName + "</font>");
        }
      });
      
    }else{
      //$("#folderPathSpan").append("<a href='javascript:void(0)' class='filePathClass' color='#000000;'>全部文件</a>");
    }
    
  }else{
	$.MsgBox.Alert_auto(jsonRs.rtMsg);  
  }
}


/**打包下载
 * 
 * @param sids
 */
function downLoadFilesShareFunc(folderSid){	
     var ids = getSelectItem();
     if(ids.length==0){
	   $.MsgBox.Alert_auto("至少选择一项");  
       return ;
     }
     var url = contextPath + "/fileNetdiskPerson/downFileShareToZipBySid.action?sids=" + ids + "&folderSid=" + folderSid ;
     window.location.href = url;
} 


/**
 * 打开文件夹
 * @param sid
 */
function openFolderShareFunc(sid){
  location.href = contextPath + "/system/core/base/fileNetdiskPerson/fileNetdiskShareList.jsp?shareFolderSid=<%=shareFolderSid%>&sid=" + sid;
}



/**
 * 获取文件权限
 * @param sid
 */
function getFilePrivValueBySid(sid){
  var rootFolderPrivStr = 0;
  var url = contextPath + "/fileNetdiskPerson/getFilePrivValueBySid.action?sid=" + sid;
  var jsonRs = tools.requestJsonRs(url);
  if(jsonRs.rtState){
    rootFolderPrivStr = jsonRs.rtData.rootFolderPriv;
  }else{
	  $.MsgBox.Alert_auto(jsonRs.rtMsg);  
  }
  return rootFolderPrivStr;
}




/**
 * 获取最大记录数
 */
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
	$("#totalPerson").empty();
	//改变列表样式左边线
	//$("#totalPerson").append( data.total );
	/* $(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'}); */
	$(".datagrid-toolbar").css({"padding":"0px"});
	
	$(".fileItem").each(function(i,obj){
		var priv = parseInt($(obj).attr("priv"));
		if((priv&4)==4){
			priv -= 4;
		}
		var attachModel = {fileName:$(obj).attr("fileName"),
		priv:priv
		,ext:$(obj).attr("fileExt"),sid:$(obj).attr("sid")};
		var fileItem = tools.getAttachElement(attachModel,{});
		$(obj).append(fileItem);
	});
}

/**
 * 上传文件
 */
function uploadFileFunc(folderSid){
	var url = contextPath+"/system/core/base/fileNetdiskPerson/uploadFileNetdiskPerson.jsp?sid="+folderSid;
	bsWindow(url,"上传文件",{width:"650",height:"250",submit:function(v,h){
		var cw = h[0].contentWindow;
		cw.doSave();
	}});
}




/**
 * 批量删除文件
 */
function deleteFileFunc(){
	var ids = getSelectItem();
	if(ids.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
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
			window.location.reload();
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
	    <input type='button' id="uploadFileButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_shangchuan.png)" class='btn btn-one' value='上传' onclick='uploadFileFunc("<%=sid %>");' >
  		<input type='button' id="newFolderButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_xinjianwenjianjia.png)"  class='btn btn-one' value='新建文件夹' onclick='newFolderFunc("<%=sid %>");' >
   		<button type='button' id="downLoadFilesButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_xiazai.png)"  class='btn btn-one' value='文件下载' onclick='downLoadFilesShareFunc("<%=sid %>");' >&nbsp;下载</button>
   		<button type='button' style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/grwp/icon_shuaxin.png)" class='btn btn-one'  onclick="$('#datagrid').datagrid('unselectAll');$('#datagrid').datagrid('reload');">&nbsp;刷新</button>	
	</div>
	
</div>
</body>
</html>