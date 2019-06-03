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
#timesTips{
	color:#ffa800;
	font-weight:bold;
	margin-left:5px;
}
.time_info{
	line-height:30px;
	font-weight:bold;
}
.info img,.info span{
	vertical-align:middle;
}
table{
	border-collapse: collapse;
/*     border: 1px solid #f2f2f2; */
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
	/*font-weight:bold;*/
}
table tr:first-child{
	/* background-color: #f8f8f8;  */
}
table .TableHeader{
	border-bottom:2px solid #b0deff;
}
table tbody tr{
   background-color: white;
}



.modal-test {
	width: 564px;
	position: absolute;
	display: none;
	z-index: 999;
}

.modal-test .modal-header {
	width: 100%;
	height: 50px;
	background-color: #8ab0e6;
}

.modal-test .modal-header .modal-title {
	color: #fff;
	font-size: 16px;
	line-height: 50px;
	margin-left: 20px;
	float: left;
}

.modal-test .modal-header .modal-win-close {
	color: #fff;
	font-size: 16px;
	line-height: 50px;
	margin-right: 20px;
	float: right;
	cursor: pointer;
}

.modal-test .modal-body {
	width: 100%;
	background-color: #f4f4f4;
}

.modal-test .modal-body ul {
	overflow: hidden;
	clear: both;
}

.modal-test .modal-body ul li {
	width: 510px;
	height: 30px;
	line-height: 30px;
	margin-top: 25px;
	margin-left: 20px;
}

.modal-test .modal-body ul li span {
	display: inline-block;
	float: left;
	vertical-align: middle;
}

.modal-test .modal-body ul li input {
	display: inline-block;
	float: right;
	width: 400px;
	height: 25px;
}

.modal-test .modal-footer {
	width: 100%;
	height: 60px;
	background-color: #f4f4f4;
}

.modal-test .modal-footer input {
	margin-top: 12px;
	float: right;
	margin-right: 20px;
}
</style>	
<script type="text/javascript" >
	
function doInit(){
	var url = "<%=contextPath %>/teeAddressGroupController/getAddressGroups.action";
	//var para =  tools.formToJson($("#form1")) ;
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var rtData = jsonRs.rtData;
		var targetDom = $("#tbody");
		var trTem = '<tr>'+
			'<td nowrap class="TableData">{groupName}</td>'+
			'<td nowrap class="TableData">'+
			'<a  onclick="toEdit({seqId})" href="#">编辑</a> '+
			'<a href="#" onclick="deleteById({seqId})" href="#">删除</a> '+
			'<a href="#" onclick="emptyById({seqId})">清空</a> '+
			'<a href="javascript:void();" class="modal-menu-test" onclick="$(this).modal(); importAddress({seqId})">导入</a> '+
			'<a href="javascript:void();" onclick="exportAddressVcf({seqId})">导出VCF格式</a> '+
			'<a href="javascript:void();" onclick="exportAddressXls({seqId})">导出CSV格式</a> '+
			'</td>'+
		'</tr>';

		var trTem0 = '<tr>'+
		'<td nowrap class="TableData">{groupName}</td>'+
		'<td nowrap class="TableData">'+
		'<a href="javascript:void();" onclick="emptyById({seqId})">清空</a> '+
		'<a href="javascript:void();" class="modal-menu-test" onclick="$(this).modal(); importAddress({seqId})">导入</a> '+
		'<a href="javascript:void();" onclick="exportAddressVcf({seqId})">导出VCF格式</a> '+
		'<a href="javascript:void();" onclick="exportAddressXls({seqId})">导出CSV格式</a> '+
		'</td>'+
		'</tr>';
		
		var trTem2 = '<tr>'+
		'<td nowrap class="TableData">{groupName}<font color="red">(公共)</font></td>'+
		'<td nowrap class="TableData">'+
		'<a href="javascript:void();" onclick="exportAddressVcf({seqId})">导出VCF格式</a> '+
		'<a href="javascript:void();" onclick="exportAddressXls({seqId})">导出CSV格式</a> '+
		'</td>'+
		'</tr>';
		var liArray = new Array();
		if(rtData){
			$.each(rtData,function(key, val){
				var userId = val.userId;
				if(key !=0  ){//第一个和公共的
					if(userId){//不是公共的
						 str = FormatModel(trTem,val);
					}else{
						 str = FormatModel(trTem2,val);
					}
					
					liArray.push(str);
				}else{
					var str = FormatModel(trTem0,val);
					liArray.push(str);
				}
			});
		}
		targetDom.html(liArray.join(''));
		
		//$("#tbody").append(groupStr);
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 跳转去新增页面
 */
function toAddUpdate()
{
	window.location.href = "<%=contextPath%>/system/core/base/address/private/group/addGroup.jsp";
}
/**
 * zhp 20130108 删除通讯组
 */
function deleteById(id) {
    var submit = function () {
       delSigle(id);
        
    };
  var delSigle = function ()
    {
    		var url = "<%=contextPath %>/teeAddressGroupController/delAddressGroups.action";
    		var jsonRs = tools.requestJsonRs(url,{"groupId":id});
    		if(jsonRs.rtState){
    			 //top.$.jBox.tip("删除成功！");
				 $.MsgBox.Alert_auto("删除成功！");
    			window.parent.location.reload();
    		}else{
    			//alert(jsonRs.rtMsg);
				$.MsgBox.Alert_auto(jsonRs.rtMsg);
    		}
    };
    //jBox.confirm("确定删除所选记录,删除后将不可恢复！", "确认删除？", submit, { id:'hahaha', showScrolling: false, buttons: { '确定': true, '取消': false } });
	$.MsgBox.Confirm("提示", "确定删除所选记录,删除后将不可恢复！", submit);
}
/**
* zhp 20130108 清空通讯组
*/
function emptyById(id) {
   var submit = function () {   
    	   emptySigle(id);    
   };
 var emptySigle = function ()
   {
   		var url = "<%=contextPath %>/teeAddressGroupController/emptyAddressGroups.action";
   		var jsonRs = tools.requestJsonRs(url,{"groupId":id});
   		if(jsonRs.rtState){
   			 //top.$.jBox.tip("清空成功！");
			 debugger;
			 $.MsgBox.Alert_auto("清空成功！");
   		}else{
   			//alert("清空失败");
			$.MsgBox.Alert_auto("清空失败");
   		}
   };
   //jBox.confirm("", "确认清空？", submit, { id:'hahaha12', showScrolling: false, buttons: { '确定': true, '取消': false } });
   $.MsgBox.Confirm("提示", "确定清空当前通讯簿组,清空后将不可恢复！",submit);
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
	var url = "<%=contextPath%>/teeAddressController/exportAddressVcf.action?&groupId="+id;
	window.location.href = url;
}

function exportAddressXls(id){
	var url = "<%=contextPath%>/teeAddressController/exportAddressXls.action?&groupId="+id;
	window.location.href = url;
}

function importAddress(id){
	var url = "<%=contextPath%>/teeAddressController/importAddress.action?isPub=0&groupId="+id;
	//$("#uploadDiv").modal("show");
	$("#uploadForm").attr("action",url);
}

function doImport(obj){
	if(document.getElementById("file").value.indexOf(".csv")==-1 && document.getElementById("file").value.indexOf(".vcf")==-1){
		//alert("仅能上传csv和vcf后缀名的文件！");
		$.MsgBox.Alert_auto("仅能上传csv和vcf后缀名的文件！");
		return false;
	}
	$("#uploadBtn").attr("value","上传中").attr("disabled","");
	return true;
}

function uploadSuccess(){
	$.MsgBox.Alert_auto("导入成功！");
	//$("#uploadDiv").modal("hide");
	$("#uploadBtn").attr("value","上传").removeAttr("disabled");
	$("#uploadForm")[0].reset();
}

</script>
</head>

<body onload="doInit()" style="overflow:hidden;padding-left:10px;padding-right:10px;box-sizing:border-box;">
<div class="topbar clearfix" style='padding:0!important;border-bottom:none!important;'>
	<table style="width:100%;">
		<tr>
			<td style='text-indent: 0px!important;'>
				<b><i class="glyphicon glyphicon-sound-stereo"></i>&nbsp;管理分组</b>
			</td>
			<td align=right>
				<input type='button' value='新增分组' class='btn-win-white fr' onclick='toAddUpdate();' style="margin-right:70px;margin-top:6px;"/>
				&nbsp;&nbsp;
			</td>
		</tr>
	</table>
	<span class='basic_border' style="margin-top:0;"></span>
</div>

<div class="time_info">

<table  class="TableBlock" width="90%" align="center" >
	 <thead>
	   <tr  class="TableHeader" >
       	<td width="50%"><b>分组名称</b></td>
          <td ><b>操作</b></td>
      </tr>
	 </thead>
    <tbody id="tbody">
    
 
    
    </tbody>
 </table>

</div>

</div>


<form id="uploadForm" onsubmit="return doImport()" target="frame" action="/teeAddressController/importAddress.action?isPub=0&groupId" name="uploadForm" method="post" enctype="multipart/form-data" >
<div class="modal-test" id="uploadDiv">
      <div class="modal-header">
        <p class="modal-title">导入</p>
		<span class="modal-win-close">×</span>
      </div>
      <div class="modal-body">
        <!-- 导入专用 -->
        	<span style="color:red;margin-left:15px;margin-top:150px;">1.导入的格式为*.csv或*.vcf，且必须为通用标准格式文件</span><br/><br/>
			<input style="border:0px;margin-left:20px;" type="file" name="file" id="file"/>
			<iframe id="frame" name="frame" style="display:none" src="" ></iframe>
      </div>
      <div class="modal-footer clearfix">
	    <button type="submit" style="margin-left:450px; margin-top: 20px;" class="modal-save btn-alert-blue" id="uploadBtn" >上传</button>
        <button type="button" class="modal-btn-close btn-alert-gray" data-dismiss="modal">关闭</button>
        
      </div>
</div><!-- /.modal -->
</form>

</body>
</html>