	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('main'));

	var optionSQFS = {
		title : {
			text : '申请方式分析',
			subtext : '',
			x : 'center'
		},
		toolbox : {
			show : true,
			feature : {
				myTool1 : {
					show : true,
					title : '条形图',
					icon : 'image:///xzfy/imgs/statana_icon/bar.png',
					onclick : function() {
						switchCharts(optionSQFS_bar);
					}
				},
				dataView : {
					readOnly : false,
					icon : 'image:///xzfy/imgs/statana_icon/tableview.png'
				},
				restore : {
					icon : 'image:///xzfy/imgs/statana_icon/refresh.png'
				},
				saveAsImage : {
					icon : 'image:///xzfy/imgs/statana_icon/save.png'
				},
			}
		},
		tooltip : {
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			itemWidth: 5,
	        itemHeight: 5,
			data : [ '网上申请', '当面接待', '来件申请' ]
		},
		color : [ '#3781BD', '#D34946', '#91BB5E' ],
		series : [ {
			name : '',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : 70,
				name : '网上申请'
			}, {
				value : 50,
				name : '当面接待'
			}, {
				value : 44,
				name : '来件申请'
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				},
				normal: {// 饼图，线连接的文案
                    label: {
                        show: true,
                        formatter : "{b}，{c}，{d}%"
                    }
                }
			}
		} ]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(optionSQFS);

var optionSQFS_bar = {
		 title : {
				text : '申请方式分析',
				subtext : '',
				x : 'center'
			},
			legend : {
			    orient: 'vertical',
		        top: 'center',
		        left: 'right',
				data : [ '网上申请', '当面接待', '来件申请' ],
				itemWidth: 5,
		        itemHeight: 5
			},
			color : [ '#3781BD', '#91BB5E', '#00ADC7' ],
			tooltip : {
// trigger : 'axis'
			},
			toolbox : {
				show : true,
				feature : {
					myTool1 : {
						show : true,
						title : '饼图',
						icon : 'image:///xzfy/imgs/statana_icon/pie.png',
						onclick : function() {
							switchCharts(optionSQFS);
						}
					},
					dataView : {
						readOnly : false,
						icon : 'image:///xzfy/imgs/statana_icon/tableview.png'
					},
					restore : {
						icon : 'image:///xzfy/imgs/statana_icon/refresh.png'
					},
					saveAsImage : {
						icon : 'image:///xzfy/imgs/statana_icon/save.png'
					}
				}
			},
			calculable: true,
			xAxis : [ {
				type : 'category',
				data : [ '网上申请', '当面接待', '来件申请' ],

			} ],
			yAxis : [ {
				type : 'value',
				name : '数量'
			} ],
			series : [ {
                    name:'网上申请',
                    type: 'bar',
                    barWidth: 20,
                    barGap:'-100%',
                    itemStyle: {
                        normal: {
                            color: '#3781BD',
                            label: {
                                show: true,
                                position: 'top',
                                formatter:  function (params)  {
                                            if  (params.value  >  0)  {
                                                    return  params.value;
                                            }  else  {
                                                    return  '';
                                            }
                                    },
                            }
                        }
                    },
                    data: [70,0,0],
                },
                {
                name: '当面接待',
                type: 'bar',
                 barGap: '-100%',
                barWidth: 20,
                itemStyle: {
                    normal: {
                        color:  '#D34946',
                        label: {
                            show: true,
                            position: 'top',
                             formatter: function  (params) {
                                if (params.value > 0) {
                                    return params.value;
                                } else {
                                    return '';
                                }
                            },
                        }
                    }
                },
                data: [0,50,0],
            },
            {
                name: '来件申请',
                type: 'bar',
                 barGap: '-100%',
                barWidth: 20,
                itemStyle: {
                    normal: {
                        color:'#91BB5E',
                        label: {
                            show: true,
                            position: 'top',
                             formatter: function  (params) {
                                if (params.value > 0) {
                                    return params.value;
                                } else {
                                    return '';
                                }
                            },
                        }
                    }
                },
                data: [0,0,44],
            }]
};
var optionFYSX = {
	    title : {
	        text: '复议事项分析',
	        subtext: '',
	        x:'center'
	    },
	    toolbox: {
	        show: true,
	        feature: {
	        	 myTool1: {  
	                show: true,  
	                title: '条形图',  
	                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
	                onclick: function (){  
	                	switchCharts(optionFYSX_bar);
	                }  
	            },
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
	             
	        }
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        itemWidth: 5,
	        itemHeight: 5,
	        data: ['行政处罚','行政强制','行政征收','行政许可','行政确认','行政确权','信息公开','行政不作为','举报投诉处理','其他']
	    },
	    color:['#3781BD', '#D34946','#91BB5E', '#8463A0','#00ADC7', '#FF903D','#224F74','#822B2A','#597537','#513D61'],
	    series : [
	        {
	            name: '',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:2, name:'行政处罚'},
	                {value:11, name:'行政强制'},
	                {value:14, name:'行政征收'},
	                {value:2, name:'行政许可'},
	                {value:5, name:'行政确认'},
	                {value:10, name:'行政确权'},
	                {value:2, name:'信息公开'},
	                {value:20, name:'行政不作为'},
	                {value:8, name:'举报投诉处理'},
	                {value:2, name:'其他'}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
var optionFYSX_bar={
	title : {
		text : '复议事项分析',
		subtext : '',
		x : 'center'
	},
    legend: {
        data : ['行政处罚','行政强制','行政征收','行政许可','行政确认','行政确权','信息公开','行政不作为','举报投诉处理','其他'],
        orient: 'vertical',
        top: 'center',
        left: 'right',
        itemWidth: 5,
        itemHeight: 5
    },
    tooltip : {
        // trigger: 'axis'
    },
    toolbox: {
        show: true,
        feature: {
        	 myTool1: {  
                 show: true,  
                 title: '饼图',  
                 icon: 'image:///xzfy/imgs/statana_icon/pie.png',  
                 onclick: function (){  
                 	switchCharts(optionFYSX);
                 }  
             },
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
             
        }
    },
    calculable: true,
    xAxis : [
        {
            axisLabel: {
            rotate: 25,
            margin: 4
        },
        type : 'category',
        data : ['行政处罚','行政强制','行政征收','行政许可','行政确认','行政确权','信息公开','行政不作为','举报投诉处理','其他']
      
    }
    ],
    yAxis : [
        {
            type : 'value',
            name : '数量'
        }
    ],
    series : [
        {
            name: '行政处罚',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#3781BD',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[2,0,0,0,0,0,0,0,0,0]
        },
        {
            name: '行政强制',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#D34946',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,11,0,0,0,0,0,0,0,0]
        },
        {
            name: '行政征收',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#91BB5E',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,14,0,0,0,0,0,0,0]
        },
        {
            name: '行政许可',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#8463A0',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,2,0,0,0,0,0,0]
        },
        {
            name: '行政确认',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#00ADC7',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,5,0,0,0,0,0]
        },
        {
            name: '行政确权',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#FF903D',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,10,0,0,0,0]
        },
        {
            name: '信息公开',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#224F74',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,2,0,0,0]
        },
        {
            name: '行政不作为',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#822B2A',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,0,20,0,0]
        },
        {
            name: '举报投诉处理',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#597537',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,0,0,8,2]
        },
        {
            name: '其他',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#513D61',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
           data:[0,0,0,0,0,0,0,0,0,2]
        }
    ]
};
var optionXZGLLB_bar = {
		title : {
			text : '行政管理类型分析',
			subtext : '',
			x : 'center'
		},
	    legend: {
	        data : ['公安','土地','其他','房屋征补（拆迁）','城乡规划','食品药品','工商','社会保障','环保','民政'],
	        orient: 'vertical',
	        top: 'center',
	        left: 'right',
	        itemWidth: 5,
	        itemHeight: 5,
	    },
	    tooltip : {
	        // trigger: 'axis'
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
	        }
	    },
	    calculable: true,
	    xAxis : [
	        {
	            axisLabel: {
	            rotate: 25,
	            margin: 4
	        },
	        type : 'category',
	        data :  ['公安','土地','其他','房屋征补（拆迁）','城乡规划','食品药品','工商','社会保障','环保','民政'],
	      
	    }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '数量'
	        }
	    ],
	    series : [
	        {
	            name: '公安',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#3781BD',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[2178,0,0,0,0,0,0,0,0,0]
	        },
	        {
	            name: '土地',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#D34946',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,553,0,0,0,0,0,0,0,0]
	        },
	        {
	            name: '其他',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#91BB5E',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,378,0,0,0,0,0,0,0]
	        },
	        {
	            name: '房屋征补（拆迁）',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#8463A0',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,250,0,0,0,0,0,0]
	        },
	        {
	            name: '城乡规划',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#00ADC7',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,240,0,0,0,0,0]
	        },
	        {
	            name: '食品药品',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#FF903D',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,228,0,0,0,0]
	        },
	        {
	            name: '工商',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#224F74',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,240,0,0,0]
	        },
	        {
	            name: '社会保障',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#822B2A',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,0,228,0,0]
	        },
	        {
	            name: '环保',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#597537',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,0,0,240,0]
	        },
	        {
	            name: '民政',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#513D61',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	           data:[0,0,0,0,0,0,0,0,0,228]
	        }
	    ]
};
var optionSLLX={
		 title : {
				text : '受理类型分析',
				subtext : '',
				x : 'center'
			},
			toolbox : {
				show : true,
				feature : {
					myTool1 : {
						show : true,
						title : '条形图',
						icon : 'image:///xzfy/imgs/statana_icon/bar.png',
						onclick : function() {
							switchCharts(optionSLLX_bar);
						}
					},
					dataView : {
						readOnly : false,
						icon : 'image:///xzfy/imgs/statana_icon/tableview.png'
					},
					restore : {
						icon : 'image:///xzfy/imgs/statana_icon/refresh.png'
					},
					saveAsImage : {
						icon : 'image:///xzfy/imgs/statana_icon/save.png'
					},
				}
			},
			tooltip : {
				trigger : 'item',
				formatter : "{b} : {c} ({d}%)"
			},
			legend : {
				orient : 'vertical',
				left : 'left',
				itemWidth: 5,
		        itemHeight: 5,
				data : [ '立案受理', '不予受理', '告知','补正','转送','其它']
			},
			color : [ '#4F81BD', '#C0504D', '#9BBB59','#8064A2','#4BACC6','#F79646' ],
			series : [ {
				name : '',
				type : 'pie',
				radius : '55%',
				center : [ '50%', '60%' ],
				data : [ {
					value : 80,
					name : '立案受理'
				}, {
					value : 50,
					name : '不予受理'
				}, {
					value : 30,
					name : '告知'
				} , {
					value : 240,
					name : '补正'
				} , {
					value : 28,
					name : '转送'
				} , {
					value : 60,
					name : '其它'
				} ],
				itemStyle : {
					emphasis : {
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 0, 0, 0.5)'
					},
					normal: {
		                label: {
		                    show: true,
		                    formatter : "{b}，{c}，{d}%"
		                }
		            },
				}
			} ]
}
var optionSLLX_bar = {
		title : {
			text : '受理类型',
			subtext : '',
			x : 'center'
		},
	    legend: {
	        data : [ '立案受理', '不予受理', '告知','补正','转送','其它'],
	        orient: 'vertical',
	        top: 'center',
	        left: 'right',
	        itemWidth: 5,
	        itemHeight: 5,
	    },
	    tooltip : {
	        // trigger: 'axis'
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            myTool1: {  
	                show: true,  
	                title: '饼图',  
	                icon: 'image:///xzfy/imgs/statana_icon/pie.png',  
	                onclick: function (){  
	                	switchCharts(optionSLLX);
	                }  
	            }, 
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
	        }
	    },
	    calculable: true,
	    xAxis : [
	        {
	            axisLabel: {
	            rotate: 25,
	            margin: 4
	        },
	        type : 'category',
	        data :  [ '立案受理', '不予受理', '告知','补正','转送','其它'],
	      
	    }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '数量'
	        }
	    ],
	    series : [
	        {
	            name: '立案受理',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#3781BD',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[80,0,0,0,0,0]
	        },
	        {
	            name: '不予受理',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#D34946',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	             data:[0,50,0,0,0,0]
	        },
	        {
	            name: '告知',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#91BB5E',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	             data:[0,0,30,0,0,0]
	        },
	        {
	            name: '补正',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#8463A0',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	             data:[0,0,0,240,0,0]
	        },
	        {
	            name: '转送',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#00ADC7',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	             data:[0,0,0,0,28,0]
	        },
	        {
	            name: '其它',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#FF903D',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	             data:[0,0,0,0,0,60]
	        }
	    ]
};
var optionJALX={
		title : {
			text : '结案类型分析',
			subtext : '',
			x : 'center'
		},
		toolbox : {
			show : true,
			feature : {
				myTool1 : {
					show : true,
					title : '条形图',
					icon : 'image:///xzfy/imgs/statana_icon/bar.png',
					onclick : function() {
						switchCharts(optionJALX_bar);
					}
				},
				dataView : {
					readOnly : false,
					icon : 'image:///xzfy/imgs/statana_icon/tableview.png'
				},
				restore : {
					icon : 'image:///xzfy/imgs/statana_icon/refresh.png'
				},
				saveAsImage : {
					icon : 'image:///xzfy/imgs/statana_icon/save.png'
				},
			}
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			itemWidth: 5,
	        itemHeight: 5,
			data : [ '驳回', '维持', '确认违法','撤销','变更','责令履行','终止（调解）','终止（其他）','其他']
		},
		color : [ '#4F81BD', '#4F81BD', '#9BBB59','#8064A2','#4BACC6','#F79646','#2C4D75','#772C2A','#5F7530' ],
		series : [ {
			name : '',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : 280,
				name : '驳回'
			}, {
				value : 50,
				name : '维持'
			}, {
				value : 30,
				name : '确认违法'
			} , {
				value : 40,
				name : '撤销'
			} , {
				value : 28,
				name : '变更'
			} , {
				value : 60,
				name : '责令履行'
			} , {
				value : 45,
				name : '终止（调解）'
			} , {
				value : 40,
				name : '终止（其他）'
			} , {
				value : 40,
				name : '其他'
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				},
				normal: {
	                label: {
	                    show: true,
	                    formatter : "{b}，{c}，{d}%"
	                }
	            },
			}
		} ]
};
var optionJALX_bar = {
		title : {
			text : '结案类型分析',
			subtext : '',
			x : 'center'
		},
	    legend: {
	        data : [ '驳回', '维持', '确认违法','撤销','变更','责令履行','终止（调解）','终止（其他）','其他'],
	        orient: 'vertical',
	        top: 'center',
	        left: 'right',
	        itemWidth: 5,
	        itemHeight: 5,
	    },
	    tooltip : {
	        // trigger: 'axis'
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            myTool1: {  
	                show: true,  
	                title: '饼图',  
	                icon: 'image:///xzfy/imgs/statana_icon/pie.png',  
	                onclick: function (){  
	                	switchCharts(optionJALX);
	                }  
	            }, 
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
	        }
	    },
	    calculable: true,
	    xAxis : [
	        {
	            axisLabel: {
	            rotate: 25,
	            margin: 4
	        },
	        type : 'category',
	        data :  [ '驳回', '维持', '确认违法','撤销','变更','责令履行','终止（调解）','终止（其他）','其他'],
	      
	    }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '数量'
	        }
	    ],
	    series : [
	        {
	            name: '驳回',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#3781BD',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[280,0,0,0,0,0,0,0,0]
	        },
	        {
	            name: '维持',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#D34946',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,50,0,0,0,0,0,0,0]
	        },
	        {
	            name: '确认违法',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#91BB5E',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,30,0,0,0,0,0,0]
	        },
	        {
	            name: '撤销',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#8463A0',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,40,0,0,0,0,0]
	        },
	        {
	            name: '变更',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#00ADC7',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,28,0,0,0,0]
	        },
	        {
	            name: '责令履行',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#FF903D',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,60,0,0,0]
	        },
	        {
	            name: '终止（调解）',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#2C4D75',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }v
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,45,0,0]
	        },
	        {
	            name: '终止（其他）',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#772C2A',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,0,40,0]
	        },
	        {
	            name: '其他',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#5F7530',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,0,0,40]
	        }
	    ]
};
var optionJCYY={
		title : {
			text : '纠错原因分析',
			subtext : '',
			x : 'center'
		},
		toolbox : {
			show : true,
			feature : {
				myTool1 : {
					show : true,
					title : '条形图',
					icon : 'image:///xzfy/imgs/statana_icon/bar.png',
					onclick : function() {
						switchCharts(optionJCYY_bar);
					}
				},
				dataView : {
					readOnly : false,
					icon : 'image:///xzfy/imgs/statana_icon/tableview.png'
				},
				restore : {
					icon : 'image:///xzfy/imgs/statana_icon/refresh.png'
				},
				saveAsImage : {
					icon : 'image:///xzfy/imgs/statana_icon/save.png'
				},
			}
		},
		tooltip : {
			trigger : 'item',
			formatter : "{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			itemWidth: 5,
	        itemHeight: 5,
			data : [ '主要事实不清、证据不足', '适用依据错误', '违反法定程序',
			'超用或者滥用职权','具体行政行为明显不当','被申请人未提交证据、依据材料',
			'被申请人不履行法定职责','其他']
		},
		color : [ '#4F81BD', '#C0504D','#9BBB59','#8064A2','#4BACC6','#F79646','#2C4D75','#772C2A' ],
		series : [ {
			name : '',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : 23,
				name : '主要事实不清、证据不足'
			}, {
				value : 20,
				name : '适用依据错误'
			} , {
				value : 20,
				name : '违反法定程序'
			} , {
				value : 40,
				name : '超用或者滥用职权'
			} , {
				value : 20,
				name : '具体行政行为明显不当'
			} , {
				value : 10,
				name : '被申请人未提交证据、依据材料'
			} , {
				value : 10,
				name : '被申请人不履行法定职责'
			} , {
				value : 30,
				name : '其他'
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				},
				normal: {
	                label: {
	                    show: true,
	                    formatter : "{b}，{c}，{d}%"
	                }
	            },
			}
		} ]
};
var optionJCYY_bar={
		title : {
			text : '纠察原因分析',
			subtext : '',
			x : 'center'
		},
		grid:{//图形区域边距
	        top:'25%',//距上边距
	        // left:'25%',//距离左边距
	        right:'25%',//距离右边距
	        bottom:'25%',//距离下边距
	    },
	    legend: {
	        data :[ '主要事实不清、证据不足', '适用依据错误', '违反法定程序',
			'超用或者滥用职权','具体行政行为明显不当','被申请人未提交证据、依据材料',
			'被申请人不履行法定职责','其他'],
	        orient: 'vertical',
	        top: 'center',
	        left: 'right',
	        itemWidth: 5,
	        itemHeight: 5,
	    },
	    tooltip : {
	        // trigger: 'axis'
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            myTool1: {  
	                show: true,  
	                title: '饼图',  
	                icon: 'image:///xzfy/imgs/statana_icon/pie.png',  
	                onclick: function (){  
	                	switchCharts(optionJCYY);
	                }  
	            }, 
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
	        }
	    },
	    calculable: true,
	    xAxis : [
	        {
	            axisLabel: {
	            rotate: 25,
	            margin: 4
	        },
	        type : 'category',
	        data :  [ '主要事实不清、证据不足', '适用依据错误', '违反法定程序',
			'超用或者滥用职权','具体行政行为明显不当','被申请人未提交证据、依据材料',
			'被申请人不履行法定职责','其他']
	    }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '数量'
	        }
	    ],
	    series : [
	        {
	            name: '主要事实不清、证据不足',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#3781BD',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[23,0,0,0,0,0,0,0]
	        },
	        {
	            name: '适用依据错误',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#D34946',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,20,0,0,0,0,0,0]
	        },
	        {
	            name: '违反法定程序',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#91BB5E',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,20,0,0,0,0,0]
	        },
	        {
	            name: '超用或者滥用职权',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#8463A0',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,40,0,0,0,0]
	        },
	        {
	            name: '具体行政行为明显不当',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#00ADC7',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,20,0,0,0]
	        },
	        {
	            name: '被申请人未提交证据、依据材料',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#FF903D',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,10,0,0]
	        },
	        {
	            name: '被申请人不履行法定职责',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#2C4D75',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }v
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,10,0]
	        },
	        {
	            name: '其他',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 20,
	            itemStyle: {
	                normal: {
	                    color:  '#772C2A',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function (params) {
	                            if (params.value > 0) {
	                                return params.value;
	                            } else {
	                                return '';
	                            }
	                        },
	                    }
	                }
	            },
	            data:[0,0,0,0,0,0,0,30]
	        }
	    ]
};
var optionAJZT={
	title : {
		text : '办件状态分析',
		subtext : '',
		x : 'center'
	},
	toolbox : {
		show : true,
		feature : {
			myTool1 : {
				show : true,
				title : '条形图',
				icon : 'image:///xzfy/imgs/statana_icon/bar.png',
				onclick : function() {
					switchCharts(optionAJZT_bar);
				}
			},
			dataView : {
				readOnly : false,
				icon : 'image:///xzfy/imgs/statana_icon/tableview.png'
			},
			restore : {
				icon : 'image:///xzfy/imgs/statana_icon/refresh.png'
			},
			saveAsImage : {
				icon : 'image:///xzfy/imgs/statana_icon/save.png'
			},
		}
	},
	tooltip : {
		trigger : 'item',
		formatter : "{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		left : 'left',
		itemWidth: 5,
        itemHeight: 5,
		data : [ '登记中', '已登记', '受理中','已受理','补正','审理中','已审理',
		'中止','已结案','已归档']
	},
	color : [ '#4F81BD', '#C0504D','#9BBB59','#8064A2','#4BACC6','#F79646','#2C4D75','#772C2A','#5F7530','#4D3B62' ],
	series : [ {
		name : '',
		type : 'pie',
		radius : '55%',
		center : [ '50%', '60%' ],
		data : [ {
			value : 40,
			name : '登记中'
		}, {
			value : 50,
			name : '已登记'
		} , {
			value : 30,
			name : '受理中'
		} , {
			value : 40,
			name : '已受理'
		} , {
			value : 28,
			name : '补正'
		} , {
			value : 60,
			name : '审理中'
		} , {
			value : 40,
			name : '已审理'
		} , {
			value : 10,
			name : '中止'
		}  , {
			value : 40,
			name : '已结案'
		}  , {
			value : 5,
			name : '已归档'
		} ],
		itemStyle : {
			emphasis : {
				shadowBlur : 10,
				shadowOffsetX : 0,
				shadowColor : 'rgba(0, 0, 0, 0.5)'
			},
			normal: {
                label: {
                    show: true,
                    formatter : "{b}，{c}，{d}%"
                }
            },
		}
	} ]
};
var optionAJZT_bar = {
   title : {
		text : '办件状态分析',
		subtext : '',
		x : 'center'
	},
    legend: {
        data :[ '登记中', '已登记', '受理中','已受理','补正','审理中','已审理',
		'中止','已结案','已归档'],
        orient: 'vertical',
        top: 'center',
        left: 'right',
        itemWidth: 5,
        itemHeight: 5,
     
    },
    tooltip : {
        // trigger: 'axis'
    },
    toolbox: {
        show: true,
        feature: {
            myTool1: {  
                show: true,  
                title: '饼图',  
                icon: 'image:///xzfy/imgs/statana_icon/pie.png',  
                onclick: function (){  
                	switchCharts(optionAJZT);
                }  
            }, 
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
        }
    },
    calculable: true,
    xAxis : [{
        type : 'category',
        data :  [ '登记中', '已登记', '受理中','已受理','补正','审理中','已审理',
		'中止','已结案','已归档']
    }
    ],
    yAxis : [
        {
            type : 'value',
            name : '数量'
        }
    ],
    series : [
        {
            name: '登记中',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#3781BD',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[40,0,0,0,0,0,0,0,0,0]
        },
        {
            name: '已登记',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#D34946',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,50,0,0,0,0,0,0,0,0]
        },
        {
            name: '受理中',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#91BB5E',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,30,0,0,0,0,0,0,0]
        },
        {
            name: '已受理',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#8463A0',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,40,0,0,0,0,0,0]
        },
        {
            name: '补正',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#00ADC7',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,28,0,0,0,0,0]
        },
        {
            name: '审理中',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#FF903D',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,60,0,0,0,0]
        },
        {
            name: '已审理',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#2C4D75',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }v
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,40,0,0,0]
        },
        {
            name: '中止',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#772C2A',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,0,10,0,0]
        },
        {
            name: '已结案',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#5F7530',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,0,0,40,0]
        },
        {
            name: '已归档',
            type: 'bar',
            barGap: '-100%',
            barWidth: 20,
            itemStyle: {
                normal: {
                    color:  '#4D3B62',
                    label: {
                        show: true,
                        position: 'top',
                        formatter: function (params) {
                            if (params.value > 0) {
                                return params.value;
                            } else {
                                return '';
                            }
                        },
                    }
                }
            },
            data:[0,0,0,0,0,0,0,0,0,5]
        }
    ]
};
function switchCharts(option){
	$("#tablediv_sqr").hide();
	$("#tablediv_bsqr").hide();
	$("#main").show();
	myChart.clear();
	myChart.setOption(option);
}