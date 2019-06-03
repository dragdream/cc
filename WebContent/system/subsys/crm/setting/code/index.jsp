<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style type="text/css">
html{
	padding:5px 0px 0px 5px;
}
</style>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/common/jqueryui/jquery.layout-latest.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script>

function doInit(){
	$("#layout").layout({auto:true});
	$("#group").group();
	changePage(1);
	getCrmList();
}

function changePage(sel , sid){
	if(sel==1){
		$("#frame0").attr("src",contextPath+"/system/subsys/crm/setting/code/blank.jsp");//外出
	}else if(sel==2){
		$("#frame0").attr("src",contextPath +"/system/subsys/crm/setting/code/addupdate.jsp?sid=" + sid);//请假
	}
}
function getCrmList(){
	//添加例子一
	var url = "<%=contextPath %>/crmCode/getSysPara.action";
	var jsonObj = tools.requestJsonRs(url);
	//alert(jsonObj);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		$("#tbody").empty();
		jQuery.each(json, function(i, sysPara) {
		 	$("#tbody").append("<tr class='TableData'>"
					+"<td nowrap align='center'>" + sysPara.codeNo + "</td>"
					+"<td nowrap align='center'>" + sysPara.codeName + "</td>"
					+"<td nowrap align='center'>"
					 +"<a href='javascript:changePage(2,\"" + sysPara.sid+ "\")'>修改</a>"
					 +"&nbsp;&nbsp;<a id='menu-a-" + sysPara.sid+ "' href='#'>下一级</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteSysPara(\"" + sysPara.sid + "\")'>删除</a>"
					 +"</td>"
		  	+ "</tr>");
		 	$("#menu-a-" + sysPara.sid).bind("click",function(){
		 		toChild(sysPara.sid,sysPara.codeNo,sysPara.codeName);
			});
		});

	//}

	}else{
		alert(jsonObj.rtMsg);
	}
}


/**
 * 删除
 */
function deleteSysPara(sid){
	
	if(confirm("确认要删除此CRM主编码！删除后将删除所有下级编码")){
		var url = "<%=contextPath %>/crmCode/delMainCode.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if(jsonRs.rtState){
			alert("删除成功！");
			var parent = window.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
	}
}

/**
 * 下级编码管理
 */
function toChild(sid,paraValue,paraName){
	$("#frame0").attr("src", "<%=contextPath%>/system/subsys/crm/setting/code/childPara.jsp?paraValue=" + encodeURIComponent(paraValue) + "&sid=" + sid + "&paraName=" + encodeURIComponent(paraName));
}
</script>

</head>
<body onload="doInit()" style="overflow:hidden;font-size:12px;">
<div id="layout" >
	<div layout="west" width="430" style="overflow:auto;">
		<br>
		<div id="group" class="list-group">
		    <table class="TableList" width="80%" align="center">
		        <tr>
		            <td colspan="3"> <a href="javascript:changePage(2,'');">新增CRM主编码</a></td>
		        </tr>
		        <tr class="TableHeader">
		     <!--  		<td width="40" align="center">UUId</td> -->
		      		<td nowrap align="center">编码编号</td>
		     	    <td nowrap align="center">编码名称</td>
		      		<td nowrap align="center">操作</td>
		       </tr>
		      <tbody id="tbody">
		       
		      </tbody>
		   </table>
		</div>
	</div>
	<div layout="center" style="padding-left:10px">
		<iframe id="frame0" frameborder=0 style="width:100%;height:100%"></iframe>
	</div>
</div>
</body>
</html>