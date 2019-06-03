<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
 
	int voteId  = TeeStringUtil.getInteger(request.getParameter("voteId"), 0); //投票Id
%>

<html>
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script src="<%=contextPath %>/common/js/tools.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>投票</title>
<style>
table{
	border-collapse:collapse;
}
</style>
<script>
var id = "<%=voteId%>";
var anonymity ='0';

$.MsgBox = {
		Alert_auto:function(msg){
			alert(msg);
		}
}

function doInit(){
	
	//判断是否已经投票过
	var url =   "<%=request.getContextPath() %>/voteManage/checkVote.action";
	var para = {voteId : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if(jsonObj.rtData){
		var checkFlag = jsonObj.rtData.checkFlag;
		if(checkFlag == '1'){
			messageMsg("您无权限投票。","message","info");
			$("#message").show();
			return;
		}else if(checkFlag == '2'){
			messageMsg("您已投票过，请勿重复投票。","message","info");
			$("#message").show();
			return;
		}
	}
	
	//加载基本投票信息
	var url =   "<%=request.getContextPath() %>/voteManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		//获取发布状态
		var publishStatus=prc.publish;//0-未发布  1-已发布；2-终止
		//获取投票开始时间和结束时间
	    var beginDateStr=prc.beginDateStr;
		var endDateStr=prc.endDateStr;
		var beginDate= new Date(Date.parse(beginDateStr.replace(/-/g,   "/"))); 
		var endDate= new Date(Date.parse(endDateStr.replace(/-/g,   "/")));
		//获取当前的时间
		var currentDate=new Date();
		
		if(currentDate>=beginDate&&currentDate<=endDate){//在时间段内
			if(publishStatus=="2"){			
				messageMsg("投票已终止","message","info");
				$("#message").show();
				return;	
			}else{

				if (prc && prc.sid) {
					$("#subject").html(prc.subject);
					var voteDescStr = "";
					if(prc.content){
						voteDescStr = "<span>" + prc.content+"</span>";
					}
					
					var attachmentStr ="<div id='attachment' style='display:none;margin-top:10px;'>"	
						+"<span id='' ><b>附件：</b></span>"
						+"<hr style='width:100%;margin-bottom:5px' color='#d6d6d6'/>";
						+"</div>";
						
					var content = voteDescStr 
					+"<br>" + attachmentStr;
					$("#content").html(content);
					
					
					var attaches = prc.attacheModels;
					if(attaches.length>0){
						$("#attachment").show();
						for(var i=0;i<attaches.length;i++){
							var item = attaches[i];
							$("#attachment").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;("+item.sizeDesc+")"+"</a><div>");
						}
					}
					
					anonymity = prc.anonymity;
				}
				
				
				
				
			}
			
		}else if(currentDate<beginDate){//未开始
			messageMsg("投票未开始","message","info");
		    $("#message").show();
			return;
		}else if(currentDate>endDate){//已经结束
			messageMsg("投票已结束","message","info");
			$("#message").show();
			return;
		}
		
		
		
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
		return;
	}

	
	var url = "<%=contextPath %>/voteManage/getVotes.action";
	var para = {sid : id};
	var json = tools.requestJsonRs(url, para);
	var html = [];
	var subjects = json.rtData;
	if(!json.rtState){
		messageMsg(json.rtMsg,"message","info");
		$("#message").show();
		return;
	}
	
	for(var i=0;i<subjects.length;i++){
		var subjectItem = subjects[i];
		var voteId = subjects[i].voteId;
		var requiredStr = "";
		if(subjects[i].required ==1){
			requiredStr = "<font color='red'>(必填)</font>";
		}
		html.push("<tr style='background-color:#eaeaea;line-height:35px;text-indent:5px;' >");
		html.push("<td class='TableHeader' style='border:1px solid #ccc;text-align: left;font-size:14px;'>"+(i+1)+"."+subjectItem.subject+requiredStr +"</td>");
		html.push("</tr>");
		html.push("<tr>");
		html.push("<td class='TableData' style='padding:5px;border-left:1px solid #ccc;border-right:1px solid #ccc;border-bottom:1px solid #ccc;'  subject='"+subjectItem.subject+"' type='"+subjectItem.type+"' min='"+subjectItem.min+"' max='"+subjectItem.max+"' required0='"+subjects[i].required+"'>");
		if(subjectItem.type=='0'){//多选
			var voteItems = subjectItem.voteItems;
			for(var j=0;j<voteItems.length;j++){
				var name = voteItems[j].name;
				var itemId = voteItems[j].itemId;
				html.push("<input style='margin-top:5px;margin-bottom:5px'  class='BigCheckbox' type='checkbox' itemId="+itemId+" id='cb"+itemId+"' name='cb"+itemId+"'/>&nbsp;<label  style='width: 95%;margin-top:3px;' for='cb"+itemId+"'>"+name+"</label><br/>");
			}
		}else if(subjectItem.type=='1'){//单选
			var voteItems = subjectItem.voteItems;
			for(var j=0;j<voteItems.length;j++){
				var name = voteItems[j].name;
				var itemId = voteItems[j].itemId;
				html.push("<input style='margin-top:5px;margin-bottom:5px' type='radio' id='rd"+itemId+"' itemId="+itemId+" name='vote"+voteId+"'/>&nbsp;<label  style='width: 95%;' for='rd"+itemId+"'>"+name+"</label><br/>");
			}
		}else if(subjectItem.type=='2'){//文本框
			var itemId = subjectItem.voteItem.itemId;
			var name = subjectItem.voteItem.name;
			html.push("<input  class='BigInput' type='text' itemId="+itemId+" name='ipt"+itemId+"' id='ipt"+itemId+"' style=''/>");
		}else if(subjectItem.type=='3'){//多行
			var itemId = subjectItem.voteItem.itemId;
			var name = subjectItem.voteItem.name;
			html.push("<textarea class='BigTextarea' itemId="+itemId+" name='txt"+itemId+"' id='txt"+itemId+"' style='height:150px'></textarea>");
		}else if(subjectItem.type=='4'){//下拉
			var voteItems = subjectItem.voteItems;
			html.push("<select class='BigSelect' id='slt"+voteId+"'>");
			for(var j=0;j<voteItems.length;j++){
				var name = voteItems[j].name;
				var itemId = voteItems[j].itemId;
				html.push("<option name='opt"+itemId+"' itemId="+itemId+">"+name+"</option>");
			}
			html.push("</select>");
		}
		
		if(subjectItem.ifContent==1){
			html.push("<div style='margin-top:10px;color:red;font-size:12px'>");
			html.push("<b>说明：</b><br/>");
			html.push(subjectItem.content);
			html.push("</div>");
		}
		
		html.push("</td>");
		html.push("</tr>");
		html.push("<tr>");
		html.push("<tr class='' style='line-height:35px;text-indent:20px;'>");
		html.push("<td class='' style='border:0px;height:20px' colspan='4'></td>");
		html.push("<tr>");
	}
		
	$("#tb").html(html.join(""));
}


function vote(){
	var para = {};
	var valide = true;
	var msg;
	$("td[type]").each(function(i,obj){
		var type = $(obj).attr("type");
		var required = $(obj).attr("required0");
		var subject = $(obj).attr("subject");
		if(type=='0'){//复选框
			var max = parseInt($(obj).attr("max"));
			var min = parseInt($(obj).attr("min"));
			var count = 0;
			$(obj).find("input:checked").each(function(j,c){
				count++;
				para["item"+$(c).attr("itemId")] = $(c).attr("itemId");
			});
			if(required=='1' && count==0){
				valide = false;
				msg = subject+" 投票项未选择";
				return;
			}
			if(count<min || count>max){
				valide = false;
				msg = subject+" 选项最少选择"+min+"个，最大选择"+max+"个";
				return;
			}
		}else if(type=='1'){//单选框
			var count = 0;
			$(obj).find("input:checked").each(function(j,c){
				para["item"+$(c).attr("itemId")] = $(c).attr("itemId");
				count++;
			});
			if(required=='1' && count==0){
				valide = false;
				msg = subject+" 投票项未选择";
				return;
			}
		}else if(type=='2'){//文本框
			var text = $(obj).find("input:first");
			para["item"+$(text).attr("itemId")] = $(text).val();
			if(required=='1' && text.val()==""){
				valide = false;
				msg = subject+" 投票项未填写";
				return;
			}
		}else if(type=='3'){//多行文本框
			var text = $(obj).find("textarea:first");
			para["item"+$(text).attr("itemId")] = $(text).val();
			if(required=='1' && text.val()==""){
				valide = false;
				msg = subject+" 投票项未填写";
				return;
			}
		}else if(type=='4'){//下拉
			var opt = $(obj).find("select:first").find("option:selected");
			para["item"+$(opt).attr("itemId")] = $(opt).attr("itemId");
		}
	});
	para["anonymity"] = 0;
	
	if(!valide){
		$.MsgBox.Alert_auto(msg);
		return;
	}
	para["voteId"] = id;
	if(anonymity=='1'){
		para["anonymity"] = 1;
		
		  if(window.confirm("是否确认匿名投票？")){
			  var url = contextPath+"/voteManage/savePersonalVote.action";
				var json = tools.requestJsonRs(url, para);
				if(json.rtState){
					var checkFlag = json.rtData.checkFlag;
					if(checkFlag == '1'){
						messageMsg("您无权限投票。","message","info");
						$("#message").show();
						return;
					}else if(checkFlag == '2'){
						messageMsg("您已投票过，请勿重复投票。","message","info");
						$("#message").show();
						return;
					}
					$.MsgBox.Alert_auto("投票成功！");
					CloseWindow();
				}else{
					$.MsgBox.Alert_auto(json.rtMsg);
				}  
		  }

	}else{
		if(window.confirm("是否确认投票？")){
			var url = contextPath+"/voteManage/savePersonalVote.action";
			var json = tools.requestJsonRs(url, para);
			if(json.rtState){
				var checkFlag = json.rtData.checkFlag;
				if(checkFlag == '1'){
					messageMsg("您无权限投票。","body","info");
					return;
				}else if(checkFlag == '2'){
					messageMsg("您已投票过，请勿重复投票。","body","info");
					return;
				}
				$.MsgBox.Alert_auto("投票成功！");
				CloseWindow();
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			} 
		}
	}

	
	
}
</script>

</head>
<body onload="doInit()" id="body" style="overflow-x:hidden;">
  <div id="toolbar" class="clearfix" style="padding:10px">
      <div class="fl" style="position:static;">
		<b><span class=""><span id="subject"></span></span></b>   
	  </div>
	   <span class="basic_border" ></span>
	  <div  style="position:static;font-size:14px" id="content">
	     
	  </div>  
	  <div id="btns" class="" style="text-align:right;">
	       <button class="btn-win-white" onclick="try{vote();}catch(e){alert(e);}">提交</button>
		   &nbsp;&nbsp;
		   <button class="btn-win-white" onclick="CloseWindow()">关闭</button>
	   </div>
  </div>
  <div>
     <table class="TableList" id="tb" style="width:99%;font-size:12px;margin-top:10px;border-collapse:collapse;">
     </table>
  </div>
  <div id="message" style="margin-top: 10px;display: none"></div>
</body>
</html>