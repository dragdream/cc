<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/userheader.jsp" %>
<title>用户权限</title>


<script type="text/javascript" src="<%=contextPath %>/system/core/person/js/person.js"></script>



<script type="text/javascript">

function doInit(){
	var personObj = getPersonInfo(loginPersonId);
	if(personObj && personObj.uuid){
		 bindJsonObj2Cntrl(personObj);
		 var POST_PRIV = personObj.postPriv
		 if( POST_PRIV == '1'){
			 POST_PRIV =  "全体";
		}else if(POST_PRIV == '2'){
			POST_PRIV = personObj.postDeptStrName;
				
		}else if(POST_PRIV == '3'){
			POST_PRIV = "本部门及下级所有部门";
		}else{
			POST_PRIV = "本部门";
		}
		 $("#postDept").html(POST_PRIV);
		 
		 
		 var FOLDER_CAPACITY = personObj.folderCapacity;
		 
		 var EMAIL_CAPACITY = personObj.emailCapacity;
		 if(!FOLDER_CAPACITY || FOLDER_CAPACITY == "" || FOLDER_CAPACITY == 0){
			 
			 FOLDER_CAPACITY = "不限制";
		 }else{
			 FOLDER_CAPACITY = FOLDER_CAPACITY + "MB" ;
		 }
		 $("#FOLDER_CAPACITY").html(FOLDER_CAPACITY);
		 if(!EMAIL_CAPACITY || EMAIL_CAPACITY == "" || EMAIL_CAPACITY == 0){
			 EMAIL_CAPACITY = "不限制";
		 }else{
			 EMAIL_CAPACITY = EMAIL_CAPACITY + "MB" ;
		 }
		
		 $("#EMAIL_CAPACITY").html(EMAIL_CAPACITY);
		 
	}else{
		$.MsgBox.Alert_auto("没有相关人员！");
	}
}


/**
 * 保存
 */

 function savePersonDeskTop(){
 	if (check()){
 		var url = "<%=contextPath %>/personManager/updatePersonDeskTopType.action?updatePersonDeskTopType=8";
 		var para =  tools.formToJson($("#form1")) ;
 		var jsonRs = tools.requestJsonRs(url,para);
 		//alert(jsonRs);
 		if(jsonRs.rtState){
 			$.MsgBox.Alert_auto("保存成功！");
 			//$.jBox.tip("保存成功！");
 		}else{
 			$.MsgBox.Alert_auto(jsonRs.rtMsg);
 		//	alert(jsonRs.rtMsg);

 		}
 	}	
 }


function check() {

	return true;
  }
</script>

</head>
<body onload="doInit()" style="overflow:hidden;padding-left: 10px;padding-right: 10px;font-size:12px;">
<div id="toolbar" class="topbar clearfix" style="line-height:25px;border-bottom:1px solid #f2f2f2;border-bottom:2px solid #b0deff!important;background-color: #f8f8f8; ">
     <div class="fl" >
         <img class="title_img" src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_yhqx.png" alt="" />
         &nbsp;<span class="title">用户权限</span>
     </div>
</div>

	<form  method="post" name="form1" id="form1" >
<table class="TableBlock_page" width="95%" align="center">

            <tr>
                <td colspan="2" style='vertical-align: middle; font-weight: bold;' >
                    <img src="<%=contextPath%>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle"/>&nbsp;&nbsp; <span>用户别名</span>
                </td>
            </tr>
            <tr style="border:none">
                <td style="text-indent: 20px; width: 200px">用户名：<span style=""></span></td>
                <td id="userId"></td>
            </tr>
            <tr style="border:none">
                <td style="text-indent: 20px">用户别名：</td>
                <td> 
                    <input type="text" name="byname" id="byname" newMaxLength="30" />
                </td>
            </tr>
            <tr>
    <td style="text-indent: 20px;padding-left: 500px;" colspan="2">
        <input type="hidden" id="uuid" name="uuid"  value=""/>
        <input type="button" value="保存" class="btn-win-white" title="保存" onclick="savePersonDeskTop()" />&nbsp;&nbsp;
    </td>
        </tr>
</table>
  </form>
  <br/>
  
  
  <table class="TableBlock_page" width="95%" align="center">

   <tr>
    <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle"/>&nbsp;&nbsp; <span>用户部门、角色和管理范围</span></td>
   </tr>
   <tr style="border:none">
    <td style="text-indent: 20px;width:200px">用户部门：<span style=""></span></td>
    <td  id="deptIdName" >
    </td>
   </tr>
   <tr style="border:none">
    <td style="text-indent: 20px ">用户辅助部门：</td>
    <td  id="deptIdOtherStrName" > </td>
   </tr>
   <tr style="border:none">
    <td style="text-indent: 20px" >用户角色：</td>
    <td id="userRoleStrName" >
         </td>
   </tr> 
   <tr  style="border:none">
    <td style="text-indent: 20px" >用户辅助角色：</td>
    <td  id="userRoleOtherName" >    
 	</td>
   </tr>
   <tr>
    <td style="text-indent: 20px">管理范围：</td>
    <td  id="postDept" >
    
 	</td>
   </tr>
  
</table>
<br/>

<%-- 
  <table class="TableBlock_page" width="95%" align="center">

   <tr>
    <td colspan="2" style='vertical-align: middle;font-weight:bold;'><img src="<%=contextPath %>/system/frame/classic/imgs/icon/desktop/icon_mmxg.png" align="absMiddle">&nbsp;&nbsp; <span>用户使用权限</span></td>
   </tr>
   <tr>
    <td style="text-indent: 20px;width:200px">用户访问控制：<span style=""></span></td>
    <td id="" >
     	<input type="checkbox" name="notLogin" id="notLogin" value="1" disabled="disabled">禁止登录OA系统&nbsp;
    	<input type="checkbox" name="notViewUser" id="notViewUser" value="1"  disabled="disabled">禁止查看用户列表&nbsp;
    	<input type="checkbox" name="notViewTable" id="notViewTable" value="1" disabled="disabled">禁止显示桌面
        <input type="checkbox" name="notSearch" id="notSearch" value="1"  disabled="disabled">禁止OA搜索
 
      </td>
   </tr>
   
  <tr>
    <td style="text-indent: 20px">内部邮箱容量：<span style=""></span></td>
    <td id="EMAIL_CAPACITY" >
     
      </td>
   </tr>
  <tr>
    <td style="text-indent: 20px">个人文件柜容量：<span style=""></span></td>
    <td id="FOLDER_CAPACITY" >
     
      </td>
   </tr>
  
</table> --%>
<br/>
</body>
</html>