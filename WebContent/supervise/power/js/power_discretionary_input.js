var datagrid;
var _powerId;

function doInit() {
    _powerId = $('#powerId').val();
    doInitDatagrid({powerId: _powerId});
}

function doSearch() {
    var params = {
            powerId: _powerId,
            illegalFact: $('#illegalFact').val(),
            punishStandard: $('#punishStandard').val()
    };
    doInitDatagrid(params);
}

function doInitDatagrid(params) {
    datagrid = $('#discretionary_datagrid').datagrid({
        url: contextPath + '/discretionaryCtrl/listByPage.action',
        queryParams: params,
        pagination: true,
        singleSelect: true,
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
            title: "ID",
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
            field: 'illegalFact',
            title: '违法事实',
            width: 50,
            halign : 'center',
            formatter: function(value, rowData) {
                var lins = "<span title='" + value + "'>" + value + "</span>";
                return lins;
            }
        },
        {
            field: 'punishStandard',
            title: '裁量标准',
            width: 50,
            halign : 'center',
            formatter: function(value, rowData) {
                var lins = "<span title='" + value + "'>" + value + "</span>";
                return lins;
            }
        },
        {
            field: 'createDateStr',
            title: '创建日期 ',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: '___',
            title: '操作',
            align : 'center',
            halign : 'center',
            formatter: function(e, rowData) {
                var optStr = "&nbsp;&nbsp;<a href='#' onclick='doEditDiscretionary(\"" + rowData.id + "\")'>修改</a>&nbsp;&nbsp;";
                optStr = optStr + "<a href='#' onclick='doDeleteDiscretionary(\"" + rowData.id + "\")'>删除</a>&nbsp;&nbsp;";
                return optStr;
            }
        }]]
    });
}

function doEditDiscretionary(id) {
    var url=contextPath+"/discretionaryCtrl/addOrUpdate.action?id=" + id;
    top.bsWindow(url ,"修改",{width:"600",height:"300",buttons:
        [
            {name:"保存",classStyle:"btn-alert-blue"},
            {name:"返回",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v == "保存") {
                var result = cw.doSave();
                if(result.rtState) {
                    doInitDatagrid({powerId: _powerId});
                    $.MsgBox.Alert_auto("修改成功");
                    return true;
                } else {
                    return false;
                }
            } else if(v=="返回"){
                return true;
            }
        }
    });
}

function doDeleteDiscretionary(id) {
    var params = {
            id: id
    };
    
    var resultInfo = tools.requestJsonRs("/discretionaryCtrl/deleteById.action", params);
    if(resultInfo.rtState) {
        doInitDatagrid({powerId: _powerId});
        $.MsgBox.Alert_auto("删除成功");
    } else {
        $.MsgBox.Alert_auto("删除失败");
    }
}

function doAdd() {
    var url=contextPath+"/discretionaryCtrl/addOrUpdate.action?powerId=" + _powerId;
    top.bsWindow(url ,"新增",{width:"600",height:"300",buttons:
        [
            {name:"保存",classStyle:"btn-alert-blue"},
            {name:"返回",classStyle:"btn-alert-gray"}
        ]
        ,submit:function(v,h){
            var cw = h[0].contentWindow;
            if(v == "保存") {
                var result = cw.doSave();
                if(result.rtState) {
                    doInitDatagrid({powerId: _powerId});
                    $.MsgBox.Alert_auto("保存成功");
                    return true;
                } else {
                    return false;
                }
            } else if(v=="返回"){
                return true;
            }
        }
    });
}


