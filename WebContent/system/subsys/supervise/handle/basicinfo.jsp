<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//任务主键
  int  sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本详情</title>
</head>
<script>
var sid=<%=sid%>;
function doInit(){
	var url=contextPath+"/supervisionController/getInfoBySid.action";
    var json=tools.requestJsonRs(url,{sid:sid});
    if(json.rtState){
    	var data=json.rtData;
        bindJsonObj2Cntrl(data);
		if(data.parentName==""||data.parentName==null){
			$("#parentName").html("无");
		}
		var status=data.status;
		var desc="";
		if(status==0){
			desc="未发布";
		}else if(status==1){
			desc="办理中";
		}else if(status==2){
			desc="暂停申请";
		}else if(status==3){
			desc="暂停中";
		}else if(status==4){
			desc="恢复申请中";
		}else if(status==5){
			desc="办结申请中";
		}else if(status==6){
			desc="已办结";
		}else if(status==7){
			desc="未接收";
		}
		$("#status").html(desc);
		$.each(data.attachmentsMode,function(i,v){
			v.priv=1+2;
			var attachElement = tools.getAttachElement(v,{});	
			$("#attachList").append(attachElement);
	    });
    }
	
}

</script>
<body onload="doInit()" style="">
  <table class="TableBlock_page" width="100%" style="margin-top: 10px">
     <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">基本信息</B></TD>
	</tr>
     <tr>
        <td width="15%" style="text-indent: 10px">工作事项：</td>
        <td id="supName" name="supName"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">责任领导：</td>
        <td id="leaderName" name="leaderName"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">主办人：</td>
        <td id="managerName" name="managerName"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">协办人：</td>
        <td id="assistNames" name="assistNames"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">开始时间：</td>
        <td id="beginTimeStr" name="beginTimeStr"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">结束时间：</td>
        <td id="endTimeStr" name="endTimeStr"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">工作状态：</td>
        <td id="status" name="status"></td>
     </tr>
      <tr>
        <td width="15%" style="text-indent: 10px">创建人员：</td>
        <td id="createrName" name="createrName"></td>
     </tr>
      <tr>
        <td width="15%" style="text-indent: 10px">工作描述：</td>
        <td id="content" name="content"></td>
     </tr>
      <tr>
        <td width="15%" style="text-indent: 10px">上级任务：</td>
        <td id="parentName" name="parentName"></td>
     </tr>
     <tr>
        <td width="15%" style="text-indent: 10px">附件：</td>
        <td id="attachList" name="attachList"></td>
     </tr>
  </table>
</body>
</html>