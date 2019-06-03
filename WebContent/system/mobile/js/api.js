function _DownloadByIframe(attachId){
	var target = $("#_downloadIframe");
	if(target.length==0){
		target = $("<iframe style='display:none'></iframe>").appendTo($("body"));
	}
	target.attr("src",contextPath+"/imAttachment/downFile.action?id="+attachId+"&vt="+new Date().getTime());
}

//展示文件
function GetFile(attachId,fileName,attachName){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		_DownloadByIframe(attachId);
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
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

//批量预览图片
function PicExplore(attachIds,attachNames,curId){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC

	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉

	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信

	}else{
		if(window.external && window.external.PicExplore){//安卓
			window.external.PicExplore(attachIds,attachNames,curId);
		}else{//ios
			document.location = "zatp:{\"method\":\"PicExplore:\",\"params\":[\""+attachIds+"\",\""+attachNames+"\",\""+curId+"\"]}";
		}
	}
}



//编辑文件
function EditFile(attachId,fileName,attachName){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		alert("仅安卓版本支持在线编辑");
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		alert("仅安卓版本支持在线编辑");
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		alert("仅安卓版本支持在线编辑");
	}else{
		if(window.external && window.external.GetFile){//安卓
			window.external.EditFile(parseInt(attachId+""),fileName,attachName);
		}else{//ios
			alert("仅安卓版本支持在线编辑");
		}
	}
}

//（旧版本，作废）打开版式文件 aipDocId 版式文件id  runId 流程id    type 1：只读   2：编辑，
function OpenAipFile(aipDocId,runId,type){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		alert("版式文件组件仅支持安卓版本");
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		alert("版式文件组件仅支持安卓版本");
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		alert("版式文件组件仅支持安卓版本");
	}else{
		if(window.external && window.external.OpenAipFile){//安卓
			window.external.OpenAipFile(aipDocId,runId,type);
		}else{//ios
			document.location = "zatp:{\"method\":\"OpenAipFile:\",\"params\":[\""+aipDocId+"\",\""+runId+"\",\""+type+"\"]}";
		}
	}
}

//打开版式文件 fileId 版式文件id  type 1：只读   2：编辑   params：模版参数{a:1,b:2}   sealData 盖章数据[{id:xxx,name:xxx}]
function OpenAip(fileId,fileName,type,params,sealData){
	if(!params){
		params = {};
	}
	if(!sealData){
		sealData = [];
	}
	if(type==1){
		type = "";
	}else if(type==2){
		type = 1;
	}
	params = string2Unicode(jsonObj2String(params));
	sealData = string2Unicode(jsonArray2String(sealData));
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		alert("版式文件组件仅支持安卓版本");
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		alert("版式文件组件仅支持安卓版本");
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		alert("版式文件组件仅支持安卓版本");
	}else{
		if(window.external && window.external.OpenAip){//安卓
			window.external.OpenAip(fileId,fileName,type,params,sealData);
		}else{//ios
			document.location = "zatp:{\"method\":\"OpenAip:\",\"params\":[\""+fileId+"\",\""+fileName+"\",\""+type+"\",\""+params+"\",\""+sealData+"\"]}";
		}
	}
}

//设置框架标题
function SetTitle(title){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		dd.biz.navigation.setTitle({
		    title : title,//控制标题文本，空字符串表示显示默认文本
		    onSuccess : function(result) {
		        
		    },
		    onFail : function(err) {}
		});
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		
	}else{
		if(window.external && window.external.setTitle){//安卓
			window.external.setTitle(title);
		}else{//ios
			//document.location = "zatp:{\"method\":\"SetTitle:\",\"params\":[\""+title+"\"]}";
		}
	}
}

//设置框架标题
function GeoLocation(callback){
	window.LocationCallBackFunc = callback;
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		
	}else{
		if(window.external && window.external.geoLocation){//安卓
			window.external.geoLocation("LocationCallBackFunc");
		}else{//ios
			document.location = "zatp:{\"method\":\"GeoLocation:\"}";
		}
	}
}

//框架提示内容
function Alert(content){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		DingTalkPC.device.notification.toast({
		    type: "information", //toast的类型 alert, success, error, warning, information, confirm
		    text: content, //提示信息
		    duration: 4, //显示持续时间，单位秒，最短2秒，最长5秒
		    delay: 0, //延迟显示，单位秒，默认0, 最大限制为10
		    onSuccess : function(result) {
		        /*{}*/
		    },
		    onFail : function(err) {}
		});
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		dd.device.notification.toast({
		    icon: '', //icon样式，有success和error，默认为空 0.0.2
		    text: content, //提示信息
		    duration: 4, //显示持续时间，单位秒，默认按系统规范[android只有两种(<=2s >2s)]
		    delay: 0, //延迟显示，单位秒，默认0
		    onSuccess : function(result) {
		        /*{}*/
		    },
		    onFail : function(err) {}
		});
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		
	}else{
		if(window.external && window.external.alert){//安卓
			window.external.alert(content);
		}else{//ios
			document.location = "zatp:{\"method\":\"Alert:\",\"params\":[\""+content+"\"]}";
		}
	}
}

//打开窗体
function OpenWindow(title,url,showTitleBar){
	if(showTitleBar==undefined){
		showTitleBar = true;
	}
	
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		window.location = url;
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		window.location = url;
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		window.location = url;
	}else{
		if(window.external && window.external.open){//安卓
			window.external.open(title,url,showTitleBar);
		}else{//ios
			document.location = "zatp:{\"method\":\"OpenWindow:\",\"params\":[\""+title+"\",\""+url+"\",\""+showTitleBar+"\"]}";
		}
	}
}

//关闭窗体
function CloseWindow(){
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		wx.closeWindow();
	}else{
		if(window.external && window.external.open){//安卓
			window.external.close();
		}else{//ios
			document.location = "zatp:{\"method\":\"CloseWindow:\",\"params\":[]}";
		}
	}
}

//二维码扫描
function QrCode(callback){
	window.QrCodeCallBaclFunc = callback;
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		dd.biz.util.scan({
		    type: 'qrCode',//type为qrCode或者barCode
		    onSuccess: function(data) {
		    	callback(data.text);
		    },
		   onFail : function(err) {
		   }
		});
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		wx.scanQRCode({
		    desc: 'scanQRCode desc',
		    needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
		    	var result = res.resultStr;
		    	callback(result);
			}
		});
	}else{
		if(window.external && window.external.open){//安卓
			window.external.QrCode("QrCodeCallBaclFunc");
		}else{//ios
			document.location = "zatp:{\"method\":\"QrCode:\",\"params\":[\"QrCodeCallBaclFunc\"]}";
		}
	}
}

/**
 * 语音录入
 * @param callback
 * @param model
 * @param modelId
 */
function Voice(callback,model,modelId){
	window.VoiceCallBaclFunc = callback;
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		alert("钉钉暂不支持语音录制，请使用协同办公移动客户端");
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		alert("微信暂不支持语音录制，请使用协同办公移动客户端");
	}else{
		if(window.external && window.external.open){//安卓
			window.external.Voice("VoiceCallBaclFunc",model,modelId);
		}else{//ios
			document.location = "zatp:{\"method\":\"Voice:\",\"params\":[\"VoiceCallBaclFunc\",\""+model+"\",\""+modelId+"\"]}";
		}
	}
}

/**
 * 获取图片
 * @param callback
 * @param type 1：图片  2：文件  3：图片和文件
 * @param count 一次性上传数量
 */
function TakePhoto(callback,type,count){
	if(!type){
		type = 3;//图片和文件
	}
	if(!count){
		count = 9;//一次性上传数量
	}
	
	window.TakePhotoCallBackFunc = callback;
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		DingTalkPC.biz.util.uploadImage({
		    multiple: true, //是否多选，默认false
		    max: count, //最多可选个数
		    onSuccess : function(result) {
		       var files = [];
     	       for(var i=0;i<result.length;i++){
     	    	   var split = result[i].split(".");
     	    	   files.push({name:"图片"+new Date().getTime()+"."+split[split.length-1],path:result[i]});
     	       }
     	       window.TakePhotoCallBackFunc(files);
		    },
		    onFail : function() {
		    	alert("请正确设置应用的APPID");
		    }
		});
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		
		dd.device.notification.actionSheet({
		    title: "上传附件", //标题
		    cancelButton: '取消', //取消按钮文本
		    otherButtons: ["相册","拍照"],
		    onSuccess : function(result) {
		        if(result.buttonIndex==0){
		        	dd.biz.util.uploadImage({
		        	    multiple: true, //是否多选，默认false
		        	    compression:true,
		        	    max: count, //最多可选个数
		        	    onSuccess : function(result) {
		        	       var files = [];
		        	       for(var i=0;i<result.length;i++){
		        	    	   var split = result[i].split(".");
		        	    	   files.push({name:"图片"+new Date().getTime()+"."+split[split.length-1],path:result[i]});
		        	       }
		        	       window.TakePhotoCallBackFunc(files);
		        	    },
		        	    onFail : function() {
		        	    	alert("请正确设置应用的APPID");
		        	    }
		        	});
		        }else if(result.buttonIndex==1){
		        	dd.biz.util.uploadImageFromCamera({
		        	    compression:true,//(是否压缩，默认为true)
		        	    onSuccess : function(result) {
		        	       var files = [];
		        	       for(var i=0;i<result.length;i++){
		        	    	   var split = result[i].split(".");
		        	    	   files.push({name:"图片"+new Date().getTime()+"."+split[split.length-1],path:result[i]});
		        	       }
		        	       window.TakePhotoCallBackFunc(files);
		        	    },
		        	    onFail : function() {
		        	    	alert("请正确设置应用的APPID");
		        	    }
		        	});
		        }
		    },
		    onFail : function(err) {
		    	alert("请正确设置应用的APPID");
		    }
		});
		
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		wx.chooseImage({
		    count: count, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        wx.uploadImage({
				    localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
				    isShowProgressTips: 1,// 默认为1，显示进度提示
				    success: function (res) {
				        var serverId = res.serverId; // 返回图片的服务器端ID
				        var fileName = "图片"+new Date().getTime()+".png";
			     	    window.TakePhotoCallBackFunc([{name:fileName,path:serverId+"`"+fileName}]);
				    }
				});
		    }
		});
		
	}else{
		if(window.external && window.external.open){//安卓
			window.external.TakePhoto("TakePhotoCallBackFunc",type,count);
		}else{//ios
			document.location = "zatp:{\"method\":\"TakePhoto:\",\"params\":[\"TakePhotoCallBackFunc\",\""+count+"\"]}";
		}
	}
}

/**
 * 上传图片
 * @param callback
 * @param filesArray
 */
function UploadPhoto(callback,filesArray,model,modelId){
	window.uploadPhotoCallBackFunc = callback;
	if(filesArray==undefined || filesArray==null || filesArray.length==0){
		callback([]);
		return;
	}
	if(userAgent.indexOf("dingtalk-win")!=-1){//钉钉PC
		$.ajax({
		    type: 'post',
		    url: contextPath+"/dingding/downloadDingFile.action" ,
		    data: {files:filesArray.join(","),model:model,modelId:modelId} ,
		    success: function(json){
		    	window.uploadPhotoCallBackFunc(json.rtData);
		    },
		    dataType: "json",
		    error:function(){
		    	
		    }
		});
	}else if(userAgent.indexOf("DingTalk")!=-1){//钉钉
		$.ajax({
		    type: 'post',
		    url: contextPath+"/dingding/downloadDingFile.action" ,
		    data: {files:filesArray.join(","),model:model,modelId:modelId} ,
		    success: function(json){
		    	window.uploadPhotoCallBackFunc(json.rtData);
		    },
		    dataType: "json",
		    error:function(){
		    	
		    }
		});
	}else if(userAgent.indexOf("MicroMessenger")!=-1){//微信
		$.ajax({
		    type: 'post',
		    url: contextPath+"/weixin/downloadWxFile.action" ,
		    data: {files:filesArray.join(","),model:model,modelId:modelId} ,
		    success: function(json){
		    	window.uploadPhotoCallBackFunc(json.rtData);
		    },
		    dataType: "json",
		    error:function(){
		    	
		    }
		});
	}else{
		if(window.external && window.external.open){//安卓
			window.external.UploadPhoto("uploadPhotoCallBackFunc",filesArray.join(",")+"",model,modelId);
		}else{//ios
			document.location = "zatp:{\"method\":\"UploadPhoto:\",\"params\":[\"uploadPhotoCallBackFunc\",\""+filesArray.join(",")+"\",\""+model+"\",\""+modelId+"\"]}";
		}
	}
}

function string2Unicode(str) {
    var res=[];
    for(var i=0;i < str.length;i++)
        res[i]=("000"+str.charCodeAt(i).toString(16)).slice(-4);
    if(str=="" || str==null || str==undefined){
    	return "";
    }
    return "\\u"+res.join("\\u");
}

function unicode2String(str) {
	if(str=="" || str==null || str==undefined){
    	return "";
    }
    str=str.replace(/\\u/g,"%u");
    return unescape(str);
}

function jsonArray2String(json){
	var str = "[";
	for(var i=0;i<json.length;i++){
		if(typeof(json[i])=="string"){
			str+="\""+json[i]+"\"";
		}else if(typeof(json[i])=="number"){
			str+=json[i];
		}else if(json[i] instanceof Array){
			str+=jsonArray2String(json[i]);
		}else{
			str+=jsonObj2String(json[i]);
		}
		
		if(i!=json.length-1){
			str+=",";
		}
	}
	str += "]";
	return str;
}
/**
 * json对象转化为String
 */
function jsonObj2String(json){
	var str = "{";
	var value;
	var exist = false;
	for(var key in json){
		exist = true;
		str+="\""+key+"\":";
		value = json[key];
		if(value instanceof Array){
			str+=jsonArray2String(value);
		}else if(typeof(value)=="number"){
			str+=value;
		}else if(typeof(value)=="string"){
			str+="\""+(value+"").replace(/[\"]/gi, "\\\"").replace(/[\r\n]/gi, "\\n").replace(/[\n]/gi, "\\n").replace(/[\r]/gi, "\\n") +"\"";
		}else{
			str+=jsonObj2String(value);
		}
		str+=",";
	}
	if(exist){
		str = str.substring(0,str.length-1);
	}
	str +="}";
	return str;
}