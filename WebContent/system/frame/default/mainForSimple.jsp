<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.util.*,java.text.SimpleDateFormat"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<%@ include file="/header/header2.0.jsp"%>
<%@ include file="/header/easyui2.0.jsp"%>
<%@ include file="/header/upload.jsp"%>
<script type="text/javascript" src="<%=contextPath%>/supervise/adminCoercion/js/coercion_direct_input.js"></script>
<script type="text/javascript" src="<%=contextPath%>/supervise/common/js/common.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/js/juicer-min.js"></script>
<script src="<%=contextPath%>/system/frame/default/js/echarts.min.js"></script>
<script src="<%=contextPath%>/system/frame/default/js/mainForSimple.js"></script>

<link rel="stylesheet" href="/xzfy/css/init1.css">
<link rel="stylesheet" type="text/css" href="xzfy/css/common.css">
    
<link rel="stylesheet" type="text/css" href="/common/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" type="text/css" href="/supervise/testFrontPage/iconfont/iconfont.css" />

<title>行政强制案件填报</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
        /*整体布局样式*/
        
        .main {
            width: 100%;
            height: 100%;
        }
        
        .main-up {
            width: 100%;
            height: 38%;
        }
        
        .main-down {
            width: 100%;
            height: 59%;
            margin-top: 1%;
        }
        
        .moheader {
            width: 100%;
            height: 35px;
            line-height: 35px;
            display: inline-block;
            text-align: left;
            text-indent: 10px;
            border-bottom: 1px solid #e8e8e8;
            background: #fbfbfb;
            color: #2d4c98;
            border-top-left-radius: 5px;
            border-top-right-radius: 5px;
            font-size: 13px;
        }
        
        .downleft {
            width: 74%;
            height: 100%;
            display: inline-block;
            vertical-align: top;
        }
        
        .downright {
            width: 25%;
            height: 100%;
            vertical-align: top;
            border: 1px solid #d1d1d1;
            float: right;
            border-radius: 5px;
        }
        /*待办事项*/
        
        .info {
            width: 30%;
            float: left;
            border: 1px solid #d1d1d1;
            border-radius: 5px;
            height: 100%;
            margin-bottom: 10px;
        }
        
        .infoheader {
            text-align: center;
            text-indent: 0;
        }
        
        .infocontent li {
            height: 22px;
            margin: 0 5px;
        }
        
        .infocontent li :first-child {
            padding-left: 20px;
            line-height: 22px;
            font-size: 14px;
            float: left;
        }
        
        .infocontent li :last-child {
            padding-right: 20px;
            line-height: 22px;
            font-size: 14px;
            float: right;
            color: #6989FF;
        }
        /*通知公告*/
        
        .notice {
            width: 40%;
            margin: 0 10px;
            border: 1px solid #d1d1d1;
            border-radius: 5px;
            float: left;
            height: 100%;
            margin-bottom: 10px;
        }
        
        .noticeUl li {
            list-style-type: square;
            margin-left: 27px;
            height: 25px;
            text-indent: -5px;
            line-height: 25px;
            cursor: pointer;
        }
        
        .noticeDate {
            width: 25%;
            text-align: center;
            float: right;
        }
        /*受理情况统计*/
        
        .pie {
            width: 28%;
            border: 1px solid #d1d1d1;
            border-radius: 5px;
            float: right;
            height: 100%;
            margin-bottom: 10px;
        }
        
        #pieDiv {
            width: 100%;
            text-align: center;
            height: calc(100% - 35px);
        }
        /*常用功能*/
        
        .enter {
            height: 25%;
            position: relative;
            border: 1px solid #d1d1d1;
            border-radius: 5px;
            margin-bottom: 10px;
        }
        
        .enterhead {
width: 60px;
    height: 60px;
    line-height: 30px;
    font-size: 16px;
    color: #2d4c98;
    margin: 0 auto;
    padding-right: 20px;
    border-right: 1px solid;
        }
        
        .enter ul {
            height: 100%;
            margin-left: 15px;
            display: table;
        }
        
        .enter ul li {
            margin-right: 10px;
            display: table-cell;
            width: 80px;
            height: 70%;
            text-align: center;
            vertical-align: middle;
        }
        
        .enter ul li span {
            display: inline-block;
            width: 100%;
            margin-top: 5px;
        }
        /*常用功能*/
        
        .list {
            height: 72%;
            border: 1px solid #d1d1d1;
            border-radius: 5px;
        }
        
        .data {
            border-bottom: 1px solid #e8e8e8;
        }
        
        #pieDiv {
            width: 100%;
            text-align: center;
            height: calc(100% - 35px);
        }
        /*结案情况统计*/
        
        #dataDiv {
            width: 100%;
            text-align: center;
            height: calc(100% - 35px);
        }
        /*待办tab切换*/
        
        #tableNav {
            width: 100%;
            border-bottom: 1px solid #ececed;
        }
        
        .tabactive {
            border-bottom: 3px solid #4374e8;
            color: #4374e8;
            font-size: 15px!important;
        }
        
        .tabnav-button input {
            margin-top: 3px;
            margin-left: 10px;
            border-radius: 5px;
            color: #fff;
            font-size: 14px;
            width: 80px;
            height: 26px;
        }
        
        #tab {
            /* font-size: 0; */
            height: 37px;
        }
        
        #tab li {
            /* border-right: 1px solid #ececed; */
            display: inline-block;
            padding: 0 20px;
            /* margin: 0 20px; */
            line-height: 38px;
            height: 38px;
            cursor: pointer;
            font-size: 13px;
            /* vertical-align: middle; */
        }
    </style>
</head>
<body style=" overflow: hidden; padding: 10px;">
    <div class="main">
        <div class="main-up">
            <!-- 待办提醒 -->
            <div class="info">
                <p class="moheader infoheader">您好，李莹莹！您所在复议机关：深圳市人民政府</p>
                <ul class="infocontent">
                    <li><span>预警</span><span>5</span></li>
                    <li><span>预警</span><span>5</span></li>
                    <li><span>预警</span><span>5</span></li>
                    <li><span>预警</span><span>5</span></li>
                    <li><span>预警</span><span>5</span></li>
                    <li><span>预警</span><span>5</span></li>
                    <li><span>预警</span><span>5</span></li>
                </ul>
            </div>
            <!-- 通知公告 -->
            <div class="notice">
                <p class="moheader">通知公告</p>
                <ul class="noticeUl">
                    <li><a>【置顶】抓住规范行为的牛鼻子123123541345</a><span class="noticeDate">2019-02-10</span></li>
                    <li><a>【置顶】抓住规范行为的牛鼻子123123541345</a><span class="noticeDate">2019-02-10</span></li>
                    <li><a>【置顶】抓住规范行为的牛鼻子123123541345</a><span class="noticeDate">2019-02-10</span></li>
                    <li><a>【置顶】抓住规范行为的牛鼻子123123541345</a><span class="noticeDate">2019-02-10</span></li>
                    <li><a>【置顶】抓住规范行为的牛鼻子123123541345</a><span class="noticeDate">2019-02-10</span></li>
                    <li><a>【置顶】抓住规范行为的牛鼻子123123541345</a><span class="noticeDate">2019-02-10</span></li>
                </ul>
            </div>
            <!-- 受理情况统计 -->
            <div class="pie">
                <p class="moheader">受理情况统计</p>
                <div id="pieDiv"></div>
            </div>
        </div>

        <div class="main-down">
            <div class="downleft">
                <!-- 常用功能 -->
                <div class="enter">
                    <ul>
                        <li>
                            <p class="enterhead">常用功能</p>
                        </li>
                        <li><img src="./img/home_red.png" alt="">
                            <span>案件登记</span>
                        </li>
                        <li><img src="./img/home_yellow.png" alt="">
                            <span>案件填报</span>
                        </li>
                        <li><img src="./img/home_blue.png" alt="">
                            <span>案件提取</span>
                        </li>
                        <li><img src="./img/home_green.png" alt="">
                            <span>案件提取</span>
                        </li>
                        <li><img src="./img/home_plus.png" alt=""></li>
                    </ul>
                </div>
                <!-- 待办事项/办件 -->
                <div class="list">
                    <div id="tableNav">
                        <ul id='tab' class='tab maintab'>
                            <li id="option1" class='tabactive'>待办事项</li>
                            <li id="option2">我的办件</li>
                        </ul>
                    </div>
                    <table width="100%" id="datagrid" fit="true"></table>
                </div>
            </div>
            <!-- 结案情况统计 -->
            <div class="downright">
                <p class="moheader">结案情况统计</p>
                <div id="dataDiv"></div>
            </div>
        </div>
    </div>


    <script type="text/javascript">
        //受理情况统计 饼图
        var myChart1 = echarts.init(document.getElementById('pieDiv'));
        var option1 = {
            // title: {
            //     text: '某站点用户访问来源',
            //     subtext: '纯属虚构',
            //     x: 'center'
            // },
            color: [
                '#7D83FF',
                '#79CCEA',
                '#EF7D65',
                '#FED176',
                '#87D8C9',
                '#4DA5FE'
            ],
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            // legend: {
            //     orient: 'vertical',
            //     left: 'left',
            //     data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
            // },
            series: [{
                name: '访问来源',
                type: 'pie',
                radius: '70%',
                center: ['50%', '50%'],
                data: [{
                    value: 122,
                    name: '行政许可'
                }, {
                    value: 326,
                    name: '行政强制'
                }, {
                    value: 480,
                    name: '行政处罚'
                }, {
                    value: 406,
                    name: '信息公开'
                }, {
                    value: 152,
                    name: '行政特权'
                }, {
                    value: 142,
                    name: '行政确认'
                }],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }]
        };
        if (option1 && typeof option1 === "object") {
            myChart1.setOption(option1, true);
        }

        //结案情况统计 横向柱状图
        var myChart2 = echarts.init(document.getElementById('dataDiv'));
        var option2 = {
            // title: {
            //     text: '世界人口总量',
            //     subtext: '数据来自网络'
            // },
            tooltip: {
                show: false,
                formatter: '{c}%'　,
                trigger: 'item',
            },
            // legend: {
            //     data: ['2011年', '2012年']
            // },
            grid: {
                top: '2%',
                left: '2%',
                right: '2%',
                bottom: '2%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                boundaryGap: [0, 0.01],
                show: false,
                axisLine: {
                    show: false
                }, // 控制y轴线是否显示
                splitLine: {
                    show: false
                }, // 控制网格线是否显示
                axisTick: {
                    show: false
                } // 去除y轴上的刻度线
            },
            yAxis: {
                type: 'category',
                data: ['撤销', '缺人违法', '变更', '责令履行', '终止，其他', '终止、调解', '维持', '驳回', '其他'],
                axisLine: {
                    show: false
                }, // 控制y轴线是否显示
                splitLine: {
                    show: false
                }, // 控制网格线是否显示
                axisTick: {
                    show: false
                } // 去除y轴上的刻度线
            },
            series: [{
                type: 'bar',
                data: [3, 3, 10, 48, 171, 359, 359, 127, 89],
                itemStyle: {
                    normal: {
                        color: new echarts.graphic.LinearGradient(1, 0, 0, 0, [{
                            offset: 0,
                            color: "#5FD7FC" // 0% 处的颜色
                        }, {
                            offset: 0.6,
                            color: "#649CFD" // 60% 处的颜色
                        }, {
                            offset: 1,
                            color: "#6B66F5" // 100% 处的颜色
                        }], false),
                        label: {
                            show: true,
                            position: 'right',
                            formatter: '{c}'
                        }
                    }
                },
            }]
        };
        if (option2 && typeof option2 === "object") {
            myChart2.setOption(option2, true);
        }
        //图表自适应
        window.onresize = function() {
            myChart1.resize();
            myChart2.resize();
        };

        //待办事项/办件切换
        $("#tab li").click(function() {
            $('#tab li').removeClass('tabactive');
            $(this).addClass('tabactive');
        });
    </script>
</body>
</html>