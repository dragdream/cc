<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
    int parentId=TeeStringUtil.getInteger(request.getParameter("parentId"),0);
	int sid =TeeStringUtil.getInteger(request.getParameter("sid"), 0) ;
    String title="";
    if(sid>0){
    	title="修改督办任务";
    }else{
    	title="新建督办任务";	
    }
%>
<!DOCTYPE HTML>
<html>
<head>
<title><%=title %></title>
<%@ include file="/system/mobile/mui/header.jsp" %>
<link href="/system/mobile/phone/style/style.css" rel="stylesheet" type="text/css" />
</head>
<body onload="doInit()">
<header id="header" class="mui-bar mui-bar-nav">
	
	<span class="mui-icon mui-icon-back" id="backBtn"></span>
	<a class="mui-action-menu mui-icon mui-icon-more mui-pull-right" href="#topPopover"></a>
	
	<h1 class="mui-title"><%=title %></h1>
	
</header>

<div id="muiContent" class="mui-content">
<form id="form1" name="form1"  >
    <input type="hidden" name="parentId" value="<%=parentId %>" id="parentId"/>
     <input type="hidden" name="sid" value="<%=sid %>" id="sid"/>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>工作事项</label>
		</div>
		<div class="mui-input-row">
		    <input type="text" placeholder="工作事项" name="supName" id="supName"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>所属类别</label>
		</div>
		<div class="mui-input-row">
			<select id="typeId" name="typeId" style="height: 23px;width: 150px">
        
            </select>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>责任领导</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="leaderName" name="leaderName" readonly placeholder="请选择责任领导" />
			<input type="hidden" id="leaderId" name="leaderId"/>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>主办人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="managerName" name="managerName" readonly placeholder="请选择主办人" />
			<input type="hidden" id="managerId" name="managerId"/>
		</div>
	</div>
	<div class="mui-input-group">
	    <div class="mui-input-row">
			<label>协办人</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
			<input type="text" id="assistNames" name="assistNames" readonly placeholder="请选择协办人" />
			<input type="hidden" id="assistIds" name="assistIds"/>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>开始时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='beginTimeStr' name='beginTimeStr' class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>结束时间</label>
		</div>
		<div class="mui-input-row">
			<input type="text" id='endTimeStr' name='endTimeStr'  class="Wdate BigInput" style="width: 160px" />
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>督办内容</label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		  <textarea rows="6" style="width: 550px" name="content" id="content" placeholder="督办内容" ></textarea>
		</div>
	</div>
	<div class="mui-input-group">
		<div class="mui-input-row">
			<label>相关附件</label>
			<label><a href="javascript:void(0)" onclick="doUploadPublicAttach()" >上传附件</a></label>
		</div>
		<div class="mui-input-row" style="height:inherit">
		   <div id="attachList" style="padding-left: 15px"></div>
		   <div id="upfileList" style="padding-left: 15px"></div>
		    
		</div>
	</div>
</form>	
</div>


<!--右上角弹出菜单-->
	<div id="topPopover" class="mui-popover">
		<ul class="mui-table-view">
		   <li class="mui-table-view-cell" onclick="save(0);">保存</li>
		   <li class="mui-table-view-cell" onclick="save(1);">发布</li>
		  </ul>
	</div>

<script>
var sid=<%=sid%>;
var parentId=<%=parentId%>;
function doInit(){
	
	if(sid>0){//修改督办任务
		getInfoByUuid(sid);  
	}
	//初始化督办任务分类
	initSupType();
	

	//开始时间
	beginTimeStr.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
	    beginTimeStr.value = rs.text;	
		picker.dispose(); 
		});
	}, false);

	
	endTimeStr.addEventListener('tap', function() {
		var picker = new mui.DtPicker({type:"date"});
		picker.show(function(rs) {
	    endTimeStr.value = rs.text;	
		picker.dispose(); 
		});
	}, false);
}


//根据主键获取详情
function getInfoByUuid(sid){
	var url=contextPath+"/supervisionController/getInfoBySid.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		data:{sid:sid},
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				bindJsonObj2Cntrl(json.rtData);
				
				var  attachments = json.rtData.attachmentsMode;
				if(attachments.length > 0){
					 $.each(attachments, function(index, item){  
						 $("#attachList").append("<div ><a href='javascript:void(0);' onclick=\"GetFile('"+item.sid+"','"+item.fileName+"','"+item.attachmentName+"')\">"+item.fileName + "&nbsp;&nbsp;"+"</a><img style='vertical-align:middle' src='/common/images/upload/remove.png' onclick='delAttach("+item.sid+")' /><div>");
					 });
				}
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




//初始化督办任务分类
function initSupType(){
	var url=contextPath+"/supTypeController/getSupTypeTree.action";
	mui.ajax(url,{
		type:"post",
		dataType:"html",
		timeout:10000,
		success:function(json){
			json = eval("("+json+")");
			if(json.rtState){
				var data=json.rtData;
				for(var i=0;i<data.length;i++){
					$("#typeId").append("<option value="+data[i].id+" >"+data[i].name+"</option>");
				}
			}
		}
	});
}




//验证
function check(){
	var supName=$("#supName").val();
	var typeId=$("#typeId").val();
	var leaderName=$("#leaderName").val();
	var managerName=$("#managerName").val();
	var beginTimeStr=$("#beginTimeStr").val();
	var endTimeStr=$("#endTimeStr").val();
	if(supName==""||supName==null){
		alert("请填写工作事项！");
		return false;
	}
	if(typeId==""||typeId==null||typeId==0||typeId=="0"){
		alert("请选择所属类别！");
		return false;
	}
	if(leaderName==""||leaderName==null){
		alert("请选择责任领导！");
		return false;
	}
	if(managerName==""||managerName==null){
		alert("请选择主办人！");
		return false;
	}
	if(beginTimeStr==""||beginTimeStr==null){
		alert("请选择开始时间！");
		return false;
	}
	if(endTimeStr==""||endTimeStr==null){
		alert("请选择结束时间！");
		return false;
	}
	return true;
}



//保存/提交
function save(status){
	if(check()){
		var filesArray = [];//组装文件数据
		$(".img").each(function(i,obj){
			filesArray.push(obj.getAttribute("path"));
		});
		
		//先上传图片  
		UploadPhoto(function(files){
			var attachIds="";
			for(var i=0;i<files.length;i++){
				//alert(files[i].id);
				attachIds=attachIds+files[i].id+"," ;
			}
			var url=contextPath+"/supervisionController/mobileAddOrUpdate.action";
			var param=formToJson("#form1");
			param['status']=status;
			param['attachIds']=attachIds;
			mui.ajax(url,{
			type:"post",
			dataType:"html",
			data:param,
			timeout:10000,
			success:function(json){
				json = eval("("+json+")");
				if(json.rtState){
				   var data=json.rtData;
				   var typeSid=data.typeSid;
				   var typeName=data.typeName;
				   if(parentId>0){//创建子任务
					   window.location="detail/index.jsp?sid="+parentId;
				   }else{//普通的创建督办任务
					   window.location="list.jsp?typeName="+typeName+"&&typeSid="+typeSid;
				   }
	               
				}
			}
		});
			
		},filesArray,"supervision","");//一开始modelid是没有的 因为主键还没生成呢
   }	
}





mui.ready(function() {
	//返回
	backBtn.addEventListener("tap",function(){
		if(parentId>0){
			window.location="detail/index.jsp?sid="+parentId;
		}else{
			history.go(-1);
		}
		
	});
	
	//选择责任领导
	leaderName.addEventListener('tap', function() {
		selectSingleUser("leaderId","leaderName");
	}, false);
	//选择主办人
	managerName.addEventListener('tap', function() {
		selectSingleUser("managerId","managerName");
	}, false);
	//选择协办人
	assistNames.addEventListener('tap', function() {
		selectUser("assistIds","assistNames");
	}, false);
});


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

</script>

</body>
</html>