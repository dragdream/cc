<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<%@ include file="/header/validator2.0.jsp"%> 
<%@ include file="/header/upload.jsp" %>
<script type="text/javascript" src="<%=contextPath %>/common/fullAvatarEditor/scripts/swfobject.js"></script>
<script type="text/javascript" src="<%=contextPath %>/common/fullAvatarEditor/scripts/fullAvatarEditor.js"></script>
<title>昵称头像</title>

<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>

<script type="text/javascript">
var oldAvatarId = 0;
var AVATAR_WIDTH = "<%=TeeSysProps.getString("AVATAR_WIDTH")%>";
var AVATAR_HEIGHT = "<%=TeeSysProps.getString("AVATAR_HEIGHT")%>";
function doInit(){
	var personObj = getPersonInfo(loginPersonId);
	if(personObj && personObj.uuid){
		// bindJsonObj2Cntrl(personObj);
		 $("#nickName").val(personObj.nickName);
		 if(personObj.avatar){
			 $("#avatar").val(personObj.avatar);
			 $("#avatarUpload").hide();
			 oldAvatarId = personObj.avatar;
			 $("#avatarShow").show();
			 //获取附件
			 var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + personObj.avatar + "&model=person";
			 $("#avatarImg").attr("src",url);
		 }
	}else{
		$.MsgBox.Alert_auto("没有相关人员!");
	}
	
// 	var AVATAR_RULE = "";
// 	if(AVATAR_WIDTH > 0 && AVATAR_HEIGHT > 0){
// 		AVATAR_RULE = "大小不能超过" + AVATAR_WIDTH + "*" + AVATAR_HEIGHT;
// 	}
// 	var avatarDesc = "头像文件要求是" + suppotPictureFileType.toString()  +  "格式，" + AVATAR_RULE;
// 	$("#avatarDesc").html(avatarDesc);
}


function deleteAvatar(type){
	var avatarId =  $("#avatar").val();
	if(type == 1){//物理删除
		if(oldAvatarId > 0 && avatarId == ""){
			 $("#avatar").val("");
			 $("#avatarShow").hide();

			 var url = contextPath+"/attachmentController/deleteFile.action";
			var para = {attachIds:oldAvatarId};
			var json = tools.requestJsonRs(url,para);
			if(json.rtState){
				$("#avatar").val("");
				$("#avatarShow").hide();
				$("#avatarUpload").show();
			}else{
				$.MsgBox.Alert_auto(json.rtMsg);
			}
		}
		
	}else{
		 $("#avatar").val("");
		 $("#avatarShow").hide();
		 $("#avatarUpload").show();
	}

}

function checkForm(){
    var check= $("#form1").valid(); 
    if(!check){
    	return false;
    }
    return true;
}
/* function check() {
	if($("#avatarFile").val()){
		return checkSuppotPictureFile('avatarFile');
	}
	return true;
} */

/**
 * 保存
 */
function submitForm(){
	if(checkForm()){
		
		deleteAvatar(1);
		var para =  tools.formToJson($("#form1")) ;
		var  url = "<%=contextPath %>/personManager/updateAvatar.action";


		 $("#form1").ajaxSubmit({
			  url :url,
	          iframe: true,
	          data: para,
	          type:"post",
	          success: function(res) {
			 		//callback(res);
			 		if(res.rtState){
			 			$.MsgBox.Alert_auto("保存成功！");
			 			//$.jBox.tip("保存成功！" , 'info', {timeout:1500});
			 		}else{
			 			$.MsgBox.Alert_auto(res.rtMsg);
			 		}
	            },
	          error: function(arg1, arg2, ex) {
	                // ... my error function (never getting here in IE)
	              $.MsgBox.Alert_auto("保存错误!");
	          },
	          dataType: 'json'});
	}
}

</script>

</head>
<body onload="doInit()" style="margin-left: 10px;margin-right: 10px;">
<div id="toolbar" class="topbar clearfix">
   <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_ncytx.png" alt="" />
   &nbsp;<span class="title">用户昵称与图象</span>
</div>
<form enctype="multipart/form-data" action=""  method="post" name="form1" id="form1">
  <table class="TableBlock_page" width="90%" align="center">
    <tr>
      <td width="180px;" style="text-indent: 10px"> 昵称（用于讨论区交流等）：</td>
      <td >
        <input type="text" name="nickName" id="nickName" size="25" maxlength="25" onblur="" class="BigInput" value="">
      </td>
    </tr>
    <tr id="avatarUpload" >
      <td  valign="top" style="text-indent: 10px"> 上传头像：</td>
      <td >
      <div id="swfContainer">
      </div>
<!--       	  <input type='hidden' id="avatar" name="avatar"  value=""/> -->
<!--           <input type="file" name="avatarFile" id="avatarFile" size="30" class="BigInput" title="选择图片文件" value=""> -->
<!--       	  <br><span id="avatarDesc"></span> -->
		
      </td>
    </tr>
    <tr id="avatarShow" style="display:none;">
      <td  valign="top" style="text-indent: 10px"> 头像：</td>
      <td >
        <img id="avatarImg" ></img>
        <input id="avatar" name="avatar" type="hidden"/>
        <input type="button" class="btn-del-red" onclick="deleteAvatar(0)" value="删除"/>
      </td>
    </tr>
    <tr id="photoUpload"  style="display:none;"> 
      <td valign="top" style="text-indent: 10px">上传照片：</td>
      <td >
        <div id="upload">
        
          <input type="file" name="photo" id="photo" size="30"  title="选择附件文件" value="">
          <br><span id="photoDesc"></span>
        </div>
      </td>
    </tr>
    <tr id="photoShow" style="display:none;">
      <td  valign="top" style="text-indent: 10px"> 照片：</td>
      <td class="TableData">
      	
        <img id="photoImg"></img><input type="button" class="btn-del-red" onclick="deletePhoto()" value="删除"/>
      </td>
    </tr>
   
    <tr >
      <td colspan="2"  style="text-indent: 20px;padding-left: 500px;">
        <input type="button" value="保存并修改" class="btn-win-white" onclick="submitForm()">&nbsp;&nbsp;
      </td>
    </tr>
  </table>
</form>

  <br>
<script type="text/javascript">
    swfobject.addDomLoadEvent(function () {
	var swf = new fullAvatarEditor(contextPath+"/common/fullAvatarEditor/fullAvatarEditor.swf", contextPath+"/common/fullAvatarEditor/expressInstall.swf", "swfContainer", {
		    id : 'swf',
			upload_url : contextPath+'/avatarUploadController/upload.action',	//上传接口
			method : 'post',	//传递到上传接口中的查询参数的提交方式。更改该值时，请注意更改上传接口中的查询参数的接收方式
			src_upload : 0,		//是否上传原图片的选项，有以下值：0-不上传；1-上传；2-显示复选框由用户选择
			avatar_box_border_width : 0,
			avatar_sizes : AVATAR_WIDTH+'*'+AVATAR_HEIGHT,
			avatar_sizes_desc : AVATAR_WIDTH+'*'+AVATAR_HEIGHT+"像素"
		}, function (msg) {
			if(msg.code==5){//上传成功，则将头像赋值给头像输入框
				var sid = msg.content.sid;
				$("#avatarShow").show();
				$("#avatarImg").attr("src",contextPath+"/attachmentController/downFile.action?id="+sid);
				$("#avatar").val(sid);
				$("#avatarUpload").hide();
			}
		}
	);
	document.getElementById("upload").onclick=function(){
		swf.call("upload");
	};
});
</script>

</body>
</html>