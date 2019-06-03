<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header.jsp" %>
<title>系统界面设置</title>
<link href="<%=cssPath %>/style.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath %>/stylebootstrap.css" rel="stylesheet" type="text/css" />

 <link href="<%=cssPath %>/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="<%=cssPath %>/bootstrap.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/easyui/themes/gray/easyui.css"/>
<script type="text/javascript" src="<%=contextPath %>/common/jquery/jquery-min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/bootstrap/js/popover.js"></script>

<script type="text/javascript" src="<%=contextPath%>/common/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/src/teeValidagteBox.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/js/tools.js"></script>
<script type="text/javascript">
function doInit(){
	$('#qq').popover();
	var url = "<%=contextPath %>/interfaceController/select.action";
	var para =  {} ;
	 var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sysInterface = jsonRs.rtData;
		if(sysInterface){
			bindJsonObj2Cntrl(sysInterface);
		}
		
		
	}else{
		//alert(jsonRs.rtMsg);
	} 
	
}

/**
 * 保存
 */
function doSave(){
	if (checkFrom()){
		var url = "<%=contextPath %>/interfaceController/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		var jsonRs = tools.requestJsonRs(url,para);
		if(jsonRs.rtState){
			//alert(jsonRs.rtMsg);
			$.messager.show({
				msg : '保存成功！！',
				title : '提示'
			});
		}else{
			alert(jsonRs.rtMsg);
			
		}
	}
	
}

function checkFrom(){
	return $("#form1").form('validate'); 
	//return true;
}
function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 
function aa(){
	
}

function ddd(){
	$('#drop4').dropdown();
	
	$('#queryType').dropdown();
	
}
</script>
</head>
<body onload="doInit()" topmargin="5" style="padding:8px;">
<table border="0" width="100%" cellspacing="0" cellpadding="3" class="small">
  <tr>
    <td class="Big"><span class="Big3">&nbsp;系统界面设置</span>
    </td>
  </tr>
</table>



    
    
<form enctype="multipart/form-data" method="post" name="form1" id="form1">
  <table class="TableBlock" width="700" align="center">
   <tr>
    <td colspan=2 class="TableHeader">IE浏览器窗口标题</td>
   </tr>
   <tr>
    <td nowrap class="TableData">IE浏览器窗口标题：</td>
    <td nowrap class="TableData" >
        <input type="text" name="ieTitle" id="ieTitle" class="BigInput" size="40" maxlength="100" value="" style="">&nbsp;
    </td>
   </tr>
   <tr>
    <td colspan=2 class="TableHeader">主界面</td>
   </tr>
   <tr>
    <td nowrap class="TableData">顶部大标题文字：</td>
    <td nowrap class="TableData">
        <input type="text" name="topBannerText" id="topBannerText" class="BigInput" size="40" maxlength="100" value="">&nbsp;
    </td>
   </tr>
   <tr>
  
   
   <tr>
    <td nowrap class="TableData">顶部图标宽度：</td>
    <td nowrap class="TableData">

        <input type="text" name="topImgWidth" id="topImgWidth"  size="5" class="easyui-validatebox BigInput"   maxlengtd='3'  validType ='integeBetweenLength[1,1000]' value="">&nbsp;像素
      &nbsp;(建议宽度小于550像素)
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">经典界面-顶部图标高度：</td>
    <td nowrap class="TableData">
        <input type="text" name="topImgHeight" id="topImgHeight" size="5" class="easyui-validatebox BigInput"   maxlengtd='3'  validType ='integeBetweenLength[1,1000]' value="">&nbsp;像素&nbsp;(建议高度小于64像素)
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">经典界面-底部状态栏置中文字：</td>
    <td nowrap class="TableData">
        <textarea name="bottomStatusText" id="bottomStatusText" class="" cols="44" rows="3"></textarea><br>多行文字可以实现轮换显示
    </td>
   </tr>
  
 
  
   <tr style="display:none;">
    <td nowrap class="TableData">选择界面布局：</td>
    <td nowrap class="TableData">
        <input type="checkbox" name="loginInterface" id="loginInterface" ><label for="loginInterface">允许用户登录时选择界面布局</label>
    </td>
   </tr>
 
  
  
   <tr>
    <td colspan=2 class="TableHeader">界面风格</td>
   </tr>
   <tr>
    <td nowrap class="TableData">缺省界面风格：</td>
    <td nowrap class="TableData">
        <select name="style" id="style" class="BigSelect">
          <option id="classic" value="0">经典界面</option>
          <option id="webos" value="1">待定界面</option>
 
        </select>
    </td>
   </tr>
   <tr>
    <td colspan=2 class="TableHeader">用户头像</td>
   </tr>
   <tr>
    <td nowrap class="TableData">用户上传头像：</td>
    <td nowrap class="TableData">
        <input type="checkbox" name="avatarUpload" id="avatarUpload" value='1' ><label for="avatarUpload">允许用户上传头像</label>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">用户上传头像最大宽度：</td>
    <td nowrap class="TableData">
        <input type="text" name="avatarWidth" id="avatarWidth" class="easyui-validatebox BigInput"   maxlengtd='3'  validType ='integeBetweenLength[1,1000]' size="10" value="">&nbsp;像素
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">用户上传头像最大高度：</td>
    <td nowrap class="TableData">
        <input type="text" name="avatarHeight" id="avatarHight"  class="easyui-validatebox BigInput"   maxlengtd='3'  validType ='integeBetweenLength[1,1000]' size="10"  value="">&nbsp;像素
    </td>
   </tr>
  
   <tr>
    <td colspan=2 class="TableHeader">注销提示文字</td>
   </tr>
   <tr>
    <td nowrap class="TableData" width="150">用户点击注销时，显示这里设置的文字：</td>
    <td nowrap class="TableData">
        <textarea name="logOutText" id="logOutText" class="BigTextarea" cols="44" rows="3"></textarea><br>
    </td>
   </tr>
   <tr>
    <td nowrap  class="" colspan="2" align="center">
 
        <input type="button" id="" value="确定" class="btn btn-primary" onclick="doSave();">
    </td>
   </tr>
</table>
</form>








<nav role="navigation" class="navbar navbar-default navbar-static" id="navbar-example">
        <div class="navbar-header">
          <button data-target=".bs-js-navbar-collapse" data-toggle="collapse" type="button" class="navbar-toggle">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a href="#" class="navbar-brand">Project Name</a>
        </div>
        <div class="collapse navbar-collapse bs-js-navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="dropdown">
              <a data-toggle="dropdown" class="dropdown-toggle" role="button" href="#" id="drop1">Dropdown <b class="caret"></b></a>
              <ul aria-labelledby="drop1" role="menu" class="dropdown-menu">
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Action</a></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Another action</a></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Something else here</a></li>
                <li class="divider" role="presentation"></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Separated link</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a data-toggle="dropdown" class="dropdown-toggle" role="button" id="drop2" href="#">Dropdown 2 <b class="caret"></b></a>
              <ul aria-labelledby="drop2" role="menu" class="dropdown-menu">
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Action</a></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Another action</a></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Something else here</a></li>
                <li class="divider" role="presentation"></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Separated link</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li class="dropdown" id="fat-menu">
              <a data-toggle="dropdown" class="dropdown-toggle" role="button" id="drop3" href="#">Dropdown 3 <b class="caret"></b></a>
              <ul aria-labelledby="drop3" role="menu" class="dropdown-menu">
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Action</a></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Another action</a></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Something else here</a></li>
                <li class="divider" role="presentation"></li>
                <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Separated link</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.nav-collapse -->
      </nav>


<div class="btn-group">
        <button data-toggle="dropdown" class="btn btn-default dropdown-toggle" type="button" id="queryType">Default <span class="caret"></span></button>
        <ul role="menu" class="dropdown-menu">
          <li><a href="#">Action</a></li>
          <li><a href="#">Another action</a></li>
          <li><a href="#">Something else here</a></li>
          <li class="divider"></li>
          <li><a href="#">Separated link</a></li>
        </ul>
      </div>


    <div class="col-lg-6">
          <div class="input-group dropdown">
            <input type="text" class="form-control"  >
	            <span class="input-group-btn">
	             </span>
	             <button type="button" class="btn btn-default"   data-toggle="dropdown" role="button" onclick="ddd();" id="drop4">Go!</button>
	           
             <ul aria-labelledby="drop4" role="menu" class="dropdown-menu" id="menu1">
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Action</a></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Another action</a></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Something else here</a></li>
            <li class="divider" role="presentation"></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Separated link</a></li>
          </ul>
          </div><!-- /input-group -->
        </div>


<br>
<!-- 
<div class="dropdown">
  <a data-toggle="dropdown" id="drop4" role="button">Dropdown trigger</a>
  <ul class="dropdown-menu" role="presentation" aria-labelledby="drop4">
     		<li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Action</a></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Another action</a></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Something else here</a></li>
            <li class="divider" role="presentation"></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Separated link</a></li>
    
  </ul>
  

</div> -->






<br>


<br>
<br>

<br>

<!-- <li class="dropdown">
          <a href="#" data-toggle="dropdown" role="button" id="drop4">Dropdown <b class="caret"></b></a>
          <ul aria-labelledby="drop4" role="menu" class="dropdown-menu" id="menu1">
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Action</a></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Another action</a></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Something else here</a></li>
            <li class="divider" role="presentation"></li>
            <li role="presentation"><a href="http://twitter.com/fat" tabindex="-1" role="menuitem">Separated link</a></li>
          </ul>
        </li>
 -->

<div class="bs-example tooltip-demo">
      <div class="bs-example-tooltips">
        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="left" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
          左侧弹框
        </button>
        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="top" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
          上方弹框
        </button>
        <button type="button"  id="qq" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="bottom" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
          下方弹框
        </button>
        <button type="button" class="btn btn-default" data-container="body" data-toggle="popover" data-placement="right" data-content="Vivamus sagittis lacus vel augue laoreet rutrum faucibus.">
          右侧弹框
        </button>
      </div>
    </div><!-- /example -->
    
    
<br>
<br>

<br>
</body>
</html>