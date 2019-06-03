<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %> 
<%
 
	int voteId  = TeeStringUtil.getInteger(request.getParameter("voteId"), 0); //投票Id
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@include file="/header/upload.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>投票结果查看</title>

<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<style>
table{
	border-collapse:collapse;
}
</style>


<script>
/**
 *  [{subject:'',type:'1(4,0)',voteItems:[{name:'item1',total:'',name:'item2',total:''}]},
	 * 			{subject:'',type:'2(3)',voteItem:{name:'',datas:[{user:'',data:''}]}}]
 */
var id = "<%=voteId%>";
function doInit(){
	var privFlag = isVotePrivfunc(id);
	if(privFlag == '1'){
		messageMsg("投票后允许查看结果","message","info");
		$("#message").show();
		return;
	}else if(privFlag == '3'){
		messageMsg("该投票不允许查看结果","message","info");
		$("#message").show();
		return;
	}
	
	
	//加载基本投票信息
	var url =   "<%=request.getContextPath() %>/voteManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			$("#subject").html(prc.subject);
			
			var anonymityDesc = "（说明：红色字体是自己的投票内容。）";
			if(prc.anonymity =='1'){
				anonymityDesc = "（说明：该投票结果为匿名投票，不能查阅自己的投票内容。）";
			}
			
			var voteDescStr = "";
			if(prc.content){
				voteDescStr = "<span>投票描述：" + prc.content+"</span>";
			}
			
			var attachmentStr = "<div id='attachment' style='display:none;margin-top:10px;'>"
				+"<span id=''><b>附件：</b></span>"
			    +"<hr style='width:100%;margin-bottom:5px' color='#d6d6d6'/>"
			    +"</div>";
			
			var content = voteDescStr + "&nbsp;<font color='red'>" +anonymityDesc + "</font>"
			+"<br>" + attachmentStr;
			$("#content").html(content);
			
			
			var attaches = prc.attacheModels;
			if(attaches.length>0){
				
				$("#attachment").show();
				for(var i=0;i<attaches.length;i++){
					var attach = attaches[i];
					attach["priv"] = 3;
					var fileItem = tools.getAttachElement(attach);
					$("#attachment").append(fileItem);
				}
			}
		}
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
		return;
	}

	
	//获取自己投过的票
	var url = "<%=contextPath %>/voteManage/getMyVoteItemsData.action";
	var para = {sid : id};
	var json = tools.requestJsonRs(url, para);
	var myitems = json.rtData;
	
	//获取所有投票结果
	var url = "<%=contextPath %>/voteManage/getVoteResult.action";
	var para = {sid : id};
	var json = tools.requestJsonRs(url, para);
	var html = [];
	var subjects = json.rtData;
	if(!json.rtState){
		messageMsg(json.rtMsg,"message","info");
		$("#message").show();
		return;
	}
	//遍历投票数据
	for(var i=0;i<subjects.length;i++){
		var subjectItem = subjects[i];
		
		var requiredStr = "";
		if(subjects[i].required ==1){
			requiredStr = "<font color='red'>(必填)</font>";
		}
		html.push("<tr style='background-color:#eaeaea;line-height:35px;text-indent:5px;'>");
		html.push("<td class='TableHeader' style='border:1px solid #ccc;text-align: left;font-size:14px;' >"+(i+1)+"."+subjectItem.subject +requiredStr +"</td>");
		html.push("</tr>");
		html.push("<tr>");
		html.push("<td class='TableData' style='border-left:1px solid #ccc;border-right:1px solid #ccc;'>");
		if(subjectItem.type=='2' || subjectItem.type=='3'){//单行文本框或多行文本框
			var datas = subjectItem.voteItem.datas;
			var itemId = subjectItem.voteItem.itemId;
			for(var j=0;j<datas.length;j++){
				var hasExist = false;
				for(var k=0;k<myitems.length;k++){
					if(myitems[k].itemId==itemId && myitems[k].userId == datas[j].userId){
						hasExist = true;
					}
				}
				html.push("<div style='padding:5px;margin:5px;border:1px solid #f0f0f0;"+(hasExist?"color:red":"")+"'>"+datas[j].data+"</div>");
			}
		}else{
			var voteItems = subjectItem.voteItems;
			//投票项目总和
			var total = 0;
			for(var j=0;j<voteItems.length;j++){
				total+=voteItems[j].total;
			}
			html.push("<table  style='width:100%;font-size:12px;'>");
			for(var j=0;j<voteItems.length;j++){
				html.push("<tr style='line-height:35px;text-indent:20px;'>");
				html.push("<td style='border-bottom:1px solid #ccc;width:20%;'>");
				var hasExist = false;
				for(var k=0;k<myitems.length;k++){
					if(myitems[k].itemId==voteItems[j].itemId){
						hasExist = true;
					}
				}
				if(hasExist){
					html.push("<span style='color:red'>");
				}else{
					html.push("<span>");
				}
				//alert(voteItems[j].name + ">>" + voteItems[j].total);
				html.push(voteItems[j].name);
				html.push("</span>");
				html.push("</td>");
				html.push("<td  style='border-bottom:1px solid #ccc;width:20%;'  class='progress0' percent='"+voteItems[j].total/total+"'></td>");
				html.push("<td style='border-bottom:1px solid #ccc;width:5%;'>"+voteItems[j].total+"票</td>");
				html.push("<td class='' style='border-bottom:1px solid #ccc;'><span title='"+voteItems[j].voteUserName+"'></span></td>");
				html.push("<tr>");
			}
			html.push("</table>");
		}
		html.push("</td>");
		html.push("</tr>");
		html.push("<tr>");
		html.push("<td class='' style='line-height:35px;text-indent:20px;'></td>");
		html.push("<td class='' style='border:0px;height:20px' colspan='4'></td>");
		html.push("<tr>");
	}
		
	$("#tb").html(html.join(""));
	
	//读取进度条$("#progress").progressBar(json.rtData.progress, {showText: true});
	$(".progress0").each(function(i,obj){
		//alert($(obj).attr("percent"));
		$(obj).progressBar(parseInt($(obj).attr("percent")*100), {showText: true});
	});
}

//查看投票结果权限
function isVotePrivfunc(id){
	var privFlag = 0; //0-可查投票结果；1-投票后允许查看结果;2-投票前允许查看;3-该投票不允许查看结果
	var url =   "<%=contextPath%>/voteManage/isVotePrivById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		privFlag = prc.privFlag;
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	return privFlag;
}




</script>

</head>
<body onload="doInit()" id="body" style="margin: 10px;overflow-x:hidden;padding-top: 10px">
  
  <div id="toolbar" class="clearfix" >
      <div class="fl" style="position:static;">
		<b><span class="">投票结果&nbsp;———&nbsp;<span id="subject"></span></span></b>   
	  </div>
	   <span class="basic_border" ></span>
	  <div  style="position:static;" id="content">
	     
	  </div>  
  </div>
  <div>
     <table class="TableList" id="tb" style="width:99%;font-size:12px;margin-top:10px;border-collapse:collapse;">
     </table>
  </div>
  <div id="message" style="margin-top: 10px;display: none"></div>
</body>




<!-- <body onload="doInit()" id="body" style="margin-bottom: 10px;">
<div class="base_layout_top" style="position:static">
	<span class="easyui_h1"><b>投票结果&nbsp;-&nbsp;<span id="subject"></b></span></span>
</div>
<div id="content" style="margin-left:30px; margin-bottom:10px; margin-top:10px;  font-size:14px;width: 90%;"></div>
	<center>
	<table class="TableList" id="tb" style="width:95%;font-size:12px">
		
	</table>
	</center>
</body> -->
</html>