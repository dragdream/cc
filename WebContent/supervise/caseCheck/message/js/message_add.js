function doInit(){
	messageTypeInit();
	doInitMultipleUpload();
}
function save(){
	if($('#form1').form('enableValidation').form('validate')){
		var param = tools.formToJson($("#form1"));
		if(param.attaches == "" || param.attaches == null){
			$.MsgBox.Alert_auto("请选择文件");
			return false;
		}
		var json = tools.requestJsonRs("/casecheckMessageCtrl/save.action",param);
	    return json.rtState;
	}
}
function messageTypeInit(){
	var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "MESSAGE_TYPE"});
    if(json.rtState) {
        $('#messageType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight:'80px',
        });
    }
}
/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUpload() {
  //多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainer2",//文件列表容器
		renderContainer:"renderContainer2",//渲染容器
		uploadHolder:"uploadHolder2",//上传按钮放置容器
		valuesHolder:"attaches",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
			
		},
		swfUploadLoaded:function(){
			var swf = swfUploadObj.swf;
			
		 	//多附件快速上传
			var successfulUploads = 0;
			
			var stats = swf.getStats();
			stats.successful_uploads = successfulUploads;
			swf.setStats(stats);
			//alert(swfUploadObj.swf.getStats().successful_uploads);
		},
		file_types:"*.doc;*.docx;*.xls;*.xlsx;*.pdf",
//		file_size_limit : "1024",//文件大小默认kb
		file_upload_limit : 1,//上传文件限制
//		file_queue_limit : 1,//上传队列限制
		renderFiles:true,//渲染附件
		post_params:{model:"caseCheckMessage",priv: 2+4}//后台传入值，model为模块标志
	});
	console.log(swfUploadObj);
//	var stats = swfUploadObj.swf.getStats();
//    stats.successful_uploads--;
//    swfUploadObj.swf.setStats(stats);
}