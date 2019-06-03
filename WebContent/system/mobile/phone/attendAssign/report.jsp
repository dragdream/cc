<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&&ak=jEtlvZ7UXrKl6FtnqikedMIz"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.js"></script>
<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchControl/1.4/src/SearchControl_min.css" />
<title>外勤上报</title>
<script type="text/javascript">

  mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		CloseWindow();
	});
	
	listBtn.addEventListener("tap",function(){
		window.location = "list.jsp";
	});
  });
  
  
   //初始化功能
   function doInit(){
	   renderMap();
   }
   
   
   
   //渲染地图
   function renderMap(){
	// 百度地图API功能
		var map = new BMap.Map("mapDiv");
		var geoc = new BMap.Geocoder();  
		GeoLocation(function(rs){
			var point = new BMap.Point(rs.lng,rs.lat);
			map.centerAndZoom(point,18);
			
			var mk = new BMap.Marker(point);
			map.addOverlay(mk);
			map.panTo(point);
		
			$("#addrPoint").val(rs.lng+","+rs.lat);
		
			geoc.getLocation(point, function(rs){
				var addComp = rs.addressComponents;
				$("#address").html("地址:"+addComp.province + ", " + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
			});     
		});
   }
   
   
   //刷新 地图
   function refreshMap(){	   
	   renderMap();
   }
   
   
   //提交信息
   function doSave(){ 
	   
	   
	   var params=formToJson($("#form1"));
	   //获取上报地址
	   var address=$("#address").text();
	   params["address"]=address;
	   //获取上传的图片的id串
	   var pics=$(".pic");
	   var attachIds="";
	  
	   if(pics.length>0){
		  for(var m=0;m<pics.length;m++){
			  attachIds+=$(pics[m]).attr("attachId");
			  if(m!=pics.length-1){
				  attachIds+=",";
			  }
		  }
	   }
	   params["attachIds"]=attachIds;
	   var url=contextPath+"/TeeAttendAssignController/add.action";
	   mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:params,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
				   Alert("提交成功！");	
				   $("#remark").val("");
				   var ps=$(".pic");
				   if(ps.length>0){
					   for(var j=0;j<ps.length;j++){
						   $(ps[j]).remove();
					   }   
				   }
				}
			}
		}); 
   }
   
   
   //图片上传
   function upload(){
	   TakePhoto(function(files){
			if(files.length!=0){
				var attachIds = [];
				for(var i=0;i<files.length;i++){
					attachIds.push(files[i].path);
				}
				UploadPhoto(function(files0){
					for(var n=0;n<files0.length;n++){
						$("<img class=\"pic\" src='/attachmentController/downFile.action?id="+files0[n].id+"'  style=\"width:70px;height:70px;margin-left:15px \"  attachId="+files0[n].id+" />").appendTo($("#attachDiv"));
					}
				},attachIds,"AttendAssign","0");
			}
			
		});
   }
</script>

</head>
<body onload="doInit()" style="background:white;overflow:hidden">
<header id="header" class="mui-bar mui-bar-nav">
	<span class="mui-icon mui-icon-back" id="backBtn" ></span>
	<span class="mui-icon mui-icon-list mui-pull-right" id="listBtn"></span>
	<h1 class="mui-title">上报位置</h1>
</header>
<form id="form1">
 <div id="mapDiv" style="position:absolute;top:44px;left:0px;right:0px;bottom:304px;border-bottom: 1px solid #CCCCCC">

  </div>
  
  <div style="position:absolute;left:0px;right:0px;height:130px;bottom:173px;border-bottom: 1px solid #CCCCCC">
  	<textarea style="width:100%;height:100%" id="remark" name="remark" placeholder="请输入相关备注"></textarea>
  </div>
  <div style="position:absolute;left:0px;right:0px;height:90px;bottom:82px;border-bottom: 1px solid #CCCCCC;line-height:90px;padding-top:9px;padding-left:4px;overflow:hidden;overflow-x:auto;">
  	<div style="height:90px;" id="attachDiv">
  		<img src="img/upload_pic.png" style="width:70px;height:70px" onclick="upload()"/>
  	</div>
  </div>
  <div style="position:absolute;left:0px;right:0px;height:40px;bottom:41px;border-bottom: 1px solid #CCCCCC;line-height:40px">
  	<span id="address" name="address" style="padding-left: 5px"></span>
    <input type="hidden" name="addrPoint" id="addrPoint"/>
  </div>
  <div style="position:absolute;left:0px;right:0px;height:40px;bottom:0px;border-bottom: 1px solid #CCCCCC;line-height:40px">
  	 <div style="padding-left: 10px;text-align: center;padding-right: 10px;padding-top: 2px;">
  	    <button type="button" class="mui-btn mui-btn-primary mui-btn-outlined " onclick="refreshMap()"  style="float: left">刷新地图</button>
       
        <button type="button" class="mui-btn mui-btn-success mui-btn-outlined " onclick="doSave();" style="float: right">提交信息</button>
  	 </div> 
  </div>
</form>
</body>
</html>