<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<title>系统界面设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css"/>
<style type="text/css">

</style>
<script type="text/javascript" src="<%=contextPath %>/common/colorPicker/colorPicker.js"></script>
<script type="text/javascript">
/**
 * 保存
 */
function doSave(){
	 if (checkFrom()){
		 getfont();
		var url = "<%=contextPath %>/interfaceController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//alert(jsonRs.rtMsg);
			$.messager.show({
				msg : '保存成功！！',
				title : '提示'
			});
		}else{
			alert(jsonRs.rtMsg);
			
		}
		
	} 
	
	
	
}

function checkFrom(){
	return $("#form1").form('validate'); 
	//return true;
}
function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
  
function toNewWebMail(){
	var url = "<%=contextPath %>/system/core/mail/webMailCustom.jsp";
	window.location = url;
}

function toEdit(sid){
	var url = "<%=contextPath %>/system/core/mail/webMailCustom.jsp?sid="+sid;
	window.location = url;
}

function toDelete(sid){
	var url = "<%=contextPath %>/mail/delWebMail.action?sid="+sid;
	var para =  {} ;
	var jsonRs = tools.requestJsonRs(url,para);
	alert(jsonRs.rtMsg);
	window.location.reload();
}

function setWeb(sid){
	var url = "<%=contextPath %>/mail/defaultWebMail.action?sid="+sid;
	var para =  {} ;
	var jsonRs = tools.requestJsonRs(url,para);
	//alert(jsonRs.rtMsg);
	window.location.reload();
}
 
</script>
</head>
<body style="margin:5px 0 0 1px;">
	<div align="center" class="moduleHeader">
	  	<input type="button"  value="新建邮箱" class="btn btn-primary" onClick="toNewWebMail();" title="新建邮箱" />
	</div>
  	<table width="99%" class="TableBlock">
	    <tr class="TableHeader">
		      <td nowrap align="center" width="30%">邮箱</td>
		      <td nowrap align="center" width="20%">默认外部邮箱</td>
		      <td nowrap align="center" width="50%">操作</td>
	    </tr>
	    <c:forEach items="${webMailList}" var="webMailSort" varStatus="webMailStatus">
	        <tr class="TableLine">
      			<td align="center" width="30%">${webMailList[webMailStatus.index].loginType}<input type="hidden" id="id_${queryListStatus.count}" value = "${webMailList[webMailStatus.index].sid}"/></td>
      			<td align="center" width="20%">
    				<c:choose>  
						<c:when test="${webMailList[webMailStatus.index].isDefault == 0}"> 
							否
						</c:when>
						<c:otherwise> 
							是
						</c:otherwise>
					</c:choose>	
      			</td>
     			<td align="center" nowrap width="50%">
     				<a href="javascript:setWeb('${webMailList[webMailStatus.index].sid}');">设为默认外部邮箱</a>
     	    		<a href="javascript:toEdit('${webMailList[webMailStatus.index].sid}');">编辑</a>
          			<a href="javascript:toDelete('${webMailList[webMailStatus.index].sid}');">删除</a>
		      	</td>
		    </tr>
		</c:forEach>
 	</table>
	<div align="center" id="noData" style="display:none;">
	  	无模板！
	</div>
</body>
</html>