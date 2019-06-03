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
<%@ include file="/header/userheader.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>文件管理</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileManage.js?v=2"></script>
<style type="text/css">
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
.downButton{
        padding:5px 8px;
		/* padding-left:22px; */
		text-align:center; 
		/* text-align:right; 
		background-repeat:no-repeat;
		background-position:6px center; */
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
<script type="text/javascript" charset="UTF-8">

var sid = '<%=sid%>';
var datagrid;
var userForm;
var title ="";
var rootFolderPriv = "0";

function doInit(){
  if(sid !=0){
    if(setOptPriv(sid)!=0){
    	getFileData(sid);
    }
  }else{
    $("#easyuiDiv").hide();
    $(".panel").css("border","0px");
    messageMsg("没有相关文件", "listDiv" ,'' ,380);
  }
  
  /* $("#uploadFileButton").bingUpload({uploadSuccess:function(fileInfo){
	  rootFolderPriv = getFilePrivValueBySid(sid);
	  var url = contextPath+"/fileNetdisk/uploadNetdiskFile.action";
	  var isRemind="0";
	  if($("#isRemind").attr("checked")=="checked"){
		  isRemind="1"; 
	  }
	  
	  var para = {sid:sid,valuesHolder:fileInfo.sid,rootFolderPriv:rootFolderPriv,isRemind:isRemind};
	  var jsonRs = tools.requestJsonRs(url,para);
	  
	},queueComplele:function(){
		window.location.reload();
	},model:"fileNetdisk",html:"<br><input type='checkbox' id='isRemind'  />&nbsp;&nbsp;提醒所有有权限人员"}); */
}

function setOptPriv(sid){
	rootFolderPriv = getFilePrivValueBySid(sid);
	if(rootFolderPriv==0){
		messageMsg("您没有浏览该文件夹的权限","body","info");
		return 0;
	}
	
	if((rootFolderPriv & 8)==8){//编辑
		$("#deleteFileButton").show();
		$("#copyFilesButton").show();
		$("#cutFilesButton").show();
		$("#pasteFilesButton").show();
	}
	if((rootFolderPriv & 2)==2){//下载
		$("#downLoadFilesButton").show();
	}
	if((rootFolderPriv & 32)==32){//上传
		$("#uploadFileButton").show();
		$("#newFolderButton").show();
	}
	if((rootFolderPriv & 4)==4){//删除
		$("#deleteFileButton").show();
	}
	if((rootFolderPriv & 64)==64){//管理
		$("#deleteFileButton").show();
		$("#copyFilesButton").show();
		$("#cutFilesButton").show();
		$("#downLoadFilesButton").show();
		$("#uploadFileButton").show();
		$("#newFolderButton").show();
		$("#setPrivButton").show();
		$("#pasteFilesButton").show();
	}
}

function getFileData(sid){
	//获取文件夹目录级别完整路径
	getFolderPathFunc(sid);
	
	/* userForm = $('#userForm').form(); */
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/fileNetdisk/getFileNetdiskPage.action?folderSid=' + sid,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		title : title,
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
			field : 'fileName',
			title : '名称',
			width : 230,
			sortable : true,
			formatter : function(value, rowData, rowIndex) {
				var fileExt = rowData.fileTypeExt;
				var fileTypeImg="";
				var fileNameStr = "" ;
				if(fileExt !="0"){
				  	fileNameStr = "<div class='fileItem' isSignRead='" + rowData.isSignRead +"' priv='" +rootFolderPriv + "' sid='" + rowData.attachSid +"' fileName='"+value+"' fileExt='"+fileExt+"' ></div>";
					if(rowData.isSignRead == '0'){
						fileNameStr = "<a href='javascript:void(0);' onclick='showContentFunc(\"" +rowData.sid + "\",\"" + <%=sid%> + "\",\"" + rootFolderPriv + "\" );' title='点击进行签阅查看'><div class='fileItem' isSignRead='" + rowData.isSignRead +"' priv='" +rootFolderPriv + "' sid='" + rowData.attachSid +"' fileName='"+value+"' fileExt='"+fileExt+"' ></div></a>";
					}
				}else{
					fileTypeImg = "<img src='"+contextPath+"/common/zt_webframe/imgs/zsjl/ggwp/icon_wjj1.png'>&nbsp;" + value ;
					fileNameStr = "<a href='#' onclick='openFolderFunc("+ rowData.sid + ");'>" + fileTypeImg + "</a>" ;
				}
				return "<span title='" + value + "'>" + fileNameStr  +"</span>";
			}
		} ] ],
		columns : [ [  {
			field : 'fileSize',
			title : '大小',
			width : 110,
			sortable : false,
			formatter : function(value, rowData, rowIndex) {
			  if(value){
			    return value;
			  }else{
			    return "-";
			  }
			}
		 } ,  {
			field : 'createTimeStr',
			title : '修改日期',
			width : 130,
			sortable : true,
			formatter : function(value, rowData, rowIndex) {
				if(rowData.notLogin && rowData.notLogin == '1'){
					return "<font color='gray'> " + value + "</font>";
				}else if(rowData.passwordIsNUll == '1'){
					return "<font color='red'> " + value + "</font>";
				}else{
					return value;
				}
			}
		 } ,  {
			field : '_optmanage',
			title : '操作',
			width : 200,
			formatter : function(value, rowData, rowIndex) {
				
				var optStr = "";
				var fileExt = rowData.fileTypeExt;
				var editOpt = "<a href='javascript:void(0);'  onclick='reNameFunc(\"" +rowData.sid + "\",\"" + fileExt + "\");'>重命名 </a>&nbsp;";
				var deleteOpt = "<a href='javascript:void(0);' onclick='deleteSingleFileFunc(" +rowData.sid + ");'>删除</a>&nbsp;&nbsp;";
				if((rootFolderPriv & 8)==8){
				  optStr = editOpt;
				}
				if((rootFolderPriv & 4)==4){
				  optStr += "<a href='javascript:void(0);' onclick='deleteSingleFileFunc(" +rowData.sid + ");'>删除</a>&nbsp;";
				}
				if((rootFolderPriv & 64)==64){
				  optStr = editOpt + deleteOpt;
				}
				
				if(rowData.filetype == '1' ){//文件
					optStr += "<a href='javascript:void(0);' onclick='showContentFunc(\"" +rowData.sid + "\",\"" + <%=sid%> + "\",\"" + rootFolderPriv + "\");'>详情 </a>&nbsp;";
				<%-- 	if(rowData.createrId == loginPersonId || (rootFolderPriv & 64)==64){//上传者或者管理权限
						optStr += "<a href='javascript:void(0);' onclick='contentFunc(\"" +rowData.sid + "\",\"" + <%=sid%> + "\",\"" + rootFolderPriv + "\");'>编辑</a>&nbsp;&nbsp;";
					}
						optStr += "<a href='javascript:void(0);' onclick='showContentFunc(\"" +rowData.sid + "\",\"" + <%=sid%> + "\",\"" + rootFolderPriv + "\");'>详情 </a>&nbsp;";
					if(rowData.isSignRead !='1'){
						optStr += "<a href='javascript:void(0);' onclick='signReadFunc(\"" + rowData.sid + "\");'>签阅 </a>&nbsp;";
					}
					if((rootFolderPriv & 64)==64){
						optStr += "<a href='javascript:void(0);' onclick='showSignRead(\"" + rowData.sid + "\",\"" + <%=sid%> + "\");'>查看签阅 </a>&nbsp;";
					} --%>
				}
				/* if((rootFolderPriv & 64)==64){//有管理权限  可以查看历史记录
					  optStr +="<a href='javascript:void(0);' onclick='viewHistory(\"" + rowData.sid + "\");'>历史记录</a>&nbsp;";
					} */
				return "<div align='center'>" + optStr + "</span>";
			} 
	 } ] ],onLoadSuccess:onLoadSuccessFunc
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
 * 获取最大记录数
 */
function onLoadSuccessFunc(data){
// 	var data=$('#datagrid').datagrid('getData');
// 	$("#totalPerson").empty();
	//改变列表样式左边线
	//$("#totalPerson").append( data.total );
//     $(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'});
//     $(".datagrid-toolbar").css({"padding":"0px"});
    
//     if (data.rows.length > 0) {
//         //循环判断操作为新增的不能选择
//         for (var i = 0; i < data.rows.length; i++) {
//             //根据operate让某些行不可选
//             if (data.rows[i].filetype==0) {
//                 $("input[type='checkbox']")[i + 1].disabled = true;
//                 $("input[type='checkbox']")[i + 1].style.display = "none";
//             }
//         }
//     }
    
    //处理悬浮菜单
    $(".fileItem").each(function(i,obj){
      var priv = parseInt($(obj).attr("priv"));
      if($(obj).attr("isSignRead") == '0'){//未签阅
    	  priv = 0;
      }
      //alert(priv);
      if((priv & 4) == 4){//去掉删除浮动菜单
    	  priv-=4;
      } 
      var attachModel = {fileName:$(obj).attr("fileName"),
          priv:priv
          ,ext:$(obj).attr("fileExt"),sid:$(obj).attr("sid")};
      var fileItem = tools.getAttachElement(attachModel,{});
      $(obj).append(fileItem);
    });
}




	
/**
 * 批量删除文件
 */
function deleteFileFunc(){
	var selections = $('#datagrid').datagrid('getSelections');
	if(selections.length==0){
		$.MsgBox.Alert_auto("至少选择一项");
		return ;
	}
	
	 $.MsgBox.Confirm ("提示", "确定要删除所选中文件？", function(){
		 var ids = "";
			for(var i=0;i<selections.length;i++){
				ids+=selections[i].sid;
				if(i!=selections.length-1){
					ids+=",";
				}
			}
			deleteFileNetdiskFunc(ids);
	  });
}
/**
 * 单个删除文件
 */
function deleteSingleFileFunc(sid){
	$.MsgBox.Confirm ("提示", "确定要删除所选中文件？", function(){
		deleteFileNetdiskFunc(sid);
	  });
}
/**
 * 批量删除文件
 */
function deleteFileNetdiskFunc(ids){
  var url = contextPath +  "/fileNetdisk/deleteFileBySid.action?sids=" + ids;
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		$.MsgBox.Alert_auto("删除成功!");
		$("#datagrid").datagrid("reload");
		parent.frames["file_tree"].deledteZTreeNode(ids);  
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

 /**
  * 上传文件
  */
 function uploadFileFunc(folderSid){
 	var url = contextPath+"/system/core/base/fileNetdisk/fileManage/uploadNetdiskFile.jsp?sid="+folderSid;
 	bsWindow(url,"上传文件",{width:"650",height:"250",
 		buttons:[],
 		submit:function(v,h){
 		  /*  var cw = h[0].contentWindow;
 		   cw.doSave(); */
 		}
 	});
 	
 }



function queryFileFunc(){
	location.href  = contextPath + "/system/core/base/fileNetdisk/fileManage/queryFile.jsp?folderId=<%=sid%>";
}

function setPrivObj(){
	var url = "<%=contextPath %>/system/core/base/fileNetdisk/manage/setPriv/setPrivIndex.jsp?sid=<%=sid%>";
    bsWindow(url ,"权限设置",{width:"860",height:"350",buttons:
        [
        ]
     ,submit:function(v,h){
     }});
}


</script>
</head>

<body onload="doInit();" style="margin:0px;padding-right:10px;" id="body">
<table id="datagrid" fit="true"></table>
<div id="toolbar" class = "topbar clearfix" style='border-bottom:none!important;'>
    <div style="font-weight:bold;border-bottom:2px solid #74c5ff;">
       <span id="previousSpan" ></span>
	   <span id="folderPathSpan"></span>
    </div>
	<div class="fl clearfix t_btns" style="margin-top: 5px;">	
	    <button type='button' id="downLoadFilesButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_xiazai.png)"  onclick='downLoadFilesFunc("<%=sid %>");' >&nbsp;下载</button>
 	    <button type='button' id="uploadFileButton" style="display: none;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_shangchuan.png)"  value='上传' onclick='uploadFileFunc("<%=sid %>");'>&nbsp;上传</button>
 	    <button type='button'   style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_jiansuo.png)"  onclick="queryFileFunc();">&nbsp;检索文件</button>
	    <div class="btn-group " style="display: inline-block;vertical-align: top;">
		  <button type="button" class="downButton btn-menu">
		      操作<span style='margin-top:6px;' class="caret-down"></span>
		  </button>
		  <ul class="btn-content">
		  	<li id="" onclick="$('#datagrid').datagrid('unselectAll');$('#datagrid').datagrid('reload');"><a href="#">刷新</a></li>
		   <!--  <li role="separator" class="divider"></li> -->
		    <li id="copyFilesButton" style="display: none;" onclick="copyFunc(<%=sid %>)"><a href="#">复制</a></li>
		    <li id="cutFilesButton" style="display: none;" onclick="batchCutFunc(<%=sid %>)"><a href="#">剪切</a></li>
		    <li id="deleteFileButton" onclick="deleteFileFunc()" style="display: none;"><a href="#">删除</a></li>
		   <!--  <li role="separator" class="divider"></li> -->
		    <li id="newFolderButton" onclick='newFolderFunc("<%=sid %>");' style="display: none;"><a href="#">新建文件夹</a></li>
		  	<li id="setPrivButton" onclick='setPrivObj()' style="display: none;"><a href="#">设置权限</a></li>
		  </ul>
		</div>
	</div>
	<div class="fr clearfix" style="margin-top:15px;">
	    <img src="<%=systemImagePath %>/favorite_click.png" class="favStyle" title="添加收藏夹" id="addFav"/>
	</div>
	<div>
		<div region="center" border="false" style="overflow: hidden;" id="easyuiDiv"></div>
	</div>
</div>
<div id="listDiv"></div>
</body>

</html>