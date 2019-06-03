<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" style="background-color: #eaedf2">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp" %>
<title>多选人员</title>

<script type="text/javascript">
$(function(){

	getAddresGroup();
	getAddresLastName();
	
});
function getAddresGroup(){
	var url = contextPath +  "/teeAddressGroupController/getPublicAddressGroups2Priv.action";
	var para = {};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		addRows(dataList);
	}else{
		$.MsgBox.Alert_auto(jsonRs.rtMsg);
	} 
}

function addRows(rtData){
	var ulDom = $("#grouplistId");
	var liTem = '<li onclick="paraGroupRightList(\'{seqId}\')">'+
		'<a href="#"  >'+
		'{groupName}'+
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

function addTypeRows(rtData){
	var ulDom = $("#addressLastNameList");
	var liTem = '<li onclick="paraRightList(\'{seqId}\')">'+
		'<a href="#" >'+
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
		var url = contextPath +  "/teeAddressController/getPublicAddressLastName.action";
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
	var url = contextPath+"/system/core/base/address/public/address/addressList.jsp";
	if(para){
		url = url + "?seqIds="+para;
	}
	_rightWin.location.href = url;
}

//分组查询 zhp 2013-12-23
function paraGroupRightList(para){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/public/address/addressList.jsp";
	if(para){
		url = url + "?groupId="+para;
	}
	_rightWin.location.href = url;
}

//分组查询 zhp 2013-12-23
function queryList(para){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/public/address/queryAddressList.jsp";
	if(para){
		url = url + "?groupId="+para;
	}
	_rightWin.location.href = url;
}

//管理分组zhp 2013-12-23
function manageAddressGroup(){
	var _rightWin = window.parent.rightList;
	var url = contextPath+"/system/core/base/address/public/group/index1.jsp";
	_rightWin.location.href = url;
}
</script>
<style>
		body{
			background-color:#eaedf2;
		}
		.panel-heading > span{
			position:absolute;
		}
		.panel-heading{
		padding: 10px 5px;
		font-size: 14px;
		text-align: left;
		text-indent:20px;
		box-sizing: border-box;
		}
		.panel-title {
			margin-left:15px;
			display:inline;
		}
		.panel-title a{
		color:#000;
		}
		.groupContent li{
		height: 30px;
		line-height: 30px;
		font-size: 12px;
		text-align: left;
		text-indent:60px;
		cursor:pointer;
		}
		.groupContent li:hover{
		background-color:#fff;
		color:#fff;
		}
		.groupContent li a{
		color:#000;
		}
		.groupContent li a:hover{
		color:#000;
		}
		li.li_active{
			background-color:#fff;
		}
</style>
</head>
<body style="background-color: #eaedf2">
<div class="panel-group" id="accordion">
  <div class="panel panel-default">
    <div class="panel-heading">
    <span class='caret-down' ></span>
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
       	  联系人分组
        </a>
      </h4> 
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
      <div class="panel-body">
      
      <ul class="nav nav-pills nav-stacked groupContent" id="grouplistId" style="margin-left:0px;">
	  </ul>
	  
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
    <span class='caret-down' ></span>
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" >
         	索引（按照姓氏）
        </a>
      </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse">
      <div class="panel-body">
      <ul class="nav nav-pills  groupContent" id="addressLastNameList" style="margin-left:0px;" >
	  </ul>
      </div>
    </div>
  </div>
  <div class="panel panel-default">
    <div class="panel-heading">
    <span class='caret-right' ></span>
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="queryList();">
       		 查找（关键字）
        </a>
      </h4>
    </div>
  </div>
   <div class="panel panel-default">
    <div class="panel-heading">
     <span class='caret-right' ></span>
      <h4 class="panel-title">
        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion111" href="#" onclick="manageAddressGroup();">
       		  管理分组
        </a>
      </h4>
    </div>
  </div>
</div>

</body>

<script>
$('body').on('click','.nav-pills li',function(){
	$('.nav li').removeClass('li_active');
	$(this).addClass("li_active").siblings().removeClass("li_active");
});

			$(".panel-heading").click(function(){
				if($(this).siblings().find('.panel-body').length==0){
					return false;
				}
				var $span = $(this).find('span');
				var isOpen = $span.hasClass("caret-down");
				if(isOpen){
					//$('.panel-body ul').slideUp();
					$(this).siblings('.collapse').slideUp(200);
					$span.attr("class","caret-right");
				}else{
					$(this).siblings('.collapse').slideDown(200);
					$span.attr("class","caret-down");
				}
		})
		</script>

</html>