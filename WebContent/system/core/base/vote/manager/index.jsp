<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/validator2.0.jsp"%>
<title>投票管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
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
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/system/core/base/vote/js/vote.js"></script>
<script type="text/javascript" charset="UTF-8">
function doInit(){
	getInfoList();
}
var datagrid;
function getInfoList(){
	datagrid = $('#datagrid').datagrid({
		url : contextPath + '/voteManage/selectPostVoteManager.action' ,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		toolbar : '#toolbar',
		//iconCls : 'icon-save',
		iconCls:'',
		pagination : true,
		pageSize : 20,
		/* pageList : [ 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 ], */
		fit : true,
		fitColumns : false,
		nowrap : true,
		border : false,
		idField : 'sid',
		singleSelect:true,
		columns : [ [
	                 {field:'sid',checkbox:true},{
	                     field : 'subject',
	                     title : '标题',
	                     width : 150,
	                     sortable : true,
	                     formatter : function(value, rowData, rowIndex) {
	                         return value ;
	                     }
	                 } ,{
	                     field : 'createUserName',
	                     title : '发布人',
	                     width : 100
	                     
	                  },
		    {
			field : 'postDeptNames',
			title : '发布范围',
			width : 250,
			formatter : function(value, rowData, rowIndex) {
				var postUserRoleNames  = rowData.postUserRoleNames;
				var postUserNames  = rowData.postUserNames;
				var valueStr = "";
				if(value != ""){
					valueStr =   "<b>部门:</b><span title='"+value+"'>" + value + "<span><br>";
				}
				if(postUserNames != ""){
					postUserNames =  "<b>用户:</b><span title='"+postUserNames+"'>" + postUserNames + "<span><br>";
				}
				
				if(postUserRoleNames != ""){
					postUserRoleNames =  "<b>角色:</b><span title='"+postUserRoleNames+"'>" + postUserRoleNames+ "<span><br>";
				}
				valueStr = valueStr + postUserNames + postUserRoleNames;
				if(valueStr != ""){
					valueStr = valueStr.substring(0,valueStr.length -4);
				}
				return valueStr;
		    } 
		  },  {
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
		  },
		  {
				field : 'createDateStr',
				title : '创建时间',
				width : 150	,
				sortable : true
			} ,
		  {
				field : '_optmanage',
				title : '操作',
				width : 200,
				formatter : function(value, rowData, rowIndex) {
					var publish = rowData.publish;
					
					var publishStr = "<a href='javascript:void(0);' onclick='updatePublish(\"" +rowData.sid + "\",1);'> 生效</a>";
					if(publish == '1'){
						publishStr = "<a href='javascript:void(0);' onclick='updatePublish(\"" +rowData.sid + "\",2);'> 终止</a>";
					}
				     return "<a href='javascript:void(0);' onclick='toAddUpdate(\"" +rowData.sid + "\");'> 编辑 </a>" +
				     "&nbsp;<a href='javascript:void(0);' onclick='deleteObj(\"" +rowData.sid + "\");'> 删除 </a>"+
				     
				     "&nbsp;<a href='#' onclick='toVoteItem(\"" +rowData.sid + "\");'> 设置投票项</a>"+
				     "&nbsp;<a href='#' onclick='exportExcel(\"" +rowData.sid + "\");'> 导出</a><br>"
				     +publishStr +
				     "&nbsp;<a href='javascript:void(0);' onclick='clearVoteFunc(\"" +rowData.sid + "\");'> 清空投票数据 </a>" +
				     "&nbsp;<a href='javascript:void(0);' onclick='lookResult(\"" +rowData.sid + "\");'> 查看结果 </a>";
				}
		  }
	  
		] ],onLoadSuccess:onLoadSuccessFunc
	});
	
}




	
	function lookResult(id) {
//         var url1 =   contextPath+"/voteManage/checkVoteItemNum.action";
//         var para1 = {sid:id};
//         var jsonObj1 = tools.requestJsonRs(url1, para1);
//         var num = jsonObj1.rtData;
//         if(num<=0){
//             alert("您还未设置投票项，没有投票结果！");
//             return ;
//         }
// 	    var htmlStr = "<div class='portlet_class' title='投票结果'><span class='ui-loading'>正在加载数据……</span></div>";  
// 	    htmlStr = getVoteResult(id);
// 	    top.$.jBox(htmlStr, { title: "投票结果", width:600,height:400,buttons:{'关闭': true}});
	    
	    openFullWindow(contextPath+"/system/core/base/vote/manager/vote_result.jsp?voteId="+id);
	    
	}

<%-- function clearResult(id){
	if(confirm('确实要清空投票结果吗?')){
		var url = "<%=contextPath %>/voteManage/clearResult.action";
		var para = {voteId : id};
		var jsonObj = tools.requestJsonRs(url, para);
		alert(jsonObj.rtMsg);
		window.top.$('.portlet_class').remove();
	}
} --%>

function getVoteResult(id){
	var url = "<%=contextPath %>/voteManage/getVoteListBySid.action";
	var para = {voteId : id};
	var jsonObj = tools.requestJsonRs(url, para);
	var html = "";
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		html = "<form id = 'formA' name = 'formA' method='post'><table style='margin-left: 10px;'>";
		for(var i = 0;i<json.length;i++){
			var order = i+1+"、";
			html += "<tr style='min-height:30px;'><td><strong>"+order+json[i].subject+"</strong></td></tr>";
			var url1 = "<%=contextPath %>/voteManage/getVoteItemListBySid.action";
			var para1 = {voteId : json[i].sid};
			var jsonObj1 = tools.requestJsonRs(url1, para1);
			var text = "";
			if(jsonObj1.rtState){
				var json1 = jsonObj1.rtData;
				text = "<tbody>";
				switch (json[i].voteType) {
				case "0":
					for(var j = 0;j<json1.length;j++){
						var url2 = "<%=contextPath %>/voteManage/getVoteData.action";
						var para2 = {id : json1[j].sid,voteId : id};
						var jsonObj2 = tools.requestJsonRs(url2, para2);
						var jsonData2 = jsonObj2.rtData;
						var sign = "";
						for(var o = 0;o<jsonData2.length;o++){
							sign += "投票人：";
							sign += jsonData2[o].userName + "，";
						}
					    if(sign.charAt(sign.length-1)=="，"){
					    	sign = sign.substring(0,sign.length-1);
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
							sign += "投票人：";
							sign += jsonData2[o].userName + "，";
						}
					    if(sign.charAt(sign.length-1)=="，"){
					    	sign = sign.substring(0,sign.length-1);
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
							sign += "投票人：";
							sign += jsonData2[o].userName + "，";
						}
					    if(sign.charAt(sign.length-1)=="，"){
					    	sign = sign.substring(0,sign.length-1);
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


/**
 * 获取最大记录数
*/
function onLoadSuccessFunc(){
	var data=$('#datagrid').datagrid('getData');
}


/**
 * 条件查询
 */
function query(){
	var para =  tools.formToJson($("#form1")) ;
	$('#datagrid').datagrid('load', para);	
}

/**
 * 新建/编辑
 */
function toAddUpdate(id){
	var title = "新增投票";
	if(id){
		 title = "编辑投票";
	}
	var  url = contextPath + "/system/core/base/vote/addOrUpdate.jsp?sid=" + id;
	bsWindow(url ,title,{width:"700",height:"380",buttons:
		[
		 {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h,f,d){
		var cw = h[0].contentWindow;
		if(v=="保存"){
			cw.doSaveOrUpdate(function(){
				d.remove();
				query();
				//关闭bsWindow
				//hideBsWindow();
				
			});
			//return true;
		}else if(v=="关闭"){
			return true;
		}
	}});
}


function toVoteItem(id){
	window.location.href = contextPath + "/system/core/base/vote/item/index.jsp?voteId=" + id;
}

<%-- function toAddUpdatePerson(id,status){
	var url =   "<%=contextPath%>/voteManage/toAddUpdatePerson.action";
	if(status=="2"){//删除操作
		if(window.confirm('你确定要删除该投票么？')){ 
			var para = {sid:id,status:status};
			var jsonObj = tools.requestJsonRs(url, para);
			location.reload();
			if (jsonObj.rtState) {
				
			} else {
				alert(jsonObj.rtMsg);
			}
		}
	}else{
		if(status==1){
			var url1 =   contextPath+"/voteManage/checkVoteItemNum.action";
// 		    var para1 = {sid:id};
// 		    var jsonObj1 = tools.requestJsonRs(url1, para1);
// 		    var num = jsonObj1.rtData;
// 		    if(num<=0){
// 		    	alert("您还未设置投票项，所以无法发布该投票！");
// 		    	return ;
// 		    }
		}
		var para = {sid:id,status:status};
		var jsonObj = tools.requestJsonRs(url, para);
		location.reload();
		if (jsonObj.rtState) {
			
		} else {
			alert(jsonObj.rtMsg);
		}
	}
	
} --%>


/* 删除 */
function deleteObj(sid){
  if(sid){
	  $.MsgBox.Confirm("提示", "是否确认删除该数据？", function(){
		  var url = "<%=contextPath %>/voteManage/deleteObjById.action";
	      var para = {sids:sid};
	      var jsonRs = tools.requestJsonRs(url,para);
	      if(jsonRs.rtState){
	    	$.MsgBox.Alert_auto("删除成功！");
	        datagrid.datagrid("reload");
	      }else{
	    	$.MsgBox.Alert_auto(jsonRs.rtMsrg);
	      }  
	  });
  }
}
/**
 * 更新状态
 */
function updatePublish(sid,optValue){
	var url = "<%=contextPath %>/voteManage/updatePublish.action";
    var para = {sid:sid,optValue:optValue};
    var jsonRs = tools.requestJsonRs(url,para);
    if(jsonRs.rtState){
       $.MsgBox.Alert_auto("操作成功！");
       datagrid.datagrid("reload");
       //window.location.reload();
    }else{
      $.MsgBox.Alert_auto(jsonRs.rtMsrg);
    }
}

//清空投票数据
function clearVoteFunc(sid){
	 $.MsgBox.Confirm ("提示", "确认要清空该投票数据？", function(){
		 var url = "<%=contextPath %>/voteManage/clearVoteById.action";
		    var para = {sid:sid};
		    var jsonRs = tools.requestJsonRs(url,para);
		    if(jsonRs.rtState){
		    	$.MsgBox.Alert_auto("清空投票数据成功！");
		    	datagrid.datagrid("reload");
		      	//window.location.reload();
		    }else{
		    	$.MsgBox.Alert_auto(jsonRs.rtMsrg);
		    }
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
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/xzbg/tpgl/icon_toupiaoguanli.png">
		<span class="title">投票管理</span>
	</div>
	<div class = "right fr t_btns">
		<%-- <input type="button" style="background-color:transparent;width:102px;height:32px;background-image:url(<%=contextPath %>/common/zt_webframe/imgs/grbg/gztj/icon_gaojijiansuo.png);background-size:100% 100%;color:#fff;border:none;padding-left:15px;" class="btn-win-white fl modal-menu-test" onclick="$(this).modal()" value="高级检索"/> --%>
        <button type="button" value="新建投票"  
        style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/xzbg/tpgl/icon_xinjiantoupiao.png)"
        onclick="toAddUpdate()">&nbsp;新建投票</button>&nbsp;
		<button type="button" value="刷新"  onclick="query()"
		style="background-image:url(<%=contextPath %>/common/zt_webframe/imgs/xzbg/tpgl/icon_shuaxin.png)"
		>&nbsp;刷新</button>&nbsp;
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