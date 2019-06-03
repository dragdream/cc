var datagrid;

function doInit() {
	doSearchLawDetail();
}

function doSearchLawDetail() {
    var param = {
    	subjectId: subjectId
    };
    doInitDatagrid(param);
}

function doInitDatagrid(param) {
    datagrid = $('#selectPowerTable').datagrid({
        url: contextPath + '/powerCtrl/getPowerByActSubject.action',
        queryParams: params,
        pagination: false,
        singleSelect: false,
        /*pageSize : 15,
        pageList : [ 15, 20, 30, 40, 50, 60, 70, 80, 90, 100 ],*/
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
            width: 20
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
            width: 15
        },
        {
            field: 'name',
            title: '职权名称',
            halign: 'center', 
            align: 'left',
            width: 60,
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

function saveLawDetails () {
	//parent.asdf(2,1);
    var lawDetails = $('#selectPowerTable').datagrid('getSelections');
    return lawDetails;
} 