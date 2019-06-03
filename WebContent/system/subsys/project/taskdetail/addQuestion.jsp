<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  taskId=TeeStringUtil.getInteger(request.getParameter("taskId"), 0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>创建问题</title>
</head>
<script>
var taskId=<%=taskId%>;

//验证
function check(){
	var questionName=$("#questionName").val();
	var operatorName=$("#operatorName").val();
	var questionDesc=$("#questionDesc").val();
	if(questionName==""||questionName==null){
		$.MsgBox.Alert_auto("请填写问题名称！");
		return false;
	}
	if(questionDesc==""||questionDesc==null){
		$.MsgBox.Alert_auto("请填写问题描述！");
		return false;
	}
	if(operatorName==""||operatorName==null){
		$.MsgBox.Alert_auto("请选择处理人！");
		return false;
	}
	return true;
}

//保存
function commit(){
	if(check()){
		var url=contextPath+"/projectQuestionController/addQuestion.action";
		var param=tools.formToJson("#form1");
		var json=tools.requestJsonRs(url,param);
		return json.rtState;
		
	}

	
}

</script>

<body style="background-color: #f2f2f2">
   <form id="form1" name="form1">
      <input type="hidden" name="taskId" id="taskId"  value="<%=taskId%>"/>
      <table class="TableBlock" width="100%">
         <tr>
            <td width="15%" style="text-indent: 10px">问题名称：</td>
            <td>
               <input type="text" name="questionName" id="questionName" style="width: 300px;height: 23px" />
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">优先级：</td>
            <td>
               <select id="questionLevel" name="questionLevel" style="height: 23px;width: 150px">
                  <option value="低">低</option>
                  <option value="普通">普通</option>
                  <option value="高">高</option>
                  <option value="非常高">非常高</option>
               </select>
            </td>
         </tr>
         <tr>
            <td style="text-indent: 10px">问题描述：</td>
            <td>
               <textarea rows="6" cols="60" id="questionDesc" name="questionDesc"></textarea>
            </td>
         </tr> 
          <tr>
            <td style="text-indent: 10px">处理人：</td>
            <td>
               <input name="operatorId" id="operatorId" type="hidden"/>
			   <input class="BigInput readonly" type="text" id="operatorName" name="operatorName" style="height:23px;width:300px"  readonly/>
			   <span class='addSpan'>
			   <img src="<%=contextPath %>/system/subsys/project/img/icon_select.png" onclick="selectSingleUser(['operatorId','operatorName'],'14')" value="选择"/>
				 &nbsp;&nbsp;
				<img src="<%=contextPath %>/system/subsys/project/img/icon_cancel.png" onclick="clearData('operatorId','operatorName')" value="清空"/>
			</span>
            </td>
         </tr>   
      </table>
   </form>
</body>
</html>