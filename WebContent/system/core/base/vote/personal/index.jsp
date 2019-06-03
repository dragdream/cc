<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<%
	TeePerson loginUser = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
	int uuid = loginUser.getUuid();
%>
<title>投票</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/vote/js/vote.js"></script>
<style>
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
		height: 120px;
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
		/* float: left; */
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
<script type="text/javascript" charset="UTF-8">
function doInit(){
	getInfoList();
}
var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/voteManage/getPostVote.action' ,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 20,
		/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
		fit : true,
		fitColumns : true,
		nowrap : true,
		border : false,
		idField : 'sid',
		singleSelect:true,
	
		frozenColumns : [ [
		     /*{field:'sid',checkbox:true},*/{
			field : 'subject',
			title : '标题',
			width : 180,
			sortable : true,
			formatter : function(value, rowData, rowIndex) {
				return "<span>" + value + "</span>" ;
			}
		} ,{
			field : 'createUserName',
			title : '发布人',
			width : 80,
			formatter : function(value, rowData, rowIndex) {
				return  value;
			}
			
		 },{
				field : 'publish',
				title : '发布状态',
				width : '70',
				formatter : function(value, rowData, rowIndex) {
					if(value == '1'){
						return "<font color='#009100'>已发布</font>";
					}else if(value == '2'){
						return "<font color='red'>已终止</font>";
					}else{
						return "<font color='red'>未发布</font>";
					}
			    } 
		} ] ],
		columns : [ [
		    {
			field : 'postDeptNames',
			title : '发布范围',
			width : 220,
			formatter : function(value, rowData, rowIndex) {
				var postUserRoleNames  = rowData.postUserRoleNames;
				var postUserNames  = rowData.postUserNames;
				var valueStr = "";
				if(value != ""){
					valueStr =   "<b>部门:</b>" + value + "<br>";
				}
				if(postUserNames != ""){
					postUserNames =  "<b>用户:</b>" + postUserNames + "<br>";
				}
				
				if(postUserRoleNames != ""){
					postUserRoleNames =  "<b>角色:</b>" + postUserRoleNames+ "<br>";
				}
				valueStr = valueStr + postUserNames + postUserRoleNames;
				if(valueStr != ""){
					valueStr = valueStr.substring(0,valueStr.length -4);
				}
				return valueStr;
		    } 
		  }, {
				field : 'anonymity',
				title : '是否匿名',
				width : 60,
				formatter : function(value, rowData, rowIndex) {
					if(value == '1'){
						return "<font color='#009100'>允许</font>"; 
					}else{
						return "<font color='red'>不允许</font>"; 
					}
			    } 
			 },  
		  {
			field : 'beginDateStr',
			title : '生效日期',
			width : 80	,
			sortable : true
		  } ,
			{
			field : 'endDateStr',
			title : '终止时间',
			width : 80,
			sortable : true,
		  }, 
		  {
			field : 'voteStatus',
			title : '投票状态',
			width : 60,
			formatter : function(value, rowData, rowIndex) {
				var publish = rowData.publish;
				if(publish =='2'){
					return "<font color='red'>已终止</font>";
				}else{
					if(value == '0'){
						return "<font color='red'>未开始</font>";
					}else if(value == '1'){
						return "<font color='green'>进行中</font>";
					}else if(value == '2'){
						return "<font color='red'>已结束</font>";
					}else{
						return "<font color='#616130 '>已投票</font>";
					}
				}
		    } 
		  }
		  , {
				field : 'createDateStr',
				title : '创建时间',
				width : 140	,
				sortable : true
			} ,
		  
		  {
				field : '_optmanage',
				title : '操作',
				width : 180,
				formatter : function(value, rowData, rowIndex) {
					var publish = rowData.publish;
					
					var lookResult = "<a href='javascript:void(0);' onclick='lookResult(\"" +rowData.sid + "\");'>查看结果 </a>";
					if(publish == '2'){
						if(rowData.viewPriv == "1"){//查看投票结果0：投票后允许查看   1：投票前允许查看  2：不允许查看
							return lookResult;
						}
					}else{
						if(rowData.voteStatus == "0"){
							return "";
						}else if(rowData.voteStatus == "1"){ //投票状态：0 未开始 1 进行中 2 已结束 3 已投票
							if(rowData.viewPriv == "1"){//查看投票结果0：投票后允许查看   1：投票前允许查看  2：不允许查看
								return "<a href='javascript:void(0);' onclick='toVote(\"" +rowData.sid + "\");'>参加投票 </a>&nbsp;&nbsp;" + lookResult;
							}else{
								return "<a href='javascript:void(0);' onclick='toVote(\"" +rowData.sid + "\");'>参加投票 </a>";
							}
						}else if(rowData.voteStatus == "2"){
							return lookResult;
						}else{
							return lookResult;
						}
					}
				}
		  }
		  
		] ],onLoadSuccess:onLoadSuccessFunc
	});		
}

//根据条件查询
function doSearch(){
	if($("#form1").valid()){
		var queryParams=tools.formToJson($("#form1"));
		datagrid.datagrid('options').queryParams=queryParams; 
		datagrid.datagrid("reload");
		//关闭模态框
		$(".modal-win-close").click();
	}
	
	
}


	
/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('unselectAll');
}


/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}

function toVoteItem(id){
	window.location.href = contextPath + "/system/core/base/vote/item/index.jsp?voteId=" + id;
}

/**
 * 打开预览模态窗口
 * @param json
 */
function toVote(id) {
	//alert(json.itemNum);
// 	var htmlStr = "<div class='portlet_class' title='投票'><span class='ui-loading'>正在加载数据……</span></div>";  
    /*
	dig = window.top.$(htmlStr).appendTo(window.top.document.body);  
	dig.dialog({
		modal: true,
		width: 600,
		height: 400,//screen.availHeight / 2,
		open: function() {
			getVote(id);
		},
		close: function() {
			window.top.$('.portlet_class').remove();
		},
		buttons: [{
			text: '提交',
			'class' : 'btn btn-success',
			click: function(){sendVote(id);}
		},{
			text: '关闭',
			'class' : 'btn btn-warning',
			click: function(){window.top.$('.portlet_class').remove();}
		}]
	});
	*/
//     htmlStr = getVote(id);
// 	var submit = function (v, h, f) {
// 	    if (v == 0) {
// 	    	var flag = sendVote(id);
// 	    	return flag;
// 	    }
// 	    return true;
// 	};

//     top.$.jBox(htmlStr, { title: "投票", width:600,height:400,buttons:{'提交':0,'关闭': 1},submit:submit});
	openFullWindow(contextPath+"/system/core/base/vote/personal/vote.jsp?voteId="+id);
}


function getVote(id){
	//查看是否允许匿名
	var urlp = "<%=contextPath %>/voteManage/getVoteBySid.action";
	var parap = {voteId : id};
	var jsonObjp = tools.requestJsonRs(urlp, parap);
	var anonymity = jsonObjp.rtData.anonymity;
	var html = "";
	var url = "<%=contextPath %>/voteManage/getVoteListBySid.action";
	var para = {voteId : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		html = "<form id = 'formA' name = 'formA' method='post'><table style='margin-left: 10px;'>";

		for(var i = 0;i<json.length;i++){
			var content = "";
			if(json[i].ifContent==1){
				content = "说明："+json[i].content+"，";
			}
			var required = "";
			if(json[i].required==1){
				required = "必填，";
			}
			var minNum = "";
			if(json[i].minNum!=""){
				minNum = "最小可选："+json[i].minNum+"，";
			}
			var maxNum = "";
			if(json[i].maxNum!=0&&json[i].maxNum!=""&&json[i].minNum!="0"){
				maxNum = "最大可选："+json[i].maxNum+"，";
			}
			var appendix = content + required + minNum + maxNum;
			if(appendix!=""){
			    if(appendix.charAt(appendix.length-1)=="，"){
			    	appendix = appendix.substring(0,appendix.length-1);
				}
				appendix = "<font color='red'>（"+appendix+"）</font>";
			}
			var order = i+1+"、";
			html += "<tr style='min-height:30px;'><td><strong>"+order+json[i].subject+"</strong>"+appendix+"</td></tr>";
			var url1 = "<%=contextPath %>/voteManage/getVoteItemListBySid.action";
			var para1 = {voteId : json[i].sid};
			var jsonObj1 = tools.requestJsonRs(url1, para1);
			if(jsonObj1.rtState){
				var json1 = jsonObj1.rtData;
				var text = "<tbody>";
				switch (json[i].voteType) {
				case "0":
					for(var j = 0;j<json1.length;j++){
						text += "<tr><td><span><input type='checkbox' name='"+json1[j].sid+"' id='"+json1[j].sid+"'>" + json1[j].itemName + "</span></td></tr>";
					}
					break;
				case "1":
					for(var j = 0;j<json1.length;j++){
						text += "<tr><td><span><input type='radio' name='radio_"+json[i].sid+"' id='"+json1[j].sid+"' value='"+json1[j].sid+"'>" + json1[j].itemName + "</span></td></tr>";
					}
					break;
				case "2":
					for(var j = 0;j<json1.length;j++){
						text += "<tr><td><span><input type='text' name='"+json1[j].sid+"' id='"+json1[j].sid+"'></span></td></tr>";
					}
					break;
				case "3":
					for(var j = 0;j<json1.length;j++){
						text += "<tr><td><span><textarea cols='50' rows='5' name='"+json1[j].sid+"' id='"+json1[j].sid+"'></textarea></span></td></tr>";
					}
					break;
				case "4":
					text += "<tr><td><select name='select_"+json[i].sid+"' id='select_"+json[i].sid+"'>";
					for(var j = 0;j<json1.length;j++){
						text += "<option value='"+json1[j].sid+"'>"+json1[j].itemName+"</option>";
					}
					text += "</select></td></tr>";
					break;
				}
				text += "</tbody>";
			}
			//var textHtml = json[i].htmlForPreview;
			html += "<tr><td>"+text+"</td></tr>";
		}	
		if(anonymity=="1"){
			html += "<tr style='min-height:50px;'><td ><input id='anonymity' name = 'anonymity' type='checkBox'><strong><font color='red'>是否匿名投票</font></strong></td></tr>";
		}
		html+="</table></form>";
		//window.top.$('.portlet_class').html(html);
	}
	return html;
}

function sendVote(id){
	if(checkForm(id)){
		if(window.top.$("#anonymity").val()=="on"){
			anonymity = "1";
		}else{
			anonymity = "0";
		}
		var url = "<%=contextPath %>/voteManage/savePersonalVote.action?voteId="+id+"&anonymity="+anonymity;
		var para = tools.formToJson(window.top.$("#formA")) ;
		var jsonObj = tools.requestJsonRs(url, para);
		$.MsgBox.Alert(jsonObj.rtMsg);
		//window.top.$.jbox.close();
		location.reload();
		return true;
	}else{
		return false;
	}
}

function doPageHandler(){
	datagrid.datagrid("unselectAll");
	datagrid.datagrid("reload");
}

function checkForm(id){
	var url = "<%=contextPath %>/voteManage/getVoteListBySid.action";
	var para = {voteId : id};
	var jsonObj = tools.requestJsonRs(url, para);
	var $ = window.top.$;
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		for(var i = 0;i<json.length;i++){
			var required = false;
			var minNum = 0;
			var maxNum = 0;
			var num = 0;
			var nowRequired = false;
			if(json[i].required==1){
				required = true;
			}
			if(json[i].minNum!=0&&json[i].minNum!=""&&json[i].minNum!="0"){
				minNum = json[i].minNum;
			}
			if(json[i].maxNum!=0&&json[i].maxNum!=""&&json[i].minNum!="0"){
				maxNum = json[i].maxNum;
			}
			var url1 = "<%=contextPath %>/voteManage/getVoteItemListBySid.action";
			var para1 = {voteId : json[i].sid};
			var jsonObj1 = tools.requestJsonRs(url1, para1);
			if(jsonObj1.rtState){
				var json1 = jsonObj1.rtData;
				switch (json[i].voteType) {
				case "0":
					for(var j = 0;j<json1.length;j++){
						if(!required||($("#"+json1[j].sid).attr("checked")=="checked")){
							nowRequired = true;
						}
						if($("#"+json1[j].sid).attr("checked")=="checked"){
							num++;
						}
					}
					if(!nowRequired){
						$.MsgBox.Alert_auto(json[i].subject+"为必填项！");
						return false;
						
					}
					if((num<json[i].minNum)||(num>json[i].maxNum)){
						
						$.MsgBox.Alert_auto(json[i].subject+"可选数量应该在"+json[i].minNum+"和"+json[i].maxNum+"之间");
						return false;
						
					}
					break;
				case "1":
					for(var j = 0;j<json1.length;j++){
						if(!required||($("input:radio[name='radio_"+json[i].sid+"']:checked").val()!=null)){
							nowRequired = true;
						}
					}
					if(!nowRequired){
						$.MsgBox.Alert_auto(json[i].subject+"为必填项！");
						return false;
					}
					break;
				case "2":
					for(var j = 0;j<json1.length;j++){
						if(!required||$("#"+json1[j].sid).val()!=""){
							nowRequired = true;
						}
					}
					if(!nowRequired){
						$.MsgBox.Alert_auto(json[i].subject+"为必填项！");
						return false;
						
					}
					break;
				case "3":
					for(var j = 0;j<json1.length;j++){
						if(!required||$("#"+json1[j].sid).val()!=""){
							nowRequired = true;
						}
					}
					if(!nowRequired){
						$.MsgBox.Alert_auto(json[i].subject+"为必填项！");
						return false;
						
					}
					break;
				case "4":

					break;
				}
			}
		}
	}
	
	return true;
}

/* 新建文件夹 */
function createFolderFunc(titleStr){
  var html = "<br><form method='post' name='mailBoxForm' id='mailBoxForm'><table class='TableBlock' width='80%' align='center'>"
           +   "<tr class='TableLine2'>"
           +     "<td>文件名称</td>"
           +     "<td><input type='text' name='boxInput' id='boxInput' class='easyui-validatebox BigInput' size='35' maxlength='100'></td>"
           +   "</tr>"
           +   "<tr class='TableControl'>"
           +     "<td colspan='2' align='center'>"
           +       "<input type='button' value='保存' class='btn btn-primary' onclick='submitNewFolder();'>&nbsp;&nbsp;"
           +       "<input type='button' value='关闭' class='btn btn-primary' onclick='javascript:$.jBox.close();'>&nbsp;&nbsp;"
           +     "</td>"
           +   "</tr>"
           + "</table></form>";
  
  $.jBox(html, { title: titleStr, width:500,height:200,buttons:{} });
}

function lookResult(id) {
	
	//alert(json.itemNum);
// 	var htmlStr = "<div class='portlet_class' title='投票结果'><span class='ui-loading'>正在加载数据……</span></div>";  
    /*
	dig = window.top.$(htmlStr).appendTo(window.top.document.body);  
	dig.dialog({
		modal: true,
		width: 600,
		height: 400,//screen.availHeight / 2,
		open: function() {
			getVoteResult(id);
		},
		close: function() {
			window.top.$('.portlet_class').remove();
		},
		buttons: [{
			text: '关闭',
			'class' : 'btn btn-success',
			click: function(){window.top.$('.portlet_class').remove();}
		}]
	});
	*/
// 	htmlStr = getVoteResult(id);
// 	top.$.jBox(htmlStr, { title: "投票结果", width:600,height:400,buttons:{'关闭': true}});
	openFullWindow(contextPath+"/system/core/base/vote/personal/vote_result.jsp?voteId="+id,800,600);
}

function getVoteResult(id){
	var url = "<%=contextPath %>/voteManage/getVoteListBySid.action";
	var para = {voteId : id};
	var html = "";
	var jsonObj = tools.requestJsonRs(url, para);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		html = "<form id = 'formA' name = 'formA' method='post'><table style='margin-left: 10px;'>";
		for(var i = 0;i<json.length;i++){
			var order = i+1+"、";
			html += "<tr style='min-height:30px;'><td><strong>"+order+json[i].subject+"</strong></td></tr>";
			var url1 = "<%=contextPath %>/voteManage/getVoteItemListBySid.action";
			var para1 = {voteId : json[i].sid};
			var jsonObj1 = tools.requestJsonRs(url1, para1);
			if(jsonObj1.rtState){
				var json1 = jsonObj1.rtData;
				var text = "<tbody>";
				switch (json[i].voteType) {
				case "0":
					for(var j = 0;j<json1.length;j++){
						var url2 = "<%=contextPath %>/voteManage/getVoteData.action";
						var para2 = {id : json1[j].sid,voteId : id};
						var jsonObj2 = tools.requestJsonRs(url2, para2);
						var jsonData2 = jsonObj2.rtData;
						var sign = "";
						for(var o = 0;o<jsonData2.length;o++){
							if(uuid==jsonData2[o].userId){
								sign = "√";
							}
						}
						text += "<tr><td><span>" + json1[j].itemName + "（"+json1[j].voteCount+" 票）<font color='red'>"+sign+"</font></span></td></tr>";
					}
					break;
				case "1":
					for(var j = 0;j<json1.length;j++){
						var url2 = "<%=contextPath %>/voteManage/getVoteData.action";
						var para2 = {id : json1[j].sid,voteId : id};
						var jsonObj2 = tools.requestJsonRs(url2, para2);
						var jsonData2 = jsonObj2.rtData;
						var sign = "";
						for(var o = 0;o<jsonData2.length;o++){
							if(uuid==jsonData2[o].userId){
								sign = "√";
							}
						}
						text += "<tr><td><span>" + json1[j].itemName + "（"+json1[j].voteCount+" 票）<font color='red'>"+sign+"</font></span></td></tr>";
					}
					break;
				case "2":
					var url2 = "<%=contextPath %>/voteManage/getVoteData.action";
					var para2 = {id : json1[0].sid,voteId : id};
					var jsonObj2 = tools.requestJsonRs(url2, para2);
					var json2 = jsonObj2.rtData;
					for(var j = 0;j<json2.length;j++){
						text += "<tr><td><span><font color='red'>"+json2[j].userName+"</font>："+json2[j].voteData+"</span></td></tr>";
					}
					break;
				case "3":
					var url2 = "<%=contextPath %>/voteManage/getVoteData.action";
					var para2 = {id : json1[0].sid,voteId : id};
					var jsonObj2 = tools.requestJsonRs(url2, para2);
					var json2 = jsonObj2.rtData;
					for(var j = 0;j<json2.length;j++){
						text += "<tr><td><span><font color='red'>"+json2[j].userName+"</font>："+json2[j].voteData+"</span></td></tr>";
					}
					break;
				case "4":
					for(var j = 0;j<json1.length;j++){
						var url2 = "<%=contextPath %>/voteManage/getVoteData.action";
						var para2 = {id : json1[j].sid,voteId : id};
						var jsonObj2 = tools.requestJsonRs(url2, para2);
						var jsonData2 = jsonObj2.rtData;
						var sign = "";
						for(var o = 0;o<jsonData2.length;o++){
							if(uuid==jsonData2[o].userId){
								sign = "√";
							}
						}
						text += "<tr><td><span>" + json1[j].itemName + "（"+json1[j].voteCount+" 票）<font color='red'>"+sign+"</font></span></td></tr>";
					}
					break;
				}
				text += "</tbody>";
			}
			//var textHtml = json[i].htmlForPreview;
			html += "<tr><td>"+text+"</td></tr>";
		}	
		html+="</table></form>";
		
		//window.top.$('.portlet_class').html(html);
	}
	return html;
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
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<table id="datagrid"></table>
<div id="toolbar" class = "topbar clearfix">
	<div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tpgl/icon_toupiaochaxun.png">
		<span class="title">投票查询</span>
	</div>
	<div class = "right fr t_btns">
		<button type="button" onclick="$(this).modal();" value="高级检索" class="modal-menu-test"
		style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/xzbg/tpgl/icon_gaojijiansuo.png)"
		>&nbsp;高级检索</button>
    </div>
</div>
<!-- 模态框Modal -->
<form id="form1" name="form1">
<div class="modal-test">
	<div class="modal-header">
		<p class="modal-title">
			查询条件
		</p>
		<span class="modal-win-close">×</span>
	</div>
	<div class="modal-body" id="mailBoxForm">
		<ul>
		   <table class="TableBlock" width="100%" style="">
		      <tr>
		         <td class="TableData" style="text-indent: 10px" width="100px">标题：</td>
		         <td><input class="" type="text" id ="subject" name='subject' size="" style="height: 20px"/></td>    
		      </tr>  
		      <tr>
		         <td class="TableData" style="text-indent: 10px"  width="100px">标题：</td>
		         <td>
                    <select id="publish" name="publish" class="" style="height: 20px">
					<option value="">全部</option>
					<option value="0">未发布</option>
					<option value="1">已发布</option>
					<option value="2">已终止</option>
				    </select>
                 </td>    
		      </tr>  
		      <tr>
		         <td class="TableData" style="text-indent: 10px"  width="100px">创建日期：</td>
		         <td>
                 <input type="text" id="startDateStr" name="startDateStr"
				 style="height: 20px;width: 120px"
				 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDateStr\')}'})" required value="" />
				 &nbsp;至 &nbsp;
				 <input type="text" id="endDateStr" name="endDateStr" 
				 style="height: 20px;width: 120px"
				 class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDateStr\')}'})" required value=""/>
                 </td>    
		      </tr>  
		   </table>
		</ul>
	</div>
	<div class="modal-footer clearfix">
		<input class = "btn-alert-gray" type="reset" value='重置' onclick=""/>
		<input class = "modal-save btn-alert-blue" type="button" onclick="doSearch();" value = '查询'/>
	</div>
</div>
</form>


</body>





</html>