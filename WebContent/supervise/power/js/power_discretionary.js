var datagrid;

function doInit() {
    var params = {
        powerType: "01"
    };
    doInitDetailCombobox();
    doInitDatagrid(params);
    
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
            isDeiscretionary:$('#isDeiscretionary').combobox('getValue'),
            name: $('#powerName').val(),
            code: $('#powerCode').val(),
            powerDetail: $('#powerDetail').combobox('getValues').join(","),
            powerType: "01"
        };
        
        doInitDatagrid(params);
    }
}

function doInitDatagrid(params) {
    datagrid = $('#appreciation_datagrid').datagrid({
        url: contextPath + '/powerCtrl/listByPage.action',
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
            field: 'code',
            title: '职权编号',
            width: 15,
            align : 'center',
            halign : 'center'
        },
        {
            field: 'name',
            title: '职权名称',
            width: 60,
            halign : 'center',
            formatter: function(e, rowData) {
                var lins = "<a href='#' onclick='showPowerFormal(\"" + rowData.id + "\")'>" + rowData.name + "</a>"
                return lins;
            }
        },
        {
            field: 'powerDetail',
            title: '职权分类',
            width: 30,
            align : 'center',
            halign : 'center',
            formatter: function(value, rowDate, rowIndex) {
                var lins = "";
                if(value != null && value != '') {
                    lins = "<span title='" + value + "'>" + value + "</span>";
                }
                return lins;
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
            align : 'center',
            halign : 'center',
            formatter: function(e, rowData) {
                var optStr = "&nbsp;&nbsp;<a href='#' onclick='doEditDiscretionary(\"" + rowData.id + "\")'>自由裁量管理</a>&nbsp;&nbsp;";
                return optStr;
            }
        }]]
    });
}

function showPowerFormal(id) {
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

function doEditDiscretionary(id) {
    var url=contextPath+"/discretionaryCtrl/input.action?id=" + id;
    top.bsWindow(url ,"自由裁量权管理",{width:"900",height:"400",buttons:
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

/**
 * 职权分类下拉框加载方法
 * @param parentCodeNo
 * @param codeNo
 * @returns
 */
function doInitDetailCombobox() {
    var params = {
            parentCodeNo: "POWER_DETAIL",
            codeNo: "01"
        };
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