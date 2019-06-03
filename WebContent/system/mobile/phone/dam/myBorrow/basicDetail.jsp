<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
   int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
%>

<!DOCTYPE HTML>
<html>
<head>
<title>档案详情</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=contextPath %>/common/js/sys2.0.js"></script>
<style>
#topPopover {
	position: fixed;
	top: 16px;
	right: 6px;
	width: 120px;
}
#topPopover .mui-popover-arrow {
	left: auto;
	right: 6px;
}

.app-row{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
}
.app-row br{
	margin:0px;
}
.app-row-content{
	margin-left:14px;
	padding-top:10px;
	padding-right:10px;
	padding-bottom:10px;
	border-bottom:1px solid #f0f0f0;
	color:gray;
}

</style>
</head>

<script type="text/javascript">
var sid=<%=sid %>;
//初始化方法
function doInit(){

	if(sid>0){
		getInfoBySid();
		getAttchList();
	}
	
	
	//返回按钮
	backBtn.addEventListener("tap",function(){
		history.go(-1);
	});
}


//根据主键获取详情
function getInfoBySid(){
	var url=contextPath+"/TeeDamFilesController/getInfoBySid.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{sid:sid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			if(json.rtState){
				var prc = json.rtData;
				if (prc && prc.sid) {
					bindJsonObj2Cntrl(prc);
				}
			}else{
				alert(json.rtMsg);
			}
		}
	});
}

//获取附件列表
function getAttchList(){
	
	var url=contextPath+"/TeeFileAttchController/getFileAttchListByFileId.action";
	mui.ajax(url,{
		type:"POST",
		dataType:"JSON",
		data:{fileId:sid},
		timeout:10000,
		async:false,
		success:function(text){
			var json = eval("("+text+")");
			var attList=json.rtData;
			var html="";
			if(attList==null||attList.length==0){
				html="<div id='mess'align='center' ></div>";
				$("#attList").append(html);
				messageMsg("暂无附件信息","mess","info" ,"" );
			}else{
				for(var i=0;i<attList.length;i++){
					var attModel=attList[i].attModel;
					if(attModel!=null){
						attModel['priv']=0;
					}
					html="<li class=\"mui-table-view-cell mui-collapse\">"
					     +"<a class=\"mui-navigate-right\" href=\"#\">"+attList[i].title+"&nbsp;(共"+attList[i].pageNum+"页)</a>"
					     +"<div class=\"mui-collapse-content\">"
						 +"<div class=\"mui-input-group\" style='border-bottom:1px solid #f0f0f0'>"
						 +"<div class=\"mui-input-row\">"
						 +"<label>文件字：</label>"
						 +"<label>"+attList[i].wjz+"</label>"
						 +"</div>"
					 	 +"</div>"
					 	 +"<div class=\"mui-input-group\" style='border-bottom:1px solid #f0f0f0'>"
						 +"<div class=\"mui-input-row\">"
						 +"<label>责任者：</label>"
						 +"<label>"+attList[i].manager+"</label>"
						 +"</div>"
						 +"</div>"
					     +"</div>"
				         +"</li>";
					
					$("#attList").append(html);
				}
				
			}
		}
	});
}




</script>
<body onload="doInit();">
	<header class="mui-bar mui-bar-nav">
		<span class="mui-icon mui-icon-back" id="backBtn" ></span>
		<h1 class="mui-title">档案详情</h1>
		<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover" id="moreOpt" style="display: none"></a>
	</header>
	<div id="muiContent" class="mui-content">
		<div class="mui-card">
			<div class="mui-card-header">基础信息</div>
			<div class="mui-card-content">
				<div class="mui-card-content-inner" id="div1">
				     <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>机构代码：</label>
						   <label><span  name="orgCode" id="orgCode" ></span></label>
						</div>
					 </div> 
				     <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>全宗号：</label>
						   <label><span  name="qzh" id="qzh" ></span></label>
						</div>
					 </div>
				     <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>年份：</label>
						   <label><span  name="year" id="year" ></span></label>
						</div>
					 </div>
				     <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>保管期限：</label>
						   <label><span  name="retentionPeriodStr" id="retentionPeriodStr"  ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>所属卷盒：</label>
						   <label><span name="boxNo" id="boxNo"  ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>件号：</label>
						   <label><span name="jh" id="jh"></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>档案号：</label>
						   <label><span name="dah" id="dah"></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>文件标题：</label>
						   <label><span  name="title" id="title" ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>发/来文单位：</label>
						   <label><span  name="unit" id="unit" ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>文件编号：</label>
						   <label><span  name="number" id="number" ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>密级：</label>
						   <label><span name="mj" id="mj"  ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>缓急：</label>
						   <label><span  name="hj" id="hj" ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>主题词：</label>
						   <label><span  name="subject" id="subject"  ></span></label>
						</div>
					 </div>
					 <div class="mui-input-group" style='border-bottom:1px solid #f0f0f0'>
					    <div class="mui-input-row">
						   <label>备注：</label>
						   <label><span  name="remark" id="remark"  ></span></label>
						</div>
					 </div>
				</div>
			</div>
		</div>
		
		<div class="mui-card">
			<div class="mui-card-header">附件信息</div>
			<div class="mui-card-content">
				<ul class="mui-table-view" id="attList">
					
				</ul>
			</div>
		</div>
		
</div>




</body>
</html>