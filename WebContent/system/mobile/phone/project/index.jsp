<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>项目管理</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
	.mui-media-body{
		line-height:42px;
	}
</style>
<script type="text/javascript">
//初始化方法
function  doInit(){
	var url=contextPath+"/teeMenuGroup/getPrivSysMenu.action";
	mui.ajax(url,{
	type:"post",
	dataType:"html",
	data:null,
	timeout:10000,
	success:function(json){
		json = eval("("+json+")");
		if(json.rtState){
			var data=json.rtData;
			if(data!=null&&data.length>0){
				for(var i=0;i<data.length;i++){
					if(data[i].menuCode=="/system/subsys/project/myproject/index.jsp"){//我的项目
						$("#myproject").show();
					}else if(data[i].menuCode=="/system/subsys/project/mytask/index.jsp"){//我的任务
						$("#mytask").show();
					}else if(data[i].menuCode=="/system/subsys/project/mylookup/index.jsp"){//项目查阅
						$("#mylookup").show();
					}else if(data[i].menuCode=="/system/subsys/project/projectfile/index.jsp"){//项目文档
						$("#projectfile").show();
					}else if(data[i].menuCode=="/system/subsys/project/projectquestion/index.jsp"){//项目问题
						$("#projectquestion").show();
					}else if(data[i].menuCode=="/system/subsys/project/projectquery/index.jsp"){//项目查询
						$("#projectquery").show();
					}else if(data[i].menuCode=="/system/subsys/project/projectApprove/index.jsp"){//项目审批
						$("#projectapprove").show();
					}
					
				}
			}
		}
	}
});	
	
	
}


</script>

</head>
<body onload="doInit();">
		<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="CloseWindow()"></span>
			<h1 class="mui-title">项目管理</h1>
		</header>
		<div class="mui-content">
			<div id="wrapper">
				<ul class="mui-table-view">
				<li class="mui-table-view-cell mui-media" id="myproject" style="display: none;">
					<a href="myproject/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_wdxm.png">
						<div class="mui-media-body">
							我的项目
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="mytask" style="display: none;">
					<a href="mytask/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_wdrw.png">
						<div class="mui-media-body">
							我的任务
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="mylookup"  style="display: none;">
					<a href="mylookup/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmcy.png">
						<div class="mui-media-body">
							项目查阅
						</div>
					</a>
				</li>
                <li class="mui-table-view-cell mui-media" id="projectfile" style="display: none;">
					<a href="projectfile/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmwd.png">
						<div class="mui-media-body">
							项目文档
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="projectquestion" style="display: none;">
					<a href="projectquestion/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmwt.png">
						<div class="mui-media-body">
							项目问题
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="projectquery" style="display: none;">
					<a href="projectquery/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmcx.png">
						<div class="mui-media-body">
							项目查询
						</div>
					</a>
				</li>
				<li class="mui-table-view-cell mui-media" id="projectapprove" style="display: none;">
					<a href="projectapprove/index.jsp" class="">
						<img class="mui-media-object mui-pull-left" src="imgs/icon_xmsp.png">
						<div class="mui-media-body">
							项目审批
						</div>
					</a>
				</li>
			</ul>
			</div>
		</div>
</body>
</html>