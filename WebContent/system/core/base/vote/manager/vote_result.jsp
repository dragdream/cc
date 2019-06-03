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
<%@include file="/header/upload.jsp"%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>投票结果查看</title>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/processbar/jquery.progressbar.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/system/core/base/vote/js/vote.js"></script>

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
	//加载基本投票信息
	var url =   "<%=request.getContextPath() %>/voteManage/getById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			var obj = getVotePersonCount(id);
			$("#subject").html(prc.subject +"<span style='color:orange;'>（<a style='color:orange;' title='查看投票情况' href='javascript:void(0);' onclick='showVoteDetail(" + id +")' >已投票：" + obj.voteCount + "人，未投票：" + obj.notVoteCount + "人</a>）</span>");
			$("#btns").append("<input type='button'  class='btn-win-white' onclick='remindFunc(" + id +")' value='提醒' />"
					+"<input class=\"btn-win-white\" type='button' onclick=\"document.execCommand('Print');\" value=\"打印\" />"+
					"<input  type='button' class=\"btn-win-white\" onclick=\"exportExcel("+id+");\" value=\"导出\"/>");
			
			
			var voteDescStr = "";
			if(prc.content){
				voteDescStr = "<span>投票描述："+prc.content+"</span>"  ;
			}
			
			
			var attachmentStr ="<div id='attachment' style='display:none;margin-top:10px;'>"	
			+"<span id='' ><b>附件：</b></span>"
			+"<hr style='width:100%;margin-bottom:5px' color='#d6d6d6'/>";
			+"</div>";
			var content =  voteDescStr 
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
	//获取投票结果
	var url = "<%=contextPath %>/voteManage/getManageVoteResult.action";
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
		var requiredStr = "";
		if(subjects[i].required ==1){
			requiredStr = "<font color='red'>(必填)</font>";
		}
		
		html.push("<tr style='background-color:#eaeaea;line-height:35px;text-indent:5px;'>");
		html.push("<td class='TableHeader' style='border:1px solid #ccc;text-align: left;font-size:14px;'>"+(i+1)+"."+subjectItem.subject+ requiredStr + "</td>");
		html.push("</tr>");
		html.push("<tr>");
		html.push("<td class='TableData' style='border-left:1px solid #ccc;border-right:1px solid #ccc;'>");
		//遍历投票数据
		if(subjectItem.type=='2' || subjectItem.type=='3'){//文本框
			var datas = subjectItem.voteItem.datas;
			for(var j=0;j<datas.length;j++){
				var voteUserName = "";
				if(datas[j].voteUserName){
					voteUserName = "<font color='#9F5000'>【"+datas[j].voteUserName+"】</font>";
				}
				if(datas[j].data){
					html.push("<div style='padding:5px;margin:5px;text-indent:5px;'>"+ voteUserName +datas[j].data +"</div>");
				}
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
				var voteUserName = "";
				if(voteItems[j].voteUserName){
					voteUserName = "<font color='#9F5000'>【"+voteItems[j].voteUserName+"】</font>";
				}
				html.push("<tr class='' style='line-height:35px;text-indent:20px;'>");
				html.push("<td class='' style='border-bottom:1px solid #ccc;width:20%;'>"+voteItems[j].name+"</td>");
				html.push("<td style='border-bottom:1px solid #ccc;width:20%;' class='progress0' percent='"+voteItems[j].total/total+"'></td>");
				html.push("<td class='' style='border-bottom:1px solid #ccc;width:5%;'>"+voteItems[j].total+"票</td>");
				html.push("<td class='' style='border-bottom:1px solid #ccc;'><span title='"+voteItems[j].voteUserName+"'>"+voteUserName+"</span></td>");
			}
			html.push("</table>");
		}
		html.push("</td>");
		html.push("</tr>");
		html.push("<tr>");
		html.push("<tr class='' style='line-height:35px;text-indent:20px;'>");
		html.push("<td class='' style='border:0px;height:20px' colspan='4'></td>");
		html.push("<tr>");
	}
		
	$("#tb").html(html.join(""));
	
	//读取进度条$("#progress").progressBar(json.rtData.progress, {showText: true});
	$(".progress0").each(function(i,obj){
		$(obj).progressBar(parseInt($(obj).attr("percent")*100), {showText: true});
	});
}



/**
 * 获取未投票/已投票人员数据
 *  {voteCount:'已投人数',notVoteCount:'未投人数'}
 */
function getVotePersonCount(id){
	var url =   "<%=contextPath%>/voteManage/getVotePersonCount.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		return jsonObj.rtData;
	} else {
		$.MsgBox.Alert_auto(jsonObj.rtMsg);
	}
	return "";
}
 //查看投票情况
function showVoteDetail(id){
  var url = contextPath + "/system/core/base/vote/manager/showVoteDetail.jsp?sid=" + id ;
  bsWindow(url ,"查看投票情况",{width:"500",height:"300",buttons:
     [{name:"关闭",classStyle:"btn-alert-gray"}]
  ,submit:function(v,h){
    var cw = h[0].contentWindow;
    if(v=="修改"){
      
    }else if(v == "删除"){
      
    }else if(v=="关闭"){
      return true;
    }
  }});
}
 //提醒所有未投票人
function remindFunc(id){
	  $.MsgBox.Confirm ("提示", '是否确认提醒所有未投票人？', function(){
		  var url =  "<%=contextPath%>/voteManage/remindVoteById.action";
		  var para = {sid:id};
		  var json = tools.requestJsonRs(url,para);
		  if(json.rtState){
		     $.MsgBox.Alert_auto("提醒成功！");
			//location.reload();
		  }else{
			 $.MsgBox.Alert_auto(json.rtMsg);
		  } 
	  });
}

 
 
</script>

</head>
<body onload="doInit()" id="body" style="margin: 10px;overflow-x:hidden;padding-top: 10px">
  <div id="toolbar" class="clearfix" >
      <div class="fl" style="position:static;">
		<b><span class="">投票结果&nbsp;———&nbsp;<span id="subject"></span></span></b>   
	  </div>
	   <div id="btns" class="fr right" style="margin-right:20px">
	    
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
</html>