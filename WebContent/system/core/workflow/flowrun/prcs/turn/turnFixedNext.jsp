<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
		int runId = TeeStringUtil.getInteger(request.getParameter("runId"),0);
		int flowId = TeeStringUtil.getInteger(request.getParameter("flowId"),0);
		int frpSid = TeeStringUtil.getInteger(request.getParameter("frpSid"),0);
		String prcsEvent = TeeStringUtil.getString(request.getParameter("prcsEvent"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>转交工作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/header/header2.0.jsp"%>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/prototype.js"></script>
<script type="text/javascript"
	src="<%=contextPath%>/common/js/workflowUtils.js"></script>
<script type="text/javascript">
var runId = '<%=runId%>';
var flowId = '<%=flowId%>';
var frpSid = '<%=frpSid%>';
var prcsEvent = '<%=prcsEvent%>';

var allNextPrcsNodes = [1,2,3,4,5];//所有直接隶属于当前步骤的下一步节点
var parallelPrcsNodes = [1,2];//其中并发节点的SID
var disabledPrcsNodes = [3,11];//转出条件失败的节点SID
var disabledPrcsNodesMsg = [3,11];//转出条件失败的节点SID
var parallelChildNodes = {'1':{force:true,nodes:[7,8]},'2':{force:false,nodes:[9,10,11]}};//并发节点下的子节点步骤
var prcsNodeInfos = [
{prcsId:1,prcsName:'审批',userLock:1,opFlag:1,autoSelect:true,opUser:'1',prcsUser:'1,2,3,4',opUserDesc:'系统管理员',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:2,prcsName:'查阅',userLock:1,opFlag:2,autoSelect:true,opUser:'2',prcsUser:'1,2,3,4',opUserDesc:'张三',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:3,prcsName:'初审',userLock:1,opFlag:2,autoSelect:true,opUser:'3',prcsUser:'1,2,3,4',opUserDesc:'李四',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:4,prcsName:'回退',userLock:0,opFlag:2,autoSelect:true,opUser:'4',prcsUser:'1,2,3,4',opUserDesc:'王五',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:5,prcsName:'文书科',userLock:0,opFlag:1,autoSelect:false,opUser:'1',prcsUser:'1,2,3,4',opUserDesc:'赵六',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:6,prcsName:'打印室',userLock:0,opFlag:1,autoSelect:false,opUser:'2',prcsUser:'1,2,3,4',opUserDesc:'张三',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:7,prcsName:'秘书室',userLock:0,opFlag:3,autoSelect:false,opUser:'3',prcsUser:'1,2,3,4',opUserDesc:'李四',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:8,prcsName:'档案室',userLock:1,opFlag:3,autoSelect:false,opUser:'4',prcsUser:'1,2,3,4',opUserDesc:'王五',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:9,prcsName:'回收处',userLock:1,opFlag:3,autoSelect:false,opUser:'1',prcsUser:'1,2,3,4',opUserDesc:'赵六',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:10,prcsName:'领导办公室',userLock:1,opFlag:1,autoSelect:false,opUser:'2',prcsUser:'1,2,3,4',opUserDesc:'张三',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'},
{prcsId:11,prcsName:'复核',userLock:1,opFlag:1,autoSelect:false,opUser:'3',prcsUser:'1,2,3,4',opUserDesc:'李四',prcsUserDesc:'张三,王五,赵刘,李四',userFilter:'1,2,3,4,5,6,7,8,9,10,11,12,13,14,15'}
];
var prcsNodePrefix = '#prcsTo';
var nextPrcsContent;//步骤列表容器
var parallelPrcsContent;//并发步骤列表容器
var childFlowNodeInfos = [
                          {prcsId:7,prcsName:'子流程1',userFilter:'1,2,3,4,5,6'}
                          ];//子流程节点信息
var diabledChildFlowNodes = [4,2];//转出失败的子流程节点
var diabledChildFlowNodesMsg = [4,2];//转出失败的子流程节点

var parentWindowObj;
var viewPriv;
var error = false;
var autoTurn = 0;


var textWidth = function(text){ 
    var sensor = $('<pre>'+ text +'</pre>').css({display: 'none'}); 
    $('body').append(sensor); 
    var width = sensor.width();
    sensor.remove(); 
    return width;
};

function doInit(){
	wfCluster.init();
	try{
		var div = parent.document.getElementById("jbox-content");
		$(div).css({overflowY:"hidden"});
	}catch(e){}
	
	document.body.onclick=function(){
		$('#opFlagOptsPanel').remove();
	};
	ChangeInputSize();
	
	
	//根据frpSid判断当前步骤是否允许归档
	var url=contextPath+"/flowRun/checkIsArchive.action?frpSid="+frpSid;
	var json=tools.requestJsonRs(url);
	if(json.rtState){
		var data=json.rtData;
		if(data==1){
			$("#archiveBd").show();
			$("#isArchive").attr("checked","checked");
		}else{
			$("#archiveBd").hide();
			$("#isArchive").removeAttr("checked");
		}
	}
}


function ChangeInputSize(){
	$(".changelistener").each(function(i,obj){
		obj.style.width = "0px";
		var maxWidth = $(obj).parent().width();
		var targetWidth = textWidth($(obj).val());
		if(targetWidth>=maxWidth){
			targetWidth = maxWidth-100;
		}
		$(obj).width(targetWidth);
	});
}


var wfCluster = {
init:function(){//初始化各项参数
	nextPrcsContent = $('#nextPrcsContent');
	parallelPrcsContent = $('#parallelPrcsContent');

	//请求转交数据模型
	var url = contextPath+'/flowRun/getTurnHandlerData.action';
	var json = tools.requestJsonRs(url,{runId:runId,flowId:flowId,frpSid:frpSid,prcsEvent:prcsEvent});
	if(json.rtState){
		var data = json.rtData;
		allNextPrcsNodes = data.allNextPrcsNodes;
		parallelPrcsNodes = data.parallelPrcsNodes;
		disabledPrcsNodes = data.disabledPrcsNodes;
		disabledPrcsNodesMsg = data.disabledPrcsNodesMsg;
		parallelChildNodes = data.parallelChildNodes;
		prcsNodeInfos = data.prcsNodeInfos;//下一步骤节点
		childFlowNodeInfos = data.childFlowNodeInfos;
		diabledChildFlowNodes = data.diabledChildFlowNodes;//无法通过的子流程节点
		diabledChildFlowNodesMsg = data.diabledChildFlowNodesMsg;
		autoTurn = data.autoTurn;//自动转交
		//alert(tools.jsonArray2String(data.disabledPrcsNodesMsg));
		
		viewPriv = data.viewPriv;
		
		//渲染短信提醒
		var nextPrcsAlert = data.nextPrcsAlert;
		var beginUserAlert = data.beginUserAlert;
		var allPrcsUserAlert = data.allPrcsUserAlert;
		
		for(var i=1;i<=2;i++){
			if((i & nextPrcsAlert)==i){
				$("#nextPrcsAlert"+(i)).attr("checked",true);
			}
			if((i & beginUserAlert)==i){
				$("#beginUserAlert"+(i)).attr("checked",true);
			}
			if((i & allPrcsUserAlert)==i){
				$("#allPrcsUserAlert"+(i)).attr("checked",true);
			}
		}
		if((4 & beginUserAlert) == 4){
			$("#beginUserAlert"+3).attr("checked","checked");
		}
		if((4 & allPrcsUserAlert) == 4){
			$("#allPrcsUserAlert"+3).attr("checked","checked");
		}
		if((4 & nextPrcsAlert) == 4){
			$("#nextPrcsAlert"+3).attr("checked","checked");
		}

		//设置传阅
		if(viewPriv==1){
			$("#viewPerson").attr("value",data.viewPerson);
			$("#viewPersonDesc").attr("value",data.viewPersonDesc);
			$("#viewPersonFilter").attr("value",data.viewPerson);
		}

		//短消息加载
		$("#msg").attr("value",data.msg);
		
	}else{
		messageMsg(json.rtMsg,"center","info");
		error = true;
		return;
	}
	
	this.renderSelectPrcsUser();
	this.renderInitDatas();
	this.renderChildFlow();

	var validNextPrcesNodes = [];
	//默认选择第一个转交节点
	var childrens = nextPrcsContent.children();
	for(var i=0;i<childrens.length;i++){
		var node = $(childrens[i]);
		if(node.attr("class").indexOf("trun_prcs_disabled_box")==-1){
			if(validNextPrcesNodes.length==0){
				this.prcsSelectEvent(node);
			}
			validNextPrcesNodes.push(node);
		}
	}
	
	//开启自动转交
	if(autoTurn==1){
		//判断当前有效节点数,如果只有一个，则直接转交
		if(validNextPrcesNodes.length==1 && this.commit()){
			if(parent.xparent.doPageHandler){
				parent.xparent.doPageHandler();
			}
			parent.CloseWindow();
		}
	}
	
},
renderChildFlow:function(){
	var childFlow = $("#childFlow");
	childFlow.hide();
	var html = "<p  style='padding:5px;background:#f0f0f0'><b>子流程</b></p>";
	for(var i=0;i<childFlowNodeInfos.length;i++){
		childFlow.show();
		var childFlowNodeInfo = childFlowNodeInfos[i];
		var id = "childFlow_"+childFlowNodeInfo.prcsId;
		if(diabledChildFlowNodes.exist(childFlowNodeInfo.prcsId)){//条件不符
			html+="<div disabled title=\""+childFlowNodeInfo.conditionMsg+"\" style='padding:5px;'>";
			html+=""+childFlowNodeInfo.prcsName+"：";
			html+="<span style='color:red'>条件不符</span>";
		}else{//条件满足
			html+="<div prcsId='"+childFlowNodeInfo.prcsId+"' style='padding:5px;'>";
			html+=""+childFlowNodeInfo.prcsName+"：";
			//selectSingleUser(['opUserInput'+prcsId,'opUserDescInput'+prcsId],'','',data.userFilter,'');
			//如果是自动选人
			if(childFlowNodeInfo.autoSelect){
				html+="<input type=\"hidden\" class=\"BigInput\" id=\""+id+"\" value=\""+childFlowNodeInfo.prcsUser+"\"/>";
				html+="<input style=\'width:0px;border:0px\' type=\"text\" readonly class=\"changelistener readonly\" id=\""+id+"_Desc\" value=\""+childFlowNodeInfo.prcsUserDesc+"\"/>";
			}else{
				html+="<input type=\"hidden\" class=\"BigInput\" id=\""+id+"\" />";
				html+="<input style=\'width:0px;border:0px\' type=\"text\" readonly class=\"changelistener readonly\" id=\""+id+"_Desc\" />";
			}
			if(childFlowNodeInfo.userLock==0){
				continue;
			}
			//多实例步骤
			if(childFlowNodeInfo.multiInst==1){
				html+="&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"selectUserWorkFlow(['"+id+"','"+id+"_Desc'],'','','"+childFlowNodeInfo.prcsId+"_"+frpSid+"','')\">选择</a>&nbsp;<a href=\"javascript:void(0)\" onclick=\"clearData('"+id+"','"+id+"_Desc');ChangeInputSize();\">清空</a>";
			}else{//单实例步骤
				html+="&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"selectSingleUserWorkFlow(['"+id+"','"+id+"_Desc'],'','','"+childFlowNodeInfo.prcsId+"_"+frpSid+"','')\">选择</a>&nbsp;<a href=\"javascript:void(0)\" onclick=\"clearData('"+id+"','"+id+"_Desc');ChangeInputSize();\">清空</a>";
			}
		}
		html+="</div>";
	}
	childFlow.html(html);
},
renderInitDatas:function(){//渲染初始化数据
	for(var i=0;i<allNextPrcsNodes.length;i++){//遍历步骤节点
		var info = this.getPrcsInfo(allNextPrcsNodes[i]);
		this.renderNextNodes(info);
	}
},
prcsSelectEvent:function(elm){//步骤选择事件
	var elm = $(elm);
	//遍历所有步骤节点
	for(var i=0;i<allNextPrcsNodes.length;i++){
		var obj = $(prcsNodePrefix+allNextPrcsNodes[i]);
		if(obj.length!=0 && elm[0]==obj[0]){
			if(disabledPrcsNodes.exist(allNextPrcsNodes[i])){//如果有不能选择的节点，则取消选择权限
				obj.find('input[type=checkbox]:first').attr('checked',false);
				break;
			}
			this.hideAllPrcsOpts();//隐藏所有选人面板
			if(elm.find('input[type=checkbox]:first').attr('checked')!=true){
				this.cancelSelectAllPrcs();
				elm.find('input[type=checkbox]:first').attr('checked',true);
				if(parallelPrcsNodes.exist(allNextPrcsNodes[i])){
					$('#parallelPrcsContent').show();
					this.renderParallelNode(elm);
				}else{
					$('#parallelPrcsContent').html('').hide();
					if(this.getPrcsInfo(allNextPrcsNodes[i]).prcsType.toString()!='2'){//如果不是结束节点
						$('#prcsOpts'+allNextPrcsNodes[i]).show();//显示选人面板
						$('#passing').hide();//隐藏传阅面板
					}else{
						if(viewPriv==1){
							$('#passing').show();//显示传阅面板
						}
					}
				}
			}
			break;
		}
	}
	ChangeInputSize();
},
cancelSelectAllPrcs:function(){//取消选择所有步骤
	for(var i=0;i<allNextPrcsNodes.length;i++){
		var obj = $(prcsNodePrefix+allNextPrcsNodes[i]);
		if(obj.length!=0){
			obj.find('input[type=checkbox]:first').attr('checked',false);
			$('#prcsOpts'+allNextPrcsNodes[i]).hide();//隐藏选人面板
		}
	}
},
parallelSelectEvent:function(elm){//并发节点选择事件
	var elm = $(elm);
	var prcsId = elm.attr('prcsId');
	
	if(disabledPrcsNodes.exist(prcsId)){
		elm.find('input[type=checkbox]:first').attr('checked',false);
		return ;
	}

	for(var key in parallelChildNodes){
		var force = parallelChildNodes[key].force;
		var nodes = parallelChildNodes[key].nodes;
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].toString()==prcsId.toString()){//如果是并发节点的子节点
				if(!force){//非强制并发
					if(!elm.find('input[type=checkbox]:first').attr('checked')){
						if(!disabledPrcsNodes.exist(prcsId)){
							elm.find('input[type=checkbox]:first').attr('checked',true);
						}
						$('#prcsOpts'+prcsId).show();
					}else{
						elm.find('input[type=checkbox]:first').attr('checked',false);
						$('#prcsOpts'+prcsId).hide();
					}
				}else{//强制并发
					if(!disabledPrcsNodes.exist(prcsId)){
						elm.find('input[type=checkbox]:first').attr('checked',true);
					}
				}
			}
		}
	}
	ChangeInputSize();
},
renderSelectPrcsUser:function(){//渲染选人容器
	for(var i=0;i<prcsNodeInfos.length;i++){//遍历所有节点信息
		var data = prcsNodeInfos[i];
		if(disabledPrcsNodes.exist(data.prcsId)){//如果存在不合法节点，则过滤掉
			continue;
		}
		
		var userLock = data.userLock;
		var opFlagDesc = this.opFlagOpts(data.opFlag);

		var opUser = data.opUser;
		var opUserDesc = data.opUserDesc;
		var prcsUser = data.prcsUser;
		var prcsUserDesc = data.prcsUserDesc;
	    
		var html = '<table id=\'prcsOpts'+data.prcsId+'\' style=\'display:none\' prcsId=\''+data.prcsId+'\' class=\'prcsOpts_tb\' >';
		html+='<tr>';
		html+='<td class=\'prcsOpts_header\'><b>'+data.prcsName+'</b></td>';
		html+='</tr>';
		html+='<tr style="display:none">';
		html+='<td class=\'prcsOpts_opUser\'><span id=\'opFlagSpan'+data.prcsId+'\' class=\'opFlagStyle\' onclick=\'wfCluster.showOpFlagOptsPanel(this,'+userLock+','+data.prcsId+')\'>'+opFlagDesc+'</span><span id\'opUserSpan'+data.prcsId+'\'></span><input type=\'hidden\' value=\''+(data.opFlag.toString()=='1'?opUser:'')+'\' hidden id=\'opUserInput'+data.prcsId+'\'/><input type=\'\' id=\'opUserDescInput'+data.prcsId+'\' value=\''+(data.opFlag.toString()=='1'?opUserDesc:'')+'\' class=\'changelistener\' readonly style=\'border:0px;width:0px;font-size:12px\'/>&nbsp;<span id=\'opUserBtn'+data.prcsId+'\' style=\'display:'+(data.opFlag.toString()!='1'?'none':'')+'\'   ><a href=\'javascript:wfCluster.selectOpUser('+data.prcsId+')\'>选择</a></span></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td class=\'prcsOpts_prcsUser\'><span>办理人员</span>：<span id=\'prcsUsersSpan'+data.prcsId+'\'></span><input type=\'hidden\' id=\'prcsUserInput'+data.prcsId+'\' value=\''+(prcsUser)+'\' hidden/><input type=\'text\' id=\'prcsUserDescInput'+data.prcsId+'\' readonly class=\'changelistener\' value=\''+prcsUserDesc+'\' style=\'border:0px;width:0px;font-size:12px\'/>&nbsp;<a href=\'javascript:wfCluster.selectPrcsUser('+data.prcsId+')\' '+(data.showSelect?"":"style=\'display:none\'")+'>选择</a></td>';
		html+='</tr>';
		html+='<tr style="'+(data.timeoutFlag==1?"":"display:none")+'">';
		html+='<td class=\'prcsOpts_prcsUser\'><span>超时时限：</span><input type="text" id="timeout'+data.prcsId+'" value="'+data.timeout+'" class="BigInput" style="width:70px" onblur="this.value = xParseNumber(this.value)" />&nbsp;小时&nbsp;&nbsp;(0为不限制)</td>';
		html+='</tr>';
		html+='</table>';
		
		$(html).appendTo(prcsOpts);
	}
},
opFlagOpts:function(flag){
	switch(flag){
		case 1:return '主办人员';
		case 2:return '无主办会签';
		case 3:return '先接收转交';
		case 4:return '抢占式办理';
	}
	return '';
},
renderNextNodes:function(data){//渲染步骤节点
	var className = 'trun_prcs_box';
	var msg = '';
	var none = false;
	if(disabledPrcsNodes.exist(data.prcsId)){//如果存在不符合条件的节点，则渲染
		className += ' trun_prcs_disabled_box';
		msg = '&nbsp;<span style=\'color:red;font-size:10px\'>(条件不符)</span>';
		none = true;
	}else if(parallelPrcsNodes.exist(data.prcsId)){
		msg = '&nbsp;<span style=\'color:green;font-size:10px\'>(并发)</span>';
	}
	
	var html = '<div style=\''+(none?"display:none":"")+'\' title=\''+((data.conditionMsg==null)?data.prcsName:data.conditionMsg)+'\' id=\''+(prcsNodePrefix.replace('#','')+data.prcsId)+'\' prcsId=\''+data.prcsId+'\' class=\''+className+'\' onclick=\'wfCluster.prcsSelectEvent(this)\'><input type=\'checkbox\' />'+data.prcsName+msg+'</div>';
	var event = this.prcsSelectEvent;
	$(html).appendTo(nextPrcsContent);
},
renderParallelNode:function(elm){//渲染并发节点逻辑
	var prcsId = elm.attr('prcsId');
	if(!parallelPrcsNodes.exist(prcsId)){
		return;
	}
	
	var info = parallelChildNodes[prcsId];
	$('#parallelPrcsContent').html('');
	for(var i=0;i<info.nodes.length;i++){
		var data = this.getPrcsInfo(info.nodes[i]);
		
		var className = 'trun_parallel_prcs_box';
		var msg = '';
		var none = false;
		if(disabledPrcsNodes.exist(data.prcsId)){//如果存在不符合条件的节点，则渲染
			className += ' trun_prcs_disabled_box';
			msg = '&nbsp;<span style=\'color:red;font-size:10px\'>(条件不符)</span>';
			none = true;
		}

		var html = $('<div style=\''+(none?"display:none":"")+'\' id=\'prcsTo'+data.prcsId+'\' title=\''+((data.conditionMsg==null)?data.prcsName:data.conditionMsg)+'\' class=\''+className+'\' prcsId=\''+data.prcsId+'\' onclick=\'wfCluster.parallelSelectEvent(this)\'><input type=\'checkbox\' checked/>'+data.prcsName+msg+'</div>');
		
		$('#parallelPrcsContent').append(html);
		if(!disabledPrcsNodes.exist(data.prcsId)){
			this.parallelSelectEvent(html[0]);
		}
		
		if(info.force && msg==""){
			$('#prcsOpts'+info.nodes[i]).show();//显示选人面板
		}else{
			$('#prcsOpts'+info.nodes[i]).hide();//隐藏选人面板
			$("#prcsTo"+data.prcsId).find("input[checked]").prop("checked",false);
		}
		
		
		
	}
},
getPrcsInfo:function(prcsId){
	for(var i=0;i<prcsNodeInfos.length;i++){
		if(prcsNodeInfos[i].prcsId.toString()==prcsId.toString()){
			return prcsNodeInfos[i];
		}
	}
},
hideAllPrcsOpts:function(){//隐藏所有选人面板
	for(var i=0;i<prcsNodeInfos.length;i++){
		$('#prcsOpts'+prcsNodeInfos[i].prcsId).hide();
	}
},
showOpFlagOptsPanel:function(obj,userLock,prcsId){//显示经办类型选择面板,obj-点击的按钮对象,userLock,prcsId
	var panel = $('#opFlagOptsPanel');
	if('1'==userLock.toString()){
		panel.remove();
		top.$.jBox.tip('不允许修改办理选项',"info");
		return;
	}
	if(panel.length==0){
		panel = $('<div id=\'opFlagOptsPanel\' class=\'opFlagOptsPanel\'><div onclick=\'wfCluster.opFlagOptsCallback(1,'+prcsId+')\'>主办人员</div><div onclick=\'wfCluster.opFlagOptsCallback(2,'+prcsId+')\'>无主办会签</div><div onclick=\'wfCluster.opFlagOptsCallback(3,'+prcsId+')\'>先接收转交</div><div onclick=\'wfCluster.opFlagOptsCallback(4,'+prcsId+')\'>抢占式办理</div></div>').css({left:$(obj).offset().left,top:$(obj).offset().top+20}).appendTo($('body'));
	}
	window.event.cancelBubble = true;
},
opFlagOptsCallback:function(opFlag,prcsId){//经办类型值回填
	var panel = $('#opFlagOptsPanel');
	panel.remove();
	ChangeInputSize();
	var opFlagSpan = $('#opFlagSpan'+prcsId);
	opFlagSpan.html(this.opFlagOpts(opFlag));
	for(var i=0;i<prcsNodeInfos.length;i++){
		if(prcsNodeInfos[i].prcsId.toString()==prcsId.toString()){
			prcsNodeInfos[i].opFlag=opFlag;
			if(opFlag.toString()=='1'){//如果为指定主办人，则开放设置主办人按钮
				$('#opUserBtn'+prcsId).show();
			}else{
				$('#opUserBtn'+prcsId).hide();
				this.clearOpUser(prcsId);
			}
			break;
		}
	}
},
selectOpUser:function(prcsId){//选择主办人
	var data = wfCluster.getPrcsInfo(prcsId);
	selectSingleUserWorkFlow(['opUserInput'+prcsId,'opUserDescInput'+prcsId],'','',data.prcsId+"_"+frpSid,'');
},
selectPrcsUser:function(prcsId){//选择经办人
	var data = wfCluster.getPrcsInfo(prcsId);
	selectUserWorkFlow(['prcsUserInput'+prcsId,'prcsUserDescInput'+prcsId],'','',data.prcsId+"_"+frpSid,'');
},
getParallelParentNode:function(prcsId){//获取并发节点的父节点，没有则返回undefined
	for(var key in parallelChildNodes){
		var data = parallelChildNodes[key];
		var nodes = data.nodes;
		if(nodes.exist(prcsId)){
			return key;
		}
	}
	return undefined;
},
clearOpUser:function(prcsId){//清除主办人相关信息
	$('#opUserInput'+prcsId).attr('value','');
	$('#opUserDescInput'+prcsId).attr('value','');
	$('#opUserSpan'+prcsId).html('');
},
clearPrcsUser:function(prcsId){//清除经办人相关信息
	$('#prcsUserInput'+prcsId).attr('value','');
	$('#prcsUserDescInput'+prcsId).attr('value','');
	$('#prcsUserSpan'+prcsId).html('');
},
selectViewPerson:function(){
	var userFilter = $('#viewPersonFilter').val();
	selectUserWorkFlow(['viewPerson','viewPersonDesc'],'','',userFilter,'');
},
commit:function(){//提交请求数据
	if(error){
		return ;
	}
	
	var requestDatas=[];
	var prcsTo = [];
	//判断是否有可提交节点
	var prcsOptsTables = $('#prcsOpts table');
	for(var i=0;i<prcsOptsTables.length;i++){
		var tb = $(prcsOptsTables[i]);
		if(tb.css('display')!='none'){
			prcsTo.push(tb.attr('prcsId'));
		}
	}
	var isEndNode = false;
	var endNode;
	if(prcsTo.length==0){
		var endNodeCheckbox = nextPrcsContent.find("input[type=checkbox][checked]");
		if(endNodeCheckbox.length==1){
			endNode = this.getPrcsInfo(endNodeCheckbox.parent().attr('prcsId'));
			if(endNode.prcsType.toString()=='2'){
				isEndNode = true;
			}
		}else{
			top.$.jBox.tip("请选择转交步骤节点","info");
			return;
		}
	}

	if(!isEndNode){
		//遍历下一步骤节点
		for(var i=0;i<prcsTo.length;i++){
			var requestItem = {};
			var parallelNode = this.getParallelParentNode(prcsTo[i]);
			if(parallelNode){//存在并发
				requestItem.parallelNode = parallelNode;
			}
			var curPrcs = this.getPrcsInfo(prcsTo[i]);
			requestItem.prcsId = prcsTo[i];
			requestItem.opFlag = curPrcs.opFlag;
			requestItem.prcsUser = $('#prcsUserInput'+prcsTo[i]).val();
			requestItem.opUser = $('#opUserInput'+prcsTo[i]).val();
			requestItem.timeout = $('#timeout'+prcsTo[i]).val();
			if(curPrcs.opFlag.toString()=='1'){//如果指定主办人
				if(requestItem.opUser==''){//如果主办人为空，则提示没有主办人员
					top.$.jBox.tip('[步骤：'+curPrcs.prcsName+']需要指定主办人员',"info");
					return;
				}
			}else if(curPrcs.opFlag.toString()=='2'){//无主办会签
				if(requestItem.prcsUser==''){//如果主办人为空，则提示没有主办人员
					top.$.jBox.tip('[步骤：'+curPrcs.prcsName+']需要指定经办人员',"info");
					return;
				}
			}else{//先接受为主办   ||  抢占式办理
				if(requestItem.prcsUser==''){//如果主办人为空，则提示没有主办人员
					top.$.jBox.tip('[步骤：'+curPrcs.prcsName+']需要指定经办人员',"info");
					return;
				}
			}
			requestDatas.push(requestItem);
		}
	}else{
		var requestItem = {};
		requestItem.prcsId = endNode.prcsId;
		requestItem.opFlag = endNode.opFlag;
		requestDatas.push(requestItem);
	}

	//处理子流程请求
	var childFlowTurnDatas = [];
	var childFlowDivs = $("#childFlow").find("div[disabled!=disabled]");
	for(var i=0;i<childFlowDivs.length;i++){
		var firstInput = $(childFlowDivs[i]).find("input:first");
		if(firstInput.length!=0 && firstInput.val()!=""){
			var childFlowTurnModel = {};
			childFlowTurnModel["prcsUser"] = firstInput.val();
			childFlowTurnModel["prcsId"] = $(childFlowDivs[i]).attr("prcsId");
			childFlowTurnDatas.push(childFlowTurnModel);
		}
	}

	//判断是否选归档目录
	if($("#isArchive").attr("checked")=="checked"){//要归档
		//判断是否选择归档的路径
		if($("#storeHouseId").val()==""||$("#storeHouseId").val()==null){//提示  请选择归档目录
			top.$.jBox.tip("请选择归档目录","info");
		    return;
		}else{//进行归档操作
			var url=contextPath+"/TeeDamFilesController/workFlowArchive.action";
		    var json=tools.requestJsonRs(url,{runId:runId,storeId:$("#storeHouseId").val()});
		}
	}
	
	
	
	var url = contextPath+'/flowRun/turnNextHandler.action';
	var json = tools.requestJsonRs(url,{runId:runId,
		flowId:flowId,
		frpSid:frpSid,
		nextPrcsAlert:getNextPrcsAlert(),
		beginUserAlert:getBeginUserAlert(),
		allPrcsUserAlert:getAllPrcsUserAlert(),
		viewPerson:$("#viewPerson").val(),
		msg:$("#msg").val(),
		prcsEvent:prcsEvent,
		turnModel:tools.jsonArray2String(requestDatas),
		childFlowTurnModel:tools.jsonArray2String(childFlowTurnDatas)
		});
	if(json.rtState){
		//top.$.jBox.tip(json.rtMsg,"info");
		return true;
	}else{
		top.$.jBox.tip(json.rtMsg,"info");
		return false;
	}

}
}

//获取下一步经办人提醒权限值
function getNextPrcsAlert(){
	var priv = 0;
	if($("#nextPrcsAlert1").attr("checked")){
		priv+=1;
	}

	if($("#nextPrcsAlert2").attr("checked")){
		priv+=2;
	}

	if($("#nextPrcsAlert3").attr("checked")){
		priv+=4;
	}
	return priv;
}
//获取发起人提醒权限值
function getBeginUserAlert(){
	var priv = 0;
	if($("#beginUserAlert1").attr("checked")){
		priv+=1;
	}

	if($("#beginUserAlert2").attr("checked")){
		priv+=2;
	}

	if($("#beginUserAlert3").attr("checked")){
		priv+=4;
	}
	return priv;
}
//获取所有经办人提醒权限值
function getAllPrcsUserAlert(){
	var priv = 0;
	if($("#allPrcsUserAlert1").attr("checked")){
		priv+=1;
	}

	if($("#allPrcsUserAlert2").attr("checked")){
		priv+=2;
	}

	if($("#allPrcsUserAlert3").attr("checked")){
		priv+=4;
	}
	return priv;
}

window.onclick=function(){
	var panel = $('#opFlagOptsPanel');
	panel.remove();
}

//选择归档目录
function selArchiveDir(){
	var  url=contextPath+"/system/core/workflow/flowrun/prcs/docArchive.jsp";
	top.bsWindow(url ,"公文归档",{width:"400",height:"200",buttons:
		[
         {name:"确定",classStyle:"btn-alert-blue"},
	 	 {name:"关闭",classStyle:"btn-alert-gray"}
		 ]
		,submit:function(v,h){
		var cw = h[0].contentWindow;
		if(v=="确定"){
		     var s=cw.sel();
		     if(s==null){
		    	 return false;
		     }else{
		    	 $("#storeHouseName").val(s.storeName);
		    	 $("#storeHouseId").val(s.storeId);
		    	 return true;
		     }
		    
		}else if(v=="关闭"){
			return true;
		}
	}}); 
}



//清除归档目录
function clearArchiveDir(){
	$("#storeHouseName").val("");
	 $("#storeHouseId").val("");
}
</script>
<style>
.trun_prcs_box{
	padding:6px;
	cursor:pointer;
}
.trun_parallel_prcs_box{
	padding:6px;
	cursor:pointer;
}
.trun_prcs_disabled_box{
	color:gray;
	text-decoration:line-through;
}
.opFlagStyle{
	color:blue;
	cursor:pointer;
}
.opFlagOptsPanel{
	width:100px;
	position:absolute;
	background:white;
	border:1px solid green;
}
.opFlagOptsPanel div{
	padding:5px;
	text-align:center;
	cursor:pointer;
}
.opFlagOptsPanel div:hover{
	background:#e5f1fa;
}
.prcsOpts_tb{
	width:100%;
	border-collapse:collapse;
	text-align:left;
	border:1px solid #e2e2e2;
	margin-bottom:3px;
}
.prcsOpts_tb td{
	font-size:12px;
	padding:5px;
}
.prcsOpts_header{
	background:#8ab0e6;
	color:white;
}
.prcsOpts_opUser{
	
}
.prcsOpts_prcsUser{
	
}
button{
font-size:12px;
}
</style>
</head>
<body onload="doInit()" style="font-size:12px">
<div id="center">
<table class="none-table" style="font-size:12px;width:100%">
    <tbody id="archiveBd" style="display: none;">
	    <tr>
			<td colspan="3" style="padding:5px;background:#f0f0f0"><b>归档选项</b></td>
		</tr>
		<tr>
			<td colspan="3" style="padding: 5px">
			    &nbsp;<input type="checkbox" name="isArchive" id="isArchive" />处理后归档
			           &nbsp;&nbsp;归档到：<input type="text" name="storeHouseName"  id="storeHouseName"/>&nbsp;<a href="#" onclick="selArchiveDir();">选择</a>&nbsp;&nbsp;<a href="#" onclick="clearArchiveDir();">清除</a>
			          <input type="hidden" name="storeHouseId"  id="storeHouseId"/>
			</td>
		</tr>
    
    </tbody>
    

	<tr>
		<td colspan="3" style="padding:5px;background:#f0f0f0"><b>请选择下一步骤</b></td>
	</tr>
	<tr>
		<td id="nextPrcsContent" style="width:150px;vertical-align:top;min-height: 150px">
			
		</td>
		<td id="parallelPrcsContent" style="width:150px;display:none;vertical-align:top">
			
		</td>
		<td id="prcsOpts"  style="font-size:12px;vertical-align:top">
    		
    	</td>
	</tr>
	<tr>
		<td id="childFlow" style="text-align:left"  colspan="3">
    	
    	</td>
	</tr>
	<tr>
		<td id="passing" style="display:none"  colspan="3"  class="TableData">
		    <table style="border:0px;font-size:12px;width:100%">
		    	<tr>
		    		<td style="border:0px;font-size:12px;padding:5px;background:#f0f0f0;font-weight:bold" colspan="2">流程传阅</td>
		    	</tr>
		    	<tr>
		    		<td style="border:0px;font-size:12px;width:75px">选择传阅人：</td>
		    		<td style="border:0px;font-size:12px">
		    			<input type="text" class="changelistener" id="viewPersonDesc" class="BigTextarea" readonly style="width:0px;border:0px" />
				    	<input id="viewPerson" type="hidden" name="viewPerson"/>
				    	<input id="viewPersonFilter" type="hidden" name="viewPersonFilter"/>
				    	&nbsp;<a href="javascript:void(0)" onclick="wfCluster.selectViewPerson()">选择</a>&nbsp;<a href="javascript:void(0)" onclick="clearData('viewPerson','viewPersonDesc')">清空</a>
		    		</td>
		    	</tr>
		    </table>
		 </td>
	</tr>
	<tr>
		<td  colspan="3" style="padding:5px;background:#f0f0f0">
    		<b>事务提醒</b>
    	</td>
    </tr>
    <tr>
    	<td  colspan="3">
    		<div>
				下步办理人：
				<input id="nextPrcsAlert1" type="checkbox" /><label for="nextPrcsAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
				<input id="nextPrcsAlert2" type="checkbox" /><label for="nextPrcsAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
				<%-- <input id="nextPrcsAlert3" type="checkbox" /><label for="nextPrcsAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label> --%>
				<br/>
				<%-- 流程发起人：
				<input id="beginUserAlert1" type="checkbox" /><label for="beginUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png"  title="内部消息"/></label>
				<input id="beginUserAlert2" type="checkbox" /><label for="beginUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
				<input id="beginUserAlert3" type="checkbox" /><label for="beginUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label>
				<br/>
				全部经办人：
				<input id="allPrcsUserAlert1" type="checkbox" /><label for="allPrcsUserAlert1"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/sms.png" title="内部消息"/></label>
				<input id="allPrcsUserAlert2" type="checkbox" /><label for="allPrcsUserAlert2"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/mobile_sms.png"  title="手机短信"/></label>
				<input id="allPrcsUserAlert3" type="checkbox" /><label for="allPrcsUserAlert3"><img align=absMiddle src="<%=contextPath %>/common/images/workflow/email.png"  title="邮件提醒"/></label> --%>
			</div>
			<!-- <DIV style="margin-top: 5px;" ><b>提醒内容：</b>
				<input id="msg" class="BigInput" value="" style="width:500px" type="text" />
			</DIV> -->
    	</td>
	</tr>
</table>
</div>
</body>
</html>
