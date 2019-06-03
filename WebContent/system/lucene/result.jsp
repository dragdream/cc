<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tianee.lucene.TeeLuceneSoapService"%>
<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@page import="com.tianee.lucene.entity.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String contextPath = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ contextPath + "/";

	
	int currentPage=Integer.parseInt(request.getParameter("currentPage"));
	String space = request.getParameter("space");
	String term = request.getParameter("term");
	
	SearchModel model=new SearchModel();
	if(("pubnetdisk").equals(space)){
		model.setCurPage(currentPage);
		model.setPageSize(20);
		model.setReturnFieldWordCount(new int[]{300,300});
		model.setDefaultSearchField(new String[]{"title","body"});
		model.setReturnField(new String[]{"title","body","attachSid","fileNetDiskSid"});
		model.setLightedField(new String[]{"body","title"});
		model.setSpace(space);
		model.setTerm(term);
	}
	DocumentRecords documentRecords =  TeeLuceneSoapService.queryParserSearch(model);
	

	
	int totalPages=0;
	if(documentRecords.getTotalHits()%model.getPageSize()==0){
		totalPages=documentRecords.getTotalHits()/model.getPageSize();
	}else{
		totalPages=documentRecords.getTotalHits()/model.getPageSize()+1;
	}
%>
<title>检索结果</title>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqPaginator.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/init0.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/zt_webframe/css/package0.css">
<script type="text/javascript">
var contextPath='<%=contextPath%>';
var term='<%=term%>';
var space='<%=space%>';

/**
 * 全屏幕打开窗口
 * @param actionUrl
 * @param title
 * @returns
 */
function openFullWindow(actionUrl, title) {
	  var winParam = "menubar=0,toolbar=0,status=0";
	  winParam += ",scrollbars=1,resizable=1";
	  var left = 0;
	  var top = 0;
	  var width = window.screen.availWidth;
	  var height = window.screen.availHeight;
//	  if($.browser.webkit){
//		  height = parseInt(height)-60;
//	  }
	  height = parseInt(height)-80;
	  winParam += ",width=" + width;
	  winParam += ",height=" + height;
	  winParam += ",top=" + top;
	  winParam += ",left=" + left;
	  winParam += ",location=no";
	  
	  try{
		  if(window.external && window.external.IM_OA){
			window.external.IM_OpenNavigation(title,actionUrl.replace(contextPath,""),0,0);
			return;
		  }
	  }catch(e){
		  
	  }
	  
	  return window.open(actionUrl, new Date().getTime()+"", winParam);
	}

//增加一个String的方法
String.prototype.endWith=function(endStr){
  var d=this.length-endStr.length;
  return (d>=0&&this.lastIndexOf(endStr)==d);
};

function doSearch(){
	   var space=$('input:radio:checked').val();
	   var term=$("#term").val();
	   if(term==""){
		   window.location= "search.jsp";
		   return;
	   }
	   window.location= "result.jsp?space="+space+"&term="+encodeURI(term)+"&currentPage=1";
	   
	}

$(function(){
	try{
		$.jqPaginator('#pagination1', {
		    totalPages: <%=totalPages%>,
		    visiblePages: 10,
		    currentPage: <%=model.getCurPage()%>,
		    onPageChange: function (num, type) {
		    	window.location.href= contextPath+"/system/lucene/result.jsp?space="+'<%=space%>'+"&term="+encodeURI('<%=term%>')+"&currentPage="+num; 
		    }
		});
	}catch(e){
		
	}
});

function toDetail(obj){
	var attaId=$(obj).attr("attachId");
	var title=$(obj).attr("title");
	var diskId=$(obj).attr("fileNetDiskSid");
	

	if(title.endWith(".docx")||title.endWith(".doc")||title.endWith(".xls")||title.endWith(".xlsx")||title.endWith(".ppt")||title.endWith(".pptx")){
		openFullWindow(contextPath+"/system/core/ntko/indexNtko.jsp?attachmentId="+attaId+"&attachmentName="+title+"&op=7","文件详情");
	}else{
		openFullWindow(contextPath+"/system/core/base/fileNetdisk/fileManage/showContent.jsp?sid="+diskId+"&rootFolderPriv=3","文件详情");
	}
}
	
</script>

<style>
.c3{
	text-align:center;
	font-size:0px;
}
.inputWrap{
	float:left;
	width:450px;
	height:32px;
	position:relative;
	display:inline-block;
	overflow:hidden;
	padding:0px;
	margin:0px;
	vertical-align:top;
}
.btnWrap{
	float:left;
	width:80px;
	height:30px;
	display:inline-block;
	vertical-align:top;
	margin:0px;
	padding:0px;
	background:#3385ff;
	border:1px solid #2d78f4;
}
.btn{
	border:1px solid #b6b6b6;
	padding:0px;
	margin:0px;
	height:100%;
	width:100%;
	border:0px;
	background:transparent;
	color:white;
	font-family:"Microsoft YaHei";
	cursor:pointer;
}
.iput{
	border:0px;
	background:transparent;
	padding:0 0 0 10px;
	width:450px;
	height:30px;
	font-size:14px;
	color:gray;
	font-family:"Microsoft YaHei";
}
.pagination{
	float:right;
	margin-bottom:10px;
	margin-right
}
.pagination li{
	width:45px;
	height:30px;
	float:left;
	border:1px solid #f2f2f2;
	text-align:center;
	line-height:30px;
}
.pagination li a{
}

li.disabled{
	cursor:disabled;
}
li.disabled a{
	color:#ddd;
	
}
li.active{
	background-color:#428bca;
	color:#fff;
	
	
}
li.active a{
	color:#FFF;
}
</style>



</head>
<body>
<div style="padding: 10px;">
<table>
	<tr>
		<td>
			<img style="vertical-align: middle;" src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/qwjszx/icon_qwjszx.png" />
			<span style="font-size: 18px;margin-left:16px;vertical-align: middle;">全文检索中心</span>
		</td>
		<td style="padding-right: 200px;">
				<input style="display:inline-block;vertical-align: middle;margin-left:10px;" id="ggwp" name="space" class="radio" type="radio" value="pubnetdisk" checked="checked" />
				<label for="ggwp" style="vertical-align: middle;line-height:25px">公共网盘</label>
				
		</td>
	</tr>
	<tr>
		<td colspan="2" style="padding-top: 5px;padding-bottom: 5px;">
			<div class="c3 fl">
				<span class="inputWrap">
					<input onkeydown="if (event.keyCode == 13){doSearch();}" type="text" class="iput" name="term" id="term" value="<%=term%>"/>
				</span>
				<span class="btnWrap">
					<button type="button" class="btn" onclick="doSearch()">搜 索</button>
				</span>
			</div>
				<span style="line-height:30px;float:left;margin-left:20px;"> -- 搜索到&nbsp;<%=documentRecords.getTotalHits() %>&nbsp;条记录</span>
		</td>
	</tr>
</table>
</div>
<a style='position:absolute;right:20px;top:16px;' class='btn-win-white' href="search.jsp">返回首页</a>


<div style="padding-left:30px;min-width:900px;width:80%;">
   <c:forEach var="map" items="<%=documentRecords.getRecordList()%>" varStatus="s">
	   <div class="c1">
	    <p onclick="toDetail(this)" style="padding-top:10px; cursor:pointer;font-size:14px;color:blue" title="${map.title }" attachId="${map.attachSid }" fileNetDiskSid="${map.fileNetDiskSid }">${map.title}</p>
		<br>
		<p style="padding-bottom: 20px;">${map.body}...</p>
	   </div>
	</c:forEach>
</div>
		
<div style="padding: 10px;min-width:900px;width:80%;" class='clearfix'>
   <p id="p1"></p>
   <ul class="pagination" id="pagination1"></ul>
</div>
</body>
<script>
	$(function(){
		function checkPage(){
			$(".pagination li").each(function(i,obj){
				if($(this).hasClass("disabled")){
					$(this).prop("disabled",true);
				}
			});
		};
		checkPage();
		$(".pagination li").click(function(){
			checkPage();
		});
	});
	
	
</script>
</html>