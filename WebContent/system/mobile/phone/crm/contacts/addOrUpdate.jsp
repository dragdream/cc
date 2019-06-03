<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int sid=TeeStringUtil.getInteger(request.getParameter("sid"), 0);
	String customerName = TeeStringUtil.getString(request.getParameter("customerName"), null);
	int type=TeeStringUtil.getInteger(request.getParameter("type"),1);//默认查询全部
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
<script type="text/javascript" src="<%=contextPath%>/system/mobile/js/tools.js"></script>
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
.shadow{
	position:absolute;
	top:0;
	left:0;
	right:0;
	bottom:0;
	background-color:rgba(0,0,0,0.6);
	z-index:2;
	display:none;
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
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">基本信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>所属客户</label>
	         <input type="text" id="customerName" name="customerName" onclick="selectCustomer();"  placeholder="请选择所属客户"/>
	        <input type="hidden" id="customerId" name="customerId"/>
	        <iframe id="iframe1" src="" 
		  	style="display:none;border: none;position: fixed;left: 10%;width:80%;
		  	top: 30%;height:40%;z-index: 10;" scrolling="yes"></iframe>
		</div>
	
		<div class="mui-input-row">
			<label>名片</label>
			<label><a href="javascript:void(0)" onclick="doUploadPublicAttach()" >上传图片</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <div id="attachList" style="padding-left: 15px"></div>
		   <div id="upfileList" style="padding-left: 15px"></div>
		    
		</div>
	
		<div class="mui-input-row">
			<label>姓名</label>
			<input type="text" placeholder="请输入姓名" name="contactName" id="contactName">
		</div>
	
		<div class="mui-input-row">
			<label>所属部门</label>
			<input type="text" placeholder="请输入所属部门" name="department"  id="department" />
		</div>
	
		<div class="mui-input-row">
			<label>职务</label>
			<input type="text" placeholder="请输入职务" name="duties"  id="duties"/>
		</div>
			
	
		<div class="mui-input-row">
			<label>关键决策人</label>
			<select id="isKeyPerson" placeholder="关键决策人"  name="isKeyPerson" style="margin-left:0px;font-size: 14px;">
	          		<option value="0">请选择</option>
					<option value="1">是</option>
					<option value="2">否</option>
	         </select>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">联系方式</span></label>
		</div>
		<div class="mui-input-row">
			<label>电话</label>
			<input type="text" placeholder="请输入电话" name="telephone"  id="telephone"/>
		</div>
		<div class="mui-input-row">
			<label>手机</label>
			<input type="text" placeholder="请输入手机" name="mobilePhone"  id="mobilePhone"/>
		</div>
		<div class="mui-input-row">
			<label>邮箱</label>
			<input type="text" placeholder="请输入邮箱" name="email"  id="email" />
		</div>
		<div class="mui-input-row">
			<label>地址</label>
			<input type="text" placeholder="请输入地址" name="address"  id="address" />
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label><img src="/common/zt_webframe/imgs/zsjl/ggtxb/icon_fenzu.png" style="vertical-align: middle;">&nbsp;<span style="font-weight: bold;">附加信息</span></label>
		</div>
		<div class="mui-input-row">
			<label>出生日期</label>
			<input type="text" id='birthdayDesc' readonly="readonly" name='birthdayDesc'  data-options='{"type":"date","beginYear":1949,"endYear":2028}'  placeholder="请输入出生日期" />
		</div>
		<div class="mui-input-row">
			<label>性别</label>
			<select id="gender" placeholder="性别"  name="gender" style="margin-left:0px;font-size: 14px;">
	          		<option value='0'>男</option>
					<option value='1'>女</option>
	         </select>
		</div>
		<div class="mui-input-row">
			<label>公司名称</label>
			<input type="text" placeholder="请输入公司名称" name="companyName"  id="companyName" />
		</div>
		<div class="mui-input-row">
			<label>介绍人</label>
			<select id="introduceName"  name="introduceName" style="margin-left:0px;font-size: 14px;" >
	        </select>
			<input type="hidden" id="introduceId" name="introduceId"/>
		</div>

		<div class="mui-input-row">
			<label>备注</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="remark" id="remark" placeholder="请输入备注" ></textarea>
		</div>
	</div>
</form>	
</div>

<div class="shadow" id="shadow"></div>
<!--右上角弹出菜单-->

<script>
var sid = "<%=sid%>";
var cusName = "<%=customerName%>";
var type= "<%=type%>";
function doInit(){
	//初始化客户数据
	//initCustomer();
	//初始化客户数据
	//selectCustomer();
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

//选择所属客户
function selectCustomer(){
	var url = contextPath+'/TeeCrmCustomerController/selectAllCustomer.action';
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
						options = options + "<option value='"+prcs[i].sid+"'>" + prcs[i].customerName + "</option>";
					}
					$("#customerName").append(options);
				return prcs;
			}else{
				alert("数据查询失败！");
			}
		}
	});

}

//初始化客户数据   以弹出菜单样式展示
/* function initCustomer(){
	var page = 1;
	var url = contextPath+'/TeeCrmCustomerController/datagrid.action';
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{state:-1,rows:5,page:page++},
		timeout:10000,
		success:function(json){
			
			json = eval("("+json+")");
			var render = [];
				for(var i=0;i<json.rows.length;i++){
					var item = json.rows[i];
					
					render.push("<li class=\"mui-table-view-cell mui-media\" uuid='"+item.sid+"' customerName='"+item.customerName+"' >"+item.customerName+"");
					render.push("</li>");
					
				}
				$("#middlePopover").html(render.join(""));
				
				$(".mui-media").each(function(i,obj){
					obj.removeEventListener("tap",setValue);
					obj.addEventListener("tap",setValue);
				}); 
		}
	});
} */

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



var cardId;//名片主键
//上传公共附件
function doUploadPublicAttach(){
	
	var imgs=$(".img");
	if(imgs.length>=1){
		alert("只允许上传一张名片！");
		return;
	}
	
	TakePhoto(function(files){
		$("<p class='img' path=\""+files[0].path+"\">"+files[0].name+"&nbsp;&nbsp;<img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='removePublicAttachImg(this)' /></p>").appendTo($("#upfileList"));

		var filesArray = [];
		filesArray.push(files[0].path);
		//直接上传到后台
		UploadPhoto(function(files1){
			cardId=files1[0].id;
		     var url=contextPath+"/TeeCrmContactsController/ScanCard.action";
		     mui.ajax(url,{
		 		type:"post",
		 		dataType:"html",
		 		data:{attachId:files1[0].id},
		 		timeout:10000,
		 		success:function(json){
		 			json = eval("("+json+")");
		 			if(json.rtState){
		 				var data=json.rtData;
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
		 			}else{
		 				alert("查询失败！");
		 			}
		 		}
		 	});
			 
		},filesArray,"crmCustomerContacts","");
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
				$("#customerName").val(data.customerId);
				$("#introduceName").val(data.introduceId);
				
				var  attachments = data.attachmodels;
				if(attachments.length > 0){
					 $.each(attachments, function(index, item){  
						 $("#attachList").append("<p class='img' ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='delAttach("+item.sid+")' /><p>");
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
	var customerId=$("#customerId").val();
	var contactName=$("#contactName").val();
	if(customerId==""||customerId==null||customerId=="null"||customerId=="0"){
		alert("请选择所属客户！");
		return false;	
	}
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
		
			var attachIds=cardId;
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
						window.location.href=contextPath+"/system/mobile/phone/crm/contacts/contactsInfo.jsp?sid="+sid+"&customerName="+cusName+"&type="+type;
					}else{
						window.location.href=contextPath+"/system/mobile/phone/crm/contacts/index.jsp?type="+type;
					}
	               
				}
			}
		});
	
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
					bool=false;;
				}else{
					bool=true;
					
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
			window.location.href=contextPath+"/system/mobile/phone/crm/contacts/index.jsp?type="+type;
		}
	});

});


//选择所属客户
/* function selectCustomer(){
 	mui('#middlePopover').popover('toggle',document.getElementById("middlePopover"));
 	//mui('#middlePopover').popover('hide');
}
//所属客户赋值
 function setValue(){
	var uuid = this.getAttribute("uuid");
	var customerName = this.getAttribute("customerName");
	$("#customerId").val(uuid);
	$("#customerName").val(customerName);
	mui('#middlePopover').popover('hide');
} */

//选择所属客户
function selectCustomer(){
	var iframe_t = document.getElementById('iframe1');
 	if(iframe_t.style.display =='block'){
		return;
	}
	$(iframe_t).slideDown(); 
	//iframe_t.style.display='block'; 
	$(".shadow").fadeIn();
	$("body").css("overflow","hidden");
	iframe_t.src='../orders/selectCustomers.jsp'; 
	
	//window.location = 'selectCustomers.jsp';

}



function operPage(){
	changeShadow();
	//显示隐藏有一个异步的过程 执行该行代码是隐藏尚未完成
	if(!$("#shadow").is(":hidden")){
		$("body").css("overflow","auto");
	}
}
function changeShadow(){//这里要处理下 看是谁返回的 不然都宣布显示了
	$("#shadow").fadeToggle();
	$("#iframe1").fadeToggle();
}

function selectCustomerCallBackFunc(sid,name){
	$("#customerName").val(name);
	$("#customerId").val(sid);
}

</script>

</body>
</html>