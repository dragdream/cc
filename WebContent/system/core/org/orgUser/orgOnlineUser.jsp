<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/ztree.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>在线人员</title>
<script type="text/javascript" src="<%=contextPath%>/system/core/person/js/person.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/TeeMenu.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/system/core/org/orgUser/orgUser.js"></script> 
<script type="text/javascript">

/**
 * 一次性加载
 */
 var zTreeObj ;
 function doInit(){
		var url = "<%=contextPath %>/orgManager/checkOrg.action";
		var jsonObj = tools.requestJsonRs(url);
		//alert(jsonObj);
		if(jsonObj.rtState){
			var json = jsonObj.rtData;
			if(json.sid){
				var url = "<%=contextPath %>/orgSelectManager/getOnlineOrgUserTree.action";
				var config = {
					zTreeId:"orgUserZtree",
					requestURL:url,
					param:{"para1":"111"},
					onClickFunc:personOnClick,
					async:false,
					onRightClickFunc:onRightClickFunc,
					onAsyncSuccess:callBackOrgFunc
			};
	
			zTreeObj = ZTreeTool.config(config);
				//expandNodes(zTreeObj);
			}else{
				alert("单位信息未录入，请您先填写单位信息！");
				return;
			}
		}
 }


</script>
</head>
<BODY onload="doInit()" style="padding-left:3px;">
	<div>
        <div style="padding-left:10px;">
		<ul id="orgUserZtree" class="ztree" style="border:0px;width:180px;height:auto;"></ul>
		
			<ul id='orgRightMenu' class="dropdown-menu" align='center' role="menu" style="min-width:100px" >
			</ul>
	</div>
	</div>
</BODY>
</html>