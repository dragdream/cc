<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<title>人员查询</title>
<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>

<script type="text/javascript">
function doInit(){
	//获取角色
	selectUserPrivList('userRoleStr','1');
	//部门
	getDeptParent();
}

/**
 * 导出
 */
function extDept(){
	
	
 	// var pars = Form.serialize($('form1'));
 	var para =  tools.formToJson($("#form1")) ;
 	var param=tools.jsonObj2String(para);
 	<%-- window.location.href = "<%=contextPath %>/orgImportExport/exportToCsv.action?"+para; --%>
 	$("#exportIframe").attr("src",contextPath+"/orgImportExport/exportToCsv.action?param="+param);
}
/**
 * 查询中
 */
function doQuery(){
	
	var url = "<%=contextPath %>/adminPerson/queryPerson.action";
	var para =  tools.formToJson($("#form1")) ;
	var jsonObj = tools.requestJsonRs(url , para);
	
	if(jsonObj.rtState){
		$("#personList").empty();//清空
		$("#fromSearch").hide();//隐藏查询条件
		$("#personList").show();//显示人员列表
		var dataList = jsonObj.rtData;
		if(dataList.length > 0){
			var tableStr = "<table width='90%' class='TableBlock_page' align='center'>"
		    	 + "<tbody id='tbody'>";
		  
		    	 tableStr = tableStr + "<tr class='TableData' style='line-height:35px;border-bottom:1px solid #f2f2f2;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8;'>"
			      	 + "<td width='40' align='center'>主部门</td>"
			      	 +"<td nowrap align='center'>用户名</td>"
			     	 +"<td nowrap align='center'>用户姓名</td>"
			     	 +"<td nowrap align='center'>性别</td>"
			      	 +"<td nowrap align='center'>主角色</td>"
			      	 +"<td nowrap align='center'>管理范围</td>"
			      	 +"<td nowrap align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<dataList.length ; i++){
				var person = dataList[i];
				var uuid = person.uuid;
				var postPriv = person.postPriv;
				var postPrivStr ="";
				var sexStr = "男";
				if(person.sex == '1'){
					sexStr = "女";
				}
				var fontStr = "";
				if(person.notLogin && person.notLogin == '1'){
					fontStr = "gray";
				}else if(person.passwordIsNUll == '1'){
					fontStr = "red";
				}
				if( postPriv == '1'){
					postPrivStr =  "全体";
				}else if(postPriv == '2'){
					postPrivStr = "指定部门";
				}else{
					postPrivStr = "本部门";
				}	
				var deptName = "";
				if(person.deptIdName){
					deptName = person.deptIdName
				}
				tableStr = tableStr +"<tr class='TableData' style='line-height:25px;'>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ deptName +"</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + person.userId + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + person.userName + "</font></td>"
				     	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + sexStr + "</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + person.userRoleStrName + "</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + postPrivStr + "</font></td>"
				      	 +"<td nowrap align='center'>"
				      	 +"<a href='#' onclick='deletePerson(\"" + uuid + "\",this);'> 离职 </a>"
				      	 +"&nbsp;&nbsp;<a href='#' onclick='toAddUpdatePerson(\"" + uuid + "\");'> 编辑 </a>"
				      	 +"&nbsp;&nbsp;<a href='javascript:void(0);' onclick='clearPassword(\"" + uuid + "\");'>清空密码 </a>"
				      	 +"</td>"
				         +"</tr>";
				
		
			}
			tableStr = tableStr + "</tbody></table>";
			$("#personList").append(tableStr);	
		}else{
		 	messageMsg("没有查询到相关人员", "personList" ,'' ,280);
		}
		var buttonStr =  "<center style=\"padding-top:10px;\"><input style='width:45px;height:25px;' type='button' class='btn-win-white' value='返回' onclick='window.location.reload();'/></center>";
	 	$("#personList").append(buttonStr);
	}else{
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
}

/**
 * 批量删除人员
 */
function deletePerson(uuid,obj){
	$.MsgBox.Confirm ("提示", "确定要将所选中人员更改为离职状态吗？",function(){
		var url = contextPath +  "/personManager/updateDelPersonByUuids.action?uuids=" + uuid;
		var para = {};
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			$.MsgBox.Alert_auto("操作成功！",function(){
				
			$(obj).parent().parent().remove();
			});
		}else{
			$.MsgBox.Alert_auto(jsonRs.rtMsg);
		}
	});
}

/**
 * 获取部门
 */
function getDeptParent(){
	var url =  "<%=contextPath %>/deptManager/getDeptTreeAll.action";
		var config = {
				zTreeId:"deptIdZTree",
				requestURL:url,
	           	onClickFunc:onclickDept,
				async:false
			};
		zTreeObj = ZTreeTool.config(config);
		setTimeout('setDeptParentSelct()',500);
} 
/**
 * 初始化后选中节点,上级部门
 */
function setDeptParentSelct(){
	ZTreeObj = $.fn.zTree.getZTreeObj(ZTreeTool.zTreeId);
    if(ZTreeObj == null){
    	setTimeout('setDeptParentSelct()',500);
    }else{
    	ZTreeObj.expandAll(true);
    	 var node = ZTreeObj.getNodeByParam("id",$("#deptId").val(),null);
    	    if(node){
    	    	ZTreeObj.selectNode(node);
    	  }
    }  
    ZTreeTool.inputBindZtree(ZTreeTool.zTreeId,'deptId','');
}

//点击树执行事件
function onclickDept (event, treeId, treeNode) {
	$("#deptIdName").val(treeNode.name);
	$("#deptId").val(treeNode.id);
	ZTreeTool.hideZtreeMenu();
}
</script>
</head>
<body onload="doInit()" style="padding: 10px;overflow-x:hidden;margin-left: 5px;">
<div id="fromSearch">

<form action="" method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="90%" align="center">
   <tr>
    <td nowrap colspan="2" style='vertical-align: middle;font-family: MicroSoft YaHei;font-size: 14px;'><img src="<%=contextPath %>/common/zt_webframe/imgs/common_img/icon_yhcx.png">&nbsp;&nbsp; <span>用户查询</span></td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="150px;">用户名：</td>
    <td nowrap class="TableData">
        <input type="text" name="userId" id="userId" class="BigInput" size="20" newMaxLength="20">&nbsp;
    </td>
   </tr>
  <tr>
    <td nowrap class="TableData">真实姓名：</td>
    <td nowrap class="TableData">
        <input type="text" name="userName" id="userName" class="BigInput" size="10" newMaxLength="10">&nbsp;
    </td>
   </tr>
 <tr>
    <td nowrap class="TableData">别名：</td>
    <td nowrap class="TableData">
        <input type="text" name="byname" id="byname" class="BigInput" size="10" newMaxLength="10">&nbsp;
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">性别：</td>
    <td nowrap class="TableData">
        <select style="height: 25px;" name="sex" id="sex" class="BigSelect">
        <option value=""></option>
        <option value="0">男</option>
        <option value="1">女</option>
        </select>
    </td>
   </tr>
     <tr>
    <td nowrap class="TableData">主部门：</td>
    <td nowrap class="TableData">
       <!--  <select name="deptId" id="deptId" class="BigSelect">
        <option value=""></option>
        </select> -->
		<input id="deptId" name="deptId"  type="text" style="display:none;"/>
		<input type="text" id="deptName" name="deptName" style="font-family:MicroSoft YaHei;"/>
		 <span class='addSpan'>
		         	<img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/add.png" onclick="selectSingleDept(['deptId','deptName'],'1')" value="选择"/>
			        <img src="<%=contextPath %>/common/zt_webframe/imgs/xtgl/zzjggl/yhgl/clear.png"  onclick="clearData('deptId','deptName')" value="清空"/>
		 </span>
		<!-- <ul id="deptIdZTree" class="ztree" style="margin-top:0; width:247px; display:none;"></ul> -->
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">主角色：</td>
    <td nowrap class="TableData">
        <select style="height: 25px;" name="userRoleStr" id="userRoleStr" class="BigSelect">
           <option value=''></option>
        </select>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" >管理范围：</td>
    <td nowrap class="TableData">
        <select style="height: 25px;" name="postDeptStr" id="postDeptStr" class="BigSelect">
          <option value=""></option>
          <option value="0">本部门</option>
          <option value="1">全体</option>
          <option value="2">指定部门</option>
        </select>
    </td>
   </tr>
    <tr>
    <td nowrap class="TableData">是否允许登录OA系统：</td>
    <td nowrap class="TableData">
    	  <select style="height: 25px;" name="notLogin" id="notLogin" class="BigSelect">
          <option value=""></option>
          <option value="0">允许</option>
          <option value="1">禁止</option>
        </select>
      </td>
   </tr>
  <tr>
    <td nowrap class="TableData">是否允许查看用户列表：</td>
    <td nowrap class="TableData">
    	  <select style="height: 25px;" name="notViewUser" id="notViewUser" class="BigSelect">
          <option value=""></option>
          <option value="0">允许</option>
          <option value="1">禁止</option>
        </select>
     </td>
   </tr>
   <tr>
    <td nowrap class="TableData">是否允许显示桌面：</td>
    <td nowrap class="TableData">
    	  <select style="height: 25px;" name="notViewTable" id="notViewTable" class="BigSelect">
          <option value=""></option>
          <option value="0">允许</option>
          <option value="1">禁止</option>
        </select>
    </td>
   </tr> 
   <!-- <tr>
    <td nowrap class="TableData">考勤排班类型：</td>
    <td nowrap class="TableData">
        <select name="dutyType" id="dutyType" class="BigSelect">
        <option value=""></option>
        </select>
    </td>
   </tr> -->
  <!--  <tr style="display:none">
    <td nowrap class="TableData" width="120">按最后登录时间排序：</td>
    <td nowrap class="TableData">
        <select name="lastVisitTime" id="lastVisitTime" class="BigSelect">
          <option value=""></option>
          <option value="desc">降序</option>
          <option value="asc">升序</option>
        </select>
    </td>
   </tr> -->
   <tr>
    <td nowrap colspan="2" align="center" style="text-align: center;">
        <input style="width: 45px;height: 25px;" type="button" value="查询" class="btn-win-white" onclick="doQuery();" title="查询用户" name="button">&nbsp;&nbsp;
        <input  style="width: 45px;height: 25px;" type="button" value="导出" class="btn-win-white" onClick="extDept();" title="导出用户信息" name="button">
    </td>
  </table>
</form>
<br>
</div>

<div id="personList" style="display:none;">
</div>


 <iframe id="exportIframe" style="display:none"></iframe>
</body>
</html>