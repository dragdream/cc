<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<title>群组管理</title>
<style>
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8; 
}

	.modal-test{
		width: 500px;
		height: 210px;
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
		height: 100px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-body ul{
		overflow: hidden;
		clear:both;
	}
	.modal-test .modal-body ul li{
		width: 510px;
		height: 30px;
		line-height: 30px;
		margin-top: 25px;
		margin-left: 20px;
	}
	.modal-test .modal-body ul li span{
		display: inline-block;
		float:left;
		vertical-align: middle;
	}
	.modal-test .modal-body ul li input{
		display: inline-block;
		float: left;
		width: 400px;
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

</style>		
	
<script type="text/javascript" >
	
function doInit(){
	var url = "<%=contextPath %>/teeAddressGroupController/getPublicAddressGroups2Priv.action";
	//var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var rtData = jsonRs.rtData;
		var targetDom = $("#tbody");
		var trTem = '<tr>'+
			'<td nowrap class="TableData">{groupName}</td>'+
			'<td nowrap class="TableData">'+
			'<a onclick="emptyById({seqId})" href="javascript:void(0)">清空</a>&nbsp; '+
			'<a class="modal-menu-test" href="javascript:void();" onclick="$(this).modal();importAddress({seqId})">导入</a>&nbsp; '+
			'<a onclick="exportAddressVcf({seqId})" href="#">导出VCF格式</a>&nbsp; '+
			'<a onclick="exportAddressXls({seqId})" href="#">导出CSV格式</a> '+
			'</td>'+
		'</tr>';
		var trTem0 = '<tr>'+
		'<td nowrap class="TableData">{groupName}</td>'+
		'<td nowrap class="TableData">'+
		'<a onclick="emptyById({seqId})" href="javascript:void(0)">清空</a>&nbsp; '+
		'<a class="modal-menu-test" href="javascript:void();" onclick="$(this).modal();importAddress({seqId})">导入</a>&nbsp; '+
		'<a onclick="exportAddressVcf({seqId})" href="#">导出VCF格式</a>&nbsp; '+
		'<a onclick="exportAddressXls({seqId})" href="#">导出CSV格式</a> '+
		'</td>'+
	'</tr>';
		var liArray = new Array();
		if(rtData){
			$.each(rtData,function(key, val){
				if(key !=0){
					var str = FormatModel(trTem,val);
					liArray.push(str);
				}else{
					var str = FormatModel(trTem0,val);
					liArray.push(str);
				}
			});
		}
		targetDom.append(liArray.join(''));
		
		//$("#tbody").append(groupStr);
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	}
}

/**
 * 跳转去新增页面
 */
function toAddUpdate()
{
	window.location.href = "<%=contextPath%>/system/core/base/address/public/group/addGroup.jsp";
}
/**
 * zhp 20130108 删除通讯组
 */
function deleteById(id) {
	$.MsgBox.Confirm ("提示", "确定删除所选记录,删除后将不可恢复！", function(){
		var url = "<%=contextPath %>/teeAddressGroupController/delAddressGroups.action?isPub=1";
		var jsonRs = tools.requestJsonRs(url,{"groupId":id});
		if(jsonRs.rtState){
			 $.MsgBox.Alert_auto("删除成功！");
			window.location.reload();
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
    });
}

/**
* zhp 20130108 清空通讯组
*/
function emptyById(id) {
	
	 $.MsgBox.Confirm ("提示", "确定清空当前通讯簿组,清空后将不可恢复！", function(){
		 var url = "<%=contextPath %>/teeAddressGroupController/emptyAddressGroups.action?isPub=1";
	   		var jsonRs = tools.requestJsonRs(url,{"groupId":id});
	   		if(jsonRs.rtState){
	   			$.MsgBox.Alert_auto("清空成功！");
	   		}else{
	   			$.MsgBox.Alert_auto("清空失败");
	   		}
	  });
}

/**
 * 跳转去编辑页面
 */
function toEdit(id)
{
	window.location.href = "<%=contextPath%>/system/core/base/address/private/group/editGroup.jsp?id=" + id;
}

/**
* 导出通讯薄
*/
function exportAddressVcf(id){
	var url = "<%=contextPath%>/teeAddressController/exportAddressVcf.action?isPub=1&groupId="+id;
	window.location.href = url;
}

function exportAddressXls(id){
	var url = "<%=contextPath%>/teeAddressController/exportAddressXls.action?isPub=1&groupId="+id;
	window.location.href = url;
}

function importAddress(id){
	var url = "<%=contextPath%>/teeAddressController/importAddress.action?isPub=1&groupId="+id;
	$("#uploadForm").attr("action",url);
}

function doImport(obj){
	if(document.getElementById("file").value.indexOf(".csv")==-1 && document.getElementById("file").value.indexOf(".vcf")==-1){
		$.MsgBox.Alert_auto("仅能上传csv和vcf后缀名的文件！");
		return false;
	}
	$("#uploadBtn").attr("value","上传中").attr("disabled","");
	return true;
}

function uploadSuccess(){
	
	$("#uploadBtn").attr("value","上传").removeAttr("disabled");
	$("#uploadForm")[0].reset();
	/* //隐藏模态框
	$(".modal-test").hide(); */
	//window.location.reload();
	$(".modal-win-close").click();
	$.MsgBox.Alert_auto("导入成功！");	
}
</script>
</head>
<body onload="doInit()" style="padding-right: 10px;padding-left:10px">
   <div id="toolbar" class="topbar clearfix">
      <div class="fl left">
         <span style="font-size: 14px;font-weight: bold;">群组列表</span>
      </div>
   </div>

   <div style="padding-top:10px;">   
   <table  class="" width="90%" align="center" id="tbody">
  	
  	   <tr  class="TableHeader" >
         	<td width="50%">分组名称</td>
            <td > 操作</td>
        </tr>
   </table>
  </div>
  
<!-- Modal模态框 -->
<form id="uploadForm" onsubmit="return doImport()" target="frame" action="/teeAddressController/importAddress.action?isPub=0&groupId" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			导入
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
			<li class="clearfix">	  
			  <span style="color:red;">1.导入的格式为*.csv或*.vcf，且必须为通用标准格式文件</span><br>
			  <input style="border:0px;" type="file" name="file" id="file" />
			  <iframe id="frame" name="frame" style="display:none" src="" ></iframe>
			</li>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
		<input class = "modal-save btn-alert-blue" type="submit"   id="uploadBtn"  value = '上传'/>
	</div>
</div>
</form> 
</body>
</html>