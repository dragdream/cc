<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
	<% 
	int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>图书类别管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">
var sid=<%=sid%>;
function doInit(){
   if(sid>0){
	   selectById();
   }
}

function selectById(){
	 var url = "<%=contextPath%>/bookManage/selectById.action";
	 var jsonRs = tools.requestJsonRs(url,{sid:sid});
	 var data=jsonRs.rtData;
	 if(jsonRs.rtState){
	      $("#sid").val(sid);
	      $("#typeName").val(data.typeName);
	 }
}
<%-- function toEdit(id,name){
	
	var typeName = encodeURIComponent(name);
	var url = "<%=contextPath%>/system/subsys/booksManagement/bookType/editBookType.jsp?typeName="+typeName+"&sid="+id;
	location.href = url;
} 

function toDelete(id){
	var url = "<%=contextPath%>/bookManage/deleteType.action";
	var para =  {sid:id};
	if(confirm('确实要删除该图书分类吗?注：删除后会级联删除该分类下的所有图书，慎重！')){	
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			alert(jsonRs.rtMsg);
			location.reload();
		}
	}
}--%>
/**
 * 校验
 */
function checkFrom(){
     /* var check = $("#form1").form('validate'); 
     if(!check  ){
         return false; 
     } */
    var typeName= $("#typeName").val();
     if(typeName=="" || typeName==null){
    	 return false;
     }
     return true;
}

function doSave(){
	 if(checkFrom()){
		  var url = "<%=contextPath%>/bookManage/addType.action";
		    var para =  tools.formToJson($("#form1")) ;
		    var jsonRs = tools.requestJsonRs(url,para);
		    if(jsonRs.rtState){
		        alert(jsonRs.rtMsg);
		        window.close();
		    }
	 } 

}
</script>

</head>
<body onload="doInit();" style="background-color: #f4f4f4;">
<!-- <div class="base_layout_top" style="position:static">
	<span class="easyui_h1">图书类别管理</span>
</div> -->
<form action=""  method="post" name="form1" id="form1" style="background-color: #f4f4f4;">
<br>
	<table align="center" width="500px" class="TableBlock">
	  <tr>
	      <td nowrap class="TableData"> 类别名称：</td>
	      <td class="TableData">
	          <input type="hidden" id="sid" name="sid" vlaue="0"></input>
	          <input type="text" name="typeName" id="typeName" style="width:250px;height:22px;"  class="BigInput" value="">
	      </td>
	  </tr>
    </table>
     <!-- <tr>
        <td nowrap class="TableData" colspan="4" align="center">
		    <input id="button" type="button" value="添加" onclick="doSave();" class="btn btn-success"/>
	    </td>
     </tr>
<br>
</form>
	<table align="center" width="500px" class="TableBlock">
	 	<tr class="TableHeader">
		    <td nowrap align="center" width="70%">图书类别</td>
		    <td nowrap align="center" width="30%">操作</td>
	    </tr>
	    <c:forEach items="${bookTypeList}" var="bookTypeListSort" varStatus="bookTypeListStatus">
	        <tr class="TableLine">
      			<td align="center" width="70%">${bookTypeList[bookTypeListStatus.index].typeName}</td>
     			<td align="center" nowrap width="30%">
     	    		<a href="javascript:toEdit('${bookTypeList[bookTypeListStatus.index].sid}','${bookTypeList[bookTypeListStatus.index].typeName}');">编辑</a>
          			<a href="javascript:toDelete('${bookTypeList[bookTypeListStatus.index].sid}');">删除</a>
		      	</td>
		    </tr>
		</c:forEach>
    </table> -->
  </form>
</body>
</html>
