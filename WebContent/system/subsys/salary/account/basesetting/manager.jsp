<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
	Calendar cal = Calendar.getInstance();
	String deptId = request.getParameter("deptId")==null?"0":request.getParameter("deptId");
	int accountId = TeeStringUtil.getInteger(request.getParameter("accountId"), 0);//账套

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header.jsp"%>
<title>薪酬基数设置</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
var deptId="<%=deptId%>";
var accountId = <%=accountId%>;
/**
 * 初始化
 */
function doInit(){
	querySalary();
}

function querySalary(){
	var html="<table class='MessageBox' align='center'><tbody><tr><td class=\"msg info\"><div>暂无符合条件的数据！</div> </td> </tr> </tbody></table>";
	var queryParams=tools.formToJson($("#form1"));
	queryParams["deptId"]=deptId;
	queryParams["accountId"] = accountId; 
	var url = contextPath+"/teeSalItemController/querySalaryBase.action";
	var json = tools.requestJsonRs(url,queryParams);
	if(json.rtState){
		var data = json.rtData;
		if(data["valueList"].length==0){
			$("#dataList").html(html);
			return;
		}
		//表头字段字段名称
		html="<input id=\"delSalary\" name =\"delSalary\" type=\"button\" class=\"btn btn-success\" onclick=\"doSave();\" value='保存' />"
				+"&nbsp;&nbsp;<span style='font-size:12px;color:green'>注：SYS_基本工资、社保基数、公积金基数为0时，默认走岗位工资设定或保险基数设定。</span>";
	    html+="<table class='TableBlock'  style='margin-top:5px;'>";
		html+="<tr class='TableHeader'>";
		var tableHeader = data.tableHeaderName;
		var headers = tableHeader.split(",");
		for(var n=0;n<headers.length;n++){
			if(n!=0){
				html+="<td nowrap align='center' style='padding:0 0;'>"+headers[n].split("|")[0]+"</td>";
			}
		}
		html+="</tr>";
		//数据行
		var valueList = data.valueList;
		for(var i = 0;i<valueList.length;i++){
			html+="<tr class='TableData' clazz=''>";
			var values = valueList[i].split("*");
			for(var m=0;m<values.length;m++){
				if(m==1){
					html+="<td nowrap sid='"+values[0]+"'>"+values[m]+"</td>";
				}else if(m!=0){
					var headerSp = headers[m].split("|");
					if(headerSp.length==3){
						if(headerSp[2]=="0"){//输入项
							html+="<td nowrap clazz='"+headerSp[1]+"'><input type='text' class='BigInput' onchange='doChange(this)' style='width:50px' value='"+values[m]+"'/>&nbsp;<img src='clipboard-copy.png' style='width:10px;cursor:pointer;' title='同步该值到同列其他数据框' onclick='copy2Others(this)'/></td>";
						}else if(headerSp[2]=="1"){
							html+="<td nowrap clazz='"+headerSp[1]+"'>计算项</td>";
						}else if(headerSp[2]=="2"){
							html+="<td nowrap clazz='"+headerSp[1]+"'>提取项</td>";
						}else if(headerSp[2]=="3"){
							html+="<td nowrap clazz='"+headerSp[1]+"'>SQL项</td>";
						}
					}else{
						html+="<td nowrap clazz='"+headerSp[1]+"'><input type='text' class='BigInput' onchange='doChange(this)' style='width:50px' value='"+values[m]+"'/>&nbsp;<img src='clipboard-copy.png' style='width:10px;cursor:pointer;' title='同步该值到同列其他数据框' onclick='copy2Others(this)'/></td>";
					}
				}
			}
			html+="</tr>";
		}
		html+="</table>";
	}
	$("#dataList").html(html);
}

function doChange(input){
	$(input).parent().parent().attr("clazz","dataTr");
}

function copy2Others(obj){
	var clazz = $(obj).parent().attr("clazz");
	var val = $(obj).prev().val();
	$("td[clazz="+clazz+"] input").val(val);
	$(".TableData").attr("clazz","dataTr");
}

/**
 * 全选
 */
function doSave() {
	var trs = $("tr[clazz=dataTr]");
	var arr = [];
	for(var i=0;i<trs.length;i++){
		var tds = $(trs[i]).children();
		var obj = {};
		obj["sid"] = tds[0].getAttribute("sid");
		for(var j=1;j<tds.length;j++){
			var input = $(tds[j]).find("input:first");
			if(input.length!=0){
				obj[tds[j].getAttribute("clazz")] = input.val();
			}
		}
		arr.push(obj);
	}
	
	var url = contextPath+"/teeSalItemController/updateSalaryBase.action";
	var json = tools.requestJsonRs(url,{model:tools.jsonArray2String(arr)});
	$("tr[clazz=dataTr]").removeAttr("clazz");
	alert("保存成功");
}


</script>

</head>
<body onload="doInit();">
<input type="hidden" name="accountId" value="<%=accountId%>"></input>
<div id='dataList' name='dataList'  style="padding:10px">
</div>
</body>
</html>
