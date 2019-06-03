var datagrid;
var flowInfo;
var _selectedPowerId;
var _id;

var flag;

function doInit() {
    var token = $('#token').val();
    if(token == "") {
        flag = "submit";
        _id = $('#baseId').val();
        _selectedPowerId = $('#selectedPowerId').val();
        
        $('#submitPower').show();
        $('#returnSubmit').show();
        $('#adjustReason').show();
        var params = {
            ids: _selectedPowerId
        };
        
        doInitDatagrid(params);
    } else {
        flowInfo = tools.string2JsonObj(token);
        var params = {
                id: flowInfo.primaryId,
                prcsId: flowInfo.prcsId,
                prcsName: flowInfo.prcsName
            };
        if(flowInfo.isFinished == true) {
            params.isFinished = "true";
        } else {
            params.isFinished = "false";
        }
        
        var result = tools.requestJsonRs("/powerAdjustCtrl/getById.action", params);
        if(result.rtState) {
            bindJsonObj2Cntrl(result.rtData.adjustModel);
            bindJsonObj2Cntrl(result.rtData.adjustTacheModel);
            
            $('#baseId').val(result.rtData.adjustModel.id);
            $('#tacheId').val(result.rtData.adjustTacheModel.id);
            
        }
        if(flowInfo.prcsId == 0) {
            flag = "backTo";
        } else {
            flag = "examine";
        }
        doChangeButton(flowInfo.prcsId);
        var params = {
            id: flowInfo.primaryId
        };
        doInitExamineGrid(params);
    }
}

function doChangeButton(prcsId) {
    if(!flowInfo.isFinished) {
        $('#examineView').removeAttr("disabled");
        if(prcsId == 99) {
            $('#closedFlow').show();
        } else {
            $('#turnNext').show();
        }
        $('#saveTache').show();
        $('#turnBack').show();
    }
}

function doInitExamineGrid(params) {
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerAdjustCtrl/getPowersByAdjustId.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 100,
        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar',
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
            field: 'powerId',
            checkbox: true,
            width: 20
        },
        {
            field: '_index',
            width: 5,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'powerOptType',
            title: '调整方式',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'powerName',
            title: '职权名称',
            width: 65,
            halign : 'center',
            formatter: function(value, rowData) {
                var lins = "<a href='#' title='"+ value +"' onclick='examinePower(\"" + rowData.powerId + "\", \"" + rowData.id + "\")'>" + value + "</a>"
                return lins;
            }
        },
        {
            field: 'powerType',
            title: '职权类型 ',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'examineStateStr',
            title: '审核状态 ',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: '_opt',
            title: '操作',
            width: 25,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData) {
                var lins = "";
                if(!flowInfo.isFinished) {
                    if(flag == "examine") {
                        lins = "<a href='#' title='通过' onclick='doPass(\"" + rowData.id + "\")'> 通过</a>";
                        lins = lins + "&nbsp;&nbsp;<a href='#' title='建议修改' onclick='doEdit(\"" + rowData.id + "\")'> 建议修改</a>"
                        lins = lins + "&nbsp;&nbsp;<a href='#' title='不予通过' onclick='doFail(\"" + rowData.id + "\")'> 不予通过</a>"
                    } else if(flag == "backTo") {
                        if(rowData.examineState == "30" || rowData.examineState == "10") {
                            lins = "<a href='#' title='修改' onclick='doEditPower(\"" + rowData.powerId + "\")'> 修改</a>";
                        }
                    }
                }
                return lins;
            }
        }]]
    });
}

function doInitDatagrid(params) {
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerTempCtrl/getPowerByIds.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 100,
        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar: '#toolbar',
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
            field: 'id',
            checkbox: true,
            width: 20
        },
        {
            field: '_index',
            width: 5,
            title: '序号',
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                return rowIndex + 1;
            }
        },
        {
            field: 'operationType',
            title: '调整方式',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'name',
            title: '职权名称',
            width: 65,
            halign : 'center',
            formatter: function(value, rowData) {
                var lins = "<a href='#' title='"+ value +"' onclick='showPowerTemp(\"" + rowData.id + "\")'>" + value + "</a>"
                return lins;
            }
        },
        {
            field: 'powerType',
            title: '职权类型 ',
            width: 10,
            align : 'center',
            halign : 'center'
        }]]
    });
}

function examinePower(powerId, adjustId) {
    var url=contextPath+"/powerTempCtrl/powerShow.action?id=" + powerId;
    top.bsWindow(url ,"职权查看",{width:"900",height:"400",buttons:
        [
          {name:"通过",classStyle:"btn-alert-blue"},
          {name:"建议修改",classStyle:"btn-alert-blue"},
          {name:"不予通过",classStyle:"btn-alert-blue"},
          {name:"关闭",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v == "通过") {
                var result = tools.requestJsonRs("/powerAdjustCtrl/examinePower.action", {id: adjustId, examineState: '20'});
                if(result.rtState) {
                    $.MsgBox.Alert_auto("提交成功");
                    var params = {
                        id: flowInfo.primaryId
                    };
                    doInitExamineGrid(params);
                }
                return true;
            } else if(v == "建议修改") {
                var result = tools.requestJsonRs("/powerAdjustCtrl/examinePower.action", {id: adjustId, examineState: '30'});
                if(result.rtState) {
                    $.MsgBox.Alert_auto("提交成功");
                    var params = {
                        id: flowInfo.primaryId
                    };
                    doInitExamineGrid(params);
                }
                return true;
            } else if(v == "不予通过") {
                var result = tools.requestJsonRs("/powerAdjustCtrl/examinePower.action", {id: adjustId, examineState: '90'});
                if(result.rtState) {
                    $.MsgBox.Alert_auto("不予通过"); 
                    var params = {
                        id: flowInfo.primaryId
                    };
                    doInitExamineGrid(params);
                }
                return true;
            } else if(v=="关闭"){
                return true;
            }
            
        }
    });
}

function showPowerTemp(id) {
    var url=contextPath+"/powerTempCtrl/powerShow.action?id=" + id;
    top.bsWindow(url ,"职权查看",{width:"900",height:"400",buttons:
        [
          {name:"通过",classStyle:"btn-alert-blue"},
          {name:"建议修改",classStyle:"btn-alert-blue"},
          {name:"不予通过",classStyle:"btn-alert-blue"},
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

function showExamineHis() {
    var adjustId = $('#baseId').val();
    if(adjustId != "") {
        var url=contextPath + "/powerAdjustCtrl/showExamineHis.action?adjustId=" + adjustId;
        top.bsWindow(url ,"审核记录",{width:"900",height:"400",buttons:
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
}

function doPass(id) {
    var result = tools.requestJsonRs("/powerAdjustCtrl/examinePower.action", {id: id, examineState: '20'});
    if(result.rtState) {
        $.MsgBox.Alert_auto("审核通过");
        var params = {
            id: flowInfo.primaryId
        };
        doInitExamineGrid(params);
    }
}

function doEdit(id) {
    var result = tools.requestJsonRs("/powerAdjustCtrl/examinePower.action", {id: id, examineState: '30'});
    if(result.rtState) {
        $.MsgBox.Alert_auto("提交成功");
        var params = {
            id: flowInfo.primaryId
        };
        doInitExamineGrid(params);
    }
}

function doFail(id) {
    var result = tools.requestJsonRs("/powerAdjustCtrl/examinePower.action", {id: id, examineState: '90'});
    if(result.rtState) {
        $.MsgBox.Alert_auto("审核不通过"); 
        var params = {
            id: flowInfo.primaryId
        };
        doInitExamineGrid(params);
    }
}

function doEditPower(id) {
    top.bsWindow("/powerTempCtrl/powerInput.action?editFlag=1&type=10&id=" + id ,"职权调整",{width:"900",height:"500",buttons:
        [
            {name:"保存",classStyle:"btn-alert-blue"},
            {name:"关闭",classStyle:"btn-alert-gray"}
        ],
        submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var resultInfo = cw.save();
                if(resultInfo.rtState) {
                    $.MsgBox.Alert_auto("保存成功");
                    doInitTable();
                    return true;
                }else {
                    $.MsgBox.Alert_auto("保存失败");
                        return false;
                }
            } else if (v=="关闭"){
                return true;
            }
        }
    });
}

function returnSubmitPage() {
    window.location.href = "/supervise/power/power_manage.jsp";
}

function doSubmitPower() {
    
    var json = tools.strToJson($('#adjust').val());
    var baseId = $('#baseId').val();
    var selectedPowerId = $('#selectedPowerId').val();
    json.id = baseId;
    json.selectedPowerId = selectedPowerId;
    json.adjustReason = $('#adjustReason').val();
    json.currentStatus = "10";

    var result = tools.requestJsonRs("/powerTempCtrl/saveAdjust.action", json);
    if(result.rtState) {
        var date = new Date();
        var dateStr = date.pattern("yyyy-MM-dd HH:mm");
        
        var runJson = tools.requestJsonRs(contextPath + "/flowRun/createNewWork.action",
                {fType : "4002", runName:"职权变更审核 " + dateStr, "VAR_PRIMARY_ID":result.rtData, runId:0});
        
        if(runJson.rtState) {
            var runId = runJson.rtData.runId;
            var frpSid = runJson.rtData.frpSid;
            var flowId = runJson.rtData.flowId;
            
            //创建工作流工具对象
            var workFlowUtil = new WorkFlowUtil(runId,frpSid,flowId,0);
            workFlowUtil.turnNext("转交",function(rt){
                if(rt==true){
                    alert("转交成功");
                    var params = {
                        id: result.rtData,
                        runId: runId
                    };
                    var resultBind = tools.requestJsonRs("/powerAdjustCtrl/bindRunId.action", params);
                    if(resultBind.rtState) {
                        window.location.href = "/supervise/power/power_manage.jsp";
                    }
                }else{
                    alert("转交失败");
                }
            });
        }
    } else {
        $.MsgBox.Alert_auto("提交失败");
    }
}

function saveTache() {
    var examineView = $('#examineView').val();
    var params = {
            id: $('#tacheId').val(),
            examineView: examineView
    };
    
    var result = tools.requestJsonRs("/powerAdjustCtrl/saveTache.action", params);
    if(result.rtState) {
        $.MsgBox.Alert_auto("保存成功");
    }
}

function turnNext() {
    var examineView = $('#examineView').val();
    var date = new Date();
    var dateStr = date.pattern("yyyy-MM-dd HH:mm");
    var params = {
            id: $('#tacheId').val(),
            examineView: examineView,
            closedDateStr: dateStr
    };
    
    var result = tools.requestJsonRs("/powerAdjustCtrl/turnNextTache.action", params);
    if(result.rtState) {
        $.MsgBox.Alert_auto("保存成功");
        //流程转交
        var workFlowUtil = new WorkFlowUtil(flowInfo.runId, flowInfo.frpSid, flowInfo.flowId, flowInfo.goBack);
        workFlowUtil.turnNext("转交", function(rt) {
            if (rt == true) {
                alert("转交成功");
                window.close();
            } else {
                alert("转交失败");
            }
        }); 
    }
}

// 退回
function turnBack() {
  
    var examineView = $('#examineView').val();
    var date = new Date();
    var dateStr = date.pattern("yyyy-MM-dd HH:mm");
    var params = {
            id: $('#tacheId').val(),
            examineView: examineView,
            closedDateStr: dateStr
    };
    
    var result = tools.requestJsonRs("/powerAdjustCtrl/turnNextTache.action", params);
    if(result.rtState) {
        $.MsgBox.Alert_auto("保存成功");
        //流程转交
        var workFlowUtil = new WorkFlowUtil(flowInfo.runId, flowInfo.frpSid, flowInfo.flowId, flowInfo.goBack);
        workFlowUtil.backTo(function(rt) {
            if (rt == true) {
                alert("退回成功");
                window.close();
            } else {
                alert("退回失败");
            }
        });
    }
}

// 流程办结
function closedFlow() {
    var result = tools.requestJsonRs("/powerAdjustCtrl/checkClosed.action", {id: $('#baseId').val()});
    if(result.rtState) {
        var examineSum = result.rtData;
        var edit = examineSum.edit;
        if(edit != 0) {
            alert("请将所有职权审核完毕后再办结本批次！");
            return ;
        }
    } else {
        return;
    }
    
    var examineView = $('#examineView').val();
    var date = new Date();
    var dateStr = date.pattern("yyyy-MM-dd HH:mm");
    var params = {
            id: $('#tacheId').val(),
            adjustId: $('#baseId').val(),
            examineView: examineView,
            closedDateStr: dateStr
    };
    
    var result = tools.requestJsonRs("/powerAdjustCtrl/closedFlow.action", params);
    if(result.rtState) {
      var url = contextPath + "/flowRun/turnEnd.action";
      json = tools.requestJsonRs(url, {
          flowId : flowInfo.flowId,
          frpSid : flowInfo.frpSid
      });
      if (json.rtState) {
          alert("该文书已办理完成。");
          params = {
              adjustId: $('#baseId').val()
          };
          var rt = tools.requestJsonRs("/powerTempCtrl/transferPower.action", params);
          window.close();
      } else {
          alert("该文书办理失败。");
      }
    }

}