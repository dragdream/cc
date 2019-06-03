<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/system/mobile/js/tools.js?v=3"></script>

<%
String valueModel=TeeStringUtil.getString(request.getParameter("valueModel"));
int tdIndex=TeeStringUtil.getInteger(request.getParameter("tdIndex"),0);
int trIndex=TeeStringUtil.getInteger(request.getParameter("trIndex"),0);
int itemId=TeeStringUtil.getInteger(request.getParameter("itemId"),0);
String title=TeeStringUtil.getString(request.getParameter("title"));
%>

<script type="text/javascript">
var loader;
var valueModel = "<%=valueModel%>";
var title = "<%=title%>";
var tdIndex=<%=tdIndex %>;
var trIndex=<%=trIndex %>;
var itemId=<%=itemId %>;
var para = {};

function doInit(){
	
	para["identity"] = valueModel;
	var json = tools.requestJsonRs(contextPath+"/bisView/listBisViewListItem.action",para);
	var metadata = json.rows;
	
	opts = [{
		field:"BISKEY",
		title:"主键",
		width:100,
		checkbox:true
	}];
	
	var html = [];
	var k=1;
	for(var i=0;i<metadata.length;i++){
		var field = metadata[i];
		if(field.isSearch==1){//查询字段
			html.push("<div class=\"mui-input-row\">");
			html.push("<label>"+field.title+"</label>");
			html.push("<input type='text' name='"+field.searchField+"_"+field.fieldType+"_SEARCH' placeholder='请输入"+field.title+"'/>");
			html.push("</div>");
		}
		opts.push({
			field:field.field,
			title:field.title,
			width:field.width
		});
	}
	
	$("#form1").append(html.join(""));

	
	para = tools.formToJson(parent.$("#form")[0],true);
	para["dfid"] = valueModel;
}


var page = 1;
mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		down: {
			callback: pulldownRefresh
		},
		up: {
			contentrefresh: '正在加载...',
			callback: pullupRefresh
		}
	}
});

/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {
	$("#list").html("");
	page = 1;
	var url=contextPath + "/bisView/dflist.action";
	para["rows"] = 20;
	para["page"] = page++;
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:para,
		timeout:10000,
		success:function(json){
			mui('#pullrefresh').pullRefresh().endPulldownToRefresh(false);
			json = eval("("+json+")");
			
			for(var i=0;i<json.rows.length;i++){
				var render = [];
				var item = json.rows[i];
				render.push("<li class=\"mui-table-view-cell mui-media\">");
				render.push("<div class=\"mui-media-body\">");
	 			for(var j=0;j<opts.length;j++){
					if(j!=0){
						render.push("<p style='font-size:12px'>"+opts[j].title+"："+item[opts[j].field]+"</p>");
					}
				}
				render.push("</div>");
				render.push("</li>");
				
				var tmp = $(render.join("")).attr("data",tools.jsonObj2String(item));
				$("#list").append(tmp);
			}
			
			$(".mui-media").each(function(i,obj){
				obj.removeEventListener("tap",selectIt);
				obj.addEventListener("tap",selectIt);
			});
		},
		error:function(){
			
		}
	});
	
}

/**
 * 上拉加载具体业务实现
 */
function pullupRefresh() {
	var url=contextPath + "/bisView/dflist.action";
	para["rows"] = 20;
	para["page"] = page++;
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:para,
		timeout:10000,
		success:function(json){
			mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
			json = eval("("+json+")");
			
			for(var i=0;i<json.rows.length;i++){
				var render = [];
				var item = json.rows[i];
				render.push("<li class=\"mui-table-view-cell mui-media\" >");
				
				render.push("<div class=\"mui-media-body\">");
				for(var j=0;j<opts.length;j++){
					if(j!=0){
						render.push("<p style='font-size:12px'>"+opts[j].title+"："+item[opts[j].field]+"</p>");
					}
				}
				render.push("</div>");
				render.push("</li>");
				var tmp = $(render.join("")).attr("data",tools.jsonObj2String(item));
				$("#list").append(tmp);
			}
			
			
			$(".mui-media").each(function(i,obj){
				obj.removeEventListener("tap",selectIt);
				obj.addEventListener("tap",selectIt);
			});
		},
		error:function(){
			
		}
	});
	
}

mui.ready(function() {
	doInit();
	mui('#pullrefresh').pullRefresh().pulldownLoading();
	
	backBtn.addEventListener("tap",function(){
		parent.layer.closeAll();
	});//返回
	
	document.getElementById('offCanvasHide').addEventListener('tap', function() {
		search();
		mui('#offCanvasWrapper').offCanvas('close');
	});
});


function selectIt(){
	var obj = $(this)[0];
	var selected = obj.getAttribute("data");
	selected = eval("("+selected+")");
	
	//填写上所有表单title
	for(var key in selected){
		var data = selected[key]; //获取数据
		if(key==title){
			parent.$("#XLIST_"+itemId).find("tr[index='"+trIndex+"']").find("td[index='"+tdIndex+"']").find("input:eq(0)").val(data);
		}
	}
	
	parent.layer.closeAll();
}

function search(){
	var tmp = tools.formToJson($("#form1"));
	for(var key in tmp){
		para[key] = tmp[key];
	}
	pulldownRefresh();
}

</script>


</head>
<body>
<!--侧滑菜单容器-->
<div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable">
	<!--菜单部分-->
	<aside id="offCanvasSide" class="mui-off-canvas-right" style="background:white">
		<div id="offCanvasSideScroll" class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<form id="form1" class="mui-input-group">
				</form>
				<p style="margin: 10px 15px;">
					<button id="offCanvasHide" type="button" class="mui-btn mui-btn-danger mui-btn-block" style="padding: 5px 20px;">查询</button>
				</p>
			</div>
		</div>
	</aside>
	<div class="mui-inner-wrap">
		<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" id="backBtn"></span>
			<h1 class="mui-title"><%=title %></h1>
			<a class="mui-icon mui-action-menu mui-icon-bars mui-pull-right" href="#offCanvasSide"></a>
		</header>
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
			<div class="mui-scroll">
				<ul class="mui-table-view" id="list">
				</ul>
			</div>
		</div>
		<!-- off-canvas backdrop -->
		<div class="mui-off-canvas-backdrop"></div>
	</div>
</div>
</body>
</html>