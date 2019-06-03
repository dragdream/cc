<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指定时间查询</title>
<script type="text/javascript">
function doInit(){
	
	//返回
	backBtn.addEventListener("tap",function(){
		window.location="list.jsp?status=1";
	});
	
	
	//查询
	searchBtn.addEventListener("tap",function(){
		if(checkForm()){
			var startTimeStr=$("#startTime").val();
			var endTimeStr=$("#endTime").val();
			window.location="list.jsp?status=5&startTimeStr="+startTimeStr+"&endTimeStr="+endTimeStr;
		}
		
	});
	
	//启动加载完毕的逻辑	
	//开始时间
	startTime.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		startTime.value = rs.text;	
		picker.dispose(); 
		});
	}, false);

	
	endTime.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
		endTime.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
}

//验证
function checkForm(){
	
	var startTimeStr=$("#startTime").val();
	var endTimeStr=$("#endTime").val();
    
	if(startTimeStr==null||startTimeStr==""||startTimeStr=="undefine"){
		Alert("请选择开始时间！");
		return  false;
	}
	if(endTimeStr==null||endTimeStr==""||endTimeStr=="undefine"){
		Alert("请选择结束时间！");
		return false;
	}
	
	return true;
}

</script>
</head>
<body onload="doInit()">
	<header id="header" class="mui-bar mui-bar-nav">
	    <span class="mui-icon mui-icon-back" id="backBtn" ></span>
	    <span class="mui-icon mui-icon-search mui-pull-right" id="searchBtn"></span>
		<h1 class="mui-title">指定时间查询 </h1>
	</header>
	<div id="muiContent" class="mui-content">
       <form id="form1" name="form1">
			<div class="mui-input-group">
			    <div class="mui-input-row">
					<label>开始时间：</label>
				</div>
				<div class="mui-input-row">
					<input type="text" id='startTime' name='startTime' class="Wdate BigInput" style="width: 160px" />
				</div>
			</div>
			<div class="mui-input-group">
				<div class="mui-input-row">
					<label>结束时间：</label>
				</div>
				<div class="mui-input-row">
					<input type="text" id='endTime' name='endTime' class="Wdate BigInput" style="width: 160px" />
				</div>
			</div>
		</form>
     </div>
</body>
</html>