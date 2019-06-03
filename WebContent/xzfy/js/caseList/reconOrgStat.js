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
//            myTool1: {  
//                show: true,  
//                title: '条形图',  
//                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
//                onclick: function (){  
//                	switchCharts(optionSQRLX_bar);
//                }  
//            },  
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
var optionGBMAJSL = {
	title : {
        text: '各部门案件数分析',
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
//            myTool1: {  
//                show: true,  
//                title: '条形图',  
//                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
//                onclick: function (){  
//                	switchCharts(optionSQRLX_bar);
//                }  
//            },  
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
            rotate: 45,
            margin: 4
        },
        data:['税务局','公安局','审计局','民政局','教育局','市场监督管理局']
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
            data: [25,50,10,18,25,10]
        },
        {
            name: '立案数量',
            type: 'bar',
            data: [22,40,8,8,22,8]
        },
        {
            name: '结案数量',
            type: 'bar',
            data: [20,20,2,5,20,2]
        }
    ]
};
var optionDJRY = {
	    title : {
	        text: '登记人员办案情况分析',
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
//	            myTool1: {  
//	                show: true,  
//	                title: '条形图',  
//	                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
//	                onclick: function (){  
//	                	switchCharts(optionSQRLX_bar);
//	                }  
//	            },  
	        }
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'right',
	        top:'center',
	        data: ['登记中案件数', '已登记案件数']
	    },
	    color:['#3781BD','#D34946'],
	    tooltip: {},
	    xAxis:[{
	        type: 'category',
	        axisLabel: {
	            rotate: 45,
	            margin: 4
	        },
	        data:['赵一','钱二','孙三','李四','周五','吴六','郑七','王八一']
	    }],
	    yAxis: [
	       {
	            type: 'value',
	            name:'数量'
	        }
	    ],
	     series: [
	        {
	            name: '登记中案件数',
	            type: 'bar',
	            data: [15,19,10,18,25,10,4,8]
	        },
	        {
	            name: '已登记案件数',
	            type: 'bar',
	            data: [12,8,8,8,22,8,3,8]
	        }
	    ]
	};
optionLARY = {
	    title : {
	        text: '立案人员办案情况分析',
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
//	            myTool1: {  
//	                show: true,  
//	                title: '条形图',  
//	                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
//	                onclick: function (){  
//	                	switchCharts(optionSQRLX_bar);
//	                }  
//	            },  
	        }
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'right',
	        top:'center',
	        data: ['受理中案件数', '已受理案件数']
	    },
	    color:['#3781BD','#D34946'],
	    tooltip: {},
	    xAxis:[{
	        type: 'category',
	        axisLabel: {
	            rotate: 45,
	            margin: 4
	        },
	        data:['赵一','钱二','孙三','李四','周五','吴六','郑七','王八一']
	    }],
	    yAxis: [
	       {
	            type: 'value',
	            name:'数量'
	        }
	    ],
	     series: [
	        {
	            name: '受理中案件数',
	            type: 'bar',
	            data: [15,19,10,18,25,10,4,8]
	        },
	        {
	            name: '已受理案件数',
	            type: 'bar',
	            data: [12,8,8,8,22,8,3,8]
	        }
	    ]
	};
var optionSLRY = {
	    title : {
	        text: '审理人员办案情况分析',
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
	            // myTool1: {  
	            //     show: true,  
	            //     title: '条形图',  
	            //     icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
	            //     onclick: function (){  
	            //     	switchCharts(optionSQRLX_bar);
	            //     }  
	            // },  
	        }
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'right',
	        top:'center',
	        data: ['审理中中案件数', '已审理案件数','中止案件数']
	    },
	    color:['#3781BD','#D34946','#91BB5E'],
	    tooltip: {},
	 
	    xAxis:[{
	        type: 'category',
	        axisLabel: {
	            rotate: 45,
	            margin: 4
	        },
	        data:['赵一','钱二','孙三','李四','周五','吴六','郑七','王八一']
	    }],
	    yAxis: [
	       {
	            type: 'value',
	            name:'数量'
	        }
	    ],
	     series: [
	        {
	            name: '审理中中案件数',
	            type: 'bar',
	            data: [15,19,10,18,25,10,4,8]
	        },
	        {
	            name: '已审理案件数',
	            type: 'bar',
	            data: [12,8,8,8,22,8,3,8]
	        },
	        {
	            name: '中止案件数',
	            type: 'bar',
	            data: [3,5,6,9,2,13,5,5]
	        }
	    ]
	};
function switchCharts(option,isshowsubbtn){
	myChart.clear();
	if(isshowsubbtn){
		$("#buttonDiv").show();
	}else{
		$("#buttonDiv").hide();
	}
	if(option==='optionGZRYBAQK'){
		myChart.setOption(optionDJRY);
	}else{
		myChart.setOption(option);
	}
}
function gzrybaqkCharts(){
	myChart.clear();
	$("#buttonDiv").show();
	myChart.setOption(optionDJRY);
}
function showTable(tablediv){
	$("#tablediv_sqr").hide();
	$("#tablediv_bsqr").hide();
	$("#main").hide();
	$("#"+tablediv).show();
}