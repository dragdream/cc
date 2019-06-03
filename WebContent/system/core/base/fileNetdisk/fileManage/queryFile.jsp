<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String folderId = request.getParameter("folderId");
if(folderId == null){
	folderId = "0";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>检索文件</title>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/upload.jsp"%>
<%@ include file="/header/userheader.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileManage.js"></script>
<style type="text/css">

	.modal-test{
		width: 500px;
		height: 230px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 50px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 16px;
		line-height:50px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 16px;
		line-height:50px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		width: 100%;
		height: 150px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 15px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.sp{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		/* float: left; */
		width: 330px;
		height: 25px;
		
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 60px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}



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
.returnButton{
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
<script type="text/javascript">
var sid = '<%=folderId%>';
function doInit(){
	//$('#searchDiv').modal('hide');
	setOptPriv(sid);
	getInfoList();
}
var rootFolderPriv="0";
function setOptPriv(sid){
	rootFolderPriv = getFilePrivValueBySid(sid);
	//alert("rootFolderPriv>>" + (rootFolderPriv));
	
	if((rootFolderPriv & 8)==8){//编辑
		/* $("#deleteFileButton").show();
		$("#copyFilesButton").show();
		$("#cutFilesButton").show(); */
	}
	if((rootFolderPriv & 2)==2){//下载
		/* $("#downLoadFilesButton").show(); */
	}
	if((rootFolderPriv & 32)==32){//上传
		/* $("#uploadFileButton").show();
		$("#newFolderButton").show(); */
	}
	if((rootFolderPriv & 4)==4){//删除
		/* $("#deleteFileButton").show(); */
	}
	if((rootFolderPriv & 64)==64){//管理
	/* 	$("#deleteFileButton").show();
		$("#copyFilesButton").show();
		$("#cutFilesButton").show();
		$("#downLoadFilesButton").show();
		$("#uploadFileButton").show();
		$("#newFolderButton").show(); */
	}
}

var datagrid;
function getInfoList(){
	var queryParams=tools.formToJson($("#form2"));
	datagrid = $('#datagrid').datagrid({
		url:contextPath+"/fileNetdisk/getManageInfoList.action?folderId=<%=folderId%>",
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:true,
		queryParams:queryParams,
		singleSelect:false,
		toolbar:'#toolbar',//工具条对象
		checkbox:true,
		border:false, 
		idField:'sid',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		pageSize : 10,
		/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
		columns:[[
			{field:'sid',checkbox:true,title:'ID',width:1},
			//{field:'parentFileSid',title:'parentFileSid',width:10},
			{field:'parentFileName',title:'文件夹路径',width:40,formatter:function(value, rowData, rowIndex){
// 				var str = "<a href='#' onclick='showInfoFunc("+rowData.parentFileSid+")'>" + value + " </a>";
				return value;
			}},
			{field:'fileName',title:'文件名称',width:40,formatter : function(value, rowData, rowIndex) {
				var fileExt = rowData.fileTypeExt;
				var fileTypeImg="";
				var fileNameStr = "" ;
				if(fileExt !="0"){
					fileNameStr = "<div class='fileItem' isSignRead='" + rowData.isSignRead +"' priv='" +rootFolderPriv + "' sid='" + rowData.attachSid +"' fileName='"+value+"' fileExt='"+fileExt+"' ></div>";
					if(rowData.isSignRead == '0'){
						fileNameStr = "<a href='javascript:void(0);' onclick='showContentFunc(\"" +rowData.sid + "\",\"" + rowData.parentFileSid + "\",\"" + rootFolderPriv + "\" );' title='点击进行签阅查看'><div class='fileItem' isSignRead='" + rowData.isSignRead +"' priv='" +rootFolderPriv + "' sid='" + rowData.attachSid +"' fileName='"+value+"' fileExt='"+fileExt+"' ></div></a>";
					}
				}else{
					fileTypeImg = "<img src='"+systemImagePath+"/filetype/folder.gif'>&nbsp;" + value ;
					fileNameStr = "<a href='#' onclick='openFolderFunc("+ rowData.sid + ");'>" + fileTypeImg + "</a>" ;
				}
				return "<span title='" + value + "'>" + fileNameStr  +"</span>";
			}},
			{field:'fileSize',title:'大小',width:20},
			{field:'createTimeStr',sortable : true,title:'修改日期',width:30,formatter:function(value, rowData, rowIndex) {
				if(rowData.notLogin && rowData.notLogin == '1'){
					return "<font color='gray'> " + value + "</font>";
				}else if(rowData.passwordIsNUll == '1'){
					return "<font color='red'> " + value + "</font>";
				}else{
					return value;
				}
			}},
			{field:'2',title:'操作',width:55,formatter:function(value, rowData, rowIndex){
				var optStr = "";
				var fileExt = rowData.fileTypeExt;
				//alert("rootFolderPriv>>" + rootFolderPriv + " &>>" +(rootFolderPriv & 8));
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
					if(rowData.createrId == loginPersonId || (rootFolderPriv & 64)==64){//上传者或者管理权限
						optStr += "<a href='javascript:void(0);' onclick='contentFunc(\"" +rowData.sid + "\",\"" + rowData.parentFileSid + "\",\"" + rootFolderPriv + "\");'>编辑</a>&nbsp;&nbsp;";
					}
						optStr += "<a href='javascript:void(0);' onclick='showContentFunc(\"" +rowData.sid + "\",\"" +rowData.parentFileSid + "\",\"" + rootFolderPriv + "\");'>详情 </a>&nbsp;";
					if(rowData.isSignRead !='1'){
						optStr += "<a href='javascript:void(0);' onclick='signReadFunc(\"" + rowData.sid + "\");'>签阅 </a>&nbsp;";
					}
					if((rootFolderPriv & 64)==64){
						optStr += "<a href='javascript:void(0);' onclick='showSignRead(\"" + rowData.sid + "\",\"" + rowData.parentFileSid + "\");'>查看签阅 </a>&nbsp;";
					}
				}
				
				return "<div align='center'>" + optStr + "</span>";
			}}
		]],onLoadSuccess:onLoadSuccessFunc
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
		$.MsgBox.Alert_auto("删除成功！");
		$("#datagrid").datagrid("reload");
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}



/**
 * 获取最大记录数
 */
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
	$("#totalPerson").empty();
	//改变列表样式左边线
	//$("#totalPerson").append( data.total );
   // $(".datagrid-view,.datagrid-pager").css({"border-left":'1px solid #d3d3d3'});
   // $(".datagrid-toolbar").css({"padding":"0px"});
    
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

function showInfoFunc(sid){
	location.href = contextPath + "/system/core/base/fileNetdisk/fileManage/fileManage.jsp?sid=" + sid;
}

function returnBack(){
	history.back();
}



function checkForm(){
	return true;
}
//根据条件查询
function doSubmit(){
	if(checkForm()){
		  var queryParams=tools.formToJson($("#form2"));
		  datagrid.datagrid('options').queryParams=queryParams; 
		  datagrid.datagrid("reload");
		  //关闭模态框
		  $(".modal-win-close").click();
	}
}

</script>
</head>
<body onload="doInit()" style="padding-right: 10px">
<table id="datagrid" fit="true"></table>

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<span class="title">检索文件</span>
	</div>
	<div class = "right fr clearfix ">
		<button class="returnButton" onclick="returnBack()">返回</button>
    </div>
	<div class = "right fr clearfix t_btns">
	    <button type="button" onclick="$(this).modal();" value="高级检索" class="modal-menu-test"
		style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_gaojijiansuo.png)"
		>&nbsp;高级查询</button>
    </div>
    
</div>


<!-- 模态框Modal -->
<form id="form2" name="form2">
<div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			查询条件
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
		    <li>
		        <span class='sp'>文件名：&nbsp;&nbsp;&nbsp;&nbsp;</span>
		        <input type="text" name="fileName" id="fileName"  style="margin-left: 20px" >
		    </li>
		    <li>
		        <span class='sp'>创建人：&nbsp;&nbsp;&nbsp;&nbsp;</span>
		        <input type=hidden name="createrId" id="createrId" value=""/>
				<input  type="text" name="createrName" id="createrName"  readonly value="" style="margin-left: 20px"/>
				<span class="addSpan">
				  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_select.png" onClick="selectSingleUser(['createrId', 'createrName']);" value="选择"/>
				   &nbsp;&nbsp;
				  <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_cancel.png" onClick="$('#createrId').val('');$('#createrName').val('');" value="清空"/>
				</span>
		    </li>
			<li class="clearfix">
				<span class='sp'>创建日期：</span>
				<input type="text" id="createTimeStrMin" name="createTimeStrMin"
				 style="width: 150px;margin-left: 22px;"
				 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeStrMax\')}'})" required value="" />
				 &nbsp;至 &nbsp;
				 <input type="text" id="createTimeStrMax" name="createTimeStrMax" 
				  style="width: 150px;"
				 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeStrMin\')}'})" required value=""/>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "btn-alert-gray" type="reset" value='重置' onclick=""/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doSubmit();" value = '查询'/>
	</div>
</div>
</form>
</body>
</html>