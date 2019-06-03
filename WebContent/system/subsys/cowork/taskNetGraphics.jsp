<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<head>
    <meta charset="utf-8">
    <title>任务跟踪</title>
    
    <script>
    var contextPath = "<%=request.getContextPath() %>";
    var taskId = <%=request.getParameter("taskId")%>;
    function doInit(){
    	$("#main").layout({auto:true});
    	
    	var json = tools.requestJsonRs(contextPath+"/coWork/showTasksGraphics.action",{sid:taskId});
    	var list = json.rtData;
    	var nodes = [];
    	var lines = [];
    	var nodesMapping = {};
    	var tip = "";
    	for(var i=0;i<list.length;i++){
    		var content = ""+list[i].taskTitle+"\n进度："+list[i].progress+"%\n负责人："+list[i].chargerName;
    		//data:['主线节点','进行中','已完成','未开始','已作废/失败']
    		if(list[i].sid!=taskId){//非主线节点
    			if(list[i].status==0){//未开始
    				nodes.push({category:3,name:content,value:1});
    			}else if(list[i].status==8){//已完成
    				nodes.push({category:2,name:content,value:1});
    			}else if(list[i].status==7 || list[i].status==9){//已作废/失败
    				nodes.push({category:4,name:content,value:1});
    			}else{
    				nodes.push({category:1,name:content,value:1});
    			}
    		}else{//主线节点
    			nodes.push({category:0,name:content,value:20});
    			tip = content;
    		}
    		
    		nodesMapping[list[i].sid] = i;
    	}
    	
    	for(var i=0;i<list.length;i++){
    		var item = list[i];//当前节点信息
    		if(item.pid!=-1){
    			var pItemIndex = nodesMapping[item.pid];//父节点index
    			lines.push({source:i,target:pItemIndex,weight:1});
    		}
    	}
    	
    	option = {
    		    title : {
    		        text: '任务跟踪：'+tip,
    		        subtext: '数据来源任务中心',
    		        x:'right',
    		        y:'bottom'
    		    },
    		    tooltip : {
    		        trigger: 'item',
    		        formatter: '{a} : {b}'
    		    },
    		    legend: {
    		        x: 'left',
    		        data:['主线节点','进行中','已完成','未开始','已作废/失败']
    		    },
    		    series : [
    		        {
    		            type:'force',
    		            name : "关系",
    		            categories : [
    		                {
    		                    name: '主线节点',
    		                    itemStyle: {
    		                        normal: {
    		                            color : '#66ccff'
    		                        }
    		                    }
    		                },
    		                {
    		                    name: '进行中',
    		                    itemStyle: {
    		                        normal: {
    		                            color : '#c74545'
    		                        }
    		                    }
    		                },
    		                {
    		                    name: '已完成',
    		                    itemStyle: {
    		                        normal: {
    		                            color : '#69b239'
    		                        }
    		                    }
    		                },
    		                {
    		                    name: '未开始',
    		                    itemStyle: {
    		                        normal: {
    		                            color : '#e2de71'
    		                        }
    		                    }
    		                },
    		                {
    		                    name: '已作废/失败',
    		                    itemStyle: {
    		                        normal: {
    		                            color : '#8c8c8c'
    		                        }
    		                    }
    		                }
    		            ],
    		            itemStyle: {
    		                normal: {
    		                    label: {
    		                        show: true,
    		                        textStyle: {
    		                            color: '#800080'
    		                        }
    		                    },
    		                    nodeStyle : {
    		                        brushType : 'both',
    		                        strokeColor : 'rgba(255,215,0,0.4)',
    		                        lineWidth : 10
    		                    }
    		                },
    		                emphasis: {
    		                    label: {
    		                        show: false
    		                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
    		                    },
    		                    nodeStyle : {
    		                        r: 30
    		                    },
    		                    linkStyle : {}
    		                }
    		            },
    		            minRadius : 15,
    		            maxRadius : 25,
    		            density : 0.05,
    		            attractiveness: 1.2,
    		            nodes:nodes,
    		            links : lines
    		        }
    		    ]
    		};
    	var myChart = echarts.init(main);
    	myChart.setOption(option);
    	myChart.on('click',function (param) {
    		var dataIndex = param.dataIndex;
    		var item = list[dataIndex];
    		openFullWindow(contextPath+"/system/subsys/cowork/detail.jsp?taskId="+item.sid,"任务详情");
    	});
    }
    </script>
</head>

<body onload="doInit()" style="margin:0px;overflow:hidden">
<div id="layout">
	<div id="main" layout="center"></div>
</div>
</body>
</html>
<script src="<%=request.getContextPath() %>/common/required/r.js"></script>
<script src="<%=request.getContextPath() %>/common/echarts/echarts-plain.js"></script>
<script src="<%=request.getContextPath() %>/common/jquery/jquery-min.js"></script>
<script src="<%=request.getContextPath() %>/common/js/layout/layout.js"></script>
<script src="<%=request.getContextPath() %>/common/js/tools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/sys.js"></script>