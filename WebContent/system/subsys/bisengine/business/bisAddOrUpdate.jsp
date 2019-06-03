<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header1.0.jsp"%>
<%
	String bisKey = request.getParameter("bisKey");
    String operate=request.getParameter("operate");
    int pkid=TeeStringUtil.getInteger(request.getParameter("pkid"), 0);
    
%>
<%@ include file="/header/upload.jsp" %>
<title><%="add".equals(operate)?"新建":"编辑" %></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.Text{
	border:1px solid gray;
	width:80%;
	height:25px;
}
.Text:focus{
	border:1px solid #66afe9;
}
.Associate{
	height:25px;
}
.TextArea{
	height:70px;
	width:80%;
}
label{
	font-weight:normal;
}
body{
	background-image:url('preview-bg.png');
}
.form{
	width:800px;
	margin:0 auto;
	-webkit-box-shadow: 0 0 5px rgba(0, 0, 0, 0.4);
	box-shadow: 0 0 5px rgba(0, 0, 0, 0.4);
	background:#fff;
	margin:15px auto 15px;
	border-radius:4px;
	padding:20px;
}
/* .form table{
	width:auto!important;
} */
</style>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script>
var bisKey='<%=bisKey%>';
var operate='<%=operate%>';
var pkid=<%=pkid%>;

var swfUploadObj;
//初始化
function doInit(){

	//动态加载页面信息
	var url=contextPath+"/businessModelController/getModelContent.action";
	var json = tools.requestJsonRs(url,{bisKey:bisKey,operate:operate});
	var content=json.rtData;
	$("#form1").append(content);

	
	
	//获取所有上传组建
	var uploadArray=$("input.upload");
	for(var i=0;i<uploadArray.length;i++){
		var obj=$(uploadArray[i]);
		var fc=obj.prev().prev().prev().attr("id");
		var rc=obj.prev().prev().attr("id");
		var uh=obj.prev().attr("id");
		var vh=obj.attr("id");
		var itemId=obj.next().attr("id");
		//alert(itemId);
		doInitUpload(fc,rc,uh,vh,"BIS_ITEM_"+itemId);	
	}
	
	
	//如果是编辑   初始化数据
	if(operate=="update"){
		//加载原有的数据
		var url=contextPath+"/businessModelController/getRecordByPkId.action";
		var json1 = tools.requestJsonRs(url,{pkId:pkid,bisKey:bisKey});
		if(json1.rtState){
			bindJsonObj2Cntrl(json1.rtData);
			////获取所有上传组建
			var uploadArray=$("input.upload");
			//alert(uploadArray.length);
			for(var i=0;i<uploadArray.length;i++){
				var attIds=$(uploadArray[i]).val();
				//alert(attIds);
				 //根据附件id  获取附件集合
				var url1=contextPath+"/businessModelController/getAttachList.action";
				var json1 = tools.requestJsonRs(url1,{attIds:attIds});
				var modelList=json1.rtData;
				//alert(modelList.length);
				for(var j=0;j<modelList.length;j++){	
					var att = {priv:1+2+4,fileName:modelList[j].fileName,ext:modelList[j].ext,sid:modelList[j].sid};
					var attach = tools.getAttachElement(att,{});	
					//$(uploadArray[i]).after("<p class='attach'  fileName='"+modelList[i].fileName+"' ext='"+modelList[i].ext+"' sid='"+modelList[i].sid+"'></p>");				
					$(uploadArray[i]).prev().prev().prev().append("<p class='attach'  fileName='"+modelList[j].fileName+"' ext='"+modelList[j].ext+"' sid='"+modelList[j].sid+"'></p>");				
					$("p[sid='"+modelList[j].sid+"']").append(attach);
				} 
				
			}
			
		}
	}
	
	//自动调节form表单的宽度	
	var width_val = $('.setWidth').find("table").width();
	//console.log(width_val);
	var formWidth = width_val + 20;
	//console.log(formWidth);
	$(".form").width(formWidth);
}

//保存按钮
function commit(){
        
		var requiredFields = $("input[required],textarea[required],select[required],checkbox[required],radio[required]");
		for(var i=0;i<requiredFields.length;i++){
			var jqObj = $(requiredFields[i]);
			if(jqObj.val()==""){
				alert(jqObj.attr("title")+"不允许为空！");
				jqObj.focus();
				return;
			}
		}
        
      
        	var url = contextPath + "/businessModelController/addOrUpdateRecord.action";
        	var para=tools.formToJson($('#form1'));
    		var p=tools.jsonObj2String(para);
    		var json = tools.requestJsonRs(url, {bisKey : bisKey,operate:operate,param:p,pkId:pkid});
    		if(json.rtState){
    			alert("保存成功！");
    			back();
    		} 
     
	    
}

//点击关联主表的按钮
function showMainTableDialog(fieldId){
	var url=contextPath+"/system/subsys/bisengine/business/mianTableList.jsp?fieldId="+fieldId;
	dialog(url,600,400);
}

//返回按钮
function back(){
	
	window.location = "bis.jsp?bisKey="+bisKey;
}


//快速上传附件
function doInitUpload(fc,rc,uh,vh,model){
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
				fileContainer:fc,//文件列表容器
				renderContainer:rc,//渲染容器
				uploadHolder:uh,//上传按钮放置容器
				valuesHolder:vh,//附件主键返回值容器，是个input
				quickUpload:true,//快速上传
				showUploadBtn:false,//不显示上传按钮
				queueComplele:function(){//队列上传成功回调函数，可有可无
					
				},
				renderFiles:true,//渲染附件
				post_params:{model:model}//后台传入值，model为模块标志
				});
}


		

		
</script>
<body onload="doInit();" id="myBody" >
	<form id="form1" class="form setWidth"></form>
	<div align="center">	
		<br/>
			<button type="button" class="btn btn-primary btn-sm" onclick="commit()">保&nbsp;&nbsp;存</button>
			&nbsp;&nbsp;
			<button type="button" class="btn btn-default btn-sm"
			onclick="javascript:back();">返&nbsp;&nbsp;回</button>
	</div>
</body>
</html>