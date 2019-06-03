<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/validator2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>
<title>系统界面设置</title>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/colorPicker/css/colorPicker.css"/>
<style type="text/css">

</style>
<script type="text/javascript" src="<%=contextPath %>/common/colorPicker/colorPicker.js"></script>
<script type="text/javascript">
var oldTopAttachmentId = "";

function systemOnchange(){
	var val = $("#dlrk").val();
	if(val=="1"){
		$("#mrdlzxtTr").hide();
		$("#zxtFirstTr").show();
	}else if(val=="2"){
		$("#zxtFirstTr").hide();
		$("#mrdlzxtTr").show();
	}
}

function doInit(){
	var url = "<%=contextPath %>/interfaceController/select.action";
	var para =  {} ;
	 var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		var sysInterface = jsonRs.rtData;
		if(sysInterface){
			
			//获取子系统数据
			var json = tools.requestJsonRs("/ApplicationSystemMaintainController/getList.action?rows=100000");
			for(var i=0;i<json.rows.length;i++){
				$("#mrdlzxt").append("<option value='"+json.rows[i].sid+"'>"+json.rows[i].systemName+"</option>");
			}
			
			
			bindJsonObj2Cntrl(sysInterface);
			oldTopAttachmentId = sysInterface.topAttachmentId;
			//字体样式
			var topBannerFont = sysInterface.topBannerFont;
			if(topBannerFont != null && topBannerFont !=""){
				var topBannerFontArr = topBannerFont.split(";");
				for(var i =0; i< topBannerFontArr.length ; i++){
					var topBannerFontStr = topBannerFontArr[i];
					if( topBannerFontStr != ''){
						if(topBannerFontStr.split(":").length  == 2){
							if(topBannerFontStr.split(":")[0] == 'font-family'){
								setFont(topBannerFontStr.split(":")[1]);
							}
							if(topBannerFontStr.split(":")[0] == 'font-size'){
								var fontSize = getMenuDataFontSize(topBannerFontStr.split(":")[1]);
								setSize(fontSize.value , fontSize.size);
							}
							if(topBannerFontStr.split(":")[0] == 'color'){
								$('#colorSelector2 div').css('backgroundColor', topBannerFontStr.split(":")[1]);
								$("#FONT_COLOR").val(topBannerFontStr.split(":")[1]);
							}
						}
						
					}
					
				}
			}
			
			 if(sysInterface.topAttachmentId ){
				 $("#topAttachmentFileDesc").show();
				 //获取附件
				 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + sysInterface.topAttachmentId + "&model=system";
				 $("#topAttachmentFileImg").attr("src",url);
			 }
			 
			 if(sysInterface.imPic ){
				 $("#imPicDesc").show();
				 //获取附件
				 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + sysInterface.imPic + "&model=system";
				 $("#imPicImg").attr("src",url);
			 }
			 
			 if(sysInterface.mLogo ){
				 $("#mLogoDesc").show();
				 //获取附件
				 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + sysInterface.mLogo + "&model=system";
				 $("#mLogoImg").attr("src",url);
			 }
			 
			 if(sysInterface.mPic ){
				 $("#mPicDesc").show();
				 //获取附件
				 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + sysInterface.mPic + "&model=system";
				 $("#mPicImg").attr("src",url);
			 }
			 
			 if(sysInterface.welcomePic ){
				 $("#welcomePicDesc").show();
				 //获取附件
				 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + sysInterface.welcomePic + "&model=system";
				 $("#welcomePicImg").attr("src",url);
			 }
			 
			 if(sysInterface.appTopBg){
				 $("#appTopBgDesc").show();
				 //获取附件
				 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + sysInterface.appTopBg + "&model=system";
				 $("#appTopBgImg").attr("src",url);
			 }
			 systemOnchange();
		}
	}else{
		//alert(jsonRs.rtMsg);
	} 
	
	bannerSet();
}

function getMenuDataFontSize(size){
	for(var i =0;i<menuDataSize.length ; i++){
		var fontF = menuDataSize[i];
		if(fontF.size == size){
			return fontF;
		}
	}
}
/**
 * 保存
 */
function doSave(){
	 if (checkFrom()){
		 getfont();
		 var url = "<%=contextPath %>/interfaceController/addOrUpdate.action";
		 var para =  tools.formToJson($("#form1")) ;
		 $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 		//callback(res);
			 		if(res.rtState){
			 			
			 			$.MsgBox.Alert_auto("保存成功",function(){
			 				window.location.reload();
			 			});
			 			
			 		}else{
			 			$.MsgBox.Alert_auto(res.rtMsg);
			 		}
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	                $.MsgBox.Alert_auto("保存错误");
	          },
	          dataType: 'json'});	
	} 
}

function deleteTopAttachment(type){
	var topAttachmentId =  $("#topAttachmentId").val();
	if(type == 1){//物理删除
		if(oldTopAttachmentId > 0 && topAttachmentId == ""){
			var url = contextPath+"/attachmentController/deleteFile.action";
			var para = {attachIds:oldAvatarId};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$("#topAttachmentId").val("");
				$("#topAttachmentFileDesc").hide();
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
		
	}else{
		 $("#topAttachmentId").val("");
		 $("#topAttachmentFileDesc").hide();
	}

}

function deleteImPic(){
	 $("#imPic").val("");
	 $("#imPicDesc").hide();
}

function deleteImPic(){
	 $("#imPic").val("");
	 $("#imPicDesc").hide();
}

function deletemLogo(){
	 $("#mLogo").val("");
	 $("#mLogoDesc").hide();
}

function deleteWelcomePic(){
	 $("#welcomePic").val("");
	 $("#welcomePicDesc").hide();
}

function deletemPic(){
	 $("#mPic").val("");
	 $("#mPicDesc").hide();
}

function deleteAppTopBg(){
	 $("#appTopBg").val("");
	 $("#appTopBgDesc").hide();
}

function checkFrom(){
	var status =  $("#form1").valid(); 
	if(!status){
		return false;
	}
	if($("#topAttachmentFile").val()){
		var isPicture =  checkSuppotPictureFile('topAttachmentFile');
		if(!isPicture){
			$("#topAttachmentFile")[0].select();
			return false;
		}
	}
	return true;
	//return true;
}
function checkStr(str){ 
    var re=/[\\"']/; 
    return str.match(re); 
  }
 

/**
 * 设置顶部字体各种样式
 */
var menuDataFont = [{ name:'<div  style="padding-top:5px;margin-left:10px">默认字体</div>' , value:"默认字体"}
,{ name:'<div  style="font-family:黑体;padding-top:5px;margin-left:10px">微软雅黑</div>',value:"微软雅黑"}
,{ name:'<div  style="font-family:黑体;padding-top:5px;margin-left:10px">黑体</div>',value:"黑体"}
,{ name:'<div  style="font-family:楷体;padding-top:5px;margin-left:10px">楷体</div>',value:"楷体"}
,{ name:'<div  style="font-family:隶书;padding-top:5px;margin-left:10px">隶书</div>',value:"隶书"}
,{ name:'<div  style="font-family:幼圆;padding-top:5px;margin-left:10px">幼圆</div>',value:"幼圆"}
,{ name:'<div  style="font-family:Arial;padding-top:5px;margin-left:10px">Arial</div>',value:"Arial"}
,{ name:'<div  style="font-family:Courier New;padding-top:5px;margin-left:10px">Courier New</div>',value:"Courier New"}
,{ name:'<div  style="font-family:Fixedsys;padding-top:5px;margin-left:10px">Fixedsys</div>',value:"Fixedsys"}
,{ name:'<div  style="font-family:Georgia;padding-top:5px;margin-left:10px">Georgia</div>',value:"Georgia"}

];
/**
 * 设置顶部字体大小各种样式
 */
var menuDataSize = [{ name:'<div  style="padding-top:5px;margin-left:10px">默认大小</div>',value:'默认大小' , size:"14pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">五号</div>',value:'五号',size:"10pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">小四</div>',value:'小四',size:"12pt" }
,{ name:'<div  style="padding-top:5px;margin-left:10px">四号</div>',value:'四号',size:"14pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">小三</div>',value:'小三' ,size:"15pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">三号</div>',value:'三号' ,size:"16pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">小二</div>',value:'小二' ,size:"18pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">二号</div>',value:'二号' ,size:"22pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">小一</div>',valuea:'小一',size:"14pt"}
,{ name:'<div  style="padding-top:5px;margin-left:10px">一号</div>',value:'一号' ,size:"26pt"}
]

/**
 * 设置字体
 */
function bannerSet(){
	var FONT_FAMILY_STR = "";
	for(var i = 0; i < menuDataFont.length ; i ++){
		FONT_FAMILY_STR = FONT_FAMILY_STR + " <li role='presentation'><a role='menuitem' tabIndex='-1' onclick='setFont(\""+ menuDataFont[i].value+"\")' >"+menuDataFont[i].name+ "</a></li>" ;
	}
	$("#fontFamilyLi").append(FONT_FAMILY_STR);
	
	
	var FONT_SIZE_STR = "";
	for(var i = 0; i < menuDataSize.length ; i ++){
		FONT_SIZE_STR = FONT_SIZE_STR + " <li role='presentation'><a role='menuitem' tabIndex='-1' onclick='setSize(\""+ menuDataSize[i].value+"\",\"" +menuDataSize[i].size+ "\")' >"+menuDataSize[i].name+ "</a></li>" ;
	}
	$("#fontSizeLi").append(FONT_SIZE_STR);
	
	

	$('#colorSelector2').ColorPicker({
		color: '#0000ff',
		onShow: function (colpkr) {
			$(colpkr).fadeIn(500);
			return false;
		},
		onHide: function (colpkr) {
			$(colpkr).fadeOut(500);
			return false;
		},
		onChange: function (hsb, hex, rgb) {
			//$('#colorSelector div').css('backgroundColor', '#' + hex);
		},onSubmit: function(hsb, hex, rgb, el) {
			$(el).val(hex);
			$(el).ColorPickerHide();
			$('#colorSelector2 div').css('backgroundColor', '#' + hex);
			$("#FONT_COLOR").val('#' + hex);
		} 
	});
}
/* 设置字体 */
function setFont(value){
	$("#fontFamily").html(value + "<b class=\"caret\"></b>");
	$("#FONT_FAMILY").val(value);
	
}
/* 设置字体大小 */
function setSize(value , size){
	$("#fontSize").html(value + "<b class=\"caret\"></b>");
	$("#FONT_SIZE").val(size);
}


function getfont(){
	var fontFamily = $("#FONT_FAMILY").val();
	var fontSize = $("#FONT_SIZE").val();
	var fontColor = $("#FONT_COLOR").val();
	//alert(fontFamily +":"+ fontSize +":"+ fontColor )
	$("#topBannerFont").val("font-family:" + fontFamily + ";font-size:" + fontSize + ";color:" + fontColor + ";" );
}

</script>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
<div id="toolbar" class="topbar clearfix">
	   <img class="title_img" src="/system/core/system/imgs/icon_xtjmsz.png" alt="">
	   &nbsp;<span class="title">系统界面设置</span>
	   
	   <button class="btn-win-white fr" onclick="doSave();">保存</button>	   
	</div>
<form enctype="multipart/form-data" method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="100%" align="center">
  <tr>
    <td colspan=2 class="TableHeader">
      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">全局设置</b>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">登录入口：</td>
    <td nowrap class="TableData" >
    	<select name="dlrk" id="dlrk" class="BigSelect" onchange="systemOnchange()">
       	 <option value="1">平台</option>
       	 <option value="2">子系统</option>
        </select>
    </td>
   </tr>
   <tr id="mrdlzxtTr" style="display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">默认登录子系统：</td>
    <td nowrap class="TableData" >
    	<select name="mrdlzxt" id="mrdlzxt" class="BigSelect">
        </select>
    </td>
   </tr>
   <tr id="zxtFirstTr" style="display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">有且仅有一个子系统权限时：</td>
    <td nowrap class="TableData" >
    	<select name="zxtFirst" id="zxtFirst" class="BigSelect">
       		<option value="1">默认不进入</option>
       		<option value="2">默认进入</option>
        </select>
    </td>
   </tr>
   <tr>
    <td colspan=2 class="TableHeader">
      <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">IE浏览器窗口标题</b>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">IE浏览器窗口标题：</td>
    <td nowrap class="TableData" >
        <input type="text" name="ieTitle" id="ieTitle" class="BigInput" size="40" maxlength="100" value="" style="">&nbsp;
    </td>
   </tr>
   <tr>
    <td colspan=2 class="TableHeader">
       <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">主界面</b>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">顶部大标题文字：</td>
    <td nowrap class="TableData">
        <input type="text" name="topBannerText" id="topBannerText" class="BigInput" size="40" maxlength="100" value="">&nbsp;
    </td>
   </tr>
   <tr  style="z-index:1200;display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">顶部大标题样式：</td>
    <td nowrap class="TableData"  >


		<ul class="nav navbar-nav">
            <li class="dropdown" style="float:left;" >
                <a id="fontFamily" class="dropdown-toggle" role="button" href="#" data-toggle="dropdown" style ="padding-top:5px;">字体 <b class="caret"></b></a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="fontFamily"  id="fontFamilyLi" style="min-width:100px;" >
               
                </ul>
            </li>
            <li class="dropdown" style="float:left;">
              <a id="fontSize" class="dropdown-toggle" role="button" href="#" data-toggle="dropdown" style ="padding-top:5px;padding-bottom:5px;">大小 <b class="caret"></b></a>
              <ul class="dropdown-menu" role="menu" aria-labelledby="drop2" style="min-width:100px" id="fontSizeLi">
                     
              </ul>
            </li>
             <li class="dropdown" style="float:left;">
            	  <a id="" class="dropdown-toggle" role="button" href="#" data-toggle="dropdown" style ="padding-top:5px;padding-bottom:5px;padding-right:3px;">颜色</a>
             </li>
             <li class="dropdown" style="float:left;">
        	  	  <div id="colorSelector2"><div style="background-color: #000000"></div></div>
        	  	 
            </li>
            <li style="clear:both;"></li>
          
            <!--  <li class="dropdown" style="margin-left:50px;">
            	  <a id="fontFilter" class="dropdown-toggle" role="button" href="#" data-toggle="dropdown" style ="padding-top:5px;padding-bottom:5px;">效果</a>
            	  <ul class="dropdown-menu" role="menu" aria-labelledby="drop3" style="min-width:100px" id="fontFilterLi">
                     
              </ul>
             </li>  -->
          </ul>
          
          <input type="hidden" name="FONT_FAMILY" id="FONT_FAMILY" value="默认字体">
          <input type="hidden" name="FONT_SIZE" id="FONT_SIZE" value="12pt">
          <input type="hidden" name="FONT_COLOR" id="FONT_COLOR" value="#000000">
          <input type="hidden" name="FONT_FILTER" id="FONT_FILTER" value="">
          <input type="hidden" name="topBannerFont" id="topBannerFont" value="">

    </td>
   </tr>
 	<tr   style="z-index:10">
   		 <td nowrap class="TableData" style="text-indent: 15px;width: 250px">顶部Logo：</td>
   		 <td nowrap class="TableData">

   			<input type="hidden" name="topAttachmentId" id="topAttachmentId" >
        	<input type="file" name="topAttachmentFile" id="topAttachmentFile" size="30" class="BigInput" title="选择图片文件" value="选择图片文件">
	   	 	<span id="topAttachmentFileDesc" style="display:none;">
	   	 		<img id="topAttachmentFileImg" ></img>
	   	 		<input type="button" value="删除" class="btn-win-white" onclick="deleteTopAttachment(0);"/>
	   	 		<br>
	   	 	</span>
          		JPG、GIF、PNG格式<!-- <span class="big4">注意：图像名称不能含有中文</span> -->
    </td>
   </tr> 
   <tr style="display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">顶部图标宽度：</td>
    <td nowrap class="TableData">

        <input type="text" name="topImgWidth" id="topImgWidth"  size="5" class="BigInput"   maxlengtd='3'  positive_integer="true" value="">&nbsp;像素
      &nbsp;(建议宽度小于500像素)
    </td>
   </tr>
   <tr style="display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">经典界面-顶部图标高度：</td>
    <td nowrap class="TableData">
        <input type="text" name="topImgHeight" id="topImgHeight" size="5" class="easyui-validatebox BigInput"   maxlengtd='3'  positive_integer="true" value="">&nbsp;像素&nbsp;(建议高度小于40像素)
    </td>
   </tr>
   <tr style="display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">顶部Logo文字描述：</td>
    <td  class="TableData">
    	注：通过修改对应风格包下的logo.txt文件，可以将当前风格包中的logo默认图标替换成对应的中文描述，修改后，请重新刷新系统缓存。刷新缓存请到 系统管理-》资源管理-》缓存管理 中重新刷新缓存数据。
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">经典界面-底部状态栏置中文字：</td>
    <td nowrap class="TableData">
        <textarea name="bottomStatusText" id="bottomStatusText" class="BigTextarea" cols="44" rows="3"></textarea><br>多行文字可以实现轮换显示
    </td>
   </tr>
<!--    <tr>
    <td colspan=2 class="TableHeader">登录界面</td>
   </tr>
   <tr>
    <td nowrap class="TableData">登录界面模板：</td>
    <td nowrap class="TableData">
        <select name="template" id="template"  class="BigSelect">
        </select>
    </td>
   </tr> 
   <tr>
    <td nowrap class="TableData">登录界面图片：</td>
    <td nowrap class="TableData">
       <div id="editPic1" style="display:none"><span><a href="javascript:void(0);" id="attachmentNameShow1" name="attachmentNameShow1"></a></span>
        <input type="button" value="恢复默认" class="BigButton" onClick="deleteAttachPic(1);"></div>
      <div id="showPic1" style="display:none"><br><input type="file" name="ATTACHMENT1" size=40 class="BigInput" id="ATTACHMENT1" ></div> 
       
   	   <input type="hidden" id="ATTACHMENT_ID_OLD1"  name="ATTACHMENT_ID_OLD1" value="">
   	   <input type="hidden" id="attachmentName1"  name="attachmentName1" value="">
   	   <input type="hidden" id="attachmentId1"  name="attachmentId1" value="">
	   <input type="hidden" id="ATTACHMENT_NAME_OLD1"  name="ATTACHMENT_NAME_OLD1" value="">	  
    <br>JPG、GIF、PNG格式，<span class="big4">注意：图像名称不能有中文</span>
    </td>
   </tr>
   -->
   <tr style="display:none;">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">选择界面布局：</td>
    <td nowrap class="TableData">
        <input type="checkbox" name="loginInterface" id="loginInterface" ><label for="loginInterface">允许用户登录时选择界面布局</label>
    </td>
   </tr>
   <tr style="display:none;">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">默认界面布局：</td>
    <td nowrap class="TableData">
        <select name="ui" id="ui" >
        <option value="0">标准界面</option>
        </select>
    </td>
   </tr>
   <tr style="display:none;">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">网站备案号：</td>
    <td nowrap class="TableData">
        <input type="text" name="miibeian" id="miibeian" class="BigInput" size="25" value="">
    </td>
   </tr>
   <!-- <tr>
    <td colspan=2 class="TableHeader">界面主题</td>
   </tr>
   <tr>
    <td nowrap class="TableData">选择界面主题：</td>
    <td nowrap class="TableData">
        <input type="checkbox" name="themeSelect" id="themeSelect" value="1" /><label for="themeSelect">允许用户选择界面主题</label>
        
        
     </td>
   </tr>
   <tr>
    <td nowrap class="TableData">默认界面主题：</td>
    <td nowrap class="TableData">
        <select name="theme" id="theme"  class="BigSelect">
        </select>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData">缺省界面风格：</td>
    <td nowrap class="TableData">
        <select name="style" id="style" class="BigSelect">
          <option id="classic" value="0">经典界面</option>
          <option id="webos" value="1">待定界面</option>
 
        </select>
    </td>
   </tr> -->
   <tr >
    <td colspan=2 class="TableHeader">
        <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">客户端设置</b>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">PC客户端Logo图片：</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="imPic" id="imPic" >
    	<input type="file" name="imPicFile" id="imPicFile" size="30" class="BigInput" title="选择图片文件" value="选择图片文件">
    	必须是PNG格式文件，建议尺寸为290*170
    	<span id="imPicDesc" style="display:none;">
    		<br/>
  	 		<img id="imPicImg" style="width:100px"></img>
  	 		<input type="button" value="删除" class="btn-win-white" onclick="deleteImPic(0);"/>
  	 		<br>
  	 	</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">移动端Logo图片：</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="mLogo" id="mLogo" >
    	<input type="file" name="mLogoFile" id="mLogoFile" size="30" class="BigInput" title="选择图片文件" value="选择图片文件">
    	必须是PNG格式文件,建议尺寸为400*100
    	<span id="mLogoDesc" style="display:none;">
    		<br/>
  	 		<img id="mLogoImg" style="width:100px"></img>
  	 		<input type="button" value="删除" class="btn-win-white" onclick="deletemLogo(0);"/>
  	 		<br>
  	 	</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">移动端背景图片：</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="mPic" id="mPic" >
    	<input type="file" name="mPicFile" id="mPicFile" size="30" class="BigInput" title="选择图片文件" value="选择图片文件">
    	必须是PNG格式文件，建议尺寸为1080*1920
    	<span id="mPicDesc" style="display:none;">
    		<br/>
  	 		<img id="mPicImg"  style="width:100px"></img>
  	 		<input type="button" value="删除" class="btn-win-white" onclick="deletemPic(0);"/>
  	 		<br>
  	 	</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">移动端欢迎界面：</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="welcomePic" id="welcomePic" >
    	<input type="file" name="welcomePicFile" id="welcomePicFile" size="30" class="BigInput" title="选择图片文件" value="选择图片文件">
    	必须是PNG格式文件，建议尺寸为1080*1920
    	<span id="welcomePicDesc" style="display:none;">
    		<br/>
  	 		<img id="welcomePicImg"  style="width:100px"></img>
  	 		<input type="button" value="删除" class="btn-win-white" onclick="deleteWelcomePic(0);"/>
  	 		<br>
  	 	</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">移动端签到区背景图</td>
    <td nowrap class="TableData">
    	<input type="hidden" name="appTopBg" id="appTopBg" >
    	<input type="file" name="appTopBgFile" id="appTopBgFile" size="30" class="BigInput" title="选择图片文件" value="选择图片文件">
    	必须是PNG格式文件
    	<span id="appTopBgDesc" style="display:none;">
    		<br/>
  	 		<img id="appTopBgImg"  style="width:100px"></img>
  	 		<input type="button" value="删除" class="btn-win-white" onclick="deleteAppTopBg(0);"/>
  	 		<br>
  	 	</span>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">移动端自定义主页：</td>
    <td nowrap class="TableData">
    	<input type="text" name="appIndex" id="appIndex" class="BigInput" >
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">移动端是否显示快速签到：</td>
    <td nowrap class="TableData">
    	<select id="appTopSignShow" name="appTopSignShow">
    		<option value="1">是</option>
    		<option value="0">否</option>
    	</select>
    </td>
   </tr>
   <tr >
    <td colspan=2 class="TableHeader">
       <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">用户头像</b>
    </td>
   </tr>
   <tr  style="display:none">
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">用户上传头像：</td>
    <td nowrap class="TableData">
        <input type="checkbox" name="avatarUpload" id="avatarUpload" value='1' ><label for="avatarUpload">允许用户上传头像</label>
    </td>
   </tr>
   <tr >
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">头像宽度：</td>
    <td nowrap class="TableData">
        <input type="text" name="avatarWidth" id="avatarWidth" class="easyui-validatebox BigInput"   maxlengtd='3'  validType ='integeBetweenLength[1,1000]' size="10" value="">&nbsp;像素
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">头像高度：</td>
    <td nowrap class="TableData">
        <input type="text" name="avatarHeight" id="avatarHight"  class="easyui-validatebox BigInput"   maxlengtd='3'  validType ='integeBetweenLength[1,1000]' size="10"  value="">&nbsp;像素
    </td>
   </tr>
  
   <tr>
    <td colspan=2 class="TableHeader">
       <img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;">
		<b style="color: #0050aa">注销提示文字</b>
    </td>
   </tr>
   <tr>
    <td nowrap class="TableData" style="text-indent: 15px;width: 250px">用户点击注销时，显示这里设置的文字：</td>
    <td nowrap class="TableData">
        <textarea name="logOutText" id="logOutText" class="BigTextarea" cols="44" rows="3"></textarea><br>
    </td>
   </tr>
   <tr>
    <td nowrap  class="" colspan="2" align="center">
         <input type="hidden" name="actionFont" id="actionFont" value="">
         <input type="hidden" name="actionSize" id="actionSize" value="">
         <input type="hidden" name="actionLight" id="actionLight" value="">
         <input type="hidden" name="actionLights" id="actionLights" value="">
         <input type="hidden" name="actionColor" id="actionColor" value="">
         <input type="hidden" name="actionLightFlag" id="actionLightFlag" value="">
         <input type="hidden" name="styleDis" id="styleDis" value="">
         <input type="hidden" name="ATTACHMENT_NAME" value="">
        <input type="hidden" name="ATTACHMENT_NAME1" value="">
        <input type="hidden" name="sid" id="sid" value="0">
    </td>
   </tr>
</table>
</form>
</body>
</html>