<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Calendar cal = Calendar.getInstance();
	int year = cal.get(Calendar.YEAR);
	int month = cal.get(Calendar.MONTH) + 1;
	String deptId = request.getParameter("deptId")==null?"0":request.getParameter("deptId");
	int accountId = TeeStringUtil.getInteger(request.getParameter("accountId"), 0);//账套
	int salYear =  TeeStringUtil.getInteger(request.getParameter("salYear"), year);//年份
	int salMonth = TeeStringUtil.getInteger(request.getParameter("salMonth"),month);//月份
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<%@ include file="/header/easyui.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>员工工资明细统计</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=cssPath%>/style.css"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<link rel="stylesheet" href="<%=contextPath%>/common/jquery/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=contextPath %>/common/jquery/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=contextPath %>/common/jquery/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/easyuiTools.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/ZTreeSync.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/sys.js"></script>
<script>
var deptId="<%=deptId%>";
var accountId = <%=accountId%>;
var salYear = <%=salYear%>;
var salMonth = <%=salMonth%>;

/**
 * 初始化
 */
function doInit(){
	querySalary();
}

function querySalary(){
	
	var html="<table class='MessageBox' align='center'><tbody><tr><td class=\"msg info\"><div>暂无符合条件的数据！</div> </td> </tr> </tbody></table>";
	var queryParams= {deptId:deptId,accountId:accountId,salYear:salYear,salMonth:salMonth};//;tools.formToJson($("#form1"));

	var url = contextPath+"/teeSalItemController/querySalary.action";
	var json = tools.requestJsonRs(url,queryParams);
	if(json.rtState){
		var data = json.rtData;
		if(data.length>1){
			//表头字段字段名称
			html="<table class='TableBlock'  width='100%'>";
			html+="<tr class='TableHeader'>";
			var tableHeader = json.rtData[0].tableHeaderName;
			var headers = tableHeader.split(",");
			for(var n=0;n<headers.length;n++){
				if(n==0){
					html+="<td nowrap width='50' style='display:none;'><input id='allbox_for' name='allbox' type='checkbox' onclick='checkAll(this)'/>全选</td>";
				}else{
					html+="<td nowrap align='center' style='padding:0 0;'>"+headers[n]+"</td>";
				}
			}
			html+="</tr>";
			
			//统计行
			var sumHtml="<tr class='TableData'>";
			var valueList = data[1].valueList;
			var values = valueList.split("*");
			//定义合计数组，用户统计每一列的总数
			var total = new Array(values.length);
		 	for(var i=0;i<total.length;i++){
				total[i]=0;
			} 
			//数据行
			for(var i = 1;i<data.length;i++){
				html+="<tr class='TableData'>";
				var valueList = data[i].valueList;
				var values = valueList.split("*");
				for(var m=0;m<values.length;m++){//列数
					if(m==0){
						html+="<td nowrap width='50' style='display:none;'><input name='deleteFlag' type='checkbox' value='"+values[m]+"'/></td>";
					}else{
						html+="<td nowrap>"+values[m]+"</td>";
					}
					//合计总数
					if(m>2){
						total[m] = Number(total[m]) + Number(values[m]);
					}
				}
				html+="</tr>";
			}
			
			for(var k=0;k<values.length;k++){
				if(k==0){
					sumHtml+="<td nowrap style=\"display:none;\"></td>";
				}else if(k==1){
					sumHtml+="<td nowrap align='center' style='font-weight:bolder;'>合计</td>";
				}else if(k==2){
					sumHtml+="<td nowrap></td>";
				}else{
					sumHtml+="<td nowrap>"+total[k]+"</td>";
				}
			}
			sumHtml+="</tr>";
			
			html+=sumHtml;
			html+="</table>";
		}
	}
	$("#dataList").html(html);
}

/**
 * 全选
 */
function checkAll(field) {
  var deleteFlags = document.getElementsByName("deleteFlag");
  for(var i = 0; i < deleteFlags.length; i++) {
    deleteFlags[i].checked = field.checked;
  }
}
/**
 * 删除选中信息
 */
function delSelectedInfo(){
	var idStrs = checkMags('deleteFlag');
	if(idStrs.length<=0){
		$.jBox.tip("请选择需要删除项目","info");
		return;
	}
	$.jBox.confirm("确认删除选中信息吗","确认",function(v){
		if(v=="ok"){
			var url = contextPath+"/teeSalItemController/delSalaryInfo.action";
			var json = tools.requestJsonRs(url,{sids:idStrs});
			if(json.rtState){
				$.jBox.tip(json.rtMsg,"success");
				querySalary();
			}else{
				$.jBox.tip(json.rtMsg,"error");
			}
		}
	});
}


//取得选中选项
function checkMags(cntrlId){
  var ids= "";
  var checkArray = $("#dataList").find("input");
  for(var i = 0 ; i < checkArray.length ; i++){
    if(checkArray[i].name == cntrlId && checkArray[i].checked ){
      if(ids != ""){
        ids += ",";
      }
      ids += checkArray[i].value;
    }
  }
  return ids;
}



</script>

</head>
<body onload="doInit();">

<%--<div id='queryCondition'>
 <form enctype="multipart/form-data" action=""  method="post" name="form1" id="form1">
  <table class="TableBlock" width="70%" align="center">
     <tr>
      <td nowrap class="TableData" width="">用户名称：</td>
      <td class="TableData">
      	<input id="userName" name="userName"  type="text" class="BigInput"/>
      </td>
      <td nowrap class="TableData" width="">工资年份：</td>
      <td class="TableData">
       	  <select name="salYear" class="BigSelect">
       	  	<%
       	  	for(int i = 2000 ; i <2100 ; i++){
       	  		if(year == i){	
       	  		
       	  	%>
       	  	<option value="<%=i %>" selected="selected"><%=i %>年</option>
       	  	<%}else{ %>
       	  	<option value="<%=i %>"><%=i %>年</option>
       	  	<%	}}
       	  	%>
       	  </select>
       	  
       	  <select name="salMonth"  class="BigSelect">
       	  	<%
       	  	for(int i = 1 ; i <13 ; i++){
       	  		if(month == i){	
       	  	%>
       	  		<option value="<%=i %>" selected="selected"><%=i %>月</option>
       	  	<%
       	  		}else{
       	  	%>       
       	  		<option value="<%=i %>"><%=i %>月</option>
       	  	<%	}}
       	  	%>
       	  </select>
      </td>
    </tr>
    <tr>
    	<td colspan='4' align='center'>
    		<input type='button' class='btn btn-primary' id="query" name='query' onclick="querySalary()" value='查询'/>
    	</td>
    </tr>
</table>
</form> 
</div>
</br>--%>
<br>
<div id='dataList' name='dataList'  style="width:98%;padding-left:10px;">
</div>
</body>
</html>
