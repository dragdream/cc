<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
		String item = request.getParameter("item");
		String callback = request.getParameter("callback");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script type="text/javascript">
/**
 * 盖章获取可以盖的公章
 */
 
function getSeal(){
   var url = "<%=contextPath%>/sealManage/getHavePrivStamp.action";
   var rtJson = tools.requestJsonRs(url);
   if(rtJson.rtState){
     var data = rtJson.rtData;
     return data;
   }else{
     return ;
   }
 }

var item = "<%=item%>";
var callback = "<%=callback%>";
var parentWindowObj = xparent;
function click_seal(ID) {
  if (callback) {
    parentWindowObj.<%=callback%>('<%=item%>',ID);
  }
  //close();
  CloseWindow();
}
function doInit() {
  var url = "<%=contextPath%>/sealManage/getHavePrivStamp.action";
  var json = tools.requestJsonRs(url);
  if (json.rtState) {
    var seals = json.rtData;
    if (seals.length > 0 ) {
      for (var i = 0 ; i < seals.length ;i ++) {
        var seal = seals[i];
        addRow(seal);
      }
      $('#hasData').show();
    } else {
      $('#noData').show();
    }
  } 
}
function addRow(seal) {
 // var tr = new Element("tr", {'class':'TableData'});
  var tr = $("<tr class='TableData' ></tr>");
  $('#sealList').append(tr);
  
  //var td = new Element("td" , {'class': 'menulines'});
  var ltd = $("<td class='menulines' ></td>")[0];
  ltd.align = 'center';
  ltd.style.cursor = 'hand';
  ltd.onclick = function() {
    click_seal(seal.sid);
  }
  $(ltd).html(seal.sealId);
  //td.update(seal.sealId);
  $(tr).append(ltd);
  var rtd = $("<td class='menulines' ></td>")[0];
  //td = new Element("td" , {'class': 'menulines'});
  rtd.align = 'center';
  rtd.style.cursor = 'hand';
  rtd.onclick = function() {
    click_seal(seal.sid);
  }
  $(rtd).html(seal.sealName);
  //td.update(seal.sealName);
  $(tr).append(rtd);
}
</script>
</head>

<body  topmargin="5" onload="doInit()">

<div id="hasData" style="display:none">
<table class="TableBlock" width="100%">
<tr class="TableHeader">
  <td align="center"><b>印章ID</b></td>
  <td align="center"><b>印章名称</b></td>
</tr>

<tbody id="sealList"></tbody>
</table>
</div>
<div id="noData"  align=center style="display:none">
<table class="MessageBox" width="300">
    <tbody>
        <tr>
            <td class="msg info">无任何印章权限！</td>
        </tr>
    </tbody>
</table>

</div>
<style>
.menulines{
cursor:pointer;
}
</style>
</body>
</html>