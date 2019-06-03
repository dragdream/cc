
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp"%>
<title>个人常用语维护</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="<%=contextPath%>/system/core/base/attend/js/attend.js"></script>
<script type="text/javascript">
var contextPath = '<%=contextPath%>';
function  doOnload(){
	queryLeave();
}
/**
 *查询管理
 */
function queryLeave(){
	var url =   "<%=contextPath %>/CommonWord/testDatagrid.action";
	var jsonObj = tools.requestJsonRs(url);
	if(jsonObj.rtState){
		var prcs = jsonObj.rtData;
		$("#listDiv").empty();
		if(prcs.length > 0){
			var tableStr = "<table class='TableBlock' width='50%' align='center'><tbody id='listTbody'>";
		    	 tableStr = tableStr + "<tr class='TableHeader'>"
			      	 +"<td nowrap width='60px' align='center'>常用语</td>"
			      	 +"<td nowrap width='80px' align='center'>次数  </td>"
			      	 +"<td nowrap  width='60px' align='center'>操作</td>"
			         +"</tr>";
			for(var i = 0;i<prcs.length ; i++){
			 	var prc = prcs[i];
				var sid = prc.sid;
				var  fontStr = "";
				
					var opts = "<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myModal\" onclick='del(\"" + sid + "\" );'> 删除</a>";
					
					var optss = "<a href='javascript:void(0);' data-toggle=\"modal\" data-target=\"#myModal\" onclick='up(\"" + sid + "\" );'> 修改</a>";
				
				tableStr = tableStr +"<tr class='TableData'>"
				      	 + "<td width='140' align='center'><font color='" + fontStr + "'>"+ prc.cyy +"</font></td>"
				      	 +"<td nowrap align='center'><font color='" + fontStr + "'>" + prc.cis + "</font></td>"
				      	 +"<td nowrap align='center'>"
				      	 + opts
				      	 +optss
				      	 +"</td>"
				         +"</tr>";
			}
			tableStr = tableStr + "</tbody></table>";
				
			$("#listDiv").append(tableStr);	
		}else{
		 	messageMsg("没有相关请假信息", "listDiv" ,'' ,380);
		}
	}else{
		alert(jsonObj.rtMsg);
	}
}
function del(sid){
	top.$.jBox.confirm("是否要删除该数据？","确认",function(v){
		if(v=="ok"){
			var url = contextPath + "/CommonWord/deleteCm.action";
			var json = tools.requestJsonRs(url,{sid:sid});
			if(json.rtState){
				top.$.jBox.tip(json.rtMsg,"info");
				window.location.reload();
				return true;
			}
			top.$.jBox.tip(json.rtMsg,"error");
		}
	});
}
function up(sid){
	alert("dfa");
	window.location=contextPath+"/system/core/person/commonword/update.jsp?sid="+sid;
}



function checkForm(){
    return $("#form1").form('validate'); 
}
function commit(){

		var url = "";
		var para = tools.formToJson($("#form1"));
		url = "<%=contextPath %>/CommonWord/addCm.action";
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			top.$.jBox.tip(jsonRs.rtMsg,"info");
			try{
				xparent.location.reload();
			}catch(e){}
			
		}else{
			top.$.jBox.tip(jsonRs.rtMsg,"error");
			return false;
		}
	
	
}
</script>
</head>
<body class="" onload="doOnload();">

<div class="base_layout_top">
个人常用语维护
</div>
<div align='center'style="margin-top: 100px;">
<form action="" id="form1">
  <table >
        <tr>
        <td>常用语</td>
        <td><input type="text" name="cyy" id="cyy" ></td>
        </tr>
        <tr>
        <td>次数</td>
        <td><input type="text" name="cis" id="cis" ></td>
        </tr>
         <tr><td></td><td><button TYPE="button" class="btn btn-primary" style="" onclick="commit()">保存</button></td></tr>
    </table>
    </form>
</div>
<div style="margin-top:50px;">
	<br>
	<div id='listDiv'></div>
</div>
<!-- </div> -->
</body>

</html>