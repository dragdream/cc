<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	//任务主键
	int  supId=TeeStringUtil.getInteger(request.getParameter("supId"),0);
	//反馈主键
	int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String title="";
	if(sid>0){
		title="修改反馈";
	}else{
		title="发表反馈";
	}
	
	 String option=TeeStringUtil.getString(request.getParameter("option"),"wjs");
%>
<!DOCTYPE HTML>
<html>
<head>
<title>发表反馈</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<link href="/system/mobile/phone/style/style.css" rel="stylesheet" type="text/css" />
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<span class="mui-icon mui-icon-checkmarkempty mui-pull-right " onclick="save()"></span>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1"  >
   <input type="hidden" value="<%=supId %>" id="supId" name="supId" />
   <input type="hidden" value="<%=sid %>" id="sid" name="sid" />
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>标题</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		    <input type="text" name="title" id="title" placeholder="标题"/>
		</div>
	</div>
	
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>缓急</label>
		</div>
		<div class="mui-input-row" >
		   <select id="level" name="level" style="width: 300px">
               <option value="0">普通</option>
               <option value="1">紧急</option>
            </select>
		</div>
	</div>
	
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="content" id="content" placeholder="内容" ></textarea>
		</div>
	</div>
</form>	
</div>

<script>
var supId=<%=supId%>;
var sid=<%=sid%>;

var option="<%=option%>";

//初始化
function doInit(){
   if(sid>0){
	   getInfoBySid();
   }
}

//根据主键  获取详情
function getInfoBySid(){
	var url=contextPath+"/supFeedBackController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
			}
		}
	});
}
//保存
function save(){
	if(check()){
		var url=contextPath+"/supFeedBackController/addOrUpdate.action";
		var param=formToJson("#form1");
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					if(sid>0){
						alert("修改成功！");
						window.location="feedBackRecords.jsp?supId="+supId+"&&option="+option;	
					}else{
						alert("反馈成功！");	
						window.location="index.jsp?sid="+supId+"&&option="+option;
					}	
				}
			}
		});
	}
}
//验证
function check(){
	var title=$("#title").val();
	var content=$("#content").val();
    if(title==""||title==null){
    	alert("请填写标题！");
    	return false;
    }
    if(content==""||content==null){
    	alert("请填写内容！");
    	return false;
    }
    return true;
}


mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){		
		if(sid>0){
			window.location="feedBackRecords.jsp?supId="+supId+"&&option="+option;	
		}else{
			window.location="index.jsp?sid="+supId+"&&option="+option;
		}	
	});
});
</script>

</body>
</html>