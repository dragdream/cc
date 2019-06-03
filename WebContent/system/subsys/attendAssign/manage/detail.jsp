<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
       int sid=TeeStringUtil.getInteger(request.getParameter("sid"),0);
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外勤记录详情</title>
<script type="text/javascript">
 var sid=<%=sid %>;
 //初始化
 function doInit(){
	 
	 var url=contextPath+"/TeeAttendAssignController/getInfoBySid.action";
     var json=tools.requestJsonRs(url,{sid:sid});
     if(json.rtState){
    	 var data=json.rtData;
    	 bindJsonObj2Cntrl(data);
    	 
    	 var attachIds=data.attachIds;
    	 var render=[];
    	 if(attachIds!=null&&attachIds!=""){
    		 attIdArray=attachIds.split(",");
    		 for(var n=0;n<attIdArray.length;n++){	
				render.push("<img class=\"pic\" src='/attachmentController/downFile.action?id="+attIdArray[n]+"'  style=\"width:97px;height:97px;margin-right:15px;margin-top:5px \"  onclick=\"picView('"+attachIds+"',"+attIdArray[n]+")\"  />");
			}	 
    		 $("#attachDiv").append(render.join(""));
    	 }
     }
 }
 
 //图片预览
 function picView(ids,id){
	 var url=contextPath+"/system/core/attachment/picExplore.jsp?id="+id+"&pics="+ids;
	 openFullWindow(url);
 }
</script>
</head>

<body onload="doInit()" style="background-color: #f2f2f2">

    <table class="TableBlock" width="100%">
        <tr>
           <td style="text-indent: 10px">上报地点：</td>
           <td>
              <span id="address" name="address"></span>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">上报时间：</td>
           <td>
              <span id="createTimeStr" name="createTimeStr"></span>
           </td>
        </tr>
        <tr>
           <td style="text-indent: 10px">备注：</td>
           <td>
              <span id="remark" name="remark"></span>
           </td>
        </tr>
    </table>
    <div id="attachDiv" style="padding-top: 5px;padding-left: 15px;padding-right: 0px;">
    
    </div>
</body>
</html>