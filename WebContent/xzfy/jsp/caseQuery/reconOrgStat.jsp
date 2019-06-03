<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header/header2.0.jsp" %>
<%@ include file="/header/easyui2.0.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>复议机关情况统计</title>
	<link rel="stylesheet" type="text/css" href="/xzfy/css/caseQuery.css" />
	<script type="text/javascript" src="/xzfy/js/common/common.js"></script>
	<script type="text/javascript" src="/xzfy/js/base/echarts.min.js"></script>
	<script type="text/javascript" src="/common/My97DatePicker/WdatePicker.js"></script>
	<style> 
	.table_center{
		border-collapse:collapse;
	}
	.tab_center td{
		vertical-align:middle;
		text-align:center
	}
	.btn-win-white  {width:110px !important;}
    </style> 
</head>

<body style="padding-left: 10px;padding-right: 10px" onload="">
<div id="toolbar" class = " clearfix" style="margin-top: 5px">
	    <div class="fl" style="position:static">
		    <img id="img1" class = 'title_img' src="<%=contextPath %>/common/zt_webframe/imgs/jhrw/icon_任务中心.png">
		    <span class="title" id="title">复议机关情况统计</span>
	    </div>
	    <span class="basic_border_grey" style="margin-top: 10px"></span>
	    
	    <div class="setHeight">
	        <form id="form1" style="">
	            <input type="hidden" id="orgId" value="" >
	            <table class="none_table" width="100%" height="65">
	           
	 				<tr id="accsearchTr1">
	 					<td>复议机关：
	 					    <select name="moreselAge" id="addNew">   
						        <option value="1" selected>默认机关名称</option>   
						        <option value="2">下级机关名称</option>    
						      </select>   
	 					    <input type="checkbox" name="" value="" />本级及下级
	 					</td>
	 					<td>开始时间：
	 					 	<input type="text" name="beginTime" id="beginTime" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" 
	 					 		class="Wdate BigInput" style="width:172px;height: 20px"/>
						</td>
						<td>结束时间：
	 						<input type="text" name="endTime" id="endTime" readonly onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:0});}',dateFmt:'yyyy-MM-dd'})" 
							    class="Wdate BigInput" style="width:172px;height: 20px"/>
	 					</td>
 						<td class="TableData"><button class="btn-win-white" type="button" onclick="">生成统计图</button></td>
	 				</tr>
	 				<tr id="accsearchTr1">
	 					<td>
	 						统计维度：
	 						<button class="btn-win-white" type="button" onclick="switchCharts(optionAJSL,false)">案件数量</button>
	 					</td>
	 					<td>
	 						<button class="btn-win-white" type="button" onclick="switchCharts(optionGBMAJSL,false)">各部门案件数量</button>
	 					</td>
						<td>
							<button class="btn-win-white" type="button" onclick="switchCharts('optionGZRYBAQK',true)">工作人员办案情况</button>
	 					</td>
		 					<!-- <td>
		 						<button class="btn-win-white" type="button" onclick="showTable('tablediv_bsqr')">被申请人类型</button>
		 					</td> -->
	 				</tr>
				</table>
			</form>
		</div>
		<span class="basic_border"></span>
		
   	</div>
   	<br>
   	<div id='buttonDiv' style="display:none">
   		<table class="none_table" width="100%" height="65">
			<tr id="accsearchTr1">
				<td>
					人员类型：
					<button class="btn-win-white" type="button" onclick="switchCharts(optionDJRY,true)">登记人员</button>
				</td>
				<td>
					<button class="btn-win-white" type="button" onclick="switchCharts(optionLARY,true)">立案人员</button>
				</td>
			    <td>
				    <button class="btn-win-white" type="button" onclick="switchCharts(optionSLRY,true)">审理人员</button>
				</td>
			</tr>
	</table>
   	</div>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div align="center" >
    <div id="main"style="width: 600px;height:400px;"></div>
    </div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        var optionAJSL = {
        		title : {
        	        text: '案件数量分析',
        	        subtext: '',
        	        x:'center'
        	    },
        	    toolbox: {
        	        show: true,
        	        feature: {
        	        	dataView: {
        	            	readOnly: false,
        	             	icon: 'image:///xzfy/imgs/statana_icon/tableview.png'
        	            },
        	            restore: {
        	             	icon: 'image:///xzfy/imgs/statana_icon/refresh.png'
        	            },
        	            saveAsImage: {
        	             	icon: 'image:///xzfy/imgs/statana_icon/save.png'
        	            },
        	            /* myTool1: {  
        	                show: true,  
        	                title: '条形图',  
        	                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
        	                onclick: function (){  
        	                	switchCharts(optionSQRLX_bar);
        	                }  
        	            },   */
        	        }
        	    },
        	    legend: {
        	        orient: 'vertical',
        	        left: 'right',
        	        top:'center',
        	        data: ['收案数量', '立案数量', '结案数量']
        	    },
        	    color:['#3781BD','#D34946','#91BB5E'],
        	    tooltip: {},
        	    // dataset: {
        	    //     source: [
        	    //         ['product', '收案数量', '立案数量', '结案数量'],
        	    //         ['深圳市人民政府', 10, 8, 2],
        	    //         ['南山区人民政府', 25, 22,20],
        	    //         ['罗湖区人民政府', 50,40,20],
        	    //         ['福田区人民政府', 10, 8, 2],
        	    //         ['龙岗区人民政府', 18, 8, 5],
        	    //         ['盐田区人民政府', 25, 22, 20],
        	    //         ['坪山区人民政府', 10, 8, 2],
        	    //         ['保安区人民政府', 50,40,20],
        	    //         ['光明区人民政府', 19, 9, 8]
        	    //     ]
        	    // },
        	    xAxis:[{
        	        type: 'category',
        	        axisLabel: {
        	            rotate: 25,
        	            margin: 4
        	        },
        	        data:['深圳市人民政府','南山区人民政府','罗湖区人民政府','福田区人民政府','龙岗区人民政府','盐田区人民政府','坪山区人民政府','保安区人民政府','光明区人民政府']
        	    }],
        	    yAxis: [
        	       {
        	            type: 'value',
        	            name:'数量'
        	        }
        	    ],
        	     series: [
        	        {
        	            name: '收案数量',
        	            type: 'bar',
        	            data: [10,25,50,10,18,25,10,50,19]
        	        },
        	        {
        	            name: '立案数量',
        	            type: 'bar',
        	            data: [8,22,40,8,8,22,8,40,9]
        	        },
        	        {
        	            name: '结案数量',
        	            type: 'bar',
        	            data: [2,20,20,2,5,20,2,20,8]
        	        }
        	    ]
        	};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(optionAJSL);
    </script>
    <script type="text/javascript" src="/xzfy/js/base/juicer-min.js"></script>
    <script type="text/javascript" src="/xzfy/js/caseList/reconOrgStat.js"></script>
</body>
</html>