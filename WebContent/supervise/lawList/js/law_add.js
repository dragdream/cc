function doInit(){
	initsubmitlawLevel();
	
 	//多附件快速上传
	new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		renderContainer:"renderContainer2",//渲染容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attaches",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		renderFiles:true,//渲染附件
		post_params:{model:"lawInfo"}//后台传入值，model为模块标志
	});
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
//保存
function save(){
	if($("#form1").valid()){
		var param = tools.formToJson($("#form1"));
		console.log(param);
		param.isDelete = 0;//删除标识
		param.examine = 0;//审核状态
			var json = tools.requestJsonRs("/lawInfoController/save.action",param);
			return json.rtState;
	}else{
		return false;
	}
}