<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>被申请人被复议次数详情分析</title>
	<link rel="stylesheet" type="text/css" href="/xzfy/css/caseQuery.css" />
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/xzfy/js/base/echarts.min.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="">
<div class='data-page-container' id='page-container'>
        <div class='data-echarts-content'>
            <div id='echarts-left' style="width: 45%;height:400px; display:inline-block">

            </div>
            <div id='echarts-right' style="width: 45%;height:400px;display:inline-block">
            </div>
        </div>
        <div class='data-table'>
            <table cellspacing="0">
                <tr>
                    <th>序号</th>
                    <th>案件编号</th>
                    <th>申请人</th>
                    <th>被申请人</th>
                    <th>案件状态</th>
                    <th>复议机关</th>
                    <th>申请复议日期</th>
                    <th>复议事项</th>
                    <th>结案类型</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
            
            
                </tr>
                <tr>
                    <td>2</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                    <td>22</td>
                </tr>
            
            </table>
        </div>
    </div>
    
    <script>    
        var myChart1=echarts.init(document.getElementById('echarts-left'))
           option = {
                title: {
                   text: '被申请人名称：北京市人民政府',
                    subtext: '申请事项分析',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)",
                },
                legend: {
                    orient: 'vertical',
                    top:'center',
                    left:'left',
                    data: ['行政处罚', '行政强制', '行政征收', '行政许可','行政确认', '行政确权', '信息公开', '行政不作为', '举报投诉处理', '其它'],
                    itemWidth: 5,
                    itemHeight: 5
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        magicType: {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: 1548
                                }
                            }
                        },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },
                calculable: true,
                series: [
                    {
                        name: '访问来源',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: [
                            { value: 1000, name: '行政处罚',itemStyle:{color:'#3781BD'} },
                            { value: 200, name: '行政强制' ,itemStyle:{color:'#D34946'}},
                            { value: 200, name: '行政征收' ,itemStyle:{color:'#91BB5E'}},
                            { value: 200, name: '行政许可' ,itemStyle:{color:'#8463A0'}},
                            { value: 0, name: '行政确认'},
                            { value: 0, name: '举报投诉处理'},
                            { value: 0, name: '信息公开' },
                            { value: 0, name: '行政确权' },
                            { value: 0, name: '行政不作为' },
                            { value: 0, name: '其它' },
                        ]
                    }
                ]
            };

            myChart1.setOption(option);

            var myChart2 = echarts.init(document.getElementById('echarts-right'))
                option = {
                    title: {
                        text: '被申请人种类：省级政府',
                        subtext:'结案类型分析',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    legend: {
                         orient: 'vertical',
                        top: 'center',
                        left: 'right',
                        data: ['撤销', '驳回', '维持', '变更', '确认违法', '责令履行', '终止（和解/调解）', '其他'],
                        itemWidth:5,
                        itemHeight:5
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            mark: { show: true },
                            dataView: { show: true, readOnly: false },
                            magicType: {
                                show: true,
                                type: ['pie', 'funnel'],
                                option: {
                                    funnel: {
                                        x: '25%',
                                        width: '50%',
                                        funnelAlign: 'left',
                                        max: 1548
                                    }
                                }
                            },
                            restore: { show: true },
                            saveAsImage: { show: true }
                        }
                    },
                    calculable: true,
                    series: [
                        {
                            name: '访问来源',
                            type: 'pie',
                            radius: '55%',
                            center: ['50%', '60%'],
                            data: [
                                { value: 200, name: '撤销',itemStyle:{color:'#3781BD'} },
                                { value: 0, name: '驳回', itemStyle: { color: '#D34946' }},
                                { value: 0, name: '维持',itemStyle:{color:'#3781BD'} },
                                { value: 100, name: '变更',itemStyle:{color:'#8463A0'} },
                                { value: 100, name: '确认违法' ,itemStyle:{color:'#00ADC7'}},
                                { value: 100, name: '责令履行',itemStyle:{color:'#FF903D'} },
                                { value: 100, name: '终止（和解/调解）',itemStyle:{color:'#224F74'} },
                                { value: 100, name: '其他' ,itemStyle:{color:'#822B2A'}},
                            ]
                        }
                    ]
                };

                myChart2.setOption(option);
    </script>
    <script type="text/javascript" src="/xzfy/js/caseList/aplycntdtl.js"></script>
</body>
</html>