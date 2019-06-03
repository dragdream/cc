<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="/header/header2.0.jsp" %>
<%@include file="/header/validator2.0.jsp" %>
<title>创建报表</title>

<script type="text/javascript">
//保存
function save(){
	if($("#form1").valid()){
		var param=tools.formToJson("#form1");
		var url=contextPath+"/teeEreportController/create.action";
		var json=tools.requestJsonRs(url,param);
		return json;
	}
}

</script>

</head>
<body style="background-color: #f2f2f2;">
   <form  id="form1">
      <table class="TableBlock"   width="100%">
          <tr>
             <td style="text-indent: 10px" >报表名称:</td>
             <td>
                <input type="text" name="title" id="title" style="height: 23px;width: 250px" required/>
             </td>
          </tr>
      </table>
   </form>  
</body>
<script type="text/javascript">
  $("#form1").validate();
</script>
</html>