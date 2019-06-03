var datagrid;
var _id;

function doInit() {
    
    doInitEnter();
    
    doInitTypeComboBox();

    doInitDatagrid({currentState: '20'});
}


/**
 * 获取回车事件
 * @returns
 */
function doInitEnter() {
    document.onkeyup = function (e) {
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            doSearch();
        }
    }
}


function doSearch() {
    if($('#searchForm').form('validate')) {

        var powerDetail = $('#powerDetail').combobox('getValues');
        if(powerDetail.length != 0) {
            powerDetail = powerDetail.join(',');
        } 
        var params = {
            name: $('#powerName').val(),
            powerType: $('#powerType').combobox('getValue'),
            powerDetail: powerDetail,
            currentState: $('#currentState').combobox('getValue')
        }
        
        doInitDatagrid(params);
    }
}

/**
 * 职权类型下拉框加载方法
 * @returns
 */
function doInitTypeComboBox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#powerType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(){
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType,
                        panelHeight: 'auto'
                    };
                    doInitDetailCombobox(params);
                } else {
                    $('#powerType').combobox('setValue', -1);
                }
                
            },
            onChange: function() {
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType
                    };
                    doInitDetailCombobox(params);
                }
            }
        });
    }
}

/**
 * 职权分类下拉框加载方法
 * @param parentCodeNo
 * @param codeNo
 * @returns
 */
function doInitDetailCombobox(params) {
    var result = tools.requestJsonRs("/sysCode/getSysParaByParentCode.action", params);
    if(result.rtState) {
        $('#powerDetail').combobox({
            data: result.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto'
        });
    }
}

function doInitDatagrid(params) {
    
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerTempCtrl/getPowerExamineGrid.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 20,
        pageList : [ 10, 20, 50, 100 ],
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
            width: 10
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
            field: 'currentStateValue',
            title: '职权状态',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData) {
                if(value == "提交") {
                    return "待审核";
                }else {
                    return value;
                }
            }
        },
        {
            field: 'name',
            title: '职权名称',
            width: 50,
            halign : 'center',
            formatter: function(e, rowData) {
                var lins = "<a href='#' onclick='showPowerTemp(\"" + rowData.id + "\")'>" + rowData.name + "</a>"
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
            field: 'powerDetail',
            title: '职权分类',
            width: 30,
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
            field: 'createDateStr',
            title: '创建日期',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: '___',
            title: '操作',
            width: 10,
            align : 'center',
            halign : 'center',
            formatter: function(e, rowData) {
                var optStr = "";
                if(rowData.currentState == '20') {
                    optStr = "&nbsp;&nbsp;<span title='审核'><a href='#' onclick='examinePowerTemp(\"" + rowData.id + "\")'><i class='fa fa-pencil-square-o common-green'></i></a></span>&nbsp;&nbsp;";
                } else {
                    optStr = "&nbsp;&nbsp;<span title='查看'><a href='#' onclick='showPowerTemp(\"" + rowData.id + "\")'><i class='fa fa-eye common-blue'></i></a></span>&nbsp;&nbsp;";
                }
                return optStr;
            }
        }]]
    });
}

function examinePowerTemp(id) {
    _id = id;
    var timestamp = Date.parse(new Date());
    var moduleId = timestamp/1000;
    var url=contextPath+"/powerTempCtrl/powerShow.action?id=" + id;
    top.bsWindow(url ,"审核",{width:"900",height:"400",buttons:
        [
          {name:"关闭",classStyle:"btn-alert-gray"},
          {name:"不予通过",classStyle:"btn-alert-blue"},
          {name:"审核通过",classStyle:"btn-alert-blue"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v == "审核通过") {
                var rtInfo = cw.doPass(window, moduleId);
            } else if(v == "不予通过") {
                debugger;
                var rtInfo = cw.doFail(window, moduleId);
                if(rtInfo) {
                    return true;
                    doSearch();
                }
            } else if(v=="关闭"){
                return true;
            }
        }
    }, moduleId);
}

function showPowerTemp(id) {
    var url=contextPath+"/powerTempCtrl/powerShow.action?id=" + id;
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

function doAuditingGroup() {
    var rows = $('#datagrid').datagrid('getSelections');
    if(rows.length == 0) {
        alert("请选择需要审核的职权！");
        return;
    } 
    var ids = [];
    for(var index in rows) {
    	if(rows[index].currentState != '20'){
    		$.MsgBox.newAlert('审核失败','职权状态为“审核通过”、“审核未通过”的职权不可进行审核，请检查后重新审核！');
    		return false;
    	}
        ids.push(rows[index].id);
    }
    
    var params = {
        id: ids.join(","),
        currentState: '90'
    };
    var rtInfo = tools.requestJsonRs("/powerTempCtrl/updateState.action", params);
    if(rtInfo.rtState) {
        $.MsgBox.Alert_auto("审核完成！");
        doSearch();
    }else {
        $.MsgBox.Alert_auto("审核失败！");
    }
}