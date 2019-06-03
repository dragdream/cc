<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  //获取分类主键
  int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作事项办理</title>
</head>
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
caption, th, td {
    text-align: center;
    font-weight: normal;
    }
</style>
<script>
var sid=<%=sid%>;
//初始化方法
function doInit(){
	if(sid>0){
		findDutyTypeId(sid);
	}
	  $("body").on("click","a",function(){
		  $(this).parent().parent().remove();
	 });	
	
}

//提交
function commit(){
	    var nameArr=$("input[name='name']");
	    var seaseArr=$("input[name='sease2']");
	    var nameStr="";
	    for(var i=0;i<nameArr.length;i++){
	    	nameStr+=$(nameArr[i]).val()+";"+$(seaseArr[i]).val()+",";
	    }
	    var typeName=$("#typeName").val();
	    var sease=$("#sease").val();
		var url=contextPath+"/teePbDutyTypeController/addOrUpdateDutyType.action";
		var json=tools.requestJsonRs(url,{sid:sid,nameStr:nameStr,typeName:typeName,sease:sease});
		return json.rtState;
}
//检验
function check(){
	var contentFk=$("#contentFk").val();
	if(contentFk=="" || contentFk==null){
		$.MsgBox.Alert_auto("请填写办理情况！");
		return false;
	}
	return true;
}
//反馈情况列表
function findDutyTypeId(sid){
	$("#tbody2").val("");
	var url=contextPath+"/teePbDutyTypeController/findDutyTypeById.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	var data=json.rtData;
	if(data!=null){
		$("#typeName").val(data.typeName);
		$("#sease").val(data.sease);
		var childModel=data.childModel;
		if(childModel!=null && childModel.length>0){
			for(var i=0;i<childModel.length;i++){
				$("#tbody2").append("<tr><td><input name='name' value='"+childModel[i].name+"' type='text' style='height: 25px;width:90%;'/></td><td><input value='"+childModel[i].sease+"' name='sease2' type='text' style='height: 25px;width:90%;'/></td><td><a href='javascript:void(0);'>删除</a></td></tr>");
			}
		}
	}
}


function add(){
	$("#tbody2").append("<tr><td><input name='name' type='text' style='height: 25px;width:90%;'/></td><td><input name='sease2' type='text' style='height: 25px;width:90%;'/></td><td><a href='javascript:void(0);'>删除</a></td></tr>");
}


</script>
<body onload="doInit()" style="margin-left:2%;width:96%;">
<div id="toolbar" class = "topbar clearfix">
	   <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/exam/imgs/icon_tkgl.png">
		  <span class="title">新增/编辑值班类型</span>
	   </div>
	   <div class = "right fr clearfix">
	     <!--  <input type="button" class="btn-win-white fl" onclick="add()" value="保存"/> -->
	   </div>
	</div>
	<div id="bgjl" style="font-size: 14px;font-weight: bold;margin-top: 10px;">基础信息
<hr style="width:100%;"></hr>
<div style="width:100%;">
<form id="form1">
   <input type="hidden" name="sid" value="<%=sid %>"/>
   <table class="TableBlock" style="width: 100%;background-color: white;" >
      <tr>
         <td style="text-indent: 10px">值班类型：</td>
         <td>
            <input type="text" name="typeName" id="typeName" style="width: 80%;height: 23px;"/>
         </td>
      </tr>
       <tr>
         <td style="text-indent: 10px">值班描述：</td>
         <td>
            <input type="text" name="sease" id="sease" style="width: 80%;height: 23px;"/>
         </td>
      </tr>
    
   </table>
</form>
</div>
<div>
<div id="bgjl" style="font-size: 14px;font-weight: bold;margin-top: 10px;">字段管理
<div style="float:right;"><input class="btn-win-white fl" style="margin-bottom: 2px;" type="button" value="添加" onclick="add();"/></div>
<hr style="width:100%;"></hr>
<div style="margin-top: 10px;">
<table cellpadding="0" cellspacing="0" border="1" style="width: 100%" class="table">
   <thead>
     <tr>
       <td nowrap class="TableData" style="width:45%;">字段名称</td>
       <td nowrap class="TableData" style="width:45%;">字段描述</td>
       <td nowrap class="TableData" style="width:10%;">操作</td>
     </tr>
   </thead>
<tbody id="tbody2">
    
</tbody>
</table>
</div>
</div>
</div>
</body>
</html>