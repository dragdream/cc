var optionSQRLX_bar = {
		title : {
			text : '申请人类型分析',
			subtext : '',
			x : 'center'
		},
	    legend: {
	    	data: ['公民','法人或其他组织','个体工商户'],
	    	orient: 'vertical',
	        top: 'center',
	        left: 'right',
	        itemWidth: 5,
	        itemHeight: 5
	    },
	    color:['#3781BD', '#91BB5E','#00ADC7'],
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
	                	switchCharts(optionSQRLX);
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
	            type : 'category',
	            data : ['公民','法人或其他组织','个体工商户'],
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
	            name:'公民',
	            type: 'bar',
	            barWidth:40,
	            barGap:'-100%',
	            itemStyle: {
	                normal: {
	                    color: '#3781BD',
	                    label: {
	                        show: true,
	                        position: 'top',
	                        formatter: function(params){
	                            if (params.value > 0) {
	                                    return  params.value;
	                            }  else  {
	                                    return  '';
	                            }
	                        },
	                    }
	                }
	            },
	            data: [100,0,0],
	        },
	        {
	            name: '法人或其他组织',
	            type: 'bar',
	             barGap: '-100%',
	            barWidth: 40,
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
	            name: '个体工商户',
	            type: 'bar',
	             barGap: '-100%',
	            barWidth: 40,
	            itemStyle: {
	                normal: {
	                    color:'#91BB5E',
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
	            data: [0,0,80],
	        }
	    ]
	};
var optionBSQRLX_bar = {
		title : {
			text : '被申请人类型分析',
			subtext : '',
			x : 'center'
		},
	    legend: {
	        data : [ '市级政府', '市级政府的部门', '区级政府','区级政府的部门','其他' ],
	        orient: 'vertical',
	        top: 'center',
	        left: 'right',
	        itemWidth: 5,
	        itemHeight: 5
	    },
	    color:['#3781BD', '#91BB5E','#00ADC7'],
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
	        			switchCharts(optionBSQRLX);
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
	            type : 'category',
	            data : [ '市级政府', '市级政府的部门', '区级政府','区级政府的部门','其他' ]
	          
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
	            name: '市级政府',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 40,
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
	            data: [100,0,0,0,0]
	        },
	        {
	            name: '市级政府的部门',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 40,
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
	            data: [0,50,0,0,0]
	        },
	        {
	            name: '区级政府',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 40,
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
	            data: [0,0,80,0,0]
	        },
	        {
	            name: '区级政府的部门',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 40,
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
	            data: [0,0,0,88,0]
	        },
	        {
	            name: '其他',
	            type: 'bar',
	            barGap: '-100%',
	            barWidth: 40,
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
	            data: [0,0,0,0,10]
	        }
	    ]
		};
var optionSQRLX = {
	title : {
		text : '申请人类型分析',
		subtext : '',
		x : 'center'
	},
	toolbox: {
        show: true,
        feature: {
        	myTool1: {  
        		show: true,  
        		title: '条形图',  
        		icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
        		onclick: function (){  
        			switchCharts(optionSQRLX_bar);
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
		trigger : 'item',
		formatter : "{a} <br/>{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		left : 'left',
		data : [ '公民', '法人或其他组织', '个体工商户' ]
	},
	color:['#3781BD', '#91BB5E','#00ADC7'],
	series : [ {
		name : '',
		type : 'pie',
		radius : '55%',
		center : [ '50%', '60%' ],
		data : [ {
			value : 100,
			name : '公民'
		}, {
			value : 50,
			name : '法人或其他组织'
		}, {
			value : 80,
			name : '个体工商户'
		} ],
		itemStyle : {
			emphasis : {
				shadowBlur : 10,
				shadowOffsetX : 0,
				shadowColor : 'rgba(0, 0, 0, 0.5)'
			}
		}
	} ]
};
var optionBSQRLX = {
		title : {
			text : '被申请人类型分析',
			subtext : '',
			x : 'center'
		},
		toolbox : {
			show : true,
			feature : {
	            myTool1: {  
	                show: true,  
	                title: '条形图',  
	                icon: 'image:///xzfy/imgs/statana_icon/bar.png',  
	                onclick: function (){  
	                	switchCharts(optionBSQRLX_bar);
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
			trigger : 'item',
			formatter : "{a} <br/>{b} : {c} ({d}%)"
		},
		legend : {
			orient : 'vertical',
			left : 'left',
			data : [ '市级政府', '市级政府的部门', '区级政府','区级政府的部门','其他' ]
		},
		color:['#3781BD', '#3781BD','#00ADC7','#224F74','#597537'],
		series : [ {
			name : '',
			type : 'pie',
			radius : '55%',
			center : [ '50%', '60%' ],
			data : [ {
				value : 100,
				name : '市级政府'
			}, {
				value : 50,
				name : '市级政府的部门'
			}, {
				value : 80,
				name : '区级政府'
			}, {
				value : 88,
				name : '区级政府的部门'
			}, {
				value : 10,
				name : '其他'
			} ],
			itemStyle : {
				emphasis : {
					shadowBlur : 10,
					shadowOffsetX : 0,
					shadowColor : 'rgba(0, 0, 0, 0.5)'
				}
			}
		} ]
	};
function switchCharts(option){
	$("#tablediv_sqr").hide();
	$("#tablediv_bsqr").hide();
	$("#main").show();
	myChart.clear();
	myChart.setOption(option);
}
function showTable(tablediv){
	$("#tablediv_sqr").hide();
	$("#tablediv_bsqr").hide();
	$("#main").hide();
	$("#"+tablediv).show();
}