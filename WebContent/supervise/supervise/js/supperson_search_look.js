function doInit(){
	var url = contextPath + "/SupPersonController/getPersonById.action";
    var json = tools.requestJsonRs(url,{id:$("#id").val()});
    if(json.rtState){
    	bindJsonObj2Cntrl(json.rtData);
    	if(json.rtData.sex == '01'){
    		document.getElementById('sex').innerText = "男";
    	}else{
    		document.getElementById('sex').innerText = "女";
    	}
    	if(json.rtData.isGetcode == '1'){
    		document.getElementById('isGetcode').innerText = "取得过执法证";
    	}else{
    		document.getElementById('isGetcode').innerText = "未取得过执法证";
    	}
    	if(json.rtData.isLawyer == '1'){
    		document.getElementById('isLawyer').innerText = "是公职律师";
    	}else{
    		document.getElementById('isLawyer').innerText = "不是公职律师";
    	}
    }
	//处理附件
	var json = tools.requestJsonRs(contextPath + "/SupPersonController/getFilelistById.action",{id:id});
	var attachModels = json.rtData;
	if(attachModels != null){
	    for(var i=0;i<attachModels.length;i++){
	        attachModels[i].priv = 2;
	        var attachElement = tools.getAttachElement(attachModels[i]);
	        $("#attachDiv").append(attachElement);
	    }
	}
}
//是否取得过执法证
function certificate(){
	var getCode = $("input[type='checkbox']").is(':checked')
	if(getCode==false){
		document.getElementById("isGetcode").checked="true";
	}else{
		document.getElementById("isGetcode").checked="";
	}
	
}