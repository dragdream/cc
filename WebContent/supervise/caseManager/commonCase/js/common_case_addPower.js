var actSubject = $('#actSubject').val();//实施主体ID
var powerType = $('#powerType').val();//职权类型
/**
 * 默认加载违法行为数据
 * @returns
 */
function doAddPowerInit() {
    if(actSubject == null || actSubject == '' || actSubject == 'null'){
        actSubject = "isEmpty";
    }
    var params = {
         code: $('#search_powerCode').val().trim(),
         name: $('#search_powerName').val().trim(),
         actSubject: actSubject,
         powerType: powerType
    };
    doInitAddPowerDataGird(params);
}

/**
 * 手动查询维护行为数据
 * @returns
 */
function doSearchAddPower() {
    if(actSubject == null || actSubject == '' || actSubject == 'null'){
        actSubject = "isEmpty";
    }
    var params = {
        code: $('#search_powerCode').val().trim(),
        name: $('#search_powerName').val().trim(),
        actSubject: actSubject,
        powerType: powerType
    };
    $('#select_common_case_addPower_table').datagrid('reload', params)
}

function doInitAddPowerDataGird(params) {
    datagrid = $('#select_common_case_addPower_table').datagrid({
       url: contextPath + '/powerCtrl/getPowerByActSubject.action',
       queryParams: params,
       pagination: true,
       singleSelect: false,
       pageSize : 10,
       pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 500, 1000 ],
       view: window.EASYUI_DATAGRID_NONE_DATA_TIP,
       toolbar: '#toolbar',
       // 工具条对象
       checkbox: true,
       border: false,
       striped: true,//隔行变色
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
           halign: 'center', 
           align: 'center',
           width: 5
       },
       {
           field:'ID',
           title:'序号',
           align:'center',
           formatter:function(value,rowData,rowIndex){
               return rowIndex+1;
           }
       },
       {
           field: 'code',
           title: '职权编号',
           halign: 'center', 
           align: 'center',
           width: 10
       },
       {
           field: 'name',
           title: '职权名称',
           halign: 'center', 
           align: 'left',
           width: 80,
           formatter: function(e, rowData) {
               var lins = "<lable class='common-overflow-hidden common-table-td-full-width' title='"+rowData.name+"'><a href='#' onclick='doOpenAddPowerShowPage(\"" + rowData.id + "\")'>" + rowData.name + "</a></lable>"
               return lins;
           }
       },
       {
           field: 'powerType',
           title: '职权类型 ',
           halign: 'center', 
           align: 'center',
           width: 10
       }]]
   });
}


function doOpenAddPowerShowPage(id) {
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


function getSelectedPower() {
    var formalPowers = $('#select_common_case_addPower_table').datagrid('getSelections');
    return formalPowers;
}