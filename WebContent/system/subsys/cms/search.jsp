<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@include file="/header/header.jsp" %>
<%@include file="/header/easyui.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<style>
.pager{
	margin-top:20px;
	width:100%;
	margin:0 atuo;
	line-height:32px;
	font-size:14px;
}

.unSelected{
	min-width:36px;
	height:36px;
	line-height:36px;
	border:1px solid #E0E0E0;
	display:inline-block;
	margin-right:5px;
	padding:0 3px;
	background:#F0F0F0;
	cursor:pointer;
}

.selected{
	min-width:36px;
	height:36px;
	line-height:36px;
	border:1px solid #E0E0E0;
	display:inline-block;
	margin-right:5px;
	padding:0 3px;
	background:#0066FF;
	cursor:pointer;
	color:#FFFFFF;
}
</style>
<script>
var curPage=1;
var pageSize=10;
function doInit(){
	
	
}
/**
 * 搜索内容
 */
function doSearch(){
	var keyword = $("#keyword").val();
	curPage=1;
	if(keyword=="" || keyword==null || keyword=="null"){
		alert("搜索内容不能为空！");
		return;
	}
	var url = contextPath+"/cmsDocument/search.action";
	var json = tools.requestJsonRs(url,{keyword:keyword,curPage:curPage,pageSize:pageSize});
	if(json.rtState){
		var data = json.rtData;
		if(data){
			if(data.pageNums>0){
				$("#searchResult").html(data.docList);
				
				//分页导航信息
				var pageHtml = "<div class='pager'><a href='javascript:void(0)' onclick='renderPage(1)'><span class='unSelected'>第一页</span></a>";
				if(curPage>10){
					var m=0;
					for(var i = curPage-5;i<=data.pageNums;i++){
						if(i==curPage){
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='selected'>"+i+"</span></a>";
						}else{
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='unSelected'>"+i+"</span></a>";
						}
						m++;
						if(m>10){
							pageHtml+="<span class='unSelected'>。。。</span></a>";
							break;
						}
					}
				}else{
					for(var i = 1;i<=data.pageNums;i++){
						if(i==curPage){
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='selected'>"+i+"</span></a>";
						}else{
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='unSelected'>"+i+"</span></a>";
						}
						if(i>10){
							pageHtml+="<span class='unSelected'>。。。</span></a>";
							break;
						}
					}
				}
				
				pageHtml+="<a href='javascript:void(0)' onclick='renderPage("+data.pageNums+")'><span class='unSelected'>最后一页</span></a></div>";
				$("#searchResult").html($("#searchResult").html()+pageHtml);
			}else{
				$("#searchResult").html("<span style='width:100%;text-align:center;display:inline-block;'>没有搜索到相关内容！</span>");
			}
			
		}
		
	}
}

function renderPage(page){
	curPage = page;
	var keyword = $("#keyword").val();
	var url = contextPath+"/cmsDocument/search.action";
	var json = tools.requestJsonRs(url,{keyword:keyword,curPage:curPage,pageSize:pageSize});
	if(json.rtState){
		var data = json.rtData;
		if(data){
			if(data.pageNums>0){
				$("#searchResult").html(data.docList);
				
				//分页导航信息
				var pageHtml = "<div class='pager'><a href='javascript:void(0)' onclick='renderPage(1)'><span class='unSelected'>第一页</span></a>";
				if(curPage>10){
					var m=0;
					for(var i = curPage-5;i<=data.pageNums;i++){
						if(i==curPage){
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='selected'>"+i+"</span></a>";
						}else{
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='unSelected'>"+i+"</span></a>";
						}
						m++;
						if(m>10){
							pageHtml+="<span class='unSelected'>。。。</span></a>";
							break;
						}
					}
				}else{
					for(var i = 1;i<=data.pageNums;i++){
						if(i==curPage){
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='selected'>"+i+"</span></a>";
						}else{
							pageHtml += "<a href='javascript:void(0)' onclick='renderPage("+i+")'><span class='unSelected'>"+i+"</span></a>";
						}
						if(i>10){
							pageHtml+="<span class='unSelected'>。。。</span></a>";
							break;
						}
					}
				}
				pageHtml+="<a href='javascript:void(0)' onclick='renderPage("+data.pageNums+")'><span class='unSelected'>最后一页</span></a></div>";
				$("#searchResult").html($("#searchResult").html()+pageHtml);
			}else{
				$("#searchResult").html("<span style='width:100%;text-align:center;display:inline-block;'>没有搜索到相关内容！</span>");
			}
		}
		
	}
	
}


</script>
</head>
<body onload="doInit();" style="margin:5px 5px;">
<form id="form1">
	<div id="search" style='margin:0 auto; text-align:center;'>
		<input type="text" class="BigInput" id="keyword" name="keyword"><input type="button" value="搜索" onclick="doSearch();"/>
	</div>
	<div id="searchResult" style='margin-top:20px;'>
	
	</div>
</form>
</body>
</html>