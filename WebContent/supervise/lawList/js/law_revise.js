function doInit(){
		console.log(id);
		initsubmitlawLevel();
 		var json = tools.requestJsonRs("/lawInfoController/get.action",{id:id});
 		console.log(json.rtData);
 		bindJsonObj2Easyui(json.rtData , "form1");
 		
 		//处理附带的附件信息
 		var attachModels = json.rtData.attachModels;
 		for(var i=0;i<attachModels.length;i++){
 			attachModels[i].priv = 1+2+4+8+16;
 			var attachElement = tools.getAttachElement(attachModels[i]);
 			$("#attachDiv").append(attachElement);
 		}
}
function save(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
			saved();
			param.isDelete = 0;//删除标识
			param.examine = 0;//审核状态
			var json = tools.requestJsonRs("/lawInfoController/save.action",param);
			return json.rtState;
	}
}

function saved(){
	var param = tools.formToJson($("#form1"));
	param.isDelete = 0;//删除标识
	param.examine = 0;//审核状态
	var json = tools.requestJsonRs("/lawInfoController/abolish.action",{id:param.id});
	return json.rtState;
}
function initsubmitlawLevel(){
		var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "LAW_TYPE"});
	    if(json.rtState) {
	        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
	        $('#submitlawLevel').combobox({
	            data: json.rtData,
	            valueField: 'codeNo',
	            textField: 'codeName',
	            onLoadSuccess:function(){
	                $('#submitlawLevel').combobox('setValue',-1);
	            },
	            onChange: function() {
	                var powerType = $('#submitlawLevel').combobox('getValue');
	                if(powerType != "") {
	                    var params = {
	                        parentCodeNo: "LAW_TYPE",
	                        codeNo: powerType
	                    };
	                    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
	                    if(result.rtState) {
	                        $('#powerDetail').combobox({
	                            data: result.rtData,
	                            valueField: 'codeNo',
	                            textField: 'codeName'
	                        });
	                    }
	                }
	            }
	        });
	    }
	}