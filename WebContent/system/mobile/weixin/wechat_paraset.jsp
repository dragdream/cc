<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/header/header.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>基础参数设置</title>

<script type="text/javascript" charset="UTF-8">
function doInit(){
	var json = tools.requestJsonRs(contextPath+"/weixin/getBasicParam.action");
// 	$("#WEIXIN_URL").val(json.rtData.WEIXIN_URL);
	$("#WE_CHAT_CORPID").val(json.rtData.WE_CHAT_CORPID);
	$("#WE_CHAT_SECRET").val(json.rtData.WE_CHAT_SECRET);
// 	$("#wechat").val(1);
}

/**
 * 保存基本信息
 */
function save(){
	var json = tools.requestJsonRs(contextPath+"/weixin/saveBasicParam.action",{
// 		WEIXIN_URL:$("#WEIXIN_URL").val(),
		WE_CHAT_CORPID:$("#WE_CHAT_CORPID").val(),
		WE_CHAT_SECRET:$("#WE_CHAT_SECRET").val(),
		wechat:$("#wechat").val()
	});
	if(json.rtState == true){
		alert("保存成功");
	}else{
		alert(json.rtMsg);
	}
	
}

/**
 * 测试连接  
 */
function test(){
	$("#testBtn").attr("disabled","disabled");
	tools.requestJsonRs(contextPath+"/weixin/testConnectionWeiXin.action",{WEIXIN_CORPID:$("#WE_CHAT_CORPID").val(),WEIXIN_SECRET:$("#WE_CHAT_SECRET").val()},true,function(json){
		$("#testBtn").removeAttr("disabled");
		if(json.rtState){
			alert("连接成功");
		}else{
			alert(json.rtMsg);
		}
	});
}
</script>
</head>
<body onload="doInit();" style="text-indent:15px;">
	<!-- <fieldset>
		<legend>
			<h5 style="font-size:16px;font-weight:bold;font-family:微软雅黑">参数设置 - OA外网地址
			
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="help/index.jsp" target="_blank">点击查看帮助文档</a> <br />
		
			</h5>
		</legend>
	</fieldset> -->
	<form>
	 <!--  <div class="form-group">
	    <label for="DD_URL">微信企业号参数设置</label>
	    <input type="text" class="form-control" id="WEIXIN_URL" name="WEIXIN_URL" placeholder="http://xxx.xxx.xxx" style="width:600px;">
	    <span class="help-block">注：
	   		 OA的外网地址格式为: <code>http://www.xxx.com</code> 或者 数字ip <code>http://158.58.12.XX</code>。</span>
	     	  &nbsp;如OA地址使用了https协议443端口，请填写https协议头， <code>https://www.xxx.com</code>。</span>
	 
	  </div> -->
	  
	  <fieldset>
		<legend>
			<h5 style="font-size:16px;font-weight:bold;font-family:微软雅黑">微信公众号参数设置</h5>
		</legend>
	</fieldset>
	  <div class="form-group">
	    <label for="CorpID">CorpID</label>
	    <input type="text" class="form-control" id="WE_CHAT_CORPID" name="WE_CHAT_CORPID" placeholder="CorpID" style="width:250px;margin-left:30px;">
	    <input type="hidden" name="wechat" id="wechat" value="1"/>
	  </div>
	  <div class="form-group">
	    <label for="Secret">Secret</label>
	    <input type="text" class="form-control" id="WE_CHAT_SECRET" name="WE_CHAT_SECRET" placeholder="Secret" style="width:600px;margin-left:30px;">
	  </div>
	  <button type="button" class="btn btn-default" onclick="save()" style="margin-left:30px;">保存</button>
	  &nbsp;
	  <button type="button" id="testBtn" class="btn btn-warning" onclick="test()">连接测试</button>
	<!--   &nbsp;&nbsp;<a href="help1/index.jsp" target="_blank">帮助文档</a> -->
	</form>
</body>
</html>