/**
 * 页面初始化函数
 * @returns
 */
function doInit() {
    doInitEnter();
    
    initPowerType();
    initDatagrid();
    
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

/**
 * 查询职权
 * @returns
 */
function doSearch() {
    if($('#searchForm').form('validate')) {
        var powerDetail = $('#powerDetail').combobox('getValues');
        if(powerDetail.length != 0) {
            powerDetail = powerDetail.join(',');
        } 
        var param = {
            code: $('#powerCode').textbox('getValue'),
            name: $('#powerName').val(),
            powerType: $('#powerType').combobox('getValue'),
            powerDetail: powerDetail
        }
        $('#datagrid').datagrid('reload', param);
    }
}
/**
 * 初始化职权类型下拉框
 * @returns
 */
function initPowerType() {
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
            onChange: function() {
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType
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
            }
        });
    }
}
/**
 * 初始化datagrid
 * @returns
 */
function initDatagrid() {
    datagrid = $('#datagrid').datagrid({
        url: contextPath + '/powerCtrl/listByPage.action',
        pagination: true,
        singleSelect: true,
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
            formatter: function(e, rowData) {
                var lins = "<a href='#' onclick='doOpenShowPage(\"" + rowData.id + "\")'>" + rowData.name + "</a>"
                return lins;
            },
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
            field: 'powerDetail',
            title: '职权分类',
            width: 30,
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
            formatter: function(e, rowData) {
                var optStr = "<span title='查看'><a href='javaScript:void(0);' onclick='doOpenShowPage(\"" + rowData.id + "\")'><i class='fa fa-eye'></i></a></span>";
                return optStr;
            },
            align : 'center',
            halign : 'center'
        }]]
    });
}
/**
 * 打开职权查看页面
 * @returns
 */
function doOpenShowPage(id) {
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