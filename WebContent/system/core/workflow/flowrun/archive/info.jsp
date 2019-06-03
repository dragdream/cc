<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
   String archiveDateStr=TeeStringUtil.getString(request.getParameter("archiveDateStr")); 
   String archiveDesc=TeeStringUtil.getString(request.getParameter("archiveDesc"));
%>
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<title>工作流归档数据查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
</style>
<script type="text/javascript">
var archiveDateStr="<%=archiveDateStr%>";
var archiveDesc="<%=archiveDesc%>";
function doInit(){
	var url=contextPath+"/flowArchiveController/getArchiveCount.action";
    var json=tools.requestJsonRs(url,{archiveDateStr:archiveDateStr});
    if(json.rtState){
    	var data=json.rtData;
    	$("#count1").html(data.sumCount);
    	$("#count2").html(data.archiveCount);
    	$("#count3").html(data.noArchiveCount);
    	$("#count4").html(data.noArchiveCount);
    	$("#count5").html(data.noArchiveCount);
    	
    	if(data.archiveCount>0){//有可以归档的流程
    		$("#option1").show();
    		$("#option2").hide();
    	}else{//没有可以归档的流程
    		$("#option1").hide();
    		$("#option2").show();
    	}
    }
}

//返回
function back(){
	window.location.href=contextPath+"/system/core/workflow/flowrun/archive/index.jsp";
}


//复选框
function render(){
	if($("#isAgree")[0].checked){
		$("#archiveBtn").show();
	}else{
		$("#archiveBtn").hide();
	}
}

//开始进行数据归档
function doArchive(){
	var url=contextPath+"/flowArchiveController/doArchive.action?archiveDateStr="+archiveDateStr+"&archiveDesc="+archiveDesc;
// 	var json=tools.requestJsonRs(url,{archiveDateStr:archiveDateStr,archiveDesc:archiveDesc});
// 	if(json.rtState){
// 		$.MsgBox.Alert_auto("归档成功！");
// 		window.location.href=contextPath+"/system/core/workflow/flowrun/archive/index.jsp";
// 	}else{
// 		$.MsgBox.Alert_auto("归档失败！");
// 	}
	bsWindow(url,"归档进度",{buttons:[],width:"400px",height:"300px"});
}
</script>

</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;font-size: 14px;">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		<span class="title">工作流归档数据详情</span>
	</div>
   <div class="fr right">
      <input type="button"  id="archiveBtn" style="display: none" value="开始归档" class="btn-win-white" onclick="doArchive();"/>
      <input type="button" value="返回" class="btn-win-white" onclick="back();"/>
   </div>
</div>

<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="60%" align="center">
	<tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">特别提示</B></TD>
	</tr>
	<tr>
    <td  class="TableData" align="left" id="groupSelect">
       <span style="color:red">(1)在执行确认归档操作之前，请您做好数据库备份工作 </span><br>
       <span>(2)归档后的数据仍然可以查询，您可以在工作查询【归档查询】操作中找到它们</span><br>
       <span style="color:green">(3)归档操作可能需要花费较长时间，为确保数据的准确及不影响您的使用，建议您在无人使用OA时执行</span>
    </td>
   </tr>
  <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">归档数据分析</B></TD>
	</tr>
	<tr>
    <td  class="TableData" align="left" id="groupSelect">
       <span>截止到<span style="color:blue"><%=archiveDateStr %></span>前，系统中共有<span id="count1"></span>个工作，其中：</span><br>
       <span style="color:green">可以归档的工作流程数量：<span id="count2"></span></span><br>
       <span style="color:red">无法归档的工作流程数量：<span id="count3"></span></span>
    </td>
   </tr>
   <tr>
		<TD class=TableHeader colSpan=2 noWrap>
		<img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		<B style="color: #0050aa">无法归档原因</B></TD>
	</tr>
	<tr>
    <td  class="TableData" align="left" id="groupSelect">
      <div id="option1" style="display: none">
         <span><span id="count4" name="count4" style="color:red"></span>个工作流程仍在办理中，此部分数据不会影响归档，但不在本次归档范围内，</span><span style="color:green">您是否确定要进行归档操作？请确认：</span><br>
         <input type="checkbox" id="isAgree" onchange="render()"/><a href="#">我已详细阅读上述说明并确定要进行归档操作</a>
      </div>
      <div id="option2" style="display: none">
          <span>(1)<span id="count5" name="count5" style="color:red"></span>个工作流程仍在办理中，此部分数据不会影响归档，但不在本次归档范围内 </span><br>
          <span style="color:red">(2)您当前没有可以归档的工作流程，无法进行归档操作</span><br>
      </div> 
    </td>
   </tr>
</table>
  </form>
</body>

</html>
