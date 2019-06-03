<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>

<%
int sid = TeeStringUtil.getInteger(request.getParameter("sid"), 0);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp" %>
<title>图书信息录入</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>

<script type="text/javascript">
var oldAvatarId = 0;
var sid=<%=sid%>;
function doInit()
{
	bookType();
	if(sid > 0){
		getBookInfoById(sid);
	}
}


/**
 * 新建或者更新
 */
function doSaveOrUpdate(){
	if(checkFrom()){
		var url = contextPath + "/bookManage/addOrUpdate.action";
		var para =  tools.formToJson($("#form1")) ;
		//var json = tools.requestJsonRs(url, para);
		
		para['model'] = 'book';
		$("#form1").doUpload({
			url:url,
			success:function(json){
				alert(json.rtMsg);
				location.reload();
				return true;
			},
			post_params:para
		}); 
	}
	doClose();
	return false;
}
function doClose(){
	window.close();
}
/**
 * 校验
 */
 
function checkFrom(){
	 var url =   "<%=contextPath%>/bookManage/checkBookNo.action";
	 var para = {bookNo : $("#bookNo").val(),sid:'${sidStr}'};
	 var jsonObj = tools.requestJsonRs(url, para);
	 if(jsonObj.rtData==1 && sid==0){
		 alert("该图书编号已经存在！");
         return false; 
	 }
	 if($("#bookTypeId").val()==""){
		 alert("图书类别不能为空，请先录入图书类别！");
		 return false; 
	 }
   /*   if($("#avatarFile").val()==""){
         return checkSuppotPictureFile('avatarFile');
     } */
	/*  var check = $("#form1").form('validate'); 
	 if(!check  ){
		 return false; 
	 } */
	 return true;
}

/**
 * 获取外出 by Id
 */
function getBookInfoById(id){
	var url =   "<%=contextPath%>/bookManage/getBookInfoById.action";
	var para = {sid : id};
	var jsonObj = tools.requestJsonRs(url, para);
	if (jsonObj.rtState) {
		var prc = jsonObj.rtData;
		if (prc && prc.sid) {
			bindJsonObj2Cntrl(prc);
			$("#ISBN").val(prc.isbn);
	        if(prc.avatarId){
	            $("#avatar").val(prc.avatarId);
	            oldAvatarId = prc.avatarId;
	            $("#avatarShow").show();
	            //获取附件
	            var url = "<%=contextPath%>/attachmentController/downFile.action?id=" + prc.avatarId + "&model=book";
	            $("#avatarImg").attr("src",url);
	        }
		}
	} else {
		alert(jsonObj.rtMsg);
	}
}

function toReturn(){
    window.location = contextPath+"/bookManage/bookQuery.action";
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
                //$.jBox.tip("删除图像成功！",'info',{timeout:1500});
            }else{
                alert(json.rtMsg);
            }
        }
        
    }else{
         $("#avatar").val("");
         $("#avatarShow").hide();
    }

}
function bookType(){
	var url = "<%=contextPath%>/bookManage/bookType.action";
	var jsonRs = tools.requestJsonRs(url);
	var html="<option value='all'>全部</option>";
	if(jsonRs.rtState){
		var data=jsonRs.rtData;
		console.info(data);
		for(var i=0;i<data.length;i++){
			html+="<option value='"+data[i].sid+"'>"+data[i].typeName+"</option>";
		}
		$("#bookTypeId").html(html);
	}
}
</script>

</head>
<body onload="doInit();">
<form action=""  method="post" name="form1" id="form1" style="background-color: #f4f4f4;">

<table align="center" width="80%" class="TableBlock">
		<tr>
		    <td nowrap class="TableData">部门：</td>
		    <td class="TableData" colspan="3">
		   		 <input type="hidden" name="createDeptId" id="createDeptIds" value="">
		  		 <input  name="createDeptName" id="createDeptNames" rows=2 class="BigStatic BigInput"  readonly />
		  		 <a href="javascript:void(0);" class="orgAdd" onClick="selectSingleDept(['createDeptIds', 'createDeptNames']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('createDeptIds','createDeptNames');">清空</a>
				&nbsp;（<i>说明：图书管理员只能审批管理人所管部门的图书申请。</i>）
		    </td>
		</tr>
	  <tr>
	    <td nowrap class="TableData"> 图书名称：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="bookName" id="bookName"  maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	    <td nowrap class="TableData"> 图书编号：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="bookNo" id="bookNo"  maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData" width="50" >图书类别：<font style='color:red'>*</font></td>
	    <td class="TableData"   >
	    	<select id="bookTypeId" name="bookTypeId" class="BigSelect" required="true">
    		   <%--  <c:forEach items="${bookTypeList}" var="bookTypeListSort" varStatus="bookTypeListStatus">
			        <option value="${bookTypeList[bookTypeListStatus.index].sid}">${bookTypeList[bookTypeListStatus.index].typeName}</option>
				</c:forEach> --%>
       		 </select>
	     </td>
	     <td nowrap class="TableData" width="50" >借阅状态：<font style='color:red'>*</font></td>
	    <td class="TableData"   >
	    	<!-- <select id="lend" name="lend" class="BigSelect">
          		<option value="0">未借出</option>
         		<option value="1">已借出</option>
       		 </select> -->
       		 <input type="hidden" id="lend" name="lend" value="0"/>
       		 <input type="text" readonly="readonly" id="lendStr" name="lendStr" value="未借出"/>
	     </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData"> 图书作者：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="author" id="author"  maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	    <td nowrap class="TableData"> ISBN号：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="ISBN" id="ISBN"  maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	  </tr>
	  <tr>
	    <td nowrap class="TableData"> 出版社：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="pubHouse" id="pubHouse"  maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	    <td nowrap class="TableData" width="70"> 出版日期：</td>
		    <td class="TableData" >
		    	<input type="text" name="pubDateStr" id="pubDateStr" size="15" class="BigInput  easyui-validatebox" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="">
		    </td>
	  	</tr>
   	  	<tr>
	    <td nowrap class="TableData"> 存放地点:</td>
	    <td class="TableData" >
	      <input type="text" name="area" id="area"  maxlength="300" class="BigInput easyui-validatebox" value="" required="true">
	    </td>
	    <td nowrap class="TableData"> 数量：<font style='color:red'>*</font></td>
	    <td class="TableData" >
	      <input type="text" name="amt" id="amt" size="15" maxlength="300" class="BigInput easyui-validatebox" value="" required="true" validType ='positivIntege[]'>
	    </td>
	    </tr>
	    <tr>
	    <td nowrap class="TableData"> 价格：<font style='color:red'>*</font></td>
	    <td class="TableData" colspan="3">
	      <input type="text" name="price" id="price" size="15" maxlength="14" class="BigInput easyui-validatebox" value="" required="true" validType ='pointTwoNumber[]'>元
	    </td>
	  </tr>
	  <tr>
       <td nowrap class="TableData" colspan="1"><span id="ATTACH_LABEL">内容简介：</span></td>
       <td class="TableData" colspan="3">
           <textarea name="brief" id="brief" class="BigTextarea"  cols="60" rows="2"></textarea>
		   <input type="hidden" name="sid" id="sid" value="${sid }">
 	   </td>
     </tr>
		<tr>
		    <td nowrap class="TableData">借阅范围（部门）：</td>
		    <td class="TableData" colspan="3">
		   		 <input type="hidden" name="postDeptIds" id="postDeptIds" value="">
		  		 <textarea cols=50 name="postDeptNames" id="postDeptNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
		  		 <a href="javascript:void(0);" class="orgAdd" onClick="selectDept(['postDeptIds', 'postDeptNames']);">添加</a>
				<a href="javascript:void(0);" class="orgClear" onClick="$('#postDeptIds').val('');$('#postDeptNames').val('');">清空</a>
		    </td>
		</tr>
    <tr>
      <td nowrap class="TableData">借阅范围（角色）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="postUserRoleIds" id="postUserRoleIds" value="">
	      <textarea cols=50 name="postUserRoleNames" id="postUserRoleNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectRole(['postUserRoleIds', 'postUserRoleNames']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserRoleIds').val('');$('#postUserRoleNames').val('');">清空</a>
      </td>
   </tr>
   <tr>
      <td nowrap class="TableData">借阅范围（人员）：</td>
      <td class="TableData" colspan="3">
        <input type="hidden" name="postUserIds" id="postUserIds" value="">
	      <textarea cols=50 name="postUserNames" id="postUserNames" rows=2 class="BigStatic BigTextarea" wrap="yes" readonly></textarea>
	      <a href="javascript:void(0);" class="orgAdd" onClick="selectUser(['postUserIds', 'postUserNames']);">添加</a>
	      <a href="javascript:void(0);" class="orgClear" onClick="$('#postUserIds').val('');$('#postUserNames').val('');">清空</a>
      </td>
   </tr> 
	<tr>
       <td nowrap class="TableData" colspan="1"><span id="ATTACH_LABEL1">备注：</span></td>
       <td class="TableData" colspan="3">
           <textarea name="memo" id="memo" class="BigTextarea"  cols="60" rows="2"></textarea>
 	   </td>
     </tr>
    <tr id="avatarUpload" >
      <td nowrap class="TableData" valign="top"> 上传封面：</td>
      <td class="TableData" id="" colspan="3" >
          <input type='hidden' id="avatar" name="avatar"  value=""/>
          <input type="file" name="avatarFile" id="avatarFile" size="30" class="BigInput" title="选择图片文件" value="">
          <br><span id="avatarDesc"></span>
      </td>
    </tr>
    <tr id="avatarShow" style="display:none;">
      <td nowrap class="TableData" valign="top"> 封面：</td>
      <td class="TableData" colspan="3">
        <img id="avatarImg" width="" height="" ></img>
        <input type="button" class="btn btn-primary" onclick="deleteAvatar(0)" value="删除"/>
      </td>
    </tr>
  </table>
</form>	
</body>
</html>
