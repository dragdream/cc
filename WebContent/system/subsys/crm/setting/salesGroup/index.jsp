<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%-- <link href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css" rel="stylesheet"/>
 --%>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script>
 
<script>

function doInit(){
	//getProductsType();
	
	var url = "<%=contextPath %>/teeCrmSalesGroupController/getAllGroup.action";
	var jsonRs = tools.requestJsonRs(url);
	if(jsonRs.rtState){
		var prcs = jsonRs.rtData;
		if(prcs.length > 0){
			for(var i = 0 ; i < prcs.length ; i++){
				var prc = prcs[i];
				var sid = prc.sid;
				var groupName = prc.groupName;
				
				var parentPath = prc.parentPath;//级别
				var parentId = prc.parentId;
				var roleIdObj = null;
				if(parentId){
					 roleIdObj = document.getElementById("" + parentId);
				}
				var level = parentPath.split("\/");
				if(parentPath){
					level = (level.length-1) * 30;
				}
				
				parentPath = "parent_" + parentPath;
			
				var tr = "<tr id='"+ sid + "'  parentPath='" + parentPath + "'>"
					+	"<td class='TableData' style='padding-left:"+level+"px;'> " + prc.groupName + "</td>"
					+	"<td class='TableData'> " + prc.parentName + "</td>"
					+	"<td class='TableData'  align='center'> " + prc.groupOrder + "</td>"
					+	"<td class='TableData'> " + prc.managerUserName + "</td>"
					+	"<td class='TableData'> " + prc.managerMemberNames + "</td>"
					+	"<td class='TableData'>"
						+	"<a href='#;' onclick='toAddOrUpdate(1 , " + sid + " )' >编辑</a>&nbsp;&nbsp; "
						+	"<a href='#;' onclick='deleteById( " + sid +")'>删除</a> "
						+	"</td>"
					+ 	"</tr>";
				if(roleIdObj){

					var deptLevelTemp =  $(roleIdObj).attr("parentPath");//获取级别
					//获取上级部门  and 获取同辈之后的所有元素 and 级别是同一级别的
					var temp = $(roleIdObj).nextAll("[parentPath='"+ deptLevelTemp+"']").first();
					//alert(deptLevelTemp +":"+ temp.length)
					if(temp[0]){
						temp.before(tr);
					}else{//如果没有则在上级后面添加
						$(roleIdObj).after(tr);		
					}  
				}else{
				    $("#tbody").append(tr);
				}
				
			}
		}
	}else{
		alert(jsonRs.rtMsg);
	}
}

/**
 * 获取产品类型
 */
function getProductsType(){
	var url = "<%=contextPath %>/teeCrmSalesGroupController/getGroupTree.action";
	var config = {
			zTreeId:"orgZtree",
			requestURL:url,
			param:{"para1":"111"},
			onClickFunc:onClickFunc,
			async:false,
			onAsyncSuccess:onDeptAsyncSuccess
			
		};
	zTreeObj = ZTreeTool.config(config);

}
function onDeptAsyncSuccess(event, treeId, treeNode, msg) {//执行成功后
	
}
function onClickFunc(event, treeId, treeNode) {
	if (treeNode && treeNode.onRight) {
		var id = treeNode.id;
		var name = treeNode.name;
		var value = id.split(";")[0] ;
		
		var menus = [{name:'新增',action:function(uuid,name){
			toAddOrUpdate(0 , uuid , name);
		},extData:[value,name]}
		,{name:'编辑',action:function(uuid , name){
			toAddOrUpdate(1 , uuid , name);
		},extData:[value,name]}
		,{name:'删除',action:function(uuid){
			deleteById( uuid );
		},extData:[value,name]}];
		$.TeeMenu(menus,{left:event.pageX,top:event.pageY,width:71,height:80});
	}
	window.event.cancelBubble = true;
}

/**
 * 点击触发事件 
   type ： 类型 0-新增  1-编辑 - 2 删除
 */
function  toAddOrUpdate(type , sid , name){
	$('#productTypeModal').modal('toggle');
	$("#parentId").val(sid);
	$("#parentIdName").val("");
	$("#groupName").val("");
	$("#groupOrder").val("");
	$("#managerUserId").val("");
	$("#managerUserName").val("");
	$("#managerMemberIds").val("");
	$("#managerMemberNames").val("");
	
	if(type == 1){
		var url = "<%=contextPath %>/teeCrmSalesGroupController/getById.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if(jsonRs.rtState){
			var prc = jsonRs.rtData;
			bindJsonObj2Cntrl(prc);
		}else{
			alert(jsonRs.rtMsg);
		}
	}
	getParentProductTypeTree(type, sid);
}
/**
 * 删除
 */
function deleteById(sid){
	if(confirm("确认要删除此销售组吗！删除后将删除下级销售组！")){
		var url = "<%=contextPath %>/teeCrmSalesGroupController/deleteById.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if(jsonRs.rtState){
			top.$.jBox.tip('删除成功', 'info' , {timeout:1500});
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}


/**
 *  新增或者更新
 */
function addUpdateType(){
	if(checkForm()){
		var url = "<%=contextPath %>/teeCrmSalesGroupController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.jBox.tip('保存成功','info',{timeout:1000});
			window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

function checkForm(){
	return $("#form1").form('validate');
}


//上级产品类型 ------
/**
 * 获取上级产品类型
 type ： 类型 0-新增  1-编辑 
 sid: id
 */
function getParentProductTypeTree(type , sid){
	$("#parentProductTypeZtree").empty();


	var url = "<%=contextPath %>/teeCrmSalesGroupController/getParentGroupTree.action";
	var param = {sid : sid , type:type};
	var config = {
		zTreeId:"parentProductTypeZtree",
		requestURL:url,
		param:param,
		onClickFunc:onClickProductTypeFunc,
		async:false,
		onAsyncSuccess:onProductTypeAsyncSuccess
	};
	var zTreeObjType = ZTreeTool.config(config);
}
/**
 * 点击上级产品类型触发事件
 */
function onClickProductTypeFunc(event, treeId, treeNode, msg) {
	$("#parentIdName").val(treeNode.name);
	$("#parentId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}
function onProductTypeAsyncSuccess(event, treeId, treeNode, msg) {//执行成功后
	var deptName = "";
	ZTreeObj = $.fn.zTree.getZTreeObj("parentProductTypeZtree");
	if(ZTreeObj == null){
		onProductTypeAsyncSuccess();
	}else{
		///ZTreeObj.expandAll(true);全部展开
		 var node = ZTreeObj.getNodeByParam("id",$("#parentId").val(),null);
		    if(node){
		    	ZTreeObj.selectNode(node);
		    	deptName = node.name;
		  }
	}  
	//alert(deptName)
	ZTreeTool.inputBindZtree("parentProductTypeZtree",'parentId',deptName);
}
</script>

</head>
<body onload="doInit()" style="">
 <div class="Big3" style="padding:6px;">
 	销售组管理
 </div>
 
 <div style="" align="center">
 	<input type="button" class="btn btn-primary" data-toggle="modal" data-target="#productTypeModal" value="新增销售组"  onclick="toAddOrUpdate(0,0);">
   	<!-- Modal -->
   	<form id="form1" name="form1" method="post">
		<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		  <div class="modal-dialog"  style="width:550px;">
		    <div class="modal-content">
		    	 <div class="modal-header">
		        	 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		       		<h4 class="modal-title" id="myModalLabel">新建销售组</h4>
		   	
		      	</div> 
		      <div class="modal-body">
		          <table  class="TableBlock" width="520px" align="center">
					    <tr>
					        <td nowrap class="TableData" width="100"  align="left"> 销售组名称：</td>
					        <td class="TableData" align="left"">
					      	    <input type="text" id="groupName" name="groupName" size="20" maxlength="200"  class="BigInput easyui-validatebox" required="true" value="">
					        </td>
					    </tr>
					     <tr>
					        <td nowrap class="TableData" width="100"  align="left"> 排序号：</td>
					        <td class="TableData"  align="left">
					      	    <input type="text" id="groupOrder" name="groupOrder" size="20" maxlength="9"  class="BigInput easyui-validatebox" value="0" validType="integeZero[]">
					        </td>
					    </tr>
					   <tr>
					        <td nowrap class="TableData" width="100" align="left"> 负责人：</td>
					        <td class="TableData"  align="left">
					      	     <input type="hidden" name="managerUserId" id="managerUserId" value=""/>
					        	 <input cols=30 name="managerUserName" id="managerUserName" rows=2 class="SmallStatic  easyui-validatebox BigInput" wrap="yes" readonly  />
					        	 <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleUser(['managerUserId','managerUserName'])">选择</a>
					        	 <a href="javascript:void(0);" class="orgClear" onClick="clearData('managerUserId','managerUserName')">清空</a>
					        </td>
					    </tr>
					    <tr>
					        <td nowrap class="TableData" width="100"  align="left"> 成员：</td>
					        <td class="TableData"  align="left">
					      	     <input type="hidden" name="managerMemberIds" id="managerMemberIds" value=""/>
					        	 <textarea rows="4"  cols="40"   name="managerMemberNames" id="managerMemberNames" class="SmallStatic  easyui-validatebox BigTextarea" wrap="yes" readonly ></textarea>
					        	 <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['managerMemberIds','managerMemberNames'])">选择</a>
					        	 <a href="javascript:void(0);" class="orgClear" onClick="clearData('managerMemberIds','managerMemberNames')">清空</a>
					        </td>
					    </tr>

					    <tr class="TableData"  align="left">
							<td nowrap align="left">上级销售组：</td>
							<td nowrap align="left">
							
								<input id="parentId" name="parentId"  type="text" style="display:none;" />		
								<ul id="parentProductTypeZtree" class="ztree" style="margin-top:0; width:247px; display:none;border: 1px solid #617775;background: #ffffff;width:220px;height:200px;overflow-y:scroll;overflow-x:auto;"></ul>

							<span style=""> &nbsp;&nbsp;<a href="javascript:void(0);" class="" 
								onClick="clearData('parentId','parentIdName')">清空</a></span>
								
			                  </td>
						</tr>
						
					    <tr >
					    	<td colspan="2"   align="center">
					    	 <div class="" >
					    	 	<input id="sid" name="sid" type="hidden" value="0">
					    	 	
		       				 	<button type="button" class="btn btn-primary" onclick="addUpdateType();">保存</button>
					    		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					    	</div>
					    	</td>
					    </tr>
				 </table>
		      </div>

		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div>
	 </form>
 </div>
 <ul id="orgZtree" class="ztree" style="overflow:auto;border:0px;margin-top:0px;"></ul>


<div align="center">
  <table class='TableList' width="95%">
  	 <tr class="TableHeader">
  	 	<td>销售组名称</td>
  	 	<td style="width:150px;">上级销售组</td>
  	 	<td style="width:80px;">排序</td>
  	 	<td style="width:100px;">负责人</td>
  	 	<td>成员</td>
  	 	<td style="width:100px;">操作</td>
  	 </tr>
  	 <tbody id="tbody">
  	 	
  	 </tbody>
  </table>
</div>
</body>
</html>