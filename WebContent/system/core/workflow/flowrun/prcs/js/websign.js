function delCB(signatureid, signData){
   if(Signature.list != null && Signature.list[signatureid] != null){
	   var signatureCreator = Signature.create();
	   signatureCreator.removeSignature(signData.documentid, signatureid);
   }
   return true;
}
/**
 * 
 * @param item
 * @param callback - 回掉函数
 * @return
 */
function show_seal(item,callback)
{
  var URL = contextPath + "/system/core/workflow/websign/sel_seal/index.jsp?item=" + item +"&callback=" + callback;
  dialog(URL,  470, 400);
}

function show_ctrl_seal(item,callback){
	var URL = contextPath + "/system/core/workflow/websign/sel_seal/index1.jsp?item=" + item +"&callback=" + callback;
	dialog(URL,  470, 400);
}

function lookSeal(sid){
	var sealUrl = contextPath + "/system/core/workflow/flowrun/prcs/feedbackSealLook.jsp?sid="+sid;
	top.$.jBox.open("iframe:"+sealUrl,"查看签章",500,300);
}

function handWrite(item  , color) {
	if($("#"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	var itemId = item.split("_")[1];
	var signatureCreator = Signature.create();
	var fieldString = $("#"+item).attr("fieldString");
	if(fieldString==""){
		fieldString = [];
	}else{
		fieldString = fieldString.split(",");
	}
	
	signatureCreator.handWriteDlg({
		image_height: "2",
		image_width: "4",
		onBegin: function() {
			
		},
		onEnd: function() {
			
		}
	}, function(param){
		//alert(param.imageData);
		signatureCreator.runHW(param, {
			protectedItems:fieldString,//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
			position: "SIGN_POS_"+item,//设置盖章定位dom的ID，必须设置
			okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
				var data = this.getSignatureid()+","+this.getSignatureData();
				fn(true);
				$("#"+item).val(data);
				var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
				var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).position().top;
				var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).position().left;
				var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
				var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
				$("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);
				bindSignEvent();//绑定签章事件
			},
			cancelCall : function() {//点击取消后的回调方法
				
			}
		});
	});
}

function handCtrlWrite(item ,color) {
	if($("#CTRL_SIGN_DATA_INPUT_O_"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	
	var signatureCreator = Signature.create();
	signatureCreator.handWriteDlg({
		image_height: "2",
		image_width: "4",
		onBegin: function() {
			
		},
		onEnd: function() {
			
		}
	}, function(param){
		signatureCreator.runHW(param, {
			position: "SIGN_POS_CTRL_"+item+"_"+rand,//设置盖章定位dom的ID，必须设置
			okCall: function(fn) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
				var data = this.getSignatureid()+","+this.getSignatureData();
				fn(true);
				$("#"+item).attr("rand",rand);
				$("#CTRL_SIGN_DATA_INPUT_O_"+item).val(data);
				var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
				var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginTop");
				var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginLeft");
				var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
				var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
				$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);
				bindSignEvent();//绑定签章事件
			},
			cancelCall : function() {//点击取消后的回调方法
				
			}
		});
	});
	
}

function addSeal(item,seal_id) {
	if($("#"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	var itemId = item.split("_")[1];
	var signatureCreator = Signature.create();
	var fieldString = $("#"+item).attr("fieldString");
	
	/*signatureCreator.run({
		protectedItems : fieldString,//设置定位页面DOM的id，自动查找ID，自动获取保护DOM的kg-desc属性作为保护项描述，value属性为保护数据。不设置，表示不保护数据，签章永远有效。
		position : "SIGN_POS_"+item,//设置盖章定位dom的ID，必须设置
		autoCert : false,
		okCall : function(fn, image) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
			var data = this.getSignatureid()+","+this.getSignatureData();
			fn(true);
			$("#"+item).val(data);
			//kg-img-div-152527746582307091-SIGN_POS_DATA_6
			var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
			var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).css("marginTop");
			var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_"+item).css("marginLeft");
			var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
			var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
			$("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);
			bindSignEvent();//绑定签章事件
		},
		cancelCall : function() {//点击取消后的回调方法
			
		}
	});*/
	
	//创建一个签章对象
	var tmpObj = eval("window."+"SignObj_"+item);
	tmpObj= CreateWebSignObject("SignObj_"+item, SignData1, "SignData1('"+item+"',"+itemId+",'"+fieldString+"')",  VerifySign1, "VerifySign1('"+item+"',"+itemId+",'"+fieldString+"')", RemoveSignObj1, "RemoveSignObj1('"+item+"',"+itemId+",'"+fieldString+"')");
	if(tmpObj) {
		tmpObj.SetVisible(false);
		//注意：如果不调用SetOffsetPos这个函数，则控件在页面可以拖动
		//设置签章的位置
		tmpObj.SetOffsetPos("SIGN_POS_"+item, 0, 0);
		/*tmpObj.Sign(" ");
		tmpObj.SetVisible(true);*/
		SignData1(item,itemId,fieldString);
	}
}

/**
 * 获取校验字符串
 * @returns {String}
 */
function GetOrgData1(fieldString){
	var temp = "";
	if(fieldString==""){
		temp=" ";
		fieldString = [];
	}else{
		fieldString = fieldString.split(",");
	}
	
	if(fieldString!=null&&fieldString.length>0){
		for(var i=0;i<fieldString.length;i++){
			temp += $("#"+fieldString[i]).val();
		}
	}
	return temp;
}

/**
 * 执行签章的回调函数
 */
function SignData1(item,itemId,fieldString){
   //alert("执行签章的回调函数！");
   var orgData = GetOrgData1(fieldString);
   var tmpObj = eval("window.SignObj_"+item);
   var signature = tmpObj.Sign(orgData);
   tmpObj.SetVisible(true);
   if(signature != "") {
	   //设置签章的原值
	   $("#"+item).val(signature);
	   //获取签章64位png格式
	   var imgData =getPIC(item,itemId);
	   
	   var top = tmpObj.GetYPos();
	   var left = tmpObj.GetXPos();
	   var width = 0 ;
	   var height = 0 ;
	   $("body").append("<img id=\"WEBSIGN_IMG_"+item+"\" src=\"data:image/png;base64,"+imgData+"\" style=\"display:none;\" >");
	   
	   $("<img/>").attr("src", $("#WEBSIGN_IMG_"+item).attr("src")).load(  
	            function() {  
	            	width = this.width;  
	            	height = this.height; 
	            	//签章数据
	                $("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);  	
	   }); 
  
   }
    // document.getElementById("signData1").value = SignObj_1.Sign(orgData);
   //SignObj11.SetOffsetPos("qianfa", 10, 10);
}


/*
撤销签章的回调函数
*/
function RemoveSignObj1(item,itemId,fieldString){
	//alert("撤销签章的回调函数！");
	var tmpObj = eval("window.SignObj_"+item);
	if (0 == tmpObj.IsSigned()) {
		tmpObj.SetVisible(false);
	    //清空数据
		$("#"+item).val("");
		$("#SEAL_"+itemId).val("");
	}
}


/*
验证签章的回调函数
*/
function VerifySign1(item,itemId,fieldString){
	//alert("验证签章的回调函数！");	
	var tmpObj = eval("window.SignObj_"+item);
	var orgData = GetOrgData1(fieldString);
	var signData1 = $("#"+item).val();
	if (signData1 != ""){
		var ret = tmpObj.Verify(orgData, signData1);
        if(ret){
	        alert("验证成功，数据有效！");
        } else{
	        alert("验证失败，数据无效！");
        }
	}
	return ret;
}


/**
 * 获取签章64位png
 */
function getPIC(item,itemId){
	var tmpObj = eval("window.SignObj_"+item);
	var mzhres2=$("#"+item).val();
	var picStr=tmpObj.GetESealFromSignature(mzhres2);
	return picStr;
}


function addCtrlSeal(item,seal_id) {
	if($("#CTRL_SIGN_DATA_INPUT_O_"+item).val()!=""){
		alert("已存在签章数据");
		return;
	}
	var itemId=item.split("_")[1];
	/*var signatureCreator = Signature.create();
	signatureCreator.run({
		position : "SIGN_POS_CTRL_"+item+"_"+rand,//设置盖章定位dom的ID，必须设置
		autoCert : false,
		okCall : function(fn, image) {//点击确定后的回调方法，this为签章对象 ,签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,保存成功后必须回调fn(true/false)渲染签章到页面上
			var data = this.getSignatureid()+","+this.getSignatureData();
			fn(true);
			$("#"+item).attr("rand",rand);
			$("#CTRL_SIGN_DATA_INPUT_O_"+item).val(data);
			var imgData = $("#kg-img-"+this.getSignatureid()).attr("src");
			var top = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginTop");
			var left = $("#kg-img-div-"+this.getSignatureid()+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginLeft");
			var width = $("#kg-img-"+this.getSignatureid()).width()+"px";
			var height = $("#kg-img-"+this.getSignatureid()).height()+"px";
			$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);
			bindSignEvent();//绑定签章事件
		},
		cancelCall : function() {//点击取消后的回调方法
			
		}
	});*/
	
	//创建一个签章对象
	var tmpObj = eval("window."+"SignObj_"+item);
	tmpObj= CreateWebSignObject("SignObj_"+item, SignData2, "SignData2('"+item+"',"+itemId+")",  VerifySign2, "VerifySign2('"+item+"',"+itemId+")", RemoveSignObj2, "RemoveSignObj2('"+item+"',"+itemId+")");
	if(tmpObj) {
		tmpObj.SetVisible(false);
		//注意：如果不调用SetOffsetPos这个函数，则控件在页面可以拖动
		//设置签章的位置
		//alert("SIGN_POS_CTRL_"+item+"_"+rand);
		tmpObj.SetOffsetPos("SIGN_POS_CTRL_"+item+"_"+rand, 0, 0);
		/*tmpObj.Sign(" ");
		tmpObj.SetVisible(true);*/
		SignData2(item,itemId);
	}
	
}

/**
 * 签章数据回显
 */
function LoadSignData() {
	$("input[xtype=xseal]").each(function(i,obj){
		var item=$(obj).attr("id");
		var itemId = item.split("_")[1];
		var data = $(obj).val();
		var fieldString= $(obj).attr("fieldString");
		//校验的字符串
		var orgData=GetOrgData1(fieldString);
		
		//取到对应的签章数据
		var tmpObj = eval("window.SignObj_"+item);
		alert(data);
		if(data!=""){
			var writable = $(obj).attr("writable");
			
			tmpObj = CreateWebSignObject("SignObj_"+item, SignData1, "SignData1('"+item+"',"+itemId+",'"+fieldString+"')",  VerifySign1, "VerifySign1('"+item+"',"+itemId+",'"+fieldString+"')", RemoveSignObj1, "RemoveSignObj1('"+item+"',"+itemId+",'"+fieldString+"')");
			
			//设置位置
			tmpObj.SetOffsetPos("SIGN_POS_"+item, 0, 0);
			
			var ret = tmpObj.Verify(orgData, data);//原文 签章值
			/*if(ret){
		        alert("验证成功，数据有效！");
	        }else{
		        alert("验证失败，数据无效！");
	        }*/
		}
	});
}


function bindSignEvent(){
	Signature.bind({
		remove:function(fn){//签章数据撤销时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,
			var signatureid = this.getSignatureid();
			$("input[xtype=xseal][value!='']").each(function(i,obj){
				var sid = $(obj).val().split(",");
				if(sid[0]==signatureid){
					
					$(obj).val("");
					var itemId = $(obj).attr("id").split("_")[1];
					$("#SEAL_"+itemId).val("");
				}
			});
			$("textarea[xtype=xfeedback]").each(function(i,obj){
				var item = $(obj).attr("id");
				var sid = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val().split(",");
				if(sid[0]==signatureid){
					$("#CTRL_SIGN_DATA_INPUT_O_"+item).val("");
					$("#CTRL_SIGN_DATA_INPUT_I_"+item).val("");
				}
			});
			fn(true);//保存成功后必须回调fn(true/false)传入true/false分别表示保存成功和失败
		},
		update:function(fn){//签章数据有变动时，将回调此方法，需要实现签章数据持久化（保存数据到后台数据库）,执行后必须回调fn(true/false)，传入true/false分别表示保存成功和失败
			fn(true);
			var signatureid = this.getSignatureid();
			var data = this.getSignatureData();
			$("input[xtype=xseal][value!='']").each(function(i,obj){
				var sid = $(obj).val().split(",");
				if(sid[0]==signatureid){
					$(obj).val(signatureid+","+data);
					var itemId = $(obj).attr("id").split("_")[1];
					var imgData = $("#kg-img-"+signatureid).attr("src");
					var top = $("#kg-img-div-"+signatureid+"-SIGN_POS_"+$(obj).attr("id")).css("marginTop");
					var left = $("#kg-img-div-"+signatureid+"-SIGN_POS_"+$(obj).attr("id")).css("marginLeft");
					var width = $("#kg-img-"+signatureid).width()+"px";
					var height = $("#kg-img-"+signatureid).height()+"px";
					$("#SEAL_"+itemId).val(imgData+"|"+left+","+top+"|"+width+","+height);
				}
			});
			$("textarea[xtype=xfeedback]").each(function(i,obj){
				var item = $(obj).attr("id");
				var sid = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val().split(",");
				var rand = $(obj).attr("rand");
				if(sid[0]==signatureid){
					$("#CTRL_SIGN_DATA_INPUT_O_"+item).val(signatureid+","+data);
					var imgData = $("#kg-img-"+signatureid).attr("src");
					var top = $("#kg-img-div-"+signatureid+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginTop");
					var left = $("#kg-img-div-"+signatureid+"-SIGN_POS_CTRL_"+item+"_"+rand).css("marginLeft");
					var width = $("#kg-img-"+signatureid).width()+"px";
					var height = $("#kg-img-"+signatureid).height()+"px";
					$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);
				}
			});
		}
	});
}


/**
 * <会签控件>执行签章的回调函数
 */
function SignData2(item,itemId){

   //alert("执行签章的回调函数！");
   var orgData = " ";
   var tmpObj = eval("window.SignObj_"+item);
   var signature = tmpObj.Sign(orgData);
   tmpObj.SetVisible(true);
   if(signature != "") {
	   
	   //设置签章的原值
	   $("#"+item).attr("rand",rand);
	   $("#CTRL_SIGN_DATA_INPUT_O_"+item).val(signature);
	   //获取签章64位png格式
	   var imgData =getPIC2(item,itemId);
	   
	   var top = tmpObj.GetYPos();
	   var left = tmpObj.GetXPos();
	   var width = 0 ;
	   var height = 0 ;
	   $("body").append("<img id=\"FB_WEBSIGN_IMG_"+item+"\" src=\"data:image/png;base64,"+imgData+"\" style=\"display:none;\" >");
	   
	   $("<img/>").attr("src", $("#FB_WEBSIGN_IMG_"+item).attr("src")).load(  
	            function() {  
	            	width = this.width;  
	            	height = this.height; 
	            	//签章数据
	            	//alert(imgData+"|"+left+","+top+"|"+width+","+height);
	            	$("#CTRL_SIGN_DATA_INPUT_I_"+item).val(imgData+"|"+left+","+top+"|"+width+","+height);  	
	   }); 
  
   }
    // document.getElementById("signData1").value = SignObj_1.Sign(orgData);
   //SignObj11.SetOffsetPos("qianfa", 10, 10);
}


/*
撤销签章的回调函数
*/
function RemoveSignObj2(item,itemId){
	//alert("无法撤销签章！");
	var tmpObj = eval("window.SignObj_"+item);
	if (0 == tmpObj.IsSigned()) {
		tmpObj.SetVisible(false);
	    //清空数据
		$("#CTRL_SIGN_DATA_INPUT_O_"+item).val("");
		$("#CTRL_SIGN_DATA_INPUT_I_"+item).val("");
	}
}


/*
验证签章的回调函数
*/
function VerifySign2(item,itemId){
	//alert("验证签章的回调函数！");	
	var tmpObj = eval("window.SignObj_"+item);
	var orgData = " ";
	var signData1 = $("#CTRL_SIGN_DATA_INPUT_O_"+item).val();
	if (signData1 != ""){
		var ret = tmpObj.Verify(orgData, signData1);
        if(ret){
	        alert("验证成功，数据有效！");
        } else{
	        alert("验证失败，数据无效！");
        }
	}
	return ret;
}


/**
 * 获取签章64位png
 */
function getPIC2(item,itemId){
	var tmpObj = eval("window.SignObj_"+item);
	var mzhres2=$("#CTRL_SIGN_DATA_INPUT_O_"+item).val();
	var picStr=tmpObj.GetESealFromSignature(mzhres2);
	return picStr;
}