var illegalFile = [];// 证据材料

/**
 * 初始化证据信息
 */
function initIllegal(){
	var isEdit = $("#isEdit").val();
	var caseId = $("#caseId").val();
	if(isEdit == 1){
		doEdit();
	}else if(isEdit == 0){
		doInit();
	}
	
	// 上传证据材料
    doInitMultipleUploadIllegal('commonCase', caseId+'illegalDocumentId', 'illegalDocument');
}

/**
 * 新增
 */
function doInit(){
	initCodeListInput('COMMON_ILLEGAL_TYPE','illegalEvidenceType');// 证据类型
	initCodeListInput('COMMON_ILLEGAL_SOURCE','illegalSource');// 证据来源
}

/**
 * 修改
 */
function doEdit(){
	var json = top.params;
	delete top.params;
	bindJsonObj2Easyui(json, 'common_case_illegal');
	initCodeListInput('COMMON_ILLEGAL_TYPE','illegalEvidenceType',json.illegalEvidenceType);// 证据类型
	initCodeListInput('COMMON_ILLEGAL_SOURCE','illegalSource',json.illegalSource);// 证据来源
	
	var illegalDocumentPath = json.illegalDocumentPath;
	if(illegalDocumentPath != null && illegalDocumentPath != ''){
		// 修改回显证据材料
		illegalFile = initAttachmentInfo('commonCase', illegalDocumentPath, 'illegalDocument');
	}
}

/**
 * 附件上传
 * @param model 后台文件路径文件夹
 * @param modelId 查询文件关键字
 * @param elemId 前台展示元素ID
 * @returns
 */
function doInitMultipleUploadIllegal(model, modelId, elemId) {
  //多附件快速上传
    swfUploadObj = new TeeSWFUpload({
        fileContainer:"fileContainerIllegal",//文件列表容器
        uploadHolder:"uploadHolderIllegal",//上传按钮放置容器
        valuesHolder:"attachmentSidStrIllegal",//附件主键返回值容器，是个input
        quickUpload:true,//快速上传
        showUploadBtn:false,//不显示上传按钮
        queueComplele:function(files){//队列上传成功回调函数，可有可无
            
        },
        uploadSuccess:function(files){//上传成功
        	illegalFile.push(files.sid);//存放上传文件ID
        	illegalFile = addAttachmentProp(files, elemId, illegalFile);
        },
        renderFiles:false,//渲染附件
        post_params:{model:model, modelId: modelId}//后台传入值，model为模块标志
    });
}

/**
 * 保存证据信息
 * @returns
 */
function saveIllegal(){
	if($("#common_case_illegal").form('enableValidation').form('validate')){
		var params = tools.formToJson($("#common_case_illegal"));
		
		// 证据材料保存
        if(illegalFile != null && illegalFile.length > 1){
            $.MsgBox.Alert_auto("请仅上传一个证据材料");
            $('html, body').animate({scrollTop: $("#illegalDocument").offset().top}, 500);
            return false;
        }
		params.illegalEvidenceTypeValue = $("#illegalEvidenceType").combobox('getText');
		params.illegalSourceValue = $("#illegalSource").combobox('getText');
		return params;
	}
    return false;
}