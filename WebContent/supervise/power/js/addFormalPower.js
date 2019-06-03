function doInit() {
    doInitCombobox();
    var params = {
            powerType: $('#search_powerType').combobox('getValue')
    };
    doInitFormalPowerGrid(params);
    
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
            doSearchFormalPower();
        }
    }
}

function doSearchFormalPower() {
    var params = {
        powerType: $('#search_powerType').combobox('getValue'),
        name: $('#search_powerName').val(),
        code: $('#search_powerCode').val()
    };
    
    doInitFormalPowerGrid(params);
}

function doInitFormalPowerGrid(params) {
    datagrid = $('#selectFormalPowerTable').datagrid({
       url: contextPath + '/powerCtrl/listByPage.action',
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
               var lins = "<a href='#' onclick='doOpenFormalShowPage(\"" + rowData.id + "\")'>" + rowData.name + "</a>"
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
           title: '职权分类名称',
           width: 30,
           halign : 'center'
       },
       {
           field: 'createDateStr',
           title: '创建日期',
           width: 15,
           align : 'center',
           halign : 'center'
       }]]
   });
}


function doOpenFormalShowPage(id) {
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

function doInitCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_TYPE"});
    if(json.rtState) {
        $('#search_powerType').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            onLoadSuccess:function(data){
                $('#search_powerType').combobox('setValue', data[0].codeNo);
            }
        });
    }
}

function getSelectedPower() {
    var formalPowers = $('#selectFormalPowerTable').datagrid('getSelections');
    return formalPowers;
}