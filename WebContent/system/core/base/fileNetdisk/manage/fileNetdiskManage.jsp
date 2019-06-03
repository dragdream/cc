<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%
TeePerson person = (TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公共网盘设置管理</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/core/base/fileNetdisk/dialog/css/dialog.css"/>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/dialog/js/dialog.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileUserPriv.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/deptPriv.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/fileNetdisk/js/fileRolePriv.js"></script>
<script type="text/javascript">

function doInit(){
  getFileNetdiskList();
}

/* 获取网络硬盘信息 */
function getFileNetdiskList(){
  var url = "<%=contextPath %>/fileNetdisk/getFileNetdiskList.action";
  var jsonObj = tools.requestJsonRs(url);
  if(jsonObj.rtState){
    var json = jsonObj.rtData;
    jQuery.each(json,function(i,sysPara){
      $("#tbody").append("<tr class='TableData'>"
					+"<td nowrap align='center' style='width:10%'>" + sysPara.fileNo+ "</td>"
					+"<td nowrap align='center' style='width:40%'>" + sysPara.fileName + "</td>"
					+"<td nowrap align='center' style='width:30%'>"
					 //+"<a href='javascript:setPriv(\"" + sysPara.sid  + "\")'>权限设置</a>"
					 +"<a href='javascript:setPrivObj(\"" + sysPara.sid  + "\")'>权限设置</a>"
					 +"&nbsp;&nbsp;<a href='javascript:editObj(\"" + sysPara.sid  + "\")'>修改</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteObj(\"" + sysPara.sid + "\")'>删除</a>"
					 +"&nbsp;&nbsp;<a href='javascript:addMenuFunc(\"" + sysPara.sid + "\",\"" + sysPara.fileName + "\")'>菜单定义指南</a>"
					 +"</td>"
		  	+ "</tr>"); 
		 	//alert(123);
		 	$("#sysPara-child-a-" + sysPara.uuid).bind("click",function(){
		 		toMenu(sysPara.uuid);
			});
    });
  }
}
/* 编辑 */
function editObj(sid){
  if(sid){
    createFileNetdisk("编辑文件网盘");
    var url = "<%=contextPath %>/fileNetdisk/getFileNetdiskById.action";
    var para = {sid:sid};
    var jsonObj = tools.requestJsonRs(url,para);
    if(jsonObj){
      var json = jsonObj.rtData;
      if(json.sid){
        bindJsonObj2Cntrl(json);
      }
    }
  }
}
/* 删除 */
function deleteObj(sid){
  if(sid){
	  $.MsgBox.Confirm ("提示", "是否确认删除该数据？", function(){
		  var url = "<%=contextPath %>/fileNetdisk/delFileNetdiskByIds.action";
	      var para = {sid:sid};
	      var jsonRs = tools.requestJsonRs(url,para);
	      if(jsonRs.rtState){
	        $.MsgBox.Alert_auto(jsonRs.rtMsg,function(){
	        	window.location.reload();
	        });
	      }else{
	    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
	      } 
	  });
  }
}
/* 新建文件夹 */
function createFileNetdisk(titleStr){
  var html = "<br><form method='post' name='form1' id='form1'>"
           +    "<div style='margin-left:10px'>文件排序号："
           +     "<input type='hidden' name='sid' id='sid' value=''><input type='text' name='fileNo' id='fileNo' style='width:240px;height:20px;margin-left:15px'>"
           +   "</div>"
           +   "<div style='margin-left:10px;margin-top:5px'>文&nbsp;件&nbsp;名&nbsp;称："
           +     "<input type='text' name='fileName' id='fileName' class='' style='width:240px;height:20px;margin-left:15px'/>"
           +   "</div>"
           +   "<div align='right' style='margin-top:20px;margin-right:10px'>"
           +       "<input type='button' value='保存' class='btn-alert-blue' onclick='doSave();'>&nbsp;&nbsp;"
           +       "<input type='button' value='关闭' class='btn-alert-gray' onclick='$("+'"#win_ico"'+").click();'>&nbsp;&nbsp;"
           +   "</div>"
           + "</form>";
  
 // $.jBox(html, { title: titleStr, width:500,height:300,buttons:{} });
  bsWindow(html,titleStr,{width:380,height:90,buttons:[]},"html");
}


/* 校验数据 */
function checkForm(){
  if($("#fileNo").val() && !isIntegeOrZero($("#fileNo").val())){
    //$.jBox.tip("请输入整数！");
    $.MsgBox.Alert_auto("请输入整数！");
    $("#fileNo").focus();
    return false;
  }
  if(!$("#fileName").val()){
    //$.jBox.tip("请输入文件名称！");
    $.MsgBox.Alert_auto("请输入文件名称！");
    $("#fileName").focus();
    return false;
  }
  if(isValidateFilePath($("#fileName").val())){
	  $.MsgBox.Alert_auto("名称不能包含有以下字符/\:*<>?\"|");
    $("#fileName").focus();
    return false;
  }
  return true;
}
/**
 * 新建或编辑文件夹
 */
function doSave(){
  if(checkForm()){
    var url = "<%=contextPath %>/fileNetdisk/addOrUpdateFileNetdisk.action";
    var para =  tools.formToJson($("#form1"));
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){	
    	$("#win_ico").click();
    	$.MsgBox.Alert_auto("操作成功！",function(){		
    		window.location.reload();
    	});
        
       
    }else{
    	$.MsgBox.Alert_auto(jsonRs.rtMsg);
    }
  }
}


/**
 * 设置权限
 */
function setPriv(sid){
  $("#fileFolderSid").val(sid);
  /* 显示权限div */
  ShowDialog('detail');
  /* 人员权限初始化 */
  doInitUserPrivFunc(sid);
  /* 部门权限初始化 */
  doInitDeptPrivFunc(sid);
  /* 角色权限初始化 */
  doInitRolePrivFunc(sid);
}

/**
 * 菜单自定义
 */
function addMenuFunc(sid,menuName){
  var childMenuName = menuName;
  var menuURL = "/system/core/base/fileNetdisk/fileManage/index.jsp?sid=" + sid;
  var url = contextPath + "/system/core/menu/addupdatechild.jsp?childMenuName=" + encodeURIComponent(childMenuName) + "&menuURL=" + encodeURIComponent(menuURL);
  bsWindow(url ,"菜单定义指南",{width:"700",height:"300",buttons:
     [//{name:"关闭",classStyle:"btn btn-primary"}
     ]
  ,submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="修改"){
      
    }else if(v == "删除"){
      
    }else if(v=="关闭"){
      return true;
    }
  }});
}


/* 编设置权限*/
function setPrivObj(sid){
  if(sid){
    var url = "<%=contextPath %>/system/core/base/fileNetdisk/manage/setPriv/setPrivIndex.jsp?sid=" + sid;
//     $.jBox("iframe:" + url, { title: "权限设置", width:800,height:450,buttons:{} });  
    bsWindow(url ,"权限设置",{width:"860",height:"350",buttons:
        [//{name:"关闭",classStyle:"btn btn-primary"}
        ]
     ,submit:function(v,h){
//        var cw = h[0].contentWindow;
//        if(v=="修改"){
         
//        }else if(v == "删除"){
         
//        }else if(v=="关闭"){
//          return true;
//        }
     }});
  }
}





</script>
<style>
	.t_btns>button{
		padding:5px 8px;
		/* padding-left:22px; */
		text-align:right;
		/* background-repeat:no-repeat;
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
	
	
.TableList{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
.TableList tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
.TableList  tr td:first-child{
	text-indent:10px;
}
.TableList tr:first-child td{
	font-weight:bold;
}
.TableList  tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}

</style>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid"></table>

<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggwp/icon_wangpanshezhi.png">
		<span class="title">公共网盘设置</span>
	</div>
	<div class = "right fr clearfix t_btns">
	    <button onclick="createFileNetdisk('新建文件网盘');" class="" type = "button" value = "新建文件网盘">新建文件网盘</button>
    </div>
</div>

   <table class="TableList" width="80%" align="center" style="margin-bottom: 10px" >
      <tbody id="tbody">
        <tr class="TableHeader">
      		<td nowrap align="center" style='width:10%'>文件排序号</td>
     	    <td nowrap align="center" style='width:40%'>文件名称</td>
      		<td nowrap align="center" style='width:30%'>操作</td>
       </tr>
      </tbody>
   </table>
</body>
</html>