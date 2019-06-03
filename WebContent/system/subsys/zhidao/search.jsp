<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
   //检索的问题
   String keyWord=TeeStringUtil.getString(request.getParameter("keyWord"));
   //分类主键
   int catId=TeeStringUtil.getInteger(request.getParameter("catId"),0);
   String catName=TeeStringUtil.getString(request.getParameter("catName"));
   
   int status=TeeStringUtil.getInteger(request.getParameter("status"),1);
   
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知道</title>
<%@ include file="/header/header2.0.jsp" %>
<script type="text/javascript" src="/system/lucene/js/jqPaginator.js"></script>
<link rel="stylesheet" type="text/css" href="/system/lucene/js/bootstrap.min.css">

<style>
body{
	font-family:微软雅黑;
}
ul{
	list-style:none;
	margin:0px;
	padding:0px;
}
li{
	
}
.selected{
	font-weight:bold;
	color:#379ff6;
	border-bottom:2px solod #379ff6;
}
.item01{
	width:150px;
	padding:10px;
	float:left;
}
.item01 p{
	font-size:12px;
	cursor:pointer;
}
.childItem{
	margin:0px;
	margin-right:5px;
	padding:0px;
}
.result{
	margin-bottom:40px;
}
.result .title{
	font-size:14px;
	color:#0440de;
	font-weight:normal;
}
.result .content{
	margin-top:5px;
	font-size:12px;
	color:#505050;
	font-weight:normal;
	line-height:18px;
}
.hottable{
table-layout:fixed;
}
.hottable td{
	height:20px;
	color:#043edd;
}
.hot{
	color:#fe6665;
	font-weight:bold;
}
</style>
<script>
var keyWord="<%=keyWord %>";
var catId=<%=catId %>;
var catName="<%=catName %>";
var status=<%=status %>;//  1=已解决   0=待解决
$(document).ready(function(){
	
	$("#keyWord").val(keyWord);
	renderCat();
	
	if(catId!=0){//传来的catId
		$("#catId").val(catId);
		$("#catButton").html(catName);
	}
	
	
	if(status==1){
		$("#djj").removeClass("selected");
		$("#yjj").addClass("selected");
	}else{
		$("#yjj").removeClass("selected");
		$("#djj").addClass("selected");
	}
	//获取相关热搜
	getRelations();
	
	//渲染结果
	renderResult(1);
	
	$("#yjj").bind("click",function(){
		$("#djj").removeClass("selected");
		$("#yjj").addClass("selected");
		status=1;
		renderResult(1);
	});
	
	$("#djj").bind("click",function(){
		$("#yjj").removeClass("selected");
		$("#djj").addClass("selected");
		status=0;
		renderResult(1);
	});
	
});

function  renderResult(page){
	$("#resultDiv").html("");
	
	var param={};
	param["keyWord"]=$("#keyWord").val();
	param["status"]=status;
	param["catId"]=$("#catId").val();
	param["page"]=page;
	param["pageSize"]=20;
	var url=contextPath+"/zhiDaoQuestionController/getSearchResult.action";
	var json=tools.requestJsonRs(url,param);
	var total=json.total;
	var rows=json.rows;
	$("#totalMess").html("共"+total+"条结果");
	if(total!=0){
		$("#paginationDiv").show();
		var html=[];
		//渲染数据
		for(var i=0;i<rows.length;i++){
			html.push("<div class=\"result\">"+
					"<p style=\"cursor:pointer;\" class=\"title\" onclick=\"detail("+rows[i].sid+")\">"+rows[i].title+"<span style=\"color:green\">【"+rows[i].catName+"】</span></p>"+
					"<p class=\"content\">"+rows[i].description+"</p>"+
				    "</div>");
			
		}
		$("#resultDiv").append(html.join(""));
		
		var totalPage=Math.ceil(total/20);
		$.jqPaginator('#pagination1', {
		    totalPages:totalPage,
		    visiblePages: 5,
		    currentPage: 1,
		    onPageChange: function (num,type) {
		    	renderResult(num);
		    }
		});
		
	}else{
		$("#paginationDiv").hide();
		messageMsg("暂无匹配的结果！","resultDiv","info");
	}
	
}

//问题详情
function detail(sid){
	var url=contextPath+"/system/subsys/zhidao/detail.jsp?sid="+sid;
	openFullWindow(url);
}

//渲染分类
function renderCat(){
	var url=contextPath+"/zhiDaoCatController/getAllCat1.action";
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			
			for(var i=0;i<data.length;i++){
				var parent=data[i].parent;
				var children=data[i].children;
				
				html.push("<div class=\"item01\">");
				html.push("<p onclick=\"changeCat("+parent.sid+",'"+parent.catName+"');\" style=\"font-weight:bold;font-size:14px\">"+parent.catName+"</p>");
			    if(children!=null&&children.length>0){
			    	for(var j=0;j<children.length;j++){
			    	    html.push("<p onclick=\"changeCat("+children[j].sid+",'"+children[j].catName+"')\" class=\"childItem\" style='float:left;'>"+children[j].catName+"</p>");
			    	}
			    }
			    html.push("</div>");
			}
		}
		$("#catDiv").append(html.join(""));
	}
}

//选择所属分类
function changeCat(catId,catName){
	$("#catButton").html(catName);
	$("#catId").val(catId);
	$("#catDiv").hide();
	
	renderResult(1);
}

function search(){
	var keyWord=$("#keyWord").val();
	var catId=$("#catId").val();
	var catName=$("#catButton").html();

	window.location.href="search.jsp?keyWord="+encodeURI(keyWord)+"&catId="+catId+"&catName="+catName+"&status="+status;
	
}


//获取相关问题  前10
function getRelations(){
	var url=contextPath+"/zhiDaoQuestionController/getRelationsByTitle.action";
	var json=tools.requestJsonRs(url,{title:keyWord,page:1,pageSize:10});
	if(json.rtState){
		var data=json.rtData;
		var html=[];
		if(data!=null&&data.length>0){
			for(var i=0;i<data.length;i++){
				html.push("<tr style=\"cursor:pointer;\" onclick=\"detail("+data[i].sid+")\">");
				if(i==0||i==1||i==2){
					html.push("<td class=\"hot\" width='15px'>"+(i+1)+".</td>");
				}else{
					html.push("<td  width='15px'>"+(i+1)+".</td>");
				}
				
				html.push("<td style='overflow: hidden; text-overflow: ellipsis; white-space: nowrap;' nowrap>"+data[i].title+"</td>");
				if(i==0||i==1||i==2){
					html.push("<td width='15px'><img src=\"img/icon4.png\" /></td>");
				}else{
					html.push("<td width='15px'></td>");
				}
				
				html.push("<td width='20px' style='text-align:right'>"+data[i].clickCount+"</td>");
				html.push("</tr>");
			}
		}
		$("#relations").append(html.join(""));
	}
}
</script>
</head>

<body style="padding:10px">
	<div style="border-bottom:1px solid #dadada;padding:4px">
		<div style="float:left">
			<img src="img/icon2.png" style="width:35px;cursor:pointer"  onclick="window.location = 'index.jsp';"/>
		</div>
		<div style="font-family:微软雅黑;float:left;margin-left:7px">
			<p style="font-size:15px;margin:0px;font-weight:bold;cursor:pointer" onclick="window.location = 'index.jsp';">知道</p>
			<ul>
				<li style="float:left;cursor: pointer;" class="item1 selected" id="yjj">已解决</li>
				<li style="float:left;margin-left:10px;cursor: pointer;" class="item1" id="djj">待解决</li>
			</ul>
		</div>
		<div style="float:right;margin-top:10px;">
			<input  id="keyWord" name="keyWord" style="width:250px;border:1px solid #b8b8b8;height:24px;margin:0px;padding:0px;border-right:0px;" /><button style="border:1px solid #3388ff;background:#3388ff;padding:5px;width:80px;font-size:12px;color:#fff;font-weight:bold" onclick="search()">检&nbsp;&nbsp;索</button>
			<button style="border:1px solid #317df4;background:white;padding:5px;width:80px;font-size:12px;color:#317df4;font-weight:bold" onclick="window.location.href='addQuestion.jsp'">我要提问</button>
		</div>
		<div style="clear:both"></div>
	</div>
	<div style="position:absolute;top:60px;left:10px;right:290px;padding:5px;;padding-right:50px;border-right:1px solid #dadada">
		<div>
			<span style="color:#999999;margin-right:5px;;font-size:12px" id="totalMess" name="totalMess">共0条结果</span>
			<button id="catButton" onclick="$('#catDiv').toggle();" style="width:auto;overflow:visible;height:25px;padding-right:25px;text-align:left;border:1px solid #dadada;padding-left:5px;background:none;background-image:url('img/icon3.png');background-position:right;background-repeat:no-repeat;font-size:12px" nowrap>全部分类</button>
			<input type="hidden" id="catId" name="catId" />
			<div id="catDiv" style="display:none;max-width:600px;position:absolute;left:85px;padding:10px;background:#f0f0f0;z-index:10000;border:1px solid #adadad;">
				<div class="item01">
					<p onclick="changeCat(0,'全部分类');" style="font-weight:bold;font-size:14px">全部分类</p>
				</div>
			</div>
		</div>
		<div style="margin-top:20px;">
			<div id="resultDiv" name="resultDiv">
				
			</div>
			<div style="text-align:right" id="paginationDiv">
	      	 	<ul class="pagination" id="pagination1"></ul>
	      	 </div>
		</div>
	</div>
	<div style="position:absolute;width:270px;top:60px;right:10px;padding:10px;">
		<div style="font-size:12px;font-weight:bold;margin-bottom:10px;"><img src="img/icon5.png" style="width:15px;float:left"/>&nbsp;&nbsp;相关热搜</div>
		<table style="width:100%" class="hottable" id="relations">
			
		</table>
	</div>
</body>

</html>