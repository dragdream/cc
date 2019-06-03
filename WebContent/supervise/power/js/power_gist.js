var datagrid;

function doInit() {
    doInitDatagrid();
    doInitGistTypeCombobox();
    doInitPowerTypeCombobox();
    
    doInitEnter();
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
        var params = {
            lawName: $('#lawName').val(),
            gistType: $('#gistType').combobox('getValue'),
            powerName: $('#powerName').val(),
            powerType: $('#powerType').combobox('getValue')
        };
        doInitDatagrid(params);
    }
}

function doInitDatagrid(params) {
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerGistCtrl/listByPage.action',
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
            title: "ID",
            width: 20
        },
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
            width: 50,
            halign : 'center'
        },
        {
            field: 'gistType',
            title: '依据类型',
            width: 20,
            align : 'center',
            halign : 'center'
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
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            },
            align : 'center',
            halign : 'center'
        },
        {
            field: 'gistItem',
            title: '项',
            width: 10,
            formatter: function(value, rowData, rowIndex) { 
                if(value == 0) {
                    return "";
                } else {
                    return value;
                }
            },
            align : 'center',
            halign : 'center'
        },
        {
            field: 'content',
            title: '内容',
            width: 60,
            formatter: function(value, rowData, rowIndex) { 
                var lins = "<span title='" + value + "'>" + value + "</span>";
                return lins;
            },
            halign : 'center'
        },
        {
            field: 'powerName',
            title: '所属职权',
            width: 50,
            formatter: function(value, rowData, rowIndex) { 
                var lins = "<span title='" + value + "'>" + value + "</span>";
                return lins;
            },
            halign : 'center'
        },
        {
            field: 'powerType',
            title: '职权类型',
            width: 20,
            halign : 'center'
        }]]
    });
}

function doInitGistTypeCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "GIST_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#gistType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(){
                $('#gistType').combobox('setValue',-1);
            },
        });
    }
}

function doInitPowerTypeCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#powerType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(){
                $('#powerType').combobox('setValue',-1);
            },
        });
    }
}