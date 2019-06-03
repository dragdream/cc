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

String isSingle = request.getParameter("isSingle") == null  ? "" : request.getParameter("isSingle");

//回调函数
String callBackPara = request.getParameter("callBackPara") == null ? "" : request.getParameter("callBackPara")  ;
callBackPara = callBackPara.replace("\"", "\\\"");
%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="cache-control" content="no-cache, must-revalidate"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/ztree.jsp"%>
<title>选择部门</title>
<link rel="stylesheet" href ="<%=cssPath %>/org_select.css"/>
<link href="<%=cssPath%>/selectControls.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/system/core/orgselect/orgselect.js?v=1"></script>
<style type="text/css">
/*** 定义全部添加和全部删除的样式  **/
li.list-group-item:hover{
	background-color: #f5f5f5;
	cursor: pointer;
}
li.list-group-item.active, li.list-group-item.active:hover, li.list-group-item.active:focus {
	background-color: #fff;
	cursor: pointer;
}

li.list-dept-item:hover{
	background-color: #f5f5f5;
	cursor: pointer;
}
li.list-dept-item.active, li.list-group-item.active:hover, li.list-group-item.active:focus {
	background-color: #fff;
	cursor: pointer;
}

	/* html{
		overflow-y: hidden;
	} */
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
	    height: 304px;
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
var deptId,deptName;
var moduleId = "<%=moduleId%>";
var isSingle = "<%=isSingle%>";//是否单个选择选择
objSelectType = '<%=objSelectType%>';

var callBackPara = "<%=callBackPara%>";
<%-- var privNoFlag = "<%=privNoFlag%>";
var privOp = "<%=privOp%>"; --%>

var parentWindowObj = xparent;
var to_id_field ;
var to_name_field ;

var single_select = false;
var ctroltime=null,key="";

function doInit(){
	$("#collapseTwo").addClass("panel-collapse collapse in");
	if(isSingle == '1'){
		single_select = true;
	}
	var deptRetNameArray = parentWindowObj["deptRetNameArray"];
	if (deptRetNameArray && (deptRetNameArray.length == 2 ||  deptRetNameArray.length == 3) ) {
	    var roleCntrl = deptRetNameArray[0];
	    var roleDescCntrl = deptRetNameArray[1];
	    deptId = parentWindowObj.document.getElementById(roleCntrl);
	    deptName = parentWindowObj.document.getElementById(roleDescCntrl);
	  }else {
		deptId = parentWindowObj.document.getElementById("dept");
	    deptName = parentWindowObj.document.getElementById("deptDesc");
	  }
	to_id_field = deptId;
	to_name_field = deptName;
    getDeptTree();
//     if(isSingle == '1'){//单选模式
//     	getDeptStr('');
//     }
  
	to_id_field_value = to_id_field.value;
	to_name_field_value = to_name_field.value;
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
	

}
/**
 * 获取部门select字符串
 */
function getDeptStr(uuid){
	
	return;
	var url = contextPath +  "/orgSelectManager/getSelectDept.action";
	var para = {deptId:uuid , moduleId:moduleId};
	var jsonRs = tools.requestJsonRs(url,para);
	
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		$("a").remove(".list-group-item");
	    for(var i = 0; i < dataList.length; i++){
			var roleId = dataList[i].uuid;
			var roleName = dataList[i].deptName;
			var deptNameStr = dataList[i].deptNameStr;//处理后部门名称
			var deptLevel = dataList[i].deptLevel;//级别
			var deptParentId = dataList[i].deptParentId;
			var roleIdObj = null;
			if(deptParentId){
				 roleIdObj = document.getElementById("select_id_" + deptParentId);
			}
			if(roleIdObj){

				var deptLevelTemp =  $(roleIdObj).attr("deptLevel");//获取级别
				//获取上级部门  and 获取同辈之后的所有元素 and 级别是同一级别的
				var temp = $(roleIdObj).nextAll("[deptLevel='"+ deptLevelTemp+"']").first();
				if(temp){
					temp.before("<a class='list-group-item active'  style='text-align:left;;cursor:pointer' id='select_id_" +roleId + "' item_id='"+roleId+ "' item_name='"+roleName+"'  deptLevel='"+deptLevel+ "'><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
				}else{//如果没有则在上级后面添加
					$(roleIdObj).after("<a class='list-group-item active'  style='text-align:left;;cursor:pointer' id='select_id_" +roleId + "' item_id='"+roleId+ "' item_name='"+roleName+"'><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
						
				} 
				 
			}else{
			    $("#dept_item_0").append("<a class='list-group-item active'  style='text-align:left;;cursor:pointer' id='select_id_" +roleId + "' item_id='"+roleId+ "' item_name='"+roleName+"' deptLevel='"+deptLevel+ "'><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
			}
		} 
	    
		if(dataList.length < 0){
			 $("#dept_item_0").append("<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关部门！</h6></div>");
		}
		
		load_init();  
		load_init_item();  
	    //默认加载角色选中状态
	    init_item('dept');
		$(".list-group-item").unbind( "click" ,remove_item);
		$(".list-group-item").unbind( "click" ,add_item);
	}else{
		alert(jsonRs.rtMsg);
	} 
}
/**
 * 初始化加载
 */
function doInitDept(){
	 for(var i = 0; i < id_field_array.length; i++){
		var item = id_field_array[i];
		var uuid = item.id;
		var deptNameStr = item.name;
	/* 	var uuid = tempObj.id;
		var deptNameStr = tempObj.name; */
		$("#dept_item_0").append("<a class='list-group-item dept-item active'  style='text-align:center;;cursor:pointer' id='select_id_" +uuid + "' item_id='"+uuid+ "' item_name='"+deptNameStr+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
		add_item(uuid, deptNameStr);
		checkOrNocheckNodesById(uuid ,true);
	}
	jQuery('#dept_item_0 .dept-item').on('click', function(){
		 checkOrNocheckNodesById(this.getAttribute("item_id") ,false);
		 remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
		 jQuery(this).remove();
		 
	}); 
	
}


/**
 * 初始化加载
 */
function doInitDept0(){
	 for(var i = 0; i < id_field_array.length; i++){
		var item = id_field_array[i];
		var uuid = item.id;
		var deptNameStr = item.name;
	/* 	var uuid = tempObj.id;
		var deptNameStr = tempObj.name; */
		if(deptNameStr!=''){//去除无部门的时候第一个有padding值显示的一块  等于空则什么也不添加
			$("#dept_item_0").append("<a class='list-group-item dept-item active'  style='text-align:center;;cursor:pointer' id='select_id_" +uuid + "' item_id='"+uuid+ "' item_name='"+deptNameStr+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
		 }
		add_item0(uuid, deptNameStr);
	}
	jQuery('#dept_item_0 .dept-item').on('click', function(){
		 checkOrNocheckNodesById(this.getAttribute("item_id") ,false);
		 remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
		 jQuery(this).remove();
		 
	}); 
	
}

/**
 * 选中项
 * @param item_id   Id
 * @param item_name  name
 * @param extend  扩展字段---比如人员在线状态
 */
function add_item0(item_id, item_name , extend)
{
	//var toIdFiledName  = to_id_field.name;//文档框名称
	var callBackFunc =   "ORG_SELECT_ADD_BACH_FUNC";//回调函数
	
	
	 arguments.length = 5;
     var callBackParaObj ;
     if(callBackPara && callBackPara != ''){
    	 callBackParaObj = tools.string2JsonObj(callBackPara);
     }

	if(objSelectType &&objSelectType == '1'){//当为多选框时
		getMultiple();
		/*to_id_field_value = item_id_multiple;
		to_name_field_value = item_id_multiple_name;*/
	}else if(objSelectType == '2'){//DIV
		
	}

	//var to_id_field_value = to_id_field.value;
   if(!item_id || !item_name)
      return;
   
   var item = {id:item_id , name:item_name};
   if(single_select)//单选
   {
	  id_field_array.length = 0;//清空数组
	
	  id_field_array.push(item);//添加数组
      to_id_field.value = item_id;
      to_name_field.value = item_name;
	  arguments[2] =  to_id_field.value;
	  arguments[3] = to_name_field.value;
	  arguments[4] = callBackParaObj;
      trigger_callback(callBackFunc, arguments  );
      return;
   }

   //添加 数组 页面文本属性
   var isAddItem = true;
   for(var i =0 ; i<id_field_array.length ; i++){//循环所选项
	   var itemTemp = id_field_array[i];
	   if(itemTemp.id == item_id){//如果相等
		   isAddItem = false;
		   break;
	   }
   }
   if(isAddItem){
	   id_field_array.push(item);//添加对象
	   
	   if(to_id_field.value == ""){// 等于空，直接赋值
		   to_id_field_value = item_id;
		   to_name_field_value = item_name;
	   }else{
		   if(to_id_field.value.substring(to_id_field.value.length - 1 , to_id_field.value.length) == ','){
			   to_id_field_value = to_id_field.value + item_id ;
			   to_name_field_value = to_name_field.value + item_name;
		   }else{
			   to_id_field_value = to_id_field.value + "," + item_id ;
			   to_name_field_value = to_name_field.value + "," + item_name;
		   }
	   }
   }
  
   to_id_field.value = to_id_field_value;
   to_name_field.value = to_name_field_value;

   /**
    * 判断是否存在此方法，转为多选人员，第三栏处理
    */
   if( window.dataSelectLoadInit ){  
	    dataSelectLoadInit(id_field_array);  
	 }   
   
  // arguments.push(to_id_field.value );
  // arguments.push(to_name_field.value );


   arguments[2] =  to_id_field.value;
   arguments[3] = to_name_field.value;
   arguments[4] = callBackParaObj;
   trigger_callback(callBackFunc, arguments );
}

function getDeptTree(){
	var url = "<%=contextPath %>/orgSelectManager/getSelectDeptTree.action?moduleId="+moduleId;
	var config = {};
	if(isSingle == '1'){
		 config = {//单选
					zTreeId:"selectDeptZtree",
					requestURL:url,
					param:{"moduleId":moduleId,isSingle:isSingle},
		           	onClickFunc:deptOnClick,
					async:false,
					onAsyncSuccess:doInitDept0
				};
	}else{
		 config = {//多选
				zTreeId:"selectDeptZtree",
				requestURL:url,
				param:{"moduleId":moduleId,isSingle:isSingle},
				checkController:{
					enable : true,
					chkboxType: { "Y": "", "N": ""}
	               },
	            onClickFunc:deptOnClick0,
	           	onCheckFunc:deptOnCheckFunc,
				async:false,
				onAsyncSuccess:doInitDept,
				
			};
	}
	
	zTreeObj = ZTreeTool.config(config);
	
}
function deptOnClick(event, treeId, treeNode) {
	 var uuid = treeNode.id;
	if(uuid.split(";").length == 1){
		add_item(treeNode.id,treeNode.name);
	} 
}
function deptOnClick0(event, treeId, treeNode) {
	if(treeNode.nocheck){
		return;
	}
	if(treeNode.checked){
		setCheckStatue(false,treeNode);
	}else{
		setCheckStatue(true,treeNode);
	}
	
	
}
/**
 * 勾选
 */
function deptOnCheckFunc(event, treeId, treeNode) {
	var uuid = treeNode.id;
	//console.log(treeNode);
	var isChecked = treeNode.checked;
	var deptNameStr = treeNode.name;
	var zTreeObj = $.fn.zTree.getZTreeObj(treeId);
	// = $.fn.zTree.getZTreeObj("selectDeptZtree");
	$(".search_result").slideUp(200);
	if(uuid.split(";")[1] == 'org'){//点击单位
		
		var allChildNote = zTreeObj.getNodesByParamFuzzy("name", "", treeNode);
		for(var i= 0  ;i <allChildNote.length ; i ++){
			var tempNote = allChildNote[i];
			if(tempNote.nocheck){
				continue;
			}
			setCheckStatue(isChecked ,tempNote);
		}
	}else{
		setCheckStatue(isChecked ,treeNode);
		var allChildNote = zTreeObj.getNodesByParamFuzzy("name", "", treeNode);
		for(var i= 0  ;i <allChildNote.length ; i ++){
			var tempNote = allChildNote[i];
			setCheckStatue(isChecked ,tempNote);
		}
	}
	

// 	getDeptStr(uuid,window.isCheckedState,deptName);
	
	//添加不嫩
	// $("#dept_item_0").append("<a class='list-group-item' href='javascript:void(0);' style='text-align:center'  item_id='"+uuid+ "' item_name='"+deptName+"'><h6 class='list-group-item-heading' >"+ deptName+"<i class='glyphicon glyphicon-remove' style='text-align:right;'></i></h6></a>");
	
		 
}
/**
 * 点击或者或者不选择状态
 */
function setCheckStatue(isChecked , treeNode){
	var uuid = treeNode.id;
	var deptNameStr = treeNode.name;
	
	if(isChecked){
		if(!existsId(uuid)){
			$("#dept_item_0").append("<a class='list-group-item dept-item active'  style='text-align:center;;cursor:pointer' id='select_id_" +uuid + "' item_id='"+uuid+ "' item_name='"+deptNameStr+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptNameStr+"</h6></a>");
			add_item(uuid, deptNameStr);
			jQuery('#dept_item_0 .dept-item').on('click', function(){
				 checkOrNocheckNodesById(this.getAttribute("item_id") ,false);
				 remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
				 jQuery(this).remove();
				 
			});
		}
	}else{
		//if(existsId(uuid)){
			 remove_item($("#select_id_" +uuid).attr("item_id"),$("#select_id_" +uuid).attr("item_name"));
			 $("#select_id_" +uuid).remove();
			
		//}
	}
	checkOrNocheckNodesById(uuid , isChecked);
}
/**
 * 判断是否存在此部门id
 */
function existsId( id ){
	var existsObj = false;
	var obj = $("#select_id_" +id)[0];
	if(obj){
		existsObj = true;
	}
	return existsObj;
}
/**
 * 批量删除
 */
function removeDeptAll(){
	var zTreeObj = $.fn.zTree.getZTreeObj("selectDeptZtree");
	jQuery('#dept_item_0 .dept-item').each(function(){
		remove_item(this.getAttribute("item_id"), this.getAttribute("item_name"));
		 jQuery(this).remove();
	});
	zTreeObj.checkAllNodes(false);//取消所有节点
}
/**
 * 取消部门树打勾状态
  id:ID
  check：取消选中
 */
function checkOrNocheckNodesById(id , check){
	var zTreeObj = $.fn.zTree.getZTreeObj("selectDeptZtree");
	var node = zTreeObj.getNodeByParam("id",id,null);
	if(node){
		if(check){
			zTreeObj.checkNode(node, true,true); 
		}else{
			zTreeObj.checkNode(node, false,true); 
		}
		
	}
}
	
	
/**
 * 按条件查询部门
 */
function CheckSend1(){
	var kword = document.getElementById("kword");
	var search_icon = document.getElementById("search_icon1");
	  if(kword.value=="按部门名称搜索...")
	     kword.value="";
	  if(kword.value=="" && search_icon.innerHTML =="查询"){
		  search_icon.innerHTML =="查询";
	  }
	  if(key!=kword.value && kword.value!=""){
	     key=kword.value;
	     doSearch(key);
	     
	     if(search_icon.innerHTML =="查询"){   
	    	 search_icon.innerHTML ="清除";
	    	 search_icon.title="清除关键字";
	    	 search_icon.onclick=function(){
	    		 $(".search_result").slideUp(200);
	    		 kword.value='按部门名称搜索...';
	    		 search_icon.innerHTML ="查询";
	    		 search_icon.title="";
	    		 search_icon.onclick=null;
	    		 };
	     }
	  }
	  
	  ctroltime=setTimeout(CheckSend1,100);
}



/***
 * 按部门id和部门名称模糊查询
 */
function doSearch(name){
	var url = contextPath +  "/orgSelectManager/getSelectDeptByDeptName.action";
	var para = {dept:name};
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var dataList = jsonRs.rtData;
		dataLoad1(dataList,"部门查询");
		//dataLoad(dataList,jsonRs.rtMsg);
	}else{
		alert(jsonRs.rtMsg);
	} 
}

/***
 * 加载数据 
 * datList: 人员列表
 * type ：判断是否需要初始化右侧项目 1-不需要 其他-需要
 */
function dataLoad1(dataList,name, type){
	 $("a").remove(".right .list-group-item");
	 $("a").remove(".list-group-item-header");
	 $("div").remove(".block-right-item,.emptyClass");
	 if(dataList.length > 0){
		 if(isSingle == '1'){//单选
				single_select = true;
			 $("#dept_item_0").append("<a  class='list-group-item-header' style='padding:8px 15px;cursor:pointer'>" + name + "</a>");
			
			 for(var i = 0; i < dataList.length; i++){
					var roleId = dataList[i].uuid;
					var item = dataList[i];
					var itemId = item.id;
					var name = dataList[i].deptName;
					
					 if(roleId != ''){
						 $("#dept_item_0").append("<a class='list-group-item' style='text-align:center;cursor:pointer'  item_id='"+roleId+ "' item_name='"+name+"'><h6 class='list-group-item-heading'>"+ name +"</h6></a>");
					 }  
				 }
			 
		 }else{
			var arr = [];
			for(var i = 0,l=dataList.length;i<l;i++){
				arr.push("<li uuid="+dataList[i].uuid+" deptName="+dataList[i].deptName+" class='search_result_li' style='list-style:none;text-indent:20px;color:#000;'><a class='' uuid="+dataList[i].uuid+">"+dataList[i].deptName+"</a></li>");
			};
			$(".search_result").empty();
			$(".search_result").append(arr.join(""));
			$(".search_result").slideDown(200);
			 
			 $("li").remove(".list-group-item");
		     $("#dept_item_0").append("<a  class='list-group-item-header' style='padding:8px 15px;cursor:pointer'>" + '已选部门'+ "</a>");
		     $("#dept_item_0").append("<li  id='removeDeptAll' onclick='removeDeptAll();'  class='list-group-item' style='text-align:center;;cursor:pointer'>全部删除</li>");
		     doInitDept();
		 };
 
	 }else{
		 /* if(!name){
			name = "已选部门";
		}  */
		//$("li").remove(".list-group-item");
		 if(isSingle == '1'){
				single_select = true;
				$("li").remove(".list-group-item");
				 $("#dept_item_0").append("<a   class='list-group-item-header'  style='padding:8px 15px;;cursor:pointer'>" + name + "</a>"
							+"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关部门！</h6></div>");
		 }else{
			 
		$(".search_result").empty();
		$(".search_result").append("<a   class='list-group-item-header'  style='padding:8px 15px;;cursor:pointer'>" + name + "</a>"
				+"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关部门！</h6></div>");
		$(".search_result").slideDown(200);
		$("li").remove(".list-group-item");
		$("#dept_item_0").append("<a  class='list-group-item-header' style='padding:8px 15px;cursor:pointer'><input type='checkbox' id='isCheckAll' onclick='checkAll(this)'/>" + '已选部门'+ "</a>");
		$("#dept_item_0").append("<li  id='removeDeptAll' onclick='removeDeptAll();'  class='list-group-item' style='text-align:center;;cursor:pointer'>全部删除</li>");
		doInitDept();
		 }
		/* $("#dept_item_0").append("<a   class='list-group-item-header'  style='padding:8px 15px;;cursor:pointer'>" + name + "</a>"
			+"<div align='center' class='emptyClass' style='padding-top:5px;'><h6>无相关人员！</h6></div>"); */
		
	 }
/* 	 //处理多选人员。点击删除
     if(type && type == '1'){
    	 alert(type);
    	 dataSelectLoadInit(dataList); 
     } */
	
 	load_init();  
	load_init_item();  
    //默认加载角色选中状态
   init_item('dept'); 
}


</script>

<style>
.search_result li:hover{
	background-color:#aaa;
	text-indent:20px;
	color:#fff;
}
.search_result li a{
	display:inline-block;
	width:100%;
	height:100%;
	padding:10px 5px;
}
.search_result li a:hover{
	background-color:#aaa;
	text-indent:20px;
	color:#fff;
	text-decoration:none;
	
}
</style>
</head>

<body onload="doInit()" >
<div style="margin-top:5px;margin-left:5px;">
	<div class="titlebar">
		<img src="<%=contextPath %>/system/core/orgselect/icon_dept.png" alt="" class='title1_img'/>
		<div class="titleBlock">
			<p class="title">选择部门</p>
			<ul class="tab">
				<li class='tabMenu tabActive'>按部门选择</li>
			</ul>
		</div>
	</div>
	<div class='panel-group'>
		<ul class='tabList' id="accordion" >
			<li class="tabContent" style="display:block;">
				<ul id="selectDeptZtree" class="ztree">
				</ul>
			</li>

		</ul>
	</div>

	
	</div>
 <div class="main-block" id="block_dept" style="display:block;top:0px;">
    <div class="right single" id="dept_item">
    	<div style="padding-bottom: 5px;">
		<table >
		<tr>
			<td>
		    	 <div class="input-group clearfix" style='position:relative;'>
          		    <input class="form-control" style="float:left;;width:190px;" type="text" id="kword" name="kword" value="按部门名称搜索..." onfocus="ctroltime=setTimeout(CheckSend1,100);" onblur="clearTimeout(ctroltime);if(this.value=='')this.value='按部门名称搜索...';" >
            	    <div class="input-group-btn" style="float:left;">          	
	            	  <button tabindex="-1" class="btn btn-default" type="button"  value=""  id="search_icon1">查询</button>
                   </div>
                   
               		<ul class="search_result" style='position:absolute;z-index:999;overflow-y:auto;overflow-x:hidden;margin-left:-2px;border:1px solid #e2e2e2;border-top:none;padding-left:0px;top:100%;left:0;width:248px;height:313px;background-color:#fff;display:none;'>
               		
               		</ul>
                   
                 </div>
		    </td>
		</tr>
		</table>
	</div>
    
    
		<div class="block-right" id="dept_item_0">
		
			<a  class='list-group-item-header'  style="padding:8px 15px;;cursor:pointer">已选部门</a>
				 <%if(!isSingle.equals("1")){ %>
			   <!--  <li href='javascript:void(0);' id="addDeptAll" class="list-group-item " style="text-align:center;">全部添加</li> -->
				<li  id="removeDeptAll" onclick="removeDeptAll();" class="list-group-item" style="text-align:center;;cursor:pointer">全部删除</li>
			   <%} %> 
			</div>
		<!-- <div class="block-right" id="dept_item_0">
	    </div>  -->
		<div id="" align="center" style="margin-top:10px;height:40px;">
		   <input type="button" class="btn btn-primary" value="确定" onclick="close_window();">&nbsp;&nbsp;
		</div>
  </div>
</div>
</div>
</body>

<script>
$("body").on("click",".search_result_li",function(){
	deptId= $(this).attr('uuid');
	deptName= $(this).attr('deptName');
	$(".search_result").empty();
	$(".search_result").hide();
	$(".form-control").val("按部门名称搜索...");
	//$("#dept_item_0").append("<a class='list-group-item dept-item active' style='text-align:center;cursor:pointer;display:none;'  item_id='"+''+ "' item_name='"+''+"'><h6 class='list-group-item-heading'>"+ '' +"</h6></a>");
	//$("#dept_item_0").append("<a class='list-group-item dept-item active'  style='text-align:center;;cursor:pointer' id='select_id_" +deltId + "' item_id='"+deptId+ "' item_name='"+deptName+"' deptLevel=''><h6 class='list-group-item-heading'>"+ deptName+"<i class='glyphicon glyphicon-remove' style='text-align:right;'></i></h6></a>");
	var oUl = $(".block-right").find(".dept-item");
	if(oUl.length==0){
		$("#dept_item_0").append("<a class='list-group-item dept-item active' style='text-align:center;cursor:pointer' id='select_id_"+deptId+"' item_id='"+deptId+ "' item_name='"+deptName+"'><h6 class='list-group-item-heading'>"+ deptName +"</h6></a>");
		add_item(deptId, deptName);
		checkOrNocheckNodesById(deptId, true);

	}else{
		var check1 = false;
		for(var i=0,l=oUl.length;i<l;i++){
			if($(oUl[i]).attr("item_id")==deptId){
				return;
			} else{
				check1 = true;
				
			}; 
		};
		if(check1){
			$("#dept_item_0").append("<a class='list-group-item dept-item active' style='text-align:center;cursor:pointer' id='select_id_"+deptId+"' item_id='"+deptId+ "' item_name='"+deptName+"'><h6 class='list-group-item-heading'>"+ deptName +"</h6></a>");
			add_item(deptId, deptName);
		};
			checkOrNocheckNodesById(deptId, true);
	};

});

var check1 = 0;//判断是否为空  空收回面板  显示已选择数据
$(".form-control").keyup(function(){
	if($(this).val()==""){
		$(".search_result").slideUp(200);
		//$(".search_result").empty();
		check1++;
		
	}
});

$(".tabMenu").click(function(){
	var index = $(this).index();
	$(".tabMenu").removeClass('tabActive');
	$(this).addClass("tabActive");
	$(".tabContent").hide();
	$(".tabContent").eq(index).show();
});
</script>
</html>