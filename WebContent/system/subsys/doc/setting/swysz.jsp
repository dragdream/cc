<%@page import="com.tianee.webframe.util.auth.TeeFunctionControl"%>
<%TeeFunctionControl.distinguishTheVersion(request,response);%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	TeePerson loginPerson = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<style>
</style>
<script>
var list;
var deptMap = {};
var pidMap = {};
function doInit(){
	//messageMsg("该页面主要用来设置收文部门所对应的收文人员","msg","info");
   
	var json = tools.requestJsonRs(contextPath+"/deptManager/getDeptTreeAll.action",{});
	var rows = json.rtData;
	for(var i=0;i<rows.length;i++){
		if(!rows[i].children){
			rows[i].children = [];
		}
		if(!pidMap[rows[i].pId]){
			pidMap[rows[i].pId] = [];
		}
		//将其加入到集合中
		pidMap[rows[i].pId].push(rows[i]);
		
		if(!deptMap[rows[i].pId]){//如果其父节点不存在，则进入到顶级节点中
			deptMap[rows[i].id] = rows[i];
		}else{//如果找到了父节点，则将当前节点以及之前节点加入父节点中
			deptMap[rows[i].id] = rows[i];
			var pNode = deptMap[rows[i].pId];
			if(!pNode.children){
				pNode.children = [];
			}
			var array = pidMap[rows[i].pId];
			for(var j=0;j<array.length;j++){
				pNode.children.push(array[j]);
				array[j].hasParent = true;
			}
			pidMap[rows[i].pId] = [];
		}
	}
	
	//清空掉有父节点的数据
	for(var node in deptMap){
		if(!deptMap[node].hasParent){
			renderDept(0,deptMap[node]);
		}
	}
	
	
 /* 	tools.requestJsonRs(contextPath+"/docRecMapping/list.action",{},true,function(json){
		var render = [];
		list = json.rtData;
		for(var i=0;i<list.length;i++){
			render.push("<tr>");
			render.push("<td>"+list[i].deptName+"</td>");
			render.push("<td>"+list[i].privUserNames+"</td>");
			render.push("<td><a href='javascript:void(0);' onclick=\"del("+i+")\">删除</a></td>");
			render.push("</tr>");
		} 
		$("#tBody").append(render.join(""));
	});  */
	
}

function renderDept(level,node){
	var children = node.children;
	
	//渲染node
	var render = [];
	render.push("<tr>");
	render.push("<td class='TableData'>");
	var blank = "";
	var icon = contextPath+"/common/jquery/ztree/css/zTreeStyle/img/diy/node_dept.gif";
	if(node.iconSkin=="pIconHome"){
		icon = contextPath+"/common/jquery/ztree/css/zTreeStyle/img/diy/1_open.png";
	}
	icon = "<img src='"+icon+"' />&nbsp;&nbsp;";
	for(var i=0;i<level;i++){
		blank+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if(children.length!=0){
		render.push(blank+icon+node.title);
	}else{
		render.push(blank+icon+node.title);
	}
	render.push("</td>");
	var deptId=node.id;
	var deptName=node.title;
	//根据部门的id 获取该部门的收文员设置
	var url=contextPath+"/docRecMapping/getDocRecMappingByDeptId.action";
	var json=tools.requestJsonRs(url,{deptId:deptId});
	var data=json.rtData;
	if(data!=null&&data!=""){
		render.push("<td class='TableData'>"+data.privUserNames+"</td>");
		render.push("<td class='TableData'><a href='javascript:void(0);' onclick=\"addOrUpdate('"+data.uuid+"',"+data.deptId+",'"+data.deptName+"','"+data.privUserNames+"','"+data.privUserIds+"')\">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		render.push("<a href='javascript:void(0);' onclick=\"javascript:del('"+data.uuid+"');\">取消</a></td>");	
	}else{
		
		render.push("<td class='TableData'></td>");
		render.push("<td class='TableData'><a href='javascript:void(0);' onclick=\"addOrUpdate('0',"+deptId+",'"+deptName+"','"+""+"','"+""+"')\">添加</a>");
		render.push("</td>");
		
		
	}	
	$("#tBody").append(render.join(""));
	//渲染子节点
	for(var i=0;i<children.length;i++){
		renderDept(level+1,children[i]);
	}
}



function commit(){
	if($("#deptId").val()==""){
		alert("请选择对应部门！");
		return;
	}
	if($("#privUserIds").val()==""){
		alert("请选择收文人员！");
		return;
	}
	
	var json = tools.requestJsonRs(contextPath+"/docRecMapping/addOuUpdate.action",{deptId:$("#deptId").val(),privUserIds:$("#privUserIds").val()});
	if(json.rtState){
		alert("添加成功！");
		window.location.reload();
	}else{
		alert(json.rtMsg);
	}
	
}

function del(uuid){
	 if(window.confirm("确定要删除该收文规则吗？")){
		tools.requestJsonRs(contextPath+"/docRecMapping/delete.action",{uuid:uuid});
		window.location.reload();
	} 
}


function check(){
	if($("#dId").val()==""){
		alert("请选择对应部门！");
		return false;
	}
	if($("#privIds").val()==""){
		alert("请选择收文人员！");
		return false;
	}
	 return true;
}
function commit2(){
	//获取存放到隐藏域中的uid
	var uuid=$("#uid").val();
	if(check()){
		if(uuid=="0"){//添加
			var json = tools.requestJsonRs(contextPath+"/docRecMapping/addOuUpdate.action",{deptId:$("#dId").val(),privUserIds:$("#privIds").val()});
			if(json.rtState){
				alert("添加成功！");
				//$('#addDiv').modal('hide');
				window.location.reload();
			}else{
				alert(json.rtMsg);
			}	
			
		}else{//编辑
			
			var json = tools.requestJsonRs(contextPath+"/docRecMapping/addOuUpdate.action",{deptId:$("#dId").val(),privUserIds:$("#privIds").val(),uuid:uuid});
			if(json.rtState){
				alert("更新成功！");
				//$('#addDiv').modal('hide');
				window.location.reload();
			}else{
				alert(json.rtMsg);
			}	
	
		}
		
	}
	
	
	
	
	
}
//添加或者编辑
function addOrUpdate(uuid,deptId,deptName,privUserNames,privUserIds){
	$('#addDiv').modal('show');
	
	$("#dDesc").val(deptName);
	$("#dId").val(deptId);
	$("#uid").val(uuid);
	
	$("#uDesc").val(privUserNames);
	$("#privIds").val(privUserIds);
}

</script>
</head>
<body onload="doInit()" style="margin:5px;">
<div class="moduleHeader">
	<b>收文员设置</b>
</div>
<center id="msg">
</center>
<center>
	<!-- <table style="width:500px;font-size:12px" class="TableBlock">
		<tr>
			<td class="TableData" style="background:#f0f0f0">选择部门</td>
			<td class="TableData">
				<input type="hidden" name="deptId" id="deptId"/>
				<input type="text" name="deptDesc" id="deptDesc" class="readonly BigInput" readonly />
				<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptDesc'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('deptId','deptDesc')">清除</a>
			</td>
		</tr>
		<tr>
			<td class="TableData" style="background:#f0f0f0">选择收文人员</td>
			<td class="TableData">
				<input type="hidden" name="privUserIds" id="privUserIds"/>
				<textarea  name="userDesc" id="userDesc" class="readonly BigTextarea" readonly style="width:300px;height:100px"></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['privUserIds','userDesc'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('privUserIds','userDesc')">清除</a>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2">
				<center>
					<button class="btn btn-default" onclick="commit()">添加</button>
				</center>
			</td>
		</tr>
	</table> 
	
	<hr/>-->
	
	<table class="TableBlock" style="width:800px;font-size:12px" border="1px">
		<thead>
			<tr>
				<td class="TableData" style="background:#f0f0f0;width:200px"><b>部门</b></td>
				<td class="TableData" style="background:#f0f0f0"><b>收文人员</b></td>
				<td class="TableData" style="background:#f0f0f0;width:130px"><b>操作</b></td>
			</tr>
		</thead>
		<tbody id="tBody">
			
		</tbody>
	</table>
	
</center>



    
<div class="modal fade" id="addDiv" style="display:none;">
  <div class="modal-dialog"  style="width:550px">
    <div class="modal-content">
    
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">收文员设置</h4>

      <div class="modal-body" style="font-size:12px;">
        <form id="form2" name="form2" method="post">
			<table  style="width:100%;font-size:12px" id="" class="TableBlock"  >
		<tr>
			<td class="TableData" style="background:#f0f0f0">部门</td>
			<td class="TableData">
			    <input type="hidden" name="uid" id="uid"/>
				<input type="hidden" name="dId" id="dId"/>
				<input type="text" name="dDesc" id="dDesc" class="readonly BigInput" readonly />
			</td>
		</tr>
		<tr>
			<td class="TableData" style="background:#f0f0f0">收文人员</td>
			<td class="TableData">
				<input type="hidden" name="privIds" id="privIds"/>
				<textarea  name="uDesc" id="uDesc" class="readonly BigTextarea" readonly style="width:300px;height:100px"></textarea>
				<a href="javascript:void(0)" onclick="selectUser(['privIds','uDesc'])">选择</a>
				&nbsp;
				<a href="javascript:void(0)" onclick="clearData('privIds','uDesc')">清除</a>
			</td>
		</tr>
		<tr>
			<td class="TableData" colspan="2">
				<center>
					<button type="button" class="btn btn-default" onclick="commit2()">保存</button>
					<button class="btn btn-default" data-dismiss="modal">取消</button>
				</center>
			</td>
		</tr>
			</table>	
		</form>
		</div>  
     </div>
    </div>
  </div>
</div>

</body>
</html>