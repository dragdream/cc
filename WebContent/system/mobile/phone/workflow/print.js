function doInit(){
	if(window.external && window.external.setTitle){
		window.external.setTitle("原始表单");
	}
	
	//setTitle("原始表单");//設置标题
	//查阅
	var url = contextPath+"/flowRun/flowRunLookup.action";
	var json = tools.requestJsonRs(url,{runId:runId});
	
	
	var url = contextPath+"/flowRun/getFormPrintData.action";
	var json = tools.requestJsonRs(url,{runId:runId,view:view,frpSid:frpSid});
	if(json.rtState){
		var form = json.rtData.form;
		var css = json.rtData.css;
		var attach = json.rtData.attach;
		var feedback = json.rtData.feedback;
		var graph = json.rtData.graph;
		var viewInfo = json.rtData.viewInfo;
		var docInfo = json.rtData.docInfo;
		if(form){
			$("#css").html("<style>"+css+"</style>");
			$("#form").html(form).show();
		}
		
		
		//签章检验与加载
// 		try{
// 			loadSignData();
// 		}catch(e){}
 		try{
 			loadCtrlFeedbackDatas();
 		}catch(e){}
		loadSignDataPng();
		
		loadCtrlSignDataPng();
		LoadH5HwData();
		LoadMobileHwData();
		try{
			loadMobileSealData();
		}catch(e){}
	}else{
		messageMsg(json.rtMsg,"content","info");
	}
	
	//将表单数据导入parent框架中
	parent.FORM_PRINT_DATA = {};
	$(".FORM_PRINT").each(function(i,obj){
		var title = $(obj).attr("title");
		var val = $(obj).val();
		parent.FORM_PRINT_DATA[title] = val;
	});
}

//加载移动签批图片
function LoadMobileHwData(){
	for(var i=0;i<mobileHwArray.length;i++){
		var itemId = mobileHwArray[i];
		
		var targetObject = $("#MOBILE_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_HW_POS_IMG_"+itemId+"\" onerror=\"this.style.display = 'none'\"/>").appendTo($("#MOBILE_HW_POS_"+itemId));
		}
		targetObject.attr("src",mobileHwStores[i]).css({"position":"absolute"}).show();
		
	}
}

//加载移动签批图片
function LoadH5HwData(){
	for(var i=0;i<h5HwArray.length;i++){
		var itemId = h5HwArray[i];
		var targetItem = $("#"+itemId);
		var targetObject = $("#H5_HW_POS_IMG_"+itemId);
		if(targetObject.length==0){
			targetObject = $("<img id=\"H5_HW_POS_IMG_"+itemId+"\" height=\""+targetItem.attr("h")+"\"  onerror=\"this.style.display = 'none'\"  width=\""+targetItem.attr("w")+"\"/>").appendTo($("#H5_HW_POS_"+itemId));
		}
		targetObject.attr("src",h5HwStores[i]).show();
		targetObject.css({height:targetItem.attr("h"),width:targetItem.attr("w")});
		if(h5HwStores[i]==""){
			targetObject.hide();
		}
	}
}

function _DownloadByIframe(attachId){
	var target = $("#_downloadIframe");
	if(target.length==0){
		target = $("<iframe style='display:none'></iframe>").appendTo($("body"));
	}
	target.attr("src",contextPath+"/imAttachment/downFile.action?id="+attachId+"&vt="+new Date().getTime());
}

//展示文件
function GetFile(attachId,fileName,attachName){
	if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		_DownloadByIframe(attachId);
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		_DownloadByIframe(attachId);
	}else{
		if(window.external && window.external.GetFile){//安卓
			window.external.GetFile(parseInt(attachId+""),fileName,attachName);
		}else{//ios
			document.location = "zatp:{\"method\":\"GetFile:\",\"params\":[\""+attachId+"\",\""+fileName+"\",\""+attachName+"\"]}";
		}
	}
}

function loadSignDataPng(){
for(var i=0;i<stores.length;i++){
var itemId = stores[i];
var signData = sealSignDatas[i];
var pos = sealSignPos[i].split(",");
$("<img />").attr("src",signData).css({position:"absolute",left:pos[0]+"px",top:pos[1]+"px"}).appendTo($("#SIGN_POS_"+itemId).css({position:"absolute"}));
}
}
function loadCtrlSignDataPng(){
	for(var i=0;i<ctrlRandArray.length;i++){
		var sp = ctrlRandArray[i].split("_");
		var itemId = sp[0]+"_"+sp[1];
		var random = sp[2];
		var signData = ctrlRandPngArray[i];
		var pos = ctrlRandPngPos[i].split(",");
		$("<img />").attr("src",signData).css({position:"absolute",left:pos[0]+"px",top:pos[1]+"px"}).appendTo($("#SIGN_POS_CTRL_"+itemId+"_"+random).css({position:"absolute"}));
	}
	
	for(var i=0;i<ctrlRandArray4Pic.length;i++){
		var name = ctrlRandArray4Pic[i];
		$("#SIGN_POS_CTRL_"+name).css({position:"absolute"});
		var signData = ctrlRandPngArray4Pic[i];
		if(signData==""){
			continue;
		}
		var pos = signData.split(",");
		$("<img />").attr("src",pos[0]+","+pos[1]).css({position:"absolute",left:pos[3]+"px",top:pos[4]+"px"}).appendTo($("#SIGN_POS_CTRL_"+name).css({position:"absolute"}));
	}
}

function loadMobileSealData(){
	for(var i=0;i<mobileStores.length;i++){
		var itemId = mobileStores[i];
		var sealDatas = mobileSealSignDatas[i].split(",");
		var md5 = mobileMD5Datas[i];
		
		var targetObject = $("#MOBILE_SEAL_IMG_"+itemId);
		if(targetObject.length==0){
		 targetObject = $("<img id=\"MOBILE_SEAL_IMG_"+itemId+"\" target=\""+itemId+"\" onerror=\"this.style.display = 'none'\" />").appendTo($("#MOBILE_SIGN_POS_"+itemId));
		}
		targetObject.attr("src",sealDatas[0]+","+sealDatas[1]).css({"position":"absolute",left:sealDatas[3]+"px",top:sealDatas[4]+"px"});
		
		if(MD5(md5)==sealDatas[2]){//验证生效
			
		}else{//验章失效
// 			targetObject.css({opacity:0.1});
			grayscale(targetObject);
		}
	}
}

function loadSignData(){
	var DWebSignSeal = document.getElementById("DWebSignSeal");
	  try {
	    for(var i=0;i<storeDatas.length;i++) {
	    	DWebSignSeal.SetStoreData(storeDatas[i]);
	    }
	    DWebSignSeal.ShowWebSeals();


	    for(var i=0;i<stores.length;i++){
	    	var hw = stores[i]+"_hw";
	    	var seal = stores[i]+"_seal";
	    	var strObjectName = DWebSignSeal.FindSeal(hw,2);
	    	if(strObjectName!=""){
	    		DWebSignSeal.SetSealSignData(strObjectName,sealSignValideDatas[i]);
	    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
	    		DWebSignSeal.LockSealPosition(strObjectName);
			}
	    	strObjectName = DWebSignSeal.FindSeal(seal,2);
	    	if(strObjectName!=""){
	    		DWebSignSeal.SetSealSignData(strObjectName,sealSignValideDatas[i]);
	    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
	    		DWebSignSeal.LockSealPosition(strObjectName);
			}
	    	
		}
	  } catch (err) {
		    //alert(err);
	 }
}

function loadCtrlFeedbackDatas(){
	var DWebSignSeal = document.getElementById("DWebSignSeal");
	for(var i=0;i<ctrlRandArray.length;i++){
		var name = ctrlRandArray[i];
		var ctrlSign = $("#CTRL_SIGN_"+name).val();
		var ctrlContent = $("#CTRL_CONTENT_"+name).val();

		if(ctrlSign && ctrlContent){
			//加载签章数据
	        DWebSignSeal.SetStoreData(ctrlSign);
		}
	}
	DWebSignSeal.ShowWebSeals();
	
	for(var i=0;i<ctrlRandArray.length;i++){
		var datas = ctrlRandArray[i];
		var name = datas.split("_")[0]+"_"+datas.split("_")[1];
		var rand0 = datas.split("_")[2];
		var ctrlSign = $("#CTRL_SIGN_"+datas).val();
		var ctrlContent = $("#CTRL_CONTENT_"+datas).val();
		var hw = name+"_hw_"+rand0;
		var seal = name+"_seal_"+rand0;
		var strObjectName = DWebSignSeal.FindSeal(hw,2);
    	if(strObjectName!=""){
    		DWebSignSeal.SetSealSignData(strObjectName,ctrlContent);
    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
    		DWebSignSeal.LockSealPosition(strObjectName);
		}
    	strObjectName = DWebSignSeal.FindSeal(seal,2);
    	if(strObjectName!=""){
    		DWebSignSeal.SetSealSignData(strObjectName,ctrlContent);
    		DWebSignSeal.SetMenuItem(strObjectName,4+8+10);
    		DWebSignSeal.LockSealPosition(strObjectName);
		}
	}
}

/**
 * 渲染附件
 */
function renderAttach(attach){
	var tbody_attach = $("#tbody_attach");
	for(var i=0;i<attach.length;i++){
		var item = attach[i];
		//render+="<div><b>第"+item.prcsId+"步</b></div>";
		item.priv = 1+2;
		var fileItem = tools.getAttachElement(item);
		tbody_attach.append(fileItem);
	}
}

/**
 * 渲染会签
 */
function renderFeedback(feedback){
	var render = "";
	var tbody_feedback = $("#tbody_feedback");
	
	for(var i=0;i<feedback.length;i++){
		var item = feedback[i];
		var tr = $("<tr></tr>");
		var td1 = $("<td style=\"text-align:left;border:1px solid #e2e2e2;padding:5px;\"></td>");
		td1.append("<div><b>第"+item.prcsId+"步&nbsp;"+item.userName+"&nbsp;&nbsp;"+item.editTimeDesc+"</b></div>");
		if(item.voiceId!="" && item.voiceId && item.voiceId!=null){
			td1.append("语音：<video type='video/mp3' style=\"cursor:pointer\" onclick='this.play();' src='/attachmentController/downFile.action?id="+item.voiceId+"' title=\"点击播放语音\" width='20px' height='20px' poster=\"/common/images/workflow/voice_play.png\"></video>");
		}
		td1.append("<div>"+item.content+"</div>");
		
		var attachList = item.attachList;
		if(attachList!=null){
			for(var j=0;j<attachList.length;j++){
				var attach = attachList[j];
				attach.priv = 1+2;
				var fileItem = tools.getAttachElement(attach);
				td1.append(fileItem);
			}
		}
		tr.append(td1);
		tbody_feedback.append(tr);
	}
}

/**
 * 流程图
 */
function renderGraph(prcsList){
	var tbody = $("#tbody_graph");
	var group = groupBy(prcsList);
	var render = "";
	for(var i=0;i<group.length;i++){
		render+="<tr>";
		var set = group[i];
		var rows = 0;
		for(var key in set){
			rows++;
		}
		render+="<td rowspan="+rows+" style='border:1px solid #e2e2e2;;padding:5px;'>第"+(i+1)+"步</td>";
		for(var key in set){
			var arr = set[key];
			if(arr[0].flowPrcsId!=0){
				render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>步骤："+arr[0].prcsName+"</td>";
			}
			render+="<td style=\"text-align:left;border:1px solid #e2e2e2;padding:5px;\">";
			var title = "";
			for(var j=0;j<arr.length;j++){
				var prcsInfo = arr[j];
				if(prcsInfo.topFlag==1){//主办
					title+="主办："+prcsInfo.prcsUserName+" ";
				}else{//经办
					title+="经办："+prcsInfo.prcsUserName+" ";
				}

				if(prcsInfo.flag==1){//未接收
					title+="<span style=\"color:gray\">未接收</span>";
				}else if(prcsInfo.flag==2){//办理中
					title+="<span style=\"color:red\">办理中</span> 用时："+prcsInfo.passedTime;
				}else if(prcsInfo.flag==3 || prcsInfo.flag==4){//已办结
					title+="<span style=\"color:green\">已办结</span> 用时："+prcsInfo.passedTime;
				}

				title+="<br/>";
				if(prcsInfo.beginTimeDesc!=""){
					title+="接收时间："+prcsInfo.beginTimeDesc;
				}
				if(prcsInfo.endTimeDesc!=""){
					title+="&nbsp;&nbsp;办结时间："+prcsInfo.endTimeDesc;
				}
				title+="<br/><br/>";
			}
			render+=title;
			render+="</td>";
			if(rows!=1){
				render+="</tr>";
				render+="<tr>";
			}
		}
		render+="</tr>";
	}
	tbody.append(render);
}

function renderViewInfo(viewInfos){
	var tbody = $("#tbody_viewinfo");
	var render = "";
	for(var i=0;i<viewInfos.length;i++){
		var info = viewInfos[i];
		render+="<tr>";
		render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>"+info.viewUsername+"</td>";
		render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>"+(info.viewFlag==0?"<span style='color:red'>未查看</span>":"<span style='color:green'>已查看</span>")+"</td>";
		render+="<td style='border:1px solid #e2e2e2;;padding:5px;'>"+info.viewTimeDesc+"</td>";
		render+="</tr>";
	}
	tbody.append(render);
}

function renderDocInfo(docInfo){
	  
}

function groupBy(prcsList){
	var group = new Array;
	var maxPrcsId = prcsList[prcsList.length-1].prcsId;
	for(var i=1;i<=maxPrcsId;i++){
		var set = {};
		for(var j=0;j<prcsList.length;j++){
			var prcsId = prcsList[j].prcsId;
			var flowPrcsId = prcsList[j].flowPrcsId;
			if(prcsId!=i){
				continue;
			}
			var id = prcsId+""+flowPrcsId;
			var arr = set[id];
			if(!arr){
				arr = new Array();
			}
			arr.push(prcsList[j]);
			set[id] = arr;
		}
		group.push(set);
	}
	return group;
}