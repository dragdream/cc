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
<title>工资项管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
	var queryParams=tools.formToJson($("#form1"));
	queryParams["deptId"]=deptId;
	queryParams["accountId"] = accountId; 
	queryParams["salYear"]=salYear;
	queryParams["salMonth"] = salMonth; 
	var url = contextPath+"/teeSalItemController/querySalary.action";
	var json = tools.requestJsonRs(url,queryParams);
	if(json.rtState){
		var data = json.rtData;
		if(data.length>1){
			//表头字段字段名称
			html="<input id=\"delSalary\" name =\"delSalary\" type=\"button\" class=\"btn btn-danger\" onclick=\"delSelectedInfo();\" value='批量删除' />";
// 				+"&nbsp;&nbsp;<input id=\"editSalary\" name =\"editSalary\" type=\"button\" class=\"btn btn-primary\" onclick=\"editSelectedInfo();\" value='编辑' />";
		    html+="<table class='TableBlock'  style='margin-top:5px;'>";
			html+="<tr class='TableHeader'>";
			var tableHeader = json.rtData[0].tableHeaderName;
			var headers = tableHeader.split(",");
			for(var n=0;n<headers.length;n++){
				if(n==0){
					html+="<td nowrap ><input id='allbox_for' name='allbox' type='checkbox' onclick='checkAll(this)'/>全选</td>";
				}else{
					html+="<td nowrap align='center' style='padding:0 0;'>"+headers[n]+"</td>";
				}
			}
			html+="</tr>";
			//数据行
			for(var i = 1;i<data.length;i++){
				html+="<tr class='TableData'>";
				var valueList = data[i].valueList;
				var values = valueList.split("*");
				for(var m=0;m<values.length;m++){
					if(m==0){
						html+="<td nowrap width='50'><input name='deleteFlag' type='checkbox' value='"+values[m]+"'/></td>";
					}else{
						html+="<td nowrap>"+values[m]+"</td>";
					}
				}
				html+="</tr>";
			}
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

function editSelectedInfo(){
	var idStrs = checkMags('deleteFlag');
	if(idStrs.length<=0){
		$.jBox.tip("请选择需要编辑的项目","info");
		return;
	}
	if(idStrs.split(",").length>1){
		$.jBox.tip("一次只能编辑一条数据","info");
		return;
	}
	var url=contextPath+"/system/subsys/salary/manage/edit.jsp?sid="+idStrs;
	bsWindow(url,"修改人员薪资",{width:"500",height:"300",submit:function(v,h){
		var cw = h[0].contentWindow;
		cw.commit(function(json){
			$.jBox.tip('保存成功','info',{timeout:1000});
			querySalary();
			
		});
		return true;//返回true就是关闭窗口	
	}});
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
<input type="hidden" name="accountId" value="<%=accountId%>"></input>
<div id='dataList' name='dataList'  style="padding:10px">
</div>
</body>
</html>
