//function upload(){
//	$("#form1").ajaxSubmit({
//		  url :<%=contextPath%>+"/detailController/importLaw.action",
//      iframe: true,
//      data: {
//        },
//        dataType: 'json'});
//		alert("文件导入成功！");
//		Window.close();
//}
var _id = '';
var swfUploadObj = null;
var finalContentType = '';
var singleUpload = null;
function doInit() {
    _id = $('#lawAdjustId').val();
    //	console.log(_id);
    //	//多附件快速上传.law-content-type
    initLawFiles(_id);

    $('.law-content-type').radiobutton({
        width : 15,
        height : 15,
        labelPosition : 'after',
        labelWidth: 120,
        labelAlign : 'left',
    });
    initLawSplitResultTable();
    doInitMultipleUpload();
}

function initLawFiles(lawId){
    if (lawId != null && lawId != '') {
        var json = tools.requestJsonRs(contextPath + "/lawAdjustReportCtrl/getFilelistById.action", {
            id : lawId
        });
        var attachModels = json.rtData;
        if (attachModels != null) {
            for (var i = 0; i < attachModels.length; i++) {
                attachModels[i].priv = 1 ;
                var attachElement = tools.getAttachElement(attachModels[i]);
                $("#attachDiv").html(attachElement);
            }
        }
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
//    swfUploadObj = new TeeSWFUpload({
//        fileContainer : "fileContainer2",//文件列表容器
//        renderContainer : "renderContainer2",//渲染容器
//        uploadHolder : "uploadHolder2",//上传按钮放置容器
//        valuesHolder : "attaches",//附件主键返回值容器，是个input
//        quickUpload : true,//快速上传
//        showUploadBtn : false,//不显示上传按钮
//        queueComplele : function() {//队列上传成功回调函数，可有可无
//            
//        },
//        renderFiles : true,//渲染附件
//        post_params : {
//            model : "lawInfo"
//        },
//        file_upload_limit: 1
//        
//    //后台传入值，model为模块标志
//    });
    singleUpload = new TeeSingleUpload({
        uploadBtn: "uploadHolder2",
        callback: function(data){
            //
            var replaceResult = tools.requestJsonRs("/lawAdjustExamineCtrl/replaceLawFile.action", {id: _id, attachId:data.rtData[0].sid});
            if(replaceResult.rtState){
                $.MsgBox.Alert_auto("上传成功！");
                initLawFiles(_id);
            }else{
                $.MsgBox.Alert_auto("出现异常！请刷新页面重新尝试");
            }
            console.log(data);
        },
        post_params:{
            model : "lawInfo"
        }
    });
}

function splitLawWordFile() {
    var contentType = '';
    $('#contentType_radio_group input[name="contentType"]').each(function() {
        if ($(this).is(':checked')) {
            contentType = $(this).val();
        }
    });
    if(contentType == null || contentType == ''){
        $.MsgBox.Alert_auto("请指定内容格式！");
        return false;
    }
    var json = tools.requestJsonRs("/lawAdjustExamineCtrl/splitLawWordFile.action", {
        lawId : _id,
        contentType: contentType
    });
    if (json.rtState) {
        var lawDetailList = json.rtData;
        finalContentType = contentType;
        $.MsgBox.Alert_auto("拆分成功！");
        $('#lawSplit_result_table').datagrid('loadData', lawDetailList);
    } else {
        alert("拆分失败，请确认所选内容格式是否正确");
    }
}

function saveSpliteResult (paramWindow, childWindow){
    top.$.MsgBox.Confirm('提示','执行“导入”操作,将会清空该部法律原有全部细则，并替换为该次拆分结果，确定执行？',function(){
        $(childWindow).parents(".bsWindow").find('.iframeDiv'+selfId).hide();
        var json = tools.requestJsonRs("/lawAdjustExamineCtrl/savelawSplitResult.action", {
            lawId : _id,
            contentType: finalContentType
        });
        if (json.rtState) {
            paramWindow.initDatagriad();
            var selfId = childWindow.id.substring(10);
            top.$('.iframeDiv'+selfId).hide();
            top.$('.zhezhao'+selfId).remove();
            top.$('.iframeDiv'+selfId).remove();
            $.MsgBox.Alert_auto("导入成功!");
            return true;
        } else {
            $.MsgBox.Alert_auto("导入失败!");
            return false;
        }
    });         
}

function initLawSplitResultTable() {
    $('#lawSplit_result_table').datagrid({
        pagination : false,
        singleSelect : true,
        striped : true,
        view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar',
        // 工具条对象
        checkbox : false,
        border : false,
        /* idField:'formId',//主键列 */
        fitColumns : true,
        // 列是否进行自动宽度适应
        nowrap : true,
        columns : [ [{
            field : '___',
            title : '序号',
            align : 'center',
            width : 15,
            formatter:function(value,rowData,rowIndex){
                return rowIndex+1;
            }
        }, {
            field : 'iPian',
            title : '篇',
            align : 'center',
            width : 15
        }, {
            field : 'iZhang',
            title : '章',
            align : 'center',
            width : 15
        }, {
            field : 'iJie',
            title : '节',
            align : 'center',
            width : 15
        }, {
            field : 'iTiao',
            title : '条',
            align : 'center',
            width : 15
        }, {
            field : 'iKuan',
            title : '款',
            align : 'center',
            width : 15
        }, {
            field : 'iXiang',
            title : '项',
            align : 'center',
            width : 15
        }, {
            field : 'iMu',
            title : '目',
            align : 'center',
            width : 15
        }, {
            field : 'strNeiRong',
            title : '内容',
            halign : 'center',
            align : 'left',
            width : 120,
            formatter: function(value, rowData, rowIndex){
                return '<span class="custom-text-overflow table-td-full-width" title="' + value+ '">' + value + '</span>';
            }
        }] ]
    });
}
