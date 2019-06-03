<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	String customerId = request.getParameter("customerId")==null?"0":request.getParameter("customerId");
    String title="";
    if(sid>0){
    	title="编辑联系人";
    }else{
    	title="新建联系人";	
    }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<style>
#middlePopover {
	position: fixed;
	top: 100px;
	right: 200px;
	width: 200px;
}
#middlePopover .mui-popover-arrow {
	left: auto;
	right: 100px;
}
</style>
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">

    <button class="mui-btn mui-btn-link mui-btn-nav mui-pull-left" id="backBtn">
	    <span class="mui-icon mui-icon-left-nav" ></span>返回
	</button>
	<button class="mui-btn mui-btn-link mui-btn-nav mui-pull-right" onclick='save()'>
	    保存
	</button>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
		<!-- <div id="middlePopover" class="mui-popover">
				<ul class="mui-table-view" id="aaa">
					
				</ul>
		</div> -->
<form id="form1" name="form1">
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属客户</label>
		</div>
		<div class="mui-input-row">
			<!-- <a href="#middlePopover"  class="mui-action-menu mui-icon" style="line-height: 25px;font-size: 14px;padding-left: 10px;">选择所属客户</a> -->
			<!-- <input type="button" id="customerName" name="customerName" readonly value="选择所属客户"  onclick="selectCustomer()" style="width: 550px;line-height: 27px;text-align: left;"/> 
			 -->
			<input type="hidden" id="customerId" name="customerId"/>
			<input  id='customerName' name='customerName' type='text' readonly="readonly"/>
			<!-- <select id="customerName"  name="customerName" style="width: 550px;font-size: 14px;">
	       		<option value="0">选择所属客户</option>
	        </select> -->
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>名片</label>
			<label><a href="javascript:void(0)" onclick="doUploadPublicAttach()" >上传图片</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <div id="attachList" style="padding-left: 15px"></div>
		   <div id="upfileList" style="padding-left: 15px"></div>
		    
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>姓名</label>
		</div>
		<div class="mui-input-row">
            <input type="text" placeholder="姓名" name="contactName" id="contactName">
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属部门</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="所属部门" name="department"  id="department" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>职务</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="职务" name="duties"  id="duties"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>关键决策人</label>
		</div>
		<div class="mui-input-row">
		     <select id="isKeyPerson" placeholder="关键决策人"  name="isKeyPerson" style="width: 550px;font-size: 14px;">
	          		<option value="0">请选择</option>
					<option value="1">是</option>
					<option value="2">否</option>
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>电话</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="电话" name="telephone"  id="telephone"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>手机</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="手机" name="mobilePhone"  id="mobilePhone"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>邮箱</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="邮箱" name="email"  id="email" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>地址</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="地址" name="address"  id="address" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>出生日期</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='birthdayDesc' readonly="readonly" name='birthdayDesc' data-options='{"type":"date","beginYear":1949,"endYear":2028}' style="width: 550px" placeholder="出生日期" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>性别</label>
		</div>
		<div class="mui-input-row">
		     <select id="gender" placeholder="性别"  name="gender" style="width: 550px;font-size: 14px;">
	          		<option value='0'>男</option>
					<option value='1'>女</option>
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>公司名称</label>
		</div>
		<div class="mui-input-row" >
			<input type="text" placeholder="公司名称" name="companyName"  id="companyName" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>介绍人</label>
		</div>
		<div class="mui-input-row">
			<select id="introduceName"  name="introduceName" style="width: 550px;font-size: 14px;" >
	        </select>
			<!-- <input type="text" id="introduceName" name="introduceName" readonly placeholder="请选择介绍人" />-->
			<input type="hidden" id="introduceId" name="introduceId"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="remark" id="remark" placeholder="备注" ></textarea>
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var customerId = "<%=customerId%>";
var customerName = "<%=customerName%>";
var type= "<%=type%>";

function doInit(){
	
	$("#customerName").val(customerName);
	$("#customerId").val(customerId);

	//初始化联系人数据
	selectContacts();
	
	if(sid>0){
		getInfoBySid(sid);
	}
	
	birthdayDesc.addEventListener('tap', function() {
		//var picker = new mui.DtPicker({type:"date"});
	   var optionsJson = this.getAttribute('data-options') || '{}';
	   var options = JSON.parse(optionsJson);
	   var picker = new mui.DtPicker(options);
		picker.show(function(rs) {
			birthdayDesc.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
	
}

//选择介绍人
function selectContacts(){
	var url = contextPath+'/TeeCrmContactsController/selectAllContacts.action';
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var prcs = json.rtData;
					var options = "";
					for ( var i = 0; i < prcs.length; i++) {
						options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].contactName + "</option>";
					}
					$("#introduceName").append(options);
				return prcs;
			}else{
				alert("数据查询失败！");
			}
		}
	});
}

//上传公共附件
function doUploadPublicAttach(){
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='removePublicAttachImg(this)' /></p>").appendTo($("#upfileList"));
	});
}

//移除公共附件项
function removePublicAttachImg(obj){
	$(obj).parent().remove();
}


//根据主键获取详情
function getInfoBySid(sid){
	var url=contextPath+"/TeeCrmContactsController/getContactsInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				bindJsonObj2Cntrl(data);
				
				$("#introduceName").val(data.introduceId);
				$("#introduceId").val(data.introduceId);
				
				var  attachments = data.attachmodels;
				if(attachments.length > 0){
					 $.each(attachments, function(index, item){  
						 $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='delAttach("+item.sid+")' /><div>");
					 });
				}
				
			}else{
				alert("查询失败！");
			}
		}
	});
}

//删除附件
function delAttach(sid){
	if(confirm("是否确认删除该附件？")){
		var url=contextPath+"/attachmentController/deleteFile.action";
		var param={attachIds:sid};
		mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					alert("附件删除成功！");
					window.location.reload();
				}
			}
		});
		
		
	}
}


//验证
function check(){
	var contactName=$("#contactName").val();

	if(contactName==""||contactName==null||contactName=="null"){
		alert("请输入联系人姓名！");
		return false;	
	} 
	
	return true;
}




//保存/提交
function save(){
	if(check()&&checkContactsIsExist()){

		var introduceId = $("#introduceName").val();
		$("#introduceId").val(introduceId);
		
		var filesArray = [];//组装文件数据
		$(".img").each(function(i,obj){
			filesArray.push(obj.getAttribute("path"));
		});
		
		//先上传图片  
		UploadPhoto(function(files){
			var attachIds="";
			for(var i=0;i<files.length;i++){
				attachIds=attachIds+files[i].id+"," ;
			}

			var url=contextPath+"/TeeCrmContactsController/addOrUpdate.action";
			var param=formToJson("#form1");
			param['sid']=sid;
			param['attachmentSidStr']=attachIds;
			param["isPhone"]=1;
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
					if(sid>0){
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsDetail.jsp?sid="+sid+"&customerName="+customerName+"&type="+type+"&customerId="+customerId;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
					}
	               
				}
			}
		});
			
		},filesArray,"crmCustomerContacts","");//一开始modelid是没有的 因为主键还没生成呢
	
   }	
}

//判断此客户下联系人名称是否已经存在
function checkContactsIsExist(){
	var contactName=document.getElementById("contactName").value;
	var customerId = $("#customerId").val();
	var url=contextPath+"/TeeCrmContactsController/getContactsByName.action";
	var bool=false;
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		async:false,
		data:{contactName:contactName,customerId:customerId,sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				if(data==1){
					alert("新建失败，客户下已存在该联系人！");
					bool=false;
				}else if(data==0){
					bool=true;
				}else{
					bool=false;
				}
			}
		}
	});
	return bool;
	
}



mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(sid>0){
			history.go(-1);
		}else{
			window.location.href=contextPath+"/system/mobile/phone/crm/customer/customerContacts/contactsList.jsp?type="+type+"&customerId="+customerId+"&customerName="+customerName;
		}
	});

});


</script>

</body>
</html>