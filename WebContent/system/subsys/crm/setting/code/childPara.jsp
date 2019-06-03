<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	String paraValue = request.getParameter("paraValue") == null ? "" : request.getParameter("paraValue");
	int parentId = TeeStringUtil.getInteger(request.getParameter("sid") , 0);
	String paraName = request.getParameter("paraName") == null ? "" : request.getParameter("paraName");
	paraName = paraName.replace("\'", "\\\'");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>CRM子编码管理</title>
<script type="text/javascript">
var paraValue = '<%=paraValue%>';
var parentId = "<%=parentId%>";
var paraName = "<%=paraName%>";
function doInit(){
	//alert(paraValue);
	var url = "<%=contextPath %>/crmCode/getSysParaByParent.action?parentId=" + parentId;
	var jsonObj = tools.requestJsonRs(url);
	$("#tbody").empty();
	if(jsonObj.rtState){
		var json = jsonObj.rtData;
		jQuery.each(json, function(i, sysPara) {
		 	$("#tbody").append("<tr class='TableData'>"
					+"<td nowrap align=''>" + sysPara.codeNo+ "</td>"
					+"<td nowrap align=''>" + sysPara.codeName + "</td>"
					+"<td nowrap align=''>"
					 +"<a href='#' id='sysPara-child-a-" + sysPara.sid + "'>修改</a>"
					 +"&nbsp;&nbsp;<a href='javascript:deleteById(" + sysPara.sid + ")'>删除</a>"
					 +"</td>"
		  	+ "</tr>"); 
		 	//alert(123);
		 	$("#sysPara-child-a-" + sysPara.sid).bind("click",function(){
		 		toAddOrUpdate(sysPara.sid);
			});
		});

	}else{
		alert(jsonObj.rtMsg);
	}
}


function deleteById(id){
	if(confirm("确认要删除此编码？")){
		var url = "<%=contextPath %>/crmCode/delById.action";
		var jsonRs = tools.requestJsonRs(url,{sid:id});
		if(jsonRs.rtState){
			 $.jBox.tip("删除成功!" , 'info' ,{timeout:1500});
			 doInit();
		}else{
			alert(jsonRs.rtMsg);
		}
		
	}
	
}


/**
 * 新增或者修改
 */
function toAddOrUpdate(id){
	var url = contextPath + "/system/subsys/crm/setting/code/addupdatechild.jsp?sid=" + id + "&parentId=" + parentId;
	var title = "新增子代码";
	if(id>0){
		title = "编辑子代码";
	}
	bsWindow(url ,title,{width:"500",height:"200",buttons:
		[
		{name:"保存",classStyle:"btn btn-primary"},
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
			var cw = h[0].contentWindow;
			if(v=="保存"){
				cw.doSaveOrUpdate(function(){	
					doInit();
					$.jBox.tip("保存成功！", 'info',{timeout:1500});	
					BSWINDOW.modal("hide");
					//return true;
				});
		
			}else if(v=="关闭"){
				return true;
			}
		}
	});
}

</script>

</head>
<body onload="doInit()">
   <table border="0" width="95%" cellspacing="0" cellpadding="3" class="small" align="center">
    <tr>
      <td class="Big"><img src="<%=imgPath%>/sys_config.gif" align="absmiddle"><span class="big3">CRM子编码管理</span></td>
    </tr>
  </table><br>

 
   <table class="TableList" width="50%" align="center">
     
        <tr>
         <td colspan="3" class="TableHeader" id="mainMenuName">
           <%=paraValue %> : <%=paraName %>
        </td>
        </tr>
        <tr>
        <td colspan="3" class="" align="center">
          
         <a href="javascript:toAddOrUpdate(0);">新增编码</a></td>
        </tr>
      
        <tr class="TableHeader">
      		<td nowrap align="center">代码编号</td>
     	    <td nowrap align="center">代码名称</td>
      		<td nowrap align="center">操作</td>
       </tr>
        <tbody id="tbody">
      </tbody>
   </table>

</body>
</html>