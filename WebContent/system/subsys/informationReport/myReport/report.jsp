<%@page import="com.tianee.webframe.util.str.TeeStringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
    //任务发布项主键
    int pubRecordItemId=TeeStringUtil.getInteger(request.getParameter("pubRecordItemId"),0);
    //任务发布主键
    int pubRecordId=TeeStringUtil.getInteger(request.getParameter("pubRecordId"), 0);
    //任务模板主键
    int taskTemplateId=TeeStringUtil.getInteger(request.getParameter("taskTemplateId"), 0);
    //任务模板名称
    String taskTemplateName=TeeStringUtil.getString(request.getParameter("taskTemplateName"));
    //如果type=1 代表是从消息提醒打开该页面的
    int type=TeeStringUtil.getInteger(request.getParameter("type"),0);
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<script src="<%=contextPath %>/common/ueditor/ueditor.config.js"></script>
<script src="<%=contextPath %>/common/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<title>上报</title>
<script type="text/javascript">
var pubRecordItemId=<%=pubRecordItemId %>;
var pubRecordId=<%=pubRecordId %>;
var taskTemplateId=<%=taskTemplateId %>;
var taskTemplateName="<%=taskTemplateName %>";
var type=<%=type %>;
var uEditorObj;//uEditor编辑器
//初始化
function doInit(){
	//渲染基础信息
	renderBasicInfo();
	
	//附件选择
	var ttAttach = new TeeSimpleUploadRender({
		fileContainer:"upfileList",
	});
	
}
//获取汇报的基础信息
function renderBasicInfo(){
	//根据任务模板主键  获取  任务模板项  并进行渲染
	var url=contextPath+"/TeeTaskTemplateItemController/getListByTemplateId.action";
	var json=tools.requestJsonRs(url,{taskTemplateId:taskTemplateId});
	
	if(json.rtState){
		var data=json.rtData;
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				var render=[];
				var ctrId="DATA_"+data[i].sid;
				if(data[i].fieldType==1){//单行文本 
				    render.push("<tr><td>"+data[i].fieldName+":</td><td><input type=\"text\" name='"+ctrId+"' id='"+ctrId+"' style=\"height:23px;width:300px\"  /></td></tr>");
				}else if(data[i].fieldType==3){// 数字文本 
					 render.push("<tr><td>"+data[i].fieldName+":</td><td><input type=\"text\" name='"+ctrId+"' id='"+ctrId+"' style=\"height:23px;width:300px\"  isNumber=\"true\" /></td></tr>");
				}else if(data[i].fieldType==2){//多行文本框
					 render.push("<tr><td>"+data[i].fieldName+":</td><td><textarea name='"+ctrId+"' id='"+ctrId+"' style=\"height:200px;width:1000px\" ></textarea></td></tr>");
				}else if(data[i].fieldType==5){//下拉列表
					render.push("<tr><td>"+data[i].fieldName+":</td><td><select name='"+ctrId+"' id='"+ctrId+"' style=\"height:23px;\" ></select></td></tr>");      
				}else if(data[i].fieldType==4){//日期时间
					render.push("<tr><td>"+data[i].fieldName+":</td><td><input type=\"text\" name='"+ctrId+"' id='"+ctrId+"' style=\"height:23px;width:300px\"  onClick=\"WdatePicker({dateFmt:'"+data[i].showType+"'})\"  class=\"Wdate\" /></td></tr>");   
				}	
				
				//渲染一个大概
				$("#table1").append(render.join(""));
				//处理富文本框
				if(data[i].fieldType==2&&data[i].showType=="富文本"){
					 uEditorObj = UE.getEditor(ctrId); //获取编辑器对象
					 uEditorObj.ready(function(){//初始化方法
							uEditorObj.setHeight(200);
					 });
				}
				//处理下拉列表
				if(data[i].fieldType==5){
					var showType=data[i].showType.split(",");
				    for(var j=0;j<showType.length;j++){
				    	$("#"+ctrId).append("<option value='"+showType[j]+"'>"+showType[j]+"</option>");
				    } 
				}
			}
			
		}else{
			var render=[];
			render.push("<tr><td colspan=\"2\"><div id=\"mess\"></div></td></tr>");
			$("#table1").append(render.join(""));
		    messageMsg("该任务模板暂且没有基础信息！", "mess","info");
		}
	}	
}


//保存
function doSave(){
	if($("#form1").valid()){
		var para=tools.formToJson($("#form1")) ;
		 $("#form1").ajaxSubmit({
	           url: "<%=contextPath%>/TeeTaskPubRecordItemController/report.action",
	           iframe: true,
	           data: para,
	           success: function(res) {
	        	   if(res.rtState){
	        		   
	        		   
	        		   $.MsgBox.Alert_auto("汇报成功！",function(){
	        			   //关闭窗口  刷新父列表
	        			   if(type!=1){
	        				   opener.datagrid.datagrid("reload");
	        			   }
	            		   window.close();
	        		   });
	        		   
	        		
	        	   }else{
	        		   $.MsgBox.Alert_auto(res.rtMsg);
	        	   }
	            },
	           error: function(arg1, arg2, ex) {
	              $.MsgBox.Alert_auto("汇报出错！");
	           },
	           dataType: 'json'});
	}
	
}




</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px" >
	<div class="topbar clearfix" id="toolbar">
	   <div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/informationReport/imgs/icon_xxhb.png">
			<span class="title"><%=taskTemplateName %>&nbsp;-&nbsp;信息汇报</span>
		</div>
	   <div class="fr right">
	      <input type="button" value="提交" class="btn-win-white" onclick="doSave();"/>
	   </div>
	</div>
  <form id="form1" method="post"  enctype="multipart/form-data" >
     <input type="hidden" name="pubRecordItemId" value="<%=pubRecordItemId %>" />
     <input type="hidden" name="pubRecordId" value="<%=pubRecordId %>" />
     <input type="hidden" name="taskTemplateId" value="<%=taskTemplateId %>" />
     <table class="TableBlock_page" id="table1">
        <tr>
           <td colspan="2">
              <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">基础信息</b>
           </td>
        </tr>
     
     
     </table>
     <table class="TableBlock_page" id="table2">
        <tr>
           <td colspan="2">
              <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		      <b style="color: #0050aa">附件信息</b>
           </td>
        </tr>
      <tr>
   		<td style="text-indent:10px;"  class="TableData" width="10%">附件选择：</td>
    	<td   class="TableData" align="left" >
    	  <div align="left">
    	     <span id="fileModel"></span>
	    	 <div id="upfileList"></div>
    	  </div>	
   	    </td>
    </tr>
     
     </table>
  </form>
  
  <script>
      $("#form1").validate();
  </script>
</body>
</html>