<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 

	String  optType = TeeStringUtil.getString(request.getParameter("optType"), "0");//0-我的分组  1-我的同事
	int  groupId = TeeStringUtil.getInteger(request.getParameter("groupId") , -1);//我的分组Id
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/system/core/base/address/js/slidernav.css" media="screen, projection" />
<script type="text/javascript" charset="UTF-8" src="<%=contextPath%>/system/core/base/address/js/slidernav.js"></script>
<script type="text/javascript" charset="UTF-8" src="<%=contextPath%>/system/core/base/address/public/js/address.js"></script>
<script type="text/javascript">

var groupId = "<%=groupId%>";
var optType = "<%=optType%>";
/**
 * 获取内部通讯录
 */
function getAddresList(){
		var url = contextPath +  "/teeAddressController/getAddressFullNamList.action";
		var para = {groupId:groupId,userName:$("#userName").val()};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var dataList = jsonRs.rtData;
			addTypeRows(dataList);
		}else{
			alert(jsonRs.rtMsg);
		} 
}
/*
* 获取人员通讯录
*/
function getPersonInfoList(){
		var url = contextPath +  "/teeAddressController/getPersonAddress.action";
		var para = {userName:$("#userName").val()};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			var dataList = jsonRs.rtData;
			addTypeRows(dataList);
		}else{
			alert(jsonRs.rtMsg);
		} 
}
/**
 * 插入数据
 */
 var items = new Array();//有人员的字母
function addTypeRows(rtData){
	var ulDom = $("#addressList");
	ulDom.empty();
	var liTem = "";
	items.length  = 0;//数组清空
	var navCount = 0 ;
	$("div").remove("#nullData");
	for (var property in rtData) {
		 var value = rtData[property];  
		 if(value.length > 0){
			 ++navCount;
			 items.push(property.toLowerCase() );
			 var temp = property.toLowerCase();
			 if(temp == "#"){
				 temp = "AA";
			 }
			 liTem = liTem + '<li id="'+temp +'"><a name="'+property.toLowerCase()+'" class="title" style="font-size:14px;">'+property+'</a>'
			     +'<ul>';
			 for (var address in value) {
				  var sex = value[address].sex;
				  var sexDesc = "男";
				  if(sex == '1'){
					  sexDesc = "女";
				  }
				 liTem = liTem 
				  +'<li><a href="javascript:void();" onclick="lookDetailAddress('+value[address].sid+');"><table width="100%" style="font-size:12px;" ><tbody><tr>'
			         +'<td style="width:7%;" nowrap>'+value[address].psnName +'&nbsp;</td>'
// 					+'<td style="width:16%;" nowrap>'+value[address].deptName+'&nbsp;</td>'	
// 					+'<td style="width:10%;" nowrap>'+value[address].ministration+'&nbsp;</td>'
// 					+'<td style="width:5%;" nowrap>'+sexDesc+'&nbsp;</td>'	
// 					+'<td style="width:11%;" nowrap>'+value[address].telNoDept+'&nbsp;</td>'	
// 					+'<td style="width:11%;" nowrap>'+value[address].mobilNo+'&nbsp;</td>'	
// 					+'<td style="width:18%;" nowrap>'+value[address].email+'&nbsp; </td>'	
// 					+'<td style="width:19%;" nowrap>'+value[address].addHome+'</td> '
				  +'</tr></tbody></table></a></li>';
			  }
			  liTem = liTem  +'</ul></li>' ;
		 }
		 ///alert(property);
	 } 
	if(navCount <= 0){
		$(".slider-content").append("<div id='nullData'></div>");
	}
	ulDom.html(liTem);
}
function doInit(){
	var clientHeight = $(document).height();
	/* $(".slider-content").height(clientHeight-120) ;
	$(".slider-nav").height(clientHeight-120) ;
	 */
		if(optType == "1"){
			getPersonInfoList();
		}else{
			 getAddresList();
		}
		//$('#slider').sliderNav();
		$('#slider').sliderNav({items:items,height: clientHeight-83});
		//$('#transformers').sliderNav({items:['a','b',"e","r"], debug: true, height: '300', arrows: false});
	
}

function queryAddress(){
	 doInit();
}
/**
 * 查看地址信息
 */
function lookDetailAddress(sid ){
	var url = "";
	if(optType == "1"){
		url = contextPath + "/system/core/person/userinfo.jsp?uuid="+sid;
	}else{
		url = contextPath + "/system/core/base/address/private/address/addressDetail.jsp?id="+sid;
	}
	$("#frame0").attr("src",url);
}
</script>
<style>
	/* The following styles are used only for this page - the actual plugin styles are in slidernav.css */
	* { margin: 0; padding: 0; }
	body { margin:0px;padding:0px;}
	a { text-decoration: none; }
	h2, h3 { margin: 0 0 10px;  }
	h2 { font-size: 28px; }
	h3 { font-size: 22px; }
	pre { background: #fff; width: 460px; padding: 10px 20px; border-left: 5px solid #ccc; margin: 0 0 20px; }
	p { width: 500px; font-size: 18px; line-height: 24px; margin: 0 0 30px; }
</style>
</head>
<body onload="doInit();" style=";overflow:hidden">
<div style="position:absolute;top:0px;left:0px;bottom:0px;width:200px;overflow:hidden">
	<div class="input-group">
	  <input onkeyup="queryAddress();" name="userName" id="userName" type="text" class="form-control" placeholder="搜索通讯簿人员" aria-describedby="basic-addon2">
	  <span class="input-group-addon" id="basic-addon2"><i class="glyphicon glyphicon-search"></i></span>
	</div>
	<div id="slider" style="width:100%;margin-top:5px">
		<div class="slider-content">
			<ul id="addressList">
			
			</ul>
		</div>
	</div>
</div>
<div style="position:absolute;top:0px;right:0px;bottom:0px;left:210px;overflow:hidden">
	<iframe id="frame0" style="width:100%;height:100%;" frameborder="0"></iframe>
</div>
</body>
</html>