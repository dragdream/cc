var datagrid;
        // 初始化
        function doInit(){
            handleInitDate();
            //isLawEnfDepartment(${sessionScope.isLawEnfDep});
         // 初始化表格
            datagrid = $('#case_stage_datagrid') .datagrid( {
                view : window.EASYUI_DATAGRID_NONE_DATA_TIP,
                toolbar : '#toolbar'// 工具条对象
            });
        }
        // 查询
        function query(){
            var param=tools.formToJson("#search_form");
            datagrid = $('#case_stage_datagrid').datagrid({
                url:contextPath + "/analysisCaseCtrl/caseStage.action",
                queryParams:param,
                view:window.EASYUI_DATAGRID_NONE_DATA_TIP,
                pagination:true,
                singleSelect:true,
                toolbar:'#toolbar',//工具条对象
                pageSize: 50,//每页显示的记录条数，默认为50
                pageList: [50],//可以设置每页记录条数的列表
                striped:true,
                border:false,
                //idField:'formId',//主键列
                fitColumns:true,//列是否进行自动宽度适应
                columns:[
                    [
                        {field: 'number', title: '序号', width:10, align:'center',"rowspan":2,
                            formatter:function(value,rowData,rowIndex){
                                return rowIndex+1;
                            }
                        },
                        {field: 'areaName', title: '行政区划', width:50, "rowspan":2, sortable: true},
                        {field: 'registerSum', title:'立案数量', width:20, align:'center', "rowspan":2},
                        {field: 'revokeRegisterSum', title: '撤销立案<br/>数量', width:20, align:'center', "rowspan":2},
                        {field: 'surveySum', title: '调查取证<br/>数量', width:20, align:'center', "rowspan":2},
                        {title: '作出决定数量', width:80, align:'center', "colspan":3},
                        {field: 'revokePunishSum', title: '撤销处罚<br/>决定数量', width:30, align:'center', "rowspan":2},
                        {title: '结案数量', width:20, align:'center', "colspan":5},
                        {field: 'endSum', title: '终结数量', width:20, align:'center', "rowspan":2},
                        {field: 'simpleCaseSum', title: '简易案件<br/>数量', width:20, align:'center', "rowspan":2}
                    ],
                    [
                        {field:'noPunishSum', title:'作出不予<br/>处罚数量', align:'center'},
                        {field:'punishSum', title:'作出<br/>处罚数量',width:20, align:'center'},
                        {field:'punishCaseSum', title:'合计', width:20, align:'center'},
                        {field:'punishEndSum', title:'作出处罚<br/>结案', width:20, align:'center'},
                        {field:'revokePunishEndSum', title:'撤销原处罚<br/>决定结案', width:25, align:'center'},
                        {field:'revokeEndSum', title:'撤销立案<br/>结案', width:20, align:'center'},
                        {field:'noPunishEndSum', title:'作出不予<br/>处罚结案', width:20, align:'center'},
                        {field: 'closedCaseSum', title: '合计', width:20, align:'center'}
                    ]
                ]
            });
        
        }
        /* 判断登录用户是否是执法部门,对查询框进行处理 */
        function isLawEnfDepartment(isLawEnfDep){
            if(isLawEnfDep==true){
                $("#isManubrium").empty();
                $("#isManubrium").attr("disabled","disabled");
                $("#isManubrium").css("background","#EBEBE4");
                $("#subjectId").empty();
                $("#subjectId").attr("disabled","disabled");
                $("#subjectId").css("background","#EBEBE4");
            }
        }
        //初始化日期控件时间
        function handleInitDate(){
            var now = new Date(); //实例一个时间对象
            var beginCreateTimeStr = now.getFullYear() + '-01-01';
            var endCreateTimeStr = now.getFullYear() + '-12-31';
            $("#begincreateTimeStr").datebox('setValue', beginCreateTimeStr);
            $("#endcreateTimeStr").datebox('setValue', endCreateTimeStr);
        };
        function exportExcel(){
            $.MsgBox.Confirm ("提示", "是否确认导出所有查询结果？", function(){
                //导出所有的查询结果
                //获取查询参数
                var params = tools.formToJson($("#search_form"));
                $("#frame0").attr("src",contextPath+"/analysisCaseCtrl/exportCaseStage.action?params="+encodeURIComponent(tools.jsonObj2String(params)));
            });
        }
        function querySubjectId(isManubrium){
            $.ajax({
                url:"/basicSubjectController/list3BasicSubjects.action?isManubrium="+isManubrium,
                type:"post",
                dataType:"json",
                success:function(json){
                    var txt2="<option  value='' selected='selected' >全部</option>";
                    for (var i=0;i<json.rtData.length;i++){
                        
                        txt2+="<option  value='"+json.rtData[i][0]+"' >"+json.rtData[i][1]+"</option>";
                    }
                    $("#subjectId").html(txt2);
                },
                error:function(){
                }
            });
        }
        
        
        