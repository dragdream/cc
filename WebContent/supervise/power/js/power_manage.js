var datagrid;

function doInit() {
    doInitEnter();
    
    doInitTypeComboBox();
    doInitStateCombobox();
    doInitOperationType();

    doInitTable();
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
    //        code: $('#powerCode').textbox('getValue'),
            name: $('#powerName').val(),
            powerType: $('#powerType').combobox('getValue'),
            powerDetail: powerDetail,
            currentState: $('#currentState').combobox('getValue')
    //        operationType: $('#operationType').combobox('getValue')
        }
        
        doInitTable(params);
    }
}

function doInitOperationType() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_OPERATION"});
    if(json.rtState) { 
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#operationType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(){
                $('#operationType').combobox('setValue', -1);
            }
        });
    }
    
}

/**
 * 职权类型下拉框加载方法
 * @returns
 */
function doInitTypeComboBox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
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
            panelHeight:'200px',
            formatter: function (row) {
                var opts = $(this).combobox('options');
                return '<input type="checkbox" class="combobox-checkbox" style="margin-right:5px;cursor:pointer;">' + row[opts.textField]
            },

            onShowPanel: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onLoadSuccess: function () {
                var opts = $(this).combobox('options');
                var target = this;
                var values = $(target).combobox('getValues');
                $.map(values, function (value) {
                    var el = opts.finder.getEl(target, value);
                    el.find('input.combobox-checkbox')._propAttr('checked', true);
                })
            },
            onSelect: function (row) {
                //console.log(row);
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', true);
            },
            onUnselect: function (row) {
                var opts = $(this).combobox('options');
                var el = opts.finder.getEl(this, row[opts.valueField]);
                el.find('input.combobox-checkbox')._propAttr('checked', false);
            }
        });
    }
}

function doInitStateCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_STATE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "全部"});
        $('#currentState').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(data){
                $('#currentState').combobox('setValue', data[0].codeNo);
            }
        });
    }
}

function doInitTable(params) {
    if(params == null) {
        params = {
            currentState: $('#currentState').combobox('getValue')
        };
    }
    
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerTempCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: false,
        pageSize : 20,
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
//        {
//            field: 'operationType',
//            title: '调整方式',
//            width: 15,
//            align : 'center',
//            halign : 'center'
//        },
        {
            field: 'currentStateValue',
            title: '职权状态',
            width: 15,
            align : 'center',
            halign : 'center'
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
            width: 8,
            align : 'center',
            halign : 'center',
            formatter: function(e, rowData) {
                var optStr = "";
                if(rowData.currentState == '10') {
                    optStr = "<span title='修改'><a href='javaScript:void(0);' onclick='doOpenEditPage(\"" + rowData.id + "\")'><i class='fa fa-pencil common-yellow'></i></a></span>&nbsp;&nbsp;" + 
                                "<span title='查看'><a href='javaScript:void(0);' onclick='showPowerTemp(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
                } else {
                    optStr = "<span title='查看'><a href='javaScript:void(0);' onclick='showPowerTemp(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
                    
                }
                return optStr;
            }
        }]]
    });
}

/**
 * 打开职权调整页面-新增
 * @returns
 */
function doOpenInputPage() {
    top.bsWindow("/powerTempCtrl/powerInput.action?type=10" ,"新增",{width:"900",height:"500",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
        ],
        submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存") {
                var resultInfo = cw.save();
                if(resultInfo != null) {
                    if(resultInfo.rtState) {
                        $.MsgBox.Alert_auto("保存成功");
                        doInitTable();
                        return true;
                    }else {
                        $.MsgBox.Alert_auto("保存失败");
                            return false;
                    }
                }
            } else if (v=="关闭"){
                return true;
            }
        }
    });
}

function doOpenEditPage(id) {
    top.bsWindow("/powerTempCtrl/powerInput.action?editFlag=1&type=10&id=" + id ,"职权调整",{width:"900",height:"500",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
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

function doOpenConfirmPage(id) {
    top.bsWindow("/powerTempCtrl/powerInput.action?type=20" ,"职权调整",{width:"900",height:"500",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
            
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

function doOpenDeletePage() {
    top.bsWindow("/powerTempCtrl/powerInput.action?type=50" ,"职权撤销",{width:"900",height:"500",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
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

function doOpenSplitPage() {
    top.bsWindow("/powerTempCtrl/powerInput.action?type=30" ,"职权拆分",{width:"900",height:"500",buttons:
        [
            {name:"保存并结束拆分",classStyle:"btn-alert-blue"},
            {name:"新建下一条",classStyle:"btn-alert-blue"},
            {name:"关闭",classStyle:"btn-alert-gray"}
        ],
        submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存并结束拆分") {
                var resultInfo = cw.save();
                if(resultInfo.rtState) {
                    showMessage("提示","保存成功");
                    doInitTable();
                    return true;
                }else {
                    showMessage("提示","保存失败");
                    return false;
                }
            } else if (v=="新建下一条"){
                var resultInfo = cw.save();
                if(resultInfo.rtState) {
                    showMessage("提示","保存成功");
                    doInitTable();
                    doOpenSplitNextPage(resultInfo.rtData.powerFormalId);
                    return true;
                }else {
                    showMessage("提示","保存失败");
                        return false;
                }
                return true;
            } else if (v=="关闭"){
                return true;
            }
        }
    });
}


function doOpenSplitNextPage(powerFormalId) {
    top.bsWindow("/powerTempCtrl/powerInput.action?type=30&isNext=1&powerFormalId=" + powerFormalId ,"职权拆分",{width:"900",height:"500",buttons:
        [
            {name:"保存并结束拆分",classStyle:"btn-alert-blue"},
            {name:"新建下一条",classStyle:"btn-alert-blue"},
            {name:"关闭",classStyle:"btn-alert-gray"}
        ],
        submit:function(v,h){
            var cw = h[0].contentWindow;
            if( v == "保存并结束拆分") {
                var resultInfo = cw.save();
                if(resultInfo.rtState) {
                    showMessage("提示","保存成功");
                    doInitTable();
                    return true;
                }else {
                    showMessage("提示","保存失败");
                        return false;
                }
            } else if (v=="新建下一条"){
                var resultInfo = cw.save();
                if(resultInfo.rtState) {
                    showMessage("提示","保存成功");
                    doInitTable();
                    doOpenSplitNextPage(resultInfo.rtData.powerFormalId);
                    return true;
                }else {
                    showMessage("提示","保存失败");
                        return false;
                }
                return true;
            } else if (v=="关闭"){
                return true;
            }
        }
    });
}

function doOpenMergePage() {
    top.bsWindow("/powerTempCtrl/powerInput.action?type=40" ,"职权合并",{width:"900",height:"500",buttons:
        [
         	{name:"关闭",classStyle:"btn-alert-gray"},
            {name:"保存",classStyle:"btn-alert-blue"}
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


function showMessage(title, message) {
    $.messager.show({
        title: title,
        msg: message,
        showType:'show'
    });
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

function doSubmit() {
    var rows = $('#datagrid').datagrid('getSelections');
    if(rows.length == 0) {
        alert("请选择需要提交的职权！");
        return;
    } 
    var ids = [];
    for(var index in rows) {
        ids.push(rows[index].id);
    }
    
    window.location.href="/powerTempCtrl/submitAdjust.action?ids=" + ids.join(",");
}

function doAuditing() {
    var rows = $('#datagrid').datagrid('getSelections');
    if(rows.length == 0) {
        alert("请选择需要提交的职权！");
        return;
    }
    var ids = [];
    var states = [];
    for(var index in rows) {
    	if(rows[index].currentState != '10' && rows[index].currentState != '99' ){
    		//不存在可提交数据
    		$.MsgBox.newAlert("提交审核失败","职权状态为“提交”、“审核通过”的职权不可再次提交，请检查后重新提交！");
    		return false;
    	}
        ids.push(rows[index].id);
    }

    var params = {
        id: ids.join(","),
        currentState: '20'
    };
    var rtInfo = tools.requestJsonRs("/powerTempCtrl/updateState.action", params);
    if(rtInfo.rtState) {
        $.MsgBox.Alert_auto("提交成功");
        doInitTable();
    }else {
        $.MsgBox.Alert_auto("提交失败");
    }
    
}