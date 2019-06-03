<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<title>档案详情</title>
<style>
.attTable {
	border-collapse: collapse;
    border: 1px solid #f2f2f2;
    width:100%;
}
.attTable tr{
	line-height:35px;
	border-bottom:1px solid #f2f2f2;
}
.attTable tr td:first-child{
	text-indent:10px;
}
.attTable tr:first-child td{
	font-weight:bold;
}
.attTable tr:first-child{
	border-bottom:2px solid #b0deff!important;
	background-color: #f8f8f8;
}
</style>
<script type="text/javascript">
var sid=<%=sid %>;
//初始化
function doInit(){
	
	if(sid>0){
		getInfoBySid();
		getAttchList();
		getOperRecords();
	}
}

//获取附件列表
function getAttchList(){
	//清空除首行外的其他内容
	$("#attList  tr:not(:first)").empty("");
	var url=contextPath+"/TeeFileAttchController/getFileAttchListByFileId.action";
	var json=tools.requestJsonRs(url,{fileId:sid});
	var attList=json.rtData;
	var html="";
	if(attList==null||attList.length==0){
		html="<tr><td colspan='8' id='mess'></td></tr>";
		$("#attList").append(html);
		messageMsg("暂无附件信息","mess","info" ,"" );
	}else{
		for(var i=0;i<attList.length;i++){
			var tdId="td"+i;
			var attModel=attList[i].attModel;
			if(attModel!=null){
				attModel['priv']=3;
			}
			
			var attObj=tools.getAttachElement(attModel,{});
			html="<tr>"
			+"<td nowrap>"+attList[i].sortNo+"</td>"
			+"<td nowrap>"+attList[i].manager+"</td>"
			+"<td nowrap>"+attList[i].wjz+"</td>"
			+"<td nowrap>"+attList[i].title+"</td>"
			+"<td nowrap>"+attList[i].pageNum+"</td>"
			+"<td nowrap>"+attList[i].pubTimeStr+"</td>"
			+"<td nowrap>"+attList[i].remark+"</td>"
			+"<td nowrap id="+tdId+"></td>";
			$("#attList").append(html);
			$("#"+tdId).append(attObj);
		}
		
	}
}



//获取操作日志
function getOperRecords(){
	//清空除首行外的其他内容
	$("#recordList  tr:not(:first)").empty("");
	var url=contextPath+"/opeRecordsController/getListByFileId.action";
	var json=tools.requestJsonRs(url,{fileId:sid});
	var recordList=json.rtData;
	var html="";
	if(recordList==null||recordList.length==0){
		html="<tr><td colspan='3' id='mess1'></td></tr>";
		$("#recordList").append(html);
		messageMsg("暂无日志信息！","mess1","info" ,"" );
	}else{
		for(var i=0;i<recordList.length;i++){
			html="<tr>"
			+"<td nowrap>"+recordList[i].operUserName+"</td>"
			+"<td nowrap>"+recordList[i].operTimeStr+"</td>"
			+"<td nowrap>"+recordList[i].content+"</td>"
            +"</tr>";
			$("#recordList").append(html);
		}	
	}
}







//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/TeeDamFilesController/getInfoBySid.action";
	var json=tools.requestJsonRs(url,{sid:sid});
	if(json.rtState){
		var prc = json.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
		}
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	
	
}


//返回
function back(){
	var url=contextPath+"/system/core/base/dam/preArchive/manage/index.jsp";
	window.location.href=url;
}


</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px;">
   <div id="toolbar" class="topbar clearfix">
       <div class="fl" style="position:static;">
		  <img id="img1" class = 'title_img' src="<%=contextPath %>/system/core/base/dam/imgs/icon_daxq.png">
		  <span class="title">档案详情</span>
	    </div>
	    <div class = "right fr clearfix">
		   <input type="button" class="btn-win-white fl" onclick="back();" value="返回"/>
         </div>   
   </div>
<form id="form1" name="form1">
   <table class="TableBlock_page">
   <input type="hidden" name="sid" id="sid" value="<%=sid %>"/>
      <tr>
        <td class="TableHeader" colspan="2" nowrap="">
		   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">基础信息</b>
		</td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">组织机构代码：</td>
         <td class="TableData" name="orgCode" id="orgCode">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">全宗号：</td>
         <td class="TableData" name="qzh" id="qzh">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">年份：</td>
         <td class="TableData" name="year" id="year">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">保管期限：</td>
         <td class="TableData" id="retentionPeriodStr" name="retentionPeriodStr">
         </td>
      </tr>
       <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">所属卷盒：</td>
         <td class="TableData" id="boxNo" name="boxNo">
         </td>
      </tr>
       <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">件号：</td>
         <td class="TableData" id="jh" name="jh">
         </td>
      </tr>
       <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">档案号：</td>
         <td class="TableData" id="dah" name="dah">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">文件标题：</td>
         <td class="TableData" name="title" id="title">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">发/来文单位：</td>
         <td class="TableData"  name="unit" id="unit">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">文件编号：</td>
         <td class="TableData" name="number" id="number">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">密级：</td>
         <td class="TableData" id="mj" name="mj">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">缓急：</td>
         <td class="TableData"  id="hj" name="hj">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">主题词：</td>
         <td class="TableData" name="subject" id="subject">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">创建人：</td>
         <td class="TableData"  id="createUserName" name="createUserName">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">创建时间：</td>
         <td class="TableData" name="createTimeStr" id="createTimeStr">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">档案管理员：</td>
         <td class="TableData"  id="archiveUserName" name="archiveUserName">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">归档时间：</td>
         <td class="TableData" name="archiveTimeStr" id="archiveTimeStr">
         </td>
      </tr>
      <tr>
         <td class="TableData" style="text-indent: 10px;width: 150px;">备注：</td>
         <td class="TableData" id="remark" name="remark">
         </td>
      </tr>
      
      <tr>
        <td class="TableHeader" colspan="2" nowrap="">
		   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">附件信息</b>
		    
		    
		</td>
      </tr>
      <tr>
         <td colspan="2">
             <table class="attTable" id="attList">
                 <tr class='TableHeader'>
                    <td nowrap width="5%">顺序号</td>
                    <td nowrap>责任者</td>
                    <td nowrap>文件字</td>
                    <td nowrap>标题</td>
                    <td nowrap width="5%">页数</td>
                    <td nowrap width="15%">生成日期</td>
                    <td nowrap width="25%">备注</td>
                    <td nowrap width="20%">文件</td>
                 </tr>
                
             </table>
         </td>
      </tr>
      <tr>
        <td class="TableHeader" colspan="2" nowrap="">
		   <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		    <b style="color: #0050aa">操作日志</b>
		    
		    
		</td>
      </tr>
      <tr>
         <td colspan="2">
             <table class="attTable" id="recordList">
                 <tr class='TableHeader'>
                    <td nowrap>操作人</td>
                    <td nowrap>操作时间</td>
                    <td nowrap>操作内容</td>
                  </tr>
                
             </table>
         </td>
      </tr>
   </table>
</form>   
</body>
