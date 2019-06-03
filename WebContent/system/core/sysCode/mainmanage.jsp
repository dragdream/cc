<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>系统编码</title>

<script type="text/javascript">
function doInit(){
	
	//添加例子一
	var url = "<%=contextPath %>/sysCode/getSysPara.action";
	var jsonObj = tools.requestJsonRs(url);
	//alert(jsonObj);
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		jQuery.each(json, function(i, sysPara) {
		 	$("#tbody").append("<tr class='TableData'>"
					+"<td nowrap align='center'>" + sysPara.codeNo + "</td>"
					+"<td nowrap align='center'>" + sysPara.codeName + "</td>"
					+"<td nowrap align='center'>"
					 +"<a href='javascript:toMainPara(\"" + sysPara.sid+ "\")'>修改</a>"
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
	
	if(confirm("确认要删除此主编码！删除后将删除所有下级编码")){
		var url = "<%=contextPath %>/sysCode/delMainCode.action";
		var jsonRs = tools.requestJsonRs(url,{sid:sid});
		if(jsonRs.rtState){
			alert("删除成功！");
			var parent = window.parent;
			parent.location.reload();
		}else{
			alert(jsonRs.rtMsg);
		}
		
	}
	
}
/**
 * 新增或删除主编码
 */
function toMainPara(sid){
	//alert(uuid);
	var parent = window.parent.contentFrame;
	parent.location = "<%=contextPath%>/system/core/sysCode/addupdate.jsp?sid=" + sid;
		 
}
/**
 * 下级编码管理
 */
function toChild(sid,paraValue,paraName){
	var parent = window.parent.contentFrame;
	parent.location = "<%=contextPath%>/system/core/sysCode/childPara.jsp?paraValue=" + encodeURIComponent(paraValue) + "&sid=" + sid + "&paraName=" + encodeURIComponent(paraName);
}

</script>

</head>
<body onload="doInit()">
   <table border="0" width="100%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><img src="<%=imgPath%>/sys_config.gif" align="absmiddle">&nbsp;<span class="big3">主编码</span></td>
    </tr>
  </table><br>

 
   <table class="TableList" width="80%" align="center">
      <tbody id="tbody">
        <tr>
         <td colspan="3"> <a href="javascript:toMainPara('');">新增主编码</a></td>
        </tr>
        <tr class="TableHeader">
     <!--  		<td width="40" align="center">UUId</td> -->
      		<td nowrap align="center">编码编号</td>
     	    <td nowrap align="center">编码名称</td>
      		<td nowrap align="center">操作</td>
       </tr>
      </tbody>
   </table>

</body>
</html>