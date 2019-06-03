<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header.jsp" %>
<%@ include file="/header/easyui.jsp" %>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/KinSlideshow/jquery.KinSlideshow-1.2.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/seniorreport.js"></script>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script src="<%=contextPath %>/common/highcharts/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/jquery.highchartsTable.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/exporting.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/data.src.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/highcharts-more.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/highcharts/js/modules/funnel.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/lazyloader.js"></script>

<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/6/css/datepicker.css">
<script type="text/javascript" src="<%=contextPath%>/system/frame/6/js/jquery.datePicker-min.js"></script>

<title>主界面</title>

<link href="css/index.css" type="text/css" rel="stylesheet" />
<style>
body{
margin:10px;
}
.column {
  float: left;
  padding-bottom: 100px;
}

.portlet {
  margin: 0 1em 1em 0;
  padding: 0.3em;
}

.portlet-header {
  padding: 0.2em 0.3em;
  margin-bottom: 0.5em;
  position: relative;
}

.portlet-toggle {
  position: absolute;
  top: 50%;
  right: 0;
  margin-top: -8px;
}

.portlet-content {
  padding: 0.4em;
}

.portlet-placeholder {
  border: 1px dotted black;
  margin: 0 1em 1em 0;
  height: 50px;
}
#datepicker{background:#fff;border:1px dashed #cdcdcd}
/*主体中心区右侧域*/
/* #main_conetnt{ width:100%;  height:800px;  overflow-y:auto;} */
.desktop .modular{ border:1px #cdcdcd solid; margin:10px;}
.desktop .modular .modular_hd{ height:36px; line-height:36px; overflow:hidden; background:#f9f9f9;}
.desktop .modular .modular_hd .toggle{ float:left; width:37px; height:36px; background:url(images/desktop_ico.png) no-repeat left top; border-right:1px #cdcdcd solid; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .toggle.on{ background:url(images/desktop_ico.png) no-repeat left -38px;}
.desktop .modular .modular_hd .delete{ float:right; width:38px; height:36px; background:url(images/desktop_ico.png) no-repeat left -107px; border-left:1px #cdcdcd solid; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .renovate{ float:right;  width:38px; height:36px; background:url(images/desktop_ico.png) no-repeat left -71px; text-indent:-9999px; cursor:pointer;}
.desktop .modular .modular_hd .title{ margin:0 100px 0 50px; font-size:16px; color:#455156; font-family:"Microsoft YaHei";}


.center1{ border-top:1px #cdcdcd solid; overflow:hidden; padding:12px 15px 8px 15px;} 
.center1 .time{ color:#959595; font-size:13px;}
.center1 .more{ height:24px; margin-top:5px; text-indent:-9999px;}
.center1 .more a{ float:right; display:block; width:30px; height:24px; background:url(images/desktop_ico.png) no-repeat -2px -148px; text-indent:-9999px; }
.center1 .news_headlines{ overflow:hidden; margin:0px;}
.center1 .news_headlines dt{ float:left; width:222px; height:145px; overflow:hidden;}
.center1 .news_headlines dd{ margin-left:238px;}
.center1 .news_headlines dd h6{ line-height:30px; background:url(images/desktop_ico.png) no-repeat -15px -230px; padding-left:12px; margin:0px;}
.center1 .news_headlines dd h6 a{ font-size:14px; color:#313131;}
.center1 .news_headlines dd p{ line-height:22px; max-height:88px; overflow:hidden; font-size:12px; color:#898989; padding-left:12px; font-family:"Microsoft YaHei";}
.center1 .news_headlines dd span.time{ padding-left:12px;}

.center1 .news_list{ overflow:hidden; margin-top:5px; margin-bottom:0px;}
.center1 .news_list li{ line-height:30px; border-bottom:1px #c3c3c3 dashed; background:url(images/desktop_ico.png) no-repeat -15px -226px; text-indent:15px;}
.center1 .news_list li a{ font-size:12px; color:#313131;}
.center1 .news_list li span.time{ float:right;}
.center1 .workflow_list{ overflow:hidden; margin-top:5px; margin-bottom:0px;}
.center1 .workflow_list li{ line-height:38px; border-bottom:1px #c3c3c3 dashed;}
.center1 .workflow_list li a{ font-size:14px; color:#313131;}
.center1 .workflow_list li a .red{ color:#F00;}
.center1 .workflow_list li a .yellow{ color:#f39800;}
.center1 .workflow_list li a .grey{ color:#313131;}
.center1 .workflow_list li span.time{ float:right;}


.center1 .schedule{ overflow:hidden; height:269px;}
.center1 .schedule .schedule_content{ margin-left:285px; margin-top:10px;}
.center1 .schedule .schedule_content h3{ font-size:14px;font-family:"Microsoft YaHei"; font-weight:bold;}
.center1 .schedule .schedule_content h4{ font-size:50px;font-family:"Microsoft YaHei"; text-indent:20px;}
.center1 .schedule .schedule_content li{ border-bottom:1px #CCC dashed; line-height:22px; margin:10px 0 0 0; font-size:12px;}
.center1 .schedule .schedule_content li span.schedule_time{ font-weight:bold; margin-right:10px;}
.center1 .schedule .schedule_content li span.red{ color:#F00;}
.center1 .schedule .schedule_content li span.green{ color:#009e96;}
.list_div_content .clearfix{border-bottom:1px dashed #c3c3c3;padding-bottom:5px;background:url(images/desktop_ico.png) no-repeat -15px -230px; padding-left:12px;}
</style>


<script type="text/javascript">
var cols = 1;
function doInit(){
	
var desktop ="";
var json= tools.requestJsonRs(contextPath+"/teePortalTemplateUserDataController/getTemplateUserData.action");
desktop=json.rtData.data; 
cols = json.rtData.cols;
try{
	desktop = eval("("+desktop+")");
}catch(e){
	return;
}


switch(cols){
case 1:
	$("body").append("<div id=\"column0\" class=\"column\" style='width:33%'></div>");
	$("body").append("<div id=\"column1\" class=\"column\" style='width:66%'></div>");
	break;
case 2:
	$("body").append("<div id=\"column0\" class=\"column\" style='width:66%'></div>");
	$("body").append("<div id=\"column1\" class=\"column\" style='width:33%'></div>");
	break;
case 3:
	$("body").append("<div id=\"column0\" class=\"column\" style='width:50%'></div>");
	$("body").append("<div id=\"column1\" class=\"column\" style='width:50%'></div>");
	break;
case 4:
	$("body").append("<div id=\"column0\" style='width:100%'></div>");
	$("body").append("<div style=\"clear:both\"></div>");
	$("body").append("<div id=\"column1\" class=\"column\" style='width:50%'></div>");
	$("body").append("<div id=\"column2\" class=\"column\" style='width:50%'></div>");
	break;
case 5:
	$("body").append("<div id=\"column0\" style='width:100%'></div>");
	$("body").append("<div style=\"clear:both\"></div>");
	$("body").append("<div id=\"column1\" class=\"column\" style='width:33%'></div>");
	$("body").append("<div id=\"column2\" class=\"column\" style='width:66%'></div>");
	break;
case 6:
	$("body").append("<div id=\"column0\" style='width:100%'></div>");
	$("body").append("<div style=\"clear:both\"></div>");
	$("body").append("<div id=\"column1\" class=\"column\" style='width:66%'></div>");
	$("body").append("<div id=\"column2\" class=\"column\" style='width:33%'></div>");
	break;
default:
	break;
}

var json = tools.requestJsonRs(contextPath+"/portlet/getUseablePortlet.action",{});
var useablePortlet = json.rtData;
var sp = useablePortlet.split(",")

//插入值
for(var i=0;i<desktop.length;i++){
	for(var j=0;j<desktop[i].length;j++){
		var exist = false;
		for(var k=0;k<sp.length;k++){
			if((sp[k])==(desktop[i][j].id+"")){
				exist = true;
				break;
			}
		}
		if(exist){
			renderPortlet(i,desktop[i][j]);
		}
	}
}

//
$( ".column" ).sortable({
  connectWith: ".column",
  handle: ".portlet-header",
  cancel: ".portlet-toggle",
  placeholder: "portlet-placeholder ui-corner-all",
  beforeStop: function( event, ui ) {
	  var desktop = [];
	  $("body").children(".column").each(function(i,obj){
		  desktop.push([]);
		  $(obj).children(".portlet").each(function(j,o){
			  desktop[i].push({id:o.getAttribute("pid"),rows:15});
		  });
	  });
	  desktop = tools.jsonArray2String(desktop);
	  
	  //var json = tools.requestJsonRs(contextPath+"/portlet/updatePersonDesktop.action",{desktop:desktop},true);
	  var json = tools.requestJsonRs(contextPath+"/teePortalTemplateUserDataController/updateTemplateUserData.action",{desktop:desktop},true);
  }
});
// $( ".portlet" )
//   .addClass( "ui-widget ui-widget-content ui-helper-clearfix ui-corner-all" )
//   .find( ".portlet-header" )
//     .addClass( "ui-widget-header ui-corner-all" )
//     .prepend( "<span class='ui-icon ui-icon-minusthick portlet-toggle'></span>");
$( ".portlet-toggle" ).click(function() {
  var icon = $( this );
  icon.toggleClass( "ui-icon-minusthick ui-icon-plusthick" );
  icon.closest( ".portlet" ).find( ".portlet-content" ).toggle();
});
}

/**
 * <table class="portlet1">
	<thead>
	<tr>
		<td class="top_left1"></td>
		<td class="top1"></td>
		<td class="top_right1"></td>
	</tr>
</thead>
<tbody>
	<tr>
		<td class="left1"></td>
		<td class="center1">asdsad</td>
		<td class="right1"></td>
	</tr>
</tbody>
<tfoot>
	<tr>
		<td class="bottom_left"></td>
		<td class="bottom"></td>
		<td class="bottom_right"></td>
	</tr>
</tfoot>
</table>
 */
function renderPortlet(col,item){
	var render = [];
	render.push("<table class='portlet1 portlet' pid="+item.id+">");
		render.push("<thead>");
			render.push("<tr class='portlet-header'>");
				render.push("<td class='top_left1'></td>");
				render.push("<td class='top1'>");
			 	render.push("<h3 class='fl clearfix'>");
			 	render.push("<img class='fl' onerror=\"this.style.display='none'\" style='height:20px;margin-top:4px;' id='pIcon"+item.id+"' alt='' />");
			 	render.push("</h3>");
			 	render.push("<img title='刷新' src='<%=request.getContextPath() %>/common/images/other/icon_refresh.png'  class='fr' style='height:10px;margin-top:6px;margin-left:10px;cursor:pointer' onclick=\"refreshPortlet('"+item.id+"')\"/><a href='javascript:void(0);' class='fr' id='pTitle"+item.id+"' style='font-weight:bold;margin-top:4px;'></a>");
				render.push("</td>");
				render.push("<td class='top_right1'></td>");
			render.push("</tr>");
		render.push("</thead>");
		render.push("<tbody>");
			render.push("<tr>");
				render.push("<td class='left1'></td>");
				render.push("<td class='center1' id='pContent"+item.id+"'>");
				render.push("<center><img src='"+contextPath+"/common/styles/imgs/loading01.gif' /></center>");
				render.push("</td>");
				render.push("<td class='right1'></td>");
			render.push("</tr>");
		render.push("</tbody>");
		render.push("<tfoot>");
			render.push("<tr>");
				render.push("<td class='bottom_left'></td>");
				render.push("<td class='bottom'>");
				render.push("<div class='more' id='pMore"+item.id+"'></div>");
				render.push("</td>");
				render.push("<td class='bottom_right'></td>");
			render.push("</tr>");
		render.push("</tfoot>");
	render.push("</table>");
	
// 	render.push("<div class='list_div_box portlet' pid="+item.id+">");
// 	render.push("<div class='list_div_left'></div>");
// 	render.push("<div class='list_div_center'>");
// 	render.push("<div class='list_div_title clearfix portlet-header'>");
// 	render.push("<h3 class='fl clearfix'>");
// 	render.push("<img class='fl' id='pIcon"+item.id+"' alt='' />");
// 	render.push("</h3>");
// 	render.push("<a href='javascript:void(0);' class='fr' id='pTitle"+item.id+"' style='font-weight:bold'></a>");
// 	render.push("</div>");
// 	render.push("<span id='pContent"+item.id+"'>");
// 	render.push("</span>");
// 	render.push("<div class='more' id='pMore"+item.id+"'></div>");
// 	render.push("</div>");
// 	render.push("<div class='list_div_right'></div>");
// 	render.push("</div>");
	
	$("#column"+col).append(render.join(""));
	refreshPortlet(item.id);
	
}

function refreshPortlet(itemId){
	$("#pContent"+itemId).html("<center>正在加载，请稍候</center>");
	tools.requestJsonRs(contextPath+"/portlet/renderPortlet.action",{sid:itemId},true,function(json){
		if(json!=null){
			$("#pContent"+itemId).html(json.content);
			$("#pTitle"+itemId).html(json.title);
			if(json.moreUrl){
				$("#pMore"+itemId).html("<a href=\"javascript:void();\" onclick=\"openFullWindow('"+json.moreUrl+"','查看全部')\">更多</a>");
			}
			$("#pIcon"+itemId).attr("src",json.icons);
			
			if(json.autoRefresh!=0){
				if(!window["P_AUTO_REFRESH_"+itemId]){
					window["P_AUTO_REFRESH_"+itemId] = "1";
					//setInterval("refreshPortlet("+itemId+")",json.autoRefresh*1000);
					setTimeout("refreshPortletsTimeout("+itemId+","+json.autoRefresh*1000+")",json.autoRefresh*1000);
				}
			}
		}
	});
}

function refreshPortletsTimeout(itemId,time){
	refreshPortlet(itemId);
	setTimeout("refreshPortletsTimeout("+itemId+","+time+")",time);
}

/**
 *获取邮件信息
 */
function getEmailInfo(optType){
	var url = contextPath + "/mail/selectMailForPortlet.action";
	var type = 1;//状态 0-全部   1未读
	var maxSize  = 5; //
	if(optType == 'email_all_list'){
		type = 0;
	}
	var para =  {type: type , maxSize: maxSize} ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var prcs = jsonRs.rtData;
		var calStr = "";
		if(prcs.length > 0){
			for(var i = 0 ; i<prcs.length ; i++){
				var prc= prcs[i];
				var formName = prc.formName;
				var sendTime = prc.sendTime;
				var sendTimeStr = "";
				if(sendTime){
					sendTimeStr = getFormatDateStr(sendTime,'yyyy-MM-dd');
				}
				calStr = calStr + '<li class="clearfix"><a class="fl" href="javascript:void();" onclick=\"toEmailDetail(' + prc.sid + ', 2)\">' + prc.subject +  ' (' + formName+ ') ' + '</a><i class="fr">' + sendTimeStr + '</i></li>';
			}
		}else{
			calStr = "<div class='warning-null'>暂无相关邮件</div>";
		}
		$("#" +optType).html(calStr);
	}else{
		
	}
}
/**
 * 查看日程信息
 * @param state- 状态  0-未读  1- 已读
 */
function toEmailDetail(id , state )
{
	var url = contextPath + "/mail/readMailForOther.action?type=0&ifBox=0&mailId="+ id;
	
 	top.bsWindow(url ,"查看邮件详情",{width:"800",height:"320",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}


/**
 *获取新闻信息
 */
function getNewsInfo(optType){
	var url = contextPath + "/teeNewsController/getAllNoReadNews.action";
	var state = 0;//状态  0-未读  1-只读 -1 - 全部
	var count = 5;//count = -1; //全部，所有记录
	if(optType == 'news_all_list' ){
		 state = -1;
	}

	var para =  {state: state , count: count} ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var prcs = jsonRs.rtData;
		var calStr = "";
		if(prcs.length > 0){
			for(var i = 0 ; i<prcs.length ; i++){
				var prc= prcs[i];
				var sendTime = prc.newsTime;
				var sendTimeStr = "";
				//private Date newsTime = null;// 发布时间
			//private String newsTimeStr = "";
				if(sendTime){
					sendTimeStr = getFormatDateStr(sendTime,'yyyy-MM-dd');
				}
				calStr = calStr + '<li class="clearfix"><a class="fl" href="javascript:void();" onclick=\"toNewsDetail(' + prc.sid + ',' + prc.isLooked + ')\">' + prc.subject + '>' + prc.subject + '</a><i class="fr">' + sendTimeStr + '</i></li>';
			}
		}else{
			calStr = "<div class='warning-null'>暂无相关新闻</div>";
		}
		$("#" +optType).html(calStr);
	}else{
		
	}
}
/**
 * 查看新闻信息
 * @param state- 状态  0-未读  1- 已读
 */
function toNewsDetail(id , isLooked )
{
	 var url = contextPath + "/system/core/base/news/person/readNews.jsp?id="+id+"&isLooked=" + isLooked;
	//var url = contextPath + "/system/core/base/notify/person/readNotify.jsp?id=" + id;
	top.bsWindow(url ,"查看新闻详情",{width:"800",height:"330",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}});
}


/**
 *获取公告信息
 */
function getNotifyInfo(optType){
	var url = contextPath + "/teeNotifyController/getNotifyByState.action";
	var type = 0;//状态  0-未读  1-只读 -1 - 全部
	var maxSize  = 5; //
	if(optType == 'notify_all_list'){
		type = -1;
	}
	var para =  {state: type , count: maxSize} ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var prcs = jsonRs.rtData;
		var calStr = "";
		if(prcs.length > 0){
			for(var i = 0 ; i<prcs.length ; i++){
				var prc= prcs[i];
				var formName = prc.formName;
				var sendTime = prc.sendTime;
				var sendTimeStr = "";
				//private Date newsTime = null;// 发布时间
			//private String newsTimeStr = "";
				if(sendTime){
					sendTimeStr = getFormatDateStr(sendTime,'yyyy-MM-dd');
				}
				calStr = calStr + '<li class="clearfix"><a class="fl" href="javascript:void();" onclick=\"toNotifyDetail(' + prc.sid + ',' + prc.isLooked + ')\">' + prc.subject + '>' + prc.subject + '</a><i class="fr">' + sendTimeStr + '</i></li>';
			}
		}else{
			calStr = "<div class='warning-null'>暂无相关公告</div>";
		}
		$("#" +optType).html(calStr);
	}else{
		
	}
}

/**
 * 查看公告信息
 * @param state- 状态  0-未读  1- 已读
 */
function toNotifyDetail(id , state )
{
	var url = contextPath + "/system/core/base/notify/person/readNotify.jsp?id=" + id;
	top.bsWindow(url ,"查看公告通知详情",{width:"800",height:"340",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}});
}

/**
 * 工作流
 */
function hand_workflow_list(){
	var url = "<%=contextPath %>/workflow/getNoHandledWorks.action";
	var para =  {rows:10,page:1};
	var json = tools.requestJsonRs(url,para);
	var rows = json.rows;
	var html = "";
	for(var i=0;i<rows.length;i++){
		html+="<li class=\"clearfix\"  ><a  class='fl' href='javascript:void(0)' style='width:96%;' onclick='forward("+rows[i].runId+","+rows[i].flowId+","+rows[i].frpSid+")'>"+rows[i].runName+"</a></li>";
	//	html = html + '<li class="clearfix"><a class="fl" href="javascript:void();" onclick=\"forward('+rows[i].runId+','+rows[i].flowId+','+rows[i].frpSid+')\">' + prc.subject + '>' + prc.subject + '</a><i class="fr">' + sendTimeStr + '</i></li>';
	}
	if(rows.length==0){
		$("#hand_workflow_list").html("<center>暂无工作待办数据</center>");
		return;
	}
	$("#hand_workflow_list").html(html);
}

function forward(runId,flowId,frpSid){
	var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
	window.openFullWindow(contextPath +'/system/core/workflow/flowrun/prcs/index.jsp?'+para,"流程办理");
}

function doPageHandler(){
	window.location.reload();
}


</script>
</head>

<body style="background:url(images/content_bg.png) left top;" onload="doInit()">
<script>

var email_all_list_click = false;//邮件
var news_all_list_click = false;//新闻
var notify_all_list_click = false;//公告
$(".list_div_center .list_div_tab span").click(function ()
{
	$(this).parent().find("span").removeClass("spanColor");
	$(this).addClass("spanColor");
	
	var index = $(this).index();
	if($(this).attr("item") == "email_all_list" && !email_all_list_click){//邮件
		//email_all_list_click = true;
		//getEmailInfo("email_all_list");
	}else if($(this).attr("item") == "news_all_list" && !news_all_list_click){//新闻
		//news_all_list_click = true;
		//getNewsInfo("news_all_list");
	}else if($(this).attr("item") == "notify_all_list" && !notify_all_list_click){//新闻
		//notify_all_list_click = true;
		//getNotifyInfo("notify_all_list");
	}
	$(this).parent().parent().find(".list_div_content").hide();
	
	$(this).parent().parent().find(".list_div_content").eq(index).show();

})
</script>
</body>
</html>
