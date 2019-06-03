
var datagrid;

function doInit() {
    var tacheId = $('#tacheId').val();
    doInitDatagrid({tacheId: tacheId});
}

function doInitDatagrid(params) {
    datagrid = $('#tache_datagrid').datagrid({
        url: contextPath + '/powerAdjustCtrl/tacheListByPage.action',
        queryParams: params,
        pagination: false,
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
            field: 'powerOpt',
            title: '调整方式',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'powerName',
            title: '职权名称',
            width: 65,
            halign : 'center'
        },
        {
            field: 'powerType',
            title: '职权类型 ',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'examineState',
            title: '审核状态 ',
            width: 10,
            align : 'center',
            halign : 'center'
        }]]
    });
}
