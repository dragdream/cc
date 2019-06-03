var introduceFile = [];
function documentCtralInit(model){
	var id = $('#id').val(); //主键ID(案件id,调查管理Id,听证id等主键)
	doInitUpload(model,id,"filingApprovalDocument");
}
/**
 * 初始化附件上传
 * model 附件表对每个模块文件的区分
 * 每个模块下每个文件都有一个除id外的一个唯一标识（可有可无）
 * elemId
 */
function doInitUpload(model, modelId, elemId){
	$("#"+elemId).html("");
	//多附件快速上传
	swfUploadObj = new TeeSWFUpload({
		fileContainer:"fileContainerIntroduce",//文件列表容器
		uploadHolder:"uploadHolderIntroduce",//上传按钮放置容器
		valuesHolder:"attachmentSidStrIntroduce",//附件主键返回值容器，是个input
		quickUpload:true,//快速上传
		showUploadBtn:false,//不显示上传按钮
		queueComplele:function(){//队列上传成功回调函数，可有可无
		},
		uploadSuccess:function(files){//上传成功
			$.jBox.tip("上传成功", 'info' , {timeout:1500});
			introduceFile.push(files.sid);//存放上传文件ID
            introduceFile = addAttachmentProp(files, elemId, introduceFile);
		},
		renderFiles:true,//渲染附件
		post_params:{model:model,modelId: modelId}//后台传入值，model为模块标志
	});
}
/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @param priv 权限
 * @returns
 */
function addAttachmentProp(obj, elemId, punishFile, priv) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="'+elemId+'Id" id="'+elemId+'Path" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="'+elemId+'Name" id="'+elemId+'Name" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2+4; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in punishFile) {
            if(punishFile[index] == attachModel.sid) {
                punishFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
    return punishFile;
}
/**
 *	 修改初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfoIntroduce(model, sid, elemId) {
    var json = tools.requestJsonRs("/attachmentController/getAttachmentModelsByIds.action?attachIds="+sid);
    if(json.rtData != null && json.rtData.length > 0) {
        introduceFile = [];
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            introduceFile.push(o.sid);//存放上传文件ID
            addAttachmentPropIntroduce(o, elemId);
        });
    }
}

/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentPropIntroduce(obj, elemId) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<div style="float: left; margin-right:20px" id="flowsheet' + obj.sid + '">';
    page = page + '<input type="hidden" name="introduceFileId" id="introduceFileId" value="' + obj.sid + '" />';
    page = page + '<input type="hidden" name="introduceFileName" id="introduceFileName" value="' + obj.fileName + '" />';
    page = page + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>';
    page = page + '</div>';
    mainObj.append(page);
    
    obj.priv = 1+2+4; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
        for(var index in introduceFile) {
            if(introduceFile[index] == attachModel.sid) {
                introduceFile.splice(index, 1);
                break;
            }
        }
        
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}
//材料信息回显
function initFiling(model,id){
	var json = tools.requestJsonRs("/xzfy/common/getAttachByModelAndModelId.action?id="+id+"&model="+model);
	$("#fyDocumentName").html("");
	if(json.rtData != null && json.rtData.length > 0) {
		// 文件信息回显
	    initAttachmentInfo(model, 'fyDocumentName',json.rtData);
	}
}
/**model:每个模块对应模块标识
 *elemId:jsp中文件展示的地方
 *data:文件list
 */
function initAttachmentInfo(model, elemId,data) {
       var punishFile = [];
       var attachList = data;
       $.each(attachList, function(i, o){
           // 添加附件属性
           punishFile.push(o.sid);//存放上传文件ID
           addAttachmentProp(o, elemId, punishFile, 2);
       });
}
