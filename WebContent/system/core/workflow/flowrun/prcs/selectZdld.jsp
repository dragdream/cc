<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 
    int itemId=TeeStringUtil.getInteger(request.getParameter("itemId"),0);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/header/header2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择总队领导</title>
<script type="text/javascript">
var itemId=<%=itemId %>;
function doInit(){
	var url=contextPath+"/personManager/getPersonByDept.action?deptId=2";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html=[];
	    for(var i=0;i<data.length;i++){
	    	html.push("<tr>");
	    	html.push("<td style=\"text-indent:10px\"><input type=\"checkbox\" class=\"ck\" value="+data[i].id+" uname='"+data[i].name+"'/></td>");
	    	html.push("<td><span style=\"font-size:16px\">"+data[i].name+"</span></td>");
	    	html.push("</tr>");
	    }
	    $("#table1").append(html.join(""));
	}
}


//确认
function commit(){
	var ck=$(".ck");
	var html="";
	for(var i=0;i<ck.length;i++){
		if(ck[i].checked==true){
			html+=$(ck[i]).attr("uname")+",";
		}
	}
	if(html.endWith(",")){
		html=html.substring(0,html.length-1);
	} 
	if(html!=""&&html!=null){
		xparent.$("#DATA_"+itemId).val("请"+html+"批示");
	}
	window.close();
}
//取消
function cancel(){
	window.close();
}

String.prototype.endWith=function(endStr){
    var d=this.length-endStr.length;
    return (d>=0&&this.lastIndexOf(endStr)==d)
  }
</script>

</head>

<body onload="doInit();" style="width: 100%">
   <table id="table1" class="TableBlock_page" style="width:100%">
      
   </table>
   <table class="" style="width:100%;border: none;padding-top: 10px">
      <tr>
         <td width="50%"></td>
         <td>
            <input type="button" class="btn-win-white" value="确定" onclick="commit();"/>
            <input type="button" class="btn-del-red" value="取消" onclick="cancel();"/>
         </td>
      </tr>
   </table>
</body>
</html>