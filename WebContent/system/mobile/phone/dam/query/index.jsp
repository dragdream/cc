<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>档案查询</title>
<%@ include file="/system/mobile/mui/header.jsp" %>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>

	<h1 class="mui-title">档案查询</h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1" action="result.jsp">
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>组织机构代码</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="组织机构代码" name="orgCode" id="orgCode">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>全宗号</label>
		</div>
		<div class="mui-input-row">
			 <input type="text" placeholder="全宗号" name="qzh" id="qzh">
        </select>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>年份</label>
		</div>
		<div class="mui-input-row">
			<input type="text" placeholder="年份" name="year" id="year">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>保管期限</label>
		</div>
		<div class="mui-input-row">
			<select id="retentionPeriod" name="retentionPeriod">
                <option value="">请选择</option>
              </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>文件标题</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='title' name='title' placeholder="文件标题" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>发/来文单位</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id='unit' name='unit' placeholder="发/来文单位" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>文件编号</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <input type="text" id='number' name='number' placeholder="文件编号" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>主题词</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <input type="text" id='subject' name='subject' placeholder="主题词" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>密级</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		     <select id="mj" name="mj">
                <option value=" ">请选择</option>
                <option value="">空</option>
                <option value="内部">内部</option>
                <option value="秘密">秘密</option>
                <option value="机密">机密</option>
                <option value="绝密">绝密</option>
             </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>缓急</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<select id="hj" name="hj">
               <option value=" ">请选择</option>
                <option value="">空</option>
                <option value="普通">普通</option>
                <option value="急">急</option>
                <option value="加急">加急</option>
                <option value="特急">特急</option>
                <option value="特提">特提</option>
                <option value="平急">平急</option>
             </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			 <textarea rows="6" style="width: 550px" id="remark" name="remark" placeholder="备注"></textarea>
		</div>
	</div>
    <div class="mui-content-padded">
        <button type="button" class="mui-btn mui-btn-primary mui-btn-block" onclick="doSearch();">查询</button>
    </div>
</form>	
</div>


<script>

function doInit(){
	//初始化保管期限
	getSysCodeByParentCodeNo("DAM_RT" , "retentionPeriod");
}






mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		window.location= "../index.jsp";
	});

});


//查询
function doSearch(){
   var param=formToJson("#form1");
   window.location = "result.jsp?param="+jsonObj2String(param);

}
</script>

</body>
</html>