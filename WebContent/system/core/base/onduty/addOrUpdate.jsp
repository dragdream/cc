<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  //获取分类主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
  String curDateStr=request.getParameter("curDateStr");
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作事项办理</title>
<style>
.table{
	border-collapse:  collapse;
    border:  1px  solid  #f2f2f2;
    width:90%;
    margin-left:  0px;
}
.table  tr{
	line-height:35px;
	border-bottom:1px  solid  #f2f2f2;
}
.table  tr  td:first-child{
	text-indent:10px;
}
.table  thead tr:first-child  td{
	font-weight:bold;
}
.table thead tr:first-child{
	border-bottom:2px  solid  #b0deff!important;
	background-color:  #f8f8f8;  
}
</style>
<script>
var curDateStr='<%=curDateStr%>';
function doInit(){
	dutyType();
}

function goback(){
	window.location = contextPath+"/system/subsys/cowork/list.jsp";
}

//提交
function commit(){
	if(check()){
		//var para = tools.formToJson($("#form1"));
		var childId=$("input[name='childId']");
		var childUserId=$("input[name='childUserId']");
		var deptId=$("#deptId").val();
		var userId=$("#userId").val();
		var typeId=$("#typeId").val();
		var childStr="";
		if(childId!=null && childId.length>0){
			for(var i=0;i<childId.length;i++){
				childStr+=$(childId[i]).val()+";"+$(childUserId[i]).val()+",";
			}
		}
		
		var para={childStr:childStr,deptId:deptId,userId:userId,typeId:typeId,curDateStr:curDateStr};
		var url="<%=contextPath%>/teePbOnDutyController/addOrUpdateDuty.action";
		var json=tools.requestJsonRs(url,para);
		return json.rtState;
	}
}

//值班类型
function dutyType(){
	var url="<%=contextPath%>/teePbDutyTypeController/findDutyTypeList.action";
	var json=tools.requestJsonRs(url);
	var row=json.rows;
	if(row!=null && row.length>0){
		var html="";
		for(var i=0;i<row.length;i++){
			html="<option value='"+row[i].sid+"'>"+row[i].typeName+"</option>";
		   $("#typeId").append(html);
		}
	}
}

//值班类型的字段
function findField(){
	$("#typeIdTr").nextAll().remove();
	var typeId=$("#typeId").val();
	var url="<%=contextPath%>/teePbTypeChildController/findFieldAll.action";
	var json=tools.requestJsonRs(url,{typeId:typeId});
	var row=json.rows;
	if(row!=null && row.length>0){
		for(var i=0;i<row.length;i++){
			var userUuid="userUuid"+(i+1);
			var userName="userName"+(i+1);
			var html="<tr>";
				html+="<td style='text-indent: 10px'><input name='childId' type='hidden' value='"+row[i].sid+"'/>"+row[i].name+"：</td>";
				html+="<td>";
					html+="<input name='childUserId' id='"+userUuid+"' type='hidden' value=''></input>";
					html+="<input name='"+userName+"'  id='"+userName+"' class='BigInput' readonly='readonly'/>";
					html+="<a href='javascript:void(0);' class='orgAdd'  id='selectSingleUserZhiBan"+i+"'>选择</a>&nbsp;&nbsp;";
					html+="<a href='javascript:void(0);' class='orgClear' onClick=\"clearData('"+userUuid+"','"+userName+"')\">清空</a>&nbsp;&nbsp;";
				html+="</td>";
			html+="</tr>";
			$(".TableBlock").append(html);
			var userss = { userUuid: userUuid, userName: userName };
			$("#selectSingleUserZhiBan"+i).click(userss,function(even){//获取值班司室部门ID  根据这个条件找到本部门下的人员
				var userDeptId=$("#deptId").val(); 
				if(userDeptId==null || userDeptId==""){
					alert("请先选中值班部门", 'error');
					return;
				}
				//根据选中的值班司室  获取相应的本部门下的所有部门和人员
			    selectSingleUser([even.data.userUuid,even.data.userName],'2',userDeptId);
	         });
		}
		
	}
}

function check(){
	var deptName=$("#deptName").val();
	if(deptName==null || deptName==""){
		alert("请添加值班部门！");
		return false;
	}
	var userName=$("#userName").val();
	if(userName==null || userName==""){
		alert("请添加值班领导！");
		return false;
	}
	var typeId=$("#typeId").val();
    if(typeId=="0"){
    	alert("请选择值班类型");
    	return false;
    }else{
    	var childUserId=$("input[name='childUserId']");
    	if(childUserId!=null && childUserId.length>0){
    		for(var i=0;i<childUserId.length;i++){
    			if($(childUserId[i]).val()==null || $(childUserId[i]).val()==""){
    				alert("请选择值班人");
    				return false;
    			}
    		}
    		
    	}
    }
    return true;
}
</script>
</head>

<body onload="doInit()" style="margin-left:2%;width:96%;">
<div id="toolbar" class = "topbar clearfix">
	   <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_tkgl.png">
		  <span class="title">安排值班信息</span>
	   </div>
	   <div class = "right fr clearfix">
	     <!--  <input type="button" class="btn-win-white fl" onclick="add()" value="保存"/> -->
	   </div>
	</div>
<div style="width:100%;">
<form id="form1">
   <input type="hidden" name="sid" value="<%=sid %>"/>
   <table class="TableBlock" style="width: 100%;background-color: white;" >
      <tr>
         <td style="text-indent: 10px">值班部门：</td>
         <td>
	        <input type="hidden" name="deptId" id="deptId"/>
			<input class="BigInput  readonly easyui-validatebox" required name="deptName"  id="deptName" readonly/>
			<a href="javascript:void(0)" onclick="selectSingleDept(['deptId','deptName'])">添加</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="orgClear" onClick="clearData('deptId','deptName')">清空</a>
         </td>
      </tr>
       <tr>
         <td style="text-indent: 10px">带班领导：</td>
         <td>
            <input type="hidden" name="userId" id="userId"/>
			<input class="BigInput  readonly easyui-validatebox" required name="userName"  id="userName" readonly/>
			<a href="javascript:void(0)" onclick="selectSingleUser(['userId','userName'])">添加</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="orgClear" onClick="clearData('userId','userName')">清空</a>
         </td>
      </tr>
     <tr id="typeIdTr">
         <td style="text-indent: 10px">值班类型：</td>
         <td>
           <select name="typeId" id="typeId" onchange="findField();">
             <option value="0">--选择值班类型--</option>
           </select>
         </td>
      </tr>
      
   </table>
</form>
</div>
</body>
</html>