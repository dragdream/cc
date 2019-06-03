/**
 * 查看页面初始化方法
 * @returns
 */

var flowJson;
var gistGrid;
var subjectGrid;
var _id;

function doInit() {
    _id = $('#id').val();
    flowJson = $('#flowsheetArray').val();
    if(flowJson != null && flowJson != '') {
        flowJson = tools.strToJson(flowJson);
    }
    
    var attachIds = "";
    for(var i = 0; i < flowJson.length; i++) {
        attachIds = attachIds + flowJson[i].fileId + ","; 
    }
    attachIds = attachIds.substring(0, attachIds.length - 1);
    initAttachmentInfo(attachIds, "flowsheetTable");
    
    doInitGistgrid({powerId: _id});
    doInitSubjectgrid({powerId: _id});
}

/**
 * 初始化附件列表
 * @param model
 * @param modelId
 * @param elemId
 * @returns
 */
function initAttachmentInfo(ids, elemId) {
    var params = {ids: ids};
    var json = tools.requestJsonRs("/commonCtrl/attachmentByIds.action", params);   
    if(json.rtState) {
        var attachList = json.rtData;
        $.each(attachList, function(i, o){
            // 添加附件属性
            addAttachmentProp(o, elemId);
        });
    }
}

/**
 * 上传完毕后加载已经上传的文件
 * @param obj 文件对象
 * @param elemId 展示元素ID
 * @returns
 */
function addAttachmentProp(obj, elemId) {
    var mainObj = $('#' + elemId);
    var page = "";
    page = page + '<tr class="common-tr-border" id="flowsheet' + obj.sid + '">';
    page = page + '<td class="power-table-label">流程图类型：</td>';
    page = page + '<td style=" width: 350px;">';
    for(var index in flowJson) {
        if(flowJson[index].fileId == obj.sid) {
            page = page + flowJson[index].flowsheetType;
        }
    }
    page = page + '</td>';
    page = page + '<td class="power-table-label">文件名称：</td>';
    page = page + '<td style="width: 300px;">' + '<a href="javascript:void(0);" sid="FILE' + obj.sid + '"></a>'+ '</td>';
    page = page + '</tr>';
    
    mainObj.append(page);
    
    obj.priv = 1+2; // 阅读、下载、删除
    var attach = tools.getAttachElement(obj, {deleteEvent:function(attachModel){
        $("#flowsheet" + attachModel.sid).remove();
    }});
    $("[sid='FILE" + obj.sid + "']").append(attach);
}

function doInitGistgrid(params) {
    gistGrid = $('#gistGrid').datagrid({
        url: contextPath + '/powerTempCtrl/findGistsByPowerId.action',
        queryParams: params,
        pagination: true,
        singleSelect: true,
        pageSize : 10,
        pageList : [ 10, 20, 50, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[
        {
            field: '_index',
            width: 10,  
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'lawName',
            title: '依据名称',
            width: 60,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: 'gistStrip',
            title: '条',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'gistFund',
            title: '款',
            width: 10,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'gistItem',
            title: '项',
            width: 10,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'content',
            title: '内容',
            width: 80,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        }]]
    });
}

function doInitSubjectgrid(params) {
    subjectGrid = $('#subjectGrid').datagrid({
        url: contextPath + '/powerTempCtrl/findSubjectsByPowerId.action',
        queryParams: params,
        pagination: true,
        singleSelect: true,
        pageSize : 10,
        pageList : [ 10, 20, 50, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        // 工具条对象
        checkbox: true,
        border: false,
        /* idField:'formId',//主键列 */
        fitColumns: true,
        // 列是否进行自动宽度适应
        nowrap: true,
        striped: true,
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: '_index',
            width: 10,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'subjectName',
            title: '主体名称',
            width: 150,
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(value == null || value == "") {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>" + value + "</span>";
                    return lins;
                }
            }
        },
        {
            field: 'deputeType',
            title: '主体类型',
            width: 20,
            align : 'center',
            halign : 'center'
        }]]
    });
}

function openShowFormalPower(id){
    var url=contextPath+"/powerCtrl/showById.action?id=" + id;
    top.bsWindow(url ,"职权查看",{width:"900",height:"400",buttons:
        [
          {name:"关闭",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v=="关闭"){
                return true;
            }
        }
    });
}

function doPass(parentWindow, moduleId) {
    var flag = false;
    $.MsgBox.Confirm("提示","是否审核通过？",function(){
        var rtInfo = tools.requestJsonRs("/powerTempCtrl/updateState.action", {id: _id, currentState: '90'});
        if(rtInfo.rtState) {
            parentWindow.doSearch();
            $(window.parent.document.getElementsByClassName("iframeDiv"+moduleId)).hide();
            $(window.parent.document.getElementsByClassName("iframeDIv"+moduleId)).find("iframe").remove();
            $(window.parent.document.getElementsByClassName("zhezhao"+moduleId)).remove();
            $(window.parent.document.getElementsByClassName("iframeDiv"+moduleId)).remove();
            flag = true;
        } else {
            parentWindow.doSearch();
            flag = false;
        }
    });
    return flag;
}

function doFail(parentWindow, moduleId) {
    var flag = false;
    $.MsgBox.Confirm("提示","是否不予通过？",function(){
        var rtInfo = tools.requestJsonRs("/powerTempCtrl/updateState.action", {id: _id, currentState: '99'});
        if(rtInfo.rtState) {
            parentWindow.doSearch();
            $(window.parent.document.getElementsByClassName("iframeDiv"+moduleId)).hide();
            $(window.parent.document.getElementsByClassName("iframeDIv"+moduleId)).find("iframe").remove();
            $(window.parent.document.getElementsByClassName("zhezhao"+moduleId)).remove();
            $(window.parent.document.getElementsByClassName("iframeDiv"+moduleId)).remove();
            flag = true;
        } else {
            parentWindow.doSearch();
            flag = false;
        }
    });
    return flag;
}

function getResult(rtState){
    return doFail();
}
function doAuditingGroup() {
    
}