<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String host = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() ; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<%@ include file="/header/header.jsp" %>
	<%
		int sid = TeeStringUtil.getInteger(request.getParameter("sid"),0);
	%>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>印章查看</title>
<script>

var contextPath = "<%=contextPath%>";
var sid = <%=sid%>;
var host = '<%=host%>';
var sealForm = 2;


function doInit(){
	if(sid){
		getSealData(sid);
	}
}


</script>
<style>
</style>
</head>
<body onload="doInit()" style="">
<span id="SIGN_INFO_FEEDBACK" ></span>
<script type="text/javascript">

function LoadSignData(values) {
	  try{
		//  var str123 = $("DATA_13").value;
	    DWebSignSeal.SetStoreData(values);
	    DWebSignSeal.ShowWebSeals();
	    var strObjectName ;
	    strObjectName = DWebSignSeal.FindSeal("",0);
	    while(strObjectName) {
	      DWebSignSeal.SetSealSignData (strObjectName,"ZHONGAN");
	      DWebSignSeal.SetMenuItem(strObjectName,4);
	      strObjectName = DWebSignSeal.FindSeal(strObjectName,0);
	    }
	    
	  }catch(e) {
	  }
}
function getSealData(id){
	var url = contextPath+"/feedBack/getFeedBackSealById.action";
	var para = {};
	para['sid'] = id;
	var json = tools.requestJsonRs(url,para);
	if(json.rtState){
		var sealData = json.rtData;
		LoadSignData(sealData);
	}else{
		alert(json.rtMsg);
	}
}
</script>
</body>

</html>