<%@page import="com.tianee.webframe.util.global.TeeSysProps"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

Cookie cookie66 = TeeCookieUtils.getCookie(request, "skin_new");
String skinNew66 = "1";
if(cookie66!=null){
	skinNew66 = cookie66.getValue();
}

%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>Insert title here</title>
<link rel="stylesheet" href="css/onee.css">
<link rel="stylesheet" href="css/resett.css">
<%@ include file="/header/header2.0.jsp" %>
<script src="<%=contextPath %>/common/jquery/portlet/jquery-ui-1.9.2.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/frame/6/js/jquery.datePicker-min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=contextPath %>/system/frame/6/css/datepicker.css">
<script type="text/javascript" src="<%=contextPath%>/common/KinSlideshow/jquery.KinSlideshow-1.2.1.min.js"></script>
<title>主界面</title>

<link href="css/main<%=skinNew66 %>.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
function doinit(){
	var desktop ="";
	var json= tools.requestJsonRs(contextPath+"/teePortalTemplateUserDataController/getTemplateUserData.action");
	desktop=json.rtData.data;
	cols = json.rtData.cols;
	try{
		desktop = eval("("+desktop+")");
	}catch(e){
		return;
	}
	if(desktop!=null){
		for(var i=0;i<desktop.length;i++){
			var desk=desktop[i];
			if(desk!=null){
				for(var j=0;j<desk.length;j++){
					var itemId=desk[j].id;
					getContent(itemId,i)
				}
			}
		}
	}
}
function shuxin(itemId,m){
	$("#div_"+itemId).html("");
	tools.requestJsonRs(contextPath+"/portlet/renderPortlet.action",{sid:itemId},true,function(jsonRs){
    	if(jsonRs!=null){
    		var content=jsonRs.content;
    		$("#div_"+itemId).html(content);
	   }
	});
}
function deleteDiv(sid,m){
	 $("#four_"+sid).remove();
	 changeDesktop();
}
function getContent(sid,m){
		var html="<div class='four' pid='"+sid+"' id='four_"+sid+"'>";
		      html+="<div class='four-1'>";
		        html+="<div class='four-1-1' style='height:30px;width:30px'>";
		          html+="<img src='image/01.png' alt='' style='width: 100%;height: 100%'>";
		        html+="</div>";
		        html+="<div class='four-1-2'>";
	              html+="<span style='font-size: 16px' id='title"+sid+"'></span>";
                html+="</div>";
                html+="<div class='four-1-3' style='height:20px;width:20px'>";
                  html+="<img src='image/images/dw.png' alt='' style='width: 100%;height: 100%' onclick='deleteDiv("+sid+","+m+")'>";
                html+="</div>";
                html+="<div class='four-1-3' style='height:20px;width:20px'>";
                  html+="<img src='image/09.png' alt='' style='width: 100%;height: 100%' onclick='shuxin("+sid+","+m+")'>";
                html+="</div>";
              html+="</div>";
              html+="<div class='four-2'>";
              html+="<div class='erk'></div>";
              html+="<div class='bk'></div>";
              html+="<div id='div_"+sid+"' style='margin-top:30px;'></div>"
            html+=" </div></div>"
            if(m==0){
		        $("#oneFour").append(html);
            }else if(m==1){
            	$("#twoFour").append(html);
            }else{
            	$("#threeFour").append(html);
            }
            getContentInfo(sid);
            //$("#div_"+sid).html(content);
}
function getContentInfo(sid){
	tools.requestJsonRs(contextPath+"/portlet/renderPortlet.action",{sid:sid},true,function(jsonRs){
    	if(jsonRs!=null){
    		var content=jsonRs.content;
    		var title=jsonRs.title;
    		$("#div_"+sid).html(content);
    		$("#title"+sid).html(title);
	   }
		
	});
}
function changeDesktop(){
	var desktop = [[],[],[]];
	
	  var oneFourInput=$("#oneFour .four");
	  if(oneFourInput!=null){
		  for(var i=0;i<oneFourInput.length;i++){
			  var id=$(oneFourInput[i]).attr("pid");
			  desktop[0].push({id:id,rows:15});
		  }
	  }
	  var twoFourInput=$("#twoFour .four");
	  if(twoFourInput!=null){
		  for(var i=0;i<twoFourInput.length;i++){
			  var id=$(twoFourInput[i]).attr("pid");
			  desktop[1].push({id:id,rows:15});
		  }
	  }
	  var threeFourInput=$("#threeFour .four");
	  if(threeFourInput!=null){
		  for(var i=0;i<threeFourInput.length;i++){
			  var id=$(threeFourInput[i]).attr("pid");
			  desktop[2].push({id:id,rows:15});
		  }
	  }
	  desktop = tools.jsonArray2String(desktop);
	  var json = tools.requestJsonRs(contextPath+"/teePortalTemplateUserDataController/updateTemplateUserData.action",{desktop:desktop},true);
}
</script>
</head>
<body onload="doinit();">
<div class="body1">
    <div id="ft">
        <div class="header-right">
            <div class="f-four f-header" id="oneFour">
            </div>
            <!-- 第二部分 -->
            <div class="f-four f-center" id="twoFour"><!--两个二-->
            </div>
            <!-- 第三部分 -->
            <div class="f-four f-footer" id="threeFour">
            </div>
        </div>
    </div>
</div>
<style>
</style>
<script type="text/javascript">
    $(function () {
            $(".f-four").sortable({
                connectWith: ".f-four",
                opacity: .6,
                tolerance: "pointer",
                helper: "clone",
                stop : function( event, ui ) {
              	  changeDesktop();
                }
            });



    });


</script>

</body>
</html>