<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的日志</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>

<%
	TeePerson person = (TeePerson)session.getAttribute(TeeConst.LOGIN_USER);
%>
<!-- jQuery 布局器 -->
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/TeeMenu.js"></script>
	<style>

		body{
			background: #f2f2f2;
			font-family: "微软雅黑";
		}
		.journalDate{
			font-size: 16px;
		}
		.journal_blcok{
			overflow:hidden;
			margin-top: 20px;
		}
		.journalLeft{
			clear: both;
			overflow: hidden;
			float: left;
		}
		.timeLine{
			line-height: 60px;
		    width: 60px;
		    height: 60px;
		    background: #30cafe;
		    border-radius: 35px;
		    text-align: center;
		    border: 3px solid #d2d2d2;
		    color: #fff;
		    float: left;
		}
		.journalRight{
			float: left;
			width: 90%;
			border-radius: 10px;
			background-color: #fff;
			margin-left: 10px;
			padding: 20px 0;
		}
		.journalRight .title{
			color:#309ffd;
		}
		.journalRight .title,.journalRight .journal,.journalRight .journalTime{
			padding-left: 20px;
		}
		.journalRight .journalTime,.journalList li{
			color: #999;
		}
		.journalRight .journalTime{
			line-height: 35px;
		}
		.journalList{
			overflow: hidden;
			padding: 0 20px 0px 0;
			border-bottom: 1px solid #f2f2f2;
			width: 94%;
			margin: 0 auto;
			margin-left: 20px;
		}
		.journal{
			height: 35px;
			line-height: 35px;
		}
		.journalList li{
			font-size: 12px;
			float: left;
			list-style: none;
			padding-bottom: 9px;
			margin-right: 30px;
			cursor: pointer;
		}
		.journalList li.j_select{
			border-bottom: 2px solid #30cafd;
		}
		.journalContent{
			margin: 0 20px;
			height:500px;
			margin-top: 10px;
			background-color: #eaeaea;
		}
		.journalContent iframe{
			width: 100%;
			height: 100%;
		}

		.addNew{
		    border-radius: 5px;
			width: 45px;
			height: 20px;
			color: #fff;
			background-color: #379ff7;
			text-align: center;
			line-height: 20px;
			font-size: 14px;
			float: right;
			margin-right: 20px;
			cursor: pointer;
			font-weight: normal;
		}
		.catch_up{
			float: right;
			margin-right: 20px;
			width: 45px;
			height: 20px;
			color: #fff;
			background-color: #379ff7;
			text-align: center;
			line-height: 20px;
			font-size: 14px;
			cursor: pointer;
			margin-top: 5px;
		}
		.hr{
			margin-top: 66px;
			width: 2px;
			background: #e0e0e0;
			margin-left: 33px;
			position: absolute;

		}

		.j_l_content{
			text-indent: 15px;
			word-wrap: break-word;
		}
		.j_content_list li{
			padding-left: 20px;
			padding-right: 20px;
			line-height: 25px;
		}
		.j_content_list li:hover{
		}
		.j_content_list .j_opers{
			float: right;
		}
		.j_content_list .j_opers a{
			font-size: 12px;
			text-decoration: none;
			margin-right:7px;
		}
		.noContent{
			font-size: 14px;
		}
		.reply{
			background-color: #fffce9;
			border:1px solid #ebdebc;
		}
		.reply ul{
			margin-bottom: 5px;
		}
		.readMore{
			text-align: center;
			padding: 5px;
			cursor: pointer;
			color: #007eff;
			background: #ebebeb;
		}
	</style>

<script>
var userId = <%=person.getUuid()%>;

function addDiary(day,$this){
	var url = contextPath+"/system/core/base/diary/manage/addDiary.jsp?day="+day;
	openFullWindow(url,"添加日志");
	//$($this).parents(".journal_blcok").find(".journalContent").slideDown();
	//$($this).parents(".journal_blcok").find(".journalContent").find("iframe").attr("src",url);
// 	top.bsWindow(url,"添加日志",{height:"70%",submit:function(v,h){
// 		var cw = h[0].contentWindow;
// 		//var createTimeDesc = cw.document.getElementById("createTimeDesc");
// 		//createTimeDesc.value=day;
// 		cw.doSave(function(json){
// 			if(json.rtState){
// 				$.jBox.tip("保存成功","success");
// 				var json = tools.requestJsonRs(contextPath+"/diaryController/getDiaryById.action",{sid:json.rtData});
// 				renderData(json.rtData,false);
// 				top.window.BSWINDOW.modal("hide");
// 			}
// 		});
// 	}});
}

function modifyDiary(sid){
	var url = contextPath+"/system/core/base/diary/manage/addDiary.jsp?sid="+sid;
	//$(".iframe").attr("src",url);
	openFullWindow(url,"编辑日志");
// 	top.bsWindow(url,"修改日志",{height:"70%",submit:function(v,h){
// 		var cw = h[0].contentWindow;
// 		cw.doSave(function(json){
// 			if(json.rtState){
// 				$.jBox.tip("保存成功","success");
// 				doSearch();
// 				top.window.BSWINDOW.modal("hide");
// 			}
// 		});
// 	}});
}

function showDiary(sid){
	var url = contextPath+"/system/core/base/diary/manage/detail.jsp?sid="+sid;
	$.jBox.open("iframe:"+url,"日志详情",800,400,{buttons:{'关闭':true}});
}


function delDiary(sid){
	$.MsgBox.Confirm("提示","确定要删除吗？",function(){
			var url = contextPath + '/diaryController/delDiary.action?sid='+sid;
			var json = tools.requestJsonRs(url);
			if(json.rtState){
				$("#diary"+sid).css({opacity:0}).animate({height:0},500,function(){
					$(this).remove();
					hrAutoHeight();
					//window.location.reload("reload");
					$(".j_content_list").each(function(i,obj){
						if($(obj).html()==""){
							$(obj).append("<p style='padding-left:20px;font-size:14px;'>无日志内容！</p>");
							}
					});
					
					
				});

				return true;
			}
	});
}
var editor;
function doInit(){
	setDefaultTime();
	doSearch();
	hrAutoHeight();
}

function setDefaultTime(){
	var url = contextPath + '/diaryController/returnStartAndEndTime.action';
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var startTime = document.getElementById("startTime");
		var endTime = document.getElementById("endTime");
		startTime.value=json.rtData.startTime;
		endTime.value=json.rtData.endTime;
	}
}

function doSearch(){
	if(checkForm()){
		var url = contextPath + '/diaryController/doSearch.action';
		var para =  tools.formToJson($("#form1"));
		//parent.$.MsgBox.Alert_auto("正在加载数据……");
		tools.requestJsonRs(url,para,true,function(json){
			if(json.rtState){
				var returnData = json.rtData;
				var dateModels = returnData.dateModels;
				var html = "";
				//创建网格布局
				// html+="<p class='journalDate'>"dateModels[0].date"</p>";
				for(var i=dateModels.length-1;i>=0;i--){

	                // html+="<p class='journalDate'>"dateModels[0].date"</p>";
					html+="<div class='journal_blcok'>";
					html+="<div class='journalLeft'>";
					html+="<p class='timeLine'>"+dateModels[i].dateDesc+"</p>";
					html+="<p class='hr'></p></div>";

					html+="<div class='journalRight'>";
					html+="<p class='title'>";
					html+="<span class=''>"+"<%=person.getUserName()%>"+"</span>";

					html+="<span href='.journalContent' class='addNew' onclick='addDiary(\""+dateModels[i].date+"\")'>新建</span></p>";
					html+="<p class='journalTime'>"+dateModels[i].dateDesc+"&nbsp;&nbsp;"+dateModels[i].week+"</p>";
					html+="<div class='journalTitle'>";
					html+="<ul id='d"+ dateModels[i].date +"' class='j_content_list'>";

					html+="</ul>";
					html+="<div style='display:none;' class='journalContent'>";
					html+="<iframe src='' frameborder='0'></iframe>";
					html+="</div>";
					html+="</div>";
					html+="</div>";
					html+="</div>";


					/* html+="<table style='width:100%'>";
					html+="<tr>";
					html+="<td class='weekTd'>";
					html+="<div class='weekItem'>";
					html+="<div class='d1'>"+dateModels[i].week+"</div>";
					html+="<div class='d2'>"+dateModels[i].dateDesc+"</div>";
					html+="</div>";
					html+="<td class='weekTdRight'>";
					html+="<div class='info'>";
					html+="<img style='cursor:pointer' title='新建' src='/common/images/other/plus.png' class='pull-right' onclick='addDiary(\""+dateModels[i].date+"\")' />";
					html+="<div style='clear:both'></div>";
					html+="</div>";
					html+="<div id='d"+dateModels[i].date+"'>";
					html+="</div>";
					html+="</td>";
					html+="</tr>";
					html+="</table>";
					html+="<hr/>"; */
				}
				dataModels = json.rtData.dataModels;
				//渲染数据
				$("#container").html(html);
				for(var n=0;n<dataModels.length;n++){
					renderData(dataModels[n],true);
					// console.log(dataModels[n]);
				};

				$(".j_content_list").each(function(i,obj){
					if($(obj).html()==""){
						$(obj).append("<p style='padding-left:20px;font-size:14px;'>无日志内容！</p>");
					}
				});



				 // dataModels = json.rtData.dataModels;
				//渲染数据
				// for(var i=0;i<dataModels.length;i++){
				// 	renderData(dataModels[i],true);
				// }

				//$.jBox.closeTip();
			}
		});
	}
}

/**
 * 渲染数据
 */
function renderData(dataModel,globalRefresh){
	// console.log(dataModel.sid);
	var dom="";
	dom+="<li id='diary"+dataModel.sid+"'><span style='font-weight:bold;display:inline-block;'>"+dataModel.title+"</span>"+"&nbsp;&nbsp;"+dataModel.createTimeDesc;
	dom+="<span class='j_opers'>";
	dom+="<a href='javascript:void(0)' onclick='modifyDiary("+dataModel.sid+")'>编辑</a>";
	dom+="<a href='javascript:void(0)' onclick='delDiary("+dataModel.sid+");'>删除</a>";
	dom+="<a href='javascript:void(0)' onclick='reply("+dataModel.sid+",\""+dataModel.title+"\",\""+dataModel.writeTimeDesc+"\")'>回复</a>";
	dom+="</span>";
	dom+="<div class='j_l_content'>";
	dom+=dataModel.content;
	dom+="</div>";
	dom+="<div class='attach' id='atta"+dataModel.sid+"'></div>";
	dom+="<div id='reply"+dataModel.sid + "' class='reply clearfix'>";
	dom+="</div>";
	dom+="</li>";

	if(!globalRefresh){//局部加载
		var firstDiv = $("#d"+dataModel.writeTimeDesc).find("li:first");
		if(firstDiv && firstDiv.length==0){
			$("#d"+dataModel.writeTimeDesc).append(dom);
			hrAutoHeight();
		}else{
			firstDiv.prepend(dom);
		}
	}else{//全局加载
		$("#d"+dataModel.writeTimeDesc).append(dom);
		hrAutoHeight();
	};


	var attaches = dataModel.attacheModels;
	for(var j=0;j<attaches.length;j++){
		var fileItem = tools.getAttachElement(attaches[j],{});
		$("#atta"+dataModel.sid).append(fileItem);
		hrAutoHeight();
	}

	var replies = dataModel.replyModels;
	renderReply(dataModel.sid,replies,dataModel.writeTimeDesc);
	if($("#reply"+dataModel.sid).html==""){
		$("#reply"+dataModel.sid).hide();
	};

	/* var doms = "";
	doms+="<div id='diary"+dataModel.sid+"'>";
	doms+="<div class='title'>";
	doms+="<div style='float:left;'><span style='color:#007fcb'>"+dataModel.createUserName+"</span>&nbsp;&nbsp;"+dataModel.createTimeDesc+"&nbsp;&nbsp;["+dataModel.title+"]</div>";
	if(!getDiarySetting(dataModel.writeTimeDesc+" 00:00:00")){
		doms+="<div style='float:right;'><a href='javascript:void(0)' onclick='reply("+dataModel.sid+",\""+dataModel.title+"\",\""+dataModel.writeTimeDesc+"\")'>回复</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='modifyDiary("+dataModel.sid+")'>编辑</a>&nbsp;&nbsp;<a href='javascript:void(0)' onclick='delDiary("+dataModel.sid+")'>删除</a></div>";
	}
	doms+="<div style='clear:both'></div>";
	doms+="</div>";
	doms+="<div class='content'>";
	doms+=dataModel.content;
	doms+="</div>";
	doms+="<div class='attach' id='atta"+dataModel.sid+"'>";

	doms+="</div>";
	var hidden = true;
	if(dataModel.replies && dataModel.replies.length!=0){
		hidden = false;
	}
	doms+="<div class='reply' id='reply"+dataModel.sid+"' style='"+(hidden?"display:none":"")+"'>";

	doms+="</div>";

	doms+="</div>";

	if(!globalRefresh){//局部加载
		var firstDiv = $("#d"+dataModel.writeTimeDesc).find("div:first");
		if(firstDiv && firstDiv.length==0){
			$("#d"+dataModel.writeTimeDesc).append(doms);
		}else{
			firstDiv.prepend(doms);
		}
	}else{//全局加载
		$("#d"+dataModel.writeTimeDesc).append(doms);
	}



	var attaches = dataModel.attacheModels;
	for(var j=0;j<attaches.length;j++){
		var fileItem = tools.getAttachElement(attaches[j],{});
		$("#atta"+dataModel.sid).append(fileItem);
	}

	var replies = dataModel.replyModels;
	renderReply(dataModel.sid,replies,dataModel.writeTimeDesc); */
}
function hrAutoHeight(){
	for(var i=0,l=$(".journalRight").length;i<l;i++){
		var h = $($(".journalRight")[i]).height();
		$($(".journalRight")[i]).siblings('.journalLeft').find(".hr").height(h-6);
	}
};
function renderReply(diaryId,replies,writeTimeDesc){
	var rep="";
	for(var j=0;j<replies.length;j++){
		rep+="<ul>";
		rep+="<li class='clearfix'>";
		rep+="<span class='fl'>"+replies[j].replyUserName+"</span>";
		rep+="<span class='fl'>"+replies[j].createTimeDesc+"</span>";
		if(replies[j].replyUserId==userId){
		rep+="<span class='fr' style=''><a href='javascript:void(0)' onclick=\"delReply("+replies[j].sid+","+diaryId+",'"+writeTimeDesc+"')\">删除</a></span>";
		}
		rep+="</li>";
		rep+="<li>";
		rep+=replies[j].content;
		rep+="</li>";
		rep+="</ul>";
	};
	$("#reply"+diaryId).append(rep);
	var ul =  $("#reply"+diaryId).find("ul");
	ul.hide();
	ul.eq(0).show();
	if(ul.length>1){
		$("#reply"+diaryId).append('<div class="readMore">显示更多</div>');
		$("#reply"+diaryId).find(".readMore").on("click",function(){
			$("#reply"+diaryId).find("ul").show();
			$(this).remove();
			hrAutoHeight();
		});
	}else{
		$("#reply"+diaryId).find(".readMore").remove();
	}
	hrAutoHeight();
	if($("#reply"+diaryId).html()==""){
		$("#reply"+diaryId).css("border","none");
	}else{
		$("#reply"+diaryId).css("border","1px solid #ebdebc");
	}
	/* $("#reply"+diaryId).hide();
	for(var j=0;j<replies.length;j++){
		var reply = replies[j];
		$("#reply"+diaryId).show();
		var dom = "<div>";
		dom+="<div class='replyTitle'>";
		dom+="<div class='pull-left'><span style='color:#007fcb'>"+reply.replyUserName+"</span>&nbsp;&nbsp;"+reply.createTimeDesc+"</div>";
		if(reply.replyUserId==userId && !getDiarySetting(writeTimeDesc+" 00:00:00")){
			dom+="<div class='pull-right'><a href='javascript:void(0)' onclick=\"delReply("+reply.sid+","+diaryId+",'"+writeTimeDesc+"')\">删除</a></div>";
		}
		dom+="<div style='clear:both'></div>";
		dom+="</div>";
		dom+="<div class='replyContent'>"+reply.content+"</div>";
		dom+="</div>";
		$("#reply"+diaryId).append(dom);
	} */
}

function delReply(replyId,diaryId,writeTimeDesc){
	$.MsgBox.Confirm("提示","确定要删除吗？",function(){
		var url = contextPath+"/diaryController/deleteReply.action";
		var json = tools.requestJsonRs(url,{replyId:replyId});
		if(json.rtState){
			json = tools.requestJsonRs(contextPath+"/diaryController/getReplies.action",{diaryId:diaryId});
			$("#reply"+diaryId).html("");
			renderReply(diaryId,json.rtData,writeTimeDesc);
		}
	});


}

function reply(diaryId,diaryTitle,writeTimeDesc){
	var url = contextPath+"/system/core/base/diary/reply.jsp?diaryId="+diaryId;
	bsWindow(url,"回复日志&nbsp;&nbsp;["+diaryTitle+"]",{width:"600",height:"240",
		buttons:[{name:"确定",classStyle:"btn-alert-blue"},
              	 {name:"关闭",classStyle:"btn-alert-gray"}
	            ],submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
			var flag = cw.commit();
			if(flag){
				var json = tools.requestJsonRs(contextPath+"/diaryController/getReplies.action",{diaryId:diaryId});
				$("#reply"+diaryId).html("");
				renderReply(diaryId,json.rtData,writeTimeDesc);
				$("#reply"+diaryId).find("ul").show();
				$("#reply"+diaryId).find(".readMore").remove();
				hrAutoHeight();
				//top.BSWINDOW.modal("hide");
				return true;
			}else{
				return false;
			}
			
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function checkForm(){
	var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}


function getDiarySetting(addTime){
	var url = contextPath+"/diaryController/isLocked.action?addTime="+addTime;
	var json = tools.requestJsonRs(url);
	var flag=false;
	if(json.rtState){
		flag=json.rtData.flag;
	}else{
		$.MsgBox.Alert_auto(json.rtMsg);
	}
	return flag;
}
</script>

</head>
<body onload="doInit()" style="font-size:12px;background:#f5f6f7">
	<table id="datagrid" fit="true"></table>
<div style="padding:10px;min-width:800px;background-color:#f5f6f7;">
	<form id="form1" name="form1" method="post">
	<table style="width:700px;font-size:12px;background-color:transparent;margin-left:62px;" align="center" class="TableBlock">
		<tr style='border:none;'>
			<td class="TableData">
				标题：
			</td>
			<td style="width: 150px;" class="TableData"><input id="title" name="title" type="text" class="BigInput"  style="width:120px;height:25px;font-family: MicroSoft YaHei;font-size: 12px;"/></td>
			<td class="TableData ">类型：</td>
			<td style="width: 110px;" class="TableData"><select id="type" name="type" class="BigSelect" style="height:25px;font-family: MicroSoft YaHei;font-size: 12px;">
					<option value="0">全部</option>
					<option value="2">工作日志</option>
					<option value="1">个人日志</option>
				</select>
			</td>
			<td  class="TableData " style="text-align:left">
				日期范围：
				</td>
			<td class="TableData">
			从&nbsp;
				<input type="text" id='startTime' name='startTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})" class="Wdate BigInput" style="width:100px;height:25px;"/>&nbsp;到
				<input type="text" id='endTime' name='endTime' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})" class="Wdate BigInput"  style="width:100px;height:25px;"/>
				<input type='button' onClick='doSearch()' style="border-radius: 5px;margin-left:10px;width:45px;height:25px;border:1px solid #f2f2f2;outline:none;color:#fff;background-color:#379ff7;" value="查询"/>
			</td>
		</tr>
	</table>
	</form>
	<div id="container" style="margin:0 auto;width:95%"></div>
</div>
</body>
</html>