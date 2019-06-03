var datagrid;

function doInit() {
    doInitDatagrid();
    
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
            doSearchLawDetail();
        }
    }
}

function doSearchLawDetail() {
    var param = {
        lawName: $('#lawName').val(),
        detailStrip: $('#detailStrip').val(),
        detailFund: $('#detailFund').val(),
        detailItem: $('#item').val(),
        content: $('#content').val()
    };
    
    doInitDatagrid(param);
}

function doInitDatagrid(param) {
    datagrid = $('#selectLawDetailTable').datagrid({
        url: contextPath + '/detailController/listByPage.action',
        queryParams: param,
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
        onLoadSuccess: function(data) {
        },
        columns: [[{
            field: 'id',
            checkbox: true,
            title: "ID",
            width: 20,
            halign : 'center'
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
            field: 'detailStrip',
            title: '条',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'detailFund',
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
            field: 'detailItem',
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
                var lins = "<span title='" + value + "'>" + value + "</span>";
                return lins;
            }
        }]]
    });
}

function saveLawDetails () {
    var lawDetails = $('#selectLawDetailTable').datagrid('getSelections');
    return lawDetails;
} 