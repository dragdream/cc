<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html style="background-image:url(img/bg.jpg)">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>个人便签</title>

<link rel="stylesheet" type="text/css" href="demo-css/highlight-railscasts.css">
<link rel="stylesheet" type="text/css" href="css/color-sticker.css">
<style type="text/css">

</style>
</head>
<body style=" " >


<div class="demo-container">


</div>

<script src="js/jquery-1.11.0.min.js" type="text/javascript"></script>
<script src="/common/js/tools2.0.js?v=1"></script>
<script src="/common/zt_webframe/js/package.js"></script>
<script type="text/javascript" src="js/colorsticker.js"></script>
<script type="text/javascript" src="demo-js/highlight.pack.js"></script>
<script type="text/javascript">
	//
	var stickers = []; 
    //获取当前登陆人的便签
    var jsonObj=tools.requestJsonRs("/noteManage/selectPersonalNote.action",{});
    if(jsonObj.rtState){
    	var list=jsonObj.rtData;
    	for(var i=0;i<list.length;i++){
    		stickers.push({stickerId:list[i].sid,left:list[i].left, top:list[i].top, content:list[i].content});
    	}
    }else{
    	$.MsgBox.Alert_auto("数据获取失败！");
    }
	$('body').sticker({
		saveStickerCallback: function(sticker){
			//alert('sticker info: left ' + sticker.left + ',top ' + sticker.top + ',content ' + sticker.content);
			var url="/noteManage/addOrUpdate.action";
		    var sid=sticker.stickerId;
		    if(sid==""||sid===null||sid=="null"){
		    	sid=0;
		    }
			var json=tools.requestJsonRs(url,{left:sticker.left,top:sticker.top,content:sticker.content,sid:sid});
		    if(json.rtState){
		    	$.MsgBox.Alert_auto("保存成功！",function(){
		    		window.location.reload();
		    	});
		    	
		    }else{
		    	$.MsgBox.Alert_auto("保存失败！");
		    } 
		},closeStickerCallback: function(stickerId){  //删除便签的回调方法，参数是便签的stickerId
			 if(window.confirm("是否确认删除该便签？")){
				    var url="/noteManage/del.action";
				    var json=tools.requestJsonRs(url,{sid:stickerId});
				    if(json.rtState){
				    	$.MsgBox.Alert_auto("删除成功！",function(){
				    		window.location.reload();
				    	});
				    	
				    }else{
				    	$.MsgBox.Alert_auto("删除失败！");
				    }
			  }
			return false;
		}
	},stickers);
</script>


</body>
</html>