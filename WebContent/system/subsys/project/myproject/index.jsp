<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
  String option=TeeStringUtil.getString(request.getParameter("option"));
  if(TeeUtility.isNullorEmpty(option)){
	 option="立项中";
  }


%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的项目</title>
<script>
//初始化
var option="<%=option%>";
function  doInit(){
	
	var isCreate=hasCreatePriv();
	if(isCreate==1){
		$("#createButton").show();
	}else{
		$("#createButton").hide();
	}
	
}

//判断当前登陆人有没有创建权限
function hasCreatePriv(){
	var url=contextPath+"/projectCreatePrivController/isCreateByLoginUser.action";
	var json=tools.requestJsonRs(url,{});
	if(json.rtState){
		var data=json.rtData;
		return data;
	}
	return 0;
}

//新建项目
function newProject(){
	 window.location.href=contextPath+"/system/subsys/project/myproject/addOrUpdate.jsp";
 }


</script>


</head>
<body onload="doInit()" style="overflow: hidden;padding-left:10px;padding-right:10px;padding-top: 5px">
	
	<div class="titlebar clearfix" >
		<img class = 'tit_img' style="margin-right:10px" src="<%=contextPath %>/system/subsys/project/img/icon_project.png">
		<p class="title">我的项目</p>
		<ul id = 'tab' class = 'tab clearfix' style='display:inline-block;'>
			
		</ul>
		<div class="right fr clearfix">
		    <input type="button" class="btn-win-white" style="display: none;" value="创建项目"  id="createButton" onclick='newProject();'/>
		    <!-- <input type="button" class="btn-win-white" value="检索"  id="searchButton" onclick='obtIframeFun("a");'/> -->
		</div>
		<span class="basic_border_grey fl"></span>
	</div>
	  <div id="tab-content" style="padding-left: 10px;padding-right:10px"></div>
	  
</body>
<script>
/*  $.addTab("tab","tab-content",[{title:"立项中",url:contextPath+"/system/subsys/project/myproject/lixiangzhong.jsp"},
                              {title:"审批中",url:contextPath+"/system/subsys/project/myproject/shenpizhong.jsp"},
                              {title:"办理中",url:contextPath+"/system/subsys/project/myproject/banlizhong.jsp"},
                              {title:"挂起中",url:contextPath+"/system/subsys/project/myproject/guaqizhong.jsp"},
                              {title:"已办结",url:contextPath+"/system/subsys/project/myproject/yibanjie.jsp"},
                              ]);  */
 
 
                              
                              
 arr=[{title:"立项中",url:contextPath+"/system/subsys/project/myproject/lixiangzhong.jsp"},
      {title:"审批中",url:contextPath+"/system/subsys/project/myproject/shenpizhong.jsp"},
      {title:"办理中",url:contextPath+"/system/subsys/project/myproject/banlizhong.jsp"},
      {title:"挂起中",url:contextPath+"/system/subsys/project/myproject/guaqizhong.jsp"},
      {title:"已办结",url:contextPath+"/system/subsys/project/myproject/yibanjie.jsp"},
      ];
      for(var i=0,l=arr.length;i<l;i++){
         if(option== arr[i].title){
             arr[i].active=true;
          }
      }
      $.addTab("tab","tab-content",arr); 	

 

/* function obtIframeFun(fun){
	$("iframe")[0].contentWindow.eval(fun+'()');
} */
</script>

</html>