function doInit() {    
    doInitPowerTypeCombobox();
    doInitStateCombobox();

    doInitFlowState();

    var state = $('#flow_state').combobox('getValue');
    if(state == "01") {
        doInitDealDatagrid();
    } else if(state == "02"){
        doInitHandleDatagrid();
    }
}

function doInitFlowState() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_DEAL_STATE"});
    if(json.rtState) {
        $('#flow_state').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(data){
                $('#flow_state').combobox('setValue', data[0].codeNo);
            },
            onChange: function() {
                var state = $('#flow_state').combobox('getValue');
                if(state == "01") {
                    doInitDealDatagrid();
                } else if(state == "02"){
                    doInitHandleDatagrid();
                }
            }
        });
    }
    
}

function doQuery() {
    doInitDealDatagrid();
}

function doInitDealDatagrid() {
    // 获取待办任务列表
    var para =  tools.formToJson($("#searchForm")) ;
    var opts = [{field:'count',
                title:'序号',
                sortable:false,
                width:30,
                align : 'center',
                halign : 'center',
                formatter:function(value,rowData,rowIndex){
                    return rowIndex + 1;
                }
            },
            {field:'RUN_NAME',
                title:'任务文书',
                ext:'@任务文书',
                width:300,
                halign : 'center',
                sortable:true,
                formatter:function(a,data,c){
                    var render = "";
                    if(data.level==1 || data.level==0){
                        render = "<span style='color:green'>【普通】</span>";
                    }else if(data.level==2){
                        render = "<span style='color:orange'>【紧急】</span>";
                    }else if(data.level==3){
                        render = "<span style='color:red'>【加急】</span>";
                    }
                    render = render+"<a href='javascript:void(0)' onclick=\"doHandler('"+c+"')\" >"+data.runName+"</a>";
                    if(data.backFlag==1){
                        render += "&nbsp;&nbsp;<img src='/common/images/workflow/goback.png' title='退回步骤'/>";
                    }
                    return render;
                }
            },
            {field:'FLOW_NAME',
                title:'任务流程',
                ext:'@任务流程',
                width:150,
                halign : 'center',
                sortable:true,
                formatter:function(a,data,c){
                    return "<a href='javascript:void(0)' onclick='detailFlow("+data.flowId+")'>"+data.flowName+"</a>";
                }
            },
            {field:'BEGIN_PERSON',
                title:'任务发起人',
                ext:'@任务发起人',
                width:80,
                formatter:function(a,data,c){
                    return "<span target='_blank'>"+data.beginPerson+"</span>";
                }
            },
            {field:'PRCS_DESC',
                title:'任务状态',
                ext:'@任务状态',
                align : 'center',
                halign : 'center',
                width:100,
                formatter:function(a,data,c){
                    return "<a href='javascript:void(0)' onclick='detailRunFlow("+data.runId+","+data.flowId+")'>"+data.prcsDesc+"</a>";
                }
            },
            {field:'frp.BEGIN_TIME',
                title:'接收时间',
                ext:'@时间',
                width:120,
                sortable:true,
                formatter:function(a,data,c){
                    return "<span>"+data.beginTime.slice(0,-3)+"</span>";
                },
                align : 'center',
                halign : 'center'
            },
            {field:'fr.END_TIME',
                title:'状态',
                ext:'@状态',
                width:80,
                sortable:true,
                formatter:function(a,data,c){
                    if(data.endFlag==null){
                        return "<span style='color:green'>进行中</span>";
                    }
                    return "<span style='color:red'>已结束</span>";
                },
                align : 'center',
                halign : 'center'
            },
            {field:'_manage',
                title:'操作',
                ext:'@操作',
                width:50,
                formatter:function(value,rowData,rowIndex){
                    var render = "无权限";
                    if(rowData.opHandle){
                        render ="&nbsp;<a href='javascript:void(0)' onclick=\"doHandler('"+rowIndex+"')\" ><b>办理</b></a>";
                    }
                    /* if(rowData.delegate){
                        render+="&nbsp;<a href='javascript:void(0)' onclick=\"doDelegate('"+rowIndex+"')\" >委托</a>";
                    } */
                    return render;
                },
                align : 'center',
                halign : 'center'
            }];
    // 获取任务列表信息
    $('#datagrid').datagrid({
        url:contextPath + "/workflow/getReceivedWorks.action?flowSortId=4000",
        view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar',
        queryParams: para,
        pagination : true,
        pageSize : 20,
        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        fit : true,
        fitColumns : true,
        nowrap : true,
        border : false,
        idField : 'runId',
        sortOrder: 'desc',
        striped: true,
        remoteSort: true,
        singleSelect:true,
        columns:[opts],
        pagination:true,
        onLoadSuccess:function(){
        }
    });
    
    $(".datagrid-header-row td div span").each(function(i,th){
        var val = $(th).text();
         $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
    });
}

function doInitHandleDatagrid() {
    var para =  tools.formToJson($("#form")) ;
    var opts = [
            {field:'count',
                title:'序号',
                sortable:false,
                width:50,
                formatter:function(value,rowData,rowIndex){
                    return rowIndex + 1;
                },
                align : 'center',
                halign : 'center'
            },
            {field:'RUN_NAME',
                title:'任务文书',
                ext:'@任务文书',
                width:400,
                sortable:true,
                formatter:function(a,data,c){
                    var render = "";
                    if(data.level==1 || data.level==0){
                        render = "<span style='color:green'>【普通】</span>";
                    }else if(data.level==2){
                        render = "<span style='color:orange'>【紧急】</span>";
                    }else if(data.level==3){
                        render = "<span style='color:red'>【加急】</span>";
                    }
                    render = render+"<a href='javascript:void(0)' onclick=\"doHandler('"+c+"')\" >"+data.runName+"</a>";
                    if(data.backFlag==1){
                        render += "&nbsp;&nbsp;<img src='/common/images/workflow/goback.png' title='退回步骤'/>";
                    }
                    return render;
                },
                halign : 'center'
            },
            {field:'FLOW_NAME',
                title:'任务流程',
                ext:'@任务流程',
                width:200,
                sortable:true,
                formatter:function(a,data,c){
                    return "<a href='javascript:void(0)' onclick='detailFlow("+data.flowId+")'>"+data.flowName+"</a>";
                },
                halign : 'center'
            },
            {field:'BEGIN_PERSON',
                title:'任务发起人',
                ext:'@任务发起人',
                width:100,
                formatter:function(a,data,c){
                    return "<span target='_blank'>"+data.beginPerson+"</span>";
                },
                align : 'center',
                halign : 'center'
            },
            {field:'PRCS_DESC',
                title:'任务状态',
                ext:'@任务状态',
                width:200,
                formatter:function(a,data,c){
                    return "<a href='javascript:void(0)' onclick='detailRunFlow("+data.runId+","+data.flowId+")'>"+data.prcsDesc+"</a>";
                },
                align : 'center',
                halign : 'center'
            },
            {
                field:'currStepDesc',
                title:'当前步骤',
                width:200,
                halign : 'center'
            },
            {field:'frp.END_TIME',
                title:'办结时间',
                ext:'@时间',
                width:150,
                sortable:true,
                formatter:function(a,data,c){
                    return "<a>"+data.endTime.slice(0,-3)+"</a>";
                },
                align : 'center',
                halign : 'center'
            },
            {field:'fr.END_TIME',
                title:'状态',
                ext:'@状态',
                width:80,
                sortable:true,
                formatter:function(a,data,c){
                    if(data.endFlag==null){
                        return "<span style='color:green'>进行中</span>";
                    }
                    return "<span style='color:red'>已结束</span>";
                },
                align : 'center',
                halign : 'center'
            },
            {field:'_manage',
                title:'操作',
                ext:'@操作',
                width:150,
                formatter:function(value,rowData,rowIndex){
                    var render = [];
                    render.push("<a href=\"#\" onclick=\"historyStep("+rowData.runId+")\">历史步骤</a>");
                    return render.join("&nbsp;&nbsp;");
                },
                align : 'center',
                halign : 'center'
            }
        ];
    
    $('#datagrid').datagrid({
        url:contextPath + "/workflow/getHandledWorks.action?flowSortId=<%=flowSortId%>",
        view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
        toolbar : '#toolbar',
        title : '',
        iconCls : 'icon-save',
        queryParams:para,
        pagination : true,
        pageSize : 20,
        pageList : [ 20, 30, 40, 50, 60, 70, 80, 90, 100 ],
        fit : true,
        fitColumns : true,
        nowrap : true,
        border : false,
        idField : 'runId',
        sortOrder: 'desc',
        striped: true,
        remoteSort: true,
        singleSelect:true,
        columns:[opts],
        pagination:true,
        onLoadSuccess:function(){
        }
    });
    
    $(".datagrid-header-row td div span").each(function(i,th){
        var val = $(th).text();
         $(th).html("<label style='font-weight: bolder;'>"+val+"</label>");
    });
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
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType,
                        panelHeight: 'auto'
                    };
                    doInitDetailCombobox(params);
                    doInitChangePowerType(powerType);
                } else {
                    $('#powerType').combobox('setValue', -1);
                }
                
            },
            onChange: function() {
                var powerType = $('#powerType').combobox('getValue');
                if(powerType != "") {
                    var params = {
                        parentCodeNo: "POWER_DETAIL",
                        codeNo: powerType
                    };
                    doInitDetailCombobox(params);
                }
            }
        });
    }
}

/**
 * 职权分类下拉框加载方法
 * @param parentCodeNo
 * @param codeNo
 * @returns
 */
function doInitDetailCombobox(params) {
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

function doInitStateCombobox() {
    var json = tools.requestJsonRs("/sysCode/getSysCodeByParentCodeNo.action", {codeNo: "POWER_STATE"});
    if(json.rtState) {
        json.rtData.unshift({codeNo: -1, codeName: "请选择"});
        $('#currentState').combobox({
            data: json.rtData,
            valueField: 'codeNo',
            textField: 'codeName',
            panelHeight: 'auto',
            onLoadSuccess:function(data){
                $('#currentState').combobox('setValue', data[1].codeNo);
            }
        });
    }
}

// 任务办理
function doHandler(rowIndex){
    var rows = $('#datagrid').datagrid('getRows');
    var frpSid = rows[rowIndex].frpSid;
    var runId = rows[rowIndex].runId;
    var flowId = rows[rowIndex].flowId;
    var para = "runId="+runId+"&frpSid="+frpSid+"&flowId="+flowId;
    window.openFullWindow(contextPath +'/workflow/toFlowRun.action?'+para,"流程办理");
}

// 点击打开任务流程
function detailFlow(flowId){
    var url = contextPath+"/system/core/workflow/flowrun/flowview/flowview.jsp?flowId="+flowId;
    openFullWindow(url,"任务流程");
}

//点击打开步骤与流程图
function detailRunFlow(runId,flowId){
    var url = contextPath+"/system/core/workflow/flowrun/flowview/index.jsp?runId="+runId+"&flowId="+flowId;
    openFullWindow(url,"任务状态");
}

//历史步骤
function historyStep(runId){
    var url=contextPath+"/system/core/workflow/flowrun/list/historyStep.jsp?runId="+runId;
    bsWindow(url ,"历史步骤",{width:"600",height:"320",buttons:
        [
         {name:"关闭",classStyle:"btn-alert-gray"}
         ]
        ,submit:function(v,h){
        var cw = h[0].contentWindow;
        if(v=="保存"){
        
        }else if(v=="关闭"){
            return true;
        }
    }}); 
}