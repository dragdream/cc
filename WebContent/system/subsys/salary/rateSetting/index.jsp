<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>税率设置</title>
<%@ include file="/header/header2.0.jsp" %>
<style type="text/css">
	#tb{
		border-collapse: collapse;
	    border: 1px solid #f2f2f2;
	    width:100%;
	}
	 #tb tr{
		line-height:35px;
		border-bottom:1px solid #f2f2f2;
	}
	#tb tr td:first-child{
		text-indent:10px;
	}
	#tb tr:first-child td{
		font-weight:bold;
	}
	#tb tr:first-child{
		border-bottom:2px solid #b0deff!important;
		background-color: #f8f8f8; 
	}
	.modal-test{
		width: 600px;
		height: 270px;
		position: absolute;
		display: none;
		z-index: 999;
	}
	.modal-test .modal-header{
		width: 100%;
		height: 35px;
		background-color:#8ab0e6;
	}
	.modal-test .modal-header .modal-title{
		color: #fff;
		font-size: 14px;
		line-height:35px;
		margin-left:20px;
		float: left;
	}
	.modal-test .modal-header .modal-win-close{
		color:#fff;
		font-size: 14px;
		line-height:35px;
		margin-right:20px;
		float: right;
		cursor: pointer;
	}
	.modal-test .modal-body{
		height: 130px;
		background-color:#f4f4f4;
		padding:10px;
		overflow: auto;
	}
	.modal-test .modal-footer{
		width: 100%;
		height: 50px;
		background-color:#f4f4f4;
	}
	.modal-test .modal-footer input{
		margin-top:12px;
		float: right;
		margin-right:20px;
	}
    
</style>
<script type="text/javascript">

Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
	  this.splice(index, 1);
	}
};

var jsonData=[];

//初始化方法
function doInit(){
	renderTable();
}

function renderTable(){
	//获取已有的税率设置
	   var url=contextPath+"/salaryManage/getRate.action";
	   var json=tools.requestJsonRs(url,null);
	   if(json.rtState){  
		   var data=tools.strToJson(json.rtData);
		   jsonData=data;
		   var html=[];
		   for(var i=0;i<data.length;i++){
			   html.push("<tr>");
			   html.push("<td>"+data[i].exp.replace("<","&lt;").replace(">","&gt;")+"</td>");
			   html.push("<td>"+(data[i].rate)*100+"</td>");
			   html.push("<td>"+data[i].reduce+"</td>");
			   html.push("<td><a href=\"#\" class=\"modal-menu-test\" onclick=\"$(this).modal();update("+i+")\">编辑</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"#\" onclick=\"del("+i+")\">删除</a></td>");
			   html.push("<tr>");
		   }
		   $("#tb").append(html.join(""));
	   }
}


//刪除 
function del(i){
	
	
	$.MsgBox.Confirm ("提示","是否确认删除?", function(){
		jsonData.remove(jsonData[i]);
		
		//保存
	    var url=contextPath+"/salaryManage/updateRate.action";
		var json=tools.requestJsonRs(url,{model:tools.jsonArray2String(jsonData)});
		if(json.rtState){
			$(".modal-win-close").click();
			//再次渲染
			$("#tb  tr:not(:first)").empty("");  
			renderTable();	
			
			$.MsgBox.Alert_auto("删除成功！");
		}
	});

}

//编辑渲染
function update(i){
	$("#flag").val(i+1);//i代表修改
	$("#minData").val(jsonData[i].minData);
	$("#maxData").val(jsonData[i].maxData);
	$("#rate").val(jsonData[i].rate*100);
	$("#reduce").val(jsonData[i].reduce);
}

//新增渲染
function add(){
	$("#flag").val(0);//0代表新增
}


//新建  编辑
function addOrUpdate(){
	if(check()){
		var flag=$("#flag").val();
		
		var minData=$("#minData").val();
		var maxData=$("#maxData").val();	
		var rate=$("#rate").val();
		var reduce=$("#reduce").val();
		
		var exp="";
		if(minData!=""&&maxData!=""){
			exp=minData+"<A"+"<="+maxData;
		}else if(minData==""&&maxData!=""){
			exp="A<="+maxData;
		}else if(minData!=""&&maxData==""){
			exp="A>"+minData;
		}
		if(flag==0){//新增
			jsonData.push({"exp":exp,"rate":rate/100+"","reduce":reduce,"minData":minData,"maxData":maxData});
		}else{//修改
			jsonData[flag-1]={"exp":exp,"rate":rate/100+"","reduce":reduce,"minData":minData,"maxData":maxData};
		}
		
		
		
		//排序
		bubbleSort(jsonData);
		
		//保存
	    var url=contextPath+"/salaryManage/updateRate.action";
		var json=tools.requestJsonRs(url,{model:tools.jsonArray2String(jsonData)});
		if(json.rtState){
			$(".modal-win-close").click();
			//再次渲染
			$("#tb  tr:not(:first)").empty("");  
			renderTable();	
			
		}
		
	}
}



//数组排序
function bubbleSort(arr){
	for(var i=0;i<arr.length-1;i++){  
        for(var j=i+1;j<arr.length;j++){  
            if(Number(arr[i].rate)>Number(arr[j].rate)){//如果前面的数据比后面的大就交换  
                var temp=arr[i];  
                arr[i]=arr[j];  
                arr[j]=temp;  
            } 
        }  
    }   
    return arr;
}


//验证
function check(){
	var minData=$("#minData").val();
	var maxData=$("#maxData").val();	
	var rate=$("#rate").val();
	var reduce=$("#reduce").val();
	var regu = /^\d+$/;
	
	
    if(minData==""&&maxData==""){
    	$.MsgBox.Alert_auto("请至少填写一个范围！");
    	return false;
    }else{
    	if(minData!="" && !regu.test(minData)){
    		$.MsgBox.Alert_auto("最小范围请输入非负整数！");
        	return false;
    	}
    	
    	if(maxData!="" && !regu.test(maxData)){
    		$.MsgBox.Alert_auto("最大范围请输入非负整数！");
        	return false;
    	}
    }
    
    
    if(rate==""||rate==null){
    	$.MsgBox.Alert_auto("请填写税率！");
    	return false;
    }else{
    	if(rate!="" && !regu.test(rate)){
    		$.MsgBox.Alert_auto("税率请输入非负整数！");
        	return false;
    	}
    }
    
    
    if(reduce==""||reduce==null){
    	$.MsgBox.Alert_auto("请填写速扣数！");
    	return false;
    }else{
    	if(reduce!="" && !regu.test(reduce)){
    		$.MsgBox.Alert_auto("速扣数请输入非负整数！");
        	return false;
    	}
    }
	
    return true;
}
</script>
</head>

<body onload="doInit();" style="padding-left: 10px;padding-right: 10px;">
  <div id="toolbar" class = "topbar clearfix">
		<div class="fl" style="position:static;">
			<img id="img1" class = 'title_img' src="<%=contextPath %>/system/subsys/salary/rateSetting/imgs/icon_slsz.png">
			<span class="title">税率设置</span>
		</div>
		<div class = "right fr clearfix">
		    <input type="button" class="btn-win-white fl modal-menu-test" onclick="$(this).modal();add();" value="新增"/>
        </div>
   </div>
   
   <table align='center' width='90%' id="tb">
      <tr>
         <td>范围</td>
         <td>税率(%)</td>
         <td>速扣数</td>
         <td width="20%">操作</td>
      </tr>
   </table>
   
  
   <!-- Modal -->
	 <div class="modal-test">
		<div class="modal-header">
			<p class="modal-title">
				新增/编辑
			</p>
			<span class="modal-win-close">×</span>
		</div>
		<div class="modal-body" >
		    <input type='hidden'  name="flag" id="flag" value="0"/>
			<table style="width:100%" class="TableBlock">
			    <tr>
			        <td width="80px;" align=right style="text-indent: 10px">范围:</td>
			        <td>
			           <input type="text" id="minData" name="minData"  style="width:170px;height: 23px"/>
					    ~
					   <input type="text" id="maxData" name="maxData" style="width:170px;height: 23px"/>
			        </td>
			    </tr>
			    <tr>
			        <td width="80px;" align=right style="text-indent: 10px">税率(%):</td>
			        <td>
			           <input type="text"  id="rate" name="rate" style="width:200px;height: 23px"/>
			        </td>
			    </tr>
			    <tr>
			        <td width="80px;" align=right style="text-indent: 10px">速扣数:</td>
			        <td>
			           <input type="text"  id="reduce" name="reduce" style="width:200px;height: 23px"/>
			        </td>
			    </tr>
		    </table>
		</div>
		<div class="modal-footer clearfix">
			<input class = "modal-btn-close btn-alert-gray" type="button" value = '关闭'/>
			<input class = "modal-save btn-alert-blue" type="button" onclick="addOrUpdate();" value = '保存'/>
		</div>
	</div>
</body>
</html>