<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header1.0.jsp" %>
<title>通讯录</title>
<script type="text/javascript">
$(function(){
	getAddresGroup();//获取分组
	//getMyPersonList();
	
});
var groupId = 0;
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



function addActtive(){
	 $("#grouplistId li").bind("click",function(){
	//	 $("#grouplistId li.active").

	});
}

//我的同事
function getMyPersonList(){
	
	//var url = contextPath+"/system/core/base/address/private/address/manage.jsp";
	
	 var _rightWin = window.parent.rightList;
	var url=contextPath+"/system/core/base/address/private/address/colleagueList.jsp";
	 _rightWin.location.href=url; 
}

//分组查询 syl 
function paraGroupRightList(para , userId){
	groupId = para;
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/address/addressList.jsp";
	url = url + "?optType=0&groupId="+para + "&userId=" + userId;
	_rightWin.location.href = url;
	
}

//分组查询 zhp 2013-12-23
function queryList(para){
	groupId = 0
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/address/queryAddressList.jsp";
	if(para){
		url = url + "?groupId="+para;
	}
	_rightWin.location.href = url;
}

//管理分组zhp 2013-12-23
function manageAddressGroup(){
	groupId = 0
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/group/index.jsp";
	_rightWin.location.href = url;
}

/* 新增 */
function addNewAddress(){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/private/address/addAddress.jsp?groupId="+groupId;
	_rightWin.location.href = url;
}
</script>
</head>
<body style="margin:5px 0px 0px 5px;">
<div class="panel-group" id="accordion">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" style="cursor:pointer;"  onclick="paraGroupRightList('','')">
       	  我的分组(全部)
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
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" style="cursor:pointer;" onclick="getMyPersonList();">
         	我的同事(OA用户)
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
       		 查询（关键字）
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