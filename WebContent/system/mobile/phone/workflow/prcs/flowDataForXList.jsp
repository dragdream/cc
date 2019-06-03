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
int flowId =TeeStringUtil.getInteger(request.getParameter("flowId"), 0);
String mappingStr = request.getParameter("mappingstr");
int itemId=TeeStringUtil.getInteger(request.getParameter("itemId"),0);
%>

<script type="text/javascript">
var loader;
var flowId = <%=flowId%>;
var mappingStr = "<%=mappingStr%>";
var itemId=<%=itemId %>;
var para = {};
var maps;
var opts;
function doInit(){
	var showFields="";
	//根据mappingStr获取其他显示字段
	var json = tools.requestJsonRs(contextPath+"/flowForm/getMappingFormItemsByFlowId.action",{mappingStr:mappingStr,flowId:flowId});
	maps=json.rtData;
	//获取表单上需要映射的控件
	var mappings=tools.string2JsonObj(mappingStr);
	if(mappings!=null&&mappings.length>0){
		for(var i=0;i<mappings.length;i++){
			showFields+=maps[mappings[i].title1]+",";
		}
	}
	if(showFields!=""&&showFields!=null){
		para["showFields"] = showFields.substring(0, showFields.length-1);
	}else{
		para["showFields"]="";
	}
	
	
	para["flowId"] = flowId;
	para["mappingStr"]=mappingStr;
	opts = [{
		title : '流水号',
		field : 'runId'
	},{
		field : 'runName',
		title : '工作名称',
	}];
	
	//根据mappingStr获取其他显示字段
	var json = tools.requestJsonRs(contextPath+"/flowForm/getMappingFormItemsByFlowType.action",para);
	var mapFormItems=json.rtData;

	if(mapFormItems!=null&&mapFormItems.length>0){
		for(var i=0;i<mapFormItems.length;i++){
			var formItemModel = mapFormItems[i];
			//alert(formItemModel.name);
			opts.push({
				field:formItemModel.name,
				title:formItemModel.title,
			});
		}
	}
	
	
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
	var url=contextPath+"/workQuery/query.action?runNameOper=like2";
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
					
					render.push("<p style='font-size:12px'>"+opts[j].title+"："+item[opts[j].field]+"</p>");
					
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
	var url=contextPath+"/workQuery/query.action?runNameOper=like2";
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
				
					render.push("<p style='font-size:12px'>"+opts[j].title+"："+item[opts[j].field]+"</p>");
					
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
	
	var mappings=tools.string2JsonObj(mappingStr);
	
	for(var k in maps){
		if(selected[maps[k]]){
			if(mappings!=null&&mappings.length>0){
				for(var j=0;j<mappings.length;j++){
					if(mappings[j].title1==k){
						selected[mappings[j].title2]=selected[maps[k]];
					}
				}
			}
			
		}
	}
	parent.addRow(itemId,selected);
	

	parent.layer.closeAll();
}

function search(){
	$("#offCanvasHide").focus();
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
				    <div class="mui-input-row">
			            <label>流水号</label>
			            <input type='text' name="runId" id="runId" placeholder='请输入流水号'/>");
			         </div>
			         <div class="mui-input-row">
			            <label>工作名称</label>
			            <input type='text' name="runName" id="runName" placeholder='请输入工作名称'/>");
			         </div>
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
			<h1 class="mui-title">流程数据选择</h1>
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