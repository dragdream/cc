
var type = getQueryString("type");

function doInit(){
    
	//创建组织机构树
	getDeptTree();
	
	//触发事件
	$(".dom").click(function(){
		$(".dom").removeClass("li_active");
		$(this).addClass("li_active");
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
    });
}

//加载树  
var zTreeObj = null;
function getDeptTree(){
	var url = "/orgManager/checkOrg.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		if(json.sid){ 
			//var url = "/deptManager/getOrgDeptTree.action";
			var url = "/xzfy/organTree/getFyTree.action"
			var config = {
				zTreeId:"orgZtree",
				requestURL:url,
				param:{"para1":"111"},
				onClickFunc:deptOnClick,
				onAsyncSuccess:onDeptAsyncSuccess
			};
			zTreeObj = ZTreeTool.config(config);
		}else{
			$.MsgBox.Alert_auto("单位信息未录入，请您先填写单位信息！");
			return;
		}
	}
 }
 
 
//异步执行成功后
function onDeptAsyncSuccess(event, treeId, treeNode, msg) {
	expandNodes(); 
}
//第一级展开部门
function expandNodes() {
	 if(!zTreeObj){
		zTreeObj = $.fn.zTree.getZTreeObj("orgZtree"); 
	 }
	var nodes = zTreeObj.getNodes();
	zTreeObj.expandNode(nodes[0], true, false, false);
	if (nodes[0].isParent && nodes[0].zAsync  && nodes[0].id =='0') {//是第一级节点
		expandNodes(nodes[0].children);
	}

}

/**
 * 点击节点
 */
function deptOnClick(event, id, treeNode) {
	
	var treeId = treeNode.id;
	var treeName = treeNode.name;
	if(treeId.split(";").length == 2){
		var url = "";
		//组织机构
		if( type == 1){
			//url = "/xzfy/jsp/organ/organlist.jsp?treeId=" + treeId.split(";")[0]
			//    + "&treeName=" + escape(treeName);
			
			url = "/xzfy/jsp/organ/organlist.jsp?treeId=" 
				  + treeId + "&treeName="+escape(treeName);
		}//组织机构人员
		else{
			url = "/xzfy/jsp/organperson/personlist.jsp?treeId=" + treeId.split(";")[0]
			    + "&treeName=" + escape(treeName);
		}
		$(window.parent.$("#frame0")).attr("src", url);
	}
}



