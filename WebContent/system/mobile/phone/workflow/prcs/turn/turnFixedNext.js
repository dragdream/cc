var wfCluster = {
init:function(){//初始化各项参数
	window.history.forward(1);
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
		parallelChildNodes = data.parallelChildNodes;
		prcsNodeInfos = data.prcsNodeInfos;
		childFlowNodeInfos = data.childFlowNodeInfos;
		diabledChildFlowNodes = data.diabledChildFlowNodes;
		autoTurn = data.autoTurn;//自动转交
		
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
		$("body").html("<br/><center><h3>"+json.rtMsg+"</h3></center>");
//		messageMsg(json.rtMsg,"center","info");
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
			if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
				window.location = "../../index.jsp";
			}else{
				CloseWindow();
			}
		}
	}
},
renderChildFlow:function(){
	var childFlow = $("#childFlow");
	childFlow.hide();
	var html = "<b>触发子流程：</b>";
	for(var i=0;i<childFlowNodeInfos.length;i++){
		childFlow.show();
		var childFlowNodeInfo = childFlowNodeInfos[i];
		var id = "childFlow_"+childFlowNodeInfo.prcsId;
		if(diabledChildFlowNodes.exist(childFlowNodeInfo.prcsId)){//条件不符
			html+="<div disabled>";
			html+=""+childFlowNodeInfo.prcsName+"：";
			html+="<span style='color:red'>条件不符</span>";
		}else{//条件满足
			html+="<div prcsId='"+childFlowNodeInfo.prcsId+"'>";
			html+=""+childFlowNodeInfo.prcsName+"：";
			//selectSingleUser(['opUserInput'+prcsId,'opUserDescInput'+prcsId],'','',data.userFilter,'');
			//如果是自动选人
			if(childFlowNodeInfo.autoSelect){
				html+="<input type=\"hidden\" class=\"BigInput\" id=\""+id+"\" value=\""+childFlowNodeInfo.prcsUser+"\"/>";
				html+="<input type=\"text\" readonly class=\"BigInput readonly\" id=\""+id+"_Desc\" value=\""+childFlowNodeInfo.prcsUserDesc+"\"/>";
			}else{
				html+="<input type=\"hidden\" class=\"BigInput\" id=\""+id+"\" />";
				html+="<input type=\"text\" readonly class=\"BigInput readonly\" id=\""+id+"_Desc\" />";
			}
			if(childFlowNodeInfo.userLock==0){
				continue;
			}
			//多实例步骤
			if(childFlowNodeInfo.multiInst==1){
				html+="<a href=\"javascript:void(0)\" onclick=\"selectUserWorkFlow('"+id+"','"+id+"_Desc','"+childFlowNodeInfo.prcsId+"_"+frpSid+"','')\">选择</a>&nbsp;<a href=\"javascript:void(0)\" onclick=\"clearData('"+id+"','"+id+"_Desc')\">清空</a>";
			}else{//单实例步骤
				html+="<a href=\"javascript:void(0)\" onclick=\"selectSingleUserWorkFlow('"+id+"','"+id+"_Desc','"+childFlowNodeInfo.prcsId+"_"+frpSid+"','')\">选择</a>&nbsp;<a href=\"javascript:void(0)\" onclick=\"clearData('"+id+"','"+id+"_Desc')\">清空</a>";
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
		html+='<tr>';
		html+='<td class=\'prcsOpts_opUser\'><span id=\'opFlagSpan'+data.prcsId+'\' class=\'opFlagStyle\' onclick=\'wfCluster.showOpFlagOptsPanel(this,'+userLock+','+data.prcsId+')\'>'+opFlagDesc+'</span>：<span id\'opUserSpan'+data.prcsId+'\'></span><input type=\'\' value=\''+(data.opFlag.toString()=='1'?opUser:'')+'\' hidden id=\'opUserInput'+data.prcsId+'\'/><input type=\'\' id=\'opUserDescInput'+data.prcsId+'\' value=\''+(data.opFlag.toString()=='1'?opUserDesc:'')+'\' class=\'BigInput\' style=\'border:1px solid gray\' readonly/><input type=\'button\' id=\'opUserBtn'+data.prcsId+'\' class=\'btn btn-default\' onclick=\'wfCluster.selectOpUser('+data.prcsId+')\' value=\'选择\' '+(data.opFlag.toString()!='1'?'disabled':'')+' /></td>';
		html+='</tr>';
		html+='<tr>';
		html+='<td class=\'prcsOpts_prcsUser\'><span>经办人员</span>：<span id=\'prcsUsersSpan'+data.prcsId+'\'></span><input type=\'\' id=\'prcsUserInput'+data.prcsId+'\' value=\''+(prcsUser)+'\' hidden/><textarea id=\'prcsUserDescInput'+data.prcsId+'\' readonly class=\'BigTextarea\' style=\'height:50px;width:98%\'>'+prcsUserDesc+'</textarea><input type=\'button\' class=\'btn btn-default\' value=\'选择\' onclick=\'wfCluster.selectPrcsUser('+data.prcsId+')\' /></td>';
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
	if(disabledPrcsNodes.exist(data.prcsId)){//如果存在不符合条件的节点，则渲染
		className += ' trun_prcs_disabled_box';
		msg = '&nbsp;<span style=\'color:red\'>(不符合条件)</span>';
	}else if(parallelPrcsNodes.exist(data.prcsId)){
		msg = '&nbsp;<span style=\'color:green\'>(并发)</span>';
	}
	var html = '<div id=\''+(prcsNodePrefix.replace('#','')+data.prcsId)+'\' prcsId=\''+data.prcsId+'\' class=\''+className+'\' onclick=\'wfCluster.prcsSelectEvent(this)\'><input type=\'checkbox\' />'+data.prcsName+msg+'</div>';
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
		if(disabledPrcsNodes.exist(data.prcsId)){//如果存在不符合条件的节点，则渲染
			className += ' trun_prcs_disabled_box';
			msg = '&nbsp;<span style=\'color:red\'>(不符合条件)</span>';
		}

		var html = $('<div id=\'prcsTo'+data.prcsId+'\' title=\''+((data.conditionMsg==null)?data.prcsName:data.conditionMsg)+'\' class=\''+className+'\' prcsId=\''+data.prcsId+'\' onclick=\'wfCluster.parallelSelectEvent(this)\'><input type=\'checkbox\' checked/>'+data.prcsName+msg+'</div>');
		
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
	var opFlagSpan = $('#opFlagSpan'+prcsId);
	opFlagSpan.html(this.opFlagOpts(opFlag));
	for(var i=0;i<prcsNodeInfos.length;i++){
		if(prcsNodeInfos[i].prcsId.toString()==prcsId.toString()){
			prcsNodeInfos[i].opFlag=opFlag;
			if(opFlag.toString()=='1'){//如果为指定主办人，则开放设置主办人按钮
				$('#opUserBtn'+prcsId).removeAttr('disabled');
			}else{
				$('#opUserBtn'+prcsId).attr('disabled','disabled');
				this.clearOpUser(prcsId);
			}
			break;
		}
	}
},
selectOpUser:function(prcsId){//选择主办人
	var data = wfCluster.getPrcsInfo(prcsId);
	selectSingleUserWorkFlow('opUserInput'+prcsId,'opUserDescInput'+prcsId,data.prcsId+"_"+frpSid);
},
selectPrcsUser:function(prcsId){//选择经办人
	var data = wfCluster.getPrcsInfo(prcsId);
	selectUserWorkFlow('prcsUserInput'+prcsId,'prcsUserDescInput'+prcsId,data.prcsId+"_"+frpSid);
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
			}else{//先接受为主办   或者  抢占式办理
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
		//top.$.jBox.tip(json.rtMsg,"info");
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

function selectSingleUserWorkFlow(idsInput,namesInput,userFilter){
	var sp = userFilter.split("_");
	selectSingleUser(idsInput,namesInput,sp[0],sp[1]);
}

function selectUserWorkFlow(idsInput,namesInput,userFilter){
	var sp = userFilter.split("_");
	selectUser(idsInput,namesInput,sp[0],sp[1]);
}

function tuenNext(){
	$("#turnNextBtn").attr("disabled","disabled");
	try{
		if(wfCluster.commit()){
			if(userAgent.indexOf("DingTalk")!=-1 || userAgent.indexOf("MicroMessenger")!=-1){
				window.location = "../../index.jsp";
			}else{
				CloseWindow();
			}
		}else{
			$("#turnNextBtn").removeAttr("disabled");
		}
	}catch(e){
		$("#turnNextBtn").removeAttr("disabled");
	}
	
}