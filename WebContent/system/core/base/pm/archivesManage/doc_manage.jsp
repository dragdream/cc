<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="">
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
		.li_active{
			background-color:#fff;
		}
</style>
<script>

function doInit(){
	changePage('<%=contextPath%>/system/core/base/pm/archivesManage/comfireNo.jsp');
	getDeptTree();
	
	$(".dom").click(function(){
		$(".dom").removeClass("li_active");
		$(this).addClass("li_active");
	})
}
function  changePage(url){
	$("#frame0").attr("src", url);
}

/**
 * 马上加载
 */
 var zTreeObj ;
 function getDeptTree(){
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/deptManager/getOrgDeptTree.action";
				var config = {
						zTreeId:"orgZtree",
						requestURL:url,
						param:{"para1":"111"},
						onClickFunc:deptOnClick,
						async:true,
						onAsyncSuccess:onDeptAsyncSuccess
						
					};
				zTreeObj = ZTreeTool.config(config);
			}else{
				$.MsgBox.Alert_auto("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
 }
 function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//异步执行成功后

	// expandNodes(); 
 }
 /**
   *第一级展开部门
   */
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("orgZtree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
	}
/* 	alert(nodes[0].id)
	for (var i=0, l=nodes.length; i<l; i++) {
		zTreeObj.expandNode(nodes[i], true, false, false);
		if (nodes[i].isParent && nodes[i].zAsync ) {
			expandNodes(nodes[i].children);
		}
	} */
}

/**
 * 点击节点
 */
function deptOnClick(event, treeId, treeNode) {
	var uuid = treeNode.id;
	if(uuid.split(";").length == 2){
		changePage("<%=contextPath%>/system/core/base/pm/archivesManage/humanIndex.jsp?uuid=" + uuid.split(";")[0]+"&name="+encodeURI(treeNode.name)) ;
	}
}

/**
 * 设置菜单
 */
function setMenu(){
	$('#teemenulist ul li a').click(function(even){
	    if($(this).parent().children("div").is(':hidden')){//上级（LI）下面存在UL标签且为隐藏的 ，都展开，否则反之
			$(this).parent().children("div").show('slow');//显示   
			this.className = "aMenuVisited";
	     }else{
	    	$(this) .parent().children("div").hide('slow');//隐藏	
	    	this.className = "aMenulink";//css({'class':'aMenuVisited'});
	      } 
	   
	}).css({'cursor':'pointer'});
	 isFirst = true;
	//$("#teemenu ul li div").hide();//隐藏下级所有节点
}

function importDept(){
	changePage('import.jsp');
}
/**
 * 导出
 */
function exportDept(){
	var url = "<%=contextPath%>/orgImportExport/exportDept.action";
	window.location.href = url;
	
}

/**
 * 人事档案导入
 */
 function toImport(){
		changePage("/system/core/base/pm/archivesManage/importpd.jsp");
	}

</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
	<div layout="west" style="overflow-y:auto;overflow-x:hidden;position:absolute;left:0px;top:0px;bottom:0px;width:230px;background-color:#eaedf2;">
		<div id="group" class="list-group">
		 	<div class="panel-group" id="accordion" style=" padding-top: 10px;">
				  <div class="panel panel-default">
				    <div class="panel-heading menuList">
				      <h4 class="panel-title" style="font-size: 16px;">
				       <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
				          	组织架构	
				       </a>	       
				      </h4>
				    </div>
				    </br>
				    <div id="collapseOne" class="panel-collapse collapse in">
				      	<div class="panel-body">
				       		<ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;width:100%;height:100%;min-height:330px; padding:2px;"></ul>
				   	    </div>
				    </div>
				  </div>
				  <br />
		 <div class="panel panel-default" >
             <div class="panel-heading menuList dom">
               <h4 class="panel-title" style="font-size: 16px;">
                 <a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#" onclick="toImport();">
       		             人事档案导入
                 </a>
               </h4>
            </div>
          </div>
				  
			</div>
		  </div>

		  
	</div>
	<div layout="center" style="position:absolute;left:230px;top:0px;bottom:0px;right:0px;">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>


</body>
</html>