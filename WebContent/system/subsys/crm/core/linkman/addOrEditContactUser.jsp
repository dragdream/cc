<%@page import="com.tianee.oa.oaconst.TeeAttachmentModelKeys"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
    String model = TeeAttachmentModelKeys.CRM_CUSTOMER_CONTACTS;
    String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<%@ include file="/header/upload.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=contextPath%>/common/js/layout/layout.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/system/subsys/crm/js/crm.js"></script>
<style>
	td{
		line-height:28px;
		min-height:28px;
	}
.ztree{
		background:white;
		border:1px solid #f0f0f0;
	}
</style>

<script>
var sid = "<%=sid%>";
var customerName = <%=customerName%>;
function doInit(){
	if( sid > 0){
		var url = contextPath+"/TeeCrmContactsController/getContactsInfoBySid.action?sid="+sid;
		var json = tools.requestJsonRs(url);
		if(json.rtState){
			var prc = json.rtData;
			bindJsonObj2Cntrl(prc);
			
			//附件
			var  attachmodels = prc.attachmodels;
			for(var i=0;i<attachmodels.length;i++){
				var fileItem = tools.getAttachElement(attachmodels[i]);
				$("#attachments").append(fileItem);
			}
		}else{
			$.MsgBox.Alert_auto(json.rtMsg);
		}
	}
	doInitUpload();

}

function selectCustomer(){
	var url = contextPath+"/system/subsys/crm/core/customer/query.jsp";
	  dialogChangesize(url, 860, 500);
}

var contactsArray = null;
/**
 * 查询联系人信息 --绑定对象
 * @return
 * retArray:表单文本框数组 
 * @param callBackFunc 回调方法参数 
 */
function selectContacts(retArray ,callBackFunc) {
	contactsArray = retArray;
	var url = contextPath+"/system/subsys/crm/core/linkman/query.jsp?type= 0";
    if(callBackFunc){
	   url += "?callBackFunc=" + callBackFunc;
    }
    dialogChangesize(url, 900, 500);
}


/**
 * 初始化附件上传
 */
function doInitUpload(){
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attachmentSidStr",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		uploadSuccess:function(file){
			var url=contextPath+"/TeeCrmContactsController/ScanCard.action";
		    var json=tools.requestJsonRs(url,{attachId:file.sid});
		    if(json.rtState){
 				var data=json.rtData;
 				if(data!=null&&data!="null"){
 					var jsonObj=tools.string2JsonObj(data);
 	 				var addrArray=jsonObj.addr;
 	 				var emailArray=jsonObj.email;
 	 				var name=jsonObj.name;
 	 				var tellArray=jsonObj.tel_cell;
 	 				var workArray=jsonObj.tel_work;
 	 				var titleArray=jsonObj.title;
 	 				if(addrArray!=null&&addrArray.length>0){
 	 					$("#address").val(addrArray[0]);
 	 				}
 	                if(emailArray!=null&&emailArray.length>0){
 	                	$("#email").val(emailArray[0]);
 	 				}
 	                if(name!=""&&name!=null&&name!="null"){
 	                	$("#contactName").val(name);
 	                }
 	                if(tellArray!=null&&tellArray.length>0){
 	 					$("#mobilePhone").val(tellArray[0]);
 	 				}
 	                if(workArray!=null&&workArray.length>0){
 	 					$("#telephone").val(workArray[0]);
 	 				}
 	                if(titleArray!=null&&titleArray.length>0){
 	 					$("#duties").val(titleArray[0]);
 	 				}
 				}
 				
               
 			}
		    
		},
		renderFiles:true,//渲染附件
		post_params:{model:"<%=model%>"}//后台传入值，model为模块标志
	});
}


/**
 * 保存
 */
function save(status){
	var url="";
	//编辑客户
	if(sid>0){
		if(check()){
		    url=contextPath+"/TeeCrmContactsController/addOrUpdate.action?sid="+sid;
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
			    if(json.rtState){
			    	  parent.$.MsgBox.Alert_auto(json.rtMsg);
				        window.location.href=contextPath+"/system/subsys/crm/core/linkman/contactsInfo.jsp?sid="+sid+"&customerName=<%=customerName%>";
				        opener.datagrid.datagrid("unselectAll");
						opener.datagrid.datagrid('reload');
			    
			    };	
		}
	}else{//添加联系人
		if(check()&&checkContactsIsExist()){
			url=contextPath+"/TeeCrmContactsController/addOrUpdate.action";
		    var param=tools.formToJson("#form1");
		    var json=tools.requestJsonRs(url,param);
		    if(json.rtState){
		    	     parent.$.MsgBox.Alert_auto(json.rtMsg,function(){
			         window.location.href=contextPath+"/system/subsys/crm/core/linkman/index.jsp";
		    	 });
		    	 };	
		}
	    
	};
	
}

/**
 * 验证
 */
function check(){
	if($("#customerId").val()=="" || $("#customerId").val()=="null" || $("#customerId").val()==null){
		$.MsgBox.Alert_auto("请选择所属客户！");
		return false;
	}
	if($("#contactName").val()=="" || $("#contactName").val()=="null" || $("#contactName").val()==null){
		$.MsgBox.Alert_auto("请输入联系人姓名！");
		return false;
	}
	return true;
}

//判断此客户下联系人名称是否已经存在
function checkContactsIsExist(){
	var contactName=document.getElementById("contactName").value;
	var customerId = $("#customerId").val();
		var url=contextPath+"/TeeCrmContactsController/getContactsByName.action";
		var json=tools.requestJsonRs(url,{contactName:contactName,customerId:customerId});
		if(json.rtState){
			var data=json.rtData;
			if(data==1){
				$.MsgBox.Alert_auto("新建失败，客户下已存在该联系人！");
				return false;
			  }else if(data==0){
					return true;
				}else{
					return false;
				}
			}
}

function getContactUerList(customerId){
	var url = "<%=contextPath%>/TeeCrmContactUserController/getContactUserList.action?customerId="+customerId;
	var json = tools.requestJsonRs(url);
	if(json.rtState){
		var contactUserList = json.rtData;
		$.each( contactUserList, function(i, item){
			addContactUser(item);
		});
	}
}


function goBack(){
	var url="";
	if(sid>0){
		history.go(-1);
		<%-- url= contextPath+"/system/subsys/crm/core/linkman/contactsInfo.jsp?sid="+sid+"&contactName =<%=contactName%>"; --%>
	}else{
	    url= contextPath+"/system/subsys/crm/core/linkman/index.jsp";
	}
	location.href=url;
}

</script>
</head>
<body onload="doInit();" style="padding-left: 10px;padding-right: 10px">
<div class="topbar clearfix" id="toolbar">
   <div class="fl" style="position:static;">
		<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/crm/img/icon_tjlxr.png">
		<span class="title">新增/编辑联系人</span>
	</div>
   <div class="fr right">
      <input type="button" value="保存" class="btn-win-white" onclick="save(1);"/>
     <!--  <input type="button" value="提交" class="btn-win-white" onclick="save(2);"/> -->
      <input type="button" value="返回" class="btn-win-white" onclick="goBack();"/>
   </div>
</div>

<form method="post" name="form1" id="form1" >
	<table class="TableBlock_page" width="60%" align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">基本信息</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">
				所属客户<span style="color:red;font-weight:bold;">*</span>：
			</td>
			<td>
				<input style="height: 23px;width: 350px;" id='customerName' name='customerName' class="BigInput" type='text' readonly="readonly"/>
				<input id='customerId' name='customerId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectCustomerInfo(['customerId','customerName'])">选择客户</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('customerId','customerName')">清空</a>
			</td>
		</tr>
		<tr>
           <td class="TableData" width="150" style="text-indent:15px"> 名片：</td>
		    <td class="TableData">
		       	<span id="attachments"></span>
					<input type="hidden" id="dbAttachSid" name="dbAttachSid" value="">
	
					<div id="fileContainer2"></div>
					<a id="uploadHolder2" class="add_swfupload">
						<img src="/common/images/upload/batch_upload.png"/>上传图片
					</a>
					<span style="color: red;">（支持jpg、gif、png格式的图片，最多可添加20张）</span>
					<input id="attachmentSidStr" name="attachmentSidStr" type="hidden"/>
		    </td>

        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">姓名<span style="color:red;font-weight:bold;">*</span>：</td>
		   <td class="TableData">
				<input type="text" id="contactName" name="contactName" class="BigSelect"></select>
		   </td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">所属部门：</td>
			<td>
				<input type="text"  id="department" name="department" class="BigInput"/>
			</td>
        </tr>
        <tr>
          <td class="TableData" width="150" style="text-indent:15px">职务：</td>
			<td>
				<input type="text"  id="duties" name="duties" class="BigInput"/>
			</td>
        </tr>
        <tr>
	        <td class="TableData" width="150" style="text-indent:15px">关键决策人：</td>
			<td class="TableData">
				<select id="isKeyPerson" name="isKeyPerson" class="BigSelect" style="height: 25px;width: 100px;">
				    <option value="0">请选择</option>
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
			</td>
	   </tr>
	 </table>
	<table class='TableBlock_page' align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">联系方式</B>
		   </td>
	   </tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">电话：</td>
			<td>
				<input type="text" class="BigInput" name="telephone" id="telephone" validType='maxLength[15]' />
			</td>
		</tr>
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">手机：</td>
			<td>
				<input type="text" class="BigInput" name="mobilePhone" id="mobilePhone" validType='mobile'/>
			</td>
		</tr >
		<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">邮箱：</td>
			<td>
				<input type="text" name="email" id="email"  class="BigInput" validType='email'> 
			</td>
		</tr>
			<tr class='TableData' align='left'>
			<td class="TableData" width="150" style="text-indent:15px">地址：</td>
			<td>
				<input type="text" name="address" id="address"  class="BigInput"> 
			</td>
		</tr>
	</table>
		<table class='TableBlock_page' align="center">
		<tr>
		   <td class=TableHeader colSpan=2 noWrap>
		       <img src="<%=contextPath %>/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" alt="" style="vertical-align:middle;"/>
		       <B style="color: #0050aa">附加信息</B>
		   </td>
	   </tr>
	   <tr>
	   	<td class="TableData" width="150" style="text-indent:15px">出生日期：</td>
			<td>
				<input type="text" id='birthdayDesc' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" name='birthdayDesc' class="Wdate BigInput" />
			</td>
	   </tr>
	   <tr>
	       <td class="TableData" width="150" style="text-indent:15px">性别：</td>
			<td>
				<select id="gender" name="gender" class="BigSelect" style="height: 25px;width: 100px;">
					<option value='0'>男</option>
					<option value='1'>女</option>
				</select>
			</td>
	   </tr>
	   <tr>
	       <td class="TableData" width="150" style="text-indent:15px">公司名称：</td>
	       <td>
				<input type="text" name=companyName id="companyName"  class="BigInput"> 
			</td>
	   </tr>
	   <tr>
	      	<td class="TableData" width="150" style="text-indent:15px">介绍人：</td>
			<td>
				<input id='introduceName' name='introduceName' class="BigInput" type='text' readonly="readonly"/>
				<input id='introduceId' name='introduceId' class="BigInput" type='hidden'/>
				<a href="javascript:void(0);" class="orgAdd" onClick="selectContacts(['introduceId','introduceName'])">选择介绍人</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="orgClear" onClick="clearData('introduceId','introduceName')">清空</a>
			</td>
	   </tr>
	   <tr>
	       <td class="TableData" width="150" style="text-indent:15px">备注：</td>
			<td>
				<textarea id="remark" name="remark" class="BigTextarea" cols='60' rows='6'>
				</textarea>
			</td>
	   </tr>

	</table>
</form>
</body>
</html>