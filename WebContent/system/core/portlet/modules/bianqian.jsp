<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html >
<html>
<head>

<%@ include file="/header/header.jsp" %>
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<style type="text/css">
html,body{
	overflow:hidden;
}
textarea {
	  width:100%;
    height:98%;
	 position:absolute;
	 top:0;
	 left:0;
	/*  margin-left:10px; */
	 font-size:12px;background:#fcfdfd;border:0px
}

</style>
<script type="text/javascript">
var isClick= false;
function getNotes(){ 
	var url = contextPath + "/noteManage/selectPersonalNote.action";
	var para =  {} ;
	var jsonRs = tools.requestJsonRs(url,para);
	if(jsonRs.rtState){
		
		 var prcs = jsonRs.rtData;
		if(prcs.length > 0){
			var prc = prcs[0];
			$("#noteContent").val(prc.content);
			$("#sid").val(prc.sid);
			/* var calStr = "<ul class='' style='padding-top:5px;padding-left:5px;'>";
			for(var i = 0 ; i<prcs.length ; i++){
				var prc= prcs[i]; 
				var userName = prc.userName;
				calStr = calStr + "<li class=\"myModuleliItem\"> " + userName + " &nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"calendarDetail(" + prc.sid + ")\">" + prc.outDesc + "</a></li>";
			}
			calStr = calStr + "</ul>"; */
			
		}else{
			//calStr = "<div align='center' width='100px' height:='33px;'>暂无外出人员</div>";
		} 
		//$("#" + attendType).html(calStr);
	}else{
		//alert(jsonRs.rtMsg);
	} 

}



$(function () {
	getNotes();
});

function saveNote(){
	  var url = contextPath + "/noteManage/addOrUpdate.action";
	  var para = {};
	  para["content"] = $("#noteContent").val();
	  para["sid"] = $("#sid").val();
	  var jsonRs = tools.requestJsonRs(url,para);
	  if (jsonRs.rtState ) {
	    
	  }else{
	      alert("保存失败");
	  }
	}
	
	

/**
 * 查看日程信息
 */
function calendarDetail(id , CAL_AFF_TYPE)
{
	var url = contextPath + "/system/core/base/attend/out/detail.jsp?id=" + id;

	top.bsWindow(url ,"查看外出详情",{width:"600",height:"280",buttons:
		[
	 	 {name:"关闭",classStyle:"btn btn-primary"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="保存"){
		
		}else if(v=="关闭"){
			return true;
		}
	}});
}

function doInit(){
	$("#外出人员").layout({auto:true});
}

</script>
</head>
<body onload="doInit()" style="margin:0px;background-color:transparent;">

<div id="外出人员">
<textarea name="content" id="noteContent"  onblur="saveNote();" placeholder="在这里可以记录便签内容" style="background-color:transparent;" ></textarea>
<input type="hidden" id="sid" name="sid" value="0" >
</div>
</body>
</html>