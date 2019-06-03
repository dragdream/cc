var datagrid;
var adjustId;

function doInit() {
    adjustId = $('#adjustId').val();
    doInitDatagrid();
}

function doInitDatagrid() {
    datagrid = $('#examine_his_datagrid').datagrid({
        url: contextPath + '/powerAdjustCtrl/examineList.action',
        queryParams: {adjustId: adjustId},
        pagination: false,
        singleSelect: true,
//        pageSize : 20,
//        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
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
            field: 'tacheName',
            title: '环节名称',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'examineDateStr',
            title: '审核开始日期',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData) {
                if(value == "null" ) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'closedDateStr',
            title: '结束日期 ',
            width: 15,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData) {
                if(value == "null" ) {
                    return "";
                } else {
                    return value;
                }
            }
        },
        {
            field: 'examinePersonName',
            title: '审核人 ',
            width: 10,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'examineView',
            title: '审核意见',
            width: 30,
            halign : 'center',
            formatter: function(value, rowData) {
                if(value == null ) {
                    return "";
                } else {
                    var lins = "<span title='" + value + "'>"+ value + "</span>"
                    return lins;
                }
                
            }
        },
        {
            field: '_opt',
            title: '操作 ',
            width: 10,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowData, rowIndex) {
                if(rowIndex != 0) {

                    return "<a href='#' onclick='showTacheInfo(\"" + rowData.id + "\")'>查看</a>";
                } else {
                    return "";
                }
            }
        }]]
    });
}

function showTacheInfo(tacheId) {
    var url=contextPath + "/powerAdjustCtrl/showTacheInfo.action?tacheId=" + tacheId;
    top.bsWindow(url ,"环节记录",{width:"900",height:"400",buttons:
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