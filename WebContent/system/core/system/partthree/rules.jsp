<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<title>规则设置</title>
<style type="text/css">
table{
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
table tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
table tr td:first-child{
	text-indent:10px;
}
table tr:first-child td{
	font-weight:bold;
}
table tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8;
}
</style>
<script type="text/javascript">

function doInit(){
	//渲染规则列表
	getAllRules();
}

//渲染规则列表
function getAllRules(){
	$("#tb tr:not(:first)").empty("");

	var url=contextPath+"/teePartThreeRuleController/getAllRules.action";
	var json=tools.requestJsonRs(url,null);
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			 var render=[];
			 for(var i=0;i<data.length;i++){
				 render.push("<tr>");
				 render.push("<td>"+data[i].ruleCode+"</td>");
				 render.push("<td>"+data[i].ruleDesc+"</td>");
				 render.push("<td>"+data[i].operPrivDesc+"</td>");
				 if(data[i].isOpen==1){
					 render.push("<td>√</td>");
				 }else{
					 render.push("<td>×</td>");
				 } 
				 render.push("<td><a href=\"#\"  onclick='update("+data[i].sid+")'>修改</a></td>");
				 render.push("</tr>");
			 }	
			 $("#tb").append(render.join(""));
		}
	}
	
}


function update(sid){
	var url=contextPath+"/system/core/system/partthree/updateRule.jsp?sid="+sid;
	bsWindow(url ,"修改规则",{width:"500",height:"150",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		    var json=cw.doSaveOrUpdate();
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("修改成功！",function(){
		    		
		    		getAllRules();
		    	});
		    	return true;
		    }else{
		    	$.MsgBox.Alert_auto("修改失败！");
		    	return false;
		    }
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px"> 
   <table style="margin-top: 10px" id="tb">
      <tr> 
         <td>规则代码</td>
         <td>规则描述</td>
         <td>操作权限</td>
         <td>开启状态</td>
         <td>操作</td>
      </tr>
   </table>
</body>
</html>