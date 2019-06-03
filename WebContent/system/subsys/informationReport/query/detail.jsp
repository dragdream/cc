<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
   int taskPubRecordId=TeeStringUtil.getInteger(request.getParameter("taskPubRecordId"),0);
   String reportTime=TeeStringUtil.getString(request.getParameter("reportTime"));
   String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
   int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"),0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<title>已报详情</title>
<script type="text/javascript">
var taskPubRecordId=<%=taskPubRecordId %>;//任务发布记录主键
var taskTemplateId=<%=taskTemplateId %>;//任务模板Id
var taskTemplateName="<%=taskTemplateName %>"; //任务模板名称
//初始化
var datagrid;
function doInit(){
	var opts = [{field:'CREATE_USER_NAME',
        title:'上报人',
        width:200
      }];
	
	//渲染datagrid
	var url=contextPath+"/TeeTaskTemplateItemController/getListByTemplateId.action";
	var json=tools.requestJsonRs(url,{taskTemplateId:taskTemplateId});
	if(json.rtState){
		
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				//判断是否 显示
				if(data[i].showAtList==1){//表头显示
					opts.push({
						     field:"DATA_"+data[i].sid,
						     title:data[i].fieldName,
						     width:200});
				}
			}
		}	
	}
	
	opts.push({field:'opt_',
        title:'操作',
        width:200,
    	formatter:function(value,rowData,rowIndex){
    		if(rowData.isFooter!=1){
    			var opt="<a href=\"#\" onclick=\"detail("+rowData.RECORD_ITEM_ID+","+rowData.CREATE_USER_ID+")\">详情</a>";
    			return opt;
    		} 
			
		}
      });
	
	datagrid = $('#datagrid').datagrid({
		url:contextPath + "/TeeTaskPubRecordItemController/getRepDataListByRecordId.action?taskPubRecordId="+taskPubRecordId+"&taskTemplateId="+taskTemplateId,
		view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
		pagination:false,
		singleSelect:true,
		toolbar:'#toolbar',//工具条对象
		checkbox:false,
		border:false,
		//idField:'formId',//主键列
		fitColumns:true,//列是否进行自动宽度适应
		showFooter: true,
		columns:[opts]
	});
	
	
	
	
	
}

//返回
function  back(){
	history.go(-1);
}


//查看详情
function detail(taskPubRecordItemId,createUserId){
	var url=contextPath+"/system/subsys/informationReport/query/info.jsp?taskPubRecordItemId="+taskPubRecordItemId+"&taskTemplateName="+taskTemplateName+"&taskTemplateId="+taskTemplateId+"&createUserId="+createUserId;
    openFullWindow(url);
}
</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
   <div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_ybxq.png">
			<span class="title"><%=taskTemplateName %>(<%=reportTime %>)--已报详情</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl" onclick="back();" value="返回"/>
        </div>
	</div>
    <table id="datagrid" fit="true"></table>
</body>
</html>