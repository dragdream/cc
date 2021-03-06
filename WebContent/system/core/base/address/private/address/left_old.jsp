<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>
	<%@ include file="/header/easyui.jsp" %>
<title>多选人员</title>
<script type="text/javascript">
$(function(){

	getAddresGroup();
	getAddresLastName();
	
});
function getAddresGroup(){
	var url = contextPath +  "/teeAddressGroupController/getAddressGroups.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		addRows(dataList);
	}else{
		alert(jsonRs.rtMsg);
	} 
}

function addRows(rtData){
	var ulDom = $("#grouplistId");
	
	var liArray = new Array();
	if(rtData){
		$.each(rtData,function(key, val){
			var userId = val.userId;
			if(!val.userId){
				userId = "";
			}
			var liTem = '<li>';
			if(!val.userId && val.groupName != "默认"){
				liTem = liTem +'<a href="#" onclick="paraGroupRightList(\'{seqId}\',\'\' )" >{groupName}<font color="red">（公共）</font>';
			}else{
				if( val.groupName == "默认"){
					userId = "0";
				}
				liTem = liTem +'<a href="#" onclick="paraGroupRightList(\'{seqId}\',\''+userId+'\' )" >{groupName}';
			}
			liTem = liTem +'</a>'+
		    '</li>';
			var str = FormatModel(liTem,val);
			liArray.push(str);
		});
	}
	ulDom.html(liArray.join(''));
}

function addTypeRows(rtData){
	var ulDom = $("#addressLastNameList");
	var liTem = '<li>'+
		'<a href="#" onclick="paraRightList(\'{seqId}\')">'+
		'{nameStrs}'+
		'</a>'+
	'</li>';
	var liArray = new Array();
	if(rtData){
		$.each(rtData,function(key, val){
			var str = FormatModel(liTem,val);
			liArray.push(str);
		});
	}
	ulDom.html(liArray.join(''));
}

function addActtive(){
	 $("#grouplistId li").bind("click",function(){
	//	 $("#grouplistId li.active").

	});
}

//获取 姓氏 分组 zhp  2013-12-23
function getAddresLastName(){
		var url = contextPath +  "/teeAddressController/getAddressLastName.action";
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var dataList = jsonRs.rtData;
			addTypeRows(dataList);
		}else{
			alert(jsonRs.rtMsg);
		} 
}

//姓氏 查询 zhp 2013-12-23
function paraRightList(para){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/address/addressList.jsp";
	if(para){
		url = url + "?seqIds="+para;
	}
	_rightWin.location.href = url;
}

//分组查询 zhp 2013-12-23
function paraGroupRightList(para , userId){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/address/addressList.jsp";
	url = url + "?groupId="+para + "&userId=" + userId;
	_rightWin.location.href = url;
	
}

//分组查询 zhp 2013-12-23
function queryList(para){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/address/queryAddressList.jsp";
	if(para){
		url = url + "?groupId="+para;
	}
	_rightWin.location.href = url;
}

//管理分组zhp 2013-12-23
function manageAddressGroup(){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/group/index.jsp";
	_rightWin.location.href = url;
}
</script>
</head>
<body style="margin:5px 0px 0px 5px;">
<div class="panel-group" id="accordion">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
       	  联系人分组
        </a>
      </h4> 
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      <div class="panel-body">
      
      <ul class="nav nav-pills nav-stacked" id="grouplistId" style="margin-left:0px;">
	  </ul>
	  
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" >
         	索引（按照姓氏）
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse">
      <div class="panel-body">
      <ul class="nav" id="addressLastNameList" style="margin-left:0px;" >
	  </ul>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="queryList();">
       		 查找（关键字）
        </a>
      </h4>
    </div>
  </div>
   <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion111" href="#" onclick="manageAddressGroup();">
       		  管理分组
        </a>
      </h4>
    </div>
  </div>
</div>

</body></html>