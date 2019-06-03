<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
      TeePerson loginUser=(TeePerson)request.getSession().getAttribute(TeeConst.LOGIN_USER);
      int userId=loginUser.getUuid();
    %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/system/mobile/mui/header.jsp" %>
<script type="text/javascript" charset="UTF-8">
var userId=<%=userId%>;
function doInit(){
	var url = contextPath+"/noteManage/selectPersonalNote.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			var list = json.rtData;
			var render = [];
			for(var i=0;i<list.length;i++){
				var color="";
				if(list[i].color=="rgb(235, 231, 230)"){
					color=6;
				}else if(list[i].color=="rgb(187, 224, 253)"){
					color=7;
				}else if(list[i].color=="rgb(254, 250, 203)"){
					color=8;
				}else if(list[i].color=="rgb(210, 248, 189)"){
					color=9;
				}else if(list[i].color=="rgb(254, 185, 190)"){
					color=10;
				}else{
					color=1;
				}
				render.push("<div style='background-color:"+list[i].color+"' class='outline outline_"+color+"'  ontouchstart='gtouchstart("+list[i].sid+")' ontouchmove='gtouchmove()' ontouchend='gtouchend("+list[i].sid+")'>");
				render.push("<div>");
				render.push(list[i].content.substring(0,50).replace(/\r\n/gi,"<br/>")+"…");
				render.push("</div>");
				render.push("<table class='tb'>");
				render.push("<tr>");
				render.push("<td>"+list[i].createTimeStr+"</td>");
				render.push("<td class='tb_op'>");
				//render.push("<img src='/common/images/other/5.png' onclick=\"Email("+list[i].sid+")\"/>");
				//render.push("&nbsp;&nbsp;");
				//render.push("<img src='/common/images/other/8.png' onclick='window.event.cancelBubble = true;del("+list[i].sid+")'/>");
				render.push("</td>");
				render.push("</tr>");
				render.push("</table>");
				render.push("</div>");
			}
			
			$("#warpcontent").html(render.join(""));
		}
	});
	$("body").on("tap","#addOrUpdatebianqian",function(){
		window.location.href="addOrUpdatebianqian.jsp?sid=0";
	 });
}



/**
 * 邮件
 */
function Email(sid){
	openFullWindow("/system/core/email/send.jsp?noteId="+sid);
	window.event.cancelBubble = true;
}

/**
 * 导出
 */
function exp(){
	$("#frame0").attr("src",contextPath+"/noteManage/export.action");
}


/**
 * 删除
 */
function del(sid){
	 $.MsgBox.Confirm ("提示", "是否确认删除该便签？", function(){
		    var url = contextPath+"/noteManage/del.action";
			var para = {sid:sid};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){					
				$.MsgBox.Alert_auto("删除成功！");
                window.location.reload();
			}   
	  });
}




/**
 * 新建/编辑
 */
function addOrUpdate(sid){
	var title="";
	var mess="";
	if(sid>0){
		title="新建便签";
		mess="新建成功！";
	}else{
		title="编辑便签";
		mess="编辑成功！";	
	}
	
	bsWindow(url ,title,{width:"600",height:"320",buttons:
		[
         {name:"保存",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}}); 
	
}


var timeOutEvent=0;//定时器   
//开始按   
function gtouchstart(sid){
    timeOutEvent = setTimeout("longPress("+sid+")",500);//这里设置定时器，定义长按500毫秒触发长按事件，时间可以自己改，个人感觉500毫秒非常合适   
    return false;   
};   
//手释放，如果在500毫秒内就释放，则取消长按事件，此时可以执行onclick应该执行的事件   
function gtouchend(sid){   
    clearTimeout(timeOutEvent);//清除定时器   
    if(timeOutEvent!=0){   
        //这里写要执行的内容（尤如onclick事件）   
    	window.location.href="addOrUpdatebianqian.jsp?sid="+sid;
    }   
    return false;   
};   
//如果手指有移动，则取消所有事件，此时说明用户只是要移动而不是长按   
function gtouchmove(){   
    clearTimeout(timeOutEvent);//清除定时器   
    timeOutEvent = 0;   
      
};   
   
//真正长按后应该执行的内容   
function longPress(sid){   
    timeOutEvent = 0;
    //执行长按要执行的内容，如弹出菜单
    	  if(window.confirm("确定删除吗?")){
		    	$.ajax({
					  type: 'POST',
					  url: "<%=contextPath%>/noteManage/del.action",
					  data: {sid:sid},
					  timeout: 10000,
					  async:false,
					  success: function(json){
						  //json = eval("("+json+")");
						  window.location.reload();
					  },
					  error: function(xhr, type){
					    //alert('服务器请求超时!');
					  }
					});
		    }
   
}   

</script>
<style>
.tb{
width:100%;
font-size:12px;
color:#999999;
margin-top:10px;
}
.tb_op{
text-align:right;
display:none;
}
.outline{
cursor:pointer;
margin:10px;
border:1px solid #e6ddde;
border-left:4px solid #e6ddde;
background:#ebe7e6;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline:hover{
	background:#f5f5f5;
}
.outline:hover .tb_op{
	display:block;
}
.outline_1{
margin:10px;
border:1px solid #f34b74;
border-left:4px solid #f34b74;
background:#feb9be;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_2{
margin:10px;
border:1px solid #c1e28b;
border-left:4px solid #c1e28b;
background:#d2f8bd;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_3{
margin:10px;
border:1px solid #ebdb83;
border-left:4px solid #ebdb83;
background:#fefacb;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_4{
margin:10px;
border:1px solid #a5cfe5;
border-left:4px solid #a5cfe5;
background:#bbe0fd;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_5{
margin:10px;
border:1px solid #e6ddde;
border-left:4px solid #e6ddde;
background:#ebe7e6;
font-size:12px;
padding:5px;
border-radius:5px;
}

.outline_6{
margin:10px;
border:1px solid #7b7a7a;;
border-left:4px solid #7b7a7a;
font-size:12px;
padding:5px;
border-radius:5px;
}

.outline_7{
margin:10px;
border:1px solid #1c96f7;
border-left:4px solid #1c96f7;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_8{
margin:10px;
border:1px solid #fbe52b;;
border-left:4px solid #fbe52b;;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_9{
margin:10px;
border:1px solid #9cf533;
border-left:4px solid #9cf533;
font-size:12px;
padding:5px;
border-radius:5px;
}
.outline_10{
margin:10px;
border:1px solid #f34b74;
border-left:4px solid #f34b74;
font-size:12px;
padding:5px;
border-radius:5px;
}
</style>
</head>
<body onload="doInit()" style="padding-left: 10px;padding-right: 10px">
	<header class="mui-bar mui-bar-nav">
			<span class="mui-icon mui-icon-back" onclick="CloseWindow()"></span>
			<h1 class="mui-title">个人便签</h1>
			<div style="float: right;color: #0070ffc7;margin-top: 12px;font-size: 26px;" id="addOrUpdatebianqian">+</div>
			
	</header>
 
	<div id="warpcontent" style="position:absolute;top:50px;left:0px;right:0px;bottom:0px;overflow:auto">
	</div>
	<iframe id="frame0" style="display:none"></iframe>
</body>
</html>
		        