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

//prcsId
String prcsId = request.getParameter("prcsId") == null ? "0" : request.getParameter("prcsId")  ;

String callBackPara = request.getParameter("callBackPara") == null ? "" : request.getParameter("callBackPara")  ;
callBackPara = callBackPara.replace("\"", "\\\"");


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>

<title>多选人员</title>

<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/index1.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/orgselect/orgselectWorkFlow.js?version=3"></script>
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
<script type="text/javascript">
var selectedColor = "rgb(0, 51, 255)";
var RoleId,RoleName;
var moduleId = "<%=moduleId%>";
objSelectType = '<%=objSelectType%>';
var privOp = '<%=privOp%>';
var privNoFlag = "<%=privNoFlag%>";
var prcsId = xparent.PRCSID;
var frpSid = xparent.FRPSID;
var callBackPara = "<%=callBackPara%>";
<%-- var privNoFlag = "<%=privNoFlag%>";
var privOp = "<%=privOp%>"; --%>
var parentWindowObj = xparent;
var to_id_field ;
var to_name_field;
var single_select = false;

var ctroltime=null,key="";//人员搜索



var userFilter="";
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
    
	//初始化数据
    dataInit();
}

//重新排序
function resort(){
// 	to_id_field_ = [];
// 	to_name_field = [];
	var ids = [];
	var names = [];
	$("#select_item_0 .list-group-item").each(function(i,obj){
		ids.push(obj.getAttribute("item_id"));
		names.push(obj.getAttribute("item_name"));
	});
	to_id_field.value = ids.join(",");
	to_name_field.value = names.join(",");
	to_id_field_value = ids.join(",");
	to_name_field_value = names.join(",");
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
		getCurrentDeptPerson();
		$("#collapseTwo").addClass("panel-collapse collapse in");
		return;
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
	dataLoadInit(id_field_array ,'1');
	load_init_item();
}


/***
 * 初始化已选人员 ---加载数据 
 * datList: 人员列表
 */
function dataSelectLoadInit(dataList ){
	 $("#select_item_0 a").remove(".list-group-item");
	 $("#select_item_0 div").remove(".emptyClass");
	 if( dataList.length> 0  ){
		// $("#dept_item_0").append("<div class='block-right-header'>已选人员</div>");
		 for(var i = 0; i < dataList.length; i++){
			var item = dataList[i];
			var itemId = item.id;
			var name = item.name;
			if(itemId != ''){
				   $("#select_item_0").append("<a class='list-group-item'  style='text-align:center;cursor:pointer' item_id='"+itemId+ "' item_name='"+name+"' ><h6 class='list-group-item-heading' >"+ name+"&nbsp;<img title='移除'  class='list-item-remove' item_id='"+itemId+ "' item_name='"+name+"' src=\"/common/images/upload/remove.png\" onclick='remove0(this)'/>&nbsp;<img class='list-item-up' item_id='"+itemId+ "' item_name='"+name+"' src=\"up.png\"  title='上移' onclick='toUp(this)'/>&nbsp;<img class='list-item-down' item_id='"+itemId+ "' item_name='"+name+"' src=\"down.png\"  onclick='toDown(this)' title='下移'/></h6></a>");
			}
		}

	 }else{
			$("#select_item_0").append("<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关人员！</h6></div>");
	 }
}

function toUp(oo){
	var prev = $(oo).parent().parent().prev();
   if(!prev.hasClass("list-group-item-header")){
	   var obj = $(oo).parent().parent();
	   prev.before(obj);
   }
   resort();
}

function remove0(oo){
	//删除中间选人选项
	   remove_item(oo.getAttribute("item_id"), oo.getAttribute("item_name"),'1');
	   //删除自己
	   //$(this).remove();
	   resort();
}

function toDown(oo){
	var next = $(oo).parent().parent().next();
	   next.after($(oo).parent().parent());
	   resort();
}

/**
 * 获取本部门人员
 */
function getCurrentDeptPerson(){
	var url = contextPath +  "/orgSelectManager/getSelectUserByCurrentDeptWorkflow.action";
	var para = {prcsId:prcsId,frpSid:frpSid,moduleId:moduleId,privNoFlag:privNoFlag};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		dataLoad(dataList,jsonRs.rtMsg);
	}else{
		alert(jsonRs.rtMsg);
	} 
}



/**
 * 获取部门select字符串
 * uuid:部门id
 * isCheck:是否是勾选 0-勾选为false,1-勾选为true
 */
function getDeptStr(uuid,isCheckedState,name){
	var url = contextPath +  "/orgSelectManager/getSelectUserByDeptWorkFlow.action";
	var para = {deptId:uuid,isCheckedState:isCheckedState,prcsId:prcsId,frpSid:frpSid,moduleId:moduleId,privNoFlag:privNoFlag};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		selectDateInit(dataList,isCheckedState,name);
	}else{
		alert(jsonRs.rtMsg);
	} 
}

/**
 * 获取部门树
 */
function getDeptTree(){
	var param = "privNoFlag="+ privNoFlag +"&moduleId="+moduleId + "&prcsId=" + prcsId+"&frpSid"+frpSid ;
	var url =  "<%=contextPath %>/orgSelectManager/getSelectDeptTreeWorkFlow.action";
	var config = {
			zTreeId:"selectDeptZtree",
			requestURL:url,
           
           	checkController:{
				enable : true,
				chkboxType: { "Y": "s", "N": "s" }
               },
           	onClickFunc:deptOnClick,
			onCheckFunc:deptOnCheckFunc,
			async:false,
			asyncExtend:true,
			param:{privNoFlag:privNoFlag,moduleId:moduleId,prcsId:prcsId,frpSid:frpSid}
			
		};
	zTreeObj = ZTreeTool.config(config); 	
}

/**
 * 处理数据
 */
function selectDateInit(data,isCheckedState,name){
	var dataList = data;
	 $("a").remove(".right .list-group-item");
	 $("a").remove(".list-group-item-header");
	 $("div").remove(".block-right-item,.emptyClass");
	 if(dataList.length > 0){
		 $("#dept_item_0").append("<a class='list-group-item-header' style='padding:7px 15px;'>" + name + "</a>");
		 for(var i = 0; i < dataList.length; i++){
				var roleId = dataList[i].uuid;
				var name = dataList[i].userName;
				if(isCheckedState == '0'){//删除
	
					 remove_item(roleId, name);
				}else if(isCheckedState == '1'){
					add_item(roleId, name);
				}
			    $("#dept_item_0").append("<a class='list-group-item' style='text-align:center;cursor: hand;'  item_id='"+roleId+ "' item_name='"+name+"'><h6 class='list-group-item-heading'>"+ name+"</h6></a>");
			} 
			
	 }else{
		 if(!name){
				name = "已选人员";
			}
			$("#dept_item_0").append("<a  class='list-group-item-header' style='padding:7px 15px;;cursor:pointer'>" + name + "</a>"
					+"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关人员！</h6></div>");
	 }
	load_init();  
	load_init_item();
    //默认加载角色选中状态
    init_item('dept');
}
/**
 *  点击
 */
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	var deptName = treeNode.name;
	getDeptStr(uuid,'',deptName);
};

/**
 * 勾选
 */
function deptOnCheckFunc(event, treeId, treeNode) {
	var uuid = treeNode.id;
	var isChecked = treeNode.checked;
	
	var isCheckedState = "0";
	if(isChecked){
		isCheckedState = "1";
	}
	var deptName = treeNode.name;
	getDeptStr(uuid,isCheckedState,deptName);
};

</script>
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

	<!-- <div class="panel-group" id="accordion" style="width:260px;display:;">
	  <div class="panel panel-default">
	    <div class="panel-heading" style="padding:7px 15px;">
	      <h5 class="panel-title">
	        <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
	          	按部门选择
	        </a>
	      </h5>
	    </div>
	    <div id="collapseTwo">
	      <div class="panel-body in">
	          <div>
	     		  <ul id="selectDeptZtree" class="ztree" style="overflow-x:hidden;overflow-y:auto;border:0px;height:200px;"></ul>
			  </div>
	      </div>
	    </div>
	  </div>
	  <div class="panel panel-default">
	    <div class="panel-heading" style="padding:7px 15px;">
	      <h5 class="panel-title">
	        <a onclick="getRole()" data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
	          	按角色选择
	        </a>
	      </h5>
	    </div>
	    <div id="collapseThree" class="panel-collapse collapse">
		    <div class="panel-body">
		        <div class="list-group" id="roleList">

				</div>
			</div>
	    </div>
	  </div>
	  <div class="panel panel-default" >
	    <div class="panel-heading" style="padding:7px 15px;">
	      <h5 class="panel-title">
	        <a onclick="getGroup()" data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
	          	自定义组
	        </a>
	      </h5>
	    </div>
	    <div id="collapseFour" class="panel-collapse collapse">
	    	 <div class="panel-body">
		    	 <div class="list-group" id="userGroupList">
			  		<div class="panel-body" id="defaultGroup">

		      		</div>
				 </div>
			 </div>
	    </div>
	  </div> -->
	</div>
<div class="main-block" id="block_dept" style="display:block;top:0px;">
   <div class="right single" id="dept_item" style="width: 240px;overflow: auto">
   		<div style="padding-bottom: 5px;">
			<table >
			<tr>
				<td>
			    	<div class="input-group">
          		    <input class="form-control" style="float:left;;width:150px;" type="text" id="kword" name="kword" value="按用户名或姓名搜索..." onfocus="ctroltime=setTimeout(CheckSend,100);" onblur="clearTimeout(ctroltime);if(this.value=='')this.value='按用户名或姓名搜索...';"  >
            	    <div class="input-group-btn"  style="float:left;">
	            	  <button tabindex="-1" class="btn btn-default" type="button" value=""  id="search_icon">查询</button>
                   </div>
                   <div style="clear:both;"> </div>
                 </div>
			    </td>
			</tr>
			</table>
		</div>
		<div class="block-right" id="dept_item_0" >
	    </div>
	<div id="" align="center" style="margin-top:10px;height:40px;">
   		<input type="button" style="cursor:pointer;" class="btn btn-primary" value="确定" onclick="close_window();">&nbsp;&nbsp;
	</div>
 	</div>
 	<div class="select single" id="select_item" style="left: 515px;">
		<div class='list-group-item-header' style="padding:7px 15px;">已选人员</div>
		<div class="block-select" id="select_item_0">
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
	var text;
	$("body").on("mouseenter",".block-select .list-group-item",function(){
// 			text = $(this).find(".list-group-item-heading").text();
// 			$(this).find(".list-group-item-heading").text("删除");
	})
	$("body").on("mouseleave",".block-select .list-group-item",function(){
// 			$(this).find(".list-group-item-heading").text(text);
	});
</script>
</html>