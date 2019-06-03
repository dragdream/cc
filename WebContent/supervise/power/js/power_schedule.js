var datagrid;

function doInit() {
    doInitDatagrid();
    doInitCurrentStatus();
    
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

function doInitCurrentStatus(){
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_ADJUST_STATE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#currentStatus').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(){
                $('#currentStatus').combobox('setValue',-1);
            }
        });
    }
}

function doInitDatagrid(params) {
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerAdjustCtrl/listByPage.action',
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
        {
            field: 'batchCode',
            title: '批次号',
            width: 15,
            halign : 'center'
        },
        {
            field: 'adjustReason',
            title: '修改原因',
            width: 30,
            halign : 'center'
        },
        {
            field: 'currentStatus',
            title: '批次状态',
            width: 50,
            halign : 'center'
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
                optStr = "<a href='#' onclick='showAdjust(\"" + rowData.id + "\")'>查看</a>";
                return optStr;
            }
        }]]
    });
}

function showAdjust(id) {
    var url=contextPath + "/powerAdjustCtrl/showExamineHis.action?adjustId=" + id;
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

function doSearch() {
    if($('#searchForm').form('validate')) {
        var params = {
                batchCode: $('#batchCode').textbox('getValue'),
                currentStatus: $('#currentStatus').combobox('getValue')
            }
            
            doInitDatagrid(params);
        
    }
}