<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String moduleId = request.getParameter("moduleId");
if (moduleId == null) {
  moduleId = "";
}
String privNoFlag = request.getParameter("privNoFlag");
if (privNoFlag == null || "".equals(privNoFlag)) {
  privNoFlag = "0";
}
String privOp = request.getParameter("privOp");
if (privOp == null) {
  privOp = "";
}

String objSelectType = request.getParameter("objSelectType");
if (objSelectType == null) {
	objSelectType = "";
}


//回调方法参数
String callBackPara = request.getParameter("callBackPara") == null ? "" : request.getParameter("callBackPara")  ;
callBackPara = callBackPara.replace("\"", "\\\"");


//flowPrcs是TeeFlowProcess的主键  ---暂不起作用
//frpSid是TeeFlowRunPrcs的主键
String flowPrcs = request.getParameter("flowPrcs") == null ? "0" : request.getParameter("flowPrcs")  ;
String frpSid = request.getParameter("frpSid") == null ? "0" : request.getParameter("frpSid")  ;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>单个选择人员</title>
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/index1.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/orgselect/orgselectWorkFlow.js?version=3"></script>

<script type="text/javascript">
var selectedColor = "rgb(0, 51, 255)";
var RoleId,RoleName;
var moduleId = "<%=moduleId%>";
objSelectType = '<%=objSelectType%>';
var privOp = '<%=privOp%>';
var privNoFlag = "<%=privNoFlag%>";
var userFilter = xparent.RANDOM_USER_FILTER;
var callBackPara = "<%=callBackPara%>";
var flowPrcs = "<%=flowPrcs%>";
var prcsId = xparent.PRCSID;
var frpSid = xparent.FRPSID;
var parentWindowObj = xparent;
var to_id_field ;//父级文本框对象Id
var to_name_field;//父级文本框对象Name
var single_select = true;//是否是单用户选择
var ctroltime=null,key="";

function doInit(){
	  var userRetNameArray = parentWindowObj["userRetNameArray"];
	  if (userRetNameArray && (userRetNameArray.length == 2 || userRetNameArray.length == 3 )) {
	    var deptCntrl = userRetNameArray[0];
	    var deptDescCntrl = userRetNameArray[1];  
	    to_id_field = parentWindowObj.document.getElementById(deptCntrl);
	    to_name_field = parentWindowObj.document.getElementById(deptDescCntrl);  
	  }else {
		to_id_field = parentWindowObj.document.getElementById("user");
	    to_name_field = parentWindowObj.document.getElementById("userName");
	  }  
	  
	//获取部门树
    getDeptTree();
	
	//初始化对象
    dataInit();
}


/**
 * 按条件查询人员
 */
function CheckSend(){
	//alert("d")
	var kword = document.getElementById("kword");
	var search_icon = document.getElementById("search_icon");
	  if(kword.value=="按用户名或姓名搜索...")
	     kword.value="";
	  if(kword.value=="" && search_icon.innerHTML =="查询")
	  {
		  search_icon.innerHTML =="查询";
	  }
	  if(key!=kword.value && kword.value!="")
	  {
	     key=kword.value;
	     doSearch(key);
	     
	     if(search_icon.innerHTML =="查询")
	     {   
	    	 search_icon.innerHTML ="清除";
	    	 //alert(search_icon.value);
	    	 search_icon.title="清除关键字";
	    	 search_icon.onclick=function(){
	    		 kword.value='按用户名或姓名搜索...';
	    		 search_icon.innerHTML ="查询";
	    		 search_icon.title="";
	    		 search_icon.onclick=null;};
	     }
	  }
	  ctroltime=setTimeout(CheckSend,100);
}
/***
 * 按人员ID和用户名模糊查询
 */
function doSearch(name){
	var url = contextPath +  "/orgSelectManager/getSelectUserByUserIdOrUserNameWorkflow.action";
	var para = {user:name,moduleId:moduleId,privNoFlag:privNoFlag,prcsId:prcsId,frpSid:frpSid};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		dataLoad(dataList,"人员查询");
		//dataLoad(dataList,jsonRs.rtMsg);
	}else{
		alert(jsonRs.rtMsg);
	} 
}
/**
 * 处理数据
 */
function dataInit(){
	
	if(objSelectType &&objSelectType == '1'){//当为多选框时
		getMultiple();
		to_id_field_value = item_id_multiple;
		to_name_field_value = item_id_multiple_name;
	}else{
		to_id_field_value = to_id_field.value;
		to_name_field_value = to_name_field.value;
	}
	if(to_id_field_value == ''){//如果初始化为空时，从当前部门获取
		//getCurrentDeptPerson();
		$("#collapseTwo").addClass("panel-collapse collapse in");
		return;
	}
	if(to_id_field_value != ''){
		$("#collapseTwo").addClass("panel-collapse collapse");
	}
	var dataList = new Array();
	var dataNameList  = new Array();
	if(to_id_field_value != ""){
		dataList = to_id_field_value.split(",");
		dataNameList = to_name_field_value.split(",");
	}
	//转数组对象
	for(var i =0 ; i <dataList.length ; i++){
		if(dataList[i] && dataList[i] != "" ){
			var item = {id:dataList[i] , name:dataNameList[i]};
			id_field_array.push(item);
		}
		
	}
	dataLoadInit(id_field_array);
}
/**
 * 点击已选人员
 */
function clickSelectData(){
	var dataList = to_id_field.value.split(",");
	var dataNameList = to_name_field.value.split(",");
	if(to_id_field.value == ''){
		dataList = [];
		dataNameList = [];
	}
	

	//转数组对象
	if(id_field_array.length <= 0){
		for(var i =0 ; i <dataList.length ; i++){
			if(dataList[i] && dataList[i] != "" ){
				var item = {id:dataList[i] , name:dataNameList[i]};
				id_field_array.push(item);
			}
			
		}
	}
	
	dataLoadInit(id_field_array);
}





/**
 * 获取部门select字符串
 * uuid:部门id
 * isCheck:是否是勾选 0-勾选为false,1-勾选为true
 */
function getDeptStr(uuid,isCheckedState,deptName){
	var url = contextPath +  "/orgSelectManager/getSelectUserByDeptWorkFlow.action";
	var para = {deptId:uuid,isCheckedState:isCheckedState,prcsId:prcsId,frpSid:frpSid,moduleId:moduleId,privNoFlag:privNoFlag};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		dataLoad(dataList,deptName);
	}else{
		alert(jsonRs.rtMsg);
	} 
}
function getDeptTree(){
	var param = "privNoFlag="+ privNoFlag +"&moduleId="+moduleId + "&userFilter=" + userFilter
				"&flowPrcs=" + flowPrcs + "&frpSid=" + frpSid ;

	var url =  "<%=contextPath %>/orgSelectManager/getSelectDeptTreeWorkFlow.action";
	var config = {
			zTreeId:"selectDeptZtree",
			requestURL:url,
           	onClickFunc:deptOnClick,
			async:false,
			asyncExtend:true,
			onAsyncSuccess:onAsyncSuccessFunc,
			param:{privNoFlag:privNoFlag,moduleId:moduleId,prcsId:prcsId,frpSid:frpSid}
			
		};
	zTreeObj = ZTreeTool.config(config); 	
}
/**
 *  点击
 */
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	var deptName = treeNode.name;
	getDeptStr(uuid,'',deptName);
}
/**
 * zNodesLength ：树节点数
 * rtMsg： 返回的json rtMsg 信息
 */
function onAsyncSuccessFunc(zNodesLength , jsonData){
	//获取人员数组
	var personData = jsonData.rtData.personData;
	var currDept = jsonData.rtData.currDept;
	var currDeptName =  "选择人员";
    if(currDept){
    	currDeptName = currDept.deptName;
    }
	dataLoad(personData ,currDeptName);
}


</script>
<style type="text/css">
	html{
		overflow-y: hidden;
	}
	a:hover{
		color:#fff;
		background-color: #70beff;
	}
	.titlebar{
		overflow:hidden;
	}
	.title1_img{
		width:40px;
		height:40px;
		display: inline-block;
		float: left;
	}
	.titleBlock{
		float: left;
		display: inline-block;
	}
	.titleBlock .title{
		margin-left: 5px;
		font-weight: normal;
	}
	.tab{
		margin-left: 2px;
		overflow:hidden;
	}
	.tab .tabMenu{
		float: left;
		margin:0 3px;
		padding: 3px 0;
		cursor: pointer;
	}
	.tabActive{
		color:#379ff7;
		border-bottom:2px solid #379ff7;
	}
	.tabList .tabContent{
	    height: 296px;
	    width: 260px;
	    border: 1px solid #dadada;
	    overflow-x:hidden;
	    overflow-y:auto;
	    display: none;
	}
	.list-group-item{
		display:block;
		line-height: 25px;
		/* margin-left: 10px; */
	}
	.form-control{
		height:26px;
		text-indent: 5px;
	}
	.btn{
		background-color: #0d93f6;
		color:#fff;
		border:none;
		height: 28px;
		width:53px;
	}
	a.list-group-item.active, a.list-group-item.active:hover, a.list-group-item.active:focus{
		z-index: 2;
    color: #fff;
    background-color: #70beff;
    border-color: #428bca;
	}
	.tabList .list-group-item{
		width: 210px;
	}
	.block-right{
		border: 1px solid #dadada;
		min-height: 305px;
	}
	.block-right .list-group-item{
		/* width: 183px; */
		cursor: pointer;
	}
	div.right.single{
		padding-top:13px;
	}
	.list-group-item-header{
		border:none;
		border-bottom: 1px solid #dadada;
	}
	.block-select .list-group-item{
		/* width: 195px; */
	}
	div#select_item{
		height: 380px;
		margin:8px;
		padding:0;
		border:1px solid #dadada;
	}
	.block-select{
		height: 349px;
		overflow-x: hidden;
		overflow-y:auto;
	}
 	.selected_person{
		display:block;
		width:262px;
		height:33px;
		text-align:left;
		text-indent:50px;
		border:1px solid #dadada;
		line-height:33px;
		margin-top:5px;
		color:#000;
		background-image: url(unselected.png);
		background-repeat:no-repeat;
		background-position: 25px center;
	}
	.selected_person:hover{
		background-color:#70beff;
		color:#fff;
		background-image: url(selected.png);
	} 
</style>
</head>
<body onload="doInit()" >
<div style="margin-top:5px;margin-left:5px;">
	<div class="titlebar">
		<img src="<%=contextPath %>/system/core/orgselect/icon_selecct.png" alt="" class='title1_img'/>
		<div class="titleBlock">
			<p class="title">选择人员</p>
			<ul class="tab">
				<li class='tabMenu tabActive'>按部门选择</li>
				<li class='tabMenu' onclick="getRole()">按角色选择</li>
				<li class='tabMenu' onclick="getGroup()">自定义组</li>
			</ul>
		</div>
	</div>
	<div class='panel-group'>
		<ul class='tabList' id="accordion" >
			<li class="tabContent" style="display:block;">
				<ul id="selectDeptZtree" class="ztree">
				</ul>
			</li>
			<li class="tabContent">
				<div class="list-group" id="roleList">
				<div class="panel-body" id="defaultRole">
				</div>
			</li>
			<li class="tabContent">
				<div class="list-group" id="userGroupList">
		  		<div class="panel-body" id="defaultGroup">
	      	</div>
				</div>
			</li>
		</ul>
	</div>
	
 	<div>
		<a onclick="clickSelectData()" class='selected_person' style="cursor:pointer;">
         	 已选人员
        </a>
	</div>
	
	</div>
<div class="main-block" id="block_dept" style="display:block;top:0px;">
   <div class="right single" id="dept_item" style="overflow: auto">
   		<div style="padding-bottom: 5px;">
			<table >
			<tr>
				<td>
			    	<div class="input-group">
          		    <input class="form-control" style="float:left;;width:170px;" type="text" id="kword" name="kword" value="按用户名或姓名搜索..." onfocus="ctroltime=setTimeout(CheckSend,100);" onblur="clearTimeout(ctroltime);if(this.value=='')this.value='按用户名或姓名搜索...';"  >
            	    <div class="input-group-btn"  style="float:left;">
	            	  <button tabindex="-1" class="btn btn-default" type="button" value=""  id="search_icon">查询</button>
                   </div>
                   <div style="clear:both;"> </div>
                 </div>
			    </td>
			</tr>
			</table>
		</div>
		<div class="block-right" id="dept_item_0">
	    </div>
	<div id="" align="center" style="margin-top:10px;height:40px;">
   		<!-- <input type="button" class="btn btn-primary" value="确定" onclick="confirmCallBackFunc();close_window();">&nbsp;&nbsp; -->
   		<input type="button" class="btn btn-primary" value="确定" onclick="close_window();">&nbsp;&nbsp;
	</div>
 	</div>
 	
</div>
</div>
</body>

<script type="text/javascript">
	$(".tabMenu").click(function(){
		var index = $(this).index();
		$(".tabMenu").removeClass('tabActive');
		$(this).addClass("tabActive");
		$(".tabContent").hide();
		$(".tabContent").eq(index).show();
	});
</script>
</html>